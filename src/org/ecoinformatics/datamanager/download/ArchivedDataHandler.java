/**
 *    '$RCSfile: ArchivedDataHandler.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-09-20 00:45:51 $'
 *   '$Revision: 1.5 $'
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
import java.io.FileOutputStream;
import java.net.URL;

import org.ecoinformatics.ecogrid.queryservice.EcogridGetToStreamClient;

/**
 * This class is a sub-class of DownloadHandler. It will handle archived data
 * Entity such as tar file. After downloading, the file will be unarchived and
 * written to data storage system.
 * @author tao
 *
 */
public abstract class ArchivedDataHandler extends DownloadHandler
{
	/**
	 * Constructor
	 * @param url  The url of entity which need be downloaded
	 */
    protected ArchivedDataHandler(String url)
    {
    	super(url);
    }
  
   /*
    * Method to downloadc compressed file from ecogrid to a tmp dir
    * This is tmp solution, we need figure out to remove this step.
    * The tmpZip File will be returned. If download failed, null will be return
    */
   protected File writeEcoGridArchivedDataIntoTmp(String endPoint, String ecogridIdentifier, String suffix)
   {
       
        File compressedFile = null;
		if (endPoint != null && ecogridIdentifier != null)
		{
		        //log.debug("Get " + identifier + " from " + endPoint);
		        
		        try
		        {
		            //fatory
		            //log.debug("This is instance pattern");
		            
		            URL endPointURL = new URL(endPoint);
		            EcogridGetToStreamClient ecogridClient = new EcogridGetToStreamClient(endPointURL);
		            String localIdentifier = ecogridIdentifier+suffix;
		            File tmp = new File(System.getProperty("java.io.tmpdir"));
		            compressedFile = new File(tmp, localIdentifier);
		            FileOutputStream stream = new FileOutputStream(compressedFile);
		     		if (stream != null)
		            {
		                BufferedOutputStream bos = new BufferedOutputStream(stream);
		                ecogridClient.get(ecogridIdentifier, bos);
		                bos.flush();
		                bos.close();
		                stream.close();
			             
		            }
		      	            
		        }
		        catch(Exception ee)
		        {
		            //log.debug("EcogridDataCacheItem - error connecting to Ecogrid ", ee);
		            System.out.println("Error: "+ee.getMessage());
		        }
		}
		return compressedFile;
   }
}
