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
 *     '$Date: 2008-07-30 00:31:56 $'
 * '$Revision: 1.1 $'
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
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.ecoinformatics.datamanager.database.Query;
import org.ecoinformatics.datamanager.database.Union;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * A Class that represents a structured query, and can be constructed from an
 * XML serialization conforming to
 *
 * @see pathquery.dtd. The printSQL() method can be used to print a SQL
 *      serialization of the query.
 */
public class DataquerySpecification extends DefaultHandler
{

    /** Identifier for this query document */
    private String meta_file_id;

    /** Title of this query */
    private String queryTitle;

    /** A string buffer to stored normalized query (Sometimes, the query have 
     * a value like "&", it will cause problem in html transform). So we need a
     * normalized query xml string.
     */
    private StringBuffer xml = new StringBuffer();

    // Query data structures used temporarily during XML parsing
    private Stack elementStack;

    private Stack queryStack;
    
    private Stack datapackageStack;
    
    private Stack attributeStack;
    
    private Stack entityStack;

    private String currentValue;

    private String currentPathexpr;

    private String parserName = null;

    public static final String ATTRIBUTESYMBOL = "@";

    public static final char PREDICATE_START = '[';

    public static final char PREDICATE_END = ']';

    private StringBuffer textBuffer = new StringBuffer();
    
    private static Logger logMetacat = Logger.getLogger(DataquerySpecification.class);
    
    private Query query = null;
    
    private Union union = null;

    /**
     * construct an instance of the QuerySpecification class
     *
     * @param queryspec
     *            the XML representation of the query (should conform to
     *            pathquery.dtd) as a Reader
     * @param parserName
     *            the fully qualified name of a Java Class implementing the
     *            org.xml.sax.XMLReader interface
     */
    public DataquerySpecification(Reader queryspec, String parserName) throws IOException
    {
        super();

        // Initialize the class variables
        elementStack = new Stack();
        queryStack = new Stack();
        entityStack = new Stack();
        attributeStack = new Stack();
        this.parserName = parserName;

        // Initialize the parser and read the queryspec
        XMLReader parser = initializeParser();
        if (parser == null) {
            System.err.println("SAX parser not instantiated properly.");
        }
        try {
            parser.parse(new InputSource(queryspec));
        } catch (SAXException e) {
            System.err.println("error parsing data in "
                    + "QuerySpecification.QuerySpecification");
            System.err.println(e.getMessage());
        }
    }

    /**
     * construct an instance of the QuerySpecification class
     *
     * @param queryspec
     *            the XML representation of the query (should conform to
     *            pathquery.dtd) as a String
     * @param parserName
     *            the fully qualified name of a Java Class implementing the
     *            org.xml.sax.Parser interface
     */
    public DataquerySpecification(String queryspec, String parserName) throws IOException
    {
        this(new StringReader(queryspec), parserName);
    }

    /**
     * construct an instance of the QuerySpecification class which don't need
     * to parser a xml document
     *
     * @param accNumberSeparator
     *            the separator between doc version
     */
    public DataquerySpecification(String accNumberSeparator) throws IOException
    {
        // Initialize the class variables
        elementStack = new Stack();
        queryStack = new Stack();
    }

    /**
     * Accessor method to return the identifier of this Query
     */
    public String getIdentifier()
    {
        return meta_file_id;
    }

    /**
     * Accessor method to return the title of this Query
     */
    public String getQueryTitle()
    {
        return queryTitle;
    }

