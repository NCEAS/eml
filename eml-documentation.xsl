<?xml version="1.0"?>
<!--
  *    '$RCSfile: eml-documentation.xsl,v $'
  *      Authors: Chris Jones
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *     '$Author: cjones $'
  *       '$Date: 2001-02-16 07:50:21 $'
  *   '$Revision: 1.4 $'
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

        <table class="tabledefault">
          <tr>
            <td colspan="5">
              <!-- display the documentation for each defined element -->
              <h2>Element Definitions:</h2>
              The elements described below are either containers for other
              elements, or are containers for values provided by the person
              using the module:
            </td>
          </tr>
          <!-- step through the elements that have annotation children-->
          <xsl:for-each select ="//xs:element[xs:annotation]">
          <tr>
            <td class="tablehead">
              <!--give each element an anchor name-->
              <a class="sitelink">
                <xsl:attribute name="name">
                  <xsl:if test="./@name">
                  <xsl:value-of select="./@name" />
                  </xsl:if>
                  <xsl:if test="./@ref">
                  <xsl:value-of select="./@ref" />
                  </xsl:if>
                </xsl:attribute>
                <!-- and display the name of the element-->
                <xsl:value-of select="./@name" />
                <xsl:value-of select="./@ref" />
              </a>
            </td>
            <td class="tablehead">
                <!-- display the use -->
                Required:
              <xsl:if test="./@minOccurs &gt; '0'">
              <span class="plaintext">
                Yes
              </span>
              </xsl:if>
              <xsl:if test="./@minOccurs = '0'">
              <span class="plaintext">
                No
              </span>
              </xsl:if>
            </td>
            <td class="tablehead">
              <!-- display the cardinality -->
              How many:
              <span class="plaintext">
                <xsl:value-of select="./@minOccurs" />
                to
                <xsl:value-of select="./@maxOccurs" />
              </span>
            </td>
            <td class="tablehead">
              <!-- display a link to the element's abstract type -->
              Type: 
              <a>
                <xsl:attribute name="href">
                  #<xsl:value-of select="./@type" />
                </xsl:attribute>
                <xsl:value-of select="./@type" />
              </a>
            </td>
            <td class="tablehead">
              <!--Attributes:-->
            </td>
          </tr>
          <tr>
            <td colspan="5" class="tablepanel">
              <!-- display the help fields for each element-->
              <blockquote>
                <br><span class="boldtext">Tooltip: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/tooltip" />
                <br><span class="boldtext">Summary: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/summary" />
                <br><span class="boldtext">Description: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/description" />
                <br><span class="boldtext">Example: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/example" />
                <br><span class="boldtext">Lineage: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/lineage" />
              </blockquote>
            </td>
          </tr>
          </xsl:for-each>
          <tr>
            <td colspan="5">
              <!-- display the documentation for each defined attribute -->
              <h2>Attribute Definitions:</h2>
            </td>
          </tr>
          <xsl:for-each select ="//xs:attribute[xs:annotation]">
            <tr>
              <td class="tablehead">
                <!--give each attribute an anchor name-->
                <a class="sitelink">
                  <xsl:attribute name="name">
                    <xsl:if test="./@name">
                    <xsl:value-of select="./@name" />
                    </xsl:if>
                    <xsl:if test="./@ref">
                    <xsl:value-of select="./@ref" />
                    </xsl:if>
                  </xsl:attribute>
                  <!-- and display the name of the attribute-->
                  <xsl:value-of select="./@name" />
                  <xsl:value-of select="./@ref" />
                </a>
              </td>
              <td class="tablehead">
                <!-- display the use -->
                  Use:
                <span class="plaintext">
		<xs:value-of select="./@use" />
                </span>
              </td>
              <td colspan="3" class="tablehead">
                <!-- display the default value -->
                Default value:
                <span class="plaintext">
                  <xsl:value-of select="./@value" />
                </span>
              </td>
            </tr>
            <tr>
              <td colspan="5" class="tablepanel">
                <!-- display the help fields for each element-->
                <blockquote>
                  <br><span class="boldtext">Tooltip: </span></br>
                  <xsl:value-of select="./xs:annotation/xs:appInfo/tooltip" />
                  <br><span class="boldtext">Summary: </span></br>
                  <xsl:value-of select="./xs:annotation/xs:appInfo/summary" />
                  <br><span class="boldtext">Description: </span></br>
                  <xsl:value-of select="./xs:annotation/xs:appInfo/description" />
                  <br><span class="boldtext">Example: </span></br>
                  <xsl:value-of select="./xs:annotation/xs:appInfo/example" />
                  <br><span class="boldtext">Lineage: </span></br>
                  <xsl:value-of select="./xs:annotation/xs:appInfo/lineage" />
                </blockquote>
              </td>
            </tr>
          </xsl:for-each>
          <tr>
            <td colspan="5">
              <!-- display the documentation for each defined complex type -->
              <h2>Complex Type Definitions:</h2>
            </td>
          </tr>
          <xsl:for-each select ="//xs:complexType">
          <tr>
            <td colspan="5" class="tablehead">
              <h3>
                <a class="sitelink">
                  <xsl:attribute name="name">
                    <xsl:value-of select="./@name" />
                  </xsl:attribute>
                  <xsl:value-of select="./@name" />
                </a>
              </h3>
            </td>
          </tr>
          <tr>
            <td colspan="5" class="tablepanel">
              <blockquote>
                <br><span class="boldtext">Tooltip: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/tooltip" />
                <br><span class="boldtext">Summary: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/summary" />
                <br><span class="boldtext">Description: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/description" />
                <br><span class="boldtext">Example: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/example" />
                <br><span class="boldtext">Lineage: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/lineage" />
              </blockquote>
            </td>
          </tr>
          </xsl:for-each>
          <tr>
            <td colspan="5">
              <!-- display the documentation for each defined simple type -->
              <h2>Simple Type Definitions:</h2>
            </td>
          </tr>
          <xsl:for-each select ="//xs:simpleType[xs:annotation]">
          <tr>
            <td colspan="5" class="tablehead">
              <h3>
                <a class="sitelink">
                  <xsl:attribute name="name">
                    <xsl:value-of select="./@name" />
                  </xsl:attribute>
                  <xsl:value-of select="./@name" />
                </a>
              </h3>
            </td>
          </tr>
          <tr>
            <td colspan="5" class="tablepanel">
              <blockquote>
                <br><span class="boldtext">Tooltip: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/tooltip" />
                <br><span class="boldtext">Summary: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/summary" />
                <br><span class="boldtext">Description: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/description" />
                <br><span class="boldtext">Example: </span></br>
                <xsl:value-of select="./xs:annotation/xs:appInfo/example" />
                <br><span class="boldtext">Lineage: </span></br>
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
