/**
 *    '$RCSfile: TableMonitor.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-11-08 22:48:08 $'
 *   '$Revision: 1.14 $'
 *
 *  For Details: http://kepler.ecoinformatics.org
 *
 * Copyright (c) 2003 The Regents of the University of California.
 * All rights reserved.
 *
 * Permission is hereby granted, without written agreement and without
 * license or royalty fees, to use, copy, modify, and distribute this
 * software and its documentation for any purpose, provided that the
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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.ecoinformatics.datamanager.parser.Entity;

/**
 * TableMonitor monitors all data tables in the database. It stores information
 * about each table in a data table registry:
 *  
 *   table name                    the database table name
 *   entity identifier             the entity identifier (its URL)
 *   entity name                   the entity name
 *   creation date                 creation date of the database table
 *   last usage date               last usage date of the database table
 *   priority (expiration policy)  controls whether the table can be expired 
 *                                 from the cache
 *   
 * It also sets the maximum amount of space that the database can use, and
 * attempts to free up space by dropping old tables when necessary.
 * 
 */
public class TableMonitor {
  
  /*
   * Class fields
   */


  /*
   * Instance fields
   */

  private DatabaseMetaData databaseMetaData = null; // For getting db metadata
  private Connection dbConnection  = null;   // the database connection
  private DatabaseAdapter    dbAdapter = null;   // the DatabaseAdapter name
  private final String DATA_TABLE_REGISTRY = "DATA_TABLE_REGISTRY";
                                             // name of the database table where
                                             // data tables are registered
  private final int DEFAULT_DB_SIZE = 100;   // default maximum DB size (in Mb)
  private int dbSize = DEFAULT_DB_SIZE;
  
  
  /*
   * Constructors
   */

  /**
   * Constructs a new TableMonitor object.
   * 
   * @param   dbConnection  the database Connection object
   * @param   dbAdapterName the DatabaseAdapter name
   * @return  a TableMonitor object
   */
  public TableMonitor(Connection dbConnection, DatabaseAdapter dbAdapter)
        throws SQLException {
    this.dbConnection = dbConnection;
    this.dbAdapter = dbAdapter;
    this.databaseMetaData = dbConnection.getMetaData();

    /*
     * Check for existence of dataTableRegistry table. Create it if it does not
     * already exist.
     */
    if (!isTableInDB(DATA_TABLE_REGISTRY)) {
      createDataTableRegistry();
    }
    
  }
  
  
  /*
   * Class methods
   */
	

  /*
   * Instance methods
   */
	
