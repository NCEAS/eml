/**
 *    '$RCSfile: Entity.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-09-29 00:24:59 $'
 *   '$Revision: 1.8 $'
 *
 *  For Details: http://kepler.ecoinformatics.org
 *
 * Copyright (c) 2003 The Regents of the University of California.
 * All rights reserved.
 *
 * Permission is hereby granted, without written agreement and without
 * license or royalty fees, to use, copy, modify, and distribute this
 * software and its documentation for any purpose, provided that the
 * above copyright notice and the following two paragraphs appear in
 * all copies of this software.
 *
 * IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 * FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 * ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN
 * IF THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 *
 * THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 * PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY
 * OF CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT,
 * UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */

package org.ecoinformatics.datamanager.parser;

//import org.kepler.objectmanager.data.DataObjectDescription;
//import org.kepler.objectmanager.cache.DataCacheObject;
//import org.kepler.objectmanager.data.text.TextComplexDataFormat;
import java.util.Vector;
import org.ecoinformatics.datamanager.download.DownloadHandler;
import org.ecoinformatics.datamanager.download.GZipDataHandler;
import org.ecoinformatics.datamanager.download.TarDataHandler;
import org.ecoinformatics.datamanager.download.ZipDataHandler;

/**
 * This object represents an TableEntity.  A TableEntity stores
 * information about a table of Attributes that is used in a Step
 * in the pipeline.
 */
public class Entity extends DataObjectDescription 
{
    /**static variable for ROWMAJOR tables**/
    public static String ROWMAJOR = "ROWMAJOR";
    /**static variable for COLUMNMAJOR tables**/
    public static String COLUMNMAJOR = "COLUMNMAJOR";
    public static String ZIP         = "zip";
    public static String TAR         = "application/x-tar";
    public static String GZIP        = "gzip";

    /**static variable for table type**/
    //public static String TABLEENTITY = "TABLEENTITY";
    public static String SPATIALRASTERENTITY = "SPATIALRASTERENTITY";
    public static String SPATIALVECTORENTITY = "SPATIALVECTORENTITY";
    public static String STOREDPROCEDUREENTITY = "STOREDPROCEDUREENTITY";
    public static String VIEWENTITY = "VIEWENTITY";
    public static String OTHERENTITY = "OTHERENTITY";
    
    private AttributeList attributeList = new AttributeList();
    private Boolean      caseSensitive;
    private String       orientation;
    private int          numRecords      = 0;
    private int          numHeaderLines  = 0;
    private int          numFooterLines  = 0;
    private String       delimiter       = null;
    private String       recordDelimiter = null;
    private boolean      multiple        = false;        // if true, multiple inputs can be mapped to one table

    private String fileName;    // filename where TableEntity data is stored
    private String url;  // distribution url for this entity
    private String DBtableName; // the unique table name will be stored in DB
    private String compressionMethod = null;
    private boolean isImageEntity    = false;
    private boolean hasGZipDataFile  = false;
    private boolean hasZipDataFile   = false;
    private boolean hasTarDataFile   = false;
    //private DataCacheObject dataCacheObject = null;
    private boolean simpleDelimited  = true;
    private TextComplexDataFormat[] dataFormatArray = null;
    private String physicalLineDelimiter  = null;
    private boolean collapseDelimiter = false;
    


 
    /**
     * construct this object with some extra parameters
     * @param name the name of the tableEntity
     * @param description the description of the tableEntity
     * @param caseSensitive indicates whether this tableEntity is caseSensitive
     * or not.
     * @param orientation indicates whether this tableEntity is column or row
     * major
     * @param numRecords the number of records in this tableEntity
     */
    public Entity(String id, String name, String description,
                       Boolean caseSensitive, String orientation,
                       int numRecords)
    {
        this(id, name, description, null);
        attributeList = new AttributeList();
        if (caseSensitive != null) {
            this.caseSensitive = caseSensitive;
        }
        if (orientation != null) {
            this.orientation = orientation;
        }
        this.numRecords = numRecords;
    }

    /**
     * Construct a TableEntity, setting the list of attributes.
     */
    public Entity(String id, String name, String description,
            AttributeList attributeList)
    {
        super(id, name, description);
        //attributeList = new AttributeList();
        fileName = "";
        this.attributeList = attributeList;
        /*if (attributeList != null) {
            for (int i=0; i<attributeList.length; i++) {
                this.add(attributeList[i]);
            }
        }*/
        this.caseSensitive = new Boolean(false);
        this.orientation = "";

        // For now, set the database table name to the entity name, replacing
        // spaces with underscores. We need a way to generate a unique name for 
        // the entity, so this will not be sufficient.
        this.DBtableName = this.getName().replace(' ', '_').replace('-', '_');
    }

    /**
     * Add an Attribute to this table.
     */
    public void add(Attribute a)
    {
      this.attributeList.add(a);
     
      //a.setParent(this);
    }

    /**
     * Return the unit for this TableEntity
     */
    public Attribute[] getAttributes()
    {
        Attribute[] attrList = attributeList.getAttributes();
       
        return attrList;
    }

