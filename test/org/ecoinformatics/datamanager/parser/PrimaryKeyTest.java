package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ecoinformatics.datamanager.parser.Constraint;
import org.ecoinformatics.datamanager.parser.PrimaryKey;
import org.ecoinformatics.datamanager.parser.UnWellFormedConstraintException;

/**
 * @author tao
 * 
 * JUnit tests for the PrimaryKey class.
 *
 */
public class PrimaryKeyTest extends TestCase
{
  /*
   * Instance fields
   */
  private PrimaryKey primaryKey = null;
  private String CONSTRAINTNAME = "primary_key";
  private String KEY1           = "id1";
  private String KEY2           = "id2";

  
  /*
   * Constructors
   */
  public PrimaryKeyTest (String name)
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
     suite.addTest(new PrimaryKeyTest("initialize"));
     suite.addTest(new PrimaryKeyTest("testPrintStringHaveOneKey"));
     suite.addTest(new PrimaryKeyTest("testPrintStringHaveTwoKeys"));
     suite.addTest(new PrimaryKeyTest("testNameGetterAndSetter"));
     suite.addTest(new PrimaryKeyTest("testTypeGetter"));
     suite.addTest(new PrimaryKeyTest("testKeysGetterAndSetter"));
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
    primaryKey = new PrimaryKey();
    String constraintName = CONSTRAINTNAME;
    primaryKey.setName(constraintName);
  }

  
  /**
   * Releases any objects after tests are complete.
   */
  protected void tearDown() throws Exception
  {
    primaryKey = null;
    super.tearDown();
  }


  /**
   * Tests the printString() method for one key.
   * 
   * @throws UnWellFormedConstraintException
   */
  public void testPrintStringHaveOneKey() throws UnWellFormedConstraintException
  {
    String[] keys = new String[1];
    keys[0] = KEY1;
    primaryKey.setKeys(keys);
    String expectedReturn = " "+ Constraint.CONSTRAINT + " "+CONSTRAINTNAME + " " +
                            Constraint.PRIMARYKEYSTRING + " "+"("+KEY1+")" +" ";
    String actualReturn = primaryKey.printString();
    assertEquals("return value", expectedReturn, actualReturn);

  }

  /**
   * Tests the printString() method for two keys.
   * 
   * @throws UnWellFormedConstraintException
   */
  public void testPrintStringHaveTwoKeys() 
          throws UnWellFormedConstraintException
  {
    String[] keys = new String[2];
    keys[0] = KEY1;
    keys[1] = KEY2;
    primaryKey.setKeys(keys);
    String expectedReturn = " "+ Constraint.CONSTRAINT + " "+CONSTRAINTNAME + 
                            " " +
                            Constraint.PRIMARYKEYSTRING + " "+"("+
                            KEY1 + "," + KEY2 + ")"+" ";
    String actualReturn = primaryKey.printString();
    assertEquals("return value", expectedReturn, actualReturn);
  }
  
  
  /**
   * Method to test getter and setter for the name field.
   */
  public void testNameGetterAndSetter()
  {
	  String name = "newName";
	  primaryKey.setName(name);
	  String gotName = primaryKey.getName();
	  assertEquals(name, gotName);
  }
  
  
  /**
   * Test getter method for the type field.
   */
  public void testTypeGetter()
  {
	  int type = primaryKey.getType();
	  assertEquals(type, Constraint.PRIMARYKEY);
  }
  
  
  /**
   * Tests getter and setter method for keys.
   */
  public void testKeysGetterAndSetter()
  {
	  String[] keys = new String[2];
	  keys[0] = KEY1;
	  keys[1] = KEY2;
	  primaryKey.setKeys(keys);
	  String[] gotKeys = primaryKey.getKeys();
	  assertEquals(keys, gotKeys);
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
