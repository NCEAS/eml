<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-constraint-2.0.0.xsl,v $'
  *      Authors: Matthew Brooke
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
<xsl:import href="eml-identifier-2.0.0beta6-@name@.xsl" />

  <xsl:output method="html" encoding="iso-8859-1"/>


  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css"
              href="{$stylePath}/{$qformat}.css" />
      </head>
      <body>
        <center>
          <h1>Constraints</h1>
          <h3>Ecological Metadata Language</h3>
        </center>
        <table class="tabledefault" width="100%"><!-- width needed for NN4 - doesn't recognize width in css -->
        <xsl:apply-templates select="eml-constraint/identifier" mode="resource"/>
        <xsl:for-each select="eml-constraint/constraint">
        <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Relational Integrity Constraints:</xsl:text></td></tr>
           <xsl:apply-templates select="."/>
        </xsl:for-each>

        </table>
      </body>
    </html>
  </xsl:template>


  <xsl:template match="constraint">
      <xsl:if test="normalize-space(constraintType)!=''">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Type</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="constraintType"/></td></tr>
      </xsl:if>
      <xsl:if test="normalize-space(constraintName)!=''">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Name</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="constraintName"/></td></tr>
      </xsl:if>
      <xsl:if test="normalize-space(constraintDescription)!=''">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Description</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="constraintDescription"/></td></tr>
      </xsl:if>
      <xsl:if test="normalize-space(entityId)!=''">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Entity Id</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="entityId"/></td></tr>
      </xsl:if>
      <xsl:if test="normalize-space(referencedEntityId)!=''">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Referenced Entity Id</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="referencedEntityId"/></td></tr>
      </xsl:if>
      <xsl:if test="normalize-space(referencedKey)!=''">
          <xsl:for-each select="referencedKey/attributeName">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            <xsl:text>Referenced Key</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:apply-templates select="."/></td></tr>
          </xsl:for-each>
      </xsl:if>
      <xsl:if test="normalize-space(key)!=''">
          <xsl:for-each select="key/attributeName">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            <xsl:text>Key</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:apply-templates select="."/></td></tr>
          </xsl:for-each>
      </xsl:if>
      <xsl:if test="normalize-space(cardinality)!=''">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Cardinality</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="cardinality"/></td></tr>
      </xsl:if>
      <xsl:if test="normalize-space(checkCondition)!=''">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Check Condition</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="checkCondition"/></td></tr>
      </xsl:if>
   </xsl:template>

   <xsl:template match="attributeName">
        <xsl:value-of select="."/>
   </xsl:template>


</xsl:stylesheet>
