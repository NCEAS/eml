/**
 *  '$RCSfile: SaxValidateTest.java,v $'
 *  Copyright: 2000 Regents of the University of California and the
 *              National Center for Ecological Analysis and Synthesis
 *    Authors: @authors@
 *    Release: @release@
 *
 *   '$Author: jones $'
 *     '$Date: 2002-09-12 20:25:30 $'
 * '$Revision: 1.4 $'
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

package org.ecoinformatics.emltest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
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
 * A JUnit test for testing instance document validity
 *
 * @author   Matt Jones
 */
public class SaxValidateTest extends TestCase
{
    // The parser to use for validation
    private final static String
                DEFAULT_PARSER = "org.apache.xerces.parsers.SAXParser";
    private final static String TEST_DIR = "@testdir@";

    /**
     * Constructor to build the test
     *
     * @param name  the name of the test method
     */
    public SaxValidateTest(String name)
    {
        super(name);
    }

    /** Establish a testing framework by initializing appropriate objects  */
    public void setUp()
    {
    }

    /** Release any objects after tests are complete  */
    public void tearDown()
    {
    }

    /**
     * Check that the testing framework is functioning properly with a trivial
     * assertion.
     */
    public void initialize()
    {
        assertTrue(true);
        org.apache.xerces.impl.Version version = 
            new org.apache.xerces.impl.Version();
        System.out.println("Using Xerces: " + version.getVersion());
    }

    /**
     * Test if XML documents are valid with respect to their schemas.
     */
    public void testSchemaValid()
    {
        SAXValidate test = new SAXValidate(true);
        assertTrue(test != null);

        File testDir = new File(TEST_DIR);
        Vector fileList = getXmlFiles(testDir);

        for (int i=0; i<fileList.size(); i++) {
            File testFile = (File)fileList.elementAt(i);
            try {
                System.err.println("Validating file: " + testFile.getName());
                test.runTest(new FileReader(testFile), DEFAULT_PARSER);
            } catch (Exception e) {
                if (e instanceof SAXParseException) {
                    SAXParseException spe = (SAXParseException)e;
                    System.err.println("Error on line: " + spe.getLineNumber());
                }
                e.printStackTrace(System.err);
                fail("Document NOT valid!\n\n" + e.getClass().getName() +
                    "(" + e.getMessage() + ")" );
            }
        }
    }

    /**
     * do a test validation on the produced eml-docbook documentation
     */
    public void testDocbook()
    {
      SAXValidate test = new SAXValidate(false);
      assertTrue(test != null);
      System.err.println("Validating the docs/eml-docbook.xml file.");
      File f = new File("docs/eml-docbook.xml");
      if(!f.exists())
      {
        System.err.println("The file eml-docbook.xml is not in the docs " +
             "directory.  You " +
             "must run 'ant docbook' before running this test.");
        fail("The file eml-docbook.xml is not in the docs directory.  You " +
             "must run 'ant docbook' before running this test.");
      }

      try
      {
        test.runTest(new FileReader("docs/eml-docbook.xml"),
                                    DEFAULT_PARSER);
      }
      catch(Exception e)
      {
        String msg = e.getMessage();
        if(msg.equals("Attribute \"xmlns:xs\" must be declared for element " +
                      "type \"book\"."))
        {
          //if its just this message. the file is valid.
          System.err.println("eml-docbook.xsl is valid");
        }
        else
        {
          System.err.println("eml-docbook.xsl is NOT valid: " + msg);
          fail("eml-docbook.xml NOT valid: " + msg);
        }
      }
    }

    /**
     * Create a suite of tests to be run together
     *
     * @return   The test suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite();
        suite.addTest(new SaxValidateTest("initialize"));
        suite.addTest(new SaxValidateTest("testSchemaValid"));
        suite.addTest(new SaxValidateTest("testDocbook"));
        return suite;
    }

    /**
     * Get the list of files in a directory.
     *
     * @param directory the directory to list
     * @return a vector of File objects in the directory
     */
    private Vector getXmlFiles(File directory)
    {
        String[] files = directory.list();
        Vector fileList = new Vector();

        for (int i=0;i<files.length;i++)
        {
            String filename = files[i];
            File currentFile = new File(directory, filename);
            if (currentFile.isFile() && filename.endsWith(".xml")) {
                fileList.addElement(currentFile);
            }
        }
        return fileList;
    }

    /**
     * Validate an XML document using a SAX parser
     */
    private class SAXValidate extends DefaultHandler implements ErrorHandler
    {
        private boolean schemavalidate = false;

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
         * Run the validation test.
         *
         * @param xml           the xml stream to be validated
         * @param parserName    the name of a SAX2 compliant parser class
         * @exception IOException thrown when test files can't be opened
         * @exception ClassNotFoundException  thrown when the SAX Parser
         *                                    class can't be located
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

            parser.setFeature("http://xml.org/sax/features/validation", true);
            if (schemavalidate) {
                parser.setFeature(
                    "http://apache.org/xml/features/validation/schema",
                    true);
            }

            // Parse the document
            parser.parse(new InputSource(xml));
        }
    }
}

