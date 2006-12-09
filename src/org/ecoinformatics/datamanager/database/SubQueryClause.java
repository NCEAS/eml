/**
 *    '$RCSfile: SubQueryClause.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-12-09 01:17:19 $'
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

import java.sql.SQLException;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;

/**
 * This class represents a subquery in query class. It contains "IN" or "Not in"
 * part. The SubQueryClause should look like entityName.attributeName in (query)
 * @author tao
 *
 */
public class SubQueryClause implements ConditionInterface 
{
    // Class fields, constants
    private static final String IN = "IN";
    private static final String NOT_IN = "NOT IN";
    private static final String LEFT_PARENSIS = "(";
    private static final String RIGHT_PARENSIS = ")";
    
    
	// Instance fields
	private String operator = null;
	private Entity entity = null;
	private Attribute attribute = null;
	private Query query = null;
	
	/**
	 * Constructor. Initializes a sub-query clause with given entity, attribute,
     * operator and query. 
     * 
	 * @param entity    the entity information (It can be null if only 
     *                  has one entity in query)
	 * @param attribute the attribute information
	 * @param operator  operator should be IN or NOT IN
	 * @param query the query part
	 */
	public SubQueryClause(Entity entity, 
                          Attribute attribute, 
                          String operator, 
                          Query query)
	{
		this.entity = entity;
		this.attribute = attribute;
		this.operator = operator;
		this.query = query;
	}
	
    
	/**
	 * Transform a sub-query clause into a SQL string.
	 */
	public String toSQLString() throws UnWellFormedQueryException
	{
		if (attribute == null)
		{
			throw new UnWellFormedQueryException(
                         UnWellFormedQueryException.SUBQUERY_ATTRIBUTE_IS_NULL);
		}
        
		if (operator == null || 
            (!operator.equalsIgnoreCase(IN) && 
             !operator.equalsIgnoreCase(NOT_IN)
            )
           )
		{
			throw new UnWellFormedQueryException(
                       UnWellFormedQueryException.SUBQUERY_OPERATOR_IS_ILLEGAL);
		}
        
		if (query == null)
		{
			throw new UnWellFormedQueryException(
                             UnWellFormedQueryException.SUBQUERY_QUERY_IS_NULL);
		}
		
		StringBuffer sql = new StringBuffer();
        
		if (entity != null)
		{
			String entityName = null;
			try
    		{
    		   entityName = DataManager.getDBTableName(entity); 
    		}
    		catch (SQLException sqle)
    		{
    			System.err.println("entity name is null "+sqle.getMessage());
    		}
    		
			if (entityName != null && 
                !entityName.trim().equals(ConditionInterface.BLANK)
               )
			{
			  sql.append(entityName);
			  sql.append(ConditionInterface.SEPERATER);
			}
		}
        
		String attributeName = null;
		try
		{
			attributeName = DataManager.getDBFieldName(entity, attribute);
		}
		catch(SQLException sqle)
		{
			System.err.println("attribute name is null "+sqle.getMessage());
		}
        
		if (attributeName == null || 
            attributeName.trim().equals(ConditionInterface.BLANK))
		{
			throw new UnWellFormedQueryException(
                    UnWellFormedQueryException.SUBQUERY_ATTRIBUTE_NAME_IS_NULL);
		}
		else
		{
			sql.append(attributeName);
		}
        
		sql.append(ConditionInterface.SPACE);
		sql.append(operator);
		sql.append(ConditionInterface.SPACE);
		sql.append(LEFT_PARENSIS);
		sql.append(removeSemicolon(query.toSQLString()));
		sql.append(RIGHT_PARENSIS);
        
		return sql.toString();
	}
	
    
	/*
	 * Replaces the semicolon by space in the query string 
	 */
	private String removeSemicolon(String query)
	{
		String queryWithoutSemiconlon = null;
        
		if (query != null)
		{
			queryWithoutSemiconlon = query.replaceAll(
                                     Query.SEMICOLON, ConditionInterface.SPACE);
		}
        
		return queryWithoutSemiconlon;
	}
	
}

