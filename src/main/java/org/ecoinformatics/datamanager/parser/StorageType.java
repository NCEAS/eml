/**
 *    '$RCSfile: TextDomain.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2011-02-23 00:00:00 $'
 *   '$Revision: 1.2 $'
 *
 * Copyright (c) 2011 The Regents of the University of California
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


/**
 * @author costa
 * 
 * This class stores info for storageType elements. An EML attribute can have
 * zero or more storageType elements. The storageType, when present, is a hint
 * from the metadata provider about the optimal data type for storing the
 * attribute in a given system. For example:
 * 
 * <storageType typeSystem="Java">double</storageType>
 * 
 * is a hint that the attribute should be represented in Java as data type
 * 'double'.
 * 
 */
public class StorageType {

    /*
     * Instance fields
     */
    
    private String textValue;   // text value of the storageType element
    private String typeSystem;  // optional typeSystem attribute string value
    
    
    /*
     * Constructors
     */
    
    
    /**
     * Constructs a StorageType object when all that's known is its textValue.
     * 
     * @param  textValue   the text value of the storageType EML element,
     *                     for example, "integer".
     */
    public StorageType(String textValue) {
      this(textValue, "");
    }
    
    
    /**
     * Constructs a StorageType object when both the textValue and the
     * optional typeSystem attribute value have been specified.
     * 
     * @param  textValue   the text value of the storageType EML element,
     *                     for example, "integer".
     * @param typeSystem   the value of the typeSystem attribute,
     *                     for example, "Java"
     */
    public StorageType(String textValue, String typeSystem) {
      this.textValue = textValue;
      this.typeSystem = typeSystem;
    }

    
    /*
     * Instance methods
     */
    
    /**
     * Gets the value of the textValue field.
     * 
     * @return the textValue value, a String
     */
    public String getTextValue() {
        return textValue;
    }
    
    
    /**
     * Gets the value of the typeSystem field. In EML, this is an
     * optional attribute of the storageType element.
     * 
     * @return the typeSystem value, a String
     */
    public String getTypeSystem() {
        return typeSystem;
    }
    
    
    /**
     * Sets the value of the textValue field.
     * 
     * @param textValue, the string to set
     */
    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }
    
    
    /**
     * Sets the value of the typeSystem field. In EML, this is an
     * optional attribute of the storageType element.
     * 
     * @param typeSystem, the string value to set
     */
    public void setTypeSystem(String typeSystem) {
        this.typeSystem = typeSystem;
    }
    
}
