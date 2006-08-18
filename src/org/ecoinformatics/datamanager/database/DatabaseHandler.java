/**
 *    '$RCSfile: DatabaseHandler.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-08-18 01:39:42 $'
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
package org.ecoinformatics.datamanager.database;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;

import org.ecoinformatics.datamanager.download.DataSourceNotFoundException;
import org.ecoinformatics.datamanager.download.DataStorageInterface;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;

public class DatabaseHandler implements DataStorageInterface
{
	private Connection dbConnection  = null;
	private String     dbAdapterName = null;
	
	public DatabaseHandler(Connection dbConnection, String dbAdapterName)
	{
		this.dbConnection = dbConnection;
		this.dbAdapterName = dbAdapterName;
	}
	
	
	
	/**
	 * Start to serialize remote inputstream. The OutputStream is 
	 * the destination.
	 * @param identifier
	 * @return
	 */
	public OutputStream startSerialize(String identifier)
	{
		return null;
	}
	
	public void finishSerialize(String indentifier, String errorCode)
	{
		
	}
	
	public boolean generateTables(DataPackage dataPackage)
	{
		return false;
	}
	
	public boolean generateTable(Entity entity)
	{
		return false;
	}
	
	public boolean loadData(DataPackage dataPackage)
	{
		return false;
	}
	
	public boolean loadData(Entity entity)
	{
		return false;
	}
	
	public ResultSet selectData(String ANSISQL, DataPackage[] dataPackage)
	{
		return null;
	}
	
	public boolean dropTables(DataPackage dataPackage)
	{
		return false;
	}
	
	public boolean dropTable(Entity entity)
	{
		return false;
	}
	
	
	public InputStream load(String identifier) throws DataSourceNotFoundException
	{
		return null;
	}
	
	public boolean doesDataExist(String identifier)
	{
		return false;
	}
}
