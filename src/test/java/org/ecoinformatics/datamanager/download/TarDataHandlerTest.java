package org.ecoinformatics.datamanager.download;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test suite for the TarDataHandler class.
 * 
 * @author tao
 *
 */
public class TarDataHandlerTest extends TestCase
{
	private EcogridEndPointInterfaceTest endPointInfo = 
                                             new EcogridEndPointInterfaceTest();
    
	/**
	 * Constructor 
	 * @param name The name of testing
	 */
	  public TarDataHandlerTest (String name)
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
//         suite.addTest(new TarDataHandlerTest("testDownloadFailed"));
//         suite.addTest(new TarDataHandlerTest("testDownloadSuccess"));   
//         suite.addTest(new TarDataHandlerTest("testEcogridDownloadFailed"));
//         suite.addTest(new TarDataHandlerTest("testEcogridDownloadSuccess"));
//         suite.addTest(new TarDataHandlerTest("testCorrectURLByDowload"));
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
	   * Test a succssful download process through http protocol.
	   */
	  public void testDownloadSuccess()
	  {
		  String url = "http://pacific.msi.ucsb.edu:8080/knb/metacat?" +
                       "action=read&qformat=xml&docid=tao.12100.1";
		  //String identifier = "tao.7.1";
		  testDownloadByThread(true, url, url);
	  }
	  
      
	  /**
	   * Test a failed download process (without storage interface) 
       * through http protocol.
	   */
	  public void testDownloadFailed()
	  {
		  String url = "http://pacific.msi.ucsb.edu:8080/knb/metacat?" + 
                       "action=read&qformat=xml&docid=tao.12100.1";
		  //String identifier = "tao.7.1";
		  testDownloadByThread(false, url, url);
	  }
	  
      
	  /**
	   * Test a succssful download process through ecogrid protocol.
	   */
	  public void testEcogridDownloadFailed()
	  {
		  String url = "ecogrid://knb/tao.12100.1";
		  //String identifier = "tao.8.1";
		  testDownloadByThread(false, url, url);
	  }
	  
      
	  /**
	   * Test a failed download process (without storage interface) 
       * through ecogrid protocol.
	   */
	  public void testEcogridDownloadSuccess()
	  {
		  String url = "ecogrid://knb/tao.12100.1";
		  //String identifier = "tao.8.1";
		  testDownloadByThread(true, url, url);
	  }
	  
	  
	  /*
	   * Test download method.
	   */
	  private void testDownloadByThread(boolean success, 
                                        String url, 
                                        String identifier)
	  {
		  TarDataHandler handler = 
                        TarDataHandler.getTarHandlerInstance(null, url, endPointInfo);
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
	  
      
	  /**
	   * Tests download method by a correct url.
       * 
	   * @throws Exception
	   */
	  public void testCorrectURLByDowload() throws Exception
	  {
		  String url  = "http://pacific.msi.ucsb.edu:8080/knb/metacat?" + 
                        "action=read&qformat=xml&docid=tao.12100.1";
		  testDownloadMethod(true, url);
	  }
	  
      
	  /*
	  * This method will test download method in DownloadHandler.
	   */
	  private void testDownloadMethod(boolean success, String url) 
              throws Exception
	  {
		  TarDataHandler handler = 
                        TarDataHandler.getTarHandlerInstance(null, url, endPointInfo);
		  DataStorageTest dataStorage = new DataStorageTest();
		  DataStorageTest[] list = new DataStorageTest[1];
		  list[0] = dataStorage;
		  boolean result = handler.download(list);
		  assertTrue(handler.isBusy() == false);
		  assertTrue(result == success);
		  assertTrue(dataStorage.doesDataExist(url) == success);
		 
	  }
	  
}
