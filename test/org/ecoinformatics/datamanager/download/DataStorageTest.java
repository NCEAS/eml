/**
 *    '$RCSfile: DataStorageTest.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-11-16 00:05:03 $'
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class implements DataStorageInterface in for testing. It does not
 * have a test suite of its own, but is used by other test suites.
 * 
 * @author tao
 *
 */
public class DataStorageTest extends TestCase implements DataStorageInterface 
{

  /*
   * Instance fields
   */
  
	private File tmp = new File(System.getProperty("java.io.tmpdir"));	
	FileOutputStream stream = null;
    
    
   /*
    * Instance methods
    */
    
	/**
	 * Start to serialize remote inputstream. The OutputStream is 
	 * the destination.
     * 
	 * @param identifier  the URL of the stream to be serialized.
	 * @return  the OutputStream to be serialized
	 */
	public OutputStream startSerialize(String identifier) 
	{
		identifier = transformURLToIdentifier(identifier);
		File file = new File(tmp, identifier);
		System.out.println("The tmp dir is "+ 
                           System.getProperty("java.io.tmpdir"));
		
		try
		{
			stream = new FileOutputStream(file);
		}
		catch(Exception e)
		{
			System.err.println("Erorr: "+e.getMessage());
		}
        
		return stream;
	}
	
    
	/**
	 * Finish serialize method. Closes the OutputStream after serialization
     * has completed.
     * 
	 * @param indentifier  the URL of the stream that has finished serializing
	 * @param errorCode    the error code string was generated during
     *                     serialization
	 */
	public void finishSerialize(String indentifier, String errorCode)
	{
		if (stream != null)
		{
			try
			{
			  stream.close();
			}
			catch(Exception e)
			{
				System.err.println("Erorr: "+ e.getMessage());
			}
		}
	}
	
    
	/**
	 * Load data from data storage system. Returns an input stream from which
     * the data can be loaded.
     * 
	 * @param identifier   the URL of the data source to be loaded
	 * @return inputStream, an input stream from which the data can be loaded
	 * @throws DataSourceNotFoundException
	 */
	public InputStream load(String identifier) 
            throws DataSourceNotFoundException
	{
		identifier = transformURLToIdentifier(identifier);
		File file = new File(tmp, identifier);
		FileInputStream inputStream = null;
        
		try
		{
		   inputStream = new FileInputStream(file);
		}
		catch(Exception e)
		{
			throw new DataSourceNotFoundException(e.getMessage());
		}
		
		return inputStream;
	}
	
    
	/**
	 * Gets an entity size (e.g, file size) for a given identifier.
     * 
	 * @param identifier Identifier of a entity
	 * @return The size of entity. If identifier doesn't exist, returns 0.
	 */
	public long getEntitySize(String identifier)
	{
		long size = 0;
		identifier = transformURLToIdentifier(identifier);
		File file = new File(tmp, identifier);
		size = file.length();
        
		return size;
	}
	
    
    /**
     * Transforms a URL to an identifier used by this test class.
     * 
     * @param   url   the URL to be transformed
     * @return  identifier, a string
     */
	private String transformURLToIdentifier(String url)
	{
		String identifier = null;
        
		if (url != null)
		{
		   identifier = "tao" + url.hashCode();
		}
        
		return identifier;
	}
	
    
	/**
	 * Method to test if data already been downloaded or not.
     * 
	 * @param identifier   the URL to the data
	 * @return  true if the data has been downloaded, else false
	 */
	public boolean doesDataExist(String identifier)
	{
		//boolean exist = false;
		identifier = transformURLToIdentifier(identifier);
		System.out.println("the identifier is ============ " + identifier);
		File file = new File(tmp, identifier);
        
		return file.exists();
	}
	
    
	/**
	 * Gets the status of the serialize process to determine whether it
     * has completed or not.
     * 
	 * @param  identifier Identifier of the entity which is being serialized
	 * @return The boolean value if serialize is completed or not
	 */
	public boolean isCompleted(String identifier)
	{
		return true;
	}
	
    
	/**
	 * Gets the result of serialize process - success or failure.
     * 
	 * @param identifier Identifier of the entity which has been serialized
	 * @return sucess or failure
	 */
	public boolean isSuccess(String identifier)
	{
		return true;
	}
    
    
	 /**
	   * This is not a test class, so it is empty here.
	   */
	   public static Test suite()
	   {
	     TestSuite suite = new TestSuite();
	     return suite;
	   }
	   
       
	   /**
		 * Gets the Exception that happened during serialization.
         * 
		 * @return Exception that happened in serialization
		 */
		public Exception getException()
		{
			return null;
		}
        
}
