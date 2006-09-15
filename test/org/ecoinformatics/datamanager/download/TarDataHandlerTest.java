package org.ecoinformatics.datamanager.download;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TarDataHandlerTest extends TestCase
{
	  public TarDataHandlerTest (String name)
	  {
	    super(name);
	  }

	  protected void setUp() throws Exception
	  {
	    super.setUp();
	    
	  }

	  protected void tearDown() throws Exception
	  {
	    
	    super.tearDown();
	  }
	  
	  /**
	   * Test a download success
	   * @param success
	   */
	  public void testDownloadSuccess()
	  {
		  String url = "http://pacific.msi.ucsb.edu:8080/knb/metacat?action=read&qformat=xml&docid=tao.12100.1";
		  //String identifier = "tao.7.1";
		  testDownload(true, url, url);
	  }
	  
	  /**
	   * Test a download success
	   * @param success
	   */
	  public void testDownloadFailed()
	  {
		  String url = "http://pacific.msi.ucsb.edu:8080/knb/metacat?action=read&qformat=xml&docid=tao.12100.1";
		  //String identifier = "tao.7.1";
		  testDownload(false, url, url);
	  }
	  /**
	   * Test a download success
	   * @param success
	   */
	  public void testEcogridDownloadFailed()
	  {
		  String url = "ecogrid://knb/tao.12100.1";
		  //String identifier = "tao.8.1";
		  testDownload(false, url, url);
	  }
	  
	  /**
	   * Test a download success
	   * @param success
	   */
	  public void testEcogridDownloadSuccess()
	  {
		  String url = "ecogrid://knb/tao.12100.1";
		  //String identifier = "tao.8.1";
		  testDownload(true, url, url);
	  }
	  
	  
	  
	  /*
	   * Test download method
	   */
	  private void testDownload(boolean success, String url, String identifier)
	  {
		  
		  TarDataHandler handler = new TarDataHandler(url);
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
			  
		  }
		  else
		  {
			  //assertTrue(dataStorage.doesDataExist(identifier) == false);
			  assertTrue(handler.isSuccess() == false);
			  
		  }
		  assertTrue(handler.isBusy() == false);
	  }
	  
	  /**
	   * Create a suite of tests to be run together
	   */
	   public static Test suite()
	   {
	     TestSuite suite = new TestSuite();
	     suite.addTest(new TarDataHandlerTest("testDownloadFailed"));
	     suite.addTest(new TarDataHandlerTest("testDownloadSuccess"));	 
	     suite.addTest(new TarDataHandlerTest("testEcogridDownloadFailed"));
	     suite.addTest(new TarDataHandlerTest("testEcogridDownloadSuccess"));
	     return suite;
	   }
}
