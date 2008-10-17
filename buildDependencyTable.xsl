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

        '$Author: obrien $'
          '$Date: 2008-10-17 23:32:59 $'
      '$Revision: 1.18 $'

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
                xmlns:doc="eml://ecoinformatics.org/documentation-2.1.0"
                version="1.0">
<xsl:output method="html" indent="yes"/>

<xsl:template match="/">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../default.css"/>
<title>EML Dependency Chart</title>
</head>
<body class="tabledefault">
  <h2 class="title">EML Dependency Chart</h2>
  <p>
    To read this chart, look at the row header and move across to the X.  The
    relationship can be read
    <em>&lt;row header&gt; depends on &lt;column header&gt;</em>
    or
    <em>&lt;row header&gt; imports &lt;column header&gt;</em>
  </p>
  <p>
    If you read the chart in the reverse fashion the relationship becomes
    <em>&lt;column header&gt; is depended on by &lt;row header&gt;</em>
    or
    <em>&lt;column header&gt; is imported by &lt;row header&gt;</em>
  </p>
  <p>
    If one module depends on another, it is a good chance that you are going
    to be using at least a small subset of the other modules nodes within
    the module that you want to use.  This can be seen clearly by looking
    at the diagrams associated with each
    <a href="index.html#moduleDescriptions">module</a>.
  </p>
  <table border="1" cellpadding="3">
  <tr>
  <th/>
  <!--create the column headers-->
  <xsl:for-each select="//doc:module">
    <th valign="bottom" class="tablehead2">
      <xsl:call-template name="verttext">
        <xsl:with-param name="text" select="."/>
      </xsl:call-template>
    </th>
  </xsl:for-each>
  </tr>
  
    <!--loop through each module to do the rows-->
    <xsl:for-each select="/xs:schema/xs:annotation/xs:appinfo/doc:module">
      <xsl:variable name="modFile">
        <xsl:value-of select="."/>
      </xsl:variable>
      <tr>

      <!--color the row based on it's position-->
      <xsl:attribute name="class">
       <xsl:choose>
         <xsl:when test="position() mod 2 = 1">rowodd</xsl:when>
         <xsl:when test="position() mod 2 = 0">roweven</xsl:when>
       </xsl:choose>
      </xsl:attribute>

      <!-- save the position for later use-->
      <xsl:variable name="pos">
        <xsl:value-of select="position() mod 2"/>
      </xsl:variable>

      <td class="tablehead2"><xsl:value-of select="."/></td> <!--the row header-->
        <!--go through the list of modules again, this time comparing its imported
            modules to the module name from the outer loop-->
        <xsl:for-each select="/xs:schema/xs:annotation/xs:appinfo/doc:module">
          <xsl:variable name="modName">
            <xsl:value-of select="substring-before(., '.')"/>
          </xsl:variable>
          <td align="center">
          <xsl:for-each select="document($modFile)//xs:import">
          <xsl:variable name="importedDoc">
            <xsl:value-of select="substring-before(./@schemaLocation, '.')"/>
          </xsl:variable>
          <!-- compare the import variable to the modName variable-->
          <xsl:if test="normalize-space($importedDoc)=normalize-space($modName)">
            <xsl:attribute name="class">
              <xsl:choose>
               <!--change the color of the X cells slightly-->
               <xsl:when test="$pos = 1">rowoddemphasis</xsl:when>
               <xsl:when test="$pos = 0">rowevenemphasis</xsl:when>
             </xsl:choose>
            </xsl:attribute>
            <xsl:text>X</xsl:text> <!--put the x where they are equal-->
          </xsl:if>
          <xsl:if test="normalize-space($modName)=normalize-space(substring-before($modFile, '.'))">
            <!--color the diagonal cells where the info makes no sense-->
            <xsl:attribute name="bgcolor">
              <xsl:text>black</xsl:text>
            </xsl:attribute>
          </xsl:if>
        </xsl:for-each>
        &#160; <!--put in a nonbreaking space to create the empty cells-->
        </td>
      </xsl:for-each>
      </tr>
    </xsl:for-each>
  </table>
</body>
</html>
</xsl:template>

<!--this template makes the table headers go vertical-->
<xsl:template name="verttext">
  <xsl:param name="text"/>
  <xsl:variable name="textchar">
    <xsl:value-of select="substring($text, 1, 1)"/>
  </xsl:variable>
  <xsl:variable name="textend">
    <xsl:value-of select="substring($text, 2)"/>
  </xsl:variable>

  <xsl:value-of select="$textchar"/><br/>

  <xsl:if test="string-length($textend) > 0">
    <!--recurse if there is anything left in the string-->
    <xsl:call-template name="verttext">
      <xsl:with-param name="text" select="$textend"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
