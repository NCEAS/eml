import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory; 
import javax.xml.transform.TransformerException;
import javax.xml.transform.Source;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import java.net.URL;
import java.io.*;

public class MyURIResolver implements URIResolver{   

  static String baseDefault = "./"; 
  static String dataDefault = ""; 

  static public void setDataDefault(String s) {
    dataDefault = s;
  }
 
 public Source resolve(String href, String base) throws TransformerException { 
  URL u = null;
  URL uDefault = null;
  try{  
    if ((base!=null)&&(isXMLXSL(href))) {
     System.out.println("base: "+base+ " --href: "+href);
     URL context = new URL(base); 
      baseDefault = base;
      u = new URL(context, href);
    } else if (isXMLXSL(href)) {
      System.out.println("baseDefault: "+baseDefault+ " --href: "+href);
      uDefault = new URL(baseDefault);
      u = new URL(uDefault, href); 
    } else {
      System.out.println("dataDefault: "+dataDefault+ " --href: "+href); 
      uDefault = new URL(dataDefault);
      u = new URL(uDefault, href); 
    }
    System.out.println("----URL:"+u.toString());
    InputStream is = u.openStream(); 
    InputStreamReader isr = new InputStreamReader(is);

    StringWriter sw = new StringWriter();
    int c;

    while ((c = isr.read()) != -1) {
        sw.write(c);
    } 
    sw.flush();
    is.close();
    sw.close();
    String sws = sw.toString();

    String outStr = removeDOCTYPE(sws);
    
    StringReader newStr = new StringReader(outStr);

    StreamSource stSrc = new StreamSource(newStr); 
    return stSrc;

    }
    catch (Exception e) { 
      System.out.println("-----Exception triggered in MyURIResolver-----");
      return null;
    }
  }

  private String removeDOCTYPE(String in) {  
    String ret = "";
    int startpos = in.indexOf("<!DOCTYPE");
    if (startpos>-1) {
      int stoppos = in.indexOf(">", startpos+10);   
      ret = in.substring(0,startpos) + in.substring(stoppos+1,in.length());
    } else {
      return in;
    }
    return ret;  
  }
   
  private boolean isXMLXSL(String str) {
    boolean ret = false;
    if ((str.indexOf(".xsl")>-1)||(str.indexOf(".XSL")>-1)) ret = true;
    if ((str.indexOf(".xml")>-1)||(str.indexOf(".XML")>-1)) ret = true;
    return ret;
  }

}
