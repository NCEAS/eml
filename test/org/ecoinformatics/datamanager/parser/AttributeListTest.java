package org.ecoinformatics.datamanager.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * @author Jing Tao
 *
 * JUnit tests for the AttributeList class.
 */

public class AttributeListTest extends TestCase
{
  /*
   * Instance fields
   */
  private AttributeList list      = null;
  
  
  /*
   * Class methods
   */
  
  /**
   * Creates a suite of tests to be run together.
   */
   public static Test suite()
   {
     TestSuite suite = new TestSuite();
     suite.addTest(new AttributeListTest("testIdSetterAndGetter"));
     suite.addTest(new AttributeListTest("testIsReference"));
     suite.addTest(new AttributeListTest("testReferenceIdGetterAndSetter"));
     suite.addTest(new AttributeListTest("testParentEntityGetterAndSetter"));
     suite.addTest(new AttributeListTest("testAddAttribute"));
     return suite;
   }
   
   
  /*
   * Constructors
   */
   
  public AttributeListTest (String name)
  {
    super(name);
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
    list = new AttributeList();
  }

  
  /**
   * Releases any objects after tests are complete.
   */
  protected void tearDown() throws Exception
  {
	list = null;
    super.tearDown();
  }
  

  /**
   * Tests the getId() and setId methods.
   */
  public void testIdSetterAndGetter()
  {
	  String id = "1";
	  list.setId(id);
	  String gotId = list.getId();
	  assertEquals(id, gotId);
  }
  

  /**
   * Tests the isReference() method.
   */
  public void testIsReference()
  {
	  boolean reference = false;
	  list.setReference(reference);
	  boolean getReference = list.isReference();
	  assertTrue(reference == getReference);
  }
  

  /**
   * Tests the getReferenceId() and setReferenceId() methods.
   */
  public void testReferenceIdGetterAndSetter()
  {
	  String referenceId= "2";
	  list.setReferenceId(referenceId);
	  String gotReferenceId = list.getReferenceId();
	  assertEquals(referenceId, gotReferenceId);
  }
  

  /**
   * Tests the getParent() and setParent() methods.
   */
  public void testParentEntityGetterAndSetter()
  {
	  Entity entity         = null;
	  String id             = "001";
	  String name           = "newEntity";
	  String description    = "test";
	  Boolean caseSensitive = new Boolean(false);
	  String  orientation   = "column";
	  int     numRecords    = 200;
	  entity = new Entity(id, name, description,caseSensitive,orientation,numRecords);
	  list.setParent(entity);
	  Entity gotEntity = list.getParent();
	  assertEquals(entity, gotEntity);
  }


  /**
   * Tests the getAttributes() method.
   *
   */
  public void testAddAttribute()
  {
	  TextDomain domain = new TextDomain();
	  String attributeName = "name";
	  String attributeId   = "id";
	  Attribute attribute = new Attribute(attributeId, attributeName, domain);
	  list.add(attribute);
	  Attribute[] attributes = list.getAttributes();
	  Attribute gotAttribute = attributes[0];
	  assertEquals(attribute, gotAttribute);  
  }

}

