import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

// Needed java classes
import java.io.InputStream;
import java.io.Reader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.io.FileWriter;

import java.util.Properties;

// Needed SAX classes  
import org.xml.sax.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.Parser;
import org.xml.sax.helpers.ParserAdapter;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.XMLReader;
import org.xml.sax.XMLFilter;
import org.xml.sax.ContentHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.ext.DeclHandler;

// Imported Serializer classes
import org.apache.xalan.serialize.Serializer;
import org.apache.xalan.serialize.SerializerFactory;
import org.apache.xalan.templates.OutputProperties;


import javax.xml.parsers.*;

public class EMLConvert
{ 

  static String path = "";
  static String fname = "";

  private static String indentAmount = "2";

  public static void setIndentAmount(String indentAmount) {
    EMLConvert.indentAmount = indentAmount;
  }

  /**
   * Method main
   */
  public static void main(String argv[]) 
            throws TransformerException, TransformerConfigurationException, Exception {
    if (argv.length<1) {
      System.out.println("Must have an argument with name/path of dataset module");
      System.exit(0);
    }

    
    // Instantiate  a TransformerFactory.
  	TransformerFactory tFactory = TransformerFactory.newInstance();
    URIResolver res = new MyURIResolver();   
    if (argv.length>1) {
      path = argv[1];
      if (!path.startsWith("file://")) {
         path = "file://" + path;
      }
      MyURIResolver.setDataDefault(path);
    }
    
    
    tFactory.setURIResolver(res);  


    // Determine whether the TransformerFactory supports The use uf SAXSource 
    // and SAXResult
    if (tFactory.getFeature(SAXSource.FEATURE) && tFactory.getFeature(SAXResult.FEATURE))
    { 
      // Cast the TransformerFactory to SAXTransformerFactory.
      SAXTransformerFactory saxTFactory = ((SAXTransformerFactory) tFactory);	  
      // Create a TransformerHandler for each stylesheet.
      TransformerHandler tHandler1 = saxTFactory.newTransformerHandler(new StreamSource("xsl/triple_info.xsl"));
      TransformerHandler tHandler2 = saxTFactory.newTransformerHandler(new StreamSource("xsl/emlb6toeml2.xsl")); 
      
      Transformer tr = tHandler1.getTransformer(); 
      getPathInfo(argv[0]);
      if (path.length()>0) {
         if (!path.startsWith("file://")) {
            path = "file://" + path;
         }
         MyURIResolver.setDataDefault(path);
      }
      tr.setParameter("packageName", fname); 

      Transformer lastTransformer = tHandler2.getTransformer();
      lastTransformer.setOutputProperty(OutputProperties.S_KEY_INDENT_AMOUNT, EMLConvert.indentAmount);

    
      // Create an XMLReader.
	    XMLReader reader = XMLReaderFactory.createXMLReader();
      reader.setContentHandler(tHandler1);
      reader.setProperty("http://xml.org/sax/properties/lexical-handler", tHandler1);

      tHandler1.setResult(new SAXResult(tHandler2));

//      StringWriter writer = new StringWriter();
      File outfile = new File("eml2out.xml");
      FileWriter writer = new FileWriter(outfile);
      Result result = new StreamResult(writer);

      tHandler2.setResult(result);

	    // Parse the XML input document. The input ContentHandler and output ContentHandler
      // work in separate threads to optimize performance. 

      reader.parse("xsl/triple_info.xsl"); 
//      String resultString = writer.toString();
//      System.out.println(resultString);
    }  
  }

  private static void getPathInfo1(String str) {
    int pos = -1;
    if ((str.indexOf("/")>-1)||(str.indexOf("\\")>-1)) {  
      int pos1 = str.lastIndexOf("/");
      int pos2 = str.lastIndexOf("\\");
      if (pos1>pos2) {
        pos = pos1;  
      }
      else { pos = pos2; }
      path = str.substring(0,pos+1);
      fname = str.substring(pos+1,str.length());  
    }
    else {
      fname = str;
    }
   System.out.println("path: "+path+"  --fname: "+fname);
  } 

  private static void getPathInfo(String str) {
    File nf = new File(str);
    if (nf.exists()) {
      fname = nf.getName();
      path = nf.getAbsolutePath();
   System.out.println("path: "+path+"  --fname: "+fname);
    }
  }

}