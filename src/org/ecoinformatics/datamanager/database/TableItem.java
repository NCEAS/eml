/**
 *    '$RCSfile: TableItem.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-12-09 01:17:59 $'
 *   '$Revision: 1.4 $'
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

import java.sql.SQLException;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.parser.Entity;


/**
 * This class reprents one entity in a "FROM" clause in SQL query.
 * 
 * @author tao
 */
public class TableItem 
{
    /*
     * Instance fields
     */
	private Entity entity = null;
	
	
    
	/**
	 * Constructor
     * @param entity entity object which will be in "From" clause
	 */
    public TableItem(Entity entity)
    {
    	this.entity = entity;
    }
    
    
    /**
     * Gets the table name in DB.
     * 
     * @return table name string in DB
     * @throws UnWellFormedQueryException if entity is null or table name is 
     *         null will throw exception
     */
    public String toSQLString() throws UnWellFormedQueryException
    {
    	String tableName = null;
        
    	if (entity != null)
    	{
    		try
    		{
    		   tableName = DataManager.getDBTableName(entity); 
    		}
    		catch (SQLException sqle)
    		{
    			System.err.println("entity name is null "+sqle.getMessage());
    		}
    		
    		if (tableName == null || tableName.trim().equals(""))
    		{
    			
    		   throw new UnWellFormedQueryException(
                      UnWellFormedQueryException.TABLEITEM_ENTITY_NAME_IS_NULL);
    		
    		}
    	}
    	else
    	{
    		throw new UnWellFormedQueryException(
                           UnWellFormedQueryException.TABLEITEM_ENTITY_IS_NULL);
    	}
        
    	return tableName;
    }
    
}
