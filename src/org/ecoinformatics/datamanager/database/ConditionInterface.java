/**
 *    '$RCSfile: ConditionInterface.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-11-16 21:33:45 $'
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
 * This interface represents a Condition in where clause in SQL query.
 * Condition means "field1=1" or join
 * @author tao
 *
 */
public interface ConditionInterface 
{
	//Constants
	public static final String LIKE_OPERATOR = "LIKE";
	public static final String NOT_LIKE_OPERATOR = "NOT LIKE";
	public static final String EQUAL_PERATOR = "=";
	public static final String NOT_EQUAL_PERATOR = "!=";
	public static final String LESS_THAN_OPERATOR = "<";
	public static final String LESS_THAN_OR_EQUALS_OPERATOR = "<=";
	public static final String GREATER_THAN_OPERATOR = ">";
	public static final String GREATER_THANOR_EQUALS_OPERATOR = ">=";
	public static final String SPACE  = " ";
	public static final String SINGLEQUOTE = "'";
	
	public static final String[] STRING_OPERATOR_LIST = {LIKE_OPERATOR, NOT_LIKE_OPERATOR, EQUAL_PERATOR, NOT_EQUAL_PERATOR};
    public static final String[] NUMBER_OPERATOR_LIST = {LESS_THAN_OPERATOR, LESS_THAN_OR_EQUALS_OPERATOR, GREATER_THAN_OPERATOR, GREATER_THANOR_EQUALS_OPERATOR};
   /**
	* Transfers a Condition object to sql string which 
	* has real entity and attribute name in db.
	* @return condition sql string
    * @throws UnWellFormedQueryException
    */
   public String toSQLString() throws UnWellFormedQueryException;
   
}
