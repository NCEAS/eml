package org.ecoinformatics.datamanager.database;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.TextDomain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.*;

/**
 * JUnit test for the Union class.
 * 
 * @author leinfelder
 *
 */
public class UnionTest extends TestCase {
  
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
	  private List        queryList = null;
	  private int		queryCount = 0;
	  private Union union = null;
	 

  /**
   * Constructor 
   * 
   * @param name The name of testing
   */
  public UnionTest(String name) {
    super(name);
  }


  /**
   * Create a suite of tests to be run together
   */
  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new UnionTest("testToSQLStringBasicUnion"));
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
	queryList = new ArrayList();
	queryCount = 2;
	union = new Union();
  }


  /**
   * Release any objects and closes database connections after tests 
   * are complete.
   */
  protected void tearDown() throws Exception {

    super.tearDown();
  }
  
  
  /**
   * Simple Union only has selection, no where clauses
   *
   */
  public void testToSQLStringBasicUnion()
  {
	
	for (int i=0; i<queryCount; i++) {
		query = new Query();
		query.addSelectionItem(select1);
		query.addSelectionItem(select2);
		query.addTableItem(table1);
		query.addTableItem(table2);
		union.addQuery(query);
	}
	union.setUnionType(Union.UNION);
    
	try
	{
		 String sql = union.toSQLString();
		 System.out.println("sql is"+sql);
		 assertTrue(
				 "Should have a sql ", 
				 sql.equals(
						 "SELECT table1.attribute1,table2.attribute2 FROM table1,table2 UNION SELECT table1.attribute1,table2.attribute2 FROM table1,table2;"));
	}
	catch (UnWellFormedQueryException e)
	{
		assertTrue("Should have a sql", 1==2);
	}
	
  }
 
}

