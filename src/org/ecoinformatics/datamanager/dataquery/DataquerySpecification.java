/**
 *  '$RCSfile: DataquerySpecification.java,v $'
 *    Purpose: A Class that represents a structured query, and can be
 *             constructed from an XML serialization conforming to
 *             pathquery.dtd. The printSQL() method can be used to print
 *             a SQL serialization of the query.
 *  Copyright: 2000 Regents of the University of California and the
 *             National Center for Ecological Analysis and Synthesis
 *    Authors: Matt Jones
 *
 *   '$Author: leinfelder $'
 *     '$Date: 2009-01-08 18:51:48 $'
 * '$Revision: 1.18 $'
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.ecoinformatics.datamanager.dataquery;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.database.ANDRelation;
import org.ecoinformatics.datamanager.database.Condition;
import org.ecoinformatics.datamanager.database.ConditionInterface;
import org.ecoinformatics.datamanager.database.DatabaseConnectionPoolInterface;
import org.ecoinformatics.datamanager.database.Join;
import org.ecoinformatics.datamanager.database.LogicalRelation;
import org.ecoinformatics.datamanager.database.ORRelation;
import org.ecoinformatics.datamanager.database.Query;
import org.ecoinformatics.datamanager.database.SelectionItem;
import org.ecoinformatics.datamanager.database.StaticSelectionItem;
import org.ecoinformatics.datamanager.database.SubQueryClause;
import org.ecoinformatics.datamanager.database.TableItem;
import org.ecoinformatics.datamanager.database.Union;
import org.ecoinformatics.datamanager.database.WhereClause;
import org.ecoinformatics.datamanager.download.DocumentHandler;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterface;
import org.ecoinformatics.datamanager.download.document.DocumentDataPackageHandler;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import edu.ucsb.nceas.utilities.OrderedMap;

/**
 * A Class that represents a data query, and can be constructed from an
 * XML serialization conforming to
 *
 * @see dataquery.xsd. The Data Manager Library should be used to execute the 
 * Query or Union provided by this class.
 * Note that the Datapackages involved in the Query are given by the getDataPackages() method
 */
public class DataquerySpecification extends DefaultHandler
{
	private DatabaseConnectionPoolInterface connectionPool;
	
	private EcogridEndPointInterface ecogridEndPoint;

    /** A string buffer to store query
     */
    private StringBuffer xml = new StringBuffer();

    private StringBuffer textBuffer = new StringBuffer();
    
    private String parserName = null;

    // Query data structures used temporarily during XML parsing
    private Stack elementStack = new Stack();

    private Stack datapackageStack = new Stack();

    private Stack queryStack = new Stack();
            
    private Stack entityStack = new Stack();
    
    private Stack attributeStack = new Stack();
    
    private Stack conditionStack = new Stack();
    
    private Stack logicalStack = new Stack();
    
    private Stack subQueryStack = new Stack();
        
    private LogicalRelation currentLogical = null;
    
    private LogicalRelation currentSubQueryLogical = null;
    
    private DocumentDataPackageHandler ddpHandler = null;
    
    private DataPackage metadataPackage = null;
     
    private boolean inCondition = false;
        
    private static Log log = LogFactory.getLog(DataquerySpecification.class);
    
    private Map fetchedDatapackages = new HashMap(); 
    
    private Map metadataDatapackages = new HashMap(); 
    
    private List queryList = new ArrayList();
    
    private Union union = null;

    /**
     * construct an instance of the QuerySpecification class
     *
     * @param queryspec
     *            the XML representation of the query (should conform to
     *            dataquery.xsd) as a Reader
     * @param parserName
     *            the fully qualified name of a Java Class implementing the
     *            org.xml.sax.Parser interface
     */
    public DataquerySpecification(
    		Reader queryspec, 
    		String parserName, 
    		DatabaseConnectionPoolInterface connectionPool,
    		EcogridEndPointInterface ecogridEndPoint) throws IOException
    {
        super();
        
        //for the DataManager
        this.connectionPool = connectionPool;
        this.ecogridEndPoint = ecogridEndPoint;

        // Initialize the class variables
        this.parserName = parserName;

        // Initialize the parser and read the queryspec
        XMLReader parser = initializeParser();
        if (parser == null) {
            log.error("SAX parser not instantiated properly.");
        }
        try {
            parser.parse(new InputSource(queryspec));
        } catch (SAXException e) {
        	log.error("error parsing data in "
                    + "DataquerySpecification.DataquerySpecification");
        	log.error(e.getMessage());
        }
    }

