package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ecoinformatics.datamanager.parser.Constraint;
import org.ecoinformatics.datamanager.parser.UniqueKey;
import org.ecoinformatics.datamanager.parser.UnWellFormedConstraintException;

/**
 * @author tao
 * 
 * JUnit tests for the UniqueKey class.
 *
 */

public class UniqueKeyTest extends TestCase
{
  /*
   * Instance fields
   */
  private UniqueKey uniqueKey = null;
  private String CONSTRAINTNAME = "unique_key";
  private String KEY1           = "id1";
  private String KEY2           = "id2";

  
  /*
   * Constructors
   */
  
  public UniqueKeyTest (String name)
  {
    super(name);
  }
  
  
  /*
   * Class methods
   */
  
  /**
   * Creates a suite of tests to be run together.
   */
   public static Test suite()
   {
     TestSuite suite = new TestSuite();
     suite.addTest(new UniqueKeyTest("initialize"));
     suite.addTest(new UniqueKeyTest("testPrintStringHaveOneKey"));
     suite.addTest(new UniqueKeyTest("testPrintStringHaveTwoKeys"));
     suite.addTest(new UniqueKeyTest("testNameGetterAndSetter"));
     suite.addTest(new UniqueKeyTest("testTypeGetter"));
     suite.addTest(new UniqueKeyTest("testKeysGetterAndSetter"));
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
    uniqueKey = new UniqueKey();
    String constraintName = CONSTRAINTNAME;
    uniqueKey.setName(constraintName);
  }

  
  /**
   * Releases any objects after tests are complete.
   */
  protected void tearDown() throws Exception
  {
    uniqueKey = null;
    super.tearDown();
  }

  
  /**
   * Tests the printString() method using one key.
   * 
   * @throws UnWellFormedConstraintException
   */
  public void testPrintStringHaveOneKey() throws UnWellFormedConstraintException
  {
    String[] keys = new String[1];
    keys[0] = KEY1;
    uniqueKey.setKeys(keys);
    String expectedReturn = " "+ Constraint.CONSTRAINT + " "+CONSTRAINTNAME + 
                            " " +
                            Constraint.UNIQUEKEYSTRING + " "+"("+KEY1+")" +" ";
    String actualReturn = uniqueKey.printString();
    assertEquals("return value", expectedReturn, actualReturn);

  }
  
  
  /**
   * Tests the printString() methods using two keys.
   * 
   * @throws UnWellFormedConstraintException
   */
  public void testPrintStringHaveTwoKeys() 
          throws UnWellFormedConstraintException
  {
    String[] keys = new String[2];
    keys[0] = KEY1;
    keys[1] = KEY2;
    uniqueKey.setKeys(keys);
    String expectedReturn = " "+ Constraint.CONSTRAINT + " "+CONSTRAINTNAME + " " +
                            Constraint.UNIQUEKEYSTRING + " "+"("+
                            KEY1 + "," + KEY2 + ")"+" ";
    String actualReturn = uniqueKey.printString();
    assertEquals("return value", expectedReturn, actualReturn);
  }
  
  
  /**
   * Tests the getter and setter methods for the name field.
   *
   */
  public void testNameGetterAndSetter()
  {
	  String name = "newName";
	  uniqueKey.setName(name);
	  String gotName = uniqueKey.getName();
	  assertEquals(name, gotName);
  }
  
  
  /**
   * Tests the getType() method.
   *
   */
  public void testTypeGetter()
  {
	  int type = uniqueKey.getType();
	  assertEquals(type, Constraint.UNIQUEKEY);
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
	  uniqueKey.setKeys(keys);
	  String[] gotKeys = uniqueKey.getKeys();
	  assertEquals(keys, gotKeys);
  }

  
  /**
  * Runs an initial test that always passes to check that the test
  * harness is working.
  */
  public void initialize()
  {
    assertTrue(1 == 1);
  }

}
