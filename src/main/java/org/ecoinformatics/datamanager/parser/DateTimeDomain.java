/**
 *    '$RCSfile: DateTimeDomain.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-10-31 21:00:40 $'
 *   '$Revision: 1.2 $'
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


/**
 * @author tao
 * 
 * This class will handle DateTimeDomain.
 */
public class DateTimeDomain implements Domain
{
   /*
    * Instance fields
    */
  
  //private DataType dataType;
  //private DataTypeResolver resolver = DataTypeResolver.instanceOf();
  private String formatString;
  private double dateTimePrecision;
  private double minimum;
  private double maximum;
   

  /*
   * Constructors
   */
  
  /**
   * Constructor setup numberType for this domain
   * 
   */
  public DateTimeDomain() {

  }
   
  
  /*
   * Instance methods
   */
  
  /**
   * Gets the dataTimePrecision value.
   * 
   * @return Returns the dateTimePrecision, a double.
   */
  public double getDateTimePrecision() {
    return dateTimePrecision;
  }


  /**
   * Sets the dataTimePrecision value.
   * 
   * @param dateTimePrecision The dateTimePrecision to set
   */
  public void setDateTimePrecision(double dateTimePrecision) {
    this.dateTimePrecision = dateTimePrecision;
  }


  /**
   * Gets the formatString value.
   * 
   * @return Returns the formatString.
   */
  public String getFormatString() {
    return formatString;
  }


  /**
   * Sets the formatString value.
   * 
   * @param formatString The formatString value to set.
   */
  public void setFormatString(String formatString) {
    this.formatString = formatString;
  }


  /**
   * Gets the maximum value.
   * 
   * @return  the maximum value, a double
   */
  public double getMaximum() {
    return maximum;
  }


  /**
   * Sets the maximum value.
   * 
   * @param maximum   The maximum value to set.
   */
  public void setMaximum(double maximum) {
    this.maximum = maximum;
  }


  /**
   * Gets the minimum value.
   * 
   * @return  the minimum value, a double
   */
  public double getMinimum() {
    return minimum;
  }


  /**
   * Sets the minimum value.
   * 
   * @param minimum The minimum value to set.
   */
  public void setMinimum(double minimum) {
    this.minimum = minimum;
  }
  
}
