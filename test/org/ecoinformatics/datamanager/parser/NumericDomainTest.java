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

public class NumericDomainTest extends TestCase
{
  private NumericDomain domain      = null;
  private String numberType         = "nature";
  private Double min                = new Double(1);
  private Double max                = new Double(900);

  public NumericDomainTest(String name)
  {
    super(name);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    domain = new NumericDomain(numberType, min , max);
  }

  protected void tearDown() throws Exception
  {
    domain = null;
    super.tearDown();
  }


  /**
   * Method to test getter NumberType
   *
   */
  public void testNumberTypeGetter()
  {
	  
	  String gotNumberType = domain.getNumberType();
	  assertEquals(numberType, gotNumberType);
	  
  }
  
  
  /**
   * Test getter and setter method for Minimium 
   *
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
   * Test getter and setter method for Maxmium 
   *
   */
  public void testMaxGetterAndSetter()
  {
	  Double gotMax = domain.getMaxmum();
	  assertEquals(gotMax, max);
	  Double newMax = new Double(2);
	  domain.setMaxmum(newMax);
	  Double newGotMax = domain.getMaxmum();
	  assertEquals(newMax, newGotMax);
  }
  /**
   * Test getter and setter method for precision
   *
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


  /**
  * Create a suite of tests to be run together
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


}

