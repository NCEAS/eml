<?xml version="1.0"?>
<!--
       '$RCSfile: sortDocBook.xsl,v $'
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

        '$Author: jones $'
          '$Date: 2002-09-09 09:46:42 $'
      '$Revision: 1.6 $'

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
                version="1.0">
<xsl:output method="xml" indent="yes"/>
<xsl:output doctype-public="-//OASIS//DTD DocBook XML V4.1.2//EN"
            doctype-system="http://www.oasis-open.org/docbook/xml/4.0/docbookx.dtd"/>

<xsl:template match="/">
    <xsl:apply-templates select="book"/>

    <!--<index>
      <title>Index</title>
      <xsl:apply-templates select="indexdiv"/>
    </index>-->
</xsl:template>

<xsl:template match="book">
  <book>
    <xsl:apply-templates select="bookinfo"/>
    <xsl:apply-templates select="toc"/>
    <xsl:apply-templates select="preface"/>
    <xsl:apply-templates select="chapter"/>
    <xsl:apply-templates select="index"/>

  </book>
</xsl:template>

<xsl:template match="bookinfo">
  <xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="toc">
  <xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="preface">
  <xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="chapter">
  <xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="index">
  <index id="index">
    <title>Index</title>
      <xsl:apply-templates select="indexdiv"/>
    </index>
</xsl:template>

<xsl:template match="indexdiv">
  <indexdiv>
    <xsl:copy-of select="./title"/>
    <xsl:for-each select="./indexentry">
        <xsl:sort select="./primaryie/ulink"/>
      <indexentry>
        <xsl:copy-of select="./primaryie"/>
      </indexentry>
    </xsl:for-each>
  </indexdiv>
</xsl:template>

</xsl:stylesheet>
