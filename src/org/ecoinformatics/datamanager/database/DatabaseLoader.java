package org.ecoinformatics.datamanager.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.download.DataSourceNotFoundException;
import org.ecoinformatics.datamanager.download.DataStorageInterface;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;

/**
 * This class implments the DataStorageInterface to load data into db.
 * In this class, PipedOutputStream and PipedInputStream are used in order
 * to remove temporary files.
 * @author Jing Tao
 *
 */
public class DatabaseLoader implements DataStorageInterface, Runnable 
{
	
	private PipedInputStream     inputStream = null;
	private PipedOutputStream   outputStream = null;
	private Entity                    entity = null;
	private Connection         dbConnection  = null;
	private String             dbAdapterName = null;
    private DatabaseAdapter  databaseAdapter = null;
    private String                 errorCode = null;
    private static TableMonitor tableMonitor = null;             
    
    /**
     * Constructor of this class. In constructor, it will create a pair of
     * PipedOutputStream object and PipedInputStream object.
     * @param dbConnection Connection to database
     * @param dbAdapterName Name of database adapter
     * @param entity Metad
     * ata information associated with the loader
     * @throws IOException
     */
    public DatabaseLoader(Connection dbConnection, String dbAdapterName, Entity entity) throws IOException, 
                                                                                               SQLException
    {
    	outputStream = new PipedOutputStream();
        inputStream  = new PipedInputStream();
        outputStream.connect(inputStream);
        this.dbConnection = dbConnection;
		this.dbAdapterName = dbAdapterName;
		this.entity        = entity;
    
	    //Class databaseAdapterClass = Class.forName(dbAdapterName);
	    //databaseAdapter = (DatabaseAdapter) databaseAdapterClass.newInstance();
	    
	    if (dbAdapterName.equals(DatabaseAdapter.POSTGRES_ADAPTER)) {
	      this.databaseAdapter = new PostgresAdapter();
	    }
	    else if (dbAdapterName.equals(DatabaseAdapter.HSQL_ADAPTER)) {
	      this.databaseAdapter = new HSQLAdapter();
	    }
	    else if (dbAdapterName.equals(DatabaseAdapter.ORACLE_ADAPTER)) {
	      this.databaseAdapter = new OracleAdapter();
	      
	    }
	    //tableMonitor =new TableMonitor(dbConnection, dbAdapterName);
	    
    }
	
	 
	/**
	   * Accesses the data for a given identifier, opening an input stream on it
	   * for loading. This method is required for implementing DataStorageInterface.
	   * 
	   * @param  identifier  An identifier of the data to be loaded.
	   * @throws DataSourceNotFoundException Indicates that the data was not found
	   *         in the local store.
	   */
	  public InputStream load(String identifier) throws DataSourceNotFoundException
	  {
	    InputStream inputStream = null;

	    return inputStream;
	  }
	  
	  /**
	   * Start to serialize a remote inputstream. The OutputStream is 
	   * the destination in the local store.
	   * The DownloadHandler reads data from the remote source and writes it to 
	   * the output stream for local storage. For the DatabaseHandler class,
	   * the database itself serves as the local store. We used Piped input stream
	   * and output stream to read and write data from remote source. So the process
	   * will be ran in another thread
	   * 
	   * @param  identifier  An identifier to the data in the local store that is
	   *                     to be serialized.
	   * @return An output stream to the location in the local store where the data 
	   *         is to be serialized.
	   */
		public OutputStream startSerialize(String identifier)
		{
	        
	        Thread newThread = new Thread(this);
	        newThread.start();	        	
			return outputStream;
		}
		
		 /** 
		   * Finishes serialization of the data to the local store. This method is
		   * required for implementing DataStorageInterface.
		   * 
		   * @param identifier  the identifier for the data whose serialization is done
		   * @param errorCode   a string indicating whether there was an error during
		   *                    the serialization
		   */
		  public void finishSerialize(String identifier, String errorCode) 
		  {
			  this.errorCode = errorCode;
			  if (inputStream != null)
			  {
				  try
				  {
				    inputStream.close();
				  }
				  catch (Exception e)
				  {
					  System.err.println("Could not close inputStream in DatabaseLoader.finishSerialize() because "
				              +e.getMessage());
				  }
			  }
			  if (outputStream != null)
			  {
				  try
				  {
				    outputStream.close();
				  }
				  catch (Exception e)
				  {
					  System.err.println("Could not close outputStream in DatabaseLoader.finishSerialize() because "
							              +e.getMessage());
				  }
			  }
			
		    
		  }
		  
		  
	
		/**
		 * Gets PipedInputStream Object
		 * @return PipedInputStream
		 */
		public PipedInputStream getPipedInputStream()
		{
			return this.inputStream;
		}
		
		/**
		 * Gets PipedOutputStream Object
		 * @return PipedOutputStream Object
		 */
		public PipedOutputStream getPipedOutputStream()
		{
			return this.outputStream;
		}
		
		/**
		 * Gets the errorCode that DownloadHanlder passed.
		 * @return eErroCode
		 */
	    public String getErrorCode()
	    {
	    	return this.errorCode;
	    }
		
