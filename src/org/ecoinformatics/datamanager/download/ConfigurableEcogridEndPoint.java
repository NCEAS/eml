/**
 *    '$RCSfile: ConfigurableEcogridEndPoint.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2008-08-08 21:40:51 $'
 *   '$Revision: 1.2 $'
 *
 *  For Details: http://ecoinformatics.org
 *
 * Copyright (c) 2007 The Regents of the University of California.
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

import java.util.ResourceBundle;

/**
 * This class implements EcogridEndPointInterface and is configurable using a properties file.
 * Should be useful for switching to remote endpoints etc.
 * 
 * @author leinfelder
 *
 */
public class ConfigurableEcogridEndPoint implements AuthenticatedEcogridEndPointInterface 
{  
		
	private String metacatEcogridEndPoint = null;
	private String metacatAuthenticatedEcogridEndPoint = null;
	private String metacatEcogridAuthEndPoint = null;
	private String metacatEcogridPutEndPoint = null;
	private String metacatEcogridIdentifierEndPoint = null;
	private String srbEcogridEndPoint = null;
	private String srbMachineName = null;
	private String sessionId = null;
	
	public ConfigurableEcogridEndPoint() {
		
		ResourceBundle props = ResourceBundle.getBundle("endpoint");
		metacatEcogridEndPoint 				= props.getString("metacatEcogridEndPoint");
		metacatAuthenticatedEcogridEndPoint = props.getString("metacatAuthenticatedEcogridEndPoint");
		metacatEcogridAuthEndPoint 			= props.getString("metacatEcogridAuthEndPoint");
		metacatEcogridPutEndPoint 			= props.getString("metacatEcogridPutEndPoint");
		metacatEcogridIdentifierEndPoint 	= props.getString("metacatEcogridIdentifierEndPoint");
		srbEcogridEndPoint 					= props.getString("srbEcogridEndPoint");
		srbMachineName 						= props.getString("srbMachineName");
	}
	
	 /**
	    * Gets the end point which Metacat implements ecogrid interface.
	    * This end point will be used to handle ecogrid protocol
        * 
	    * @return end point url string
	    */
	   public String getMetacatEcogridEndPoint()
	   {
		   return metacatEcogridEndPoint;
	   }
	   
	   public String getMetacatAuthenticatedEcogridEndPoint()
	   {
		   return metacatAuthenticatedEcogridEndPoint;
	   }
	   
	   public String getMetacatEcogridAuthEndPoint()
	   {
		   return metacatEcogridAuthEndPoint;
	   }
	   
	   public String getMetacatEcogridPutEndPoint()
	   {
		   return metacatEcogridPutEndPoint;
	   }
	   
	   public String getMetacatEcogridIdentifierEndPoint() 
	   {
			return metacatEcogridIdentifierEndPoint;
		}
	   
       
	   /**
	    * Gets the end point which SRB implements ecogrid interface.
	    * This end point will be used to handle srb protocol.
        * 
	    * @return end point url string
	    */
	   public String getSRBEcogridEndPoint()
	   {
		   return srbEcogridEndPoint;
	   }

       
	   /**
	    * Gets the machine name which srb protocol will be used.
	    * The default value for this class is "srb-mcat.sdsc.edu".
        * 
	    * @return the machine name of srb server
	    */
	   public String getSRBMachineName()
	   {
		   return srbMachineName;
	   }
	   
	   public String getSessionId() {
		   return sessionId;
	   }
	   
	   public void setSessionId(String id) {
		   sessionId = id;
	   }
       
}

