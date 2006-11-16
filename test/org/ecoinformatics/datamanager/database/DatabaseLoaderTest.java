package org.ecoinformatics.datamanager.database;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.download.DownloadHandler;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterfaceTest;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test class for DatabaseLoader which will store remote source
 * into database
 * @author Jing Tao
 *
 */
public class DatabaseLoaderTest extends TestCase
{
     /*
      * Instance fields
      */
     private DatabaseConnectionPoolInterfaceTest connectionPool = null;
     private DataManager dataManager = null;
	 private String documentURL = 
                              "http://pacific.msi.ucsb.edu:8080/knb/metacat?" + 
                              "action=read&qformat=xml&docid=tao.12103.2";
	 private String dbAdaptor = null;
	 private EcogridEndPointInterfaceTest endPointInfo = 
                                             new EcogridEndPointInterfaceTest();
     private Entity entity = null;
     
     
     /*
      * Constructors
      */
     
     /**
     * Constructor 
     * 
     * @param name The name of the test suite
     */
      public DatabaseLoaderTest (String name) throws Exception
      {
        super(name);
      }

      
     /*
      * Class methods
      */
     
      /**
       * Create a suite of tests to be run together
       */
       public static Test suite()
       {
         TestSuite suite = new TestSuite();
         
         try
         {
            suite.addTest(new DatabaseLoaderTest("initialize"));
            suite.addTest(new DatabaseLoaderTest("testDoesDataExist"));
         }
         catch(Exception e)
         {
             System.err.println("The error in test is: " + e.getMessage());
         }
         return suite;
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
	  protected void setUp() throws Exception
	  {
	    super.setUp();	    

        connectionPool = new DatabaseConnectionPoolInterfaceTest();
        dbAdaptor = connectionPool.getDBAdapterName();
        dataManager = DataManager.getInstance(connectionPool, dbAdaptor);
        DataPackage dataPackage = null;
        InputStream metadataInputStream;
        URL url;

        /*
         * Initialize the entity field by parsing the test data package and
         * using the first entity it contains.
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
        
        assertNotNull("Data package is null", dataPackage);
      
        if (dataPackage != null) {
          Entity[] entities = dataPackage.getEntityList();
          entity = entities[0];
        }
	  }

      
	  /**
	   * Release any objects and closes database connections after tests 
	   * are complete.
	   */
	  protected void tearDown() throws Exception
	  {	    
	    super.tearDown();
        
        connectionPool = null;
        dataManager = null;
        dbAdaptor = null;
        documentURL = null;
        endPointInfo = null;
        entity = null;
	  }
	  
      
	  /**
	   * Test startSerialize and finishSerialize methods.
	   * A downloadHanlder will be used to test the methods.
	   *
	   */
	  public void testDoesDataExist() throws Exception
	  {
          Connection connection, dbConnection;
		  String identifier = entity.getEntityIdentifier();
		  //System.out.println("The identifier is: "+identifier);
		  DownloadHandler handler = 
                          DownloadHandler.getInstance(identifier, endPointInfo);
		  dbConnection = connectionPool.getConnection();
		  DatabaseHandler databaseHandler = new DatabaseHandler(dbAdaptor);
		  boolean success = databaseHandler.generateTable(entity);
	      assertTrue("DatabaseHandler did not succeed in generating table", 
                     success);
		  DatabaseLoader dataStorage = new DatabaseLoader(dbAdaptor, entity);
		  boolean isPresent = dataStorage.doesDataExist(identifier);
          assertFalse("Table for identifier " + identifier + " is empty, " + 
                      "but doesDataExist() returned true", 
                      isPresent);
		  DatabaseLoader[] list = new DatabaseLoader[1];
		  list[0] = dataStorage;
		  handler.setDataStorageClassList(list);
		 
		  assertTrue(handler.isBusy() == false);
		  assertTrue(handler.isSuccess() == false);
		  Thread downloadThread = new Thread(handler);
		  downloadThread.start();
          
		  while(!handler.isCompleted())
		  {
			 
		  }
          //Thread.sleep(50000);

          /*
           * Check that the data table does exist, and that one of the values
           * in the table matches the expected value.
           */
		  assertTrue(dataStorage.doesDataExist(identifier) == true);
		  assertTrue(handler.isSuccess() == true);
		  assertTrue(handler.isBusy() == false);
		  String sql = "select column_1 from head_linedata where column_2=2;";
          connectionPool.returnConnection(dbConnection);
		  connection = connectionPool.getConnection();
		  Statement statement = connection.createStatement();
		  ResultSet result = statement.executeQuery(sql);
		  boolean next = result.next();
		  float col1 = 0;
          
		  if (next)
		  {
		     col1 = result.getFloat(1);
		  }
          
		  result.close();
		  statement.close();
		  assertTrue("Unexpected value found in table", col1==1);
          
          // Clean up
		  databaseHandler.dropTable(entity);
	      isPresent = dataStorage.doesDataExist(identifier);
	      assertFalse("Found table for identifier " + identifier +
	                  " but it should NOT be in db", isPresent);
          connectionPool.returnConnection(connection);
	  }

}
