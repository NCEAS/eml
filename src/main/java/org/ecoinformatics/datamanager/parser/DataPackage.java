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

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.ecoinformatics.datamanager.quality.EntityReport;
import org.ecoinformatics.datamanager.quality.QualityReport;
import org.ecoinformatics.datamanager.quality.QualityCheck;
import org.ecoinformatics.datamanager.quality.QualityCheck.Status;
import org.ecoinformatics.eml.EMLParser;
import org.ecoinformatics.eml.SAXValidate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import edu.ucsb.nceas.utilities.XMLUtilities;

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
  
	  private static final String IDENTIFIER_WITH_LEADING_ZERO_PATTERN = "^[A-Za-z_0-9\\-]+\\.0\\d+\\.\\d+$";
	  private static final String REVISION_WITH_LEADING_ZERO_PATTERN =   "^[A-Za-z_0-9\\-]+\\.\\d+\\.0\\d+$";
	  //private static final String LTER_PACKAGE_ID_PATTERN =              "^knb-lter-[a-z][a-z][a-z]\\.\\d+\\.\\d+$";
	  private static final String METACAT_PACKAGE_ID_PATTERN =           "^[A-Za-z_0-9\\-]+\\.\\d+\\.\\d+$";
	  
	  
	  /* A comma-separated list of allowable scope values. This value should be set
	   * to non-null by applications that use the 'packageIdPattern' quality check.
	   */
	  private static String scopeRegistry = null;

  /*
   * Instance fields
   */
  
  private String accessXML = null;       // <access> element XML string
  private String emlNamespace = null;    // e.g. "eml://ecoinformatics.org/eml-2.1.0"
  private Entity[] entityList = null;
  private boolean parserValid = false;
  private boolean schemaValid = false;
  private int numberOfKeywordElements = 0;
  private int numberOfMethodsElements = 0;
  private int numberOfCoverageElements = 0;
  private int numberOfGeographicCoverageElements = 0;
  private int numberOfTaxonomicCoverageElements = 0;
  private int numberOfTemporalCoverageElements = 0;
  private String packageId  = null;
  private String pubDate;
  private QualityReport qualityReport = null;
  private String system = null;
  private String title = null;
  private List<Party> creators = null;
  private String language = null;
  private List<String> keywords = null;
  private String absctract = null;
  private Party publisher = null;

  
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
    this.creators = new ArrayList<Party>();
    this.keywords = new ArrayList<String> ();
    qualityCheckPackageId(packageId);
  }
  
  
  /*
   * Class methods
   */
  
  /**
   * Applies the EML dereferencing stylesheet (whose path is set in the 
   * properties file and stored in the QualityReport class) to transform 
   * the original EML document to a fully dereferenced EML document.
   * 
   * @param originalEmlString  the original EML XML string
   * @return emlString   the result of the transformation from the 
   *                     original EML to the dereferenced EML
   * @throws IllegalStateException
   *                     if an error occurs during the transformation process
   */
  public static String dereferenceEML(String originalEmlString) 
          throws IllegalStateException {
    String dereferencedEmlString = "";
    Result result;
    StringWriter stringWriter = new StringWriter();
    javax.xml.transform.Transformer transformer;
    javax.xml.transform.TransformerFactory transformerFactory;
    final String xslPath = QualityReport.getEmlDereferencerXSLTPath();
    Source xmlSource;
    File xsltFile = new File(xslPath);            
    Source xsltSource;
    
    StringReader stringReader = new StringReader(originalEmlString);
    xmlSource = new javax.xml.transform.stream.StreamSource(stringReader);
    xsltSource = new javax.xml.transform.stream.StreamSource(xsltFile);
    result = new javax.xml.transform.stream.StreamResult(stringWriter);
    String transformerFactoryValue = System.getProperty("javax.xml.transform.TransformerFactory");
    System.out.println("javax.xml.transform.TransformerFactory :" + transformerFactoryValue);
    transformerFactory = javax.xml.transform.TransformerFactory.newInstance();

    try {
      transformer = transformerFactory.newTransformer(xsltSource);      
      transformer.transform(xmlSource, result);
      dereferencedEmlString = stringWriter.toString();
    }
    catch (TransformerConfigurationException e) {
      Throwable x = e;
      if (e.getException() != null) {
        x = e.getException();    
      }
      x.printStackTrace();
      throw new IllegalStateException(e);
    }
    catch (TransformerException e) {
      Throwable x = e;
      if (e.getException() != null) {
        x = e.getException();    
      }
      x.printStackTrace(); 
      throw new IllegalStateException(e);
    }
      
    return dereferencedEmlString;
  }
  
  
  /**
   * Sets the value of the scope registry, for use by the
   * 'packageIdPattern' quality check.
   * 
   * @param scopeRegistry  A comma-separated list of allowable
   *   data package scope values, e.g. "scope1,scope2,scope3".
   */
  public static void setScopeRegistry(String scopeRegistry) {
	  DataPackage.scopeRegistry = scopeRegistry;
  }
  
  
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
   * Get the language used in this eml document
   * @return the language 
   */
  public String getLanguage() {
      return language;
  }

  /**
   * Set the language description
   * @param language
   */
  public void setLanguage(String language) {
      this.language = language;
  }


  /**
   * Get the list of keywords in this eml document
   * @return
   */
  public List<String> getKeywords() {
      return keywords;
  }

  /**
   * Set the keywords list
   * @param keywords
   */
  public void setKeywords(List<String> keywords) {
      this.keywords = keywords;
  }

  /**
   * Get the abstract of this eml document
   * @return the abstract
   */
  public String getAbsctract() {
      return absctract;
  }

  /**
   * Set the abstract 
   * @param absctrac
   */
  public void setAbsctract(String absctract) {
      this.absctract = absctract;
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
   * Gets the value of the system attribute.
   * 
   * @return  a String, e.g. "knb"
   */
  public String getSystem() {
    return system;
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


  /*
   * Gets the value of the numberOfKeywordElements variable.
   * 
   * @return   the number of keyword elements detected in this
   *           data package
   */
  public int getNumberOfKeywordElements() {
    return numberOfKeywordElements;
  }


  /*
   * Gets the value of the numberOfMethodsElements variable.
   * 
   * @return   the number of methods elements detected in this
   *           data package
   */
  public int getNumberOfMethodsElements() {
    return numberOfMethodsElements;
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


  /*
   * Boolean to determine whether a given packageId has an
   * identifier with a leading zero character. This usually causes problems
   * with subsequent processing of the data package and should be
   * flagged as an error. A zero by itself is acceptable, however.
   * 
   * Examples:
   * 
   *     "datapackage.012.12"    // returns true
   *     "datapackage.0.12"      // returns false
   *     "datapackage.10.12"     // returns false
   */
  private boolean identifierHasLeadingZero(String packageId) {
    boolean hasLeadingZero = false;
    
    String regexPattern = IDENTIFIER_WITH_LEADING_ZERO_PATTERN;   
    hasLeadingZero = Pattern.matches(regexPattern, packageId);

    return hasLeadingZero;
  }
  
  
  /*
   * Boolean to determine whether a given packageId has a
   * revision with a leading zero character. If true, this may cause problems
   * with subsequent processing of the data package and should be
   * flagged as an error. A zero by itself is acceptable, however.
   * 
   * Examples:
   * 
   *     "datapackage.12.012"    // returns true
   *     "datapackage.12.0"      // returns false
   *     "datapackage.12.11"     // returns false
   */
  private boolean revisionHasLeadingZero(String packageId) {
    boolean hasLeadingZero = false;
    
    String regexPattern = REVISION_WITH_LEADING_ZERO_PATTERN;   
    hasLeadingZero = Pattern.matches(regexPattern, packageId);

    return hasLeadingZero;
  }
  
  
  /**
   * Gets the parserValid value
   * 
   * @return  the value of the parserValid variable
   */
  public boolean isParserValid() {
    return parserValid;
  }


  /**
   * Gets the schemaValid value
   * 
   * @return  the value of the schemaValid variable
   */
  public boolean isSchemaValid() {
    return schemaValid;
  }


  /*
   * Boolean to determine whether a given packageId conforms to
   * the string pattern of a particular organization such as LTER.
   * If the specified system value does not have a regular
   * expression pattern declared for its packageId, then the
   * packageId is assumed to be valid by default.
   */
  private boolean isValidPackageIdForMetacat(String packageId) {
    boolean isValid = true;
    String regexPattern = METACAT_PACKAGE_ID_PATTERN;
    
    isValid = Pattern.matches(regexPattern, packageId);
 
    return isValid;
  }
  
  
  /*
   * Boolean to determine whether a given packageId scope value conforms to
   * the allowed set of scopes of a particular organization such as LTER.
   */
	private boolean isValidScope(String packageId) {
		boolean isValid = false;

		if (packageId == null)
			return false;
		if (scopeRegistry == null)
			return true;

		StringTokenizer stringTokenizer = new StringTokenizer(scopeRegistry, ",");
		final int tokenCount = stringTokenizer.countTokens();

		for (int i = 0; i < tokenCount; i++) {
			String token = stringTokenizer.nextToken();
			if (packageId.startsWith(token + ".")) {
				isValid = true;
				break;
			}
		}

		return isValid;
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
    String packageIdIdentifier = "packageIdPattern";
    QualityCheck packageIdTemplate = QualityReport.getQualityCheckTemplate(packageIdIdentifier);
    QualityCheck packageIdQualityCheck = new QualityCheck(packageIdIdentifier, packageIdTemplate);
	String systemAttribute = packageIdQualityCheck.getSystem();

    if (QualityCheck.shouldRunQualityCheck(this, packageIdQualityCheck)) {
    	
      if (scopeRegistry != null) {
    	packageIdQualityCheck.setExpected("'scope.n.m', where 'n' and 'm' are integers and 'scope' is one of an allowed set of values");
      }

      if (packageId != null) {
        packageIdQualityCheck.setFound(packageId);
      }
      
      if (!isValidPackageIdForMetacat(packageId)) {
        packageIdQualityCheck.setStatus(Status.error);
        packageIdQualityCheck.setExplanation("The packageId value should match the pattern 'scope.identifier.revision'.");
      }
      else if (identifierHasLeadingZero(packageId)) {
        packageIdQualityCheck.setStatus(Status.error);
        packageIdQualityCheck.setExplanation("A leading zero was found in the identifier. The identifier value must be a whole number.");
        packageIdQualityCheck.setSuggestion("Remove leading zeros from the identifier value.");
      }
      else if (revisionHasLeadingZero(packageId)) {
        packageIdQualityCheck.setStatus(Status.error);
        packageIdQualityCheck.setExplanation("A leading zero was found in the revision. The revision value must be a whole number.");
        packageIdQualityCheck.setSuggestion("Remove leading zeros from the revision value.");
      }
      else if (isValidScope(packageId)) {
        packageIdQualityCheck.setStatus(Status.valid);
        packageIdQualityCheck.setSuggestion("");
      }
      else {
        packageIdQualityCheck.setFailedStatus();
        if (scopeRegistry != null) {
        	packageIdQualityCheck.setExplanation(String.format(
        			"A packageId should start with one of the following scope values: %s",
        			scopeRegistry));
        	if (systemAttribute != null && systemAttribute.equals("lter")) {
        	  packageIdQualityCheck.setSuggestion(
            		"Use a scope value that you are authorized to use for your site or project, " +
                    "or you may request that a new scope value be added to the list of allowed " +
            	    "values by contacting tech_support@LTERnet.edu.");
        	}
        }
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
    String emlVersionIdentifier = "emlVersion";
    QualityCheck emlVersionTemplate = QualityReport.getQualityCheckTemplate(emlVersionIdentifier);
    QualityCheck emlVersionQualityCheck = new QualityCheck(emlVersionIdentifier, emlVersionTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, emlVersionQualityCheck)) {
      // Initialize the emlNamespaceQualityCheck
      boolean isValidNamespace = false;

      if (emlNamespace != null) {
        emlVersionQualityCheck.setFound(emlNamespace);
        if (emlNamespace.equals("eml://ecoinformatics.org/eml-2.1.0") ||
            emlNamespace.equals("eml://ecoinformatics.org/eml-2.1.1")
           ) {
          isValidNamespace = true;
        }
      }
      
      if (isValidNamespace) {
        emlVersionQualityCheck.setStatus(Status.valid);
        emlVersionQualityCheck.setSuggestion("");
      }
      else {
        emlVersionQualityCheck.setFailedStatus();
      }
      this.addDatasetQualityCheck(emlVersionQualityCheck);
    }
  }
  
  
  /**
   * Performs the 'schemaValid' quality check.
   * 
   * @param doc            the XML DOM document object
   * @param namespaceInDoc the namespace value specified in the document
   */
  public void checkSchemaValid(Document doc, String namespaceInDoc) {
    String schemaValidIdentifier = "schemaValid";
    QualityCheck schemaValidTemplate = 
      QualityReport.getQualityCheckTemplate(schemaValidIdentifier);
    QualityCheck schemaValidQualityCheck = 
      new QualityCheck(schemaValidIdentifier, schemaValidTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, schemaValidQualityCheck)) {
      // Initialize the schemaValidQualityCheck
      boolean validateSchema = true;
      String found = "";
      final String parserName = "DEFAULT";

      Node documentElement = doc.getDocumentElement();
      String xmlString = XMLUtilities.getDOMTreeAsString(documentElement);
      StringReader stringReader = new StringReader(xmlString);
      SAXValidate saxValidate = new SAXValidate(validateSchema);
    
      try {
        saxValidate.runTest(stringReader, parserName, namespaceInDoc);
        found = "Document validated for namespace: '" + namespaceInDoc + "'";
        schemaValidQualityCheck.setStatus(Status.valid);
        schemaValidQualityCheck.setSuggestion("");
        this.schemaValid = true;
      }
      catch (Exception e) {
        found = "Failed to validate for namespace: '" + namespaceInDoc + 
                "'; " + e.getMessage();
        schemaValidQualityCheck.setFailedStatus();
      }
      
      schemaValidQualityCheck.setFound(found);
      this.addDatasetQualityCheck(schemaValidQualityCheck);
    }
  }
  
  
  /**
   * Performs the 'parserValid' quality check, using the 'EML IDs
   * and References Parser' code in the org.ecoinformatics.eml.EMLParser 
   * class.
   * 
   * @param doc            the XML DOM document object
   */
  public void checkParserValid(Document doc) {
    String parserValidIdentifier = "parserValid";
    QualityCheck parserValidTemplate = 
      QualityReport.getQualityCheckTemplate(parserValidIdentifier);
    QualityCheck parserValidQualityCheck = 
      new QualityCheck(parserValidIdentifier, parserValidTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, parserValidQualityCheck)) {
      // Initialize the parserValidQualityCheck
      String found = "";
      Node documentElement = doc.getDocumentElement();
      String xmlString = XMLUtilities.getDOMTreeAsString(documentElement);
   
      try {
        EMLParser emlParser = new EMLParser(xmlString);
        found = "EML IDs and references parser succeeded";
        parserValidQualityCheck.setStatus(Status.valid);
        parserValidQualityCheck.setSuggestion("");
        this.parserValid = true;
      }
      catch (Exception e) {
        found = "Failed to parse IDs and references: " + e.getMessage();
        parserValidQualityCheck.setFailedStatus();
      }
      
      parserValidQualityCheck.setFound(found);
      this.addDatasetQualityCheck(parserValidQualityCheck);
    }
  }
  
  
  /**
   * Performs the 'schemaValidDereferenced' quality check.
   * Checks schema validity after the original document has
   * been dereferenced (i.e. all references to "id" elements
   * have been substituted with the actual elements.) An XSLT
   * stylesheet is used for performing the dereferencing.
   * 
   * @param doc            the XML DOM document object
   * @param namespaceInDoc the namespace value specified in the document
   */
  public void checkSchemaValidDereferenced(Document doc, String namespaceInDoc) {
    String identifier = "schemaValidDereferenced";
    QualityCheck qualityCheckTemplate = 
      QualityReport.getQualityCheckTemplate(identifier);
    QualityCheck qualityCheck = 
      new QualityCheck(identifier, qualityCheckTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, qualityCheck)) {
      // Initialize the schemaValidQualityCheck
      boolean validateSchema = true;
      String found = "";
      final String parserName = "DEFAULT";

      Node documentElement = doc.getDocumentElement();
      String xmlString = XMLUtilities.getDOMTreeAsString(documentElement);
      String deferencedXmlString = DataPackage.dereferenceEML(xmlString);
      
      StringReader stringReader = new StringReader(deferencedXmlString);
      SAXValidate saxValidate = new SAXValidate(validateSchema);
    
      try {
        saxValidate.runTest(stringReader, parserName, namespaceInDoc);
        found = "Dereferenced document validated for namespace: '" + namespaceInDoc + "'";
        qualityCheck.setStatus(Status.valid);
        qualityCheck.setSuggestion("");
        this.schemaValid = true;
      }
      catch (Exception e) {
        found = "Failed to validate dereferenced document for namespace: '" + namespaceInDoc + 
                "'; " + e.getMessage();
        qualityCheck.setFailedStatus();
      }
      
      qualityCheck.setFound(found);
      this.addDatasetQualityCheck(qualityCheck);
    }
  }
  
  
  /**
   * Set the value of the numberOfKeywordElements instance variable
   * and run a quality check based on the value.
   * 
   * @param   n  the number of keyword elements detected in
   *             this data package by the parser
   */
  public void setNumberOfKeywordElements(int n) {
    this.numberOfKeywordElements = n;

    /*
     *  Do a quality check for the presence of at least
     *  one 'keyword' element
     */
    String qualityCheckIdentifier = "keywordPresent";
    QualityCheck qualityCheckTemplate = 
      QualityReport.getQualityCheckTemplate(qualityCheckIdentifier);
    QualityCheck qualityCheck = 
      new QualityCheck(qualityCheckIdentifier, qualityCheckTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, qualityCheck)) {
      String found = numberOfKeywordElements + " 'keyword' element(s) found";
      qualityCheck.setFound(found);
      if (numberOfKeywordElements > 0) {
        qualityCheck.setStatus(Status.valid);
        qualityCheck.setExplanation("");
        qualityCheck.setSuggestion("");
      }
      else {
        qualityCheck.setFailedStatus();
      }
      
      addDatasetQualityCheck(qualityCheck);
    }
  }


  /**
   * Set the value of the numberOfMethodsElements instance variable
   * and run a quality check based on the value.
   * 
   * @param   n  the number of methods elements detected in
   *             this data package by the parser
   */
  public void setNumberOfMethodsElements(int n) {
    this.numberOfMethodsElements = n;

    /*
     *  Do a quality check for the presence of at least
     *  one 'methods' element
     */
    String methodsElementIdentifier = "methodsElementPresent";
    QualityCheck methodsElementTemplate = 
      QualityReport.getQualityCheckTemplate(methodsElementIdentifier);
    QualityCheck methodsElementQualityCheck = 
      new QualityCheck(methodsElementIdentifier, methodsElementTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, methodsElementQualityCheck)) {
      String found = numberOfMethodsElements + " 'methods' element(s) found";
      methodsElementQualityCheck.setFound(found);
      if (numberOfMethodsElements > 0) {
        methodsElementQualityCheck.setStatus(Status.valid);
        methodsElementQualityCheck.setExplanation("");
        methodsElementQualityCheck.setSuggestion("");
      }
      else {
        methodsElementQualityCheck.setFailedStatus();
      }
      
      addDatasetQualityCheck(methodsElementQualityCheck);
    }
  }


  /**
   * Set the value of the numberOfCoverageElements instance variable
   * and run a quality check based on the value.
   * 
   * @param   n  the number of coverage elements detected in
   *             this data package by the parser
   */
  public void setNumberOfCoverageElements(int n) {
    this.numberOfCoverageElements = n;

    /*
     *  Do a quality check for the presence of at least
     *  one 'coverage' element
     */
    String identifier = "coveragePresent";
    QualityCheck qualityCheckTemplate = 
      QualityReport.getQualityCheckTemplate(identifier);
    QualityCheck qualityCheck = 
      new QualityCheck(identifier, qualityCheckTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, qualityCheck)) {
      String found = numberOfCoverageElements + " 'coverage' element(s) found";
      qualityCheck.setFound(found);
      if (numberOfCoverageElements > 0) {
        qualityCheck.setStatus(Status.valid);
        qualityCheck.setExplanation("");
        qualityCheck.setSuggestion("");
      }
      else {
        qualityCheck.setFailedStatus();
      }
      
      addDatasetQualityCheck(qualityCheck);
    }
  }


  /**
   * Set the value of the numberOfGeographicCoverageElements instance 
   * variable and run a quality check based on the value.
   * 
   * @param   n  the number of geographicCoverage elements detected in
   *             this data package by the parser
   */
  public void setNumberOfGeographicCoverageElements(int n) {
    this.numberOfGeographicCoverageElements = n;

    /*
     *  Do a quality check to report the number of
     *  'geographicCoverage' elements detected
     */
    String identifier = "geographicCoveragePresent";
    QualityCheck qualityCheckTemplate = 
      QualityReport.getQualityCheckTemplate(identifier);
    QualityCheck qualityCheck = 
      new QualityCheck(identifier, qualityCheckTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, qualityCheck)) {
      String found = numberOfGeographicCoverageElements + " 'geographicCoverage' element(s) found";
      qualityCheck.setFound(found);      
      addDatasetQualityCheck(qualityCheck);
    }
  }


  /**
   * Set the value of the numberOfTaxonomicCoverageElements instance 
   * variable and run a quality check based on the value.
   * 
   * @param   n  the number of taxonomicCoverage elements detected in
   *             this data package by the parser
   */
  public void setNumberOfTaxonomicCoverageElements(int n) {
    this.numberOfTaxonomicCoverageElements = n;

    /*
     *  Do a quality check to report the number of
     *  'taxonomicCoverage' elements detected
     */
    String identifier = "taxonomicCoveragePresent";
    QualityCheck qualityCheckTemplate = 
      QualityReport.getQualityCheckTemplate(identifier);
    QualityCheck qualityCheck = 
      new QualityCheck(identifier, qualityCheckTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, qualityCheck)) {
      String found = numberOfTaxonomicCoverageElements + " 'taxonomicCoverage' element(s) found";
      qualityCheck.setFound(found);      
      addDatasetQualityCheck(qualityCheck);
    }
  }


  /**
   * Set the value of the numberOfTemporalCoverageElements instance 
   * variable and run a quality check based on the value.
   * 
   * @param   n  the number of temporalCoverage elements detected in
   *             this data package by the parser
   */
  public void setNumberOfTemporalCoverageElements(int n) {
    this.numberOfTemporalCoverageElements = n;

    /*
     *  Do a quality check to report the number of
     *  'temporalCoverage' elements detected
     */
    String identifier = "temporalCoveragePresent";
    QualityCheck qualityCheckTemplate = 
      QualityReport.getQualityCheckTemplate(identifier);
    QualityCheck qualityCheck = 
      new QualityCheck(identifier, qualityCheckTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, qualityCheck)) {
      String found = numberOfTemporalCoverageElements + " 'temporalCoverage' element(s) found";
     qualityCheck.setFound(found);      
      addDatasetQualityCheck(qualityCheck);
    }
  }


  /**
   * Sets the value of the 'pubDate' to the specified String 
   * value.
   * 
   * @param systemValue   the 'pubDate' value to set
   */
  public void setPubDate(String pubDate) {
    this.pubDate = pubDate;

    /*
     *  Do a quality check on pubDate presence
     */
    String identifier = "pubDatePresent";
    QualityCheck qualityCheckTemplate = 
      QualityReport.getQualityCheckTemplate(identifier);
    QualityCheck qualityCheck = 
      new QualityCheck(identifier, qualityCheckTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, qualityCheck)) {
    	boolean hasPubDate = ((pubDate != null) && (pubDate.length() >= 4));
    	String found = pubDate;

        if (hasPubDate) {
            qualityCheck.setStatus(Status.valid);
            qualityCheck.setExplanation("");
            qualityCheck.setSuggestion("");
        }
        else {
        	found = "pubDate not found";
            qualityCheck.setFailedStatus();
        }
        qualityCheck.setFound(found);      
        addDatasetQualityCheck(qualityCheck);
    }
  }
  
  /**
   * Get the publication date of this package
   * @return
   */
  public String getPubDate() {
      return this.pubDate;
  }
  
  
  /**
   * Sets the value of the 'system' to the specified String 
   * value.
   * 
   * @param systemValue   the 'system' value to set
   */
  public void setSystem(String systemValue) {
    this.system = systemValue;
  }
  
  
  /**
   * Sets the value of the 'title' to the specified String 
   * value.
   * 
   * @param titleText  the title value to set
   */
  public void setTitle(String titleText) {
    this.title = titleText;

    /*
     *  Do a quality check on the number of words in the
     *  dataset title text
     */
    String identifier = "titleLength";
    QualityCheck qualityCheckTemplate = 
      QualityReport.getQualityCheckTemplate(identifier);
    QualityCheck qualityCheck = 
      new QualityCheck(identifier, qualityCheckTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, qualityCheck)) {
      int wordCount = wordCount(titleText);
      String found = wordCount + " words found.";
      qualityCheck.setFound(found);    

      if (wordCount >= 5) {
        qualityCheck.setStatus(Status.valid);
        qualityCheck.setExplanation("");
        qualityCheck.setSuggestion("");
      }
      else {
        qualityCheck.setFailedStatus();
      }

      addDatasetQualityCheck(qualityCheck);
    }
  }
  
  /**
   * Returns the package title
   * @return
   */
  public String getTitle() {
	  return this.title;
  }
  
  
  public List<Party> getCreators() {
	return creators;
  }


  public void setCreators(List<Party> creators) {
	this.creators = creators;
  }

  /**
   * Get the publisher of this package
   * @return
   */
  public Party getPublisher() {
    return publisher;
  }

  /**
   * Set the publisher of this package
   * @param publisher
   */
  public void setPublisher(Party publisher) {
    this.publisher = publisher;
  }



