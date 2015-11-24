/**
 *  '$RCSfile: BasicNode.java,v $'
 *    Purpose: A Class that represents an XML node and its contents
 *  Copyright: 2000 Regents of the University of California and the
 *             National Center for Ecological Analysis and Synthesis
 *    Authors: Matt Jones
 *
 *   '$Author: leinfelder $'
 *     '$Date: 2008-07-30 00:31:56 $'
 * '$Revision: 1.1 $'
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

package org.ecoinformatics.datamanager.dataquery;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;

/** A Class that represents an XML node and its contents */
public class BasicNode {

    private long	node_id;
    private String	tagname;
    private long	parent_id;
    private long    rootnode_id;
    private String  doc_id;
    private Hashtable	attributes;
    private Hashtable	namespace;
    private int         childNum;
    private int         nodeIndex;
    private String      nodeType;
    private Vector  	children;

    /** Construct a Basic Node */
    public BasicNode () {
      children = new Vector();
      attributes = new Hashtable(); 
      namespace = new Hashtable(); 
      this.childNum = 0;
    }

    /** 
     * Construct a Basic Node 
     *
     * @param tagname the name of the node
     */
    public BasicNode (String tagname) {
      this();
      this.tagname = tagname;
    }

    /** 
     * Construct a Basic Node 
     *
     * @param tagname the name of the node
     * @param parent_id the id number of the parent node
     * @param nodeIndex - index of node among siblings in parent node
     *                    Every node initializes childNum to 0 when 
     *                    created and has interface incChildNum
     *                    when new child is created
     */
    public BasicNode (String tagname, long parent_id, int nodeIndex) {
      this();
      this.tagname = tagname;
      this.parent_id = parent_id;
      this.nodeIndex = nodeIndex;
    }
    
    /** Construct a Basic Node 
     *
     * @param node_id the id number of the node
     * @param tagname the name of the node
     * @param parent_id the id number of the parent node
     */
    public BasicNode (long node_id, String tagname, long parent_id, 
                         int nodeIndex) {
      this(tagname,parent_id,nodeIndex);
      this.node_id = node_id;
    }

    /** convert the node to a string representation for display */
/*  MAKE THIS AN ABSTRACT METHOD??????
    public String toString ()
    {
	StringBuffer value = new StringBuffer();
	value.append('<');
	value.append(getTagName());
	value.append(getAttributes().toString());
	value.append('>');
	return value.toString();
    }
*/

    /** Get the id of this node */
    public long getNodeID() 
    { 
      return node_id; 
    }

    /** Set the id of this node */
    public void setNodeID(long node_id) 
    { 
      this.node_id = node_id; 
    }

    /** Get the parent id of this node */
    public long getParentID() 
    { 
      return parent_id; 
    }

    /** Set the parent id of this node */
    public void setParentID(long parent_id) 
    { 
      this.parent_id = parent_id; 
    }

    /** Get the root node id of this node */
    public long getRootNodeID() 
    { 
      return rootnode_id; 
    }

    /** Set the root node id of this node */
    public void setRootNodeID(long rootnode_id) 
    { 
      this.rootnode_id = rootnode_id; 
    }

    /** Get the doc id of this node */
    public String getDocID() 
    { 
      return doc_id; 
    }

    /** Set the doc id of this node */
    public void setDocID(String doc_id) 
    { 
      this.doc_id = doc_id; 
    }

    /** Get the name of this node */
    public String getTagName() 
    { 
      return tagname; 
    }

    /** Set the name of this node */
    public void setTagName(String tagname) 
    { 
      this.tagname = tagname; 
    }

    /** Get the attributes as a string */
    public String getAttributes() {
      StringBuffer buf = new StringBuffer();
      String attName = null;
      String attValue = null;

      Enumeration attList = attributes.keys();
      while (attList.hasMoreElements()) {
        attName = (String)attList.nextElement();
        attValue = (String)attributes.get(attName);
        buf.append(" ").append(attName).append("=\"");
        buf.append(attValue).append("\"");        
      }
      return buf.toString();      
    }

    /** Add a new attribute to this node, or set its value */
    public void setAttribute(String attName, String attValue) {
      if (attName != null) {
        // Enter the attribute in the hash table
        attributes.put(attName, attValue);
 
      } else {
        System.err.println("Attribute name must not be null!");
      }
    }

    /** Get an attribute value by name */
    public String getAttribute(String attName) {
      return (String)attributes.get(attName);
    }

    /** Add a namespace to this node */
    public void setNamespace(String prefix, String uri) {
      if (prefix != null) {
        // Enter the namespace in the hash table
        namespace.put(prefix, uri);
      } else {
        System.err.println("Namespace prefix must not be null!");
      }
    }

    /** Get an uri of the namespace prefix */
    public String getNamespace(String prefix) {
      return (String)namespace.get(prefix);
    }

    /** Get nodeIndex of the node */
    public int getNodeIndex() {
      return this.nodeIndex;
    }

    /** Set the node index of this node */
    public void setNodeIndex(int nodeIndex) { 
      this.nodeIndex = nodeIndex; 
    }

    /** Get the type of this node */
    public String getNodeType() { 
      return nodeType; 
    }

    /** Set the type of this node */
    public void setNodeType(String type) { 
      this.nodeType = type; 
    }

    /** Add a child node to this node */
    public void addChildNode(BasicNode child) { 
      this.children.add(child);
    }

    /** Get the an enumeration of the children of this node */
    public Enumeration getChildren() { 
      return children.elements(); 
    }

    /** increase childNum when new child for the node is created */
    public int incChildNum() {
      return ++this.childNum;    
    }    

}
