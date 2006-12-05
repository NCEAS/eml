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
    String insertSQL = "";
    // System.out.println("====================== start load data into db");
    Vector rowVector = new Vector();
    
    if (entity == null) {
      success = false;
      completed = true;
      return;
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
          delimitedReader.setCollapseDelimiter(entity.getCollapseDelimiter());
          delimitedReader.setNumFooterLines(entity.getNumFooterLines());
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
        System.err.println("Error message in DatabaseLoader.run() is "
                           + e.getMessage());
        success = false;
        completed = true;
        exception = e;
        return;
      }

      Connection connection = DataManager.getConnection();

      try {
        // System.out.println("The first row data is "+rowVector);
    	connection.setAutoCommit(false);
        while (!rowVector.isEmpty()) {
          insertSQL = databaseAdapter.generateInsertSQL(attributeList,
                                                        tableName, 
                                                        rowVector);
          if (insertSQL != null)
          {
        	  PreparedStatement statement = connection.prepareStatement(insertSQL);
        	  statement.execute();
          }
            
          rowVector = dataReader.getOneRowDataVector();
          // System.out.println("The row data in while loop is "+rowVector);
        }
        
        connection.commit();
        success = true;
      } 
      catch (Exception e) {
        System.err.println("DatabaseLoader.run(): Error message: " + 
                           e.getMessage());
        System.err.println("Stack trace:");
        e.printStackTrace();
        System.err.println("SQL string to insert row:\n" + insertSQL);
        
        success = false;
        exception = e;
        
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
      System.err.println(" input stream is null");
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
