<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-party-2.0.0beta6.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2002-06-17 20:00:33 $'
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

  <xsl:import href="eml-settings-2.0.0beta6.xsl"/>
 
  <xsl:output method="html" encoding="iso-8859-1"/>

     
<!-- *************************************************************************
  * NOTE: EACH TEMPLATE CREATES A TR CONTAINING 2 TD's.  YOU NEED TO PROVIDE 
  * YOUR OWN TABLE TAGS IN THE CALLING STYLESHEET. 
  * Can either use the templates below in "xsl:apply-templates" calls 
  * (but don't forget to specify "mode=" attribute), 
  * or can do an xsl:call-template; eg:  
  **************************************************************************** 
  *    <xsl:template match="whatever">
  *    <table width=100%>    .....TABLE DEFINITION IN CALLING STYLESHEET
  *
  *   .....CALL TO THIS TEMPLATE
  *        <xsl:call-template name="party"/>
  *
  *    </table>
  *    </xsl:template>
-->
  <xsl:template name="party">
      <xsl:apply-templates select="individualName" mode="party"/>
      <xsl:apply-templates select="organizationName" mode="party"/>
      <xsl:apply-templates select="positionName" mode="party"/>
      <xsl:apply-templates select="address" mode="party"/>
      <xsl:apply-templates select="phone" mode="party"/>
      <xsl:apply-templates select="electronicMailAddress" mode="party"/>
      <xsl:apply-templates select="onlineLink" mode="party"/>
      <xsl:apply-templates select="role" mode="party"/>
  </xsl:template>

  <!-- *********************************************************************** -->
  
  <xsl:template match="individualName"/>
  <xsl:template match="individualName" mode="party">
      <xsl:if test="../individualName and normalize-space(../individualName)!=''">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Individual:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <b>
           <xsl:value-of select="./salutation"/><xsl:text> </xsl:text>
           <xsl:value-of select="./givenName"/><xsl:text> </xsl:text>
           <xsl:value-of select="./surName"/>
        </b></td></tr>
      </xsl:if>
  </xsl:template>

  <xsl:template match="organizationName"/>
  <xsl:template match="organizationName" mode="party">
      <xsl:if test="../organizationName and normalize-space(../organizationName)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Organization:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <b><xsl:value-of select="../organizationName"/></b></td></tr>
      </xsl:if>
  </xsl:template>

  <xsl:template match="positionName"/>
  <xsl:template match="positionName" mode="party">
      <xsl:if test="../positionName and normalize-space(../positionName)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Position:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <b><xsl:value-of select="../positionName"/></b></td></tr>
      </xsl:if>
  </xsl:template>

  <xsl:template match="address"/>
  <xsl:template match="address" mode="party">
    <xsl:if test="normalize-space(.)!=''">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}" valign="top">
        Address:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
    <table width="100%">
    <xsl:for-each select="./deliveryPoint">
    <tr><td><xsl:value-of select="../deliveryPoint"/><xsl:text>, </xsl:text></td></tr>
    </xsl:for-each>
    <!-- only include comma if city exists... -->
    <xsl:if test="normalize-space(./city)!=''">
        <tr><td><xsl:value-of select="./city"/><xsl:text>, </xsl:text></td></tr>
    </xsl:if> 
    <xsl:if test="normalize-space(./administrativeArea)!='' or normalize-space(./postalCode)!=''">
        <tr><td><xsl:value-of select="./administrativeArea"/><xsl:text> </xsl:text><xsl:value-of select="./postalCode"/></td></tr>
    </xsl:if>
    <xsl:if test="normalize-space(./country)!=''">
      <tr><td><xsl:value-of select="./country"/></td></tr>
    </xsl:if>
    </table></td></tr>
    </xsl:if>
    </xsl:template>

  <xsl:template match="phone"/>
  <xsl:template match="phone" mode="party">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Phone:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
       <xsl:value-of select="."/>
       <xsl:if test="normalize-space(./@phonetype)!=''">
            <xsl:text> (</xsl:text><xsl:value-of select="./@phonetype"/><xsl:text>)</xsl:text>
       </xsl:if></td></tr>
  </xsl:template>

  <xsl:template match="electronicMailAddress"/> 
  <xsl:template match="electronicMailAddress" mode="party">
      <xsl:if test="../electronicMailAddress and normalize-space(../electronicMailAddress)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Email Address:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../electronicMailAddress"/></td></tr>
      </xsl:if>
  </xsl:template>  

  <xsl:template match="onlineLink"/>
  <xsl:template match="onlineLink" mode="party">
      <xsl:if test="../onlineLink and normalize-space(../onlineLink)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Web Address:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../onlineLink"/></td></tr>
      </xsl:if>
  </xsl:template>  
  
  <xsl:template match="role"/>
  <xsl:template match="role" mode="party">
      <xsl:if test="../role and normalize-space(../role)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Role:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../role"/></td></tr>
      </xsl:if>
  </xsl:template>
  
</xsl:stylesheet>
