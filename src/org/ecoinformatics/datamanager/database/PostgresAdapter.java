/**
 *    '$RCSfile: PostgresAdapter.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-09-12 17:15:50 $'
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

import java.util.Map;
import java.util.Vector;

import org.ecoinformatics.datamanager.parser.AttributeList;

public class PostgresAdapter extends DatabaseAdapter {

  /**
   * Create a SQL command to generate a table
   * 
   * @param  attributeList   List of attributes that determine the table columns
   * @param  tableName       The table name.
   * @return A string containing the DDL needed to create the table with
   *         its columns
   */
  public String generateDDL(AttributeList attributeList, String tableName) {
   // For now, return some dummy SQL just to allow for further development
   String ddlString = 
     "CREATE TABLE " + tableName + " " +
       "(COFFEE_NAME varchar(32), " +
        "SUPPLIER_ID int, " +
        "PRICE float, " +
        "SALES int, " +
        "TOTAL int)";
   
   return ddlString;
  }

  
  /**
   * Create a drop table SQL command.
   * 
   * @param tableName  Name of the table to be dropped
   * @return   A SQL string that can be used to drop the table.
   */
  public String generateDropTableSQL(String tableName)
  {
    String sqlString = "DROP TABLE " + tableName;
    
    return sqlString;
  }
  
  
	/**
	 * Create a sql command to insert data
	 * @param attributeList
	 * @param tableName
	 * @param oneRowData
	 * @return
	 */
	public String generateInsertSQL(AttributeList attributeList, 
                                  String tableName , 
                                  Vector oneRowData)
	{
    String sqlString = "";
    
		return sqlString;
	}
	
  
  /**
   * The map between metadat data type and database native data type
   * @return
   */
  public Map getDataTypeMap()
  {
    Map dataTypeMap = null;
    
    return dataTypeMap;
  }


  /**
	 * Transform ANSI selection sql to a native db sql command
	 * @param ANSISQL
	 * @return
	 */
	public String transformSelectionSQL(String ANSISQL)
	{
    String sqlString = "";
    
		return sqlString;
	}
	
}
