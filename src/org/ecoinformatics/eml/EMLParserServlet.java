/**
 *     '$RCSfile: EMLParserServlet.java,v $'
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
 *      '$Author: berkley $'
 *        '$Date: 2002-10-29 18:00:20 $'
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

import java.io.*;
import java.lang.*;
import java.util.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.zip.*;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUtils;
import javax.servlet.ServletOutputStream;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Servlet interface for the EMLParser
 */
public class EMLParserServlet extends HttpServlet
{

  private ServletConfig servletconfig = null;
  private ServletContext context = null;
  private HttpServletRequest request;
  private static HttpServletResponse response;
  private static PrintWriter out = null;
  private Hashtable params = new Hashtable();
  private static final String namespaces = "@namespaces@";

  /**
   * Initialize the servlet
   */
  public void init(ServletConfig servletconfig) throws ServletException
  {
    try
    {
      super.init(servletconfig);
      this.servletconfig = servletconfig;
      this.context = servletconfig.getServletContext();
      System.out.println("Starting EMLParserServlet");
    }
    catch (ServletException ex)
    {
      throw ex;
    }
  }

  /**
   * Destroy the servlet
   */
  public void destroy()
  {
    System.out.println("Destroying EMLParserServlet");
  }

  /** Handle "GET" method requests from HTTP clients */
  public void doGet (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    // Process the data and send back the response
    handleGetOrPost(request, response);
  }

  /** Handle "POST" method requests from HTTP clients */
  public void doPost( HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    // Process the data and send back the response
    handleGetOrPost(request, response);
  }

  /**
   * Control servlet response depending on the action parameter specified
   */
  private void handleGetOrPost(HttpServletRequest request,
                               HttpServletResponse response)
                               throws ServletException, IOException
  {
    this.request = request;
    this.response = response;
    StringBuffer html = new StringBuffer();
    out = response.getWriter();
    String ctype = request.getContentType();
    InputStream fileToParse = null;
    File tempfile = null;

    html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD html 4.0//EN\">");
    html.append("<html>");
    html.append("<head>");
    html.append("<title>EML ID and References Parser</title>");
    html.append("<link rel=\"stylesheet\" type=\"text/css\" ");
    html.append("href=\"http://knb.ecoinformatics.org/default.css\">");
    html.append("</head>");
    html.append("<body>");
    html.append("<table class=\"tabledefault\" width=\"100%\">");
    html.append("<tbody>");
    html.append("<tr>");
    html.append("<td rowspan=\"2\">");
    html.append("<img src=\"http://knb.ecoinformatics.org/images/KNBLogo.gif\">");
    html.append("</td>");
    html.append("<td colspan=\"7\"><div class=\"title\">");
    html.append("EML ID and References Parser</div></td>");
    html.append("</tr>");
    html.append("<tr>");
    html.append("<td><a href=\"http://knb.ecoinformatics.org/\" ");
    html.append("class=\"toollink\"> KNB Home </a></td>");
    html.append("<td><a href=\"http://knb.ecoinformatics.org/data.html\" ");
    html.append("class=\"toollink\"> Data </a></td>");
    html.append("<td><a href=\"http://knb.ecoinformatics.org/people.html\" ");
    html.append("class=\"toollink\"> People </a></td>");
    html.append("<td><a href=\"http://knb.ecoinformatics.org/informatics\" ");
    html.append("class=\"toollink\"> Informatics </a></td>");
    html.append("<td><a href=\"http://knb.ecoinformatics.org/biodiversity\" ");
    html.append("class=\"toollink\"> Biocomplexity </a></td>");
    html.append("<td><a href=\"http://knb.ecoinformatics.org/education\" ");
    html.append("class=\"toollink\"> Education </a></td>");
    html.append("<td><a href=\"http://knb.ecoinformatics.org/software\" ");
    html.append("class=\"toollink\"> Software </a></td>");
    html.append("</tr>");
    html.append("</tbody>");
    html.append("</table>");
    html.append("<hr>");

    HttpSession sess = request.getSession(true);
    String sess_id = "";
    try
    { //get the cookie for the session.
      sess_id = (String)sess.getId();
    }
    catch(IllegalStateException ise)
    {
      System.out.println("error in handleGetOrPost: this shouldn't " +
                         "happen: the session should be valid: " +
                         ise.getMessage());
    }

    File tempdir = new File("@tempdir@");
    if(!tempdir.exists())
    {
      tempdir.mkdirs();
    }

    tempfile = new File("@tempdir@/.tmpfile." + sess_id);

    if (ctype != null && ctype.startsWith("multipart/form-data"))
    { //deal with multipart encoding of the package zip file
      try
      {
        fileToParse = handleGetFile(request, response);
        int c = fileToParse.read();
        FileOutputStream fos = new FileOutputStream(tempfile);
        while(c != -1)
        {
          fos.write(c);
          c = fileToParse.read();
        }
        fos.flush();
        fos.close();
      }
      catch(Exception e)
      {
        out.println("<html><body><h1>Error handling multipart data: " +
                    e.getMessage() + "</h1></body></html>");
        System.out.println("Error handling multipart data: " + e.getMessage());
        e.printStackTrace();
      }
    }
    else
    {
      Enumeration paramlist = request.getParameterNames();
      while (paramlist.hasMoreElements())
      {
        String name = (String)paramlist.nextElement();
        Object value = request.getParameterValues(name);
        params.put(name,value);
      }
    }

    String action = ((String[])params.get("action"))[0];

    if(action.equals("parse"))
    { //parse action
      html.append(parse(tempfile));
    }
    else if(action.equals("textparse"))
    {
      String doctext = ((String[])params.get("doctext"))[0];
      if(doctext == null || doctext.trim().equals(""))
      {
        html.append("<h2>Error.  Submitted document is null.</h2>");
      }
      else
      {
        StringReader sr = new StringReader(doctext);
        FileWriter fw = new FileWriter(tempfile);
        int c = sr.read();
        while(c != -1)
        {
          fw.write(c);
          c = sr.read();
        }
        fw.flush();
        fw.close();

        html.append(parse(tempfile));
      }
    }
    else
    {
      html.append("<h2>Error.  Action '").append(action);
      html.append("' not registered</h2>");
    }

    tempfile.delete();

    html.append("<hr><a href=\"/emlparser\">Back</a> to the previous page.");
    html.append("</body></html>");
    response.setContentType("text/html");
    out.println(html.toString());
    out.flush();
  }

