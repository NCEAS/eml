package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Jing Tao
 *
 */
public class DataObjectDescriptionTest extends TestCase 
{
	private DataObjectDescription description = null;
	protected String id = "101";
    protected String name = "name";
    //protected String dataType;
    protected String definition = "test";
	public DataObjectDescriptionTest (String name)
	{
	    super(name);
	}

	protected void setUp() throws Exception
	{
	    description = new DataObjectDescription(id, name, definition);
		super.setUp();
	    
	}
	
	public void testGetterMethods()
	{
		String gotId = description.getId();
		assertEquals(id, gotId);
		String gotName = description.getName();
		assertEquals(name, gotName);
		String gotDefinition = description.getDefinition();
		assertEquals(definition, gotDefinition);
	}

	protected void tearDown() throws Exception
	{
		description = null;
	    super.tearDown();
	}
    
	 /**
	  * Create a suite of tests to be run together
	  */
	  public static Test suite()
	  {
	    TestSuite suite = new TestSuite();
	    suite.addTest(new DataObjectDescriptionTest("testGetterMethods"));
	    return suite;
	  }
}
