package org.ecoinformatics.datamanager.database;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.download.DownloadHandler;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
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
	 private Entity entity = null;
	 private String documentURL = "http://knb.ecoinformatics.org/knb/metacat?action=read&qformat=xml&docid=tao.1.1";
	/**
	 * Constructor 
	 * @param name The name of testing
	 */
	  public DatabaseLoaderTest (String name) throws Exception
	  {
	    super(name);
	    DataManager dataManager = DataManager.getInstance();
	    DataPackage dataPackage = null;
	    InputStream metadataInputStream;
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
	    assertNotNull("Data package is null", dataPackage);

	  
	    if (dataPackage != null) {
	      Entity[] entities = dataPackage.getEntityList();
	      entity = entities[0];
	      //boolean success = databaseHandler.generateTable(entity);
	    }
	  }
      
	  /**
	   * Establish a testing framework by initializing appropriate objects.
	   */
	  protected void setUp() throws Exception
	  {
	    super.setUp();
	    
	  }
      
	  /**
	   * Release any objects and closes database connections after tests 
	   * are complete.
	   */
	  protected void tearDown() throws Exception
	  {
	    
	    super.tearDown();
	  }
	  
	  /**
	   * Tests the DatabaseHandler.doesDataExist() method. Does so by creating a 
	   * test table. First drops the table in case it was already
	   * present. Then creates the table, calls isTableInDB(), and asserts that
	   * the table exists. Then drops the table again, ca
	   * lls isTableInDB(), and
	   * asserts that the table does not exist.
	   * 
	   * @throws SQLException
	   */
	  /*public void testDoesDataExist()
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
	    assertNotNull("Data package is null", dataPackage);

	  
	    if (dataPackage != null) {
	      Entity[] entities = dataPackage.getEntityList();
	      Entity entity = entities[0];
	      boolean success = databaseHandler.generateTable(entity);
	      assertTrue("DatabaseHandler did not succeed in generating table", success);
	      String identifier = entity.getEntityIdentifier();
	      boolean isPresent = databaseHandler.doesDataExist(identifier);
	      assertTrue("Could not find table for identifier " + identifier
	          + " but it should be in db", isPresent);
	      databaseHandler.dropTable(entity);
	      isPresent = databaseHandler.doesDataExist(identifier);
	      assertFalse("Found table for identifier " + identifier +
	                  " but it should NOT be in db", isPresent);
	      }
	  }*/
	  
	  /**
	   * Test startSerialize and finishSerialize methods.
	   * A downloadHanlder will be used to test the methods
	   *
	   */
	  public void testStartAndFinishSerializeAndDoesDataExist() throws Exception
	  {
		  
		  String identifier = entity.getEntityIdentifier();
		  System.out.println("The identifier is ======================= "+identifier);
		  DownloadHandler handler = DownloadHandler.getInstance(identifier);
		  //System.out.println("here1");
		  Connection dbConnection = DataManager.getConnection();
		  String     dbAdaptor    = DatabaseAdapter.POSTGRES_ADAPTER;
		  DatabaseHandler databaseHandler = new DatabaseHandler(dbConnection, dbAdaptor);
		  boolean success = databaseHandler.generateTable(entity);
	      assertTrue("DatabaseHandler did not succeed in generating table", success);
		  DatabaseLoader dataStorage = new DatabaseLoader(dbConnection, dbAdaptor, entity);
		  boolean isPresent = dataStorage.doesDataExist(identifier);
		  assertTrue("Could not find table for identifier " + identifier
		          + " but it should be in db", isPresent);
		  DatabaseLoader[] list = new DatabaseLoader[1];
		  list[0] = dataStorage;
		  handler.setDataStorageCladdList(list);
		 
		  //System.out.println("here2");
		  assertTrue(handler.isBusy() == false);
		  assertTrue(handler.isSuccess() == false);
		  Thread downloadThread = new Thread(handler);
		  System.out.println("here3");
		  downloadThread.start();
		  System.out.println("here4");
		  while(!handler.isCompleted())
		  {
			 
		  }
          //Thread.sleep(50000);
		 
		  //assertTrue(dataStorage.doesDataExist(identifier) == true);
		  assertTrue(handler.isSuccess() == true);
		  assertTrue(handler.isBusy() == false);
		  databaseHandler.dropTable(entity);
	      isPresent = dataStorage.doesDataExist(identifier);
	      assertFalse("Found table for identifier " + identifier +
	                  " but it should NOT be in db", isPresent);
	  }
	  
	 
          
      
	  

	  /**
	   * Create a suite of tests to be run together
	   */
	   public static Test suite()
	   {
	     TestSuite suite = new TestSuite();
	     try
	     {
	        suite.addTest(new DatabaseLoaderTest("testStartAndFinishSerializeAndDoesDataExist"));
	     }
	     catch(Exception e)
	     {
	    	 System.err.println("the Erorr in test is "+e.getMessage());
	     }
	     return suite;
	   }
}
