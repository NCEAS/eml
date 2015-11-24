<?xml version='1.0'?>

<!--
       '$RCSfile: getProjectionNames.xsl,v $'
       Copyright: 1997-2002 Regents of the University of California,
                            University of New Mexico, and
                            Arizona State University
        Sponsors: National Center for Ecological Analysis and Synthesis and
                  Partnership for Interdisciplinary Studies of Coastal Oceans,
                     University of California Santa Barbara
                  Long-Term Ecological Research Network Office,
                     University of New Mexico
                  Center for Environmental Studies, Arizona State University
   Other funding: National Science Foundation (see README for details)
                  The David and Lucile Packard Foundation
     For Details: http://knb.ecoinformatics.org/

        '$Author: mccartne $'
          '$Date: 2002-10-02 18:26:14 $'
      '$Revision: 1.2 $'

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<simpleType name="horizCoordSysNames">
    <xs:restriction base="string">
  	<xsl:for-each  select="//horizCoordSysDef/@name">
    	<xsl:element name="xs:enumeration">
			<xsl:attribute name="value">
				<xsl:value-of select="."/>
			</xsl:attribute>
	 	</xsl:element>
	</xsl:for-each>
    </xs:restriction>
</simpleType>


<simpleType name="projectionNames">
    <xs:restriction base="string">
  	<xsl:for-each select="//projection[not(@name=preceding::projection/@name)]">
		<xsl:element name="xs:enumeration">
			<xsl:attribute name="value">
				<xsl:value-of select="./@name"/>
			</xsl:attribute>
	 	</xsl:element>
	</xsl:for-each>
    </xs:restriction>
</simpleType>
<simpleType name="parameterNames">
    <xs:restriction base="string">
  	<xsl:for-each select="//parameter[not(@name=preceding::parameter/@name)]">
		<xsl:element name="xs:enumeration">
			<xsl:attribute name="value">
				<xsl:value-of select="./@name"/>
			</xsl:attribute>
	 	</xsl:element>
	</xsl:for-each>
    </xs:restriction>
</simpleType>

</xsl:template>

	

</xsl:stylesheet><!-- Stylus Studio meta-information - (c)1998-2002 eXcelon Corp.
<metaInformation>
<scenarios ><scenario default="no" name="Scenario1" userelativepaths="yes" externalpreview="no" url="eml&#x2D;spatialReferenceDictionary.xml" htmlbaseurl="" processortype="internal" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext=""/><scenario default="yes" name="Scenario2" userelativepaths="yes" externalpreview="no" url="eml&#x2D;spatialReferenceDictionaryShort.xml" htmlbaseurl="" processortype="internal" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext=""/></scenarios><MapperInfo srcSchemaPath="" srcSchemaRoot="" srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/>
</metaInformation>
-->