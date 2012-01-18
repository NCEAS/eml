package org.ecoinformatics.datamanager.quality;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xpath.CachedXPathAPI;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.ucsb.nceas.utilities.IOUtil;


public class QualityReport {
  
  /*
   * Class variables
   */
  
  private static final String INCLUDE_SYSTEM_PATH = "//includeSystem";
  private static final String QUALITY_CHECK_PATH = "//qualityCheck";

  // A list of systems whose quality checks are to be included in this
  // quality report
  private static ArrayList<String> includeSystems;
  
  // Stores a list quality check templates. Each quality check template
  // holds static content for a given quality check name
  private static ArrayList<QualityCheck> qualityCheckTemplates;

  /*
   *  Boolean switch to determine whether quality reporting is turned on or off
   *  in the application that is using the Data Manager library. It is the
   *  application's responsibility to set this value by reading in the value
   *  of the 'qualityReporting' property and calling the 
   *  QualityReport.setQualityReporting() static method, passing in the 
   *  appropriate boolean value.
   */
  private static Boolean qualityReporting = new Boolean(false);
  private static String qualityReportTemplatePath = null;
  
  /*
   * Instance variables
   */
  
  // The DataPackage object that this QualityReport is reporting on
  private DataPackage dataPackage;
  
  // A list of dataset-level quality checks
  private ArrayList<QualityCheck> datasetQualityChecks;
  
  private String packageId;     // the EML packageId value
  
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
    this.includeSystems = new ArrayList<String>();
    this.qualityCheckTemplates = new ArrayList<QualityCheck>();
    
