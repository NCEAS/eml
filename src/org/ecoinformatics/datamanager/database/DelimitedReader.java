/**
 *    '$RCSfile: DelimitedReader.java,v $'
 *
 *     '$Author: leinfelder $'
 *       '$Date: 2008-02-29 23:23:36 $'
 *   '$Revision: 1.9 $'
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
import java.io.InputStreamReader;
import java.util.Vector;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * tokenizes a delimited file.  This reader assumes that one record is on one
 * line which ends with the line
 */
public class DelimitedReader extends TextDataReader
{
  
  /*
   * Instance fields
   */
  
  private String data;
  private InputStreamReader dataReader;
  private Vector[] lines;
  private Vector linesVector;
  private int numHeaderLines;
  private int numRecords;
  private boolean stripHeader = false;
  //private int currentRecord = 0;
  private int numCols;
  private String delimiter;
  private String lineEnding;
  private boolean collapseDelimiter = false;
  private int numFooterLines = 0;
  private Vector footerBuffer = new Vector();
  private boolean initializedFooterBuffer = false;
  private int headLineNumberCount = 0;
  private String quoteCharacter = null;
  private String literalCharacter = null;
  private boolean includeLiteralCharacter = false;
  

  /*private static Log log;
  static {
	  log = LogFactory.getLog("org.ecoinformatics.util.DelimitedReader");
  }*/
  
  
  /*
   * Constructors
   */

  /**
   * Constructor. Reads the csv (comma-separated values) stream.
   * 
   * @param data           the delimited stream to read as a string
   * @param numCols        the number of columns in the stream
   * @param delimiter      the delimiter string to tokenize on
   * @param numHeaderLines the number of lines to skip at the top of the file
   * @param lineEnding     the line ending char(s)...either "\n" (Unix),
   *                       "\r\n" (Windows) or "\r" (Mac)
   * @param numRecords     the number of rows in the data string
   */
  public DelimitedReader(String data, int numCols, String delimiter,
                         int numHeaderLines, String lineEnding, int numRecords) 
          throws Exception
  {
    this.numHeaderLines = numHeaderLines;
    this.data = data;
    this.numCols = numCols;
    this.numRecords = numRecords;    
    //log.debug("Delimiter is: " + delimiter);
    this.delimiter = unescapeDelimiter(delimiter);
    //log.debug("LineEnding is: " + lineEnding);
    this.lineEnding = unescapeDelimiter(lineEnding);
    
    //lines = new Vector[numRecords + numHeaderLines + 1];
    linesVector = new Vector();

    int begin = 0;
    int end = 0;
//    int i = 0;
    
    while(end < data.length())
    { //add each line of the string as an element in a vector
      end = data.indexOf(this.lineEnding, begin); //DFH 'this.' added
      
      if(end == -1)
      {
        end = data.length();
      }
      
      String line = data.substring(begin, end);
      
      if(!line.trim().equals(""))
      {
        //take off the line ending
        // MBJ: I commented out the next line as it was improperly truncating 
        // lines. I'm not sure why it was there in the first place, as the 
        // previous substring removed the delimiter
        //line = line.substring(0, line.length() - lineEnding.length());

        //split the line based on the delimiter
        Vector v = splitDelimitedRowStringIntoVector(line);
        /*String[] s = line.split(delimiter.trim(), numCols);
        Vector v = new Vector();
        
        for(int j=0; j<s.length; j++)
        {
          v.addElement(s[j]);
        }

        if(v.size()  < numCols)
        {
          int vsize = v.size();
          for(int j=0; j<numCols - vsize; j++)
          { //add any elements that aren't there so that all the records have the
            //same number of cols
            v.addElement("");
          }
        }*/
        
        //lines[i] = v;
        linesVector.add(v);
//        i++;
      }
      
      //go to the next line
      begin = end + this.lineEnding.length();   //DFH  'this.' added
    }
    
    int records = linesVector.size();
    
    if (records != this.numRecords) {
        this.numRecords = records;
        //log.warn("Metadata disagrees with actual data. Changing number of records to: " + records);
    }
    
    lines = new Vector[records];
    
    for (int k=0; k < records; k++) {
        lines[k] = (Vector)linesVector.get(k);
    }
/*
    for(int j=0; j<lines.length; j++)
    {
      if(lines[j] == null)
      {
        lines[j] = new Vector();
      }
    }
*/
    
  }
  
  
  /**
   * This constructor will read delimited data from stream rather a string.
   * 
   * @param dataStream     InputStream  The input stream
   * @param numCols        int the number of columns
   * @param delimiter      String the delimiter to tokenize on
   * @param numHeaderLines int numHeaderLines the number of lines to skip at the
   *                       top of the file
   * @param lineEnding     String lineEnding the line ending char(s)...either 
   *                       "\n" (Unix),"\r\n" (Windows) or "\r" (Mac)
   * @param numRecords     int number of rows in the input stream
   */
  public DelimitedReader(InputStream dataStream, int numCols, String delimiter,
                         int numHeaderLines, String lineEnding, int numRecords, 
                         boolean stripHeader)
  {
    this.dataReader = new InputStreamReader(dataStream);
    this.numHeaderLines = numHeaderLines;
    this.numCols = numCols;
    this.numRecords = numRecords;    
    //log.debug("Delimiter is: " + delimiter);
    this.delimiter = unescapeDelimiter(delimiter);
    //log.debug("LineEnding is: " + lineEnding);
    this.lineEnding = unescapeDelimiter(lineEnding);
    this.stripHeader = stripHeader;

  }
  
  
  /*
   * Class methods
   */
  
