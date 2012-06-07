package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * @author tao
 * 
 * JUnit tests for the TextWidthFixedDataFormat class.
 *
 */
public class TextWidthFixedDataFormatTest extends TestCase
{
  /*
   * Instance fields
   */
  
  private TextWidthFixedDataFormat format      = null;
  private int                      fieldWidth1 = 10;
  //private int                      fieldWidth2 = 20;

  
  /*
   * Constructors
   */
  
  public TextWidthFixedDataFormatTest (String name)
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
     suite.addTest(new TextWidthFixedDataFormatTest("initialize"));
     suite.addTest(
             new TextWidthFixedDataFormatTest("testLineNumberGetterAndSetter"));
     suite.addTest(
       new TextWidthFixedDataFormatTest("testFieldStartColumnGetterAndSetter"));
     suite.addTest(
             new TextWidthFixedDataFormatTest("testFieldWidthGetterAndSetter"));
     return suite;
   }
  
  
  /*
   * Instance methods
   */
  
  

  /**
   * Establishes a testing framework by initializing appropriate objects.
   */
  protected void setUp() throws Exception
  {
    super.setUp();
    format = new TextWidthFixedDataFormat(fieldWidth1);
  }

  /**
   * Releases any objects after tests are complete.
   */
  protected void tearDown() throws Exception
  {
    format = null;
    super.tearDown();
  }


  /**
   * Tests getter and setter methods for the lineNumber field.
   */
  public void testLineNumberGetterAndSetter()
  {
	  long lineNumber = 101;
	  format.setLineNumber(lineNumber);
	  long gotLineNumber = format.getLineNumber();
	  assertTrue(lineNumber == gotLineNumber);
	  
  }
  
  
  /**
   * Tests getter and setter methods for the fieldStartColumn field.
   */
  public void testFieldStartColumnGetterAndSetter()
  {
	  int setNumber = 101;
	  format.setFieldStartColumn(setNumber);
	  int gotNumber = format.getFieldStartColumn();
	  assertTrue(setNumber == gotNumber);
  }
  
  
  /**
   * Tests getter and setter methods for the fieldWidth field.
   */
  public void testFieldWidthGetterAndSetter()
  {
	  int setNumber = 102;
	  format.setFieldWidth(setNumber);
	  int gotNumber = format.getFieldWidth();
	  assertTrue(setNumber == gotNumber);
  }

  
  /**
  * Runs an initial test that always passes to check that the test
  * harness is working.
  */
  public void initialize()
  {
    assertTrue(1 == 1);
  }

}
