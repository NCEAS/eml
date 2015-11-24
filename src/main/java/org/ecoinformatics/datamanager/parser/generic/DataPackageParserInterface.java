/**
 *    '$RCSfile: DataPackageParserInterface.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2007-10-18 00:45:08 $'
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
package org.ecoinformatics.datamanager.parser.generic;

import java.io.InputStream;

import org.ecoinformatics.datamanager.parser.DataPackage;
import org.xml.sax.InputSource;

/**
 * This interface specifies methods that a datapackage parser must implement
 * All implementations must be capable of returning a valid DataPackage object 
 * after parsing a metadata document that describes such a DataPackage.
 * DataManager functionality should not vary depending on the underlying 
 * parser implementation.
 * 
 * @author leinfelder
 *
 */
public interface DataPackageParserInterface {

	/**
	 * Parses the data package using InputSource object as input.
	 * 
	 * @param source The InputSource which contains metadata source
	 */
	public void parse(InputSource source) throws Exception;

	/**
	 * Parses the data package using InputStream object as input.
	 * 
	 * @param is The InputStream which contains metadata source
	 */
	public void parse(InputStream is) throws Exception;

	/**
	 * Method to get the parsed DataPackage metadata object.
	 * 
	 * @return the value of the DataPackage object representing the parsed metadata
	 */
	public DataPackage getDataPackage();

}