/**
 *    '$RCSfile: WhereClause.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-11-17 02:06:18 $'
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
package org.ecoinformatics.datamanager.database;

/**
 * This class represents a where clause in sql query. toSQLString can
 * transfer this object to a sql string. This class can one component -
 * one of ConditionInterface, ANDRelation, or ORRelation
 * @author tao
 *
 */
public class WhereClause 
{
	//class fields
    private ConditionInterface condition = null;
    private ANDRelation and = null;
    private ORRelation or = null;
    
    //Constants
    private static final String WHERE = "where";
    
    /**
     * Default Constructor
     *
     */
    public WhereClause()
    {
    	
    }
    
    /**
     * Sets condtion to where clause. Since where clause can have only
     * one component, ANDRelation and ORRelation will be reset to null.
     * @param condition condition be set
     */
    public void setConditionInterface(ConditionInterface condition)
    {
    	this.condition = condition;
    	this.and       = null;
    	this.or        = null;
    }
    
    /**
     * Sets ANDRelation to where clause. Since where clause can have only
     * one component, Condtion and ORRelation will be reset to null.
     * @param and ANDRelation be set
     */
    public void setANDRelation(ANDRelation and)
    {
    	this.and       = and;
    	this.condition = null;
    	this.or        = null;
    }
    
    /**
     * Sets ORRelation to where clause. Since where clause can have only
     * one component, Condtion and ANDRelation will be reset to null.
     * @param or ORRelation be set
     */
    public void setORRelation(ORRelation or)
    {
    	this.or        = or;
    	this.condition = null;
    	this.and       = null;
    }
    
    /**
     * Gets the sql string from the where clause java object
     * @return sql string
     * @throws UnWellFormedQueryException
     */
    public String toSQLString() throws UnWellFormedQueryException
    {
    	if (condition == null && or == null && and == null)
    	{
    		throw new UnWellFormedQueryException(UnWellFormedQueryException.WHERECLAUSE_IS_NULL);
    	}
    	StringBuffer sql = new StringBuffer();
    	sql.append(ConditionInterface.SPACE);
		sql.append(WHERE);
		sql.append(ConditionInterface.SPACE);  	
    	if (condition != null)
    	{  		
    		sql.append(condition.toSQLString());
    	}
    	else if (and != null)
    	{
    	  sql.append(and.toSQLString());
    	}
    	else if (or != null)
    	{
    		sql.append(or.toSQLString());
    	}
    	
    	return sql.toString();
    }
}
