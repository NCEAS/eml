<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-protocol-2.0.0beta6.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2003-11-13 19:36:14 $'
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
  <xsl:import href="eml-literature-2.0.0beta6.xsl"/>

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
        <table class="tabledefault" width="100%"><!-- width needed for NN4 - doesn't recognize width in css -->
        <xsl:apply-templates select="eml-protocol/identifier" mode="resource"/>
        <xsl:apply-templates select="eml-protocol/protocol"/>
        </table>
      </body>
    </html>
  </xsl:template>
  
  <xsl:template match="protocol">
        <xsl:for-each select="method">
          <xsl:apply-templates select="."/>
        </xsl:for-each>      
        <xsl:for-each select="processingStep">
          <xsl:apply-templates select="."/>
        </xsl:for-each>      
        <xsl:for-each select="qualityControl">
          <xsl:apply-templates select="."/>
        </xsl:for-each>      
  </xsl:template>


  <xsl:template match="method">
    <tr class="{$subHeaderStyle}"><td colspan="2">
      <xsl:text>Method:</xsl:text></td></tr>
      <xsl:call-template name="renderParagsCits"/>
  </xsl:template>

  
  <xsl:template match="processingStep">
    <tr class="{$subHeaderStyle}"><td colspan="2">
      <xsl:text>Processing Step:</xsl:text></td></tr>
      <xsl:call-template name="renderParagsCits"/>
  </xsl:template>

  <xsl:template match="qualityControl">
    <tr class="{$subHeaderStyle}"><td colspan="2">
      <xsl:text>Quality Control Mechanisms:</xsl:text></td></tr>
      <xsl:call-template name="renderParagsCits"/>
  </xsl:template>
  
  
  <xsl:template name="renderParagsCits">
      <xsl:for-each select="./paragraph">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}" valign="top">
        &#160;</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
      </xsl:for-each>

      <xsl:for-each select="./citation">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}" valign="top">
      Literature Citation</td><td width="{$secondColWidth}" class="{$secondColStyle}">
      <table width="100%">
        <xsl:apply-templates select="."/>
      </table></td></tr>
      </xsl:for-each>
  </xsl:template> 

</xsl:stylesheet>
