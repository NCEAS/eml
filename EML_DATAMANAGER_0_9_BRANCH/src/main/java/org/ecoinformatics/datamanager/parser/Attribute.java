/**
 *    '$RCSfile: Attribute.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-11-16 21:45:07 $'
 *   '$Revision: 1.6 $'
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

import java.util.ArrayList;
import java.util.Vector;
import org.ecoinformatics.datamanager.parser.Domain;


/**
 * @author tao
 * 
 * This object represents an Attribute.  A Attribute stores information about
 * a scalar value that is used in a Step in the pipeline.
 */
public class Attribute extends DataObjectDescription
{
  
  /*
   * Class fields
   */

  /**unit type for standard stmml units**/
  public static String STANDARDUNIT = "STANDARDUNIT";
  /**unit type for custom units**/
  public static String CUSTOMUNIT = "CUSTOMUNIT";

  
  /*
   * Instance fields
   */
  
  private String dbFieldName = null;    // legal field name stored in DB
  private String label;
  private String unit;
  private String unitType;
  private String measurementScale;
  //private String precision;
  private Domain domain;
  private Vector missingValueCode = new Vector();
  private ArrayList<StorageType> storageTypeArray = 
          new ArrayList<StorageType>();

  // This is an auxiliary variable to store an attribute's 
  // most appropriate attribute type, as determined by the
  // database adapter logic.
  private String attributeType;
  
  
  /*
   * Constructors
   */
  
  /**
   * Constructs an Attribute, specifying its id, name, and domain.
   */
  public Attribute(String id, String name, Domain dom)
  {
      this(id, name, null, null, dom);
  }

  
  /**
   * Constructs an Attribute, specifying its id, name, description, and
   * domain.
   */
  public Attribute(String id, String name, String description, Domain dom)
  {
	  this(id, name, description, null, dom);
  }

  
  /**
   * Constructs an Attribute, specifying its id, name, description, unit, and
   * domain.
   */
  public Attribute(String id, String name, String description,
                   String unit, Domain dom)
  {
      this(id, name, null, description, unit, null, null, dom);
  }
  
  
  /**
   * Constructor for extra local params
   * 
   * @param id    the id of the attribute
   * @param name  the name of the attribute
   * @param label the label of the attribute
   * @param description the description of the attribute
   * @param unit  the unit of the attribute
   * @param unitType the type of the attribute. defined by STANDARDUNIT or
   *              CUSTOMUNIT
   * @param measurementScale the measurement scale of the attribute
   * @param dom   the Domain of the attribute
   */
  public Attribute(String id, String name, String label, String description, 
                   String unit, String unitType, String measurementScale,
                   Domain dom)
  {
    super(id, name, description);
    
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


  /*
   * Instance methods
   */
  
  /**
   * Accesses the attributeType instance variable.
   * 
   * @return  the value of the attributeType, a String
   */
  public String getAttributeType() {
    return this.attributeType;
  }
  
  
  /**
   * Gets the unit for this attribute
   * 
   * @return  a string representing the unit
   */
  public String getUnit()
  {
      return this.unit;
  }

  
  /**
   * Gets the label for this attribute.
   * 
   * @return  a string representing the label
   */
  public String getLabel()
  {
    return label;
  }

  
  /**
   * Gets the unit type for this attribute.
   * 
   * @return  a string representing the unit type
   */
  public String getUnitType()
  {
    return unitType;
  }

  
  /**
   * Gets the measurementScale for this attribute
   * 
   * @return  a string representing the measurement scale.
   */
  public String getMeasurementScale()
  {
    return measurementScale;
  }

 
  /**
  public void setParent(Entity p)
  {
    parentTable = p;
  }


  public Entity getParent()
  {
    return parentTable;
  }
  */

  
  /**
   * Gets the Domain for this Attribute
   * 
   * @return  the Domain object for the attribute
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
  /*public String toXml()
    {
        StringBuffer x = new StringBuffer();
        x.append("<attribute id=\"");
        x.append(getId());
        x.append("\">\n");
        appendElement(x, "attributeName", getName());
        //appendElement(x, "dataType", getDataType());
        appendElement(x, "attributeDescription", getDefinition());
        appendElement(x, "unit", getUnit());
        x.append("</attribute>\n");

        return x.toString();
    }*/
    
  
  /**
   * Method to add missing value code into a vector. This method will be used 
   * to store the missing value code found in the metadata.
   * 
   * @param code   a string representing the missing value code found in the
   *               metadata
   */
  public void addMissingValueCode(String code) {
    if (code != null) {
      missingValueCode.add(code);
    }
  }
    
  
  /**
   * Method to return an array which stores missing value codes. If this
   * attribute doesn't contain any missing value codes, an empty array will be
   * returned.
   * 
   * @return  a string array holding the missing value codes
   */
  public String[] getMissingValueCode() {
    if (missingValueCode == null || missingValueCode.size() == 0) {
      return null;
    } 
    else {
      int size = missingValueCode.size();
      String[] list = new String[size];
      
      for (int i = 0; i < size; i++) {
        list[i] = (String) missingValueCode.elementAt(i);
      }
      
      return list;
    }
  }
  

  /**
   * Gets the value of the dbFieldName field.
   * 
   * @return  dbFieldName field, a String
   */
  public String getDBFieldName() {
    return dbFieldName;
  }

  
  /**
   * Sets the attributeType instance variable.
   * 
   * @parameter  the attributeType value to set
   */
  public void setAttributeType(String attributeType) {
    this.attributeType = attributeType;
  }
  
  
  /**
   * Sets the value of the dbFieldName field.
   * 
   * @param  name, the String value to set
   */
  public void setDBFieldName(String name) {
    dbFieldName = name;
  }

  
  /**
   * Adds a StorageType object to the StorageType array list.
   * 
   * @param  storageType, the object to be added to the array list
   */
  public void addStorageType(StorageType storageType) {
    if (storageType != null) {
      this.storageTypeArray.add(storageType);
    }
  }
  

  /**
   * Gets the value of the storageTypeArray instance variable.
   * 
   * @return  storageTypeArray, an ArrayList<StorageType>
   */
  public ArrayList<StorageType> getStorageTypeArray() {
    return this.storageTypeArray;
  }

}
