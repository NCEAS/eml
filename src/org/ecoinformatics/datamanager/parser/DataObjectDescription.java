/**
 *    '$RCSfile: DataObjectDescription.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-10-31 21:00:40 $'
 *   '$Revision: 1.4 $'
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

//import org.kepler.objectmanager.data.db.DSTableFieldIFace;


/**
 * A DataObjectDescription stores information about a DataItem that is used 
 * in a Step in the pipeline. It is the parent class of Attribute and Entity.
 * 
 * @author tao
 */
public class DataObjectDescription 
{
  /*
   * Instance fields
   */
  
  protected String id;
  protected String name;
  // protected String dataType;
  protected String definition;
  // protected Vector missingValueCode = new Vector();

  
  /*
   * Constructors
   */
  
  /*
   * Construct a DataObjectDescription, specifying its id and name.
   */
  public DataObjectDescription(String id, String name) {
    this(id, name, null);
  }


  /**
   * Construct a DataObjectDescription, specifying its id, name, and 
   * definition.
   */
  public DataObjectDescription(String id, String name, String definition) {
    if (id == null) {
      this.id = "";
    } 
    else {
      this.id = id;
    }
    
    if (name == null) {
      this.name = "";
    } 
    else {
      this.name = name.trim();
    }

    if (definition == null) {
      this.definition = "";
    } 
    else {
      this.definition = definition;
    }
  }

  
  /**
   * Gets the unique ID for this data object.
   * 
   * @return  a string representing the id for this data object
   */
  public String getId() {
    return this.id;
  }

  
  /**
   * Gets the name for this data object.
   * 
   * @return  a string representing the name for this data object
   */
  public String getName() {
    return this.name;
  }

   
  /**
   * Gets the definition for this data object.
   * 
   * @return  a string representing the definition for this data object
   */
  public String getDefinition() {
    return this.definition;
  }

  
  /**
   * Boolean to determine whether this data object is equal to another data
   * object.
   * 
   * @param  didesc   the other DataObjectDescription to which this one is
   *                  being compared
   * @return          true if all of the fields of didesc are equal to the 
   *                  fields of this object, else false
   */
  public boolean equals(DataObjectDescription didesc) {
    if (didesc.getId().trim().equals(this.id.trim()) && 
        didesc.getName().trim().equals(this.name.trim()) && 
        didesc.getDefinition().trim().equals(this.definition.trim())
       ) {
      return true;
    }

    return false;
  }
  

  /**
   * Sets the identifier for this data item.
   * 
   * @param  id  the identifier value to be set
   */
  public void setId(String id) {
    this.id = id;
  }

  
  /**
   * Sets the name for this data item.
   * 
   * @param  name  the name value to be set
   */
  public void setName(String name) {
    this.name = name;
  }

   
  /**
   * Sets the definition for this data item.
   * 
   * @param  definition  the definition value to be set
   */
  public void setDefinition(String definition) {
    this.definition = definition;
  }

  
  /**
   * Produces a string representation of the data object, using just its name.
   * 
   * @return  the name of the data object
   */
  public String toString() {
    return name;
  }
    

  /**
   * Utility method for writing out XML elements.
   * 
   * @param x     the string buffer to append to
   * @param name  the name of the element to create
   * @param value the value for the element
   */
  protected static void appendElement(StringBuffer x, 
                                      String name, 
                                      String value) {
    x.append("<");
    x.append(name);
    x.append(">");
    x.append(value);
    x.append("</");
    x.append(name);
    x.append(">\n");
  }

}
