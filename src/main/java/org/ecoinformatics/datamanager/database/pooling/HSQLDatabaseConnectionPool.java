/**
 *    '$RCSfile: HSQLDatabaseConnectionPool.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2007-10-05 21:10:17 $'
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
package org.ecoinformatics.datamanager.database.pooling;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecoinformatics.datamanager.database.ConnectionNotAvailableException;
import org.ecoinformatics.datamanager.database.DatabaseConnectionPoolInterface;
import org.hsqldb.jdbc.jdbcDataSource;

/**
 * This class implements DataConnetionPoolInterface to provide a connection for
 * testing. Database information in this class will be read from property file.
 * 
 * @author tao
 * 
 */
public class HSQLDatabaseConnectionPool implements
		DatabaseConnectionPoolInterface {
	
	public static Log log = LogFactory.getLog(HSQLDatabaseConnectionPool.class);

	
	/* Configuration directory and file name for the properties file */
	private static final String CONFIG_NAME = "datamanager";
	private static ResourceBundle options = null;
	private static String databaseName = null;
	private static String user = null;
	private static String password = null;
	private static String databaseAdapterName = null;

	private static jdbcDataSource source = null;
	
	private static int connCount = 0;
	
	/**
	 * Constructor. Loading database parameter from property file
	 * 
	 */
	public HSQLDatabaseConnectionPool() {
		loadOptions();
		initPool();
	}

	private static void initPool() {
		source = new jdbcDataSource();
		source.setDatabase(databaseName);
		source.setUser(user);
		source.setPassword(password);
	}

	/**
	 * Loads Data Manager options from a configuration file.
	 */
	private static void loadOptions() {

		try {
			options = ResourceBundle.getBundle(CONFIG_NAME);
			databaseName = options.getString("dbName");
			user = options.getString("dbUser");
			password = options.getString("dbPassword");
			databaseAdapterName = options.getString("dbAdapter");
		} catch (Exception e) {
			System.out.println("Error in loading options: " + e.getMessage());
		}
	}

	/**
	 * Get dabase adpater name.
	 * 
	 * @return database adapter name
	 */
	public String getDBAdapterName() {
		return databaseAdapterName;
	}

	/**
	 * Gets a database connection from the pool
	 * 
	 * @return checked out connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException,
			ConnectionNotAvailableException {
		Connection connection = null;

		try {
			connection = source.getConnection();
			connCount++;
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			throw (e);
		}

		return connection;
	}

	/**
	 * Returns checked out dabase connection to the pool
	 * 
	 * @param conn
	 *            Connection needs to be returned.
	 * @return indicator if the connection was returned successfully
	 */
	public boolean returnConnection(Connection conn) {
		boolean success = false;

		try {
			conn.close();
			success = true;
			connCount--;
		} catch (Exception e) {
			success = false;
		}

		//log.debug(Thread.currentThread().getName() + ": connection count=" + connCount);

		return success;
	}

	public static void main(String arg[]) {
		HSQLDatabaseConnectionPool pool = new HSQLDatabaseConnectionPool();
		try {
			Connection conn = pool.getConnection();
			log.debug("conn=" + conn);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
