<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-attribute.xsl,v $'
  *      Authors: Matt Jones
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2002-05-01 01:02:19 $'
  * '$Revision: 1.2 $'
  * 
  * This program is free software; you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation; either version 2 of the License, or
  * (at your option) any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with this program; if not, write to the Free Software
  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
  *
  * This is an XSLT (http://www.w3.org/TR/xslt) stylesheet designed to
  * convert an XML file that is valid with respect to the eml-variable.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format 
  * suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:import href="eml-settings.xsl" />
<xsl:import href="eml-identifier.xsl" />

  <xsl:output method="html" encoding="iso-8859-1"/>

  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" 
              href="{$stylePath}/{$qformat}.css" />
      </head>
      <body>
        <center>
          <h1>Attribute structure description</h1>
          <h3>Ecological Metadata Language</h3>
        </center>

        <table class="tabledefault" width="100%"><!-- width needed for NN4 - doesn't recognize width in css -->
        <xsl:apply-templates select="eml-attribute/identifier" mode="resource"/>
        <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Attributes in the Data Set</xsl:text></td></tr>
        <tr>
        <td colspan="2"><table width="100%"><tr> 
            <td class="{$firstColStyle}" width="{$firstColWidth}"><xsl:text>Attribute Name</xsl:text></td>
            <td class="{$firstColStyle}" width="{$firstColWidth}"><xsl:text>Attribute Definition</xsl:text></td>
            <td class="{$firstColStyle}" ><xsl:text>Unit</xsl:text></td>
            <td class="{$firstColStyle}" ><xsl:text>Type</xsl:text></td>
            <td class="{$firstColStyle}" ><xsl:text>Codes</xsl:text></td>
            <td class="{$firstColStyle}" ><xsl:text>Range</xsl:text></td>
            <td class="{$firstColStyle}" ><xsl:text>Missing</xsl:text></td>
            <td class="{$firstColStyle}" ><xsl:text>Precision</xsl:text></td>
        </tr>
        <xsl:for-each select="eml-attribute/attribute">
          <tr valign="top">
            <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1">rowodd</xsl:when>
                <xsl:when test="position() mod 2 = 0">roweven</xsl:when>
              </xsl:choose>
            </xsl:variable>
              <td width="{$firstColWidth}" class="{$firstColStyle}"><xsl:value-of select="attributeName"/></td>
              <td width="{$firstColWidth}" class="{$stripes}"><xsl:value-of select="attributeDefinition"/></td>
              <td class="{$stripes}"><xsl:value-of select="unit"/></td>
              <td class="{$stripes}"><xsl:value-of select="dataType"/></td>
              <td class="{$stripes}">
              <xsl:if test="normalize-space(attributeDomain/enumeratedDomain/code)!='' or normalize-space(attributeDomain/enumeratedDomain/definition)!=''">
              <ul><xsl:for-each select="attributeDomain/enumeratedDomain">
                    <li><xsl:value-of select="code"/>
                        <xsl:text> - </xsl:text>
                        <xsl:value-of select="definition"/>
                    </li>
                  </xsl:for-each></ul>
              </xsl:if></td>
              <td class="{$stripes}">
              <xsl:if test="normalize-space(attributeDomain/numericDomain/minimum)!='' or normalize-space(attributeDomain/numericDomain/maximum)!=''">
                <ul>
                  <xsl:for-each select="attributeDomain/numericDomain">
                    <li><xsl:value-of select="minimum"/>
                        <xsl:text> - </xsl:text>
                        <xsl:value-of select="maximum"/>
                    </li>
                  </xsl:for-each>
                </ul>
              </xsl:if></td>
              <td class="{$stripes}">
                <xsl:for-each select="missingValueCode">
                    <xsl:value-of select="."/><br />
                </xsl:for-each>
                </td>
              <td class="{$stripes}"><xsl:value-of select="precision"/></td>
          </tr>
        </xsl:for-each>
        </table>
        </td>
        </tr>
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
