/**
 *    '$RCSfile: CompressedDataHandler.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2008-08-08 21:40:51 $'
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import org.ecoinformatics.ecogrid.authenticatedqueryservice.AuthenticatedQueryServiceGetToStreamClient;
import org.ecoinformatics.ecogrid.queryservice.QueryServiceGetToStreamClient;

/**
 * This is the sub-class of DownloadHander class. It will handle download 
 * compressed data entity, such as gzipped or zipped entity. After downloading
 * the compressed entity will be uncompressed and written to data storage 
 * interface.
 * 
 * @author tao
 *
 */
public abstract class CompressedDataHandler extends DownloadHandler 
{
  /*
   * Constructors
   */	 
	
	 /**
	  * Constructor
	  * @param url url of entity
	  * @param endPoint the object which provides ecogrid endpoint information
	  */
	 protected CompressedDataHandler(String url, EcogridEndPointInterface endPoint)
	 {
	    	super(url, endPoint);
	 }

  /*
   * Instance methods
   */
	    
     /**
      * Method to download compressed file from ecogrid to a tmp dir.
      * This is a temporary solution, we need figure out how to remove this 
      * step. The tmpZip File will be returned. If download failed, 
      * null will be returned.
      *
      * 
      * @param endPoint             EcoGrid end point value
      * @param ecogridIdentifier    EcoGrid identifier value
      * @param suffix               suffix of the local identifier              
      * @return  compressedFile, the temporary File object         
      */
     protected File writeEcoGridCompressedDataIntoTmp(String endPoint, 
                                                      String ecogridIdentifier, 
                                                      String suffix)
     {      
        File compressedFile = null;
        
     	if (endPoint != null && ecogridIdentifier != null)
     	{
  	        //log.debug("Get " + identifier + " from " + endPoint);
  	        
  	        try
  	        {
  	            //fatory
  	            //log.debug("This is instance pattern");
  	            String localIdentifier = ecogridIdentifier+suffix;
  	            File tmp = new File(System.getProperty("java.io.tmpdir"));
  	            compressedFile = new File(tmp, localIdentifier);
  	            FileOutputStream stream = new FileOutputStream(compressedFile);
                
           		if (stream != null)
  	            {
                    BufferedOutputStream bos = new BufferedOutputStream(stream);
                    
                    URL endPointURL = new URL(endPoint);
      	            if (sessionId != null) {
      	            	AuthenticatedQueryServiceGetToStreamClient authenticatedEcogridClient = 
                            new AuthenticatedQueryServiceGetToStreamClient(endPointURL);
      	            	authenticatedEcogridClient.get(ecogridIdentifier, sessionId, bos);
      	            }
      	            else {
      	            	QueryServiceGetToStreamClient ecogridClient = 
                                          new QueryServiceGetToStreamClient(endPointURL);
      	            	ecogridClient.get(ecogridIdentifier, bos);
      	            }
                    
                    bos.flush();
                    bos.close();
                    stream.close();
  	            }	      	            
  	        }
  	        catch(Exception ee)
  	        {
  	            //log.debug(
                //   "EcogridDataCacheItem - error connecting to Ecogrid ", ee);
  	            System.out.println("Error: "+ee.getMessage());
  	        }
     	}
        
     	return compressedFile;
     }
}
