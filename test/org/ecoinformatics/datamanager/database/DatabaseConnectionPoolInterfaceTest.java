/**
 *    '$RCSfile: DatabaseConnectionPoolInterfaceTest.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-11-10 19:18:18 $'
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class implements DataConnetionPoolInterface to provide
 * a connection for testing. Database information in this class
 * will be read from property file.
 * @author tao
 *
 */
public class DatabaseConnectionPoolInterfaceTest implements DatabaseConnectionPoolInterface
{
	  /* Configuration directory and file name for the properties file */
	    private static final String CONFIG_NAME = "datamanager";
	    private static ResourceBundle properties = null;
	    private static String dbDriver = null;
	    private static String dbURL    = null;
	    private static String dbUser  = null;
	    private static String dbPassword = null;
	    private static String databaseAdapterName = null;
	    
	    /**
	     * Constructor. Loading database parameter from property file
	     *
	     */
	    public DatabaseConnectionPoolInterfaceTest()
	    {
	    	loadOptions();
	    }
	    
	    /**
	     * Loads Data Manager options from a configuration file.
	     */
	    private static void loadOptions() {
	      properties = ResourceBundle.getBundle(CONFIG_NAME);

	      try {
	        dbDriver = properties.getString("dbDriver");
	        dbURL = properties.getString("dbURL");
	        dbUser = properties.getString("dbUser");
	        dbPassword = properties.getString("dbPassword");
	        databaseAdapterName = properties.getString("dbAdapter");
	      } 
	      catch (Exception e) {
	        System.out.println("Error in loading properties: " + e.getMessage());
	      }
	    }
	   
	    /**
	     * Get database adapter name.
	     * @return database adapter name
	     */
	    public String getDBAdapterName()
	    {
	    	return databaseAdapterName;
	    }
	
	  /**
	    * Gets a database connection from the pool
		* @return checked out connection
	    * @throws SQLException
	    */
	   public Connection getConnection() 
               throws SQLException, ConnectionNotAvailableException
	   {
		      Connection connection = null;
              
		      try {
		        Class.forName(dbDriver);
		      } 
		      catch(java.lang.ClassNotFoundException e) {
		        System.err.print("ClassNotFoundException: "); 
		        System.err.println(e.getMessage());
		        throw(new SQLException(e.getMessage()));
		      }

		      try {
		        connection = DriverManager.getConnection(dbURL, 
		                                               dbUser, 
		                                               dbPassword);
		      } 
		      catch(SQLException e) {
		        System.err.println("SQLException: " + e.getMessage());
		        throw(e);
		      }
		   		    
		    return connection;
	   }
	   
       
	   /**
	    * Returns checked out dabase connection to the pool
	    * @param conn Connection needs to be returned.
	    * @return indicator if the connection was returned successfully
	    */
	   public boolean returnConnection(Connection conn)
	   {
		   boolean success = false;
           
		   try
		   {
			   conn.close();
			   success = true;
		   }
		   catch(Exception e)
		   {
			   success = false;   
		   }
           
		   return success;
	   }
}

