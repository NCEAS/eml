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
    <title>Guide to Ecological Metadata Language (EML)</title>
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
      <title>Purpose Statement</title>
      <para>
        To provide the ecological community with an extensible, flexible,
        metadata standard for use in data analysis and archiving that will 
        allow automated machine processing, searching and retrieval.  
      </para>
    </section>
    
    <section>
      <title>Extensibility</title>
      <para>
        Say something about extensibility here when we figure out the packaging
        methods.
      </para>
    </section>
  </preface>
  
  
  <chapter label="Module Descriptions" id="moduleDescriptions">
    <title>Module Descriptions</title>
    <xsl:for-each select="//doc:module">
      <xsl:apply-templates select="document(.)//doc:moduleDocs"/>
    </xsl:for-each>
  </chapter>
  
  <index>
    <title>Index</title>
      <indexdiv>
        <title>A</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'a')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>B</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'b')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>C</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'c')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>D</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'd')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>E</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'e')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>F</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'f')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>G</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'g')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>H</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'h')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>I</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'i')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>J</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'j')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>k</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'k')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>L</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'l')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>M</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'm')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>N</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'n')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>O</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'o')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>P</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'p')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>Q</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'q')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>R</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'r')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>S</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 's')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>T</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 't')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>U</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'u')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>V</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'v')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>W</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'w')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>X</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'x')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>Y</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'y')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
      <indexdiv>
        <title>Z</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:if test="starts-with(./@name, 'z')">
              <xsl:apply-templates select="." mode="indexentry"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </indexdiv>
  </index>
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

<xsl:template match="xs:element" mode="indexentry">
  <indexentry>
    <primaryie>
      <ulink>
        <xsl:attribute name="url">file://../<xsl:value-of select="//doc:moduleName"/>.html#<xsl:value-of select="@name"/></xsl:attribute>
        <xsl:value-of select="@name"/>
      </ulink>
    </primaryie>
  </indexentry>
</xsl:template>
</xsl:stylesheet>
