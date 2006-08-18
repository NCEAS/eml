/**
 *    '$RCSfile: DataPackage.java,v $'
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

/**
 * This class reprents a metadata package information to describe entity
 * @author Jing Tao
 */
public class DataPackage 
{
  private Entity[] entityList = null;
  
  /**
   * Constructor
   *
   */
  public DataPackage()
  {
	  
  }
  
  /**
   * Add a entity to the object
   * @param entity
   */
  public void add(Entity entity)
  {
	  addEntityIntoArray(entity);
  }
  
  /**
   * Get the entity array which is in this obj.
   * @return
   */
  public Entity[] getEntityList()
  {
	  return entityList;
  }
  
  /*
   * Add a entity into a array
   * 
   */
   private void addEntityIntoArray(Entity entity)
   {
	   if (entityList == null)
	   {
		  entityList = new Entity[1];
		  entityList[0] = entity;
	   }
	   else
	   {
		  int size = entityList.length;
		  Entity[] tmp = new Entity[size+1];
		  for (int i=0; i<size; i++)
		  {
			  tmp[i] = entityList[i];
		  }
		  tmp[size] = entity;
		  entityList = tmp;
	   }
	   
   }
}
