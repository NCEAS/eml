/**
 *    '$RCSfile: HSQLAdapter.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-10-24 23:46:13 $'
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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.Domain;
import org.ecoinformatics.datamanager.parser.NumericDomain;

public class HSQLAdapter extends DatabaseAdapter {

	private static final String IFEXISTS          = "IF EXISTS";
	private static final String CREATETABLE       = "CREATE CACHED TABLE";
	private static final String CREATETEXTTABLE   = "CREATE TEXT TABLE";
    /**
	 * Create a sql command to generate table base on attributeList 
     * information and table
	 * @param attributeList
	 * @param tableName
	 * @return
	 */
	public String generateDDL(AttributeList attributeList, String tableName)
	               throws SQLException
	{
	   String attributeSQL = null;
	   StringBuffer stringBuffer = new StringBuffer();
	   stringBuffer.append(CREATETABLE);
	   stringBuffer.append(PostgresAdapter.SPACE);
	   stringBuffer.append(tableName);
	   stringBuffer.append(PostgresAdapter.LEFTPARENTH);
	   attributeSQL = parseAttributeList(attributeList);
	   stringBuffer.append(attributeSQL);
	   stringBuffer.append(PostgresAdapter.RIGHTPARENTH);
	   stringBuffer.append(PostgresAdapter.SEMICOLON);
	   String sqlStr = stringBuffer.toString();
	   return sqlStr;
		
	}
	
  
  /**
   * Create a drop table sql command
   * @param tableName
   * @return
   */
  public String generateDropTableSQL(String tableName)
  {
	  String sql = PostgresAdapter.DROPTABLE + PostgresAdapter.SPACE + tableName + PostgresAdapter.SPACE + 
	  IFEXISTS + PostgresAdapter.SEMICOLON;
	  return sql;
   
  }
  
  
	  /*
	   * Gets attribute type for a given attribute. Attribute types include
	   * text, numeric and et al.
	   * 
	   */	
	  protected String getAttributeType(Attribute attribute) {
		    String attributeType = "string";
		    Domain domain = attribute.getDomain();
		    String className = domain.getClass().getName();
		    
		    System.out.println("  className:  " + className);

		    if (className.endsWith("DateTimeDomain") ||
		        className.endsWith("EnumeratedDomain") ||
		        className.endsWith("TextDomain")) {
		      attributeType = "string";
		    }
		    else if (className.endsWith("NumericDomain")) {
		      NumericDomain numericDomain = (NumericDomain) domain;
		      attributeType = numericDomain.getNumberType();
		    }
		    
		    System.out.println("  attributeType:  " + attributeType);
		    return attributeType;
		  }
		  
	   
	  /*
	   * Gets the HSQL database type base on given attribute type. 
	   */
	  protected String mapDataType(String attributeType) {
	    String dbDataType;
	    Map map = new HashMap();
	    
	    map.put("string", "LONGVARCHAR");
	    map.put("integer", "INTEGER");
	    map.put("real", "FLOAT");
	    map.put("whole", "INTEGER");
	    map.put("natural", "INTEGER");
	    
	    dbDataType = (String) map.get(attributeType);
	    
	    return dbDataType;
	  }

    /**
	 * Transform ANSI selection sql to a native db sql command
     * 
	 * @param ANSISQL
	 * @return
	 */
	public String transformSelectionSQL(String ANSISQL)
	{
		return null;
	}
	
	/**
	 * Get the sql command to count how many rows in a given table
	 * @param tableName  the given table name
	 * @return the sql string which can count how many rows
	 */
	 public String getCountingRowNumberSQL(String tableName)
	 {
		  String selectString = "SELECT COUNT(*) \"count\" FROM " + tableName;
		  return selectString;
	 }
	
}
