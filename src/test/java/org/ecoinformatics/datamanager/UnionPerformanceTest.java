package org.ecoinformatics.datamanager;

import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecoinformatics.datamanager.database.Condition;
import org.ecoinformatics.datamanager.database.DatabaseConnectionPoolInterface;
import org.ecoinformatics.datamanager.database.DatabaseConnectionPoolInterfaceTest;
import org.ecoinformatics.datamanager.database.Query;
import org.ecoinformatics.datamanager.database.SelectionItem;
import org.ecoinformatics.datamanager.database.TableItem;
import org.ecoinformatics.datamanager.database.Union;
import org.ecoinformatics.datamanager.database.WhereClause;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterface;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterfaceTest;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;

public class UnionPerformanceTest extends TestCase {

	/*
	 * Class fields
	 */
	public static Log log = LogFactory.getLog(UnionPerformanceTest.class);

	private final String QUERY_TEST_DOCUMENT = "knb-lter-gce.1.9";
	private final String QUERY_TEST_SERVER = "https://knb.ecoinformatics.org/knb/metacat";

	//private final String QUERY_TEST_DOCUMENT = "leinfelder.253.6";
	//private final String QUERY_TEST_SERVER = "http://localhost:8080/knb/metacat";
	
	/*
	 * Instance fields
	 */
	private DataManager dataManager;
	private DatabaseConnectionPoolInterface connectionPool = null;
	private EcogridEndPointInterface endPointInfo = null;
	private int conditionColumnIndex = 0;
	
	/*
	 * Constructors
	 */

	/**
	 * Because DataManagerTest is a subclass of TestCase, it must provide a
	 * constructor with a String parameter.
	 * 
	 * @param name
	 *            the name of a test method to run
	 */
	public UnionPerformanceTest(String name) {
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

		testSuite.addTest(new UnionPerformanceTest("initialize"));
		testSuite.addTest(new UnionPerformanceTest("testSelectData"));

		return testSuite;
	}

	/*
	 * Instance methods
	 */

	/**
	 * Run an initial test that always passes to check that the test harness is
	 * working.
	 */
	public void initialize() {
		assertTrue(1 == 1);
	}

	/**
	 * Establish a testing framework by initializing appropriate objects.
	 */
	public void setUp() throws Exception {
		super.setUp();
		DatabaseConnectionPoolInterfaceTest connectionPool = 
                new DatabaseConnectionPoolInterfaceTest();
		String dbAdapterName = connectionPool.getDBAdapterName();
		dataManager = DataManager.getInstance(connectionPool, dbAdapterName);
		endPointInfo = new EcogridEndPointInterfaceTest();
	}

	/**
	 * Release any objects after tests are complete.
	 */
	public void tearDown() throws Exception {
		connectionPool = null;
		dataManager = null;
		endPointInfo = null;
		super.tearDown();
	}

	/**
	 * Unit test for the DataManager.selectData() method. Tests the creation and
	 * use of a Query object for querying a data table. Runs a query and checks
	 * the result set against known values in the data table. This test is
	 * essentially a complete cradle-to-grave test of Uses Cases 1-4.
	 */
	public void testSelectData() throws Exception {
		
		log.info("starting the test");
		
		long startTime = System.currentTimeMillis();
		
		Entity entity = null;
		AttributeList attributeList;
		Attribute attribute;
		Attribute conditionAttribute;
		DataPackage dataPackage = null;
		DataPackage[] dataPackages = null;
		Union union = null;
		
		String documentURL = 
			QUERY_TEST_SERVER
			+ "?action=read&qformat=xml&docid=" 
			+ QUERY_TEST_DOCUMENT;
		
		InputStream inputStream = null;
		String operator = "like";
		boolean success;
		String value = new String("%");
		ResultSet resultSet = null;
		URL url;

		// First create the DataPackage object that will be used in the query.
		url = new URL(documentURL);
		inputStream = url.openStream();
		
		log.debug("parsing datapackage");
		dataPackage = dataManager.parseMetadata(inputStream);
		log.debug("parsed datapackage!");
		
		//check if this has been loaded before
		String testDbTable = DataManager.getDBTableName(dataPackage.getEntityList()[0]);
		log.debug("testDbTable=" + testDbTable);
		
		//if (testDbTable == null ) {
			//load the package
			success = dataManager.loadDataToDB(dataPackage, endPointInfo);
			log.error("loadedDataToDB completed");
//		}
//		else {
			//don't try to get the data again
			success = true;
//		}
		
		log.debug("loaded data to db, success=" + success);

		union = new Union();

		Entity[] entityList = dataPackage.getEntityList();
		for (int i = 0; i < entityList.length; i++) {
			entity = entityList[i];
			attributeList = entity.getAttributeList();
			Attribute[] attributes = attributeList.getAttributes();
			conditionAttribute = attributes[conditionColumnIndex];
			
			//build some queries from each entity
			if (success && dataPackage != null) {
				dataPackages = new DataPackage[1];
				dataPackages[0] = dataPackage;
				
				//log.debug("creating Query");
				
				Query query = new Query();
				/* SELECT clause */
				for (int j=0; j < attributes.length; j++) {
					attribute = attributes[j];
					SelectionItem selectionItem = 
						new SelectionItem(entity,attribute);
					query.addSelectionItem(selectionItem);
				}
				/* FROM clause */
				TableItem tableItem = new TableItem(entity);
				query.addTableItem(tableItem);
				/* WHERE clause with condition */
				Condition condition = 
					new Condition(entity, conditionAttribute, operator, value);
				WhereClause whereClause = new WhereClause(condition);
				query.setWhereClause(whereClause);
								
				//log.debug("Query SQL = " + query.toSQLString());

				//add the query to the union
				union.addQuery(query);
				
			}//dp success
			
		} //for loop
				
		log.debug("created Union object: time check: " + (System.currentTimeMillis() - startTime));
		
		log.debug("Union Query SQL = " + union.toSQLString());
		
		log.debug("time check: " + (System.currentTimeMillis() - startTime));
		
		try {
			//try to get the results
			log.debug("about to select data");
			
			resultSet = dataManager.selectData(union, dataPackages);
			
			log.debug("got resultset: time check: " + (System.currentTimeMillis() - startTime));
			
			if (resultSet != null) {

				int j = 1;

				while (resultSet.next()) {
					Object column0 = resultSet.getString(1);
					//Object column1 = resultSet.getObject(2);
					log.debug("resultSet[" + j + "], column0 =  " + column0);
					//log.error("resultSet[" + j + "], column1 =  " + column1);
					j++;
					//just the first one
					//break;
				}
			} 
			else {
				throw new Exception("resultSet is null");
			}
			log.info("about to drop tables");
			dataManager.dropTables(dataPackage); // Clean-up test tables
		} 
		catch (Exception e) {
			System.err.println("Exception: "
					+ e.getMessage());
			e.printStackTrace();
			throw (e);
		} 
		finally {
			if (resultSet != null) {
				resultSet.close();
			}
			
		}
		
		long endTime = System.currentTimeMillis();
		
		log.info("time to run in millis: " + (endTime - startTime));
	}
	
	public static void main(String args[]) {
		UnionPerformanceTest upt = new UnionPerformanceTest("UnionPerformanceTest");
		try {
			upt.setUp();
			upt.testSelectData();
			upt.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