  /**
   * Adds a new table entry for a given Entity object. By default, the creation
   * date and last used date are set to the current date and time. By default,
   * the expiration policy is set to 1 (may be expired).
   * 
   * @param   entity        the Entity object for which a table entry is added
   * @return  the name of the table that was added, or null if not successful
   */
  public String addTableEntry(Entity entity) throws SQLException {
    String entityIdentifier = entity.getEntityIdentifier();
    String entityName = entity.getName();
    String packageId = entity.getPackageId();
    String insertString;
    Date now = new Date();
    String priority = "1";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Statement stmt = null;
    
    // Assign a table name for this entity
    String tableName = assignTableName(entityIdentifier, entityName);
    
    boolean inUse = isDBTableNameInUse(tableName);

    /*
     * If we already have an entry for this entity in the data table registry,
     * simply update its last usage date to the current date.
     */
    if (inUse) {
      setLastUsageDate(tableName, now);
    } 
    /*
     * Otherwise, insert a new entry for this entity into the data
     * table registry.
     */
    else {
      insertString = 
        "INSERT INTO " + 
        DATA_TABLE_REGISTRY + 
        " values(" + 
          "'" + tableName + "', " + 
          "'" + packageId + "', " + 
          "'" + entityIdentifier + "', " + 
          "'" + entityName + "', " + 
          "'" + simpleDateFormat.format(now) + "', " + 
          "'" + simpleDateFormat.format(now) + "', " + 
          priority + 
        ")";

      try {
        stmt = dbConnection.createStatement();
        stmt.executeUpdate(insertString);
      } 
      catch (SQLException e) {
        System.err.println("Error inserting record for " + tableName
            + " into the data table registry (" + DATA_TABLE_REGISTRY + ")");
        System.err.println("SQLException: " + e.getMessage());
        tableName = null;
      } 
      finally {
        if (stmt != null)
          stmt.close();
      }
    }
    
    entity.setDBTableName(tableName);
    return tableName;
  }
  
  
  /**
   * Assigns a table name to a particular entity name.
   * 
   * If the table name has previously been assigned to this entity, simply
   * return the previously assigned table name.
   * 
   * Otherwise, choose a table name to assign. If the table name is already in
   * use by a different entity, mangle the name, and check again. If still in
   * use, mangle again, check again, etc.
   * 
   * The table name assigned to the entity must be unique, and once we have
   * assigned a name for this entity we should not need to assign a new one.
   * 
   * @param entityIdentifier    the id of the Entity object
   * @param entityName  the name of the Entity object
   * @return tableName  the name of the database table assigned to this Entity
   *         object
   */
  String assignTableName(String entityIdentifier, String entityName) 
          throws SQLException {
    String tableName = null;
    String selectString = "SELECT TABLE_NAME, ENTITY_IDENTIFIER, ENTITY_NAME" +
                          " FROM " + DATA_TABLE_REGISTRY +
                          " WHERE ENTITY_IDENTIFIER='" + entityIdentifier + 
                          "' AND ENTITY_NAME='" + entityName + "'";
    Statement stmt = null;

    /*
     * First, determine whether this entity has already been assigned a table
     * name. If it has, just return the previously assigned table name.
     */
    try {
      stmt = dbConnection.createStatement();
      ResultSet rs = stmt.executeQuery(selectString);
      
      while (rs.next()) {
        tableName = rs.getString("table_name");
      }    
    }
    catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
    finally {
      if (stmt != null) stmt.close();
    }

    /*
     * If tableName is still null, it means that we have not already assigned
     * a table name to this entity. Now we have to go through a loop,
     * trying different candidate table names until we finally
     * find a name that is not already in use.
     */
    if (tableName == null) {
      String tableNameCandidate = 
                                DatabaseAdapter.getLegalDBTableName(entityName);
      
      while (tableName == null) {
        if (isDBTableNameInUse(tableNameCandidate)) {
          tableNameCandidate = mangleName(tableNameCandidate);
        }
        else {
          tableName = tableNameCandidate;
        }
      }
    }
    
    return tableName;
  }
  

