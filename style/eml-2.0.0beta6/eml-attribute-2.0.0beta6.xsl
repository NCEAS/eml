<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-attribute-2.0.0beta6.xsl,v $'
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2003-12-06 01:43:32 $'
  * '$Revision: 1.4 $'
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
<xsl:import href="eml-settings-2.0.0beta6.xsl" />
<xsl:import href="eml-identifier-2.0.0beta6.xsl" />

  <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  

  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css"
                href="{$stylePath}/{$qformat}/{$qformat}.css" />
        <script language="Javascript" type="text/JavaScript"
                src="{$stylePath}/{$qformat}/{$qformat}.js"></script>
        <script language="Javascript" type="text/JavaScript"
                src="{$styleCommonPath}/branding.js"></script>      
      </head>
      <body>

        <script language="JavaScript">insertTemplateOpening();</script> 
        <center>
          <h1>Attribute structure description</h1>
          <h3>Ecological Metadata Language</h3>
        </center>

        <table class="tabledefault" width="100%"><!-- width needed for NN4 - doesn't recognize width in css -->
        <xsl:apply-templates select="eml-attribute/identifier" mode="resource"/>
        <tr><td class="{$subHeaderStyle}" colspan="2">
        Attributes in the Data Set</td></tr>
        <tr>
        <td colspan="2"><table width="100%"><tr> 
            <td class="{$firstColStyle}" width="{$firstColWidth}">Attribute Name</td>
            <td class="{$firstColStyle}" >Label</td>
            <td class="{$firstColStyle}" >Definition</td>
            <td class="{$firstColStyle}" >Unit</td>
            <td class="{$firstColStyle}" >Type</td>
            <td class="{$firstColStyle}" >Missing</td>
            <td class="{$firstColStyle}" >Precision</td>
            <td class="{$firstColStyle}" >Attrib Domain</td>
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
              <td class="{$stripes}"><xsl:value-of select="attributeLabel"/>&#160;</td>
              <td class="{$stripes}"><xsl:value-of select="attributeDefinition"/></td>
              <td class="{$stripes}"><xsl:value-of select="unit"/>&#160;</td>
              <td class="{$stripes}"><xsl:value-of select="dataType"/>&#160;</td>
              <td class="{$stripes}">
                <xsl:for-each select="missingValueCode">
                    <xsl:value-of select="."/>&#160;<br />
                </xsl:for-each>
                </td>
              <td class="{$stripes}"><xsl:value-of select="precision"/>&#160;</td>

              <td class="{$stripes}">
              <table width="100%">
                  <xsl:for-each select="attributeDomain/enumeratedDomain">
                    <xsl:apply-templates select="."/>
                  </xsl:for-each>
                  <xsl:for-each select="attributeDomain/textDomain">
                    <xsl:apply-templates select="."/>
                  </xsl:for-each>
                  <xsl:for-each select="attributeDomain/numericDomain">
                    <xsl:apply-templates select="."/>
                  </xsl:for-each>
              </table></td>
          </tr>
        </xsl:for-each>
        </table>
        </td>
        </tr>
        </table>
        
        <script language="JavaScript">insertTemplateClosing();</script>
        
      </body>
    </html>
  </xsl:template>


  <xsl:template match="enumeratedDomain">
      <tr><td colspan="3" align="center" class="{$firstColStyle}">enumerated:</td></tr>
      <tr><td>code:</td><td>defin:</td><td>source:</td></tr>
      <tr><td><xsl:value-of select="code"/></td>
          <td><xsl:value-of select="definition"/></td>
          <td><xsl:value-of select="source"/>&#160;</td>
      </tr>
  </xsl:template>
  
  <xsl:template match="textDomain">
      <tr><td colspan="3" align="center" class="{$firstColStyle}">text:</td></tr>
      <tr><td>defin:</td><td>pattern:</td><td>source:</td></tr>
      <tr><td><xsl:value-of select="definition"/></td>
          <td><xsl:for-each select="pattern">
            <xsl:value-of select="."/><br />
          </xsl:for-each></td>
          <td><xsl:value-of select="source"/>&#160;</td>
      </tr>
  </xsl:template>  
  
  <xsl:template match="numericDomain">
      <tr><td colspan="2" align="center" class="{$firstColStyle}">numeric:</td></tr>
      <tr><td>min:</td><td>max:</td></tr>
      <tr><td><xsl:value-of select="minimum"/></td>
          <td><xsl:value-of select="maximum"/>&#160;</td></tr>
  </xsl:template>  
  
</xsl:stylesheet>
