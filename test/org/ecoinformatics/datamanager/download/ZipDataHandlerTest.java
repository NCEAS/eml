package org.ecoinformatics.datamanager.download;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ZipDataHandlerTest extends TestCase
{
    /*
     * Instance fields
     */
	private EcogridEndPointInterfaceTest endPointInfo = 
                                             new EcogridEndPointInterfaceTest();
    
	/**
	 * Constructor 
	 * @param name The name of testing
	 */
	  public ZipDataHandlerTest (String name)
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
         suite.addTest(new ZipDataHandlerTest("testDownloadFailed"));
         suite.addTest(new ZipDataHandlerTest("testDownloadSuccess"));   
         suite.addTest(new ZipDataHandlerTest("testEcogridDownloadFailed"));
         suite.addTest(new ZipDataHandlerTest("testEcogridDownloadSuccess"));
         return suite;
       }
      
       
      /*
       * Instance methods
       */
      
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
	   * Test a successful download process through http protocol
	   */
	  public void testDownloadSuccess()
	  {
		  String url = "http://pacific.msi.ucsb.edu:8080/knb/metacat?" + 
                       "action=read&qformat=xml&docid=tao.12096.1";
		  //String identifier = "tao.3.1";
		  testDownload(true, url, url);
	  }
	  
      
	  /**
	   * Test a failed download process through http protocol.
	   */
	  public void testDownloadFailed()
	  {
		  String url = "http://pacific.msi.ucsb.edu:8080/knb/metacat?" + 
                       "action=read&qformat=xml&docid=tao.12096.1";
		  //String identifier = "tao.3.1";
		  testDownload(false, url, url);
	  }
	  
      
	  /**
	   * Test a failed download process through ecogrid protocol.
	   */
	  public void testEcogridDownloadFailed()
	  {
		  String url = "ecogrid://knb/tao.12096.1";
		  //String identifier = "tao.4.1";
		  testDownload(false, url, url);
	  }
	  
      
	  /**
	   * Test a successful download process through ecogrid protocol.
	   */
	  public void testEcogridDownloadSuccess()
	  {
		  String url = "ecogrid://knb/tao.12096.1";
		  //String identifier = "tao.4.1";
		  testDownload(true, url, url);
	  }
	  
	  
	  /*
	   * Test download method.
	   */
	  private void testDownload(boolean success, String url, String identifier)
	  {
		  ZipDataHandler handler = 
                        ZipDataHandler.getZipHandlerInstance(url, endPointInfo);
		  //System.out.println("here1");
		  DataStorageTest dataStorage = new DataStorageTest();
          
		  if (success)
		  {
			  DataStorageTest[] list = new DataStorageTest[1];
			  list[0] = dataStorage;
			  handler.setDataStorageClassList(list);
		  }
          
		  //System.out.println("here2");
		  assertTrue(handler.isBusy() == false);
		  assertTrue(handler.isSuccess() == false);
		  Thread downloadThread = new Thread(handler);
		  //System.out.println("here3");
      downloadThread.start();
      
      while(!handler.isCompleted())
      {
        System.err.println("Waiting for DownloadHandler to complete.");
        try {
         Thread.sleep(1000);
        }
        catch (InterruptedException e) {
          System.err.println(e.getMessage());
        }
      }
      System.err.println("DownloadHandler finished.");
          
		  //assertTrue(handler.isSuccess() == true);
          
		  if (success)
		  {
			  assertTrue(dataStorage.doesDataExist(identifier) == true);
			  assertTrue(handler.isSuccess() == true);
			  
		  }
		  else
		  {
			  //assertTrue(dataStorage.doesDataExist(identifier) == false);
			  assertTrue(handler.isSuccess() == false);
			  
		  }
          
		  assertTrue(handler.isBusy() == false);
	  }
	  
}