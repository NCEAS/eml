/**
 *    '$RCSfile: DatabaseAdapter.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-11-01 00:28:24 $'
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
   * well-formedness of a table name. This method simply looks for illegal
   * table name characters in the entity name and replaces them with underscore 
   * characters.
   * 
   * @param entityName   the entity name
   * @return             a well-formed table name corresponding to the entity\
   *                     name
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
   * Creates a sql command to generate table.
   * 
   * @param  attributeList   An AttributeList object holding the entity 
   *                         attributes.
   * @param  tableName       The name of the table to be generated
   * @return the DDL string. In the parent DatabaseAdapter class, the string is
   *                         empty.
   */
  public String generateDDL(AttributeList attributeList, String tableName)
      throws SQLException {
    String ddlString = "";

    return ddlString;
  }

  
  /**
   * Creates a drop table sql command.
   * 
   * @param  tableName       The name of the table to be dropped.
   * @return the SQL string. In the parent DatabaseAdapter class, the string is
   *                         empty.
   */
  public String generateDropTableSQL(String tableName) {
    String sqlString = "";

    return sqlString;
  }

  
  /**
   * The map between metadat data type and database native data type.
   * 
   * @return   In the parent DatabaseAdapter class, returns null.
   */
  public Map getDataTypeMap() {
    Map typeMap = null;
    
    return typeMap;
  }
  

  /**
   * Transforms ANSI selection SQL to a native db SQL command.
   * 
   * @param   ANSISQL       The ANSI SQL string.
   * @return  The native SQL string. In the parent DatabaseAdapter class, the
   *          string is empty.
   */
  public String transformSelectionSQL(String ANSISQL) {
    String sqlString = "";

    return sqlString;
  }
	
  
  /*
   * Adds the attribute definitions to a create table command.
   * If one attribute is null or has some error an exception will be thrown.
   */
  protected String parseAttributeList(AttributeList attributeList)
      throws SQLException {
    Attribute[] list = attributeList.getAttributes();
    StringBuffer attributeSql = new StringBuffer();

    if (list == null || list.length == 0) {
      // log.debug("There is no attribute definition in entity");
      throw new SQLException("No attribute definition found in entity");
    }

    int size = list.length;
    // DBDataTypeResolver dataTypeResolver = new DBDataTypeResolver();
    boolean firstAttribute = true;

    for (int i = 0; i < size; i++) {
      Attribute attribute = list[i];

      if (attribute == null) {
        // log.debug("One attribute definition is null attribute list");
        throw new SQLException("Attribute list contains a null attribute");
      }

      String name = attribute.getName();
      String attributeType = getAttributeType(attribute);
      String dbDataType = mapDataType(attributeType);

      // String dataType = attribute.getDataType();
      // String dbDataType = "VARCHAR(32)";
      // String dbDataType = dataTypeResolver.resolveDBType(dataType);
      // String javaDataType = dataTypeResolver.resolveJavaType(dataType);
      // dbJavaDataTypeList.add(javaDataType);

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
   * Creates a SQL command to insert data. If some error happens, null will be
   * returned.
   * 
   * @param attributeList  AttributeList which will be inserted
   * @param tableName      The name of the table which the data will be inserted into
   * @param oneRowData     The data vector which contains data to be inserted
   * @return A SQL String that can be run to insert one row of data into table
   */
  public String generateInsertSQL(AttributeList attributeList,
                                  String tableName, 
                                  Vector oneRowData) {
    String sqlString = null;
    int NULLValueCounter = 0;
    
    if (attributeList == null) {
      //log.debug("There is no attribute defination in entity");
      return sqlString;
    }

    if (oneRowData == null || oneRowData.isEmpty()) {
      return sqlString;
    }

    StringBuffer sqlAttributePart = new StringBuffer();
    StringBuffer sqlDataPart = new StringBuffer();
    sqlAttributePart.append(INSERT);
    sqlAttributePart.append(SPACE);
    sqlAttributePart.append(tableName);
    sqlAttributePart.append(LEFTPARENTH);
    sqlDataPart.append(SPACE);
    sqlDataPart.append(VALUES);
    sqlDataPart.append(SPACE);
    sqlDataPart.append(LEFTPARENTH);
    Attribute[] list = attributeList.getAttributes();
    
    if (list == null || list.length == 0) {
      //log.debug("There is no attribute definition in entity");
      return sqlString;
    }
    
    int size = list.length;
    // column name part
    boolean firstAttribute = true;
    
    for (int i = 0; i < size; i++) {
      // if data vector
      Object obj = oneRowData.elementAt(i);
      String value = null;
      
      if (obj == null) {
        NULLValueCounter++;
        continue;
      } else {
        value = (String) obj;
      }
      
      Attribute attribute = list[i];
      
      if (attribute == null) {
        //log.debug("One attribute definition is null attribute list");
        return sqlString;
      }
      
      String name = attribute.getName();
      
      if (!firstAttribute) {
        sqlAttributePart.append(COMMA);
        sqlDataPart.append(COMMA);
      }
      
      sqlAttributePart.append(name);
      Domain domain = attribute.getDomain();
      //System.out.println("the value in element is "+value);
      
      /* If domain is null or it is not NumericDomain we assign it text type
       * and wrap single quotes around the value.
       */
      if (domain == null || !(domain instanceof NumericDomain)) {
        //System.out.println("in non NumericDomain " + value);
        sqlDataPart.append(SINGLEQUOTE);
        sqlDataPart.append(value);
        sqlDataPart.append(SINGLEQUOTE);
      } 
      /* Else we have a NumericDomain. Determine whether it is a float or
       * integer.
       */
      else {
        String attributeType = getAttributeType(attribute);
        String dataType = mapDataType(attributeType);

        try {
          if (dataType.equals("FLOAT")) {
            //System.out.println("in float NumericDomain " + value);
            Float floatObj = new Float(value);
            /* System.out.println("after generating floatObj numericDomain "
                                 + value); */
            float floatNum = floatObj.floatValue();
            //System.out.println("float number " + floatNum);
            sqlDataPart.append(floatNum);
            //System.out.println("end of float");
          } 
          else {
            //System.out.println("in integer NumericDomain " + value);
            Integer integerObj = new Integer(value);
            //System.out.println("after generate Integer Obj NumericDomain "
            //                   + value);
            int integerNum = integerObj.intValue();
            //System.out.println("the int value is " + integerNum);
            sqlDataPart.append(integerNum);
            //System.out.println("end of integer");
          }
        } 
        catch (Exception e) {
          System.out.println("Error determining numeric value: " + 
                             e.getMessage());
          return sqlString;
        }
      }

      firstAttribute = false;
      // insert
    }
    
    // If all data is null, return null value for sql string.
    if (NULLValueCounter == list.length) {
      return sqlString;
    }
    
    sqlAttributePart.append(RIGHTPARENTH);
    sqlDataPart.append(RIGHTPARENTH);
    sqlDataPart.append(SEMICOLON);
    
    // Combine the two parts
    sqlAttributePart.append(sqlDataPart.toString());
    sqlString = sqlAttributePart.toString();
    System.out.println("the sql command is " + sqlString);
    
    return sqlString;
  }
  
  
  /**
   * Gets attribute type for a given attribute. Attribute types include
   * text, numeric and et al.
   * 
   * @param  attribute  the Attribute object
   * @return a String holding the attribute type
   */
  protected abstract String getAttributeType(Attribute attribute);


  /**
   * Gets the database type based on attribute type. This data type
   * varies on different db system.
   * 
   * @param  attributeType  a String holding the attribute type
   * @return a String holding the database type
   */
  protected abstract String mapDataType(String attributeType);


  /**
   * Gets the sql command to count the rows in a given table.
   * 
   * @param tableName  the given table name
   * @return the sql string which can count how many rows
   */
  public abstract String getCountingRowNumberSQL(String tableName);

}
