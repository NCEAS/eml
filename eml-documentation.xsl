<?xml version="1.0"?>
<!--
  *    '$RCSfile: eml-documentation.xsl,v $'
  *      Authors: Chris Jones
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *     '$Author: bojilova $'
  *       '$Date: 2001-02-15 17:14:57 $'
  *   '$Revision: 1.2 $'
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
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xs="http://www.w3.org/2000/10/XMLSchema" 
                version="1.0">
  <xsl:output method="html" />
  <xsl:template match="/">
    <html>
      <head>
        <title>
          <xsl:value-of select="//moduleName" /> Documentation
        </title>
        <link rel="stylesheet" type="text/css" href="default.css"/>
      </head>
      <body>

        <!-- display the module name and description -->
        <div class="title">
          Module Documentation: <xsl:value-of select="//moduleName" />
        </div>
        <blockquote>
          <xsl:value-of select="//moduleDescription" />
        </blockquote>

        <table class="tabledefault" width="100%">
          <tr>
	    <td>
              <!-- display the documentation for each defined element -->
              <h3>Element Definitions:</h3>
	    </td>
	  </tr>
          <xsl:for-each select="//xs:element[xs:annotation]">
	  <tr>
	    <td class="tablehead">
              <xsl:value-of select="./@name" />
              <xsl:value-of select="./@ref" />
	    </td>
	  </tr>
	  <tr>
	    <td class="tablepanel">
              <blockquote>
                <h4>Tooltip: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/tooltip" />
                <h4>Summary: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/summary" />
                <h4>Description: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/description" />
                <h4>Example: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/example" />
                <h4>Lineage: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/lineage" />
              </blockquote>
	    </td>
	  </tr>
          </xsl:for-each>
	  <tr>
	    <td>
              <!-- display the documentation for each defined complex type -->
              <h3>Complex Type Definitions:</h3>
	    </td>
	  </tr>
          <xsl:for-each select ="//xs:complexType[xs:annotation]">
	  <tr>
	    <td class="tablehead">
              <xsl:value-of select="./@name" />
	    </td>
	  </tr>
	  <tr>
	    <td class="tablepanel">
              <blockquote>
                <h4>Tooltip: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/tooltip" />
                <h4>Summary: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/summary" />
                <h4>Description: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/description" />
                <h4>Example: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/example" />
                <h4>Lineage: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/lineage" />
              </blockquote>
	    </td>
	  </tr>
          </xsl:for-each>
	  <tr>
	    <td>
              <!-- display the documentation for each defined simple type -->
              <h3>Simple Type Definitions:</h3>
	    </td>
	  </tr>
          <xsl:for-each select ="//xs:simpleType[xs:annotation]">
	  <tr>
	    <td class="tablehead">
              <xsl:value-of select="./@name" />
	    </td>
	  </tr>
	  <tr>
	    <td class="tablepanel">
              <blockquote>
                <h4>Tooltip: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/tooltip" />
                <h4>Summary: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/summary" />
                <h4>Description: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/description" />
                <h4>Example: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/example" />
                <h4>Lineage: </h4>
                <xsl:value-of select="./xs:annotation/xs:appInfo/lineage" />
              </blockquote>
	    </td>
	  </tr>
          </xsl:for-each>
        </table>
        <p class="contact">
          Web Contact:
          <a href="mailto:jones@nceas.ucsb.edu">jones@nceas.ucsb.edu</a>
        </p>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>

