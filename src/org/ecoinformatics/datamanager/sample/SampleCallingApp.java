package org.ecoinformatics.datamanager.sample;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.database.Condition;
import org.ecoinformatics.datamanager.database.ConnectionNotAvailableException;
import org.ecoinformatics.datamanager.database.DatabaseConnectionPoolInterface;
import org.ecoinformatics.datamanager.database.Query;
import org.ecoinformatics.datamanager.database.SelectionItem;
import org.ecoinformatics.datamanager.database.TableItem;
import org.ecoinformatics.datamanager.database.WhereClause;
import org.ecoinformatics.datamanager.download.DataStorageInterface;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterface;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.quality.QualityCheck;

  /**
   * This class is a sample calling application to demonstrate use of the
   * Data Manager Library API. 
   * 
   * This class implements DatabaseConnectionPoolInterface to provide
   * database connections to the Data Manager code. This class is also
   * associated with the SampleDataStorage class, which illustrates an
   * implementation of the DataStorageInterface, and the EcogridEndPoint
   * class, which illustrates an implementation of the
   * EcogridEndPointInterface.
   * 
   * Database information in this class will be read from a properties file,
   * determined by the values of the CONFIG_DIR and CONFIG_NAME constants.
   * 
   * @author dcosta
   *
   */
public class SampleCallingApp implements DatabaseConnectionPoolInterface {

  /*
   * Class fields
   */
  
  /*
   * Configuration directory and file name for the properties file. You can
   * edit the properties in this file to change the database connection
   * information as well as the sample metadata document used by this 
   * application.
   */
  private static final String CONFIG_NAME = "datamanager";

  /*
   * These fields will be assigned values when the properties file is loaded. 
   */
  private static ResourceBundle options = null;
  private static String dbDriver = null;
  private static String dbURL = null;
  private static String dbUser = null;
  private static String dbPassword = null;
  private static String databaseAdapterName = null;
  private static String testDocument = null;
  private static String testServer = null;
  private static String entityName = null;
  private static String packageID = null;
  private static Boolean qualityAudit = new Boolean("false");  // default value
    
  
  /*
   * Instance fields
   */

  // An instance of the DataManager class. This object provides the 
  // calling application access to all the public methods exposed by the 
  // Data Manager Library API.
  private DataManager dataManager;

  // InputStream object used to read metadata from the remote location
  private InputStream metadataInputStream = null;

  // A dataPackage object is returned after parsing the metadata
  // (see method testParseMetadata() ). We save it in this field so that it
  // can be subsequently used by other methods.
  private DataPackage dataPackage;
  
  // This string holds the URL to the sample metadata document as found on
  // a Metacat server. It is determined by the values in the properties file.
  private String documentURL = null;
 
  // A DataStorageInterface object that this class is associated with.
  // The calling application must use an object of this type to interact
  // with the Data Manager's download manager (see testDownloadData()).
  private DataStorageInterface dsi = null;

  // The calling application needs to be associated with an
  // EcogridEndPointInterface object for loading data to the database.
  // (See testLoadDataToDB()).
  private EcogridEndPointInterface eepi = null;
  
  
  
  /*
   * Constructors
   */
        
  /**
   * Constructor. Load database parameters and sample document name from 
   * properties file, get an instance of the DataManager class, and construct 
   * a DataStorageInterface object and an EcogridEndPointInterface object.
   */
  public SampleCallingApp() {
    loadOptions();
    dataManager = DataManager.getInstance(this, databaseAdapterName);
    documentURL = testServer + "?action=read&qformat=xml&docid=" + testDocument;
    dsi = new SampleDataStorage();
    eepi = new EcogridEndPoint();
  }

  
  /*
   * Class methods
   */
 
  /**
   * Main method. Creates an instance of the calling application object,
   * initializes it, and runs a number of calls to the DataManager that
   * test the various use cases. Each use case builds on the previous
   * use cases.
   */
  public static void main(String[] args)
    throws MalformedURLException, IOException, Exception {
    boolean success = true;
    SampleCallingApp dmm = new SampleCallingApp();
    dmm.setUp();
    success = success && dmm.testParseMetadata();   // Use Case #1
    success = success && dmm.testDownloadData();    // Use Case #2
    success = success && dmm.testLoadDataToDB();    // Use Case #3
    success = success && dmm.testSelectData();      // Use Case #4
    success = success && dmm.testEnumerationMethods();  // Miscellaneous other
    System.err.println("Finished all tests, success = " + success + "\n");
    dmm.tearDown();  // clean-up tables
    System.exit(0);
  }


