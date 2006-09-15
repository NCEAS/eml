/**
 *    '$RCSfile: DownloadHandler.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-09-15 23:25:23 $'
 *   '$Revision: 1.8 $'
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
import java.util.zip.ZipInputStream;

import org.ecoinformatics.ecogrid.queryservice.EcogridGetToStreamClient;

/**
 * This class will read a input stream from remote entity for given URL and
 * write data into given local storage systems. This is the main class of download
 * component. The class implements Runnable interface, so the download process
 * will be ran in another thread.
 * @author tao
 *
 */

public class DownloadHandler implements Runnable 
{
	/*
	 * Instance fields
	 */
	//private String identifier = null;
	private String url        = null;
	private DataStorageInterface[] dataStorageClassList = null;
	private String[] errorMessages = null;
	protected boolean completed = false;
	protected boolean success = false;
	protected boolean busy = false;
	
	/*
	 * Constants
	 */
	private static final String ECOGRIDENDPOINT = "http://pacific.msi.ucsb.edu:8080/knb/services/EcoGridQuery";
	private static final String SRBENDPOINT     = "http://srbbrick8.sdsc.edu:8080/SRBImpl/services/SRBQueryService";
	private static final String SRBMACHINE      = "srb-mcat.sdsc.edu";
	private static final String SRBUSERNAME     = "testuser.sdsc";
	private static final String SRBPASSWD       = "TESTUSER";
	
	/**
	 * Default constructor of this class
	 */
	public DownloadHandler()
	{
		
	}
	
	/**
	 * Constructor of this class
	 * @param url
	 */
	public DownloadHandler(String url)
	{
		this.url = url;
		//this.identifier = identifier;
		//this.dataStorageClassList = dataStorageClassList;
	}
	
