package org.ecoinformatics.datamanager;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterfaceTest;
import org.ecoinformatics.ecogrid.EcogridObjType;
import org.ecoinformatics.ecogrid.client.PutServiceClient;

public class DataPackagePopulator extends TestCase {

	public static Log log = LogFactory.getLog(DataPackagePopulator.class);

	//data file generation constants
	private static final String EOLCharacter = "\n";
	private static final String RECORD_SEP = ",";
	private static final String DOCID_PREFIX = "union";
	private static final int DOCID_SEED = 13;
	private static final String DATA_FILE_DIR = "/Users/leinfelder/temp";

	private final int fileCount = 100;
	private final int recordCount = 5000;
	private final int columnCount = 6;
	
	//metadata file vars
	private static final String DOC_ID = "leinfelder.252.2";
	private static final String ATTR_LIST_ID = "FIRST1234";
	
	private String sessionId = "4C87FB06241B30A6D26C7B9503FF5F0C";
	
	private EcogridEndPointInterfaceTest endPointInfo = null;
	/*
	 * Constructors
	 */

	/**
	 * Because DataManagerTest is a subclass of TestCase, it must provide a
	 * constructor with a String parameter.
	 * 
	 * @param name
	 *            the name of a test method to run
	 */
	public DataPackagePopulator(String name) {
		super(name);
	}

	/*
	 * Class methods
	 */

