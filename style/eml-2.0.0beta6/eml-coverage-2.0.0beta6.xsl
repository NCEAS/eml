<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-coverage-2.0.0beta6.xsl,v $'
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
    <xsl:import href="eml-literature-2.0.0beta6.xsl"/>
        
    <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  

<!-- 
     *********************** N O T E S ************************* 
     **
     **   1) Many of the element names in the DTD will need 
     **      to be changed to comply with later versions of eml
     **         
     ***********************************************************
-->

     
<!-- ********************************************************************* -->
<!-- **************  G E O G R A P H I C   C O V E R A G E  ************** -->
<!-- ********************************************************************* -->
     
  <xsl:template match="geographicCov" mode="resource"/>
 
  <xsl:template match="geographicCov">
    <tr><td colspan="2" class="{$subHeaderStyle}">
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

<!-- ********************************************************************* -->
<!-- ****************  T E M P O R A L   C O V E R A G E  **************** -->
<!-- ********************************************************************* -->

  
  <xsl:template match="temporalCov" mode="resource"/>
  <xsl:template match="temporalCov">
     <tr><td colspan="2" class="{$subHeaderStyle}">
      <xsl:text>Temporal Coverage:</xsl:text></td></tr>
      <xsl:apply-templates select="sngdate"/>
      <xsl:apply-templates select="mdattim"/>
      <xsl:apply-templates select="rngdates"/>
  </xsl:template>

  <xsl:template match="sngdate">
    <xsl:if test="./caldate and normalize-space(./caldate)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Calendar Date:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="caldate"/>
        <xsl:if test="./time and normalize-space(./time)!=''">
          <xsl:text>&#160; at &#160;</xsl:text><xsl:apply-templates select="time"/>
        </xsl:if></td></tr>
    </xsl:if>
    <xsl:if test="./geolage">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Geologic Age:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:apply-templates select="geolage"/></td></tr>
    </xsl:if>
  </xsl:template>

  <xsl:template match="mdattim">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        MULTPLE DATES</td><td width="{$secondColWidth}" class="{$secondColStyle}">&#160;</td></tr> 
    <xsl:for-each select="./sngdate">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template match="rngdates">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        RANGE OF DATES</td><td width="{$secondColWidth}" class="{$secondColStyle}">&#160;</td></tr> 
        
        <xsl:if test="(./begdate and normalize-space(./begdate)!='') or (./enddate and normalize-space(./enddate)!='')">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
                Begin:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
                <xsl:value-of select="begdate"/>
                <xsl:if test="./begtime and normalize-space(./begtime)!=''">
                  <xsl:text>&#160; at &#160;</xsl:text><xsl:value-of select="begtime"/>
                </xsl:if>
            </td></tr>
        
        
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
                End:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
                <xsl:value-of select="enddate"/>
                <xsl:if test="./endtime and normalize-space(./endtime)!=''">
                  <xsl:text>&#160; at &#160;</xsl:text><xsl:value-of select="endtime"/>
                </xsl:if>
            </td></tr>
       </xsl:if>
       
        <xsl:if test="(./beggeol) or (./endgeol)">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Begin:<br />(Geologic Age)</td><td width="{$secondColWidth}" class="{$secondColStyle}">
                <xsl:apply-templates select="beggeol"/></td></tr>

            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            End:<br />(Geologic Age)</td><td width="{$secondColWidth}" class="{$secondColStyle}">
                <xsl:apply-templates select="endgeol"/></td></tr>
        </xsl:if>
        
  </xsl:template>

  
  <xsl:template match="beggeol">  
      <xsl:apply-templates select="geolage"/>
  </xsl:template> 
  
  <xsl:template match="endgeol">  
      <xsl:apply-templates select="geolage"/>
  </xsl:template> 

  <xsl:template match="geolage">
    <table width="100%">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            timescale:</td><td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="geolscal"/></td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            age estimate:</td><td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="geolest"/></td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            age uncertainty:</td><td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="geolun"/></td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            age explanation:</td><td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="geolexpl"/></td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            citation:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <table width="100%"><xsl:apply-templates select="geolcit"/></table>
        </td></tr>
    </table>
  </xsl:template> 

  <xsl:template match="geolcit">  
      <xsl:apply-templates select="./*"/>
  </xsl:template> 

