Feb 28, 2003

Converting eml beta6 packages to eml2

The xslt stylesheets 'emlb6toeml2.xsl', 'triple_info.xsl', 'eml2entphy.xsl', and 'eml2attr.xsl' can be used to convert a set of eml beta6 modules that represent an emlbeta6 package into an eml2 document. There are two transformations in the process and a Windows batch file for executing the operation is named 'runnit.bat'.

The process starts with the beta6 dataset module. This XML document contains the 'triples' which identify all the modules in the document and the relationships among the modules. The stylesheet 'triple_info.xsl' reads the triples in the dataset module and builds an xml document called 'packageStructure.xml'. An example showing the basic structure is shown below. This xslt transform collects information in the triples and arranges it so there is a list of entities in the package and the attribute, physical, and datafile module document IDs are children of the appropriate entityID. 'triple_info.xsl' is thus an example of processing triple information using XSLT rather than Java. Note that xsl:params are used to give the stylesheet the id and path information for the package. 

----Example packageStructure document-----
?xml version="1.0" encoding="ISO-8859-1"?>
<package id="jones.204.18">
<acl>jones.203.2</acl>
<entities>
  <entity id="jones.206.3">
    <attribute>jones.207.3</attribute>
    <physical>jones.208.3</physical>
    <dataFile>jones.205.3</dataFile>
  </entity>

  <entity id="jones.215.1">
    <attribute>jones.216.1</attribute>
    <physical>jones.217.1</physical>
    <dataFile>jones.214.1</dataFile>
  </entity>
</entities>
</package>
------------------------------------------

The stylesheet 'emlb6toeml2.xsl' is the top level stylesheet for using the information in the packageStructure document to create an eml2 document. This stylesheet 'includes' the 'eml2entphy.xsl', and 'eml2attr.xsl' stylesheets for handling entity, physical, and attribute information.         

The 'eml2attr.xsl' sheet includes checking units against the eml-unitDictionary. If a beta6 unit (or ist abbreviation) is found in the unit-Dictionary, a standard unit is created. Otherwise, a custom unit is defined. (Currently, a definition of the custom unit is not automatically created and added to the otherMetadata element.) [A copy of eml-unitDictionary needs to be in the same directory as the style sheets.]
Also, the determination of measurementScale is rather arbitrary. All nonNUmerica and nonDate attribute information (i.e. text) is mapped to the 'nominal' measurementScale. (No 'ordinal' values are ever created.) Numeric information is mapped to 'ratio' if the minimum is greater than 0, or 'interval' if the minumum is negative. 


Currently Unimplemented:
	Currently, actual data is not included in the eml2 document. Neither are modules other than access, dataset, entity, attribute, and physical.