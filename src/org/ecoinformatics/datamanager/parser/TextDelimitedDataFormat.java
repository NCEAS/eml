/**
 *    '$RCSfile: TextDelimitedDataFormat.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-10-31 21:00:40 $'
 *   '$Revision: 1.2 $'
 *
 *  For Details: http://kepler.ecoinformatics.org
 *
 * Copyright (c) 2004 The Regents of the University of California.
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
 * 
 * This class will contain the info about TextDelimitedDataFormat.
 */
public class TextDelimitedDataFormat implements TextComplexDataFormat
{
    /*
     * Instance fields
     */
    private String fieldDelimiter;   //string that separates fields in text
    private String collapseDelimiters; //treat the consecutive delimters as 
                                      //single or not
    private long   lineNumber;       //the physical line number
    private String[] quoteCharacterArray;  //for escape delimiter
  
    
    /*
     * Constructors
     */

    /**
     * Constructor
     * 
     * @param fieldDelimiter  the delimiter for field, a String
     */
    public TextDelimitedDataFormat(String fieldDelimiter)
    {
        super();
        this.fieldDelimiter = fieldDelimiter;
    }
    
    
    /*
     * Instance methods
     */
    
    /**
     * Gets the value of the collapseDelimiters field.
     * 
     * @return the collapseDelimiters value
     */
    public String getCollapseDelimiters()
    {
        return collapseDelimiters;
    }
    
    
    /**
     * Sets the value of the collapseDelimiters field.
     * 
     * @param collapseDelimiters The collapseDelimiters value to set
     */
    public void setCollapseDelimiters(String collapseDelimiters)
    {
        this.collapseDelimiters = collapseDelimiters;
    }
    
    
    /**
     * Gets the value of the fieldDelimiter field.
     * 
     * @return the fieldDelimiter value
     */
    public String getFieldDelimiter()
    {
        return fieldDelimiter;
    }
    
    
    /**
     * Sets the value of the fieldDelimiter field.
     * 
     * @param fieldDelimiter the fieldDelimiter value to set
     */
    public void setFieldDelimiter(String fieldDelimiter)
    {
        this.fieldDelimiter = fieldDelimiter;
    }
    
    
    /**
     * Gets the value of the lineNumber field.
     * 
     * @return the lineNumber
     */
    public long getLineNumber()
    {
        return lineNumber;
    }
    
    
    /**
     * Sets the value of the lineNumber field.
     * 
     * @param lineNumber The lineNumber value to set
     */
    public void setLineNumber(long lineNumber)
    {
        this.lineNumber = lineNumber;
    }
    
    
    /**
     * Gets the value of the quoteCharacterArray field.
     * 
     * @return  the quoteCharacterArray value, a String[]
     */
    public String[] getQuoteCharacterArray()
    {
        return quoteCharacterArray;
    }
    
    
    /**
     * Sets the value of the quoteCharacterArray field to a 
     * list of Strings.
     * 
     * @param quoteCharacterArray The array value to set, a String[]
     */
    public void setQuoteCharacterArray(String[] quoteCharacterArray)
    {
        this.quoteCharacterArray = quoteCharacterArray;
    }
    
}
