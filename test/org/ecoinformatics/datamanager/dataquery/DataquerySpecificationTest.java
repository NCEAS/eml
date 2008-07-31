package org.ecoinformatics.datamanager.dataquery;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.database.DatabaseConnectionPoolInterfaceTest;
import org.ecoinformatics.datamanager.download.ConfigurableEcogridEndPoint;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterface;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterfaceTest;

public class DataquerySpecificationTest extends TestCase {
  
  /*
   * Instance fields
   */
  
  private EcogridEndPointInterface endPointInfo;
  
  private DatabaseConnectionPoolInterfaceTest connectionPool;
  
  private DataManager dataManager;
  
  private String fileName = "lib/datamanager/schema/dataquery_simple.xml";
  
  private String parserName = "org.apache.xerces.parsers.SAXParser";
  
  /**
   * Because DataqueryTest is a subclass of TestCase, it must provide a
   * constructor with a String parameter.
   * 
   * @param name   the name of a test method to run
   */
  public DataquerySpecificationTest(String name) {
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
    
    testSuite.addTest(new DataquerySpecificationTest("initialize"));
    testSuite.addTest(new DataquerySpecificationTest("testParseQuery"));
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
   * Establish a testing framework by initializing appropriate objects.
   */
  public void setUp() throws Exception {
    super.setUp();
    //endPointInfo = new EcogridEndPointInterfaceTest();
    endPointInfo = new ConfigurableEcogridEndPoint();
    connectionPool = new DatabaseConnectionPoolInterfaceTest();
    dataManager = 
    	DataManager.getInstance(connectionPool, connectionPool.getDBAdapterName());
  }
  
  
  /**
   * Release any objects after tests are complete.
   */
  public void tearDown() throws Exception {
    dataManager = null;
    super.tearDown();
  }
  
  
  /**
   * Unit test for the parsing dataquery
 * @throws Exception 
   * 
   * @throws MalformedURLException
   * @throws IOException
   * @throws Exception
   */
  public void testParseQuery() throws Exception {
	  
	DataquerySpecification specification = 
		new DataquerySpecification(
				new FileReader(fileName),
				parserName,
				connectionPool,
				endPointInfo);
	//log.debug(ds.getQuery().toSQLString());
	    	

    /*
     * Assert that the spec is not null 
     */
    assertNotNull("Specification is null", specification);
    
    assertNotNull("Query is null", specification.getQuery());

    if (specification.getQuery() != null) {
    	//System.out.println(specification.getQuery().toSQLString());
    	System.out.println(specification.getUnion().toSQLString());
    	//TODO more tests!
    } 
  }
  
}
