package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ecoinformatics.datamanager.parser.Constraint;
import org.ecoinformatics.datamanager.parser.UniqueKey;
import org.ecoinformatics.datamanager.parser.UnWellFormedConstraintException;

/**
 * @author Jing Tao
 *
 */

public class TextWidthFixedDataFormatTest extends TestCase
{
  private TextWidthFixedDataFormat format      = null;
  private int                      fieldWidth1 = 10;
  private int                      fieldWidth2 = 20;

  public TextWidthFixedDataFormatTest (String name)
  {
    super(name);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    format = new TextWidthFixedDataFormat(fieldWidth1);
  }

  protected void tearDown() throws Exception
  {
    format = null;
    super.tearDown();
  }


  /**
   * Method to test getter and Setter for LineNumber
   *
   */
  public void testLineNumberGetterAndSetter()
  {
	  long lineNumber = 101;
	  format.setLineNumber(lineNumber);
	  long gotLineNumber = format.getLineNumber();
	  assertTrue(lineNumber == gotLineNumber);
	  
  }
  
  
  /**
   * Test getter and setter method for field start column number
   *
   */
  public void testFieldStartColumnGetterAndSetter()
  {
	  int setNumber = 101;
	  format.setFieldStartColumn(setNumber);
	  int gotNumber = format.getFieldStartColumn();
	  assertTrue(setNumber == gotNumber);
  }
  
  /**
   * Test getter and setter method for field width
   *
   */
  public void testFieldWidthGetterAndSetter()
  {
	  int setNumber = 102;
	  format.setFieldWidth(setNumber);
	  int gotNumber = format.getFieldWidth();
	  assertTrue(setNumber == gotNumber);
  }

  /**
  * Run an initial test that always passes to check that the test
  * harness is working.
  */
  public void initialize()
  {
    assertTrue(1 == 1);
  }


  /**
  * Create a suite of tests to be run together
  */
  public static Test suite()
  {
    TestSuite suite = new TestSuite();
    suite.addTest(new TextWidthFixedDataFormatTest("initialize"));
    suite.addTest(new TextWidthFixedDataFormatTest("testLineNumberGetterAndSetter"));
    suite.addTest(new TextWidthFixedDataFormatTest("testFieldStartColumnGetterAndSetter"));
    suite.addTest(new TextWidthFixedDataFormatTest("testFieldWidthGetterAndSetter"));
    return suite;
  }


}
