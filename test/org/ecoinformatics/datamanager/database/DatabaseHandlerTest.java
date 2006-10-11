package org.ecoinformatics.datamanager.database;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class DatabaseHandlerTest extends TestCase {

  /*
   * Class fields
   */

  static final String dbDriver = DataManager.getDbDriver();
  static final String dbURL = DataManager.getDbURL();
  static final String dbUser = DataManager.getDbUser();
  static final String dbPassword = DataManager.getDbPassword();

  
  /*
   * Instance fields
   */
    
  private DatabaseHandler databaseHandler;  //An instance of object being tested
  private Connection dbConnection  = null;            // the database connection
  private String  dbAdapterName = DatabaseAdapter.POSTGRES_ADAPTER;
  private final String TEST_DOCUMENT = "tao.1.1";
  private final String TEST_SERVER ="http://knb.ecoinformatics.org/knb/metacat";
  
  
    
  /*
   * Constructors
   */

  /**
   * Because DataManagerTest is a subclass of TestCase, it must provide a
   * constructor with a String parameter.
   * 
   * @param name   the name of a test method to run
   */
  public DatabaseHandlerTest(String name) {
    super(name);
  }
    
    
  /*
   * Class methods
   */
  
  /**
   * Create a suite of tests to be run together.
   */
  public static Test suite() {
    TestSuite testSuite = new TestSuite();
    
    testSuite.addTest(new DatabaseHandlerTest("initialize"));
    //testSuite.addTest(new DatabaseHandlerTest("testDoesDataExist"));
    testSuite.addTest(new DatabaseHandlerTest("testGenerateTable"));
    testSuite.addTest(new DatabaseHandlerTest("testDropTable"));
    
    return testSuite;
  }
    
    
  /*
   * Instance methods
   */
    
  /**
   * Run an initial test that always passes to check that the test harness
   * is working.
   */
  public void initialize() {
    assertTrue(1 == 1);
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
        Class.forName(DatabaseHandlerTest.dbDriver);
      } 
      catch(java.lang.ClassNotFoundException e) {
        System.err.print("ClassNotFoundException: "); 
        System.err.println(e.getMessage());
        throw(e);
      }

      try {
        dbConnection = DriverManager.getConnection(DatabaseHandlerTest.dbURL, 
                                                DatabaseHandlerTest.dbUser, 
                                                DatabaseHandlerTest.dbPassword);
      } 
      catch(SQLException e) {
        System.err.println("SQLException: " + e.getMessage());
        throw(e);
      }
    }
      
    return dbConnection;
  }

    
  /**
   * Establish a testing framework by initializing appropriate objects.
   */
  public void setUp() throws Exception {
    super.setUp();
    dbConnection = getConnection();
    databaseHandler = new DatabaseHandler(dbConnection, dbAdapterName);
  }
    
    
  /**
   * Release any objects and closes database connections after tests 
   * are complete.
   */
  public void tearDown() throws Exception {
    dbConnection.close();
    dbConnection = null;
    databaseHandler = null;
    super.tearDown();
  }
  

 
  

  /**
   * Tests the DatabaseHandler.generateTable() method.
   * 
   * @throws MalformedURLException
   * @throws IOException
   * @throws SQLException
   * @throws Exception
   */
  public void testGenerateTable() 
         throws MalformedURLException, IOException, SQLException, Exception {
    DataManager dataManager = DataManager.getInstance();
    DataPackage dataPackage = null;
    InputStream metadataInputStream;
    String documentURL = TEST_SERVER + "?action=read&qformat=xml&docid="
        + TEST_DOCUMENT;
    URL url;

    /*
     * First get a test data package and parse it.
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

    /*
     * Assert that dataManager.parseMetadata() returned a non-null dataPackage
     * object.
     */
    assertNotNull("Data package is null", dataPackage);
   
    /*
     * Generate the table from the entity. Assert that the generateTable()
     * method succeeded. Assert that entity's database table name has been
     * set, and that the table can be found in the database. Finally, clean-up
     * by dropping the table.
     */
    if (dataPackage != null) {
      Entity[] entities = dataPackage.getEntityList();
      Entity entity = entities[0];
      boolean success = databaseHandler.generateTable(entity);
      assertTrue("DatabaseHandler did not succeed in generating table",success);
      String tableName = entity.getDBTableName(); 
      boolean isPresent = databaseHandler.isTableInDB(tableName);
      assertTrue("Could not find table " + tableName +" but it should be in db", 
                 isPresent);
      databaseHandler.dropTable(entity);
    }
  }

  
  /**
   * Tests the DatabaseHandler.dropTable() method. Does so by creating a test
   * table. First drops the table in case it was already present. Then creates
   * the table, calls isTableInDB(), and asserts that the table exists. Then
   * drops the table again, calls isTableInDB(), and asserts that the table does
   * not exist.
   * 
   * @throws SQLException
   */
  public void testDropTable() 
          throws IOException, MalformedURLException, SQLException, Exception {
    DataManager dataManager = DataManager.getInstance();
    DataPackage dataPackage = null;
    InputStream metadataInputStream;
    String documentURL = TEST_SERVER + "?action=read&qformat=xml&docid="
        + TEST_DOCUMENT;
    URL url;

    /*
     * First get a test data package and parse it.
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

    /*
     * Assert that dataManager.parseMetadata() returned a non-null dataPackage
     * object.
     */
    assertNotNull("Data package is null", dataPackage);
    
    /*
     * Generate the table and check for its existence in the database. Then
     * drop the table and check that it no longer exists in the database.
     */
    if (dataPackage != null) {
      Entity[] entities = dataPackage.getEntityList();
      Entity entity = entities[0];
      boolean success = databaseHandler.generateTable(entity);
      assertTrue("DatabaseHandler did not succeed in generating table",success);
      
      String tableName = entity.getDBTableName(); 
      boolean isPresent = databaseHandler.isTableInDB(tableName);
      assertTrue("Could not find table " + tableName +" but it should be in db", 
                 isPresent);
      success = databaseHandler.dropTable(entity);
      assertTrue("DatabaseHandler did not succeed in dropping table",success);
      isPresent = databaseHandler.isTableInDB(tableName);
      assertFalse("Found table " + tableName + " but it should not be in db", 
          isPresent);
    }
  }

}
