/**
 *    '$RCSfile: Eml200Parser.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-08-26 23:52:42 $'
 *   '$Revision: 1.4 $'
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

package org.ecoinformatics.datamanager.parser.eml;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.apache.xpath.CachedXPathAPI;
//import org.kepler.objectmanager.data.DataType;
//import org.kepler.objectmanager.data.DataTypeResolver;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.DateTimeDomain;
import org.ecoinformatics.datamanager.parser.Domain;
import org.ecoinformatics.datamanager.parser.EnumeratedDomain;
import org.ecoinformatics.datamanager.parser.NumericDomain;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.Entity;
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
 * This plugin parses EML 2.0.0 metadata files
 */
public class Eml200Parser
{

    private static String NAMESPACE = "eml://ecoinformatics.org/eml-2.0.0";
    //private Hashtable entityHash = new Hashtable();
    //private Hashtable fileHash = new Hashtable();
    private int numEntities = 0;
    private int numRecords = -1;
    private Entity entityObject = null;
    //private DataTypeResolver dtr = DataTypeResolver.instanceOf();
    private int elementId = 0;
    //private boolean hasImageEntity = false;
    private int complexFormatsNumber = 0;
    private Hashtable attributeListHash = new Hashtable();
    private boolean hasMissingValue = false;
    private DataPackage emlDataPackage = new DataPackage();
    
    //private static Log log;
    private static boolean isDebugging;
    private static final String ID = "id";
   
	  
    /*static {
    	log = LogFactory.getLog( "org.ecoinformatics.seek.datasource.eml.eml2.Eml200Parser" );
    	isDebugging = log.isDebugEnabled();
    }*/
    
    //constants
    public static final String TABLEENTITY           = "//dataset/dataTable";
    public static final String SPATIALRASTERENTITY   = "//dataset/spatialRaster";
    public static final String SPATIALVECTORENTITY   = "//dataset/spatialVector";
    public static final String STOREDPROCEDUREENTITY = "//dataset/storedProcedure";
    public static final String VIEWENTITY            = "//dataset/view";
    public static final String OTHERENTITY           = "//dataset/otherEntity";
    /**
     * returns a hashtable of with the id of the entity as the key and the data
     * file id to which the entity refers as the value. This way, if you want to
     * know what data file goes with an entity, you can do a get on this hash
     * for the id of the entity. note that the entity id is the xml entity id
     * from the generated input step, not the id of the entity file itself.
     */
    /*public Hashtable getDataFilesHash()
    {
        return fileHash;
    }*/

    /**
     * parses the EML package using an InputSource
     */
    public void parse(InputSource source) throws Exception
    {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder();
        Document doc = builder.parse(source);
        parseDocument(doc);
    }

    /**
     * parses the EML package using an InputStream
     */
    public void parse(InputStream is) throws Exception
    {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder();
        Document doc = builder.parse(is);
        parseDocument(doc);
    }

    /*
     * parses the EML document. Now except dataTable, spatialRaster and 
     * spatialVector entities are added. 
     */
    private void parseDocument(Document doc) throws Exception
    {
        NodeList entities;
        NodeList spatialRasterEntities;
        NodeList spatialVectorEntities;
        NodeList otherEntities;
        NodeList viewEntities;
        CachedXPathAPI xpathapi = new CachedXPathAPI();
        try {
            // now dataTable, spatialRaster and spatialVector are handled
            entities              = xpathapi.selectNodeList(doc, TABLEENTITY);
            spatialRasterEntities = xpathapi.selectNodeList(doc, SPATIALRASTERENTITY);
            spatialVectorEntities = xpathapi.selectNodeList(doc, SPATIALVECTORENTITY);
            otherEntities         = xpathapi.selectNodeList(doc, OTHERENTITY);
            viewEntities          = xpathapi.selectNodeList(doc, VIEWENTITY);
            
            
        } catch (Exception e) {
            throw new Exception("Error extracting entities from eml2.0.0 package.");
        }
        
        try {
            //log.debug("Processing entities");
            processEntities(xpathapi, entities, TABLEENTITY);
            //TODO: current we still treat them as TableEntity java object, 
            //in future we need add new SpatialRasterEntity and SpatialVector
            // object for them
            processEntities(xpathapi, spatialRasterEntities, SPATIALRASTERENTITY);
            processEntities(xpathapi, spatialVectorEntities, SPATIALVECTORENTITY);
            processEntities(xpathapi, otherEntities, OTHERENTITY);
            processEntities(xpathapi, viewEntities, VIEWENTITY);
            //log.debug("Done processing entities");
        } catch (Exception e) {
            throw new Exception("Error processing entities: " + e.getMessage());
        }
    }

