<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xs="http://www.w3.org/2001/XMLSchema" 
                xmlns:doc="eml:documentation-2.0.0beta8" 
                version="1.0">
<xsl:output method="xml" indent="yes"/>
<xsl:output doctype-public="-//OASIS//DTD DocBook XML V4.1.2//EN" 
            doctype-system="http://www.oasis-open.org/docbook/xml/4.0/docbookx.dtd"/>
<xsl:template match="/">
<book>
  <bookinfo>
    <title>Guide to EML</title>
  </bookinfo>
  
  <preface>
    <title>EML Overview &amp; History</title> 
    <section>
      <title>Introduction</title>
      <para>
       Ecological Metadata Language (EML) is a metadata standard developed by the 
       ecology discipline and for the ecology discipline. It is based on prior 
       work done by the Ecological Society of America and associated efforts 
       (Michener et al., 1997, Ecological Applications). EML is implemented as 
       a series of XML document types that can by used in a modular and extensible 
       manner to document ecological data. Each EML module is designed to describe 
       one logical part of the total metadata that should be included with any 
       ecological dataset.
      </para>
    </section>
    
    <section>
      <title>Purpose</title>
      <para>
        To provide the ecological community with an extensible, flexible,
        metadata standard for use in data analysis and archiving that will 
        allow automated machine processing, searching and retrieval.  
      </para>
    </section>
    
    <section>
      <title>Extensibility</title>
      <para>
      
      </para>
    </section>
  </preface>
  
  
  <chapter label="Module Descriptions" id="moduleDescriptions">
    <title>Module Descriptions</title>
    <xsl:for-each select="//doc:module">
      <xsl:apply-templates select="document(.)//doc:moduleDocs"/>
    </xsl:for-each>
  </chapter>
</book>
</xsl:template>

<xsl:template match="doc:moduleDocs">
<section>
  <xsl:attribute name="id">
    <xsl:value-of select="./doc:moduleName"/>
  </xsl:attribute>
  <title><xsl:value-of select="./doc:moduleName"/></title>
  <itemizedlist>
    <listitem>
      <para>Recommended Usage: <xsl:value-of select="normalize-space(./doc:recommendedUsage)"/></para>
    </listitem>
    <listitem>
      <para>Stand-alone: <xsl:value-of select="normalize-space(./doc:standAlone)"/></para>
    </listitem>
    <xsl:if test="count(./doc:importedBy) > 0">
    <listitem>
      <para>Imported By:</para> 
      <itemizedlist><xsl:apply-templates select="./doc:importedBy"/></itemizedlist>
    </listitem>
    </xsl:if>
    <xsl:if test="count(./doc:relationship) > 0">
    <listitem>
      <para>Relates To:</para>
      <segmentedlist>
        <segtitle>related module</segtitle>
        <segtitle>relationship</segtitle>
        <xsl:apply-templates select="./doc:relationship"/>
      </segmentedlist>
    </listitem>
    </xsl:if>
    <listitem>
      <para>
        <ulink>
          <xsl:attribute name="url">file://../<xsl:value-of select="./doc:moduleName"/>.html</xsl:attribute>
          Technical Specifications
        </ulink>
      </para>
    </listitem>
  </itemizedlist>
  <para>
    <xsl:value-of select="./doc:moduleDescription"/>
  </para>
</section>
</xsl:template>

<xsl:template match="doc:relationship">
  <seglistitem>
    <seg><xsl:value-of select="normalize-space(./doc:moduleName)"/></seg>
    <seg><xsl:value-of select="normalize-space(./doc:relationshipType)"/></seg>
  </seglistitem>
</xsl:template>

<xsl:template match="doc:importedBy">
  <listitem>
    <para>
      <xsl:value-of select="normalize-space(.)"/>
    </para>
  </listitem>
</xsl:template>
</xsl:stylesheet>
