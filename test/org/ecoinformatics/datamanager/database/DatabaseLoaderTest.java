package org.ecoinformatics.datamanager.database;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.download.DataStorageInterface;
import org.ecoinformatics.datamanager.download.DownloadHandler;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterface;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterfaceTest;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test class for DatabaseLoader which will store remote source into
 * database
 * 
 * @author Jing Tao
 * 
 */
public class DatabaseLoaderTest extends TestCase {

  /*
   * Class fields
   */

  /*
   * Configuration directory and file name for the properties file. You can edit
   * the properties in this file to change the database connection information
   * as well as the sample metadata document used by this application.
   */
  private static final String CONFIG_NAME = "datamanager";
  private static DataPackage dataPackage = null;
  private static String documentURL = null;
  private static ResourceBundle options = null;

  // A DataStorageInterface object that this class is associated with.
  // The calling application must use an object of this type to interact
  // with the Data Manager's download manager (see testDownloadData()).
  private static DataStorageInterface dsi = null;

  // The calling application needs to be associated with an
  // EcogridEndPointInterface object for loading data to the database.
  // (See testLoadDataToDB()).
  private static EcogridEndPointInterface eepi = null;

  /*
   * Instance fields
   */
  private DatabaseConnectionPoolInterfaceTest connectionPool = null;
  private DataManager dataManager = null;
  private String dbAdaptor = null;
  private EcogridEndPointInterfaceTest endPointInfo = new EcogridEndPointInterfaceTest();
  private Entity entity = null;


  /*
   * Constructors
   */

  /**
   * Constructor
   * 
   * @param name
   *          The name of the test suite
   */
  public DatabaseLoaderTest(String name) throws Exception {
    super(name);
  }


  /*
   * Class methods
   */

  /**
   * Create a suite of tests to be run together
   */
  public static Test suite() {
    TestSuite suite = new TestSuite();

    try {
      suite.addTest(new DatabaseLoaderTest("testDoesDataExist"));
    }
    catch (Exception e) {
      System.err.println("The error in test is: " + e.getMessage());
    }
    return suite;
  }


  /*
   * Instance methods
   */

  /**
   * Establish a testing framework by initializing appropriate objects.
   */
  protected void setUp() throws Exception {
    super.setUp();

    loadOptions();
    connectionPool = new DatabaseConnectionPoolInterfaceTest();
    dbAdaptor = connectionPool.getDBAdapterName();
    dataManager = DataManager.getInstance(connectionPool, dbAdaptor);
    InputStream metadataInputStream;
    URL url;

    /*
     * Initialize the entity field by parsing the test data package and using
     * the first entity it contains.
     */
    try {
      url = new URL(documentURL);
      metadataInputStream = url.openStream();
      dataPackage = dataManager.parseMetadata(metadataInputStream);
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

    assertNotNull("Data package is null", dataPackage);

    if (dataPackage != null) {
      Entity[] entities = dataPackage.getEntityList();
      entity = entities[0];
    }

    // We need to provide the Data Manager with a list of all
    // DataStorageInterface objects that want to download the data
    // to their local data store (in this case there is only one).
    DataStorageInterface[] dataStorageList = new DataStorageInterface[1];

    dataStorageList[0] = dsi;

    // Download the data
    if (dataPackage != null) {
      boolean success = dataManager.downloadData(dataPackage, eepi,
          dataStorageList);
      assertTrue(success);
    }

    // Load the data for this data package. We also need to provide an
    // EcogridEndPointInterface object to the Data Manager.
    if (dataPackage != null) {
      boolean success = dataManager.loadDataToDB(dataPackage, eepi);
      assertTrue(success);
    }
  }


  /**
   * Release any objects and closes database connections after tests are
   * complete.
   */
  protected void tearDown() throws Exception {
    super.tearDown();

    if (dataPackage != null) {
      dataManager.dropTables(dataPackage); // Clean-up test tables
      System.err.println("Finished dropping tables.\n");
    }

    connectionPool = null;
    dataManager = null;
    dbAdaptor = null;
    documentURL = null;
    endPointInfo = null;
    entity = null;
  }


  /**
   * Loads Data Manager options from a configuration file.
   */
  private static void loadOptions() {

    try {
      // Load database connection options
      options = ResourceBundle.getBundle(CONFIG_NAME);

      // Sample document
      documentURL = options.getString("documentURL");
    }
    catch (Exception e) {
      System.err.println("Error in loading options: " + e.getMessage());
    }
  }


  /**
   * Test startSerialize and finishSerialize methods. A downloadHanlder will be
   * used to test the methods.
   * 
   */
  public void testDoesDataExist() throws Exception {
    Connection connection, dbConnection;
    String identifier = entity.getEntityIdentifier();
    DownloadHandler handler = DownloadHandler.getInstance(identifier,
        endPointInfo);
    dbConnection = connectionPool.getConnection();
    DatabaseHandler databaseHandler = new DatabaseHandler(dbAdaptor);
    boolean success = databaseHandler.generateTable(entity);
    assertTrue("DatabaseHandler did not succeed in generating table", success);
    DatabaseLoader dataStorage = new DatabaseLoader(dbAdaptor, entity);
    boolean isPresent = dataStorage.doesDataExist(identifier);
    assertTrue("Table for identifier " + identifier + " is not empty, "
        + "but doesDataExist() returned false", isPresent);
    DatabaseLoader[] list = new DatabaseLoader[1];
    list[0] = dataStorage;
    handler.setDataStorageClassList(list);

    assertTrue(handler.isBusy() == false);
    assertTrue(handler.isSuccess() == false);
    Thread downloadThread = new Thread(handler);
    downloadThread.start();

    while (!handler.isCompleted()) {
    }

    /*
     * Check that the data table does exist, and that one of the values in the
     * table matches the expected value.
     */
    assertTrue(dataStorage.doesDataExist(identifier) == true);
    assertTrue(handler.isSuccess() == true);
    assertTrue(handler.isBusy() == false);
    String sql = "select sppm2 from NoneSuchBugCount where fld='Blue Field';";
    connectionPool.returnConnection(dbConnection);
    connection = connectionPool.getConnection();
    Statement statement = connection.createStatement();
    ResultSet result = statement.executeQuery(sql);
    boolean next = result.next();
    float sppm2 = 0;

    if (next) {
      sppm2 = result.getFloat(1);
    }

    result.close();
    statement.close();
    assertTrue("Unexpected value found in table", sppm2 == 4.5);

    // Clean up
    databaseHandler.dropTable(entity);
    isPresent = dataStorage.doesDataExist(identifier);
    assertFalse("Found table for identifier " + identifier + 
                " but it should NOT be in the database", isPresent);
    connectionPool.returnConnection(connection);
  }

}
