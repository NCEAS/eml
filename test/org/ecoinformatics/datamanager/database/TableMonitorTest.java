package org.ecoinformatics.datamanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class TableMonitorTest extends TestCase {

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
    
  private TableMonitor tableMonitor;  // An instance of the object being tested
  private Connection dbConnection  = null;           // the database connection
  private String  dbAdapterName = "PostgresAdapter";;   // DatabaseAdapter name
    
    
  /*
   * Constructors
   */

  /**
   * Because DataManagerTest is a subclass of TestCase, it must provide a
   * constructor with a String parameter.
   * 
   * @param name   the name of a test method to run
   */
  public TableMonitorTest(String name) {
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
    
    testSuite.addTest(new TableMonitorTest("initialize"));
    testSuite.addTest(new TableMonitorTest("testIsTableInDB"));
    
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
        Class.forName(TableMonitorTest.dbDriver);
      } 
      catch(java.lang.ClassNotFoundException e) {
        System.err.print("ClassNotFoundException: "); 
        System.err.println(e.getMessage());
        throw(e);
      }

      try {
        dbConnection = DriverManager.getConnection(TableMonitorTest.dbURL, 
                                                   TableMonitorTest.dbUser, 
                                                   TableMonitorTest.dbPassword);
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
    tableMonitor = new TableMonitor(dbConnection, dbAdapterName);
  }
    
    
  /**
   * Release any objects and closes database connections after tests 
   * are complete.
   */
  public void tearDown() throws Exception {
    dbConnection.close();
    dbConnection = null;
    tableMonitor = null;
    super.tearDown();
  }
    
 
  /**
   * Tests the TableMonitor.isTableInDB() method. Does so by creating a bogus
   * table named 'COFFEES'. First drops the table in case it was already
   * present. Then creates the table, calls isTableInDB(), and asserts that
   * the table exists. Then drops the table again, calls isTableInDB(), and
   * asserts that the table does not exist.
   * 
   * @throws SQLException
   */
  public void testIsTableInDB() throws SQLException {
    String createString = "create table COFFEES " +
                          "(COFFEE_NAME varchar(32), " +
                          "SUPPLIER_ID int, " +
                          "PRICE float, " +
                          "SALES int, " +
                          "TOTAL int)";
    String dropString = "drop table COFFEES";
    boolean isPresent;
    Statement stmt;

    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(dropString);
      stmt.close();
    }
    catch(SQLException e) {
      // Ignore error if COFFEES table can't be dropped at this point.
    }

    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(createString);
      stmt.close();
      isPresent = tableMonitor.isTableInDB("COFFEES");
      assertTrue("Could not find table COFFEES but should be in db", isPresent);
    } 
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
   
    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(dropString);
      stmt.close();
      isPresent = tableMonitor.isTableInDB("COFFEES");
      assertFalse("Found table COFFEES but should not be in db", isPresent);
    }
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
    }

  }

}
