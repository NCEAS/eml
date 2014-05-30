/**
 *    '$RCSfile: Domain.java,v $'
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
 * This object represents an Attribute Domain, a set of restrictions
 * on legal values that the attribute can contain.
 * 
 * @author tao
 */
public interface Domain
{
  /*
   * Class fields
   */
  public static final int DOM_NONE = 0;
  public static final int DOM_NUMBERTYPE = 1;
  public static final int DOM_BOUNDED = 2;
  public static final int DOM_ENUMERATED = 3;
  public static final int DOM_ENUMENTITY = 4;
  public static final int DOM_TEXT = 5;
  public static final int DOM_DATETIME = 6;
  public static final int DOM_REFERENCE = 7;

  public static final String typeStrings[] = {
    "None",
    "NumberType",
    "Bounded",
    "Enumerated",
    "EnumEntity",
    "Text",
    "DateTime",
    "Reference"
  };
  
  
  /*
   * Instance methods
   */
  
  /**
   * Method to get DataType
   * @return
   */
  //public DataType getDataType();

}
