<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-resource-2.0.0beta6.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2003-12-06 01:43:32 $'
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
  * convert an XML file that is valid with respect to the eml-variable.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format 
  * suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:import href="eml-party-2.0.0beta6.xsl"/>
    <xsl:import href="eml-identifier-2.0.0beta6.xsl"/>
    
    <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  

  <xsl:template match="shortName"/>
  <xsl:template match="shortName" mode="resource">
      <xsl:if test="normalize-space(../shortName)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Short Name:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../shortName"/></td></tr>
      </xsl:if>
  </xsl:template>

  <xsl:template match="title"/>
  <xsl:template match="title" mode="resource">
      <xsl:if test="normalize-space(../title)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Title:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../title"/></td></tr>
      </xsl:if>
  </xsl:template>
      
  <xsl:template match="pubDate"/>
  <xsl:template match="pubDate" mode="resource">
      <xsl:if test="normalize-space(../pubDate)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Publication Date:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../pubDate"/></td></tr>
      </xsl:if>
  </xsl:template>     
      
      
  <xsl:template match="pubPlace"/>
  <xsl:template match="pubPlace" mode="resource">
      <xsl:if test="normalize-space(../pubPlace)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Publication Place:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../pubPlace"/></td></tr>
      </xsl:if>
  </xsl:template> 
      
  <xsl:template match="series"/>
  <xsl:template match="series" mode="resource">
      <xsl:if test="normalize-space(../series)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Series:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../series"/></td></tr>
      </xsl:if>
  </xsl:template>   

  <xsl:template match="originator"/> 
  <xsl:template match="originator[1]" mode="resource">
    <tr><td colspan="2" class="{$subHeaderStyle}">
      <xsl:text>Data Set Owner(s):</xsl:text></td></tr>
      <xsl:apply-templates select="." mode="party"/>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">&#160;</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;</td></tr>
  </xsl:template>

  <xsl:template match="originator" mode="resource">
      <xsl:apply-templates select="." mode="party"/>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">&#160;</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;</td></tr>
  </xsl:template>

   <xsl:template match="abstract"/>   
   <xsl:template match="abstract" mode="resource">
   <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Abstract:</xsl:text></td></tr>
        <xsl:for-each select="./paragraph">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>&#160;</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
        </xsl:for-each>
  </xsl:template>
 
  <xsl:template match="keywordSet"/>
  <xsl:template match="keywordSet[1]" mode="resource">
        <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Keywords:</xsl:text></td></tr>
        <xsl:call-template name="renderKeywordSet"/>
  </xsl:template> 

  <xsl:template match="keywordSet" mode="resource">
        <xsl:call-template name="renderKeywordSet"/>
  </xsl:template> 
       
  <xsl:template name="renderKeywordSet">        
    <xsl:for-each select="keywordThesaurus">
    <xsl:if test="normalize-space(.)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Thesaurus:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
    </xsl:if>
    </xsl:for-each>
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>&#160;</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
      <xsl:if test="normalize-space(keyword)!=''">
      <ul>
        <xsl:for-each select="keyword">
          <li><xsl:value-of select="."/> 
          <xsl:if test="./@keywordType and normalize-space(./@keywordType)!=''">
            (<xsl:value-of select="./@keywordType"/>)
          </xsl:if>
          </li>
        </xsl:for-each>
      </ul>
      </xsl:if>
    </td></tr>
  </xsl:template>  

   <xsl:template match="additionalInfo"/>   
   <xsl:template match="additionalInfo" mode="resource">
   <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Additional Information:</xsl:text></td></tr>
        <xsl:for-each select="./paragraph">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            <xsl:text>&#160;</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="."/></td></tr>
        </xsl:for-each>
  </xsl:template>

   <xsl:template match="rights"/>   
   <xsl:template match="rights" mode="resource">
   <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>License and Usage Rights:</xsl:text></td></tr>
        <xsl:for-each select="./paragraph">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            <xsl:text>&#160;</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="."/></td></tr>
        </xsl:for-each>
  </xsl:template>


  <xsl:template match="offlineMedium"/>
  <xsl:template match="offlineMedium" mode="resource">
    <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Offline Distribution information:</xsl:text></td></tr>
    <xsl:if test="(medName) and normalize-space(medName)!=''">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><xsl:text>Medium:</xsl:text></td>
    <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="medName"/></td></tr>
    </xsl:if>
    <xsl:if test="(medName) and normalize-space(medName)!=''">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><xsl:text>Medium Density:</xsl:text></td>
    <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="medDensity"/>
    <xsl:if test="(medDensityUnits) and normalize-space(medDensityUnits)!=''">
    <xsl:text> (</xsl:text><xsl:value-of select="medDensityUnits"/><xsl:text>)</xsl:text>
    </xsl:if>
    </td></tr>
    </xsl:if>
    <xsl:if test="(medVol) and normalize-space(medVol)!=''">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><xsl:text>Volume:</xsl:text></td>
    <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="medVol"/></td></tr>
    </xsl:if>
    <xsl:if test="(medFormat) and normalize-space(medFormat)!=''">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><xsl:text>Format:</xsl:text></td>
    <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="medFormat"/></td></tr>
    </xsl:if>
    <xsl:if test="(medNote) and normalize-space(medNote)!=''">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><xsl:text>Notes:</xsl:text></td>
    <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="medNote"/></td></tr>
    </xsl:if>
  </xsl:template>

  <xsl:template match="onlineURL"/>
  <xsl:template match="onlineURL" mode="resource">
    <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Online Distribution information:</xsl:text></td></tr>
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>&#160;</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}"><a>
       <xsl:attribute name="href"><xsl:value-of select="."/></xsl:attribute>
       <xsl:attribute name="target">_blank</xsl:attribute>
       <xsl:value-of select="."/>
    </a></td></tr>
  </xsl:template>

  
  
  <xsl:template match="triple"/>
  <xsl:template match="triple[1]" mode="resource">
    <tr><td colspan="2" class="{$subHeaderStyle}">
    <xsl:text>Related Metadata and Data Files:</xsl:text></td></tr>
    <xsl:call-template name="renderTriple"/>
  </xsl:template> 

  <xsl:template match="triple" mode="resource">
        <xsl:call-template name="renderTriple"/>
  </xsl:template> 
       
  <xsl:template name="renderTriple">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
      <xsl:text>&#160;</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
      <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI" /><xsl:value-of select="./subject"/></xsl:attribute><xsl:value-of select="./subject"/></a>
         <xsl:text> &#160;&#160;</xsl:text>
         <xsl:value-of select="./relationship"/>
         <xsl:text> &#160;&#160;</xsl:text>
      <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI" /><xsl:value-of select="./object"/></xsl:attribute><xsl:value-of select="./object"/></a>
    </td></tr>
    </xsl:template>
  
</xsl:stylesheet>
