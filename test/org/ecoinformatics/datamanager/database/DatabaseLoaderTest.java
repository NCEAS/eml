package org.ecoinformatics.datamanager.database;
import java.sql.Connection;

import org.ecoinformatics.datamanager.download.DownloadHandler;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
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
	/**
	 * Constructor 
	 * @param name The name of testing
	 */
	  public DatabaseLoaderTest (String name)
	  {
	    super(name);
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
	   * Test startSerialize and finishSerialize methods.
	   * A downloadHanlder will be used to test the methods
	   *
	   */
	  public void testStartAndFinishSerialize() throws Exception
	  {
		  String identifier = "http://knb.ecoinformatics.org/knb/metacat?action=read&qformat=xml&docid=tao.1.1";
		  DownloadHandler handler = DownloadHandler.getInstance(identifier);
		  //System.out.println("here1");
		  Connection dbConnection = null;
		  String     dbAdaptor    = DatabaseAdapter.POSTGRES_ADAPTER;
		  Entity     entity       = null;
		  DatabaseLoader dataStorage = new DatabaseLoader(dbConnection, dbAdaptor, entity);
		  DatabaseLoader[] list = new DatabaseLoader[1];
		  list[0] = dataStorage;
		  handler.setDataStorageCladdList(list);
		 
		  //System.out.println("here2");
		  assertTrue(handler.isBusy() == false);
		  assertTrue(handler.isSuccess() == false);
		  Thread downloadThread = new Thread(handler);
		  //System.out.println("here3");
		  downloadThread.start();
		  //System.out.println("here4");
		  while(!handler.isCompleted())
		  {
			 
		  }
          //Thread.sleep(50000);
	
		  //assertTrue(dataStorage.doesDataExist(identifier) == true);
		  assertTrue(handler.isSuccess() == true);
		  assertTrue(handler.isBusy() == false);
	  }
	  
	 
          
      
	  

	  /**
	   * Create a suite of tests to be run together
	   */
	   public static Test suite()
	   {
	     TestSuite suite = new TestSuite();
	     //suite.addTest(new DatabaseLoaderTest("testStartAndFinishSerialize"));
	     return suite;
	   }
}

