/**
 *    '$RCSfile: Attribute.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-08-18 01:41:10 $'
 *   '$Revision: 1.1 $'
 *
 *  For Details: http://kepler.ecoinformatics.org
 *
 * Copyright (c) 2003 The Regents of the University of California.
 * All rights reserved.
 * 
 * Permission is hereby granted, without written agreement and without
 * license or royalty fees, to use, copy, modify, and distribute this
 * software and its documentation for any purpose, provided that the
 * above copyright notice and the following two paragraphs appear in
 * all copies of this software.
 * 
 * IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 * FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 * ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN
 * IF THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 * 
 * THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 * PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY
 * OF CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT,
 * UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */

package org.ecoinformatics.datamanager.parser;

import java.util.Vector;

//import org.kepler.objectmanager.data.DataObjectDescription;
import org.ecoinformatics.datamanager.parser.Domain;

//import src.org.kepler.objectmanager.data.DataObjectDescription;



/**
 * This object represents an Attribute.  A Attribute stores information about
 * a scalar value that is used in a Step in the pipeline.
 */
public class Attribute extends DataObjectDescription
{
  /**unit type for standard stmml units**/
  public static String STANDARDUNIT = "STANDARDUNIT";
  /**unit type for custom units**/
  public static String CUSTOMUNIT = "CUSTOMUNIT";

  private String label;
  private String unit;
  private String unitType;
  private String measurementScale;
  private String precision;
  private Domain domain;
  private Vector missingValueCode = new Vector();

  
 

  
  /**
   * Construct a Attribute.
   */
  public Attribute(String id, String name, String type)
  {
      this(id, name, type, null, null, null);
  }

  /**
   * Construct a Attribute.
   */
  public Attribute(String id, String name, String type, String description)
  {
	  this(id, name, type, description, null, null);
  }

  /**
   * Construct a Attribute.
   */
  public Attribute(String id, String name, String type, String description,
          String unit, Domain dom)
  {
      this(id, name, null, description, unit, null, type, null, dom);
  }
  
  /**
   * Constructor for extra local params
   * @param name the name of the attribute
   * @param label the label of the attribute
   * @param definition the definition of the attribute
   * @param unit the unit of the attribute
   * @param unitType the type of the attribute. defined by STANDARDUNIT or
   * CUSTOMUNIT
   * @param storageType the data type of the attribute
   * @param measurementScale the scale of the attribute
   * @param precision the number of precise numbers to the right of the decimal
   */
  public Attribute(String id, String name, String label, String definition, String unit,
                   String unitType, String dataType, String measurementScale,
                   Domain dom)
  {
    super(id, name, dataType, definition);
    if (label == null) {
        this.label = "";
    } else {
        this.label = label;
    }
    if (unit == null) {
        this.unit = "";
    } else {
        this.unit = unit;
    }
    if (unitType == null) {
        this.unitType = "";
    } else {
        this.unitType = unitType;
    }
    if (measurementScale == null) {
        this.measurementScale = "";
    } else {
        this.measurementScale = measurementScale;
    }    
    domain = dom;
  }


  /**
   * Return the unit for this Attribute
   */
  public String getUnit()
  {
      return this.unit;
  }

  /**
   * accessor method
   */
  public String getLabel()
  {
    return label;
  }

  /**
   * accessor method
   */
  public String getUnitType()
  {
    return unitType;
  }

  /**
   * accessor method
   */
  public String getMeasurementScale()
  {
    return measurementScale;
  }

 
  /**public void setParent(Entity p)
  {
    parentTable = p;
  }

  public Entity getParent()
  {
    return parentTable;
  }
  */

  /**
   * Returns the domain.
   * @return Domain
   */
  public Domain getDomain()
  {
    return domain;
  }
  
 

  /*
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("NAME: ").append(name).append("\n");
    sb.append("id: ").append(id).append("\n");
    sb.append("dataType: ").append(dataType).append("\n");
    sb.append("description: ").append(description).append("\n");
    sb.append("label: ").append(label).append("\n");
    sb.append("definition: ").append(definition).append("\n");
    sb.append("unit: ").append(unit).append("\n");
    sb.append("unitType: ").append(unitType).append("\n");
    sb.append("measurementScale: ").append(measurementScale).append("\n");
    sb.append("precision: ").append(precision).append("\n");
    return sb.toString();
  }
  */

    /**
     * Serialize the data item in XML format.
     */
    public String toXml()
    {
        StringBuffer x = new StringBuffer();
        x.append("<attribute id=\"");
        x.append(getId());
        x.append("\">\n");
        appendElement(x, "attributeName", getName());
        appendElement(x, "dataType", getDataType());
        appendElement(x, "attributeDescription", getDefinition());
        appendElement(x, "unit", getUnit());
        x.append("</attribute>\n");

        return x.toString();
    }
    
    /**
     * Method to add missing value code into a vector. 
     * This method will be used to store the missing value code in metadata
     * @param code
     */
    public void addMissingValueCode(String code)
    {
        if (code != null )
        {
            missingValueCode.add(code);
        }
    }
    
    /**
     * Method to return the vector which store the missing value code.
     * If this attribute doesn't has the missing value code, empty
     * vector will be returned.
     * @return
     */
    public Vector getMissingValueCode()
    {
        return missingValueCode;
    }
    
   
    
}
