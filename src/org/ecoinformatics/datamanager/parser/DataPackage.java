/**
 *    '$RCSfile: DataPackage.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2008-08-12 23:28:18 $'
 *   '$Revision: 1.6 $'
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

import org.ecoinformatics.datamanager.quality.EntityReport;
import org.ecoinformatics.datamanager.quality.QualityReport;
import org.ecoinformatics.datamanager.quality.QualityCheck;

/**
 * This class reprents a metadata package information to describe entity
 * 
 * @author tao
 */
public class DataPackage 
{
  
  /*
   * Class fields
   */
  
  
  /*
   * Instance fields
   */
  
  private String accessXML = null;       // <access> element XML string
  private Entity[] entityList = null;
  private String   packageId  = null;
  private QualityReport qualityReport = null;
  
  
  /*
   * Constructors
   */
  
  /**
   * Constructs a DataPackage object.
   * 
   * @param packageId  Identifier of this DataPackage
   */
  public DataPackage(String packageId)
  {
	  this.packageId = packageId;  
    this.qualityReport = new QualityReport(this);
  }
  
  
  /*
   * Class methods
   */
  
  
  /*
   * Instance methods
   */
  
  /**
   * Adds a dataset-level quality check to the data packages's associated 
   * qualityReport object.
   * 
   * @param qualityCheck    the new quality check to add
   */
  public void addDatasetQualityCheck(QualityCheck qualityCheck) {
    if (qualityReport != null) {
      qualityReport.addDatasetQualityCheck(qualityCheck);
    }
  }
  
  
  /**
   * Getter method for the accessXML field
   * 
   * @return  the value of the accessXML field
   */
  public String getAccessXML() {
    return accessXML;
  }
  
  
  public Entity[] getEntities(String name)
  {
	  Vector list = new Vector();
	  if (entityList != null) {
		  for (int i=0; i < entityList.length; i++) {
			  if (entityList[i].getName().equals(name)) {
				  list.add(entityList[i]);
			  }
		  }
	  }
	  return (Entity[]) list.toArray(new Entity[0]);
  }
  
  
  public Entity getEntity(String name) {
	  if (getEntities(name).length > 0) {
		  return getEntities(name)[0];
	  }
	  return null;
  }
  
  
  /**
   * Adds an entity into the DataPackage
   * 
   * @param entity The entity which will be added
   */
  public void add(Entity entity)
  {
    addEntityIntoArray(entity);
  }
  
  
  /**
   * Gets the entity array which is in this DataPackage.
   * 
   * @return Entity array in the DataPackage
   */
  public Entity[] getEntityList()
  {
    return entityList;
  }
  
  
  /**
   * Gets the number of entities in this DataPackage.
   * 
   * @return  an int representing the number of entities
   */
  public int getEntityNumber()
  {
    if (entityList == null) {
      return 0;
    } 
    else {
      return entityList.length;
    }
  }
  
  
  /**
   * Gets the qualityReport object associated with this data package.
   * 
   * @return  the qualityReport instance variable
   */
  public QualityReport getQualityReport()
  {
    return qualityReport;
  }
  
  
  /**
   * Gets the package identifier for this DataPackage.
   * 
   * @return a string representing the DataPackage identifier
   */
  public String getPackageId()
  {
    return packageId;
  }
  
  
  /*
   * Add an entity into the entityList array.
   * 
   * @param   entity   the entity object to be added to the array
   */
  private void addEntityIntoArray(Entity entity) {
    if (entityList == null) {
      entityList = new Entity[1];
      entityList[0] = entity;
    } 
    else {
      int size = entityList.length;
      Entity[] tmp = new Entity[size + 1];
      
      for (int i = 0; i < size; i++) {
        tmp[i] = entityList[i];
      }
      
      tmp[size] = entity;
      entityList = tmp;
    }
  }
  
  
  /***
   * Removes ALL previously added Entity objects from the DataPackage
   */
  public void clearEntityList() {
	  entityList = null;
  }
  
  
  /**
   * Setter method for accessXML field.
   * 
   * @param xmlString  the XML string to assign to the
   *                   accessXML field. Should be a block
   *                   of <access> XML.
   */
  public void setAccessXML(String xmlString) {
    this.accessXML = xmlString;
  }
  
}
