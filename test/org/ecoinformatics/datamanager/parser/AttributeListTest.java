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

public class AttributeListTest extends TestCase
{
  private AttributeList list      = null;
  
  public AttributeListTest (String name)
  {
    super(name);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    list = new AttributeList();
  }

  protected void tearDown() throws Exception
  {
	list = null;
    super.tearDown();
  }
  
  public void testIdSetterAndGetter()
  {
	  String id = "1";
	  list.setId(id);
	  String gotId = list.getId();
	  assertEquals(id, gotId);
  }
  
  public void testIsReference()
  {
	  boolean reference = false;
	  list.setReference(reference);
	  boolean getReference = list.isReference();
	  assertTrue(reference == getReference);
  }
  
  public void testReferenceIdGetterAndSetter()
  {
	  String referenceId= "2";
	  list.setReferenceId(referenceId);
	  String gotReferenceId = list.getReferenceId();
	  assertEquals(referenceId, gotReferenceId);
  }
  
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
   * Method to test getter and Setter for LineNumber
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
  
  


  /**
  * Create a suite of tests to be run together
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


}