	/**
	 * This method will download data for the given url
	 */
    public void run()
    {
    	busy = true;
    	completed = false;
    	success = getContentFromSource(url);
    	busy = false;
    	completed = true;
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
    
    public void setDataStorageCladdList(DataStorageInterface[] dataStorageClassList)
    {
    	this.dataStorageClassList = dataStorageClassList;
    }
    
    /*
     * Method to get content from given source.
     */
    protected boolean getContentFromSource(String resourceName)
    {
    	 //log.debug("download data from EcogridDataCacheItem URL : " + resourceName);
    	//System.out.println("in getContent method!!!!!!!!!!!!!!! " +resourceName);
    	boolean successFlag = false;
        if (resourceName != null && (resourceName.startsWith("http://") ||
                 resourceName.startsWith("file://") ||resourceName.startsWith("ftp://") )) {
             // get the data from a URL
        	//System.out.println("after if !!!!!!!!!!!!!!!!!!11");
             try {
            	 //System.out.println("start try !!!!!!!!!!!!!!!!!!11");
                 URL url = new URL(resourceName);
                 //System.out.println("afterURL !!!!!!!!!!!!!!!!!!11");
                 if (url != null) {
                	 
                         InputStream filestream = url.openStream();
                         //System.out.println("after get inpustream !!!!!!!!!!!!!!!!!!11");
                         return this.writeRemoteInputStreamIntoDataStorage(filestream);
                    // }
                       
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
         else if (resourceName != null && resourceName.startsWith("ecogrid://")) {
             // get the docid from url
             int start = resourceName.indexOf("/", 11) + 1;
             //log.debug("start: " + start);
             int end = resourceName.indexOf("/", start);
             if (end == -1) {
                 end = resourceName.length();
             }
             //log.debug("end: " + end);
             String ecogridIdentifier = resourceName.substring(start, end);
             // pass this docid and get data item
             System.out.println("the endpoint is "+ECOGRIDENDPOINT);
             System.out.println("The identifier is "+ecogridIdentifier);
             //return false;
             return getDataItemFromEcoGrid(ECOGRIDENDPOINT, ecogridIdentifier);
         }
         else if (resourceName != null && resourceName.startsWith("srb://")) {
             // get srb docid from the url
             String srbIdentifier = transformSRBurlToDocid(resourceName);
             // reset endpoint for srb (This is hack we need to figure ou
             // elegent way to do this
             //mEndPoint = Config.getValue("//ecogridService/srb/endPoint");
             // pass this docid and get data item
             //log.debug("before get srb data@@@@@@@@@@@@@@@@@@@@@@@@@@");
             return getDataItemFromEcoGrid(SRBENDPOINT, srbIdentifier);
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
     *
     *@param  endPoint
     *@param  identifier
     */
    protected boolean getDataItemFromEcoGrid(String endPoint, String identifier)
    {
        
        // create a ecogrid client object and get the full record from the
        // client
    	//System.out.println("=================the endpoint is "+endPoint);
    	//System.out.println("=================the identifier is "+identifier);
    	boolean successFlag = false;
    	if (endPoint != null && identifier != null)
    	{
	        //log.debug("Get " + identifier + " from " + endPoint);
	        
	        try
	        {
	            //fatory
	            //log.debug("This is instance pattern");
	            
	            URL endPointURL = new URL(endPoint);
	            EcogridGetToStreamClient ecogridClient = new EcogridGetToStreamClient(endPointURL);
	            
	            //log.debug("Get from EcoGrid: " + identifier);
	            NeededOutputStream [] outputStreamList = getOutputStreamList();
	            if (outputStreamList != null)
	            {
	            	for (int i=0; i<outputStreamList.length; i++)
	            	{
	            		boolean oneLoopSuccess = true;
	            		NeededOutputStream stream = outputStreamList[i];
	            		if (stream != null && stream.getNeeded())
	            		{
		                    BufferedOutputStream bos = new BufferedOutputStream(stream.getOutputStream());
		                    ecogridClient.get(identifier, bos);
		                    bos.flush();
		                    bos.close();
		                    if (oneLoopSuccess)
		                    {
		                    	successFlag = true;
		                    }
	            		}
	            		else if (stream != null)
	            		{
	            			if (oneLoopSuccess)
		                    {
		                    	successFlag = true;
		                    }
	            		}
	            		else
	            		{
	            			oneLoopSuccess = false;
	            		}
	            	}
	            }
	            else
	            {
	            	successFlag = false;
	            }
	            
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
    		//return getContentFromSource(identifier);
    		return false;
    		
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
        String user = SRBUSERNAME;//Config.getValue("//ecogridService/srb/user");
        String passwd = SRBPASSWD;//Config.getValue("//ecogridService/srb/passwd");
        String machineName = SRBMACHINE;//Config.getValue("//ecogridService/srb/machineName");
        String replacement = user+":"+passwd+"@"+machineName;
        docid = srbURL.replaceFirst(regex, replacement);
        //log.debug("The srb id is " + docid);
        return docid;
    }
    
    /*
     * Method to get every outputstream for datastorage class.
     * If dataStorageClassList is null, null will return.
     * If some dataStrogae object couldn't get an outputstream or
     * identifier is already in the datastorge object, null will associate
     * with this ojbect
     * 
     */
    private NeededOutputStream[] getOutputStreamList()
    {
    	NeededOutputStream[] list = null;
    	if (dataStorageClassList != null)
    	{ 
    		 list = new NeededOutputStream[dataStorageClassList.length];
	  		 for (int i = 0; i<dataStorageClassList.length; i++)
	  		 {
	  			 DataStorageInterface dataStorge = dataStorageClassList[i];
	  			 if (dataStorge != null && !dataStorge.doesDataExist(url))
	  			 {
	  				 OutputStream osw = dataStorge.startSerialize(url);
	  				 NeededOutputStream stream = new NeededOutputStream(osw, true);
	                 list[i] = stream;
	  			 }
	  			 else if(dataStorge != null)
	  			 {
	  				OutputStream osw = dataStorge.startSerialize(url);
	  				 NeededOutputStream stream = new NeededOutputStream(osw, false);
	                 list[i] = stream;
	  			 }
	  			 else
	  			 {
	  				 list[i] = null;
	  			 }
	  		 }
  	  }
     
      return list;
    }
    
    /*
     * Method to close a OutputStream array
     */
    private void closeOutputStream(NeededOutputStream[] outputStreamList) throws IOException
    {
    	if (outputStreamList != null)
    	{	            //String type = conn.getContentType();
            
            // Crate a new Cache Filename and write the resultsets directly to the cached file
            //File localFile = getFile();
    		for (int i = 0; i<outputStreamList.length; i++)
   		 {
   			 NeededOutputStream neededOutput = outputStreamList[i];
   			 OutputStream output = neededOutput.getOutputStream();
   			 if (output != null)
   			 {
                    output.close();
   			 }
   		 }
    	}
    }
    
    /**
     * Get Error messages
     * @return
     */
    public String[] getErorrMessages()
    {
    	return errorMessages;
    }
    
    /**
     * If data storage insterface already download this identifier,
     * this OuptStream will be marked as NonNeeded. So download would NOT
     * happen again
     * @author tao
     *
     */
    private class NeededOutputStream
    {
    	private OutputStream stream = null;
    	private boolean needed      = true;
    	
    	/**
    	 * Constructor
    	 * @param stream
    	 * @param needed
    	 */
    	public NeededOutputStream(OutputStream stream, boolean needed)
    	{
    		this.stream = stream;
    		this.needed = needed;
    	}
    	
    	public OutputStream getOutputStream()
    	{
    		return stream;
    	}
    	
    	public boolean getNeeded()
    	{
    		return needed;
    	}
    }
    
    /**
     * Method to get url
     */
    public String getUrl()
    {
    	return url;
    }
    
    /*
     * This method will read from remote inputsream and write it
     * to StorageSystem. It only handle http or ftp protocal. It couldn't
     * handle ecogrid protocol
     */
    protected boolean writeRemoteInputStreamIntoDataStorage(InputStream filestream) throws IOException
    {
    	boolean successFlag = false;
    	//System.out.println("in download method!!!!!!!!!!!!!!!!!!11");
    	if (filestream != null) 
    	{
	            
	     	 NeededOutputStream [] outputStreamList = getOutputStreamList();
		   	 byte [] c = new byte[1024];
		   	 int bread = filestream.read(c, 0, 1024);
		   	 boolean oneLoopSuccess = true;
		        while (bread != -1) 
		        {   //FileOutputStream osw = new FileOutputStream(localFile);
		       	  if (outputStreamList != null)
		       	  {
		       		 for (int i = 0; i<outputStreamList.length; i++)
		       		 {
		       			 NeededOutputStream neededOutput = outputStreamList[i];
		       			 if (neededOutput != null)
		       			 {
		       				 OutputStream output = neededOutput.getOutputStream();
		       				 boolean need = neededOutput.getNeeded();
		           			 if (output != null && need)
		           			 {
		           				 
		                            output.write(c, 0, bread);
		                            if(oneLoopSuccess)
		                            {
		                           	 successFlag = true;
		                            }
		           			 }
		           			 else if (output != null)
		           			 {
		           				 if(oneLoopSuccess)
		                            {
		                           	 successFlag = true;
		                            }
		           			 }
		           			 else
		           			 {
		           				 oneLoopSuccess = false;
		           			 }
		       			 }
		       			 else
		       			 {
		       				 oneLoopSuccess = false;
		       			 }
		       		 }
		       	  }
		       	  else
		       	  {
		       		  successFlag = false;
		       	  }
		       	  bread = filestream.read(c, 0, 1024);
		       }
		       filestream.close();
		       closeOutputStream(outputStreamList);
		       //successFlag = true;
		       System.out.println("the success is "+successFlag);
		       return successFlag;
	    }
	    else
	    {
	    		return successFlag;
	    }
    }
}  
