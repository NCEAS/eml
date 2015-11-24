/**
 *    '$RCSfile: TextDataReader.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-11-01 00:28:24 $'
 *   '$Revision: 1.3 $'
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

/**
 * 
 * An abract class which can read row data from a text data input stream.
 *
 */
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
   * Gets a vector which contains one row of data. The first row data will
   * be got in the first time calling this method. After calling this
   * method, the cursor will go to next row. At the end of the input stream,
   * an empty vector will be returned. The data will be stored as String
   * element in the Vector
   * 
   * @return Vector constains one row data
   */
  public abstract Vector<String> getOneRowDataVector() throws Exception;
   
  
  /**
   * Gets the reader.
   * 
   * @return the inpustream from text data
   */
  public InputStream getReader()
  {
	  return reader;
  }
   
  
  /**
   * Sets the input stream of text data.
   * 
   * @param reader the input stream of text data
   */
  public void setReader(InputStream reader)
  {
	  this.reader = reader;
  }
  
}
