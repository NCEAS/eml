/**
 *    '$RCSfile: GenericDataPackageParser.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2007-10-18 00:45:08 $'
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

package org.ecoinformatics.datamanager.parser.generic;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xpath.CachedXPathAPI;

import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.DateTimeDomain;
import org.ecoinformatics.datamanager.parser.Domain;
import org.ecoinformatics.datamanager.parser.EnumeratedDomain;
import org.ecoinformatics.datamanager.parser.NumericDomain;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.StorageType;
import org.ecoinformatics.datamanager.parser.TextComplexDataFormat;
import org.ecoinformatics.datamanager.parser.TextDelimitedDataFormat;
import org.ecoinformatics.datamanager.parser.TextDomain;
import org.ecoinformatics.datamanager.parser.TextWidthFixedDataFormat;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This is plugin Parser which parses EML 2.0.0 metadata files to 
 * get the metadata information which decribes data file.
 * 
 * Note that the term "generic" is misleading in that a generic document
 * needs to have an EML-compliant dataset element somewhere within it.
 * This class simply allows more general forms of schema to be parsed. 
 * 
 * @author tao
 * @author leinfelder (refactored to this form from orginal EML200Parser)
 */
public class GenericDataPackageParser implements DataPackageParserInterface
{
    /*
     * Class fields
     */

    // private static Log log;
    private static boolean isDebugging;
    private static final String ID = "id";
 
    /*static {
      log = LogFactory.getLog( 
                   "org.ecoinformatics.seek.datasource.eml.eml2.Eml200Parser" );
      isDebugging = log.isDebugEnabled();
    }*/
    
    /*
     * Instance fields
     */
    
    // previously these were constants, now member variables with defaults
    protected String packageIdPath = null;
    protected String tableEntityPath = null;
    protected String spatialRasterEntityPath = null;
    protected String spatialVectorEntityPath  = null;
    protected String storedProcedureEntityPath = null;
    protected String viewEntityPath = null;
    protected String otherEntityPath = null;
    
    protected String accessPath = null;
    protected String datasetTitlePath = null;
    protected String datasetAbstractPath = null;
    protected String entityAccessPath = null;
    
    //private Hashtable entityHash = new Hashtable();
    //private Hashtable fileHash = new Hashtable();
    private int numEntities = 0;
    //private int numRecords = -1;
    private Entity entityObject = null;
    //private DataTypeResolver dtr = DataTypeResolver.instanceOf();
    private int elementId = 0;
    //private boolean hasImageEntity = false;
    private int numberOfComplexFormats = 0;
    // Associates attributeList id values with attributeList objects
    private Hashtable<String, AttributeList> attributeListIdHash = 
                                     new Hashtable<String, AttributeList>();
    //private boolean hasMissingValue = false;
    private DataPackage emlDataPackage = null;
    private final String DEFAULT_RECORD_DELIMITER = "\\r\\n";
    
    /**
     * Default constructor - no custom xpath parameters
     */
    public GenericDataPackageParser() {
    	//sets the default path values for documents
		this.initDefaultXPaths();
    }
    
    /**
     * Constructor that accepts only the packageIdPath.
     * Allows packageId to be located anywhere in schema,
     * but assumes default (EML) placement of dataset
     * @param packageIdPath path expression specifying where to look for packageId
     */
    public GenericDataPackageParser(String packageIdPath) {
    	//sets the default path values for documents
    	this.initDefaultXPaths();
    	
    	//set the param
		this.packageIdPath = packageIdPath;
    }

    /**
     * Constructor that accepts xpath input strings 
     * for many more datapackage element locations
	 * @param packageIdPath
	 * @param tableEntityPath
	 * @param spatialRasterEntityPath
	 * @param spatialVectorEntityPath
	 * @param storedProcedureEntityPath
	 * @param viewEntityPath
	 * @param otherEntityPath
	 */
	public GenericDataPackageParser(String packageIdPath, String tableEntityPath,
			String spatialRasterEntityPath, String spatialVectorEntityPath,
			String storedProcedureEntityPath, String viewEntityPath,
			String otherEntityPath) {
		
		//set default so that caller can pass nulls for some params
		this.initDefaultXPaths();
		
		//set the paths that are provided (not null)
		if (packageIdPath != null) {
			this.packageIdPath = packageIdPath;
		}
		if (tableEntityPath != null) {
			this.tableEntityPath = tableEntityPath;
		}
		if (spatialRasterEntityPath != null) {	
			this.spatialRasterEntityPath = spatialRasterEntityPath;
		}
		if (spatialVectorEntityPath != null) {	
			this.spatialVectorEntityPath = spatialVectorEntityPath;
		}
		if (storedProcedureEntityPath != null) {	
			this.storedProcedureEntityPath = storedProcedureEntityPath;
		}
		if (viewEntityPath != null) {	
			this.viewEntityPath = viewEntityPath;
		}
		if (otherEntityPath != null) {	
			this.otherEntityPath = otherEntityPath;
		}	
	}

	/**
	 * sets the default xpath strings for locating datapackage elements
	 * note that root element can be anything with a packageId attribute
	 */
	private void initDefaultXPaths() {
    	//sets the default path values for documents
		packageIdPath = "//*/@packageId";
    tableEntityPath = "//dataset/dataTable";
		spatialRasterEntityPath = "//dataset/spatialRaster";
		spatialVectorEntityPath  = "//dataset/spatialVector";
		storedProcedureEntityPath = "//dataset/storedProcedure";
		viewEntityPath = "//dataset/view";
		otherEntityPath = "//dataset/otherEntity";
		
		accessPath = "//access";
    datasetTitlePath = "//dataset/title";
    datasetAbstractPath = "//dataset/abstract";
    entityAccessPath = "physical/distribution/access";
	}
	
