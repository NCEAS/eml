<?xml version="1.0" encoding="utf-8"?>
<!--

ASCII XML Tree Viewer 1.0 (13 Feb 2001)
An XPath/XSLT visualisation tool for XML documents

Written by Jeni Tennison and Mike J. Brown
No license; use freely, but please credit the authors if republishing elsewhere.

Use this stylesheet to produce an ASCII art representation of an XML document's
node tree, as exposed by the XML parser and interpreted by the XSLT processor.
Note that the parser may not expose comments to the XSLT processor.

Usage notes
===========

By default, this stylesheet will not show namespace nodes. If the XSLT processor
supports the namespace axis and you want to see namespace nodes, just pass a
non-empty "show_ns" parameter to the stylesheet. Example using Instant Saxon:

    saxon somefile.xml ascii-treeview.xsl show_ns=yes

If you want to ignore whitespace-only text nodes, uncomment the xsl:strip-space
instruction below.

-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:output method="html" />

<!-- uncomment the following to ignore whitespace-only text nodes -->
<!-- xsl:strip-space elements="*" -->

<!-- pass a non-empty show_ns parameter to the stylesheet to show namespace nodes -->
<xsl:param name="show_ns"/>

<xsl:variable name="apos">'</xsl:variable>


<xsl:template name="additionalmetadata">
      <h3>Additional Metadata</h3>
    <pre>
    <xsl:text>additionalMetadata&#xA;</xsl:text>
    <xsl:apply-templates mode="ascii-art" />
    </pre>
</xsl:template>

<xsl:template match="*" mode="ascii-art">
    <xsl:call-template name="ascii-art-hierarchy" />
    <xsl:text />___element '<xsl:value-of select="local-name()" />'<xsl:text />
    <xsl:if test="namespace-uri()"> in ns '<xsl:value-of select="namespace-uri()"/>' ('<xsl:value-of select="name()"/>')</xsl:if>
    <xsl:text>&#xA;</xsl:text>
    <xsl:apply-templates select="@*" mode="ascii-art" />
    <xsl:if test="$show_ns">
         <xsl:for-each select="namespace::*">
             <xsl:call-template name="ascii-art-hierarchy" />
             <xsl:text />  \___namespace '<xsl:value-of select="name()" />' = '<xsl:value-of select="." />'&#xA;<xsl:text />
         </xsl:for-each>
    </xsl:if>
    <xsl:apply-templates mode="ascii-art" />
</xsl:template>

<xsl:template match="@*" mode="ascii-art">
    <xsl:call-template name="ascii-art-hierarchy" />
    <xsl:text />  \___attribute '<xsl:value-of select="local-name()" />'<xsl:text />
    <xsl:if test="namespace-uri()"> in ns '<xsl:value-of select="namespace-uri()"/>' ('<xsl:value-of select="name()"/>')</xsl:if>
    <xsl:text /> = '<xsl:text />
    <xsl:call-template name="escape-ws">
        <xsl:with-param name="text" select="." />
    </xsl:call-template>
    <xsl:text />'&#xA;<xsl:text />
</xsl:template>

<xsl:template match="text()" mode="ascii-art">
    <xsl:call-template name="ascii-art-hierarchy" />
    <xsl:text>___text '</xsl:text>
    <xsl:call-template name="escape-ws">
        <xsl:with-param name="text" select="." />
    </xsl:call-template>
    <xsl:text>'&#xA;</xsl:text>
</xsl:template>

<xsl:template match="comment()" mode="ascii-art">
    <xsl:call-template name="ascii-art-hierarchy" />
    <xsl:text />___comment '<xsl:value-of select="." />'&#xA;<xsl:text />
</xsl:template>

<xsl:template match="processing-instruction()" mode="ascii-art">
    <xsl:call-template name="ascii-art-hierarchy" />
    <xsl:text />___processing instruction target='<xsl:value-of select="name()" />' instruction='<xsl:value-of select="." />'&#xA;<xsl:text />
</xsl:template>

<xsl:template name="ascii-art-hierarchy">
    <xsl:for-each select="ancestor::*">
      <xsl:choose>
        <xsl:when test="local-name()!='additionalMetadata'">
          <xsl:choose>
            <xsl:when test="following-sibling::node()">  |   </xsl:when>
            <xsl:otherwise><xsl:text>      </xsl:text></xsl:otherwise>
          </xsl:choose>
        </xsl:when>
      </xsl:choose>
    </xsl:for-each>
    <xsl:choose>
        <xsl:when test="parent::node() and ../child::node()">  |</xsl:when>
        <xsl:otherwise><xsl:text>   </xsl:text></xsl:otherwise>
    </xsl:choose>
</xsl:template>

<!-- recursive template to escape backslashes, apostrophes, newlines and tabs -->
<xsl:template name="escape-ws">
    <xsl:param name="text" />
    <xsl:choose>
        <xsl:when test="contains($text, '\')">
            <xsl:call-template name="escape-ws">
                <xsl:with-param name="text" select="substring-before($text, '\')" />
            </xsl:call-template>
            <xsl:text>\\</xsl:text>
            <xsl:call-template name="escape-ws">
                <xsl:with-param name="text" select="substring-after($text, '\')" />
            </xsl:call-template>
        </xsl:when>
        <xsl:when test="contains($text, $apos)">
            <xsl:call-template name="escape-ws">
                <xsl:with-param name="text" select="substring-before($text, $apos)" />
            </xsl:call-template>
            <xsl:text>\'</xsl:text>
            <xsl:call-template name="escape-ws">
                <xsl:with-param name="text" select="substring-after($text, $apos)" />
            </xsl:call-template>
        </xsl:when>
        <xsl:when test="contains($text, '&#xA;')">
            <xsl:call-template name="escape-ws">
                <xsl:with-param name="text" select="substring-before($text, '&#xA;')" />
            </xsl:call-template>
            <xsl:text>\n</xsl:text>
            <xsl:call-template name="escape-ws">
                <xsl:with-param name="text" select="substring-after($text, '&#xA;')" />
            </xsl:call-template>
        </xsl:when>
        <xsl:when test="contains($text, '&#x9;')">
            <xsl:value-of select="substring-before($text, '&#x9;')" />
            <xsl:text>\t</xsl:text>
            <xsl:call-template name="escape-ws">
                <xsl:with-param name="text" select="substring-after($text, '&#x9;')" />
            </xsl:call-template>
        </xsl:when>
        <xsl:otherwise><xsl:value-of select="$text" /></xsl:otherwise>
    </xsl:choose>
</xsl:template>

</xsl:stylesheet>