    /**
     * construct an instance of the DataquerySpecification class
     *
     * @param queryspec
     *            the XML representation of the query (should conform to
     *            dataquery.xsd) as a String
     * @param parserName
     *            the fully qualified name of a Java Class implementing the
     *            org.xml.sax.Parser interface
     */
    public DataquerySpecification(
    		String queryspec, 
    		String parserName, 
    		DatabaseConnectionPoolInterface connectionPool,
    		EcogridEndPointInterface ecogridEndPoint) throws IOException
    {
        this(new StringReader(queryspec), parserName, connectionPool, ecogridEndPoint);
    }

    /**
     * Returns the Query generated after parsing XML.
     * In cases where a Union is requested, this method will return only the first
     * Query used in the Union.
     * @return a single query object that can be used with the DataManager 
     */
    public Query getQuery() {
    	return (Query) queryList.get(0);
    }
    
    /**
     * Returns the Union (if any) generated after parsing XML.
     * In cases where a Union is requested, this method will return only the first
     * Query used in the Union.
     * @return a single query object that can be used with the DataManager 
     */
    public Union getUnion() {
    	return union;
    }
    
    /**
     * Needed when using the DataManager to select data using a generated Query or Union
     * @return
     */
    public DataPackage[] getDataPackages() {
    	return (DataPackage[]) datapackageStack.toArray(new DataPackage[0]);
    }
    
    /**
     * Set up the SAX parser for reading the XML serialized query
     */
    private XMLReader initializeParser()
    {
        XMLReader parser = null;

        // Set up the SAX document handlers for parsing
        try {

            // Get an instance of the parser
            parser = XMLReaderFactory.createXMLReader(parserName);

            // Set the ContentHandler to this instance
            parser.setContentHandler(this);

            // Set the error Handler to this instance
            parser.setErrorHandler(this);

        } catch (Exception e) {
            System.err.println("Error in QuerySpcecification.initializeParser "
                    + e.toString());
        }

        return parser;
    }

