package org.ecoinformatics.datamanager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class DataManagerTest extends TestCase {
  
  /*
   * Class fields
   */
  
  
  /*
   * Instance fields
   */
  
  
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
  public void setUp() {
    
  }
  
  
  /**
   * Release any objects after tests are complete.
   */
  public void tearDown() {
    
  }
  
}
