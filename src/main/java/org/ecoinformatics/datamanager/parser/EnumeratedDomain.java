/**
 *    '$RCSfile: EnumeratedDomain.java,v $'
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

import java.util.Vector;


/**
 * @author tao
 * 
 * This class stores the info for Enumerated domain.
 */
public class EnumeratedDomain implements Domain
{
    /*
     * Instance fields
     */
  
    //private String numberType;
    //private DataType dataType;
    //private DataTypeResolver resolver = DataTypeResolver.instanceOf();
    private Vector info;
    
    
    /*
     * Constructors
     */
    
    /**
     * Constructor for this domain
     */
    public EnumeratedDomain() 
    {
       
    }
    
    
    /*
     * Instance methods
     */
    
    /**
     * Sets the info field.
     * 
     * @param info The info Vector value to set.
     */
    public void setInfo(Vector info)
    {
        this.info = info;
    }
    
    
    /**
     * Method getDomainInfo gets the ith domain info item,
     * or an empty string if i is greater than the number of items.
     * 
     * @param i  the index into the info vector
     * @return   the string value of the object found at that index, or an 
     *           empty string if i is greater than the number of items in the 
     *           vector
     */
    public String getDomainInfo(int i)
    {
      if( i < info.size() )
      {
        Object o = info.get(i);
        
        if( o != null ) {
          return o.toString();
        }
      }
      
      return "";
    }

    
    /**
     * Gets the number of items in the info vector.
     * 
     * @return  the number of items in the info vector
     */
    public int getDomainInfoCount()
    {
      return info.size();
    }

}
