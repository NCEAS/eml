<?xml version="1.0"?>
<!--
       '$RCSfile: buildDependencyTable.xsl,v $'
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

        '$Author: berkley $'
          '$Date: 2002-09-09 23:32:37 $'
      '$Revision: 1.1 $'

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
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:doc="eml://ecoinformatics.org/documentation-2.0.0rc1"
                version="1.0">
<xsl:output method="html" indent="yes"/>

<xsl:template match="/">
<html>
<head>
<title>EML Dependency Table</title>
</head>
<body>
  <table border="1">
  <tr>
  <th/>
  <xsl:for-each select="//doc:module">
    <th><xsl:value-of select="."/></th>
  </xsl:for-each>
  </tr>
    <xsl:for-each select="/xs:schema/xs:annotation/xs:appinfo/doc:moduleDocs/doc:module">
      <xsl:variable name="modFile">
        <xsl:value-of select="."/>
      </xsl:variable>
      <tr>
      <td><xsl:value-of select="."/></td>
        <xsl:for-each select="/xs:schema/xs:annotation/xs:appinfo/doc:moduleDocs/doc:module">
          <xsl:variable name="modName">
            <xsl:value-of select="substring-before(., '.')"/>
          </xsl:variable>
          <td>
          <xsl:for-each select="document($modFile)//xs:import">
          <xsl:variable name="importedDoc">
            <xsl:value-of select="substring-before(./@schemaLocation, '.')"/>
          </xsl:variable>
          <xsl:choose>
            <xsl:when test="normalize-space($importedDoc)=normalize-space($modName)">
              <xsl:text>X</xsl:text>
            </xsl:when>
          </xsl:choose>
        </xsl:for-each>
        &#160;
        </td>
      </xsl:for-each>
      </tr>
    </xsl:for-each>
  </table>
</body>
</html>
</xsl:template>

</xsl:stylesheet>
