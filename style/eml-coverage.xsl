<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-coverage.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2002-05-01 01:02:19 $'
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
  * convert an XML file that is valid with respect to the eml-variable.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format 
  * suitable for rendering with modern web browsers.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:import href="eml-settings.xsl"/>

    <xsl:output method="html" encoding="iso-8859-1"/>

<!-- ******************** N O T E S ********************** 
     **   1) Many of the element names in the DTD do not match     
     **   element names in Metacat.  This XSL contains a 
     **   mixture of the two (I have used metacat-comatible
     **   element names where I was able to make metacat test 
     **   files; where this was not possible, I left the 
     **   other (dtd) names.  Need to discover which is most 
     **   current - metacat version or eml dtd version?????
     **
     **   2) output for taxonomic classification is not finished       
     **   3) output for taxonomic system is not finished           
     **               SEE END OF FILE                      
     ***************************************************** -->

  <xsl:template match="geographicCov" mode="resource"/>
 
  <xsl:template match="geographicCov">
    <tr class="{$subHeaderStyle}"><td colspan="2">
      <xsl:text>Geographic Coverage:</xsl:text></td></tr>  
      <xsl:apply-templates select="./descgeog"/>
      <xsl:apply-templates select="./bounding"/>
      <xsl:for-each select="./dsgpoly">
         <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Dataset G-Polygon:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:apply-templates select="./dsgpolyo"/></td></tr>
         <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
             &#160;</td><td width="{$secondColWidth}" class="{$secondColStyle}">
         <xsl:for-each select="./dsgpolyx">
             <xsl:apply-templates select="."/>
         </xsl:for-each></td></tr>
      </xsl:for-each>
  </xsl:template>

  <xsl:template match="descgeog">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Geographic Description:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../descgeog"/></td></tr>
  </xsl:template>
  
  <xsl:template match="bounding">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Bounding Coordinates:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="./westbc"/></td></tr>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        &#160;</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="./eastbc"/></td></tr>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        &#160;</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="./northbc"/></td></tr>        
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        &#160;</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="./southbc"/></td></tr>
        <xsl:apply-templates select="./boundalt"/>
  </xsl:template>
  
  <xsl:template match="westbc">
    <xsl:text>West: &#160;</xsl:text>
    <xsl:value-of select="."/>&#160; degrees
  </xsl:template>

  <xsl:template match="eastbc">
    <xsl:text>East: &#160;</xsl:text>
    <xsl:value-of select="."/>&#160; degrees
  </xsl:template>

  <xsl:template match="northbc">
    <xsl:text>North: &#160;</xsl:text>
    <xsl:value-of select="."/>&#160; degrees
  </xsl:template>

  <xsl:template match="southbc">
    <xsl:text>South: &#160;</xsl:text>
    <xsl:value-of select="."/>&#160; degrees
  </xsl:template>

  
  <xsl:template match="boundalt">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Bounding Altitudes:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="altmin"/></td></tr>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        &#160;</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="almax"/></td></tr>        
  </xsl:template>
  
  <xsl:template match="altmin">
    <xsl:text>Minimum: &#160;</xsl:text>
    <xsl:value-of select="."/> &#160;<xsl:value-of select="../altunits"/>
  </xsl:template>  
  
  <xsl:template match="almax">
    <xsl:text>Maximum: &#160;</xsl:text>
    <xsl:value-of select="."/> &#160;<xsl:value-of select="../altunits"/>
  </xsl:template> 
  
  <xsl:template match="dsgpolyo">
    <xsl:text>Outer Ring: &#160;</xsl:text>
    <xsl:for-each select="gringpoint">
       <xsl:apply-templates select="."/>    
    </xsl:for-each>
    <xsl:apply-templates select="gring"/>
  </xsl:template>
  
  <xsl:template match="dsgpolyx">
    <xsl:text>Exclusion Ring: &#160;</xsl:text>
    <xsl:for-each select="gringpoint">
       <xsl:apply-templates select="."/>    
    </xsl:for-each>
    <xsl:apply-templates select="gring"/>
  </xsl:template>  
  
  <xsl:template match="gring">
    <xsl:text>(GRing) &#160;</xsl:text>
    <xsl:value-of select="."/>
  </xsl:template>
  
  <xsl:template match="gringpoint">
    <xsl:text>Latitude: </xsl:text>
    <xsl:value-of select="gringlatitude"/>, 
    <xsl:text>Longitude: </xsl:text>
    <xsl:value-of select="gringlongitude"/><br/>
  </xsl:template>
  
  <xsl:template match="temporalCov" mode="resource"/>
  <xsl:template match="temporalCov">
     <tr class="{$subHeaderStyle}"><td colspan="2">
      <xsl:text>Temporal Coverage:</xsl:text></td></tr>
      <xsl:apply-templates select="singleDateTime"/>
      <xsl:apply-templates select="multipleDatesTimes"/>
      <xsl:apply-templates select="rngdates"/>
  </xsl:template>

  <xsl:template match="rngdates">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Date Range:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="begdate"/>
        <xsl:text> </xsl:text><xsl:apply-templates select="beginningTime"/>
        <xsl:text> </xsl:text><xsl:apply-templates select="beginningGeologicAge"/>
        <xsl:text>&#160; to &#160;</xsl:text>
        <xsl:apply-templates select="enddate"/>
        <xsl:text> </xsl:text><xsl:apply-templates select="endingTime"/>
        <xsl:text> </xsl:text><xsl:apply-templates select="endingGeologicAge"/>
        </td></tr>
  </xsl:template>

  <xsl:template match="singleDateTime">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Date:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="calendarDate"/>
        <xsl:text> </xsl:text><xsl:apply-templates select="time"/>
        <xsl:text> </xsl:text><xsl:apply-templates select="geologicAge"/>
        </td></tr>
  </xsl:template>

  <xsl:template match="multipleDatesTimes">
    <xsl:for-each select="./singleDateTime">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template match="calendarDate">  
      <xsl:value-of select="."/>
  </xsl:template> 
  
  <xsl:template match="time">  
      <xsl:value-of select="."/>
  </xsl:template> 
  
  <xsl:template match="begdate">  
      <xsl:value-of select="."/>
  </xsl:template> 
  
  <xsl:template match="beginningTime">  
      <xsl:value-of select="."/>
  </xsl:template> 
  
  <xsl:template match="enddate">  
      <xsl:value-of select="."/>
  </xsl:template> 
  
  <xsl:template match="endingTime">  
      <xsl:value-of select="."/>
  </xsl:template> 
  
  <xsl:template match="beginningGeologicAge">  
      <xsl:apply-templates select="geologicAge"/>
  </xsl:template> 
  
  <xsl:template match="endingGeologicAge">  
      <xsl:apply-templates select="geologicAge"/>
  </xsl:template> 

  <xsl:template match="geologicAge">
      <br /><xsl:text>GEOLOGIC AGE:</xsl:text><br /><xsl:text>timescale: </xsl:text>
      <xsl:value-of select="geologicTimeScale"/>
      <xsl:text>;</xsl:text><br /><xsl:text>age estimate: </xsl:text>
      <xsl:value-of select="geologicAgeEstimate"/>
      <xsl:text>;</xsl:text><br /><xsl:text>age uncertainty: </xsl:text>
      <xsl:value-of select="geologicAgeUncertainty"/>
      <xsl:text>;</xsl:text><br /><xsl:text>age explanation: </xsl:text>
      <xsl:value-of select="geologicAgeExplanation"/>
      <xsl:text>;</xsl:text><br /><xsl:text>citation: </xsl:text>
      <xsl:value-of select="geologicCitation"/>
  </xsl:template> 
  
  <xsl:template match="taxonomicCov" mode="resource"/>
  <xsl:template match="taxonomicCov">
     <tr class="{$subHeaderStyle}"><td colspan="2">
      <xsl:text>Taxonomic Coverage:</xsl:text></td></tr> 
      <xsl:for-each select="keywordsTaxon">
      <xsl:apply-templates select="."/>
      </xsl:for-each>
      <xsl:apply-templates select="taxonomicSystem"/>
      <xsl:apply-templates select="generalTaxonomicCoverage"/>
      <xsl:apply-templates select="taxonomicClassification"/>
  </xsl:template>

  <xsl:template match="keywordsTaxon">        
    <xsl:if test="./keywordThesaurus and normalize-space(./taxonomicKeywordThesaurus)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Taxonomic Keywords:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        Thesaurus: &#160;<xsl:value-of select="./taxonomicKeywordThesaurus"/></td></tr>
    </xsl:if>
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>&#160;</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
      <xsl:if test="normalize-space(taxomomicKeywords)!=''">
      <ul>
        <xsl:for-each select="taxonomicKeywords">
          <li><xsl:value-of select="."/></li>
        </xsl:for-each>
      </ul>
      </xsl:if>
    </td></tr>
  </xsl:template>    
    
  <xsl:template match="generalTaxonomicCoverage">
    <xsl:for-each select="."/>
  </xsl:template>

  <!-- output for taxonomic system is not finished -->
  <xsl:template match="taxonomicSystem">
    <xsl:apply-templates select="."/>
  </xsl:template>

  <!-- output for taxonomic classification is not finished -->
  <xsl:template match="taxonomicClassification">
    <xsl:apply-templates select="."/>
  </xsl:template>

</xsl:stylesheet>