    if (qualityReportTemplatePath != null) {
      try {
        File file = new File(qualityReportTemplatePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        parseQualityReportTemplate(fileInputStream);
      }
      catch (FileNotFoundException e) {
        System.err.println("FileNotFoundException while reading quality report template file: " +
                           e.getMessage());
        e.printStackTrace();
      }
      catch (ParserConfigurationException e) {
        System.err.println("ParserConfigurationException while parsing quality report template file: " +
            e.getMessage());
        e.printStackTrace();
      }
    }
    
  }

  
  /*
   * Class methods
   */
  
  
  /**
   * Gets the quality check template object (i.e. a QualityCheck object
   * holding static content for a given quality check name) for the
   * specified name
   */
  public static QualityCheck getQualityCheckTemplate(String name) {
    QualityCheck qualityCheckTemplate = null;

    if (name != null) {
      for (QualityCheck qualityCheck : qualityCheckTemplates) {
        if (name.equals(qualityCheck.getName())) {
          qualityCheckTemplate = qualityCheck;
        }
      }
    }
    
    return qualityCheckTemplate;
  }
  
  
  /**
   * Boolean to determine whether the specified system is
   * in the list of included systems (as specified in the
   * qualityReport template document).
   * 
   * @param   system  the system value (as specified in a given quality check)
   * @return  true if the specified system is an included system, else false
   */
  public static boolean isIncludeSystem(String system) {
    boolean isIncludeSystem = false;
    
    if (system != null) {
      for (String includeSystem : includeSystems) {
        if (system.equals(includeSystem)) {
          isIncludeSystem = true;
        }
      }
    }
    
    return isIncludeSystem;
  }
  
  
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
   * Sets the value of qualityReporting using a boolean parameter. If the
   * argument is true, then quality reporting is turned on. If false, then 
   * quality reporting is turned off.
   * 
   * @param trueOrFalse    true turns on quality reporting
   * @param path           the path to the quality report template file
   */
  public static void setQualityReporting(boolean trueOrFalse, String path) {
    Boolean aBoolean = new Boolean(trueOrFalse);
    qualityReporting = aBoolean;
    
    if (qualityReporting) {
      setQualityReportTemplatePath(path);
    }
  }
  
  
  /**
   * Sets the string value of the path to the quality report template file
   * @param path
   */
  public static void setQualityReportTemplatePath(String path) {
    qualityReportTemplatePath = path;
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
  
  
  /*
   * Adds an includeSystem string value to the list of include systems
   */
  private void addIncludeSystem(String includeSystem) {
    includeSystems.add(includeSystem);
  }
  
  
  /*
   * Adds a quality check template to the list of quality check templates
   */
  private void addQualityCheckTemplate(QualityCheck qualityCheck) {
    qualityCheckTemplates.add(qualityCheck);
  }
  

  /**
   * Gets the list of dataset quality checks
   * @return
   */
  public ArrayList<QualityCheck> getDatasetQualityChecks() {
    return datasetQualityChecks;
  }


  /**
   * Returns the packageId value
   * 
   * @return  packageId
   */
  public String getPackageId() {
    return packageId;
  }


  /**
   * Parses a quality report template document.
   * 
   * @param   inputStream          the input stream to the document
   * @return  dataPackage          a DataPackage object holding parsed values
   */
  public void parseQualityReportTemplate(InputStream inputStream) 
          throws ParserConfigurationException {
    
    DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    CachedXPathAPI xpathapi = new CachedXPathAPI();

    Document document = null;

    try {
      document = documentBuilder.parse(inputStream);
      
      if (document != null) {
        parseIncludeSystems(document);

        // Parse the quality check templates
        NodeList qualityCheckList = xpathapi.selectNodeList(document, QUALITY_CHECK_PATH);
      
        if (qualityCheckList != null) {
          for (int i = 0; i < qualityCheckList.getLength(); i++) {
            QualityCheck qualityCheck = new QualityCheck();
            Node qualityCheckNode = qualityCheckList.item(i);
          
            // Process qualityType attribute
            Node qualityTypeNode = xpathapi.selectSingleNode(qualityCheckNode, "@qualityType");
            if (qualityTypeNode != null) {
              String qualityType = qualityTypeNode.getNodeValue();
              qualityCheck.setQualityType(qualityType);
            }
            
            // Process system attribute
            Node systemNode = xpathapi.selectSingleNode(qualityCheckNode, "@system");
            if (systemNode != null) {
              String system = systemNode.getNodeValue();
              qualityCheck.setSystem(system);
            }
            
            // Process statusType attribute
            Node statusTypeNode = xpathapi.selectSingleNode(qualityCheckNode, "@statusType");
            if (statusTypeNode != null) {
              String statusType = statusTypeNode.getNodeValue();
              qualityCheck.setStatusType(statusType);
            }
            
            // Get the quality check name
            NodeList nameNodeList = xpathapi.selectNodeList(qualityCheckNode, "name");        
            if (nameNodeList != null && nameNodeList.getLength() > 0) {
              String name = nameNodeList.item(0).getTextContent();
              qualityCheck.setName(name);
            }
                 
            // Get description
            NodeList descriptionNodeList = xpathapi.selectNodeList(qualityCheckNode, "description");
            if (descriptionNodeList != null && descriptionNodeList.getLength() > 0) {
              String description = descriptionNodeList.item(0).getTextContent();
              qualityCheck.setDescription(description);
            }
            
            // Get expected 
            NodeList expectedNodeList = xpathapi.selectNodeList(qualityCheckNode, "expected");
            if (expectedNodeList != null && expectedNodeList.getLength() > 0) {
              String expected = expectedNodeList.item(0).getTextContent();
              qualityCheck.setExpected(expected);
            }
            
            // Get found
            NodeList foundNodeList = xpathapi.selectNodeList(qualityCheckNode, "found");
            if (foundNodeList != null && foundNodeList.getLength() > 0) {
              String found = foundNodeList.item(0).getTextContent();
              qualityCheck.setFound(found);
            }
            
            // Get status
            NodeList statusNodeList = xpathapi.selectNodeList(qualityCheckNode, "status");
            if (statusNodeList != null && statusNodeList.getLength() > 0) {
              String status = statusNodeList.item(0).getTextContent();
              qualityCheck.setStatus(status);
            }
            
            // Get explanation
            NodeList explanationNodeList = xpathapi.selectNodeList(qualityCheckNode, "explanation");
            if (explanationNodeList != null && explanationNodeList.getLength() > 0) {
              String explanation = explanationNodeList.item(0).getTextContent();
              qualityCheck.setExplanation(explanation);
            }
            
            // Get suggestion
            NodeList suggestionNodeList = xpathapi.selectNodeList(qualityCheckNode, "suggestion");
            if (suggestionNodeList != null && suggestionNodeList.getLength() > 0) {
              String suggestion = suggestionNodeList.item(0).getTextContent();
              qualityCheck.setSuggestion(suggestion);
            }
            
            // Get reference
            NodeList referenceNodeList = xpathapi.selectNodeList(qualityCheckNode, "reference");
            if (referenceNodeList != null && referenceNodeList.getLength() > 0) {
                String reference = referenceNodeList.item(0).getTextContent();
                qualityCheck.setReference(reference);
            }
            
            // Add this quality check template to the list of quality check templates
            addQualityCheckTemplate(qualityCheck);        
          }
        }
      }
    }
    catch (SAXException e) {
      e.printStackTrace();
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (TransformerException e) {
      e.printStackTrace();
    }

  }
  
  
  /*
   * Parses the includeSystem elements from the quality report template document
   */
  private void parseIncludeSystems(Document document) {
    CachedXPathAPI xpathapi = new CachedXPathAPI();

    try {
      // Process <includeSystem> elements
      NodeList includeSystemNodeList = xpathapi.selectNodeList(document, INCLUDE_SYSTEM_PATH);
      if (includeSystemNodeList != null) {
        for (int i = 0; i < includeSystemNodeList.getLength(); i++) {
          Node includeSystemNode = includeSystemNodeList.item(i);
          String includeSystem = includeSystemNode.getTextContent();
          addIncludeSystem(includeSystem);
        }
      }
    }
    catch (TransformerException e) {
      System.err.println("TransformerException parsing quality report template: " + e.getMessage());
      e.printStackTrace();
    }  
  }
  
  
  /**
   * Sets the packageId value for this quality report
   * 
   * @param packageId  the packageId value
   */
  public void setPackageId(String packageId) {
    this.packageId = packageId;
  }


  /**
   * Stores a quality report on the file system.
   * 
   * @param   qualityReportFile  the file object where the quality
   *            report is to be written
   * @return  true if success storing the report, else false
   */
  public boolean storeQualityReport(File qualityReportFile) 
          throws IOException {
    boolean success = false;
    
    String qualityReportXML = toXML();
    
    if (qualityReportXML != null) {
      StringBuffer stringBuffer = new StringBuffer(qualityReportXML);
      try {
        FileWriter fileWriter = new FileWriter(qualityReportFile);
        IOUtil.writeToWriter(stringBuffer, fileWriter, true);
      }
      catch (IOException e) {
        e.printStackTrace();
        throw(e);
      }
      finally {
        success = (qualityReportFile != null) && 
                  (qualityReportFile.exists());
     }
    }
      
    return success;   
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
    stringBuffer.append("<qr:qualityReport\n");
    stringBuffer.append("  xmlns=\"eml://ecoinformatics.org/qualityReport\"\n");
    stringBuffer.append("  xmlns:qr=\"eml://ecoinformatics.org/qualityReport\"\n");
    stringBuffer.append("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
    stringBuffer.append("  xsi:schemaLocation=\"eml://ecoinformatics.org/qualityReport http://svn.lternet.edu/svn/NIS/documents/schemas/quality/qualityReportSchema.xsd\"\n");
    stringBuffer.append("  >\n");
    stringBuffer.append("  <creationDate>" + dateCreated + "</creationDate>\n");
    stringBuffer.append("  <packageId>" + packageId + "</packageId>\n");
    
    /* 
     * Write the list of includeSystem elements that were read from 
     * the quality report template document. This is for referential purposes,
     * so that the reader of the report knows which systems were included
     * when determining the set of quality checks to apply.
     */
    for (String includeSystem : includeSystems) {
      stringBuffer.append("  <includeSystem>" + includeSystem + "</includeSystem>\n");
    }
    
    // Add quality checks at the data set level
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

    stringBuffer.append("</qr:qualityReport>\n");
    xmlString = stringBuffer.toString();
    
    return xmlString;
  }

}
