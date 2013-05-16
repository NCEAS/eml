/**
 *    '$RCSfile: ZipDataHandler.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-11-15 22:49:35 $'
 *   '$Revision: 1.11 $'
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
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.ecoinformatics.datamanager.parser.Entity;


/**
 * This is a sub-class of CompressedDataHandler class. It will handle 
 * download Zipped data entity. After downloading, the compressed entity 
 * will be uncompressed and written to DatastorageInterface transparently.
 * 
 * @author tao
 *
 */
public class ZipDataHandler extends CompressedDataHandler
{
    /*
     * Constructors
     */
  
    /**
     * Constructor
     * 
     * @param entity The entity object whose data is being downloaded
     * @param url  The url (or identifier) of the zipped entity
     * @param endPoint the object which provides ecogrid endpoint information
     */
    protected ZipDataHandler(Entity entity, String url, EcogridEndPointInterface endPoint)
    {
      super(entity, url, endPoint);
    }


    /**
     * Constructor
     * 
     * @param url  The url (or identifier) of the zipped entity
     * @param endPoint the object which provides ecogrid endpoint information
     */
    protected ZipDataHandler(String url, EcogridEndPointInterface endPoint)
    {
      super(url, endPoint);
    }

 
	/*
     * Class methods
	 */
  
    /**
     * Gets the GZipDataHandler Object.
     * 
     * @param  entity The entity object whose data is being downloaded. Used for
     *                quality reporting. Can be set to null in cases where we 
     *                don't need a back-pointer to the entity.
     * @param  url The url (or identifier) of entity need be downloaded
     * @param  endPoint the object which provides ecogrid endpoint information
     */
    public static ZipDataHandler getZipHandlerInstance (Entity entity,
                                                        String url, 
                                                        EcogridEndPointInterface endPoint) {
      ZipDataHandler  zipHandler = (ZipDataHandler) getHandlerFromHash(url);
          
      if (zipHandler == null) {
        zipHandler = new ZipDataHandler(entity, url, endPoint);
      }
      
      return zipHandler;
    }
      
      
    /*
     * Instance methods
     */
	
    
   /*
    * Overwrite the method in DownloadHandler in order to uncompressed 
    * entity first. We only write first file (if it has multiple entities) 
    * to DataStorageInterface.
    */
   protected boolean writeRemoteInputStreamIntoDataStorage(InputStream in) 
           throws IOException
   {
	   boolean success = false;
	   ZipInputStream zipInputStream = null;
       
	   if (in == null)
	   {
		   return success;
	   }
       
	   try
	   {
		   zipInputStream = new ZipInputStream(in);
		   ZipEntry entry = zipInputStream.getNextEntry();
		   int index = 0;
       
      if (entry != null) {
        while (entry != null && index < 1) {
          if (entry.isDirectory()) {
            entry = zipInputStream.getNextEntry();
            continue;
          }

          // this method will close the zipInpustream, and zipInpustream is not
          // null!!!
          success = super.writeRemoteInputStreamIntoDataStorage(zipInputStream);
          index++;
        }
      }
      else {
        throw new IOException("No entries found in zip file.");
      }
	   }
     catch (Exception e)
     {
       String errorMsg = String.format("%s %s: %s", 
                                       ONLINE_URLS_EXCEPTION_MESSAGE,
                                       "Error downloading zip file", 
                                       e.getMessage()
                                      );       
       throw new IOException(errorMsg);
     }
       
	   return success;
   }
   
     
   /*
    *  Gets data from Ecogrid server base on given Ecogrid endpoint and 
    *  identifier. This method includes the uncompress process.
    *  It overwrite the one in DownloadHanlder.java
    */
   protected boolean getContentFromEcoGridSource(String endPoint, 
                                                 String ecogridIdentifier)
   {
	   boolean success = false;
       File zipTmp = writeEcoGridCompressedDataIntoTmp(endPoint, 
                                                       ecogridIdentifier, 
                                                       ".1");
       try
       {
	       if (zipTmp != null)
	       {
	    	  InputStream stream = new FileInputStream(zipTmp);
	    	  success = this.writeRemoteInputStreamIntoDataStorage(stream);
	       }
       }
       catch(Exception e)
       {
    	   System.out.println("Error is "+e.getMessage());
       }
       
       return success;   
   }

}
