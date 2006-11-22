package org.ecoinformatics.datamanager;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ecoinformatics.datamanager.database.Condition;
import org.ecoinformatics.datamanager.database.DatabaseConnectionPoolInterfaceTest;
import org.ecoinformatics.datamanager.database.Query;
import org.ecoinformatics.datamanager.database.SelectionItem;
import org.ecoinformatics.datamanager.database.TableItem;
import org.ecoinformatics.datamanager.database.WhereClause;
import org.ecoinformatics.datamanager.download.DataStorageTest;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterfaceTest;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;

public class DataManagerTest extends TestCase {
  
  /*
   * Class fields
   */
  
  
  /*
   * Instance fields
   */
  
  private DataManager dataManager;
  private EcogridEndPointInterfaceTest endPointInfo = new EcogridEndPointInterfaceTest();
  
//  private final String TEST_DOCUMENT = "knb-lter-mcm.7002.1";
//  private final String TEST_SERVER = "http://metacat.lternet.edu/knb/metacat";
//  private final int ENTITY_NUMBER_EXPECTED = 5;

//  private final String TEST_DOCUMENT = "tao.1.1";
//  private final String TEST_SERVER ="http://knb.ecoinformatics.org/knb/metacat";
//  private final int ENTITY_NUMBER_EXPECTED = 1;
  
  private final String COLUMN_1 = "column1";
  private final String COLUMN_2 = "column2";
  private final String ENTITY_NAME = "head-line.data";
  private final int    ENTITY_NUMBER_EXPECTED = 1;
  private final int    NUMBER_OF_COLUMNS = 2;
  private final String QUERY_ENTITY_NAME = "INS-GCEM-0011_1_3.TXT";;
  private final String QUERY_PACKAGE_ID = "knb-lter-gce.1.9";
  private final String QUERY_TEST_DOCUMENT = "knb-lter-gce.1.9";
  private final String QUERY_TEST_SERVER = "http://metacat.lternet.edu/knb/metacat";
  private final String TABLE_NAME = "head_line_data";
  private final String TEST_DOCUMENT = "tao.12106.2";
  private final String TEST_SERVER = "http://pacific.msi.ucsb.edu:8080/knb/metacat";
  
  
  /*
   * Constructors
   */

