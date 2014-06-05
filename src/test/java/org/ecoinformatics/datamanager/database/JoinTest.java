package org.ecoinformatics.datamanager.database;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.TextDomain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test suite for the Join class.
 * 
 * @author tao
 *
 */
public class JoinTest extends TestCase {
  
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
	 

  /**
   * Constructor 
   * @param name The name of testing
   */
  public JoinTest(String name) {
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
    suite.addTest(new JoinTest("testToSQLStringMethod"));
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
    DatabaseConnectionPoolInterfaceTest connectionPool = 
            new DatabaseConnectionPoolInterfaceTest();
    String dbAdapterName = connectionPool.getDBAdapterName();
    DataManager.getInstance(connectionPool, dbAdapterName);

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
	   Join item1 = new Join(entity1, attribute1, entity2, attribute2);
       
	   try
	   {
		   item1.toSQLString();
		   assertTrue("both entity attribute is null, should catch exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("both entity attribute is null, should catch exception", 1==1);
	   }
       
	   entity1 = new Entity(id, name1, description,caseSensitive,orientation,numRecords);
	   TextDomain domain = new TextDomain();
	   attribute1 = new Attribute(attributeId, attributeName1, domain);
	   Join item2 = new Join(entity1, attribute1, entity2, attribute2);
       
	   try
	   {
		   item2.toSQLString();
		   assertTrue("both entity attribute is null, should catch exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("both entity attribute is null, should catch exception", 1==1);
	   }
       
	   entity2 = new Entity(id, name2, description,caseSensitive,orientation,numRecords);
	   Join item3 = new Join(entity1, attribute1, entity2, attribute2);
       
	   try
	   {
		   String sql1 = item3.toSQLString();
		   assertTrue("attribute is null, should catch exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("attribute is null, should catch exception", 1==1);
	   }
       
	   attribute2 = new Attribute(attributeId, attributeName2, domain);
	   Join item4 = new Join(entity1, attribute1, entity2, attribute2);
       
	   try
	   {
		   String sql1 = item4.toSQLString();
		   assertTrue("attribute and entity dbname is null, should catch exception", 1==2);
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("attribute and entity dbname is null, should catch exception", 1==1);
	   }
       
	   entity1.setDBTableName(dbTableName1);
	   entity2.setDBTableName(dbTableName2);
	   attribute1.setDBFieldName(dbAttributeName1);
	   attribute2.setDBFieldName(dbAttributeName2);
	   Join item5 = new Join(entity1, attribute1, entity2, attribute2);
       
	   try
	   {
		   String sql1 = item5.toSQLString();
		   System.out.println("sql is "+sql1);
		   assertTrue("SQL name", sql1.equals(dbTableName1+"."+dbAttributeName1+" = "+dbTableName2+"."+dbAttributeName2));
	   }
	   catch (UnWellFormedQueryException e)
	   {
		   assertTrue("should not catch exception", 1==2);
	   }
	   
  }


}

