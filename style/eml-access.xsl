<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-access.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: berkley $'
  *     '$Date: 2002-04-22 16:04:27 $'
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
  * convert an XML file that is valid with respect to the eml-dataset.dtd
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
          <h1>Access Control Rules</h1>
          <h3>Ecological Metadata Language</h3>
        </center>

        <xsl:apply-templates select="acl"/>
        <xsl:apply-templates select="acl/identifier"/>

        <table width="100%">
        <tr>
        <td width="25%" class="tableheadcontrast"><xsl:text>Allow/Deny</xsl:text></td>
        <td width="50%" class="tableheadcontrast"><xsl:text>Principal</xsl:text></td>
        <td width="25%" class="tableheadcontrast"><xsl:text>Permission</xsl:text></td>
        </tr>
       <tr>
        <td width="25%" class="roweven"><xsl:text>&#160;</xsl:text></td>
        <td width="50%" class="roweven"><xsl:text>&#160;</xsl:text></td>
        <td width="25%" class="roweven"><xsl:text>&#160;</xsl:text></td>
        </tr>
        
         <xsl:apply-templates select="acl" mode="allow"/>
         <xsl:apply-templates select="acl" mode="deny"/>
        </table>
      </body>
    </html>
  </xsl:template>


  <xsl:template match="acl" mode="allow">
       <xsl:for-each select="./allow">
          <tr valign="top">
              <xsl:attribute name="class">
                <xsl:choose>
                    <xsl:when test="position() mod 2 = 1">rowodd</xsl:when>
                    <xsl:when test="position() mod 2 = 0">roweven</xsl:when>
                </xsl:choose>
              </xsl:attribute>
              <td><xsl:text>ALLOW</xsl:text></td>
              <td><xsl:value-of select="./principal"/></td>
              <td><xsl:value-of select="./permission"/></td>
              </tr>
        </xsl:for-each>
       <xsl:if test="last() mod 2 = 1">
        <tr>
        <td width="25%" class="rowodd"><xsl:text>&#160;</xsl:text></td>
        <td width="50%" class="rowodd"><xsl:text>&#160;</xsl:text></td>
        <td width="25%" class="rowodd"><xsl:text>&#160;</xsl:text></td>
        </tr>
        </xsl:if>
        <xsl:if test="last() mod 2 = 0">
        <tr>
        <td width="25%" class="roweven"><xsl:text>&#160;</xsl:text></td>
        <td width="50%" class="roweven"><xsl:text>&#160;</xsl:text></td>
        <td width="25%" class="roweven"><xsl:text>&#160;</xsl:text></td>
        </tr>
        </xsl:if>   
  </xsl:template>
  
  <!--  NOTE: This needs to be changed to be mor elegant: currently repeats
        same code but with "deny" instead of "allow"... -->
        
    <xsl:template match="acl" mode="deny">
       <xsl:for-each select="./deny">
          <tr valign="top">
              <xsl:attribute name="class">
                <xsl:choose>
                    <xsl:when test="position() mod 2 = 1">roweven</xsl:when>
                    <xsl:when test="position() mod 2 = 0">rowodd</xsl:when>
                </xsl:choose>
              </xsl:attribute>
              <td><xsl:text>DENY</xsl:text></td>
              <td><xsl:value-of select="./principal"/></td>
              <td><xsl:value-of select="./permission"/></td>
              </tr>
        </xsl:for-each>
        <xsl:if test="last() mod 2 = 1">
        <tr>
        <td width="25%" class="rowodd"><xsl:text>&#160;</xsl:text></td>
        <td width="50%" class="rowodd"><xsl:text>&#160;</xsl:text></td>
        <td width="25%" class="rowodd"><xsl:text>&#160;</xsl:text></td>
        </tr>
        </xsl:if>
        <xsl:if test="last() mod 2 = 0">
        <tr>
        <td width="25%" class="roweven"><xsl:text>&#160;</xsl:text></td>
        <td width="50%" class="roweven"><xsl:text>&#160;</xsl:text></td>
        <td width="25%" class="roweven"><xsl:text>&#160;</xsl:text></td>
        </tr>
        </xsl:if>   
  </xsl:template>
  
  <xsl:template match="identifier">
    <table width="100%">
    <xsl:for-each select=".">
      <tr>
        <td class="tablehead" width="25%">
           <b><xsl:text>Metadata File ID:</xsl:text></b>
        </td>
        <td width="25%" class="tablehead">
            <xsl:value-of select="."/>
        </td>
        <td class="tablehead" width="25%">
           <b><xsl:text>Catalog System:</xsl:text></b></td>
        <td width="25%" class="tablehead">
            <xsl:value-of select="./@system"/><xsl:text>&#160;</xsl:text></td>
      </tr>
      </xsl:for-each>
      </table>
  </xsl:template>
  
  <xsl:template match="acl">
    <table width="100%">
      <tr>
        <td class="highlight" width="25%">
           <b><xsl:text>Auth System:</xsl:text></b>
        </td>
        <td width="75%">
            <xsl:value-of select="./@authSystem"/>
        </td>
      </tr>
      <tr>
        <td class="highlight" width="25%">
           <b><xsl:text>Order:</xsl:text></b>
        </td>
        <td width="75%">
            <xsl:value-of select="./@order"/>
        </td>
      </tr>
      <tr>
        <td width="25%"><xsl:text>&#160;</xsl:text></td>
        <td width="75%"><xsl:text>&#160;</xsl:text></td>
      </tr>
    </table>
  </xsl:template>
         
</xsl:stylesheet>
