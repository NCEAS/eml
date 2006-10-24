/**
 *    '$RCSfile: DatabaseAdapter.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-10-24 23:43:29 $'
 *   '$Revision: 1.8 $'
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
import java.util.Vector;
import java.util.Map;

import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.Domain;
import org.ecoinformatics.datamanager.parser.NumericDomain;

/**
 * This class provide a bridge between DatabaseHandler and a specific db.
 * 
 * @author Jing Tao
 */

public abstract class DatabaseAdapter {

  /*
   * Class fields
   */

	public static final String HSQL_ADAPTER     = "HSQLAdapter";
    public static final String ORACLE_ADAPTER   = "OracleAdapter";
	public static final String POSTGRES_ADAPTER = "PostgresAdapter";
	public static final String            COMMA = ",";
	public static final String            SPACE = " ";
	public static final String           INSERT = "INSERT INTO";
	public static final String      LEFTPARENTH = "(";
	public static final String     RIGHTPARENTH = ")";
	public static final String        SEMICOLON = ";";
	public static final String      SINGLEQUOTE = "'";
	public static final String           VALUES = "VALUES";
  
  
  /*
   * Instance fields
   */
  
  
  /*
   * Constructors
   */
  
  
  /*
   * Class methods
   */
 
  /**
   * Given an entity name, return a well-formed table name. This is a generic
   * implementation that should work for most databases. This method should be
   * overridden by a database adapter subclass if it has special rules for the
   * well-formedness of a table name.
   */
  public static String getLegalDBTableName(String entityName) {
    String legalName = entityName;
    char[] badChars = {' ', '-', '.'};
    char goodChar = '_';
    
    for (int i = 0; i < badChars.length; i++) {
      legalName = legalName.replace(badChars[i], goodChar);
    }
    
    return legalName;
  }
  
  

  /*
   * Instance methods
   */
  
	/**
	 * Create a sql command to generate table
   * 
	 * @param attributeList
	 * @param tableName
	 * @return
	 */
	public String generateDDL(AttributeList attributeList , String tableName) 
          throws SQLException {
   String ddlString = "";
   
   return ddlString;
  }

  
  /**
   * Create a drop table sql command
   * 
   * @param tableName
   * @return
   */
  public String generateDropTableSQL(String tableName) {
    String sqlString = "";
    
    return sqlString;
  }

  


  
  /**
   * The map between metadat data type and database native data type
   * 
   * @return
   */
  public Map getDataTypeMap() {
    Map typeMap = null;
    
    return typeMap;
  }
  

  /**
	 * Transform ANSI selection sql to a native db sql command
   * 
	 * @param ANSISQL
	 * @return
	 */
	public String transformSelectionSQL(String ANSISQL) {
    String sqlString = "";
   
    return sqlString;
    
  }
	