  /**
   * Convert a string-escaped representation of a delimiter character into
   * an actual String for that delimiter.  This is used for translating
   * escaped versions of tab, newline, and carriage return characters to
   * their real character values.
   * 
   * @param delimiter the String representing the delimiter
   * @return the actual String for the delimiter
   */
  public static String unescapeDelimiter(String delimiter)
  {
      String newDelimiter = delimiter;
      
      if (delimiter == null) {
          //log.debug("Delimiter is null and we set up to \n.");
          newDelimiter = "\n";   
      }else if (delimiter.equals("\\t")) {
          //log.debug("Tab interpreted incorrectly as string.");
          newDelimiter = "\t";
      } else if (delimiter.equals("\\n")) {
          //log.debug("Newline interpreted incorrectly as string.");
          newDelimiter = "\n";
      } else if (delimiter.equals("\\r")) {
          //log.debug("CR interpreted incorrectly as string.");
          newDelimiter = "\r";
      } else if (delimiter.equals("\\r\\n")) {
          //log.debug("CRNL interpreted incorrectly as string.");
          newDelimiter = "\r\n";
      } else if (delimiter.startsWith("#")){
          //log.debug("XML entity charactor.");
          String digits = delimiter.substring(1, delimiter.length());
          int radix = 10;
          if (digits.startsWith("x"))
          {
              //log.debug("Radix is "+ 16);
              radix = 16;
              digits = digits.substring(1, digits.length());
          }
          //log.debug("Int value of  delimiter is "+digits);
          
          newDelimiter = transferDigitsToCharString(radix, digits);
          
      }
      else if (delimiter.startsWith("0x") || delimiter.startsWith("0X"))
      {
          int radix = 16;
          String digits = delimiter.substring(2, delimiter.length());
          //log.debug("Int value of  delimiter is "+digits);
          newDelimiter = transferDigitsToCharString(radix, digits);
      }
      
      return newDelimiter;
  }
  
  
  /**
   * Auxiliary method called by unescapeDelimiter(). Transforms digits for a 
   * given radix into the equivalent character value.
   * 
   * @param radix        the radix value, e.g. 8 or 16
   * @param digits       a string holding the digits to be transformed
   * @return             a string holiding the equivalent character value
   */
  private static String transferDigitsToCharString(int radix, String digits)
  {
      if (digits == null )
      {
          return null;
      }
      Integer integer = Integer.valueOf(digits, radix);
      int inter = integer.intValue();
      //log.debug("The decimal value of char is "+ inter);
      char charactor =(char)inter;
      String newDelimiter = Character.toString(charactor);
      //log.debug("The new delimter is "+newDelimiter);
      return newDelimiter;
  }
    
  
  /*
   * Instance methods
   */
 
