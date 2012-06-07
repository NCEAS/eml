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
  <xsl:import href="eml-literature.xsl"/>

  <xsl:output method="html" encoding="iso-8859-1"
    doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
    indent="yes" />  

  <!-- This module is for coverage and it is self contained(It is a table
       and will handle reference by it self)-->
  <xsl:template name="coverage">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
          <table class="{$tabledefaultStyle}">
            <xsl:for-each select="geographicCoverage">
                <xsl:call-template name="geographicCoverage">
                </xsl:call-template>
            </xsl:for-each>
          </table>
          <table class="{$tabledefaultStyle}">
             <xsl:for-each select="temporalCoverage">
                <xsl:call-template name="temporalCoverage">
                </xsl:call-template>
            </xsl:for-each>
          </table>
          <table class="{$tabledefaultStyle}">
            <xsl:for-each select="taxonomicCoverage">
                <xsl:call-template name="taxonomicCoverage">
                </xsl:call-template>
            </xsl:for-each>
          </table>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <table class="{$tabledefaultStyle}">
            <xsl:for-each select="geographicCoverage">
                <xsl:call-template name="geographicCoverage">
                </xsl:call-template>
            </xsl:for-each>
          </table>
          <table class="{$tabledefaultStyle}">
            <xsl:for-each select="temporalCoverage">
                <xsl:call-template name="temporalCoverage">
                </xsl:call-template>
            </xsl:for-each>
          </table>
          <table class="{$tabledefaultStyle}">
            <xsl:for-each select="taxonomicCoverage">
                <xsl:call-template name="taxonomicCoverage">
                </xsl:call-template>
            </xsl:for-each>
          </table>
        </xsl:otherwise>
      </xsl:choose>
  </xsl:template>

 <!-- ********************************************************************* -->
 <!-- **************  G E O G R A P H I C   C O V E R A G E  ************** -->
 <!-- ********************************************************************* -->
  <xsl:template name="geographicCoverage">
    <xsl:choose>
      <xsl:when test="references!=''">
        <xsl:variable name="ref_id" select="references"/>
        <xsl:variable name="references" select="$ids[@id=$ref_id]" />
        <xsl:for-each select="$references">
          <xsl:for-each select="geographicCoverage">
            <table class="{$tabledefaultStyle}">
              <xsl:call-template name="geographicCovCommon" />
            </table>
          </xsl:for-each>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
          <table class="{$tabledefaultStyle}">
            <xsl:call-template name="geographicCovCommon" />
          </table>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template name="geographicCovCommon">
      <tr>
        <th colspan="2">
          <xsl:text>Geographic Region:</xsl:text>
        </th>
      </tr>
      <xsl:apply-templates select="geographicDescription"/>
      <xsl:apply-templates select="boundingCoordinates"/>
      <xsl:for-each select="datasetGPolygon">
          <xsl:if test="datasetGPolygonOuterGRing">
            <xsl:apply-templates select="datasetGPolygonOuterGRing"/>
          </xsl:if>
          <xsl:if test="datasetGPolygonExclusionGRing">
              <xsl:apply-templates select="datasetGPolygonExclusionGRing"/>
          </xsl:if>
     </xsl:for-each>
  </xsl:template>

  <xsl:template match="geographicDescription">
    <tr>
      <td class="{$firstColStyle}">Geographic Description:</td>
      <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
    </tr>
  </xsl:template>

  <xsl:template match="boundingCoordinates">
      <tr><td class="{$firstColStyle}">
           Bounding Coordinates:
          </td>
       <td>
         <table>
           <xsl:apply-templates select="westBoundingCoordinate"/>
           <xsl:apply-templates select="eastBoundingCoordinate"/>
           <xsl:apply-templates select="northBoundingCoordinate"/>
           <xsl:apply-templates select="southBoundingCoordinate"/>
           <xsl:apply-templates select="boundingAltitudes"/>
         </table>
      </td>
      </tr>
  </xsl:template>

  <xsl:template match="westBoundingCoordinate">
    <tr><td class="{$firstColStyle}">
         <xsl:text>West: &#160;</xsl:text>
        </td>
        <td class="{$secondColStyle}">
         <xsl:value-of select="."/>&#160; degrees
        </td>
     </tr>
  </xsl:template>

  <xsl:template match="eastBoundingCoordinate">
    <tr><td class="{$firstColStyle}">
       <xsl:text>East: &#160;</xsl:text>
       </td>
       <td class="{$secondColStyle}">
         <xsl:value-of select="."/>&#160; degrees
       </td>
     </tr>
  </xsl:template>

  <xsl:template match="northBoundingCoordinate">
    <tr><td class="{$firstColStyle}">
         <xsl:text>North: &#160;</xsl:text>
        </td>
        <td class="{$secondColStyle}">
          <xsl:value-of select="."/>&#160; degrees
        </td>
     </tr>
  </xsl:template>

  <xsl:template match="southBoundingCoordinate">
    <tr><td class="{$firstColStyle}">
         <xsl:text>South: &#160;</xsl:text>
       </td>
       <td class="{$secondColStyle}">
         <xsl:value-of select="."/>&#160; degrees
        </td>
    </tr>
  </xsl:template>


  <xsl:template match="boundingAltitudes">

      <tr><td class="{$firstColStyle}">
        Mimimum Altitude:</td><td class="{$secondColStyle}">
        <xsl:apply-templates select="altitudeMinimum"/></td></tr>
      <tr><td class="{$firstColStyle}">
        Maximum Altitude:</td><td class="{$secondColStyle}">
        <xsl:apply-templates select="altitudeMaximum"/></td></tr>

  </xsl:template>

  <xsl:template match="altitudeMinimum">
     <xsl:value-of select="."/> &#160;<xsl:value-of select="../altitudeUnits"/>
  </xsl:template>

  <xsl:template match="altitudeMaximum">
    <xsl:value-of select="."/> &#160;<xsl:value-of select="../altitudeUnits"/>
  </xsl:template>

  <xsl:template match="datasetGPolygonOuterGRing">
    <tr><td class="{$firstColStyle}">
          <xsl:text>G-Ploygon(Outer Ring): </xsl:text>
        </td>
        <td class="{$secondColStyle}">
           <xsl:apply-templates select="gRingPoint"/>
           <xsl:apply-templates select="gRing"/>
        </td>
     </tr>
  </xsl:template>

  <xsl:template match="datasetGPolygonExclusionGRing">
    <tr><td class="{$firstColStyle}">
          <xsl:text>G-Ploygon(Exclusion Ring): </xsl:text>
        </td>
        <td class="{$secondColStyle}">
           <xsl:apply-templates select="gRingPoint"/>
           <xsl:apply-templates select="gRing"/>
        </td>
     </tr>
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

  <xsl:template name="temporalCoverage">
    <xsl:choose>
      <xsl:when test="references!=''">
        <xsl:variable name="ref_id" select="references"/>
        <xsl:variable name="references" select="$ids[@id=$ref_id]" />
        <xsl:for-each select="$references">
          <table class="{$tabledefaultStyle}">
            <xsl:call-template name="temporalCovCommon" />
          </table>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
          <table class="{$tabledefaultStyle}">
            <xsl:call-template name="temporalCovCommon" />
          </table>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template name="temporalCovCommon" >
     <tr><th colspan="2">
      <xsl:text>Time Period:</xsl:text></th></tr>
      <xsl:apply-templates select="singleDateTime"/>
      <xsl:apply-templates select="rangeOfDates"/>
  </xsl:template>

  <xsl:template match="singleDateTime">
    <tr><td class="{$firstColStyle}">
            Date:
         </td>
         <td>
             <xsl:call-template name="singleDateType" />
         </td>
     </tr>
   </xsl:template>

  <xsl:template match="rangeOfDates">
     <tr><td class="{$firstColStyle}">
            Begin:
         </td>
         <td>
            <xsl:apply-templates select="beginDate"/>
          </td>
     </tr>

     <tr><td class="{$firstColStyle}">
            End:
          </td>
          <td>
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
    <table>
     <xsl:if test="calendarDate">
      <tr>
       <td colspan="2" class="{$secondColStyle}">
          <xsl:value-of select="calendarDate"/>
          <xsl:if test="./time and normalize-space(./time)!=''">
            <xsl:text>&#160; at &#160;</xsl:text><xsl:apply-templates select="time"/>
          </xsl:if>
        </td>
      </tr>
     </xsl:if>
     <xsl:if test="alternativeTimeScale">
         <xsl:apply-templates select="alternativeTimeScale"/>
     </xsl:if>
    </table>
  </xsl:template>


  <xsl:template match="alternativeTimeScale">

        <tr><td class="{$firstColStyle}">
            Timescale:</td><td class="{$secondColStyle}"><xsl:value-of select="timeScaleName"/></td></tr>
        <tr><td class="{$firstColStyle}">
            Time estimate:</td><td class="{$secondColStyle}"><xsl:value-of select="timeScaleAgeEstimate"/></td></tr>
        <xsl:if test="timeScaleAgeUncertainty and normalize-space(timeScaleAgeUncertainty)!=''">
        <tr><td class="{$firstColStyle}">
            Time uncertainty:</td><td class="{$secondColStyle}"><xsl:value-of select="timeScaleAgeUncertainty"/></td></tr>
        </xsl:if>
        <xsl:if test="timeScaleAgeExplanation and normalize-space(timeScaleAgeExplanation)!=''">
        <tr><td class="{$firstColStyle}">
            Time explanation:</td><td class="{$secondColStyle}"><xsl:value-of select="timeScaleAgeExplanation"/></td></tr>
        </xsl:if>
        <xsl:if test="timeScaleCitation and normalize-space(timeScaleCitation)!=''">
        <tr><td class="{$firstColStyle}">
            Citation:</td><td class="{$secondColStyle}">
            <xsl:apply-templates select="timeScaleCitation"/>
        </td></tr>
        </xsl:if>

  </xsl:template>

  <xsl:template match="timeScaleCitation">
     <!-- Using citation module here -->
     <xsl:call-template name="citation">
     </xsl:call-template>
  </xsl:template>

