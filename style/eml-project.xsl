<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-project.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: berkley $'
  *     '$Date: 2002-04-19 22:49:44 $'
  * '$Revision: 1.4 $'
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
<xsl:import href="eml-settings.xsl" />

  <xsl:output method="html" encoding="iso-8859-1"/>

  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" 
              href="{$stylePath}/{$qformat}.css" />
      </head>
      <body>
        <center>
          <h1>Project Description</h1>
          <h3>Ecological Metadata Language</h3><br />
        </center>
        <xsl:apply-templates select="eml-project/identifier"/>
        <xsl:apply-templates select="eml-project/researchProject"/>
      </body>
    </html>
  </xsl:template>
  
  <xsl:template match="researchProject">
    <table border="1">
      <tr>
      <td class="highlight"><h3>Personnel</h3></td>
      <td><xsl:apply-templates select="personnel"/></td>
      </tr>
      <tr>
      <td><h3>Temporal Coverage</h3></td>
      <td><xsl:apply-templates select="temporalCov"/></td>
      </tr>
      <tr>
      <td class="highlight"><h3>Geographic Coverage</h3></td>
      <td><xsl:apply-templates select="geographicCov"/></td>
      </tr>
      <tr>
      <td class="highlight"><h3>Abstract</h3></td>
      <td><xsl:apply-templates select="abstract"/></td>
      </tr>
      <tr>
      <td class="highlight"><h3>Funding</h3></td>
      <td><xsl:apply-templates select="funding"/></td>
      </tr>
      <tr>
      <td class="highlight"><h3>Site Description</h3></td>
      <td><xsl:apply-templates select="siteDescription"/></td>
      </tr>
      <tr>
      <td class="highlight"><h3>Design Description</h3></td>
      <td><xsl:apply-templates select="designDescription"/></td>
      </tr>
      <tr>
      <td class="highlight"><h3>Additional Projects</h3></td>
      <td><xsl:apply-templates select="researchProject"/></td>
      </tr>
      </table>
  </xsl:template>
  
  <xsl:template match="designDescription">
    <table>
      <tr>
        <td>Protocol</td>
        <td><xsl:apply-templates select="protocol"/></td>
      </tr>
      <tr>
        <td>Sampling</td>
        <td><xsl:apply-templates select="sampling"/></td>
      </tr>
      <tr>
        <td>Additional Info</td>
        <td><xsl:apply-templates select="paragraph"/></td>
      </tr>
      <tr>
        <td>Citation</td>
        <td><xsl:apply-templates select="citation"/></td>
      </tr>
    </table>
  </xsl:template>
  
  <xsl:template match="sampling">
    <table border="1">
      <tr>
        <td>Temporal Coverage</td>
        <td><xsl:apply-templates select="temporalCov"/></td>
      </tr>
      <tr>
        <td>Frequency</td>
        <td><xsl:apply-templates select="frequency"/></td>
      </tr>
      <tr>
        <td>Additional Information</td>
        <td><xsl:apply-templates select="paragraph"/></td>
      </tr>
      <tr>
        <td>Citation</td>
        <td><xsl:apply-templates select="citation"/></td>
      </tr>
    </table>
  </xsl:template>
  
  <xsl:template match="protocol">
    <table border="1">
      <tr>
        <td>Method</td>
        <td><xsl:apply-templates select="method"/></td>
      </tr>
      <tr>
        <td>Processing Step</td>
        <td><xsl:apply-templates select="processingStep"/></td>
      </tr>
      <tr>
        <td>Quality Control</td>
        <td><xsl:apply-templates select="qualityControl"/></td>
      </tr>
    </table>
  </xsl:template>
  
  <xsl:template match="funding">
    <xsl:apply-templates/>
  </xsl:template>
  
  <xsl:template match="identifier">
    <table>
    <tr>
      <td><b>Metadata Identifier:</b></td>
      <td><xsl:value-of select="."/></td>
    </tr>
    </table>
  </xsl:template>

  <xsl:template match="title">
    <tr>
      <td class="highlight"><b>Title:</b></td>
      <td><xsl:value-of select="."/></td>
    </tr>
  </xsl:template>

  <xsl:template match="personnel">
    <table width="100%">
      <tr>
        <td width="30%"><xsl:apply-templates select="individualName"/>
        </td>
        <td>
          <table>
          <tr><td>Organization: <xsl:value-of select="organizationName"/></td></tr>
          <tr><td>Position: <xsl:value-of select="positionName"/></td></tr>
          <tr><td>Address: <xsl:apply-templates select="address"/></td></tr>
          <tr><td>Phone: <xsl:value-of select="phone"/></td></tr>
          <tr><td>Email Address: <xsl:value-of select="electronicMailAddress"/></td></tr>
          <tr><td>Web Address: <xsl:value-of select="onlineLink"/></td></tr>
          <tr><td>Role: <xsl:value-of select="role"/></td></tr>
          </table>
        </td>
      </tr>
    </table>
    <br/>
  </xsl:template>

  <xsl:template match="individualName">
    <b>
       <xsl:value-of select="./salutation"/>
       <xsl:text> </xsl:text>
       <xsl:value-of select="./givenName"/>
       <xsl:text> </xsl:text>
       <xsl:value-of select="./surName"/>
    </b><br />
  </xsl:template>

  <xsl:template match="address">
    <table>
    <xsl:for-each select="./deliveryPoint">
      <tr>
      <td><xsl:value-of select="."/></td>
      </tr>
    </xsl:for-each>
    <tr>
    <td><xsl:value-of select="./city"/>
        <xsl:text>, </xsl:text>
        <xsl:value-of select="./administrativeArea"/>
        <xsl:text> </xsl:text>
        <xsl:value-of select="./postalCode"/>
    </td>
    </tr>
    <xsl:if test="./country">
      <tr><td><xsl:value-of select="./country"/></td></tr>
    </xsl:if>
    </table>
  </xsl:template>

  <xsl:template match="phone">
      <xsl:text>Phone: </xsl:text><xsl:value-of select="."/><br />
  </xsl:template>

  <xsl:template match="electronicMailAddress">
      <xsl:text>Email: </xsl:text><xsl:value-of select="."/><br />
  </xsl:template>

  <xsl:template match="onlineLink">
      <xsl:text>URL: </xsl:text><xsl:value-of select="."/><br />
  </xsl:template>

  <xsl:template match="role">
      <xsl:text>Role: </xsl:text><xsl:value-of select="."/><br />
  </xsl:template>
  
  <xsl:template match="paragraph">
    <p><xsl:value-of select="."/></p>
  </xsl:template>

  <xsl:template match="abstract">
    <xsl:apply-templates select="paragraph"/>
  </xsl:template>
  
  
  
  
  <xsl:template match="temporalCov">
    <xsl:apply-templates/>
  </xsl:template>
  
  

  <xsl:template match="rangeOfDates">
    <tr><td>
    <xsl:text>Date Range: </xsl:text>
    <xsl:apply-templates select="begdate"/>
    <xsl:text> </xsl:text><xsl:apply-templates select="beginningTime"/>
    <xsl:text> </xsl:text><xsl:apply-templates select="beginningGeologicAge"/>
    <xsl:text> to </xsl:text>
    <xsl:apply-templates select="enddate"/>
    <xsl:text> </xsl:text><xsl:apply-templates select="endingTime"/>
    <xsl:text> </xsl:text><xsl:apply-templates select="endingDate"/>
    </td></tr>
  </xsl:template>

  <xsl:template match="singleDate">
    <tr><td>
    <xsl:text>Date: </xsl:text>
    <xsl:apply-templates select="calendarDate"/>
    <xsl:text> </xsl:text><xsl:apply-templates select="time"/>
    <xsl:text> </xsl:text><xsl:apply-templates select="geologicAge"/>
    </td></tr>
  </xsl:template>

  <xsl:template match="multipleDatesTimes">
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="geographicCov">
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="geographicDescription">
    <p>
    <xsl:text>Description: </xsl:text>
    <xsl:value-of select="."/>
    </p>
  </xsl:template>

  <xsl:template match="westBoundingCoordinate">
    <xsl:text>West Bounding coordinate: </xsl:text>
    <xsl:value-of select="."/> degrees<br />
  </xsl:template>

  <xsl:template match="eastBoundingCoordinate">
    <xsl:text>East Bounding coordinate: </xsl:text>
    <xsl:value-of select="."/> degrees<br />
  </xsl:template>

  <xsl:template match="northBoundingCoordinate">
    <xsl:text>North Bounding coordinate: </xsl:text>
    <xsl:value-of select="."/> degrees<br />
  </xsl:template>

  <xsl:template match="southBoundingCoordinate">
    <xsl:text>South Bounding coordinate: </xsl:text>
    <xsl:value-of select="."/> degrees<br />
  </xsl:template>
  
  <xsl:template match="datasetGPolygonOuterGRing">
    <xsl:text>GPolygon Outer Ring:</xsl:text>
    <xsl:apply-templates select="gRingPoint"/>
    <xsl:apply-templates select="gRing"/>
  </xsl:template>
  
  <xsl:template match="gRing">
    <xsl:text>GRing: </xsl:text>
    <xsl:value-of select="."/>
  </xsl:template>
  
  <xsl:template match="gRingPoint">
    <xsl:text>Latitude: </xsl:text>
    <xsl:value-of select="./gRingLatitude"/>, 
    <xsl:text>Longitude: </xsl:text>
    <xsl:value-of select="./gRingLongitude"/><br/>
  </xsl:template>
  
  <xsl:template match="boundingAltitudes">
    <table>
    <tr>
      <td>
        <xsl:text>Bounding Altitudes: </xsl:text>
      </td>
      <td>
        <xsl:text>Minimum: </xsl:text>
        <xsl:value-of select="./altitudeMinimum"/>, 
        <xsl:text>Maximum: </xsl:text>
        <xsl:value-of select="./altitudeMaximum"/>
      </td>
    </tr>
    </table>
  </xsl:template>
</xsl:stylesheet>
