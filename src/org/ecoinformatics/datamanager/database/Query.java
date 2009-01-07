/**
 *    '$RCSfile: Query.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2009-01-07 18:55:06 $'
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

/**
 * This class represents a sql query in java presentation.
 * @author tao
 *
 */
public class Query 
{
   //class fields
	private SelectionItem[] selectionList = null;
	private TableItem[] tableList = null;
	private WhereClause whereClause = null;
	private boolean distinct = false;
	
	//constants
	private static final String SELECT = "SELECT";
	private static final String DISTINCT = "DISTINCT";
	private static final String FROM = "FROM";
	public static final String SEMICOLON = ";";
	private static final String COMMA = ",";
	
	/**
	 * Default constructor
	 *
	 */
	public Query()
	{
		
	}
	
	/**
	 * Adds a SelectionItem into query
	 * @param selection  selectionItem was added
	 */
	public void addSelectionItem(SelectionItem selection)
	{
		if (selectionList == null)
		{
			selectionList = new SelectionItem[1];
			selectionList[0] = selection;
		}
		else
		{
			int size = selectionList.length;
			SelectionItem[] copy = selectionList;
			selectionList = new SelectionItem[size+1];
			for(int i=0; i<size; i++)
			{
				selectionList[i]= copy[i];
			}
			selectionList[size] = selection;
			
		}
	}
	
	/**
	 * Adds a TableItem into query
	 * @param table  TableItem was added
	 */
	public void addTableItem(TableItem table)
	{
		if (containsTableItem(table)) {
			return;
		}
		
		if (tableList == null)
		{
			tableList = new TableItem[1];
			tableList[0] = table;
		}
		else
		{
			int size = tableList.length;
			TableItem[] copy = tableList;
			tableList = new TableItem[size+1];
			for(int i=0; i<size; i++)
			{
				tableList[i]= copy[i];
			}
			tableList[size] = table;
			
		}
	}
	
	public boolean containsTableItem(TableItem table)
	{
		if (tableList != null)
		{
			int size = tableList.length;
			for(int i=0; i<size; i++)
			{
				if (tableList[i].equals(table)) {
					return true;
				}
			}			
		}
		return false;
	}
	
	/**
	 * Set where clause to the query
	 * @param where where clause need be set
	 */
	public void setWhereClause(WhereClause where)
	{
		this.whereClause = where;
	}
	
	/**
	 * 
	 * @return true when query should return only distinct records
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * 
	 * @param distinct set to true to return only distinct records
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * Gets a sql string from query object
	 * @return sql string
	 * @throws UnWellFormedQueryException
	 */
	public String toSQLString() throws UnWellFormedQueryException
	{
		if (selectionList == null || tableList == null)
		{
			throw new UnWellFormedQueryException(UnWellFormedQueryException.QUERY_SELECTION_OR_TABLE_IS_NULL);
		}
		StringBuffer sql = new StringBuffer();
		// select part
		sql.append(SELECT);
		sql.append(ConditionInterface.SPACE);
		if (distinct) {
			sql.append(DISTINCT);
			sql.append(ConditionInterface.SPACE);
		}
		int selectionLength = selectionList.length;
		boolean firstSelection = true;
		for (int i=0; i<selectionLength; i++)
		{
			SelectionItem selection = selectionList[i];
			if (firstSelection)
			{
				sql.append(selection.toSQLString());
				firstSelection = false;
			}
			else
			{
				sql.append(COMMA);
				sql.append(selection.toSQLString());
			}
		}
		//from part
		sql.append(ConditionInterface.SPACE);
		sql.append(FROM);
		sql.append(ConditionInterface.SPACE);
		boolean firstFrom = true;
		int fromLength = tableList.length;
		for (int i=0; i<fromLength; i++)
		{
			TableItem tableItem = tableList[i];
			if (firstFrom)
			{
				sql.append(tableItem.toSQLString());
				firstFrom = false;
			}
			else
			{
				sql.append(COMMA);
				sql.append(tableItem.toSQLString());
			}
		}
		// where clause part
		if (whereClause != null)
		{
			sql.append(ConditionInterface.SPACE);
			sql.append(whereClause.toSQLString());
		}
		sql.append(SEMICOLON);
		return sql.toString();
	}
	
}
