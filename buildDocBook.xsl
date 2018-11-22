<?xml version="1.0"?>
<!--
       '$RCSfile: buildDocBook.xsl,v $'
       Copyright: 1997-2002 Regents of the University of California,
                            University of New Mexico, and
                            Arizona State University
        Sponsors: National Center for Ecological Analysis and Synthesis and
                  Partnership for Interdisciplinary Studies of Coastal Oceans,
                     University of California Santa Barbara
                  Long-Term Ecological Research Network Office,
                     University of New Mexico
                  Center for Environmental Studies, Arizona State University
   Other funding: National Science Foundation (see README for details)
                  The David and Lucile Packard Foundation
     For Details: http://knb.ecoinformatics.org/

        '$Author: obrien $'
          '$Date: 2009-02-26 21:10:21 $'
      '$Revision: 1.62 $'

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:doc="eml://ecoinformatics.org/documentation-2.2.0"
                version="1.0">
<xsl:output method="xml" indent="yes"
            doctype-public="-//OASIS//DTD DocBook XML V4.5//EN"
            doctype-system="http://www.oasis-open.org/docbook/xml/4.5/docbookx.dtd"/>
<xsl:template match="/">
<book>
  <bookinfo>
    <title>Ecological Metadata Language (EML) Specification</title>
  </bookinfo>
  <chapter id="moduleDescriptions">
    <title>Module Descriptions (Normative)</title>
    <xsl:for-each select="//doc:module">
      <xsl:variable name="moduleNameVar">
        <!-- save the name of the module we are in in this loop-->
        <xsl:value-of select="document(.)//doc:moduleName"/>.xsd
      </xsl:variable>
      <xsl:variable name="importedByList">
      <!--this is the variable that will be sent to the template-->
      <xsl:for-each select="/xs:schema/xs:annotation/xs:appinfo/doc:moduleDocs/doc:module">
        <xsl:variable name="currentModuleName">
          <!--save the name of the module that we are in this loop-->
          <xsl:value-of select="."/>
        </xsl:variable>
        <xsl:for-each select="document(.)//xs:import">
          <!-- go through each import statement and see if the current module is there -->
          <xsl:if test="normalize-space($moduleNameVar)=normalize-space(./@schemaLocation)">
            <!-- if it is put it in the variable -->
            <xsl:value-of select="substring($currentModuleName, 0,
                                  string-length($currentModuleName) - 3)"/>
            <xsl:text>, </xsl:text>
          </xsl:if>
        </xsl:for-each>
      </xsl:for-each>
      </xsl:variable>
      <xsl:apply-templates select="document(.)//doc:moduleDocs">
        <!--send the importedBy variable to this stylesheet-->
        <xsl:with-param name="importedBy" select="$importedByList"/>
      </xsl:apply-templates>
    </xsl:for-each>
  </chapter>
</book>
</xsl:template>

<xsl:template match="*|@*|text()" mode="copy">
  <xsl:copy>
    <xsl:apply-templates select="*|@*|text()" mode="copy"/>
  </xsl:copy>
</xsl:template>

<xsl:template match="doc:moduleDocs">
<xsl:param name="importedBy"/>
<section>
  <xsl:attribute name="id">
    <xsl:value-of select="./doc:moduleName"/>
  </xsl:attribute>
  <title><xsl:value-of select="./doc:moduleName"/></title>
  <para>Normative technical docs for
  <ulink>
      <xsl:attribute name="url">./xsd/<xsl:value-of select="./doc:moduleName"/>.html</xsl:attribute>
    <xsl:value-of select="./doc:moduleName"/>
  </ulink>
  </para>

  <!--itemizedlist>
    <listitem>
      <para>Recommended Usage: <xsl:value-of select="normalize-space(./doc:recommendedUsage)"/></para>
    </listitem>
    <listitem>
      <para>Stand-alone: <xsl:value-of select="normalize-space(./doc:standAlone)"/></para>
    </listitem>
    <listitem>
      <para>Imports:</para>
      <para>
        <xsl:variable name="importedItem">
          <xsl:for-each select="/xs:schema/xs:import">
              <xsl:value-of select="substring(normalize-space(@schemaLocation), 0,
                                      string-length(normalize-space(@schemaLocation))-3)"/>
            <xsl:text>, </xsl:text>
          </xsl:for-each>
        </xsl:variable>
        <xsl:value-of select="substring($importedItem, 0, string-length($importedItem) - 1)"/>
      </para>
    </listitem>
    <listitem>
      <para>Imported By:</para>
      <para>
      <xsl:value-of select="substring($importedBy, 0, string-length($importedBy) - 1)"/>
      </para>
    </listitem>
    <listitem>
      <para>
        <ulink>
          <xsl:attribute name="url">./<xsl:value-of select="./doc:moduleName"/>.html</xsl:attribute>
          Technical Specifications
        </ulink>
      </para>
    </listitem>
  </itemizedlist -->
  <!--para>
    <xsl:value-of select="./doc:moduleDescription"/>
  </para-->
  </section>
</xsl:template>

<xsl:template match="xs:element" mode="indexentry">
  <indexentry>
    <primaryie>
      <ulink>
          <xsl:attribute name="url">./xsd/<xsl:value-of select="//doc:moduleName"/>.html#<xsl:value-of select="@name"/></xsl:attribute>
        <xsl:value-of select="@name"/></ulink>-<xsl:value-of select="//doc:moduleName"/>
    </primaryie>
  </indexentry>
</xsl:template>
</xsl:stylesheet>
