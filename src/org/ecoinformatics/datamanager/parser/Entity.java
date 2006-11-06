/**
 *    '$RCSfile: Entity.java,v $'
 *
 *     '$Author: tao $'
 *       '$Date: 2006-11-06 19:57:54 $'
 *   '$Revision: 1.11 $'
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
import org.ecoinformatics.datamanager.download.DownloadHandler;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterface;
import org.ecoinformatics.datamanager.download.GZipDataHandler;
import org.ecoinformatics.datamanager.download.TarDataHandler;
import org.ecoinformatics.datamanager.download.ZipDataHandler;


/**
 * This object represents an Entity.  An Entity stores
 * information about a table of Attributes.
 * 
 * @author  tao
 */
public class Entity extends DataObjectDescription 
{
    /*
     * Class fields
     */
  
    /** static variable for ROWMAJOR tables **/
    public static String ROWMAJOR = "ROWMAJOR";
    
    /** static variable for COLUMNMAJOR tables **/
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
    
    /*
     * Instance fields
     */
    
    private AttributeList attributeList = new AttributeList();
    private Boolean      caseSensitive;
    private String       orientation;
    private int          numRecords      = 0;
    private int          numHeaderLines  = 0;
    private int          numFooterLines  = 0;
    private String       delimiter       = null;
    private String       recordDelimiter = null;
    private boolean      multiple        = false; // if true, multiple inputs 
                                                  // can be mapped to one table

