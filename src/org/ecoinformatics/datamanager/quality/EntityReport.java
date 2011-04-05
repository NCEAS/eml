package org.ecoinformatics.datamanager.quality;

import java.util.ArrayList;

/**
 * Represents an quality report for an entity.
 * (Not clear that this class is really necessary. May be subsumed
 * by code in the Data Manager Library. Because of this, I am
 * leaving the methods undocumented at this point.)
 * 
 * @author dcosta
 *
 */
public class EntityReport {

  /*
   * Class variables
   */
  
  
  /*
   * Instance variables
   */
  
  private String entityName;
  private String entityId;
  private ArrayList<QualityCheck> qualityChecks;
  
  /*
   * Constructors
   */
  
  public EntityReport(String entityName) {
    this.entityName = entityName;
    this.qualityChecks = new ArrayList<QualityCheck>();
  }

  
  /*
   * Class methods
   */
  
  
  /*
   * Instance methods
   */

  public String getEntityName() {
    return entityName;
  }

  
  public String getEntityId() {
    return entityId;
  }

  
  public ArrayList<QualityCheck> getQualityChecks() {
    return qualityChecks;
  }

  
  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  
  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  
  public void setQualityChecks(ArrayList<QualityCheck> qualityChecks) {
    this.qualityChecks = qualityChecks;
  }

}
