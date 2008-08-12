/**
 *    '$RCSfile: DocumentDataPackageParser.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2008-08-12 23:30:27 $'
 *   '$Revision: 1.2 $'
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

package org.ecoinformatics.datamanager.parser.document;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xpath.CachedXPathAPI;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Domain;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.NumericDomain;
import org.ecoinformatics.datamanager.parser.TextDomain;
import org.ecoinformatics.datamanager.parser.generic.DataPackageParserInterface;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.ucsb.nceas.utilities.OrderedMap;
import edu.ucsb.nceas.utilities.XMLUtilities;

/**
 * This is plugin Parser parses any XML file to 
 * retrieve the specified xPath elements as a Map record 
 * 
 * Note that the document
 * needs to have a 'packageId' attribute as part of the root node
 *  
 * 
 * @author leinfelder
 */
public class DocumentDataPackageParser implements DataPackageParserInterface
{
    /*
     * Class fields
     */

    private static Log log = LogFactory.getLog(DocumentDataPackageParser.class);
    
    /*
     * Instance fields
     */
    
    // previously these were constants, now member variables with defaults
    protected String packageIdPath = null;    
    private DocumentDataPackage dataPackage = null;
    private Map attributeXPathMap = null;
    private Map record = null;
    private Document doc = null;
    
    /**
     * Default constructor - no custom xpath parameter
     */
    public DocumentDataPackageParser() {
    	this.packageIdPath = "//*/@packageId";
    	this.record = new HashMap();
    }
    
    public void setAttributeXPathMap(Map xpaths) {
    	this.attributeXPathMap = xpaths;
    }
    
    private Vector getRecordRow() {
    	Vector row = new Vector();
    	row.addAll(record.values());
    	
    	return row;
    }
    
    /**
     * Constructor that accepts only the packageIdPath.
     * Allows packageId to be located anywhere in schema,
     * but assumes default (EML) placement of dataset
     * @param packageIdPath path expression specifying where to look for packageId
     */
    public DocumentDataPackageParser(String packageIdPath) {
    	this();
    	//set the param
		this.packageIdPath = packageIdPath;
    }

    
    /* (non-Javadoc)
	 * @see org.ecoinformatics.datamanager.parser.generic.GenericDatasetParserInterface#parse(org.xml.sax.InputSource)
	 */
    public void parse(InputSource source) throws Exception
    {
        DocumentBuilder builder = 
        	DocumentBuilderFactory.newInstance().newDocumentBuilder();
        doc = builder.parse(source);
        parseDocument();
    }

    
    /* (non-Javadoc)
	 * @see org.ecoinformatics.datamanager.parser.generic.GenericDatasetParserInterface#parse(java.io.InputStream)
	 */
    public void parse(InputStream is) throws Exception
    {
        DocumentBuilder builder = 
        	DocumentBuilderFactory.newInstance().newDocumentBuilder();
        doc = builder.parse(is);
        parseDocument();
    }
    
    /**
     * Parses the document. Uses the attributeMap to determine the
     * attributes included in the returned Map record  
     * 
     * @param doc  the Document object to be parsed
     */
    private void parseDocument() throws Exception {
    	CachedXPathAPI xpathapi = new CachedXPathAPI();
    	String packageId = null;
    	try {
    		// process packageid
    		Node packageIdNode = xpathapi.selectSingleNode(doc, packageIdPath);
    		if (packageIdNode != null) {
    			packageId = packageIdNode.getNodeValue();
    		}
    		dataPackage = new DocumentDataPackage(packageId);
        } 
        catch (Exception e) {
            throw new Exception(
            		"Error extracting packageId from root of document.");
        }
    }
    
    public void generateEntity() throws Exception {
    	
    	if (this.attributeXPathMap == null) {
        	throw new Exception(
        			"Must specify attribute xPaths for document->record parsing.");
        }
    	
    	//now get the flattened document as a map
        this.record = document2Map(doc, this.attributeXPathMap);
        
        //convert the map to an entity
        Entity entity = map2Entity(this.record, dataPackage.getPackageId());
        
        //add the entity to the datapackage
        this.dataPackage.clearEntityList();
        this.dataPackage.add(entity);
        
        //set the row record data
        this.dataPackage.setRecordRow(getRecordRow());
    }
    
    /* (non-Javadoc)
	 * @see org.ecoinformatics.datamanager.parser.generic.GenericDatasetParserInterface#getDataPackage()
	 */
    public DataPackage getDataPackage()
    {
    	return dataPackage;
    }
    
	public static Entity map2Entity(Map record, String entityId) {
		
		AttributeList attributeList = new AttributeList();
		
		Iterator iter = record.keySet().iterator();
		while (iter.hasNext()) {
			String id = (String) iter.next();
			String name = id;
			Object value = record.get(id);
			Domain domain = new TextDomain(); 
			//TODO handle more specific numeric types?
			if (value instanceof Number) { 
				domain = new NumericDomain("real", null, null);
			}
			Attribute a = new Attribute(id, name, domain);
			attributeList.add(a);
		}
		
		Entity entity = 
			new Entity(
					entityId,
					entityId, // + " name", 
					entityId, // + " description",
					attributeList);
		
		//set some other crucial info for generating the tables and sql
		entity.setPackageId(entity.getId());
		entity.setEntityIdentifier(entity.getId());
		
		return entity;
	}
	
	public static Map document2Map(Document doc, Map attributeXPaths) {
		Map record = new OrderedMap();
		
		try {
			
			//go through the list of attribute labels (key to xpath values)
			Iterator xPathIter = attributeXPaths.keySet().iterator();
			while (xPathIter.hasNext()) {
				String attributeLabel = (String) xPathIter.next();
				String attributeXPath = (String) attributeXPaths.get(attributeLabel);
				
				//handle NodeList, not just single Node
				NodeList attributeNodeList = XMLUtilities.getNodeListWithXPath(doc.getDocumentElement(), attributeXPath);
				
				//include placeholders for those non existent attributes but include null values
				if (attributeNodeList == null) {
					log.debug("no nodes found for xPath: " + attributeXPath);
					record.put(attributeLabel, null);
					log.debug("added null placeholder for attribute: " + attributeLabel);
					continue;
				}
				//get the value[s] for the attribute
				for (int i=0; i < attributeNodeList.getLength(); i++) {
					
					//get the node
					Node attributeNode = attributeNodeList.item(i);
					
					//get the text value of the node
					//TODO should we use DOM level 3 and assume java 1.5?
					String nodeTextContent = null; //attributeNode.getTextContent();
					if (attributeNode.getFirstChild() != null && attributeNode.getFirstChild().getNodeType() == Node.TEXT_NODE) {
						nodeTextContent = attributeNode.getFirstChild().getNodeValue();
					}
					
					//add the attribute to the Map, taking care to handle multiples
					String columnLabel = attributeLabel;
					if (record.containsKey(columnLabel)) {
						columnLabel = columnLabel + "_" + i;
					}
					record.put(columnLabel, nodeTextContent);
					
					log.debug("added flat attribute: " + columnLabel + "=" + nodeTextContent);
					
				}				

			}
		}
		catch (Exception e) {
			log.error("could not flatten attributes in document: " + e.getMessage());
			e.printStackTrace();
		}
		
		return record;
	}
 
    
}
