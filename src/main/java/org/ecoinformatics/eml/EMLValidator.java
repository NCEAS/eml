package org.ecoinformatics.eml;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class EMLValidator {

	private Document doc = null;

	public EMLValidator(String filename) {
		try {
			FileInputStream f = new FileInputStream(new File(filename));
			doc = parseDocument(new InputSource(f));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		String emlfile = args[0];
		// System.out.println("emlfile: " + emlfile);
		EMLValidator validator = new EMLValidator(emlfile);
		boolean isValid = validator.validate();
        System.err.println("isValid: " + (new Boolean(isValid)).toString());
	}

	private Document parseDocument(InputSource in) throws Exception {
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		dfactory.setNamespaceAware(false);
		Document doc = dfactory.newDocumentBuilder().parse(in);
		return doc;
	}

	private boolean validate() {
		boolean isValid = true;
		
		// TODO: All EML documents MUST have the 'eml' module as the root
		
		// All `id` attributes within the document MUST be unique
		Vector<String> ids = getXPathValues("//*[@id]/@id");
		HashMap idmap = new HashMap();
		for (String s : ids) {
			//System.out.println(s);
			idmap.put(s, null);
		}
		if (ids.size() != idmap.size()) {
			System.err.println("Invalid: ID attributes must be unique. Duplicates exist.");
			isValid = false;	
		}

		// If an element references another using a child `references` element or references attribute, another element with that value in its `id` attribute MUST exist in the document
		// If an `additionalMetadata` element references another using a child `describes` element, another element with that value in its `id` attribute MUST exist in the document
		Vector<String> refs = getXPathValues("//annotation[@references]/@references|//references|/describes");
		for (String s : refs) {
			System.out.println(s);
			if (!ids.contains(s)) {
				System.err.println("Invalid: Reference missing from IDs: " + s);
				isValid = false;
			}
		}
		

		// TODO: Elements which contain an `annotation` child element MUST contain an `id` attribute, unless the containing `annotation` element contains a `references` attribute
		
		// TODO: When `references` is used, the `system` attribute MUST have the same value in both the target and source elements, or it must be absent in both.
		
		// TODO: If an element references another using a child `references` element, it MUST not have an `id` attribute itself
		
		return isValid;
	}

	private Vector getXPathValues(String xpath) {
		// Use the simple XPath API to select a nodeIterator.
		Vector<String> values = new Vector<String>();
		try {
			NodeList nl = XPathAPI.selectNodeList(doc, xpath);

			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				n.normalize();
				Node t = n.getFirstChild();
				// System.out.println(t.getNodeType() + "/" +
				// t.getLocalName() + "/" +
				// t.getNodeValue());
				values.add(t.getNodeValue());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return values;
		//return values.toArray(new String[values.size()]);
	}
}

