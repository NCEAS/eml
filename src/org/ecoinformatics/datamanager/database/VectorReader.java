/**
 *    '$RCSfile: VectorReader.java,v $'
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

import java.util.Vector;

/**
 * This is a very basic concrete subclass of the TextDataReader.
 * The implementation for getOneRowDataVector() barely
 * needs to encapsulate interacting directly with Vector objects.
 * 
 * This is intended to provide callers with a mechanism for wrapping
 * tabular data without making use of the more complicated TextDataReaders
 * that use Entity metadata to parse tabular or delimited datasets.
 *  
 * @author leinfelder
 *
 */
public class VectorReader extends TextDataReader {

	private Vector records;
	private int count = 0;
	
	public VectorReader() {
		this.records = new Vector();
	}
	
	public VectorReader(Vector records) {
		this.records = records;
	}
	
	public void addOneRowDataVector(Vector row) {
		this.records.add(row);
	}
	
	public Vector getOneRowDataVector() throws Exception {
		Vector retVector = new Vector();
		if (count < records.size()) {
			retVector = (Vector) records.get(count);
			count++;
		}
		return retVector;
	}

}
