package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * @author tao
 *
 * JUnit tests for the DateTimeDomain class.
 */
public class DateTimeDomainTest extends TestCase
{
  /*
   * Instance fields
   */
  private DateTimeDomain   domain  = null;
  

  /*
   * Constructors
   */
  
  public DateTimeDomainTest (String name)
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
     suite.addTest(new DateTimeDomainTest("initialize"));
     suite.addTest(new DateTimeDomainTest("testMinGetterAndSetter"));
     suite.addTest(new DateTimeDomainTest("testMaxGetterAndSetter"));
     suite.addTest(new DateTimeDomainTest("testTimePrecisionGetterAndSetter"));
     suite.addTest(new DateTimeDomainTest("testFormatStringGetterAndSetter"));
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
    domain = new DateTimeDomain();
  }

  
  /**
   * Releases any objects after tests are complete.
   */
  protected void tearDown() throws Exception
  {
    domain = null;
    super.tearDown();
  }


  /**
   * Tests getMinimum() and setMinimum() methods.
   */
  public void testMinGetterAndSetter()
  {
	  double min = 1;
	  domain.setMinimum(min);
	  double gotMin = domain.getMinimum();
	  assertTrue(min == gotMin);
	  
  }
  
  
  /**
   * Tests getMaximum() and setMaximum() methods.
   */
  public void testMaxGetterAndSetter()
  {
	  double max = 1;
	  domain.setMaximum(max);
	  double gotMax = domain.getMaximum();
	  assertTrue(max == gotMax);
	  
  }
  
  
  /**
   * Tests the getDateTimePrecision() and setDateTimePrecision() methods.
   */
  public void testTimePrecisionGetterAndSetter()
  {
	  double precision = 1.0;
	  domain.setDateTimePrecision(precision);
	  double gotPrecision = domain.getDateTimePrecision();
	  assertTrue(precision == gotPrecision);
  }
  
  
  /**
   * Tests the getFormatString() method.
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

}

