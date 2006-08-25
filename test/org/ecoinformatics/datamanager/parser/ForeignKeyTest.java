package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ecoinformatics.datamanager.parser.Constraint;
import org.ecoinformatics.datamanager.parser.PrimaryKey;
import org.ecoinformatics.datamanager.parser.UnWellFormedConstraintException;

/**
 * @author Jing Tao
 *
 */

public class ForeignKeyTest extends TestCase
{
  private ForeignKey foreignKey = null;
  private String KEY1           = "id1";
  private String KEY2           = "id2";

  public ForeignKeyTest (String name)
  {
    super(name);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    foreignKey = new ForeignKey();
    
  }

  protected void tearDown() throws Exception
  {
	foreignKey = null;
    super.tearDown();
  }

  

  
  
  /**
   * Method to test getter and Setter for name
   *
   */
  public void testNameGetterAndSetter()
  {
	  String name = "newName";
	  foreignKey.setName(name);
	  String gotName = foreignKey.getName();
	  assertEquals(name, gotName);
	  
  }
  
  /**
   * Test getter method for type
   *
   */
  public void testTypeGetter()
  {
	  int type = foreignKey.getType();
	  assertEquals(type, Constraint.FOREIGNKEY);
  }
  
  /**
   * Test getter and setter method for keys
   *
   */
  public void testKeysGetterAndSetter()
  {
	  String[] keys = new String[2];
	  keys[0] = KEY1;
	  keys[1] = KEY2;
	  foreignKey.setKeys(keys);
	  String[] gotKeys = foreignKey.getKeys();
	  assertEquals(keys, gotKeys);
  }
  
  /**
   * Method to test getter and Setter for reference entity
   *
   */
  public void testReferenceEntityGetterAndSetter()
  {
	  String entity = "newTable";
	  foreignKey.setEntityReference(entity);
	  String gotEntity = foreignKey.getEntityReference();
	  assertEquals(entity, gotEntity);
	  
  }
  
 

  /**
  * Run an initial test that always passes to check that the test
  * harness is working.
  */
  public void initialize()
  {
    assertTrue(1 == 1);
  }
  
  


  /**
  * Create a suite of tests to be run together
  */
  public static Test suite()
  {
    TestSuite suite = new TestSuite();
    suite.addTest(new ForeignKeyTest("initialize"));
    suite.addTest(new ForeignKeyTest("testNameGetterAndSetter"));
    suite.addTest(new ForeignKeyTest("testTypeGetter"));
    suite.addTest(new ForeignKeyTest("testKeysGetterAndSetter"));
    suite.addTest(new ForeignKeyTest("testReferenceEntityGetterAndSetter"));
    return suite;
  }


}