	/**
     * Returns a hashtable of with the id of the entity as the key and the data
     * file id to which the entity refers as the value. This way, if you want to
     * know what data file goes with an entity, you can do a get on this hash
     * for the id of the entity. Note that the entity id is the XML entity id
     * from the generated input step, not the id of the entity file itself.
     * 
     * @return fileHash, a HashTable of entity ids mapped to data file ids
     */
    /*public Hashtable getDataFilesHash()
    {
        return fileHash;
    }*/
    
    
    /* (non-Javadoc)
	 * @see org.ecoinformatics.datamanager.parser.generic.GenericDatasetParserInterface#parse(org.xml.sax.InputSource)
	 */
    public void parse(InputSource source) throws Exception
    {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder();
        Document doc = builder.parse(source);
        parseDocument(doc);
    }

    
    /* (non-Javadoc)
	 * @see org.ecoinformatics.datamanager.parser.generic.GenericDatasetParserInterface#parse(java.io.InputStream)
	 */
    public void parse(InputStream is) throws Exception
    {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder();
        Document doc = builder.parse(is);
        parseDocument(doc);
    }
    
    
    /**
     * Parses the EML document. Now except dataTable, spatialRaster and 
     * spatialVector entities are added. 
     * 
     * @param doc  the Document object to be parsed
     */
    private void parseDocument(Document doc) throws Exception
    {
        NodeList dataTableEntities;
        NodeList spatialRasterEntities;
        NodeList spatialVectorEntities;
        NodeList otherEntities;
        NodeList viewEntities;
        CachedXPathAPI xpathapi = new CachedXPathAPI();
        String packageId = null;
        
        try {
          // process packageid
        	Node packageIdNode = xpathapi.selectSingleNode(doc, packageIdPath);
            
        	if (packageIdNode != null)
        	{
        	   //System.out.println("in packageIdNode is not null");
        	   packageId          = packageIdNode.getNodeValue();
        	}
        	
          emlDataPackage        = new DataPackage(packageId);
          
          String emlNamespace = parseEmlNamespace(doc);
          if (emlDataPackage != null) {
            emlDataPackage.setEmlNamespace(emlNamespace);
          }
          
          emlDataPackage.checkSchemaValid(doc, emlNamespace);
          emlDataPackage.checkParserValid(doc);
          emlDataPackage.checkSchemaValidDereferenced(doc, emlNamespace);
          
          String systemValue = parseSystemAttribute(doc);
          if (systemValue != null) {
            emlDataPackage.setSystem(systemValue);
          }
          
          int nKeywordElements = countElements(xpathapi, doc, "keyword");
          emlDataPackage.setNumberOfKeywordElements(nKeywordElements);
          int nMethodsElements = countElements(xpathapi, doc, "methods");
          emlDataPackage.setNumberOfMethodsElements(nMethodsElements);
          int nCoverageElements = countElements(xpathapi, doc, "coverage");
          emlDataPackage.setNumberOfCoverageElements(nCoverageElements);
          int nGeographicCoverageElements = countElements(xpathapi, doc, "geographicCoverage");
          emlDataPackage.setNumberOfGeographicCoverageElements(nGeographicCoverageElements);
          int nTaxonomicCoverageElements = countElements(xpathapi, doc, "taxonomicCoverage");
          emlDataPackage.setNumberOfTaxonomicCoverageElements(nTaxonomicCoverageElements);
          int nTemporalCoverageElements = countElements(xpathapi, doc, "temporalCoverage");
          emlDataPackage.setNumberOfTemporalCoverageElements(nTemporalCoverageElements);
            
            // now dataTable, spatialRaster and spatialVector are handled
            dataTableEntities              = xpathapi.selectNodeList(doc, tableEntityPath);
            spatialRasterEntities = 
                              xpathapi.selectNodeList(doc, spatialRasterEntityPath);
            spatialVectorEntities = 
                              xpathapi.selectNodeList(doc, spatialVectorEntityPath);
            otherEntities         = xpathapi.selectNodeList(doc, otherEntityPath);
            viewEntities          = xpathapi.selectNodeList(doc, viewEntityPath);
            
            
            // Store <access> XML block because some applications may need it
            Node accessNode = xpathapi.selectSingleNode(doc, accessPath);
            if (accessNode != null) {
              String accessXML = nodeToXmlString(accessNode);
              emlDataPackage.setAccessXML(accessXML);
            }
            
            // Store the dataset title
            Node datasetTitleNode = xpathapi.selectSingleNode(doc, datasetTitlePath);
            if (datasetTitleNode != null) {
              String titleText = datasetTitleNode.getTextContent();
              emlDataPackage.setTitle(titleText);
            }
            
            // Parse the dataset abstract text
            NodeList datasetAbstractNodeList = xpathapi.selectNodeList(doc, datasetAbstractPath);
            parseDatasetAbstract(datasetAbstractNodeList);
      
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(
                            "Error extracting entities from eml2.0.0 package.", e);
        }
        
        try {
            //log.debug("Processing entities");
            processEntities(xpathapi, dataTableEntities, tableEntityPath, packageId);
            //TODO: current we still treat them as TableEntity java object, 
            //in future we need add new SpatialRasterEntity and SpatialVector
            // object for them
            processEntities(xpathapi, 
                            spatialRasterEntities, 
                            spatialRasterEntityPath, packageId);
            processEntities(xpathapi, 
                            spatialVectorEntities, 
                            spatialVectorEntityPath, packageId);
            processEntities(xpathapi, otherEntities, otherEntityPath, packageId);
            processEntities(xpathapi, viewEntities, viewEntityPath, packageId);
            //log.debug("Done processing entities");
        } catch (Exception e) {
            throw new Exception("Error processing entities: " + e.getMessage(), e);
        }
    }

    
    /**
     * Returns a hashtable of entity names hashed to the entity description
     * metadata that goes with each entity.
     */
    /*public Hashtable getEntityHash()
    {
        return entityHash;
    }*/
    
    
    /* (non-Javadoc)
	 * @see org.ecoinformatics.datamanager.parser.generic.GenericDatasetParserInterface#getDataPackage()
	 */
    public DataPackage getDataPackage()
    {
    	return emlDataPackage;
    }

    
    /**
     * Gets the number of records in this dataItem.
     *
     * @param   entityId the id of the entity object to get the record count for
     * @return  the number of records in the entity object
     */
    /*public int getRecordCount(String entityId)
    {
        return ((Entity) entityHash.get(entityId)).getNumRecords();
    }*/

    
    /**
     * Gets the total number of entities in the data item collection that was
     * passed to this class when the object was created.
     * 
     * @return  the number of entities in the data item collection
     */
    /*public int getEntityCount()
    {
        return numEntities;
    }*/

    
    /**
     * Gets the number of attributes in the given entity.
     *
     * @param  entityId the id of the entity object that you want the attribute
     *         count for
     * @return the number of attributes in the entity
     */
    /*public int getAttributeCount(String entityId)
    {
        Attribute[] attArray = ((Entity) entityHash.get(entityId))
                        .getAttributes();
        return attArray.length;
    }*/
    
    
    /**
     * Boolean to determine whether the entity has a missing value declaration.
     * 
     * @return value of hasMissingValue, a boolean
     */
    /*public boolean hasMissingValue()
    {
    	return hasMissingValue;
    }*/
    
    
    /**
     * Method to get the boolean hasImageEntity. If the eml document has
     * SpatialRaster or SpatialVector entity, this variable should be true.
     * 
     * @return boolean, the value of the hasImageEntity field
     */
    /*public boolean getHasImageEntity()
    {
      return this.hasImageEntity;
      
    }*/
    
    
    /*
     * Parses the "xmlns:eml" attribute value from the
     * "eml:eml" element. This value indicates the version of
     * EML, e.g. "eml://ecoinformatics.org/eml-2.1.0"
     */
    private String parseEmlNamespace(Document doc) {
      String namespaceURI = null;
      
      if (doc != null) {
        NodeList docNodes = doc.getChildNodes();
      
        if (docNodes != null) {
          int len = docNodes.getLength();
          for (int i = 0; i < len; i++) {
            Node docNode = docNodes.item(i);
            String name = docNode.getNodeName();
          
            if (name!= null && name.equals("eml:eml")) {
              NamedNodeMap attributeMap = docNode.getAttributes();
              int mapLength = attributeMap.getLength();
              for (int m = 0; m < mapLength; m++) {
                Node attNode = attributeMap.item(m);
                String attNodeName = attNode.getNodeName();
                String attNodeValue = attNode.getNodeValue();
                if (attNodeName.equals("xmlns:eml")) {
                  namespaceURI = attNodeValue;
                }
              }
            }
          }
        }
      }

      return namespaceURI;
    }

    
    /*
     * Counts the number of elements with the specified elementName. This
     * is used to implement presence/absence quality checks while parsing EML 2.1.0
     * documents.
     */
    private int countElements(CachedXPathAPI xpathapi, Document doc, String elementName) {
      int nMethodsElements = 0;
      final String xPath = "//" + elementName;
      
      try {
        NodeList methodsList = xpathapi.selectNodeList(doc, xPath);        
        nMethodsElements = methodsList.getLength();
      }
      catch (TransformerException e) {
        System.err.println(
            "TransformerException while detecting 'methods' element: " + 
            e.getMessage());
      }
      
      return nMethodsElements;
    }

    
    /*
     * Parses the "@system" attribute value from the
     * "eml:eml" element.
     */
    private String parseSystemAttribute(Document doc) {
      String systemValue = null;
      
      if (doc != null) {
        NodeList docNodes = doc.getChildNodes();
      
        if (docNodes != null) {
          int len = docNodes.getLength();
          for (int i = 0; i < len; i++) {
            Node docNode = docNodes.item(i);
            String name = docNode.getNodeName();
          
            if (name!= null && name.equals("eml:eml")) {
              NamedNodeMap attributeMap = docNode.getAttributes();
              int mapLength = attributeMap.getLength();
              for (int m = 0; m < mapLength; m++) {
                Node attNode = attributeMap.item(m);
                String attNodeName = attNode.getNodeName();
                String attNodeValue = attNode.getNodeValue();
                if (attNodeName.equals("system")) {
                  systemValue = attNodeValue;
                }
              }
            }
          }
        }
      }

      return systemValue;
    }

    
    /**
     * Processes the attributeList element.
     * 
     * @param  xpathapi  XPath API
     * @param  attributeListNodeList   a NodeList
     * @param  xpath     the XPath path string to the data entity 
     * @param  entObj    the entity object whose attribute list is processed
     */
    private void processAttributeList(CachedXPathAPI xpathapi, 
                                      NodeList attributeListNodeList, 
                                      String xpath,
                                      Entity entObj) 
            throws Exception
    {
        AttributeList attributeList = new AttributeList();
        Node attributeListNode = attributeListNodeList.item(0);
        
        /*
         * It is allowable in EML to omit the attributeList for an
         * 'otherEntity' data entity.
         */
        if (attributeListNode == null) {
          if (xpath != null && xpath.equals(otherEntityPath)) {
            System.err.println(
                "No attributeList was specified for otherEntity '" +
                entObj.getName() + "'. This is allowable in EML."
                              );
            return;
          }
          else {
            throw new Exception(
                "No attributeList was specified for entity '" + 
                entObj.getName() + "'.");
          }
        }
        
        // Get attributeList element's id attribute
        NamedNodeMap attributeListNodeAttributes = 
            attributeListNode.getAttributes();
        String idString = null;
        
        if (attributeListNodeAttributes != null)
        {
          Node idNode = attributeListNodeAttributes.getNamedItem(ID);
          
          if (idNode != null)
          {
            idString = idNode.getNodeValue();
            attributeList.setId(idString);
            
        	  if (isDebugging) {
               //log.debug("The id value for the attributelist is " + idString);
        	  }
          }
        }
        
        NodeList attributeNodeList = 
          xpathapi.selectNodeList(attributeListNode, "attribute");
        NodeList referencesNodeList = 
          xpathapi.selectNodeList(attributeListNode, "references");
        
        if (attributeNodeList != null && 
            attributeNodeList.getLength() > 0) 
        {
            processAttributes(xpathapi, attributeNodeList, attributeList);
            
            if (idString != null)
            {
               attributeListIdHash.put(idString , attributeList);
            }
        }
        else if (referencesNodeList != null && 
                 referencesNodeList.getLength() > 0) 
        {
            // get the references id 
            Node referencesNode = referencesNodeList.item(0);
            
        	  if (isDebugging) {
                //log.debug("The reference node's name is "+
                //          referenceNode.getNodeName());
        	  }
            
            String referencesId = referencesNode.getFirstChild().getNodeValue();
            
        	  if (isDebugging) {
        		  //log.debug("the reference id is "+ referenceId);
        	  }
            
            attributeList = (AttributeList) attributeListIdHash.get(referencesId);
        }
        else
        {
       	    //log.debug(
            //    "The children name of attribute list couldn't be understood");
            throw new Exception(" couldn't be a child of attributeList");
        }
          
        if (!entityObject.isSimpleDelimited())
        {
           int numberOfAttributes = attributeList.getAttributes().length;
           
           if (numberOfAttributes != numberOfComplexFormats || 
                (
                  (numberOfAttributes == numberOfComplexFormats) && 
                  (numberOfComplexFormats == 0)
                )
              )
           {
               throw new Exception("Complex format elements should have " +
                                   "same number as attribute number");
           }
           else
           {
               //entityObject.setDataFormatArray(formatArray);
           }
        }
       
        entityObject.setAttributeList(attributeList);
    }

    
    /**
     * Processes the attributes in an attribute list. Called by
     * processAttributeList().
     * 
     * @param  xpathapi           the XPath API
     * @param  attributesNodeList a node list
     * @param  attributeList      an AttributeList object
     */
    private void processAttributes(CachedXPathAPI xpathapi, 
                                   NodeList attributesNodeList, 
                                   AttributeList attributeList)
            throws Exception
    {
        int attributesNodeListLength = attributesNodeList.getLength();
        
        // Process each attribute
        for (int i = 0; i < attributesNodeListLength; i++) {
            Node attributeNode = attributesNodeList.item(i);
            NodeList attributeNodeChildren = attributeNode.getChildNodes();
            //NamedNodeMap attAttributes = att.getAttributes();

            String attName = "";
            String attLabel = "";
            String attDefinition = "";
            String attUnit = "";
            String attUnitType = "";
            String attMeasurementScale = "";
            String attPrecision = "";
            Domain domain = null;
            String id = null;
            Vector missingValueCodeVector = new Vector();
            double numberPrecision = 0;
            ArrayList<StorageType> storageTypeArray = 
                new ArrayList<StorageType>();
            
            // get attribute id
            NamedNodeMap attributeNodeAttributesMap = 
                attributeNode.getAttributes();
            
            if (attributeNodeAttributesMap != null)
            {
                Node idNode =  attributeNodeAttributesMap.getNamedItem(ID);
                  
                if (idNode != null)
                {
                    id = idNode.getNodeValue();
                }
            }
            
            elementId++;

            for (int j = 0; j < attributeNodeChildren.getLength(); j++) {
                Node childNode = attributeNodeChildren.item(j);
                String childNodeName = childNode.getNodeName();
                String childNodeValue = childNode.getFirstChild() == null ? null: childNode.getFirstChild().getNodeValue();
                childNodeValue = childNodeValue == null ? childNodeValue : childNodeValue.trim();
                if (childNodeName.equals("attributeName")) {
                	if (childNodeValue != null) {
	                    attName = childNodeValue.replace('.', '_');
                	}
                } 
                else if (childNodeName.equals("attributeLabel")) {
                    attLabel = childNodeValue;
                } 
                else if (childNodeName.equals("attributeDefinition")) {
                    attDefinition = childNodeValue;
                }
                // Process storageType elements
                else if (childNodeName.equals("storageType")) {
                  String storageTypeTextValue = childNodeValue;
                  NamedNodeMap storageTypeAttributesMap = childNode.getAttributes();
                  StorageType storageType;
                  String typeSystem = "";
                  Node typeSystemNode = null;
                  
                  // Determine whether the typeSystem attribute was specified
                  if (storageTypeAttributesMap != null) {
                      typeSystemNode =  
                          storageTypeAttributesMap.getNamedItem(typeSystem);
                        
                      if (typeSystemNode != null) {
                          typeSystem = typeSystemNode.getNodeValue();
                      }
                  }
                                
                  // Use the appropriate StorageType constructor depending on 
                  // whether the 'typeSystem' attribute was specified
                  if (!typeSystem.equals("")) {
                      storageType = new StorageType(storageTypeTextValue, typeSystem);
                  }
                  else {
                      storageType = new StorageType(storageTypeTextValue);
                  }            
                  
                  storageTypeArray.add(storageType);
                }
                else if (childNodeName.equals("measurementScale")) {
                    //unit is tricky because it can be custom or standard
                    //Vector info = new Vector();
                    //int domainType = Domain.DOM_NONE;
                    NodeList measurementScaleChildNodes = childNode.getChildNodes();
                    
                    for (int k = 0; k < measurementScaleChildNodes.getLength(); k++) {
                        Node measurementScaleChildNode = 
                            measurementScaleChildNodes.item(k);
                        String measurementScaleChildNodeName = 
                            measurementScaleChildNode.getNodeName();
                        
                        if (measurementScaleChildNodeName.equals("interval") || 
                            measurementScaleChildNodeName.equals("ratio")
                           ) {
                            String numberType = null;
                            String min = "", max = "";
                            Node standardUnitNode = 
                              xpathapi.selectSingleNode(measurementScaleChildNode,
                                                        "unit/standardUnit");
                            Node customUnitNode = 
                              xpathapi.selectSingleNode(measurementScaleChildNode,
                                                        "unit/customUnit");
                            
                            if (standardUnitNode != null) {
                                attUnit = standardUnitNode.getFirstChild().getNodeValue();
                                attUnitType = Attribute.STANDARDUNIT;
                            } else if (customUnitNode != null) {
                                attUnit = customUnitNode.getFirstChild().getNodeValue();
                                attUnitType = Attribute.CUSTOMUNIT;
                            } else {
                                System.err.println("Unable to determine attribute unit.");
                            }
                            
                            Node precisionNode = 
                                xpathapi.selectSingleNode(measurementScaleChildNode,
                                                          "precision");
                            
                            if (precisionNode != null) {
                                // precision is optional in EML201 so if it is
                                // not provided, the attPrecision will be the
                                // empty string
                                attPrecision = precisionNode.getFirstChild()
                                                        .getNodeValue();
                                numberPrecision = 
                                       (new Double(attPrecision)).doubleValue();
                                
                            }
                            
                            Node numericDomainNode = 
                                xpathapi.selectSingleNode(measurementScaleChildNode,
                                                          "numericDomain");
                            NodeList numericDomainChildNodes = 
                                numericDomainNode.getChildNodes();
                            
                            for (int index = 0; 
                                 index < numericDomainChildNodes.getLength(); 
                                 index++)
                            {
                                String numericDomainChildNodeName = 
                                    numericDomainChildNodes.item(index).getNodeName();
                                
                                if (numericDomainChildNodeName.equals("numberType"))
                                {
                                    // Got number type
                                    numberType = 
                                        numericDomainChildNodes.item(index)
                                                               .getFirstChild()
                                                               .getNodeValue();
                                    
                                	  if (isDebugging) {
                                      //log.debug("The number type is "+ numberType);
                                	  }
                                }
                                else if (numericDomainChildNodeName.equals("boundsGroup"))
                                {
                                    // Got bounds group
                                    NodeList boundsNodeList = 
                                        xpathapi.selectNodeList(numericDomainNode, 
                                                                "./bounds");
                                    
                                    for (i = 0; i < boundsNodeList.getLength(); i++)
                                    {
                                        NodeList aNodeList;
                                        Node boundsNode;

                                        //String exclMin = null, exclMax = null;
                                        try
                                        {
                                            aNodeList = xpathapi.selectNodeList(
                                                    boundsNodeList.item(i),
                                                    "./minimum");
                                            boundsNode = aNodeList.item(0);
                                            min = boundsNode.getFirstChild()
                                                    .getNodeValue();
                                            /*exclMin = bound.getAttributes()
                                                    .getNamedItem("exclusive")
                                                    .getNodeValue();*/
                                            aNodeList = xpathapi.selectNodeList(
                                                    boundsNodeList.item(0),
                                                    "./maximum");
                                            boundsNode = aNodeList.item(0);
                                            max = boundsNode.getFirstChild()
                                                    .getNodeValue();
                                            /*exclMax = bound.getAttributes()
                                                    .getNamedItem("exclusive")
                                                    .getNodeValue();*/
                                        }
                                        catch (Exception e)
                                        {
                                        	//log.debug("Error in handle bound ", e);
                                        }
                                     }
                                 }
                            }
                            
                            Double minNum = null;
                            Double maxNum = null;
                            
                            if (!min.trim().equals(""))
                            {
                                minNum = new Double(min);
                            }
                            
                            if (!max.trim().equals(""))
                            {
                            	maxNum = new Double(max);
                            }
                            
                            NumericDomain numericDomain = 
                                  new NumericDomain(numberType, minNum, maxNum);
                            numericDomain.setPrecision(numberPrecision);
                            domain = numericDomain;
                           
                        } else if (measurementScaleChildNodeName.equals("nominal")
                                || measurementScaleChildNodeName.equals("ordinal")) {
                            NodeList nonNumericDomainChildNodes = 
                              xpathapi.selectSingleNode(measurementScaleChildNode,
                                                        "nonNumericDomain")
                                      .getChildNodes();
                            
                            for (int m = 0; 
                                 m < nonNumericDomainChildNodes.getLength(); 
                                 m++)
                            {
                                Node nonNumericDomainChildNode = 
                                    nonNumericDomainChildNodes.item(m);
                                String nonNumericDomainChildNodeName = 
                                    nonNumericDomainChildNode.getNodeName();
                                
                                if (nonNumericDomainChildNodeName.
                                        equals("textDomain")) {
                                    TextDomain textDomain = new TextDomain();
                                    NodeList definitionNodeList = 
                                      xpathapi.selectNodeList(
                                                  nonNumericDomainChildNode, 
                                                  "./definition");
                                    Node defintionNode = definitionNodeList.item(0);
                                    String definition = 
                                    	defintionNode.getFirstChild() == null ? null: defintionNode.getFirstChild().getNodeValue();
                                	
                                    if(isDebugging) {
                                      //log.debug(
                                      // "The definition value is "+definition);
                                	  }
                                    
                                    textDomain.setDefinition(definition);
                                    NodeList patternNodeList = 
                                      xpathapi.selectNodeList(
                                          nonNumericDomainChildNode,
                                          "./pattern");
                                    
                                    String[] patternList = 
                                      new String[patternNodeList.getLength()];
                                    
                                    for (int l = 0; 
                                         l < patternNodeList.getLength(); 
                                         l++) {
                                      patternList[l] = patternNodeList.item(l).
                                                       getFirstChild().
                                                       getNodeValue();
                                    }
                                    
                                    if (patternList.length > 0)
                                    {
                                      textDomain.setPattern(patternList);
                                    }
                                    
                                    domain = textDomain;
                                   
                                } else if (nonNumericDomainChildNodeName.
                                           equals("enumeratedDomain")) {
                                    EnumeratedDomain enumeratedDomain = 
                                        new EnumeratedDomain();
                                    Vector info = new Vector();
                                    
                                    NodeList codeDefinitionNodeList = 
                                      xpathapi.selectNodeList(
                                          nonNumericDomainChildNode,
                                          "./codeDefinition");
                                    
                                    for (int l = 0; 
                                         l < codeDefinitionNodeList.getLength(); 
                                         l++) {
                                        info.add(codeDefinitionNodeList.item(l).
                                                 getFirstChild().getNodeValue());
                                    }
                                    
                                    enumeratedDomain.setInfo(info);
                                    domain = enumeratedDomain;
                                }
                            }
                        } else if (measurementScaleChildNodeName.
                                       equalsIgnoreCase("datetime")) {
                            DateTimeDomain date = new DateTimeDomain();
                            String formatString = 
                              (xpathapi.selectSingleNode(measurementScaleChildNode,
                                                         "./formatString")).
                                          getFirstChild().
                                          getNodeValue();
                            
                        	  if (isDebugging) {
                        	    //log.debug(
                              //          "The format string in date time is " 
                              //          + formatString);
                        	  }
                            date.setFormatString(formatString);
                            domain = date;
                        }
                    }
                }
                else if (childNodeName.equals("missingValueCode"))
                {
               		  //log.debug("in missingValueCode");
                    NodeList missingValueCodeChildNodes = 
                        childNode.getChildNodes();
                    
                    for (int k = 0; 
                         k < missingValueCodeChildNodes.getLength(); 
                         k++) 
                    {
                        Node missingValueCodeChildNode = 
                            missingValueCodeChildNodes.item(k);
                        String missingValueCodeChildNodeName = 
                            missingValueCodeChildNode.getNodeName();
                        
                        if (missingValueCodeChildNodeName.equals("code"))
                        {
                            Node missingValueCodeTextNode = 
                                missingValueCodeChildNode.getFirstChild();
                            
                            if (missingValueCodeTextNode != null)
                            {
	                            String missingValueCode = 
                                  missingValueCodeTextNode.getNodeValue();
                                
	                        	  if(isDebugging) {
	                        	    //log.debug("the missing code is "+missingCode);
	                        	  }
                                
	                            missingValueCodeVector.add(missingValueCode);
	                            //hasMissingValue = true;
                            }
                        }
                    }
                }
            }
            
            /******************************************************
             * need to use domain type to replace data type
             ******************************************************/
            /*String resolvedType = null;
            //DataType dataType = domain.getDataType();
            //resolvedType = dataType.getName();
        	if(isDebugging) {
        		//log.debug("The final type is " + resolvedType);
        	}*/
           
            Attribute attObj = 
              new Attribute(id, attName, attLabel,
                            attDefinition, attUnit, attUnitType,
                            attMeasurementScale, domain);
            
            // Add storageType elements to the Attribute object 
            // if any were parsed in the EML
            for (StorageType storageType : storageTypeArray) {
                attObj.addStorageType(storageType);
            }
            
            // Add missing value code into attribute
            for (int k = 0; k < missingValueCodeVector.size(); k++)
            {
                String missingValueCode = 
                       (String) missingValueCodeVector.elementAt(k);
            	  if (isDebugging) {
            		  //log.debug("the mssing value code " + missingCodeValue + 
                  //          " was added to attribute");
            	  }
                
                attObj.addMissingValueCode(missingValueCode);
            }
            
            attributeList.add(attObj);
        }
    }
    

