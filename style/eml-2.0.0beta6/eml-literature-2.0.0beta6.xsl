<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-literature-2.0.0beta6.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2003-12-06 01:43:32 $'
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
  * convert an XML file that is valid with respect to the eml-variable.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format 
  * suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   
    <xsl:import href="eml-resource-2.0.0beta6.xsl"/>
    
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
          <h1>Literature (Citation) Description</h1>
          <h3>Ecological Metadata Language</h3>
        </center>
        <table class="tabledefault" width="100%"><!-- width needed for NN4 - doesn't recognize width in css -->
          <xsl:apply-templates select="./citation"/>
          </table>
        
        <script language="JavaScript">insertTemplateClosing();</script>
        
      </body>
    </html>
  </xsl:template>
    
    
	<xsl:template match="citation">
        <xsl:apply-templates select="." mode="resource"/>
        <tr><td colspan="2" class="{$subHeaderStyle}">Author(s):</td></tr>
        <xsl:for-each select="originator">
            <xsl:apply-templates select="." mode="party"/>
        </xsl:for-each>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            &#160;</td><td width="{$secondColWidth}" class="{$secondColStyle}">&#160;</td></tr>
        <xsl:if test="./ISBN and normalize-space(./ISBN)!=''">
          <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            ISBN:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="ISBN"/></td></tr>
        </xsl:if>
        <xsl:if test="./ISSN and normalize-space(./ISSN)!=''">
		  <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            ISSN:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="ISSN"/></td></tr>
        </xsl:if>
        <xsl:apply-templates select="./thesis"/>
  		<xsl:apply-templates select="./report"/>
		<xsl:apply-templates select="./manuscript"/>
   		<xsl:apply-templates select="./chapter"/>
   		<xsl:apply-templates select="./book"/>
		<xsl:apply-templates select="./article"/>
	</xsl:template>

    
    	<xsl:template match="article">
        <tr><td colspan="2" class="{$subHeaderStyle}"><xsl:text>ARTICLE:</xsl:text></td></tr>
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Journal:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="journal"/></td></tr>
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Volume:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="volume"/></td></tr>
        <xsl:if test="./issue and normalize-space(./issue)!=''">
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Issue:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="issue"/></td></tr>
        </xsl:if>
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Page Range:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="pageRange"/></td></tr>
        </xsl:template>    
    
    	<xsl:template match="book">
        <tr><td colspan="2" class="{$subHeaderStyle}"><xsl:text>BOOK:</xsl:text></td></tr>
        <xsl:if test="./edition and normalize-space(./edition)!=''">
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Edition:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="edition"/></td></tr>
        </xsl:if>
        <xsl:if test="./totalPages and normalize-space(./totalPages)!=''">
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Total Pages:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="totalPages"/></td></tr>
        </xsl:if>
        <xsl:if test="./totalFigures and normalize-space(./totalFigures)!=''">
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Total Figures:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="totalFigures"/></td></tr>
        </xsl:if>
        <xsl:if test="./totalTables and normalize-space(./totalTables)!=''">
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Total Tables:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="totalTables"/></td></tr>
        </xsl:if>
        <xsl:if test="./volume and normalize-space(./volume)!=''">
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Volume:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="volume"/></td></tr>
        </xsl:if>
        </xsl:template>

    	<xsl:template match="chapter">
        <tr><td colspan="2" class="{$subHeaderStyle}"><xsl:text>CHAPTER:</xsl:text></td></tr>
        <xsl:if test="./chapterNumber and normalize-space(./chapterNumber)!=''">
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Chapter Number:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="chapterNumber"/></td></tr>
        </xsl:if>
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Book Editor:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="bookEditor"/></td></tr>
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Book Title:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="bookTitle"/></td></tr>
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Publisher:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="publisher"/></td></tr>
        <xsl:if test="./pageRange and normalize-space(./pageRange)!=''">
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Page Range:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="pageRange"/></td></tr>
        </xsl:if>
        <xsl:if test="./edition and normalize-space(./edition)!=''">
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Edition:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="edition"/></td></tr>
        </xsl:if>
        <xsl:if test="./volume and normalize-space(./volume)!=''">
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Volume:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="volume"/></td></tr>
        </xsl:if>
        </xsl:template>

    
    <xsl:template match="manuscript">
        <tr><td colspan="2" class="{$subHeaderStyle}"><xsl:text>MANUSCRIPT:</xsl:text></td></tr>
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Location:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="location"/></td></tr>
        <xsl:for-each select="pubInfo">
          <xsl:apply-templates select="."/>
        </xsl:for-each>
    </xsl:template>

	<xsl:template match="thesis">
        <tr><td colspan="2" class="{$subHeaderStyle}"><xsl:text>THESIS:</xsl:text></td></tr>
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Degree:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="degree"/></td></tr>
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Degree Institution:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="degreeInstitution"/></td></tr>
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Total Pages:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="totalPages"/></td></tr>
        <xsl:for-each select="pubInfo">
          <xsl:apply-templates select="."/>
        </xsl:for-each>
       </xsl:template>

	<xsl:template match="report">
        <tr><td colspan="2" class="{$subHeaderStyle}"><xsl:text>REPORT:</xsl:text></td></tr>
        <xsl:for-each select="institution">
          <xsl:apply-templates select="."/>
        </xsl:for-each>
       <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Report Number:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="reportNumber"/></td></tr>
       </xsl:template>


      <xsl:template match="institution[1]">
        <tr><td colspan="2" class="{$subHeaderStyle}">
          <xsl:text>Institution:</xsl:text></td></tr>
          <xsl:apply-templates select="." mode="party"/>
          </xsl:template>
        
      <xsl:template match="institution">
          <xsl:apply-templates select="." mode="party"/>
      </xsl:template>       
       
       
      <xsl:template match="pubInfo[1]">
          <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
          <h4>Publication Info:</h4></td><td width="{$secondColWidth}" class="{$secondColStyle}">&#160;</td></tr>
          <xsl:apply-templates select="." mode="party"/>
          </xsl:template>
        
      <xsl:template match="pubInfo">
          <xsl:apply-templates select="." mode="party"/>
      </xsl:template>



<!-- these are elements we need to suppress (they are displayed by templates without a "mode=" parameter) -->

       <xsl:template match="ISBN" mode="resource"/>
       <xsl:template match="ISSN" mode="resource"/>
       <xsl:template match="thesis" mode="resource"/>
       <xsl:template match="report" mode="resource"/>       
       <xsl:template match="manuscript" mode="resource"/>
       <xsl:template match="chapter" mode="resource"/>
       <xsl:template match="book" mode="resource"/>
       <xsl:template match="article" mode="resource"/>
       <xsl:template match="originator" mode="resource"/>
       
    </xsl:stylesheet>
