package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Jing Tao
 *
 * JUnit tests for the NumericDomain class.
 */

public class NumericDomainTest extends TestCase
{
  /*
   * Instance fields
   */
  private NumericDomain domain      = null;
  private String numberType         = "nature";
  private Double min                = new Double(1);
  private Double max                = new Double(900);

  
  /*
   * Constructors
   */
  
  public NumericDomainTest(String name)
  {
    super(name);
  }

  
  /*
   * Class methods
   */
  
  /**
   * Create a suite of tests to be run together.
   */
   public static Test suite()
   {
     TestSuite suite = new TestSuite();
     suite.addTest(new NumericDomainTest("testNumberTypeGetter"));
     suite.addTest(new NumericDomainTest("testMinGetterAndSetter"));
     suite.addTest(new NumericDomainTest("testMaxGetterAndSetter"));
     suite.addTest(new NumericDomainTest("testPrecisionGetterAndSetter"));
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
    domain = new NumericDomain(numberType, min , max);
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
   * Tests getNumberType() method.
   *
   */
  public void testNumberTypeGetter()
  {
	  
	  String gotNumberType = domain.getNumberType();
	  assertEquals(numberType, gotNumberType);
  }
  
  
  /**
   * Tests getter and setter methods for minimum field.
   */
  public void testMinGetterAndSetter()
  {
	  Double gotMin = domain.getMinimum();
	  assertEquals(gotMin, min);
	  Double newMin = new Double(2);
	  domain.setMinimum(newMin);
	  Double newGotMin = domain.getMinimum();
	  assertEquals(newMin, newGotMin);
  }
  
  
  /**
   * Tests getter and setter methods for maximum field.
   */
  public void testMaxGetterAndSetter()
  {
	  Double gotMax = domain.getMaximum();
	  assertEquals(gotMax, max);
	  Double newMax = new Double(2);
	  domain.setMaximum(newMax);
	  Double newGotMax = domain.getMaximum();
	  assertEquals(newMax, newGotMax);
  }
  
  
  /**
   * Tests getter and setter method for precision field.
   */
  public void testPrecisionGetterAndSetter()
  {
	  double precision = 1;
	  domain.setPrecision(precision);
	  double gotPrecision = domain.getPrecision();
	  assertTrue(precision == gotPrecision);
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
