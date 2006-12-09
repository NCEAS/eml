/**
 *    '$RCSfile: Condition.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-12-09 01:14:50 $'
 *   '$Revision: 1.6 $'
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
 * This class represents a condition inside a where clause in a SQL query.
 * 
 * @author tao
 */
public class Condition implements ConditionInterface
{
    /*
     * Instance fields
     */
	private Entity entity = null;
	private Attribute attribute = null;
	private String operator = null;
	private Object value    = null;
	
    
	/**
	 * Constructor
     * 
	 * @param entity  the entity in condition
	 * @param attribute the attribute in condition
	 * @param operator  the operator in condition
	 * @param value  the value in condtion
	 */
	public Condition(Entity entity, Attribute attribute, 
                     String operator, Object value)
	{
		this.entity = entity;
		this.attribute = attribute;
		this.operator = operator;
		this.value = value;
	}
   
    
    /*
     * Instance methods
     */
    
	/**
	* Transfers a Condition object to a SQL string which 
	* contains the entity's table name and the attributes field names
    * as they are stored in the DB.
    * 
	* @return SQL string for this Condition object
    * @throws UnWellFormedQueryException
    */
	public String toSQLString() throws UnWellFormedQueryException
	{
		String condition = null;
		StringBuffer conditionBuffer = new StringBuffer();
        
		//entity part
		if (entity != null)
		{
			String entityName = null;
			try
			{
			   entityName = DataManager.getDBTableName(entity);
			}
			catch(SQLException sqle)
			{
			   System.err.println("entity name is null "+sqle.getMessage());
			}
            
			if (entityName != null && !entityName.trim().equals(BLANK))
			{
				conditionBuffer.append(entityName);
				conditionBuffer.append(SEPERATER);
			}
		}
        
		//attribute part
		if (attribute != null)
		{
			String attributeName = null;
			try
			{
				attributeName = DataManager.getDBFieldName(entity, attribute);
			}
			catch(SQLException sqle)
			{
				System.err.println("attribute name is null "+sqle.getMessage());
			}
            
			if (attributeName != null && !attributeName.trim().equals(BLANK))
			{
				conditionBuffer.append(attributeName);
			}
			else
			{
				throw new UnWellFormedQueryException(
                        UnWellFormedQueryException.CONDITION_ATTRIBUTE_IS_NULL);
			}
		}
		else
		{
			throw new UnWellFormedQueryException(
                   UnWellFormedQueryException.CONDITION_ATTRIBUTE_NAME_IS_NULL);
		}
        
		// operator part
		conditionBuffer.append(handleOperatorAndValue());
		condition = conditionBuffer.toString();
        
		return condition;
	}
	
    
	/*
	 * Transfer operator and value part into SQL string.
	 */
	private String handleOperatorAndValue() throws UnWellFormedQueryException
	{
		StringBuffer sql = new StringBuffer();
        
		if (isInStringOperatorList(operator))
		{
			if (value != null)
			{
				sql.append(SPACE);
				sql.append(operator);
				sql.append(SPACE);
				sql.append(SINGLEQUOTE);
				sql.append(value);
				sql.append(SINGLEQUOTE);
			}
			else
			{
				throw new UnWellFormedQueryException(
                            UnWellFormedQueryException.CONDITION_VALUE_IS_NULL);
			}
		}
		else if (isInNumericOperatorList(operator))
		{
			if (value != null && (value instanceof Number))
			{
				sql.append(SPACE);
				sql.append(operator);
				sql.append(SPACE);
				sql.append(value);
			}
			else if ( value != null)
			{
				throw new UnWellFormedQueryException("" + 
                                                     value + 
                     UnWellFormedQueryException.CONDITION_VALUE_IS_NOT_NUMBER +
                                                     operator);
			}
			else
			{
				throw new UnWellFormedQueryException(
                            UnWellFormedQueryException.CONDITION_VALUE_IS_NULL);
			}
		}
		else
		{
			throw new UnWellFormedQueryException(
                     UnWellFormedQueryException.CONDITION_NOT_HANDLED_OPERATOR +
                     operator);
		}
        
		return sql.toString();
	}
	
    
	/*
	 * Determines if a given operator is in string operator list or not.
	 */
	private boolean isInStringOperatorList(String operator) 
            throws UnWellFormedQueryException
	{
		return isInOperatorList(operator, STRING_OPERATOR_LIST);
	}
	
    
	/*
	 * Determines if a given operator is in numeric operator list or not.
	 */
	private boolean isInNumericOperatorList(String operator) 
            throws UnWellFormedQueryException
	{
		return isInOperatorList(operator, NUMBER_OPERATOR_LIST);
	}
	
    
	/*
	 * Determines if a given operator is in the given string array.
	 */
	private boolean isInOperatorList(String operator, String[] operatorList) 
            throws UnWellFormedQueryException
	{
		boolean inList = false;
		
		if (operator == null)
		{
			throw new UnWellFormedQueryException(
                         UnWellFormedQueryException.CONDITION_OPERATOR_IS_NULL);
		}
		else
		{
			if (operatorList != null)
			{
				int length = operatorList.length;
                
				for (int i=0; i<length; i++)
				{
					String operatorInList = operatorList[i];
                    
					if (operatorInList != null && 
                        operatorInList.equalsIgnoreCase(operator)
                       )
					{
						inList = true;
						break;
					}
				}
			}
		}
		
		return inList;
	}

}
