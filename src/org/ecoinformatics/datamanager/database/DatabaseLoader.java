package org.ecoinformatics.datamanager.database;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.download.DataSourceNotFoundException;
import org.ecoinformatics.datamanager.download.DataStorageInterface;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.quality.QualityCheck;
import org.ecoinformatics.datamanager.quality.QualityReport;
import org.ecoinformatics.datamanager.quality.QualityCheck.Status;

/**
 * This class implments the DataStorageInterface to load data into db.
 * In this class, PipedOutputStream and PipedInputStream are used in order
 * to eliminate the need to use temporary files on disk.
 * 
 * @author Jing Tao
 *
 */
public class DatabaseLoader implements DataStorageInterface, Runnable 
{
  
  /*
   * Class fields
   */
	
  private static TableMonitor tableMonitor = null;  
  
  
  /*
   * Instance fields
   */
  private PipedInputStream inputStream = null;
  private PipedOutputStream outputStream = null;
  private Entity entity = null;
  private DatabaseAdapter databaseAdapter = null;
  private String errorCode = null;
  private boolean completed = false;
  private boolean success = false;
  private Exception exception = null;
  
  
  /*
   * Constructors
   */

  /**
   * Constructor of this class. In constructor, it will create a pair of
   * PipedOutputStream object and PipedInputStream object.
   * 
   * @param dbConnection    Connection to database
   * @param dbAdapterName   Name of database adapter
   * @param entity          Metadata information associated with the loader
   * @throws IOException
   */
  public DatabaseLoader(String dbAdapterName, Entity entity)
          throws IOException, SQLException
  {
    outputStream = new PipedOutputStream();
    inputStream = new PipedInputStream();
    outputStream.connect(inputStream);
    
    this.entity = entity;

    /* Initialize the databaseAdapter and tableMonitor fields */
    if (dbAdapterName.equals(DatabaseAdapter.POSTGRES_ADAPTER)) {
      this.databaseAdapter = new PostgresAdapter();
    } 
    else if (dbAdapterName.equals(DatabaseAdapter.HSQL_ADAPTER)) {
      this.databaseAdapter = new HSQLAdapter();
    } 
    else if (dbAdapterName.equals(DatabaseAdapter.ORACLE_ADAPTER)) {
      this.databaseAdapter = new OracleAdapter();
    }
    
    tableMonitor = new TableMonitor(databaseAdapter);
  }
	
	 
  /**
   * Accesses the data for a given identifier, opening an input stream on it for
   * loading. This method is required for implementing DataStorageInterface.
   * 
   * @param identifier      An identifier of the data to be loaded.
   * @return  an input stream
   * @throws DataSourceNotFoundException
   *         Indicates that the data was not found in the local store.
   */
  public InputStream load(String identifier) throws DataSourceNotFoundException 
  {
    InputStream inputStream = null;

    return inputStream;
  }
      
	  
  /**
   * Start to serialize a remote inputstream. The OutputStream is the
   * destination in the local store. The DownloadHandler reads data from the
   * remote source and writes it to the output stream for local storage. For the
   * DatabaseHandler class, the database itself serves as the local store. We
   * used Piped input stream and output stream to read and write data from
   * remote source. So the process will be run in another thread.
   * 
   * @param identifier  An identifier to the data in the local store that is 
   *                    to be serialized.
   * @return An output stream to the location in the local store where the data
   *         is to be serialized.
   */
  public OutputStream startSerialize(String identifier) {
    completed = false;
    success = false;
    Thread newThread = new Thread(this);
    newThread.start();
    
    return outputStream;
  }
		
        
  /**
   * Finishes serialization of the data to the local store. Close the input
   * stream and output stream.
   * 
   * This method is required for implementing DataStorageInterface.
   * 
   * @param identifier  the identifier for the data whose serialization is done
   * @param errorCode   a string indicating whether there was an error during 
   *                    the serialization
   */
  public void finishSerialize(String identifier, String errorCode) {
    this.errorCode = errorCode;
    
    if (inputStream != null) {
      try {
      //TODO: broken pipe error with this
        inputStream.close();
      } 
      catch (Exception e) {
        System.err.println(
             "Could not close inputStream in DatabaseLoader.finishSerialize(): "
             + e.getMessage());
      }
    }
    
    if (outputStream != null) {
      try {
        outputStream.close();
      } 
      catch (Exception e) {
        System.err.println(
            "Could not close outputStream in DatabaseLoader.finishSerialize(): "
            + e.getMessage());
      }
    }

  }
		  

