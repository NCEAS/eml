/**
 *  '$RCSfile: SaxValidateTest.java,v $'
 *  Copyright: 2000 Regents of the University of California and the
 *              National Center for Ecological Analysis and Synthesis
 *
 *   '$Author: tao $'
 *     '$Date: 2008-10-09 00:03:43 $'
 * '$Revision: 1.9 $'
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


import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.ecoinformatics.eml.EMLParserServlet;
import org.ecoinformatics.eml.SAXValidate;
import org.xml.sax.SAXParseException;

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
    private final static String TEST_DIR = "./src/test/resources/";
    private final static String MODULES_DIR = TEST_DIR + "/moduleEML/";

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
        //org.apache.xerces.impl.Version version =
        //    new org.apache.xerces.impl.Version();
        //System.out.println("Using Xerces: " + version.getVersion());
    }

    /**
     * Test if XML documents are valid with respect to their schemas.
     */
    public void testSchemaValid()
    {
        SAXValidate test = new SAXValidate(true);
        assertTrue(test != null);

        Vector fileList = getXmlFiles(TEST_DIR);
        Vector modsList = getXmlFiles(MODULES_DIR);
        fileList.addAll(modsList);

        for (int i=0; i<fileList.size(); i++) {
            File testFile = (File)fileList.elementAt(i);
            try {
                System.err.println("Validating file: " + testFile.getName());
                FileReader reader = new FileReader(testFile);
                String namespace= EMLParserServlet.findNamespace(reader);
                reader.close();
                test.runTest(new FileReader(testFile), namespace);
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
     * Create a suite of tests to be run together
     *
     * @return   The test suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite();
        suite.addTest(new SaxValidateTest("initialize"));
        suite.addTest(new SaxValidateTest("testSchemaValid"));
        return suite;
    }

    /**
     * Get the list of files in a directory with XML extensions.
     *
     * @param dirname the directory to list
     * @return a vector of File objects in the directory
     */
    private Vector getXmlFiles(String dirname)
    {
        File directory = new File(dirname);
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
}