    private void startQuery(BasicNode currentNode) {
    	Query query = new Query();
    	boolean distinct = Boolean.parseBoolean(currentNode.getAttribute("distinct"));
    	query.setDistinct(distinct);
        queryStack.push(query);
    }
    private void startSubquery(BasicNode currentNode) {
    	Query query = new Query();
        subQueryStack.push(query);
    }
    private void startDatapackage(BasicNode currentNode) {
    	String docId = currentNode.getAttribute("id");
    	
    	DataPackage datapackage = (DataPackage) fetchedDatapackages.get(docId);
    	
    	if (datapackage == null) {
    	
        	//read metadata document
        	DocumentHandler dh = new DocumentHandler();
        	dh.setDocId(docId);
        	dh.setEcogridEndPointInterface(ecogridEndPoint);
        	InputStream metadataInputStream = null;
			try {
				metadataInputStream = dh.downloadDocument();
			} catch (Exception e1) {
				log.error("could not download document: " + docId);
				e1.printStackTrace();
			}
        	
        	//parse as DataPackage
        	try {
	        	datapackage =
	        		DataManager.getInstance(
	        				connectionPool, 
	        				connectionPool.getDBAdapterName()).parseMetadata(
	        						metadataInputStream);
        	} catch (Exception e) {
				log.error(
						"could not parse metadata given by docid: " + docId);
			}
        	
        	//prime the data?
        	try {
				DataManager.getInstance(
						connectionPool, 
						connectionPool.getDBAdapterName()).loadDataToDB(
								datapackage, 
								ecogridEndPoint);
			} catch (Exception e) {
				log.error(
						"could not load data given by docid: " + docId);
			}
			
			//save for later
			fetchedDatapackages.put(docId, datapackage);
    	}
    	
    	//save it for later - preserving order/nesting
    	datapackageStack.push(datapackage);
    }
    private void startEntity(BasicNode currentNode) {
    	//get the entity and save it for later
    	DataPackage datapackage = (DataPackage) datapackageStack.peek();
    	Entity entity = null;
    	
    	String idAttribute = currentNode.getAttribute("id");
    	String indexAttribute = currentNode.getAttribute("index");
    	String nameAttribute = currentNode.getAttribute("name");
    	
    	//metadata
    	if (idAttribute != null) {
    		ddpHandler = 
    			new DocumentDataPackageHandler(connectionPool);
    		ddpHandler.setDocId(idAttribute);
    		ddpHandler.setEcogridEndPointInterface(ecogridEndPoint);
    		ddpHandler.setAttributeMap(new OrderedMap()); //set it up for attributes
    		
    		//place holder for items in condition/joins
    		if (inCondition) {
    			metadataPackage = new DataPackage(idAttribute);
    		}
    	}
    	//indexed data
    	else if (indexAttribute != null) {
    		int index = Integer.parseInt(indexAttribute);
    		entity = datapackage.getEntityList()[index];
    	}
    	//named data
    	else if (nameAttribute != null) {
        	entity = datapackage.getEntity(nameAttribute);
    	}
    	
    	//save for later
    	entityStack.push(entity);
    }
    private void startAttribute(BasicNode currentNode) {
    	attributeStack.push(currentNode);
    }
    private void startCondition(BasicNode currentNode) {
    	inCondition = true;
    	
    	//indicates we are in a condition
    	ConditionInterface condition = null;
    	if (currentNode.getAttribute("type").equals("subquery")) {
    		condition = new SubQueryClause(null, null, null, null);
    	}
    	else if (currentNode.getAttribute("type").equals("join")) {
    		condition = new Join(null, null, null, null);
    	}
    	else {
    		condition = new Condition(null, null, null, null);
    	}
    	conditionStack.push(condition);
    }
    
    private void startAnd(BasicNode currentNode) {
    	//create new AND and add it to the current relation (for nesting)
    	ANDRelation and = new ANDRelation();
    	if (subQueryStack.isEmpty()) {
        	if (currentLogical != null) {
        		currentLogical.addANDRelation(and);
        	}
        	//then set the current logical to the new AND
        	currentLogical = and;
            logicalStack.push(currentLogical);
    	}
    	else {
    		if (currentSubQueryLogical != null) {
    			currentSubQueryLogical.addANDRelation(and);
        	}
        	//then set the current logical to the new AND
    		currentSubQueryLogical = and;
            logicalStack.push(currentSubQueryLogical);
    	}
    }    
    private void startOr(BasicNode currentNode) {
    	ORRelation or = new ORRelation();
    	if (subQueryStack.isEmpty()) {
        	if (currentLogical != null) {
        		currentLogical.addORRelation(or);
        	}
        	currentLogical = or;
            logicalStack.push(currentLogical);
    	}
    	else {
    		if (currentSubQueryLogical != null) {
    			currentSubQueryLogical.addORRelation(or);
        	}
        	//then set the current logical to the new AND
    		currentSubQueryLogical = or;
            logicalStack.push(currentSubQueryLogical);
    	}
    }
    
