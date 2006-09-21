package org.ecoinformatics.datamanager.download;

import org.ecoinformatics.datamanager.parser.UniqueKey;
import org.ecoinformatics.datamanager.parser.UniqueKeyTest;

import sun.security.action.GetIntegerAction;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DownloadHandlerTest extends TestCase
{
	private static final String CORRECTURL   = "http://knb.ecoinformatics.org/knb/metacat?action=read&qformat=xml&docid=tao.1.1";
	private static final String INCORRECTURL = "http://knb.ecoinformacs.org/knb/metacat?action=read&qformat=xml&docid=tao.1.1";
	/**
	 * Constructor 
	 * @param name The name of testing
	 */
	  public DownloadHandlerTest (String name)
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
	   * Test a downloading from http protocol with successful result
	   *
	   */
	  public void testDownloadSuccess()
	  {
		  //String url = "http://knb.ecoinformatics.org/knb/metacat?action=read&qformat=xml&docid=tao.1.1";
		  //String identifier = "tao.1.1";
		  testDownload(true, CORRECTURL, CORRECTURL);
	  }
	  
	  /**
	   * Test a downloading from http protocol with failed result(no StorageInterface)
	   * 
	   */
	  public void testDownloadFailed()
	  {
		  String url = "http://knb.ecoinformatics.org/knb/metacat?action=read&qformat=xml&docid=tao.1.1";
		  //String identifier = "tao.1.1";
		  testDownload(false, url, url);
	  }
	  
	  /**
	   * Test a downloading from http protocol with failed result(from incorrect url)
	   * 
	   */
	  public void testDownloadFromIncorrectURL()
	  {
		  //String url = "http://knb.ecoinformatics.org/knb/metacat?action=read&qformat=xml&docid=tao.1.1";
		  //String identifier = "tao.1.1";
		  testDownload(false, INCORRECTURL, INCORRECTURL);
	  }
	  /**
	   * Test a downloading from ecogrid protocol with failed result
	   * 
	   */
	  public void testEcogridDownloadFailed()
	  {
		  String url = "ecogrid://knb/tao.2.1";
		  //String identifier = "tao.2.1";
		  testDownload(false, url, url);
	  }
	  
	  /**
	   * Test a downloading from ecogrid protocol with successful result
	   * 
	   */
	  public void testEcogridDownloadSuccess()
	  {
		  String url = "ecogrid://knb/tao.2.1";
		  testDownload(true, url, url);
	  }
	  
	  
	  
	  /*
	   * Test download method
	   */
	  private void testDownload(boolean success, String url, String identifier)
	  {
		  
		  DownloadHandler handler = DownloadHandler.getInstance(url);
		  //System.out.println("here1");
		  DataStorageTest dataStorage = new DataStorageTest();
		  if (success)
		  {
			  DataStorageTest[] list = new DataStorageTest[1];
			  list[0] = dataStorage;
			  handler.setDataStorageCladdList(list);
		  }
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
		  //assertTrue(handler.isSuccess() == true);
		  if (success)
		  {
			  assertTrue(dataStorage.doesDataExist(identifier) == true);
			  assertTrue(handler.isSuccess() == true);
			  if (identifier == CORRECTURL)
			  {
				  assertTrue(dataStorage.getEntitySize(identifier)== 7237);
			  }
			  
		  }
		  else
		  {
			  //assertTrue(dataStorage.doesDataExist(identifier) == false);
			  assertTrue(handler.isSuccess() == false);
			  
		  }
		  assertTrue(handler.isBusy() == false);
	  }
	  
	  /**
	   * Test two DownloadHandler with same url
	   *
	   */
	  public void tesDownloadHandlerWithSameUrl()
	  {
		  String url = "http://knb.ecoinformatics.org/knb/metacat?action=read&qformat=xml&docid=adler.5.1";
		  processDownloadHandlersWithSameUrl(url);
	  }
	  
	  
	  /*
	   * Process two downloadhandler with same url
	   */
	  private void processDownloadHandlersWithSameUrl(String url)
	  {
		  
		  DownloadHandler handler = DownloadHandler.getInstance(url);
		  //System.out.println("here1");
		  DataStorageTest dataStorage = new DataStorageTest();
     	  DataStorageTest[] list = new DataStorageTest[1];
		  list[0] = dataStorage;
		  handler.setDataStorageCladdList(list);
		  
		  //System.out.println("here2");
		  assertTrue(handler.isBusy() == false);
		  assertTrue(handler.isSuccess() == false);
		  Thread downloadThread = new Thread(handler);
		  //System.out.println("here3");
		  downloadThread.start();
		  // start the second handler
		  DownloadHandler handler2 = DownloadHandler.getInstance(url);
		  handler2.setDataStorageCladdList(list);
		  Thread downloadThread2 = new Thread(handler2);
		  downloadThread2.start();
		  //assertTrue(handler == handler2);
		  System.out.println("the handler is "+handler);
		  System.out.println("the handler2 is "+handler2);
		  //System.out.println("here4");
		  while(!handler.isCompleted())
		  {
			 
		  }
		  while(!handler2.isCompleted())
		  {
			 
		  }
		  System.out.println("the handler is ===="+handler);
		  System.out.println("the handler2 is ===="+handler2);
		  //assertTrue(handler.isSuccess() == true);
		  assertTrue(dataStorage.doesDataExist(url) == true);
	      assertTrue(handler.isSuccess() == true);
		  assertTrue(handler.isBusy() == false);
		  assertTrue(handler2.isSuccess() == true);
		  assertTrue(handler2.isBusy() == false);
	  }
	  /**
	   * Create a suite of tests to be run together
	   */
	   public static Test suite()
	   {
	     TestSuite suite = new TestSuite();
	     suite.addTest(new DownloadHandlerTest("testDownloadFailed"));
	     suite.addTest(new DownloadHandlerTest("testDownloadSuccess"));	 
	     suite.addTest(new DownloadHandlerTest("testEcogridDownloadFailed"));
	     suite.addTest(new DownloadHandlerTest("testEcogridDownloadSuccess"));
	     suite.addTest(new DownloadHandlerTest("tesDownloadHandlerWithSameUrl"));
	     suite.addTest(new DownloadHandlerTest("testDownloadFromIncorrectURL"));
	     return suite;
	   }
}
