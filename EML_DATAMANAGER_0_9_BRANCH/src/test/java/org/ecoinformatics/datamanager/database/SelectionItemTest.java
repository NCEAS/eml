package org.ecoinformatics.datamanager.database;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.TextDomain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * JUnit test suite for the SelectionItem class.
 * 
 * @author tao
 */
public class SelectionItemTest extends TestCase {
  
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
	 

  /**
   * Constructor 
   * @param name The name of testing
   */
  public SelectionItemTest(String name) {
    super(name);
  }


  /**
   * Create a suite of tests to be run together
   */
  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new SelectionItemTest("testToSQLStringMethod"));
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
   * Tests toSQLString method. The situation includes entity is null, 
   * attribute is null, attribute doesn't have dbName, both entity and attribute
   * have dbname, and only attribute has dbname.
   *
   */
  public void testToSQLStringMethod()
  {
	   SelectionItem item1 = new SelectionItem(entity, attribute);
       
	   try
	   {
		   item1.toSQLString();
		   assertTrue("attribute is null - which is now allowed, should NOT catch exception", 1==1);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   fail("attribute is null - which is now allowed, should NOT catch exception");
	   }
       
	   TextDomain domain = new TextDomain();
	   attribute = new Attribute(attributeId, attributeName, domain);
	   SelectionItem item2 = new SelectionItem(entity, attribute);
       
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
	   SelectionItem item3 = new SelectionItem(entity, attribute);
       
	   try
	   {
		   String sql1 = item3.toSQLString();
		   System.out.println("sql is "+sql1);
		   assertTrue("SQL name", sql1.equals(dbAttributeName));
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("should not catch exception", 1==2);
	   }
       
       entity = new Entity(id, name, description,caseSensitive,orientation,numRecords);
       entity.setDBTableName(dbTableName);
	   entity.add(attribute);
	   SelectionItem item4 = new SelectionItem(entity, attribute);
       
	   try
	   {
		   String sql1 = item4.toSQLString();
		   System.out.println("sql is "+sql1);
		   assertTrue("SQL name", sql1.equals(dbTableName+"."+dbAttributeName));
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("should not catch exception", 1==2);
	   }
  }

}

