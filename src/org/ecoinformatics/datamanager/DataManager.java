/**
 *    '$RCSfile: DataManager.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-10-11 22:29:32 $'
 *   '$Revision: 1.8 $'
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

package org.ecoinformatics.datamanager;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ucsb.nceas.utilities.Options;

import org.ecoinformatics.datamanager.database.DatabaseAdapter;
import org.ecoinformatics.datamanager.database.DatabaseHandler;
import org.ecoinformatics.datamanager.database.TableMonitor;
import org.ecoinformatics.datamanager.download.DownloadHandler;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.eml.Eml200Parser;


/**
 * 
 * The DataManager class is the controlling class for the library. It exposes
 * the high-level API to the calling application.
 * 
 * There are six use-cases that this library supports:
 * 
 * 1. Parse the metadata to get at its entities and attributes.
 * 2. Download a data table from a remote site, using the URL in the metadata.
 * 3. Load a data table into the database table cache.
 * 4. Query a data table with a SQL select statement.
 * 5. Set an upper limit on the size of the database table cache.
 * 6. Set an expiration policy on a table in the database table cache.
 *
 */
public class DataManager {
  
  /*
   * Initializers
   */
  static {
    loadOptions();
  }

  
  /*
   * Class fields
   */

  /* Configuration directory and file name for the properties file */
  private static final String CONFIG_DIR = "lib/datamanager";
  private static final String CONFIG_NAME = "datamanager.properties";
  
  /* Holds the singleton object for this class */
  private static DataManager dataManager = new DataManager();
  
  private static String dbDriver; // e.g. "org.postgresql.Driver"
  private static String dbURL; // e.g. "jdbc:postgresql://localhost/datamanager"
  private static String dbUser;     // database username
  private static String dbPassword; // database password
  private static Options options = null;
 
  
  /*
   * Instance fields
   */
  
  /* The database adapter name. 
   * Examples are: "HSQLAdapter", "PostgresAdapter", and "OracleAdapter".
   * For now, hard-code "PostgresAdapter" since it is our first goal.
   */
  private String     databaseAdapterName = DatabaseAdapter.POSTGRES_ADAPTER;
  private Connection dbConnection = null;
  

  /*
   * Constructors
   */
  
  
  /*
   * Class methods
   */
 
  /**
   * Accessor method.
   * 
   * @return  dbDriver class field
   */
  public static String getDbDriver() {
    return dbDriver;
  }
  
  
  /**
   * Accessor method.
   * 
   * @return  dbURL class field
   */
  public static String getDbURL() {
    return dbURL;
  }
  
  
  /**
   * Accessor method.
   * 
   * @return  dbUser class field
   */
  public static String getDbUser() {
    return dbUser;
  }
  
  
  /**
   * Accessor method.
   * 
   * @return  dbPassword class field
   */
  public static String getDbPassword() {
    return dbPassword;
  }
  
  
  /**
   * Gets the singleton instance of this class.
   * 
   * @return  the single instance of the DataManager class.
   */
  static public DataManager getInstance() {
    return dataManager;
  }
  
  
  /**
   * Loads Data Manager options from a configuration file.
   */
  public static void loadOptions() {
    String configDir = CONFIG_DIR;    
    File propertyFile = new File(configDir, CONFIG_NAME);

    try {
      options = Options.initialize(propertyFile);
      dbDriver = options.getOption("dbDriver");
      dbURL = options.getOption("dbURL");
      dbUser = options.getOption("dbUser");
      dbPassword = options.getOption("dbPassword");
    } 
    catch (IOException e) {
      System.out.println("Error in loading options: " + e.getMessage());
    }
  }
  
  
  /*
   * Instance methods
   */
  
  
  /**
   * Create a database view from one or more entities in an entity list.
   * 
   * @param  ANSISQL    ANSI SQL string to create the view.
   * @param  entityList Array of entities whose table names and attribute
   *         names are used in creating the view.
   * @return a boolean value indicating the success of the create view 
   *         operation. true if successful, else false.
   */
  public boolean createDataView(String ANSISQL, Entity[] entityList) {
    boolean success = true;
    
    return success;
  }
 
  
  /**
   * Downloads all entities in a data package using the calling application's 
   * data storage interface. This allows the calling application to manage its 
   * data store in its own way. This version of the method downloads all 
   * entities in the entity list of the data package. This method implements
   * Use Case #2.
   * 
   * @param  dataPackage the data package containing a list of entities
   * @return a boolean value indicating the success of the download operation.
   *         true if successful, else false.
   */
  public boolean downloadData(DataPackage dataPackage) {
    boolean success = true;
    Entity[] entities = dataPackage.getEntityList();
    
    for (int i = 0; i < entities.length; i++) {
      success = success && downloadData(entities[i]);
    }
    
    return success;
  }

  
  /**
   * Downloads a single entity using the calling application's data storage
   * interface. This allows the calling application to manage its data store
   * in its own way. This method implements Use Case #2.
   * 
   * @param  the entity whose data is to be downloaded
   * @return a boolean value indicating the success of the download operation.
   *         true if successful, else false.
   */
  public boolean downloadData(Entity entity) {
    boolean success = false;
    DownloadHandler downloadHandler = null;
    
    //downloadHandler = entity.getDownloadHandler();
    if (downloadHandler != null) {
      //success = downloadHandler.download();
    }
    
    return success;
  }
 
  
  /**
   * Downloads data from an input stream using the calling application's data 
   * storage interface. This allows the calling application to manage its 
   * data store in its own way. The metadata input stream first needs to be
   * parsed and a data package created from it. Then, all entities in the data
   * package are downloaded. This method implements Use Case #2.
   * 
   * @param  metadataInputStream an input stream to the metadata. 
   * @return a boolean value indicating the success of the download operation.
   *         true if successful, else false.
   */
  public boolean downloadData(InputStream metadataInputStream) 
        throws Exception {
    boolean success = false;
    DataPackage dataPackage = parseMetadata(metadataInputStream);
    
    if (dataPackage != null) {
      success = downloadData(dataPackage);
    }
    
    return success;
  }
  

