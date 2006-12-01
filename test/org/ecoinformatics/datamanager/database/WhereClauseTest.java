package org.ecoinformatics.datamanager.database;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.TextDomain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class WhereClauseTest extends TestCase {
  
      /*
       * Instance fields
       */
  
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
	  private String operator = "=";
	  private Object value    = "hello";
	 

  /**
   * Constructor 
   * @param name The name of testing
   */
  public WhereClauseTest(String name) {
    super(name);
  }


  /**
   * Create a suite of tests to be run together
   */
  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new WhereClauseTest("testToSQLStringWhenEverythingIsNull"));
    suite.addTest(new WhereClauseTest("testToSQLStringBaseOnCondition"));
    suite.addTest(new WhereClauseTest("testToSQLStringBaseOnANDRelation"));
    suite.addTest(new WhereClauseTest("testToSQLStringBaseOnORRelation"));
    return suite;
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
   * Everything is null and should catch a exception
   *
   */
  public void testToSQLStringWhenEverythingIsNull()
  {
	 Condition con = null;
	 WhereClause where = new WhereClause(con);
     
	 try
	 {
		   where.toSQLString();
		   assertTrue("all list is null, should catch exception", 1==2);
	 }
	 catch (UnWellFormedQueryException e)
	 {
		   assertTrue("all list is null, should catch exception", 1==1);
	 }
  }
  
  
  /**
   * test toSQLString based on where clause constructor with condition
   *
   */
  public void testToSQLStringBaseOnCondition()
  {
	  entity1 = new Entity(id, name1, description,caseSensitive,orientation,numRecords);
	  TextDomain domain = new TextDomain();
	  attribute1 = new Attribute(attributeId, attributeName1, domain);
	  entity1.setDBTableName(dbTableName1);
	  attribute1.setDBFieldName(dbAttributeName1);
	  Condition con = new Condition(entity1, attribute1, operator, value);
	  WhereClause where = new WhereClause(con);
      
	  try
	  {
		 String sql = where.toSQLString();
		 System.out.println("sql is"+sql);
		 assertTrue("Should have a sql ", sql.equals(" where table1.attribute1 = 'hello'"));
	  }
	  catch (UnWellFormedQueryException e)
	  {
		assertTrue("Should have a sql", 1==2);
	  }
  }
  
  
  /**
   * test toSQLString based on where clause constructor with ANDRelation
   *
   */
  public void testToSQLStringBaseOnANDRelation()
  {
	   ANDRelation relation = new ANDRelation();
	   
	   entity1 = new Entity(id, name1, description,caseSensitive,orientation,numRecords);
	   TextDomain domain = new TextDomain();
	   attribute1 = new Attribute(attributeId, attributeName1, domain);
	   entity2 = new Entity(id, name2, description,caseSensitive,orientation,numRecords);
	   attribute2 = new Attribute(attributeId, attributeName2, domain);
	   entity1.setDBTableName(dbTableName1);
	   entity2.setDBTableName(dbTableName2);
	   attribute1.setDBFieldName(dbAttributeName1);
	   attribute2.setDBFieldName(dbAttributeName2);
	   Condition cond1 = new Condition(entity1, attribute1, operator, value);
	   Condition cond2 = new Condition(entity2, attribute2, operator, value);
	   relation.addCondtionInterface(cond1);
	   relation.addCondtionInterface(cond2);
	   
	   WhereClause where = new WhereClause(relation);
       
	   try
	   {
		 String sql = where.toSQLString();
		 System.out.println("sql is"+sql+"!");
		 assertTrue("Should have a sql ", sql.equals(" where  table1.attribute1 = 'hello' AND table2.attribute2 = 'hello' "));
	   }
	   catch (UnWellFormedQueryException e)
	   {
		 assertTrue("Should have a sql", 1==2);
	   }
  }
   
  /**
   * test toSQLString based on where clause constructor with ORRelation
   *
   */
  public void testToSQLStringBaseOnORRelation()
  {
	   ORRelation relation = new ORRelation();
	   
	   entity1 = new Entity(id, name1, description,caseSensitive,orientation,numRecords);
	   TextDomain domain = new TextDomain();
	   attribute1 = new Attribute(attributeId, attributeName1, domain);
	   entity2 = new Entity(id, name2, description,caseSensitive,orientation,numRecords);
	   attribute2 = new Attribute(attributeId, attributeName2, domain);
	   entity1.setDBTableName(dbTableName1);
	   entity2.setDBTableName(dbTableName2);
	   attribute1.setDBFieldName(dbAttributeName1);
	   attribute2.setDBFieldName(dbAttributeName2);
	   Condition cond1 = new Condition(entity1, attribute1, operator, value);
	   Condition cond2 = new Condition(entity2, attribute2, operator, value);
	   relation.addCondtionInterface(cond1);
	   relation.addCondtionInterface(cond2);
	   
	   WhereClause where = new WhereClause(relation);
       
	   try
	   {
		 String sql = where.toSQLString();
		 System.out.println("sql is"+sql+"!");
		 assertTrue("Should have a sql ", sql.equals(" where  table1.attribute1 = 'hello' OR table2.attribute2 = 'hello' "));
	   }
	   catch (UnWellFormedQueryException e)
	   {
		 assertTrue("Should have a sql", 1==2);
	   }
  }
   
}

