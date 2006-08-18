/**
 *    '$RCSfile: DataManager.java,v $'
 *
 *     '$Author: costa $'
 *       '$Date: 2006-08-18 20:26:48 $'
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

package org.ecoinformatics.datamanager;


import java.io.InputStream;
import java.sql.ResultSet;

import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;

/**
 * 
 * The DataManager class is the controlling class for the library. It exposes
 * the high-level API to the calling application.
 * 
 * There are six use-cases that this library supports:
 * 
 * 1. Parse the metadata to get at its entities and attributes.
 * 2. Download a data table from a remote site, using the URL in the metadata.
 * 3. Load a data table into the database table cache.
 * 4. Query a data table with a SQL select statement.
 * 5. Set an upper limit on the size of the database table cache.
 * 6. Set an expiration policy on a table in the database table cache.
 *
 */
public class DataManager {

  /*
   * Class fields
   */

  /* Holds the singleton object for this class */
  private static DataManager dataManager = new DataManager();
  
  
  /*
   * Instance fields
   */
  
  
  /*
   * Constructors
   */
  
  
  /*
   * Class methods
   */
  
  /**
   * Gets the singleton instance of this class.
   * 
   * @return  the single instance of the DataManager class.
   */
  static public DataManager getInstance() {
    return dataManager;
  }
  
  
  /*
   * Instance methods
   */
 
  /**
   * Downloads all entities in a data package using the calling application's 
   * data storage interface. This allows the calling application to manage its 
   * data store in its own way. This version of the method downloads all 
   * entities in the entity list of the data package. This method implements
   * Use Case #2.
   * 
   * @param  dataPackage the data package containing a list of entities
   * @return a boolean value indicating the success of the download operation.
   *         true if successful, else false.
   */
  public boolean downloadData(DataPackage dataPackage) {
    boolean success = true;
    
    return success;
  }

  
  /**
   * Downloads a single entity using the calling application's data storage
   * interface. This allows the calling application to manage its data store
   * in its own way. This method implements Use Case #2.
   * 
   * @param  the entity whose data is to be downloaded
   * @return a boolean value indicating the success of the download operation.
   *         true if successful, else false.
   */
  public boolean downloadData(Entity entity) {
    boolean success = true;
    
    return success;
  }
 
  
  /**
   * Downloads data from an input stream using the calling application's data 
   * storage interface. This allows the calling application to manage its 
   * data store in its own way. The metadata input stream first needs to be
   * parsed and a data package created from it. Then, all entities in the data
   * package are downloaded. This method implements Use Case #2.
   * 
   * @param  metadataInputStream an input stream to the metadata. 
   * @return a boolean value indicating the success of the download operation.
   *         true if successful, else false.
   */
  public boolean downloadData(InputStream metadataInputStream) {
    boolean success = true;
    
    return success;
  }
  
  
  /**
   * Loads all entities in a data package to the database table cache. This
   * method implements Use Case #3.
   * 
   * @param  dataPackage the data package containing a list of entities whose
   *         data is to be loaded into the database table cache.
   * @return a boolean value indicating the success of the load-data operation.
   *         true if successful, else false.
   */
  public boolean loadDataToDB(DataPackage dataPackage) {
    boolean success = true;
    
    return success;
  }
  
  
  /**
   * Loads data from a single entity into the database table cache.
   * This method implements Use Case #3.
   * 
   * @param  entity  the entity whose data is to be loaded into the database 
   *                 table cache.
   * @return a boolean value indicating the success of the load-data operation.
   *         true if successful, else false.
   */
  public boolean loadDataToDB(Entity entity) {
    boolean success = true;
    
    return success;
  }
  
  
  /**
   * Loads all entities in a data package to the database table cache. This
   * version of the method is passed a metadata input stream that needs
   * to be parsed. Then, all entities in the data package are loaded to the
   * database table cache. This method implements Use Case #3.
   * 
   * @param  metadataInputStream the metadata input stream to be parsed into
   *         a DataPackage object.
   * @return a boolean value indicating the success of the load-data operation.
   *         true if successful, else false.
   */
  public boolean loadDataToDB(InputStream metadataInputStream) {
    boolean success = true;
    
    return success;
  }
  
  
  /**
   * Parses metadata using the appropriate parser object. The return value is
   * a DataPackage object containing the parsed metadata. This method
   * implements Use Case #1.
   * 
   * @param metadataInputStream  an input stream to the metadata to be parsed.
   * @return a DataPackage object containing the parsed metadata
   */
  public DataPackage parseMetadata(InputStream metadataInputStream) {
    DataPackage dataPackage = new DataPackage();
    
    return dataPackage;
  }
  
  
  /**
   * Runs a database query on one or more data packages. This method
   * implements Use Case #4.
   * 
   * @param ANSISQL  A string holding the ANSI SQL selection syntax.
   * @param packages The data packages holding the entities to be queried. 
   *                 Metadata about the data types of the attributes being
   *                 queried is contained in these data packages.
   * @return A ResultSet object holding the query results.
   */
  public ResultSet selectData(String ANSISQL, DataPackage[] packages) {
    ResultSet resultSet = null;
    
    return resultSet;
  }
  
 
  /**
  * Runs a database query on one or more metadata input streams. Each of the
  * metadata input streams needs to first be parsed, creating a list of data 
  * packages. The data packages contain entities, and the entities hold metadata
  * about the data types of the attributes being queried. This method 
  * implements Use Case #4.
  * 
  * @param ANSISQL  A string holding the ANSI SQL selection syntax.
  * @param packages An array of input streams that need to be parsed into a 
  *                 list of data packages. The data packages hold the lists of
  *                 entities to be queried. Metadata about the data types of the
  *                 attributes in the select statement is contained in these 
  *                 data packages.
  * @return A ResultSet object holding the query results.
  */
  public ResultSet selectData(String ANSISQL, InputStream[] emlInputStreams) {
    ResultSet resultSet = null;
    
    return resultSet;
  }
  
  
  /**
   * Sets an upper limit on the size of the database table cache. If the limit
   * is about to be exceeded, the TableMonitor will attempt to free up space
   * by deleting old tables from the table cache. This method implements
   * Use Case #5.
   * 
   * @param size The upper limit, in MB, on the size of the database table
   *        cache.
   */
  public void setDatabaseSize(int size) {
    
  }
  
  
  /**
   * Sets the expiration policy on a table in the database table cache. The
   * policy is an enumerated integer value indicating whether this table can
   * be expired from the cache. (The precise meaning of these values is yet to
   * be determined.) This method implements Use Case #6.
   * 
   * @param tableName the name of the table whose expiration policy is being
   *                  set
   * @param policy    an enumerated integer value indicating whether the table
   *                  should be expired from the datbase table cache. (The
   *                  precise meaning of this value is yet to be determined.)
   */
  public void setTableExpirationPolicy(String tableName, int policy) {
    
  }

}
