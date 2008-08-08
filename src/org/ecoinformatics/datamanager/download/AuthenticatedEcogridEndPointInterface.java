/**
 *    '$RCSfile: AuthenticatedEcogridEndPointInterface.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2008-08-08 21:40:51 $'
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

/**
 * This interface provides a API for Data Manager to get Authenticated
 * Ecogrid endpoint information. It is meant to extend the existing
 * EcogridEndPointInterface and also provide a marker for 
 * the library when using protected data
 * 
 * @author leinfelder
 *
 */
public interface AuthenticatedEcogridEndPointInterface extends EcogridEndPointInterface
{
	
	public String getMetacatAuthenticatedEcogridEndPoint();
	
   /**
    * 
    * @return sessionId for operations that require authentication with ecogrid
    */
   public String getSessionId();
   
}
