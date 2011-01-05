<?xml version="1.0"?>
<!--
  *  '$RCSfile$'
  *      Authors: Jivka Bojilova
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
  * convert an XML file that is valid with respect to the eml-file.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format
  * suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">


  <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  
  <!-- This module is for datatable module-->

  <xsl:template name="spatialRaster">
      <xsl:param name="spatialrasterfirstColStyle"/>
      <xsl:param name="spatialrastersubHeaderStyle"/>
      <xsl:param name="docid"/>
      <xsl:param name="entityindex"/>
      <table class="{$tabledefaultStyle}">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="spatialRastercommon">
             <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
             <xsl:with-param name="spatialrastersubHeaderStyle" select="$spatialrastersubHeaderStyle"/>
             <xsl:with-param name="docid" select="$docid"/>
             <xsl:with-param name="entityindex" select="$entityindex"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:call-template name="spatialRastercommon">
             <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
             <xsl:with-param name="spatialrastersubHeaderStyle" select="$spatialrastersubHeaderStyle"/>
             <xsl:with-param name="docid" select="$docid"/>
             <xsl:with-param name="entityindex" select="$entityindex"/>
            </xsl:call-template>
         </xsl:otherwise>
      </xsl:choose>
      </table>
  </xsl:template>

  <xsl:template name="spatialRastercommon">
    <xsl:param name="spatialrasterfirstColStyle"/>
    <xsl:param name="spatialrastersubHeaderStyle"/>
    <xsl:param name="docid"/>
    <xsl:param name="entityindex"/>
    <xsl:for-each select="entityName">
       <xsl:call-template name="entityName">
          <xsl:with-param name="entityfirstColStyle" select="$spatialrasterfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="alternateIdentifier">
       <xsl:call-template name="entityalternateIdentifier">
          <xsl:with-param name="entityfirstColStyle" select="$spatialrasterfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="entityDescription">
       <xsl:call-template name="entityDescription">
          <xsl:with-param name="entityfirstColStyle" select="$spatialrasterfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="additionalInfo">
       <xsl:call-template name="entityadditionalInfo">
          <xsl:with-param name="entityfirstColStyle" select="$spatialrasterfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <!-- call physical moduel without show distribution(we want see it later)-->
    <xsl:if test="physical">
       <tr><td class="{$spatialrastersubHeaderStyle}" colspan="2">
        Physical Structure Description:
      </td></tr>
      <xsl:for-each select="physical">
      <tr><td colspan="2">
        <xsl:call-template name="physical">
         <xsl:with-param name="physicalfirstColStyle" select="$spatialrasterfirstColStyle"/>
         <xsl:with-param name="notshowdistribution">yes</xsl:with-param>
        </xsl:call-template>
         </td></tr>
      </xsl:for-each>
    </xsl:if>
    <xsl:if test="coverage">
       <tr><td class="{$spatialrastersubHeaderStyle}" colspan="2">
        Coverage Description:
      </td></tr>
    </xsl:if>
    <xsl:for-each select="coverage">
      <tr><td colspan="2">
        <xsl:call-template name="coverage">
        </xsl:call-template>
      </td></tr>
    </xsl:for-each>
    <xsl:if test="method">
       <tr><td class="{$spatialrastersubHeaderStyle}" colspan="2">
        Method Description:
      </td></tr>
    </xsl:if>
    <xsl:for-each select="method">
      <tr><td colspan="2">
        <xsl:call-template name="method">
          <xsl:with-param name="methodfirstColStyle" select="$spatialrasterfirstColStyle"/>
          <xsl:with-param name="methodsubHeaderStyle" select="$spatialrastersubHeaderStyle"/>
        </xsl:call-template>
      </td></tr>
    </xsl:for-each>
    <xsl:if test="constraint">
       <tr><td class="{$spatialrastersubHeaderStyle}" colspan="2">
        Constraint:
      </td></tr>
    </xsl:if>
    <xsl:for-each select="constraint">
      <tr><td colspan="2">
        <xsl:call-template name="constraint">
          <xsl:with-param name="constraintfirstColStyle" select="$spatialrasterfirstColStyle"/>
        </xsl:call-template>
      </td></tr>
    </xsl:for-each>
    <xsl:for-each select="spatialReference">
       <tr><td class="{$spatialrastersubHeaderStyle}" colspan="2">
        Spatial Reference:
      </td></tr>
      <xsl:call-template name="spatialReference">
        <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
      </xsl:call-template>
    </xsl:for-each>
     <xsl:for-each select="georeferenceInfo">
       <tr><td class="{$spatialrastersubHeaderStyle}" colspan="2">
        Grid Postion:
      </td></tr>
      <xsl:call-template name="georeferenceInfo">
        <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
      </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="horizontalAccuracy">
      <tr><td class="{$spatialrastersubHeaderStyle}" colspan="2">
        Horizontal Accuracy:
      </td></tr>
      <xsl:call-template name="dataQuality">
        <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
      </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="verticalAccuracy">
      <tr><td class="{$spatialrastersubHeaderStyle}" colspan="2">
        Vertical Accuracy:
      </td></tr>
      <xsl:call-template name="dataQuality">
        <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
      </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="cellSizeXDirection">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Cell Size(X):
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="cellSizeYDirection">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Cell Size(Y):
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="numberOfBands">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Number of Bands:
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="rasterOrigin">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Origin:
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="columns">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Max Raster Objects(X):
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="rows">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Max Raster Objects(Y):
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="verticals">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Max Raster Objects(Z):
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="cellGeometry">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Cell Geometry:
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="toneGradation">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Number of Colors:
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="scaleFactor">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Scale Factor:
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
     <xsl:for-each select="offset">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Offset:
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="imageDescription">
      <tr><td class="{$spatialrastersubHeaderStyle}" colspan="2">
        Image Info:
      </td></tr>
      <xsl:call-template name="imageDescription">
        <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
      </xsl:call-template>
    </xsl:for-each>
    <xsl:if test="$withAttributes='1'">
    <xsl:for-each select="attributeList">
      <xsl:call-template name="spatialRasterAttributeList">
        <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
        <xsl:with-param name="spatialrastersubHeaderStyle" select="$spatialrastersubHeaderStyle"/>
        <xsl:with-param name="docid" select="$docid"/>
        <xsl:with-param name="entityindex" select="$entityindex"/>
      </xsl:call-template>
    </xsl:for-each>
    </xsl:if>
    <!-- Here to display distribution info-->
    <xsl:for-each select="physical">
       <xsl:call-template name="spatialRasterShowDistribution">
          <xsl:with-param name="docid" select="$docid"/>
          <xsl:with-param name="entityindex" select="$entityindex"/>
          <xsl:with-param name="physicalindex" select="position()"/>
          <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
          <xsl:with-param name="spatialrastersubHeaderStyle" select="$spatialrastersubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>
  </xsl:template>

  <!--****************************************************
       spatial reference
      ****************************************************-->
    <xsl:template name="spatialReference">
      <xsl:param name="spatialrasterfirstColStyle"/>
       <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="spatialReferenceCommon">
              <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:call-template name="spatialReferenceCommon">
              <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
            </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>

  </xsl:template>


  <xsl:template name="spatialReferenceCommon">
    <xsl:param name="spatialrasterfirstColStyle"/>
    <xsl:for-each select="horizCoordSysName">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Name of Coordinate System:
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="horizCoordSysDef/geogCoordSys">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Definition of <xsl:text> </xsl:text><xsl:value-of select="../@name"/> <xsl:text> </xsl:text> (Geographic Coordinate System):
            </td>
            <td>
              <xsl:call-template name="geogCoordSysType">
                 <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
              </xsl:call-template>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="horizCoordSysDef/projCoordSys">
      <xsl:for-each select="geogCoordSys">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Definition of<xsl:text> </xsl:text><xsl:value-of select="../../@name"/><xsl:text> </xsl:text>(Geographic Coordinate System):
            </td>
            <td>
              <xsl:call-template name="geogCoordSysType">
                 <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
              </xsl:call-template>
            </td>
       </tr>
     </xsl:for-each>
     <xsl:for-each select="projection">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Projection in Geo Coord. System:
            </td>
            <td>
               <table class="{$tabledefaultStyle}">
                 <xsl:for-each select="parameter">
                     <tr><td class="{$spatialrasterfirstColStyle}">
                          <xsl:value-of select="./@name"/>:
                         </td>
                         <td>
                             <table class="{$tabledefaultStyle}">
                                <tr>
                                    <td class="{$secondColStyle}">
                                      <xsl:value-of select="./@value"/>
                                    </td>
                                    <td class="{$secondColStyle}">
                                       <xsl:value-of select="./@description"/>
                                    </td>
                                 </tr>
                             </table>
                          </td>
                      </tr>
                 </xsl:for-each>
                 <xsl:for-each select="unit">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          Unit:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="./@name"/>
                        </td>
                   </tr>
                </xsl:for-each>
              </table>
            </td>
       </tr>
     </xsl:for-each>
    </xsl:for-each>
    <xsl:for-each select="vertCoordSys/altitudeSysDef">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Altitude System Definition:
            </td>
            <td>
               <table class="{$tabledefaultStyle}">
                 <xsl:for-each select="altitudeDatumName">
                     <tr><td class="{$spatialrasterfirstColStyle}">
                          Datum:
                         </td>
                         <td class="{$secondColStyle}">
                            <xsl:value-of select="."/>
                          </td>
                      </tr>
                 </xsl:for-each>
                 <xsl:for-each select="altitudeResolution">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          Resolution:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
                <xsl:for-each select="altitudeDistanceUnits">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          Distance Unit:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
                <xsl:for-each select="altitudeEncodingMethod">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          Encoding Method:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
              </table>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="vertCoordSys/depthSysDef">
        <tr><td class="{$spatialrasterfirstColStyle}">
            Depth System Definition:
            </td>
            <td>
               <table class="{$tabledefaultStyle}">
                 <xsl:for-each select="depthDatumName">
                     <tr><td class="{$spatialrasterfirstColStyle}">
                          Datum:
                         </td>
                         <td class="{$secondColStyle}">
                            <xsl:value-of select="."/>
                          </td>
                      </tr>
                 </xsl:for-each>
                 <xsl:for-each select="depthResolution">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          Resolution:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
                <xsl:for-each select="depthDistanceUnits">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          Distance Unit:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
                <xsl:for-each select="depthEncodingMethod">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          Encoding Method:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
              </table>
            </td>
       </tr>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="geogCoordSysType">
   <xsl:param name="spatialrasterfirstColStyle"/>
   <table class="{$tabledefaultStyle}">
      <xsl:for-each select="datum">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Datum:
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="./@name"/>
            </td>
        </tr>
      </xsl:for-each>
      <xsl:for-each select="spheroid">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Spheroid:
            </td>
            <td>
               <table class="{$tabledefaultStyle}">
                  <tr><td class="{$spatialrasterfirstColStyle}">
                       Name:
                       </td>
                       <td class="{$secondColStyle}">
                        <xsl:value-of select="./@name"/>
                       </td>
                   </tr>
                   <tr><td class="{$spatialrasterfirstColStyle}">
                       Semi Axis Major:
                       </td>
                       <td class="{$secondColStyle}">
                        <xsl:value-of select="./@semiAxisMajor"/>
                       </td>
                   </tr>
                   <tr><td class="{$spatialrasterfirstColStyle}">
                       Denom Flat Ratio:
                       </td>
                       <td class="{$secondColStyle}">
                        <xsl:value-of select="./@denomFlatRatio"/>
                       </td>
                   </tr>
               </table>

            </td>
        </tr>
      </xsl:for-each>
       <xsl:for-each select="primeMeridian">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Prime Meridian:
            </td>
            <td>
               <table class="{$tabledefaultStyle}">
                  <tr><td class="{$spatialrasterfirstColStyle}">
                       Name:
                       </td>
                       <td class="{$secondColStyle}">
                        <xsl:value-of select="./@name"/>
                       </td>
                   </tr>
                   <tr><td class="{$spatialrasterfirstColStyle}">
                       Longitude:
                       </td>
                       <td class="{$secondColStyle}">
                        <xsl:value-of select="./@longitude"/>
                       </td>
                   </tr>
               </table>
            </td>
        </tr>
      </xsl:for-each>
     <xsl:for-each select="unit">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Unit:
            </td>
            <td class="{$secondColStyle}">
              <xsl:value-of select="./@name"/>
            </td>
        </tr>
      </xsl:for-each>
   </table>
  </xsl:template>

  <!--*******************************************************
       georeferenceinfo
      *******************************************************-->
 <xsl:template name="georeferenceInfo">
    <xsl:param name="spatialrasterfirstColStyle"/>
    <xsl:for-each select="cornerPoint">
        <tr><td class="{$spatialrasterfirstColStyle}">
            Corner Point:
            </td>
            <td>
               <table class="{$tabledefaultStyle}">
                 <xsl:for-each select="corner">
                     <tr><td class="{$spatialrasterfirstColStyle}">
                          Corner:
                         </td>
                         <td class="{$secondColStyle}">
                            <xsl:value-of select="."/>
                          </td>
                      </tr>
                 </xsl:for-each>
                 <xsl:for-each select="xCoordinate">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          xCoordinate:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
                <xsl:for-each select="yCoordinate">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          yCoordinate:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
                <xsl:for-each select="pointInPixel">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          Point in Pixel:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
              </table>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="controlPoint">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Control Point:
            </td>
            <td>
               <table class="{$tabledefaultStyle}">
                 <xsl:for-each select="column">
                     <tr><td class="{$spatialrasterfirstColStyle}">
                          Column Location:
                         </td>
                         <td class="{$secondColStyle}">
                            <xsl:value-of select="."/>
                          </td>
                      </tr>
                 </xsl:for-each>
                 <xsl:for-each select="row">
                     <tr><td class="{$spatialrasterfirstColStyle}">
                          Row Location:
                         </td>
                         <td class="{$secondColStyle}">
                            <xsl:value-of select="."/>
                          </td>
                      </tr>
                 </xsl:for-each>
                 <xsl:for-each select="xCoordinate">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          xCoordinate:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
                <xsl:for-each select="yCoordinate">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          yCoordinate:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
                <xsl:for-each select="pointInPixel">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          Point in Pixel:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
              </table>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="bilinearFit">
       <tr><td class="{$spatialrasterfirstColStyle}">
            Bilinear Fit:
            </td>
            <td>
               <table class="{$tabledefaultStyle}">
                 <xsl:for-each select="xIntercept">
                     <tr><td class="{$spatialrasterfirstColStyle}">
                          X Intercept:
                         </td>
                         <td class="{$secondColStyle}">
                            <xsl:value-of select="."/>
                          </td>
                      </tr>
                 </xsl:for-each>
                 <xsl:for-each select="xSlope">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          X Slope:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
                <xsl:for-each select="yIntercept">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          Y Intercept:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
                <xsl:for-each select="ySlope">
                    <tr><td class="{$spatialrasterfirstColStyle}">
                          Y Slope:
                        </td>
                        <td class="{$secondColStyle}">
                           <xsl:value-of select="."/>
                        </td>
                   </tr>
                </xsl:for-each>
              </table>
            </td>
       </tr>
    </xsl:for-each>
 </xsl:template>

 <!--********************************************************
     data quality
     ********************************************************-->
 <xsl:template name="dataQuality">
   <xsl:param name="spatialrasterfirstColStyle"/>
   <xsl:for-each select="accuracyReport">
       <tr><td class="{$spatialrasterfirstColStyle}">
             Report:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
   </xsl:for-each>
   <xsl:if test="quantitativeAccuracyReport">
       <tr><td class="{$spatialrasterfirstColStyle}">
             Quantitative Report:
            </td>
            <td>
                <table class="{$tabledefaultStyle}">
                  <xsl:for-each select="quantitativeAccuracyReport">
                     <tr><td class="{$spatialrasterfirstColStyle}">
                         Accuracy Value:
                      </td>
                      <td class="{$secondColStyle}">
                        <xsl:value-of select="quantitativeAccuracyValue"/>
                      </td>
                    </tr>
                    <tr><td class="{$spatialrasterfirstColStyle}">
                         Method:
                      </td>
                      <td class="{$secondColStyle}">
                        <xsl:value-of select="quantitativeAccuracyMethod"/>
                      </td>
                    </tr>
                  </xsl:for-each>
                </table>
            </td>
       </tr>
   </xsl:if>
 </xsl:template>

 <!--********************************************************
     imageDescription
     *********************************************************-->
  <xsl:template name="imageDescription">
    <xsl:param name="spatialrasterfirstColStyle"/>
    <xsl:for-each select="illuminationElevationAngle">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Illumination Elevation:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="illuminationAzimuthAngle">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Illumination Azimuth:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="imageOrientationAngle">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Image Orientation:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="imagingCondition">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Code Affectting Quality of Image:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="imageQualityCode">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Quality:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="cloudCoverPercentage">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Cloud Coverage:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="preProcessingTypeCode">
        <tr><td class="{$spatialrasterfirstColStyle}">
             PreProcessing:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="compressionGenerationQuality">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Compression Quality:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="triangulationIndicator">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Triangulation Indicator:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="radionmetricDataAvailability">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Availability of Radionmetric Data:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="cameraCalibrationInformationAvailability">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Availability of Camera Calibration Correction:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="filmDistortionInformationAvailability">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Availability of Calibration Reseau:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="lensDistortionInformationAvailability">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Availability of Lens Aberration Correction:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="bandDescription">
     <tr><td class="{$spatialrasterfirstColStyle}">
             Availability of Lens Aberration Correction:
            </td>
            <td>
               <xsl:call-template name="bandDescription">
                  <xsl:with-param name="spatialrasterfirstColStyle" select="$spatialrasterfirstColStyle"/>
               </xsl:call-template>
            </td>
      </tr>
    </xsl:for-each>
  </xsl:template>

  <!--***********************************************
      band description
      ************************************************-->
  <xsl:template name="bandDescription">
    <xsl:param name="spatialrasterfirstColStyle"/>
    <table class="{$tabledefaultStyle}">
      <xsl:for-each select="sequenceIdentifier">
        <tr><td class="{$spatialrasterfirstColStyle}">
            Sequence Identifier:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
        </tr>
     </xsl:for-each>
     <xsl:for-each select="highWavelength">
        <tr><td class="{$spatialrasterfirstColStyle}">
             High Wave Length:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
        </tr>
     </xsl:for-each>
     <xsl:for-each select="lowWaveLength">
        <tr><td class="{$spatialrasterfirstColStyle}">
             High Wave Length:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
        </tr>
     </xsl:for-each>
     <xsl:for-each select="waveLengthUnits">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Wave Length Units:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
        </tr>
     </xsl:for-each>
     <xsl:for-each select="peakResponse">
        <tr><td class="{$spatialrasterfirstColStyle}">
             Peak Response:
            </td>
            <td class="{$secondColStyle}">
                <xsl:value-of select="."/>
            </td>
        </tr>
     </xsl:for-each>
    </table>
  </xsl:template>

  <xsl:template name="spatialRasterShowDistribution">
     <xsl:param name="spatialrasterfirstColStyle"/>
     <xsl:param name="spatialrastersubHeaderStyle"/>
     <xsl:param name="docid"/>
     <xsl:param name="level">entitylevel</xsl:param>
     <xsl:param name="entitytype">spatialRaster</xsl:param>
     <xsl:param name="entityindex"/>
     <xsl:param name="physicalindex"/>

    <xsl:for-each select="distribution">
      <tr><td colspan="2">
        <xsl:call-template name="distribution">
          <xsl:with-param name="docid" select="$docid"/>
          <xsl:with-param name="level" select="$level"/>
          <xsl:with-param name="entitytype" select="$entitytype"/>
          <xsl:with-param name="entityindex" select="$entityindex"/>
          <xsl:with-param name="physicalindex" select="$physicalindex"/>
          <xsl:with-param name="distributionindex" select="position()"/>
          <xsl:with-param name="disfirstColStyle" select="$spatialrasterfirstColStyle"/>
          <xsl:with-param name="dissubHeaderStyle" select="$spatialrastersubHeaderStyle"/>
        </xsl:call-template>
      </td></tr>
    </xsl:for-each>
  </xsl:template>


  <xsl:template name="spatialRasterAttributeList">
    <xsl:param name="spatialrasterfirstColStyle"/>
    <xsl:param name="spatialrastersubHeaderStyle"/>
    <xsl:param name="docid"/>
    <xsl:param name="entitytype">spatialRaster</xsl:param>
    <xsl:param name="entityindex"/>
    <tr><td class="{$spatialrastersubHeaderStyle}" colspan="2">
        <xsl:text>Attribute(s) Info:</xsl:text>
    </td></tr>
    <tr><td colspan="2">
         <xsl:call-template name="attributelist">
           <xsl:with-param name="docid" select="$docid"/>
           <xsl:with-param name="entitytype" select="$entitytype"/>
           <xsl:with-param name="entityindex" select="$entityindex"/>
         </xsl:call-template>
       </td>
    </tr>
  </xsl:template>

</xsl:stylesheet>
