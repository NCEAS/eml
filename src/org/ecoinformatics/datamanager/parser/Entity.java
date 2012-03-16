/**
 *    '$RCSfile: Entity.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2008-06-18 00:22:13 $'
 *   '$Revision: 1.16 $'
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

import java.util.TreeSet;

import org.ecoinformatics.datamanager.database.DelimitedReader;
import org.ecoinformatics.datamanager.download.DownloadHandler;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterface;
import org.ecoinformatics.datamanager.download.GZipDataHandler;
import org.ecoinformatics.datamanager.download.TarDataHandler;
import org.ecoinformatics.datamanager.download.ZipDataHandler;
import org.ecoinformatics.datamanager.quality.EntityReport;
import org.ecoinformatics.datamanager.quality.QualityCheck;
import org.ecoinformatics.datamanager.quality.QualityReport;
import org.ecoinformatics.datamanager.quality.QualityCheck.Status;


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
    private String       fieldDelimiter  = null;
    private String       recordDelimiter = null;
    private boolean      multiple        = false; // if true, multiple inputs 
                                                  // can be mapped to one table

    private String fileName;       // filename where Entity data is stored
    private String url;            // distribution URL for this entity
    private String urlFunction;    // value of the URL "function" attribute
    private String urlContentType; // value of URLConnection.getContentType();
    private String format;
    private String dbTableName;    // the unique table name will be stored in DB
    private String compressionMethod = null;
    private String firstKilobyte = null;
    private boolean hasDistributionOnline = false;
    private boolean hasDistributionOffline = false;
    private boolean hasDistributionInline = false;
    private boolean isImageEntity    = false;
    private boolean isOtherEntity    = false;
    private boolean hasGZipDataFile  = false;
    private boolean hasZipDataFile   = false;
    private boolean hasTarDataFile   = false;
    //private DataCacheObject dataCacheObject = null;
    private boolean simpleDelimited  = true;
    private TextComplexDataFormat[] dataFormatArray = null;
    private String physicalLineDelimiter  = null;
    private boolean collapseDelimiters = false;
    private String packageId = null;
    private String quoteCharacter = null;
    private String literalCharacter = null;
    
    private EntityReport entityReport = null;
    private String entityAccessXML = null;
    
    
    /* 
     * Constructors 
     */
    
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
        this.entityReport = new EntityReport(this);
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
     * Adds a quality check to the entity's associated entityReport object.
     * 
     * @param qualityCheck    the new quality check to add to the list
     */
    public void addQualityCheck(QualityCheck qualityCheck) {
      if (entityReport != null) {
        entityReport.addQualityCheck(qualityCheck);
      }
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
     * Gets the firstKilobyte string of the entity
     * @return
     */
    public String getFirstKilobyte() {
      return firstKilobyte;
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
     * Sets the fieldDelimiter used with this entity.
     * 
     * @param  delimiter   the delimiter string to be set
     */
    public void setFieldDelimiter(String delimiter)
    {
      this.fieldDelimiter = delimiter;
      
      /*
       *  Check the validity of the fieldDelimiter value
       */
      String fieldDelimiterIdentifier = "fieldDelimiterValid";
      QualityCheck fieldDelimiterTemplate = 
        QualityReport.getQualityCheckTemplate(fieldDelimiterIdentifier);
      QualityCheck fieldDelimiterQualityCheck = 
        new QualityCheck(fieldDelimiterIdentifier, fieldDelimiterTemplate);

      if (QualityCheck.shouldRunQualityCheck(this, fieldDelimiterQualityCheck)) {
        boolean isValidDelimiter = true;
        String found = delimiter;
        String explanation = fieldDelimiterQualityCheck.getExplanation();
        
        // Check for bad field delimiters
        if (delimiter == null || delimiter.equals("")) {
          isValidDelimiter = false;
          explanation += " The fieldDelimiter value is null or empty string.";
        }
        else {
          int delimiterLength = delimiter.length();    
          if (delimiterLength > 1) {
            String unescapedDelimiter = DelimitedReader.unescapeDelimiter(delimiter);
            if (delimiter.equals(unescapedDelimiter)) {
              isValidDelimiter = false;
              explanation += " The specified delimiter, '" + 
                             delimiter + "'," +
                             " is not a recognized fieldDelimiter value.";
            }
          }
        }
          
        fieldDelimiterQualityCheck.setFound(found);
        if (isValidDelimiter) {
          explanation = "A valid fieldDelimiter value was found";
          fieldDelimiterQualityCheck.setStatus(Status.valid);
          fieldDelimiterQualityCheck.setSuggestion("");
        }
        else {
          fieldDelimiterQualityCheck.setFailedStatus();
        } 
        fieldDelimiterQualityCheck.setExplanation(explanation);
        addQualityCheck(fieldDelimiterQualityCheck);
      }
    }


    /**
     * Gets the fieldDelimiter used with this entity.
     * 
     * @return  the fieldDelimiter string value
     */
    public String getFieldDelimiter()
    {
      return this.fieldDelimiter;
    }
    
    /**
     * Gets the value of quote character. Quote character specifies a character 
     * to be used in the data file for quoting values so that field delimeters can 
     * be used within the value.
     * 
     * @return  a value indicating the value of quote character.
     */
    public String getQuoteCharacter()
    {
      return this.quoteCharacter;
    }
    
    /**
     * Sets the quote character in the entity.
     * 
     * @param quoteCharacter  the value of quote character to be set
     */
    public void setQuoteCharacter(String quoteCharacter)
    {
    	this.quoteCharacter = quoteCharacter;
    }
    
    
    /**
     * Gets the value of literal character. Literal character specifies a 
     * character to be used for escaping special character values so that 
     * they are treated as literal values 
     * 
     * @return  a value indicating the value of quote character.
     */
    public String getLiteralCharacter()
    {
      return this.literalCharacter;
    }
    
    /**
     * Sets the literal character in the entity.
     * 
     * @param literalCharacter  the value of literal character to be set
     */
    public void setLiteralCharacter(String literalCharacter)
    {
    	this.literalCharacter = literalCharacter;
    }

    
    /**
     * Sets the record delimiter used with this entity.
     * 
     * @param  delim  the record delimiter string value to be set
     */
    public void setRecordDelimiter(String delim)
    {
      this.recordDelimiter = delim;
      
      /*
       *  Do a quality check the recordDelimiter value
       */
      String recordDelimiterIdentifier = "recordDelimiterPresent";
      QualityCheck recordDelimiterTemplate = 
        QualityReport.getQualityCheckTemplate(recordDelimiterIdentifier);
      QualityCheck recordDelimiterQualityCheck = 
        new QualityCheck(recordDelimiterIdentifier, recordDelimiterTemplate);

      if (QualityCheck.shouldRunQualityCheck(this, recordDelimiterQualityCheck)) {
        boolean isValidDelimiter = true;
        String found = recordDelimiter;
        String explanation = recordDelimiterQualityCheck.getExplanation();
        
        // Check for unusual record delimiter values
        if (recordDelimiter == null || recordDelimiter.equals("")) {
          isValidDelimiter = false;
          explanation += " The recordDelimiter value is null or an empty string.";
        }
        else if (!isSuggestedRecordDelimiter(recordDelimiter)) {
          isValidDelimiter = false;
          explanation += 
            " The specified recordDelimiter, '" + 
            recordDelimiter + "'," +
            " is not in the list of suggested recordDelimiter values.";
        }
          
        recordDelimiterQualityCheck.setFound(found);
        if (isValidDelimiter) {
          explanation = "A valid recordDelimiter value was found";
          recordDelimiterQualityCheck.setStatus(Status.valid);
          recordDelimiterQualityCheck.setSuggestion("");
        }
        else {
          recordDelimiterQualityCheck.setFailedStatus();
        } 
        recordDelimiterQualityCheck.setExplanation(explanation);
        addQualityCheck(recordDelimiterQualityCheck);
      }
      
    }
    
    
    /*
     * Boolean method used for quality check on record delimiter value
     */
    public boolean isSuggestedRecordDelimiter(String recordDelimiter) {
      boolean isSuggested;
      
      TreeSet<String> treeSet = suggestedRecordDelimiters();
      isSuggested = treeSet.contains(recordDelimiter);
      
      return isSuggested;
    }
    
    
    /**
     * Returns a set of suggested record delimiter values.
     * 
     * @return  treeSet, a set of suggested record delimiter values
     */
    private TreeSet<String> suggestedRecordDelimiters() {
      TreeSet<String> treeSet = new TreeSet<String>();
      treeSet.add("\\n");
      treeSet.add("\\r");
      treeSet.add("\\r\\n");
      treeSet.add("#x0A");
      treeSet.add("#x0D");
      treeSet.add("#x0D#x0A");
      return treeSet;
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
     * Sets the urlContentType value for this entity.
     * 
     * @param url  the urlContentType string value to be set
     */
    public void setUrlContentType(String urlContentType) {
      this.urlContentType = urlContentType;
    }


    /**
     * Sets the urlFunction value for this entity.
     * 
     * @param url    the urlFunction string value to be set
     */
    public void setURLFunction(String urlFunction) {
      this.urlFunction = urlFunction;
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
     * Gets the urlContentType value for this entity.
     * 
     * @return  the urlContentType string value for this entity.
     */
    public String getUrlContentType() {
      return urlContentType;
    }


    /**
     * Gets the urlFunction value for this entity.
     * 
     * @return  the urlFunction string value for this entity.
     */
    public String getUrlFunction() {
      return urlFunction;
    }

  
    /**
     * Sets the format for this entity.
     * 
     * @param format    the format string value to be set
     */
    public void setDataFormat(String format)
    {
      this.format = format;
    }

    
    /**
     * Gets the format value for this entity.
     * 
     * @return  the format string value for this entity.
     */
    public String getDataFormat()
    {
      return this.format;
    }

    
    /**
     * Gets the database field names for the attributes in this entity.
     * 
     * @return   an array of Strings objects, or null if there are no
     *           attributes in the entity's attribute list. 
     */
    public String[] getDBFieldNames()
    {
      if (attributeList != null) {
        return attributeList.getDBFieldNames();
      }
      else {
        return null;
      }
    }

    
    /**
     * Sets the database table name for this entity.
     * 
     * @param dbTableName  the database table name string value to be set.
     */
    public void setDBTableName(String dbTableName)
    {
      this.dbTableName = dbTableName;
    }

    
    /**
     * Gets the database table name for this entity.
     * 
     * @return  the database table name string value
     */
    public String getDBTableName()
    {
      return this.dbTableName;
    }
    
    
    /**
     * Boolean to determine whether this entity can collapse consecutive 
     * delimiters.
     * 
     * @return  true if can collapse consecutive delimiters, else false.
     */
    public boolean getCollapseDelimiters()
    {
    	return this.collapseDelimiters;
    }
  
    
    /**
     * Sets the collapse delimiter value.
     * 
     * @param collapseDelimiters  the value to set for collapseDelimiters, a
     *                            boolean
     */
    public void setCollapseDelimiters(boolean collapseDelimiters)
    {
    	this.collapseDelimiters = collapseDelimiters;
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
        return this.dbTableName;
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
     * Boolean to determine if this entity has at least one distribution 
     * online element.
     * 
     * @return boolean  true if the entity has a distribution online 
     *                  element, else false
     */
    public boolean hasDistributionOnline() {
      return this.hasDistributionOnline;
    }
    
    
    /**
     * Sets the isDistributionOnline field to store whether this entity
     * has at least one distribution online element.
     * 
     * @param distributionOnline   the boolean value to set. true if 
     *        the entity has a distribution online element, else false
     */
    public void setHasDistributionOnline(boolean distributionOnline) {
      this.hasDistributionOnline = distributionOnline;
    }
    
    
    /**
     * Boolean to determine if this entity has at least one distribution 
     * offline element.
     * 
     * @return boolean  true if the entity has a distribution offline 
     *                  element, else false
     */
    public boolean hasDistributionOffline() {
      return this.hasDistributionOffline;
    }
    
    
    /**
     * Sets the isDistributionOffline field to store whether this entity
     * has at least one distribution offline element.
     * 
     * @param distributionOffline   the boolean value to set. true if 
     *        the entity has a distribution offline element, else false
     */
    public void setHasDistributionOffline(boolean distributionOffline) {
      this.hasDistributionOffline = distributionOffline;
    }
    
    
    /**
     * Boolean to determine if this entity has at least one distribution 
     * inline element.
     * 
     * @return boolean  true if the entity has a distribution inline 
     *                  element, else false
     */
    public boolean hasDistributionInline() {
      return this.hasDistributionInline;
    }
    
    
    /**
     * Sets the isDistributionInline field to store whether this entity
     * has at least one distribution inline element.
     * 
     * @param distributionInline   the boolean value to set. true if 
     *        the entity has a distribution inline element, else false
     */
    public void setHasDistributionInline(boolean distributionInline) {
      this.hasDistributionInline = distributionInline;
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
     * Gets the isOtherEntity value.
     * 
     * @return isOtherEntity  true if this is an otherEntity entity, else false
     */
    public boolean isOtherEntity() {
      return isOtherEntity;
    }
    
    
    /**
     * Sets the isOtherEntity value.
     * 
     * @param isOtherEntity  true if this is an otherEntity entity, else false
     */
    public void setIsOtherEntity(boolean isOtherEntity) {
      this.isOtherEntity = isOtherEntity;
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
     * Gets the entity <access> XML block and returns it as an XML string.
     * Will be null in cases where no <access> XML was defined for this
     * entity. (In such cases, access should default to that of the dataset.)
     * 
     * @return  an XML string holding the entity <access> block, or null
     */
    public String getEntityAccessXML() {
      return entityAccessXML;
    }
    
    
    /**
     * Sets the value of the entityAccessXML field to a string
     * which should hold a block of <access> element XML.
     * 
     * @param xmlString   the <access> element XML string
     */
    public void setEntityAccessXML(String xmlString) {
      this.entityAccessXML = xmlString;
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
     * Gets the entityReport object associated with this entity.
     * 
     * @return  the entityReport instance variable
     */
    public EntityReport getEntityReport()
    {
      return entityReport;
    }
    
    
    /**
     * Sets the identifier for this entity. Currently we use distribution url
     * as entity identifier.
     * 
     * @param identifier of this entity, a string holding the distribution url
     */
    public void setEntityIdentifier(String url)
    {
    	this.url = url;
    }
    
   
    /**
     * Sets the firstKilobyte string for the entity. Also,
     * if quality reporting is enabled, performs quality
     * checks on the first kilobyte of data.
     * 
     * @param firstKilobyte   the string value to set
     */
    public void setFirstKilobyte(String firstKilobyte) {
      this.firstKilobyte = firstKilobyte;

      /*
       *  Display the first chunk of data as a quality check
       */
      String displayDownloadIdentifier = "displayDownloadData";
      QualityCheck displayDownloadTemplate = 
        QualityReport.getQualityCheckTemplate(displayDownloadIdentifier);
      QualityCheck displayDownloadQualityCheck = 
        new QualityCheck(displayDownloadIdentifier, displayDownloadTemplate);

      if (QualityCheck.shouldRunQualityCheck(this, displayDownloadQualityCheck)) {
        /* String twoFiftySix = "";
        if (firstKilobyte != null) {
          twoFiftySix = firstKilobyte.substring(0, 256);
        }
        String foundString = "<![CDATA\n" + twoFiftySix + "\n]>"; */
        String foundString = null;
        if (isBinaryData()) {
          foundString = "*** BINARY DATA ***";
        }
        else {
          foundString = "<![CDATA[\n" + firstKilobyte.trim() + "]]>";
        }
        displayDownloadQualityCheck.setFound(foundString);
        displayDownloadQualityCheck.setStatus(Status.info);
        addQualityCheck(displayDownloadQualityCheck);
      }

      /*
       *  Check the veracity of the data returned
       */
      String urlDataIdentifier = "urlReturnsData";
      QualityCheck urlDataTemplate = 
        QualityReport.getQualityCheckTemplate(urlDataIdentifier);
      QualityCheck urlDataQualityCheck = 
        new QualityCheck(urlDataIdentifier, urlDataTemplate);

      if (QualityCheck.shouldRunQualityCheck(this, urlDataQualityCheck)) {
        boolean isHTML = isHTML(firstKilobyte);        
        if (isHTML) {
          String found = "The download URL for this entity returns HTML";
          urlDataQualityCheck.setFound(found);
          String explanation = "Either an HTML declaration string or an 'html' element was detected in the data";
          urlDataQualityCheck.setExplanation(explanation);
          String suggestion = "Specify function=\"information\" in the 'url' element when the URL links to an HTML page";
          urlDataQualityCheck.setSuggestion(suggestion);
          urlDataQualityCheck.setFailedStatus();
        }
        else {
          urlDataQualityCheck.setFound("true");
          urlDataQualityCheck.setStatus(Status.valid);
          urlDataQualityCheck.setSuggestion("");
        }
        addQualityCheck(urlDataQualityCheck);
      }
    }
    

    /*
     * Boolean to determine whether a data sample is 
     * actually an HTML page. 
     */
    private boolean isHTML(String sampleData) {
      boolean isHTML = false;
      
      if (sampleData != null) {
        String htmlDeclaration = "<!doctype html";
        String htmlElement1 = "<html ";
        String htmlElement2 = "<html>";
        String sampleDataLowerCase = sampleData.toLowerCase();
        
        // First check the MIME type
        if (urlContentType != null &&
            urlContentType.startsWith("text/html")
           ) {
          isHTML = true;
        }
        // else look for an HTML declaration
        else if (sampleDataLowerCase.contains(htmlDeclaration)) {
          isHTML = true;
        }
        // else look for an HTML tag
        else if (sampleDataLowerCase.contains(htmlElement1) ||
                 sampleDataLowerCase.contains(htmlElement2)
                ) {
          isHTML = true;
        }
      }
      
      return isHTML;
    }
    
    
    /**
     * Boolean to determine whether this entity's data is binary data
     * as opposed to character data.
     * 
     * @return  true if we determine that the entity has binary data,
     *          else false
     */
    public boolean isBinaryData() {
      boolean isBinary = false;
      
      /*
       * First check for a binary MIME type
       */
      if (isBinaryUrlContentType()) {
        isBinary = true;
      }
      
      /*
       * Then check to see whether we know this to be
       * binary based on the entity type
       */
      if (hasGZipDataFile ||
          hasTarDataFile ||
          hasZipDataFile ||
          isImageEntity ||
          isOtherEntity
         ) {
          isBinary = true;
      }

      return isBinary;
    }
    
    
    /*
     * Boolean to determine whether the URL contentType specifies
     * a binary data type.
     */
    private boolean isBinaryUrlContentType() {
      boolean isBinary = true;
      
      if (urlContentType != null) {
        /*
         * Check for known text content types
         */
        if (urlContentType.startsWith("text/") ||
            urlContentType.equals("application/xml")
           ) {
          isBinary = false;
        }
      }
      else {
        // Assume it could be text when no content type is specified
        isBinary = false;
      }
      
      return isBinary;
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
     * Sets the entity package id which it belongs to.
     * @param packageId the package id which the entity belongs to
     */
    public void setPackageId(String packageId)
    {
    	this.packageId = packageId;
    }
    
    /**
     * Gets the package id this entity belongs to 
     * @return pacakge id
     */
    public String getPackageId()
    {
    	return this.packageId;
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
            
        DownloadHandler handler = DownloadHandler.getInstance(this, url, endPointInfo);
        return handler;
    }

}
