<?xml version="1.0"?>
<!--
 *     '$RCSfile: eml-documentation.xsl,v $'
 *     Copyright: 1997-2002 Regents of the University of California,
 *                          University of New Mexico, and
 *                          Arizona State University
 *      Sponsors: National Center for Ecological Analysis and Synthesis and
 *                Partnership for Interdisciplinary Studies of Coastal Oceans,
 *                   University of California Santa Barbara
 *                Long-Term Ecological Research Network Office,
 *                   University of New Mexico
 *                Center for Environmental Studies, Arizona State University
 * Other funding: National Science Foundation (see README for details)
 *                The David and Lucile Packard Foundation
 *   For Details: http://knb.ecoinformatics.org/
 *
 *      '$Author: obrien $'
 *        '$Date: 2009-03-17 17:00:12 $'
 *    '$Revision: 1.58 $'
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:doc="eml://ecoinformatics.org/documentation-2.1.0" version="1.0">
  <xsl:output method="html"></xsl:output>
  <xsl:template match="/">
    <html>
      <head>
        <title>
          <xsl:value-of select="//doc:moduleName"></xsl:value-of> Documentation </title>
        <link rel="stylesheet" type="text/css" href="../default.css"></link>
      </head>
      <body>
        <!-- display the module name and description -->
        <table border="0" width="100%">
          <tr valign="top">
            <td>
              <div class="title"> Module Documentation: <xsl:value-of select="//doc:moduleName"
                ></xsl:value-of>
              </div>
            </td>
            <td>
              <a href="index.html" class="navlink">Back to EML Contents</a>
            </td>
          </tr>
        </table>
        <!--<blockquote>
          <xsl:value-of
            select="//doc:moduleDescription"/>
        </blockquote>-->
        <xsl:apply-templates select="//doc:moduleDescription" mode="docbook"></xsl:apply-templates>
        <xsl:apply-templates select="//doc:moduleDocs" mode="detailtable"></xsl:apply-templates>
        <!--
        <img>
          <xsl:attribute name="src"><xsl:value-of
            select="//doc:moduleName"/>.png</xsl:attribute>
        </img>-->


        <table border="0" class="tabledefault">
          <xsl:if test="count(//xs:element) > 0">
            <tr>
              <td colspan="2">
                <!-- display the documentation for each defined element -->
                <h2>Element Definitions:</h2>
              </td>
            </tr>
            <xsl:apply-templates select="//xs:element" mode="documentation"></xsl:apply-templates>
          </xsl:if>

          <xsl:if test="count(//xs:attribute) > 0">
            <tr>
              <td colspan="2">
                <!-- display the documentation for each defined attribute -->
                <h2>Attribute Definitions:</h2>
              </td>
            </tr>
            <xsl:apply-templates select="//xs:attribute" mode="documentation"></xsl:apply-templates>
          </xsl:if>

          <xsl:if test="count(//xs:complexType) > 0">
            <tr>
              <td colspan="2">
                <!-- display the documentation for each defined complex type -->
                <h2>Complex Type Definitions:</h2>
              </td>
            </tr>
            <xsl:apply-templates select="//xs:complexType" mode="documentation"
            ></xsl:apply-templates>
          </xsl:if>

          <xsl:if test="count(//xs:simpleType) > 0">
            <tr>
              <td colspan="2">
                <!-- display the documentation for each defined simple type -->
                <h2>Simple Type Definitions:</h2>
              </td>
            </tr>
            <xsl:apply-templates select="//xs:simpleType" mode="documentation"
            ></xsl:apply-templates>
          </xsl:if>

          <xsl:if test="count(//xs:group) > 0">
            <tr>
              <td colspan="2">
                <!-- display the documentation for each defined simple type -->
                <h2>Group Definitions:</h2>
              </td>
            </tr>
            <xsl:apply-templates select="//xs:group" mode="documentation"></xsl:apply-templates>
          </xsl:if>
        </table>
        <p class="contact"> Web Contact: <a href="mailto:jones@nceas.ucsb.edu"
          >jones@nceas.ucsb.edu</a>
        </p>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="doc:moduleDescription" mode="docbook">
    <xsl:apply-templates mode="docbook"></xsl:apply-templates>
  </xsl:template>

  <xsl:template match="section" mode="docbook">
    <xsl:apply-templates mode="docbook"></xsl:apply-templates>
  </xsl:template>

  <xsl:template match="para" mode="docbook">
    <p>
      <xsl:apply-templates mode="docbook"></xsl:apply-templates>
    </p>
  </xsl:template>

  <xsl:template match="literalLayout" mode="docbook">
    <pre>
      <xsl:value-of select="."></xsl:value-of>
    </pre>
  </xsl:template>

  <xsl:template match="ulink" mode="docbook">
    <a>
      <xsl:attribute name="href">
        <xsl:value-of select="./@url"></xsl:value-of>
      </xsl:attribute>
      <xsl:value-of select="."></xsl:value-of>
    </a>
  </xsl:template>

  <xsl:template match="doc:description" mode="docbook">
    <xsl:apply-templates mode="docbook"></xsl:apply-templates>
  </xsl:template>


  <xsl:template match="title" mode="docbook">
    <div class="sectiontitle">
      <xsl:value-of select="."></xsl:value-of>
    </div>
  </xsl:template>

  <!-- Build the Detail Table after the Module documentation description -->
  <xsl:template match="doc:moduleDocs" mode="detailtable">
    <xsl:param name="importedBy"></xsl:param>
    <div class="title">Module details</div>
    <table class="tabledefault" border="0">
      <xsl:attribute name="id">
        <xsl:value-of select="./doc:moduleName"></xsl:value-of>
      </xsl:attribute>
        <tr>
        <td class="tablepanel"> Recommended Usage: </td>
        <td class="tablepanel">
          <xsl:value-of select="normalize-space(./doc:recommendedUsage)"></xsl:value-of>
        </td>
      </tr>
      <tr>
        <td class="tablepanel"> Stand-alone: </td>
        <td class="tablepanel">
          <xsl:value-of select="normalize-space(./doc:standAlone)"></xsl:value-of>
        </td>
      </tr>
      <tr>
        <td class="tablepanel"> Imports: </td>
        <td class="tablepanel">
          <xsl:variable name="importedItem">
            <xsl:for-each select="/xs:schema/xs:import">
              <xsl:value-of
                select="substring(normalize-space(@schemaLocation), 0,
                  string-length(normalize-space(@schemaLocation))-3)"></xsl:value-of>
              <xsl:text>, </xsl:text>
            </xsl:for-each>
          </xsl:variable>
          <xsl:value-of select="substring($importedItem, 0, string-length($importedItem) - 1)"
          ></xsl:value-of>
        </td>
      </tr>
      <tr>
        <td class="tablepanel"> Imported By: </td>
        <td class="tablepanel">
          <xsl:value-of select="substring($importedBy, 0, string-length($importedBy) - 1)"
          ></xsl:value-of>
        </td>
      </tr>
      
      <xsl:choose>
        <xsl:when test="//doc:moduleName = 'eml-unitTypeDefinitions' ">
          <!-- no image row -->
          </xsl:when>
    <xsl:otherwise>
      <tr>
        <td class="tablepanel"> View an image of the schema: </td>
        <td class="tablepanel">
          <a>
            <xsl:attribute name="href">
              <xsl:value-of select="//doc:moduleName"></xsl:value-of>.png</xsl:attribute>
            <xsl:attribute name="target">offline</xsl:attribute>
            <xsl:value-of select="//doc:moduleName"></xsl:value-of> image</a>
        </td>
      </tr>
    </xsl:otherwise>
    </xsl:choose>
    
    </table>
  </xsl:template>

  <!-- step through the elements -->
  <xsl:template match="xs:element|xs:complexType|xs:group" mode="documentation">
    <!-- MBJ: this should be all elements -->
    <xsl:if test="./@name">
      <tr>
        <td colspan="1" class="tablehead">
          <!--give each element an anchor name-->
          <a class="sitelink">
            <xsl:attribute name="name">
              <xsl:value-of select="./@name"></xsl:value-of>
            </xsl:attribute>
            <!-- and display the name of the element-->
            <xsl:value-of select="./@name"></xsl:value-of>&#160; </a>
        </td>
        <xsl:choose>
          <xsl:when test="name(.)='xs:element'">
            <td colspan="1" class="tablehead">
              <xsl:choose>
                <xsl:when test="./@default">This element has a default value of '<xsl:value-of
                    select="./@default"></xsl:value-of>'</xsl:when>
                <xsl:otherwise>This element has no default value.</xsl:otherwise>
              </xsl:choose>
            </td>
          </xsl:when>
          <xsl:otherwise>
            <td colspan="1" class="tablehead">
              <xsl:text>
              </xsl:text>
            </td>
          </xsl:otherwise>
        </xsl:choose>
      </tr>
      <tr>
        <td class="tablepanel" width="40%"> Content of this field: </td>
        <td class="tablepanel"> Description of this field: </td>
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
                      <xsl:when test="starts-with(string(./@type), &quot;xs:&quot;)">
                        <span class="boldtext">
                          <xsl:value-of select="./@type"></xsl:value-of>
                        </span>
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:call-template name="externalNamespaceLink">
                          <xsl:with-param name="typename" select="./@type"></xsl:with-param>
                        </xsl:call-template>
                      </xsl:otherwise>
                    </xsl:choose>
                  </td>
                </tr>
                <!-- display the attributes -->
                <xsl:if test="count(xs:attribute|xs:complexType/xs:attribute) &gt; 0">
                  <tr>
                    <td valign="top" class="tablepanel">
                      <span class="boldtext"> Attributes: </span>
                    </td>
                    <td valign="top" class="tablepanel">
                      <span class="boldtext"> Use: </span>
                    </td>
                    <td valign="top" class="tablepanel">
                      <span class="boldtext"> Default Value: </span>
                      <xsl:value-of select="./@default"></xsl:value-of>
                    </td>
                  </tr>
                </xsl:if>
                <!-- Now process the CM for the attribute children -->
                <xsl:apply-templates select="xs:attribute|xs:complexType/xs:attribute"
                  mode="contentmodel"></xsl:apply-templates>
              </xsl:when>
              <xsl:otherwise>

                <!-- display a link to the base type for derivations -->
                <xsl:if test="./xs:complexContent/xs:extension|./xs:complexContent/xs:restriction">
                  <xsl:variable name="baseval"
                    select="./xs:complexContent/xs:extension/@base|./xs:complexContent/xs:restriction/@base"/>

                  <!-- use a variable for what kind of derived content,  -->
                  <xsl:choose>
                    <!-- by extension -->
                    <xsl:when test="./xs:complexContent/xs:extension">
                      <xsl:variable name="derival" select="name(./xs:complexContent/xs:extension)"/>
                      <tr>
                        <td colspan="3" valign="top" class="tablepanel">
                          <p>
                            <span class="boldtext">Derived from: </span>
                            <xsl:choose>
                              <xsl:when test="starts-with(string($baseval), &quot;xs:&quot;)">
                                <span class="boldtext">
                                  <xsl:value-of select="$baseval"></xsl:value-of>
                                </span>
                              </xsl:when>
                              <xsl:otherwise>
                                <a class="sitelink">
                                  <xsl:attribute name="href">
                                    <xsl:text>#</xsl:text>
                                    <xsl:value-of select="$baseval"></xsl:value-of>
                                  </xsl:attribute>
                                  <xsl:value-of select="$baseval"></xsl:value-of>
                                </a>
                              </xsl:otherwise>
                            </xsl:choose> (by <xsl:value-of select="$derival"></xsl:value-of>) </p>
                        </td>
                      </tr>
                    </xsl:when>
                    <!-- by restriction -->
                    <xsl:when test="./xs:complexContent/xs:restriction">
                      <xsl:variable name="derival" select="name(./xs:complexContent/xs:restriction)"/>
                      <tr>
                        <td colspan="3" valign="top" class="tablepanel">
                          <p>
                            <span class="boldtext">Derived from: </span>
                            <xsl:choose>
                              <xsl:when test="starts-with(string($baseval), &quot;xs:&quot;)">
                                <span class="boldtext">
                                  <xsl:value-of select="$baseval"></xsl:value-of>
                                </span>
                              </xsl:when>
                              <xsl:otherwise>
                                <a class="sitelink">
                                  <xsl:attribute name="href">
                                    <xsl:text>#</xsl:text>
                                    <xsl:value-of select="$baseval"></xsl:value-of>
                                  </xsl:attribute>
                                  <xsl:value-of select="$baseval"></xsl:value-of>
                                </a>
                              </xsl:otherwise>
                            </xsl:choose> (by <xsl:value-of select="$derival"></xsl:value-of>) </p>
                        </td>
                      </tr>
                    </xsl:when>
                    <xsl:otherwise>
                      <!-- no other choices? -->
                    </xsl:otherwise>
                  </xsl:choose>
                </xsl:if>

                <!-- display the elements -->
                <xsl:if
                  test="count(xs:complexType|xs:sequence|xs:choice|xs:element|xs:complexContent|xs:simpleContent) &gt; 0">
                  <xsl:if
                    test="count(xs:complexType/xs:simpleContent/xs:extension/xs:attribute) = 0">
                    <tr>
                      <td valign="top" class="tablepanel">
                        <span class="boldtext"> Elements: </span>
                      </td>
                      <td valign="top" class="tablepanel">
                        <span class="boldtext"> Use: </span>
                      </td>
                      <td valign="top" class="tablepanel">
                        <span class="boldtext"> How many: </span>
                      </td>
                    </tr>

                    <!-- Now display the CM for the element children -->
                    <xsl:apply-templates
                      select="xs:complexType|xs:sequence|xs:choice|xs:element|xs:complexContent|xs:simpleContent|xs:group"
                      mode="contentmodel"></xsl:apply-templates>

                  </xsl:if>
                </xsl:if>
                <!-- display the attributes -->
                <xsl:if
                  test="count(xs:attribute|xs:complexType/xs:attribute|xs:complexType/xs:simpleContent/xs:extension/xs:attribute) &gt; 0">
                  <tr>
                    <td valign="top" class="tablepanel">
                      <span class="boldtext"> Attributes: </span>
                    </td>
                    <td valign="top" class="tablepanel">
                      <span class="boldtext"> Use: </span>
                    </td>
                    <td valign="top" class="tablepanel">
                      <span class="boldtext"> Default Value: </span>
                      <xsl:value-of select="./@default"></xsl:value-of>
                    </td>
                  </tr>
                </xsl:if>
                <!-- Now display the CM for the attribute children -->
                <xsl:apply-templates
                  select="xs:attribute|xs:complexType/xs:attribute|xs:complexType/xs:simpleContent/xs:extension/xs:attribute"
                  mode="contentmodel"></xsl:apply-templates>
              </xsl:otherwise>
            </xsl:choose>
          </table>
          <!-- end the inner content model table -->
        </td>
        <xsl:apply-templates select="xs:annotation" mode="helpinfo"></xsl:apply-templates>
        <xsl:apply-templates select="xs:simpleType" mode="embedded"></xsl:apply-templates>
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
    <xsl:apply-templates select="xs:element|xs:complexType|xs:sequence|xs:choice|xs:group"
      mode="contentmodel"></xsl:apply-templates>
    <tr>
      <td colspan="3" class="tablepanel">
        <xsl:text>)</xsl:text>
      </td>
    </tr>

    <xsl:if test="name(..) = 'xs:choice'
                 and not(position()=last())">
      <tr>
        <td colspan="3" class="tablepanel"> OR </td>
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
    <xsl:apply-templates select="xs:element|xs:complexType|xs:sequence|xs:choice|xs:group"
      mode="contentmodel"></xsl:apply-templates>
    <tr>
      <td colspan="3" class="tablepanel">
        <xsl:text>)</xsl:text>
      </td>
    </tr>
  </xsl:template>

  <xsl:template match="xs:group" mode="contentmodel">
    <tr>
      <td class="tablepanel">
        <xsl:call-template name="externalNamespaceLink">
          <xsl:with-param name="typename" select="./@ref"></xsl:with-param>
        </xsl:call-template>
      </td>
      <td class="tablepanel">
        <xsl:text>&#160;</xsl:text>
      </td>
      <td class="tablepanel">
        <xsl:text>&#160;</xsl:text>
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
            <xsl:if test="./@name">
              <xsl:value-of select="./@name"></xsl:value-of>
            </xsl:if>
            <xsl:if test="./@ref">
              <xsl:value-of select="./@ref"></xsl:value-of>
            </xsl:if>
          </xsl:attribute>
          <!-- and display the name of the element -->
          <xsl:value-of select="./@name"></xsl:value-of>
          <xsl:value-of select="./@ref"></xsl:value-of>
        </a>
      </td>
      <td class="tablepanel">
        <xsl:choose>
          <xsl:when test="./@minOccurs = '0'">optional</xsl:when>
          <xsl:otherwise>required</xsl:otherwise>
        </xsl:choose>
      </td>
      <td class="tablepanel">
        <xsl:choose>
          <xsl:when test="./@maxOccurs = '1'">once</xsl:when>
          <xsl:when test="./@maxOccurs='unbounded'">unbounded</xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="./@maxOccurs"></xsl:value-of>
          </xsl:otherwise>
        </xsl:choose>
      </td>
    </tr>

    <xsl:if test="name(..) = 'xs:choice'
                      and not(position()=last())">
      <tr>
        <td colspan="3" class="tablepanel"> OR </td>
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
            <xsl:if test="./@name">
              <xsl:value-of select="./@name"></xsl:value-of>
            </xsl:if>
            <xsl:if test="./@ref">
              <xsl:value-of select="./@ref"></xsl:value-of>
            </xsl:if>
          </xsl:attribute>
          <!-- and display the name of the attribute-->
          <xsl:value-of select="./@name"></xsl:value-of>
          <xsl:value-of select="./@ref"></xsl:value-of>
        </a>
      </td>

      <!-- display the use -->
      <xsl:if test="./@use">
        <td class="tablepanel">
          <span class="plaintext">
            <xsl:value-of select="./@use"></xsl:value-of>
          </span>
        </td>
      </xsl:if>

      <!-- display the default value -->
      <xsl:if test="./@value">
        <td class="tablepanel">
          <span class="plaintext">
            <xsl:value-of select="./@value"></xsl:value-of>
          </span>
        </td>
      </xsl:if>

      <xsl:if test="./@default">
        <td class="tablepanel">
          <span class="plaintext">
            <xsl:value-of select="./@default"></xsl:value-of>
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
              <xsl:value-of select="./@name"></xsl:value-of>
            </xsl:if>
            <xsl:if test="./@ref">
              <xsl:value-of select="./@ref"></xsl:value-of>
            </xsl:if>
          </xsl:attribute>
          <!-- and display the name of the attribute-->
          <xsl:value-of select="./@name"></xsl:value-of>
          <xsl:value-of select="./@ref"></xsl:value-of>
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
                <xsl:when test="starts-with(string(./@type), &quot;xs:&quot;)">
                  <span class="boldtext">
                    <xsl:value-of select="./@type"></xsl:value-of>
                  </span>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:call-template name="externalNamespaceLink">
                    <xsl:with-param name="typename" select="./@type"></xsl:with-param>
                  </xsl:call-template>
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
              <xsl:value-of select="./@use"></xsl:value-of>
            </span>
          </p>
        </xsl:if>

        <!-- display the default value -->
        <xsl:if test="./@value">
          <p>
            <span class="boldtext">Default value: </span>
            <span class="plaintext">
              <xsl:value-of select="./@value"></xsl:value-of>
            </span>
          </p>
        </xsl:if>

        <xsl:if test="./@default">
          <p>
            <span class="boldtext">Default value: </span>
            <span class="plaintext">
              <xsl:value-of select="./@default"></xsl:value-of>
            </span>
          </p>
        </xsl:if>
      </td>
      <xsl:apply-templates select="xs:simpleType" mode="embedded"></xsl:apply-templates>
      <xsl:apply-templates select="xs:annotation" mode="helpinfo"></xsl:apply-templates>
    </tr>
  </xsl:template>

  <!-- format the complexType content model -->
  <xsl:template match="xs:complexType" mode="contentmodel">
    <!-- Find all of the children of this complexType and list them -->
    <xsl:apply-templates
      select="xs:sequence|xs:choice|xs:element|xs:complexContent|xs:simpleContent"
      mode="contentmodel"></xsl:apply-templates>
  </xsl:template>

  <!-- step through the simpleTypes -->
  <xsl:template match="xs:simpleType" mode="documentation">
    <xsl:if test="./@name">
      <tr>
        <td colspan="2" class="tablehead">
          <h3>
            <a class="sitelink">
              <xsl:attribute name="name">
                <xsl:value-of select="./@name"></xsl:value-of>
              </xsl:attribute>
              <xsl:value-of select="./@name"></xsl:value-of>
            </a>
          </h3>
        </td>
      </tr>
      <tr>
        <td class="tablepanel">
          <xsl:apply-templates select="xs:extension|xs:restriction" mode="contentmodel"
          ></xsl:apply-templates>
        </td>
        <xsl:apply-templates select="xs:annotation" mode="helpinfo"></xsl:apply-templates>
      </tr>
    </xsl:if>
  </xsl:template>

  <xsl:template match="xs:simpleType" mode="embedded">
    <!-- <tr> 
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
    </tr> -->
    <tr>
      <td class="tablepanel">
        <xsl:apply-templates select="xs:extension|xs:restriction" mode="contentmodel"
        ></xsl:apply-templates>
      </td>
      <xsl:apply-templates select="xs:annotation" mode="helpinfo"></xsl:apply-templates>
    </tr>
  </xsl:template>

  <!-- format the complexContent content model -->
  <xsl:template match="xs:complexContent" mode="contentmodel">
    <!-- Find all of the children of this complexContent and list them -->
    <xsl:apply-templates select="xs:extension|xs:restriction" mode="contentmodel"
    ></xsl:apply-templates>
  </xsl:template>

  <xsl:template match="xs:simpleContent" mode="contentmodel">
    <!-- Find all of the children of this complexContent and list them -->
    <xsl:apply-templates select="xs:extension|xs:restriction" mode="contentmodel"
    ></xsl:apply-templates>
  </xsl:template>

  <!-- format the extension and restriction content model -->
  <xsl:template match="xs:extension|xs:restriction" mode="contentmodel">
    <!-- display a link to the base type for derivations -->
    <p>
      <span class="boldtext">Derived from: </span>
      <xsl:choose>
        <xsl:when test="starts-with(string(./@base), &quot;xs:&quot;)">
          <span class="boldtext">
            <xsl:value-of select="./@base"></xsl:value-of>
          </span>
        </xsl:when>
        <xsl:otherwise>
      
             <xsl:call-template name="externalNamespaceLink">
              <xsl:with-param name="typename" select="./@base"></xsl:with-param>
            </xsl:call-template>
                
            <!-- 
              <a class="sitelink"> 
            <xsl:attribute name="href">
              <xsl:text>#</xsl:text>
              <xsl:value-of select="./@base"></xsl:value-of>
            </xsl:attribute>
            <xsl:value-of select="./@base"></xsl:value-of> 
            </a>
            -->
         
        </xsl:otherwise>
      </xsl:choose> (by <xsl:value-of select="name(.)"></xsl:value-of>) </p>

    <!-- Find all of the children and list them -->
    <xsl:choose>
      <xsl:when test="name(..) = 'xs:simpleType'">

        <p>
          <span class="boldtext">Allowed values: </span>
          <ul>
            <xsl:apply-templates select="xs:enumeration" mode="contentmodel"></xsl:apply-templates>
            <xsl:apply-templates select="xs:minInclusive" mode="contentmodel"></xsl:apply-templates>
            <xsl:apply-templates select="xs:maxInclusive" mode="contentmodel"></xsl:apply-templates>
          </ul>
        </p>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="xs:sequence|xs:choice|xs:attribute" mode="contentmodel"
        ></xsl:apply-templates>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- format the enumeration content model -->
  <xsl:template match="xs:enumeration" mode="contentmodel">
    <li>
      <xsl:value-of select="./@value"></xsl:value-of>
    </li>
  </xsl:template>

  <!-- format the minInclusive content model -->
  <xsl:template match="xs:minInclusive" mode="contentmodel">
    <li>
      <span class="boldtext">Minimum: </span>
      <xsl:value-of select="./@value"></xsl:value-of>
    </li>
  </xsl:template>

  <!-- format the maxInclusive content model -->
  <xsl:template match="xs:maxInclusive" mode="contentmodel">
    <li>
      <span class="boldtext">Maximum: </span>
      <xsl:value-of select="./@value"></xsl:value-of>
    </li>
  </xsl:template>


  <!-- This template formats the various types of help information
       that are provided in the xsd file -->
  <xsl:template match="xs:annotation" mode="helpinfo">
    <td colspan="1" class="tablepanel" valign="top">
      <!-- display the help fields for each element-->
      <blockquote>
        <!--<br><span class="boldtext">Tooltip: </span></br>
            <xsl:value-of select="./xs:appinfo/doc:tooltip"/>
            <br><span class="boldtext">Summary: </span></br>
            <xsl:value-of select="./xs:appinfo/doc:summary"/>-->
        <!--<br><span class="boldtext">Description: </span></br>-->
        <!--<xsl:value-of select="./xs:appinfo/doc:description"/>-->
        <xsl:apply-templates select="./xs:appinfo/doc:description" mode="docbook"></xsl:apply-templates>
        <xsl:if test="./xs:appinfo/doc:example">
          <br></br>
            <span class="boldtext">Example(s): </span>
          <br></br>
          <xsl:for-each select="./xs:appinfo/doc:example">
            <xsl:value-of select="."></xsl:value-of>
            <br></br>
          </xsl:for-each>
        </xsl:if>
        <!--
            <br><span class="boldtext">Lineage: </span></br>
            <xsl:value-of select="./xs:appinfo/doc:lineage"/>
            -->
      </blockquote>
    </td>
  </xsl:template>

  <xsl:template name="externalNamespaceLink">
    <xsl:param name="typename"></xsl:param>

    <xsl:variable name="namespaceprefix">
      <xsl:value-of select="substring-before($typename, ':')"></xsl:value-of>
    </xsl:variable>

    <xsl:choose>
      <xsl:when test="$namespaceprefix = 'ds'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-dataset.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'sw'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-software.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'pro'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-protocol.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      
      
      <xsl:when test="$namespaceprefix = 'md'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-methods.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'dat'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-dataTable.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'cov'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-coverage.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'cit'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-literature.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'res'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-resource.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'ent'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-entity.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'txt'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-text.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'con'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-constraint.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'phys'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-physical.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'att'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-attribute.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'rp'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-party.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'acc'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-access.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'spref'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-spatialReference.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'sr'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-spatialRaster.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'sv' ">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-spatialVector.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'sp' ">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-storedProcedure.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>   
      <xsl:when test="$namespaceprefix = 'v'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-view.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when> 
     
      
      <xsl:when test="$namespaceprefix = 'prot'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-protocol.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'proj'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-project.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>
      <xsl:when test="$namespaceprefix = 'unit'">
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>eml-unitTypeDefinitions.html#</xsl:text>
            <xsl:value-of select="substring-after($typename, ':')"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:when>

      <xsl:otherwise>
        <a class="sitelink">
          <xsl:attribute name="href">
            <xsl:text>#</xsl:text>
            <xsl:value-of select="$typename"></xsl:value-of>
          </xsl:attribute>
          <xsl:value-of select="$typename"></xsl:value-of>
        </a>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

</xsl:stylesheet>
