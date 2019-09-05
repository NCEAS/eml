package org.ecoinformatics.datamanager.parser.generic;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ecoinformatics.datamanager.download.DownloadHandler;
import org.ecoinformatics.datamanager.download.EcogridEndPointInterfaceTest;
import org.ecoinformatics.datamanager.download.GZipDataHandler;
import org.ecoinformatics.datamanager.download.TarDataHandler;
import org.ecoinformatics.datamanager.download.ZipDataHandler;
import org.ecoinformatics.datamanager.parser.Attribute;
import org.ecoinformatics.datamanager.parser.AttributeList;
import org.ecoinformatics.datamanager.parser.Constraint;
import org.ecoinformatics.datamanager.parser.DataPackage;
import org.ecoinformatics.datamanager.parser.DateTimeDomain;
import org.ecoinformatics.datamanager.parser.Entity;
import org.ecoinformatics.datamanager.parser.NumericDomain;
import org.ecoinformatics.datamanager.parser.TextComplexDataFormat;
import org.ecoinformatics.datamanager.parser.TextDomain;

/**
 * @author leinfelder (but tao made the original test!)
 * 
 * JUnit tests for the Eml200DataPacakgeParser class.
 *
 */

public class Eml200DataPackageParserTest extends TestCase
{
  /*
   * Class fields
   */
  
  private static final String EMLSAMPLELOCATION = "lib/sample/eml-sample.xml";
  
  
  /*
   * Constructors
   */
  
  /**
   * Constructor
   */
  public Eml200DataPackageParserTest (String name)
  {
    super(name);
  }
  
  
  /*
   * Class methods
   */
  
  /**
   * Creates a suite of tests to be run together.
   */
   public static Test suite()
   {
     TestSuite suite = new TestSuite();
     suite.addTest(new Eml200DataPackageParserTest("testParse"));
     return suite;
   }

   
  /*
   * Instance methods
   */
  
