<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-coverage.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: jones $'
  *     '$Date: 2005-12-14 23:05:46 $'
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
  <xsl:import href="eml-literature.xsl"/>
  <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  

  <!-- This module is for coverage and it is self contained(It is a table
       and will handle reference by it self)-->
  <xsl:template name="coverage">
    <table xsl:use-attribute-sets="cellspacing" class="{$tabledefaultStyle}" width="100%">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:for-each select="geographicCoverage">
                <xsl:call-template name="geographicCoverage">
                </xsl:call-template>
            </xsl:for-each>
             <xsl:for-each select="temporalCoverage">
                <xsl:call-template name="temporalCoverage">
                </xsl:call-template>
            </xsl:for-each>
            <xsl:for-each select="taxonomicCoverage">
                <xsl:call-template name="taxonomicCoverage">
                </xsl:call-template>
            </xsl:for-each>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
            <xsl:for-each select="geographicCoverage">
                <xsl:call-template name="geographicCoverage">
                </xsl:call-template>
            </xsl:for-each>
            <xsl:for-each select="temporalCoverage">
                <xsl:call-template name="temporalCoverage">
                </xsl:call-template>
            </xsl:for-each>
            <xsl:for-each select="taxonomicCoverage">
                <xsl:call-template name="taxonomicCoverage">
                </xsl:call-template>
            </xsl:for-each>
        </xsl:otherwise>
      </xsl:choose>
    </table>
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
          <xsl:if test="datasetGPolygonOuterGRing">
            <xsl:apply-templates select="datasetGPolygonOuterGRing"/>
          </xsl:if>
          <xsl:if test="datasetGPolygonExclusionGRing">
              <xsl:apply-templates select="datasetGPolygonExclusionGRing"/>
          </xsl:if>
     </xsl:for-each>
  </xsl:template>

  <xsl:template match="geographicDescription">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Geographic Description:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="boundingCoordinates">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
           Bounding Coordinates:
          </td>
       <td width="{$secondColWidth}">
         <table xsl:use-attribute-sets="cellspacing" class="{$tabledefaultStyle}" width="100%">
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
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
         <xsl:text>West: &#160;</xsl:text>
        </td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
         <xsl:value-of select="."/>&#160; degrees
        </td>
     </tr>
  </xsl:template>

  <xsl:template match="eastBoundingCoordinate">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
       <xsl:text>East: &#160;</xsl:text>
       </td>
       <td width="{$secondColWidth}" class="{$secondColStyle}">
         <xsl:value-of select="."/>&#160; degrees
       </td>
     </tr>
  </xsl:template>

  <xsl:template match="northBoundingCoordinate">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
         <xsl:text>North: &#160;</xsl:text>
        </td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
          <xsl:value-of select="."/>&#160; degrees
        </td>
     </tr>
  </xsl:template>

  <xsl:template match="southBoundingCoordinate">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
         <xsl:text>South: &#160;</xsl:text>
       </td>
       <td width="{$secondColWidth}" class="{$secondColStyle}">
         <xsl:value-of select="."/>&#160; degrees
        </td>
    </tr>
  </xsl:template>


  <xsl:template match="boundingAltitudes">

      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Mimimum Altitude:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="altitudeMinimum"/></td></tr>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        Maximum Altitude:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:apply-templates select="altitudeMaximum"/></td></tr>

  </xsl:template>

  <xsl:template match="altitudeMinimum">
     <xsl:value-of select="."/> &#160;<xsl:value-of select="../altitudeUnits"/>
  </xsl:template>

  <xsl:template match="altitudeMaximum">
    <xsl:value-of select="."/> &#160;<xsl:value-of select="../altitudeUnits"/>
  </xsl:template>

  <xsl:template match="datasetGPolygonOuterGRing">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
          <xsl:text>G-Ploygon(Outer Ring): </xsl:text>
        </td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
           <xsl:apply-templates select="gRingPoint"/>
           <xsl:apply-templates select="gRing"/>
        </td>
     </tr>
  </xsl:template>

  <xsl:template match="datasetGPolygonExclusionGRing">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
          <xsl:text>G-Ploygon(Exclusion Ring): </xsl:text>
        </td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
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
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Date:
         </td>
         <td width="{$secondColWidth}">
             <xsl:call-template name="singleDateType" />
         </td>
     </tr>
   </xsl:template>

  <xsl:template match="rangeOfDates">
     <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Begin:
         </td>
         <td width="{$secondColWidth}">
            <xsl:apply-templates select="beginDate"/>
          </td>
     </tr>

     <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            End:
          </td>
          <td width="{$secondColWidth}">
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
    <table xsl:use-attribute-sets="cellspacing" class="{$tabledefaultStyle}" width="100%">
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

        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Timescale:</td><td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="timeScaleName"/></td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Time estimate:</td><td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="timeScaleAgeEstimate"/></td></tr>
        <xsl:if test="timeScaleAgeUncertainty and normalize-space(timeScaleAgeUncertainty)!=''">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Time uncertainty:</td><td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="timeScaleAgeUncertainty"/></td></tr>
        </xsl:if>
        <xsl:if test="timeScaleAgeExplanation and normalize-space(timeScaleAgeExplanation)!=''">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Time explanation:</td><td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="timeScaleAgeExplanation"/></td></tr>
        </xsl:if>
        <xsl:if test="timeScaleCitation and normalize-space(timeScaleCitation)!=''">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Citation:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
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
      <xsl:apply-templates select="taxonomicSystem"/>
      <xsl:apply-templates select="generalTaxonomicCoverage"/>
      <xsl:apply-templates select="taxonomicClassification"/>
  </xsl:template>


 <xsl:template match="taxonomicSystem">
     <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Taxonomic System:</xsl:text></td>
        <td width="{$secondColWidth}">
            <table xsl:use-attribute-sets="cellspacing" width="100%" class="{$tabledefaultStyle}">
              <xsl:apply-templates select="./*"/>
            </table>
        </td>
     </tr>
  </xsl:template>


  <xsl:template match="classificationSystem">
     <xsl:for-each select="classificationSystemCitation">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Classification Citation:</td>
          <td width="{$secondColWidth}">
           <xsl:call-template name="citation">
             <xsl:with-param name="citationfirstColStyle" select="$firstColStyle"/>
             <xsl:with-param name="citationsubHeaderStyle" select="$subHeaderStyle"/>
           </xsl:call-template>
         </td>
        </tr>
     </xsl:for-each>
     <xsl:if test="classificationSystemModifications and normalize-space(classificationSystemModifications)!=''">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Modification:</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
          <xsl:value-of select="classificationSystemModifications"/>
        </td>
      </tr>
     </xsl:if>
  </xsl:template>


  <xsl:template match="identificationReference">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">ID Reference:</td>
          <td width="{$secondColWidth}">
             <xsl:call-template name="citation">
                <xsl:with-param name="citationfirstColStyle" select="$firstColStyle"/>
                <xsl:with-param name="citationsubHeaderStyle" select="$subHeaderStyle"/>
             </xsl:call-template>
          </td>
     </tr>
  </xsl:template>

  <xsl:template match="identifierName">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">ID Name:</td>
          <td width="{$secondColWidth}">
             <xsl:call-template name="party">
               <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
             </xsl:call-template>
          </td>
      </tr>
  </xsl:template>

  <xsl:template match="taxonomicProcedures">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Procedures:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="taxonomicCompleteness">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Completeness:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="vouchers">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Vouchers:</td>
        <td width="{$secondColWidth}">
        <table xsl:use-attribute-sets="cellspacing" class="{$tabledefaultStyle}" width="100%">
        <xsl:apply-templates select="specimen"/>
        <xsl:apply-templates select="repository"/>
        </table>
        </td></tr>
  </xsl:template>

  <xsl:template match="specimen">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
        <xsl:text>Specimen:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="repository">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Repository:</td>
        <td width="{$secondColWidth}">
            <xsl:for-each select="originator">
               <xsl:call-template name="party">
                 <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
               </xsl:call-template>
            </xsl:for-each>
        </td>
    </tr>
  </xsl:template>


  <xsl:template match="generalTaxonomicCoverage">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
             <xsl:text>General Coverage:</xsl:text></td>
           <td width="{$secondColWidth}" class="{$secondColStyle}">
             <xsl:value-of select="."/>
          </td>
      </tr>
  </xsl:template>


  <xsl:template match="taxonomicClassification">
	
		<tr>
		<xsl:choose>
			<xsl:when test="not(boolean(../taxonomicClassification/taxonomicClassification)) and position()=1">
				<td width="{$firstColWidth}" class="{$firstColStyle}" valign="bottom"><xsl:text>Taxon:</xsl:text></td>
			</xsl:when>
			<xsl:otherwise>
				<td width="{$firstColWidth}" class="{$firstColStyle}"><xsl:text>Taxon:</xsl:text></td>
			</xsl:otherwise>
		</xsl:choose><td width="{$secondColWidth}">
		
		
		<table xsl:use-attribute-sets="cellspacing" class="{$tabledefaultStyle}" width="100%">
		
		<xsl:if test="../taxonomicClassification/taxonomicClassification or position()=1">
				<tr>
					<td class="tablehead" width="30%">Rank Name</td>
					<td class="tablehead" width="30%">Rank Value</td>
					<td class="tablehead">Common Names</td>
				</tr>
		</xsl:if>
		
				<tr>
					<td width="30%"><xsl:apply-templates select="./taxonRankName" mode="nest"/></td>
					<td width="30%"><xsl:apply-templates select="./taxonRankValue" mode="nest"/></td>
					<td><xsl:apply-templates select="commonName" mode="nest"/></td>
				</tr>
				<xsl:apply-templates select="taxonomicClassification" mode="nest"/>
			</table>
			</td></tr>	
  </xsl:template>

  
  
  <xsl:template match="taxonRankName" mode="nest" >
  		<xsl:apply-templates select=".." mode="indent"/><xsl:value-of select="."/>
  </xsl:template>
  
  <xsl:template match="taxonomicClassification" mode="indent">
  	<xsl:if test="boolean(../../taxonomicClassification)">
  		<xsl:text>&#160;&#160;</xsl:text>
  		<xsl:apply-templates select=".." mode="indent"/>
	</xsl:if>
  </xsl:template>

  
  
  <xsl:template match="taxonRankValue" mode="nest">
        <xsl:value-of select="."/>
  </xsl:template>

  <xsl:template match="commonName" mode="nest">
  			<xsl:if test="position() &gt; 1"><xsl:text>, </xsl:text></xsl:if>
            <xsl:value-of select="."/>
  </xsl:template>

  <xsl:template match="taxonomicClassification" mode="nest">
		<tr>
			<td><xsl:apply-templates select="taxonRankName" mode="nest"/></td>
			<td><xsl:apply-templates select="taxonRankValue" mode="nest"/></td>
			<td><xsl:apply-templates select="commonName" mode="nest"/></td>
		</tr>
		<xsl:apply-templates select="taxonomicClassification" mode="nest"/>
  </xsl:template>

</xsl:stylesheet>