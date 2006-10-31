package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * @author tao
 *
 * JUnit tests for the TextDomain class.
 */

public class TextDomainTest extends TestCase
{
  /*
   * Instance fields
   */
  private TextDomain domain      = null;
  //private int                      fieldWidth1 = 10;
  //private int                      fieldWidth2 = 20;

  
  /*
   * Constructors
   */
  
  public TextDomainTest (String name)
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
     suite.addTest(new TextDomainTest("initialize"));
     suite.addTest(new TextDomainTest("testDefinitionGetterAndSetter"));
     suite.addTest(new TextDomainTest("testSourceGetterAndSetter"));
     suite.addTest(new TextDomainTest("testPatternGetterAndSetter"));
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
    domain = new TextDomain();
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
   * Tests getter and setter methods for the definition field.
   */
  public void testDefinitionGetterAndSetter()
  {
	  String definition = "101";
	  domain.setDefinition(definition);
	  String gotDefinition = domain.getDefinition();
	  assertEquals(definition, gotDefinition);
	  
  }
  
  
  /**
   * Tests getter and setter methods for the source field.
   */
  public void testSourceGetterAndSetter()
  {
	  String source = "101";
	  domain.setSource(source);
	  String gotSource = domain.getSource();
	  assertEquals(source, gotSource);
  }
  
  
  /**
   * Tests getter and setter methods for the pattern field.
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

}

