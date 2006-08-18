/**
 *    '$RCSfile: ZipDataHandler.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-08-18 01:40:22 $'
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
package org.ecoinformatics.datamanager.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipDataHandler extends CompressedDataHandler
{
   public boolean uncompress()
   {
	   boolean success = false;
	   try
	   {
		 File unCompressedCacheItemDir = null;
		 File source = null;
	     //log.debug("At unCompressCacheItem method in Zip ojbect");
	     ZipFile zipFile = new ZipFile(source);
	     Enumeration enu = zipFile.entries();
	     // go though every zip entry
	     byte[] array = new byte[300 * 1024];
	     while (enu.hasMoreElements()) 
	     {
	       ZipEntry entry = (ZipEntry) enu.nextElement();
	       // write zipEntry to a local file in Cachedir/unzip/mlocatFilename/
	       String name = entry.getName();
	       if (name != null) 
	       {
	         //log.debug("Zip entry name is " + name);
	         File unzipFile = new File(unCompressedCacheItemDir, name);
	         FileOutputStream fileWriter = new FileOutputStream(unzipFile);
	         InputStream fileReader = zipFile.getInputStream(entry);
	         int len;
	         while ( (len = fileReader.read(array)) >= 0) 
	         {
	           fileWriter.write(array, 0, len);
	         }
	         fileReader.close();
	         fileWriter.close();
	       }
	     }
	     //unCompressedFileList = unCompressedCacheItemDir.list();
	   }
	   catch (Exception e)
	   {
		   
	   }
	   return success;
   }
}
