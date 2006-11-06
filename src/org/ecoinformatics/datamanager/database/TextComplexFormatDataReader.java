/**
 *    '$RCSfile: TextComplexFormatDataReader.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-11-06 21:18:34 $'
 *   '$Revision: 1.2 $'
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
package org.ecoinformatics.datamanager.database;

import java.io.InputStream;
import java.util.Vector;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.ecoinformatics.util.DelimitedReader;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.TextComplexDataFormat;
import org.ecoinformatics.datamanager.parser.TextDelimitedDataFormat;
import org.ecoinformatics.datamanager.parser.TextWidthFixedDataFormat;

/**
 * @author tao
 * 
 * This class will read a data inputstream and split them into a row vectors base
 * on the given ComplexDataFormat array. This class have a public method -
 * getRowVector.After reach the end of stream, empty vector 
 * will be returned. So this method can be iterated by a while loop until
 * a empty vector hited. During the iteration, every data in the stream will
 * be pulled out.
 */
public class TextComplexFormatDataReader extends TextDataReader
{
   /*
    * Class fields
    */
  
   /*private static Log log;
  
   static {
      log = LogFactory.getLog( "org.kepler.objectmanager.data.text.TextComplexFormatDataReader" );
   }*/

   //constants
   public static final String DEFAULTVALUE = "";
  
   
   /*
    * Instance fields
    */
   private InputStream dataStream = null;
   private Entity entity = null;
   private boolean stripHeader = true;
   private int numberOfAttirbute = 0;
   private TextComplexDataFormat[] formats = null;
   private String physicalLineDelimiter = null;
   private int    numberOfHeaderLines   = 0;
   private int physicalLineDelimiterLength = 0;
   private int headLineNumberCount = 0;
   
   
   /*
    * Constructors
    */
   
