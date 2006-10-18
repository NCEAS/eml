package org.ecoinformatics.datamanager.database;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class DatabaseAdapterTest extends TestCase {

  /**
   * Constructor 
   * @param name The name of testing
   */
  public DatabaseAdapterTest(String name) {
    super(name);
  }


  /**
   * Establish a testing framework by initializing appropriate objects.
   */
  protected void setUp() throws Exception {
    super.setUp();

  }


  /**
   * Release any objects and closes database connections after tests 
   * are complete.
   */
  protected void tearDown() throws Exception {

    super.tearDown();
  }


  /**
   * Test DatabaseAdapter.getLegalDBTableName() method. For each string in a 
   * list of bad (i.e. illegal) table names, ensure that the method returns 
   * the expected (i.e. legal) table name.
   */
  public void testGetLegalDBTableName() {
    String[] badNames = {"table name", "table-name", "table.name"};
    String[] expectedNames = {"table_name", "table_name", "table_name"};
    
    for (int i = 0; i < badNames.length; i++) {
      String legalName = DatabaseAdapter.getLegalDBTableName(badNames[i]);
      assertEquals("Returned table name does not match expected name",
                   legalName, expectedNames[i]);
    }
  }


  /**
   * Create a suite of tests to be run together
   */
  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new DatabaseAdapterTest("testGetLegalDBTableName"));
    return suite;
  }
  
}