 /**
  * Method to set up data stream as source
  * 
  * @param dataStream InputStream
  */
  public void setInputStream(InputStream dataStream)
  {
    this.dataReader = new InputStreamReader(dataStream);
  }
  
  
  /**
   * Method to set collapseDilimiter boolean value.
   * 
   * @param collapseDelimiter   if true, consecutive delimiters are collapsed
   *                            into a single delimiter
   */
  public void setCollapseDelimiter(boolean collapseDelimiter)
  {
	  this.collapseDelimiter = collapseDelimiter;
  }
  
  
  /**
   * Set up the footer line number.
   * 
   * @param numFooterLines
   */
  public void setNumFooterLines(int numFooterLines)
  {
	  this.numFooterLines = numFooterLines;
  }
  
  
  /**
   * Set quote character for this reader
   * @param quoteCharacter  the quote chacater value
   */
  public void setQuoteCharacter(String quoteCharacter)
  {
	  this.quoteCharacter = quoteCharacter;
  }
  
  /**
   * Set literal character for this reader
   * @param literalCharacter  the literal character value
   */
  public void setLiteralCharacter(String literalCharacter)
  {
	  this.literalCharacter = literalCharacter; 
  }
  
  
  /**
   * This method is used when data source is an input stream.
   * Reads one row from the input stream and returns a data vector where element 
   * is String and the value is field data. After reaching the end of stream, 
   * empty vector will be returned. So this method can be iterated by a while 
   * loop until a empty vector is hit. During the iteration, all data in the 
   * stream will be pulled out.
   * 
   * @return Vector
   */
  public Vector getOneRowDataVector() throws Exception
  {
	 //System.out.println("the numFootLines is "+numFooterLines);
	 if (!initializedFooterBuffer)
	 {
		 for (int i=0; i< numFooterLines; i++)
		 {
			 //System.out.println("the initialize with footer lines");
			 String rowData = readOneRowDataString();
			 //System.out.println("the data vector in initailize is "+rowData.toString());
			 footerBuffer.add(rowData);
		 }
         
		 // this is for no footer lines
		 if (numFooterLines == 0)
		 {
			 //System.out.println("the initialize without footer lines");
			 String rowData = readOneRowDataString();
			 //System.out.println("The initial buffere vector is "+rowData.toString());
			 footerBuffer.add(rowData);
		 }
         
		 initializedFooterBuffer = true;
	 }
     
	 String nextRowData = readOneRowDataString();
	 //System.out.println("the row string data from next row "+nextRowData.toString());
     String oneRowDataString = null;
     Vector oneRowDataVector = new Vector();
     
     if (nextRowData != null)
     {
    	 //System.out.println("before nextRowData is empty and nextRowData is "+nextRowData.toString());
    	 oneRowDataString = (String)footerBuffer.remove(0);
         reIndexFooterBufferVector();
    	 footerBuffer.add(nextRowData);
     }
     else if (numFooterLines==0 && !footerBuffer.isEmpty())
     {
    	 //System.out.println("find the last line in fottlines num is 0!!!!!!!!");
    	 oneRowDataString = (String)footerBuffer.remove(0);
     }
     
     //System.out.println("helere!!!");
     if (oneRowDataString != null)
	 {
	      //log.debug("in dataReader is not null");
	      oneRowDataVector = splitDelimitedRowStringIntoVector(oneRowDataString);
	 }
     //System.out.println("the row data from buffer "+oneRowDataVector.toString());
     return oneRowDataVector;
  }
  
  
  /*
   * This method will read a row data from vector. It
   * discard the header lines. but it doesn't discard footer lines
   * This method will be called by getRowDataVectorFromStream
   * 
   * @return   A string holding one row of data.
   */
  private String readOneRowDataString()
  {
	    //Vector oneRowDataVector = new Vector();
	    StringBuffer rowData = new StringBuffer();
	    String rowDataString = null;
	    int singleCharactor; // = -2;
	    
	    if (dataReader != null)
	    {
	      //log.debug("in dataReader is not null");
	      try
	      {
	    	//read the first character to start things off  
	    	singleCharactor = dataReader.read();
	        while (singleCharactor != -1)
	        {
	          //log.debug("singleCharactor is not EOF");
	          char charactor = (char)singleCharactor;
	          rowData.append(charactor);
              
	          // find string - line ending in the row data   
	          if (rowData.indexOf(lineEnding) != -1)
	          {
	        	//log.debug("found line ending");
	            // strip the header lines
	            if (stripHeader && numHeaderLines >0 && headLineNumberCount < numHeaderLines)
	            {
	               // reset string buffer(discard the header line)
	               rowData = null;
	               rowData = new StringBuffer();
	               
	            }
	            else
	            {
	              rowDataString = rowData.toString();
	              //log.debug("The row data is " + rowDataString);
	              break;
	            }
                
	            headLineNumberCount++;
	          }
	          //read the next character before looping back
	          singleCharactor = dataReader.read();
	        }
	      }
	      catch (Exception e)
	      {
	        //log.debug("Couldn't read data from input stream");
	    	  e.printStackTrace();
	        rowData = new StringBuffer();
	      }
	    }

	    //if we have data for the row, then return it
	    if (rowData != null && rowData.length() > 0) {
	    	rowDataString = rowData.toString();
	    }
	    //System.out.println("the row data before return is "+rowDataString);
	    return rowDataString;
  }
  
  
  /*
   * This method will forward one index for every element, 1 -> 0, 2->1
   */
  private void reIndexFooterBufferVector()
  {
	  for (int i=0; i<numFooterLines-2; i++)
	  {
		  Vector element = (Vector)footerBuffer.elementAt(i+1);
		  footerBuffer.add(i, element);
	  }
  }
  
  
  /*
   * This method will read a delimitered string and put a delimitered part into
   * an element in a vector. If the vector size is less than the column number
   * empty string will be added.
   * 
   */
  private Vector splitDelimitedRowStringIntoVector(String data) throws Exception
  {
    Vector result = new Vector();
    
    if (data == null)
    {
      return result;
    }
    
    String[] s = null;
    if (quoteCharacter == null && literalCharacter == null)
    {
    	//In this path, there is no quote character, we can spit data directly
	    if (!collapseDelimiter)
	    {
	    	s = data.split(delimiter);
	    }
	    else
	    {
	    	String newDelimiterWithRegExpress = delimiter+"+";
	    	s = data.split(newDelimiterWithRegExpress);
	    	
	    }
    }
    else
    {
    	//In this path, we should skip any field delimiter in quote charcter.
    	s = processQuoteCharacterOneRowData(data);
    }
    
    if( s != null)
    {
    	int columnCounter = s.length;
        
    	if ( columnCounter > numCols)
    	{
    		throw new DataNotMatchingMetadataException(
                                "Metadata sees data has " 
                                + numCols +
    				            " columns, but actually data has " +
                                columnCounter + " columns. " + 
                                "Please make sure metadata is correct!"
                               );
    	}
        
        for (int j = 0; j < s.length; j++) 
        {
          
          if (s[j] != null)
          {
            result.addElement(s[j].trim());
          }
          else
          {
              result.addElement("");
          }
        }
        
        //add any elements that aren't there so that all the records have the
        //same number of cols
        if (result.size() < numCols) 
        {
          int vsize = result.size();
          for (int j = 0; j < numCols - vsize; j++) 
          {
            result.addElement("");
          }
        }
    }
    
    return result;
  }
  
  
  /*
   * In oneRowData, there are quote character in it. Any field delimiter in the
   * quotes should be skiped.
   */
  private String[] processQuoteCharacterOneRowData(String oneRowData) throws Exception
  {
	  String[] elements = null;
	  Vector elementsVector = new Vector();
	  if (oneRowData == null)
	  {
		  return elements;
	  }
	  quoteCharacter = transferQuoteCharacter(quoteCharacter);
	  char quote = '#';
	  boolean quoted = false;
	  if (quoteCharacter != null)
	  {
		  quoted = true;
		  quote = quoteCharacter.charAt(0);
	  }
	  char literal = '/';
	  boolean literaled = false;
	  if (literalCharacter != null)
	  {
		  literaled = true;
		  literal = literalCharacter.charAt(0);
	  }	  
	  if (literaled && literalCharacter.length() !=1)
	  {
		  throw new Exception("Literal Character length should be 1 character in EML");
	  }
	  char currentChar  ='2';
	  StringBuffer fieldData = new StringBuffer();	  
	  int length = oneRowData.length();
	  int priviousDelimiterIndex = -2;
	  int currentDelimiterIndex = -2;
	  int delimiterLength = delimiter.length();
	  boolean startQuote = false;
	  boolean delimiterAtEnd = false;
	  //this string buffer is only for deleting if hit a delimiter
	  StringBuffer delimiterStorage = new StringBuffer(delimiter.length());
	  for (int i=0; i<length; i++)
	  {
		  currentChar = oneRowData.charAt(i);
		  //System.out.println("current char is "+currentChar);
		  fieldData.append(currentChar);
		  if (i < delimiterLength)
		  {
			 delimiterStorage.append(currentChar);
		  }
		  else
		  {			  
		    //delimiterStorage.deleteCharAt(position);
		    delimiterStorage = shiftBuffer(delimiterStorage, currentChar);
		  }
		  //System.out.println("current delimiter storage content is "+delimiterStorage.toString());
		  //System.out.println("currnet value in the string buffer is "+fieldData.toString());
          // we should check if there is quoteCharacter in the string.
		  if (quoted && currentChar == quote)
		  {
			  char previousChar = '1';
			  boolean escapingQuote = false;
			  // check if this quote is escaped
			  if (literaled)
			  {
				  if ((i-1) >= 0)
				  {
					  previousChar = oneRowData.charAt(i-1);
					  if (previousChar == literal)
					  {
					     escapingQuote = true;
					     // delette the literal character
					     if (!includeLiteralCharacter)
						  {
							  //if we don't want literal character in the data,
							  //we should delete literal character.
							  int fieldLength = fieldData.length();
							  if ((fieldLength -1-1) >=0)
							  {
								  fieldData.deleteCharAt(fieldLength-1-1);
							  }
						  }
					  }
				  }
			  }
			  if (!escapingQuote)
			  {
				  if (!startQuote)
				  {
					  //System.out.println("start quote");
					  startQuote = true;
				  }
				  else 
				  {
					  //System.out.println("end quote");
					  // at end of quote
					  //put string buffers value into vector and reset string buffer
					  startQuote = false;
					 
				  }
			  }
			  
			 
		  }
		 
		  //found a delimiter
		  if (delimiterStorage.indexOf(delimiter) != -1 && !startQuote)
		  {
			  
			  //check if there is literal character before the delimiter,
			  //if does, this we should skip this delmiter
			  int indexOfCharBeforeDelimiter = i - delimiterLength;
			  boolean escapeDelimiter = false;
			  if (literaled && indexOfCharBeforeDelimiter >= 0)
			  {
				  char charBeforeDelimiter = oneRowData.charAt(indexOfCharBeforeDelimiter);
				  ////there is a literal character before delimiter we should skip this demlimiter
				  if (charBeforeDelimiter == literal)
				  {
	     			  if (!includeLiteralCharacter)
					  {
						  //if we don't want literal character in the data,
						  //we should delete literal character.
						  int fieldLength = fieldData.length();
						  if ((fieldLength - delimiterLength -1) >=0)
						  {
							  fieldData.deleteCharAt(fieldLength-delimiterLength-1);
						  }
					  }
	     			  escapeDelimiter = true;
					  continue;				  
				  }
			  }
			  
			  // check if the delimiter is in the end of the string
			  if (i == (length-1) && !startQuote && !escapeDelimiter)
			  {
				  delimiterAtEnd = true;
			  }

			  ////here we should treat sequential delimiter as single delimiter
			  if (collapseDelimiter)
			  {
				  priviousDelimiterIndex = currentDelimiterIndex;
				  currentDelimiterIndex = i;
				  //there is nothing between two delimiter, should skip it.
				  if ((currentDelimiterIndex - priviousDelimiterIndex) == delimiterLength)
				  {
					  //delete sequnced delimiter
					  fieldData = new StringBuffer();
					  continue;
				  }
			  }			  
			  
			  String value ="";
			  int delimiterIndex = fieldData.lastIndexOf(delimiter);
			  if (delimiterIndex ==0)
			  {
				  //this path means field data on has delimiter, no real data
				  value ="";
			  }
			  else
			  {
			      value = fieldData.substring(0, delimiterIndex);
			   
			  }
			  elementsVector.add(value);
			  //reset string buffer fieldData
			  fieldData = new StringBuffer();
			 
		  }
	  }
	  // if startQuote is true at the end, which means there is no close quote character in this row,
	  // code should throw an exception
	  if (startQuote)
	  {
		  throw new Exception("There is a un-closed quote in data file");
	  }
	  // add last field. If this string end of delimiter, we need add a ""
	  // else, we need to add the value in string buffer.
	  String lastFieldValue = null;
	  if (delimiterAtEnd == true)
	  {
		  //this path means field data on has delimiter, no real data
		  lastFieldValue ="";
	  }
	  else 
	  {
		  lastFieldValue = fieldData.toString();
	   
	  }
	  elementsVector.add(lastFieldValue);
	  //transform vector to string array
	  int size = elementsVector.size();
	  elements = new String[size];
	  for (int i=0; i<size; i++)
	  {
		  elements[i] =(String)elementsVector.elementAt(i);
	  }
	  return elements;
  }
  
