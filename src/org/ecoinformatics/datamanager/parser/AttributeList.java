/**
 *    '$RCSfile: AttributeList.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-08-18 01:41:10 $'
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
package org.ecoinformatics.datamanager.parser;

import java.util.Vector;

/**
 * @author tao
 *
 * This class reprents of list of attributes in the entity object
 */
public class AttributeList
{
   private Vector attributes   = new Vector();
   private String id           = null;
   private boolean isReference = false;
   private String referenceId  = null;
   private Entity parentTable  = null;
   
   /**
    * Constructor
    *
    */
   public AttributeList()
   {
       attributes = new Vector();
   }
    /**
     * @return Returns the attributes.
     */
    public Vector getAttributes()
    {
        return attributes;
    }
    /**
     * @param attributes The attributes to set.
     */
    public void setAttributes(Vector attributes)
    {
        this.attributes = attributes;
    }
    /**
     * @return Returns the id.
     */
    public String getId()
    {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(String id)
    {
        this.id = id;
    }
    /**
     * @return Returns the isReference.
     */
    public boolean isReference()
    {
        return isReference;
    }
    /**
     * @param isReference The isReference to set.
     */
    public void setReference(boolean isReference)
    {
        this.isReference = isReference;
    }
    /**
     * @return Returns the referenceId.
     */
    public String getReferenceId()
    {
        return referenceId;
    }
    /**
     * @param referenceId The referenceId to set.
     */
    public void setReferenceId(String referenceId)
    {
        this.referenceId = referenceId;
    }
    
    /**
     * set partent entity
     * @param p
     */
    public void setParent(Entity p)
    {
      parentTable = p;
    }
    
    /**
     * get parent entity
     * @return
     */
    public Entity getParent()
    {
      return parentTable;
    }
    
    /**
     * Add an Attribute to this attribute list.
     */
    public void add(Attribute a)
    {
     
      attributes.addElement(a);
      
      
    }
}
