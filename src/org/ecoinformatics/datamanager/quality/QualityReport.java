package org.ecoinformatics.datamanager.quality;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;


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
  
  // The DataPackage object that this QualityReport is reporting on
  private DataPackage dataPackage;
  
  private String packageId;     // the eml packageId value
  // A list of dataset-level quality checks
  private ArrayList<QualityCheck> datasetQualityChecks;
  // A list of entity-level quality checks
  
  
  /*
   * Constructors
   */
  
  /**
   * Constructor used when we associate a quality report
   * with an existing data package.
   * 
   * @param dataPackage the DataPackage object associated
   *        with this quality report
   */
  public QualityReport(DataPackage dataPackage) {
    this.dataPackage = dataPackage;
    if (dataPackage != null) {
      this.packageId = dataPackage.getPackageId();
    }
    
    this.datasetQualityChecks = new ArrayList<QualityCheck>();
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

  /**
   * Adds a quality check to the list of quality checks that have been
   * performed on this data package at the data set level.
   * 
   * @param qualityCheck    the new quality check to add to the list
   */
  public void addDatasetQualityCheck(QualityCheck qualityCheck) {
    datasetQualityChecks.add(qualityCheck);
  }
  
  
  public String getPackageId() {
    return packageId;
  }


  public ArrayList<QualityCheck> getDatasetQualityChecks() {
    return datasetQualityChecks;
  }


  public void setPackageId(String packageId) {
    this.packageId = packageId;
  }


  public void setDatasetQualityChecks(ArrayList<QualityCheck> qualityChecks) {
    this.datasetQualityChecks = qualityChecks;
  }


  /**
   * Generates an XML quality report string from the quality check objects
   * and the entity report objects stored in the data package.
   * 
   * @return an XML string representation of the full quality report
   */
  public String toXML() {
    Date now = new Date();
    SimpleDateFormat dateFormat = 
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    String dateCreated = dateFormat.format(now);
    String xmlString = null;
    
    StringBuffer stringBuffer = new StringBuffer("");
    
    stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    stringBuffer.append("<qualityReport>\n");
    stringBuffer.append("  <creationDate>" + dateCreated + "</creationDate>\n");
    stringBuffer.append("  <packageId>" + packageId + "</packageId>\n");
    
    // Add quality checks at the data package metadata level
    stringBuffer.append("  <datasetReport>\n");
    if (datasetQualityChecks != null && datasetQualityChecks.size() > 0) {
      for (QualityCheck aQualityCheck : datasetQualityChecks) {
        String qualityCheckXML = aQualityCheck.toXML();
        stringBuffer.append(qualityCheckXML);
      }
    }
    stringBuffer.append("  </datasetReport>\n");
    
    // Add quality checks at the entity level
    if (this.dataPackage != null) {     
      Entity[] entityArray = dataPackage.getEntityList();    
      if (entityArray != null) {
        for (int i = 0; i < entityArray.length; i++) {
          Entity entity = entityArray[i];
          if (entity != null) {
            EntityReport entityReport = entity.getEntityReport();
            if (entityReport != null) {
              String entityReportXML = entityReport.toXML();
              stringBuffer.append(entityReportXML);
            }
          }
        }
      }
    }

    stringBuffer.append("</qualityReport>\n");
    xmlString = stringBuffer.toString();
    
    return xmlString;
  }

}
