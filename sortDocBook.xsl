<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:doc="eml://ecoinformatics.org/documentation-2.0.0beta9"
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
