<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-coverage-2.0.0.xsl,v $'
  *      Authors: Matthew Brooke
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
  * convert an XML file that is valid with respect to the eml-variable.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format
  * suitable for rendering with modern web browsers.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <!-- <xsl:import href="eml-literature-2.0.0beta6-@name@.xsl"/>-->
  <xsl:output method="html" encoding="iso-8859-1"/>

  <!-- This module is for coverage and it is self contained(It is a table 
       and will handle reference by it self)-->
  <xsl:template name="coverage">
    <table class="tabledefault" width="100%">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:apply-templates mode="coverage">
            </xsl:apply-templates>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates mode="coverage">
          </xsl:apply-templates>
        </xsl:otherwise>
      </xsl:choose>
    </table>
  </xsl:template>

 <!-- ********************************************************************* -->
 <!-- **************  G E O G R A P H I C   C O V E R A G E  ************** -->
 <!-- ********************************************************************* -->
  <xsl:template match="geographicCov" mode="coverage">
    <xsl:choose>
      <xsl:when test="references!=''">
        <xsl:variable name="ref_id" select="references"/>
        <xsl:variable name="references" select="$ids[@id=$ref_id]" />
        <xsl:for-each select="$references">
          <xsl:call-template name="geographicCovCommon">
          </xsl:call-template>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="geographicCovCommon">
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template name="geographicCovCommon">
      <tr class="{$subHeaderStyle}"><td class="{$subHeaderStyle}" colspan="2">
      <xsl:text>Geographic Coverage:</xsl:text></td></tr>
      <xsl:apply-templates select="geographicDescription"/>
      <xsl:apply-templates select="boundingCoordinates"/>
      <xsl:for-each select="datasetGPolygon">
         <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Dataset G-Polygon:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:apply-templates select="datasetGPolygonOuterGRing"/></td></tr>
         <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
             &#160;</td><td width="{$secondColWidth}" class="{$secondColStyle}">
         <xsl:for-each select="datasetGPolygonExclusionGRing">
             <xsl:apply-templates select="."/>
         </xsl:for-each></td></tr>
      </xsl:for-each>
  </xsl:template>

  <xsl:template match="geographicDescription">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Geographic Description:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="boundingCoordinates">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Bounding Coordinates:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="westBoundingCoordinate"/></td></tr>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        &#160;</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="eastBoundingCoordinate"/></td></tr>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        &#160;</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="northBoundingCoordinate"/></td></tr>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        &#160;</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="southBoundingCoordinate"/></td></tr>
        <xsl:apply-templates select="boundingAltitudes"/>
  </xsl:template>

  <xsl:template match="westBoundingCoordinate">
    <xsl:text>West: &#160;</xsl:text>
    <xsl:value-of select="."/>&#160; degrees
  </xsl:template>

  <xsl:template match="eastBoundingCoordinate">
    <xsl:text>East: &#160;</xsl:text>
    <xsl:value-of select="."/>&#160; degrees
  </xsl:template>

  <xsl:template match="northBoundingCoordinate">
    <xsl:text>North: &#160;</xsl:text>
    <xsl:value-of select="."/>&#160; degrees
  </xsl:template>

  <xsl:template match="southBoundingCoordinate">
    <xsl:text>South: &#160;</xsl:text>
    <xsl:value-of select="."/>&#160; degrees
  </xsl:template>


  <xsl:template match="boundingAltitudes">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Bounding Altitudes:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="altitudeMinimum"/></td></tr>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        &#160;</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="altitudeMaximum"/></td></tr>
  </xsl:template>

  <xsl:template match="altitudeMinimum">
    <xsl:text>Minimum: &#160;</xsl:text>
    <xsl:value-of select="."/> &#160;<xsl:value-of select="../altitudeUnits"/>
  </xsl:template>

  <xsl:template match="altitudeMaximum">
    <xsl:text>Maximum: &#160;</xsl:text>
    <xsl:value-of select="."/> &#160;<xsl:value-of select="../altitudeUnits"/>
  </xsl:template>

  <xsl:template match="datasetGPolygonOuterGRing">
    <xsl:text>Outer Ring: &#160;</xsl:text>
    <xsl:for-each select="gringpoint">
       <xsl:apply-templates select="."/>
    </xsl:for-each>
    <xsl:apply-templates select="gring"/>
  </xsl:template>

  <xsl:template match="datasetGPolygonExclusionGRing">
    <xsl:text>Exclusion Ring: &#160;</xsl:text>
    <xsl:for-each select="gRingPoint">
       <xsl:apply-templates select="."/>
    </xsl:for-each>
    <xsl:apply-templates select="gRing"/>
  </xsl:template>

  <xsl:template match="gRing">
    <xsl:text>(GRing) &#160;</xsl:text>
    <xsl:text>Latitude: </xsl:text>
    <xsl:value-of select="gRingLatitude"/>,
    <xsl:text>Longitude: </xsl:text>
    <xsl:value-of select="gRingLongitude"/><br/>
  </xsl:template>

  <xsl:template match="gRingPoint">
    <xsl:text>Latitude: </xsl:text>
    <xsl:value-of select="gRingLatitude"/>,
    <xsl:text>Longitude: </xsl:text>
    <xsl:value-of select="gRingLongitude"/><br/>
  </xsl:template>

