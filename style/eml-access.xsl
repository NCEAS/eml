<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-access.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2002-05-01 01:02:19 $'
  * '$Revision: 1.3 $'
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
<xsl:import href="eml-settings.xsl" />
<xsl:import href="eml-identifier.xsl"/>
<xsl:import href="eml-coverage.xsl" />

  <xsl:output method="html" encoding="iso-8859-1"/>
   
  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" 
              href="{$stylePath}/{$qformat}.css" />
      </head>
      <body>
        <center>
          <h1>Access Control Rules</h1>
          <h3>Ecological Metadata Language</h3>
        </center>

        <table class="tabledefault" width="100%"><!-- width needed for NN4 - doesn't recognize width in css -->
        <xsl:apply-templates select="acl/identifier" mode="resource"/>
        <xsl:apply-templates select="acl"/>
        <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Rules:</xsl:text></td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">&#160;</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;</td>
        </tr>
        <xsl:if test="normalize-space(acl/@order)='allowFirst' and (//allow)">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            ALLOW:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:call-template name="allow_deny">
                <xsl:with-param name="permission" select="'allow'"/>
            </xsl:call-template></td>
        </tr>
        </xsl:if>
        <xsl:if test="(//deny)">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">&#160;</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;</td>
        </tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            DENY:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:call-template name="allow_deny">
                <xsl:with-param name="permission" select="'deny'"/>
            </xsl:call-template></td>
        </tr>
        </xsl:if>
        <xsl:if test="normalize-space(acl/@order)='denyFirst' and (//allow)">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            ALLOW:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:call-template name="allow_deny">
                <xsl:with-param name="permission" select="'allow'"/>
            </xsl:call-template></td>
        </tr>
        </xsl:if>
        </table>
      </body>
    </html>
  </xsl:template>


  <xsl:template name="allow_deny">
   <xsl:param name="permission"/>
   <table width="100%">
   <xsl:choose>
       <xsl:when test="$permission='allow'">
           <xsl:for-each select="//allow">
              <tr><td width="{$secondColIndent}" align="right">
                <xsl:text>[ </xsl:text><xsl:value-of select="./permission"/><xsl:text> ]:&#160;&#160;&#160;</xsl:text></td>
              <td><xsl:value-of select="./principal"/></td></tr>
           </xsl:for-each>
       </xsl:when>
       <xsl:otherwise>
           <xsl:for-each select="//deny">
              <tr><td width="{$secondColIndent}" align="right">
                <xsl:text>[ </xsl:text><xsl:value-of select="./permission"/><xsl:text> ]:&#160;&#160;&#160;</xsl:text></td>
              <td><xsl:value-of select="./principal"/></td></tr>
           </xsl:for-each>
       </xsl:otherwise>
   </xsl:choose>
   </table>
   </xsl:template>
    
  <xsl:template match="acl">
        <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Access Control:</xsl:text></td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Auth System:</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="./@authSystem"/></td>
        </tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Order:</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="./@order"/></td>
        </tr>
  </xsl:template>
</xsl:stylesheet>
