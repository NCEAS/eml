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

public class TextDelimitedDataFormatTest extends TestCase
{
  private TextDelimitedDataFormat format      = null;
  private String delimiter                    = ";";

  public TextDelimitedDataFormatTest (String name)
  {
    super(name);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    format = new TextDelimitedDataFormat(delimiter);
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
   * Test getter and setter method for CollapseDelimiter
   *
   */
  public void testCollapseDelimiterGetterAndSetter()
  {
	  String collapseDelimiter = " ";
	  format.setCollapseDelimiter(collapseDelimiter);
	  String gotCollapseDelimiter = format.getCollapseDelimiter();
	  assertEquals(collapseDelimiter, gotCollapseDelimiter);
  }
  
  /**
   * Test getter and setter method for QuoteCharater
   *
   */
  public void testQuoteCharaterGetterAndSetter()
  {
	  String quote1 = "'";
	  String quote2 = "\"";
	  String[] quote = new String[2];
	  quote[0] = quote1;
	  quote[1] = quote2;
	  format.setQuoteCharater(quote);
	  String[] gotQuote = format.getQuoteCharater();
	  assertEquals(quote, gotQuote);
  }
  
  /**
   * Test getter and setter method for CollapseDelimiter
   *
   */
  public void testFieldDelimiterGetterAndSetter()
  {
	  String gotDelimiter = format.getFieldDelimiter();
	  assertEquals(gotDelimiter, delimiter);
	  String newDelimiter = ".";
	  format.setFieldDelimiter(newDelimiter);
	  String gotDelimiter2 = format.getFieldDelimiter();
	  assertEquals(newDelimiter, gotDelimiter2);
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
    suite.addTest(new TextDelimitedDataFormatTest("testLineNumberGetterAndSetter"));
    suite.addTest(new TextDelimitedDataFormatTest("testCollapseDelimiterGetterAndSetter"));
    suite.addTest(new TextDelimitedDataFormatTest("testQuoteCharaterGetterAndSetter"));
    suite.addTest(new TextDelimitedDataFormatTest("testFieldDelimiterGetterAndSetter"));
    return suite;
  }


}


