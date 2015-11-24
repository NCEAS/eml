<?xml version="1.0" encoding="UTF-8"?>

<!--
Copyright: 2014 Regents of the University of California and the
           National Center for Ecological Analysis and Synthesis
      '$Id$'
  '$Author$'
    '$Date$'
'$Revision$'

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

This is an XSLT (http://www.w3.org/TR/xslt) stylesheet designed to
convert an XML file in EML 2.1.1 format to the Dryad Metadata Profile version 3.1 format
-->

<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2011/XMLSchema"
                xmlns:eml="eml://ecoinformatics.org/eml-2.0.1"
                xmlns:dryad="http://purl.org/dryad/schema/terms/v3.1"
                xmlns:dwc="http://rs.tdwg.org/dwc/terms/" 
                xmlns:dcterms="http://purl.org/dc/terms/" 
                xmlns:bibo="http://purl.org/dryad/schema/dryad-bibo/v3.1">
                
    <xsl:output method="xml" indent="yes" encoding="UTF-8" media-type="text/xml"/>
    <xsl:strip-space elements="*" />
        
    <!-- The root template, matching the eml:eml element-->
    <xsl:template match="/eml:eml" name="main">
        <xsl:element name="DryadDataPackage">
            <xsl:attribute name="dwc" namespace="http://rs.tdwg.org/dwc/terms/">http://rs.tdwg.org/dwc/terms/</xsl:attribute>
            <xsl:attribute name="dcterms" namespace="http://purl.org/dc/terms/">http://purl.org/dc/terms/</xsl:attribute>
            <xsl:attribute name="bibo" namespace="http://purl.org/dryad/schema/dryad-bibo/v3.1">http://purl.org/dryad/schema/dryad-bibo/v3.1</xsl:attribute>
            <xsl:attribute name="xsi" namespace="http://www.w3.org/2001/XMLSchema-instance">http://www.w3.org/2001/XMLSchema-instance</xsl:attribute>
            <xsl:attribute name="schemaLocation" namespace="http://www.w3.org/2001/XMLSchema-instance">http://purl.org/dryad/schema/terms/v3.1 http://datadryad.org/profile/v3.1/dryad.xsd</xsl:attribute>
            <xsl:attribute name="targetNamespace">http://purl.org/dryad/schema/terms/v3.1</xsl:attribute>
            
            <!-- add the metadata type -->
            <xsl:element name="dcterms:type">package</xsl:element>
            <xsl:call-template name="set-dataset-creators"/>
            
            <!-- add the date submitted, defaulting to the current date -->
            <xsl:comment>We don't have a way to get dateSubmitted from EML, using current-date()</xsl:comment>
            <xsl:element name="dcterms:dateSubmitted"><xsl:value-of select="current-date()" /></xsl:element>
            
            <!-- add the date available, defaulting to the current date -->
            <xsl:comment>We don't have a way to get the availability date from EML, using current-date()</xsl:comment>
            <xsl:element name="dcterms:available"><xsl:value-of select="current-date()" /></xsl:element>
            
            <!-- add the dataset title -->
            <xsl:call-template name="set-dataset-title"/>
                 
            <!-- add the dataset id -->
            <xsl:element name="dcterms:identifier">
                <xsl:value-of select="@packageId"/>
            </xsl:element>

            <!-- add the abstract as a description -->
            <xsl:call-template name="set-dataset-description" />
            
            <!-- add the keywords as subjects -->
            <xsl:call-template name="set-dataset-subjects" />
            
            <!-- add the taxonomic coverage as scientific names -->
            <xsl:call-template name="set-scientific-names" />

            <!-- add the geographic coverage as dcterms:spatial -->
            <xsl:call-template name="set-spatial" />

            <!-- add the temporal coverage as dcterms:temporal -->
            <xsl:call-template name="set-temporal" />

            <!-- TODO: optioanlly add dryad:external element -->
            <!-- TODO: optioanlly add dcterms:references element -->
            <!-- TODO: optioanlly add bibo:pmid element -->
            <!-- TODO: optioanlly add bibo:Journal element -->
            
            <!-- add the data file distribution urls as dcterms:hasPart -->
            <xsl:for-each select="//physical/distribution/online">
                <xsl:if test="url != ''">
                    <xsl:element name="dcterms:hasPart">
                        <xsl:value-of select="url" />
                    </xsl:element>
                </xsl:if>
            </xsl:for-each>
            
        </xsl:element>
    </xsl:template> 
    
    <!-- template that matches dataset creators, creates dcterms:creator -->    
    <xsl:template match="dataset/creator" name="set-dataset-creators">
        <xsl:element name="dcterms:creator">
            <xsl:choose>
                <xsl:when test="individualName"><xsl:call-template name="set-individual-name" /></xsl:when>
                <xsl:when test="organizationName"><xsl:value-of select="organizationName" /></xsl:when>
                <xsl:when test="positionName"><xsl:value-of select="positionName" /></xsl:when>            
            </xsl:choose>
        </xsl:element>
    </xsl:template>
    
    <!-- template that matches creator individualName -->    
    <xsl:template match="individualName" name="set-individual-name">
            <xsl:if test="salutation != ''">
                <xsl:value-of select="salutation" />
                <xsl:value-of select="' '" />
            </xsl:if>
            <xsl:if test="givenName != ''">
                <xsl:for-each select="givenName">
                    <xsl:value-of select="." />
                    <xsl:value-of select="' '" />
                </xsl:for-each>
            </xsl:if>
            <xsl:value-of select="surName" />
    </xsl:template>
    
    <!-- template that matches dataset/title, creates dcterms:title -->    
    <xsl:template match="dataset/title" name="set-dataset-title">
        <xsl:element name="dcterms:title">
            <xsl:value-of select="dataset/title" />
        </xsl:element>
    </xsl:template>

    <!-- template that matches dataset/abstract, creates dcterms:description -->    
    <xsl:template match="dataset/abstract" name="set-dataset-description">
        <xsl:element name="dcterms:description">
            <xsl:if test="dataset/abstract != ''">
                <xsl:value-of select="dataset/abstract" />
            </xsl:if>
            <xsl:if test="dataset/abstract/section != ''">
                <xsl:value-of select="dataset/abstract/section" />
            </xsl:if>
            <xsl:if test="dataset/abstract/para != ''">
                <xsl:value-of select="dataset/abstract/para" />
            </xsl:if>
        </xsl:element>
    </xsl:template>

    <!-- template that matches dataset/keywordSet, creates dcterms:subject -->    
    <xsl:template match="dataset/keywordSet" name="set-dataset-subjects">
            <xsl:if test="./keyword != ''">
                <xsl:for-each select="keyword">
                    <xsl:element name="dcterms:subject">
                        <xsl:value-of select="." />
                    </xsl:element>
                </xsl:for-each>
            </xsl:if>
    </xsl:template>

    <!-- template that matches dataset/coverage/taxonomicCoverage/taxonomicClassification , creates dcterms:scientificName -->    
    <xsl:template match="dataset/coverage/taxonomicCoverage/taxonomicClassification" name="set-scientific-names">
            <xsl:if test=".//taxonRankValue != ''">
                <xsl:for-each select=".//taxonRankValue">
                    <!-- Include the taxonRankName as a comment for troubleshooting -->
                    <xsl:comment>
                        <xsl:value-of select="name(preceding-sibling::*[1])" />
                        <xsl:value-of select="': '" />
                        <xsl:value-of select="preceding-sibling::*[1]" />
                    </xsl:comment>
                    <xsl:element name="dcterms:scientificName">
                        <xsl:value-of select="." />
                    </xsl:element>
                </xsl:for-each>
            </xsl:if>
    </xsl:template>

    <!-- template that matches dataset/coverage/geographicCoverage , creates dcterms:spatial -->    
    <xsl:template match="dataset/coverage/geographicCoverage" name="set-spatial">
            <xsl:if test="geographicDescription != ''">
                <xsl:element name="dcterms:spatial">
                    <xsl:value-of select="geographicDescription" />
                </xsl:element>
            </xsl:if>
    </xsl:template>

    <!-- template that matches dataset/coverage/temporalCoverage , creates dcterms:temporal -->    
    <xsl:template match="dataset/coverage/temporalCoverage" name="set-temporal">
        <xsl:choose>
            <xsl:when test="singleDateTime/calendarDate != ''">
                <xsl:element name="dcterms:temporal">
                    <xsl:value-of select="singleDateTime/calendarDate" />
                    <xsl:if test="singleDateTime/time != ''">
                        <xsl:value-of select="' '" />
                        <xsl:value-of select="singleDateTime/time" />
                    </xsl:if>
                </xsl:element>
            </xsl:when>
            <xsl:when test="rangeOfDates/beginDate/calendarDate != ''">
                <xsl:element name="dcterms:temporal">
                    <!-- add the begin date/time -->
                    <xsl:value-of select="rangeOfDates/beginDate/calendarDate" />
                    <xsl:if test="rangeOfDates/beginDate/time != ''">
                        <xsl:value-of select="' '" />
                        <xsl:value-of select="rangeOfDates/beginDate/time" />
                    </xsl:if>

                    <xsl:value-of select="' TO '" />
                    
                    <!-- add the end date/time -->
                    <xsl:value-of select="rangeOfDates/endDate/calendarDate" />
                    <xsl:if test="rangeOfDates/endDate/time != ''">
                        <xsl:value-of select="' '" />
                        <xsl:value-of select="rangeOfDates/endDate/time" />
                    </xsl:if>
                </xsl:element>
                
            </xsl:when>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                