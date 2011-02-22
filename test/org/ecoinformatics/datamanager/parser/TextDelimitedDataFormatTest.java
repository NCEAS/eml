package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * @author tao
 * 
 * JUnit tests for the TextDelimitedDataFormat class.
 *
 */
public class TextDelimitedDataFormatTest extends TestCase
{
  /*
   * Instance fields
   */
  private TextDelimitedDataFormat format      = null;
  private String delimiter                    = ";";

  
  /*
   * Constructors
   */
  
  public TextDelimitedDataFormatTest (String name)
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
     suite.addTest(new TextDelimitedDataFormatTest("testLineNumberGetterAndSetter"));
     suite.addTest(new TextDelimitedDataFormatTest("testCollapseDelimitersGetterAndSetter"));
     suite.addTest(new TextDelimitedDataFormatTest("testQuoteCharaterGetterAndSetter"));
     suite.addTest(new TextDelimitedDataFormatTest("testFieldDelimiterGetterAndSetter"));
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
    format = new TextDelimitedDataFormat(delimiter);
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
   * Method to test getter and setter methods for the lineNumber field.
   */
  public void testLineNumberGetterAndSetter()
  {
	  long lineNumber = 101;
	  format.setLineNumber(lineNumber);
	  long gotLineNumber = format.getLineNumber();
	  assertTrue(lineNumber == gotLineNumber);
	  
  }
  

  /**
   * Test getter and setter methods for the collapseDelimiters field.
   */
  public void testCollapseDelimitersGetterAndSetter()
  {
	  String collapseDelimiters = " ";
	  format.setCollapseDelimiters(collapseDelimiters);
	  String gotCollapseDelimiters = format.getCollapseDelimiters();
	  assertEquals(collapseDelimiters, gotCollapseDelimiters);
  }
  
  
  /**
   * Test getter and setter methods for the quoteCharacter field.
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
   * Test getter and setter methods for the fieldDelimiter field.
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

}
