/**
 *    '$RCSfile: AttributeList.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-11-16 21:45:59 $'
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

import java.util.Vector;


/**
 * @author tao
 *
 * This class reprents of list of attributes in the entity object
 */
public class AttributeList
{
  /*
   * Instance Fields
   */
   private Vector attributes   = new Vector();
   private String id           = null;
   private boolean isReference = false;
   private String referenceId  = null;
   private Entity parentTable  = null;
   
   
  /*
   * Constructors
   */
   
  /**
   * Constructs an AttributeList object
   */
  public AttributeList() {
    attributes = new Vector();
  }
   
  
  /*
   * Instance methods
   */
   
  /**
   * Gets the attribute field
   * 
   * @return   an array of Attribute objects, or null if there are no
   *           attributes in the list
   */
  public Attribute[] getAttributes() {
    if (attributes == null || attributes.size() == 0) {
      return null;
    } 
    else {
      int size = attributes.size();
      Attribute[] list = new Attribute[size];
      
      for (int i = 0; i < size; i++) {
        list[i] = (Attribute) attributes.elementAt(i);
      }
      
      return list;
    }
  }
  
  
  /**
   * @param attributes   The attributes to set.
   */
  /*
   * public void setAttributes(Vector attributes) { 
   *   this.attributes = attributes; 
   * }
   */
  
  
  /**
   * Gets the database field names for the attributes in this AttributeList.
   * 
   * @return   an array of Strings objects, or null if there are no
   *           attributes in the list. 
   */
  public String[] getDBFieldNames() {
    if (attributes == null || attributes.size() == 0) {
      return null;
    } 
    else {
      int size = attributes.size();
      String[] list = new String[size];
      
      for (int i = 0; i < size; i++) {
        Attribute attribute = (Attribute) attributes.elementAt(i);
        list[i] = attribute.getDBFieldName();
      }
      
      return list;
    }
  }
  
  
  /**
   * Gets the id.
   * 
   * @return  a string representing the id
   */
  public String getId() {
    return id;
  }
    
    
  /**
   * Sets the id
   * 
   * @param id  the id to set.
   */
  public void setId(String id) {
    this.id = id;
  }
    
    
  /**
   * Gets the isReference field
   * 
   * @return a boolean, the value of the isReference field
   */
  public boolean isReference() {
    return isReference;
  }
    
    
  /**
   * Sets the isReference field.
   * 
   * @param isReference  The isReference value to set, a boolean
   */
  public void setReference(boolean isReference) {
    this.isReference = isReference;
  }
    
    
  
  /**
   * Gets the referenceId field.
   * 
   * @return  a string representing the referenceId
   */
  public String getReferenceId() {
    return referenceId;
  }
    

  /**
   * Sets the referenceId field.
   * 
   * @param referenceId   a string representing the referenceId value to set
   */
  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }
    
    
  /**
   * Sets parentTable entity for this AttributeList.
   * 
   * @param  entity    the parent Entity for this attribute list.
   */
  public void setParent(Entity entity) {
    parentTable = entity;
  }
    
    
  /**
   * Gets the parent entity for this AtttributeList.
   * 
   * @return  an Entity, the parent entity for this attribute list
   */
  public Entity getParent() {
    return parentTable;
  }
    
    
  /**
   * Adds an Attribute to this attribute list.
   * 
   * @param  a  the Attribute to be added to the 'attributes' field
   */
  public void add(Attribute a) {
    attributes.addElement(a);
  }
  
}
