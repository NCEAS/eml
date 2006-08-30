/**
 *    '$RCSfile: DownloadHandler.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-08-30 00:03:18 $'
 *   '$Revision: 1.2 $'
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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import org.ecoinformatics.ecogrid.queryservice.EcogridGetToStreamClient;


public class DownloadHandler implements Runnable 
{
	private String identifier = null;
	private String url        = null;
	private DataStorageInterface[] dataStorageClassList = null;
	private boolean completed = false;
	private boolean success = false;
	private boolean busy = false;
	private static final String ECOGRIDENDPOINT = "";
	private static final String SRBENDPOINT     = "";
	
	/**
	 * Constructor of this class
	 * @param url
	 * @param identifier
	 * @param dataStorageClassList
	 */
	public DownloadHandler(String url, String identifier, DataStorageInterface[] dataStorageClassList)
	{
		this.url = url;
		this.identifier = identifier;
		this.dataStorageClassList = dataStorageClassList;
	}
	
	/**
	 * This method will download data for the given url
	 */
    public void run()
    {
    	
    }
    
    /**
     * This method will do real download work and will be called by run method
     */
    /*public boolean download()
    {
    	
    	return success;
    }*/
    
    public boolean isBusy()
    {
    	return busy;
    }
    
    public boolean isCompleted()
    {
    	return completed;
    }
    
    public boolean isSuccess()
    {
    	return success;
    }
    
    public DataStorageInterface[] getDataStorageClassList() 
    {
    	return dataStorageClassList;
    }
    
    /*
     * Method to get content from given source.
     */
    private boolean getContentFromSource(String resourceName)
    {
    	 //log.debug("download data from EcogridDataCacheItem URL : " + resourceName);
    	boolean successFlag = false;
         if (resourceName.startsWith("http://") ||
                 resourceName.startsWith("file://") ||resourceName.startsWith("ftp://") ) {
             // get the data from a URL
             try {
                 URL url = new URL(resourceName);
                 if (url != null) {
                     URLConnection conn = url.openConnection();
                     if (conn != null) {
                         InputStream filestream = url.openStream();
                         if (filestream != null) {
                             
                                 //String type = conn.getContentType();
                                 
                                 // Crate a new Cache Filename and write the resultsets directly to the cached file
                                 //File localFile = getFile();
                        	 byte [] c = new byte[1024];
                        	 int bread = filestream.read(c, 0, 1024);
                             while (bread != -1) 
                             {   //FileOutputStream osw = new FileOutputStream(localFile);
	                        	  if (dataStorageClassList != null)
	                        	  {
	                        		 for (int i = 0; i<dataStorageClassList.length; i++)
	                        		 {
	                        			 DataStorageInterface dataStorge = dataStorageClassList[i];
	                        			 if (dataStorge != null)
	                        			 {
	                        				 OutputStream osw = dataStorge.startSerialize(identifier);
				                             if (osw != null)
				                             {
				                                     
				                                    
				                                 osw.write(c, 0, bread);
				                                         
				                                     
				                                     osw.close();
				                                     
				                              }
	                        			 }
	                        		 }
	                        	  }
	                        	  bread = filestream.read(c, 0, 1024);
                            }
                            successFlag = true;
                            return successFlag;
                         }
                     }
                 }
                 //log.debug("EcogridDataCacheItem - error connecting to http/file ");
                 successFlag = false;
                 return successFlag;
             }
             catch (IOException ioe) {
            	 successFlag = false;
                 return successFlag;
             }
             // We will use ecogrid client to handle both ecogrid and srb protocol
         }
         else if (resourceName.startsWith("ecogrid://")) {
             // get the docid from url
             int start = resourceName.indexOf("/", 11) + 1;
             //log.debug("start: " + start);
             int end = resourceName.indexOf("/", start);
             if (end == -1) {
                 end = resourceName.length();
             }
             //log.debug("end: " + end);
             String identifier = resourceName.substring(start, end);
             // pass this docid and get data item
             return getDataItemFromEcoGrid(ECOGRIDENDPOINT, identifier);
         }
         else if (resourceName.startsWith("srb://")) {
             // get srb docid from the url
             String identifier = transformSRBurlToDocid(resourceName);
             // reset endpoint for srb (This is hack we need to figure ou
             // elegent way to do this
             //mEndPoint = Config.getValue("//ecogridService/srb/endPoint");
             // pass this docid and get data item
             //log.debug("before get srb data@@@@@@@@@@@@@@@@@@@@@@@@@@");
             return getDataItemFromEcoGrid(SRBENDPOINT, identifier);
         }
         else {
        	 successFlag = false;
             return successFlag;
         }
    }
    
    /*
     *  This method will get data from ecogrid server base on given
     *  docid. This method will handle the distribution url is ecogrid or
     *  srb protocol
     */
    /**
     *  Gets the dataItemFromEcoGrid attribute of the DataCacheObject object
     *
     *@param  endPoint
     *@param  identifier
     */
    private boolean getDataItemFromEcoGrid(String endPoint, String identifier)
    {
        
        // create a ecogrid client object and get the full record from the
        // client
    	boolean successFlag = false;
    	if (endPoint != null && identifier != null)
    	{
	        //log.debug("Get " + identifier + " from " + endPoint);
	        
	        try
	        {
	            //fatory
	            //log.debug("This is instance pattern");
	            
	            URL endPointURL = new URL(endPoint);
	            /*EcogridGetToStreamClient ecogridClient = new EcogridGetToStreamClient(endPointURL);
	            
	            log.debug("Get from EcoGrid: " + identifier);
	            
	            BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(getFile()) );
	            ecogridClient.get(identifier, bos);
	            bos.flush();
	            bos.close();*/
	            
	            successFlag = true;
                return successFlag;
	            
	        }
	        catch(Exception ee)
	        {
	            //log.debug("EcogridDataCacheItem - error connecting to Ecogrid ", ee);
	            successFlag = false;
	            return successFlag;
	        }
    	}
    	else
    	{
    		//System.out.println("in else path of get data from other source");
    		// this is not ecogrid source, we need download by other protocol
    		return getContentFromSource(identifier);
    		
    	}
    }
    
    /*
     * This method will transfer a srb url to srb docid in ecogrid ecogrid
     * srb id should look like: srb://seek:/home/beam.seek/IPCC_climate/Present/ccld6190.dat
     * and correspond docid looks like:
     * srb://testuser:TESTUSER@orion.sdsc.edu/home/beam.seek/IPCC_climate/Present/ccld6190.dat
     */
    private String transformSRBurlToDocid(String srbURL)
    {
        String docid = null;
        if (srbURL == null)
        {
            return docid;
        }
        String regex = "seek:";
        srbURL = srbURL.trim();
        //log.debug("The srb url is "+srbURL);
        // get user name , passwd and machine namefrom configure file
        String user = "";//Config.getValue("//ecogridService/srb/user");
        String passwd = "";//Config.getValue("//ecogridService/srb/passwd");
        String machineName = "";//Config.getValue("//ecogridService/srb/machineName");
        String replacement = user+":"+passwd+"@"+machineName;
        docid = srbURL.replaceFirst(regex, replacement);
        //log.debug("The srb id is " + docid);
        return docid;
    }
    
    /*
     * Method to get every outputstream for datastorage class.
     * If dataStorageClassList null will return.
     * If some dataStrogae object couldn't get an outputstream, null will associate
     * with this ojbect
     */
    private OutputStream[] getOutputStreamList()
    {
    	OutputStream[] list = null;
    	if (dataStorageClassList != null)
    	{
    		list = new OutputStream[dataStorageClassList.length];
  		 for (int i = 0; i<dataStorageClassList.length; i++)
  		 {
  			 DataStorageInterface dataStorge = dataStorageClassList[i];
  			 if (dataStorge != null)
  			 {
  				 OutputStream osw = dataStorge.startSerialize(identifier);
                 list[i] = osw;
  			 }
  			 else
  			 {
  				 list[i] = null;
  			 }
  		 }
  	  }
     
      return list;
    }
}
