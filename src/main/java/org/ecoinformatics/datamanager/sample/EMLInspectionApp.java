package org.ecoinformatics.datamanager.sample;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.database.ConnectionNotAvailableException;
import org.ecoinformatics.datamanager.database.DatabaseConnectionPoolInterface;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.Entity;

/**
 * This class is a sample calling application to demonstrate use of the
 * Data Manager Library API. This class parses an EML document and inspects
 * it to print out the tables, their names, and attributes. The 
 * DatabaseConnectionPoolInterface is implemented but only as null methods to
 * satisfy the DataManager.getInstance() requirements, but because these are 
 * noop methods the database connection cannot be tested in this class.
 * 
 * @author jones modified from dcosta
 *
 */
public class EMLInspectionApp implements DatabaseConnectionPoolInterface {

    /*
     * Class fields
     */

    /*
     * Configuration file name for the properties file. You can
     * edit the properties in this file to change the database connection
     * information as well as the sample metadata document used by this 
     * application.
     */

    private static final String CONFIG_NAME = "datamanager";

    /*
     * These fields will be assigned values when the properties file is loaded. 
     */
    private static ResourceBundle options = null;

    // This string holds the URL to the sample metadata document such as found on
    // a Metacat server. It is determined by the values in the properties file.
    private static String documentURL = null;

    /**
     * Constructor. Load database parameters and sample document name from 
     * properties file, get an instance of the DataManager class, and construct 
     * a DataStorageInterface object and an EcogridEndPointInterface object.
     */
    public EMLInspectionApp() {
        loadOptions();
    }

    /**
     * Main method. Creates an instance of the calling application object,
     * initializes it, and runs a number of calls to the DataManager that
     * test the various use cases. Each use case builds on the previous
     * use cases.
     */
    public static void main(String[] args) throws MalformedURLException,
            IOException, Exception {
        boolean success = true;
        EMLInspectionApp dmm = new EMLInspectionApp();
        success = success && dmm.testParseMetadata(); // Use Case #1
        System.err.println("Finished all tests, success = " + success + "\n");
        System.exit(0);
    }

    /**
     * Loads Data Manager options from a configuration file.
     */
    private static void loadOptions() {

        try {
            options = ResourceBundle.getBundle(CONFIG_NAME);

            // Load sample document URL options
            documentURL = options.getString("documentURL");
        } catch (Exception e) {
            System.err.println("Error in loading options: " + e.getMessage());
        }
    }

    /**
     * Parse a metadata document and print out some Entity and Attribut
     * information from the metadata.
     * 
     * @return  success, true if successful, else false
     */
    private boolean testParseMetadata() {
        boolean success = false;

        DataManager dataManager = DataManager.getInstance(this, null);
        InputStream metadataInputStream = null;
        try {
            URL url = new URL(documentURL);
            metadataInputStream = url.openStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (metadataInputStream != null) {
            try {
                // Parse the metadata that is being read from the input stream
                DataPackage dataPackage = dataManager
                        .parseMetadata(metadataInputStream);
                // If a DataPackage was returned, we succeeded in parsing
                success = (dataPackage != null);
                Entity entities[] = dataPackage.getEntityList();
                for (int i = 0; i < entities.length; i++) {
                    Entity e = entities[i];
                    display("EntityName", e.getName());
                    Attribute attributes[] = e.getAttributes();
                    for (int j = 0; j < attributes.length; j++) {
                        Attribute a = attributes[j];
                        display("    AttributeName", a.getName());
                        display("    AttributeUnit", a.getUnit());
                    }
                    System.out.println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.err.println("Finished testParseMetadata(), success = " + success
                + "\n");
        return success;
    }

    /**
     * Display output on standard out.
     * @param label the field to be displayed
     * @param value the value fo the field to be displayed
     */
    private void display(String label, String value) {
        System.out.println(label + ": " + value);
        System.out.flush();
    }

    /**
     * Get database adpater name. Implementation of this method is required by
     * the DatabaseConnectionPoolInterface.
     * 
     * @return database adapter name, for example, "PostgresAdapter"
     */
    public String getDBAdapterName() {
        return "";
    }

    /**
     * Gets a database connection from the pool. Implementation of this method is 
     * required by the DatabaseConnectionPoolInterface. Note that in this
     * example, there is no actual pool of connections. A full-fledged
     * application should manage a pool of connections that can be re-used.
     * 
     * @return checked out connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException,
            ConnectionNotAvailableException {
        Connection connection = null;

        return connection;
    }

    /**
     * Returns checked out dabase connection to the pool.
     * Implementation of this method is required by the 
     * DatabaseConnectionPoolInterface.
     * Note that in this example, there is no actual pool of connections
     * to return the connection to. A full-fledged
     * application should manage a pool of connections that can be re-used.
     * 
     * @param  conn, Connection that is being returned
     * @return boolean indicator if the connection was returned successfully
     */
    public boolean returnConnection(Connection conn) {
        boolean success = false;

        return success;
    }

}
