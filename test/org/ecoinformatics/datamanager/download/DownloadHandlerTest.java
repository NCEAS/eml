package org.ecoinformatics.datamanager.download;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test class for the DownloadHandler class.
 * 
 * @author tao
 *
 */
public class DownloadHandlerTest extends TestCase
{
    /*
     * Class fields
     */
  
	private static final String CORRECTURL   = "http://knb.ecoinformatics.org/knb/metacat/knb-lter-bes.14.3";
	private static final String INCORRECTURL = "http://knb.ecoinformatcs.org/knb/metacat/knb-lter-bes.14.3";
	private static final String CORRECTURL1   = "http://knb.ecoinformatics.org/knb/metacat/knb-lter-arc.1424.1";
  private static final long CORRECTURL_SIZE = 3906; // Expected file size of knb-lter-bes.14.3
  private static final long CORRECTURL1_SIZE = 8172; // Expected file size of knb-lter-arc.1424.1
    
    /*
     * Instance fields
     */    
    private EcogridEndPointInterfaceTest endPointInfo = new EcogridEndPointInterfaceTest();

    
    /**
	 * Constructor 
	 * @param name The name of testing
	 */
	  public DownloadHandlerTest (String name)
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
         suite.addTest(new DownloadHandlerTest("initialize"));
         suite.addTest(new DownloadHandlerTest("testDownloadFailed"));
         suite.addTest(new DownloadHandlerTest("testDownloadSuccess"));  
         suite.addTest(new DownloadHandlerTest("testEcogridDownloadFailed"));
         suite.addTest(new DownloadHandlerTest("testEcogridDownloadSuccess"));
         suite.addTest(new DownloadHandlerTest("tesDownloadHandlerWithSameUrl"));
         suite.addTest(new DownloadHandlerTest("testDownloadFromIncorrectURL"));
         suite.addTest(new DownloadHandlerTest("testCorrectURLByDownload"));
         suite.addTest(new DownloadHandlerTest("testInCorrectURLByDownload"));
         suite.addTest(new DownloadHandlerTest("testCorrectEcogridURLByDownload"));
         //suite.addTest(new DownloadHandlerTest("testInCorrectEcogridURLByDownload"));
         suite.addTest(new DownloadHandlerTest("testSameURLByDownload"));
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
       * Run an initial test that always passes to check that the test harness
       * is working.
       */
      public void initialize() {
        assertTrue(1 == 1);
      }
        
        
	  /**
	   * Tests downloading from http protocol with successful result.
	   *
	   */
	  public void testDownloadSuccess()
	  {
		  testDownloadByThread(true, CORRECTURL, CORRECTURL, true);
	  }
	  
      
	  /**
	   * Tests downloading from http protocol with failed result
       * (no StorageInterface).
	   * 
	   */
	  public void testDownloadFailed()
	  {
		  String url = "http://knb.ecoinformatics.org/knb/metacat/tao.1.1";
		  testDownloadByThread(false, url, url, false);
	  }
	  
      
	  /**
	   * Tests downloading from http protocol with failed result
     * (from incorrect url).
	   * 
	   */
	  public void testDownloadFromIncorrectURL()
	  {
		  testDownloadByThread(false, INCORRECTURL, INCORRECTURL, true);
	  }
      
      
	  /**
	   * Tests downloading from ecogrid protocol with failed result.
	   * 
	   */
	  public void testEcogridDownloadFailed()
	  {
		  String url = "ecogrid://knb/tao.2.1";
		  //String identifier = "tao.2.1";
		  testDownloadByThread(false, url, url, false);
	  }
	  
      
	  /**
	   * Tests downloading from ecogrid protocol with successful result.
	   * 
	   */
	  public void testEcogridDownloadSuccess()
	  {
		  String url = "ecogrid://knb/tao.2.1";
		  testDownloadByThread(true, url, url, true);
	  }
	  
	  
	  /*
	   * Tests download process by creating a thread which initalizes from 
       * download handler.
	   */
	  private void testDownloadByThread(boolean success, 
                                        String url, 
                                        String identifier, 
                                        boolean hasDataStorage)
	  {	  
		  DownloadHandler handler = 
                                 DownloadHandler.getInstance(url, endPointInfo);
		  //System.out.println("here1");
		  DataStorageTest dataStorage = new DataStorageTest();
          
		  if (hasDataStorage)
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
			    long fileSize = dataStorage.getEntitySize(identifier);
				  assertTrue(fileSize == CORRECTURL_SIZE);
	        System.err.println("expected: " + CORRECTURL_SIZE + "; found: " + fileSize);
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
	   * Test two DownloadHandler with same url.
	   *
	   */
	  public void tesDownloadHandlerWithSameUrl()
	  {
		  String url = "http://knb.ecoinformatics.org/knb/metacat/adler.5.1";
		  processDownloadHandlersWithSameUrl(url);
	  }
	  
	  
	  /*
	   * Process two downloadhandler with same url.
	   */
	  private void processDownloadHandlersWithSameUrl(String url)
	  {
		  
		  DownloadHandler handler = 
                                 DownloadHandler.getInstance(url, endPointInfo);
		  //System.out.println("here1");
		  DataStorageTest dataStorage = new DataStorageTest();
     	  DataStorageTest[] list = new DataStorageTest[1];
		  list[0] = dataStorage;
		  handler.setDataStorageClassList(list);
		  
		  //System.out.println("here2");
		  assertTrue(handler.isBusy() == false);
		  assertTrue(handler.isSuccess() == false);
		  Thread downloadThread = new Thread(handler);
		  //System.out.println("here3");
		  downloadThread.start();
		  // start the second handler
		  DownloadHandler handler2 = 
                                 DownloadHandler.getInstance(url, endPointInfo);
		  handler2.setDataStorageClassList(list);
		  Thread downloadThread2 = new Thread(handler2);
		  downloadThread2.start();
		  //assertTrue(handler == handler2);
		  //System.out.println("the handler is "+handler);
		  //System.out.println("the handler2 is "+handler2);
		  //System.out.println("here4");
          
		  while(!handler.isCompleted())
		  {
			 
		  }
          
		  while(!handler2.isCompleted())
		  {
			 
		  }
          
		  //System.out.println("the handler is ===="+handler);
		  //System.out.println("the handler2 is ===="+handler2);
		  //assertTrue(handler.isSuccess() == true);
		  assertTrue(dataStorage.doesDataExist(url) == true);
	      assertTrue(handler.isSuccess() == true);
		  assertTrue(handler.isBusy() == false);
		  assertTrue(handler2.isSuccess() == true);
		  assertTrue(handler2.isBusy() == false);
	  }
	  
      
	  /**
	   * Test download method by a correct url.
       * 
	   * @throws Exception
	   */
	  public void testCorrectURLByDownload() throws Exception
	  {
		  String url = CORRECTURL1;
		  testDownloadMethod(true, url);
	  }
	  
      
	  /**
	   * Tests download method with an incorrect url.
       * 
	   * @throws Exception
	   */
	  public void testInCorrectURLByDownload() throws Exception
	  {
		  String url = INCORRECTURL;
		  testDownloadMethod(false, url);
	  }
	  
      
	  /**
	   * Test download method by a correct ecogrid url.
       * 
	   * @throws Exception
	   */
	  public void testCorrectEcogridURLByDownload() throws Exception
	  {
		  String url = "ecogrid://knb/tao.1.1";
		  testDownloadMethod(true, url);
	  }
	  
      
	  /**
	   * Tests download method by a incorrect ecogrid url.
       * 
	   * @throws Exception
	   */
	  public void testInCorrectEcogridURLByDownload() throws Exception
	  {
		  String url = "ecogrid://knb/tao.0.1";
		  testDownloadMethod(false, url);
	  }
	  
      
	  /**
	   * Tests download method by calling download same url.
       * 
	   * @throws Exception
	   */
	  public void testSameURLByDownload() throws Exception
	  {
	    final long EXPECTED_SIZE = 5524181;
		  final String url = "http://knb.ecoinformatics.org/knb/metacat/" +
                         "ALEXXX_015ADCP015R00_19990817.40.1";
		  DataStorageTest dataStorage = new DataStorageTest();
		  DataStorageTest[] list = new DataStorageTest[1];
		  list[0] = dataStorage;
		  DownloadHandler handler = DownloadHandler.getInstance(url, endPointInfo);
		  DownloadHandler handler1 = DownloadHandler.getInstance(url, endPointInfo);
		  boolean result1 = handler.download(list);
		  boolean result2 = handler1.download(list);
		  assertTrue(result1 == true);
		  assertTrue(result2 == true);
		  assertTrue(dataStorage.doesDataExist(url) == true);
		  long fileSize = dataStorage.getEntitySize(url);
      System.err.println("expected: " + EXPECTED_SIZE + "; found: " + fileSize);
		  assertTrue(fileSize == EXPECTED_SIZE);
	  }
	  
      
	  /*
	   * This method will test download method in DownloadHandler.
	   */
	  private void testDownloadMethod(boolean success, String url) 
               throws Exception
	  {
		  DownloadHandler handler = 
                                 DownloadHandler.getInstance(url, endPointInfo);
		  DataStorageTest dataStorage = new DataStorageTest();
		  DataStorageTest[] list = new DataStorageTest[1];
		  list[0] = dataStorage;
		  boolean result = false;
          
		  try
		  {
	        result = handler.download(list);
		  }
		  catch (Exception e)
		  {
			  if (url == INCORRECTURL)
			  {
		        assertTrue("The exception should be an instance of " +
                           "DataSourceNotFoundException", 
						   e instanceof DataSourceNotFoundException);
			  }
		  }
          
		  if (url == CORRECTURL1)
		  {
		    long fileSize = dataStorage.getEntitySize(url);
		    System.err.println("expected: " + CORRECTURL1_SIZE + "; found: " + fileSize);
			  assertTrue(fileSize == CORRECTURL1_SIZE);
		  }
          
		  assertTrue(handler.isBusy() == false);
		  assertTrue(result == success);
		  assertTrue(dataStorage.doesDataExist(url) == success);
	  }
	  
}
