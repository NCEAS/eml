package org.ecoinformatics.datamanager.quality;

import java.util.ArrayList;


public class QualityReport {
  
  /*
   * Class variables
   */
  
  /*
   *  Boolean switch to determine whether quality reporting is turned on or off
   *  in the application that is using the Data Manager library. It is the
   *  application's responsibility to set this value by reading in the value
   *  of the 'qualityReporting' property and calling the 
   *  QualityReport.setQualityReporting() static method, passing in the 
   *  appropriate value.
   */
  private static Boolean qualityReporting = new Boolean(false);
  

  /*
   * Instance variables
   */
  
  private String packageId;     // the eml packageId value
  private String dateCreated;   // the date this quality report was generated
  private ArrayList<QualityCheck> qualityChecks;
  private ArrayList<EntityReport> entityReports;
  
  
  /*
   * Constructors
   */
  
  public QualityReport(String packageId, String dateCreated) {
    this.packageId = packageId;
    this.dateCreated = dateCreated;
    this.qualityChecks = new ArrayList<QualityCheck>();
    this.entityReports = new ArrayList<EntityReport>();
  }


  
  /*
   * Class methods
   */
  
  /**
   * Returns the qualityReporting value, a Boolean. Other classes in the
   * Data Manager library call this method to determine whether quality
   * reporting operations should or should not be executed.
   * 
   * @return  qualityReporting. If true, quality reporting is turned on.
   */
  public static Boolean isQualityReporting() {
    return qualityReporting;
  }
  
  
  /**
   * Sets the value of qualityReporting using a string parameter. If the string
   * equals (ignore case) "true", then quality reporting is turned on. Any other 
   * string value results in quality reporting being turned off (the default
   * setting).
   * 
   * @param trueOrFalse   a string argument. "true" (ignore case) turns on quality
   *                      reporting
   */
  public static void setQualityReporting(String trueOrFalse) {
    Boolean aBoolean = new Boolean(trueOrFalse);
    qualityReporting = aBoolean;
  }
  
  
  /**
   * Sets the value of qualityReporting using a boolean parameter. If the
   * argument is true, then quality reporting is turned on. If false, then 
   * quality reporting is turned off.
   * 
   * @param trueOrFalse    true turns on quality reporting
   */
  public static void setQualityReporting(boolean trueOrFalse) {
    Boolean aBoolean = new Boolean(trueOrFalse);
    qualityReporting = aBoolean;
  }
  
  
  
  /*
   * Instance methods
   */

  public String getDateCreated() {
    return dateCreated;
  }


  public ArrayList<EntityReport> getEntityReports() {
    return entityReports;
  }


  public String getPackageId() {
    return packageId;
  }


  public ArrayList<QualityCheck> getQualityChecks() {
    return qualityChecks;
  }


  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }


  public void setEntityReports(ArrayList<EntityReport> entityReports) {
    this.entityReports = entityReports;
  }


  public void setPackageId(String packageId) {
    this.packageId = packageId;
  }


  public void setQualityChecks(ArrayList<QualityCheck> qualityChecks) {
    this.qualityChecks = qualityChecks;
  }


}
