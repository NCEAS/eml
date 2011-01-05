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

  <!-- This module is for party member and it is self contained-->

  <xsl:template name="party">
      <xsl:param name="partyfirstColStyle"/>
      <table class="{$tabledefaultStyle}">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:apply-templates mode="party">
             <xsl:with-param name="partyfirstColStyle" select="$partyfirstColStyle"/>
            </xsl:apply-templates>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates mode="party">
            <xsl:with-param name="partyfirstColStyle" select="$partyfirstColStyle"/>
          </xsl:apply-templates>
        </xsl:otherwise>
      </xsl:choose>
      </table>
  </xsl:template>

  <!-- *********************************************************************** -->


  <xsl:template match="individualName" mode="party">
      <xsl:param name="partyfirstColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
        <tr><td class="{$partyfirstColStyle}" >
            Individual:</td><td class="{$secondColStyle}" >
           <b><xsl:value-of select="./salutation"/><xsl:text> </xsl:text>
           <xsl:value-of select="./givenName"/><xsl:text> </xsl:text>
           <xsl:value-of select="./surName"/></b>
        </td></tr>
      </xsl:if>
  </xsl:template>


  <xsl:template match="organizationName" mode="party">
      <xsl:param name="partyfirstColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
        <tr><td class="{$partyfirstColStyle}" >
        Organization:</td><td class="{$secondColStyle}">
        <b><xsl:value-of select="."/></b>
        </td></tr>
      </xsl:if>
  </xsl:template>


  <xsl:template match="positionName" mode="party">
      <xsl:param name="partyfirstColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
      <tr><td class="{$partyfirstColStyle}">
        Position:</td><td class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
      </xsl:if>
  </xsl:template>


  <xsl:template match="address" mode="party">
    <xsl:param name="partyfirstColStyle"/>
    <xsl:if test="normalize-space(.)!=''">
      <xsl:call-template name="addressCommon">
         <xsl:with-param name="partyfirstColStyle" select="$partyfirstColStyle"/>
      </xsl:call-template>
    </xsl:if>
    </xsl:template>

   <!-- This template will be call by other place-->
   <xsl:template name="address">
      <xsl:param name="partyfirstColStyle"/>
      <table class="{$tablepartyStyle}">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="addressCommon">
             <xsl:with-param name="partyfirstColStyle" select="$partyfirstColStyle"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="addressCommon">
             <xsl:with-param name="partyfirstColStyle" select="$partyfirstColStyle"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
      </table>
  </xsl:template>

   <xsl:template name="addressCommon">
    <xsl:param name="partyfirstColStyle"/>
    <xsl:if test="normalize-space(.)!=''">
    <tr><td class="{$partyfirstColStyle}">
        Address:</td><td>
    <table class="{$tablepartyStyle}">
    <xsl:for-each select="deliveryPoint">
    <tr><td class="{$secondColStyle}"><xsl:value-of select="."/><xsl:text>, </xsl:text></td></tr>
    </xsl:for-each>
    <!-- only include comma if city exists... -->
    <tr><td class="{$secondColStyle}" >
    <xsl:if test="normalize-space(city)!=''">
        <xsl:value-of select="city"/><xsl:text>, </xsl:text>
    </xsl:if>
    <xsl:if test="normalize-space(administrativeArea)!='' or normalize-space(postalCode)!=''">
        <xsl:value-of select="administrativeArea"/><xsl:text> </xsl:text><xsl:value-of select="postalCode"/><xsl:text> </xsl:text>
    </xsl:if>
    <xsl:if test="normalize-space(country)!=''">
      <xsl:value-of select="country"/>
    </xsl:if></td></tr>
    </table></td></tr>
    </xsl:if>
   </xsl:template>

  <xsl:template match="phone" mode="party">
      <xsl:param name="partyfirstColStyle"/>
      <tr><td class="{$partyfirstColStyle}" >
             Phone:
          </td>
          <td>
            <table class="{$tablepartyStyle}">
              <tr><td class="{$secondColStyle}">
                     <xsl:value-of select="."/>
                     <xsl:if test="normalize-space(./@phonetype)!=''">
                       <xsl:text> (</xsl:text><xsl:value-of select="./@phonetype"/><xsl:text>)</xsl:text>
                     </xsl:if>
                   </td>
               </tr>
             </table>
          </td>
      </tr>
  </xsl:template>


  <xsl:template match="electronicMailAddress" mode="party">
      <xsl:param name="partyfirstColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
       <tr><td class="{$partyfirstColStyle}" >
            Email Address:
          </td>
          <td>
            <table class="{$tablepartyStyle}">
              <tr><td class="{$secondColStyle}">
                    <a><xsl:attribute name="href">mailto:<xsl:value-of select="."/></xsl:attribute><xsl:value-of select="./entityName"/>
                    <xsl:value-of select="."/></a>
                   </td>
              </tr>
            </table>
          </td>
        </tr>
      </xsl:if>
  </xsl:template>


  <xsl:template match="onlineUrl" mode="party">
      <xsl:param name="partyfirstColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
      <tr><td class="{$partyfirstColStyle}" >
            Web Address:
          </td>
          <td>
             <table class="{$tablepartyStyle}">
               <tr><td class="{$secondColStyle}">
                     <a><xsl:attribute name="href">http://<xsl:value-of select="."/></xsl:attribute><xsl:value-of select="./entityName"/>
                     <xsl:value-of select="."/></a>
                    </td>
               </tr>
             </table>
           </td>
        </tr>
      </xsl:if>
  </xsl:template>


  <xsl:template match="userId" mode="party">
      <xsl:param name="partyfirstColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
      <tr><td class="{$partyfirstColStyle}" >
        Id:</td><td class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
      </xsl:if>
  </xsl:template>
  <xsl:template match="text()" mode="party" />
</xsl:stylesheet>
