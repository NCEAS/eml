package org.ecoinformatics.datamanager.database;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.TextDomain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class SubQueryClauseTest extends TestCase {
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
	  private SubQueryClause subQuery = null;
	  private String  inClause = null;
	 

  /**
   * Constructor 
   * @param name The name of testing
   */
  public SubQueryClauseTest(String name) {
    super(name);
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
   * sub-query with nothing, will catch a exception
   *
   */
  public void testToSQLStringBaseOnEverythingIsNull()
  {
	 Entity entiy = null;
	 Attribute att = null;
	 subQuery = new SubQueryClause(entiy, att, inClause, query);
	 try
	 {
		   subQuery.toSQLString();
		   assertTrue("all list is null, should catch exception", 1==2);
	 }
	 catch (UnWellFormedQueryException e)
	 {
		   assertTrue("all list is null, should catch exception", 1==1);
	 }
  }
  
  /**
   * sub query has illegal inClause, will catch a exception
   *
   */
  public void testToSQLStringWithIllegalInClause()
  {
	 inClause = "has in";
	 query.addSelectionItem(select1);
	 query.addTableItem(table1);
	 subQuery = new SubQueryClause(entity1, attribute1, inClause, query);
	 try
	 {
		   subQuery.toSQLString();
		   assertTrue("has illegal in clause", 1==2);
	 }
	 catch (UnWellFormedQueryException e)
	 {
		   assertTrue("has illegal in clause", 1==1);
	 }
  }
  
  /**
   * Query only has selection, no where clause
   *
   */
  public void testToSQLStringBaseOnSelection()
  {
	inClause = "IN";
	query.addSelectionItem(select1);
	query.addTableItem(table1);
	subQuery = new SubQueryClause(entity1, attribute1, inClause, query);
	try
	{
		 String sql = subQuery.toSQLString();
		 System.out.println("sql is"+sql+"!");
		 assertTrue("Should have a sql ", sql.equals("table1.attribute1 IN (SELECT table1.attribute1 FROM table1 )"));
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
 
 

  /**
   * Create a suite of tests to be run together
   */
  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new SubQueryClauseTest("testToSQLStringBaseOnEverythingIsNull"));
    suite.addTest(new SubQueryClauseTest("testToSQLStringWithIllegalInClause"));
    suite.addTest(new SubQueryClauseTest("testToSQLStringBaseOnSelection"));
    //suite.addTest(new SubQueryClauseTest("testToSQLStringHasWhereCaluseBaseOnANDRelation"));
    //suite.addTest(new SubQueryClauseTest("testToSQLStringHasWhereClauseBaseOnORRelation"));
    return suite;
  }
  
}