    /**
     * indicates whether the tableEntity is caseSensitive or not
     */
    public Boolean getCaseSensitive()
    {
      return caseSensitive;
    }

    /**
     * gets the orientation of the table entity
     */
    public String getOrientation()
    {
      return orientation;
    }

    /**
     * gets the number of records in the table entity
     */
    public int getNumRecords()
    {
      return numRecords;
    }

    /**
     * sets the number of header lines in the entity
     */
    public void setNumHeaderLines(int numHeaderLines)
    {
      this.numHeaderLines = numHeaderLines;
    }
    
    /**
     * set the number of footer lines in the entity
     * @param numFooterLines
     */
    public void setNumFooterLines(int numFooterLines)
    {
    	this.numFooterLines = numFooterLines;
    }

    /**
     * get the number of header lines in the entity
     */
    public int getNumHeaderLines()
    {
      return this.numHeaderLines;
    }
    
    /**
     * get the number of footer lines in the entity
     * @return
     */
    public int getNumFooterLines()
    {
    	return this.numFooterLines;
    }

    /**
     * set the delimiter used with this entity
     */
    public void setDelimiter(String delim)
    {
      this.delimiter = delim;
    }

    /**
     * get the delimiter used with this entity
     */
    public String getDelimiter()
    {
      return this.delimiter;
    }

    /**
     * set the record delimiter used with this entity
     */
    public void setRecordDelimiter(String delim)
    {
      this.recordDelimiter = delim;
    }

    /**
     * get the recordDelimiter used with this entity
     */
    public String getRecordDelimiter()
    {
      return this.recordDelimiter;
    }

    public void setURL(String url)
    {
      this.url = url;
    }

    public String getURL()
    {
      return this.url;
    }

    public void setDBTableName(String DBtableName)
    {
      this.DBtableName = DBtableName;
    }

    public String getDBTableName()
    {
      return this.DBtableName;
    }
    
    /**
     * Method to get if this entity can collapse consecutive delimiter
     * @return
     */
    public boolean getCollapseDelimiter()
    {
    	return this.collapseDelimiter;
    }
    
    /**
     * Method to set collapse delimiter
     * @param collapseDelimiter
     */
    public void setCollaplseDelimiter(boolean collapseDelimiter)
    {
    	this.collapseDelimiter = collapseDelimiter;
    }
    /*
    public String toString()
    {
      StringBuffer sb = new StringBuffer();
      sb.append("name: ").append(name).append("\n");
      sb.append("dataType: ").append(dataType).append("\n");
      sb.append("description: ").append(description).append("\n");
      sb.append("numRecords: ").append(numRecords).append("\n");
      sb.append("caseSensitive: ").append(caseSensitive).append("\n");
      sb.append("orientation: ").append(orientation).append("\n");
      sb.append("numHeaderLines: ").append(numHeaderLines).append("\n");
      sb.append("delimiter: ").append(delimiter).append("\n");
      sb.append("attributes: {");
      for(int i=0; i<attributes.size(); i++)
      {
        sb.append(((Attribute)attributes.elementAt(i)).toString());
      }
      sb.append("\n}");
      return sb.toString();
    }
    */

    /**
     * Returns the fileName.
     * @return String
     */
    public String getFileName()
    {
      return fileName;
    }

    /**
     * Sets the fileName.
     * @param fileName The fileName to set
     */
    public void setFileName(String fileName)
    {
      this.fileName = fileName;
    }

    /**
     * Serialize the data item in XML format.
     */
    /*public String toXml()
    {
        StringBuffer x = new StringBuffer();
        x.append("<table-entity id=\"");
        x.append(getId());
        x.append("\"");
        if( multiple == true )
        {
          x.append(" multiple=\"true\"");
        }
        x.append(">\n");
        appendElement(x, "entityName", getName());
        //appendElement(x, "entityType", getDataType());
        appendElement(x, "entityDescription", getDefinition());
        Attribute[] atts = getAttributes();
        for (int i = 0; i < atts.length; i++) {
            x.append(atts[i].toXml());
        }
        x.append("</table-entity>\n");

        return x.toString();
    }*/

    /**
     * Sets the multiple to true.
     */
    public void setMultiple()
    {
      this.multiple = true;
    }

    /**
     * Returns multiple.
     * @return boolean
     */
    public boolean isMultiple()
    {
      return multiple;
    }

    //-----------------------------------------------------------------
    //-- DSTableIFace
    //-----------------------------------------------------------------
    

    /**
     * Returns the name of the table
     * @return name as string 
     */
    // This is imlemented in the base class
    //public String getName();
    
    /**
     * Return the name for this data item.
     */
    public String getMappedName()
    {
        return this.DBtableName;
    }

    /**
     * Returns a Vector of the fields in the table
     * @return vector
     */
    public Attribute[] getFields()
    {
      return attributeList.getAttributes();
    }
    
