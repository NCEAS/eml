<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-constraint.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: berkley $'
  *     '$Date: 2002-04-20 02:30:13 $'
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
          <h1>Constraints</h1>
          <h3>Ecological Metadata Language</h3>
        </center>
        <table width="100%"><!-- total 10 columns -->
        <tr>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        </tr>
        <xsl:apply-templates select="eml-constraint/identifier"/>

        <tr class="tablehead">
        <td width="10%"><xsl:text>Type</xsl:text></td>
        <td width="10%"><xsl:text>Name</xsl:text></td>
        <td colspan="2"><xsl:text>Description</xsl:text></td>
        <td width="10%"><xsl:text>entityId</xsl:text></td>
        <td width="10%"><xsl:text>key</xsl:text></td>
        <td width="10%"><xsl:text>referencedEntityId</xsl:text></td>
        <td width="10%"><xsl:text>referencedKey</xsl:text></td>
        <td width="10%"><xsl:text>cardinality</xsl:text></td>
        <td width="10%"><xsl:text>checkCondition</xsl:text></td>
        </tr>

         <xsl:call-template name="blankrow">
                <xsl:with-param name="style" select="'tabledefault'"/>
         </xsl:call-template>
         <xsl:apply-templates select="eml-constraint/constraint"/>
        </table>
      </body>
    </html>
  </xsl:template>
  

  <xsl:template match="constraint">
       <xsl:for-each select=".">
          <tr valign="top">
                <xsl:attribute name="class">
                  <xsl:choose>
                    <xsl:when test="position() mod 2 = 1">rowodd</xsl:when>
                    <xsl:when test="position() mod 2 = 0">roweven</xsl:when>
                  </xsl:choose>
                </xsl:attribute>
                <td width="10%"><xsl:value-of select="./constraintType"/><xsl:text>&#160;</xsl:text></td>
                <td width="10%"><xsl:value-of select="./constraintName"/><xsl:text>&#160;</xsl:text></td>
                <td colspan="2"><xsl:value-of select="./constraintDescription"/><xsl:text>&#160;</xsl:text></td>
                <td width="10%"><xsl:value-of select="./entityId"/><xsl:text>&#160;</xsl:text></td>
                <td width="10%"><xsl:value-of select="./key"/><xsl:text>&#160;</xsl:text></td>
                <td width="10%"><xsl:value-of select="./referencedEntityId"/><xsl:text>&#160;</xsl:text></td>
                <td width="10%"><xsl:value-of select="./referencedKey"/><xsl:text>&#160;</xsl:text></td>
                <td width="10%"><xsl:value-of select="./cardinality"/><xsl:text>&#160;</xsl:text></td>
                <td width="10%"><xsl:value-of select="./checkCondition"/><xsl:text>&#160;</xsl:text></td>
              </tr>
              <xsl:call-template name="blankrow">
                <xsl:with-param name="style" select="'roweven'"/>
              </xsl:call-template>
              </xsl:for-each>

  </xsl:template>
  
  <xsl:template match="identifier">

        <xsl:for-each select=".">
        <tr>
        <td class="highlight" colspan="2">
           <b><xsl:text>Metadata File ID:</xsl:text></b>
        </td>
        <td colspan="3">
            <xsl:value-of select="."/>
        </td>
        <td class="highlight" colspan="2">
           <b><xsl:text>Catalog System:</xsl:text></b></td>
        <td colspan="3">
            <xsl:value-of select="./@system"/><xsl:text>&#160;</xsl:text></td>
      </tr>
      </xsl:for-each>
      <xsl:call-template name="blankrow">
      <xsl:with-param name="style" select="'tabledefault'"/>
      </xsl:call-template>
  </xsl:template>
  
   <xsl:template name="blankrow">
   <xsl:param name="style"/> 
        <tr class="{$style}">
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td colspan="2"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        <td width="10%"><xsl:text>&#160;</xsl:text></td>
        </tr>
  </xsl:template>
             
</xsl:stylesheet>
