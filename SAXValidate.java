/**
 *      Name: SAXValidate.java
 */

import java.io.*;

import java.net.URL;
import java.net.MalformedURLException;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/** 
 * Validate an XML document using a SAX parser
 */
public class SAXValidate extends DefaultHandler 
                        implements ErrorHandler {

   private static final String
           DEFAULT_PARSER = "org.apache.xerces.parsers.SAXParser";

   /** Construct an instance of the handler class 
    */
   public SAXValidate() {
   }
 
   public void error (SAXParseException exception) throws SAXException {
     System.out.println("ERROR: " + exception.getMessage());
   }

  /**
   * the main routine used to test the SAXValidate utility.
   */
  static public void main(String[] args) {
     
    if (args.length != 1) {
      System.err.println("USAGE: java SAXValidate <xmlfile>");
    } else {

      String filename = args[0];

      SAXValidate test = new SAXValidate();

      try {
        test.runTest(new FileReader(new File(filename).toString()), 
                   DEFAULT_PARSER);
      } catch (Exception e) {
        System.err.println("Error running test.");
        System.err.println(e.getMessage());
        e.printStackTrace(System.err);
      }
    }
  }

  /**
   * run the test
   *
   * @param xml the xml stream to be loaded into the database
   * @param parserName the name of a SAX2 compliant parser class
   */
  public void runTest(Reader xml, String parserName)
                  throws IOException, ClassNotFoundException {

    try {
  
      // Get an instance of the parser
      XMLReader parser = XMLReaderFactory.createXMLReader(parserName);
 
      // Set Handlers in the parser
      parser.setContentHandler((ContentHandler)this);
      parser.setErrorHandler((ErrorHandler)this);

      parser.setFeature("http://xml.org/sax/features/validation", true);

      // Parse the document
      parser.parse(new InputSource(xml));

    } catch (SAXParseException e) {
      System.err.println(e.getMessage());
    } catch (SAXException e) {
      System.err.println(e.getMessage());
    } catch (Exception e) {
      System.err.println(e.toString());
    }
  }
}
