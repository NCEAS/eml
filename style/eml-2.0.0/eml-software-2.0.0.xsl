<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-software-2.0.0.xsl,v $'
  *      Authors: Matt Jones
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2003-11-13 19:35:03 $'
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
  * convert an XML file that is valid with respect to the eml-dataset.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format
  * suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:import href="eml-settings-2.0.0beta6-@name@.xsl" />
<xsl:import href="eml-resource-2.0.0beta6-@name@.xsl"/>

  <xsl:output method="html" encoding="iso-8859-1"/>

  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css"
              href="{$stylePath}/{$qformat}.css" />
      </head>
      <body>
        <center>
          <h1>Software Description</h1>
          <h3>Ecological Metadata Language</h3>
        </center>
        <table class="tabledefault" width="100%"><!-- width needed for NN4 - doesn't recognize width in css -->

          <!--xsl:apply-templates select="software/identifier" mode="resource"/>
          <xsl:apply-templates select="software/pubDate" mode="resource"/>
          <xsl:apply-templates select="software/pubPlace" mode="resource"/>
          <xsl:apply-templates select="software/series" mode="resource"/-->

          <xsl:apply-templates select="software/*" mode="resource"/>
          <xsl:apply-templates select="software"/>
          </table>
      </body>
    </html>
  </xsl:template>

    <xsl:template match="software">
        <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Software:</xsl:text></td></tr>
        <xsl:apply-templates select="./versionNumber"/>
        <xsl:apply-templates select="./sourceModule"/>
        <xsl:apply-templates select="./binaryFile"/>
        <xsl:apply-templates select="./location"/>
        <xsl:apply-templates select="./programmingLanguage"/>
    </xsl:template>

    <xsl:template match="versionNumber" mode="resource"/>
    <xsl:template match="versionNumber">
      <xsl:if test="normalize-space(../versionNumber)!=''">
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Version Number:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../versionNumber"/></td></tr>
      </xsl:if>
    </xsl:template>

    <xsl:template match="sourceModule" mode="resource"/>
    <xsl:template match="sourceModule">
      <xsl:if test="normalize-space(../sourceModule)!=''">
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Source Module:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../sourceModule"/></td></tr>
      </xsl:if>
    </xsl:template>

    <xsl:template match="binaryFile" mode="resource"/>
    <xsl:template match="binaryFile">
      <xsl:if test="normalize-space(../binaryFile)!=''">
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Binary File:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../binaryFile"/></td></tr>
      </xsl:if>
    </xsl:template>

    <xsl:template match="location" mode="resource"/>
    <xsl:template match="location">
      <xsl:if test="normalize-space(../location)!=''">
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Location:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../location"/></td></tr>
      </xsl:if>
    </xsl:template>

    <xsl:template match="programmingLanguage" mode="resource"/>
    <xsl:template match="programmingLanguage">
      <xsl:if test="normalize-space(../programmingLanguage)!=''">
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Programming Language:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../programmingLanguage"/></td></tr>
      </xsl:if>
    </xsl:template>

<!-- suppress these -->
  <xsl:template match="identifier"/>
  <xsl:template match="shortName"/>
  <xsl:template match="title"/>
  <xsl:template match="software" mode="resource"/>
  <xsl:template match="pubDate"/>
  <xsl:template match="pubPlace"/>
  <xsl:template match="series"/>

</xsl:stylesheet>
