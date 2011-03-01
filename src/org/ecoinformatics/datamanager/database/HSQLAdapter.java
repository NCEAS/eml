/**
 *    '$RCSfile: HSQLAdapter.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2008-03-01 00:31:48 $'
 *   '$Revision: 1.10 $'
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.DateTimeDomain;
import org.ecoinformatics.datamanager.parser.Domain;
import org.ecoinformatics.datamanager.parser.EnumeratedDomain;
import org.ecoinformatics.datamanager.parser.NumericDomain;
import org.ecoinformatics.datamanager.parser.TextDomain;

/**
 * This class extends the DatabaseAdapter class for the HSQL database.
 * 
 * @author tao
 *
 */
public class HSQLAdapter extends DatabaseAdapter {

    /*
     * Class fields
     */
	private static final String IFEXISTS          = "IF EXISTS";
	private static final String CREATETABLE       = "CREATE CACHED TABLE";
	//private static final String CREATETEXTTABLE   = "CREATE TEXT TABLE";
    
    
	/**
	 * A custom static class for use by the HSQLDB instance for converting stings to dates
	 */
	public static Timestamp to_timestamp(String value, String formatString) throws ParseException {
		//take care of differences between EML (ISO 8601) format strings and java format strings
		// year
		formatString = formatString.replaceAll("Y", "y");
		// day in month
		formatString = formatString.replaceAll("D", "d");
		// abbreviated month name
		formatString = formatString.replaceAll("W", "M");
		// AM or PM?
		formatString = formatString.replaceAll("A", "a");
		formatString = formatString.replaceAll("P", "p");
		//TODO: this may not be an exhaustive list for dealing with the conversion

		// remove any quotes from the dates
		// TODO: should this be handled elsewhere?
		value = value.replaceAll("\"", "");
		
		Timestamp timestamp = null;
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		Date temp = sdf.parse(value);
		timestamp = new Timestamp(temp.getTime());
		
		return timestamp;
		
	}
	
	/*
	 * Constructors
	 */
	public HSQLAdapter() {
		//for invocation within HSQLDB
		this.TO_DATE_FUNCTION = "\"" + this.getClass().getName() + ".to_timestamp\"";
	}
	
    /*
     * Instance methods
     */
    
    /**
     * Create a sql command to generate table based on attributeList 
     * information and table.
     *
     * @param attributeList    The AttributeList object that determines the
     *                         fields in the table
     * @param tableName        The table name String.
     * @return                 A String holding the DDL that can be executed
     *                         to create the table in the database.
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
   * Create a drop table sql command.
   * 
   * @param   tableName   the name of the table to be dropped
   * @return  a String holding the SQL which can be executed to drop the table
   */
  public String generateDropTableSQL(String tableName)
  {
	  String sql = PostgresAdapter.DROPTABLE + 
                   PostgresAdapter.SPACE + tableName + PostgresAdapter.SPACE + 
	               IFEXISTS + PostgresAdapter.SEMICOLON;
	  return sql;
  }
  
  
  /**
   * Gets attribute type for a given attribute. Attribute types include:
   *   "datetime"
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
    
    String className = this.getClass().getName();
    attributeType = getAttributeTypeFromStorageType(attribute, className);
    if (attributeType != null) {
      // If the attributeType can be derived from the storageType(s),
      // store it in the attribute object and return it now
      attribute.setAttributeType(attributeType);
      return attributeType;
    }
    
    // Derive the attributeType from the domain type
    attributeType = "string";
    Domain domain = attribute.getDomain();

    if (domain instanceof DateTimeDomain) {
      attributeType = "datetime";
    }
    else if (domain instanceof EnumeratedDomain
            || domain instanceof TextDomain) {
          attributeType = "string";
        } 
    else if (domain instanceof NumericDomain) {
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
	   * Gets the HSQL database type base on a given attribute type. 
	   */
	  protected String mapDataType(String attributeType) {
	    String dbDataType;
	    Map<String, String> map = new HashMap<String, String>();
	    
	    map.put("string", "LONGVARCHAR");
	    map.put("integer", "INTEGER");
	    map.put("real", "FLOAT");
	    map.put("whole", "INTEGER");
	    map.put("natural", "INTEGER");
	    map.put("datetime", "TIMESTAMP");
	    
	    dbDataType = map.get(attributeType);
	    
	    return dbDataType;
	  }

      
    /**
	 * Transform ANSI selection sql to a native db sql command. 
     * Not yet implemented.
     * 
	 * @param   ANSISQL  The ANSI selection SQL string.
	 * @return  A String holding the native SQL command.
	 */
	public String transformSelectionSQL(String ANSISQL)
	{
		return null;
	}
    
	
	/**
	 * Get the sql command to count how many rows in a given table.
     * 
	 * @param tableName  the given table name
	 * @return the sql string which can count how many rows
	 */
	 public String getCountingRowNumberSQL(String tableName)
	 {
		  String selectString = "SELECT COUNT(*) \"count\" FROM " + tableName;
		  return selectString;
	 }
	
}
