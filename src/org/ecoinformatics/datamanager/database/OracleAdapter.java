/**
 *    '$RCSfile: OracleAdapter.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-11-22 21:20:52 $'
 *   '$Revision: 1.7 $'
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

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.Domain;
import org.ecoinformatics.datamanager.parser.NumericDomain;

/**
 * This class extends the DatabaseAdapter class for the Oracle database.
 * 
 * @author tao
 *
 */
public class OracleAdapter extends DatabaseAdapter {
  
  /*
   * Instance methods
   */

  /**
   * Create a sql command to generate table based on attributeList 
   * information and table. (Not yet implemented)
   * 
   * @param attributeList    The AttributeList object that determines the 
   *                         fields in the table.
   * @param tableName        The table name String.
   * @return                 A String holding the DDL that can be executed
   *                         to create the table in the database.
   */
	public String generateDDL(AttributeList attributeList , String tableName)
	{
		return null;
	}
	

    /**
     * Create a drop table sql command. (Not yet implemented)
     * 
     * @param   tableName   the name of the table to be dropped
     * @return  a String holding the SQL which can be executed to drop the table
     */
    public String generateDropTableSQL(String tableName)
    {
      return null;
    }
  

    /**
     * Create a sql command to insert data. (Not yet implemented)
     * 
     * @param attributeList  The AttributeList holding the table fields
     * @param tableName      The table name
     * @param oneRowData     The values to be inserted
     * @return
     */
	public String generateInsertSQL(AttributeList attributeList, 
                                    String tableName , 
                                    Vector oneRowData)
	{
		return null;
	}
	

  /**
   * Gets attribute type for a given attribute. Attribute types include:
   *   "datetime" (for Postgres or HSQL, but not Oracle?)
   *   "string"
   * or, for numeric attributes, one of the allowed EML NumberType values:
   *   "natural"
   *   "whole"
   *   "integer"
   *   "real"
   * 
   * @param  attribute   The Attribute object whose type is being determined.
   * @return a string value representing the attribute type
   */
	 protected String getAttributeType(Attribute attribute) {
	    String attributeType = null;
	    
	    // Check whether attributeType has already been stored for this attribute
	    attributeType = attribute.getAttributeType();
	    if (attributeType != null) {
	      // If the attribute already knows its attributeType, return it now
	      return attributeType;
	    }
	    
	    attributeType = "string";
		    Domain domain = attribute.getDomain();
		    String className = domain.getClass().getName();
		    
		    if (className.endsWith("DateTimeDomain") ||
		        className.endsWith("EnumeratedDomain") ||
		        className.endsWith("TextDomain")) {
		      attributeType = "string";
		    }
		    else if (className.endsWith("NumericDomain")) {
		      NumericDomain numericDomain = (NumericDomain) domain;
		      attributeType = numericDomain.getNumberType();
		    }
		    
		    // Store the attributeType in the attribute so that it doesn't
		    // need to be recalculated with every row of data.
		    if (attribute != null) {
		      attribute.setAttributeType(attributeType);
		    }
		    
		    return attributeType;
     }
		
     
	 /*
	  * Gets the Oracle database type base on attribute type. 
	  */
	  protected String mapDataType(String attributeType) {
	    String dbDataType;
	    Map<String, String> map = new HashMap<String, String>();
	    
	    map.put("string", "TEXT");
	    map.put("integer", "INTEGER");
	    map.put("real", "FLOAT");
	    map.put("whole", "INTEGER");
	    map.put("natural", "INTEGER");
	    
	    dbDataType = map.get(attributeType);
	    
	    return dbDataType;
	  }


    /**
     * Transform ANSI selection sql to a native db sql command. Not yet
     * implemented.
     * 
     * @param ANSISQL The ANSI selection SQL string.
     * @return A String holding the native SQL command.
     */
	public String transformSelectionSQL(String ANSISQL)
	{
		return null;
	}
	
    
    /**
     * Gets the sql command to count how many rows in a given table.
     * 
     * @param tableName  the given table name
     * @return the sql string which can count how many rows
     */
	 public String getCountingRowNumberSQL(String tableName)
	 {
		  String selectString = "SELECT COUNT(*) FROM " + tableName;
		  return selectString;
	 }
	
}
