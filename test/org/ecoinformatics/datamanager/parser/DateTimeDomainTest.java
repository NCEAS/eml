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

public class DateTimeDomainTest extends TestCase
{
  private DateTimeDomain   domain  = null;
  

  public DateTimeDomainTest (String name)
  {
    super(name);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    domain = new DateTimeDomain();
  }

  protected void tearDown() throws Exception
  {
    domain = null;
    super.tearDown();
  }


  /**
   * Method to test getter and Setter for LineNumber
   *
   */
  public void testMinGetterAndSetter()
  {
	  double min = 1;
	  domain.setMinimum(min);
	  double gotMin = domain.getMinimum();
	  assertTrue(min == gotMin);
	  
  }
  
  /**
   * Method to test getter and Setter for LineNumber
   *
   */
  public void testMaxGetterAndSetter()
  {
	  double max = 1;
	  domain.setMaxmum(max);
	  double gotMax = domain.getMaxmum();
	  assertTrue(max == gotMax);
	  
  }
  
  /**
   * Method to test getter and Setter for LineNumber
   *
   */
  public void testTimePrecisionGetterAndSetter()
  {
	  double precision = 1.0;
	  domain.setDateTimePrecision(precision);
	  double gotPrecision = domain.getDateTimePrecision();
	  assertTrue(precision == gotPrecision);
  }
  
  
  /**
   * Test getter and setter method for field start column number
   *
   */
  public void testFormatStringGetterAndSetter()
  {
	  String format = "YYYY-MM-DD";
	  domain.setFormatString(format);
	  String gotFormat = domain.getFormatString();
	  assertEquals(format, gotFormat);
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
    suite.addTest(new DateTimeDomainTest("testMinGetterAndSetter"));
    suite.addTest(new DateTimeDomainTest("testMaxGetterAndSetter"));
    suite.addTest(new DateTimeDomainTest("testTimePrecisionGetterAndSetter"));
    suite.addTest(new DateTimeDomainTest("testFormatStringGetterAndSetter"));
    return suite;
  }


}

