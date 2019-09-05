/**
 *    '$RCSfile: UserId.java,v $'
 *
 *     '$Author$'
 *       '$Date$'
 *   '$Revision$'
 *
 *  For Details: http://kepler.ecoinformatics.org
 *
 * Copyright (c) 2019 The Regents of the University of California.
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
 * This object represent the userId - part of the party. 
 * It has a value and attribute - directory. 
 * @author tao
 *
 */
public class UserId {
    
    private String value = null;
    private String directory = null;
    
    /**
     * Default constructor
     */
    public UserId() {
        
    }

    /**
     * Get the value of the user id
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value of the user id
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get the value of the directory
     * @return
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Set the value of the directory
     * @param directory
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

}