  /**
   * Gets the database connection object. If the dbConnection field hasn't
   * already been initialized, creates a new connection and initializes the
   * field.
   * 
   * @return
   */
  private Connection getConnection() 
        throws ClassNotFoundException, SQLException {
    if (dbConnection == null) {
      try {
        Class.forName(DataManager.dbDriver);
      } 
      catch(java.lang.ClassNotFoundException e) {
        System.err.print("ClassNotFoundException: "); 
        System.err.println(e.getMessage());
        throw(e);
      }

      try {
        dbConnection = DriverManager.getConnection(DataManager.dbURL, 
                                                   DataManager.dbUser, 
                                                   DataManager.dbPassword);
      } 
      catch(SQLException e) {
        System.err.println("SQLException: " + e.getMessage());
        throw(e);
      }
    }
    
    return dbConnection;
  }
  

  /**
   * Get the value of the databaseAdapterName field.
   * 
   * @return  the value of the databaseAdapterName field
   */
  public String getDatabaseAdapterName() {
    return databaseAdapterName;
  }


  /**
   * Loads all entities in a data package to the database table cache. This
   * method implements Use Case #3.
   * 
   * @param  dataPackage the data package containing a list of entities whose
   *         data is to be loaded into the database table cache.
   * @return a boolean value indicating the success of the load-data operation.
   *         true if successful, else false.
   */
  public boolean loadDataToDB(DataPackage dataPackage)
        throws ClassNotFoundException, SQLException, Exception {
    boolean success = true;
    Entity[] entities = dataPackage.getEntityList();
    
    for (int i = 0; i < entities.length; i++) {
      success = success && loadDataToDB(entities[i]);
    }
    
    return success;
  }
  
  
  /**
   * Loads data from a single entity into the database table cache.
   * This method implements Use Case #3.
   * 
   * @param  entity  the entity whose data is to be loaded into the database 
   *                 table cache.
   * @return a boolean value indicating the success of the load-data operation.
   *         true if successful, else false.
   */
  public boolean loadDataToDB(Entity entity) 
        throws ClassNotFoundException, SQLException, Exception {
    Connection conn = getConnection();
    DatabaseHandler databaseHandler = new DatabaseHandler(conn, 
                                                          databaseAdapterName);
    boolean success;

    // First, generate a table for the entity
    success = databaseHandler.generateTable(entity);
    
    // If we have a table, then load the data for the entity.
    if (success) {
      success = databaseHandler.loadData(entity);
    }
    
    return success;
  }
  
  
  /**
   * Loads all entities in a data package to the database table cache. This
   * version of the method is passed a metadata input stream that needs
   * to be parsed. Then, all entities in the data package are loaded to the
   * database table cache. This method implements Use Case #3.
   * 
   * @param  metadataInputStream the metadata input stream to be parsed into
   *         a DataPackage object.
   * @return a boolean value indicating the success of the load-data operation.
   *         true if successful, else false.
   */
  public boolean loadDataToDB(InputStream metadataInputStream) 
        throws Exception {
    boolean success = false;
    
    DataPackage dataPackage = parseMetadata(metadataInputStream);
    
    if (dataPackage != null) {
      success = loadDataToDB(dataPackage);
    }
    
    return success;
  }
  
  
  /**
   * Parses metadata using the appropriate parser object. The return value is
   * a DataPackage object containing the parsed metadata. This method
   * implements Use Case #1.
   * 
   * @param metadataInputStream  an input stream to the metadata to be parsed.
   * @return a DataPackage object containing the parsed metadata
   */
  public DataPackage parseMetadata(InputStream metadataInputStream) 
                                  throws Exception {
    DataPackage dataPackage = null;
    Eml200Parser eml200Parser = new Eml200Parser();
    
    eml200Parser.parse(metadataInputStream);
    dataPackage = eml200Parser.getDataPackage();
    
    return dataPackage;
  }
  
  
  /**
   * Runs a database query on one or more data packages. This method
   * implements Use Case #4.
   * 
   * @param ANSISQL  A string holding the ANSI SQL selection syntax.
   * @param packages The data packages holding the entities to be queried. 
   *                 Metadata about the data types of the attributes being
   *                 queried is contained in these data packages.
   * @return A ResultSet object holding the query results.
   */
  public ResultSet selectData(String ANSISQL, DataPackage[] packages) 
        throws ClassNotFoundException, SQLException, Exception {
    Connection conn = getConnection();
    DatabaseHandler databaseHandler;
    ResultSet resultSet = null;
    
    databaseHandler = new DatabaseHandler(conn, databaseAdapterName);
    resultSet = databaseHandler.selectData(ANSISQL, packages);
    
    return resultSet;
  }
  
 
  /**
  * Runs a database query on one or more metadata input streams. Each of the
  * metadata input streams needs to first be parsed, creating a list of data 
  * packages. The data packages contain entities, and the entities hold metadata
  * about the data types of the attributes being queried. This method 
  * implements Use Case #4.
  * 
  * @param ANSISQL  A string holding the ANSI SQL selection syntax.
  * @param packages An array of input streams that need to be parsed into a 
  *                 list of data packages. The data packages hold the lists of
  *                 entities to be queried. Metadata about the data types of the
  *                 attributes in the select statement is contained in these 
  *                 data packages.
  * @return A ResultSet object holding the query results.
  */
  public ResultSet selectData(String ANSISQL, InputStream[] emlInputStreams) 
        throws Exception {
    DataPackage[] packages = new DataPackage[emlInputStreams.length];
    ResultSet resultSet = null;
    
    for (int i = 0; i < emlInputStreams.length; i++) {
      DataPackage dataPackage = parseMetadata(emlInputStreams[i]);
      packages[i] = dataPackage;
    }
    
    resultSet = selectData(ANSISQL, packages);
    
    return resultSet;
  }
  