	/**
	 * Create a suite of tests to be run together.
	 */
	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new DataPackagePopulator("initialize"));
		testSuite.addTest(new DataPackagePopulator("insertData"));

		return testSuite;
	}

	/*
	 * Instance methods
	 */

	/**
	 * Run an initial test that always passes to check that the test harness is
	 * working.
	 */
	public void initialize() {
		assertTrue(1 == 1);
	}

	/**
	 * Establish a testing framework by initializing appropriate objects.
	 */
	public void setUp() throws Exception {
		super.setUp();
		endPointInfo = new EcogridEndPointInterfaceTest();
	}

	/**
	 * Release any objects after tests are complete.
	 */
	public void tearDown() throws Exception {
		super.tearDown();
	}

	private Map uploadFiles(File dir) throws Exception {
		
	    log.debug("uploading files in dir=" + dir.getAbsolutePath());

		//the return map includes the (docid,file) key/value pairs
		Map fileMap = new TreeMap();
		
		//TODO get authentication working here with ecogrid
	    //EcogridAuthClient auth = new EcogridAuthClient(endPointInfo.getMetacatEcogridAuthEndPoint());
		//String sessionId = auth.login_action("kepler", "kepler");
		
	    log.debug("sessionId=" + sessionId);
		//String sessionId = null;

	    PutServiceClient putClient = new PutServiceClient(endPointInfo.getMetacatEcogridPutEndPoint());
	    
	    log.debug("created put client=" + putClient);

	    //go through the files and upload them
		File[] files = dir.listFiles();
		for (int i=0; i<files.length; i++) {
			File f = files[i];
			if (f.getAbsolutePath().endsWith(".csv")) {
				String docid = DOCID_PREFIX + "." + (DOCID_SEED + i) + ".1";
				//put the file
				log.debug("putting document: " + docid);
			    putClient.put(f.getAbsolutePath(), docid, EcogridObjType.DATA, sessionId);
			    fileMap.put(docid, f);
			}
		}
		return fileMap;
	}
	
	private void deleteFiles(Map files) {
		Iterator fileIter = files.values().iterator();
		while (fileIter.hasNext()) {
			File f = (File) fileIter.next();
			f.delete();
		}
	}
	
	private void generateDataFiles(File dir) throws Exception {
		for (int i=0; i<fileCount; i++) {
			//create the file
			File file = File.createTempFile("sampleData", ".csv", dir);
			//file.deleteOnExit();
			FileWriter fw = new FileWriter(file);
			
			boolean alreadyWroteHeader = false;
			//create the data
			//record
			for (int j=0; j<recordCount; j++) {
				//column 
				for (int k=0; k<columnCount; k++) {
					if (!alreadyWroteHeader) {
						fw.write("column_" + k);
					}
					else {
						long randomNumber = Math.round(Math.random()*1000);
						fw.write(String.valueOf(randomNumber));
					}
					//if not the last column
					if (k != (columnCount-1)) {
						fw.write(RECORD_SEP);
					}
				}//columns
				alreadyWroteHeader = true;
				//end of line character
				if (j != (recordCount-1)) {
					fw.write(EOLCharacter);
				}
			}//records
			fw.close();
		}//files
		
	}
	
	private void uploadMetadataXML(String xml) throws Exception {
		PutServiceClient putClient = new PutServiceClient(endPointInfo.getMetacatEcogridPutEndPoint());
		putClient.put(xml.getBytes(), DOC_ID, EcogridObjType.METADATA, sessionId);
	}
	
	/**
	 * Unit test for the DataManager.selectData() method. Tests the creation and
	 * use of a Query object for querying a data table. Runs a query and checks
	 * the result set against known values in the data table. This test is
	 * essentially a complete cradle-to-grave test of Uses Cases 1-4.
	 */
	public void insertData() throws Exception {
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		
		log.debug("starting test");
		this.generateDataFiles(new File(DATA_FILE_DIR));
	
		Map fileMap = this.uploadFiles(new File(DATA_FILE_DIR));
		log.debug("fileMap.size=" + fileMap.size());
		
		String metadataXML = this.generateMetadataXML(fileMap);
		log.debug("metadatadoc=" + metadataXML);
		
		this.uploadMetadataXML(metadataXML);
		
		this.deleteFiles(fileMap);
		
		endTime = System.currentTimeMillis();
		log.debug("time to run in millis: " + (endTime - startTime));
	}
	
	public static void main(String arg[]) {
		DataPackagePopulator dpp = new DataPackagePopulator("DataPackagePopulator");
		try {
			dpp.setUp();
			dpp.uploadFiles(new File(DATA_FILE_DIR));
			dpp.tearDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String generateMetadataXML(Map fileMap) {
		
		//get the opening of the xml, including seed
		String openingXML = 
			"<?xml version=\"1.0\"?><eml:eml xmlns:eml=\"eml://ecoinformatics.org/eml-2.0.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
			"packageId=\"#packageId#\" system=\"knb\" xsi:schemaLocation=\"eml://ecoinformatics.org/eml-2.0.1 eml.xsd\" scope=\"system\">" +
			"<dataset scope=\"document\"> " +
			"<title>union test data</title>" +
			"<creator id=\"1191029010870\" scope=\"document\"> <individualName><surName>leinfelder</surName>" +
			"</individualName>" +
			"</creator>" +
			"<abstract><para> </para>" +
			"</abstract>" +
			"<contact scope=\"document\"><references system=\"document\">1191029010870</references>" +
			"</contact>" +
			"<access authSystem=\"knb\" order=\"denyFirst\" scope=\"document\"><allow><principal>public</principal>" +
			"<permission>read</permission>" +
			"</allow>" +
			"</access>" +
			"<dataTable id=\"1191174210378\" scope=\"document\"><entityName>seed.csv</entityName>" +
			"<entityDescription>seed</entityDescription>" +
			"<physical id=\"1191174200730\" scope=\"document\"><objectName>seed.csv</objectName>" +
			"<size unit=\"byte\">277</size>" +
			"<dataFormat><textFormat><numHeaderLines>1</numHeaderLines>" +
			"<recordDelimiter>#x0A</recordDelimiter>" +
			"<attributeOrientation>column</attributeOrientation>" +
			"<simpleDelimited><fieldDelimiter>,</fieldDelimiter>" +
			"</simpleDelimited>" +
			"</textFormat>" +
			"</dataFormat>" +
			"<distribution scope=\"document\"><online><url function=\"download\">ecogrid://knb/leinfelder.9.1</url>" +
			"</online>" +
			"</distribution>" +
			"</physical>" +
			"<attributeList id=\"#attributeListId#\">" +
			"<attribute id=\"1191174210379\" scope=\"document\"><attributeName>column0</attributeName>" +
			"<attributeDefinition>unique id of the question (systemwide)</attributeDefinition>" +
			"<storageType typeSystem=\"Java\">integer</storageType>" +
			"<measurementScale><interval><unit><standardUnit>dimensionless</standardUnit>" +
			"</unit>" +
			"<precision>1</precision>" +
			"<numericDomain><numberType>integer</numberType>" +
			"</numericDomain>" +
			"</interval>" +
			"</measurementScale>" +
			"</attribute>" +

			"<attribute id=\"1191174210380\" scope=\"document\"><attributeName>column1</attributeName>" +
			"<attributeDefinition>unique student id</attributeDefinition>" +
			"<storageType typeSystem=\"Java\">integer</storageType>" +
			"<measurementScale><ratio><unit><standardUnit>dimensionless</standardUnit>" +
			"</unit>" +
			"<precision>1</precision>" +
			"<numericDomain><numberType>integer</numberType>" +
			"</numericDomain>" +
			"</ratio>" +
			"</measurementScale>" +
			"</attribute>" +

			"<attribute id=\"1191174210381\" scope=\"document\"><attributeName>column2</attributeName>" +
			"<attributeDefinition>unique student id</attributeDefinition>" +
			"<storageType typeSystem=\"Java\">integer</storageType>" +
			"<measurementScale><ratio><unit><standardUnit>dimensionless</standardUnit>" +
			"</unit>" +
			"<precision>1</precision>" +
			"<numericDomain><numberType>integer</numberType>" +
			"</numericDomain>" +
			"</ratio>" +
			"</measurementScale>" +
			"</attribute>" +

			"<attribute id=\"1191174210382\" scope=\"document\"><attributeName>column3</attributeName>" +
			"<attributeDefinition>numeric score earned on question</attributeDefinition>" +
			"<storageType typeSystem=\"Java\">integer</storageType>" +
			"<measurementScale><ratio><unit><standardUnit>dimensionless</standardUnit>" +
			"</unit>" +
			"<precision>1</precision>" +
			"<numericDomain><numberType>integer</numberType>" +
			"</numericDomain>" +
			"</ratio>" +
			"</measurementScale>" +
			"</attribute>" +

			"<attribute id=\"1191174210383\" scope=\"document\"><attributeName>column4</attributeName>" +
			"<attributeDefinition>numeric score earned on question</attributeDefinition>" +
			"<storageType typeSystem=\"Java\">integer</storageType>" +
			"<measurementScale><ratio><unit><standardUnit>dimensionless</standardUnit>" +
			"</unit>" +
			"<precision>1</precision>" +
			"<numericDomain><numberType>integer</numberType>" +
			"</numericDomain>" +
			"</ratio>" +
			"</measurementScale>" +
			"</attribute>" +

			"<attribute id=\"1191174210384\" scope=\"document\"><attributeName>column5</attributeName>" +
			"<attributeDefinition>numeric score earned on question</attributeDefinition>" +
			"<storageType typeSystem=\"Java\">integer</storageType>" +
			"<measurementScale><ratio><unit><standardUnit>dimensionless</standardUnit>" +
			"</unit>" +
			"<precision>1</precision>" +
			"<numericDomain><numberType>integer</numberType>" +
			"</numericDomain>" +
			"</ratio>" +
			"</measurementScale>" +
			"</attribute>" +

			"</attributeList>" +
			"<numberOfRecords>99</numberOfRecords>" +
			"</dataTable>";
		
		openingXML = openingXML.replaceAll("#packageId#", DOC_ID);
		openingXML = openingXML.replaceAll("#attributeListId#", ATTR_LIST_ID);
		
		String middleXML = "";
		//add the other packages
		Iterator fileIter = fileMap.keySet().iterator();
		while (fileIter.hasNext()) {
			//get the body of the dataTable element
			String dataTableTemplate = 
				"<dataTable id=\"DT_#docid#\" scope=\"document\"><entityName>#fileName#</entityName>" +
				"<entityDescription>#fileName#</entityDescription>" +
				"<physical id=\"phys_#docid#\" scope=\"document\"><objectName>#fileName#</objectName>" +
				"<size unit=\"byte\">#size#</size>" +
				"<dataFormat><textFormat><numHeaderLines>1</numHeaderLines>" +
				"<recordDelimiter>#x0A</recordDelimiter>" +
				"<attributeOrientation>column</attributeOrientation>" +
				"<simpleDelimited><fieldDelimiter>,</fieldDelimiter>" +
				"</simpleDelimited>" +
				"</textFormat>" +
				"</dataFormat>" +
				"<distribution scope=\"document\"><online><url function=\"download\">ecogrid://knb/#docid#</url>" +
				"</online>" +
				"</distribution>" +
				"</physical>" +
				"<attributeList>" +
				"<references system=\"document\">#attributeListId#</references>" +
				"</attributeList>" +
				"<numberOfRecords>#recordCount#</numberOfRecords>" +
				"</dataTable>";
			
			//get the info from the map entry
			String docid = (String) fileIter.next();
			File f = (File) fileMap.get(docid);
			
			//do som subsitution
			dataTableTemplate = dataTableTemplate.replaceAll("#docid#", docid);
			dataTableTemplate = dataTableTemplate.replaceAll("#fileName#", f.getName() );
			dataTableTemplate = dataTableTemplate.replaceAll("#size#", String.valueOf(f.length()) );
			dataTableTemplate = dataTableTemplate.replaceAll("#recordCount#", String.valueOf(recordCount) );
			dataTableTemplate = dataTableTemplate.replaceAll("#attributeListId#", ATTR_LIST_ID);
			
			//tack it on
			middleXML += dataTableTemplate;
		
		}
		
		//get the closing of the xml
		String closingXML = 
			"</dataset>" +
			"</eml:eml>";
		
		return openingXML + middleXML + closingXML;
		
	}
}