	    /**
	     * Reads the data from PipedInputStream which connects the PipedOutputStream which
	     * was return to DownloadHandler. The read data will be loaded into db. This is real
	     * procedure to load data into db
	     */
		public void run()
		{
			Vector rowVector = new Vector();
			AttributeList attributeList = entity.getAttributeList();
			String tableName = entity.getDBTableName();
			TextDataReader  dataReader = null;
			boolean stripHeaderLine = true;
			if (inputStream != null)
			{
				try
				{
					if (entity.isSimpleDelimited())
					{
						
						DelimitedReader delimitedReader = new DelimitedReader(inputStream, entity
								.getAttributes().length, entity.getDelimiter(),
								entity.getNumHeaderLines(), entity.getRecordDelimiter(),
								entity.getNumRecords(), stripHeaderLine);
						delimitedReader.setCollapseDelimiter(entity.getCollapseDelimiter());
						delimitedReader.setNumFooterLines(entity.getNumFooterLines());
						dataReader = delimitedReader;
						
					}
					else
					{
						dataReader = new TextComplexFormatDataReader(inputStream, entity, stripHeaderLine);
						
					}
					rowVector = dataReader.getOneRowDataVector();
					//dbConnection.setAutoCommit(false);
				}
				catch(Exception e)
				{
					return;
				}
				
				try
				{
					while (!rowVector.isEmpty())
					{
					   String insertSQL = databaseAdapter.generateInsertSQL(attributeList, tableName, rowVector);
					   System.out.println("the sql is "+insertSQL);
					   Statement statement = dbConnection.createStatement();
					   statement.execute(insertSQL);
					   rowVector = dataReader.getOneRowDataVector();
					}
					dbConnection.commit();
				}
				catch(Exception e)
				{
					try
					{
					   dbConnection.rollback();
					}
					catch(Exception ee)
					{
						System.err.println(ee.getMessage());
					}
				}
				finally
				{
					try
					{
					   dbConnection.setAutoCommit(true);
					}
					catch(Exception ee)
					{
					  System.err.println(ee.getMessage());
					}
				}
				
			}
			else
			{
				System.err.println(" input stream is null");
			}
		}
		
		 /**
		   * Tests the DatabaseHandler.doesDataExist() method. Does so by creating a 
		   * test table. First drops the table in case it was already
		   * present. Then creates the table, calls isTableInDB(), and asserts that
		   * the table exists. Then drops the table again, calls isTableInDB(), and
		   * asserts that the table does not exist.
		   * 
		   * @throws SQLException
		   */
		  /*public void testDoesDataExist()
		          throws MalformedURLException, IOException, SQLException, Exception {
		    DataManager dataManager = DataManager.getInstance();
		    DataPackage dataPackage = null;
		    InputStream metadataInputStream;
		    String documentURL = TEST_SERVER + "?action=read&qformat=xml&docid="
		        + TEST_DOCUMENT;
		    URL url;

		   
		    try {
		      url = new URL(documentURL);
		      metadataInputStream = url.openStream();
		      dataPackage = dataManager.parseMetadata(metadataInputStream);
		    } 
		    catch (MalformedURLException e) {
		      e.printStackTrace();
		      throw (e);
		    } 
		    catch (IOException e) {
		      e.printStackTrace();
		      throw (e);
		    } 
		    catch (Exception e) {
		      e.printStackTrace();
		      
		      throw (e);
		    } 
		    assertNotNull("Data package is null", dataPackage);

		  
		    if (dataPackage != null) {
		      Entity[] entities = dataPackage.getEntityList();
		      Entity entity = entities[0];
		      boolean success = databaseHandler.generateTable(entity);
		      assertTrue("DatabaseHandler did not succeed in generating table", success);
		      String identifier = entity.getEntityIdentifier();
		      boolean isPresent = databaseHandler.doesDataExist(identifier);
		      assertTrue("Could not find table for identifier " + identifier
		          + " but it should be in db", isPresent);
		      databaseHandler.dropTable(entity);
		      isPresent = databaseHandler.doesDataExist(identifier);
		      assertFalse("Found table for identifier " + identifier +
		                  " but it should NOT be in db", isPresent);
		      }
		  }*/
		  /**
		   * Determines whether the data table corresponding to a given identifier 
		   * already exists in the database. This method is mandated by the
		   * DataStorageInterface.
		   * private static Hashtable     pipeIOHash = new Hashtable();
			private static Hashtable     entityHash = new Hashtable();
		   * @param   identifier  the identifier for the data table
		   * @return  true if the data table already exists in the database, else false
		   */
		  public boolean doesDataExist(String identifier) {
		    boolean doesExist = false;
		    
		    try {
		      String tableName = identifierToTableName(identifier);
		      doesExist = tableMonitor.isTableInDB(tableName);
		    }
		    catch (SQLException e) {
		      System.err.println(e.getMessage());
		      e.printStackTrace();
		    }
		    
		    return doesExist;
		  }
		  
		  /**
		   * Given an identifier string, return its corresponding table name. 
		   * 
		   * @param   identifier   the identifier string for the entity
		   * @return  the corresponding table name
		   */
		  private  String identifierToTableName(String identifier) 
		          throws SQLException {
		    String tableName = "";
		    
		    tableName = tableMonitor.identifierToTableName(identifier);
		    
		    return tableName;
		  }
		  


}
