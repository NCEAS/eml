/**
 *    '$RCSfile: GZipDataHandler.java,v $'
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

public class GZipDataHandler extends CompressedDataHandler
{
   public boolean uncompress()
   {
	   boolean success = false;
	   try
	   {
	      //log.debug("At unCompressCacheItem method in Zip ojbect");
	      // read the gzip file and ungzip it
	      String filename = null;
	      GZIPInputStream gZipFileReader = new GZIPInputStream(new FileInputStream(filename));
	      String unGZipFileName = null;
	      String unGZipFilePath = null + File.separator + unGZipFileName;
	      //log.debug("The unGzip aboslute file path is "+ unGZipFilePath);
	      File unGzipFile = new File(unGZipFilePath);
	      FileOutputStream fileWriter = new FileOutputStream(unGzipFile);
	      byte[] array = new byte[3000 * 1024];
	      int len;
	      while ( (len = gZipFileReader.read(array)) >= 0) 
	      {
	        fileWriter.write(array, 0, len);
	      }
	      gZipFileReader.close();
	      fileWriter.close();
	   }
	   catch (Exception e)
	   {
		   
	   }
	   
	    return success;
   }
}
