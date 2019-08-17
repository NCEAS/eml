package org.ecoinformatics.eml;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

/**
 * Validate an EML document by executing additional validation checks that are
 * not already encoded in XML Schema.  These are described in the EML
 * specification.
 *
 * Typical usage is to instantiate EMLValidator against an EML filename, and
 * then execute the validate() method.
 */
public class EMLValidator {

    // The local document being validated, created during construction
    private Document doc = null;

    // An accumulated list of errors from validating a document
    private List<String> errors = null;

    /**
     * Construct an EMLValidator for use on a given File.
     * @param file a File containing the EML text to validate
     */
    public EMLValidator(File file) {
        try {
            FileInputStream f = new FileInputStream(file);
            doc = parseDocument(new InputSource(f));
            errors = new ArrayList<String>();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Construct an EMLValidator for use on a text String.
     * @param emltext a String containing the EML text to validate
     */
    public EMLValidator(String emltext) {
        try {
            StringReader reader = new StringReader(emltext);
            doc = parseDocument(new InputSource(reader));
            errors = new ArrayList<String>();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Simple method for command-line validation.  The first argument is the
     * EML filename.
     */
    public static void main(String[] args) {
        String emlfile = args[0];
        File file = new File(emlfile);
        EMLValidator validator = new EMLValidator(file);
        boolean isValid = validator.validate();
        System.err.println("isValid: " + (new Boolean(isValid)).toString());
        if (!isValid) {
            for (String e : validator.getErrors()) {
                System.err.println(e);
            }
        }
    }

    /**
     * Validate the EML document that was provided when the validator was
     * instantiated.  This runs all of the specialized checks that go beyond
     * XML Schema validation.  The method returns true if valid, false
     * otherwise.
     * @return true if the document iss valid, false otherwise
     */
    public boolean validate() {
        boolean isValid = true;

        // All EML documents MUST have the 'eml' module as the root
        NodeList root_not_eml = getXPathNodeList("/*[local-name() != 'eml']");
        int length = root_not_eml.getLength();
        if (length > 0) {
            errors.add("Invalid: root element is not eml.");
            isValid = false;
        }

        // All `id` attributes and `packageId` within the document MUST be unique
        ArrayList<String> packageId = getXPathValues("//*/@packageId");
        ArrayList<String> ids = getXPathValues("//*[@id]/@id");
        ids.addAll(packageId);
        HashMap idmap = new HashMap();
        for (String s : ids) {
            idmap.put(s, null);
        }
        if (ids.size() != idmap.size()) {
            errors.add("Invalid: ID attributes must be unique. Duplicates exist.");
            isValid = false;
        }

        // If an element references another using a child `references` element
        // or references attribute, another element with that value in its `id`
        // attribute MUST exist in the document
        // If an `additionalMetadata` element references another using a child
        // `describes` element, another element with that value in its `id`
        // attribute MUST exist in the document
        ArrayList<String> refs = getXPathValues("//annotation[@references]/@references|//references|//describes");
        for (String s : refs) {
            if (!ids.contains(s)) {
                errors.add("Invalid: Reference missing from IDs: " + s);
                isValid = false;
            }
        }


        // Elements which contain an `annotation` child element MUST contain an
        // `id` attribute, unless the containing `annotation` element contains a
        // `references` attribute
        NodeList missing_id_ref = getXPathNodeList("//*[annotation and not(@id) and not(annotation[@references]) and not(parent::*/describes)]");
        length = missing_id_ref.getLength();
        if (length > 0) {
            errors.add("Invalid: annotations lack id or references: " + length);
            isValid = false;
            //for (int i = 0; i < missing_id_ref.getLength(); i++) {
            //Node n = missing_id_ref.item(i);
            //debugNode(n, "");
            //}
        }

        // If an element references another using a child `references` element,
        // it MUST not have an `id` attribute itself
        NodeList both_id_ref = getXPathNodeList("//*[references and @id]");
        length = both_id_ref.getLength();
        if (length > 0) {
            errors.add("Invalid: elements use both @id and references: " + length);
            isValid = false;
            //for (int i = 0; i < both_id_ref.getLength(); i++) {
            //Node n = both_id_ref.item(i);
            //debugNode(n, "");
            //}
        }

        // TODO: When `references` is used, the `system` attribute MUST have the
        // same value in both the target and source elements, or it must be
        // absent in both. For now, we have decided to not enforce this
        // constraint, as snobody seems to use it.

        return isValid;
    }

    /**
     * Return an array of validation errors after validation hass been
     * completed.  If the array is of length 0, no errors occurred.
     * @return array of String error values
     */
    public String[] getErrors() {
        return(errors.toArray(new String[0]));
    }

    /**
     * Build a parsed version of the XML DOM using a default XML parser.
     * @param in the InputSource for the file to be parsed
     * @return an XML DOM Document representing the file
     */
    private Document parseDocument(InputSource in) throws Exception {
        DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
        dfactory.setNamespaceAware(false);
        Document doc = dfactory.newDocumentBuilder().parse(in);
        return doc;
    }

    /**
     * Extract a vector of text values from the document using an XPath
     * expression.
     */
    private ArrayList getXPathValues(String xpath) {
        // Use the simple XPath API to select a nodeIterator.
        ArrayList<String> values = new ArrayList<String>();
        try {
            NodeList nl = XPathAPI.selectNodeList(doc, xpath);

            for (int i = 0; i < nl.getLength(); i++) {
                Node n = nl.item(i);
                n.normalize();
                Node t = n.getFirstChild();
                values.add(t.getNodeValue());
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return values;
    }

    /**
     * Extract a NodeList using an XPath on the provided Node n.
     * @param xpath an XPath expression to be executed
     * @param n the XML DOM Node on which to execute the XPath
     */
    private NodeList getXPathNodeList(String xpath, Node n) {
        NodeList nl = null;
        try {
            nl = XPathAPI.selectNodeList(n, xpath);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return nl;
    }

    /**
     * Extract a NodeList ussing an XPath n the default DOM document for the
     * instantiated EMLValidator.
     * @param xpath an XPath expression to be executed
     */
    private NodeList getXPathNodeList(String xpath) {
        return getXPathNodeList(xpath, doc);
    }

    /**
     * Print out a Node for debugging purposes.
     */
    private void debugNode(Node node, String spacer) {
        if (node == null)
            return;
        switch (node.getNodeType()) {
        case Node.ELEMENT_NODE:
            String name = node.getNodeName();
            System.out.print(spacer + "<" + name);
            NamedNodeMap nnm = node.getAttributes();
            for (int i = 0; i < nnm.getLength(); i++) {
                Node current = nnm.item(i);
                System.out.print(" " + current.getNodeName() + "= " + current.getNodeValue());
            }
            System.out.print(">");
            NodeList nl = node.getChildNodes();
            if (nl != null) {
                for (int i = 0; i < nl.getLength(); i++) {
                    debugNode(nl.item(i), "");
                }
            }
            System.out.println(spacer + "</" + name + ">");
            break;
        case Node.TEXT_NODE:
            System.out.print(node.getNodeValue());
            break;
        case Node.CDATA_SECTION_NODE:
            System.out.print("" + node.getNodeValue() + "");
            break;
        case Node.ENTITY_REFERENCE_NODE:
            System.out.print("&" + node.getNodeName() + ";");
            break;
        case Node.ENTITY_NODE:
            System.out.print("<ENTITY: " + node.getNodeName() + "> </" + node.getNodeName() + "/>");
            break;
        case Node.DOCUMENT_NODE:
            NodeList nodes = node.getChildNodes();
            if (nodes != null) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    debugNode(nodes.item(i), "");
                }
            }
            break;
        case Node.DOCUMENT_TYPE_NODE:
            DocumentType docType = (DocumentType) node;
            System.out.print("<!DOCTYPE " + docType.getName());
            if (docType.getPublicId() != null) {
                System.out.print(" PUBLIC " + docType.getPublicId() + " ");
            } else {
                System.out.print(" SYSTEM ");
            }
            System.out.println(" " + docType.getSystemId() + ">");
            break;
        default:
            break;
        }
    }
}
