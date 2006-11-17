/**
 *    '$RCSfile: LogicalRelation.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-11-17 02:03:47 $'
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

import java.util.Vector;

public class LogicalRelation 
{
	
	protected static final String AND = "AND";
	protected static final String OR  = "OR";
	private static final String LEFT_PARENSIS = "(";
	private static final String RIGHT_PARENSIS = ")";
	private Vector ANDRelationList = new Vector();
    private Vector conditionList   = new Vector();
    private Vector ORRelationList  = new Vector();
    
	
    
    /**
     * Default Constructor
     *
     */
    public LogicalRelation()
    {
    	
    }
    
    /**
     * Gets the conditions list in this LogicalRelation
     * 
     * @return   an array of ConditionInterface, or null if there are no
     *           ConditionInterface in the list
     */
    public ConditionInterface[] getConditionInterfaceList() {
      if (conditionList == null || conditionList.size() == 0) {
        return null;
      } 
      else {
        int size = conditionList.size();
        ConditionInterface [] list = new ConditionInterface[size];
        
        for (int i = 0; i < size; i++) {
          list[i] = (ConditionInterface) conditionList.elementAt(i);
        }
        
        return list;
      }
    }
    
    /**
     * Gets the ANDRelation list in this LogicalRelation
     * 
     * @return   an array of ANDRelation, or null if there are no
     *           ANDRelation in the list
     */
    public ANDRelation[] getANDRelationList() {
      if (ANDRelationList == null || ANDRelationList.size() == 0) {
        return null;
      } 
      else {
        int size = ANDRelationList.size();
        ANDRelation [] list = new ANDRelation[size];
        
        for (int i = 0; i < size; i++) {
          list[i] = (ANDRelation) ANDRelationList.elementAt(i);
        }
        
        return list;
      }
    }
    
    /**
     * Gets the ORRelation list in this LogicalRelation
     * 
     * @return   an array of ORRelation, or null if there are no
     *           ORRelation in the list
     */
    public ORRelation[] getORRelationList() {
      if (ORRelationList == null || ORRelationList.size() == 0) {
        return null;
      } 
      else {
        int size = ORRelationList.size();
        ORRelation [] list = new ORRelation[size];
        
        for (int i = 0; i < size; i++) {
          list[i] = (ORRelation) ORRelationList.elementAt(i);
        }
        
        return list;
      }
    }
    
    /**
     * Adds a condtion into this LogicalRelation
     * @param condition  the added condition
     */
    public void addCondtionInterface(ConditionInterface condition)
    {
    	conditionList.add(condition);
    	
    }
    
    /**
     * Adds an ANDRelation into this LogicalRelation
     * @param and the added ANDRelation
     */
    public void addANDRelation(ANDRelation and)
    {
    	ANDRelationList.add(and);
    	
    }
    
    /**
     * Adds a ORRelation into this LogicalRelation
     * @param or the added ORRelation
     */
    public void addORRelation(ORRelation or)
    {
    	ORRelationList.add(or);
    }
    
    /*
     * Transfer this LogicalRelation to sql String
     */
    protected String transferToString(String operator) throws UnWellFormedQueryException
    {
    	StringBuffer sql = new StringBuffer();
    	if((conditionList == null && ANDRelationList == null && ORRelationList == null) ||
    			(conditionList.isEmpty() && ANDRelationList.isEmpty() && ORRelationList.isEmpty()))
    	{
    		throw new UnWellFormedQueryException(UnWellFormedQueryException.LOGICALREALTION_IS_NULL);
    	}
    	int conditionLength = 0;
    	int ANDRelationLength = 0;
    	int ORRelationLength = 0;
    	// Gets length of vector
    	if (conditionList != null)
    	{
    		conditionLength = conditionList.size();
    	}
    	
    	if (ANDRelationList != null)
    	{
    		ANDRelationLength = ANDRelationList.size();
    	}sql.append(ConditionInterface.SPACE);
    	
    	if (ORRelationList != null)
    	{
    		ORRelationLength = ORRelationList.size();
    	}
    	
    	int length = conditionLength +  ANDRelationLength + ORRelationLength;
    	// There is only one element in LogicalRelation, it is illegal syntax
    	if (length == 1)
    	{
    		throw new UnWellFormedQueryException(UnWellFormedQueryException.LOGICALREALTION_HAS_ONE_SUBCOMPOENT);
    	}
    	// Goes through conditon
    	boolean firstElement = true;
    	for (int i=0; i<conditionLength; i++)
    	{
    		ConditionInterface condition = (ConditionInterface)conditionList.elementAt(i);
    		if (firstElement)
    		{
    			sql.append(condition.toSQLString());
        		sql.append(ConditionInterface.SPACE);
        		firstElement = false;
    		}
    		else
    		{
    			sql.append(operator);
        		sql.append(ConditionInterface.SPACE);
        		sql.append(condition.toSQLString());
        		sql.append(ConditionInterface.SPACE);
    		}   		 		
    		
    	}
    	//Goes through ANDRelation
    	for (int i=0; i<ANDRelationLength; i++)
    	{
    		ANDRelation and = (ANDRelation)ANDRelationList.elementAt(i);
    		if (firstElement)
    		{
    			sql.append(LEFT_PARENSIS);
    			sql.append(and.toSQLString());
    			sql.append(RIGHT_PARENSIS);
    			sql.append(ConditionInterface.SPACE);
        		firstElement = false;
    		}
    		else
    		{
    			sql.append(operator);
        		sql.append(ConditionInterface.SPACE);
        		sql.append(LEFT_PARENSIS);
        		sql.append(and.toSQLString());
        		sql.append(RIGHT_PARENSIS);
        		sql.append(ConditionInterface.SPACE);
    		}
    	}
        // Goes through ORRelation
    	for (int i=0; i<ORRelationLength; i++)
    	{
    		ORRelation or = (ORRelation)ORRelationList.elementAt(i);
    		if (firstElement)
    		{
    			sql.append(LEFT_PARENSIS);
    			sql.append(or.toSQLString());
    			sql.append(RIGHT_PARENSIS);
    			sql.append(ConditionInterface.SPACE);
        		firstElement = false;
    		}
    		else
    		{
    			sql.append(operator);
        		sql.append(ConditionInterface.SPACE);
        		sql.append(LEFT_PARENSIS);
        		sql.append(or.toSQLString());
        		sql.append(RIGHT_PARENSIS);
        		sql.append(ConditionInterface.SPACE);
    		}
    	}
    	return sql.toString();
    }
    
   
}
