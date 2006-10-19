/**
 *    '$RCSfile: DatabaseHandler.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-10-19 00:30:42 $'
 *   '$Revision: 1.12 $'
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    DatabaseHandler.tableMonitor =new TableMonitor(dbConnection, dbAdapterName);
	}

  
  /*
   * Class methods
   */

  
  /*
   * Instance methods
   */


  
  
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
    
    tableName = tableMonitor.addTableEntry(entity);   

    /*
     * If the table monitor couldn't assign a name, then throw an exception.
     */
    if ((tableName == null) || (tableName.trim().equals(""))) {
      throw new SQLException("Entity has not been assigned a valid name.");
    }
    /*
     * If a table name was assigned for this entity, let's see whether we've 
     * already got the table in the database.
     */
    else {
      boolean doesExist = isTableInDB(tableName);
      
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
        } 
        catch (SQLException e) {
          // If something went wrong, drop the table entry from the registry.
          tableMonitor.dropTableEntry(tableName);
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
   * Determines whether the data table exists in the database. 
   * This is simply a pass-through method to the TableMonitor.
   * 
   * @param   tableName  the name of the data table being checked
   * @return  true if the data table already exists in the database, else false
   */
  public boolean isTableInDB(String tableName) {
    boolean isPresent = false;
    
    try {
      isPresent = tableMonitor.isTableInDB(tableName);
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
    
    return isPresent;
  }
  
  
  /**
   * Loads the data for all entities in a data package into the database.
   * 
   * @param   dataPackage  A DataPackage containing a list of entities.
   * @return  true on success, false on failure
   */
  public boolean loadDataToDB(DataPackage dataPackage) {
    Entity[] entities = dataPackage.getEntityList();
    boolean success = true;
    
    for (int i = 0; i < entities.length; i++) {
      Entity entity = entities[i];
      success = success && loadDataToDB(entity);
    }
    
    return success;
  }
  

  /**
   * Loads the data for a single entity into the database.
   * 
   * @param   entity  the Entity object whose data is to be loaded.
   * @return  true on success, false on failure
   */
  public boolean loadDataToDB(Entity entity)
  {
	boolean success = false;
	if (entity != null)
	{
		String identifier = entity.getEntityIdentifier();
		DownloadHandler downloadHandler = entity.getDownloadHandler();
		DatabaseLoader dbLoader = null;
		try
		{
			dbLoader = new DatabaseLoader(dbConnection, dbAdapterName, entity);
			DataStorageInterface[] storage = new DataStorageInterface[1];
			storage[0] = dbLoader;
			downloadHandler.setDataStorageCladdList(storage);
			Thread loadData = new Thread(downloadHandler);
			loadData.start();
			while (!downloadHandler.isCompleted())
			{
				
			}
			success = downloadHandler.isSuccess();
		}
		catch(Exception e)
		{
			success = false;
		}
		
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
