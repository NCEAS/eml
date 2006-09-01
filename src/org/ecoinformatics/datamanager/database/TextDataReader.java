/**
 *    '$RCSfile: TextDataReader.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-09-01 17:19:58 $'
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
package org.ecoinformatics.datamanager.database;

import java.io.InputStream;
import java.util.Vector;

public abstract class TextDataReader {

  /*
   * Class fields
   */
  
  
  /*
   * Instance fields
   */

  private InputStream reader = null;
  
  
  /*
   * Constructors
   */
  
  
  /*
   * Class methods
   */
  
  
  /*
   * Instance methods
   */
   
  /**
   * Method to get a vector which contains one row of data.
   * 
   * @return
   */
  public abstract Vector getOneRowDataVector() throws Exception;
   
  
  /**
   * Gets the reader.
   * 
   * @return
   */
  public InputStream getReader()
  {
	  return reader;
  }
   
  
  /**
   * Sets the input reader.
   * 
   * @param reader
   */
  public void setReader(InputStream reader)
  {
	  this.reader = reader;
  }
  
}
