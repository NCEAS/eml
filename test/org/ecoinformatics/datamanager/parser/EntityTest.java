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

public class EntityTest extends TestCase
{
  private Entity entity         = null;
  private String id             = "001";
  private String name           = "newEntity";
  private String description    = "test";
  private Boolean caseSensitive = new Boolean(false);
  private String  orientation   = "column";
  private int     numRecords    = 200;
  public EntityTest (String name)
  {
    super(name);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    entity = new Entity(id, name, description,caseSensitive,orientation,numRecords);
  }

  protected void tearDown() throws Exception
  {
    entity = null;
    super.tearDown();
  }
  
  public void testGetterMethod()
  {
	  String gotId = entity.getId();
	  assertEquals(id, gotId);
	  String gotName = entity.getName();
	  assertEquals(name, gotName);
	  String gotDescription = entity.getDefinition();
	  assertEquals(gotDescription, description);
	  Boolean gotCaseSensitive = entity.getCaseSensitive();
	  assertEquals(gotCaseSensitive, caseSensitive);
	  String gotOrientation = entity.getOrientation();
	  assertEquals(gotOrientation, orientation);
	  int gotNumberRecords = entity.getNumRecords();
	  assertEquals(gotNumberRecords, numRecords);
  }
  
  public void testURLSetterAndGetter()
  {
	  String url="http;//knb.ecoinformatics.org";
	  entity.setURL(url);
	  String gotURL = entity.getURL();
	  assertEquals(url, gotURL);
  }
  
  public void testAddAttirubte()
  {
	  TextDomain domain = new TextDomain();
	  String attributeName = "name";
	  String attributeId   = "id";
	  Attribute attribute = new Attribute(attributeId, attributeName, domain);
	  entity.add(attribute);
	  Attribute[] list = entity.getAttributes();
	  Attribute gotAttribute = list[0];
	  assertEquals(attribute, gotAttribute);
  }
  
  public void testAddAttirubteListGetterAndSetter()
  {
	  TextDomain domain = new TextDomain();
	  String attributeName = "name";
	  String attributeId   = "id";
	  Attribute attribute = new Attribute(attributeId, attributeName, domain);
	  AttributeList list = new AttributeList();
	  list.add(attribute);
	  entity.setAttributeList(list);
	  AttributeList gotList = entity.getAttributeList();
	  assertEquals(list, gotList);
  }
  
  public void testIsSimpleDelimited()
  {
	  boolean isSimple = false;
	  entity.setSimpleDelimited(isSimple);
	  boolean gotIsSimple = entity.isSimpleDelimited();
	  assertTrue(isSimple == gotIsSimple);
  }
   
  
  public void testDelimiter()
  {
	  String delimiter = ".";
	  entity.setDelimiter(delimiter);
	  String gotDelimiter = entity.getDelimiter();
	  assertEquals(delimiter, gotDelimiter);
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
    suite.addTest(new EntityTest("initialize"));
    suite.addTest(new EntityTest("testGetterMethod"));
    suite.addTest(new EntityTest("testURLSetterAndGetter"));
    suite.addTest(new EntityTest("testAddAttirubte"));
    suite.addTest(new EntityTest("testAddAttirubteListGetterAndSetter"));
    suite.addTest(new EntityTest("testIsSimpleDelimited"));
    suite.addTest(new EntityTest("testDelimiter"));
    return suite;
  }


}
