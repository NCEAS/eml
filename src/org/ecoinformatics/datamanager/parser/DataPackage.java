/**
 *    '$RCSfile: DataPackage.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-09-22 00:42:28 $'
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
package org.ecoinformatics.datamanager.parser;

/**
 * This class reprents a metadata package information to describe entity
 * @author Jing Tao
 */
public class DataPackage 
{
  private Entity[] entityList = null;
  private String   packageId  = null;
  
  /**
   * Constructor
   * @param packageId  Identifier of this DataPackage
   */
  public DataPackage(String packageId)
  {
	this.packageId = packageId;  
  }
  
  /**
   * Adds a entity into DataPackage
   * @param entity The entity which will be added
   */
  public void add(Entity entity)
  {
	  addEntityIntoArray(entity);
  }
  
  /**
   * Gets the entity array which is in DataPackage
   * @return Entity array in the DataPackage
   */
  public Entity[] getEntityList()
  {
	  return entityList;
  }
  
  /**
   * Gets the number of entity in DataPackage
   * @return number of entity
   */
  public int getEntityNumber()
  {
	  if (entityList == null)
	  {
		  return 0;
	  }
	  else
	  {
		  return entityList.length;
	  }
  }
  
  /**
   * Gets package identifier for this DataPackage
   * @return DataPackage identifier
   */
  public String getPackageId()
  {
	  return packageId;
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
