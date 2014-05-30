/**
 *     '$RCSfile: EMLParser.java,v $'
 *     Copyright: 1997-2014 Regents of the University of California,
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
 *      '$Author$'
 *        '$Date$'
 *    '$Revision$'
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
package org.ecoinformatics.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

public class HtmlToPdf {
	
	/**
	 * Simple HTML to PDF export method
	 * @param inputFile the path to the input HTML file
	 * @param outputFile the path to write PDF to
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void export(String inputFile, String outputFile) throws IOException, DocumentException {
        OutputStream os = new FileOutputStream(outputFile);
        
        File tidyFile = new File(inputFile + ".tidy");
        OutputStream tidyOut = new FileOutputStream(tidyFile);
        
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.parse(new FileInputStream(inputFile), tidyOut);
        
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(tidyFile);
        renderer.layout();
        renderer.createPDF(os);
        os.close();
        tidyFile.delete();
		
	}
	
	public static void main(String[] args) {
		try {
			HtmlToPdf.export(args[0], args[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
