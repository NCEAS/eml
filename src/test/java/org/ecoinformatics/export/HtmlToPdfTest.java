/**
 *  '$RCSfile: SaxValidateTest.java,v $'
 *  Copyright: 2000 Regents of the University of California and the
 *              National Center for Ecological Analysis and Synthesis
 *    Authors: @authors@
 *    Release: @release@
 *
 *   '$Author$'
 *     '$Date$'
 * '$Revision$'
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

package org.ecoinformatics.export;


import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

/**
 * A JUnit test for testing PDF export
 *
 * @author   Matt Jones
 */
public class HtmlToPdfTest extends TestCase
{

    private final static String TEST_DIR = "@testdir@";

    /**
     * Constructor to build the test
     *
     * @param name  the name of the test method
     */
    public HtmlToPdfTest(String name)
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
    }

    /**
     * Test HTML to PDF conversion
     */
    public void testExport()
    {
    	try {
    		
	        // transform EML to HTML
    		String workingPath = "build/tests/";
    		
    		String emlFileName = "eml-sample"; 
    		
	    	String emlFileExtension = ".xml";
	    	
	    	String emlFile = workingPath + emlFileName + emlFileExtension;
	    	
	    	String xslFile = "style/eml/eml.xsl";
	    	
	    	String htmlFile = workingPath + emlFileName + ".html";
	    	
	    	Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new File(xslFile)));
	    	 // add some property for style sheet
            transformer.clearParameters();
            transformer.setParameter("href_path_extension", ".html");
            transformer.setParameter("package_id",          "test.1.1");
            transformer.setParameter("package_index_name",  "metadata");
            transformer.setParameter("qformat",         "default");
            transformer.setParameter("entitystyle",          "default");
            transformer.setParameter("stylePath",            ".");
            transformer.setParameter("displaymodule",            "printall");

	    	transformer.transform(new StreamSource(new File(emlFile)), new StreamResult(new File(htmlFile)));
	    	
	    	// convert HTML to PDF
	    	String pdfFile = workingPath + emlFileName + ".pdf";
	    	
			HtmlToPdf.export(htmlFile, pdfFile);
			
			// TODO: check that it worked?
			
		} catch (Exception e) {
			// oops
			e.printStackTrace();
			fail(e.getMessage());
		}
    	
    }

    
}