<!-- ********************************************************************* -->
<!-- ****************  T E M P O R A L   C O V E R A G E  **************** -->
<!-- ********************************************************************* -->
  
  <xsl:template match="temporalCov" mode="coverage">
    <xsl:choose>
      <xsl:when test="references!=''">
        <xsl:variable name="ref_id" select="references"/>
        <xsl:variable name="references" select="$ids[@id=$ref_id]" />
        <xsl:for-each select="$references">
          <xsl:call-template name="temporalCovCommon">
          </xsl:call-template>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="temporalCovCommon">
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template name="temporalCovCommon" >
     <tr class="{$subHeaderStyle}"><td class="{$subHeaderStyle}" colspan="2">
      <xsl:text>Temporal Coverage:</xsl:text></td></tr>
      <xsl:apply-templates select="singleDateTime"/>
      <xsl:apply-templates select="rangeOfDates"/>
  </xsl:template>
  
  <xsl:template match="singleDateTime">
    <xsl:call-template name="singleDateType" />
  </xsl:template>
  
  <xsl:template match="rangeOfDates">
     <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Begin:
         </td>
         <td width="{$secondColWidth}" class="{$secondColStyle}">
                <xsl:apply-templates select="beginDate"/>
         </td>
     </tr>

     <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            End:
          </td>
          <td width="{$secondColWidth}" class="{$secondColStyle}">
             <xsl:apply-templates select="endDate"/>
          </td>
     </tr>
    

  </xsl:template>


  <xsl:template match="beginDate">
      <xsl:call-template name="singleDateType"/>
  </xsl:template>

  <xsl:template match="endDate">
      <xsl:call-template name="singleDateType"/>
  </xsl:template>
  
  <xsl:template name="singleDateType">
     <xsl:if test="calendarDate"> 
      <tr>
       <td width="{$firstColWidth}" class="{$firstColStyle}">
          Calendar Date:</td>
       <td width="{$secondColWidth}" class="{$secondColStyle}">
          <xsl:value-of select="calendarDate"/>
          <xsl:if test="./time and normalize-space(./time)!=''">
            <xsl:text>&#160; at &#160;</xsl:text><xsl:apply-templates select="time"/>
          </xsl:if>
        </td>
      </tr>
     </xsl:if>
     <xsl:if test="alternativeTimeScale">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Other Time Scale:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:apply-templates select="alternativeTimeScale"/></td></tr>
    </xsl:if>
  </xsl:template>

 
  <xsl:template match="alternativeTimeScale">
    <table width="100%">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            timescale:</td><td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="timeScaleName"/></td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            time estimate:</td><td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="timeScaleAgeEstimate"/></td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            time uncertainty:</td><td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="timeScaleAgeUncertainty"/></td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            time explanation:</td><td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="timeScaleAgeExplanation"/></td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            citation:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <table width="100%"><xsl:apply-templates select="timeScaleCitation"/></table>
        </td></tr>
    </table>
  </xsl:template>

  <xsl:template match="timeScaleCitation">
     <!-- Using citation module here -->
     
  </xsl:template>

