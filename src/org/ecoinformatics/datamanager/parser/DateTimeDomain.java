/**
 *    '$RCSfile: DateTimeDomain.java,v $'
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


/**
 * @author tao
 * This class will handle DateTimeDomain
 */
public class DateTimeDomain implements Domain
{
   
   //private DataType dataType;
   //private DataTypeResolver resolver = DataTypeResolver.instanceOf();
   private String formatString;
   private double dateTimePrecision;
   private double minimum;
   private double maxmum;
   

   /**
    * Constructor  setup numberType for this domain
    *
    */
   public DateTimeDomain() 
   {
       
   }
   
  
   
   /**
    * @return Returns the dateTimePrecision.
    */
   public double getDateTimePrecision()
   {
       return dateTimePrecision;
   }
   /**
    * @param dateTimePrecision The dateTimePrecision to set.
    */
   public void setDateTimePrecision(double dateTimePrecision)
   {
       this.dateTimePrecision = dateTimePrecision;
   }
   /**
    * @return Returns the formatString.
    */
   public String getFormatString()
   {
       return formatString;
   }
   /**
    * @param formatString The formatString to set.
    */
   public void setFormatString(String formatString)
   {
       this.formatString = formatString;
   }
   /**
    * @return Returns the maxmum.
    */
   public double getMaxmum()
   {
       return maxmum;
   }
   /**
    * @param maxmum The maxmum to set.
    */
   public void setMaxmum(double maxmum)
   {
       this.maxmum = maxmum;
   }
   /**
    * @return Returns the minimum.
    */
   public double getMinimum()
   {
       return minimum;
   }
   /**
    * @param minimum The minimum to set.
    */
   public void setMinimum(double minimum)
   {
       this.minimum = minimum;
   }
}