    /**
     * returns a hashtable of entity names hashed to the entity description
     * metadata that goes with each entity.
     */
    /*public Hashtable getEntityHash()
    {
        return entityHash;
    }*/
    
    /**
     * Method to get the data package metadata object
     */
    public DataPackage getDataPackage()
    {
    	return emlDataPackage;
    }

    /**
     * returns the number of records in this dataItem
     *
     * @param entityId the id of the entity object to get the record count for
     */
    /*public int getRecordCount(String entityId)
    {
        return ((Entity) entityHash.get(entityId)).getNumRecords();
    }*/

    /**
     * returns the total number of entities in the data item collection that was
     * passed to this class when the object was created.
     */
    /*public int getEntityCount()
    {
        return numEntities;
    }*/

    /**
     * returns the number of attributes in the given entity
     *
     * @param entityId the id of the entity object that you want the attribute
     *            count for
     */
    /*public int getAttributeCount(String entityId)
    {
        Attribute[] attArray = ((Entity) entityHash.get(entityId))
                        .getAttributes();
        return attArray.length;
    }*/
    
    /**
     * if the entity has missing value declaretion
     * @return
     */
    /*public boolean hasMissingValue()
    {
    	return hasMissingValue;
    }*/
    /**
     * Method to get the boolean hasImageEntity. If the eml document has
     * SpatialRaster or SpatialVector entity, this variable should be true;
     * @return boolean
     */
    /*public boolean getHasImageEntity()
    {
      return this.hasImageEntity;
      
    }*/
    
    /*
     * Porcess the attribute list element
     */
    private void processAttributeList(CachedXPathAPI xpathapi, NodeList attList, Entity entObj) throws Exception
    {
        AttributeList attributeList = new AttributeList();
        Node attListNode = attList.item(0);
        // get attributeList element's attribute - id
        NamedNodeMap idAttribute = attListNode.getAttributes();
        String idString = null;
        if (idAttribute != null)
        {
          Node id = idAttribute.getNamedItem(ID);
          if ( id != null)
          {
            idString = id.getNodeValue();
            attributeList.setId(idString);
        	if(isDebugging) {
        		//log.debug("The id value for the attributelist is " + idString);
        	}
          }
        }
        NodeList attNodeList = xpathapi.selectNodeList(attListNode, "attribute");
        NodeList referenceNodeList = xpathapi.selectNodeList(attListNode, "references");
        if (attNodeList != null && attNodeList.getLength() > 0) 
        {
           
            processAttributes(xpathapi, attNodeList, attributeList);
            if (idString != null)
            {
               attributeListHash.put(idString , attributeList);
              
            }
        } 
        else if (referenceNodeList != null && referenceNodeList.getLength() > 0) 
        {
            // get the references id 
            Node referenceNode = referenceNodeList.item(0);
        	if(isDebugging) {
        		//log.debug("The reference node's name is "+referenceNode.getNodeName());
        	}
            String referenceId = referenceNode.getFirstChild().getNodeValue();
        	if(isDebugging) {
        		//log.debug("the reference id is "+ referenceId);
        	}
            attributeList = (AttributeList) attributeListHash.get(referenceId);
        } 
        else
        {
       		//log.debug("The children name of attribute list couldn't be understood");
            throw new Exception(" couldn't be a child of attributeList");
        }
          
        if (!entityObject.isSimpleDelimited())
        {
           int length = attributeList.getAttributes().length;
           if (length != complexFormatsNumber || 
               (length == complexFormatsNumber && complexFormatsNumber == 0))
           {
               throw new Exception("Complex format elements should have"+
                       " some number as attribute number");
           }
           else
           {
               //entityObject.setDataFormatArray(formatArray);
           }
        }
       
        entityObject.setAttributeList(attributeList);
     
        
    }

