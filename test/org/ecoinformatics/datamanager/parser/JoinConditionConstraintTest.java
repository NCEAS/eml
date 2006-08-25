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

public class JoinConditionConstraintTest extends TestCase
{
  private JoinConditionConstraint joinConstraint = null;
  private String CONSTRAINTNAME = "joint_constraint";
  private String KEY1           = "id1";
  private String KEY2           = "id2";

  public JoinConditionConstraintTest (String name)
  {
    super(name);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    joinConstraint = new JoinConditionConstraint();
    
  }

  protected void tearDown() throws Exception
  {
	joinConstraint = null;
    super.tearDown();
  }

  

  
  
  /**
   * Method to test getter and Setter for name
   *
   */
  public void testNameGetterAndSetter()
  {
	  String name = "newName";
	  joinConstraint.setName(name);
	  String gotName = joinConstraint.getName();
	  assertEquals(name, gotName);
	  
  }
  
  /**
   * Test getter method for type
   *
   */
  public void testTypeGetter()
  {
	  int type = joinConstraint.getType();
	  assertEquals(type, Constraint.JOINCONDITIONCONSTRAINT);
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
	  joinConstraint.setKeys(keys);
	  String[] gotKeys = joinConstraint.getKeys();
	  assertEquals(keys, gotKeys);
  }
  
  /**
   * Method to test getter and Setter for reference entity
   *
   */
  public void testReferenceEntityGetterAndSetter()
  {
	  String entity = "newTable";
	  joinConstraint.setEntityReference(entity);
	  String gotEntity = joinConstraint.getEntityReference();
	  assertEquals(entity, gotEntity);
	  
  }
  
  /**
   * Method to test getter and Setter for reference entity
   *
   */
  public void testReferenceKeysGetterAndSetter()
  {
	  String[] keys = new String[2];
	  keys[0] = KEY1;
	  keys[1] = KEY2;
	  joinConstraint.setReferencedKeys(keys);
	  String[] gotKeys = joinConstraint.getReferencedKeys();
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
  
  


  /**
  * Create a suite of tests to be run together
  */
  public static Test suite()
  {
    TestSuite suite = new TestSuite();
    suite.addTest(new JoinConditionConstraintTest("initialize"));
    suite.addTest(new JoinConditionConstraintTest("testNameGetterAndSetter"));
    suite.addTest(new JoinConditionConstraintTest("testTypeGetter"));
    suite.addTest(new JoinConditionConstraintTest("testKeysGetterAndSetter"));
    suite.addTest(new JoinConditionConstraintTest("testReferenceEntityGetterAndSetter"));
    suite.addTest(new JoinConditionConstraintTest("testReferenceKeysGetterAndSetter"));
    return suite;
  }


}

