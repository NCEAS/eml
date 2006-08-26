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

public class AttributeTest extends TestCase
{
  private Attribute attribute     = null;
  private TextDomain domain = new TextDomain();
  private String name = "name";
  private String id   = "id";
  private String label = "label";
  private String description = "description";
  private String unit = "unit";
  private String unitType = "unitType";
  private String measurementScale = "scale";
  public AttributeTest (String name)
  {
    super(name);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    attribute = new Attribute(id, name, label, description, unit,
                       unitType, measurementScale, domain);
  }

  protected void tearDown() throws Exception
  {
	attribute = null;
    super.tearDown();
  }
  
  public void testIdGetterMethod()
  {
	  String gotId = attribute.getId();
	  assertEquals(id, gotId);
	  String gotName = attribute.getName();
	  assertEquals(name, gotName);
	  String gotLabel = attribute.getLabel();
	  assertEquals(label, gotLabel);
	  String gotDescription = attribute.getDefinition();
	  assertEquals(description, gotDescription);
	  String gotUnit = attribute.getUnit();
	  assertEquals(unit,gotUnit);
	  String gotUnitType = attribute.getUnitType();
	  assertEquals(unitType, gotUnitType);
	  String gotScale = attribute.getMeasurementScale();
	  assertEquals(measurementScale, gotScale);
	  Domain gotDomain = attribute.getDomain();
	  assertEquals(domain, gotDomain);
  }
  
  public void testMissingValueSetterAndGetter()
  {
	  String[] list1 = attribute.getMissingValueCode();
	  assertTrue(list1 == null);
	  String missingValue1 = "00000";
	  String missingValue2 = "999999";
	  attribute.addMissingValueCode(missingValue1);
	  attribute.addMissingValueCode(missingValue2);
	  String[] list2= attribute.getMissingValueCode();
	  assertEquals(list2[0], missingValue1);
	  assertEquals(list2[1], missingValue2);
  }
  
 
  /**
  * Create a suite of tests to be run together
  */
  public static Test suite()
  {
    TestSuite suite = new TestSuite();
    suite.addTest(new AttributeTest("testIdGetterMethod"));
    suite.addTest(new AttributeTest("testMissingValueSetterAndGetter"));
    return suite;
  }


}

