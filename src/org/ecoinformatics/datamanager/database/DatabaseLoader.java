package org.ecoinformatics.datamanager.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import org.ecoinformatics.datamanager.download.DataSourceNotFoundException;
import org.ecoinformatics.datamanager.download.DataStorageInterface;
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
	private static Hashtable     pipeIOHash = new Hashtable();
	private static Hashtable     entityHash = new Hashtable();
	private PipedInputStream    inputStream = null;
	private PipedOutputStream  outputStream = null;
	private Entity                   entity = null;
	private Connection        dbConnection  = null;
	private String            dbAdapterName = null;
    private DatabaseAdapter databaseAdapter = null;
    private String                errorCode = null;
    
    /**
     * Constructor of this class. In constructor, it will create a pair of
     * PipedOutputStream object and PipedInputStream object.
     * @param dbConnection Connection to database
     * @param dbAdapterName Name of database adapter
     * @param entity Metadata information associated with the loader
     * @throws IOException
     */
    public DatabaseLoader(Connection dbConnection, String dbAdapterName, Entity entity) throws IOException
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
			if (inputStream != null)
			{
				System.out.println(" inputStream is NOT null");
				byte[] array = new byte[1024];
				File outputFile = new File("/Users/jinsongzhang/dsafa21");
				FileOutputStream fileOutputStream = null;
				int size = 0;
				try
				{
				   fileOutputStream = new FileOutputStream(outputFile);
				   size =inputStream.read(array);
				   while (size != -1)
				   {
					   fileOutputStream.write(array, 0, size);
					   size =inputStream.read(array);
				   }
				}
				catch (Exception e)
				{
					System.err.println("The error in DatabaseLoader.run() "+e.getMessage());
				}
				
				
			}
			else
			{
				System.out.println(" input stream is null");
			}
		}
		
		
		/**
		   * Determines whether the data table corresponding to a given identifier 
		   * already exists in the database. This method is mandated by the
		   * DataStorageInterface.
		   * 
		   * @param   identifier  the identifier for the data table
		   * @return  true if the data table already exists in the database, else false
		   */
		  public boolean doesDataExist(String identifier) 
		  {
		    boolean doesExist = DatabaseHandler.doesDataExist(identifier);
		    
		    return doesExist;
		  }
		  


}
