/**
 *    '$RCSfile: DatabaseHandler.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-09-28 00:58:55 $'
 *   '$Revision: 1.6 $'
 *
 *  For Details: http://kepler.ecoinformatics.org
 *
 * Copyright (c) 2003 The Regents of the University of California.
 * All rights reserved.
 *
 * Permission is hereby granted, without written agreement and without
 * license or royalty fees, to use, copy, modify, and distribute this
 * software and its documentation for any purdire, provided that the
 * above copyright notice and the following two paragraphs appear in
 * all copies of this software.
 *
 * IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 * FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 * ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN
 * IF THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 *
 * THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 * PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY
 * OF CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT,
 * UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */
package org.ecoinformatics.datamanager.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import org.ecoinformatics.datamanager.download.DataSourceNotFoundException;
import org.ecoinformatics.datamanager.download.DataStorageInterface;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;

public class DatabaseHandler implements DataStorageInterface
{
  /*
   * Class fields
   */
  
  
  /*
   * Instance fields
   */
	private Connection dbConnection  = null;
	private String     dbAdapterName = null;
    private DatabaseAdapter databaseAdapter;
    private TableMonitor tableMonitor = null;
    private Hashtable    pipeIOHash = new Hashtable();

  
  /*
   * Constructors
   */
	public DatabaseHandler(Connection dbConnection, String dbAdapterName)
        throws Exception
	{
		this.dbConnection = dbConnection;
		this.dbAdapterName = dbAdapterName;
    
    //Class databaseAdapterClass = Class.forName(dbAdapterName);
    //databaseAdapter = (DatabaseAdapter) databaseAdapterClass.newInstance();
    
    if (dbAdapterName.equals(DatabaseAdapter.POSTGRES_ADAPTER)) {
      this.databaseAdapter = new PostgresAdapter();
    }
    else if (dbAdapterName.equals(DatabaseAdapter.HSQL_ADAPTER)) {
      this.databaseAdapter = new HSQLAdapter();
    }
    else if (dbAdapterName.equals(DatabaseAdapter.ORACLE_ADAPTER)) {
      this.databaseAdapter = new OracleAdapter();
    }
    
    this.tableMonitor = new TableMonitor(dbConnection, dbAdapterName);
	}

  
  /*
   * Class methods
   */

  
  /*
   * Instance methods
   */