    /**
     * process the attributes
     */
    private void processAttributes(CachedXPathAPI xpathapi, NodeList atts, AttributeList attributeListObj)
                    throws Exception
    {
        
        
        for (int i = 0; i < atts.getLength(); i++) { //go through each
                                                     // attribute
            Node att = atts.item(i);
            
            
            NodeList attChildren = att.getChildNodes();
            NamedNodeMap attAttributes = att.getAttributes();

            String attName = "";
            String attLabel = "";
            String attDefinition = "";
            String attUnit = "";
            String attUnitType = "";
            String attStorageType = "";
            String attMeasurementScale = "";
            String attPrecision = "";
            Domain domain = null;
            Vector missingCodeVector = new Vector();
            String id = null;
            double numberPrecision = 0;
            // get attribute id
            NamedNodeMap attributeNode = att.getAttributes();
            if (attributeNode != null)
            {
               
                  Node idNode =  attributeNode.getNamedItem(ID);
                  if (idNode != null)
                  {
                	  id = idNode.getNodeValue();
                  }
               
            }
            elementId++;

            for (int j = 0; j < attChildren.getLength(); j++) {
                Node child = attChildren.item(j);
                String childName = child.getNodeName();
                if (childName.equals("attributeName")) {
                    attName = child.getFirstChild().getNodeValue().trim().replace('.', '_');
                } else if (childName.equals("attributeLabel")) {
                    attLabel = child.getFirstChild().getNodeValue().trim();
                } else if (childName.equals("attributeDefinition")) {
                    attDefinition = child.getFirstChild().getNodeValue().trim();
                } else if (childName.equals("measurementScale")) {
                    //unit is tricky because it can be custom or standard
                    //Vector info = new Vector();
                    //int domainType = Domain.DOM_NONE;
                    NodeList msNodeList = child.getChildNodes();
                    for (int k = 0; k < msNodeList.getLength(); k++) {
                        Node n = msNodeList.item(k);
                        String name = n.getNodeName();
                        if (name.equals("interval") || name.equals("ratio")) {
                            String numberType = null;
                            String min = "", max = "";
                            Node sUnit = xpathapi.selectSingleNode(n,
                                            "unit/standardUnit");
                            Node cUnit = xpathapi.selectSingleNode(n,
                                            "unit/customUnit");
                            if (sUnit != null) {
                                attUnit = sUnit.getFirstChild().getNodeValue();
                                attUnitType = Attribute.STANDARDUNIT;
                            } else if (cUnit != null) {
                                attUnit = cUnit.getFirstChild().getNodeValue();
                                attUnitType = Attribute.CUSTOMUNIT;
                            } else {
                                System.err.println("xpath didn't work");
                            }
                            Node precision = xpathapi.selectSingleNode(n,
                                            "precision");
                            if (precision != null) {
                                // precision is optional in EML201 so if it is
                                // not provided, the attPrecision will be the
                                // empty string
                                attPrecision = precision.getFirstChild()
                                            .getNodeValue();
                                numberPrecision = (new Double(attPrecision)).doubleValue();
                                
                            }
                            Node dNode = xpathapi.selectSingleNode(n, "numericDomain");
                            NodeList numberKids = dNode.getChildNodes();
                            for (int index = 0; index < numberKids.getLength(); index++)
                            {
                  
                                String dName = numberKids.item(index).getNodeName();
                                if (dName.equals("numberType")) // got number
                                                                // type
                                {
                                    numberType = numberKids.item(index).getFirstChild().getNodeValue();
                                	if(isDebugging) {
                                		//log.debug("The number type is "+ numberType);
                                	}
                                }
                                else if (dName.equals("boundsGroup"))
                                // got bounds group
                                {
                                    NodeList boundsList = xpathapi
                                            .selectNodeList(dNode, "./bounds");
                                    for (i = 0; i < boundsList.getLength(); i++)
                                    {
                                        NodeList nl;
                                        Node bound;

                                        String exclMin = null, exclMax = null;
                                        try
                                        {
                                            nl = xpathapi.selectNodeList(
                                                    boundsList.item(i),
                                                    "./minimum");
                                            bound = nl.item(0);
                                            min = bound.getFirstChild()
                                                    .getNodeValue();
                                            exclMin = bound.getAttributes()
                                                    .getNamedItem("exclusive")
                                                    .getNodeValue();
                                            nl = xpathapi.selectNodeList(
                                                    boundsList.item(0),
                                                    "./maximum");
                                            bound = nl.item(0);
                                            max = bound.getFirstChild()
                                                    .getNodeValue();
                                            exclMax = bound.getAttributes()
                                                    .getNamedItem("exclusive")
                                                    .getNodeValue();
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
                            System.out.println("the min value is "+min);
                            if (!min.trim().equals(""))
                            {
                                minNum = new Double(min);
                                
                            }
                            if (!max.trim().equals(""))
                            {
                            	maxNum = new Double(max);
                            }
                            NumericDomain numDomain = new NumericDomain(numberType, minNum, maxNum);
                            numDomain.setPrecision(numberPrecision);
                            domain = numDomain;
                           
                        } else if (name.equals("nominal")
                                        || name.equals("ordinal")) {
                            NodeList list = xpathapi.selectSingleNode(n,"nonNumericDomain").getChildNodes();
                            for (int m =0; m <list.getLength(); m++)
                            {
                                Node dNode = list.item(m);
                                String dName = dNode.getNodeName();
                                if (dName.equals("textDomain")) {
                                    TextDomain textDomain = new TextDomain();
                                    NodeList definitionL = xpathapi.selectNodeList(dNode, "./definition");
                                    Node defintionNode = definitionL.item(0);
                                    String definition = defintionNode.getFirstChild().getNodeValue();
                                	if(isDebugging) {
                                		//log.debug("The definition value is "+definition);
                                	}
                                    textDomain.setDefinition(definition);
                                    NodeList nl = xpathapi.selectNodeList(dNode,
                                                    "./pattern");
                                    String[] patternList = new String[nl.getLength()]; 
                                    for (int l = 0; l < nl.getLength(); l++) {
                                      patternList[l] = nl.item(l).getFirstChild().getNodeValue();
                                    }
                                    if (patternList.length > 0)
                                    {
                                      textDomain.setPattern(patternList);
                                    }
                                    domain = textDomain;
                                   
                                } else if (dName.equals("enumeratedDomain")) {
                                    EnumeratedDomain enumerDomain = new EnumeratedDomain();
                                    Vector info = new Vector();
                                    NodeList nl = xpathapi.selectNodeList(dNode,
                                                    "./codeDefinition");
                                    for (int l = 0; l < nl.getLength(); l++) {
                                        info.add(nl.item(l).getFirstChild()
                                                        .getNodeValue());
                                    }
                                    enumerDomain.setInfo(info);
                                    domain = enumerDomain;
                                  
                                }
                            }
                            
                        } else if (name.equals("datetime")) {
                            DateTimeDomain date = new DateTimeDomain();
                            String formatString = 
                                (xpathapi.selectSingleNode(n,"./formatString")).getFirstChild().getNodeValue();
                        	if(isDebugging) {
                        		//log.debug("The format string in date time is " +formatString);
                        	}
                            date.setFormatString(formatString);
                            domain = date;
                           
                        }
                    }
                    
                }
                else if (childName.equals("missingValueCode"))
                {
               		//log.debug("in missilng valueCode");
                    NodeList missingNodeList = child.getChildNodes();
                    for (int k = 0; k < missingNodeList.getLength(); k++) 
                    {
                        Node n = missingNodeList.item(k);
                        String name = n.getNodeName();
                        if (name.equals("code"))
                        {
                        	
                            Node missingCodeTextNode = n.getFirstChild();
                            if (missingCodeTextNode != null)
                            {
	                            String missingCode = missingCodeTextNode.getNodeValue();
	                        	if(isDebugging) {
	                        		//log.debug("the missing code is "+missingCode);
	                        	}
	                            missingCodeVector.add(missingCode);
	                            hasMissingValue = true;
                            }
                        }
                    }
                    
                    
                }
            }
            
            /******************************************************
             * need to use domaim type to replace data type
             ******************************************************/
            /*String resolvedType = null;
            //DataType dataType = domain.getDataType();
            //resolvedType = dataType.getName();
        	if(isDebugging) {
        		//log.debug("The final type is " + resolvedType);
        	}*/
           
            Attribute attObj = new Attribute(id, attName, attLabel,
                            attDefinition, attUnit, attUnitType,
                            attMeasurementScale, domain);
            
            //add missing code into attribute
            for (int k=0; k<missingCodeVector.size(); k++)
            {
                
                String missingCodeValue = (String)missingCodeVector.elementAt(k);
            	if(isDebugging) {
            		//log.debug("the mssing value code " + missingCodeValue + " was added to attribute");
            	}
                attObj.addMissingValueCode(missingCodeValue);
            }
            
            attributeListObj.add(attObj);
            
        }
    }

    /**
     * pulls the entity information out of the xml and stores it in a hashtable
     */
    private void processEntities(CachedXPathAPI xpathapi, NodeList entities, String xpath) throws SAXException,
                    javax.xml.transform.TransformerException, Exception
    { 
        //make sure that entities is not null
        if(entities == null)
        {
          return;
        }
        int entityNodeListLength = entities.getLength();
        numEntities = numEntities + entityNodeListLength;
        String entityName = "";
        String entityDescription = "";
        String entityOrientation = "";
        String entityCaseSensitive = "";
        String entityNumberOfRecords = "-1";
        String physicalFile = "";
        String numHeaderLines = "0";
        int numFooterLines = 0;
        String fieldDelimiter = null;
        String recordDelimiter = "";
        String compressionMethod = "";
        String encodingMethod = "";
        boolean isImageEntity   = false;
        boolean isGZipDataFile  = false;
        boolean isZipDataFile   = false;
        boolean isTarDataFile   = false;
        boolean isSimpleDelimited = true;
        boolean isCollapseDelimiter = false;
        TextComplexDataFormat[] formatArray = null;
         
        for (int i = 0; i < entityNodeListLength; i++) {
            
            if (xpath != null && (xpath.equals(SPATIALRASTERENTITY) 
                                  || xpath.equals(SPATIALVECTORENTITY)))
            {
              isImageEntity = true;
            }
             //go through the entities and put the information into the hash.
            elementId++;
            Node entity = entities.item(i);
            String id = null;
            NamedNodeMap attributeNode = entity.getAttributes();
            if (attributeNode != null) {
               
                Node idNode = attributeNode.getNamedItem(ID);
                if (idNode != null)
                {
                   id = idNode.getNodeValue();
                }
               
            }
            NodeList entityChildren = entity.getChildNodes();
            for (int j = 0; j < entityChildren.getLength(); j++) {
                Node child = entityChildren.item(j);
                String childName = child.getNodeName();

                if (childName.equals("entityName")) {
                    entityName = child.getFirstChild().getNodeValue();
                } else if (childName.equals("entityDescription")) {
                    entityDescription = child.getFirstChild().getNodeValue();
                } else if (childName.equals("caseSensitive")) {
                    entityCaseSensitive = child.getFirstChild().getNodeValue();
                } else if (childName.equals("numberOfRecords")) {
                    entityNumberOfRecords = child.getFirstChild()
                                    .getNodeValue();
                    numRecords = (new Integer(entityNumberOfRecords))
                                    .intValue();
                }                
               
            }
            
            NodeList orientationNodeList = xpathapi.selectNodeList(entity,
                       "physical/dataFormat/textFormat/attributeOrientation");
            if (orientationNodeList != null && orientationNodeList.getLength() >0) {
                    entityOrientation = orientationNodeList.item(0).getFirstChild().getNodeValue();

               }

            NodeList headerLinesNL = xpathapi.selectNodeList(entity,
                       "physical/dataFormat/textFormat/numHeaderLines");
            if ((headerLinesNL != null) && (headerLinesNL.getLength() > 0)) {
                   Node headerLinesNode = headerLinesNL.item(0);
                   if (headerLinesNode != null) {
                       numHeaderLines = headerLinesNode.getFirstChild()
                                       .getNodeValue();
                   }
               }
            
            
            NodeList footerLinesNL = xpathapi.selectNodeList(entity,
                          "physical/dataFormat/textFormat/numFooterLines");
             if ((footerLinesNL != null) && (footerLinesNL.getLength() > 0)) {
                Node footerLinesNode = footerLinesNL.item(0);
                   if (footerLinesNode != null) {
                         String footerLineStr = footerLinesNode.getFirstChild().getNodeValue();
                         numFooterLines = (new Integer(footerLineStr.trim())).intValue();
                       }
            }
           
           // Here is the simple delimited data file
           NodeList delimiterNL = xpathapi.selectNodeList(entity,
                       "physical/dataFormat/textFormat/simpleDelimited/fieldDelimiter");
           if (delimiterNL != null && delimiterNL.getLength() >0) {
                   fieldDelimiter = delimiterNL.item(0).getFirstChild().getNodeValue();
               }
           
//         Here is the simple delimited data file
           NodeList collapseDelimiterNL = xpathapi.selectNodeList(entity,
                       "physical/dataFormat/textFormat/simpleDelimited/collapseDelimiters");
           if (collapseDelimiterNL != null && collapseDelimiterNL.getLength() >0) {
                   String collapseDelimiter = collapseDelimiterNL.item(0).getFirstChild().getNodeValue();
                   if (collapseDelimiter.equalsIgnoreCase("yes"))
                   {
                	   isCollapseDelimiter = true;
                   }
                   
               }
           
           // for complex format data file
           NodeList complexFormatNL = xpathapi.selectNodeList(entity,
                                      "physical/dataFormat/textFormat/complex");
           if (complexFormatNL != null && complexFormatNL.getLength() > 0)
           {
        	//log.debug("in handle complex text data format");
             isSimpleDelimited = false;
             Node complexFormatNode = complexFormatNL.item(0);
             NodeList complexFormatChildren = complexFormatNode.getChildNodes();
             int childrenLength = complexFormatChildren.getLength();
             Vector formatVector = new Vector();
             for (int k=0; k<childrenLength; k++)
             {
                 Node node = complexFormatChildren.item(k);
                 if (node != null && node.getNodeName().equals("textFixed"))
                 {
                     TextWidthFixedDataFormat textFixedFormat = handleTextFixedDataFormatNode(node);
                     if ( textFixedFormat != null)
                     {
                        formatVector.add(textFixedFormat);
                        //complexFormatsNumber++;
                     }
                     
                 }
                 else if (node != null && node.getNodeName().equals("textDelimited"))
                 {
                     TextDelimitedDataFormat delimitedFormat = 
                                     handleComplexDelimitedDataFormatNode(node);
                     if (delimitedFormat != null)
                     {
                         formatVector.add(delimitedFormat);
                         //complexFormatsNumber++;
                     }
                 }
              }
             //transfer vector to array
             complexFormatsNumber = formatVector.size();
             formatArray = new TextComplexDataFormat[complexFormatsNumber];
             for (int j=0; j< complexFormatsNumber; j++)
             {
                 formatArray[j] =(TextComplexDataFormat)formatVector.elementAt(j);  
             }
             
             
           }
           
           
           
           
           NodeList recDelimiterNL = xpathapi.selectNodeList(entity,
                       "physical/dataFormat/textFormat/recordDelimiter");
           if ((recDelimiterNL != null) && (recDelimiterNL.getLength() > 0)) {
              recordDelimiter = recDelimiterNL.item(0).getFirstChild().getNodeValue();
           } else {
              recordDelimiter = "\\r\\n";
           }
           //get the distribution information
           NodeList distributionNL = xpathapi.selectNodeList(entity,
                               "physical/distribution/online/url");
           if (distributionNL != null && distributionNL.getLength() >0)
           {
              physicalFile = distributionNL.item(0).getFirstChild().getNodeValue();
          	if(isDebugging) {
        		//log.debug("The url is "+ physicalFile);
        	}
           }

           //get the compressionMethod information
           NodeList compressionNL = xpathapi.selectNodeList(entity,
                                  "physical/compressionMethod");
           if (compressionNL != null && compressionNL.getLength() >0)
           {
              compressionMethod = compressionNL.item(0).getFirstChild().getNodeValue();
          	if(isDebugging) {
        		//log.debug("Compression method is "+compressionMethod);
        	}
              if (compressionMethod != null && compressionMethod.equals(Entity.GZIP))
              {
                isGZipDataFile = true;
              }
              else if (compressionMethod != null && compressionMethod.equals(Entity.ZIP))
              {
                isZipDataFile = true;
              }
          }
          
          // get encoding method info (mainly for tar file)
          NodeList encodingNL = xpathapi.selectNodeList(entity, "physical/encodingMethod");
          if(encodingNL != null && encodingNL.getLength() > 0)
          {
            encodingMethod = encodingNL.item(0).getFirstChild().getNodeValue();
        	if(isDebugging) {
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

            entityObject = new Entity(id, entityName.trim(),
                            entityDescription.trim(), new Boolean(
                                            entityCaseSensitive),
                            entityOrientation, new Integer(
                                            entityNumberOfRecords).intValue());
            entityObject.setNumHeaderLines((new Integer(numHeaderLines))
                            .intValue());
            entityObject.setNumFooterLines(numFooterLines);
            entityObject.setSimpleDelimited(isSimpleDelimited);
            // for simple dimited data file
            if (fieldDelimiter != null)
            {
               entityObject.setDelimiter(fieldDelimiter);
            }
            entityObject.setCollaplseDelimiter(isCollapseDelimiter);
            
            //System.out.println("in eml200 parser, the recordDelimiter is "+recordDelimiter);
            entityObject.setRecordDelimiter(recordDelimiter);
            entityObject.setURL(physicalFile);
            entityObject.setCompressionMethod(compressionMethod);
            entityObject.setIsImageEntity(isImageEntity);
            entityObject.setHasGZipDataFile(isGZipDataFile);
            entityObject.setHasZipDataFile(isZipDataFile);
            entityObject.setHasTarDataFile(isTarDataFile);
            
            try {
                NodeList attNL = xpathapi.selectNodeList(entity,
                                "attributeList");
                processAttributeList(xpathapi, attNL, entityObject);
                entityObject.setDataFormatArray(formatArray);
                
            } catch (Exception e) {
                throw new Exception("Error parsing attributes: "
                                + e.getMessage());
            }
            //entityHash.put(Integer.toString(elementId), entityObject);
            emlDataPackage.add(entityObject);
            //fileHash.put(elementId, physicalFile);
            
        }
        
      
    }
    
    /*
     * This method will digest a text fixed data format node and return
     * a TextFixedDataFormat object.
     */
    private TextWidthFixedDataFormat handleTextFixedDataFormatNode(Node node) throws Exception
    {
       TextWidthFixedDataFormat format = null;
       if (node == null)
       {
           return format;
       }
       NodeList children = node.getChildNodes();
       int length = children.getLength();
       for (int i = 0; i<length; i++)
       {
           Node kid = children.item(i);
           String elementName = kid.getNodeName();
           if (elementName != null && elementName.equals("fieldWidth"))
           {
              String fieldWidthStr = kid.getFirstChild().getNodeValue();
              
              int fieldWidth = (new Integer(fieldWidthStr)).intValue();
          	if(isDebugging) {
        		//log.debug("The filed width for fix width in eml is "+ fieldWidth);
        	}
              format = new TextWidthFixedDataFormat(fieldWidth);
           }
           else if (elementName != null && elementName.equals("fieldStartColumn") 
                   && format != null)
           {
               String startColumnStr = kid.getFirstChild().getNodeValue();
               int startColumn  = (new Integer(startColumnStr)).intValue();
           	if(isDebugging) {
        		//log.debug("The start cloumn is "+startColumn);
        	}
               format.setFieldStartColumn(startColumn);
           }
           else if (elementName != null && elementName.equals("lineNumber") 
                   && format != null)
           {
               String lineNumberStr = kid.getFirstChild().getNodeValue();
               int lineNumber  = (new Integer(lineNumberStr)).intValue();
           	if(isDebugging) {
        		//log.debug("The start cloumn is "+lineNumber);
        	}
               format.setLineNumber(lineNumber);
           }
       }
       return format;
    }
    
    /*
     * This method will digest a delimited data format node and return
     * a DelimitedFixedFormat object.
     */
    private TextDelimitedDataFormat handleComplexDelimitedDataFormatNode(Node node) throws Exception
    {
       TextDelimitedDataFormat format = null;
       if (node == null)
       {
           return format;
       }
       NodeList children = node.getChildNodes();
       int length = children.getLength();
       Vector quoteList = new Vector();
       for (int i = 0; i<length; i++)
       {
           Node kid = children.item(i);
           String elementName = kid.getNodeName();
           if (elementName != null && elementName.equals("fieldDelimiter"))
           {
              String fieldDelimiter = kid.getFirstChild().getNodeValue();
          	if(isDebugging) {
        		//log.debug("The filed delimiter for complex format in eml is "+ fieldDelimiter);
        	}
              format = new TextDelimitedDataFormat(fieldDelimiter);
           }
           else if (elementName != null && elementName.equals("lineNumber") 
                   && format != null)
           {
               String lineNumberStr = kid.getFirstChild().getNodeValue();
               int lineNumber = (new Integer(lineNumberStr)).intValue();
           	if(isDebugging) {
        		//log.debug("The line number is "+lineNumber);
        	}
               format.setLineNumber(lineNumber);
           }
           else if (elementName != null && elementName.equals("collapseDelimiter") 
                   && format != null)
           {
               String collapse = kid.getFirstChild().getNodeValue();
           	if(isDebugging) {
        		//log.debug("The collapse delimiter "+collapse);
        	}
               format.setCollapseDelimiter(collapse);
           }
           else if (elementName != null && elementName.equals("quoteCharacter") 
                   && format != null)
           {
               String quote = kid.getFirstChild().getNodeValue();
                quoteList.add(quote);
               
           }
           
       }
       // set up quoteList
       if (format != null)
       {
           int size = quoteList.size();
           String[] quoteArray = new String[size];
           for (int i=0; i<size; i++)
           {
               quoteArray[i] = (String)quoteList.elementAt(i);
           }
           format.setQuoteCharater(quoteArray);
       }
       return format;
    }
}
