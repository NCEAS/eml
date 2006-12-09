/**
 *    '$RCSfile: SelectionItem.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-12-09 01:16:17 $'
 *   '$Revision: 1.4 $'
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
package org.ecoinformatics.datamanager.database;

import java.sql.SQLException;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;


/**
 * Represents a table and field that can be selected on in a SQL query. 
 * It has two parts: entity name and attribute name. Those names are real names
 * in DB.
 * 
 * @author tao
 *
 */
public class SelectionItem 
{
    // Class fields, constants
    private static final String SEPERATOR = ".";
    private static final String  BLANKSTR = "";     

    
    // Instance fields
	private Entity       entity = null;
	private Attribute attribute = null;
	
	
	/**
	 * Constructor
     * 
	 * @param entity
	 * @param attribute
	 */
	public SelectionItem(Entity entity, Attribute attribute)
	{
		this.entity    = entity;
		this.attribute = attribute;
	}
	
    
	/**
	 * Gets one selection item string (real name in DB) in sql query string.
     * 
	 * @return string constains one selection item
	 * @throws UnWellFormedQueryException
	 */
	public String toSQLString() throws UnWellFormedQueryException
	{
		String selectionItem = "";
		String entityName = null;
		String attributeName = null;
        
		// get entity name first
		if (entity != null)
		{
			try
    		{
    		   entityName = DataManager.getDBTableName(entity); 
    		}
    		catch (SQLException sqle)
    		{
    			System.err.println("entity name is null "+sqle.getMessage());
    		}
            
			if (entityName != null && !entityName.trim().equals(BLANKSTR))
			{
				selectionItem = entityName + SEPERATOR;
			}
		}
        
		// get attribute name. If attribute is null, throw an exception
		if (attribute != null)
		{
			try
			{
				attributeName = DataManager.getDBFieldName(entity, attribute);
			}
			catch(SQLException sqle)
			{
				System.err.println("attribute name is null "+sqle.getMessage());
			}
            
			if (attributeName != null && !attributeName.trim().equals(BLANKSTR))
			{
				selectionItem = selectionItem + attributeName;
			}
			else
			{
				throw new UnWellFormedQueryException(
                   UnWellFormedQueryException.SELECTION_ATTRIBUTE_NAME_IS_NULL);
			}
		}
		else
		{
			throw new UnWellFormedQueryException(
                        UnWellFormedQueryException.SELECTION_ATTRIBUTE_IS_NULL);
		}
		return selectionItem;
	}

}
