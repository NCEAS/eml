package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author tao
 * 
 * JUnit tests for the DataPackage class.
 */

public class DataPackageTest extends TestCase
{
  /*
   * Instance fields
   */
  private DataPackage dataPackage      = null;
  private String      dataPackageId    = "hello.1.1";

  
  /*
   * Constructors
   */
  
  public DataPackageTest (String name)
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
     suite.addTest(new DataPackageTest("testAddEntity"));
     suite.addTest(new DataPackageTest("testGetPackageId"));
     return suite;
   }


  /*
   * Instance methods 
   */
  
 /**
  * Establishes a testing framework by initializing appropriate objects.
  */
  protected void setUp() throws Exception
  {
    super.setUp();
    dataPackage = new DataPackage(dataPackageId);
  }


  /**
   * Releases any objects after tests are complete.
   */
  protected void tearDown() throws Exception
  {
	dataPackage = null;
    super.tearDown();
  }


  /**
   * Tests the add() method by adding an Entity to a DataPackage.
   */
  public void testAddEntity()
  {
	  Entity entity         = null;
	  String id             = "001";
	  String name           = "newEntity";
	  String description    = "test";
	  Boolean caseSensitive = new Boolean(false);
	  String  orientation   = "column";
	  int     numRecords    = 200;
	  entity = 
         new Entity(id, name, description,caseSensitive,orientation,numRecords);
	  dataPackage.add(entity);
	  Entity[] list = dataPackage.getEntityList();
	  Entity gotEntity = list[0];
	  assertEquals(entity, gotEntity);  
  }
  
  
  /**
   * Tests getPackageId method.
   *
   */
  public void testGetPackageId()
  {
	  String id = dataPackage.getPackageId();
	  assertEquals(id, dataPackageId);
  }
  
}