<!-- ********************************************************************* -->
<!-- ***************  T A X O N O M I C   C O V E R A G E  *************** -->
<!-- ********************************************************************* -->
  <xsl:template match="taxonomicCov" mode="coverage">
     <xsl:choose>
      <xsl:when test="references!=''">
        <xsl:variable name="ref_id" select="references"/>
        <xsl:variable name="references" select="$ids[@id=$ref_id]" />
        <xsl:for-each select="$references">
          <xsl:call-template name="taxonomicCovCommon">
          </xsl:call-template>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="taxonomicCovCommon">
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
  
  <xsl:template name="taxonomicCovCommon">
      <tr class="{$subHeaderStyle}"><td class="{$subHeaderStyle}" colspan="2">
      <xsl:text>Taxonomic Coverage:</xsl:text></td></tr>
      <xsl:apply-templates select="taxonsys"/>
      <xsl:apply-templates select="generalTaxonomicCoverage"/>
      <xsl:for-each select="taxoncl">
          <xsl:apply-templates select="."/>
      </xsl:for-each>
  </xsl:template>

 
  <xsl:template match="generalTaxonomicCoverage">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>General:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <!-- output for taxonomic system is not finished -->
  <xsl:template match="taxonomicSystem">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Taxonomic System:</xsl:text></td>
        <td width="{$secondColWidth}" class="{$firstColStyle}">&#160;</td></tr>
      <xsl:apply-templates select="./*"/>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>&#160;</xsl:text></td><td width="{$secondColWidth}" class="{$firstColStyle}">&#160;</td></tr>
  </xsl:template>

  <xsl:template match="classificationSystem">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Classification System:</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
        <table width="100%">
        <xsl:apply-templates select="classificationSystemCitaion"/>
        <xsl:apply-templates select="./classmod"/>
        </table>
        </td></tr>
  </xsl:template>

  <xsl:template match="classificationSystemCitation">
    <!-- Need using citaion module -->
  
  </xsl:template>

  <xsl:template match="classificationSystemModifications">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Modifications:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="identificationReference">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">ID Reference:</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;
        </td></tr>
        <!-- Need using citaion module-->
  </xsl:template>

  <xsl:template match="identifierName">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">ID Name:</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
          &#160;
        </td></tr>
       <tr><td colspan="2">
           <xsl:call-template name="party">
             <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
           </xsl:call-template>
       </td></tr>
  </xsl:template>

  <xsl:template match="taxonomicProcedures">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Procedures:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="."/></td></tr>
  </xsl:template>

  <xsl:template match="taxonomicCompleteness">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Completeness:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="."/></td></tr>
  </xsl:template>

  <xsl:template match="vouchers">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Vouchers:</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
        <table width="100%">
        <xsl:apply-templates select="specimen"/>
        <xsl:apply-templates select="repository"/>
        </table>
        </td></tr>
  </xsl:template>

  <xsl:template match="specimen">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Specimen:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="./*"/></td></tr>
  </xsl:template>

  <xsl:template match="repository">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Repository:</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
          &#160;
        </td></tr>
        <tr><td colspan="2">
           <xsl:call-template name="party">
             <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
           </xsl:call-template>
       </td></tr>
  </xsl:template>

  <xsl:template match="taxonomicClassification">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Classification:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <table width="100%">
        <xsl:apply-templates select="./*"/>
        </table>
        </td></tr>
  </xsl:template>

  <xsl:template match="taxonRankName">
      <tr><td width="{$secondColIndent}" class="{$secondColStyle}">
        <xsl:text>Rank Name:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="taxonRankValue">
      <tr><td width="{$secondColIndent}" class="{$secondColStyle}">
        <xsl:text>Rank Value:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="commonName">
      <tr><td width="{$secondColIndent}" class="{$secondColStyle}">
        <xsl:text>Common Name:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:for-each select=".">
            <xsl:value-of select="."/>
        </xsl:for-each></td></tr>
  </xsl:template>
  
  
</xsl:stylesheet>
