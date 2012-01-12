/**
 *    '$RCSfile: DatabaseHandler.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2008-01-03 23:31:50 $'
 *   '$Revision: 1.21 $'
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
import java.util.ArrayList;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.download.DataStorageInterface;
import org.ecoinformatics.datamanager.download.DownloadHandler;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterface;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.quality.QualityCheck;
import org.ecoinformatics.datamanager.quality.QualityReport;
import org.ecoinformatics.datamanager.quality.QualityCheck.Status;

/**
 * The DatabaseHandler class is the top-level class for interacting with the
 * database. It uses the auxiliary classes DatabaseLoader and DatabaseAdapter
 * (and its children) to accomplish much of what it needs to do.
 * 
 * @author dcosta
 *
 */
public class DatabaseHandler
{
  /*
   * Class fields
   */
  private TableMonitor tableMonitor = null;
  
  
  /*
   * Instance fields
   */
  
  private String     dbAdapterName = null;
  private DatabaseAdapter databaseAdapter;
 

  /*
   * Constructors
   */
  
  /**
   * Constructs a DatabaseHandler.
   * 
   * @param  dbConnection  A database connection object.
   * @param  dbAdapterName The name of the database adapter subclass.
   * @return A DatabaseHandler object.
   */
  public DatabaseHandler(String dbAdapterName)
      throws Exception {
    this.dbAdapterName = dbAdapterName;

    /*
     * Construct the DatabaseAdapter using the appropriate child class, and
     * assign it to the databaseAdapter field.
     */
    if (dbAdapterName.equals(DatabaseAdapter.POSTGRES_ADAPTER)) {
      this.databaseAdapter = new PostgresAdapter();
    } 
    else if (dbAdapterName.equals(DatabaseAdapter.HSQL_ADAPTER)) {
      this.databaseAdapter = new HSQLAdapter();
    } 
    else if (dbAdapterName.equals(DatabaseAdapter.ORACLE_ADAPTER)) {
      this.databaseAdapter = new OracleAdapter();
    }

    /*
     * Construct a TableMonitor object and store it.
     */
    tableMonitor = new TableMonitor(databaseAdapter);
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
    Connection connection = DataManager.getConnection();
    boolean success = false;
    String sqlString;
    
    if ((tableName != null) && (!tableName.trim().equals(""))) {
      /*
       * If the table is in the database, drop it.
       */
      if (tableMonitor.isTableInDB(tableName)) {
        Statement stmt = null;
        sqlString = databaseAdapter.generateDropTableSQL(tableName);

        try {
          stmt = connection.createStatement();
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
          DataManager.returnConnection(connection);
        }
      }
      /*
       * Otherwise just clean up any table entry that may be present
       * for this table in the TableMonitor registry.
       */
      else {
        tableMonitor.dropTableEntry(tableName); 
        success = true;
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
   * Drops data tables from the database for all entities in a data package.
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
   * Drops data tables from the database for all entities in a data package
   * based on a specified packageId string.
   * 
   * @param  packageId the package identifier whose data tables are to be dropped
   * @return true if all data tables were successfully dropped, else false.
   */
  public boolean dropTables(String packageId) throws SQLException {
    boolean success = true;
    ArrayList<String> tableNames = tableMonitor.getDBTableNames(packageId);
    
    if (tableNames != null) {
      for (String tableName : tableNames) {
        success = dropTable(tableName) && success;
      }
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
    QualityCheck databaseTableQualityCheck = null;
    
    if (QualityReport.isQualityReporting()) {
      // Initialize the databaseTableQualityCheck
      databaseTableQualityCheck = new QualityCheck("Create database table");
      databaseTableQualityCheck.setSystem("knb");
      databaseTableQualityCheck.setQualityType(QualityCheck.QualityType.metadata);
      databaseTableQualityCheck.setStatusType(QualityCheck.StatusType.error);
      databaseTableQualityCheck.setDescription(
        "Status of creating a database table");
      databaseTableQualityCheck.setExpected(
        "A database table is expected to be generated from the EML attributes.");
    }
    
    tableName = tableMonitor.addTableEntry(entity);   

    /*
     * If the table monitor couldn't assign a name, then throw an exception.
     */
    if ((tableName == null) || (tableName.trim().equals(""))) {
      String message = "Entity has not been assigned a valid name.";
      if (entity != null && QualityReport.isQualityReporting()) {
        /*
         * Report database table status as 'error'
         */
        databaseTableQualityCheck.setStatus(Status.error);
        databaseTableQualityCheck.setFound(
          "An error occurred while creating the database table");
        databaseTableQualityCheck.setExplanation(message);
        entity.addQualityCheck(databaseTableQualityCheck);
      }
      throw new SQLException(message);
    }
    /*
     * If a table name was assigned for this entity, let's see whether we've 
     * already got the table in the database.
     */
    else {
      boolean doesExist = isTableInDB(tableName);
      AttributeList attributeList = entity.getAttributeList();
      
      /*
       * Even if we aren't going to execute the DDL, we still need to generate
       * it here because it has the side effect of initializing the database
       * field names for the attributes.
       */
      String ddlString = databaseAdapter.generateDDL(attributeList,tableName);
      
      /*
       * If the table is not already in the database, generate it. If it's
       * already there, we're done.
       */
      if (!doesExist) {
        Statement stmt = null;
        Connection connection = DataManager.getConnection();

        try {
          stmt = connection.createStatement();
          stmt.executeUpdate(ddlString);
          
          if (entity != null && QualityReport.isQualityReporting()) {
            /*
             * Report database table generation status as 'valid'
             */
            databaseTableQualityCheck.setStatus(Status.valid);
            databaseTableQualityCheck.setFound(
              "A database table was generated from the attributes description");
            databaseTableQualityCheck.setExplanation(ddlString);
            entity.addQualityCheck(databaseTableQualityCheck);
          }
        } 
        catch (SQLException e) {
          // If something went wrong, drop the table entry from the registry.
          tableMonitor.dropTableEntry(tableName);
          String message = 
            "SQLException while generating data table '" + tableName +
            "' for entity '" + entity.getName() + "': " + e.getMessage() + 
            "\n" + ddlString;
          System.err.println(message);
          e.printStackTrace();
          
          if (entity != null && QualityReport.isQualityReporting()) {
            /*
             * Report database table status as 'error'
             */
            databaseTableQualityCheck.setStatus(Status.error);
            databaseTableQualityCheck.setFound(
              "An error occurred while creating the database table");
            databaseTableQualityCheck.setExplanation(message);
            entity.addQualityCheck(databaseTableQualityCheck);
          }
          
          SQLException se = new SQLException(message);
          throw (se);
        }
        finally {
          if (stmt != null) stmt.close();
          DataManager.returnConnection(connection);
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
   * @param  endPointInfo which provides ecogrid endpoint information
   * @return  true on success, false on failure
   */
  public boolean loadDataToDB(DataPackage dataPackage, EcogridEndPointInterface endInfo) {
    Entity[] entities = dataPackage.getEntityList();
    boolean success = true;
    
    for (int i = 0; i < entities.length; i++) {
      Entity entity = entities[i];
      success = success && loadDataToDB(entity, endInfo);
    }
    
    return success;
  }
  

  /**
   * Loads the data for a single entity into the database.
   * 
   * @param   entity  the Entity object whose data is to be loaded.
   * @param  endPointInfo which provides ecogrid endpoint information
   * @return  true on success, false on failure
   */
  public boolean loadDataToDB(Entity entity, EcogridEndPointInterface endPointInfo)
  {
	boolean success = false;
    
	if (entity != null) {
      // String identifier = entity.getEntityIdentifier();
      DownloadHandler downloadHandler = entity.getDownloadHandler(endPointInfo);
      DatabaseLoader dbLoader = null;
      
      try {
        dbLoader = new DatabaseLoader(dbAdapterName, entity);
        DataStorageInterface[] storage = new DataStorageInterface[1];
        storage[0] = dbLoader;
        /*
         * downloadHandler.setDataStorageCladdList(storage); Thread loadData =
         * new Thread(downloadHandler); loadData.start(); while
         * (!downloadHandler.isCompleted()) {
         *  } success = downloadHandler.isSuccess();
         */
        success = downloadHandler.download(storage);
      } 
      catch (Exception e) {
        success = false;
      }
		
	}
    return success;
  }
  

  /**
   * Runs a selection query on the data contained in one or more data packages.
   * Not yet implemented.
   * 
   * @param ANSISQL      The ANSI SQL query string.
   * @param dataPackage  The data packages to be queried.
   * @return             A ResultSet object as returned by the database query.
   */
  public ResultSet selectData(String ANSISQL, DataPackage[] packages)
          throws SQLException {
    Connection connection = DataManager.getConnection();
    ResultSet rs = null;
    Statement stmt = null;
    
    try {
      stmt = connection.createStatement();
      rs = stmt.executeQuery(ANSISQL);
    }
    catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
    finally {
      //if (stmt != null) stmt.close();
      DataManager.returnConnection(connection);
    }
    
    return rs;
  }

}
