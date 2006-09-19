/**
 *    '$RCSfile: ZipDataHandler.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-09-19 00:10:52 $'
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
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


import org.ecoinformatics.ecogrid.queryservice.EcogridGetToStreamClient;

public class ZipDataHandler extends CompressedDataHandler
{
	/**
	 * Constructor
	 * @param url
	 * @param identifier
	 */
    public ZipDataHandler(String url)
    {
    	super(url);
    }
    /**
     * This method will uncompress the zip inputstream
     * and write to datastorage interface
     */
   public boolean uncompress()
   {
	   return true;
   }
   
   /**
    * Overwrite the the method in DownloadHandler in order to uncompressed it.
    * we only write first file (if have mutiple
    * @param in
    * @return
    */
   protected boolean writeRemoteInputStreamIntoDataStorage(InputStream in) throws IOException
   {
	   boolean success = false;
	   //System.out.println("in zip method!!!!!!!!!!!!!!!!!!11");
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
		   //System.out.println("in zip method!!!!!!!!!!!!!!!!!!11");
		   while (entry != null && index <1)
		   {
			  //System.out.println("the zip entry name is "+entry.getName());
			  if (entry.isDirectory())
			  {
				  entry = zipInputStream.getNextEntry();
				  continue;
			  }
			  // this method will close the zipInpustream, and zipInpustream is not null!!!
		      success = super.writeRemoteInputStreamIntoDataStorage(zipInputStream);
		      //System.out.println("after get succes from super class");
		      index++;
		      //System.out.println("end of while ");
		   }
		   //System.out.println("zip sucess flag "+success);
		   return success;
		   
	   }
	   catch (Exception e)
	   {
		   success =false;
		   //System.out.println("the error is "+e.getMessage());
	   }
	   //System.out.println("the end of method");
	   return success;
   }
   
   
   
   /*
    *  This method will get data from ecogrid server base on given
    *  It overwrite the one in DownloadHanlder.java
    */
   protected boolean getContentFromEcoGridSource(String endPoint, String ecogridIdentifier)
   {
	   boolean success = false;
       File zipTmp = writeEcoGridCompressedDataIntoTmp(endPoint, ecogridIdentifier, ".1");
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
