package org.ecoinformatics.datamanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    testSuite.addTest(new TableMonitorTest("testAddTableEntry"));
    testSuite.addTest(new TableMonitorTest("testDropTableEntry"));
    testSuite.addTest(new TableMonitorTest("testGetCreationDate"));
    testSuite.addTest(new TableMonitorTest("testGetLastUsageDate"));
    testSuite.addTest(new TableMonitorTest("testGetTableList"));
    testSuite.addTest(new TableMonitorTest("testIsTableInDB"));
    testSuite.addTest(new TableMonitorTest("testSetLastUsageDate"));
    testSuite.addTest(new TableMonitorTest("testSetTableExpirationPolicy"));
    
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
   * Tests the TableMonitor.addTableEntry() method. Does so by adding a table
   * entry for a test table and asserting that it has been entered,
   * and only one record for it exists in the data table registry.
   * 
   * @throws SQLException
   */
  public void testAddTableEntry() throws SQLException {
    boolean isPresent = false;
    boolean success = false;
    String registry = tableMonitor.getDataTableRegistryName();
    ResultSet rs;

    String selectString = 
      "SELECT * FROM " +
      registry +
      " WHERE TABLE_NAME='" + TEST_TABLE + "'";
    Statement stmt;
    
    String cleanString = 
      "DELETE FROM " +
      registry +
      " WHERE TABLE_NAME='" + TEST_TABLE + "'";

    // First, clean-up any existing entry for the test table
    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(cleanString);
      stmt.close();
    } 
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }

    // Next, tell TableMonitor to add the table entry for the test table
    success = tableMonitor.addTableEntry(TEST_TABLE);
    
    // Assert that the operation succeeded
    assertTrue("Failed to add table entry", success);

    // Query the table registry. The table entry should be present, and only
    // one record of it should exist.
    try {
      stmt = dbConnection.createStatement();             
      rs = stmt.executeQuery(selectString);
      
      int rowCount = 0;
      while (rs.next()) {
        rowCount++;
        String TABLE_NAME = rs.getString("TABLE_NAME");
        
        if (TABLE_NAME.equalsIgnoreCase(TEST_TABLE)) {
          isPresent = true;
        }
      }
      
      stmt.close();
      assertTrue("Table entry not present", isPresent);
      assertEquals("Multiple table entries found for "+TEST_TABLE, rowCount, 1);
    }
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
    }
    
    // Clean-up any existing entries for the test table
    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(cleanString);
      stmt.close();
    } 
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }

  }
    
 
  /**
   * Tests the TableMonitor.dropTableEntry() method. Does so by adding a table
   * entry for a test table and asserting that it has been entered,
   * and only one record for it exists in the data table registry.
   * 
   * @throws SQLException
   */
  public void testDropTableEntry() throws SQLException {
    Date now = new Date();
    String registry = tableMonitor.getDataTableRegistryName();
    boolean success;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    String insertString = 
      "INSERT INTO " + 
      registry +
      " values(" +
          "'" + TEST_TABLE + "', " +
          "'" + simpleDateFormat.format(now) + "', " +
          "'" + simpleDateFormat.format(now) + "', " +
          "1" +
      ")";

    // First, insert an entry for the test table
    try {
      Statement stmt = dbConnection.createStatement();             
      stmt.executeUpdate(insertString);
      stmt.close();
    } 
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }

    // Next, tell TableMonitor to drop the table entry for the test table
    success = tableMonitor.dropTableEntry(TEST_TABLE);
    
    // Assert that one row was successfully deleted
    assertTrue("Failed to drop table entry", success);
  }
  

  /**
   * Tests the TableMonitor.getCreationDate() method. Adds a data table entry,
   * retrieves the entry's creation date, and compares it to today's date
   * (they should be equal). Then cleans up by dropping the data table entry.
   */
  public void testGetCreationDate() throws SQLException {
    Date creationDate;
    Date now = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    String nowDateString = simpleDateFormat.format(now);

    // Add the test table entry. By default, creation date is today's date.
    tableMonitor.addTableEntry(TEST_TABLE);
    
    // Retrieve the creation date from the data table registry
    creationDate = tableMonitor.getCreationDate(TEST_TABLE);
    String creationDateString = simpleDateFormat.format(creationDate);

    // Assert that creation date should be equal to today's date
    assertTrue("Creation date should equal today's date: " + 
               creationDateString + " " + nowDateString, 
               creationDateString.equals(nowDateString));
    
    // Clean-up the test table entry
    tableMonitor.dropTableEntry(TEST_TABLE);
  }
    
 
  /**
   * Tests the TableMonitor.getLastUsageDate() method. Adds a data table entry,
   * retrieves the entry's last usage date, and compares it to today's date
   * (they should be equal). Then cleans up by dropping the data table entry.
   */
  public void testGetLastUsageDate() throws SQLException {
    Date lastUsageDate;
    Date now = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    String nowDateString = simpleDateFormat.format(now);

    // Add the test table entry. By default, creation date is today's date.
    tableMonitor.addTableEntry(TEST_TABLE);
    
    // Retrieve the creation date from the data table registry
    lastUsageDate = tableMonitor.getCreationDate(TEST_TABLE);
    String lastUsageDateString = simpleDateFormat.format(lastUsageDate);

    // Assert that last usage date should be equal to today's date
    assertTrue("Last usage date should equal today's date: " + 
               lastUsageDateString + " " + nowDateString, 
               lastUsageDateString.equals(nowDateString));
    
    // Clean-up the test table entry
    tableMonitor.dropTableEntry(TEST_TABLE);
  }
  

  /**
   * Tests the TableMonitor.getTableList() method. Adds a table entry for the
   * test table, then gets the table list and asserts that the test table has
   * been found. Then drops the table entry, gets the table list a second time,
   * and asserts that the test table was not found in the table list.
   * 
   * @throws SQLException
   */
  public void testGetTableList() throws SQLException {
    boolean found = false;
    
    tableMonitor.addTableEntry(TEST_TABLE);
    String[] tableList = tableMonitor.getTableList();
    
    for (int i = 0; i < tableList.length; i++) {
      found = found || (tableList[i].equalsIgnoreCase(TEST_TABLE));
    }
    
    assertTrue("Did not find " + TEST_TABLE + " in table list", found);

    found = false;
    tableMonitor.dropTableEntry(TEST_TABLE);
    tableList = tableMonitor.getTableList();
    
    for (int i = 0; i < tableList.length; i++) {
      found = found || (tableList[i].equalsIgnoreCase(TEST_TABLE));
    }
    
    assertFalse("Found " + TEST_TABLE + 
                " in table list, but it should have been dropped" , 
                found);
  }
    
 
  /**
   * Tests the TableMonitor.isTableInDB() method. Does so by creating a test
   * table. First drops the table in case it was already
   * present. Then creates the table, calls isTableInDB(), and asserts that
   * the table exists. Then drops the table again, calls isTableInDB(), and
   * asserts that the table does not exist.
   * 
   * @throws SQLException
   */
  public void testIsTableInDB() throws SQLException {
    String createString = "create table " + TEST_TABLE + " " +
                          "(COFFEE_NAME varchar(32), " +
                          "SUPPLIER_ID int, " +
                          "PRICE float, " +
                          "SALES int, " +
                          "TOTAL int)";
    String dropString = "DROP TABLE " + TEST_TABLE;
    boolean isPresent;
    Statement stmt;

    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(dropString);
      stmt.close();
    }
    catch(SQLException e) {
      // Ignore error if test table can't be dropped at this point.
    }

    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(createString);
      stmt.close();
      isPresent = tableMonitor.isTableInDB(TEST_TABLE);
      assertTrue("Could not find table " + 
                 TEST_TABLE + " but it should be in db", 
                 isPresent);
    } 
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      throw(e);
    }
   
    try {
      stmt = dbConnection.createStatement();             
      stmt.executeUpdate(dropString);
      stmt.close();
      isPresent = tableMonitor.isTableInDB(TEST_TABLE);
      assertFalse("Found table " + TEST_TABLE + " but it should not be in db", 
                  isPresent);
    }
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
    }

  }

  /**
   * Tests the TableMonitor.setLastUsageDate() method. First adds a table entry 
   * for a test table, calls setLastUsageDate() for a known date, then queries
   * the data table registry to check that the found date matches the known
   * date.
   * 
   * @throws SQLException
   */
  public void testSetLastUsageDate() throws SQLException {
    boolean success;
    long epochMilliseconds = 1156979161000l;
    String dataTableRegistryName = tableMonitor.getDataTableRegistryName();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    Date testDate = new Date(epochMilliseconds);
    String selectString = 
      "SELECT last_usage_date FROM " + dataTableRegistryName +
      " WHERE table_name ='" + TEST_TABLE + "'";
    
    tableMonitor.addTableEntry(TEST_TABLE);
    success = tableMonitor.setLastUsageDate(TEST_TABLE, testDate);
    
    assertTrue("tableMonitor.setLastUsageDate() did not succeed. ", success);
    
    // Query the table registry to determine the last_usage_date value for
    // the test table and compare it to the known value
    try {
      Date foundDate = null;
      Statement stmt = dbConnection.createStatement();             
      ResultSet rs = stmt.executeQuery(selectString);
      
      while (rs.next()) {
        foundDate = rs.getDate("last_usage_date");
      }
      
      stmt.close();
      String twoDates = "testDate = " + simpleDateFormat.format(testDate) 
                        + ",  "
                        + "foundDate = " + simpleDateFormat.format(foundDate)
                        + ". ";
      assertEquals("Last usage date found does not match test value: " + 
                   twoDates, 
                   simpleDateFormat.format(testDate),
                   simpleDateFormat.format(foundDate));
    }
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
    }

    // Clean-up table entry for test table
    tableMonitor.dropTableEntry(TEST_TABLE);
  }
  
  
  /**
   * Tests the TableMonitor.setTableExpirationPolicy() method. First adds a 
   * table entry for a test table, calls setTableExpirationPolicy() with a known
   * priority value, then queries the data table registry to check that the 
   * found priority value matches the known priority value.
   * 
   * @throws SQLException
   */
  public void testSetTableExpirationPolicy() throws SQLException {
    boolean success;
    int testPriority = 1;
    String dataTableRegistryName = tableMonitor.getDataTableRegistryName();
    String selectString = 
      "SELECT priority FROM " + dataTableRegistryName +
      " WHERE table_name ='" + TEST_TABLE + "'";
    
    tableMonitor.addTableEntry(TEST_TABLE);
    success = tableMonitor.setTableExpirationPolicy(TEST_TABLE, testPriority);
    
    assertTrue("tableMonitor.setTableExpirationPolicy() did not succeed. ", 
               success);
    
    // Query the table registry to determine the priority value for
    // the test table and compare it to the known priority value
    try {
      int foundPriority = -99;
      Statement stmt = dbConnection.createStatement();             
      ResultSet rs = stmt.executeQuery(selectString);
      
      while (rs.next()) {
        foundPriority = rs.getInt("priority");
      }
      
      stmt.close();
      assertEquals("Priority found does not match test value: ",
                   foundPriority, testPriority);
    }
    catch(SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
    }

    // Clean-up table entry for test table
    tableMonitor.dropTableEntry(TEST_TABLE);
  }
  
}
