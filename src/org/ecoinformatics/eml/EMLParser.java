/**
 *  '$RCSfile: EMLParser.java,v $'
 *  Copyright: 2000 Regents of the University of California and the
 *             National Center for Ecological Analysis and Synthesis
 *    Authors: @authors@
 *    Release: @release@
 *
 *   '$Author: berkley $'
 *     '$Date: 2002-09-24 18:33:32 $'
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

package org.ecoinformatics.eml;

import java.io.*;
import java.util.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.InputSource;

import org.apache.xpath.XPathAPI;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.DocumentType;
import org.apache.xerces.dom.DocumentTypeImpl;

import edu.ucsb.nceas.configxml.*;

/**
 * This is a SAX parser to validate EML packages.  This parser will validate
 * an EML package with references based on the following rules:
 * <ul>
 *  <li>
 *  If a user wants to reuse content to indicate the repetition of an object,
 *  a reference must be used. you cannot have two identical ids in a document.
 *  </li>
 *  <li>
 *  "Local scope" is defined as identifiers unique only to a single instance
 *  document (if a document does not have a system or if scope is set to 'local'
 *  then all ids are defined as distinct content).
 *  </li>
 *  <li>
 *  System scope is defined as identifiers unique to an entire data management
 *  system (if two documents share a system string, then any IDs in those two
 *  documents that are identical refer to the same object).
 *  </li>
 *  <li>
 *  If an element references another element, it must not have an ID.
 *  </li>
 *  <li>
 *  All EML packages must have the 'eml' module as the root.
 *  </li>
 *  <li>
 *  The system and scope attribute are always optional except for at the
 *  'eml' module where the scope attribute is fixed as 'system'. The scope
 *  attribute defaults to 'local' for all other modules.
 *  </li>
 * </ul>
 */
public class EMLParser extends DefaultHandler
{
  private String parserName;
  private ConfigXML config;
  private Key[] keys;
  private Keyref[] keyrefs;

  /**
   * Create a new Pipeline by parsing an XML specification of the Pipeline.
   *
   * @param xml the xml specification of the Pipeline
   * @param parserName the class name of a SAX parser for reading the XML
   */
  public EMLParser(Reader xml, String parserName) throws EMLParserException
  {
    try
    {
      config = new ConfigXML("@config.file@");
    }
    catch(Exception e)
    {
      throw new EMLParserException("Config file not found: " + e.getMessage());
    }

    parseConfig();

    /*for(int i=0; i<keys.length; i++)
    {
      System.out.println(keys[i].toString());
    }
    System.out.println();
    for(int i=0; i<keys.length; i++)
    {
      System.out.println(keyrefs[i].toString());
    }*/

    this.parserName = parserName;
    XMLReader parser = initializeParser(parserName);
    if (parser == null)
    {
      throw new EMLParserException("SAX parser not instantiated properly.");
    }

    try
    {
      parser.parse(new InputSource(xml));
    }
    catch (SAXException e)
    {
      throw new EMLParserException(e.getMessage());
    }
    catch (IOException ioe)
    {
      throw new EMLParserException(ioe.getMessage());
    }
  }

  /**
   * Create an XML parser to read the config file using SAX.
   */
  private XMLReader initializeParser(String parserName)
  {
    XMLReader parser = null;
    // Set up the SAX document handlers for parsing
    try
    {
      // Get an instance of the parser
      parser = XMLReaderFactory.createXMLReader(parserName);
      // Set the ContentHandler to this instance
      parser.setContentHandler(this);
      // Set the error Handler to this instance
      parser.setErrorHandler(this);
    }
    catch (Exception e)
    {
      System.err.println("Error in initializeParser " +
                       e.toString());
      e.printStackTrace();
    }
    return parser;
  }

  /**
   * This method is called by SAX when a start tag is encountered for
   * an element.
   */
  public void startElement(String uri, String localName, String qName,
                           Attributes attributes) throws SAXException
  {

  }

  /**
   * This method is called by SAX when an end tag is encountered for
   * an element.
   */
  public void endElement(String uri, String localName, String qName)
              throws SAXException
  {
  }

  /**
   * This method is called by SAX when character data is found
   * while processing an element.
   */
  public void characters(char[] ch, int start, int length)
  {

  }

