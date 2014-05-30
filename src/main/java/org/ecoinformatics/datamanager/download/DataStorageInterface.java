/**
 *    '$RCSfile: DataStorageInterface.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-11-15 22:49:35 $'
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
package org.ecoinformatics.datamanager.download;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface provides API for data storage systems. DownloadHandler can
 * write remote source into a class which impletements this interface.
 * @author tao
 *
 */
public interface DataStorageInterface 
{
	/**
	 * Start to serialize remote inputstream. The OutputStream is 
	 * the destination where the DataStorageInterface implementation will
     * store its data.
     * 
	 * @param identifier  the identifier will be written in data storage system
	 * @return The OutputStream which will serialize the remote source
	 */
	public OutputStream startSerialize(String identifier);

    
	/**
	 * Finish serialize method. This method will cleanup serialize process, such
	 * as close output stream.
     * 
	 * @param indentifier the identifier has been written in data storage system
	 * @param errorCode   the errorCode will be passed to the storage system
	 */
	public void finishSerialize(String indentifier, String errorCode);
	
    
	/**
	 * Load given entity from data storage system. Returns an input stream from
     * which the data for this entity can be read.
     * 
	 * @param identifier  Identifier of the entity which need be loaded
	 * @return The InputStream from this entity
	 * @throws DataSourceNotFoundException
	 */
	public InputStream load(String identifier) 
            throws DataSourceNotFoundException;
	
    
	/**
	 * Gets the status if the given entity is already in data storage system.
     * 
	 * @param identifier  Identifier of the entity
	 * @return The boolean value if the entity is in storage system or not.
	 */
	public boolean doesDataExist(String identifier);
	
    
	/**
	 * Gets the status of serialize process.
     * 
	 * @param identifier Identifier of the entity which is being serialized
	 * @return The boolean value if serialize is completed or not
	 */
	public boolean isCompleted(String identifier);
	
    
	/**
	 * Gets the result of serialize process - success or failure.
     * 
	 * @param identifier Identifier of the entity which has been serialized
	 * @return true if success, false if failure
	 */
	public boolean isSuccess(String identifier);
	
    
	/**
	 * Gets the Exception that happened in serialization.
     * 
	 * @return an Exception that happened in serialization
	 */
	public Exception getException();
    
}