  private String parse(File tempfile)
  {
    StringBuffer html = new StringBuffer();

    try
    {
      if(tempfile != null)
      {
        EMLParser parser = new EMLParser(tempfile,
                           new File("@servletconfigfile@"));
        html.append("<h2>Document is EML valid.</h2><p>There ");
        html.append("were no EML errors found in your document.</p>");
      }
      else
      {
        html.append("<h2>Error: The file sent to the parser was null.</h2>");
      }
    }
    catch(Exception e)
    {
      html.append("<h2>EML Errors Found</h2><p>The following errors were found:");
      html.append("</p><p>").append(e.getMessage()).append("</p>");
    }

    try
    {
      SAXValidate validator = new SAXValidate(true);
      validator.runTest(new FileReader(tempfile), "DEFAULT", namespaces);
      html.append("<hr><h2>Document is XML-schema valid.</h2>");
      html.append("<p>There were no XML errors found in your document.</p>");
    }
    catch(SAXException se)
    {
      html.append("<hr><h2>XML-Schema Errors Found</h2><p>The following errors were ");
      html.append("found:</p><p>").append(se.getMessage()).append("</p>");
    }
    catch(IOException ioe)
    {
      html.append("<hr><h2>IOException: Error reading file</h2>");
      html.append("<p>").append(ioe.getMessage()).append("</p>");
    }
    catch(ClassNotFoundException cnfe)
    {
      html.append("<hr><h2>Parser class not found</h2>");
      html.append("<p>").append(cnfe.getMessage()).append("</p>");
    }

    return html.toString();
  }

   /**
   * This method deals with getting the zip file from the client, unzipping
   * it, then running the process on it.
   */
  private InputStream handleGetFile(HttpServletRequest request,
                               HttpServletResponse response)
                               throws Exception
  {
    Hashtable fileList = new Hashtable();
    try
    {
      MultipartParser mp = new MultipartParser(request, 1024 * 1024 * 8);
      Part part;
      while ((part = mp.readNextPart()) != null)
      {
        String name = part.getName();
        if (part.isParam())
        { // it's a parameter part
          ParamPart paramPart = (ParamPart) part;
          String value = paramPart.getStringValue();
          String[] s = {value};
          params.put(name, s);
        }
        else if (part.isFile())
        { // it's a file part
          FilePart filePart = (FilePart) part;
          fileList.put(name, filePart);
          // Stop once the first file part is found, otherwise going onto the
          // next part prevents access to the file contents.  So...for upload
          // to work, the datafile must be the last part
          break;
        }
      }
    }
    catch (Exception ioe)
    {
      throw ioe;
    }

    //now that we have the file, do some checking and get the files we need
    //out of the zip file.
    FilePart fp = (FilePart)fileList.get("filename");
    return fp.getInputStream();
  }
}
