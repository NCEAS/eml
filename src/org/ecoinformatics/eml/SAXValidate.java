/**
 *  '$RCSfile: SAXValidate.java,v $'
 *  Copyright: 2000 Regents of the University of California and the
 *              National Center for Ecological Analysis and Synthesis
 *    Authors: @authors@
 *    Release: @release@
 *
 *   '$Author: berkley $'
 *     '$Date: 2002-10-03 16:33:10 $'
 * '$Revision: 1.2 $'
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
  public void runTest(Reader xml, String parserName)
           throws IOException, ClassNotFoundException,
           SAXException, SAXParseException
  {

    // Get an instance of the parser
    XMLReader parser = XMLReaderFactory.createXMLReader(parserName);

    // Set Handlers in the parser
    parser.setContentHandler((ContentHandler)this);
    parser.setErrorHandler((ErrorHandler)this);
    parser.setFeature("http://xml.org/sax/features/namespaces", true);
    parser.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
    parser.setFeature("http://xml.org/sax/features/validation", true);
    if (schemavalidate) {
        parser.setFeature(
            "http://apache.org/xml/features/validation/schema",
            true);
    }
    //parser.setEntityResolver(new EML2Resolver());

    // Parse the document
    parser.parse(new InputSource(xml));
  }


  /**
   * class to resolve eml entities
   */

  private class EML2Resolver implements EntityResolver
  {

   /**
    * resolves the entity
    *
    * this doesn't seem to work with schemas.  it returns the public id as
    * the file part of the xsi:schemaLocation and null as the system id.
    */
   public InputSource resolveEntity (String publicId, String systemId)
   {
     System.out.println("attempting to resolve entity: " + publicId + " " +
                        systemId);
     if (systemId.equals("eml://ecoinformatics.org/eml-2.0.0rc1"))
     {
       System.out.println("resolving entity for eml");
       // return a special input source
       //MyReader reader = new MyReader();
       //return new InputSource(reader);
       return null;
     }
     else
     {
       // use the default behaviour
       return null;
     }
   }
 }

}
