/**
 *    '$RCSfile: GZipDataHandler.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-09-19 18:35:12 $'
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;


import org.ecoinformatics.ecogrid.queryservice.EcogridGetToStreamClient;

/**
 * This is a sub-class of CompressedDataHandler class. It will handle download GZipped
 * data entity. After downloading, the compressed entity will be uncompressed and written to 
 * data storage interface transparently.
 * @author tao
 *
 */
public class GZipDataHandler extends CompressedDataHandler
{
	/**
	 * Constructor
	 * @param url  the url (or identifier) of Gzipped data entity
	 */
   public GZipDataHandler(String url)
   {
	    	super(url);
   }
  
   
   /*
    * Overwrite the the method in DownloadHandler in order to uncompressed it.
    * It only writes the first file (if have mutiple) into DataStorageInterface
    */
   protected boolean writeRemoteInputStreamIntoDataStorage(InputStream in) throws IOException
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
    *  Get data from Ecogrid server base on given Ecogrid endpoint and identifier.
    *  This method includes the uncompress process.
    *  It overwrite the one in DownloadHanlder.java
    */
   protected boolean getContentFromEcoGridSource(String endPoint, String ecogridIdentifier)
   {
	   boolean success = false;
       File gzipTmp = writeEcoGridCompressedDataIntoTmp(endPoint, ecogridIdentifier, ".gz");
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
    	   System.out.println("Error is "+e.getMessage());
       }
       return success;
     
   }
   
   
  
}
