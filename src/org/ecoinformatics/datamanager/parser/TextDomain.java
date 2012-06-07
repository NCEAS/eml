/**
 *    '$RCSfile: TextDomain.java,v $'
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

//import org.kepler.objectmanager.data.DataType;
//import org.kepler.objectmanager.data.DataTypeResolver;
//import org.kepler.objectmanager.data.Domain;
//import org.kepler.objectmanager.data.UnresolvableTypeException;

/**
 * @author tao
 * 
 * This class will store info for text domain type.
 */
public class TextDomain implements Domain
{
    /*
     * Instance fields
     */
    
    //private DataType dataType;
    //private DataTypeResolver resolver = DataTypeResolver.instanceOf();
    private String definition;
    private String[] pattern;
    private String source;
    
    
    /*
     * Constructors
     */
    
    /**
     * Constructor for TextDomain class.
     * 
     * @throws UnresolvableTypeException
     */
    public TextDomain() 
    {
        
    }
    
  
    /*
     * Instance methods
     */
    
    /**
     * Gets the value of the definition field.
     * 
     * @return the definition value, a String
     */
    public String getDefinition()
    {
        return definition;
    }
    
    
    /**
     * Sets the value of the definition field.
     * 
     * @param definition, the definition value to set.
     */
    public void setDefinition(String definition)
    {
        this.definition = definition;
    }
    
    
    /**
     * Gets the value of the pattern field.
     * 
     * @return pattern, a String[]
     */
    public String[] getPattern()
    {
        return pattern;
    }
    
    
    /**
     * Sets the value of the pattern field to a list of Strings.
     * 
     * @param pattern, the pattern to set, a String[]
     */
    public void setPattern(String[] pattern)
    {
        this.pattern = pattern;
    }
    
    
    /**
     * Gets the value of the source field.
     * 
     * @return source, a String
     */
    public String getSource()
    {
        return source;
    }
    
    
    /**
     * Sets the value of the source field to a String.
     * 
     * @param source, the String value to set
     */
    public void setSource(String source)
    {
        this.source = source;
    }
    
}
