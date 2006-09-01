/**
 *    '$RCSfile: TableMonitor.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-09-01 17:19:58 $'
 *   '$Revision: 1.3 $'
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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * TableMonitor monitors all data tables in the database. It stores information
 * about each table in a data table registry:
 *  
 *   table name
 *   creation date
 *   last usage date
 *   expiration policy
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
	private String     dbAdapterName = null;   // the DatabaseAdapter name
  private final String DATA_TABLE_REGISTRY = "DATA_TABLE_REGISTRY";
  
  
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
	public TableMonitor(Connection dbConnection, String dbAdapterName)
        throws SQLException {
		this.dbConnection = dbConnection;
		this.dbAdapterName = dbAdapterName;
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
   * Adds a new table entry for a given table name.
   * 
   * 
   */
  public boolean addTableEntry(String tableName)
  {
    return false;
  }
  
  
  /**
   * Creates the DATA_TABLE_REGISTRY table. This is the table that the
   * TableMonitor uses to keep track of data table information such as
   * data table name, when the data table was created, when it was last
   * used, and its priority (expiration policy) setting.
   *
   */
  private void createDataTableRegistry() {
    String createString = 
      "create table " + DATA_TABLE_REGISTRY + " " +
      "(" +
      "  TABLE_NAME varchar(64), " +
      "  CREATION_DATE date, " +
      "  LAST_USED_DATE date, " +
      "  PRIORITY int" +
      ")";

    Statement stmt;

    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(createString);
      stmt.close();
    } 
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
    }
  }
  
 
  /**
   * Drops a table entry for a given table name.
   * 
   * @param   tableName   the name of the table to be dropped from the database
   * @return  true if successfully dropped, else false
   */
  public boolean dropTableEntry(String tableName) {
    boolean success = true;
    
    return success;
  }
  

  /**
   * Frees up table space by dropping one or more old tables.
   */
  public void freeTableSpace() {
    
  }


  /**
   * Gets the creation date of a given table in the database.
   * 
   * @param   tableName   the name of the table whose creation date is returned
   * @return  the creation date, a Date object
   */
  public Date getCreationDate(String tableName)
  {
    Date creationDate = null;
    
    return creationDate;
  }
  

  /**
   * Gets the last usage date for a given table name in the database.
   * 
   * @param  tableName  the name of the the table whose last usage date
   *                    is returned
   * @return  the last usage date, a Date object
   */
  public Date getLastUsageDate(String tableName)
  {
    Date lastUsageDate = null;
    
    return lastUsageDate;
  }
  

  /**
   * Gets a list of all the tables currently in the database.
   * 
   * @return  a String array of all tables names currently in the database
   */
  public String[] getTableList()
  {
    return null;
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
   * Sets the maximum database size to the given value (in Megabytes).
   * (Note: How do we persist this value -- in a table?)
   * 
   * @param size   the maximum size (in Megabytes) of the database
   */
  public void setDBSize(int size)
  {
    
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
        throws SQLException
	{
		boolean success = false;
    
    if (isTableInDB(tableName)) {
      // Set the last usage date
      success = true;
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
		boolean success = false;
    
    if (isTableInDB(tableName)) {
      // Set the expiration policy
      success = true;
    }
    
    return success;
	}
	
}
