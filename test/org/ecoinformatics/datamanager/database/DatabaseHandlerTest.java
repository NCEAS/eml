package org.ecoinformatics.datamanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
  private String  dbAdapterName = "PostgresAdapter";;    // DatabaseAdapter name
  private final String TEST_TABLE = "COFFEES";
    
    
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
    Statement stmt;

    /*
     * First, drop the test table, just in case it got left behind by a
     * previous test.
     */
    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(dropString);
      stmt.close();
    }
    catch(SQLException e) {
      // Ignore error if test table can't be dropped at this point.
    }

    /*
     * Create the test table. Assert that databaseHandler.doesDataExist()
     * returns true for the test table.
     */
    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(createString);
      stmt.close();
      isPresent = databaseHandler.doesDataExist(TEST_TABLE);
      assertTrue("Could not find table " + 
                 TEST_TABLE + " but it should be in db", 
                 isPresent);
    } 
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
   
    /*
     * Clean-up by dropping the test table a second time. Assert that the
     * test table is not in the database.
     */
    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(dropString);
      stmt.close();
      isPresent = databaseHandler.doesDataExist(TEST_TABLE);
      assertFalse("Found table " + TEST_TABLE + " but it should not be in db", 
                  isPresent);
    }
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
    }

  }

}
