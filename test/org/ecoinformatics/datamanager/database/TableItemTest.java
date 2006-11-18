package org.ecoinformatics.datamanager.database;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.TextDomain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class TableItemTest extends TestCase {
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
  public TableItemTest(String name) {
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
   * Tests toSQLString method. The situation includes entity is null, entity dbname is null
   * and entity name is not null
   *
   */
  public void testToSQLStringMethod()
  {
	   TableItem item1 = new TableItem(entity);
	   try
	   {
		   item1.toSQLString();
		   assertTrue("entity is null, should catch exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("entity is null, should catch exception", 1==1);
	   }
	   entity = new Entity(id, name, description,caseSensitive,orientation,numRecords);
	   TableItem item2 = new TableItem(entity);
	   try
	   {
		   item2.toSQLString();
		   assertTrue("entity dbname is null, should catch exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("entity dbname is null, should catch exception", 1==1);
	   }
	   entity.setDBTableName(dbTableName);
	   TableItem item4 = new TableItem(entity);
	   try
	   {
		   String sql1 = item4.toSQLString();
		   System.out.println("sql is "+sql1);
		   assertTrue("SQL name", sql1.equals(dbTableName));
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("should not catch exception", 1==2);
	   }
	   
  }

 

  /**
   * Create a suite of tests to be run together
   */
  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new TableItemTest("testToSQLStringMethod"));
    return suite;
  }
  
}