	 /*
	   * Add attribute defination in create table command. If one attribute is null
	   * or has same error an exception will be throw
	   */
	  protected String parseAttributeList(AttributeList attributeList) 
	          throws SQLException {
	    Attribute[] list = attributeList.getAttributes();
	    StringBuffer attributeSql = new StringBuffer();

	    if (list == null || list.length == 0) {
	      //log.debug("There is no attribute defination in entity");
	      throw new SQLException("No attribute definition found in entity");
	    }
	    
	    int size = list.length;
	    //DBDataTypeResolver dataTypeResolver = new DBDataTypeResolver();
	    boolean firstAttribute = true;

	    for (int i = 0; i < size; i++) {
	      Attribute attribute = list[i];

	      if (attribute == null)
	      {
	       //log.debug("One attribute defination is null attribute list");
	        throw new SQLException("One attribute definition is null attribute list");
	      }

	      String name = attribute.getName();
	      String attributeType = getAttributeType(attribute);
	      String dbDataType = mapDataType(attributeType);
	        
	      //String dataType = attribute.getDataType();
	      //String dbDataType = "VARCHAR(32)";  // Temporary hack during development
	      //String dbDataType = dataTypeResolv;er.resolveDBType(dataType);
	      //String javaDataType = dataTypeResolver.resolveJavaType(dataType);
	      //dbJavaDataTypeList.add(javaDataType);

	      if (!firstAttribute) {
	        attributeSql.append(COMMA);
	      }
	      
	      attributeSql.append(name);
	      attributeSql.append(SPACE);
	      attributeSql.append(dbDataType);
	      firstAttribute = false;
	      
	      System.out.println("Attribute Name: " + name);
	      System.out.println("  dbDataType:   " + dbDataType + "\n");
	    }
	    
	    return attributeSql.toString();
	  }
	  
	  
	  /**
		 * Create a sql command to insert data. If some error happens, null will be returned
		 * @param attributeList  AttributeList which will be inserted
		 * @param tableName TableName which will be inserted
		 * @param oneRowData The data vector which contains data need be inserted
		 * @return A SQL String can be ran to insert one row data into table
		 */
		public String generateInsertSQL(AttributeList attributeList, 
	                                  String tableName , 
	                                  Vector oneRowData)
		{
			String sqlString = null;
			int NULLValueCounter = 0;
			if (attributeList == null)
	        {
	       		 //log.debug("There is no attribute defination in entity");
	           return sqlString;
	        }
			
			if (oneRowData == null || oneRowData.isEmpty())
			{
				return sqlString;
			}
	    
	        StringBuffer sqlAttributePart = new StringBuffer();
	        StringBuffer sqlDataPart      = new StringBuffer();
	        sqlAttributePart.append(INSERT);
	        sqlAttributePart.append(SPACE);
	        sqlAttributePart.append(tableName);
	        sqlAttributePart.append(LEFTPARENTH);
	        sqlDataPart.append(SPACE);
	        sqlDataPart.append(VALUES);
	        sqlDataPart.append(SPACE);
	        sqlDataPart.append(LEFTPARENTH);
	        Attribute[] list = attributeList.getAttributes();
	        if (list == null || list.length == 0)
	        {
	       		 //log.debug("There is no attribute defination in entity");
	           return sqlString;
	        }
	        int size = list.length;
	        // cloumna name part
	        boolean firstAttribute = true;
	        for (int i = 0; i< size; i++)
	        {
	          // if data vector
	          Object obj = oneRowData.elementAt(i);
	          String value = null;
	          if (obj == null)
	          {
	        	  NULLValueCounter++;
	        	  continue;
	          }
	          else
	          {
	        	 value = (String)obj;
	          }
	          Attribute attribute = list[i];
	          if (attribute == null)
	          {
	       		 //log.debug("One attribute defination is null attribute list");
	             return sqlString;
	          }
	          String name = attribute.getName();
	          if (!firstAttribute)
	          {
	            sqlAttributePart.append(COMMA);
	            sqlDataPart.append(COMMA);
	          }
	          sqlAttributePart.append(name);
	          Domain domain = attribute.getDomain();
	          //System.out.println("the value in element is "+value);
	          // domain is null or it is not numbericDomain we assign it text type
	          if (domain == null || !(domain instanceof NumericDomain))
	          {
	        	  //System.out.println("in none numbericDomain "+value);
	        	  sqlDataPart.append(SINGLEQUOTE);
	        	  sqlDataPart.append(value);
	        	  sqlDataPart.append(SINGLEQUOTE);
	          }
	          else
	          {
	        	 String attributeType = getAttributeType(attribute);
	        	 String dataType = mapDataType(attributeType);
	        	 
	        	 try
	        	 {
		        	 if (dataType.equals("FLOAT"))
		        	 {
		        		 //System.out.println("in float numbericDomain "+value);
		        		 Float floatObj = new Float(value);
		        		 //System.out.println("after generate FloatObj numbericDomain "+value);
		        		 float floatNum = floatObj.floatValue();
		        		 //System.out.println("float number "+floatNum);
		        		 sqlDataPart.append(floatNum);
		        		 //System.out.println("end of float");
		        	 }
		        	 else
		        	 {
		        		 //System.out.println("in integer numbericDomain "+value);
		        		 Integer integerObj = new Integer(value);
		        		 //System.out.println("after generate Integer Obj numbericDomain "+value);
		        		 int     integerNum = integerObj.intValue();
		        		 //System.out.println("the int value is "+integerNum);
		        		 sqlDataPart.append(integerNum);
		        		 //System.out.println("end of integer");
		        	 }
	        	 }
	        	 catch (Exception e)
	        	 {
	        		 System.out.println("the error is "+e.getMessage());
	        		 return sqlString;
	        	 }
	          }
	          
	          firstAttribute = false;
	          // insert
	        }
	        // if all data is null, return null value for sql string
	        if (NULLValueCounter == list.length)
	        {
	        	return sqlString;
	        }
	        sqlAttributePart.append(RIGHTPARENTH);
	        sqlDataPart.append(RIGHTPARENTH);
	        sqlDataPart.append(SEMICOLON);
	        //combine the two parts
	        sqlAttributePart.append(sqlDataPart.toString());
	        
	        sqlString = sqlAttributePart.toString();
	        System.out.println("the sql command is "+sqlString);
			return sqlString;
		}
	  
	  /*
	   * Gets attribute type for a given attribute. Attribute types include
	   * text, numeric and et al.
	   * 
	   */
	  protected abstract String getAttributeType(Attribute attribute);
	  
	  
	  /*
	   * Gets the database type base on attribute type. This data type
	   * varies on different db system.
	   */
	  protected abstract String mapDataType(String attributeType);
	  
	  /**
	   * Get the sql command to count how many rows in a given table
	   * @param tableName  the given table name
	   * @return the sql string which can count how many rows
	   */
	  public abstract String getCountingRowNumberSQL(String tableName);

	  

}
