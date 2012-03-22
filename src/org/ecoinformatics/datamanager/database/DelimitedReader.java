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
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Vector;

import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.quality.QualityCheck;
import org.ecoinformatics.datamanager.quality.QualityCheck.Status;
import org.ecoinformatics.datamanager.quality.QualityReport;

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
  private int numCols;
  private String fieldDelimiter;
  private String lineEnding;
  private boolean collapseDelimiters = false;
  private int numFooterLines = 0;
  private Vector footerBuffer = new Vector();
  private boolean initializedFooterBuffer = false;
  private int headLineNumberCount = 0;
  private String quoteCharacter = null;
  private String literalCharacter = null;
  private boolean includeLiteralCharacter = false;
  private Entity entity;
  private int rowCounter = 0;
  
  
  // Used for quality reporting purposes
  private int tooFewFieldsCounter = 0;   // Counts 'tooFewFields' errors
  private int tooManyFieldsCounter = 0;  // Counts 'tooManyFields' errors
  private final int FIELD_CHECK_MAX = 5; // Max number of field count checks to report
  private int examineRecordDelimiterCounter = 0; // Counts 'examineRecordDelimiter' checks
  private final int EXAMINE_RECORD_DELIMITER_MAX = 1; // Max number of examineRecordDelimiter checks
  

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
    this.fieldDelimiter = unescapeDelimiter(delimiter);
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
   * @param fieldDelimiter String the field delimiter to tokenize on
   * @param numHeaderLines int numHeaderLines the number of lines to skip at the
   *                       top of the file
   * @param lineEnding     String lineEnding the line ending char(s)...either 
   *                       "\n" (Unix),"\r\n" (Windows) or "\r" (Mac)
   * @param numRecords     int number of rows in the input stream
   */
  public DelimitedReader(InputStream dataStream, int numCols, String fieldDelimiter,
                         int numHeaderLines, String lineEnding, int numRecords, 
                         boolean stripHeader)
  {
    this.dataReader = new InputStreamReader(dataStream);
    this.numHeaderLines = numHeaderLines;
    this.numCols = numCols;
    this.numRecords = numRecords;    
    this.fieldDelimiter = unescapeDelimiter(fieldDelimiter);
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
      } else if (delimiter.startsWith("#")) {
          /*
           * Handle some common two-character escape sequences used as
           * record delimiters
           */
          if (delimiter.equalsIgnoreCase("#x0D#x0A")) {
            newDelimiter = "\r\n";
          }
          else if (delimiter.equalsIgnoreCase("#x0A#x0D")) {
            newDelimiter = "\n\r";
          }
          else if (delimiter.equalsIgnoreCase("#x0A#x0A")) {
            newDelimiter = "\n\n";
          }
          else if (delimiter.equalsIgnoreCase("#x0D#x0D")) {
            newDelimiter = "\r\r";
          }
          else {
            String digits = delimiter.substring(1, delimiter.length());
            int radix = 10;
            if (digits.startsWith("x"))
            {
              radix = 16;
              digits = digits.substring(1, digits.length());
            }
          
            newDelimiter = transformDigitsToCharString(radix, digits);
          }
      }
      else if (delimiter.startsWith("0x") || delimiter.startsWith("0X"))
      {
        /*
         * Handle some common two-character escape sequences used as
         * record delimiters
         */
        if (delimiter.equalsIgnoreCase("0x0D0x0A")) {
          newDelimiter = "\r\n";
        }
        else if (delimiter.equalsIgnoreCase("0x0A0x0D")) {
          newDelimiter = "\n\r";
        }
        else if (delimiter.equalsIgnoreCase("0x0A0x0A")) {
          newDelimiter = "\n\n";
        }
        else if (delimiter.equalsIgnoreCase("0x0D0x0D")) {
          newDelimiter = "\r\r";
        }
        else {
          int radix = 16;
          String digits = delimiter.substring(2, delimiter.length());
          newDelimiter = transformDigitsToCharString(radix, digits);
        }
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
  private static String transformDigitsToCharString(int radix, String digits)
  {
      if (digits == null )
      {
          return null;
      }
      
      try {
        Integer integer = Integer.valueOf(digits, radix);
        int anInt = integer.intValue();
        char aChar =(char) anInt;
        String newDelimiter = Character.toString(aChar);
        return newDelimiter;
      }
      catch (NumberFormatException e) {
        String message = 
            "An error occurred while attempting to unescape a " +
            "delimiter value. Error transforming string '" + digits + 
            "' to an integer value.";
        NumberFormatException newException = new NumberFormatException(message);
        throw newException;
      }
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
   * Method to set collapseDelimiters boolean value.
   * 
   * @param collapseDelimiters  if true, consecutive delimiters are collapsed
   *                            into a single delimiter
   */
  public void setCollapseDelimiters(boolean collapseDelimiters)
  {
	  this.collapseDelimiters = collapseDelimiters;
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
   * Reads one row from the input stream and returns a data vector where 
   * each element is a String and the value is field data. After reaching the
   * end of stream, an empty vector will be returned. So this method can be 
   * iterated by a while loop until an empty vector is hit. During the 
   * iteration, all data in the stream will be pulled out.
   * 
   * @return Vector<String>
   */
  public Vector<String> getOneRowDataVector() 
          throws Exception {
    rowCounter++;
    if (!initializedFooterBuffer) {
		  for (int i = 0; i < numFooterLines; i++) {
			  String rowData = readOneRowDataString();
			  footerBuffer.add(rowData);
		  }
         
		  // this is for no footer lines
		  if (numFooterLines == 0) {
			  String rowData = readOneRowDataString();
			  footerBuffer.add(rowData);
		  }
         
		  initializedFooterBuffer = true;
    }
     
    String nextRowData = readOneRowDataString();
    String oneRowDataString = null;
    Vector<String> oneRowDataVector = new Vector<String>();
     
    if (nextRowData != null) {
      oneRowDataString = (String) footerBuffer.remove(0);
      reIndexFooterBufferVector();
      footerBuffer.add(nextRowData);
    }
    else if (numFooterLines == 0 && !footerBuffer.isEmpty()) {
      oneRowDataString = (String)footerBuffer.remove(0);
    }
     
    if (oneRowDataString != null) {

      /*
       * Quality check: 'examineRecordDelimiter'
       */
      if (examineRecordDelimiterCounter < EXAMINE_RECORD_DELIMITER_MAX) {
        /*
         * If no valid record delimiter is specified in metadata, first row of 
         * data is examined and a potential delimiter displayed.
         */
        String examineRecordDelimiter = "examineRecordDelimiter";
        QualityCheck examineRecordDelimiterTemplate = QualityReport
            .getQualityCheckTemplate(examineRecordDelimiter);
        QualityCheck examineRecordDelimiterQualityCheck = new QualityCheck(
            examineRecordDelimiter, examineRecordDelimiterTemplate);

        if (QualityCheck.shouldRunQualityCheck(entity,
            examineRecordDelimiterQualityCheck)) {
          String found = null;
          String metadataRecordDelimiter = entity.getMetadataRecordDelimiter();
          
          /*
           * If metadata didn't specify a valid record delimiter, check
           * whether other potential candidates can be identified.
           */
          ArrayList<String> otherDelimiters = otherRecordDelimiters(oneRowDataString, metadataRecordDelimiter);
          boolean hasSuggestedDelimiter = 
            entity.isSuggestedRecordDelimiter(metadataRecordDelimiter);
         
          if (otherDelimiters.size() > 0) {
            found = 
              "Other potential record delimiters were found in the first row: ";
            found += otherDelimiters.toString();
            examineRecordDelimiterQualityCheck.setFailedStatus();
          }
          else {
            found = "No other potential record delimiters were detected.";
            if (hasSuggestedDelimiter) {
              found += " A valid record delimiter was previously detected";
              examineRecordDelimiterQualityCheck.setStatus(Status.valid);
              examineRecordDelimiterQualityCheck.setExplanation("");
              examineRecordDelimiterQualityCheck.setSuggestion("");
            }
            else {
              examineRecordDelimiterQualityCheck.setFailedStatus();
            }
          }

          examineRecordDelimiterQualityCheck.setFound(found);
          entity.addQualityCheck(examineRecordDelimiterQualityCheck);
        }
        examineRecordDelimiterCounter++;
      }
      
      oneRowDataVector = splitDelimitedRowStringIntoVector(oneRowDataString);
    }

    return oneRowDataVector;
  }
  
  
  /*
   * Used in quality reporting for the 'examineRecordDelimiter' quality check.
   * Check whether a row of data contains other potential record delimiters
   * besides the record delimiter specified in the metadata.
   */
  private ArrayList<String> otherRecordDelimiters(String row, String metadataDelimiter) {
    ArrayList<String> otherDelimiters = new ArrayList<String>();
    
    if (row != null) {
        if (row.contains("\n")) {
          if (metadataDelimiter == null || 
               (!metadataDelimiter.equals("\\n") && 
                !metadataDelimiter.equals("#x0A")
               )
             ) {
            otherDelimiters.add("\\n");
          }
        }

        if (row.contains("\r")) {
          if (metadataDelimiter == null || 
               (!metadataDelimiter.equals("\\r") && 
                !metadataDelimiter.equals("#x0D")
               )
             ) {
            otherDelimiters.add("\\r");
          }
        }

        if (row.contains("\r\n")) {
          if (metadataDelimiter == null || 
               (!metadataDelimiter.equals("\\r\\n") && 
                !metadataDelimiter.equals("#x0D#x0A")
               )
             ) {
            otherDelimiters.add("\\r\\n");
          }
        }

    }
    
    return otherDelimiters;
  }
  
  
  /*
   * This method will read a row of data from a vector. It
   * discards the header lines but it doesn't discard the footer lines.
   * This method is called by method getRowDataVectorFromStream().
   * 
   * @return   A string holding one row of data.
   */
  private String readOneRowDataString() {
    StringBuffer rowBuffer = new StringBuffer();
    String rowDataString = null;
    int singleCharacter;
	    
    if (dataReader != null) {
      try {
        // Read the first character to start things off  
        singleCharacter = dataReader.read();
        
        while (singleCharacter != -1) {
	        // singleCharacter is not the EOF character
          char aCharacter = (char) singleCharacter;
          rowBuffer.append(aCharacter);
              
          // Check for a line ending character in the row data   
          if (rowBuffer.indexOf(lineEnding) != -1) {
	          // Strip the header lines
            if (stripHeader && 
                numHeaderLines > 0 && 
                headLineNumberCount < numHeaderLines) {
	            // Reset string buffer (discard the header line)
              rowBuffer = null;
              rowBuffer = new StringBuffer();  
	          }
	          else {
              rowDataString = rowBuffer.toString();
              break;
            }

            headLineNumberCount++;
          }
	          
          // Read the next character before looping back
          singleCharacter = dataReader.read();
        }
      }
      catch (Exception e) {
        // Couldn't read data from input stream
        e.printStackTrace();
        rowBuffer = new StringBuffer();
	    }
    }

    // If we have data for the row, then return it
    if (rowBuffer != null && rowBuffer.length() > 0) {
      rowDataString = rowBuffer.toString();
    }
    
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
   * This method will read a delimited string and put a delimited part into
   * an element in a vector. If the vector size is less than the column number,
   * empty strings will be added.
   * 
   */
  private Vector<String> splitDelimitedRowStringIntoVector(String data) 
          throws Exception {
    Vector<String> rowVector = new Vector<String>();
    
    if (data == null) {
      return rowVector;
    }
    
    String[] stringArray = null;
    
    /*
     * If there is no quote character, we can split data directly
     */
    if (quoteCharacter == null && literalCharacter == null) {
      String delimiterRegex = collapseDelimiters ? fieldDelimiter + "+" : fieldDelimiter;
	    stringArray = data.split(delimiterRegex);
    }
    /*
     * Else, we should skip any field delimiters 
     * found between pairs of quote characters.
     */
    else {
    	stringArray = processQuoteCharacterOneRowData(data);
    }
    
    if (stringArray != null) {
    	int columnCounter = stringArray.length;
        
      /*
       *  Quality check for too few fields
       */
      String tooFewIdentifier = "tooFewFields";
      QualityCheck tooFewTemplate = 
        QualityReport.getQualityCheckTemplate(tooFewIdentifier);
      QualityCheck tooFewCheck = 
        new QualityCheck(tooFewIdentifier, tooFewTemplate);

      if (QualityCheck.shouldRunQualityCheck(entity, tooFewCheck)) {
        boolean foundTooFew = (columnCounter < numCols);
        if (foundTooFew) {
          String expected = numCols + " " + fieldWord(numCols);
          tooFewCheck.setExpected(expected);
          String found = columnCounter + " " + fieldWord(columnCounter);
          tooFewCheck.setFound(found);
          String explanation = 
            "In row " + rowCounter + 
            ", fewer fields were found in the row than were expected: ";
          tooFewCheck.setFailedStatus();
          explanation += "<![CDATA[" + data.trim() + "]]>";
          tooFewCheck.setExplanation(explanation);
          tooFewFieldsCounter++;
          // Limit the number of these checks included in the quality report
          if (tooFewFieldsCounter <= FIELD_CHECK_MAX) {
            entity.addQualityCheck(tooFewCheck);
          }
        }
      }
    
      /*
       *  Quality check for too many fields
       */
      String tooManyIdentifier = "tooManyFields";
      QualityCheck tooManyTemplate = 
        QualityReport.getQualityCheckTemplate(tooManyIdentifier);
      QualityCheck tooManyCheck = 
        new QualityCheck(tooManyIdentifier, tooManyTemplate);

      if (QualityCheck.shouldRunQualityCheck(entity, tooManyCheck)) {
        boolean foundTooMany = (columnCounter > numCols);
        if (foundTooMany) {
          String expected = numCols + " " + fieldWord(numCols);
          tooManyCheck.setExpected(expected);
          String found = columnCounter + " " + fieldWord(columnCounter);
          tooManyCheck.setFound(found);
          String explanation = null;
          String truncatedData = data.trim();
          if (truncatedData.length() > 200) {
            truncatedData = truncatedData.substring(0, 200) + "... (truncated)";
          }
          explanation = 
            "In row " + rowCounter +
            ", more fields were found in the row than were expected: ";
          tooManyCheck.setFailedStatus();
          explanation += "<![CDATA[" + truncatedData + "]]>";
          tooManyCheck.setExplanation(explanation);
          tooManyFieldsCounter++;
          // Limit the number of these checks included in the quality report
          if (tooManyFieldsCounter <= FIELD_CHECK_MAX) {
            entity.addQualityCheck(tooManyCheck);
          }
        }
      }
    
      if (columnCounter > numCols) {
    		throw new DataNotMatchingMetadataException(
             "Metadata specifies that data has " + numCols +
    				 " columns, but the actual data has " + columnCounter + 
    				 " columns. Please check that the metadata is correct.");
      }
        
      for (int j = 0; j < stringArray.length; j++) {
        if (stringArray[j] != null) {
          rowVector.addElement(stringArray[j].trim());
        }
        else {
          rowVector.addElement("");
        }
      }
        
      /*
       * Pad missing fields with empty strings so that all the records 
       * have the same number of columns.
       */
      int rowVectorSize = rowVector.size();
      if (rowVectorSize < numCols) {
        for (int j = 0; j < (numCols - rowVectorSize); j++) {
          rowVector.addElement("");
        }
      }
    }
    
    return rowVector;
  }
  
  
  /*
   * Returns singular or plural version of the word "field" for use
   * in Quality Check output.
   */
  private String fieldWord(int numFields) {
    return ((numFields == 1) ? "field" : "fields");
  }
  
  
  /*
   * In oneRowData, there are quote character in it. Any field delimiter in the
   * quotes should be skipped.
   */
  private String[] processQuoteCharacterOneRowData(String oneRowData) throws Exception
  {
	  String[] elements = null;
	  Vector elementsVector = new Vector();
	  if (oneRowData == null)
	  {
		  return elements;
	  }
	  quoteCharacter = transformQuoteCharacter(quoteCharacter);
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
	  int delimiterLength = fieldDelimiter.length();
	  boolean startQuote = false;
	  boolean delimiterAtEnd = false;
	  //this string buffer is only for deleting if hit a delimiter
	  StringBuffer delimiterStorage = new StringBuffer(fieldDelimiter.length());
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
		  if (delimiterStorage.indexOf(fieldDelimiter) != -1 && !startQuote)
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
			  if (collapseDelimiters)
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
			  int delimiterIndex = fieldData.lastIndexOf(fieldDelimiter);
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
   * and append the new char at the end. So the buffer size will 
   * stay the same.
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
   * If quote character is specified by hex number, we should transform it
   * to a character. If quote string is longer than 1 character,
   * throw an exception.
   */
  private String transformQuoteCharacter(String quote) 
          throws Exception {
    String newQuote = quote;
    
    if (newQuote == null) {
      return newQuote;
    }
    else if (newQuote.startsWith("#") && 
             newQuote.length() > 1) {
      String digits = newQuote.substring(1, newQuote.length());
	    int radix = 10;

	    if (digits.startsWith("x")) {
        radix = 16;
        digits = digits.substring(1, digits.length());
      }

	    newQuote = transformDigitsToCharString(radix, digits);
    }
    else if ((newQuote.startsWith("0x") || newQuote.startsWith("0X")) && 
             newQuote.length() >2) {
      int radix = 16;
      String digits = newQuote.substring(2, newQuote.length());
      newQuote = transformDigitsToCharString(radix, digits);
	  }
   
    if (newQuote.length() > 1) {
      throw new Exception("Quote string length should be 1 character in EML");
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
   * Gets the value of the tooFewFieldsCounter field used in 
   * quality reporting.
   * 
   * @return tooFewFieldsCounter, an int representing the number
   * of 'tooFewFields' errors that were counted for a given
   * entity
   */
  public int getTooFewFieldsCounter() {
    return tooFewFieldsCounter;
  }


  /**
   * Gets the value of the tooManyFieldsCounter field used in 
   * quality reporting.
   * 
   * @return tooManyFieldsCounter, an int representing the
   * number of 'tooManyFields' errors that were counted for a
   * given entity.
   */
  public int getTooManyFieldsCounter() {
    return tooManyFieldsCounter;
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
  
  
  /**
   * Sets the entity value for this 
   * @param entity
   */
  public void setEntity(Entity entity) {
    this.entity = entity;
  }
  
}