    /**
     * Returns a the Primary Key Definition for the table
     * @return object
     */
    public Constraint getPrimaryKey()
    {
      return null;
    }
    
    /**
     * Method to get compression method for distribution file
     * @return String
     */
    public String getCompressionMethod()
    {
      return this.compressionMethod;
    }
    
    /**
     * Method to set compression method for distribution file
     * @param compressionMethod String
     */
    public void setCompressionMethod(String compressionMethod)
    {
      this.compressionMethod = compressionMethod;
    }
    
    /**
     * If this entity for SpatialRaster or SpatialVector
     * @return boolean
     */
    public boolean getIsImageEntity()
    {
      return this.isImageEntity;
    }
    
    /**
     * Set if this is a Image entity
     * @param isImageEntity boolean
     */
    public void setIsImageEntity(boolean isImageEntity)
    {
      this.isImageEntity = isImageEntity;
    }
    
    /**
     * Method get if the data file is zip file
     * @return boolean
     */
    public boolean getHasZipDataFile()
    {
      return this.hasZipDataFile;
    }
    
    /**
     * Method to set if the data file is zip file
     * @param isZipDataFile boolean
     */
    public void setHasZipDataFile(boolean isZipDataFile)
    {
      this.hasZipDataFile = isZipDataFile;
    }
    
    /**
     * Method to get if the data file is gzip
     * @return boolean
     */
    public boolean getHasGZipDataFile()
    {
      return this.hasGZipDataFile;
    }
    
    /**
     * Method to set if the data file is gzip
     * @param hasGZipDataFile boolean
     */
    public void setHasGZipDataFile(boolean hasGZipDataFile)
    {
      this.hasGZipDataFile = hasGZipDataFile;
    }
    
    /**
     * Method to get if this has a tar data file
     * @return boolean
     */
    public boolean getHasTarDataFile()
    {
      return this.hasTarDataFile;
    }
    
    /**
     * Method to set if this has a tar data file
     * @param hasTarDataFile boolean
     */
    public void setHasTarDataFile(boolean hasTarDataFile)
    {
      this.hasTarDataFile = hasTarDataFile;
    }
    
    /**
     * Gets the identifier for this entity. Currently we use distribution url
     * as entity identifier
     * @return Identifier of this entity
     */
    public String getEntityIdentifier()
    {
    	return url;
    }
    
   
    
    /**
     * If data file in this entity is simple delimited
     * @return Returns the simpleDelimited.
     */
    public boolean isSimpleDelimited()
    {
        return simpleDelimited;
    }
    
    /**
     * @param simpleDelimited The simpleDelimited to set.
     */
    public void setSimpleDelimited(boolean simpleDelimited)
    {
        this.simpleDelimited = simpleDelimited;
    }
    
    /**
     * Get the complex data format array 
     * @return Returns the dataFormatArray.
     */
    public TextComplexDataFormat[] getDataFormatArray()
    {
        return dataFormatArray;
    }
    
    /**
     * Set DataFormatArray
     * @param dataFormatArray The dataFormatArray to set.
     */
    public void setDataFormatArray(TextComplexDataFormat[] dataFormatArray)
    {
        this.dataFormatArray = dataFormatArray;
    }
    
    /**
     * Gets physical line delimiter
     * @return Returns the physicalLineDelimiter.
     */
    public String getPhysicalLineDelimiter()
    {
        return physicalLineDelimiter;
    }
    
    /**
     * Sets the physical line delimiter in metadata
     * @param physicalLineDelimiter The physicalLineDelimiter to set.
     */
    public void setPhysicalLineDelimiter(String physicalLineDelimiter)
    {
        this.physicalLineDelimiter = physicalLineDelimiter;
    }
    
    /**
     * Method to set attribute list
     * @param list
     */
    public void setAttributeList(AttributeList list)
    {
        this.attributeList = list;
    }
    
    /**
     * Method to get attributeList
     */
    public AttributeList getAttributeList()
    {
    	return this.attributeList;
    }
    
    /**
     * Get DownloadHandler associated with this entity. 
     * The DownlaodHnader obj can be a sub-class of DownloadHandler. 
     * Currently we only handle one situation, e.g. one of DownloadHanlder,
     * ZipDataHandler, GZipDataHandler, and TarTataHandler. We will implement in future
     * for combination of above cases.
     * @return DownloadHandler object which will do download data task
     */
    public DownloadHandler getDownloadHanlder()
    {
    	if (hasZipDataFile)
    	{
    		ZipDataHandler handler = ZipDataHandler.getZipHandlerInstance(url);
    		return handler;
    	}
    	
    	if (hasGZipDataFile)
    	{
    		GZipDataHandler handler = GZipDataHandler.getGZipHandlerInstance(url);
    		return handler;
    	}
    	
    	if (hasTarDataFile)
    	{
    		TarDataHandler handler = TarDataHandler.getTarHandlerInstance(url);
    		return handler;
    	}
    		
        DownloadHandler handler = DownloadHandler.getInstance(url);
        return handler;
    }
    
    
}