  /**
   * Counts the number of rows in a table.
   * 
   * @param tableName         the table name
   * @return                  an integer indicating the row count, or -1 if
   *                          the table is not in the database 
   * @throws SQLException
   */
  public int countRows(String tableName) throws SQLException {
    int rowCount = -1;
    
    if (isTableInDB(tableName)) {
      String selectString = dbAdapter.getCountingRowNumberSQL(tableName);
      Statement stmt = null;

      try {
        stmt = dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery(selectString);
        
        while (rs.next()) {
          rowCount = rs.getInt("count");
        }    
      }
      catch (SQLException e) {
        System.err.println("SQLException: " + e.getMessage());
        throw(e);
      }
      finally {
        if (stmt != null) stmt.close();
      }
    }
    
    return rowCount;
  }
  
  
  /**
   * Creates the DATA_TABLE_REGISTRY table. This is the table that the
   * TableMonitor uses to keep track of data table information such as
   * data table name, when the data table was created, when it was last
   * used, and its priority (expiration policy) setting.
   *
   */
  private void createDataTableRegistry() throws SQLException {
    String createString = 
      "create table " + DATA_TABLE_REGISTRY + " " +
      "(" +
      "  TABLE_NAME varchar(64), " +         // database table name
      "  PACKAGE_ID varchar(64), " +         // package id
      "  ENTITY_IDENTIFIER varchar(256), " + // entity identifier
      "  ENTITY_NAME varchar(64), " +        // entity name
      "  CREATION_DATE date, " +             // creation date
      "  LAST_USAGE_DATE date, " +           // last usage date
      "  PRIORITY int" +                     // expiration policy
      ")";

    Statement stmt = null;

    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(createString);
    } 
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
    finally {
      if (stmt != null) stmt.close();
    }
  }
  
  
  /**
   * Drops a table entry for a given table name.
   * 
   * @param   tableName   the name of the table to be dropped from the database
   * @return  the row count returned by executing the SQL update
   */
  public boolean dropTableEntry(String tableName) throws SQLException {
    boolean success = false;
    String deleteString;
    int rowCount = -1;
    Statement stmt = null;

    deleteString = "DELETE FROM " + DATA_TABLE_REGISTRY + 
                   " WHERE TABLE_NAME='" + tableName + "'";
 
    try {
      stmt = dbConnection.createStatement();
      rowCount = stmt.executeUpdate(deleteString);
      success = (rowCount == 1);
    }
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
    finally {
      if (stmt != null) stmt.close();
    }
    
    return success;
  }
  

  /**
   * Frees up table space by dropping one or more old tables.
   * @param  databaseHandler  the databaseHandler which will free space
   * @return  the size of table which has been freed
   */
  public int freeTableSpace(DatabaseHandler databaseHandler)
          throws SQLException {
    int freedSpace = 0;
    String oldestTable = getOldestTable();
    
    return freedSpace;
  }
  
 
  /**
   * Gets the creation date of a given table in the database.
   * 
   * @param   tableName   the name of the table whose creation date is returned
   * @return  the creation date, a Date object
   */
  public Date getCreationDate(String tableName) throws SQLException {
    Date creationDate = null;
    String selectString = 
      "SELECT creation_date FROM " + DATA_TABLE_REGISTRY +
      " WHERE table_name='" + tableName + "'";
    Statement stmt = null;
    
    try {
      stmt = dbConnection.createStatement();             
      ResultSet rs = stmt.executeQuery(selectString);
      
      while (rs.next()) {
        creationDate = rs.getDate("creation_date");    
      }
    }
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
    finally {
      if (stmt != null) stmt.close();
    }
    
    return creationDate;
  }
  
 
  /**
   * Returns the name of the data table registry table. Used primarily for 
   * unit testing of this class by the TableMonitorTest class.
   * 
   * @return   The private constant, DATA_TABLE_REGISTRY.
   */
  public String getDataTableRegistryName () {
    return DATA_TABLE_REGISTRY;
  }
  

  /**
   * Gets the last usage date for a given table name in the database.
   * 
   * @param  tableName  the name of the the table whose last usage date
   *                    is returned
   * @return  the last usage date, a Date object
   */
  public Date getLastUsageDate(String tableName) throws SQLException {
    Date lastUsageDate = null;
    String selectString = 
      "SELECT last_usage_date FROM " + DATA_TABLE_REGISTRY +
      " WHERE table_name='" + tableName + "'";
    Statement stmt = null;
    
    try {
      stmt = dbConnection.createStatement();             
      ResultSet rs = stmt.executeQuery(selectString);
      
      while (rs.next()) {
        lastUsageDate = rs.getDate("last_usage_date");    
      }
    }
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
    finally {
      if (stmt != null) stmt.close();
    }
    
    return lastUsageDate;
  }
  

  /**
   * Find the oldest table in the data table registry (the table whose 
   * last_usage_date is the oldest) and return its table name.
   * 
   * @return the name of the oldest table in the data table registry
   * @throws SQLException
   */
  String getOldestTable() throws SQLException {
    Date oldestDate = new Date();
    String oldestTable = null;
    String selectString = 
               "SELECT table_name, last_usage_date FROM " + DATA_TABLE_REGISTRY;
    Statement stmt = null;
    
    try {
      stmt = dbConnection.createStatement();
      ResultSet rs = stmt.executeQuery(selectString);
      
      while (rs.next()) {
        String tableName = rs.getString("table_name");
        Date lastUsageDate = rs.getDate("last_usage_date");
        
        if (lastUsageDate.before(oldestDate)) {
          oldestDate = lastUsageDate;
          oldestTable = tableName;
        }
      }    
    }
    catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
    finally {
      if (stmt != null) stmt.close();
    }
    
    return oldestTable;
  }


  /**
   * Gets a list of all the table names currently in the database.
   * 
   * @return  a String array of all tables names currently in the database
   */
  public String[] getTableList() throws SQLException {
    String selectString = "SELECT table_name FROM " + DATA_TABLE_REGISTRY;
    Statement stmt = null;
    Vector vector = new Vector();
    
    try {
      stmt = dbConnection.createStatement();             
      ResultSet rs = stmt.executeQuery(selectString);
      
      while (rs.next()) {
        String tableName = rs.getString("table_name");
        vector.add(tableName);
      }
    }
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
    finally {
      if (stmt != null) stmt.close();
    }
    
    String[] tableList = new String[vector.size()];
    for (int i = 0; i < vector.size(); i++) {
      tableList[i] = (String) vector.get(i);
    }

    System.err.println(tableList.toString());
    return tableList;
  }
  
  
  /**
   * Given an identifier string, return its corresponding table name. 
   * 
   * @param   identifier   the identifier string for the entity
   * @return  the corresponding table name, or null if there is no entry for
   *          this identifier
   */
  String identifierToTableName(String identifier) 
          throws SQLException {
    String tableName = null;
    String selectString = 
      "SELECT table_name" +
      " FROM " + DATA_TABLE_REGISTRY +
      " WHERE entity_identifier='" + identifier + "'";
    Statement stmt = null;
    
    try {
      stmt = dbConnection.createStatement();             
      ResultSet rs = stmt.executeQuery(selectString);
      
      while (rs.next()) {
        tableName = rs.getString("table_name");    
      }
    }
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
    finally {
      if (stmt != null) stmt.close();
    }
    
    return tableName;
  }
  

  /**
   * Checks the data table registry to determine whether a given table name is
   * already in use. 
   * 
   * @param tableName        the table name to be checked 
   * @return                 true if the table name is in use, else false
   * @throws SQLException
   */
  boolean isDBTableNameInUse(String tableName) throws SQLException {
    boolean inUse = false;
    String[] tableNames = getTableList();
    
    for (int i = 0; i < tableNames.length; i++) {
      if (tableNames[i].equalsIgnoreCase(tableName)) {
        inUse = true;
        break;
      }
    }
    
    return inUse;
  }
  

  /**
   * Boolean to determine whether a given table name is currently in the
   * database.
   * 
   * @param tableName   the name of the table that is being checked
   * @return  true if the table is currently in the database, else false
   */
  public boolean isTableInDB(String tableName) throws SQLException {
    String catalog = null;          // A catalog name (may be null)
    boolean isPresent = false;  
    ResultSet rs;
    String schemaPattern = null;    // A schema name pattern (may be null)
    String tableNamePattern = "%";  // Matches all table names in the db
    String[] types = {"TABLE"};     // A list of table types to include
    
    rs = databaseMetaData.getTables(catalog, schemaPattern, 
                                    tableNamePattern, types);

    while (rs.next()) {
      String TABLE_NAME = rs.getString("TABLE_NAME");
 
      if (TABLE_NAME.equalsIgnoreCase(tableName)) {
        isPresent = true;
      }
    }
    
    return isPresent;
	}
  

  /**
   * Given a table name, return a mangled name. This is done by tagging on a
   * string pattern followed by an integer. If the table name always contains
   * the string pattern (i.e. it has already been mangled one or more times
   * already), mangle it further by incrementing the integer. Eventually, the
   * integer will be incremented to a value that has not been used before for
   * this name.
   * 
   * Examples:
   * 
   * mangleName("aTable")  -->  "aTable_XYZXY_1"
   * mangleName("aTable_XYZXY_1")  -->  "aTable_XYZXY_2"
   * mangleName("aTable_XYZXY_344")  -->  "aTable_XYZXY_345"
   * 
   * @param  tableName  the table name to be mangled
   * @return the mangled name
   */
  String mangleName(String tableName) {
    int index;
    int tailInt = 0;
    String mangledName;
    String patternMatch = "_XYZYX_";  // Used to flag this as a mangled name
    String rootName = tableName;
    
    index = tableName.indexOf(patternMatch);

    /*
     * Check whether the table name has been previously mangled one or more
     * times already. If it has (i.e. it contains the pattern)
     */
    if (index > -1) {
      String tail = tableName.substring(index + patternMatch.length());
      Integer tailInteger = Integer.valueOf(tail);
      tailInt = tailInteger.intValue();
      rootName = tableName.substring(0, index);
    }
    
    tailInt++;
    mangledName = rootName + patternMatch + tailInt;
    
    return mangledName;
  }
	

  /**
   * Sets the maximum database size to the given value (in Megabytes).
   * (Note: How do we persist this value -- in a table?)
   * 
   * @param size   the maximum size (in Megabytes) of the database
   */
  public void setDBSize(int size) {
    int minSize = 1;      // Don't allow dbSize to be set below a minimum value

    if (size < minSize) {
      dbSize = minSize;
    }
    else {
      dbSize = size;
    }
  }
  

  /**
   * Sets the last usage date for a given table in the database. This method
   * should be called by the DatabaseHandler whenever the table is accessed
   * in any way.
   * 
   * @param tableName  the name of the table whose usage date we are setting.
   * @param date       the date to set
   * @return  true if the last usage date is successfully set; else false
   */
  public boolean setLastUsageDate(String tableName, Date date)
        throws SQLException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = simpleDateFormat.format(date);
    int rowCount = 0;
    Statement stmt = null;
		boolean success = false;

    String updateString = 
      "UPDATE " + DATA_TABLE_REGISTRY +
      " SET last_usage_date='" + dateString + "'" +
      " WHERE table_name='" + tableName + "'";
    
    // Set the last usage date
    try {
      stmt = dbConnection.createStatement();
      rowCount = stmt.executeUpdate(updateString);
      success = (rowCount == 1);
    } 
    catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw (e);
    }
    finally {
      if (stmt != null) stmt.close();
    }
    
    return success;
	}


  /**
   * Sets the expiration policy for a given table in the database.
   * We'll start by keeping it very simple. Non-zero means that the table is
   * allowed to expire (i.e. can be removed from the database when it gets old),
   * while zero means that it should never expired (always stays in the
   * database).
   * 
   * @param tableName    the name of the table whose expiration policy is 
   *                     being set
   * @param priority     the expiration policy: non-zero means that table
   *                     can be deleted; zero means that it should never be
   *                     expired
   * @return  true if the expiration policy was successfully set, else false
   */
	public boolean setTableExpirationPolicy(String tableName, int priority) 
        throws SQLException {
    int rowCount = 0;
    Statement stmt = null;
    boolean success = false;

    String updateString = 
      "UPDATE " + DATA_TABLE_REGISTRY +
      " SET priority=" + priority +
      " WHERE table_name='" + tableName + "'";
    
    // Set the last usage date
    try {
      stmt = dbConnection.createStatement();
      rowCount = stmt.executeUpdate(updateString);
      success = (rowCount == 1);
    } 
    catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw (e);
    }
    finally {
      if (stmt != null) stmt.close();
    }
    
    return success;
	}
	
}