    /**
     * callback method used by the SAX Parser when the start tag of an element
     * is detected. Used in this context to parse and store the query
     * information in class variables.
     */
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException
    {
        log.debug("<" + localName + ">");
        BasicNode currentNode = new BasicNode(localName);
        //write element name into xml buffer.
        xml.append("<");
        xml.append(localName);
        // add attributes to BasicNode here
        if (atts != null) {
            int len = atts.getLength();
            for (int i = 0; i < len; i++) {
                currentNode.setAttribute(atts.getLocalName(i), atts.getValue(i));
                xml.append(" ");
                xml.append(atts.getLocalName(i));
                xml.append("=\"");
                xml.append(atts.getValue(i));
                xml.append("\"");
            }
        }
        xml.append(">");

        elementStack.push(currentNode);
        
        //process the nodes
        if (currentNode.getTagName().equals("query")) {
            startQuery(currentNode);
        }
        if (currentNode.getTagName().equals("subquery")) {
            startSubquery(currentNode);
        }
        if (currentNode.getTagName().equals("datapackage")) {
        	startDatapackage(currentNode);
        }
        if (currentNode.getTagName().equals("entity")) {
        	startEntity(currentNode);
        }
        if (currentNode.getTagName().equals("attribute")) {
        	startAttribute(currentNode);
        }
        if (currentNode.getTagName().equals("condition")) {
        	startCondition(currentNode);
        }
        if (currentNode.getTagName().equals("and")) {
        	startAnd(currentNode);
        }
        if (currentNode.getTagName().equals("or")) {
        	startOr(currentNode);
        }
        
    }

    private void endUnion(BasicNode leaving) {
    	//only instantiate it when it is used
    	union = new Union();
    	
    	//add all the queries
    	for (int i = 0; i < queryList.size(); i++) {
    		union.addQuery((Query) queryList.get(i));
    	}
    }
    
    private void endQuery(BasicNode leaving) {
    	//pop, done with the query, add to the union (even for single query)
    	Query query = (Query) queryStack.pop();
    	queryList.add(query);
    }
    