  /**
   * Gets PipedInputStream object.
   * 
   * @return PipedInputStream
   */
  public PipedInputStream getPipedInputStream() {
    return this.inputStream;
  }
	
  
  /**
   * Gets PipedOutputStream object.
   * 
   * @return PipedOutputStream object
   */
  public PipedOutputStream getPipedOutputStream() {
    return this.outputStream;
  }
		
        
  /**
   * Gets the errorCode that DownloadHandler passed.
   * 
   * @return  the errorCode string
   */
  public String getErrorCode() {
    return this.errorCode;
  }
		
  
  /**
   * Reads the data from PipedInputStream which connects the PipedOutputStream
   * which was returned to DownloadHandler. The read data will be loaded into 
   * db. This is the real procedure to load data into db.
   */
  public void run() {
    QualityCheck dataLoadQualityCheck = null;
    String insertSQL = "";
    // System.out.println("====================== start load data into db");
    Vector rowVector = new Vector();
    int rowCount = 0;
    
    if (entity == null) {
      success = false;
      completed = true;
      return;
    }
    else {
      if (QualityReport.isQualityReporting()) {
        // Initialize the dataLoadQualityCheck
        dataLoadQualityCheck = new QualityCheck("Data load status");
        dataLoadQualityCheck.setSystem("knb");
        dataLoadQualityCheck.setQualityType(QualityCheck.QualityType.congruency);
        dataLoadQualityCheck.setStatusType(QualityCheck.StatusType.error);
        dataLoadQualityCheck.setDescription(
          "Status of loading the data table into a database");
        dataLoadQualityCheck.setExpected(
          "No errors expected during data loading " +
          "or data loading was not attempted for this data entity");
      }
    }
    
    AttributeList attributeList = entity.getAttributeList();
    String tableName = entity.getDBTableName();
    TextDataReader dataReader = null;
    boolean stripHeaderLine = true;
    
    if (inputStream != null) {
      try {
        if (entity.isSimpleDelimited()) {
          DelimitedReader delimitedReader = 
            new DelimitedReader(inputStream,
                                entity.getAttributes().length, 
                                entity.getDelimiter(), 
                                entity.getNumHeaderLines(),
                                entity.getRecordDelimiter(),
                                entity.getNumRecords(),
                                stripHeaderLine
                               );
          delimitedReader.setCollapseDelimiters(entity.getCollapseDelimiters());
          delimitedReader.setNumFooterLines(entity.getNumFooterLines());
          if (entity.getQuoteCharacter() != null)
          {
            delimitedReader.setQuoteCharacter(entity.getQuoteCharacter());
          }
          if (entity.getLiteralCharacter() != null)
          {
            delimitedReader.setLiteralCharacter(entity.getLiteralCharacter());
          }
          dataReader = delimitedReader;
        } 
        else {
          dataReader = new TextComplexFormatDataReader(inputStream, 
                                                       entity,
                                                       stripHeaderLine
                                                      );
        }
        
        rowVector = dataReader.getOneRowDataVector();
      }
      catch (Exception e) {
        System.err.println("Exception in DatabaseLoader.run(): " + 
                           e.getMessage());
        
        if (entity != null && QualityReport.isQualityReporting()) {
          /*
           * Report data load status as 'error'
           */
          dataLoadQualityCheck.setStatus(Status.error);
          dataLoadQualityCheck.setFound(
            "One or more errors occurred during data loading");
          String explanation = e.getMessage();
          dataLoadQualityCheck.setExplanation(explanation);
          entity.addQualityCheck(dataLoadQualityCheck);
        }
        
        success = false;
        completed = true;
        exception = e;
        return;
      }

      Connection connection = null;

      try {
        if (entity != null && QualityReport.isQualityReporting()) {
          /*
           *  Display the first row of data in a QualityCheck object
           */
          QualityCheck displayDataQualityCheck = 
            new QualityCheck("Display some data");
          displayDataQualityCheck.setSystem("knb");
          displayDataQualityCheck.setQualityType(QualityCheck.QualityType.data);
          displayDataQualityCheck.setDescription("Display the first row of data");
          displayDataQualityCheck.setExpected("One row of data should be displayed");
          displayDataQualityCheck.setStatus(Status.info);
          // Note that rowVector starts and ends with square brackets. We're
          // using a shortcut by incorporating them into the CDATA tags
          String foundString = "<![CDATA" + rowVector.toString() + "]>";
          displayDataQualityCheck.setFound(foundString);
          entity.addQualityCheck(displayDataQualityCheck);
        }
        
    	  connection = DataManager.getConnection();
    	  if (connection == null)
    	  {
    		  success = false;
    		  exception = new Exception("The connection to db is null");
    		  completed = true;
    		  return;
    	  }
    	  connection.setAutoCommit(false);
        while (!rowVector.isEmpty()) {
          insertSQL = databaseAdapter.generateInsertSQL(attributeList,
                                                        tableName, 
                                                        rowVector);
          if (insertSQL != null)
          {
      	    PreparedStatement statement = connection.prepareStatement(insertSQL);
      	    statement.execute();
            rowCount++;
          }
            
          rowVector = dataReader.getOneRowDataVector();
        }
        connection.commit();

        if (entity != null && QualityReport.isQualityReporting()) {
          /*
           *  Report data load status as 'valid'
           */
          dataLoadQualityCheck.setStatus(Status.valid);
          dataLoadQualityCheck.setFound(
            "The data table loaded successfully into a database");
          entity.addQualityCheck(dataLoadQualityCheck);      

          /*
           * Store number of records found in a QualityCheck object
           */
          QualityCheck numberOfRecordsQualityCheck = 
            new QualityCheck("Number of records check");
          numberOfRecordsQualityCheck.setSystem("knb");
          numberOfRecordsQualityCheck.setQualityType(QualityCheck.QualityType.congruency);
          numberOfRecordsQualityCheck.setDescription(
              "Compare number of records specified in " +
              "metadata to number of records found in data");
          int expectedNumberOfRecords = entity.getNumRecords();
          numberOfRecordsQualityCheck.setExpected("" + expectedNumberOfRecords);
          numberOfRecordsQualityCheck.setFound("" + rowCount);        
          if (expectedNumberOfRecords == rowCount) {
            numberOfRecordsQualityCheck.setStatus(Status.valid);
            numberOfRecordsQualityCheck.setExplanation(
                "The expected number of records (" + 
                rowCount + ") was found in the data table.");
          }
          // When 'numberOfRecords' is not specified in the EML, the EML
          // parser sets the value to -1.
          else if (expectedNumberOfRecords < 0) {
            numberOfRecordsQualityCheck.setStatus(Status.info);
            numberOfRecordsQualityCheck.setExplanation(
              "The number of records found in the data table was: " + 
              rowCount +
              ". There was no 'numberOfRecords' value specified in the EML.");
          }
          else {
            numberOfRecordsQualityCheck.setStatus(Status.warn);
            numberOfRecordsQualityCheck.setExplanation(
              "The number of records found in the data table does " +
              "not match the 'numberOfRecords' value specified in the EML.");
          }
          entity.addQualityCheck(numberOfRecordsQualityCheck);      
        }

        success = true;

      }
      catch (Exception e) {
        System.err.println("DatabaseLoader.run(): Error message: " + 
                           e.getMessage());
        System.err.println("SQL insert string:\n" + insertSQL);        
        success = false;
        exception = e;
        
        if (entity != null && QualityReport.isQualityReporting()) {
          /*
           * Report data load status as 'error'
           */
          dataLoadQualityCheck.setStatus(Status.error);
          dataLoadQualityCheck.setFound(
            "One or more errors occurred during data loading");
          String explanation = e.getMessage();
          if (insertSQL != null && !insertSQL.equals("")) {
            explanation += "\nSQL insert string:\n" + insertSQL;
          }
          dataLoadQualityCheck.setExplanation(explanation);
          entity.addQualityCheck(dataLoadQualityCheck);
        }
        
        try {
          connection.rollback();
        } 
        catch (Exception ee) {
          System.err.println(ee.getMessage());
        }
      } 
      finally {
        try {
          connection.setAutoCommit(true);
        } 
        catch (Exception ee) {
          System.err.println(ee.getMessage());
        }

        DataManager.returnConnection(connection);
      }
    }
    else {
      System.err.println("Input stream is null.");
      success = false;
    }
    
    completed = true;
  }
		
		
  /**
   * Determines whether the data table corresponding to a given identifier
   * already exists in the database and is loaded with data. This method is
   * mandated by the DataStorageInterface.
   * 
   * First, check that the data table exists. Second, check that the row count
   * is greater than zero. (We want to make sure that the table has not only
   * been created, but has also been loaded with data.)
   * 
   * @param  identifier  the identifier for the data table
   * @return true if the data table has been loaded into the database, else
   *         false
   */
  public boolean doesDataExist(String identifier) {
    boolean doesExist = false;

    try {
      String tableName = tableMonitor.identifierToTableName(identifier);
      doesExist = tableMonitor.isTableInDB(tableName);

      if (doesExist) {
        int rowCount = tableMonitor.countRows(tableName);
        doesExist = (rowCount > 0);
      }
    } 
    catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }

    return doesExist;
  }
    
  
  /**
   * Gets the completion status of the serialize process.
   * 
   * @param identifier   Identifier of the entity which is being serialized
   * @return             true if complete, false if not complete
   */
  public boolean isCompleted(String identifier) {
    return completed || doesDataExist(identifier);
  }
	
  
  /**
   * Gets the success status of the serialize process - success or failure
   * 
   * @param identifier    Identifier of the entity which has been serialized
   * @return              true if success, else false
   */
  public boolean isSuccess(String identifier) {
    return success ||doesDataExist(identifier);
  }
  
    /**
	 * Gets the Exception happend in serialization
	 * @return Exception happend in serialization
	 */
	public Exception getException()
	{
		return exception;
	}
      
}
