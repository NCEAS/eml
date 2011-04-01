/**
 *    '$RCSfile: QualityCheck.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2011-03-17 21:40:51 $'
 *   '$Revision: 1.28 $'
 *
 *  For Details: http://kepler.ecoinformatics.org
 *
 * Copyright (c) 2003 The Regents of the University of California and
 * The University of New Mexico. All rights reserved.
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

package org.ecoinformatics.datamanager.quality;


/**
 * @author dcosta
 * @version 1.0
 * @created 17-Mar-2011 2:58:56 PM
 * 
 * An object of the QualityCheck class represents a single quality check
 * that is performed on a metadata document, a data entity, or a check
 * of congruency between metadata and data. 
 */
public class QualityCheck {
  
  /*
   * Enumeration classes
   */
  
  public enum Status { valid, info, warn, error };
  
  
  /*
   * Class variables
   */
  
  // Boolean switch to determine whether quality checking is turned on or off
  // in the application that is using the Data Manager library. It is the
  // application's responsibility to set this value by reading in the value
  // of the 'qualityAudit' property and calling the QualityCheck.setQualityAudit() 
  // static method, passing in the appropriate value.
  private static Boolean qualityAudit = new Boolean(false);
  
  
  /*
   * Instance variables
   */

	// The name of the quality check.
	private String name = "";

	// String describing the quality check.
  private String description = "";
	
	// The relevant XPath in the EML schema 
	private String emlPath = "";
	
	// The expected result 
	private String expected = "";
	
	// The found result
	private String found = "";
	
	// The status of this quality check (see Status enum for possible values)
	private Enum<Status> status;
	
	// An explanation as to why the found result differs from the expected result
	private String explanation = "";
	
	// Suggested remedy
	private String suggestion = "";
	
	// A reference that serves as the rationale behind this quality check
	private String reference = "";

	
	/*
	 * Constructors
	 */

	/**
	 * Constructs a QualityCheck object, initializing its 'name' value.
	 */
  public QualityCheck(String name) {
    this.name = name;
  }

  
  /*
   * Class methods
   */

  /**
   * Returns the qualityAudit value, a Boolean. Other classes in the
   * Data Manager library call this method to determine whether quality
   * auditing operations should or should not be executed.
   * 
   * @return  qualityAudit. If true, quality auditing is turned on.
   */
  public static Boolean getQualityAudit() {
    return qualityAudit;
  }
  
  
  /**
   * Sets the value of qualityAudit using a string parameter. If the string
   * equals (ignore case) "true", then quality auditing will be turned on.
   * Any other string value results in quality auditing being turned off (the default
   * setting).
   * 
   * @param trueOrFalse   a string argument. "true" (ignore case) turns on quality
   *                      auditing
   */
  public static void setQualityAudit(String trueOrFalse) {
    Boolean aBoolean = new Boolean(trueOrFalse);
    qualityAudit = aBoolean;
  }
	
  
  /**
   * Sets the value of qualityAudit using a boolean parameter. If the
   * argument is true, then quality auditing will be turned on. If false,
   * then quality auditing will be turned off.
   * 
   * @param trueOrFalse    true turns on quality auditing
   */
  public static void setQualityAudit(boolean trueOrFalse) {
    Boolean aBoolean = new Boolean(trueOrFalse);
    qualityAudit = aBoolean;
  }
  
  
  /*
   * Instance methods
   */
  
  
  /**
   * Boolean to test the equality of this QualityCheck objects with another
   * QualityCheck object. There are times when the same quality check information
   * might be generated more than once for a given entity but we only want to store
   * one copy of it. This gives us the means to check whether a copy of this object
   * is already found in a collection.
   * 
   * @param    qualityCheck  the object to be tested for equality against this object
   * @return   true if the two objects are equal, false otherwise
   */
  public boolean equals(QualityCheck qualityCheck) {
    return (this.description.equals(qualityCheck.getDescription()) &&
            this.emlPath.equals(qualityCheck.getEmlPath()) &&
            this.expected.equals(qualityCheck.getExpected()) &&
            this.explanation.equals(qualityCheck.getExplanation()) &&
            this.found.equals(qualityCheck.getFound()) &&
            this.name.equals(qualityCheck.getName()) &&
            this.reference.equals(qualityCheck.getReference()) &&
            this.status.equals(qualityCheck.getStatus()) &&
            this.suggestion.equals(qualityCheck.getSuggestion())
           );
  }
  
  
  /**
   * Converts the content of this QualityCheck object to an XML
   * representation.
   * 
   * @return  a string holding the XML representation
   */
	public String toXML() {
	  String xmlString = null;
	  StringBuffer xmlStringBuffer = new StringBuffer("");
    final String qualityType = "data";
	  final String system = "knb";
	  final String indent = "  ";
	  xmlStringBuffer.append(indent + indent + "<qualityCheck" +
	    " qualityType=\"" + qualityType + "\"" +
	    " system=\"" + system + "\"" +
	    " status=\"" + status.toString() + "\" >\n");
	  xmlStringBuffer.append(indent + indent + indent + "<name>" + name + "</name>\n");
	  xmlStringBuffer.append(indent + indent + indent + "<description>" + description + "</description>\n");
	  xmlStringBuffer.append(indent + indent + indent + "<expected>" + expected + "</expected>\n");
	  xmlStringBuffer.append(indent + indent + indent + "<found>" + found + "</found>\n");
    xmlStringBuffer.append(indent + indent + indent + "<explanation>" + explanation + "</explanation>\n");
    xmlStringBuffer.append(indent + indent + indent + "<suggestion>" + suggestion + "</suggestion>\n");
    xmlStringBuffer.append(indent + indent + indent + "<reference>" + reference + "</reference>\n");
	  xmlStringBuffer.append(indent + indent + "</qualityCheck>\n");
	  
	  xmlString = xmlStringBuffer.toString();
	  return xmlString;
	}
	
	
	/* Accessor methods (not individually documented) */
  
  public String getDescription() {
    return description;
  }

  
  public String getEmlPath() {
    return emlPath;
  }

  
  public String getExpected() {
    return expected;
  }

  
  public String getExplanation() {
    return explanation;
  }

  
  public String getFound() {
    return found;
  }

  
  public String getName() {
    return name;
  }

  
  public String getReference() {
    return reference;
  }

  
  public Enum<Status> getStatus() {
    return status;
  }

  
  public String getSuggestion() {
    return suggestion;
  }

  
  public void setDescription(String description) {
    this.description = description;
  }

  
  public void setEmlPath(String emlPath) {
    this.emlPath = emlPath;
  }

  
  public void setExpected(String expected) {
    this.expected = expected;
  }

  
  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  
  public void setFound(String found) {
    this.found = found;
  }

  
  public void setName(String name) {
    this.name = name;
  }


  public void setReference(String reference) {
    this.reference = reference;
  }

  
  public void setStatus(Enum<Status> status) {
    this.status = status;
  }

  
  public void setSuggestion(String suggestion) {
    this.suggestion = suggestion;
  }
  
}