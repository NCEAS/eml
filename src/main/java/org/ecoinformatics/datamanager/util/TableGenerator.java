package org.ecoinformatics.datamanager.util;

import java.io.InputStream;
import java.net.URL;

import org.ecoinformatics.datamanager.DataManager;
import org.ecoinformatics.datamanager.database.DatabaseConnectionPoolInterface;
import org.ecoinformatics.datamanager.database.pooling.DatabaseConnectionPoolFactory;
import org.ecoinformatics.datamanager.parser.DataPackage;

public class TableGenerator {

	private DataManager dataManager;

	private static final String TEST_DOCUMENT = "cmccreedy.3.39";
	private static final String TEST_SERVER = "http://knb.ecoinformatics.org/knb/metacat";
	
	public static void main(String[] args) {
		String docid = null;
		String server = null;
		String sessionid = null;
		if (args.length >= 1) {
			docid = args[0];
		}
		if (args.length >= 2) {
			server = args[1];
		}
		if (args.length >= 3) {
			sessionid = args[2];
		}
		TableGenerator tg = new TableGenerator();
		try {
			tg.setUp();
			tg.parseMetadata(docid, server, sessionid);
			tg.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Establish a testing framework by initializing appropriate objects.
	 */
	public void setUp() throws Exception {
		DatabaseConnectionPoolInterface connectionPool = DatabaseConnectionPoolFactory
				.getDatabaseConnectionPoolInterface();
		String dbAdapterName = connectionPool.getDBAdapterName();
		dataManager = DataManager.getInstance(connectionPool, dbAdapterName);
	}

	/**
	 * Release any objects after tests are complete.
	 */
	public void tearDown() throws Exception {
		dataManager = null;
	}

	/**
	 * Unit test for the DataManager.parseMetadata() method (Use Case #1).
	 * 
	 * @throws Exception
	 */
	public void parseMetadata(String docid, String server, String sessionid) throws Exception {
		DataPackage dataPackage = null;
		InputStream metadataInputStream;
		String documentURL = "";
		if (server == null) {
			server = TEST_SERVER;
		}
		if (docid == null) {
			docid = TEST_DOCUMENT;
		}
		documentURL = server + "?action=read&qformat=xml&docid="+ docid;
		if (sessionid != null) {
			documentURL += "&sessionid=" + sessionid;
		}
		URL url = new URL(documentURL);
		metadataInputStream = url.openStream();
		dataPackage = dataManager.parseMetadata(metadataInputStream);
		dataManager.createTables(dataPackage);
	}

}
