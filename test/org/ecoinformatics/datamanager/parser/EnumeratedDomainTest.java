package org.ecoinformatics.datamanager.parser;

import java.util.Vector;

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

public class EnumeratedDomainTest extends TestCase
{
  private EnumeratedDomain domain      = null;
  private int                      fieldWidth1 = 10;
  private int                      fieldWidth2 = 20;

  public EnumeratedDomainTest (String name)
  {
    super(name);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    domain = new EnumeratedDomain();
  }

  protected void tearDown() throws Exception
  {
    domain = null;
    super.tearDown();
  }

  /**
   * Test getter and setter method for field width
   *
   */
  public void testMethods()
  {
	  String pattern1 = "102";
	  String pattern2 = "202";
	  Vector info = new Vector();
	  info.add(pattern1);
	  info.add(pattern2);
	  domain.setInfo(info);
	  int length = domain.getDomainInfoCount();
	  assertTrue(length == 2);
	  String gotPattern1 = domain.getDomainInfo(0);
	  assertEquals(pattern1, gotPattern1);
	  String gotPattern2 = domain.getDomainInfo(1);
	  assertEquals(pattern2, gotPattern2);
  }

  /**
  * Run an initial test that always passes to check that the test
  * harness is working.
  */
  public void initialize()
  {
    assertTrue(1 == 1);
  }


  /**TextDomainTest
  * Create a suite of tests to be run together
  */
  public static Test suite()
  {
    TestSuite suite = new TestSuite();
    suite.addTest(new EnumeratedDomainTest("initialize"));
    suite.addTest(new EnumeratedDomainTest("testMethods"));
    return suite;
  }


}



