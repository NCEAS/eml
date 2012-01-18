/**
 *    '$RCSfile: DataManager.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2008-02-28 23:40:53 $'
 *   '$Revision: 1.34 $'
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


import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;



import org.ecoinformatics.datamanager.database.ConnectionNotAvailableException;
import org.ecoinformatics.datamanager.database.DatabaseAdapter;
import org.ecoinformatics.datamanager.database.DatabaseConnectionPoolInterface;
import org.ecoinformatics.datamanager.database.DatabaseHandler;
import org.ecoinformatics.datamanager.database.HSQLAdapter;
import org.ecoinformatics.datamanager.database.OracleAdapter;
import org.ecoinformatics.datamanager.database.PostgresAdapter;
import org.ecoinformatics.datamanager.database.Query;
import org.ecoinformatics.datamanager.database.TableMonitor;
import org.ecoinformatics.datamanager.database.Union;
import org.ecoinformatics.datamanager.download.DownloadHandler;
import org.ecoinformatics.datamanager.download.DataStorageInterface;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterface;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.generic.DataPackageParserInterface;
import org.ecoinformatics.datamanager.parser.generic.Eml200DataPackageParser;
import org.ecoinformatics.datamanager.quality.QualityCheck;
import org.ecoinformatics.datamanager.quality.QualityReport;
import org.ecoinformatics.datamanager.quality.QualityCheck.Status;


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
   * Class fields
   */

  /* Holds the singleton object for this class */
  private static DataManager dataManager = null;
  private static String databaseAdapterName = null;
  private static DatabaseConnectionPoolInterface connectionPool = null;
  
  // Constants
  private static final String  BLANKSTR = "";     
  private static final int MAXIMUM_NUMBER_TO_ACCESS_CONNECTIONPOOL = 10;
  private static final int SLEEP_TIME = 2000;
  
 
  /*
   * Constructors
   */
  
  /*
   * This is singleton class, so constructor is private
   */
  
  private DataManager(DatabaseConnectionPoolInterface connectionPool, 
                      String databaseAdapterName)
  {
    DataManager.connectionPool = connectionPool;
    DataManager.databaseAdapterName = databaseAdapterName;
  }
  
  
  /*
   * Class methods
   */

  /**
   * Gets the singleton instance of this class.
   * 
   * @return  the single instance of the DataManager class.
   */
  static public DataManager getInstance(
                                 DatabaseConnectionPoolInterface connectionPool, 
                                 String databaseAdapterName) {
	if (dataManager == null)
	{
		dataManager = new DataManager(connectionPool, databaseAdapterName);
	}
	else if (DataManager.databaseAdapterName != null && 
             !DataManager.databaseAdapterName.equals(databaseAdapterName)
            )
	{
		dataManager = new DataManager(connectionPool, databaseAdapterName);
	}
	
    return dataManager;
  }
  

  /*
   * Gets DBConnection from connection pool. If no connection available, it will
   * sleep and try again. If ceiling times reachs, null will return.
   * 
   */
  public static Connection getConnection() throws SQLException
  {
      Connection connection = null;
      int index = 0;
      if (connectionPool == null)
      {
    	  throw new SQLException("The Connection Pool is null");
      }
      while (index <MAXIMUM_NUMBER_TO_ACCESS_CONNECTIONPOOL)
      {
          try
          {
              connection = connectionPool.getConnection();
              break;
          }
          catch (ConnectionNotAvailableException cna)
          {
              try
              {
                 Thread.sleep(SLEEP_TIME);
              }
              catch(Exception e) 
              {
                 System.err.println("Error in DataManager.getConnection(): " +
                                    e.getMessage());
              }
          }
          catch (SQLException sql)
          {
            System.err.println("Error in DataManager.getConnection(): " +
                               sql.getMessage());
          }
          
          index++;
      }
      
      return connection;
  }
  
  
  /**
   * Returns checked out connection to connection pool.
   * 
   * @param  connection the Connection to be returned to the pool
   */
  public static void returnConnection(Connection connection)
  {
      connectionPool.returnConnection(connection);
  }
  

  /**
   * Get the value of the databaseAdapterName field.
   * 
   * @return  the value of the databaseAdapterName field
   */
  public static String getDatabaseAdapterName() {
    return databaseAdapterName;
  }
  
  
  /**
   * Gets the object of the database connection pool
   *  @return the object of dababase connection pool
   */
   public static DatabaseConnectionPoolInterface getDatabaseConnectionPool()
   {
       return connectionPool;
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
   *         operation. True will be returned if successful, else false
   *         will be returned.
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
   * @param  endPointInfo which provides ecogrid endpoint information
   * @param  dataStorageList the destination (data storage) of the downloading
   * @return a boolean value indicating the success of the download operation.
   *         true if successful, else false.
   */
  public boolean downloadData(DataPackage dataPackage, EcogridEndPointInterface endPointInfo,
                              DataStorageInterface[] dataStorageList) {
    boolean success = true;
    Entity[] entities = dataPackage.getEntityList();
    
    for (int i = 0; i < entities.length; i++) {
      Entity entity = entities[i];
      success = downloadData(entity, endPointInfo, dataStorageList) && success;
    }
    
    return success;
  }

  
  /**
   * Downloads a single entity using the calling application's data storage
   * interface. This allows the calling application to manage its data store
   * in its own way. This method implements Use Case #2.
   * 
   * @param  entity the entity whose data is to be downloaded
   * @param  endPointInfo which provides ecogrid endpoint information
   * @param  dataStorageList the destination (data storage) of the downloading
   * @return a boolean value indicating the success of the download operation.
   *         true if successful, else false.
   */
  public boolean downloadData(Entity entity, EcogridEndPointInterface endPointInfo,
                              DataStorageInterface[] dataStorageList) {
    DownloadHandler downloadHandler = entity.getDownloadHandler(endPointInfo);
    boolean success = false;
    
    if (downloadHandler != null) {
      

      try {
        
    	/*downloadHandler.setDataStorageCladdList(dataStorageList);
        Thread loadData = new Thread(downloadHandler);
        loadData.start();
        
        while (!downloadHandler.isCompleted()) {
        }
        
        success = downloadHandler.isSuccess();*/
    	success = downloadHandler.download(dataStorageList);
    	
      } 
      catch (Exception e) {
        System.err.println("Error downloading entity name '" + 
                           entity.getName() + "': " + e.getMessage());
        success = false;
      }
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
   * @param  endPointInfo which provides ecogrid endpoint information
   * @param  dataStorageList the destination (data storage) of the downloading
   * @return a boolean value indicating the success of the download operation.
   *         true if successful, else false.
   */
  public boolean downloadData(InputStream metadataInputStream, EcogridEndPointInterface endPointInfo,
                              DataStorageInterface[] dataStorageList) 
        throws Exception {
    boolean success = false;
    DataPackage dataPackage = parseMetadata(metadataInputStream);
    
    if (dataPackage != null) {
      success = downloadData(dataPackage, endPointInfo, dataStorageList);
    }
    
    return success;
  }
  
 
  /**
   * Drops all tables in a data package. This is simply a pass-through to the
   * DatabaseHandler class.
   * 
   * @param dataPackage  the DataPackage object whose tables are to be dropped
   * @return true if successful, else false
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws Exception
   */
  public boolean dropTables(DataPackage dataPackage)
          throws ClassNotFoundException, SQLException, Exception {
    boolean success;

    DatabaseHandler databaseHandler = new DatabaseHandler(databaseAdapterName);
	  success = databaseHandler.dropTables(dataPackage);
    
    return success;
  }
  
  
  /**
   * Drops all tables in a data package based on a packageId value. This is 
   * simply a pass-through to the DatabaseHandler class.
   * 
   * @param  packageId  the packageId associated with tables are to be dropped
   * @return true if successful, else false
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws Exception
   */
  public boolean dropTables(String packageId)
          throws ClassNotFoundException, SQLException, Exception {
    boolean success;

    DatabaseHandler databaseHandler = new DatabaseHandler(databaseAdapterName);
    success = databaseHandler.dropTables(packageId);
    
    return success;
  }
  
  
  /**
   * Creates each table described in the datapackage
   * @param dataPackage
   * @return boolean indicating success or failure of the operation
   * @throws SQLException
   * @throws Exception
   */
  public boolean createTables(DataPackage dataPackage)
	  throws SQLException, Exception {
	boolean success;
	
	DatabaseHandler databaseHandler = new DatabaseHandler(databaseAdapterName);
	success = databaseHandler.generateTables(dataPackage);
	
	return success;
	}
  

  /**
   * Gets the database field name for a given entity attribute. First, we
   * ask the Attribute object for its dbFieldName value. If it doesn't know,
   * we check to see if it persists in the database.
   * 
   * @param   entity  the Entity that holds this attribute
   * @param   attribute  the Attribute whose database field name we want to get
   * @return  dbFieldName the database field name for this attribute, or null
   *          if no database field name can be found
   */
  public static String getDBFieldName(Entity entity, Attribute attribute) 
          throws SQLException {
    String dbFieldName = null;

    // First, access the dbFieldName directly from the attribute object
    dbFieldName = attribute.getDBFieldName();

    /*
     * If the attribute doesn't contain a value, get it from the database
     * field names stored in the entity's database table.
     */
    if (dbFieldName == null || dbFieldName.trim().equals(BLANKSTR)) {
    
      if (entity != null && attribute != null) {
        Attribute[] attributeArray = entity.getAttributes();
        String packageID = entity.getPackageId();
        String entityName = entity.getName();
        String[] dbFieldNames = getDBFieldNames(packageID, entityName);

        if (attributeArray.length == dbFieldNames.length) {
          for (int i = 0; i < attributeArray.length; i++) {
            Attribute arrayAttribute = attributeArray[i];
            if (attribute.equals(arrayAttribute)) {
              dbFieldName = dbFieldNames[i];
            }
          }
        }
      }
    }
    
    return dbFieldName;
  }
  
  
  /**
   * Gets a list of database field names for the specified packageID and entity
   * name. This is a pass-through to the same method in the TableMonitor class.
   * 
   * @param packageID    the packageID for this entity
   * @param entityName   the entity name
   * @return  a String array holding the field names for this entity, or null
   *          if there was no match for this packageID and entity name in the
   *          database.
   * @throws SQLException
   */
  public static String[] getDBFieldNames(String packageID, String entityName)
          throws SQLException {
    String[] dbFieldNames = null;

    DatabaseAdapter dbAdapter = getDatabaseAdapterObject(databaseAdapterName);
    TableMonitor tableMonitor = new TableMonitor(dbAdapter);        
    dbFieldNames = tableMonitor.getDBFieldNames(packageID, entityName);

    return dbFieldNames;
  }


  /**
   * Gets the database table name for a specified packageID and entity name.
   * This is a pass-through to the same method in the TableMonitor class.
   * 
   * @param packageID    the packageID for this entity
   * @param entityName   the entity name
   * @return dbTableName the database table name for this entity, or null if
   *                     no match to the packageID and entity name is found
   * @throws SQLException
   */
  public static String getDBTableName(String packageID, String entityName)
          throws SQLException {
    String dbTableName = null;

    DatabaseAdapter dbAdapter = getDatabaseAdapterObject(databaseAdapterName);
    TableMonitor tableMonitor = new TableMonitor(dbAdapter);        
    dbTableName = tableMonitor.getDBTableName(packageID, entityName);
    
    return dbTableName;
  }
  

  /**
   * Gets the database table name for a specified Entity.
   * This is an alternative signature that uses an Entity object instead of
   * string parameters. First we ask the Entity to tell us its table name,
   * but if it doesn't know, we check to see whether the table name for this
   * entity is persistent in the database.
   * 
   * @param entity       the Entity object whose table name is being determined
   * @return dbTableName the database table name for this entity, or null if
   *                     no match to the packageID and entity name is found
   * @throws SQLException
   */
  public static String getDBTableName(Entity entity) throws SQLException {
    String dbTableName = null;

    // First, try to get the dbTableName directly from the Entity object
    if (entity != null) {
      dbTableName = entity.getDBTableName();
      
      /* If the entity doesn't know its dbTableName value, use the entity's
       * packageID and name fields to query the database for the entity's
       * table_name field value.
       */
      if (dbTableName == null || dbTableName.trim().equals(BLANKSTR)) {
        String packageID = entity.getPackageId();
        String entityName = entity.getName();
        dbTableName = DataManager.getDBTableName(packageID, entityName);
      }
    }
    
    return dbTableName;
  }
  

  /**
   * Loads all entities in a data package to the database table cache. This
   * method implements Use Case #3.
   * 
   * @param  dataPackage the data package containing a list of entities whose
   *         data is to be loaded into the database table cache.
   * @param  endPointInfo which provides ecogrid endpoint information
   * @return a boolean value indicating the success of the load-data operation.
   *         true if successful, else false.
   */
  public boolean loadDataToDB(DataPackage dataPackage, EcogridEndPointInterface endPointInfo)
        throws ClassNotFoundException, SQLException, Exception {
    boolean success = true;
    Entity[] entities = dataPackage.getEntityList();
    
    for (int i = 0; i < entities.length; i++) {
      success = loadDataToDB(entities[i],endPointInfo) && success;
    }
    
    return success;
  }
  
  
  /**
   * Loads data from a single entity into the database table cache.
   * This method implements Use Case #3.
   * 
   * @param  entity  the entity whose data is to be loaded into the database 
   *                 table cache.
   * @param  endPointInfo which provides ecogrid endpoint information
   * @return a boolean value indicating the success of the load-data operation.
   *         true if successful, else false.
   */
  public boolean loadDataToDB(Entity entity, EcogridEndPointInterface endPointInfo) 
          throws ClassNotFoundException, SQLException, Exception {
    boolean success = false;
    
    // Initialize the dataLoadQualityCheck
    String qualityCheckName = "Data load status";
    QualityCheck qualityCheckTemplate = QualityReport.getQualityCheckTemplate(qualityCheckName);
    QualityCheck dataLoadQualityCheck = new QualityCheck(qualityCheckName, qualityCheckTemplate);

    /*
     * otherEntity is allowed to optionally omit the attributeList element.
     * If this is an otherEntity and its attributeList is null, or it is
     * non-null but it contains an empty attribute array, return success.
     */
    if ((entity != null) && (entity.isOtherEntity())) {
      AttributeList attributeList = entity.getAttributeList();
      if (attributeList == null) {
        success = true;
      }
      else {
        Attribute[] attributes = attributeList.getAttributes();
        if (attributes == null) {
          success = true;
        }
      }
      
      if (success) {
        // Create an informational quality check stating that the data load
        // was not attempted for this otherEntity entity.
        if (QualityCheck.shouldRunQualityCheck(entity, dataLoadQualityCheck)) {
          dataLoadQualityCheck.setFound(
            "Data loading was not attempted for this 'otherEntity' " +
            "because no attribute list was found in the EML");
          dataLoadQualityCheck.setExplanation(
            "In EML, a data entity of type 'otherEntity' is not required " +
            "to document an attribute list");
          entity.addQualityCheck(dataLoadQualityCheck);
        }
      }
    }
    /*
     * Do not attempt to load data into a database table if the entity
     * does not have a distribution online and has either distribution
     * offline or inline.
     */
    else if ((entity != null) && 
             !entity.hasDistributionOnline() &&
             (entity.hasDistributionOffline() ||
              entity.hasDistributionInline()
             )
            ) {
      success = true;
      if (QualityCheck.shouldRunQualityCheck(entity, dataLoadQualityCheck)) {
        dataLoadQualityCheck.setFound(
          "Data loading was not attempted for this entity " +
          "because its distribution is 'inline' or 'offline'");
        dataLoadQualityCheck.setExplanation(
          "Unable to process data entities with distribution " +
          "set to 'inline' or 'offline'");
        entity.addQualityCheck(dataLoadQualityCheck);
      }
    }
    else {  
      try {
        DatabaseHandler databaseHandler = 
                                 new DatabaseHandler(databaseAdapterName);

        // First, generate a table for the entity
        success = databaseHandler.generateTable(entity);

        // If we have a table, then load the data for the entity.
        if (success) {
          success = databaseHandler.loadDataToDB(entity, endPointInfo);
    
          // If the data could not be loaded to the database, drop the table.
          if (!success) {
            databaseHandler.dropTable(entity);
          }
        }
      }
      finally {
      }
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
   * @param  endPointInfo which provides ecogrid endpoint information
   * @return a boolean value indicating the success of the load-data operation.
   *         true if successful, else false.
   */
  public boolean loadDataToDB(InputStream metadataInputStream, EcogridEndPointInterface endPointInfo) 
        throws Exception {
    boolean success = false;
    
    DataPackage dataPackage = parseMetadata(metadataInputStream);
    
    if (dataPackage != null) {
      success = loadDataToDB(dataPackage, endPointInfo);
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
    DataPackageParserInterface parser = new Eml200DataPackageParser();
    
    parser.parse(metadataInputStream);
    dataPackage = parser.getDataPackage();
    
    return dataPackage;
  }
  
  /**
   * Parses metadata using the passed parser parameter. The return value is
   * a DataPackage object containing the parsed metadata. This method
   * implements Use Case #1.
   * 
   * @param metadataInputStream  an input stream to the metadata to be parsed.
   * @param genericParser the appropriate parser implementation for the metadataInputStream
   * @return a DataPackage object containing the parsed metadata
   * 
   * @throws Exception
   */
  public DataPackage parseMetadata(InputStream metadataInputStream, DataPackageParserInterface genericParser)
			throws Exception {
	  
		DataPackage dataPackage = null;
		genericParser.parse(metadataInputStream);
		dataPackage = genericParser.getDataPackage();

		return dataPackage;
	}
  
  
  /**
   * Runs a database query on one or more data packages. This method
   * implements Use Case #4.
   * 
   * @param query    A Query java object hold query information.
   * @param packages The data packages holding the entities to be queried. 
   *                 Metadata about the data types of the attributes being
   *                 queried is contained in these data packages.
   * @return A ResultSet object holding the query results.
   */
  public ResultSet selectData(Query query, DataPackage[] packages) 
        throws ClassNotFoundException, SQLException, Exception {


    DatabaseHandler databaseHandler;
    ResultSet resultSet = null;
    
    try
    {
      databaseHandler = new DatabaseHandler(databaseAdapterName);
      String ANSISQL = query.toSQLString();
      resultSet = databaseHandler.selectData(ANSISQL, packages);
    }
    finally
    {}
    
    return resultSet;
  }
  
 
  /**
  * Runs a database query on one or more metadata input streams. Each of the
  * metadata input streams needs to first be parsed, creating a list of data 
  * packages. The data packages contain entities, and the entities hold metadata
  * about the data types of the attributes being queried. This method 
  * implements Use Case #4.
  * 
  * @param query    A Query java object hold query information.
  * @param emlInputStreams An array of input streams that need to be parsed 
  *                 into a list of data packages. The data packages hold the 
  *                 lists of entities to be queried. Metadata about the data 
  *                 types of the attributes in the select statement is 
  *                 contained in these data packages.
  * @return A ResultSet object holding the query results.
  */
  public ResultSet selectData(Query query, InputStream[] emlInputStreams) 
        throws Exception {
    DataPackage[] packages = new DataPackage[emlInputStreams.length];
    ResultSet resultSet = null;
    
    for (int i = 0; i < emlInputStreams.length; i++) {
      DataPackage dataPackage = parseMetadata(emlInputStreams[i]);
      packages[i] = dataPackage;
    }
    
    resultSet = selectData(query, packages);
    
    return resultSet;
  }
  
  public ResultSet selectData(Union union, DataPackage[] packages)
			throws ClassNotFoundException, SQLException, Exception {


		DatabaseHandler databaseHandler;
		ResultSet resultSet = null;

		try {
			databaseHandler = new DatabaseHandler(databaseAdapterName);
			String ANSISQL = union.toSQLString();
			resultSet = databaseHandler.selectData(ANSISQL, packages);
		} finally {}

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
    DataManager.databaseAdapterName = databaseAdapterName;
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
  public void setDatabaseSize(int size) throws SQLException, ClassNotFoundException {
	try
	{
		DatabaseAdapter dbAdapter = getDatabaseAdapterObject(databaseAdapterName);
	    TableMonitor tableMonitor = new TableMonitor(dbAdapter);	    
	    tableMonitor.setDBSize(size);
	}
	finally
	{}
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
        throws SQLException, ClassNotFoundException {
    
	try
	{
	   DatabaseAdapter dbAdapter = getDatabaseAdapterObject(databaseAdapterName);
       TableMonitor tableMonitor = new TableMonitor(dbAdapter);
    
       tableMonitor.setTableExpirationPolicy(tableName, policy);
	}
	finally
	{}
  }
  
  
  /** 
   * Constructs and returns a DatabaseAdapter object based on a given database 
   * adapter name.
   * 
   * @param dbAdapterName   Database adapter name, a string. It should match
   *                        one of the constants in the DatabaseAdapter class,
   *                        e.g. DatabaseAdapter.POSTGRES_ADAPTER. If no match
   *                        is made, returns null.
   */
  public static DatabaseAdapter getDatabaseAdapterObject(String dbAdapterName)
  {
	if (dbAdapterName == null)
	{
		return null;
	}
    if (dbAdapterName.equals(DatabaseAdapter.POSTGRES_ADAPTER)) {
      PostgresAdapter databaseAdapter = new PostgresAdapter();
      return databaseAdapter;
    } 
    else if (dbAdapterName.equals(DatabaseAdapter.HSQL_ADAPTER)) {
      HSQLAdapter databaseAdapter = new HSQLAdapter();
      return databaseAdapter;
    } 
    else if (dbAdapterName.equals(DatabaseAdapter.ORACLE_ADAPTER)) {
      OracleAdapter databaseAdapter = new OracleAdapter();
      return databaseAdapter;
    }
      
    return null;
  }

}