  private void parseConfig()
  {
    try
    { //parse the keys and keyrefs out of the config file
      NodeList keyNL = config.getPathContent("//key");
      keys = new Key[keyNL.getLength()];
      NodeList keyrefNL = config.getPathContent("//keyref");
      keyrefs = new Keyref[keyrefNL.getLength()];

      //get the keys
      for(int i=0; i<keyNL.getLength(); i++)
      {
        String name = "";
        String selector = "";
        String field = "";

        Node n = keyNL.item(i);

        //get the name
        NamedNodeMap atts = n.getAttributes();
        Node nameatt = atts.getNamedItem("name");
        name = nameatt.getNodeValue();

        //get the selector and field
        NodeList keychildren = n.getChildNodes();
        for(int j=0; j<keychildren.getLength(); j++)
        {
          Node kc = keychildren.item(j);
          if(kc.getNodeName().equals("selector"))
          {
            NamedNodeMap selectoratts = kc.getAttributes();
            Node selectoratt = selectoratts.getNamedItem("xpath");
            selector = selectoratt.getNodeValue();
          }
          else if(kc.getNodeName().equals("field"))
          {
            NamedNodeMap fieldatts = kc.getAttributes();
            Node fieldatt = fieldatts.getNamedItem("xpath");
            field = fieldatt.getNodeValue();
          }
        }

        if(name.equals("") | selector.equals("") | field.equals(""))
        {
          throw new EMLParserException("Error in config file.  All keys " +
                                       "must have a name, selector and field.");
        }

        keys[i] = new Key(name, selector, field);
      }

      //get the keyrefs
      for(int i=0; i<keyrefNL.getLength(); i++)
      {
        String name = "";
        String refer = "";
        String selector = "";
        String field = "";

        Node n = keyrefNL.item(i);

        //get the name and refer
        NamedNodeMap atts = n.getAttributes();
        Node nameatt = atts.getNamedItem("name");
        name = nameatt.getNodeValue();
        Node referatt = atts.getNamedItem("refer");
        refer = referatt.getNodeValue();

        //get the selector and field
        NodeList keychildren = n.getChildNodes();
        for(int j=0; j<keychildren.getLength(); j++)
        {
          Node kc = keychildren.item(j);
          if(kc.getNodeName().equals("selector"))
          {
            NamedNodeMap selectoratts = kc.getAttributes();
            Node selectoratt = selectoratts.getNamedItem("xpath");
            selector = selectoratt.getNodeValue();
          }
          else if(kc.getNodeName().equals("field"))
          {
            NamedNodeMap fieldatts = kc.getAttributes();
            Node fieldatt = fieldatts.getNamedItem("xpath");
            field = fieldatt.getNodeValue();
          }
        }

        if(name.equals("") | selector.equals("") |
           field.equals("") | refer.equals(""))
        {
          throw new EMLParserException("Error in config file.  All keys " +
                                       "must have a name, selector and field.");
        }

        keyrefs[i] = new Keyref(name, refer, selector, field);
      }
    }
    catch(Exception e)
    {
      throw new EMLParserException("Error parsing keys and keyrefs in config " +
                                   "file: " + e.getMessage());
    }
  }

  /**
   * class to represent a key
   */
  private class Key
  {
    protected String selector; //xpath expression for the selector
    protected String field;    //xpath expression for the field in the selector
    protected String name;     //name of the key

    Key(String name, String selector, String field)
    {
      this.name = name;
      this.selector = selector;
      this.field = field;
    }

    public String toString()
    {
      String s = "name: " + name + " selector: " + selector + " field: " + field;
      return s;
    }
  }

  /**
   * class to represent a keyref
   */
  private class Keyref
  {
    protected String name;     //name of the keyref
    protected String refer;    //the key that we are refering to
    protected String selector; //the selector for the keyref
    protected String field;    //the field in the selector

    Keyref(String name, String refer, String selector, String field)
    {
      this.name = name;
      this.refer = refer;
      this.selector = selector;
      this.field = field;
    }

    public String toString()
    {
      String s = "name: " + name + " refer: " + refer + " selector: " +
                  selector + " field: " + field;
      return s;
    }
  }
}