  /*
   * This method will delete the most left char in the given buffer,
   * and append the new charact at the end. So the buffer size will 
   * keep same
   */
  private static StringBuffer shiftBuffer(StringBuffer buffer, char newChar)
  {
	  StringBuffer newBuffer = new StringBuffer();
	  if (buffer == null)
	  {
		  return newBuffer;
	  }
	  int size = buffer.length();
	  for (int i=0; i<size; i++)
	  {
		 char oldChar = buffer.charAt(i);
		 if (i>0)
		 {
			 newBuffer.append(oldChar);
		 }
	  }
	  newBuffer.append(newChar);
	  return newBuffer;
  }
  
  /*
   * If quote character is specified by hex number, we should transfer it
   * to a character. If quote character is longer than 1 character, it
   * should be throw an exception
   */
  private String transferQuoteCharacter(String quote) throws Exception
  {
	  	  String newQuote = quote;
	  	  if (newQuote == null)
	  	  {
	  		  return newQuote;
	  	  }
	      else if (newQuote.startsWith("#") && newQuote.length() >1)
	      {
             //log.debug("XML entity charactor.");
	          String digits = newQuote.substring(1, newQuote.length());
	          int radix = 10;
	          if (digits.startsWith("x"))
	          {
	              //log.debug("Radix is "+ 16);
	              radix = 16;
	              digits = digits.substring(1, digits.length());
	          }
	          //log.debug("Int value of  delimiter is "+digits);
          
             newQuote = transferDigitsToCharString(radix, digits);
          
	      }
	      else if ((newQuote.startsWith("0x") || newQuote.startsWith("0X")) && newQuote.length() >2)
	      {
	          int radix = 16;
	          String digits = newQuote.substring(2, newQuote.length());
	          //log.debug("Int value of  delimiter is "+digits);
	          newQuote = transferDigitsToCharString(radix, digits);
	      }
          
	  	  if (newQuote.length() > 1)
	  	  {
	  		  throw new Exception("Quote Character length should be 1 character in EML");
	  	  }
          return newQuote;
  }
  
  /**
   * Returns the data as an array of vectors.  Each vector will have the same
   * number of elements as there are columns in the data.
   * 
   * @param stripHeaderLines true if the header lines should not be included
   *                         in the returned data, false otherwise
   */
  public Vector[] getTokenizedData(boolean stripHeaderLines)
  {
    if(stripHeaderLines)
    {
      Vector[] strip = null;
      
      if (numRecords > numHeaderLines)
      {
        strip = new Vector[numRecords-numHeaderLines];
        
        for(int i=numHeaderLines; i<lines.length; i++)
        {
          strip[i-numHeaderLines] = lines[i];
        }
      }
      
      return strip;
    }
    else
    {
      return lines;
    }
  }

  
  /**
   * Returns a string representation of the data.
   * 
   * @return the String representation of the data
   */
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    
    for(int i=0; i<lines.length; i++)
    {
      //log.debug("line[" + (i + 1) + "]: " + lines[i].toString());
      
      for(int j=0; j<lines[i].size(); j++)
      {
        sb.append((String)lines[i].elementAt(j));
        if(j != lines[i].size() - 1)
        {
          sb.append(" || ");
        }
      }
      
      sb.append(lineEnding);
    }
    
    return sb.toString();
  }
  
}