  /**
   * Loads Data Manager options from a configuration file.
   */
  private static void loadOptions() {

    try {
      // Load database connection options
      options = ResourceBundle.getBundle(CONFIG_NAME);
      dbDriver = options.getString("dbDriver");
      dbURL = options.getString("dbURL");
      dbUser = options.getString("dbUser");
      dbPassword = options.getString("dbPassword");
      databaseAdapterName = options.getString("dbAdapter");
      
      // Load sample document and Metacat server options
      testDocument = options.getString("testDocument");
      testServer = options.getString("testServer");
      entityName = options.getString("entityName");
      packageID = options.getString("packageID");
      
      /* Check the value of the qualityAudit property and call 
       * QualityCheck.setQualityAudit() accordingly. This controls whether
       * the Data Manager library will execute with quality auditing turned
       * on or off.
       */
      String qualityAuditStr = options.getString("qualityAudit");
      if (qualityAuditStr != null &&
           (qualityAuditStr.equalsIgnoreCase("true") ||
            qualityAuditStr.equalsIgnoreCase("false")
           )
         ) { 
        QualityCheck.setQualityAudit(qualityAuditStr.toLowerCase());
      }
    } 
    catch (Exception e) {
      System.err.println("Error in loading options: " + e.getMessage());
    }
  }


  /*
   * Instance methods
   */
  
  /**
   * Get database adpater name. Implementation of this method is required by
   * the DatabaseConnectionPoolInterface.
   * 
   * @return database adapter name, for example, "PostgresAdapter"
   */
  public String getDBAdapterName() {
    return databaseAdapterName;
  }


  /**
   * Gets a database connection from the pool. Implementation of this method is 
   * required by the DatabaseConnectionPoolInterface. Note that in this
   * example, there is no actual pool of connections. A full-fledged
   * application should manage a pool of connections that can be re-used.
   * 
   * @return checked out connection
   * @throws SQLException
   */
  public Connection getConnection()
          throws SQLException, ConnectionNotAvailableException {
    Connection connection = null;

    try {
      Class.forName(dbDriver);
    } 
    catch (java.lang.ClassNotFoundException e) {
      System.err.print("ClassNotFoundException: ");
      System.err.println(e.getMessage());
      throw (new SQLException(e.getMessage()));
    }

    try {
      connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
    } 
    catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw (e);
    }

