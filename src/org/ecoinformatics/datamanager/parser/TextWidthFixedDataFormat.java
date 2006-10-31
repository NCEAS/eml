/**
 *    '$RCSfile: TextWidthFixedDataFormat.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-10-31 21:00:40 $'
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
package org.ecoinformatics.datamanager.parser;

/**
 * This class expresses a fixed-width text format.
 * 
 * @author tao
 */
public class TextWidthFixedDataFormat implements TextComplexDataFormat
{
    /*
     * Instance fields
     */
    private long lineNumber = -1;  
                                // for records that span a couple physical lines
    private int fieldWidth = 0;         // width of field (in character number)
    private int fieldStartColumn = -1;  // start the field column number
    
    
    /*
     * Constructors
     */
    
    /**
     * Constructor with field width specified.
     * 
     * @param fieldWidth the field width, an int
     */
    public TextWidthFixedDataFormat(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }
    
    
    /*
     * Instance methods
     */
    
    /**
     * Sets line number.
     * 
     * @param lineNumber the lineNumber value to set
     */
    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }
    
    
    /**
     * Gets line number.
     * 
     * @return lineNumber, the value of the lineNumber field
     */
    public long getLineNumber() {
        return lineNumber;
    }
    
    
    /**
     * Sets field width.
     * 
     * @param fieldWidth the fieldWidth value to set, an int
     */
    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }
    
    
    /**
     * Gets the field width.
     *
     * @return fieldWidth, the fieldWidth value, an int
     */
    public int getFieldWidth() {
        return fieldWidth;
    }
    
    
    /**
     * Sets the fieldStartColumn value.
     * 
     * @param fieldStartColumn, the value to set, an int
     */
    public void setFieldStartColumn(int fieldStartColumn) {
        this.fieldStartColumn = fieldStartColumn;
    }
  
    
    /**
     * Gets the field start column.
     * 
     * @return fieldStartColumn, a long
     */
    public int getFieldStartColumn() {
        return fieldStartColumn;
    }

}
