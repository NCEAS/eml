package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ecoinformatics.datamanager.parser.Constraint;
import org.ecoinformatics.datamanager.parser.UniqueKey;
import org.ecoinformatics.datamanager.parser.UnWellFormedConstraintException;

/**
 * @author Jing Tao
 *
 */

public class DataPackageTest extends TestCase
{
  private DataPackage dataPackage      = null;
  private String      dataPackageId    = "hello.1.1";
  
  public DataPackageTest (String name)
  {
    super(name);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    dataPackage = new DataPackage(dataPackageId);
  }

  protected void tearDown() throws Exception
  {
	dataPackage = null;
    super.tearDown();
  }


  /**
   * Method to test getter and Setter for LineNumber
   *
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
	  entity = new Entity(id, name, description,caseSensitive,orientation,numRecords);
	  dataPackage.add(entity);
	  Entity[] list = dataPackage.getEntityList();
	  Entity gotEntity = list[0];
	  assertEquals(entity, gotEntity);
	  
  }
  
  /**
   * Test getPackageId method
   *
   */
  public void testGetPackageId()
  {
	  String id = dataPackage.getPackageId();
	  assertEquals(id, dataPackageId);
  }
  
  


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


}

