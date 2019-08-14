<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:doc="https://eml.ecoinformatics.org/documentation-2.2.0"
    exclude-result-prefixes="xs"
    version="1.0">

    <!--Identity template, provides default behavior that copies all content into the output -->

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

    <!-- template for appinfo node that a) converts to a documentation node and b) uses element name to label content -->
    <xsl:template match="xs:appinfo">

        <xsl:variable name="newline">
            <xsl:text>&#10;</xsl:text>
        </xsl:variable>

        <xsl:variable name="colon">
            <xsl:text>:</xsl:text>
        </xsl:variable>

        <xsl:variable name="comma">
            <xsl:text>,</xsl:text>
        </xsl:variable>

        <xsl:variable name="sp1">
            <xsl:text> </xsl:text>
        </xsl:variable>

        <xsl:variable name="delimiter">
            <xsl:text>|</xsl:text>
        </xsl:variable>
        <xs:documentation>
            <xsl:for-each select="doc:summary|doc:tooltip|doc:description|doc:example">
                <xsl:value-of select="$newline"/>
                <xsl:variable name="value" select="local-name()"/>
                <xsl:value-of select="$value"/>
                <xsl:value-of select="$colon"/>
                <xsl:value-of select="$sp1"/>
                <xsl:value-of select="."/>
                <xsl:value-of select="$newline"/>
            </xsl:for-each>

            <xsl:for-each select="doc:moduleDocs">
                <xsl:for-each select="*">
                    <xsl:value-of select="$newline"/>
                    <xsl:variable name="value" select="local-name()"/>
                    <xsl:value-of select="$value"/>
                    <xsl:value-of select="$colon"/>
                    <xsl:value-of select="$sp1"/>
                    <xsl:value-of select="."/>
                    <xsl:value-of select="$newline"/>
                </xsl:for-each>
            </xsl:for-each>
        </xs:documentation>
    </xsl:template>

    <!-- <xsl:template match="xs:appinfo"> <xsl:copy> <xsl:apply-templates select="@*|node()"/> <xsl:text> add some text </xsl:text> </xsl:copy> </xsl:template> -->

    <!-- not sure how to id cdata section. can't use match. string? -->
    <xsl:template name="build-CDATA">
        <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>

        <xsl:apply-templates select="child::node()"/>
        <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
    </xsl:template>
</xsl:stylesheet>
