<?xml version="1.0"?>
<!--
  *  '$RCSfile$'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: cjones $'
  *     '$Date: 2006-11-17 13:37:07 -0800 (Fri, 17 Nov 2006) $'
  * '$Revision: 3094 $'
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

  <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  



  <xsl:template name="protocol">
    <xsl:param name="protocolfirstColStyle"/>
    <xsl:param name="protocolsubHeaderStyle"/>
    <table class="{$tabledefaultStyle}">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="protocolcommon">
              <xsl:with-param name="protocolfirstColStyle" select="$protocolfirstColStyle"/>
              <xsl:with-param name="protocolsubHeaderStyle" select="$protocolsubHeaderStyle"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:call-template name="protocolcommon">
              <xsl:with-param name="protocolfirstColStyle" select="$protocolfirstColStyle"/>
              <xsl:with-param name="protocolsubHeaderStyle" select="$protocolsubHeaderStyle"/>
           </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </table>
  </xsl:template>

   <xsl:template name="protocolcommon">
        <xsl:param name="protocolfirstColStyle"/>
        <xsl:param name="protocolsubHeaderStyle"/>
        <xsl:call-template name="resource">
           <xsl:with-param name="resfirstColStyle" select="$protocolfirstColStyle"/>
           <xsl:with-param name="ressubHeaderStyle" select="$protocolsubHeaderStyle"/>
           <xsl:with-param name="creator">Author(s):</xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="proceduralStep">
          <tr><td colspan="2" class="{$protocolsubHeaderStyle}">
              Step<xsl:text> </xsl:text><xsl:value-of select="position()"/>:
              </td>
          </tr>
          <xsl:call-template name="step">
              <xsl:with-param name="protocolfirstColStyle" select="$protocolfirstColStyle"/>
              <xsl:with-param name="protocolsubHeaderStyle" select="$protocolsubHeaderStyle"/>
          </xsl:call-template>
        </xsl:for-each>
        <xsl:call-template name="protocolAccess">
              <xsl:with-param name="protocolfirstColStyle" select="$protocolfirstColStyle"/>
              <xsl:with-param name="protocolsubHeaderStyle" select="$protocolsubHeaderStyle"/>
        </xsl:call-template>
  </xsl:template>

  <xsl:template name="step">
    <xsl:param name="protocolfirstColStyle"/>
    <xsl:param name="protocolsubHeaderStyle"/>
    <xsl:for-each select="description">
      <tr><td class="{$protocolfirstColStyle}">
          Description:
          </td>
          <td>
             <xsl:call-template name="text">
               <xsl:with-param name="textfirstColStyle" select="$protocolfirstColStyle"/>
             </xsl:call-template>
          </td>
      </tr>
     </xsl:for-each>
    <xsl:for-each select="citation">
      <tr><td class="{$protocolfirstColStyle}">
          Citation:
          </td>
          <td class="{$secondColStyle}">
           &#160;
          </td>
      </tr>
      <tr><td colspan="2">
          <xsl:call-template name="citation">
            <xsl:with-param name="citationfirstColStyle" select="$protocolfirstColStyle"/>
            <xsl:with-param name="citationsubHeaderStyle" select="$protocolsubHeaderStyle"/>
          </xsl:call-template>
          </td>
      </tr>
    </xsl:for-each>
     <xsl:for-each select="protocol">
      <tr><td class="{$protocolfirstColStyle}">
          Protocol:
          </td>
          <td class="{$secondColStyle}">
           &#160;
          </td>
      </tr>
      <tr><td colspan="2">
          <xsl:call-template name="protocol">
            <xsl:with-param name="protocolfirstColStyle" select="$protocolfirstColStyle"/>
            <xsl:with-param name="protocolsubHeaderStyle" select="$protocolsubHeaderStyle"/>
          </xsl:call-template>
          </td>
      </tr>
    </xsl:for-each>
    <xsl:for-each select="instrumentation">
        <tr><td class="{$protocolfirstColStyle}">
          Instrument(s):
          </td>
          <td class="{$secondColStyle}">
            <xsl:value-of select="."/>
          </td>
      </tr>
    </xsl:for-each>
    <xsl:for-each select="software">
     <tr><td colspan="2">
          <xsl:call-template name="software">
            <xsl:with-param name="softwarefirstColStyle" select="$protocolfirstColStyle"/>
            <xsl:with-param name="softwaresubHeaderStyle" select="$protocolsubHeaderStyle"/>
          </xsl:call-template>
          </td>
      </tr>
    </xsl:for-each>
    <xsl:for-each select="subStep">
      <tr><td class="{$protocolfirstColStyle}">
          Substep<xsl:text> </xsl:text><xsl:value-of select="position()"/>
          </td>
          <td class="{$secondColStyle}">
           &#160;
          </td>
      </tr>
      <xsl:call-template name="step">
          <xsl:with-param name="protocolfirstColStyle" select="$protocolfirstColStyle"/>
          <xsl:with-param name="protocolsubHeaderStyle" select="$protocolsubHeaderStyle"/>
      </xsl:call-template>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="protocolAccess">
    <xsl:param name="protocolfirstColStyle"/>
    <xsl:param name="protocolsubHeaderStyle"/>
    <xsl:for-each select="access">
      <tr><td colspan="2">
         <xsl:call-template name="access">
           <xsl:with-param name="accessfirstColStyle" select="$protocolfirstColStyle"/>
           <xsl:with-param name="accesssubHeaderStyle" select="$protocolsubHeaderStyle"/>
         </xsl:call-template>
         </td>
       </tr>
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>
