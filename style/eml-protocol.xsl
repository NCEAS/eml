<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-protocol.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: berkley $'
  *     '$Date: 2002-04-19 22:49:44 $'
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
<xsl:import href="eml-settings.xsl" />

  <xsl:output method="html" encoding="iso-8859-1"/>

  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" 
              href="{$stylePath}/{$qformat}.css" />
      </head>
      <body>
        <center>
          <h1>Protocol Description</h1>
          <h3>Ecological Metadata Language</h3><br />
        </center>
        <xsl:apply-templates select="eml-protocol/identifier"/>
        <xsl:apply-templates select="eml-protocol/protocol"/>
      </body>
    </html>
  </xsl:template>
  
  <xsl:template match="protocol">
    <table border="1">
      <tr>
        <td>Method</td>
        <td><xsl:apply-templates select="method"/></td>
      </tr>
      <tr>
        <td>Processing Step</td>
        <td><xsl:apply-templates select="processingStep"/></td>
      </tr>
      <tr>
        <td>Quality Control</td>
        <td><xsl:apply-templates select="qualityControl"/></td>
      </tr>
    </table>
  </xsl:template>
  
  <xsl:template match="method">
    <p><h3>Method</h3>
    <xsl:apply-templates/>
    </p>
  </xsl:template>
  
  <xsl:template match="processingStep">
    <p><h3>Processing Step</h3>
    <xsl:apply-templates/>
    </p>
  </xsl:template>

  <xsl:template match="qualityControl">
    <p><h3>Quality Control Mechanisms</h3>
    <xsl:apply-templates/>
    </p>
  </xsl:template>
 
  <xsl:template match="identifier">
    <table>
    <tr>
      <td><b>Metadata Identifier:</b></td>
      <td><xsl:value-of select="."/></td>
    </tr>
    </table>
  </xsl:template>

  <xsl:template match="paragraph">
    <p><xsl:value-of select="."/></p>
  </xsl:template>

</xsl:stylesheet>
