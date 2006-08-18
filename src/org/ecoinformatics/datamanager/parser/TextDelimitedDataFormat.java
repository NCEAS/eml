/**
 *    '$RCSfile: TextDelimitedDataFormat.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-08-18 01:41:10 $'
 *   '$Revision: 1.1 $'
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
 * This class will contain the info about TextDelimitedFormat
 */
public class TextDelimitedDataFormat implements TextComplexDataFormat
{
    private String fieldDelimiter;
    private String collapseDelimiter;//treat the consecutive delimters as 
                                     //single or not
    private long   lineNumber;//the physical line number
    private String[] quoteCharater;//for escape delimiter
  

    /**
     * Constructor
     * @param fieldDelimiter  the delimiter for field
     */
    public TextDelimitedDataFormat(String fieldDelimiter)
    {
        super();
        this.fieldDelimiter = fieldDelimiter;
    }
    
    /**
     * @return Returns the collapseDelimiter.
     */
    public String getCollapseDelimiter()
    {
        return collapseDelimiter;
    }
    /**
     * @param collapseDelimiter The collapseDelimiter to set.
     */
    public void setCollapseDelimiter(String collapseDelimiter)
    {
        this.collapseDelimiter = collapseDelimiter;
    }
    /**
     * @return Returns the fieldDelimiter.
     */
    public String getFieldDelimiter()
    {
        return fieldDelimiter;
    }
    /**
     * @param fieldDelimiter The fieldDelimiter to set.
     */
    public void setFieldDelimiter(String fieldDelimiter)
    {
        this.fieldDelimiter = fieldDelimiter;
    }
    /**
     * @return Returns the lineNumber.
     */
    public long getLineNumber()
    {
        return lineNumber;
    }
    /**
     * @param lineNumber The lineNumber to set.
     */
    public void setLineNumber(long lineNumber)
    {
        this.lineNumber = lineNumber;
    }
    /**
     * @return Returns the quoteCharater.
     */
    public String[] getQuoteCharater()
    {
        return quoteCharater;
    }
    /**
     * @param quoteCharater The quoteCharater to set.
     */
    public void setQuoteCharater(String[] quoteCharater)
    {
        this.quoteCharater = quoteCharater;
    }
}
