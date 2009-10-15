/**
 *    '$RCSfile: Union.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2007-10-02 20:26:21 $'
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ecoinformatics.datamanager.parser.Attribute;

/**
 * This class represents a union of multiple sql queries in java.
 * @author leinfelder
 *
 */
public class Union
{
   //class fields
	private List queryList = null;
	private String unionType = null;
	private boolean orderQueryList = false;
	
	//constants
	public static final String UNION = "UNION";
	public static final String UNION_ALL = "UNION ALL";
	
	/**
	 * Constructor - uses UNION ALL option as default
	 *
	 */
	public Union() {
		queryList = new ArrayList();
		unionType = UNION_ALL;
	}
	
	/**
	 * Adds a query to the list to be unioned
	 * @param query fully defined Query object to use as union member
	 */
	public void addQuery(Query query) {
		queryList.add(query);
	}
		
	
	public String getUnionType() {
		return unionType;
	}

	public void setUnionType(String unionType) {
		this.unionType = unionType;
	}
	
	public boolean isOrderQueryList() {
		return orderQueryList;
	}

	public void setOrderQueryList(boolean orderQueryList) {
		this.orderQueryList = orderQueryList;
	}

	/**
	 * Helper method to remove trailing semicolon
	 * @param input String to remove trailing semicolon
	 * @return input without trailing semicolon
	 */
	private String shearSemicolon(String input) {
		String temp = input;
		if (input.endsWith(Query.SEMICOLON)) {
			temp = input.substring(0, input.lastIndexOf(Query.SEMICOLON));
		}
		return temp;
	}
	
	private void orderQueryList() {
		// init the wideness-trackers
		Query widestQuery = (Query) queryList.get(0);
		int widestSelectionCount = 0;
		Iterator queryIter = this.queryList.iterator();
		while (queryIter.hasNext()) {
			Query query = (Query) queryIter.next();
			int attributeCount = query.getSelectionList().length;
			int nonNullAttributeCounter = 0;
			for (int i = 0; i < attributeCount; i++) {
				SelectionItem selectionItem = query.getSelectionList()[i];
				Attribute attribute = selectionItem.getAttribute();
				if (attribute != null) {
					nonNullAttributeCounter = i;
					// check if we got wider
					if (widestSelectionCount < nonNullAttributeCounter) {
						widestSelectionCount = nonNullAttributeCounter;
					}
				}
			}
			if (nonNullAttributeCounter == widestSelectionCount) {
				widestQuery = query;
			}
		}
		
		// now we have the widest non-null attribute query
		// swap it out
		queryList.remove(widestQuery);
		queryList.add(0, widestQuery);
				
	}
	
	/**
	 * TODO: add accessor method[s] to underlying class[es] 
	 * @return true if the Query objects can participate in a UNION [ALL]
	 */
	private boolean validateQueryList() {
		int firstAttributeCount = 0;
		//firstAttributeCount = ((Query) this.queryList.get(0)).getSelectionList().length;
		Iterator queryIter = this.queryList.iterator();
		while (queryIter.hasNext()) {
			Query query = (Query) queryIter.next();
			int attributeCount = 0; 
			//attributeCount = query.getSelectionList().length;
			if (firstAttributeCount != attributeCount) {
				return false;
			}
		}
		
		return true;
		
	}

	/**
	 * Gets a sql string from the Query objects
	 * @return sql string
	 * @throws UnWellFormedQueryException (from underlying Query object[s])
	 */
	public String toSQLString() throws UnWellFormedQueryException {
		
		StringBuffer sql = new StringBuffer();
		
		if (orderQueryList) {
			this.orderQueryList();
		}
		
		Iterator queryIter = this.queryList.iterator();
		
		while (queryIter.hasNext()) {
			Query query = (Query) queryIter.next();
			
			//append the query, removing trailing semicolon to prevent execution
			sql.append(
					this.shearSemicolon(
							query.toSQLString()));
			
			//if there another one coming, add the union statement
			if (queryIter.hasNext()) {
				sql.append(ConditionInterface.SPACE);
				sql.append(this.getUnionType());
				sql.append(ConditionInterface.SPACE);
			}
		}
		
		//put back the semicolon for the larger query
		sql.append(Query.SEMICOLON);
		
		return sql.toString();
	}
	
}
