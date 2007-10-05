/**
 *    '$RCSfile: DatabaseConnectionPoolFactory.java,v $'
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

import edu.ucsb.nceas.utilities.Options;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecoinformatics.datamanager.database.ConnectionNotAvailableException;
import org.ecoinformatics.datamanager.database.DatabaseConnectionPoolInterface;

/**
 * This class provides an implementation of DataConnetionPoolInterface 
 * Implementation and database information for this class will be read from property file.
 * 
 * @author leinfelder
 * 
 */
public class DatabaseConnectionPoolFactory {
	
	public static Log log = LogFactory.getLog(DatabaseConnectionPoolFactory.class);

	
	/* Configuration directory and file name for the properties file */
	private static final String CONFIG_DIR = "lib/datamanager";
	private static final String CONFIG_NAME = "pool.properties";
	
	private static Options options = null;
	private static String implementationClass = null;
		
	static {
		loadOptions();
	}
	
	/**
	 * Constructor. Loading database parameter from property file
	 * 
	 */
	public DatabaseConnectionPoolFactory() {
	}

	/**
	 * Loads Data Manager options from a configuration file.
	 */
	private static void loadOptions() {
		String configDir = CONFIG_DIR;
		File propertyFile = new File(configDir, CONFIG_NAME);

		try {
			options = Options.initialize(propertyFile);
			implementationClass = options.getOption("implementationClass");
		} 
		catch (IOException e) {
			System.out.println("Error in loading options: " + e.getMessage());
		}
	}

	public static DatabaseConnectionPoolInterface getDatabaseConnectionPoolInterface() {
		DatabaseConnectionPoolInterface instance = null;
		try {
			instance = 
				(DatabaseConnectionPoolInterface) Class.forName(implementationClass).newInstance();
		} catch (Exception e) {
			log.error(e.getMessage() 
					+ ": could not create DatabaseConnectionPoolInterface implementation: " 
					+ implementationClass);
			e.printStackTrace();
		} 
		
		return instance;

	}

	public static void main(String arg[]) {
		Connection conn = null;
		try {
			conn = DatabaseConnectionPoolFactory.getDatabaseConnectionPoolInterface().getConnection();
			log.debug("conn=" + conn);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
