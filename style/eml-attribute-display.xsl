<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-attribute-display.xsl,v $'
  *      Authors: Matt Jones
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: berkley $'
  *     '$Date: 2002-04-19 16:16:10 $'
  * '$Revision: 1.1 $'
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
<xsl:import href="eml-global-settings.xsl" />

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
        
        <xsl:apply-templates select="eml-attribute/identifier"/>

        <h3>Attributes in the Data Set:</h3>
        <table width="100%">
        <tr>
        <th class="tablehead"><xsl:text>Attribute Name</xsl:text></th>
        <th class="tablehead"><xsl:text>Attribute Definition</xsl:text></th>
        <th class="tablehead"><xsl:text>Unit</xsl:text></th>
        <th class="tablehead"><xsl:text>Type</xsl:text></th>
        <th class="tablehead"><xsl:text>Codes</xsl:text></th>
        <th class="tablehead"><xsl:text>Range</xsl:text></th>
        <th class="tablehead"><xsl:text>Missing</xsl:text></th>
        <th class="tablehead"><xsl:text>Precision</xsl:text></th>
        </tr>

        <xsl:for-each select="eml-attribute/attribute">
          <tr valign="top">
            <xsl:attribute name="class">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1">rowodd</xsl:when>
                <xsl:when test="position() mod 2 = 0">roweven</xsl:when>
              </xsl:choose>
            </xsl:attribute>

          <td><b><xsl:value-of select="attributeName"/></b>
              </td>
          <td><xsl:value-of select="attributeDefinition"/>
              </td>
          <td><xsl:value-of select="unit"/>
              </td>
          <td><xsl:value-of select="dataType"/>
              </td>
          <td><ul>
              <xsl:for-each select="attributeDomain/enumeratedDomain">
                <li><xsl:value-of select="code"/>
                    <xsl:text> - </xsl:text>
                    <xsl:value-of select="definition"/>
                </li>
              </xsl:for-each>
              </ul>
              </td>
          <td><ul>
              <xsl:for-each select="attributeDomain/numericDomain">
                <li><xsl:value-of select="minimum"/>
                    <xsl:text> - </xsl:text>
                    <xsl:value-of select="maximum"/>
                </li>
              </xsl:for-each>
              </ul>
              </td>
          <td><xsl:for-each select="missingValueCode">
                <xsl:value-of select="."/><br />
              </xsl:for-each>
              </td>
          <td><xsl:value-of select="precision"/>
              </td>
          </tr>
        </xsl:for-each>
        </table>

      </body>
    </html>
  </xsl:template>

  <xsl:template match="identifier">
    <table>
      <tr>
        <td class="highlight">
           <b><xsl:text>Metadata File ID:</xsl:text></b>
        </td>
        <td>
            <xsl:value-of select="."/>
        </td>
      </tr>
    </table>
  </xsl:template>
</xsl:stylesheet>
