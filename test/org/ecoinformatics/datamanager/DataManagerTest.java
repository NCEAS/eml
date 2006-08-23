package org.ecoinformatics.datamanager;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ecoinformatics.datamanager.parser.DataPackage;

public class DataManagerTest extends TestCase {
  
  /*
   * Class fields
   */
  
  
  /*
   * Instance fields
   */
  
  private DataManager dataManager;
  private final String TEST_DOCUMENT = "knb-lter-mcm.7002.1";
  private final String TEST_SERVER = "http://metacat.lternet.edu/knb/metacat";
  
  
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
    testSuite.addTest(new DataManagerTest("parseMetadata"));
    
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
   * Unit test for the DataManager.parseMetadata() method (Use Case #1). 
   * 
   * @throws MalformedURLException
   * @throws IOException
   * @throws Exception
   */
  public void parseMetadata()
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
  }

  
  /**
   * Establish a testing framework by initializing appropriate objects.
   */
  public void setUp() {
    dataManager = DataManager.getInstance();
  }
  
  
  /**
   * Release any objects after tests are complete.
   */
  public void tearDown() {
    dataManager = null;
  }
  
}
