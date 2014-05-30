package org.ecoinformatics.datamanager.database;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.TextDomain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * JUnit test for the Query class.
 * 
 * @author tao
 *
 */
public class QueryTest extends TestCase {
  
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
	  private SelectionItem select1 = new SelectionItem(entity1, attribute1);
      private SelectionItem select2 = new SelectionItem(entity2, attribute2);
	  private TableItem     table1  = new TableItem(entity1);
	  private TableItem     table2  = new TableItem(entity2);
	  private Query          query  = null;
	 

  /**
   * Constructor 
   * 
   * @param name The name of testing
   */
  public QueryTest(String name) {
    super(name);
  }


  /**
   * Create a suite of tests to be run together
   */
  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new QueryTest("testToSQLStringBaseOnEverythingIsNull"));
    suite.addTest(new QueryTest("testToSQLStringBaseOnSelection"));
    suite.addTest(new QueryTest("testToSQLStringHasWhereClauseBaseOnCondition"));
    suite.addTest(new QueryTest("testToSQLStringHasWhereCaluseBaseOnANDRelation"));
    suite.addTest(new QueryTest("testToSQLStringHasWhereClauseBaseOnORRelation"));
    return suite;
  }
  

  /**
   * Establish a testing framework by initializing appropriate objects.
   */
  protected void setUp() throws Exception {
    super.setUp();
    entity1 = new Entity(id, name1, description,caseSensitive,orientation,numRecords);
    TextDomain domain = new TextDomain();
    attribute1 = new Attribute(attributeId, attributeName1, domain);
    entity2 = new Entity(id, name2, description,caseSensitive,orientation,numRecords);
    attribute2 = new Attribute(attributeId, attributeName2, domain);
    entity1.setDBTableName(dbTableName1);
    entity2.setDBTableName(dbTableName2);
    attribute1.setDBFieldName(dbAttributeName1);
    attribute2.setDBFieldName(dbAttributeName2);
    select1 = new SelectionItem(entity1, attribute1);
	select2 = new SelectionItem(entity2, attribute2);
	table1  = new TableItem(entity1);
	table2  = new TableItem(entity2);
	query = new Query();
  }


  /**
   * Release any objects and closes database connections after tests 
   * are complete.
   */
  protected void tearDown() throws Exception {

    super.tearDown();
  }
  
  
  /**
   * query with nothing, will catch a exception
   *
   */
  public void testToSQLStringBaseOnEverythingIsNull()
  {
	 try
	 {
		   query.toSQLString();
		   assertTrue("all list is null, should catch exception", 1==2);
	 }
	 catch (UnWellFormedQueryException e)
	 {
		   assertTrue("all list is null, should catch exception", 1==1);
	 }
  }
  
  
  /**
   * Query only has selection, no where clause
   *
   */
  public void testToSQLStringBaseOnSelection()
  {
	query.addSelectionItem(select1);
	query.addSelectionItem(select2);
	query.addTableItem(table1);
	query.addTableItem(table2);
    
	try
	{
		 String sql = query.toSQLString();
		 System.out.println("sql is"+sql);
		 assertTrue("Should have a sql ", sql.equals("SELECT table1.attribute1,table2.attribute2 FROM table1,table2;"));
	}
	catch (UnWellFormedQueryException e)
	{
		assertTrue("Should have a sql", 1==2);
	}
	
  }
  
  /**
   * test toSQLString based on where clause constructor with condition
   *
   */
  public void testToSQLStringHasWhereClauseBaseOnCondition()
  {
	  Condition con = new Condition(entity1, attribute1, operator, value);
	  WhereClause where = new WhereClause(con);
	  query.addSelectionItem(select1);
	  query.addSelectionItem(select2);
	  query.addTableItem(table1);
	  query.addTableItem(table2);
	  query.setWhereClause(where);
      
	  try
	  {
		 String sql = query.toSQLString();
		 System.out.println("sql is"+sql);
		 assertTrue("Should have a sql ", sql.equals("SELECT table1.attribute1,table2.attribute2 FROM table1,table2  where table1.attribute1 = 'hello';"));
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
  public void testToSQLStringHasWhereCaluseBaseOnANDRelation()
  {
	   ANDRelation relation = new ANDRelation();
	   Condition cond1 = new Condition(entity1, attribute1, operator, value);
	   Condition cond2 = new Condition(entity2, attribute2, operator, value);
	   relation.addCondtionInterface(cond1);
	   relation.addCondtionInterface(cond2);
	   WhereClause where = new WhereClause(relation);
	   query.addSelectionItem(select1);
	   query.addSelectionItem(select2);
	   query.addTableItem(table1);
	   query.addTableItem(table2);
	   query.setWhereClause(where);
	   try
	   {
		 String sql = query.toSQLString();
		 System.out.println("sql is"+sql);
		 assertTrue("Should have a sql ", sql.equals("SELECT table1.attribute1,table2.attribute2 FROM table1,table2  where  table1.attribute1 = 'hello' AND table2.attribute2 = 'hello' ;"));
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
  public void testToSQLStringHasWhereClauseBaseOnORRelation()
  {
	   ORRelation relation = new ORRelation();
	   Condition cond1 = new Condition(entity1, attribute1, operator, value);
	   Condition cond2 = new Condition(entity2, attribute2, operator, value);
	   relation.addCondtionInterface(cond1);
	   relation.addCondtionInterface(cond2);
	   WhereClause where = new WhereClause(relation);
	   query.addSelectionItem(select1);
	   query.addSelectionItem(select2);
	   query.addTableItem(table1);
	   query.addTableItem(table2);
	   query.setWhereClause(where);
       
	   try
	   {
		 String sql = query.toSQLString();
		 System.out.println("sql is"+sql);
		 assertTrue("Should have a sql ", sql.equals("SELECT table1.attribute1,table2.attribute2 FROM table1,table2  where  table1.attribute1 = 'hello' OR table2.attribute2 = 'hello' ;"));
	   }
	   catch (UnWellFormedQueryException e)
	   {
		 assertTrue("Should have a sql", 1==2);
	   }
  }
 
}

