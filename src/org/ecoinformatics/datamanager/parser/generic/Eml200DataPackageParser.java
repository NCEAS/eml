/**
 *    '$RCSfile: Eml200DataPackageParser.java,v $'
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


/**
 * This is plugin Parser which parses EML 2.0.0 metadata files to 
 * get the metadata information which decribes data file.
 * 
 * @author leinfelder
 */
public class Eml200DataPackageParser extends GenericDataPackageParser
{
   
    /**
     * Default constructor passes eml-specific packageId location
     * and uses the defaults from parent class for other params.
     * Note that this is a very trivial subclass and likely only useful 
     * for illustrative purposes or for being overly explicit in coding. 
     */
    public Eml200DataPackageParser() {
    	//only need to set the packaageIdPath value, use defaults for others
    	super("//eml/@packageId");
    	
    }
    
}
