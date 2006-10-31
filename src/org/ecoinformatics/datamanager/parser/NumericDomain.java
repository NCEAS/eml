/**
 *    '$RCSfile: NumericDomain.java,v $'
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
 * This class represents a numeric domain.
 */
public class NumericDomain implements Domain
{
  /*
   * Instance fields
   */
   private String numberType = null;
   //private DataType dataType = null;
   private double precision  = 0;
   private Double minimum    = null;
   private Double maximum     = null;
   //private DataTypeResolver resolver = DataTypeResolver.instanceOf();

   
   /*
    * Constructors
    */
   
   /**
    * Constructor of numeric domain
    * 
    * @param numberType  the number type of the numeric domain. In EML, a 
    *                    measurement’s numberType should be defined as real, 
    *                    natural, whole or integer.
    * @param minimum     lower bound for values this numeric domain
    * @param maximum     upper bound for values in this numeric domain
    */
   public NumericDomain(String numberType, Double minimum, Double maximum) 
   {
       this.numberType = numberType;
       this.minimum    = minimum;
       this.maximum     = maximum;
       //dataType        = resolver.resolveDataType(this.numberType, 
       //                                           this.minimum, 
       //                                           this.maximum);
   }
   
   
   /**
    * Method to get the numberType value.
    * 
    * @return  the numberType, a String. In EML, a measurement’s numberType 
    *          should be defined as real, natural, whole or integer.
    */
   public String getNumberType()
   {
      return numberType;
   }
   
   
   /**
    * Gets the maximum (upper bound) value for this numeric domain.
    * 
    * @return the value of the maximum field
    */
    public Double getMaximum()
    {
        return maximum;
    }

    
    /**
     * Sets the maximum (upper bound) value for this numeric domain.
     * 
     * @param maximum  The maximum value to set
     */
    public void setMaximum(Double maximum)
    {
        this.maximum = maximum;
    }

    
    /**
     * Gets the minumum (lower bound) value for this numeric domain.
     * 
     * @return the value of the minimum field
     */
    public Double getMinimum()
    {
        return minimum;
    }

    
    /**
     * Sets the minimum (lower bound) value for this numeric domain.
     * 
     * @param minimum  The minimum value to set
     */
    public void setMinimum(Double minimum)
    {
        this.minimum = minimum;
    }

    
    /**
     * Gets the value of the precision field.
     * 
     * @return the value of the precision field
     */
    public double getPrecision()
    {
        return precision;
    }

    
    /**
     * Sets the value of the precision field.
     * 
     * @param  The precision value to set
     */
    public void setPrecision(double precision)
    {
        this.precision = precision;
    }

}
