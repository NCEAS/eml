/**
 *    '$RCSfile: PostgresAdapter.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-08-18 01:39:42 $'
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

import java.util.Map;
import java.util.Vector;

import org.ecoinformatics.datamanager.parser.AttributeList;

public class PostgresAdapter implements DatabaseAdapter
{
	/**
	 * Create a sql command to generate table
	 * @param attributeList
	 * @param tableName
	 * @return
	 */
	public String generateDDL(AttributeList attributeList , String tableName)
	{
		return null;
	}
	
	/**
	 * Create a sql command to insert data
	 * @param attributeList
	 * @param tableName
	 * @param oneRowData
	 * @return
	 */
	public String generateInsertSQL(AttributeList attributeList, String tableName , Vector oneRowData)
	{
		return null;
	}
	
	/**
	 * Transform ANSI selection sql to a native db sql command
	 * @param ANSISQL
	 * @return
	 */
	public String transformSelectionSQL(String ANSISQL)
	{
		return null;
	}
	
	/**
	 * Create a drop talbe sql command
	 * @param tableName
	 * @return
	 */
	public String generateDropTableSQL(String tableName)
	{
		return null;
	}
	
	/**
	 * The map between metadat data type and database native data type
	 * @return
	 */
	public Map getDataTypeMap()
	{
		return null;
	}

}
