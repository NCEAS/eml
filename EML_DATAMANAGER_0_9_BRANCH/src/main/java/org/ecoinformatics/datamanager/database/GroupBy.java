/**
 *    '$RCSfile: GroupBy.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2009-01-08 01:24:08 $'
 *   '$Revision: 1.1 $'
 *
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
 * This class represents a sql group by clause in java
 * @author leinfelder
 *
 */
public class GroupBy 
{
	private static final String GROUP_BY = "GROUP BY";
	
	//class fields
	private SelectionItem[] groupByList = null;
	
	/**
	 * Adds a group by item to query
	 * @param groupItem item to group by
	 */
	public void addGroupByItem(SelectionItem groupItem)
	{
		if (groupByList == null)
		{
			groupByList = new SelectionItem[1];
			groupByList[0] = groupItem;
		}
		else
		{
			int size = groupByList.length;
			SelectionItem[] copy = groupByList;
			groupByList = new SelectionItem[size+1];
			for(int i=0; i<size; i++)
			{
				groupByList[i]= copy[i];
			}
			groupByList[size] = groupItem;
			
		}
	}

	/**
	 * Gets a sql string from group by object
	 * @return sql string
	 * @throws UnWellFormedQueryException
	 */
	public String toSQLString() throws UnWellFormedQueryException
	{
		StringBuffer sql = new StringBuffer();

		if (groupByList != null && groupByList.length > 0) {
			
			// group by
			sql.append(GROUP_BY);
			sql.append(ConditionInterface.SPACE);
			
			int selectionLength = groupByList.length;
			boolean firstSelection = true;
			for (int i=0; i<selectionLength; i++)
			{
				SelectionItem selection = groupByList[i];
				if (firstSelection)
				{
					sql.append(selection.toSQLString());
					firstSelection = false;
				}
				else
				{
					sql.append(Query.COMMA);
					sql.append(selection.toSQLString());
				}
			}
		}
		return sql.toString();
	}
	
}
