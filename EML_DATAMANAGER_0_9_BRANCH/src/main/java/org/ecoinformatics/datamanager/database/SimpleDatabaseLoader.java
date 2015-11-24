/**
 *    '$RCSfile: SimpleDatabaseLoader.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2008-06-23 23:43:01 $'
 *   '$Revision: 1.1 $'
 *
 *  For Details: http://ecoinformatics.org
 *
 * Copyright (c) 2008 The Regents of the University of California.
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

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.download.DataSourceNotFoundException;
import org.ecoinformatics.datamanager.download.DataStorageInterface;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.Entity;

/**
 * This is a very bare-bones class for loading Entity data into a table
 *  
 * 
 * @author Ben Leinfelder (based on Jing Tao's DatabaseLoader)
 * 
 */
public class SimpleDatabaseLoader implements DataStorageInterface, Runnable {

	/*
	 * Class fields
	 */

	private static TableMonitor tableMonitor = null;
	public static Log log = LogFactory.getLog(SimpleDatabaseLoader.class);

	/*
	 * Instance fields
	 */
	private Entity entity = null;
	private TextDataReader dataReader = null;
	private DatabaseAdapter databaseAdapter = null;
	private boolean completed = false;
	private boolean success = false;
	private Exception exception = null;

	/*
	 * Constructors
	 */

	/**
	 * Constructor of this class. Sets up the necessary components for loading data
	 * 
	 * @param dbAdapter
	 *            Database adapter name
	 * @param entity
	 *            Metadata information associated with the loader
	 * @param dataReader
	 *            the data for the loader            
	 */
	public SimpleDatabaseLoader(String databaseAdapterName, Entity entity, TextDataReader dataReader) {

		//the description
		this.entity = entity;

		//the data
		this.dataReader = dataReader;

		/* Initialize the databaseAdapter and tableMonitor fields */
		this.databaseAdapter = DataManager.getDatabaseAdapterObject(databaseAdapterName);
		
		try {
			tableMonitor = new TableMonitor(databaseAdapter);
		} 
		catch (SQLException e) {
			log.error("problem setting table monitor: " + e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * 
	 */
	public void run() {
		
		if (entity == null) {
			success = false;
			completed = true;
			return;
		}
		
		//don't reload data if we have it
		if (doesDataExist(entity.getEntityIdentifier())) {
			return;
		}
		
		AttributeList attributeList = entity.getAttributeList();
		String tableName = entity.getDBTableName();
		
		String insertSQL = "";
		Vector rowVector = new Vector();
		Connection connection = null;
		
		try {
			rowVector = this.dataReader.getOneRowDataVector();
			connection = DataManager.getConnection();
			if (connection == null) {
				success = false;
				exception = new Exception("The connection to db is null");
				completed = true;
				return;
			}
			connection.setAutoCommit(false);
			while (!rowVector.isEmpty()) {
				insertSQL = 
					databaseAdapter.generateInsertSQL(
							attributeList,
							tableName, 
							rowVector);
				if (insertSQL != null) {
					PreparedStatement statement = 
						connection.prepareStatement(insertSQL);
					statement.execute();
				}
				rowVector = this.dataReader.getOneRowDataVector();
			}

			connection.commit();
			success = true;
		} 
		catch (Exception e) {
			log.error("problem while loading data into table.  Error message: "
					+ e.getMessage());
			e.printStackTrace();
			log.error("SQL string to insert row:\n" + insertSQL);

			success = false;
			exception = e;

			try {
				connection.rollback();
			} catch (Exception ee) {
				System.err.println(ee.getMessage());
			}
		} 
		finally {
			try {
				connection.setAutoCommit(true);
			} 
			catch (Exception ee) {
				log.error(ee.getMessage());
			}

			DataManager.returnConnection(connection);
		}
	}

	/**
	 * Determines whether the data table 
	 * already exists in the database and is loaded with data. This method is
	 * mandated by the DataStorageInterface.
	 * 
	 * First, check that the data table exists. Second, check that the row count
	 * is greater than zero. (We want to make sure that the table has not only
	 * been created, but has also been loaded with data.)
	 * 
	 * @param identifier
	 *            the identifier for the data table
	 * @return true if the data table has been loaded into the database, else
	 *         false
	 */
	public boolean doesDataExist(String identifier) {
		boolean doesExist = false;

		try {
			String tableName = 
				tableMonitor.identifierToTableName(identifier);
			doesExist = tableMonitor.isTableInDB(tableName);

			if (doesExist) {
				int rowCount = tableMonitor.countRows(tableName);
				doesExist = (rowCount > 0);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return doesExist;
	}

	/**
	 * Gets the completion status of the serialize process.
	 * 
	 * @param identifier
	 *            Identifier of the entity which is being serialized
	 * @return true if complete, false if not complete
	 */
	public boolean isCompleted(String identifier) {
		return completed || doesDataExist(identifier);
	}

	/**
	 * Gets the success status of the serialize process - success or failure
	 * 
	 * @param identifier
	 *            Identifier of the entity which has been serialized
	 * @return true if success, else false
	 */
	public boolean isSuccess(String identifier) {
		return success || doesDataExist(identifier);
	}


	public Exception getException() {
		return exception;
	}

	public void finishSerialize(String indentifier, String errorCode) {
		log.warn("unimplemented method called");
		return;
	}

	public InputStream load(String identifier)
			throws DataSourceNotFoundException {
		log.warn("unimplemented method called");
		return null;
	}

	public OutputStream startSerialize(String identifier) {
	    log.warn("unimplemented method called");
		return null;
	}

}