<!-- ********************************************************************* -->
<!-- ***************  T A X O N O M I C   C O V E R A G E  *************** -->
<!-- ********************************************************************* -->

  <xsl:template match="taxonomicCov" mode="resource"/>
  <xsl:template match="taxonomicCov">
     <tr><td colspan="2" class="{$subHeaderStyle}">
      <xsl:text>Taxonomic Coverage:</xsl:text></td></tr>
      <xsl:for-each select="keywtax">
          <xsl:apply-templates select="."/>
      </xsl:for-each>
      <xsl:apply-templates select="taxonsys"/>
      <xsl:apply-templates select="taxongen"/>
      <xsl:for-each select="taxoncl">
          <xsl:apply-templates select="."/>
      </xsl:for-each>
  </xsl:template>

  <xsl:template match="keywtax">
    <xsl:if test="./taxonkt and normalize-space(./taxonkt)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Taxonomic Keywords:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        Thesaurus: &#160;<xsl:value-of select="./taxonkt"/></td></tr>
    </xsl:if>
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>&#160;</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
      <xsl:if test="normalize-space(taxonkey)!=''">
      <ul>
        <xsl:for-each select="taxonkey">
          <li><xsl:value-of select="."/></li>
        </xsl:for-each>
      </ul>
      </xsl:if>
    </td></tr>
  </xsl:template>

  <xsl:template match="taxongen">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>General:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <!-- output for taxonomic system is not finished -->
  <xsl:template match="taxonsys">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Taxonomic System:</xsl:text></td><td width="{$secondColWidth}" class="{$firstColStyle}">&#160;</td></tr>
      <xsl:apply-templates select="./*"/>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>&#160;</xsl:text></td><td width="{$secondColWidth}" class="{$firstColStyle}">&#160;</td></tr>
  </xsl:template>

  <xsl:template match="classsys">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Classification System:</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
        <table width="100%">
        <xsl:apply-templates select="./classcit"/>
        <xsl:apply-templates select="./classmod"/>
        </table>
        </td></tr>
  </xsl:template>

    <xsl:template match="classcit">
        <xsl:apply-templates select="citeinfo/*"/>
  </xsl:template>
  
  <xsl:template match="classmod">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Modifications:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="idref">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">ID Reference:</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
        <table width="100%">
        <xsl:apply-templates select="citeinfo/*"/>
        </table>
        </td></tr>
  </xsl:template>

  <xsl:template match="ider">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">ID Name:</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
        <table width="100%">
        <xsl:apply-templates select="cntinfo/*"/>
        </table>
        </td></tr>
  </xsl:template>
  
  <xsl:template match="taxonpro">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Procedures:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        </td></tr>
  </xsl:template>
  
  <xsl:template match="taxoncom">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Completeness:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        </td></tr>
  </xsl:template>
  
  <xsl:template match="vouchers">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Vouchers:</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
        <table width="100%">
        <xsl:apply-templates select="./specimen"/>
        <xsl:apply-templates select="./reposit"/>
        </table>
        </td></tr>
  </xsl:template>

  <xsl:template match="specimen">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Specimen:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="./*"/></td></tr>
  </xsl:template>

  <xsl:template match="reposit">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Repository:</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
        <table width="100%">
        <xsl:apply-templates select="cntinfo/*"/>
        </table>
        </td></tr>
  </xsl:template>  
  
  <xsl:template match="taxoncl">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Classification:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <table width="100%">
        <xsl:apply-templates select="./*"/>
        </table>
        </td></tr>
  </xsl:template>
  
  <xsl:template match="taxonrn">
      <tr><td width="{$secondColIndent}" class="{$secondColStyle}">
        <xsl:text>Rank Name:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>      
  </xsl:template> 
  
  <xsl:template match="taxonrv">
      <tr><td width="{$secondColIndent}" class="{$secondColStyle}">
        <xsl:text>Rank Value:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>
  
  <xsl:template match="common">
      <tr><td width="{$secondColIndent}" class="{$secondColStyle}">
        <xsl:text>Common Name:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:for-each select=".">
            <xsl:value-of select="."/>
        </xsl:for-each></td></tr>      
  </xsl:template>
  
</xsl:stylesheet>
