/**
 *    '$RCSfile: EnumeratedDomain.java,v $'
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

/**
 * @author tao
 * This class store the info for Enumerated domain info
 */
public class EnumeratedDomain implements Domain
{
    //private String numberType;
    //private DataType dataType;
    //private DataTypeResolver resolver = DataTypeResolver.instanceOf();
    private Vector info;
    
    /**
     * Constructor for this domain
     */
    public EnumeratedDomain() 
    {
       
    }
    
    /**
     * @param info The info to set.
     */
    public void setInfo(Vector info)
    {
        this.info = info;
    }
    
    /**
     * Method getDomainInfo.
     * get the ith domain info item,
     * or an empty string if i is greater than the number of items
     * @param i
     * @return String
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
     * Method getDomainInfoCount.
     * get the number of items in the info vector
     * @return int
     */
    public int getDomainInfoCount()
    {
      return info.size();
    }

}