  /**
   * Because DataManagerTest is a subclass of TestCase, it must provide a
   * constructor with a String parameter.
   * 
   * @param name   the name of a test method to run
   */
  public DataManagerTest(String name) {
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
    
    testSuite.addTest(new DataManagerTest("initialize"));
    testSuite.addTest(new DataManagerTest("testParseMetadata"));
    testSuite.addTest(new DataManagerTest("testDownloadData"));
    testSuite.addTest(new DataManagerTest("testLoadDataToDB"));
    testSuite.addTest(new DataManagerTest("testSelectData"));
    
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
    DatabaseConnectionPoolInterfaceTest connectionPool = 
                                      new DatabaseConnectionPoolInterfaceTest();
    String dbAdapterName = connectionPool.getDBAdapterName();
    dataManager = DataManager.getInstance(connectionPool, dbAdapterName);
  }
  
  
  /**
   * Release any objects after tests are complete.
   */
  public void tearDown() throws Exception {
    dataManager = null;
    super.tearDown();
  }
  
  
  /**
   * Unit test for the DataManager.downloadData() methods (Use Case #2).
   * 
   * @throws MalformedURLException
   * @throws IOException
   * @throws Exception
   */
  public void testDownloadData() 
          throws MalformedURLException, IOException, Exception {
    DataPackage dataPackage = null;
    String documentURL = TEST_SERVER + 
                         "?action=read&qformat=xml&docid=" + 
                         TEST_DOCUMENT;
    InputStream metadataInputStream;
    boolean success = false;
    DataStorageTest[] testStorageList = new DataStorageTest[1];
    URL url;
    
    try {
      url = new URL(documentURL);
      metadataInputStream = url.openStream();
      dataPackage = dataManager.parseMetadata(metadataInputStream);
    }
    catch (MalformedURLException e) {
      e.printStackTrace();
      throw(e);
    }
    catch (IOException e) {
      e.printStackTrace();
      throw(e);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw(e);
    }
    
    if (dataPackage != null) {
      testStorageList[0] = new DataStorageTest();
      success = dataManager.downloadData(dataPackage, 
                                         endPointInfo,
                                         testStorageList);
    }
    
    /*
     * Assert that dataManager.downloadData() returned a 'true' value,
     * indicating success.
     */
    assertTrue("downloadData() was not successful", success);
  }
 
  
  /**
   * Unit test for the DataManager.loadDataToDB() methods (Use Case #3).
   * This methods also tests two additional methods in the API:
   * 
   *   getDBFieldNames()
   *   getDBTableName()
   * 
   * @throws MalformedURLException
   * @throws IOException
   * @throws Exception
   */
  public void testLoadDataToDB() 
        throws MalformedURLException, IOException, Exception {
    DataPackage dataPackage = null;
    String documentURL = TEST_SERVER + 
                         "?action=read&qformat=xml&docid=" + 
                         TEST_DOCUMENT;
    InputStream metadataInputStream;
    boolean success = false;
    URL url;
    
    try {
      url = new URL(documentURL);
      metadataInputStream = url.openStream();
      dataPackage = dataManager.parseMetadata(metadataInputStream);
      
      if (dataPackage != null) {
        success = dataManager.loadDataToDB(dataPackage, endPointInfo);
        
        /*
         * Test that we can access the correct database table name and field 
         * names for this packageID and entity name.
         */
        if (success) {
          String packageID = TEST_DOCUMENT;
          String entityName = ENTITY_NAME;
          String tableName = dataManager.getDBTableName(packageID, entityName);
          assertNotNull("null value for tableName", tableName);
          assertEquals("tableName does not equal expected value", 
                       tableName, 
                       TABLE_NAME);
          String[] fieldNames = 
                             dataManager.getDBFieldNames(packageID, entityName);         
          assertNotNull("null value for fieldNames array", fieldNames);   
          assertEquals("Incorrect number of columns found", 
                       fieldNames.length, NUMBER_OF_COLUMNS);
          assertEquals("First field name does not equal expected value",
                       fieldNames[0], COLUMN_1);
          assertEquals("Second field name does not equal expected value",
                       fieldNames[1], COLUMN_2);
        }
        
        dataManager.dropTables(dataPackage);  // Clean-up test tables
      }
    }
    catch (MalformedURLException e) {
      e.printStackTrace();
      throw(e);
    }
    catch (IOException e) {
      e.printStackTrace();
      throw(e);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw(e);
    }

    /*
     * Assert that dataManager.loadDataToDB() returned a 'true' value,
     * indicating success.
     */
    assertTrue("loadDataToDB() was not successful", success);
  }

  
  /**
   * Unit test for the DataManager.parseMetadata() method (Use Case #1). 
   * 
   * @throws MalformedURLException
   * @throws IOException
   * @throws Exception
   */
  public void testParseMetadata()
        throws MalformedURLException, IOException, Exception {
    DataPackage dataPackage = null;
    InputStream metadataInputStream;
    String documentURL = TEST_SERVER + 
                         "?action=read&qformat=xml&docid=" + 
                         TEST_DOCUMENT;
    URL url;
    
    try {
      url = new URL(documentURL);
      metadataInputStream = url.openStream();
      dataPackage = dataManager.parseMetadata(metadataInputStream);
    }
    catch (MalformedURLException e) {
      e.printStackTrace();
      throw(e);
    }
    catch (IOException e) {
      e.printStackTrace();
      throw(e);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw(e);
    }

    /*
     * Assert that dataManager.parseMetadata() returned a non-null 
     * dataPackage object.
     */
    assertNotNull("Data package is null", dataPackage);

    /*
     * Compare the number of entities expected in the data package to the
     * number of entities found by the parser.
     */
    if (dataPackage != null) {
      int entityNumberFound = dataPackage.getEntityNumber();
      assertEquals("Number of entites does not match expected value: ",
                   ENTITY_NUMBER_EXPECTED,
                   entityNumberFound);
    } 
  }

