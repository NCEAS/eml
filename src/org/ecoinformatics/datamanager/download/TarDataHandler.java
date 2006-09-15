/**
 *    '$RCSfile: TarDataHandler.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-09-15 23:25:23 $'
 *   '$Revision: 1.3 $'
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

import com.ice.tar.TarEntry;
import com.ice.tar.TarInputStream;


public class TarDataHandler extends ArchivedDataHandler
{
	/**
	 * Constructor
	 * @param url
     */
    public TarDataHandler(String url)
    {
    	super(url);
    }
    
    public boolean unarchive()
    {
    	boolean success = false;
    	return success;
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
 	   TarInputStream tarInputStream = null;
 	   if (in == null)
 	   {
 		   return success;
 	   }
 	   try
 	   {
 		   tarInputStream = new TarInputStream(in);
 		   TarEntry entry = tarInputStream.getNextEntry();
 		   int index = 0;
 		   //System.out.println("in zip method!!!!!!!!!!!!!!!!!!11");
 		   while (entry != null && index <1)
 		   {
 			  //System.out.println("the zip entry name is "+entry.getName());
 			  if (entry.isDirectory())
 			  {
 				  entry = tarInputStream.getNextEntry();
 				  continue;
 			  }
 			  // this method will close the zipInpustream, and zipInpustream is not null!!!
 		      success = super.writeRemoteInputStreamIntoDataStorage(tarInputStream);
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
    protected boolean getDataItemFromEcoGrid(String endPoint, String ecogridIdentifier)
    {
 	   boolean success = false;
        File zipTmp = writeEcoGridArchivedDataIntoTmp(endPoint, ecogridIdentifier, ".tar");
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