  /**
   * Runs a database query on a view. The view must already exist in the
   * database (see createDataView() method).
   * 
   * @param  ANSISQL  A string holding the ANSI SQL selection syntax.
   * @return A ResultSet object holding the query results.
   */
  public ResultSet selectDataFromView(String ANSISQL) {
    ResultSet resultSet = null;
    
    return resultSet;
  }
  
  
  /**
   * Set the String value of the databaseAdapterName field.
   * 
   * This method should probably throw an exception if the value does not
   * match any members of the recognized set of database adapter names.
   * 
   * @param databaseAdapterName
   */
  public void setDatabaseAdapterName(String databaseAdapterName) {
    this.databaseAdapterName = databaseAdapterName;
  }

  
  /**
   * Sets an upper limit on the size of the database table cache. If the limit
   * is about to be exceeded, the TableMonitor will attempt to free up space
   * by deleting old tables from the table cache. This method implements
   * Use Case #5.
   * 
   * @param size The upper limit, in MB, on the size of the database table
   *        cache.
   */
  public void setDatabaseSize(int size) throws SQLException {
    TableMonitor tableMonitor = 
                            new TableMonitor(dbConnection, databaseAdapterName);
    
    tableMonitor.setDBSize(size);
  }
  
  
  /**
   * Sets the expiration policy on a table in the database table cache. The
   * policy is an enumerated integer value indicating whether this table can
   * be expired from the cache. (The precise meaning of these values is yet to
   * be determined.) This method implements Use Case #6.
   * 
   * @param tableName the name of the table whose expiration policy is being
   *                  set
   * @param policy    an enumerated integer value indicating whether the table
   *                  should be expired from the datbase table cache. (The
   *                  precise meaning of this value is yet to be determined.)
   */
  public void setTableExpirationPolicy(String tableName, int policy) 
        throws SQLException {
    TableMonitor tableMonitor = 
                            new TableMonitor(dbConnection, databaseAdapterName);
    
    tableMonitor.setTableExpirationPolicy(tableName, policy);
  }

}
