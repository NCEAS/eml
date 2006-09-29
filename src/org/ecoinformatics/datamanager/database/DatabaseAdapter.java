/**
 *    '$RCSfile: DatabaseAdapter.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-09-29 21:15:39 $'
 *   '$Revision: 1.4 $'
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

import org.ecoinformatics.datamanager.parser.AttributeList;

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
    
    legalName = legalName.replace(' ', '_');
    legalName = legalName.replace('-', '_');
    
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
	 * Create a sql command to insert data
   * 
	 * @param attributeList
	 * @param tableName
	 * @param oneRowData
	 * @return
	 */
	public String generateInsertSQL(AttributeList attributeList, 
                                  String tableName , 
                                  Vector oneRowData) {
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

}
