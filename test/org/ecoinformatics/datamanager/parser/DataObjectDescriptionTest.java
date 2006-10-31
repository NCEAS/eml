package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author tao
 * 
 * JUnit tests for the DataObjectDescription class.
 *
 */
public class DataObjectDescriptionTest extends TestCase 
{
    /*
     * Instance fields
     */
  
	private DataObjectDescription description = null;
	protected String id = "101";
    protected String name = "name";
    //protected String dataType;
    protected String definition = "test";

    /*
     * Constructors
     */
    
	public DataObjectDescriptionTest (String name)
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
        suite.addTest(new DataObjectDescriptionTest("testGetterMethods"));
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
	    description = new DataObjectDescription(id, name, definition);
		super.setUp();
	    
	}

    
    /**
     * Tests the getId(), getName(), and getDefinition() methods.
     *
     */
	public void testGetterMethods()
	{
		String gotId = description.getId();
		assertEquals(id, gotId);
		String gotName = description.getName();
		assertEquals(name, gotName);
		String gotDefinition = description.getDefinition();
		assertEquals(definition, gotDefinition);
	}

    
    /**
     * Releases any objects after tests are complete.
     */
	protected void tearDown() throws Exception
	{
		description = null;
	    super.tearDown();
	}
    
}
