/**
 *     '$RCSfile: SAXValidate.java,v $'
 *     Copyright: 1997-2002 Regents of the University of California,
 *                          University of New Mexico, and
 *                          Arizona State University
 *      Sponsors: National Center for Ecological Analysis and Synthesis and
 *                Partnership for Interdisciplinary Studies of Coastal Oceans,
 *                   University of California Santa Barbara
 *                Long-Term Ecological Research Network Office,
 *                   University of New Mexico
 *                Center for Environmental Studies, Arizona State University
 * Other funding: National Science Foundation (see README for details)
 *                The David and Lucile Packard Foundation
 *   For Details: http://knb.ecoinformatics.org/
 *
 *      '$Author: tao $'
 *        '$Date: 2008-07-10 22:33:15 $'
 *    '$Revision: 1.8 $'
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.ecoinformatics.eml;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import edu.ucsb.nceas.configxml.*;

/**
 * Validate an XML document using a SAX parser
 */
public class SAXValidate extends DefaultHandler implements ErrorHandler
{
  private boolean schemavalidate = false;
  public final static String
               DEFAULT_PARSER = "org.apache.xerces.parsers.SAXParser";

  /**
   * Construct an instance of the handler class
   *
   * @param validateschema  Description of Parameter
   */
  public SAXValidate(boolean validateschema)
  {
    this.schemavalidate = validateschema;
  }

  /**
   * Method for handling errors during a parse
   *
   * @param exception         The parsing error
   * @exception SAXException  Description of Exception
   */
  public void error(SAXParseException exception) throws SAXException
  {
    throw exception;
  }

  /**
   * Method for handling warnings during a parse
   *
   * @param exception         The parsing error
   * @exception SAXException  Description of Exception
   */
  public void warning(SAXParseException exception)
    throws SAXException
  {
    throw new SAXException("WARNING: " + exception.getMessage());
  }

  /**
   * Run the validation test using the DEFAULT_PARSER defined in this
   * class.
   * @param xml the xml document to parse
   * @exception IOException thrown when test files can't be opened
   * @exception ClassNotFoundException  thrown when the SAX Parser
   *                                    class can't be located
   * @exception SAXException
   * @exception SAXParserException
   */
  public void runTest(Reader xml) throws IOException, ClassNotFoundException,
                                  SAXException, SAXParseException
  {
    runTest(xml, DEFAULT_PARSER);
  }

  public void runTest(Reader xml, String parserName)throws IOException,
                                  ClassNotFoundException,
                                  SAXException, SAXParseException
  {
    ConfigXML config;
    String namespaces;
    URL configFile = getClass().getResource("@configfileinemljar@");
    try
    {
      config = new ConfigXML(configFile.openStream());
      namespaces = config.get("namespaces", 0);
    }
    catch(Exception e)
    {
      throw new SAXException("Config file not found: " + e.getMessage());
    }
    
    runTest(xml, parserName, namespaces);
  }

  /**
   * Run the validation test.
   *
   * @param xml           the xml stream to be validated
   * @param parserName    the name of a SAX2 compliant parser class
   * @exception IOException thrown when test files can't be opened
   * @exception ClassNotFoundException  thrown when the SAX Parser
   *                                    class can't be located
   * @exception SAXException
   * @exception SAXParserException
   */
  public void runTest(Reader xml, String parserName, String schemaLocation)
           throws IOException, ClassNotFoundException,
           SAXException, SAXParseException
  {

    // Get an instance of the parser
    XMLReader parser;
    if(parserName.equals("DEFAULT"))
    {
      parser = XMLReaderFactory.createXMLReader(DEFAULT_PARSER);
    }
    else
    {
      parser = XMLReaderFactory.createXMLReader(parserName);
    }

    // Set Handlers in the parser
    parser.setContentHandler((ContentHandler)this);
    parser.setErrorHandler((ErrorHandler)this);
    parser.setFeature("http://xml.org/sax/features/namespaces", true);
    parser.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
    parser.setFeature("http://xml.org/sax/features/validation", true);
    parser.setProperty(
      "http://apache.org/xml/properties/schema/external-schemaLocation",
      schemaLocation);
    if (schemavalidate) {
        parser.setFeature(
            "http://apache.org/xml/features/validation/schema",
            true);
        parser.setFeature(
                "http://apache.org/xml/features/validation/schema-full-checking",
                true);
    }
    // Parse the document
    parser.parse(new InputSource(xml));
  }
}