    return connection;
  }


  /**
   * Returns checked out dabase connection to the pool.
   * Implementation of this method is required by the 
   * DatabaseConnectionPoolInterface.
   * Note that in this example, there is no actual pool of connections
   * to return the connection to. A full-fledged
   * application should manage a pool of connections that can be re-used.
   * 
   * @param  conn, Connection that is being returned
   * @return boolean indicator if the connection was returned successfully
   */
  public boolean returnConnection(Connection conn) {
    boolean success = false;

    try {
      conn.close();
      success = true;
    } 
    catch (Exception e) {
      success = false;
    }

    return success;
  }
  

  /**
   * Initialize the calling application by opening an input stream to the
   * metadata document.
   * 
   * @throws MalformedURLException
   * @throws IOException
   * @throws Exception
   */
  private void setUp() throws MalformedURLException, IOException, Exception {
    try {
      URL url = new URL(documentURL);
      metadataInputStream = url.openStream();
    } 
    catch (MalformedURLException e) {
      e.printStackTrace();
      throw (e);
    } 
    catch (IOException e) {
      e.printStackTrace();
      throw (e);
    } 
    catch (Exception e) {
      e.printStackTrace();
      throw (e);
    }
  }


  /**
   * Clean-up after the calling application has completed its tests
   * of the various use cases. Drop tables from the database.
   * 
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws Exception
   */
  private void tearDown() throws ClassNotFoundException, SQLException,
      Exception {
    if (dataPackage != null) {
      dataManager.dropTables(dataPackage); // Clean-up test tables
      System.err.println("Finished dropping tables.\n");
    }
  }
  

  /**
   * Tests Use Case #2, downloading data from the remote location to the
   * local store.
   * 
   * @return   success, true if the download was successful, else false
   */
  public boolean testDownloadData() {
    boolean success = false;
    
    // We need to provide the Data Manager with a list of all 
    // DataStorageInterface objects that want to download the data
    // to their local data store (in this case there is only one).
    DataStorageInterface[] dataStorageList = new DataStorageInterface[1];
    
    dataStorageList[0] = dsi;

    // Download the data
    if (dataPackage != null) {
      success = dataManager.downloadData(dataPackage, eepi, dataStorageList);
    }
 
    System.err.println("Finished testDownloadData(), success = " + success + 
                       "\n");
    return success;
  }


  /**
   * Tests methods in the API to enumerate an entity's database table name,
   * or an attribute's database field names. There is no Use Case number
   * associated with these methods at present, so we can think of these
   * as "miscellaneous other" use cases.
   * 
   * @return  success, true if successful, else false
   * 
   * @throws MalformedURLException
   * @throws IOException
   * @throws Exception
   */
  public boolean testEnumerationMethods()
          throws MalformedURLException, IOException, Exception {
    boolean success = false;

    // Get the database table name for a given packageID and entityName
    String tableName = DataManager.getDBTableName(packageID, entityName);
    System.err.println("tableName: " + tableName);

    // Get a list of field names corresponding to the attrbutes for a
    // given packageID and entityName
    String[] fieldNames = DataManager.getDBFieldNames(packageID, entityName);

    // Print out the field names that were returned
    if (fieldNames != null) {
      for (int i = 0; i < fieldNames.length; i++) {
        System.err.println("  fieldNames[" + i + "]: " + fieldNames[i]);
      }
    }

    // We succeeded if we found both a table name and a list of field names
    success = ((tableName != null) && (fieldNames != null));
    System.err.println("Finished testEnumerationMethods(), success = " + 
                       success + "\n");

    return success;
  }

 
  /**
   * Tests Use Case #3, loading data into the database.
   * 
   * @return success, true if successful, else false
   * 
   * @throws MalformedURLException
   * @throws IOException
   * @throws Exception
   */
  public boolean testLoadDataToDB() 
         throws MalformedURLException, IOException, Exception {
    boolean success = false;

    // Load the data for this data package. We also need to provide an
    // EcogridEndPointInterface object to the Data Manager.
    if (dataPackage != null) {
      success = dataManager.loadDataToDB(dataPackage, eepi);
    }
 
    System.err.println("Finished testLoadDataToDB(), success = " + success
                       + "\n");
    return success;
  }
  
  
  /**
   * Tests Use Case #1, parsing a metadata document.
   * 
   * @return  success, true if successful, else false
   */
  public boolean testParseMetadata() {
    boolean success = false;
    
    if (metadataInputStream != null) {
      try {
        // Parse the metadata that is being read from the input stream
        dataPackage = dataManager.parseMetadata(metadataInputStream);
        // If a DataPackage was returned, we succeeded in parsing
        success = (dataPackage != null);
      }
      catch (Exception e){
        e.printStackTrace();
      }
    }
    
    System.err.println("Finished testParseMetadata(), success = " + success
                       + "\n");
    return success;
  }

  
  /**
   * Tests Use Case #5, selecting data from a table.
   * Tests the creation and use of a Query object for querying a data table. 
   * Runs a query with a conditional WHERE clause and prints the result set.
   * 
   * Note that if the properties file is edited to use a different test
   * document than the default document, this method will need to be re-written
   * with a different query. This method was specifically written to query
   * the default test document.
   */
  public boolean testSelectData() throws Exception {
    AttributeList attributeList;
    Attribute attribute;                   // Used in where clause condition
    Entity entity = null;
    String operator = ">";                 // Used in where clause condition
    boolean success = false;
    int intValue = 1;                      // Used in where clause condition
    Integer value = new Integer(intValue); // Used in where clause condition
    ResultSet resultSet = null;
  
    // Assumes that the DataPackage object has already been created in the
    // previous tests and saved in the 'dataPackage' instance field.
    if (dataPackage != null) {
      Entity[] entityList = dataPackage.getEntityList();
      entity = entityList[0];
      attributeList = entity.getAttributeList();
      Attribute[] attributes = attributeList.getAttributes();
      attribute = attributes[6];

    /*
     * Now build a test query, execute it, and compare the result set to
     * known values.
     */
      DataPackage[] dataPackages = new DataPackage[1];
      dataPackages[0] = dataPackage;
      Query query = new Query();
      /* SELECT clause */
      SelectionItem selectionItem = new SelectionItem(entity, attribute);
      query.addSelectionItem(selectionItem);
      /* FROM clause */
      TableItem tableItem = new TableItem(entity);
      query.addTableItem(tableItem);
      /* WHERE clause with relational operator condition */
      Condition condition = new Condition(entity, attribute, operator, value);
      WhereClause whereClause = new WhereClause(condition);
      query.setWhereClause(whereClause);
      
      /* Print out the SQL string */
      System.err.println("Query SQL = '" + query.toSQLString() + "'");

      try {
        /* Run the query */
        resultSet = dataManager.selectData(query, dataPackages);
        
        /* Output the result set */
        if (resultSet != null) {
          success = true;
          int i = 1;

          System.err.println(
                         "Printing all records with 'count' value greater than " 
                         + intValue
                            );
          while (resultSet.next()) {;
            int count = resultSet.getInt(1);
            System.err.println("resultSet[" + i + "], count =  " + count);
            i++;
          }
        }
        else {
          throw new Exception("resultSet is null");
        }
      } 
      catch (Exception e) {
        System.err.println("Exception in DataManager.selectData()"
                           + e.getMessage()
                          );
        throw (e);
      }  
      finally {
        if (resultSet != null) resultSet.close();
      }
    } 
    
    System.err.println("Finished testSelectData(), success = " + success
                       + "\n");
    return success;
  }
  
}