<!-- ********************************************************************* -->
<!-- ***************  T A X O N O M I C   C O V E R A G E  *************** -->
<!-- ********************************************************************* -->
  <xsl:template name="taxonomicCoverage">
     <xsl:choose>
      <xsl:when test="references!=''">
        <xsl:variable name="ref_id" select="references"/>
        <xsl:variable name="references" select="$ids[@id=$ref_id]" />
        <xsl:for-each select="$references">
          <table class="{$tabledefaultStyle}">
            <xsl:call-template name="taxonomicCovCommon" />
          </table>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <table class="{$tabledefaultStyle}">
          <xsl:call-template name="taxonomicCovCommon" />
        </table>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>


  <xsl:template name="taxonomicCovCommon">
      <tr><th colspan="2">
      <xsl:text>Taxonomic Range:</xsl:text></th></tr>
      <xsl:apply-templates select="taxonomicSystem"/>
      <xsl:apply-templates select="generalTaxonomicCoverage"/>
      <xsl:for-each select="taxonomicClassification">
          <xsl:apply-templates select="."/>
      </xsl:for-each>
  </xsl:template>


 <xsl:template match="taxonomicSystem">
     <tr><td class="{$firstColStyle}">
        <xsl:text>Taxonomic System:</xsl:text></td>
        <td>
            <table class="{$tabledefaultStyle}">
              <xsl:apply-templates select="./*"/>
            </table>
        </td>
     </tr>
  </xsl:template>


  <xsl:template match="classificationSystem">
     <xsl:for-each select="classificationSystemCitation">
        <tr><td class="{$firstColStyle}">Classification Citation:</td>
          <td>
           <xsl:call-template name="citation">
             <xsl:with-param name="citationfirstColStyle" select="$firstColStyle"/>
             <xsl:with-param name="citationsubHeaderStyle" select="$subHeaderStyle"/>
           </xsl:call-template>
         </td>
        </tr>
     </xsl:for-each>
     <xsl:if test="classificationSystemModifications and normalize-space(classificationSystemModifications)!=''">
      <tr><td class="{$firstColStyle}">Modification:</td>
        <td class="{$secondColStyle}">
          <xsl:value-of select="classificationSystemModifications"/>
        </td>
      </tr>
     </xsl:if>
  </xsl:template>


  <xsl:template match="identificationReference">
      <tr><td class="{$firstColStyle}">ID Reference:</td>
          <td>
             <xsl:call-template name="citation">
                <xsl:with-param name="citationfirstColStyle" select="$firstColStyle"/>
                <xsl:with-param name="citationsubHeaderStyle" select="$subHeaderStyle"/>
             </xsl:call-template>
          </td>
     </tr>
  </xsl:template>

  <xsl:template match="identifierName">
      <tr><td class="{$firstColStyle}">ID Name:</td>
          <td>
             <xsl:call-template name="party">
               <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
             </xsl:call-template>
          </td>
      </tr>
  </xsl:template>

  <xsl:template match="taxonomicProcedures">
    <tr><td class="{$firstColStyle}">
        <xsl:text>Procedures:</xsl:text></td><td class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="taxonomicCompleteness">
    <tr><td class="{$firstColStyle}">
        <xsl:text>Completeness:</xsl:text></td><td class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="vouchers">
      <tr><td class="{$firstColStyle}">Vouchers:</td>
        <td>
        <table class="{$tabledefaultStyle}">
        <xsl:apply-templates select="specimen"/>
        <xsl:apply-templates select="repository"/>
        </table>
        </td></tr>
  </xsl:template>

  <xsl:template match="specimen">
    <tr><td class="{$firstColStyle}">
        <xsl:text>Specimen:</xsl:text></td><td class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="repository">
    <tr><td class="{$firstColStyle}">Repository:</td>
        <td>
            <xsl:for-each select="originator">
               <xsl:call-template name="party">
                 <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
               </xsl:call-template>
            </xsl:for-each>
        </td>
    </tr>
  </xsl:template>


  <xsl:template match="generalTaxonomicCoverage">
      <tr><td class="{$firstColStyle}">
             <xsl:text>General Coverage:</xsl:text></td>
           <td class="{$secondColStyle}">
             <xsl:value-of select="."/>
          </td>
      </tr>
  </xsl:template>


  <xsl:template match="taxonomicClassification">
    <tr><td class="{$firstColStyle}">
        <xsl:text>Classification:</xsl:text></td><td>
        <table class="{$tabledefaultStyle}">
        <xsl:apply-templates select="./*" mode="nest"/>
        </table>
        </td></tr>
  </xsl:template>

  <xsl:template match="taxonRankName" mode="nest" >
      <tr><td class="{$firstColStyle}">
        <xsl:text>Rank Name:</xsl:text></td><td class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="taxonRankValue" mode="nest">
      <tr><td class="{$firstColStyle}">
        <xsl:text>Rank Value:</xsl:text></td><td class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="commonName" mode="nest">
      <tr><td class="{$firstColStyle}">
            <xsl:text>Common Name:</xsl:text></td><td class="{$secondColStyle}">
            <xsl:value-of select="."/>
          </td>
      </tr>
  </xsl:template>

  <xsl:template match="taxonomicClassification" mode="nest">
    <tr><td class="{$firstColStyle}">
          <xsl:text>Classification:</xsl:text>
        </td>
        <td>
           <table class="{$tabledefaultStyle}">
             <xsl:apply-templates select="./*" mode="nest"/>
           </table>
        </td>
     </tr>
  </xsl:template>

</xsl:stylesheet>
