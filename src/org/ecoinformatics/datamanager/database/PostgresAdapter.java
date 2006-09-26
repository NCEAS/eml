/**
 *    '$RCSfile: PostgresAdapter.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-09-26 05:31:18 $'
 *   '$Revision: 1.6 $'
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

public class PostgresAdapter extends DatabaseAdapter {
  
  /*
   * Class Fields
   */
  public static final String AND    = "AND";
  public static final String BOOLEAN = "Boolean";
  public static final String COMMA = ",";
  public static final String CREATETABLE = "CREATE TABLE";
  public static final String DATETIME = "Timestamp";
  public static final String DELETE = "DELETE";
  public static final String DOUBLE = "Double";
  public static final String DROPTABLE = "DROP TABLE";
  public static final String FLOAT = "Float";
  public static final String FROM = "FROM";
  public static final String INSERT = "INSERT INTO";
  public static final String INTEGER = "Integer";
  //public static final String IFEXISTS = Config.getValue(IFEXISTSPATH);
  public static final String LEFTPARENTH = "(";
  public static final String LIKE = "LIKE";
  public static final String LONG = "Long";
  public static final String QUESTION = "?";
  public static final String QUOTE = "\"";
  public static final String RIGHTPARENTH = ")";
  //public static final String FIELDSEPATATOR = Config.getValue(FIELDSPEPATH);
  public static final String SELECT = "SELECT";
  public static final String SEMICOLON = ";";
  public static final String SPACE = " ";
  //public static final String SETTABLE = Config.getValue(SETTABLEPATH);
  //public static final String SOURCE = Config.getValue(SOURCEPATH);
  //public static final String IGNOREFIRST = Config.getValue(IGNOREFIRSTPATH);
  public static final String STRING = "String";
  public static final String VALUES = "VALUES";
  public static final String WHERE = "WHERE";
  public static final String SINGLEQUOTE = "'";
  
  
  /*
   * Instance Fields
   */
  
  
  /*
   * Constructors
   */
  
  
  /*
   * Class Methods
   */
  
  
  /*
   * Instance Methods
   */

  /**
   * Create a SQL command to generate a table
   * 
   * @param  attributeList   List of attributes that determine the table columns
   * @param  tableName       The table name.
   * @return A string containing the DDL needed to create the table with
   *         its columns
   */
  public String generateDDL(AttributeList attributeList, String tableName)
          throws SQLException {
   String attributeSQL;
   StringBuffer stringBuffer = new StringBuffer();
   //String textFileName   = table.getFileName();
   //int    headLineNumber = table.getNumHeaderLines();
   //String orientation    = table.getOrientation();
   //String delimiter      = table.getDelimiter();
   
   stringBuffer.append(CREATETABLE);
   stringBuffer.append(SPACE);
   stringBuffer.append(tableName);
   stringBuffer.append(LEFTPARENTH);
   attributeSQL = parseAttributeList(attributeList);
   stringBuffer.append(attributeSQL);
   stringBuffer.append(RIGHTPARENTH);
   stringBuffer.append(SEMICOLON);
   String sqlStr = stringBuffer.toString();

   //if (isDebugging) { 
   //  log.debug("The command to create tables is "+sqlStr);
   // }
   return sqlStr;
  }

  
  /**
   * Create a drop table SQL command.
   * 
   * @param tableName  Name of the table to be dropped
   * @return   A SQL string that can be used to drop the table.
   */
  public String generateDropTableSQL(String tableName)
  {
    String sqlString = DROPTABLE + SPACE + tableName + SPACE + SEMICOLON;
    
    return sqlString;
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
   * Add attribute defination in create table command. If one attribute is null
   * or has same error an exception will be throw
   */
  private synchronized String parseAttributeList(AttributeList attributeList) 
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
  
  
  private String getAttributeType(Attribute attribute) {
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
  
  
  private String mapDataType(String attributeType) {
    String dbDataType;
    Map map = new HashMap();
    
    map.put("string", "TEXT");
    map.put("integer", "INTEGER");
    map.put("real", "FLOAT");
    map.put("whole", "INTEGER");
    map.put("natural", "INTEGER");
    
    dbDataType = (String) map.get(attributeType);
    
    return dbDataType;
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
