package org.ecoinformatics.datamanager.dataquery;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.database.DatabaseConnectionPoolInterfaceTest;
import org.ecoinformatics.datamanager.download.AuthenticatedEcogridEndPointInterface;
import org.ecoinformatics.datamanager.download.ConfigurableEcogridEndPoint;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterface;
import org.ecoinformatics.ecogrid.client.AuthenticationServiceClient;

public class DataquerySpecificationTest extends TestCase {
  
    private static Log log = LogFactory.getLog(DataquerySpecificationTest.class);

  /*
   * Instance fields
   */
  
  private EcogridEndPointInterface endPointInfo;
  
  private DatabaseConnectionPoolInterfaceTest connectionPool;
  
  private DataManager dataManager;
  
  private String fileName = "lib/datamanager/schema/dataquery.xml";
  
  private String parserName = "org.apache.xerces.parsers.SAXParser";
  
  private String username = "uid=leinfelder,o=NCEAS,dc=ecoinformatics,dc=org";
  
  private String password = "cielo1635";
  
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
    
    testSuite.addTest(new DataquerySpecificationTest("testParseQuery"));
    return testSuite;
  }
  
  
  /*
   * Instance methods
   */
   
  
  /**
   * Establish a testing framework by initializing appropriate objects.
   */
  public void setUp() throws Exception {
    super.setUp();
    endPointInfo = new ConfigurableEcogridEndPoint();

    //set up for authenticated ecogrid use
    String sessionId = login(username, password);
    ((ConfigurableEcogridEndPoint)endPointInfo).setSessionId(sessionId);
    
    connectionPool = new DatabaseConnectionPoolInterfaceTest();
    dataManager = 
    	DataManager.getInstance(connectionPool, connectionPool.getDBAdapterName());
  }
  
  /**
   * Release any objects after tests are complete.
   */
  public void tearDown() throws Exception {
	  String sessionId = ((AuthenticatedEcogridEndPointInterface)endPointInfo).getSessionId();
	  logout(sessionId);
	  ((ConfigurableEcogridEndPoint)endPointInfo).setSessionId(null);
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
    	log.debug(specification.getQuery().toSQLString());
    	//log.debug(specification.getUnion().toSQLString());
    	//TODO more tests!
    	ResultSet rs = dataManager.selectData(specification.getQuery(), specification.getDataPackages());
    	printResultSet(rs);
    } 
  }
  
  private String login(String username, String password) throws Exception {
	   String sessionId = null;
	   String metacatEcogridAuthEndPoint = ((ConfigurableEcogridEndPoint)endPointInfo).getMetacatAuthenticatedEcogridEndPoint();
	   AuthenticationServiceClient authClient = 
		   new AuthenticationServiceClient(metacatEcogridAuthEndPoint);
	   sessionId = authClient.login_action(username, password);
	   return sessionId;
  }
  
  private void logout(String sessionId) throws Exception {
	  String metacatEcogridAuthEndPoint = ((ConfigurableEcogridEndPoint)endPointInfo).getMetacatAuthenticatedEcogridEndPoint();
	   AuthenticationServiceClient authClient = 
		   new AuthenticationServiceClient(metacatEcogridAuthEndPoint); 
	   authClient.logout_action(sessionId);
  }
  
  public static void printResultSet(ResultSet resultSet) throws Exception {
	  if (resultSet != null) {

			int columns = resultSet.getMetaData().getColumnCount();
			String row = "";
			for (int i=1; i <= columns; i++) {
				String column = resultSet.getMetaData().getColumnName(i);
				row += column;
				row += "\t";
			}
			log.debug(row);

			while (resultSet.next()) {
				row = "";
				for (int i=1; i <= columns; i++) {
					Object value = resultSet.getString(i);
					row += value;
					row += "\t";
				}
				log.debug(row);
			}
		} 
  }
  
}