  /**
   * Determines whether the data table corresponding to a given identifier 
   * already exists in the database. This method is mandated by the
   * DataStorageInterface.
   * 
   * @param   identifier  the identifier for the data table
   * @return  true if the data table already exists in the database, else false
   */
  public boolean doesDataExist(String identifier) {
    boolean doesExist = false;
    
    try {
      String tableName = identifierToTableName(identifier);
      doesExist = tableMonitor.isTableInDB(tableName);
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
    
    return doesExist;
  }
  
  
  /**
   * Given a table name, drops the data table from the database.
   * 
   * @param   tableName  The name of the table that is to be dropped.
   * @return  true if the data table was successfully dropped, else false.
   */
  boolean dropTable(String tableName) throws SQLException {
    boolean success = false;
    String sqlString;
    
    if ((tableName != null) && (!tableName.trim().equals(""))) {
      Statement stmt = null;
      sqlString = databaseAdapter.generateDropTableSQL(tableName);

      try {
        stmt = dbConnection.createStatement();
        stmt.executeUpdate(sqlString);
        success = true;
        
        /*
         * Table was dropped, so we need to inform the table monitor that it
         * should drop the table entry from the data table registry.
         */
        success = success && tableMonitor.dropTableEntry(tableName);
      } 
      catch (SQLException e) {
        System.err.println("SQLException: " + e.getMessage());
        throw (e);
      }
      finally {
        if (stmt != null) stmt.close();
      }
    }

    return success;
  }


  /**
   * Given an Entity, drops the correspoding data table from the database.
   * 
   * @param   entity  The entity whose data table is to be dropped.
   * @return  true if the data table was successfully dropped, else false
   */
  public boolean dropTable(Entity entity) throws SQLException {
    boolean success = false;
    String tableName;
    
    tableName = entity.getDBTableName();
    
    if ((tableName == null) || (tableName.equals(""))) {
      throw(new SQLException("Entity does not have a valid name."));
    }
    else {
      success = dropTable(tableName);
    }
    
    return success;
  }
  
 
  /**
   * Drops data tables from the data for all entities in a data package.
   * 
   * @param  dataPackage the data package whose tables are to be dropped
   * @return true if all data tables were successfully dropped, else false.
   */
  public boolean dropTables(DataPackage dataPackage) throws SQLException {
    boolean success = true;
    Entity[] entities = dataPackage.getEntityList();

    // Drop the table for each entity in the data package.
    for (int i = 0; i < entities.length; i++) {
      Entity entity = entities[i];
      success = success && dropTable(entity);
    }
    
    return success;
  }
  

  /** 
   * Finishes serialization of the data to the local store. This method is
   * required for implementing DataStorageInterface.
   * 
   * @param identifier  the identifier for the data whose serialization is done
   * @param errorCode   a string indicating whether there was an error during
   *                    the serialization
   */
  public void finishSerialize(String identifier, String errorCode) {
	 
	  PipedIO pipeIO = (PipedIO)pipeIOHash.get(identifier);
	  PipedInputStream inputStream = null;
	  PipedOutputStream outputStream = null;
	  if (pipeIO != null)
	  {
		  inputStream = pipeIO.getPipedInputStream();
		  outputStream = pipeIO.getPipedOutputStream();
	  }
	  if (inputStream != null)
	  {
		  try
		  {
		    inputStream.close();
		  }
		  catch (Exception e)
		  {
			  
		  }
	  }
	  if (outputStream != null)
	  {
		  try
		  {
		    outputStream.close();
		  }
		  catch (Exception e)
		  {
			  
		  }
	  }
    
  }
  

  /**
   * Generates a table in the database for a given entity.
   * 
   * @param   entity  the entity whose table is to be generated in the database
   * @return  true if the table is successfully generated, else false
   */
  public boolean generateTable(Entity entity) throws SQLException {
    boolean success = true;
    String tableName;
    
    tableName = entity.getDBTableName();
    
    /*
     * If the entity can't tell us its table name, then return failure (false).
     */
    if ((tableName == null) || (tableName.trim().equals(""))) {
      throw new SQLException("Entity does not hava a valid name.");
    }
    else {
      boolean doesExist = doesDataExist(tableName);
      
      /*
       * If the table is not already in the database, generate it. If it's
       * already there, we're done.
       */
      if (!doesExist) {
        Statement stmt = null;
        AttributeList attributeList = entity.getAttributeList();
        String ddlString = databaseAdapter.generateDDL(attributeList,tableName);

        try {
          stmt = dbConnection.createStatement();
          stmt.executeUpdate(ddlString);
          // Tell the table monitor to add a new table entry.
          tableMonitor.addTableEntry(tableName);
        } 
        catch (SQLException e) {
          System.err.println("SQLException: " + e.getMessage());
          throw (e);
        }
        finally {
          if (stmt != null) stmt.close();
        }
      }
    }
    
    return success;
  }
  

  /**
   * Generates tables in the database for all entities in a data package.
   * 
   * @param  dataPackage  the data package whose entities are to have their
   *                      tables generated
   * @return true if all tables were successfully generated, else false
   */
  public boolean generateTables(DataPackage dataPackage) throws SQLException {
    boolean success = true;
    Entity[] entities = dataPackage.getEntityList();
    
    for (int i = 0; i < entities.length; i++) {
      Entity entity = entities[i];
      success = success && generateTable(entity);
    }
    
    return success;
  }
  
 
  /**
   * Given an identifier string, return its corresponding table name. (For now,
   * assume that they are equivalent.)
   * 
   * @param   identifier   the identifier string for the entity
   * @return  the corresponding table name
   */
  private String identifierToTableName(String identifier) {
    String tableName = identifier;
    
    return tableName;
  }
  

  /**
   * Accesses the data for a given identifier, opening an input stream on it
   * for loading. This method is required for implementing DataStorageInterface.
   * 
   * @param  identifier  An identifier of the data to be loaded.
   * @throws DataSourceNotFoundException Indicates that the data was not found
   *         in the local store.
   */
  public InputStream load(String identifier) throws DataSourceNotFoundException
  {
    InputStream inputStream = null;

    return inputStream;
  }
  

  /**
   * Loads the data for all entities in a data package into the database.
   * 
   * @param   dataPackage  A DataPackage containing a list of entities.
   * @return  true on success, false on failure
   */
  public boolean loadData(DataPackage dataPackage) {
    Entity[] entities = dataPackage.getEntityList();
    boolean success = true;
    
    for (int i = 0; i < entities.length; i++) {
      Entity entity = entities[i];
      success = success && loadData(entity);
    }
    
    return success;
  }
  

  /**
   * Loads the data for a single entity into the database.
   * 
   * @param   entity  the Entity object whose data is to be loaded.
   * @return  true on success, false on failure
   */
  public boolean loadData(Entity entity)
  {
	
    return true;
  }
  

  /**
   * Runs a selection query on the data contained in one or more data packages.
   * 
   * @param ANSISQL
   * @param dataPackage
   * @return
   */
  public ResultSet selectData(String ANSISQL, DataPackage[] packages)
  {
    ResultSet resultSet = null;
    
    return resultSet;
  }
  

  /**
	 * Start to serialize a remote inputstream. The OutputStream is 
	 * the destination in the local store.
   * The DownloadHandler reads data from the remote source and writes it to 
   * the output stream for local storage. For the DatabaseHandler class,
   * the database itself serves as the local store.
   * 
	 * @param  identifier  An identifier to the data in the local store that is
   *                     to be serialized.
	 * @return An output stream to the location in the local store where the data 
   *         is to be serialized.
	 */
	public OutputStream startSerialize(String identifier)
	{
        PipedOutputStream outputStream = null;
        try
        {
        	PipedIO pipe = new PipedIO();
        	pipeIOHash.put(identifier, pipe);
        	Thread newThread = new Thread(pipe);
        	newThread.start();
        	outputStream = pipe.getPipedOutputStream();
        }
        catch(IOException e)
        {
        	System.err.println("Error is "+e.getMessage());
        }
    
		return outputStream;
	}
	
	/*
	 * Private class which use PipedInputStream and PipedOutputStream to
	 * read data from DownloadHandler into database
	 */
	private class PipedIO implements Runnable
	{
		private PipedInputStream inputStream = null;
		private PipedOutputStream outputStream = null;
		private Entity entity = null;
		
		/**
		 * Generate a pair of connected PipedIO 
		 * @throws IOException
		 */
		public PipedIO() throws IOException
		{
			
			outputStream = new PipedOutputStream();
	        inputStream  = new PipedInputStream();
	        outputStream.connect(inputStream);
		}
		
		/**
		 * Gets PipedInputStream Object
		 * @return PipedInputStream
		 */
		public PipedInputStream getPipedInputStream()
		{
			return this.inputStream;
		}
		
		/**
		 * Gets PipedOutputStream Object
		 * @return PipedOutputStream Object
		 */
		public PipedOutputStream getPipedOutputStream()
		{
			return this.outputStream;
		}
		
		/**
		 * Sets entity which will be inserted into data
		 * @param entity
		 */
		public void setEntity(Entity entity)
		{
			this.entity = entity;
		}
		
		/**
		 * Use PipedInputStream which connected the PipedOutputStream in DownloadHandler
		 * to insert data into db
		 */
		public void run()
		{
			if (inputStream != null)
			{
				System.out.println(" inputStream is NOT null");
				byte[] array = new byte[1024];
				File outputFile = new File("/Users/jinsongzhang/dsafa21");
				FileOutputStream fileOutputStream = null;
				int size = 0;
				try
				{
				   fileOutputStream = new FileOutputStream(outputFile);
				   size =inputStream.read(array);
				   while (size != -1)
				   {
					   fileOutputStream.write(array, 0, size);
					   size =inputStream.read(array);
				   }
				}
				catch (Exception e)
				{
					
				}
				
				
			}
			else
			{
				System.out.println(" input stream is null");
			}
		}
	}
	
}
