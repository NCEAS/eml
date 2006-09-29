/**
 *    '$RCSfile: DatabaseHandler.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-09-29 00:23:33 $'
 *   '$Revision: 1.7 $'
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
import org.ecoinformatics.datamanager.download.DownloadHandler;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;

public class DatabaseHandler
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
    private static TableMonitor tableMonitor = null;
 

  
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
  public static boolean doesDataExist(String identifier) {
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
  private static String identifierToTableName(String identifier) {
    String tableName = identifier;
    
    return tableName;
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
	boolean success = false;
	if (entity != null)
	{
		String identifier = entity.getEntityIdentifier();
		DownloadHandler downloadHandler = entity.getDownloadHanlder();
		
	}
    return success;
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
  

  
	
	
	
}