/**
   * Checks the number of words in the dataset abstract text.
   * 
   * @param abstractText  the abstract text to be analyzed
   */
  public void checkDatasetAbstract(String abstractText) {
    /*
     *  Do a quality check on the number of words in the
     *  dataset abstract text
     */
    String identifier = "datasetAbstractLength";
    QualityCheck qualityCheckTemplate = 
      QualityReport.getQualityCheckTemplate(identifier);
    QualityCheck qualityCheck = 
      new QualityCheck(identifier, qualityCheckTemplate);

    if (QualityCheck.shouldRunQualityCheck(this, qualityCheck)) {
      int wordCount = wordCount(abstractText);
      String found = wordCount + " words found.";
      qualityCheck.setFound(found);    

      if (wordCount >= 20) {
        qualityCheck.setStatus(Status.valid);
        qualityCheck.setExplanation("");
        qualityCheck.setSuggestion("");
      }
      else {
        qualityCheck.setFailedStatus();
      }

      addDatasetQualityCheck(qualityCheck);
    }
  }
  
  
  /* 
   * Count the words in a string of text. Useful for several different
   * quality checks.
   */
  private int wordCount(String text) {
    int wordCount = 0;
    
    if ((text != null) && (!text.equals(""))) {      
      String[] wordList = text.split("[\\s\\n]");
      int listLength = wordList.length;
      for (int i = 0; i < listLength; i++) {
        String word = wordList[i];
        if (word.length() >= 3) {
          wordCount++;
        }
      }
    }    
    
    return wordCount;
  }
  
}
