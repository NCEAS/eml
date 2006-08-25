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

public class TextDomainTest extends TestCase
{
  private TextDomain domain      = null;
  private int                      fieldWidth1 = 10;
  private int                      fieldWidth2 = 20;

  public TextDomainTest (String name)
  {
    super(name);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    domain = new TextDomain();
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
  public void testDefinitionGetterAndSetter()
  {
	  String definition = "101";
	  domain.setDefinition(definition);
	  String gotDefinition = domain.getDefinition();
	  assertEquals(definition, gotDefinition);
	  
  }
  
  
  /**
   * Test getter and setter method for field start column number
   *
   */
  public void testSourceGetterAndSetter()
  {
	  String source = "101";
	  domain.setSource(source);
	  String gotSource = domain.getSource();
	  assertEquals(source, gotSource);
  }
  
  /**
   * Test getter and setter method for field width
   *
   */
  public void testPatternGetterAndSetter()
  {
	  String pattern1 = "102";
	  String[] pattern = new String[1];
	  pattern[0] = pattern1;
	  domain.setPattern(pattern);
	  String[] gotPattern = domain.getPattern();
	  assertEquals(pattern, gotPattern);
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
    suite.addTest(new TextDomainTest("initialize"));
    suite.addTest(new TextDomainTest("testDefinitionGetterAndSetter"));
    suite.addTest(new TextDomainTest("testSourceGetterAndSetter"));
    suite.addTest(new TextDomainTest("testPatternGetterAndSetter"));
    return suite;
  }


}

