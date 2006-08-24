/**
 *    '$RCSfile: TableMonitor.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-08-24 21:22:19 $'
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
import java.sql.Date;

public class TableMonitor 
{
	private Connection dbConnection  = null;
	private String     dbAdapterName = null;
	private static TableMonitor monitor     = null;
	
	private TableMonitor(Connection dbConnection, String dbAdapterName)
	{
		this.dbConnection = dbConnection;
		this.dbAdapterName = dbAdapterName;
	}
	
	public static TableMonitor getInstance()
	{
		return monitor;
	}
	
	public boolean isTableInDB(String tableName)
	{
		return false;
	}
	
	public Date getCreationDate(String tableName)
	{
		return null;
	}
	
	public Date getLastUsageDate(String tableName)
	{
		return null;
	}
	
	public void setLastUsageDate(String tableName, Date date)
	{
		
	}
	
	public String[] getTableList()
	{
		return null;
	}
	
	public boolean addTableEntry(String tableName)
	{
		return false;
	}
	
	public boolean dropTableEntry(String tableName)
	{
		return false;
	}
	
	public void setDBSize(int size)
	{
		
	}
	
	public void setTableExpirationPolicy(String tableName, int priority)
	{
		
	}
	
	public void freeTableSpace()
	{
		
	}
}
