<?xml version="1.0"?>
<!--
  *    '$RCSfile: eml-documentation.xsl,v $'
  *      Authors: Chris Jones, Matt Jones
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *     '$Author: jones $'
  *       '$Date: 2001-07-11 19:10:03 $'
  *   '$Revision: 1.17 $'
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
                xmlns:xs="http://www.w3.org/2001/XMLSchema" 
                xmlns="eml:documentation" 
                xmlns:doc="eml:documentation" 
                version="1.0">
  <xsl:output method="html"/>
  <xsl:template match="/">
    <html>
      <head>
        <title>
          <xsl:value-of select="/xs:schema/xs:annotation/xs:appinfo/moduleName"/> 
          Documentation
        </title>
        <link rel="stylesheet" type="text/css" href="default.css"/>
      </head>
      <body>
        <!-- display the module name and description -->
        <div class="title">
          Module Documentation: 
          <xsl:value-of 
            select="/xs:schema/xs:annotation/xs:appinfo/moduleName"/>
        </div>
        <blockquote>
          <xsl:value-of 
            select="/xs:schema/xs:annotation/xs:appinfo/moduleDescription"/>
        </blockquote>

        <img>
          <xsl:attribute name="src">../<xsl:value-of 
            select="/xs:schema/xs:annotation/xs:appinfo/moduleName"/>.png</xsl:attribute>
        </img>

        <table border="0" class="tabledefault">
          <tr>
            <td colspan="2">
              <!-- display the documentation for each defined element -->
              <h2>Element Definitions:</h2>
            </td>
          </tr>
          <xsl:apply-templates select="//xs:element" mode="documentation"/>
          <tr>
            <td colspan="2">
              <!-- display the documentation for each defined attribute -->
              <h2>Attribute Definitions:</h2>
            </td>
          </tr>
          <xsl:apply-templates select="//xs:attribute" mode="documentation"/>
          <tr>
            <td colspan="2">
              <!-- display the documentation for each defined complex type -->
              <h2>Complex Type Definitions:</h2>
            </td>
          </tr>
          <xsl:apply-templates select="//xs:complexType" mode="documentation" />
          <tr>
            <td colspan="2">
              <!-- display the documentation for each defined simple type -->
              <h2>Simple Type Definitions:</h2>
            </td>
          </tr>
          <xsl:apply-templates select="//xs:simpleType" mode="documentation"/>
        </table>
        <p class="contact">
          Web Contact:
          <a href="mailto:jones@nceas.ucsb.edu">jones@nceas.ucsb.edu</a>
        </p>
      </body>
    </html>
  </xsl:template>
  
  <!-- step through the elements -->
  <xsl:template match="xs:element|xs:complexType" mode="documentation">
    <!-- MBJ: this should be all elements -->
    <xsl:if test="./@name">
      <tr>
        <td colspan="2" class="tablehead">
          <!--give each element an anchor name-->
          <a class="sitelink">
            <xsl:attribute name="name">
              <xsl:value-of select="./@name"/>
            </xsl:attribute>
            <!-- and display the name of the element-->
            <xsl:value-of select="./@name"/>
          </a>
        </td>
      </tr>
      <tr>
        <td class="tablepanel" width="40%">
            Content of this field:
        </td>
        <td class="tablepanel">
            Description of this field:
        </td>
      </tr>
      <tr>
        <td class="tablepanel">
          <!-- begin the inner content model table -->
          <table border="0" class="tabledefault">
            <!-- Determine if we're processing a typed element
                 or one with an explicit content model, act accordingly -->
            <xsl:choose>
              <xsl:when test="./@type">
                <tr>
                <td valign="top" class="tablepanel">
                  <!--give each attribute link to its definition -->
                  <span class="boldtext">Type: </span>
                  <xsl:choose>
                    <xsl:when test='starts-with(string(./@type), "xs:")'>
                      <span class="boldtext">
                        <xsl:value-of select="./@type"/>
                      </span>
                    </xsl:when>
                    <xsl:otherwise>
                      <a class="sitelink">
                        <xsl:attribute name="href">
                          <xsl:text>#</xsl:text><xsl:value-of select="./@type"/>
                        </xsl:attribute>
                        <xsl:value-of select="./@type"/>
                      </a>
                    </xsl:otherwise>
                  </xsl:choose>
                </td>
                </tr>
                <!-- display the attributes -->
                <tr>
                  <td valign="top" class="tablepanel">
                  <span class="boldtext">
                    Attributes:
                  </span>
                  </td>
                  <td valign="top" class="tablepanel">
                  <span class="boldtext">
                    Required?:
                  </span>
                  </td>
                  <td valign="top" class="tablepanel">
                  <span class="boldtext">
                    Default Value:
                  </span>
                  </td>
                </tr>
                <!-- Now process the CM for the attribute children -->
                <xsl:apply-templates 
                     select="xs:attribute|xs:complexType/xs:attribute" 
                     mode="contentmodel" />
              </xsl:when>
              <xsl:otherwise>

                <!-- display a link to the base type for derivations -->
                <xsl:if test="./xs:complexContent/xs:extension|./xs:complexContent/xs:restriction">
                  <xsl:variable name="baseval" 
                       select="./xs:complexContent/xs:extension/@base|./xs:complexContent/xs:restriction/@base"/>
                  <xsl:variable name="derival" 
                       select="name(./xs:complexContent/xs:extension)"/>
                  <!-- this next line probably doesn't work :-) -->
                  <xsl:if test="./xs:complexContent/xs:restriction">
                    <xsl:variable name="derival" 
                         select="name(./xs:complexContent/xs:restriction)"/>
                  </xsl:if>
                  <tr>
                  <td colspan="3" valign="top" class="tablepanel">
                    <p>
                    <span class="boldtext">Derived from: </span>
                    <xsl:choose>
                      <xsl:when test='starts-with(string($baseval), "xs:")'>
                        <span class="boldtext">
                          <xsl:value-of select="$baseval"/>
                        </span>
                      </xsl:when>
                      <xsl:otherwise>
                        <a class="sitelink">
                        <xsl:attribute name="href">
                          <xsl:text>#</xsl:text>
                          <xsl:value-of select="$baseval"/>
                        </xsl:attribute>
                        <xsl:value-of select="$baseval"/>
                        </a> 
                       </xsl:otherwise>
                     </xsl:choose>
                     (by <xsl:value-of select="$derival"/>)
                     </p>
                  </td>
                  </tr>
                </xsl:if>

                <!-- display the elements -->
                <tr>
                  <td valign="top" class="tablepanel">
                  <span class="boldtext">
                    Elements:
                  </span>
                  </td>
                  <td valign="top" class="tablepanel">
                  <span class="boldtext">
                    Required?:
                  </span>
                  </td>
                  <td valign="top" class="tablepanel">
                  <span class="boldtext">
                    How many:
                  </span>
                  </td>
                </tr>
                <!-- Now display the CM for the element children -->
                <xsl:apply-templates 
                     select="xs:complexType|xs:sequence|xs:choice|xs:element|xs:complexContent" 
                     mode="contentmodel" />
    
                <!-- display the attributes -->
                <tr>
                  <td valign="top" class="tablepanel">
                  <span class="boldtext">
                    Attributes:
                  </span>
                  </td>
                  <td valign="top" class="tablepanel">
                  <span class="boldtext">
                    Required?:
                  </span>
                  </td>
                  <td valign="top" class="tablepanel">
                  <span class="boldtext">
                    Default Value:
                  </span>
                  </td>
                </tr>
                <!-- Now display the CM for the attribute children -->
                <xsl:apply-templates 
                     select="xs:attribute|xs:complexType/xs:attribute" 
                     mode="contentmodel" />
              </xsl:otherwise>
            </xsl:choose>
            </table>
            <!-- end the inner content model table -->
          </td>
        <xsl:apply-templates select="xs:annotation" mode="helpinfo"/>
      </tr>
    </xsl:if>
  </xsl:template>
   
  <xsl:template match="xs:sequence" mode="contentmodel">
    <tr>
    <td colspan="3" class="tablepanel">
    <xsl:text> A sequence of (</xsl:text>
    </td>
    </tr>
    <!-- Find all of the children of this sequence and list them -->
    <xsl:apply-templates 
         select="xs:element|xs:complexType|xs:sequence|xs:choice" 
         mode="contentmodel" />
    <tr>
    <td colspan="3" class="tablepanel">
    <xsl:text>)</xsl:text>
    </td>
    </tr>

    <xsl:if test="name(..) = 'xs:choice'
                 and not(position()=last())">
    <tr>
    <td colspan="3" class="tablepanel">
      OR
    </td>
    </tr>
    </xsl:if>
  </xsl:template>

  <xsl:template match="xs:choice" mode="contentmodel">
    <tr>
    <td colspan="3" class="tablepanel">
    <xsl:text> A choice of (</xsl:text>
    </td>
    </tr>
    <!-- Find all of the children of this choice and list them -->
    <xsl:apply-templates 
         select="xs:element|xs:complexType|xs:sequence|xs:choice" 
         mode="contentmodel" />
    <tr>
    <td colspan="3" class="tablepanel">
    <xsl:text>)</xsl:text>
    </td>
    </tr>
  </xsl:template>

  <xsl:template match="xs:element" mode="contentmodel">
        <!--give each element a link to its definition -->
        
        <tr>
        <td class="tablepanel">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>#</xsl:text>
            <xsl:if test="./@name"><xsl:value-of select="./@name"/></xsl:if>
            <xsl:if test="./@ref"><xsl:value-of select="./@ref"/></xsl:if>
          </xsl:attribute>
          <!-- and display the name of the element -->
          <xsl:value-of select="./@name"/>
          <xsl:value-of select="./@ref"/>
        </a>
        </td>
        <td class="tablepanel">
        <xsl:choose>
          <xsl:when test="./@minOccurs &gt; '0'">Required</xsl:when>
          <xsl:otherwise>Optional</xsl:otherwise>
        </xsl:choose>
        </td>
        <td class="tablepanel">
        <xsl:choose>
          <xsl:when test="./@maxOccurs = '1'">Once</xsl:when>
          <xsl:otherwise>Multiple Times</xsl:otherwise>
        </xsl:choose>
        </td>
        </tr>

        <xsl:if test="name(..) = 'xs:choice' 
                      and not(position()=last())">
        <tr>
        <td colspan="3" class="tablepanel">
          OR
        </td>
        </tr>
        </xsl:if>
  </xsl:template>
 
  <!-- step through the attributes -->
  <xsl:template match="xs:attribute" mode="contentmodel">
        <!--give each attribute link to its definition -->
        <tr>
        <td class="tablepanel">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>#</xsl:text>
            <xsl:if test="./@name"><xsl:value-of select="./@name"/></xsl:if>
            <xsl:if test="./@ref"><xsl:value-of select="./@ref"/></xsl:if>
          </xsl:attribute>
          <!-- and display the name of the attribute-->
          <xsl:value-of select="./@name"/>
          <xsl:value-of select="./@ref"/>
        </a>
        </td>

        <!-- display the use -->
        <xsl:if test="./@use">
        <td class="tablepanel">
          <span class="plaintext">
            <xsl:value-of select="./@use"/>
          </span> 
        </td>
        </xsl:if>

        <!-- display the default value -->
        <xsl:if test="./@value">
        <td class="tablepanel">
          <span class="plaintext">
            <xsl:value-of select="./@value"/>
          </span>
        </td>
        </xsl:if>
        </tr>
  </xsl:template>
  
  <!-- step through the attributes -->
  <xsl:template match="xs:attribute" mode="documentation">
    <tr>
      <td class="tablehead" colspan="2">
        <!--give each attribute an anchor name-->
        <a class="sitelink">
          <xsl:attribute name="name">
            <xsl:if test="./@name">
            <xsl:value-of select="./@name"/>
            </xsl:if>
            <xsl:if test="./@ref">
            <xsl:value-of select="./@ref"/>
            </xsl:if>
          </xsl:attribute>
          <!-- and display the name of the attribute-->
          <xsl:value-of select="./@name"/>
          <xsl:value-of select="./@ref"/>
        </a>
      </td>
    </tr>
    <tr>
      <td class="tablepanel">
        <!-- display the type -->
        <xsl:if test="./@type">
          <p>
          <span class="boldtext">Type: </span>
          <span class="plaintext">
            <xsl:choose>
              <xsl:when test='starts-with(string(./@type), "xs:")'>
                <span class="boldtext"><xsl:value-of select="./@type"/></span>
              </xsl:when>
              <xsl:otherwise>
                <a class="sitelink">
                  <xsl:attribute name="href">
                    <xsl:text>#</xsl:text><xsl:value-of select="./@type"/>
                  </xsl:attribute>
                  <xsl:value-of select="./@type"/>
                </a>
              </xsl:otherwise>
            </xsl:choose>
          </span>
          </p>
        </xsl:if>

        <!-- display the use -->
        <xsl:if test="./@use">
          <p>
          <span class="boldtext">Use: </span>
          <span class="plaintext">
            <xsl:value-of select="./@use"/>
          </span></p>
        </xsl:if>

        <!-- display the default value -->
        <xsl:if test="./@value">
          <p>
          <span class="boldtext">Default value: </span>
          <span class="plaintext">
            <xsl:value-of select="./@value"/>
          </span></p>
        </xsl:if>
      </td>
      <xsl:apply-templates select="xs:annotation" mode="helpinfo"/>
    </tr>
  </xsl:template>
  
  <!-- format the complexType content model -->
  <xsl:template match="xs:complexType" mode="contentmodel">
    <!-- Find all of the children of this complexType and list them -->
    <xsl:apply-templates 
         select="xs:sequence|xs:choice|xs:element|xs:complexContent" 
         mode="contentmodel" />
  </xsl:template>

  <!-- step through the simpleTypes -->
  <xsl:template match="xs:simpleType" mode="documentation">
    <tr>
      <td colspan="2" class="tablehead">
        <h3>
          <a class="sitelink">
            <xsl:attribute name="name">
              <xsl:value-of select="./@name"/>
            </xsl:attribute>
            <xsl:value-of select="./@name"/>
          </a>
        </h3>
      </td>
    </tr>
    <tr>
      <td class="tablepanel"> 
        <xsl:apply-templates select="xs:extension|xs:restriction"
                             mode="contentmodel"/>
      </td>
      <xsl:apply-templates select="xs:annotation" mode="helpinfo"/>
    </tr>
  </xsl:template>

  <!-- format the complexContent content model -->
  <xsl:template match="xs:complexContent" mode="contentmodel">
    <!-- Find all of the children of this complexContent and list them -->
    <xsl:apply-templates select="xs:extension|xs:restriction" 
                         mode="contentmodel" />
  </xsl:template>

  <!-- format the extension and restriction content model -->
  <xsl:template match="xs:extension|xs:restriction" mode="contentmodel">
    <!-- display a link to the base type for derivations -->
      <p>
      <span class="boldtext">Derived from: </span>
      <xsl:choose>
        <xsl:when test='starts-with(string(./@base), "xs:")'>
          <span class="boldtext">
            <xsl:value-of select="./@base"/>
          </span>
        </xsl:when>
        <xsl:otherwise>
          <a class="sitelink">
            <xsl:attribute name="href">
              <xsl:text>#</xsl:text>
              <xsl:value-of select="./@base"/>
            </xsl:attribute>
            <xsl:value-of select="./@base"/>
          </a> 
        </xsl:otherwise>
      </xsl:choose>
      (by <xsl:value-of select="name(.)"/>)
      </p>

    <!-- Find all of the children and list them -->
    <xsl:choose>
      <xsl:when test="name(..) = 'xs:simpleType'">
        
        <p>
        <span class="boldtext">Allowed values: </span>
        <ul>
          <xsl:apply-templates select="xs:enumeration" mode="contentmodel" />
        </ul>
        </p>
      </xsl:when> 
      <xsl:otherwise>
        <xsl:apply-templates select="xs:sequence|xs:choice" 
                             mode="contentmodel" />
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- format the enumeration content model -->
  <xsl:template match="xs:enumeration" mode="contentmodel">
    <li><xsl:value-of select="./@value"/></li>
  </xsl:template>

  <!-- This template formats the various types of help information
       that are provided in the xsd file -->
  <xsl:template match="xs:annotation" mode="helpinfo">
        <td colspan="1" class="tablepanel" valign="top">
          <!-- display the help fields for each element-->
          <blockquote>
            <br><span class="boldtext">Tooltip: </span></br>
            <xsl:value-of select="./xs:appinfo/tooltip"/>
            <br><span class="boldtext">Summary: </span></br>
            <xsl:value-of select="./xs:appinfo/summary"/>
            <br><span class="boldtext">Description: </span></br>
            <xsl:value-of select="./xs:appinfo/description"/>
            <br><span class="boldtext">Example: </span></br>
            <xsl:value-of select="./xs:appinfo/example"/>
            <br><span class="boldtext">Lineage: </span></br>
            <xsl:value-of select="./xs:appinfo/lineage"/>
          </blockquote>
        </td>
  </xsl:template>
 
</xsl:stylesheet>
