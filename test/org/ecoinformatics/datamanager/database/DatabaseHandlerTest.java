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

  /* During development, hard-code the database settings */
  static String dbDriver = "org.postgresql.Driver";
  static String dbURL = "jdbc:postgresql://localhost/datamanager";
  static String dbUser = "datamanager";
  static String dbPassword = "datamanager";
    
  /*
   * Instance fields
   */
    
  private DatabaseHandler databaseHandler;  //An instance of object being tested
  private Connection dbConnection  = null;            // the database connection
  private String  dbAdapterName = DatabaseAdapter.POSTGRES_ADAPTER;
  private final String TEST_TABLE = "COFFEES";
    
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
    testSuite.addTest(new DatabaseHandlerTest("testDoesDataExist"));
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
   * Tests the DatabaseHandler.doesDataExist() method. Does so by creating a 
   * test table. First drops the table in case it was already
   * present. Then creates the table, calls isTableInDB(), and asserts that
   * the table exists. Then drops the table again, calls isTableInDB(), and
   * asserts that the table does not exist.
   * 
   * @throws SQLException
   */
  public void testDoesDataExist() throws SQLException {
    String createString = "create table " + TEST_TABLE + " " +
                          "(COFFEE_NAME varchar(32), " +
                          "SUPPLIER_ID int, " +
                          "PRICE float, " +
                          "SALES int, " +
                          "TOTAL int)";
    String dropString = "DROP TABLE " + TEST_TABLE;
    boolean isPresent;
    Statement stmt = null;

    /*
     * First, drop the test table, just in case it got left behind by a
     * previous test.
     */
    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(dropString);
    }
    catch(SQLException e) {
      // Ignore error if test table can't be dropped at this point.
    }
    finally {
      if (stmt != null) stmt.close();
    }

    /*
     * Create the test table. Assert that databaseHandler.doesDataExist()
     * returns true for the test table.
     */
    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(createString);
      isPresent = databaseHandler.doesDataExist(TEST_TABLE);
      assertTrue("Could not find table " + 
                 TEST_TABLE + " but it should be in db", 
                 isPresent);
    } 
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
    finally {
      if (stmt != null) stmt.close();
    }
   
    /*
     * Clean-up by dropping the test table a second time. Assert that the
     * test table is not in the database.
     */
    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(dropString);
      isPresent = databaseHandler.doesDataExist(TEST_TABLE);
      assertFalse("Found table " + TEST_TABLE + " but it should not be in db", 
                  isPresent);
    }
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
    }
    finally {
      if (stmt != null) stmt.close();
    }

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
     * Compare the number of entities expected in the data package to the number
     * of entities found by the parser.
     */
    if (dataPackage != null) {
      Entity[] entities = dataPackage.getEntityList();
      Entity entity = entities[0];
      String tableName = entity.getDBTableName(); 
      boolean success = databaseHandler.generateTable(entity);
      assertTrue("DatabaseHandler did not succeed in generating table",success);
      boolean isPresent = databaseHandler.doesDataExist(tableName);
      assertTrue("Could not find table " + tableName +" but it should be in db", 
                 isPresent);
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
     * Compare the number of entities expected in the data package to the number
     * of entities found by the parser.
     */
    if (dataPackage != null) {
      Entity[] entities = dataPackage.getEntityList();
      Entity entity = entities[0];
      boolean success = databaseHandler.generateTable(entity);
      assertTrue("DatabaseHandler did not succeed in generating table",success);
      
      String tableName = entity.getDBTableName(); 
      boolean isPresent = databaseHandler.doesDataExist(tableName);
      assertTrue("Could not find table " + tableName +" but it should be in db", 
                 isPresent);
      success = databaseHandler.dropTable(entity);
      assertTrue("DatabaseHandler did not succeed in dropping table",success);
      isPresent = databaseHandler.doesDataExist(tableName);
      assertFalse("Found table " + tableName + " but it should not be in db", 
          isPresent);
    }
  }

}
