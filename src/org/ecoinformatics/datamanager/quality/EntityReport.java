package org.ecoinformatics.datamanager.quality;

import java.util.ArrayList;

import org.ecoinformatics.datamanager.parser.Entity;

/**
 * Represents an quality report for an entity.
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
  
  // The Entity objects that this EntityReport is reporting on
  private Entity entity;
  
  // List of quality checks that have been performed on this entity
  private ArrayList<QualityCheck> qualityChecks = new ArrayList<QualityCheck>();

  /*
   * Constructors
   */
  
  public EntityReport(Entity entity) {
    this.entity = entity;
  }

  
  /*
   * Class methods
   */
  
  
  /*
   * Instance methods
   */

  
  /**
   * Adds a quality check to the list of quality checks that have been
   * performed on this entity.
   * 
   * @param qualityCheck    the new quality check to add to the list
   */
  public void addQualityCheck(QualityCheck qualityCheck) {
    String name = qualityCheck.getName();
    
    /*
     * Filter out duplicate cases of the same quality check.
     * (We need a better approach to specifying these cases.)
     */
    if (name.equalsIgnoreCase("Online URLs are live")) {
      if (hasQualityCheck(qualityCheck)) {
        return;
      }
    }

    qualityChecks.add(qualityCheck);
  }
  
  
  /**
   * Retrieves the list of quality checks that have been performed on this
   * entity.
   * 
   * @return    an ArrayList of QualityCheck objects
   */
  public ArrayList<QualityCheck> getQualityChecks() {
    return qualityChecks;
  }
  
  
  /**
   * Boolean to determine whether this entity report has at
   * least one entity-level quality error. 
   * 
   * @return  true if one or more entity quality errors are found,
   *          else false
   */
  public boolean hasEntityQualityError() {
    boolean hasError = false;
    
    for (QualityCheck qualityCheck : qualityChecks) {
      if (qualityCheck.hasErrorStatus()) {
        hasError = true;
      }
    }
    
    return hasError;
  }


  /**
   * Boolean to determine whether a copy of a quality check already
   * exists in the list of quality checks for this entity. There are
   * occasions where we wish not to add duplicate copies of the same
   * quality check. This method makes it possible to determine whether
   * the entity already has a copy in its collection.
   * 
   * @param qualityCheck   the quality check that we're want to know
   *                       whether it's already in this entity's list
   * @return               true if found in this entity, else false
   */
  private boolean hasQualityCheck(QualityCheck qualityCheck) {
    for (QualityCheck qc : qualityChecks) {
      if (qualityCheck.equals(qc)) {
        return true;
      }
    }  
    return false;
  }
  

  /**
   * Generate an XML entity report structure from the quality check objects
   * stored in the entity.
   * 
   * @return an XML string representation of the <entityReport> XML fragment.
   */
  public String toXML() {
    String xmlString = null; 
    
      if (entity != null) {
        String entityName = entity.getName();
        String entityId = entity.getId();
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("  <entityReport>\n");
        stringBuffer.append("    <entityName>" + entityName + "</entityName>\n");
        if (entityId != null && !entityId.equals("")) {
          stringBuffer.append("    <entityId>" + entityId + "</entityId>\n");
        }  
        if (qualityChecks != null && qualityChecks.size() > 0) {
          for (QualityCheck aQualityCheck : qualityChecks) {
            String qualityCheckXML = aQualityCheck.toXML();
            stringBuffer.append(qualityCheckXML);
          }
        }
        stringBuffer.append("  </entityReport>\n");
        xmlString = stringBuffer.toString();
      }
    
    return xmlString;
  }

}
