/**
 *    '$RCSfile: GZipDataHandler.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-11-15 22:49:35 $'
 *   '$Revision: 1.10 $'
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
import java.util.zip.GZIPInputStream;


/**
 * This is a sub-class of CompressedDataHandler class. It will handle download
 * of GZipped data entity. After downloading, the compressed entity will be 
 * uncompressed and written to data storage interface transparently.
 * 
 * @author tao
 *
 */
public class GZipDataHandler extends CompressedDataHandler
{
    /**
     * Constructor
     * @param url  the url (or identifier) of Gzipped data entity
     * @param endPoint the object which provides ecogrid endpoint information
     */
     protected GZipDataHandler(String url, EcogridEndPointInterface endPoint)
     {
            super(url, endPoint);
     }

 
  /*
   * Class methods
   */
  
	/**
	 * Gets the GZipDataHandler Object
     * 
	 * @param url The url (or identifier) of entity need be downloaded
     * @param endPoint  the EcogridEndPointInterface object
	 * @return  GZipDataHandler object associated with the url
	 */
	public static GZipDataHandler getGZipHandlerInstance(
                                  String url, EcogridEndPointInterface endPoint)
	{
		GZipDataHandler  gzipHandler = (GZipDataHandler)getHandlerFromHash(url);
        
		if (gzipHandler == null)
		{
			gzipHandler = new GZipDataHandler(url, endPoint);
		}
        
		return gzipHandler;
	}
	
    
   /*
    * Overwrites the the method in DownloadHandler in order to uncompressed it.
    * It only writes the first file (if have mutiple) into DataStorageInterface
    */
   protected boolean writeRemoteInputStreamIntoDataStorage(InputStream in) 
           throws IOException
   {
	   boolean success = false;
	   //System.out.println("in zip method!!!!!!!!!!!!!!!!!!11");
	   GZIPInputStream zipInputStream = null;
       
	   if (in == null)
	   {
		   return success;
	   }
       
	   try
	   {
		   zipInputStream = new GZIPInputStream(in);
		   //this method will close the zipInpustream, and zipInpustream is not null!!!
		   success = super.writeRemoteInputStreamIntoDataStorage(zipInputStream);
		   //System.out.println("after get succes from super class");
			    
	   }
	   catch (Exception e)
	   {
		   //success = false;
		   System.err.println("the error is "+e.getMessage());
	   }
       
	   //System.out.println("the end of method");
	   return success;
   }
   
   
   
   /*
    *  Gets data from Ecogrid server based on given Ecogrid endpoint
    *  and identifier. This method includes the uncompress process.
    *  It overwrites the one in DownloadHanlder.java
    */
   protected boolean getContentFromEcoGridSource(String endPoint, 
                                                 String ecogridIdentifier)
   {
	   boolean success = false;
       File gzipTmp = writeEcoGridCompressedDataIntoTmp(endPoint, 
                                                        ecogridIdentifier, 
                                                        ".gz");
       System.out.println("The gzip file name is "+gzipTmp);
       
       try
       {
	       if (gzipTmp != null)
	       {
	    	  InputStream stream = new FileInputStream(gzipTmp);
	    	  success = this.writeRemoteInputStreamIntoDataStorage(stream);
	       }
       }
       catch(Exception e)
       {
    	   System.out.println("Error is " + e.getMessage());
       }
       
       return success;
   }

}
