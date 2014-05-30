package org.ecoinformatics.datamanager.database;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.TextDomain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * JUnit test suite for the Condition class.
 * 
 * @author tao
 *
 */
public class ConditionTest extends TestCase {
  
      /*
       * Instance fields
       */
  
	  private Entity entity          = null;
	  private Attribute attribute    = null;
	  private String id              = "001";
	  private String name            = "newEntity";
	  private String description     = "test";
	  private Boolean caseSensitive  = new Boolean(false);
	  private String  orientation    = "column";
	  private int     numRecords     = 200;
	  private String attributeName   = "name";
	  private String attributeId     = "id";
	  private String dbTableName     = "table1";
	  private String dbAttributeName = "attribute1";
	  private String operator        = null;
	  private Object value           = null;
	 

  /**
   * Constructor 
   * 
   * @param name The name of testing
   */
  public ConditionTest(String name) {
    super(name);
  }
  
  
  /*
   * Class methods
   */

  /**
   * Create a suite of tests to be run together
   */
  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new ConditionTest("testToSQLStringMethod"));
    return suite;
  }

  
  /*
   * Instance methods
   */
  
  /**
   * Establish a testing framework by initializing appropriate objects.
   */
  protected void setUp() throws Exception {
    super.setUp();
  }


  /**
   * Release any objects and close database connections after tests 
   * are complete.
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
   
  
  /**
   * Tests toSQLString method. The situation includes entity is null, 
   * attribute is null, attribute doesn't have dbName, both entity and attribute 
   * have dbname, and only attribute has dbname.
   *
   */
  public void testToSQLStringMethod()
  {
	   Condition item1 = new Condition(entity, attribute, operator, value);
       
	   try
	   {
		   item1.toSQLString();
		   assertTrue("attribute is null, should catch exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("attribute is null, should catch exception", 1==1);
	   }
       
	   TextDomain domain = new TextDomain();
	   attribute = new Attribute(attributeId, attributeName, domain);
	   Condition item2 = new Condition(entity, attribute, operator, value);
       
	   try
	   {
		   item2.toSQLString();
		   assertTrue("attribute dbname is null, should catch exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("attribute dbname is null, should catch exception", 1==1);
	   }
       
	   attribute.setDBFieldName(dbAttributeName);
	   Condition item3 = new Condition(entity, attribute, operator, value);
       
	   try
	   {
		   String sql1 = item3.toSQLString();
		   assertTrue("operator is null, should throw a exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("operator is null, should throw a exception", 1==1);
	   }
	   
	   operator = "&";
	   value = "er";
	   Condition item4 = new Condition(entity, attribute, operator, value);
       
	   try
	   {
		   String sql1 = item4.toSQLString();
		   assertTrue("operator is illegal, should throw a exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("operator is illegal, should throw a exception", 1==1);
	   }
	   
	   operator = "<=";
	   value = "er";
	   Condition item5 = new Condition(entity, attribute, operator, value);
       
	   try
	   {
		   String sql1 = item5.toSQLString();
		   System.out.println("sql is "+sql1);
		   assertTrue("operator is numberic but value is not, should throw a exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("operator is numberic but value is not, should throw a exception", 1==1);
	   }
	   
	   value = new Integer("2");	   
	   Condition item6 = new Condition(entity, attribute, operator, value);
       
	   try
	   {
		   String sql1 = item6.toSQLString();
		   System.out.println("sql is "+sql1);
		   assertTrue("should get a sql command but failed", sql1.equals(dbAttributeName+" "+operator+" "+value.toString()));
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   System.out.println("the error message is "+e.getMessage());
		   assertTrue("operator is numberic but value is not, should throw a exception", 1==2);
	   }
	   
	   operator = "LIKE";
	   value = "er";
       entity = new Entity(id, name, description,caseSensitive,orientation,numRecords);
       entity.setDBTableName(dbTableName);
	   entity.add(attribute);
	   Condition item7 = new Condition(entity, attribute, operator, value);
       
	   try
	   {
		   String sql1 = item7.toSQLString();
		   System.out.println("sql is "+sql1);
		   assertTrue("SQL name", sql1.equals(dbTableName+"."+dbAttributeName+" "+operator+" '"+value+"'"));
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("should not catch exception", 1==2);
	   }
  }
  
}


