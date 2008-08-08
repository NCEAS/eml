/**
 *    '$RCSfile: DocumentHandler.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2008-08-08 21:40:51 $'
 *   '$Revision: 1.2 $'
 *
 *  For Details: http://ecoinformatics.org
 *
 * Copyright (c) 2008 The Regents of the University of California.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecoinformatics.ecogrid.authenticatedqueryservice.AuthenticatedQueryServiceGetToStreamClient;
import org.ecoinformatics.ecogrid.queryservice.QueryServiceGetToStreamClient;

public class DocumentHandler {

	public static Log log = LogFactory.getLog(DocumentHandler.class);
	
	private String docId = null;
	private EcogridEndPointInterface ecogridEndPointInterface = null;
	
	private PipedInputStream inputStream = null;
	private PipedOutputStream outputStream = null;
	
	public DocumentHandler() {
		//initialize the streams for reading the document from server
		outputStream = new PipedOutputStream();
	    inputStream = new PipedInputStream();
	    
	    try {
			outputStream.connect(inputStream);
		} catch (IOException e1) {
			log.error("could not connect piped streams! " + e1.getMessage());
			e1.printStackTrace();
		}
	    
	}

	public InputStream downloadDocument() throws Exception {
		
		log.info("starting the download");
		
		boolean success = true;
		
		final String id = docId;
		final EcogridEndPointInterface endpoint = ecogridEndPointInterface;
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(
			new Runnable() {
				public void run() {
					//get from the ecogrid
					try {
						if (ecogridEndPointInterface instanceof AuthenticatedEcogridEndPointInterface) {
							AuthenticatedQueryServiceGetToStreamClient authenticatedEcogridClient = 
								new AuthenticatedQueryServiceGetToStreamClient(
				            		new URL(
				            				((AuthenticatedEcogridEndPointInterface)endpoint).getMetacatAuthenticatedEcogridEndPoint()));
							authenticatedEcogridClient.get(
									id,
									((AuthenticatedEcogridEndPointInterface)endpoint).getSessionId(),
									outputStream);
						}
						else {
							QueryServiceGetToStreamClient ecogridClient = 
								new QueryServiceGetToStreamClient(
				            		new URL(
				            				endpoint.getMetacatEcogridEndPoint()));
							ecogridClient.get(id, outputStream);
						}
						outputStream.close();
						log.debug("Done downloading id=" + id);
						
					} catch (Exception e) {
						log.error("Error getting document from ecogrid: " + e.getMessage());
						e.printStackTrace();
					}
					
				}
			});
		
		//wait for the download to complete
		service.shutdown();
		service.awaitTermination(0, TimeUnit.SECONDS);
					
		return inputStream;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public EcogridEndPointInterface getEcogridEndPointInterface() {
		return ecogridEndPointInterface;
	}

	public void setEcogridEndPointInterface(
			EcogridEndPointInterface ecogridEndPointInterface) {
		this.ecogridEndPointInterface = ecogridEndPointInterface;
	}

}
