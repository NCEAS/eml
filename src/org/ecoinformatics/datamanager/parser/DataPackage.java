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

import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Pattern;

import org.ecoinformatics.datamanager.quality.EntityReport;
import org.ecoinformatics.datamanager.quality.QualityReport;
import org.ecoinformatics.datamanager.quality.QualityCheck;
import org.ecoinformatics.datamanager.quality.QualityCheck.Status;

/**
 * This class represents a metadata package information to describe entity
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
  private String emlNamespace = null;    // e.g. "eml://ecoinformatics.org/eml-2.1.0"
  private Entity[] entityList = null;
  private String   packageId  = null;
  private QualityReport qualityReport = null;
  
  private final String LTER_PACKAGE_ID_PATTERN = "^knb-lter-[a-z][a-z][a-z]\\.\\d+\\.\\d+$";
  
  
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
    
    qualityCheckPackageId(packageId);
  }
  
  
  /*
   * Boolean to determine whether a given packageId conforms to
   * the string pattern of a particular organization such as LTER.
   * If the specified system value does not have a regular
   * expression pattern declared for its packageId, then the
   * packageId is assumed to be valid by default.
   */
  private boolean isValidPackageId(String system, String packageId) {
    boolean isValid = true;
    String regexPattern = null;
    
    /*
     * If we have a regular expression pattern string declared for a 
     * particular organization, use it to validate the packageId
     */
    if (system.equalsIgnoreCase("lter")) {
      regexPattern = LTER_PACKAGE_ID_PATTERN;
    }
    
    if (regexPattern != null) {
      isValid = Pattern.matches(regexPattern, packageId);
    }

    return isValid;
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
  
  
  /**
   * Getter method for the emlNamespace field
   * @return
   */
  public String getEmlNamespace() {
    return emlNamespace;
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
   * Determine whether the data package contains
   * two entities with the same entityName value.
   * 
   * @return  The duplicate name, or null if no
   *          duplicate entity names were found
   */
  public String findDuplicateEntityName() {
    String duplicateName = null;
    
    Entity[] entityArray = getEntityList();
    if (entityArray != null) {
      int len = entityArray.length;
      TreeSet<String> treeSet = new TreeSet<String>();
      for (int i = 0; i < len; i++) {
        Entity entity = entityArray[i];
        String entityName = entity.getName();
        if (entityName != null) {
          if (treeSet.contains(entityName)) {
            duplicateName = entityName;
            break;
          }
          else {
            treeSet.add(entityName);
          }
        }
      }
    }
    
    return duplicateName;
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
  
  
  /**
   * Removes ALL previously added Entity objects from the DataPackage
   */
  public void clearEntityList() {
	  entityList = null;
  }
  
  
  /**
   * Boolean to determine whether this data package has at
   * least one dataset-level quality error. This can be called
   * after parsing the metadata. If a data package is found to
   * have a dataset-level quality error, an application may want
   * to halt processing at that point instead of drilling down
   * to the entity-level.
   * 
   * @return  true if one or more dataset-level quality errors are 
   *          found, else false
   */
  public boolean hasDatasetQualityError() {
    boolean hasError = (qualityReport != null &&
                        qualityReport.hasDatasetQualityError());
    
    return hasError;
  }


  /**
   * Boolean to determine whether this data package has at
   * least one entity-level quality error. 
   * 
   * @return  true if one or more entity-level quality errors are 
   *          found, else false
   */
  public boolean hasEntityQualityError() {
    boolean hasError = false;
    Entity[] entityArray = getEntityList();
    for (int i = 0; i < entityArray.length; i++) {
      Entity entity = entityArray[i];
      EntityReport entityReport = entity.getEntityReport();
      if (entityReport != null) {
        if (entityReport.hasEntityQualityError()) {
          hasError = true;
        }
      }
    }
    
    return hasError;
  }


  /**
   * Boolean to determine whether this data package has at
   * least one dataset-level or entity-level quality error. 
   * 
   * @return  true if one or more quality errors are 
   *          found, else false
   */
  public boolean hasQualityError() {
    return hasDatasetQualityError() || hasEntityQualityError();
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
  
  
  /*
   * Executes the "EML packageId check" when applicable
   */
  private void qualityCheckPackageId(String packageId) { 
    // Check the value of the 'packageId' attribute
    String qualityCheckName = "EML packageId check";
    QualityCheck qualityCheckTemplate = QualityReport.getQualityCheckTemplate(qualityCheckName);
    QualityCheck packageIdQualityCheck = new QualityCheck(qualityCheckName, qualityCheckTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, packageIdQualityCheck)) {
      // Initialize the emlNamespaceQualityCheck
      boolean isValidPackageId = false;
      String systemAttribute = packageIdQualityCheck.getSystem();

      if (packageId != null) {
        packageIdQualityCheck.setFound(packageId);
        if (isValidPackageId(systemAttribute, packageId)) {
          isValidPackageId = true;
        }
      }
      
      if (isValidPackageId) {
        packageIdQualityCheck.setStatus(Status.valid);
        packageIdQualityCheck.setSuggestion("");
      }
      else {
        packageIdQualityCheck.setFailedStatus();
      }
      this.addDatasetQualityCheck(packageIdQualityCheck);
    }
  }
  
  
  /**
   * Setter method for emlNamespace field.
   * 
   * @param emlNamespace  the emlNamespace value to set,
   *                      e.g. "eml://ecoinformatics.org/eml-2.1.0"
   */
  public void setEmlNamespace(String emlNamespace) {
    this.emlNamespace = emlNamespace;
    
    // Check the value of the 'xmlns:eml' attribute
    String qualityCheckName = "EML version 2.1.0 or beyond";
    QualityCheck qualityCheckTemplate = QualityReport.getQualityCheckTemplate(qualityCheckName);
    QualityCheck eml210QualityCheck = new QualityCheck(qualityCheckName, qualityCheckTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, eml210QualityCheck)) {
      // Initialize the emlNamespaceQualityCheck
      boolean isValidNamespace = false;

      if (emlNamespace != null) {
        eml210QualityCheck.setFound(emlNamespace);
        if (emlNamespace.equals("eml://ecoinformatics.org/eml-2.1.0") ||
            emlNamespace.equals("eml://ecoinformatics.org/eml-2.1.1")
           ) {
          isValidNamespace = true;
        }
      }
      
      if (isValidNamespace) {
        eml210QualityCheck.setStatus(Status.valid);
        eml210QualityCheck.setSuggestion("");
      }
      else {
        eml210QualityCheck.setFailedStatus();
      }
      this.addDatasetQualityCheck(eml210QualityCheck);
    }
  }
  
}