    /**
     * Pulls the entity information out of the XML and stores it in a hash table.
     */
    private void processEntities(CachedXPathAPI xpathapi, 
                                 NodeList entitiesNodeList, 
                                 String xpath, 
                                 String packageId)
            throws SAXException,
                   javax.xml.transform.TransformerException, 
                   Exception
    { 
        // Make sure that entities is not null
        if (entitiesNodeList == null)
        {
          return;
        }
        
        int entityNodeListLength = entitiesNodeList.getLength();
        numEntities = numEntities + entityNodeListLength;
        String entityAccessXML = null;
        String entityName = "";
        String entityDescription = "";
        String entityOrientation = "";
        String entityCaseSensitive = "";
        String onlineUrl = "";
        String onlineUrlFunction = null;
        String format = null;
        int numHeaderLines = 0;
        int numFooterLines = 0;
        String fieldDelimiter = null;
        String recordDelimiter = DEFAULT_RECORD_DELIMITER;
        String metadataRecordDelimiter = null; // The record delimiter specified in the metadata
        String compressionMethod = "";
        String encodingMethod = "";
        String quoteCharacter = null;
        String literalCharacter = null;
        TextComplexDataFormat[] formatArray = null;
         
        for (int i = 0; i < entityNodeListLength; i++) {
            
            String entityNumberOfRecords = "-1";
            boolean hasDistributionOnline = false;
            boolean hasDistributionOffline = false;
            boolean hasDistributionInline = false;
            boolean isOtherEntity = false;
            boolean isImageEntity   = false;
            boolean isGZipDataFile  = false;
            boolean isZipDataFile   = false;
            boolean isTarDataFile   = false;
            boolean isSimpleDelimited = true;
            boolean isTextFixed = false;
            boolean isCollapseDelimiters = false;

            if (xpath != null) {
              if (xpath.equals(spatialRasterEntityPath) || 
                  xpath.equals(spatialVectorEntityPath)) {
                isImageEntity = true;
              }
              else if (xpath.equals(otherEntityPath)) {
                isOtherEntity = true;
              }
            }
            
             //go through the entities and put the information into the hash.
            elementId++;
            Node entityNode = entitiesNodeList.item(i);
            String id = null;
            NamedNodeMap entityNodeAttributes = entityNode.getAttributes();
            
            if (entityNodeAttributes != null) {        
                Node idNode = entityNodeAttributes.getNamedItem(ID);
                
                if (idNode != null)
                {
                   id = idNode.getNodeValue();
                }
            }
            
            NodeList entityNodeChildren = entityNode.getChildNodes();
            
            for (int j = 0; j < entityNodeChildren.getLength(); j++) {
                Node childNode = entityNodeChildren.item(j);
                String childName = childNode.getNodeName();
                String childValue = childNode.getFirstChild() == null ? null: childNode.getFirstChild().getNodeValue();

                if (childName.equals("entityName")) {
                    entityName = childValue;
                } else if (childName.equals("entityDescription")) {
                    entityDescription = childValue;
                } else if (childName.equals("caseSensitive")) {
                    entityCaseSensitive = childValue;
                } else if (childName.equals("numberOfRecords")) {
                    entityNumberOfRecords = childValue;
                    /*numRecords = (new Integer(entityNumberOfRecords))
                                    .intValue();*/
                }                
               
            }
            
            NodeList attributeOrientationNodeList = 
                xpathapi.selectNodeList(
                    entityNode,
                    "physical/dataFormat/textFormat/attributeOrientation");
            
            if (attributeOrientationNodeList != null && 
                attributeOrientationNodeList.getLength() > 0) {
                entityOrientation = attributeOrientationNodeList.
                                      item(0).getFirstChild().getNodeValue();

               }

            NodeList numHeaderLinesNodeList = xpathapi.selectNodeList(entityNode,
                       "physical/dataFormat/textFormat/numHeaderLines");
            
            if ((numHeaderLinesNodeList != null) && 
                (numHeaderLinesNodeList.getLength() > 0)
               ) {
                Node numHeaderLinesNode = numHeaderLinesNodeList.item(0);
                   
                if (numHeaderLinesNode != null) {
                    String numHeaderLinesStr = 
                        numHeaderLinesNode.getFirstChild().getNodeValue();
                    numHeaderLines = (new Integer(numHeaderLinesStr.trim())).intValue();
                }
            }
            
            NodeList numFooterLinesNodeList = 
              xpathapi.selectNodeList(
                  entityNode,
                  "physical/dataFormat/textFormat/numFooterLines"
                                     );
            
            if ((numFooterLinesNodeList != null) && 
                (numFooterLinesNodeList.getLength() > 0)
               ) {
                Node numFooterLinesNode = numFooterLinesNodeList.item(0);
            
                if (numFooterLinesNode != null) {
                    String numFooterLinesStr = 
                        numFooterLinesNode.getFirstChild().getNodeValue();
                    numFooterLines = 
                        (new Integer(numFooterLinesStr.trim())).intValue();
                }
            }
           
           /*
            * Simple delimited data file
            */
           NodeList fieldDelimiterNodeList = 
               xpathapi.selectNodeList(
                 entityNode,
                 "physical/dataFormat/textFormat/simpleDelimited/fieldDelimiter"
                                      );
           
           if (fieldDelimiterNodeList != null && 
               (fieldDelimiterNodeList.getLength() > 0)
              ) {
             Node fieldDelimiterNode = fieldDelimiterNodeList.item(0);
             if (fieldDelimiterNode != null) {
               Node firstChild = fieldDelimiterNode.getFirstChild();
               if (firstChild != null) {
                 fieldDelimiter = firstChild.getNodeValue();
               }
             }
           }
           
           NodeList collapseDelimitersNodeList = 
             xpathapi.selectNodeList(entityNode,
               "physical/dataFormat/textFormat/simpleDelimited/collapseDelimiters");
           
           if (collapseDelimitersNodeList != null && 
               collapseDelimitersNodeList.getLength() > 0
              ) {
             
               String collapseDelimiters = 
                   collapseDelimitersNodeList.item(0).getFirstChild().getNodeValue();
               
               if (collapseDelimiters.equalsIgnoreCase("yes"))
               {
                   isCollapseDelimiters = true;
               } 
           }
           
           NodeList quoteCharacterNodeList = 
             xpathapi.selectNodeList(entityNode,
               "physical/dataFormat/textFormat/simpleDelimited/quoteCharacter");
           
           if (quoteCharacterNodeList != null && 
        		   quoteCharacterNodeList.getLength() > 0
        		  ) {
                quoteCharacter = 
                  quoteCharacterNodeList.item(0).getFirstChild().getNodeValue();
           }
           
           NodeList literalCharacterNodeList = 
             xpathapi.selectNodeList(entityNode,
             "physical/dataFormat/textFormat/simpleDelimited/literalCharacter");
           
           if (literalCharacterNodeList != null && 
        		   literalCharacterNodeList.getLength() > 0
        		  ) {
                literalCharacter = 
                  literalCharacterNodeList.item(0).getFirstChild().getNodeValue(); 
           } // End simple delimited data file
           
           /*
            *  Complex format data file
            */
           NodeList complexNodeList = 
             xpathapi.selectNodeList(entityNode,
                                     "physical/dataFormat/textFormat/complex");
           
           if (complexNodeList != null && 
               complexNodeList.getLength() > 0
              ) {
        	   //log.debug("in handle complex text data format");
             isSimpleDelimited = false;
             Node complexNode = complexNodeList.item(0);
             NodeList complexChildNodes = complexNode.getChildNodes();
             int complexChildNodesLength = complexChildNodes.getLength();
             Vector formatVector = new Vector();
             
             for (int k = 0; k < complexChildNodesLength; k++)
             {
                 Node complexChildNode = complexChildNodes.item(k);
                 
                 /*
                  * complex, textFixed
                  */
                 if (complexChildNode != null && 
                     complexChildNode.getNodeName().equals("textFixed")
                    )
                 {
                     TextWidthFixedDataFormat textWidthFixedDataFormat = 
                         handleTextFixedDataFormatNode(complexChildNode);
                     
                     if (textWidthFixedDataFormat != null)
                     {
                        formatVector.add(textWidthFixedDataFormat);
                        isTextFixed = true;
                        //complexFormatsNumber++;
                     }
                 }
                 /*
                  * complex, textDelimited
                  */
                 else if (complexChildNode != null && 
                          complexChildNode.getNodeName().equals("textDelimited")
                         )
                 {
                     TextDelimitedDataFormat textDelimitedDataFormat = 
                         handleComplexDelimitedDataFormatNode(complexChildNode);
                     
                     if (textDelimitedDataFormat != null)
                     {
                         formatVector.add(textDelimitedDataFormat);
                         //complexFormatsNumber++;
                     }
                 }
             }
             
             // Transfer vector to array
             numberOfComplexFormats = formatVector.size();
             formatArray = new TextComplexDataFormat[numberOfComplexFormats];
             for (int j = 0; j < numberOfComplexFormats; j++)
             {
                 formatArray[j] =
                               (TextComplexDataFormat)formatVector.elementAt(j);
             }
           } // End complex format data file
           
           NodeList recordDelimiterNodeList = 
               xpathapi.selectNodeList(entityNode,
                             "physical/dataFormat/textFormat/recordDelimiter");
           
           if ((recordDelimiterNodeList != null) && 
               (recordDelimiterNodeList.getLength() > 0)
              ) {
             Node firstNode = recordDelimiterNodeList.item(0);
             if (firstNode != null) {
               Node firstChild = firstNode.getFirstChild();
               if (firstChild != null) {
                 metadataRecordDelimiter = firstChild.getNodeValue();
                 recordDelimiter = metadataRecordDelimiter;
               }
             }
           }
           
           // Store the entity access XML since some applications may need it
           Node entityAccessNode = xpathapi.selectSingleNode(
                                              entityNode, 
                                              entityAccessPath);
           if (entityAccessNode != null) {
             entityAccessXML = nodeToXmlString(entityAccessNode);
           }
           
           NodeList onlineNodeList = xpathapi.selectNodeList(
                                              entityNode,
                                              "physical/distribution/online");
           NodeList offlineNodeList = xpathapi.selectNodeList(
                                              entityNode,
                                              "physical/distribution/offline");
           NodeList inlineNodeList = xpathapi.selectNodeList(
                                              entityNode,
                                              "physical/distribution/inline");
           if (onlineNodeList != null && onlineNodeList.getLength() > 0) {
             hasDistributionOnline = true;
           }
           if (offlineNodeList != null && offlineNodeList.getLength() > 0) {
             hasDistributionOffline = true;
           }
           if (inlineNodeList != null && inlineNodeList.getLength() > 0) {
             hasDistributionInline = true;
           }
           
           
           // Get the distribution information
           NodeList urlNodeList = xpathapi.selectNodeList(entityNode,
                                           "physical/distribution/online/url");
           
           if (urlNodeList != null && urlNodeList.getLength() > 0) {
             int len = urlNodeList.getLength();
             for (int j = 0; j < len; j++) {
               Node urlNode = urlNodeList.item(j);
               String urlText = urlNode.getTextContent();
               String functionText = null;
               NamedNodeMap urlAttributes = urlNode.getAttributes();
               int nAttributes = urlAttributes.getLength();
               for (int k = 0; k < nAttributes; k++) {
                 Node attributeNode = urlAttributes.item(k);
                 String nodeName = attributeNode.getNodeName();
                 if (nodeName.equals("function")) {
                   functionText = attributeNode.getNodeValue();             
                 }
               }
               
               /*
                * Unless this URL has attribute function="information", 
                * assign it as the download URL for this entity and stop
                * processing any additional distribution URLs.
                */
               if (functionText == null ||
                   !functionText.equalsIgnoreCase("information")) {
                 onlineUrl = urlText;
                 onlineUrlFunction = functionText;
                 break;
               }
             }
           }
                      
           /**
            * Determine file format (mime)
            * Note: this could be better fleshed out in cases where the delimiter is known
            * 
            * physical/dataFormat/textFormat
            * physical/dataFormat/binaryRasterFormat
            * physical/dataFormat/externallyDefinedFormat/formatName
            */
           NodeList formatNodeList = xpathapi.selectNodeList(entityNode, "physical/dataFormat/externallyDefinedFormat/formatName");
           if (formatNodeList != null && formatNodeList.getLength() > 0) {
        	   format = formatNodeList.item(0).getFirstChild().getNodeValue();
           } else {
        	   // try binary raster
        	   formatNodeList = xpathapi.selectNodeList(entityNode, "physical/dataFormat/binaryRasterFormat");
               if (formatNodeList != null && formatNodeList.getLength() > 0) {
            	   format = "application/octet-stream";
               } else {
            	   formatNodeList = xpathapi.selectNodeList(entityNode, "physical/dataFormat/textFormat");
                   if (formatNodeList != null && formatNodeList.getLength() > 0) {
                	   format = "text/plain";
                   }
                   if (isSimpleDelimited) {
                	   format = "text/csv";
                   }
               }   
           }

           // Get the compressionMethod information
           NodeList compressionMethodNodeList = 
             xpathapi.selectNodeList(entityNode, "physical/compressionMethod");
           
           if (compressionMethodNodeList != null && 
               compressionMethodNodeList.getLength() >0
              ) {
              compressionMethod = 
                compressionMethodNodeList.item(0).getFirstChild().getNodeValue();
              
          	  if (isDebugging) {
                  //log.debug("Compression method is "+compressionMethod);
        	    }
              
              if (compressionMethod != null && 
                  compressionMethod.equals(Entity.GZIP))
              {
                isGZipDataFile = true;
              }
              else if (compressionMethod != null && 
                       compressionMethod.equals(Entity.ZIP))
              {
                isZipDataFile = true;
              }
          }
          
          // Get encoding method info (mainly for tar file)
          NodeList encodingMethodNodeList = 
              xpathapi.selectNodeList(entityNode, "physical/encodingMethod");
          
          if (encodingMethodNodeList != null && 
              encodingMethodNodeList.getLength() > 0
             ) {
              encodingMethod = 
                encodingMethodNodeList.item(0).getFirstChild().getNodeValue();
            
        	    if (isDebugging) {
        		      //log.debug("encoding method is "+encodingMethod);
        	    }
            
              if (encodingMethod != null && encodingMethod.equals(Entity.TAR))
              {
                  isTarDataFile = true;
              }
          }

          if (entityOrientation.trim().equals("column")) {
              entityOrientation = Entity.COLUMNMAJOR;
          } else {
              entityOrientation = Entity.ROWMAJOR;
          }

          if (entityCaseSensitive.equals("yes")) {
              entityCaseSensitive = "true";
          } else {
              entityCaseSensitive = "false";
          }
          
          System.err.println(String.format("Package ID: %s  Entity: %s", packageId, entityName));

          entityObject = new Entity(id, 
        		  					entityName == null ? null: entityName.trim(),
                                    entityDescription == null ? null: entityDescription.trim(), 
                                    new Boolean(entityCaseSensitive),
                                    entityOrientation, 
                                    new Integer(entityNumberOfRecords).
                                                           intValue());
          
          entityObject.setNumHeaderLines(numHeaderLines);
          entityObject.setNumFooterLines(numFooterLines);
          entityObject.setSimpleDelimited(isSimpleDelimited);
          entityObject.setTextFixed(isTextFixed);
            
          if (quoteCharacter != null)
          {
        	  entityObject.setQuoteCharacter(quoteCharacter);
          }
          
          if (literalCharacter != null)
          {
        	  entityObject.setLiteralCharacter(literalCharacter);
          }
          
          entityObject.setCollapseDelimiters(isCollapseDelimiters);         
          entityObject.setRecordDelimiter(recordDelimiter);
          entityObject.setURL(onlineUrl);
          entityObject.setURLFunction(onlineUrlFunction);
          entityObject.setDataFormat(format);
          entityObject.setCompressionMethod(compressionMethod);
          entityObject.setIsImageEntity(isImageEntity);
          entityObject.setIsOtherEntity(isOtherEntity);
          entityObject.setHasGZipDataFile(isGZipDataFile);
          entityObject.setHasZipDataFile(isZipDataFile);
          entityObject.setHasTarDataFile(isTarDataFile);
          entityObject.setPackageId(packageId);
          entityObject.setHasDistributionOnline(hasDistributionOnline);
          entityObject.setHasDistributionOffline(hasDistributionOffline);
          entityObject.setHasDistributionInline(hasDistributionInline);
          entityObject.setEntityAccessXML(entityAccessXML);
          entityObject.setFieldDelimiter(fieldDelimiter);
          entityObject.setMetadataRecordDelimiter(metadataRecordDelimiter);
          
          try {
              NodeList attributeListNodeList = 
                  xpathapi.selectNodeList(entityNode, "attributeList");
              processAttributeList(xpathapi, attributeListNodeList, xpath, entityObject);
              entityObject.setDataFormatArray(formatArray);  
          } catch (Exception e) {
                throw new Exception("Error parsing attributes: " + 
                                    e.getMessage(), e);
          }
          
          //entityHash.put(Integer.toString(elementId), entityObject);
          emlDataPackage.add(entityObject);
          //fileHash.put(elementId, onlineUrl); 
        } // end for loop
        
    }
    
    
    /**
     * This method will digest a text fixed data format node and return
     * a TextFixedDataFormat object.
     * 
     * @param  node the Node object to be processed
     */
    private TextWidthFixedDataFormat handleTextFixedDataFormatNode(Node node) 
            throws Exception
    {
       TextWidthFixedDataFormat textWidthFixedDataFormat = null;
       
       if (node == null)
       {
           return textWidthFixedDataFormat;
       }
       
       NodeList childNodes = node.getChildNodes();
       int length = childNodes.getLength();
       
       for (int i = 0; i < length; i++)
       {
           Node childNode = childNodes.item(i);
           String elementName = childNode.getNodeName();
           
           if (elementName != null && elementName.equals("fieldWidth"))
           {
              String fieldWidthStr = childNode.getFirstChild().getNodeValue();          
              int fieldWidth = (new Integer(fieldWidthStr)).intValue();
              
          	  if (isDebugging) {
        		    //log.debug("The filed width for fix width in eml is "
                //          + fieldWidth);
        	    }
              
              textWidthFixedDataFormat = new TextWidthFixedDataFormat(fieldWidth);
           }
           else if (elementName != null && 
                    elementName.equals("fieldStartColumn") && 
                    textWidthFixedDataFormat != null)
           {
               String startColumnStr = childNode.getFirstChild().getNodeValue();
               int startColumn  = (new Integer(startColumnStr)).intValue();
               
           	   if (isDebugging) {
        		     //log.debug("The start column is " + startColumn);
        	     }
               
               textWidthFixedDataFormat.setFieldStartColumn(startColumn);
           }
           else if (elementName != null && 
                    elementName.equals("lineNumber") && 
                    textWidthFixedDataFormat != null)
           {
               String lineNumberStr = childNode.getFirstChild().getNodeValue();
               int lineNumber  = (new Integer(lineNumberStr)).intValue();
               
           	   if (isDebugging) {
        		     //log.debug("The start column is " + lineNumber);
        	     }
               
               textWidthFixedDataFormat.setLineNumber(lineNumber);
           }
       }
       
       return textWidthFixedDataFormat;
    }
    
    
    /*
     * This method will digest a complex delimited data format node 
     * and return a TextDelimitedDataFormat object.
     */
    private TextDelimitedDataFormat handleComplexDelimitedDataFormatNode(
                                                                      Node node)
            throws Exception
    {
       TextDelimitedDataFormat textDelimitedDataFormat = null;
       
       if (node == null)
       {
           return textDelimitedDataFormat;
       }
       
       NodeList childNodes = node.getChildNodes();
       int length = childNodes.getLength();
       Vector quoteList = new Vector();
       
       for (int i = 0; i < length; i++)
       {
           Node childNode = childNodes.item(i);
           String elementName = childNode.getNodeName();
           String fieldDelimiter = null;
           
           if (elementName != null && 
               elementName.equals("fieldDelimiter")
              ) {
             Node firstChild = childNode.getFirstChild();
             if (firstChild != null) {
               fieldDelimiter = firstChild.getNodeValue();
             }           
             textDelimitedDataFormat = new TextDelimitedDataFormat(fieldDelimiter);
           }
           else if (elementName != null && 
                    elementName.equals("lineNumber") && 
                    textDelimitedDataFormat != null)
           {
               String lineNumberStr = childNode.getFirstChild().getNodeValue();
               int lineNumber = (new Integer(lineNumberStr)).intValue();
               textDelimitedDataFormat.setLineNumber(lineNumber);
           }
           else if (elementName != null && 
                    elementName.equals("collapseDelimiters") && 
                    textDelimitedDataFormat != null)
           {
               String collapseDelimiters = 
                   childNode.getFirstChild().getNodeValue();
               textDelimitedDataFormat.
                   setCollapseDelimiters(collapseDelimiters);
           }
           else if (elementName != null && 
                    elementName.equals("quoteCharacter") && 
                    textDelimitedDataFormat != null)
           {
               String quoteCharacter = 
                   childNode.getFirstChild().getNodeValue();
               quoteList.add(quoteCharacter); 
           }
       } // end for loop
       
       // set up quoteList
       if (textDelimitedDataFormat != null)
       {
           int size = quoteList.size();
           String[] quoteCharacterArray = new String[size];
           
           for (int i = 0; i < size; i++)
           {
               quoteCharacterArray[i] = (String)quoteList.elementAt(i);
           }
           
           textDelimitedDataFormat.setQuoteCharacterArray(quoteCharacterArray);
       }
       
       return textDelimitedDataFormat;
    }
    
    /**
     * Returns an XML representation of the provided node.
     *  
     * @param node the node to be represented in XML.
     * 
     * @return a string containing an XML representation of the 
     * provided DOM node. 
     */
    public String nodeToXmlString(Node node) {
        
        try {
            
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            
            DOMSource source = new DOMSource(node);
            StreamResult result = new StreamResult(new StringWriter());

            t.transform(source, result);

            return result.getWriter().toString();

        } catch (TransformerException e) {
            throw new IllegalStateException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new IllegalStateException(e);
        }

    }
    
    
    /*
     * Parses the dataset abstract text content for purposes of quality reporting.
     */
    private void parseDatasetAbstract(NodeList datasetAbstractNodeList) {
      if (datasetAbstractNodeList != null) {
        StringBuffer stringBuffer = new StringBuffer("");
        for (int i = 0; i < datasetAbstractNodeList.getLength(); i++) {
          Node paraNode = datasetAbstractNodeList.item(i);
          String paraText = paraNode.getTextContent();
          stringBuffer.append(" " + paraText);
        }
        String abstractText = stringBuffer.toString();
        emlDataPackage.checkDatasetAbstract(abstractText);
      }      
    }
    
}
