package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ecoinformatics.datamanager.parser.Constraint;
import org.ecoinformatics.datamanager.parser.UnWellFormedConstraintException;

/**
 * @author tao
 * 
 * JUnit tests for the NotNullConstraint class.
 *
 */
public class NotNullConstraintTest extends TestCase
{
  /*
   * Instance fields
   */
  private NotNullConstraint notNull = null;
  //private String CONSTRAINTNAME = "unique_key";
  private String KEY1           = "id1";
  private String KEY2           = "id2";

  
  /*
   * Constructors
   */
  
  public NotNullConstraintTest (String name)
  {
    super(name);
  }

  
  /**
   * Establishes a testing framework by initializing appropriate objects.
   */
  protected void setUp() throws Exception
  {
    super.setUp();
    notNull = new NotNullConstraint();
    
  }

  
  /**
   * Releases any objects after tests are complete.
   */
  protected void tearDown() throws Exception
  {
    notNull = null;
    super.tearDown();
  }

 

  public void testKeysGetterAndSetter()
  {
    String[] keys = new String[2];
    keys[0] = KEY1;
    keys[1] = KEY2;
    notNull.setKeys(keys);
    String[] gotKeys = notNull.getKeys();
    assertEquals(keys, gotKeys);

  }
  
  /**
   * Method to test getter and Setter for name
   *
   */
  public void testPrintString() throws UnWellFormedConstraintException
  {
	  String sql = notNull.printString();
	  assertEquals(sql, Constraint.NOTNULLSTRING);
	  
  }
  
  /**
   * Test getter method for type
   *
   */
  public void testTypeGetter()
  {
	  int type = notNull.getType();
	  assertEquals(type, Constraint.NOTNULLCONSTRAINT);
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
    suite.addTest(new NotNullConstraintTest("initialize"));
    suite.addTest(new NotNullConstraintTest("testPrintString"));
    suite.addTest(new NotNullConstraintTest("testTypeGetter"));
    suite.addTest(new NotNullConstraintTest("testKeysGetterAndSetter"));
    return suite;
  }


}

