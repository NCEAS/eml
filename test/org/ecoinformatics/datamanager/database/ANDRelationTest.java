package org.ecoinformatics.datamanager.database;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.TextDomain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class ANDRelationTest extends TestCase {
	  private Entity entity1          = null;
	  private Entity entity2          = null;
	  private Attribute attribute1    = null;
	  private Attribute attribute2    = null;
	  private String id              = "001";
	  private String name1            = "newEntity1";
	  private String name2            = "newEntity2";
	  private String description     = "test";
	  private Boolean caseSensitive  = new Boolean(false);
	  private String  orientation    = "column";
	  private int     numRecords     = 200;
	  private String attributeName1   = "name1";
	  private String attributeName2   = "name2";
	  private String attributeId     = "id";
	  private String dbTableName1     = "table1";
	  private String dbTableName2     = "table2";
	  private String dbAttributeName1 = "attribute1";
	  private String dbAttributeName2 = "attribute2";
	 

  /**
   * Constructor 
   * @param name The name of testing
   */
  public ANDRelationTest(String name) {
    super(name);
  }


  /**
   * Establish a testing framework by initializing appropriate objects.
   */
  protected void setUp() throws Exception {
    super.setUp();
    

  }


  /**
   * Release any objects and closes database connections after tests 
   * are complete.
   */
  protected void tearDown() throws Exception {

    super.tearDown();
  }
   
  /**
   * Tests toSQLString method. The situation includes entity is null, attribute is null,
   * attribute doesn't has dbName, both entity and attribute has dbname, and only attribute
   * has dbname
   *
   */
  public void testToSQLStringMethod()
  {
	   String operator = "=";
	   Object value    = "hello";
	   ANDRelation relation = new ANDRelation();
	   try
	   {
		   relation.toSQLString();
		   assertTrue("all list is null, should catch exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("all list is null, should catch exception", 1==1);
	   }
	   entity1 = new Entity(id, name1, description,caseSensitive,orientation,numRecords);
	   TextDomain domain = new TextDomain();
	   attribute1 = new Attribute(attributeId, attributeName1, domain);
	   entity2 = new Entity(id, name2, description,caseSensitive,orientation,numRecords);
	   attribute2 = new Attribute(attributeId, attributeName2, domain);
	   entity1.setDBTableName(dbTableName1);
	   entity2.setDBTableName(dbTableName2);
	   attribute1.setDBFieldName(dbAttributeName1);
	   attribute2.setDBFieldName(dbAttributeName2);
	   Condition con1 = new Condition(entity1, attribute1, operator, value);
	   relation.addCondtionInterface(con1);
	   try
	   {
		   relation.toSQLString();
		   assertTrue("Only has one condition, should catch exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("Only has one condition, should catch exception", 1==1);
	   }
	   Condition cond2 = new Condition(entity2, attribute2, operator, value);
	   relation.addCondtionInterface(cond2);
	   try
	   {
		   String sql = relation.toSQLString();
		   System.out.println("sql is " +sql);
		   assertTrue("Should have a sql ", sql.equals(" "+dbTableName1+"."+dbAttributeName1+" = '"+
				   value+"' AND "+dbTableName2+"."+dbAttributeName2+" = '"+value+"' "));
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("Should have a sql ", 1==2);
	   }
	   
	   ANDRelation relation1 = new ANDRelation();
	   relation1.addCondtionInterface(con1);
	   relation1.addCondtionInterface(cond2);
	   relation.addANDRelation(relation1);
	   
	   ORRelation relation2 = new ORRelation();
	   relation2.addCondtionInterface(con1);
	   relation2.addCondtionInterface(cond2);
	   relation.addORRelation(relation2);
	   
	   try
	   {
		   String sql = relation.toSQLString();
		   System.out.println("sql is " +sql);
		   assertTrue("Should have a sql ", sql.equals(" table1.attribute1 = 'hello' AND table2.attribute2 = 'hello' AND ( table1.attribute1 = 'hello' AND table2.attribute2 = 'hello' ) AND ( table1.attribute1 = 'hello' OR table2.attribute2 = 'hello' ) "));
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("Should have a sql ", 1==2);
	   }
	   
	   relation1.addORRelation(relation2);
	   try
	   {
		   String sql = relation.toSQLString();
		   System.out.println("sql is " +sql);
		   assertTrue("Should have a sql ", sql.equals(" table1.attribute1 = 'hello' AND table2.attribute2 = 'hello' AND ( table1.attribute1 = 'hello' AND table2.attribute2 = 'hello' AND ( table1.attribute1 = 'hello' OR table2.attribute2 = 'hello' ) ) AND ( table1.attribute1 = 'hello' OR table2.attribute2 = 'hello' ) "));
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("Should have a sql ", 1==2);
	   }
  }

 

  /**
   * Create a suite of tests to be run together
   */
  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new ANDRelationTest("testToSQLStringMethod"));
    return suite;
  }
  
}