   /**
    * Consturctor with default stripHeader value - true.
    * 
    * @param dataStream  the data input stream
    * @param enity       the entity metadata to describe the data stream
    */
   public TextComplexFormatDataReader(InputStream dataStream, Entity entity) 
           throws Exception
   {
      this(dataStream, entity, true);
   }
   
   
   /**
    * Constructor with assigned stripHeader value.
    * 
    * @param dataStream  the data input stream
    * @param enity       the entity metadata to describe the data stream
    * @param stripHeader if strip header when we hand read the input stream
    */
   public TextComplexFormatDataReader(InputStream dataStream, 
                                      Entity entity, 
                                      boolean stripHeader) 
           throws Exception
   {
       if (dataStream == null || entity == null)
       {
           throw new Exception("Data inputstream or entity metadata is null");
       }
       this.dataStream      = dataStream;
       this.entity          = entity;
       this.stripHeader     = stripHeader;
       getParameterFromEntity();
     
   }
   
   
   /*
    * Method to set up other parameter will be used in the reader.
    * Such as numberOfArributes, physicalLineDelimiter. 
    */
   private void getParameterFromEntity() throws Exception
   {
       Attribute[] attributeList = entity.getAttributes();
       
       if (attributeList == null)
       {
           throw new Exception("Attribute in entity metadata is null");
       }
       else
       {
           numberOfAttirbute = attributeList.length;
       }
       
       numberOfHeaderLines = entity.getNumHeaderLines();
       
       if (numberOfHeaderLines == -1)
       {
           numberOfHeaderLines = 0;
       }
       
       //physicalLineDelmiter will get from physicalDelimiter elements
       // if no physicalDelimiter element, we will try record delimter
       physicalLineDelimiter = entity.getPhysicalLineDelimiter();
       
       if (physicalLineDelimiter == null )
       {
           physicalLineDelimiter = entity.getRecordDelimiter();
       }
       
       physicalLineDelimiter = 
                   DelimitedReader.unescapeDelimiter(physicalLineDelimiter);
       physicalLineDelimiterLength = physicalLineDelimiter.length();
       
       formats = entity.getDataFormatArray();
       
       if (formats == null)
       {
           throw new Exception("Complext format is null in metadata entity");
       }
       else
       {
           int length = formats.length;
           
           if (length != numberOfAttirbute)
           {
               throw new Exception("Complex formats should have same number as attribute number"); 
           }
       }
   }
   
   
   /**
    * This method will read one row from inputstream and return a data vector 
    * which element is String and the value is field data. After reach the end 
    * of stream, empty vector will be returned. So this method can be iterated 
    * by a while loop until a empty vector hited. During the iteration, every 
    * data in the stream will be pulled out.
    * 
    * @return Vector
    */
   public Vector getOneRowDataVector() throws Exception
   {
     Vector oneRowDataVector = new Vector();
     StringBuffer lineDelimiterBuffer = new StringBuffer();// to store delmiter
     StringBuffer fieldValueBuffer = new StringBuffer();
     int singleCharactor = -2;
     int columnCount  =  1;// this is for every character in one row
     int attributeCount = 0; // this is for every attribute
     boolean startNewAttribute = true;
     boolean isWidthFix        = true;
     int width   = -1;
     int widthCount = 0;
     boolean startWidthCount = false;
     int startColumnNumberFromFormat = -1;
     String fieldDelimiter = null;
     
     if (dataStream != null)
     {
         singleCharactor = dataStream.read();
         
         while (singleCharactor != -1)
         {
           char charactor = (char)singleCharactor;
           // strip header
           if (stripHeader && numberOfHeaderLines > 0 && 
                   headLineNumberCount < numberOfHeaderLines)
           {
               lineDelimiterBuffer.append(charactor);
               if (lineDelimiterBuffer.length() == physicalLineDelimiterLength && 
                       lineDelimiterBuffer.toString().equals(physicalLineDelimiter))
               {
                   //reset the delimiter buffer
                   lineDelimiterBuffer = new StringBuffer();
                   headLineNumberCount++;
               }
               else if (lineDelimiterBuffer.length() == physicalLineDelimiterLength)
               {
                   // reset the delimiter buffer
                   lineDelimiterBuffer = new StringBuffer();
               }
              
           }
           else
           {
               // handle data after strip header
               fieldValueBuffer.append(charactor);
               lineDelimiterBuffer.append(charactor);
               
               // set up format info
               if (startNewAttribute)
               {
                  startNewAttribute = false;
                  //find the format from array
                  TextComplexDataFormat format = formats[attributeCount];
                  if (format == null)
                  {
                     throw new Exception(
                                    "The text format is null for an attribute");
                  }
                  else if (format instanceof TextWidthFixedDataFormat)
                  {
                     TextWidthFixedDataFormat widthFormat = 
                                              (TextWidthFixedDataFormat) format;
                     width = widthFormat.getFieldWidth();
                     startColumnNumberFromFormat = 
                                              widthFormat.getFieldStartColumn();
                     isWidthFix = true;
                     startWidthCount = false;

                   }
                   else if (format instanceof TextDelimitedDataFormat)
                   {
                      TextDelimitedDataFormat delimitedFormat = 
                                               (TextDelimitedDataFormat) format;
                      fieldDelimiter = delimitedFormat.getFieldDelimiter();
                      isWidthFix = false;
                   }
               }
               
               if (isWidthFix)
               {
                  // find start cloumn if metadata specify it
                  if (startColumnNumberFromFormat != -1 && 
                      startColumnNumberFromFormat == columnCount)
                  {
                      fieldValueBuffer = new StringBuffer();
                      fieldValueBuffer.append(charactor);
                      startWidthCount = true;
                  }
                  else if ( startColumnNumberFromFormat == -1)
                  {
                      startWidthCount = true;
                  }
                  // start count width
                  if (startWidthCount)
                  {
                      widthCount++;
                  }
                  // we got the value when widthcount reach width of this format
                  if (widthCount == width)
                  {
                      String value = fieldValueBuffer.toString();
                      //log.debug("Add width fixed attribute value " + value +
                      //       " to the vector");
                      oneRowDataVector.add(value.trim());
                      widthCount = 0;
                      startWidthCount = false;
                      fieldValueBuffer = new StringBuffer();
                      startNewAttribute = true;
                      attributeCount++;
                  }
                  
               }
               else
               {
                   // for delimter data
                   if (fieldValueBuffer.toString().endsWith(fieldDelimiter))
                   {
                       String value = fieldValueBuffer.toString();
                       value = value.substring(0, value.length() - 
                               fieldDelimiter.length());
                       //log.debug("Add delimited attribute value " + value +
                       //        " to the vector" );
                       oneRowDataVector.add(value.trim());
                       fieldValueBuffer = new StringBuffer();
                       startNewAttribute = true;
                       attributeCount++;
                   }
               }
               
               columnCount++;
               
               // reset columnCount to 1 when hit a physical line delimiter
               if (lineDelimiterBuffer.length() == physicalLineDelimiterLength 
                   && 
                   lineDelimiterBuffer.toString().equals(physicalLineDelimiter))
               {
                   //reset the delimiter buffer
                   lineDelimiterBuffer = new StringBuffer();
                   columnCount = 1;
               }
               else if 
                   (lineDelimiterBuffer.length() == physicalLineDelimiterLength)
               {
                   // reset the delimiter buffer
                   lineDelimiterBuffer = new StringBuffer();
               }
               
               // get a row vector break it.
               if (attributeCount == numberOfAttirbute)
               {
                   break;
               }
           }
           
           singleCharactor = dataStream.read();
         }
     }
     
     // if row vector is not empty and its length less than number of 
     // attributes, we should add "" string to make its length equal to
     // the attribute length.
     if (! oneRowDataVector.isEmpty() && 
           oneRowDataVector.size() <  numberOfAttirbute)
     {
         int size = oneRowDataVector.size();
         
         for (int i = size ; i< numberOfAttirbute; i++)
         {
             oneRowDataVector.add(DEFAULTVALUE);
         }
     }
     
     return oneRowDataVector;
   }
  
   
    /**
     * @return Returns the dataStream field.
     */
    public InputStream getDataStream()
    {
        return dataStream;
    }

    
    /**
     * Sets the dataStream field to a given input stream.
     * 
     * @param dataStream The InputStream value to set.
     */
    public void setDataStream(InputStream dataStream)
    {
        this.dataStream = dataStream;
    }

    
    /**
     * Gets the value of the entity field.
     * 
     * @return Returns the entity field.
     */
    public Entity getEntity()
    {
        return entity;
    }

    
    /**
     * Sets the value of the entity field to the specified Entity object.
     * 
     * @param entity  The Entity value to set.
     */
    public void setEntity(Entity entity)
    {
        this.entity = entity;
    }
    
}
