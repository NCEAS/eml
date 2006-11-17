/**
 *    '$RCSfile: UnWellFormedQueryException.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-11-17 02:01:58 $'
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
package org.ecoinformatics.datamanager.database;

/**
 * Exception for invalide sql query
 * @author tao
 *
 */
public class UnWellFormedQueryException extends Exception 
{
	//constants
	public static final String SELECTION_ATTRIBUTE_IS_NULL = "Selected attribute couldn't be null";
	public static final String SELECTION_ATTRIBUTE_NAME_IS_NULL = "Selected attribute name couldn't be null or blank";
	public static final String TABLEITEM_ENTITY_IS_NULL = "Entity couldn't be null in \"From clause\"";
	public static final String TABLEITEM_ENTITY_NAME_IS_NULL = "Entity name couldn't be null in \"From clause\"";
	public static final String CONDITION_ATTRIBUTE_IS_NULL = "Attribute couldn't be null in condtion";
	public static final String CONDITION_ATTRIBUTE_NAME_IS_NULL = "Attribute couldn't be null in condtion";
	public static final String CONDITION_OPERATOR_IS_NULL = "Operator couldn't be null in condition";
	public static final String CONDITION_VALUE_IS_NULL ="Value couldn't be null in condition";
	public static final String CONDITOION_VALUE_IS_NOT_NUMBER = " is NOT a number in condition, but it should be because it use a numeric operator ";
	public static final String CONDITION_NOT_HANDLED_OPERATOR = "Sorry, we couldn't handle this operator: ";
	public static final String JOIN_ENTITY_IS_NULL = "The entity in join part couldn't be null";
	public static final String JOIN_ATTRIBUTE_IS_NULL = "The attribute in join part couldn't be null";
	public static final String JOIN_ENTITY_NAME_IS_NULL = "The entity name in joint part couldn't be null";
	public static final String JOIN_ATTRIBUTE_NAME_IS_NULL = "The attribute name in join part couldn't be null";
	public static final String LOGICALREALTION_IS_NULL = "There is no sub-compents in logical (AND or OR) relation";
	public static final String LOGICALREALTION_HAS_ONE_SUBCOMPOENT = "There is only one sub components in logical relation";
	public static final String WHERECLAUSE_IS_NULL = "There is no component in where clause";
	public static final String QUERY_SELECTION_OR_TABLE_IS_NULL = "Selection item or From table item is null in query";
	/**
    * Constructor
    * @param error  error message in this exception
    */
   public UnWellFormedQueryException(String error)
   {
	   super(error);
   }
}
