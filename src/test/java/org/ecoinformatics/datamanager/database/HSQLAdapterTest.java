package org.ecoinformatics.datamanager.database;
import java.sql.SQLException;
import java.util.Vector;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.NumericDomain;
import org.ecoinformatics.datamanager.parser.TextDomain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HSQLAdapterTest extends TestCase
{
    /*
     * Constructors
     */
  
	/**
	 * Constructor 
	 * @param name The name of testing
	 */
	  public HSQLAdapterTest (String name)
	  {
	    super(name);
	  }
      
      
      /*
       * Class methods
       */
      
      /**
       * Create a suite of tests to be run together
       */
       public static Test suite()
       {
         TestSuite suite = new TestSuite();
         suite.addTest(new HSQLAdapterTest("initialize"));
         suite.addTest(new HSQLAdapterTest("testGenerateInsertSQL"));
         return suite;
       }
      
      
      /*
       * Instance methods
       */
      
       /**
        * Run an initial test that always passes to check that the test harness
        * is working.
        */
       public void initialize() {
         assertTrue(1 == 1);
       }
       
       
	  /**
	   * Establish a testing framework by initializing appropriate objects.
	   */
	  protected void setUp() throws Exception
	  {
	    super.setUp();	    
	  }
      
      
	  /**
	   * Release any objects and closes database connections after tests 
	   * are complete.
	   */
	  protected void tearDown() throws Exception
	  {	    
	    super.tearDown();
	  }
	  
      
	  /**
	   * Test a successful download process through http protocol
	   */
	  public void testGenerateInsertSQL() throws SQLException, DataNotMatchingMetadataException
	  {
		  HSQLAdapter adapter = new HSQLAdapter();
		  AttributeList attributeList = null;
          String tableName = "table1"; 
          Vector oneRowData = new Vector();
          String sql = null;
          
          // Test that a null attribute list throws a SQLException
          try
          {
             sql = 
               adapter.generateInsertSQL(attributeList, tableName, oneRowData);
             assertTrue(false);
          }
          catch(SQLException e)
          {
        	  assertTrue(true);
          }
          catch(DataNotMatchingMetadataException e)
          {
        	  System.err.println("The error is "+e.getMessage());
          }
          
          Attribute attribute1     = null;
          TextDomain domain = new TextDomain();
          String name1 = "name1";
          String id1   = "id1";
          String label1 = "label";
          String description = "description";
          String unit = "unit";
          String unitType = "unitType";
          String measurementScale = "scale";   
          attribute1 = new Attribute(id1, name1, label1, description, unit,
                               unitType, measurementScale, domain);
          Attribute attribute2     = null;
          String numberType = "natural";
          Double min = new Double(1);
          Double max = new Double(900);
          NumericDomain domain2 = new NumericDomain(numberType, min, max);
          String name2 = "name2";
          String id2   = "id2";
          String label2 = "label";
          String description2 = "description";
          String unit2 = "unit";
          String unitType2 = "unitType";
          String measurementScale2 = "scale";   
          attribute2 = new Attribute(id2, name2, label2, description2, unit2,
                               unitType2, measurementScale2, domain2);
          Attribute attribute3     = null;
          numberType = "real";
          min = new Double(1);
          max = new Double(900);
          NumericDomain domain3 = new NumericDomain(numberType, min, max);
          String name3 = "name3";
          String id3   = "id3";
          String label3 = "label";
          String description3 = "description";
          String unit3 = "unit";
          String unitType3 = "unitType";
          String measurementScale3 = "scale";   
          attribute3 = new Attribute(id3, name3, label3, description3, unit3,
                               unitType3, measurementScale3, domain3);
          attributeList = new AttributeList();
          attribute1.setDBFieldName(attribute1.getName());
          attribute2.setDBFieldName(attribute2.getName());
          attribute3.setDBFieldName(attribute3.getName());
          attributeList.add(attribute1);
          attributeList.add(attribute2);
          attributeList.add(attribute3);
          
          
          // Test that generateInsertSQL() fails when the data vector is empty
          try
          {
             sql = 
                adapter.generateInsertSQL(attributeList, tableName, oneRowData);
             assertTrue(false);
          }
          catch(SQLException e)
          {
        	  assertTrue(true);
          }
          catch(DataNotMatchingMetadataException e)
          {
        	  System.err.println("The error is "+e.getMessage());
          }
          
          String value1= "data1";
          String value2 = "1";
          String value3 = "data2";
          oneRowData.add(value1);
          oneRowData.add(value2);
          oneRowData.add(value3);
          
          // Test that generateInsertSQL() fails when one of the data values
          // is the wrong type
          try
          {
             sql = 
                adapter.generateInsertSQL(attributeList, tableName, oneRowData);
             assertTrue(false);
          }
          catch(SQLException e)
          {
        	  assertTrue(true);
          }
          catch(DataNotMatchingMetadataException e)
          {
        	  System.err.println("The error is "+e.getMessage());
          }

          // Test that generateInsertSQL() generates the correct INSERT
          // statement when given good values
          oneRowData.remove(2);
          String correctValue = "2.2";
          oneRowData.add(correctValue);
          sql = adapter.generateInsertSQL(attributeList, tableName, oneRowData);
          assertEquals(
                       "INSERT INTO table1(name1,name2,name3) VALUES ('data1',1,2.2);", 
                       sql
                      );
	  }
	  
}