  /**
   * Unit test for the DataManager.selectData() method. Tests the creation and
   * use of a Query object for querying a data table. Runs a query and checks
   * the result set against known values in the data table. This test is
   * essentially a complete cradle-to-grave test of Uses Cases 1-4. 
   */
  public void testSelectData() 
          throws Exception {
    AttributeList attributeList;
    Attribute attribute;
    Attribute countAttribute;
    DataPackage dataPackage = null;
    DataPackage[] dataPackages = null;
    String documentURL = QUERY_TEST_SERVER + 
                         "?action=read&qformat=xml&docid=" + 
                         QUERY_TEST_DOCUMENT;
    Entity entity = null;
    InputStream inputStream = null;
    String operator = ">";
    boolean success;
    Integer value = new Integer(2);
    ResultSet resultSet = null;
    URL url;
  
    // First create the DataPackage object that will be used in the query.
    try {
      url = new URL(documentURL);
      inputStream = url.openStream();
      dataPackage = dataManager.parseMetadata(inputStream);
      success = dataManager.loadDataToDB(dataPackage, endPointInfo);
      Entity[] entityList = dataPackage.getEntityList();
      entity = entityList[0];
      attributeList = entity.getAttributeList();
      Attribute[] attributes = attributeList.getAttributes();
      attribute = attributes[0];
      countAttribute = attributes[6];
    }
    catch (MalformedURLException e) {
      e.printStackTrace();
      throw(e);
    }
    catch (IOException e) {
      e.printStackTrace();
      throw(e);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw(e);
    }
 
    /*
     * Now build a test query, execute it, and compare the result set to
     * known values.
     */
    if (success && dataPackage != null) {
      dataPackages = new DataPackage[1];
      dataPackages[0] = dataPackage;
      Query query = new Query();
      /* SELECT clause */
      SelectionItem selectionItem = new SelectionItem(entity, attribute);
      query.addSelectionItem(selectionItem);
      /* FROM clause */
      TableItem tableItem = new TableItem(entity);
      query.addTableItem(tableItem);
      /* WHERE clause with condition */
      Condition condition = new Condition(entity, countAttribute, operator, value);
      WhereClause whereClause = new WhereClause(condition);
      query.setWhereClause(whereClause);
      String sqlString = query.toSQLString();
      System.out.println("Query SQL = " + query.toSQLString());
      assertEquals("Unexpected value for query.toSQLString()",
                   sqlString,
                   "SELECT INS_GCEM_0011_1_3_TXT.Site " +
                   "FROM INS_GCEM_0011_1_3_TXT  " +
                   "where INS_GCEM_0011_1_3_TXT.Count > 2;");

      try {
        resultSet = dataManager.selectData(query, dataPackages);
        
        if (resultSet != null) {
 
          int i = 1;

          while (resultSet.next()) {
            int site = resultSet.getInt(1);
            System.out.println("resultSet[" + i + "], site =  " + site);
            
            /*
             * Compare values in the result set to known values in the 
             * data table.
             */
            switch (i) {
              case 1:
                assertEquals(site, 1);
                break;
              case 2:
                assertEquals(site, 3);
                break;
              default:
                assertEquals(site, 6);
                break;
            }
            i++;
          }
        }
        else {
          throw new Exception("resultSet is null");
        }
      } 
      catch (Exception e) {
        System.err.println("Exception in DataManager.selectData()"
                           + e.getMessage());
        throw (e);
      } 
      finally {
        if (resultSet != null) resultSet.close();
        dataManager.dropTables(dataPackage);  // Clean-up test tables
      }
    } 
  }
  
}