  /**
   * Tests Eml200DataPackageParser parsing of an EML document.
   */
  public void testParse() throws Exception
  {
	  EcogridEndPointInterfaceTest endPointInfo = new EcogridEndPointInterfaceTest();
	  File emlFile = new File(EMLSAMPLELOCATION);
	  FileInputStream emlStream = new FileInputStream(emlFile);
	  DataPackageParserInterface parser = new Eml200DataPackageParser();
	  parser.parse(emlStream);

      DataPackage dataPackage = parser.getDataPackage();
	  String packageId = dataPackage.getPackageId();
	  assertEquals(packageId, "eml.1.1");

      Entity[] entityList     = dataPackage.getEntityList();
	  int size = entityList.length;
	  assertTrue(size == 1);

      Entity entity = entityList[0];
	  boolean isSimpleDelimited = entity.isSimpleDelimited();
	  assertTrue(isSimpleDelimited == true);

      boolean collapseDelimiters = entity.getCollapseDelimiters();
	  assertTrue(collapseDelimiters == false);

      String compressedMethod = entity.getCompressionMethod();
	  //System.out.println("compressedMethod "+compressedMethod);
	  assertEquals(compressedMethod, "");

      TextComplexDataFormat[] format = entity.getDataFormatArray();
	  assertTrue(format == null);

      String dbTableName = entity.getDBTableName();
	  assertTrue(dbTableName == null);

      String definition = entity.getDefinition();
	  assertEquals(definition, "patterns amoung communities at CDR");

      String delimiter = entity.getFieldDelimiter();
	  assertEquals(delimiter, "\\t");

      String fileName = entity.getFileName();
	  assertEquals(fileName, "");

      boolean hasZip = entity.getHasZipDataFile();
	  assertTrue(hasZip == false);

      boolean hasGZip = entity.getHasGZipDataFile();
	  assertTrue(hasGZip == false);

      boolean hasTar = entity.getHasTarDataFile();
	  assertTrue(hasTar == false);

      DownloadHandler handler = entity.getDownloadHandler(endPointInfo);
	  boolean isObjectOfDownloadHandler = handler instanceof DownloadHandler;
	  assertTrue( isObjectOfDownloadHandler == true);

      boolean isObjectOfTarHandler = handler instanceof TarDataHandler;
	  assertTrue(isObjectOfTarHandler == false);

      boolean isObjectOfZipHandler = handler instanceof ZipDataHandler;
	  assertTrue(isObjectOfZipHandler == false);

      boolean isObjectOfGzipHandler = handler instanceof GZipDataHandler;
	  assertTrue(isObjectOfGzipHandler == false);

      String id = entity.getId();
	  assertEquals(id, "xyz");

      boolean isImage = entity.getIsImageEntity();
	  assertTrue(isImage == false);

      String mappedName = entity.getMappedName();
	  assertTrue(mappedName == null);

      String name = entity.getName();
	  assertEquals(name, "CDR LTER-patterns among communities.txt");

      int numOfFooter = entity.getNumFooterLines();
	  //System.out.println("the number of footer line is "+numOfFooter);
	  assertTrue(numOfFooter == 0);

      int numOfHeader  = entity.getNumHeaderLines();
	  assertTrue(numOfHeader == 1);

      int numOfRecord = entity.getNumRecords();
	  assertTrue(numOfRecord == 22);

      String orientation = entity.getOrientation();
	  assertEquals(orientation, Entity.COLUMNMAJOR);

      String physicalDelimiter = entity.getPhysicalLineDelimiter();
	  assertTrue(physicalDelimiter == null);

      Constraint primaryKey = entity.getPrimaryKey();
	  assertTrue(primaryKey == null);

      String recordDelimiter = entity.getRecordDelimiter();
	  //System.out.println("the recordDelmiter is "+ recordDelimiter);
	  assertEquals(recordDelimiter, "\\r\\n");

      String url =entity.getURL();
	  //System.out.println("the url is "+url);
	  assertEquals(url, "http://metacat.nceas.ucsb.edu/knb" + 
                        "/servlet/metacat?action=read&docid=knb.46.1");

      AttributeList attributeList = entity.getAttributeList();
	  String attributeListId = attributeList.getId();
	  assertEquals(attributeListId, "at.1");

      Attribute[] attributeArray = attributeList.getAttributes();
	  int length = attributeArray.length;
	  assertTrue(length == 14);

      Attribute att1 = attributeArray[0];
	  String att1Definition = att1.getDefinition();
	  assertEquals(att1Definition, "Field where the data was collected");

      TextDomain att1Domain = (TextDomain)att1.getDomain();
	  String att1DomainDefiniation = att1Domain.getDefinition();
	  assertEquals(att1DomainDefiniation, "Valid names of fields");

      String att1Id = att1.getId();
	  //System.out.println("the id is "+att1Id);
	  assertEquals(att1Id, "att.1");

      String att1Label = att1.getLabel();
	  assertEquals(att1Label, "Field");

      String att1Measurement = att1.getMeasurementScale();
	  assertEquals(att1Measurement, "");

      String att1Name = att1.getName();
	  assertEquals(att1Name, "fld");

      String att1Unit = att1.getUnit();
	  assertEquals(att1Unit, "");

      String att1UnitType = att1.getUnitType();
	  assertEquals(att1UnitType, "");

      Attribute att2 = attributeArray[1];
	  String att2Definition = att2.getDefinition();
	  assertEquals(att2Definition, "The year the data was collected");

      DateTimeDomain att2Domain = (DateTimeDomain)att2.getDomain();
	  String att2DomainFormat = att2Domain.getFormatString();
	  assertEquals(att2DomainFormat, "YYYY");

      String att2Id = att2.getId();
	  //System.out.println("the id is "+att1Id);
	  assertEquals(att2Id, "att.2");

      String att2Label = att2.getLabel();
	  assertEquals(att2Label, "year");

      String att2Measurement = att2.getMeasurementScale();
	  assertEquals(att2Measurement, "");

      String att2Name = att2.getName();
	  assertEquals(att2Name, "year");

      String att2Unit = att2.getUnit();
	  assertEquals(att2Unit, "");

      String att2UnitType = att2.getUnitType();
	  assertEquals(att2UnitType, "");

      Attribute att3 = attributeArray[2];
	  String att3Definition = att3.getDefinition();
	  assertEquals(att3Definition, "Species richness for CDR");
      
      NumericDomain att3Domain = (NumericDomain)att3.getDomain();
	  String att3DomainFormat = att3Domain.getNumberType();
	  assertEquals(att3DomainFormat, "real");

      double att3DomainPrecision = att3Domain.getPrecision();
	  System.out.println("the precision is "+att3DomainPrecision);
	  assertTrue(att3DomainPrecision == 0.5);

      //Double min = att3Domain.getMinimum();
	  //double minValue = min.doubleValue();
	  //System.out.println("the min value is "+ minValue);
	  //assertTrue(minValue == 0);

      Double max = att3Domain.getMaximum();
	  assertEquals(max, null);

      String att3Id = att3.getId();
	  //System.out.println("the id is "+att1Id);
	  assertEquals(att3Id, "att.3");

      String att3Label = att3.getLabel();
	  assertEquals(att3Label, "Species Richness");

      String att3Measurement = att3.getMeasurementScale();
	  assertEquals(att3Measurement, "");

      String att3Name = att3.getName();
	  assertEquals(att3Name, "sr");

      String att3Unit = att3.getUnit();
	  assertEquals(att3Unit, "dimensionless");

      String att3UnitType = att3.getUnitType();
	  assertEquals(att3UnitType, Attribute.STANDARDUNIT);

      String[] missingValue = att3.getMissingValueCode();
	  assertEquals(missingValue, null);
	  
	  List<String> keywords = dataPackage.getKeywords();
      assertTrue(keywords.get(0).equals("Old field grassland"));
      assertTrue(keywords.get(1).equals("biomass"));
      assertTrue(keywords.get(2).equals("productivity"));
      assertTrue(keywords.get(3).equals("species-area"));
      assertTrue(keywords.get(4).equals("species richness"));
  }

}