    /**
     * method to set the title of this query
     */
    public void setQueryTitle(String title)
    {
        this.queryTitle = title;
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

    /**
     * callback method used by the SAX Parser when the start tag of an element
     * is detected. Used in this context to parse and store the query
     * information in class variables.
     */
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException
    {
        logMetacat.debug("start at startElement "+localName);
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
            Query query = new Query();
            queryStack.push(query);
        }
        if (currentNode.getTagName().equals("datapackage")) {
        	DataPackage datapackage = new DataPackage(currentNode.getAttribute("id"));
        	//TODO parse and load the datapackage
        	
        	//save it for later
        	datapackageStack.push(datapackage);
        	
        }
        if (currentNode.getTagName().equals("entity")) {
        	//TODO lookup by name rather than index?
        	DataPackage datapackage = (DataPackage) datapackageStack.peek();
        	int index = Integer.parseInt(currentNode.getAttribute("index"));
        	Entity entity = datapackage.getEntityList()[index];
        	entityStack.push(entity);
        }
        if (currentNode.getTagName().equals("attribute")) {
        	DataPackage datapackage = (DataPackage) datapackageStack.peek();
        	Entity entity = (Entity) entityStack.peek();
        	int index = Integer.parseInt(currentNode.getAttribute("index"));
        	Attribute attribute = entity.getAttributes()[index];
        	attributeStack.push(attribute);
        }
        logMetacat.debug("end in startElement "+localName);
    }

    /**
     * callback method used by the SAX Parser when the end tag of an element is
     * detected. Used in this context to parse and store the query information
     * in class variables.
     */
    public void endElement(String uri, String localName, String qName)
            throws SAXException
    {
    	logMetacat.debug("start in endElement "+localName);
        BasicNode leaving = (BasicNode) elementStack.pop();
        
        if (leaving.getTagName().equals("entity")) {
        	Entity entity = (Entity) entityStack.peek();
        	//assemble all the attributes
        	while (!attributeStack.empty()) {
        		Attribute attribute = (Attribute) attributeStack.pop();
        		entity.add(attribute);
        	}
        } 
        else if (leaving.getTagName().equals("datapackage")) {
        	DataPackage dataPackage = (DataPackage) datapackageStack.peek();
        	//assemble all the attributes
        	while (!entityStack.empty()) {
            	Entity entity = (Entity) entityStack.pop();
        		dataPackage.add(entity);
        	}
        } 
        String normalizedXML = textBuffer.toString().trim();
        logMetacat.debug("================xml "+normalizedXML);
        xml.append(normalizedXML);
        xml.append("</");
        xml.append(localName);
        xml.append(">");
        //rest textBuffer
        textBuffer = new StringBuffer();

    }
    
    /**
     * Gets normailized query string in xml format, which can be transformed
     * to html
     */
    public String getNormalizedXMLQuery()
    {
    	//System.out.println("normailized xml \n"+xml.toString());
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
      logMetacat.debug("the text in characters "+text);
      textBuffer.append(text);

    }

   public static String printRelationSQL(String docid)
    {
        StringBuffer self = new StringBuffer();
        self.append("select subject, relationship, object, subdoctype, ");
        self.append("objdoctype from xml_relation ");
        self.append("where docid like '").append(docid).append("'");
        return self.toString();
    }

    public static String printGetDocByDoctypeSQL(String docid)
    {
        StringBuffer self = new StringBuffer();

        self.append("SELECT docid,docname,doctype,");
        self.append("date_created, date_updated ");
        self.append("FROM xml_documents WHERE docid IN (");
        self.append(docid).append(")");
        return self.toString();
    }

    /**
     * create a String description of the query that this instance represents.
     * This should become a way to get the XML serialization of the query.
     */
    public String toString()
    {
        return "meta_file_id=" + meta_file_id + "\n" + query;
        //DOCTITLE attr cleared from the db
        //return "meta_file_id=" + meta_file_id + "\n" +
        //"querytitle=" + querytitle + "\n" + query;
    }

    /** A method to get rid of attribute part in path expression */
    public static String newPathExpressionWithOutAttribute(String pathExpression)
    {
        if (pathExpression == null) { return null; }
        int index = pathExpression.lastIndexOf(ATTRIBUTESYMBOL);
        String newExpression = null;
        if (index != 0) {
            newExpression = pathExpression.substring(0, index - 1);
        }
        logMetacat.info("The path expression without attributes: "
                + newExpression);
        return newExpression;
    }

    /** A method to get attribute name from path */
    public static String getAttributeName(String path)
    {
        if (path == null) { return null; }
        int index = path.lastIndexOf(ATTRIBUTESYMBOL);
        int size = path.length();
        String attributeName = null;
        if (index != 1) {
            attributeName = path.substring(index + 1, size);
        }
        logMetacat.info("The attirbute name from path: "
                + attributeName);
        return attributeName;
    }

}
