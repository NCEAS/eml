<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-access-2.0.0.xsl,v $'
  *      Authors: Matt Jones
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2003-12-06 01:43:31 $'
  * '$Revision: 1.5 $'
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


  <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  
  <xsl:template name="access">
    <xsl:param name="accessfirstColStyle"/>
    <xsl:param name="accesssubHeaderStyle"/>
    <table xsl:use-attribute-sets="cellspacing" class="{$tabledefaultStyle}" width="100%">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="accessCommon">
             <xsl:with-param name="accessfirstColStyle" select="$accessfirstColStyle"/>
             <xsl:with-param name="accesssubHeaderStyle" select="$accesssubHeaderStyle"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="accessCommon">
             <xsl:with-param name="accessfirstColStyle" select="$accessfirstColStyle"/>
             <xsl:with-param name="accesssubHeaderStyle" select="$accesssubHeaderStyle"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </table>

  </xsl:template>
  <xsl:template name="accessCommon">
     <xsl:param name="accessfirstColStyle" />
     <xsl:param name="accesssubHeaderStyle"/>
        <xsl:call-template name="accesssystem">
           <xsl:with-param name="accessfirstColStyle" select="$accessfirstColStyle"/>
           <xsl:with-param name="accesssubHeaderStyle" select="$accesssubHeaderStyle"/>
        </xsl:call-template>
        <tr><td class="{$accesssubHeaderStyle}" colspan="2">
        <xsl:text>Access Rules:</xsl:text></td></tr>
        <xsl:if test="normalize-space(./@order)='allowFirst' and (allow)">
            <xsl:call-template name="allow_deny">
                <xsl:with-param name="permission" select="'allow'"/>
                <xsl:with-param name="accessfirstColStyle" select="$accessfirstColStyle"/>
             </xsl:call-template>
        </xsl:if>
        <xsl:if test="(deny)">
           <xsl:call-template name="allow_deny">
                <xsl:with-param name="permission" select="'deny'"/>
                <xsl:with-param name="accessfirstColStyle" select="$accessfirstColStyle"/>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="normalize-space(acl/@order)='denyFirst' and (allow)">
            <xsl:call-template name="allow_deny">
                <xsl:with-param name="permission" select="'allow'"/>
                <xsl:with-param name="accessfirstColStyle" select="$accessfirstColStyle"/>
            </xsl:call-template>
        </xsl:if>


  </xsl:template>


  <xsl:template name="allow_deny">
   <xsl:param name="permission"/>
   <xsl:param name="accessfirstColStyle" />
   <xsl:param name="accesssubHeaderSytle"/>
   <xsl:choose>
       <xsl:when test="$permission='allow'">
           <xsl:for-each select="allow">
           <tr><td width="{$firstColWidth}" class="{$accessfirstColStyle}" valign="top">
            ALLOW:</td><td width="{$secondColWidth}">
                    <table xsl:use-attribute-sets="cellspacing" class="{$tabledefaultStyle}" width="100%">
                        <tr><td width="{$firstColWidth}" class="{$accessfirstColStyle}">
                        <xsl:for-each select="./permission">
                            <xsl:text>[</xsl:text><xsl:value-of select="."/><xsl:text>] </xsl:text>
                        </xsl:for-each></td>
                        <td class="{$accessfirstColStyle}" width="{$secondColWidth}">
                        <xsl:for-each select="principal">
                            <xsl:value-of select="."/><br/>
                        </xsl:for-each>
                        </td></tr>
                    </table></td></tr>
          </xsl:for-each>
       </xsl:when>
       <xsl:otherwise>
           <xsl:for-each select="deny">
        <tr><td width="{$firstColWidth}" class="{$accessfirstColStyle}" valign="top">
            DENY:</td><td width="{$secondColWidth}">
           <table xsl:use-attribute-sets="cellspacing" class="{$tabledefaultStyle}" width="100%">
              <tr><td width="{$firstColWidth}" class="{$accessfirstColStyle}">
                <xsl:for-each select="permission">
                    <xsl:text>[</xsl:text><xsl:value-of select="."/><xsl:text>] </xsl:text>
                </xsl:for-each></td>
                <td class="{$accessfirstColStyle}" width="{$secondColWidth}">
                <xsl:for-each select="principal">
                    <xsl:value-of select="."/><br/>
                </xsl:for-each>
                </td></tr>
              </table></td></tr>
        </xsl:for-each>
       </xsl:otherwise>
   </xsl:choose>

   </xsl:template>

  <xsl:template name="accesssystem">
       <xsl:param name="accessfirstColStyle" />
       <xsl:param name="accesssubHeaderStyle"/>
        <tr><td class="{$accesssubHeaderStyle}" colspan="2">
        <xsl:text>Access Control:</xsl:text></td></tr>
        <tr><td width="{$firstColWidth}" class="{$accessfirstColStyle}">Auth System:</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="./@authSystem"/></td>
        </tr>
        <tr><td width="{$firstColWidth}" class="{$accessfirstColStyle}">Order:</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="./@order"/></td>
        </tr>
  </xsl:template>

</xsl:stylesheet>
