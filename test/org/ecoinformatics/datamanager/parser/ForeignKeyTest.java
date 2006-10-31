package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ecoinformatics.datamanager.parser.Constraint;

/**
 * @author tao
 * 
 * JUnit tests for the ForeignKey class.
 *
 */
public class ForeignKeyTest extends TestCase
{
  /*
   * Instance fields
   */
  private ForeignKey foreignKey = null;
  private String KEY1           = "id1";
  private String KEY2           = "id2";


  /*
   * Constructors
   */
  
  public ForeignKeyTest (String name)
  {
    super(name);
  }

  
  /*
   * Class methods 
   */
  
  /**
   * Create a suite of tests to be run together.
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
  
  
  /*
   * Instance methods
   */

   
  /**
   * Establishes a testing framework by initializing appropriate objects.
   */
  protected void setUp() throws Exception
  {
    super.setUp();
    foreignKey = new ForeignKey();
    
  }

  
  /**
   * Releases any objects after tests are complete.
   */
  protected void tearDown() throws Exception
  {
	foreignKey = null;
    super.tearDown();
  }

  
  /**
   * Method to test getter and setter for name.
   */
  public void testNameGetterAndSetter()
  {
	  String name = "newName";
	  foreignKey.setName(name);
	  String gotName = foreignKey.getName();
	  assertEquals(name, gotName); 
  }
  
  
  /**
   * Tests getter method for type.
   *
   */
  public void testTypeGetter()
  {
	  int type = foreignKey.getType();
	  assertEquals(type, Constraint.FOREIGNKEY);
  }
  
  
  /**
   * Tests getter and setter methods for keys.
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
   * Tests getEntityReference() and setEntityReference() methods.
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
  
}
