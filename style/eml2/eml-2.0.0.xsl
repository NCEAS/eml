<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-2.0.0.xsl,v $'
  *      Authors: Matt Jones
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: berkley $'
  *     '$Date: 2003-06-03 21:41:35 $'
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
  <xsl:import href="eml-settings-2.0.0.xsl"/>
  <xsl:import href="eml-identifier-2.0.0.xsl"/>
  <xsl:import href="eml-dataset-2.0.0.xsl"/>

  <xsl:output method="html" encoding="iso-8859-1"/>
  <!-- global variables to store id node set in case to be referenced-->
  <xsl:variable name="ids" select="//*[@id!='']"/>
  
  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" 
              href="{$stylePath}/{$qformat}.css" />
      </head>
      <body>
        <xsl:apply-templates select="*[local-name()='eml']"/>
      </body>
    </html>
   </xsl:template>
   
   <xsl:template match="*[local-name()='eml']">
       <xsl:apply-templates select="dataset"/>
       <xsl:apply-templates select="citation"/>
       <xsl:apply-templates select="software"/>
       <xsl:apply-templates select="protocol"/>
   </xsl:template>
   
   <xsl:template match="dataset">
        <center>
           <xsl:if test="$displaymodule='dataset'">
            <h1>Data set description</h1>
            <h3>Ecological Metadata Language</h3>
           </xsl:if>
        </center>
        <table class="tabledefault" width="100%">
          <xsl:if test="$displaymodule='dataset'">
            <tr>
              <td width="100">
                <xsl:call-template name="identifier">
                  <xsl:with-param name="packageID" select="../@packageId"/>
                  <xsl:with-param name="system" select="../@system"/>
                </xsl:call-template>
                </td>
            </tr>
            <tr>
              <td width="100%">
                <xsl:apply-templates select="." mode="dataset"/>
              </td>
            </tr>
          </xsl:if>
        </table>
   </xsl:template>
   
   <xsl:template match="citation">
        <center>
          <h1>Citation description</h1>
          <h3>Ecological Metadata Language</h3>
        </center>
        <xsl:call-template name="identifier">
              <xsl:with-param name="packageID" select="../@packageId"/>
              <xsl:with-param name="system" select="../@system"/>
        </xsl:call-template>
   </xsl:template>
   
   <xsl:template match="software">
        <center>
          <h1>Software description</h1>
          <h3>Ecological Metadata Language</h3>
        </center>
        <xsl:call-template name="identifier">
              <xsl:with-param name="packageID" select="../@packageId"/>
              <xsl:with-param name="system" select="../@system"/>
        </xsl:call-template>
   </xsl:template>
   
   <xsl:template match="protocol">
       <center>
          <h1>Protocol description</h1>
          <h3>Ecological Metadata Language</h3>
        </center>
        <xsl:call-template name="identifier">
              <xsl:with-param name="packageID" select="../@packageId"/>
              <xsl:with-param name="system" select="../@system"/>
        </xsl:call-template>
   </xsl:template>
    
</xsl:stylesheet>