    private void endSubquery(BasicNode leaving) {
    	//pop, done with the subquery
    	subQueryStack.pop();
    }
    private void constructEntity() {
    	//try to load the metadata if we need to
		if (ddpHandler != null) {
    		try {
				
				//if this part of the selection area, then continue constructing it
				if (!inCondition) {
					metadataPackage = ddpHandler.loadDataToDB();
					metadataDatapackages.put(
							metadataPackage.getPackageId(), 
							metadataPackage);
				}
				
				//look up the existing one
				metadataPackage = (DataPackage) metadataDatapackages.get(metadataPackage.getPackageId());
				
				//out with the null, in with the new
				entityStack.pop(); 
				entityStack.push(metadataPackage.getEntityList()[0]);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		
		//look up the attribute from this entity
    	Entity entity = (Entity) entityStack.peek();
    	
    	//process the attributes
    	while (!attributeStack.empty()) {
    		BasicNode leaving = (BasicNode) attributeStack.pop();
    	
	    	Attribute attribute = null;
	    	
	    	String indexAttribute = leaving.getAttribute("index");
	    	if (indexAttribute != null) {
	        	int index = Integer.parseInt(indexAttribute);
	        	attribute = entity.getAttributes()[index];
	    	}
	    	else {
	    		//TODO allow multiple matches on "name"?
	    		String nameAttribute = leaving.getAttribute("name");
	    		attribute = entity.getAttributeList().getAttribute(nameAttribute);
	    	}
	    	
	    	//process conditions here
	    	if (!conditionStack.isEmpty()) {	
	    		//intitial part of the condition
	    		ConditionInterface condition = (ConditionInterface) conditionStack.pop();
	        	if (condition instanceof Condition) {
	        		condition = new Condition(entity, attribute, null, null);
				}
	        	else if (condition instanceof SubQueryClause) {
	        		Query subQuery = null;
	        		if (subQueryStack.isEmpty()) {
	        			subQuery = new Query();
	        		}
	        		else {
	        			subQuery = (Query) subQueryStack.peek();
	        		}
	        		SelectionItem selection = new SelectionItem(entity, attribute);
	        		subQuery.addSelectionItem(selection);
	        		// if we have the operator from before, we should use it
	        		String operator = ((SubQueryClause) condition).getOperator();
	        		condition = new SubQueryClause(entity, attribute, operator, subQuery);
				}
	        	else if (condition instanceof Join) {
	        		if (!((Join)condition).isLeftSet()) {
	        			((Join)condition).setLeft(entity, attribute);
	        		}
	        		else if (!((Join)condition).isRightSet()) {
	        			((Join)condition).setRight(entity, attribute);
	        		}
	        	}
	        	conditionStack.push(condition);
	        	
	    	}
	    	else {
	        	Query query = (Query) queryStack.peek();
	
	    		//create the selectionItem and add to the query
	        	SelectionItem selection = new SelectionItem(entity, attribute);
	        	query.addSelectionItem(selection);
	    	}
	    	
    	}//while
    }
    
    private void endEntity(BasicNode leaving) {
    	
    	// construct the entity
    	constructEntity();
    	
    	//in selection
    	if (conditionStack.isEmpty()) {
        	//pop, done with the entity
        	Entity entity = (Entity) entityStack.pop();
        	
        	//add to query
        	Query query = (Query) queryStack.peek();
        	query.addTableItem(new TableItem(entity));
    	}
    	
    	if (!subQueryStack.isEmpty()) {
        	//pop, done with the entity
        	Entity entity = (Entity) entityStack.pop();

    		Query subQuery = (Query) subQueryStack.peek();
    		subQuery.addTableItem(new TableItem(entity));
    	}
    	
    	//reset the document datapackage handler
    	if (ddpHandler != null) {
    		//done with this
    		ddpHandler = null;
    	}
    }
    
    private void endPathexpr(BasicNode leaving) {
    	String labelAttribute = leaving.getAttribute("label");
    	ddpHandler.getAttributeMap().put(labelAttribute, textBuffer.toString().trim());
    }
    
    private void endAttribute(BasicNode leaving) {
    	//moved to end of entity
    }
    
    private void endOperator(BasicNode leaving) {
    	String operator = textBuffer.toString().trim();
    	ConditionInterface condition = (ConditionInterface) conditionStack.peek();
    	if (condition instanceof Condition) {
			((Condition) condition).setOperator(operator);
		}
    	if (condition instanceof SubQueryClause) {
			((SubQueryClause) condition).setOperator(operator);
		}
    }
    
    private void endValue(BasicNode leaving) {
    	Object value = textBuffer.toString().trim();
    	//try some numeric options
    	//TODO incorporate type information from metadata into these decisions.
    	try {
    		value = Integer.parseInt((String) value);
    	}
    	catch (Exception e) {
    		try {
    			value = Double.parseDouble((String) value);
    		}
    		catch (Exception e2) {
				// do nothing - treat it as a String
			}
		}
    	ConditionInterface condition = (ConditionInterface) conditionStack.peek();
    	if (condition instanceof Condition) {
			((Condition) condition).setValue(value);
			
		}
    }
    
    private void endWhere(BasicNode leaving) {
    	//in subquery?
    	if (!subQueryStack.isEmpty()) {
    		WhereClause where = new WhereClause((ConditionInterface)null);
    		
        	if (currentSubQueryLogical != null) {
        		
        		if (currentSubQueryLogical instanceof ANDRelation) {
        			where = new WhereClause((ANDRelation)currentSubQueryLogical);
        		}
        		else {
        		}
        	}
        	else if (!conditionStack.isEmpty()) {
	        	//done with the condition now, we can pop it
	        	ConditionInterface condition = (ConditionInterface) conditionStack.pop();
	        	where = new WhereClause(condition);
        	}
        	
    		Query subQuery = (Query) subQueryStack.peek();
        	subQuery.setWhereClause(where);
    	}
    	else {
        	//set up the shell
        	WhereClause where = new WhereClause((ConditionInterface)null);
        	
        	if (!conditionStack.isEmpty()) {
	        	//done with the condition now, we can pop it
	        	ConditionInterface condition = (ConditionInterface) conditionStack.pop();
	        	where = new WhereClause(condition);
        	}
        	else if (currentLogical != null) {
        		
        		if (currentLogical instanceof ANDRelation) {
        			where = new WhereClause((ANDRelation)currentLogical);
        		}
        		else {
        			where = new WhereClause((ORRelation)currentLogical);
        		}
        	}
        	
        	//set the where clause
       		Query query = (Query) queryStack.peek();
        	query.setWhereClause(where);
    	}
    }
    
    private void endCondition(BasicNode leaving) {
    	inCondition = false;
    	
    	if (!subQueryStack.isEmpty()) {
    		if (currentSubQueryLogical != null) {
        		//do something?
        		currentSubQueryLogical.addCondtionInterface((ConditionInterface) conditionStack.pop());
    		}
    	}
    	else if (currentLogical != null) {
    		currentLogical.addCondtionInterface((ConditionInterface) conditionStack.pop());
    	}
    }
    
    private void endAnd(BasicNode leaving) {
    	if (!subQueryStack.isEmpty()) {
    		//set the current logical to what's on the stack
        	currentSubQueryLogical = (LogicalRelation) logicalStack.pop();
    	}
    	else {
        	//set the current logical to what's on the stack
        	currentLogical = (LogicalRelation) logicalStack.pop();
    	}
    }
    
    private void endOr(BasicNode leaving) {
    	if (!subQueryStack.isEmpty()) {
    		//set the current logical to what's on the stack
        	currentSubQueryLogical = (LogicalRelation) logicalStack.pop();
    	}
    	else {
    		currentLogical = (LogicalRelation) logicalStack.pop();
    	}
    }
    
    private void endStaticItem(BasicNode leaving) {
    	String name = leaving.getAttribute("name");
    	String value = leaving.getAttribute("value");
    	StaticSelectionItem selection = new StaticSelectionItem(name, value);
    	Query query = (Query) queryStack.peek();
    	query.addSelectionItem(selection);
    }
    
    /**
     * callback method used by the SAX Parser when the end tag of an element is
     * detected. Used in this context to parse and store the query information
     * in class variables.
     */
    public void endElement(String uri, String localName, String qName)
            throws SAXException
    {
        //log.debug("leaving: " + localName);
        BasicNode leaving = (BasicNode) elementStack.pop();
        
        if (leaving.getTagName().equals("union")) {
        	endUnion(leaving);
        }
        if (leaving.getTagName().equals("query")) {
        	endQuery(leaving);
        }
        if (leaving.getTagName().equals("subquery")) {
        	endSubquery(leaving);
        }
        if (leaving.getTagName().equals("entity")) {
        	endEntity(leaving);
        }
        if (leaving.getTagName().equals("pathexpr")) {
        	endPathexpr(leaving);        	
        }
        if (leaving.getTagName().equals("attribute")) {
        	endAttribute(leaving);
        }
        if (leaving.getTagName().equals("operator")) {
        	endOperator(leaving);
        }
        if (leaving.getTagName().equals("value")) {
        	endValue(leaving);
        }
        if (leaving.getTagName().equals("where")) {
        	endWhere(leaving);
        }
        if (leaving.getTagName().equals("condition")) {
        	endCondition(leaving);
        }
        if (leaving.getTagName().equals("and")) {
        	endAnd(leaving);
        }
        if (leaving.getTagName().equals("or")) {
        	endOr(leaving);
        }
        if (leaving.getTagName().equals("staticItem")) {
        	endStaticItem(leaving);
        }
        
        String normalizedXML = textBuffer.toString().trim();
        //log.debug("================xml "+normalizedXML);
        xml.append(normalizedXML);
        xml.append("</");
        xml.append(localName);
        xml.append(">");
        //rest textBuffer
        textBuffer = new StringBuffer();
        
        log.debug("</" + localName + ">");

    }
    
    /**
     * Gets query string in xml format (original form)
     */
    public String getXML()
    {
    	return xml.toString();
    }
    

    /**
     * callback method used by the SAX Parser when the text sequences of an xml
     * stream are detected. Used in this context to parse and store the query
     * information in class variables.
     */
    public void characters(char ch[], int start, int length)
    {
      // buffer all text nodes for same element. This is for text was splited
      // into different nodes
      String text = new String(ch, start, length);
      textBuffer.append(text);
      
      //clean up fro debug
      text=text.trim();
      if (text != null && text.length() > 0) {
    	  log.debug(text);
      }

    }

    /**
     * create a String description of the query that this instance represents.
     * This should become a way to get the XML serialization of the query.
     */
    public String toString()
    {
        return xml.toString();
    }

}