    private String fileName;       // filename where Entity data is stored
    private String url;            // distribution url for this entity
    private String DBtableName;    // the unique table name will be stored in DB
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
     * Constructs this object with some extra parameters.
     * 
     * @param name          the name of the Entity
     * @param description   the description of the Entity
     * @param caseSensitive indicates whether this Entity is caseSensitive
     * @param orientation   indicates whether this Entity is column or row
     *                      major
     * @param numRecords    the number of records in this Entity
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
     * Construct an Entity, setting the list of attributes.
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
    }

    
    /**
     * Add an Attribute to this table.
     * 
     * @param  a  the Attribute object to be added.
     */
    public void add(Attribute a)
    {
      this.attributeList.add(a);
      //a.setParent(this);
    }

    
    /**
     * Gets the list of attributes for this Entity. 
     * 
     * @return  an array of Attribute objects
     */
    public Attribute[] getAttributes()
    {
        Attribute[] attrList = attributeList.getAttributes();
       
        return attrList;
    }

    
    /**
     * Indicates whether the Entity is case sensitive or not.
     * 
     * @return  true if case sensitive, else false.
     */
    public Boolean getCaseSensitive()
    {
      return caseSensitive;
    }

    
    /**
     * Gets the orientation of the table entity.
     * 
     * @return   a string representing the orientation
     */
    public String getOrientation()
    {
      return orientation;
    }

    
    /**
     * Gets the number of records in the entity.
     * 
     * @return  the number of records, an int
     */
    public int getNumRecords()
    {
      return numRecords;
    }

    
    /**
     * Sets the number of header lines in the entity.
     * 
     * @param  numHeaderLines  the value of the number of header lines to be set
     */
    public void setNumHeaderLines(int numHeaderLines)
    {
      this.numHeaderLines = numHeaderLines;
    }
    
    
    /**
     * Sets the number of footer lines in the entity.
     * 
     * @param numFooterLines  the value of the number of footer lines to be set
     */
    public void setNumFooterLines(int numFooterLines)
    {
    	this.numFooterLines = numFooterLines;
    }

    
    /**
     * Gets the number of header lines in the entity.
     * 
     * @return  a value indicating the number of header lines
     */
    public int getNumHeaderLines()
    {
      return this.numHeaderLines;
    }
    
    
    /**
     * Gets the number of footer lines in the entity.
     * 
     * @return   a value indication the number of footer lines
     */
    public int getNumFooterLines()
    {
    	return this.numFooterLines;
    }

    
    /**
     * Sets the delimiter used with this entity.
     * 
     * @param  delim   the delimiter string to be set
     */
    public void setDelimiter(String delim)
    {
      this.delimiter = delim;
    }

    
    /**
     * Gets the delimiter used with this entity.
     * 
     * @return  the delimiter string value
     */
    public String getDelimiter()
    {
      return this.delimiter;
    }

    
    /**
     * Sets the record delimiter used with this entity.
     * 
     * @param  delim  the record delimiter string value to be set
     */
    public void setRecordDelimiter(String delim)
    {
      this.recordDelimiter = delim;
    }

    
    /**
     * Gets the recordDelimiter used with this entity.
     * 
     * @return  the recordDelimiter string value for this entity
     */
    public String getRecordDelimiter()
    {
      return this.recordDelimiter;
    }

    
    /**
     * Sets the url value for this entity.
     * 
     * @param url    the url string value to be set
     */
    public void setURL(String url)
    {
      this.url = url;
    }

    
    /**
     * Gets the url value for this entity.
     * 
     * @return  the url string value for this entity.
     */
    public String getURL()
    {
      return this.url;
    }

    
    /**
     * Sets the database table name for this entity.
     * 
     * @param DBtableName  the database table name string value to be set.
     */
    public void setDBTableName(String DBtableName)
    {
      this.DBtableName = DBtableName;
    }

    
    /**
     * Gets the database table name for this entity.
     * 
     * @return  the database table name string value
     */
    public String getDBTableName()
    {
      return this.DBtableName;
    }
    
    
    /**
     * Boolean to determine whether this entity can collapse consecutive 
     * delimiters.
     * 
     * @return  true if can collapse consecutive delimiters, else false.
     */
    public boolean getCollapseDelimiter()
    {
    	return this.collapseDelimiter;
    }
  
    
    /**
     * Sets the collapse delimiter value.
     * 
     * @param collapseDelimiter  the value to set for collapseDelimiter, a
     *                           boolean
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
     * Gets the file name for this entity.
     * 
     * @return   a string holding the file name
     */
    public String getFileName()
    {
      return fileName;
    }

    
    /**
     * Sets the fileName for this entity.
     * 
     * @param fileName   The fileName value to set
     */
    public void setFileName(String fileName)
    {
      this.fileName = fileName;
    }

    
    /**
     * Serializes the data item in XML format.
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
     * Sets the multiple value to true.
     */
    public void setMultiple()
    {
      this.multiple = true;
    }

    
    /**
     * Gets the multiple value.
     * 
     * @return   the multiple value, a boolean
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
     * Gets the database table name for this entity.
     * 
     * @return  the database table name string
     */
    public String getMappedName()
    {
        return this.DBtableName;
    }

    
    /**
     * Gets the attribute list for this entity.
     * 
     * @return vector  an array of Attribute objects
     */
    public Attribute[] getFields()
    {
      return attributeList.getAttributes();
    }
    
    
    /**
     * Gets the Primary Key Definition for the table.
     * 
     * @return   A primary key Constraint object. (Currently always null)
     */
    public Constraint getPrimaryKey()
    {
      return null;
    }
    
    
    /**
     * Gets the compression method for the entity distribution file.
     * 
     * @return the compressionMethod string value
     */
    public String getCompressionMethod()
    {
      return this.compressionMethod;
    }
    
    
    /**
     * Sets the compression method for the entity distribution file.
     * 
     * @param compressionMethod  A string representing the compression method.
     */
    public void setCompressionMethod(String compressionMethod)
    {
      this.compressionMethod = compressionMethod;
    }
    
    
    /**
     * Boolean to determine if this entity is an image entity for SpatialRaster 
     * or SpatialVector
     * 
     * @return boolean  true if this is an image entity, else false
     */
    public boolean getIsImageEntity()
    {
      return this.isImageEntity;
    }
    
    
    /**
     * Sets the isImageEntity field to store whether this is an image entity
     * 
     * @param isImageEntity   the boolean value to set. true if this is an
     *                        image entity, else false
     */
    public void setIsImageEntity(boolean isImageEntity)
    {
      this.isImageEntity = isImageEntity;
    }
    
    
    /**
     * Boolean to determine if the data file is zip file.
     * 
     * @return  true if the entity data is in a zip file, else false.
     */
    public boolean getHasZipDataFile()
    {
      return this.hasZipDataFile;
    }
    
    
    /**
     * Sets the isZipDataFile boolean field.
     * 
     * @param isZipDataFile the boolean value to set.
     */
    public void setHasZipDataFile(boolean isZipDataFile)
    {
      this.hasZipDataFile = isZipDataFile;
    }
    
    
    /**
     * Gets the value of the hasGZipDataFile field.
     * 
     * @return true if this entity has a gzip data file, else false
     */
    public boolean getHasGZipDataFile()
    {
      return this.hasGZipDataFile;
    }
    
    
    /**
     * Sets the boolean value of the hasGZipDataFile field.
     * 
     * @param hasGZipDataFile  the boolean value to set
     */
    public void setHasGZipDataFile(boolean hasGZipDataFile)
    {
      this.hasGZipDataFile = hasGZipDataFile;
    }
    
    
    /**
     * Gets the value of the hasTarDataFile field.
     * 
     * @return boolean  the boolean value of the hasTarDataFile field
     */
    public boolean getHasTarDataFile()
    {
      return this.hasTarDataFile;
    }
    
    /**
     * Sets a boolean value to determine if this entity has a tar data file
     * 
     * @param hasTarDataFile true if this entity has a tar data file, else false
     */
    public void setHasTarDataFile(boolean hasTarDataFile)
    {
      this.hasTarDataFile = hasTarDataFile;
    }
    
    
    /**
     * Gets the identifier for this entity. Currently we use distribution url
     * as entity identifier.
     * 
     * @return identifier of this entity, a string holding the distribution url
     */
    public String getEntityIdentifier()
    {
    	return url;
    }
    
   
    /**
     * Boolean to determine if data file in this entity is simple delimited.
     * 
     * @return Returns the simpleDelimited boolean value
     */
    public boolean isSimpleDelimited()
    {
        return simpleDelimited;
    }
    
    
    /**
     * Sets the value of the simpleDelimited field.
     * 
     * @param simpleDelimited The simpleDelimited boolean value to set.
     */
    public void setSimpleDelimited(boolean simpleDelimited)
    {
        this.simpleDelimited = simpleDelimited;
    }
    
    
    /**
     * Gets the complex data format array for this entity.
     * 
     * @return An array of TextComplexDataFormat objects
     */
    public TextComplexDataFormat[] getDataFormatArray()
    {
        return dataFormatArray;
    }
    
    
    /**
     * Sets the value of the DataFormatArray field.
     * 
     * @param dataFormatArray An array of TextComplexDataFormat objects
     */
    public void setDataFormatArray(TextComplexDataFormat[] dataFormatArray)
    {
        this.dataFormatArray = dataFormatArray;
    }
    
    
    /**
     * Gets the physical line delimiter string value.
     * 
     * @return Returns the physicalLineDelimiter value, a string
     */
    public String getPhysicalLineDelimiter()
    {
        return physicalLineDelimiter;
    }
    
    
    /**
     * Sets the physical line delimiter string value that was found 
     * in the metadata.
     * 
     * @param physicalLineDelimiter The physicalLineDelimiter string to set.
     */
    public void setPhysicalLineDelimiter(String physicalLineDelimiter)
    {
        this.physicalLineDelimiter = physicalLineDelimiter;
    }
    
    
    /**
     * Sets the attribute list for this entity.
     * 
     * @param list   the AttributeList object to set
     */
    public void setAttributeList(AttributeList list)
    {
        this.attributeList = list;
    }
    
    
    /**
     * Gets the attributeList field.
     * 
     * @return  the AttributeList object stored in the attributeList field
     */
    public AttributeList getAttributeList()
    {
    	return this.attributeList;
    }
    
    
    /**
     * Get the DownloadHandler associated with this entity, which may be a 
     * sub-class of DownloadHandler. Currently we only handle one situation, 
     * e.g. one of DownloadHandler, ZipDataHandler, GZipDataHandler, and 
     * TarDataHandler. In the future we will implement to allow for a 
     * combination of the above cases.
     * 
     * @param  endPointInfo  the object provides ecogrid end point information
     * @return the DownloadHandler object which will download data for this
     *         entity
     */
    public DownloadHandler getDownloadHandler(EcogridEndPointInterface endPointInfo)
    {
        if (hasZipDataFile)
        {
            ZipDataHandler handler = ZipDataHandler.getZipHandlerInstance(url, endPointInfo);
            return handler;
        }
        
        if (hasGZipDataFile)
        {
            GZipDataHandler handler = GZipDataHandler.getGZipHandlerInstance(url, endPointInfo);
            return handler;
        }
        
        if (hasTarDataFile)
        {
            TarDataHandler handler = TarDataHandler.getTarHandlerInstance(url, endPointInfo);
            return handler;
        }
            
        DownloadHandler handler = DownloadHandler.getInstance(url, endPointInfo);
        return handler;
    }

}
