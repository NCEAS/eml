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

        '$Author: cjones $'
          '$Date: 2002-09-09 18:59:05 $'
      '$Revision: 1.29 $'

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
                xmlns:doc="eml://ecoinformatics.org/documentation-2.0.0rc1"
                version="1.0">
<xsl:output method="xml" indent="yes"/>
<xsl:output doctype-public="-//OASIS//DTD DocBook XML V4.1.2//EN"
            doctype-system="http://www.oasis-open.org/docbook/xml/4.0/docbookx.dtd"/>

<xsl:template match="/">
<book>
  <bookinfo>
    <title>Ecological Metadata Language (EML) Specification</title>
  </bookinfo>
  <preface id="preface">
    <title>EML Overview &amp; History</title>
    <section id="introduction">
      <title>Introduction</title>
      <para>
       Ecological Metadata Language (EML) is a metadata standard developed by 
       the ecology discipline and for the ecology discipline. It is based on 
       prior work done by the Ecological Society of America and associated 
       efforts (Michener et al., 1997, Ecological Applications). EML is 
       implemented as a series of XML document types that can by used in a 
       modular and extensible manner to document ecological data. Each EML 
       module is designed to describe one logical part of the total metadata 
       that should be included with any ecological dataset.
      </para>
    </section>

    <section id="purpose">
      <title>Purpose Statement</title>
      <para>
        To provide the ecological community with an extensible, flexible,
        metadata standard for use in data analysis and archiving that will
        allow automated machine processing, searching and retrieval.
      </para>
    </section>

    <section id="features">
      <title>Features</title>
      <para>
        The architecture of EML was designed to serve the needs of the
        ecological community, and has benefitted from previous work in other
        related metadata languages. EML has adopted the strengths of many of
        these languages, but also addresses a number of short-comings that
        have proved to inhibit the automated processing iand integration of
        dataset resources via their metadata.
      </para>
      <para>
        The following list represents some of the features of EML:
      </para>
      <itemizedlist>
        <listitem>
        <para>
        Modularity: EML was designed as a collection of modules rather than
        one large standard to facilitate future growth of the language in both
        breadth and depth.  By implementing EML with an extensible
        architecture, groups may choose which of the core modules are
        pertinent to describing their data, literature, and software
        resources.  Also, if EML falls short in a particular area, it may be
        extended by creating a new module that describes the resource (e.g. a
        detailed soils metadata profile that extends eml-dataset).  The intent
        is to provide a common set of core modules for information exchange,
        but to allow for futute customizations of the language without the
        need of going through a lengthy 'approval' process.
        </para>
        </listitem>
        <listitem>
        <para>
        Detailed Structure: EML strives to balance the tradeoff of 'too much
        detail' with enough detail to enable advanced services in terms of
        processing data through the parsing of accompanied metadata.
        Therefore, a driving question throughout the design was: 'Will this
        particular piece of information be machine-processed, just human
        readable, or both?'  Information was then broken down into more highly
        structured elements when the answer involved machine processing.
        </para>
        </listitem>
        <listitem>
        <para>
        Compatibility: EML adopts much of it's syntax from the other metadata
        standards that have evolved from the expertise of groups in other
        disciplines.  Whenever possible, EML adopted entire trees of
        information in order to facilitate conversion of EML documents into
        other metadata languages.  EML was designed with with the following
        standards in mind: Dublin Core Metadata Initiative, the Content
        Standard for Digital Geospatial Metadata (CSDGM from the US geological
        Survey's Federal Geographic Data Committee (FGDC)), the Biological
        Profile of the CSDGM (from the National Biological Information
        Infrastructure), the International Standards Organization's Geographic
        Information Standard (ISO 19115), the ISO 8601 Date and Time Standard,
        the OpenGIS Consortiums's Geography Markup Language (GML), the
        Scientific, Technical, and Medical Markup Language (STMML), and the
        Extensible Scientific Interchange Language (XSIL).
        </para>
        </listitem>
        <listitem>
        <para>
        Strong Typing: EML is implemented in an Extensible Markup Language
        (XML) known as XML Schema, which is a language that defines the rules
        that govern the EML syntax.  XML Schema is an internet recommendation
        from the World Wide Web Consortium (http://www.w3.org), and so a
        metadata document that is said to comply with the syntax of EML will
        structurally meet the criteria defined in the XML Schema documents for
        EML.  Over and above the structure (what elements can be nested within
        others, how many, etc.), XML Schema provides the ability to use strong
        data typing within elements.  This allows for finer validation of the
        contents of the element, not just it's structure.  For instance, an
        element may be of type 'date', and so the value that is inserted in
        the field will be checked against XML Schema's definition of a date.
        Traditionally, XML documents have been validated against Document Type
        Definitions (DTDs), which do not provide a means to employ strong
        validation on field values through typing.
        </para>
        </listitem>
        <listitem>
        <para>
        There is a distinction between the content model (i.e. the concepts
        behind the structure of a document - which fields go where, how many,
        etc.) and the syntactic implementation of that model (the technology 
        used to express the concepts defined in the content model).
        The normative sections below define the content model and the 
        XML Schema documents distributed with EML define the syntactic 
        implementation. For the forseeable future, XML Schema will be the 
        syntactic specification, although it may change later.
        </para>
        </listitem>
      </itemizedlist>
    </section>
  </preface>

  <chapter id="moduleOverview">
    <title>Overview of EML modules and their use</title>
    <section>
      <para>
        The following section briefly describes each EML module and how they
        are logically designed in order to document ecological resources.
        Some of the modules are dependent on others, while others may be used
        as stand-alone descriptions.  This section describes the modules from
        the &quot;top down&quot;, starting from the top-level eml.xsd wrapper
        module, followed by modules of increasing detail.  However, there are
        modules that may be used at many levels, such as eml-access.xsd.  
        These modules are desribed when it is appropriate.
      </para>
        <section>
          <title>The eml module (eml.xsd) - a metadata container</title>
          <para>
            The eml module is a wrapper container that allows the inclusion 
            of any metadata content in a single EML document. The eml module 
            is used as a container to hold structured descriptions of 
            ecological resources.  In EML, the definition of a resource comes 
            from the 
            <ulink url="http://dublincore.org/documnets/usageguide/">
              <citetitle>
                The Dublin Core Metadata Initiative
              </citetitle>
            </ulink>, which describes a general element set used to describe 
            &quot;networked digital resources&quot;. The top-level structure 
            of EML has been design to be compatible with the Dublin Core 
            syntax. In general, dataset resources, literature resources, 
            software resources, and protocol resources comprise the 
            list of information that may be described in EML. EML is largely
            designed to desrcibe digital resources, however, it may also be
            used to describe non-digital resources such as paper maps and
            other non-digital media.  All EML documents must begin with the 
            &lt;eml&gt; tag. and end with the &lt;/eml&gt; tag.
          </para>
          <para>
            The eml module may be extended to describe other resources by
            means of it's optional sub-field, &lt;additionalMetadata&gt;.
            This field is largely reserved for the inclusion of metadata that
            may be highly discipline specific and not covered in this version
            of EML, or it may be used to internally extend fields within the
            EML standard.
          </para>
        </section>
        <section>
          <title>
            The resource module - (eml-resource.xsd) - base information for 
            all resources
          </title>
          <para>
            The eml-resource module contains general information that
            describes dataset resources, literature resources, protocol
            resources, and software resources. Each of the above four types of
            resources share a common set of information, but also have 
            information that is unique to that particular resource type.  Each
            resource type uses the eml-resource module to document the 
            information common to all resources, but then extend 
            eml-resource with modules that are specific to that particular 
            resource type.  For instance, all resources have creators, 
            titles, and perhaps keywords, but only the dataset resource would 
            have a &quot;data table&quot; within it.  Likewise, a literature 
            resource may have an &quot;ISBN&quot; number associated with it, 
            whereas the other resource types would not.
          </para>
        </section>
        <note>
          <para>
          The following four modules are used to describe separate resources:
          datasets, literature, software, and protocols.  However, note that
          the dataset module makes use of the other top-level modules by
          importing them at different levels.  For instance, a dataset may
          have been produced using a particular protocol, and that protocol
          may come from a protocol document in a library of protocols.
          Likewise, citations are used throughout the top-level resource
          modules by importing the literature module.
          </para>
          <para>
          Many sites may want to develop a library of protocol
          documents, a library of party (people) documents, and a library of
          software documents.  These may then be used by reference in your
          dataset documents as reusable content.
          </para>
        </note>
        <section>
          <title>
            The dataset module - (eml-dataset.xsd) - dataset specific information
          </title>
          <para>
          </para>
        </section>
        <section>
          <title>
            The literature module - (eml-literature.xsd) - citation specific 
            information
          </title>
          <para>
          </para>
        </section>
        <section>
          <title>
            The software module - (eml-software.xsd) - software specific 
            information
          </title>
          <para>
          </para>
        </section>
        <section>
          <title>
            The protocol module - (eml-protocol.xsd) - protocol specific 
            information
          </title>
          <para>
          </para>
        </section>
        <note>
          <para>
            The following  six modules are used to qualify the resources being
            described in more detail.  They are used to describe access control
            rules, distribution of the metadata and data themselves, parties
            associated with the resource, the geographic, temporal, and
            taxonomic extents of the resource, the overall research context of
            the resource, and detailed methodology used for creating the 
            resource.  Some of these modules are imported directly into the 
            top-level resource modules, often in many locations in order to 
            limit the scope of the description.  For instance, the eml-coverage 
            module may be used for a particular column of a dataset, rather 
            than the entire dataset as a whole.
          </para>
        </note>
        <section>
          <title>
            The access module (eml-access.xsd) - access control rules for
            resources
          </title>
          <para>
          </para>
        </section>
        <section>
          <title>
            The physical module (eml-physical.xsd) - online and 
            offline distribution of resources
          </title>
          <para>
          </para>
        </section>
        <section>
          <title>
            The party module (eml-party.xsd) - access control rules
            for resources
          </title>
          <para>
          </para>
        </section>
        <section>
          <title>
            The coverage module (eml-coverage.xsd) - geographic,
            temporal, and taxonomic extents of resources 
          </title>
          <para>
          </para>
        </section>
        <section>
          <title>
            The project module (eml-project.xsd) - research context information
            for resources
          </title>
          <para>
          </para>
        </section>
        <section>
          <title>
            The methods module (eml-methods.xsd) - methodological information
            for resources
          </title>
          <para>
          </para>
        </section>
        <section>
          <para>
          </para>
        </section>
      <para>

        <!--



        eml-entity.xsd
        eml-constraint.xsd

        eml-dataTable.xsd
        eml-attribute.xsd

        eml-spatialRaster.xsd
        eml-spatialReference.xsd
        eml-spatialVector.xsd

        eml-storedProcedure.xsd
        eml-view.xsd

        eml-text.xsd
        stmml.xsd
        eml-documentation.xsd
        -->

      </para>
      <para>
        --associated metadata (extending eml)
      </para>
      <para>
        --maybe a treeview image of how the modules are linked
      </para>
    </section>
  </chapter>

  <chapter id="technicalArch">
    <title>Technical Architecture (Normative)</title>
    <section>
      <title>Introduction</title>
      <para>
        This section explains the rules of EML.  There are some rules that cannot
        be written directly into the XML Schemas nor enforced by an XML parser.
        These are guidelines that every EML package must follow in order for
        it to be considered EML compliant.
      </para>
    </section>

    <section>
      <title>Module Structure</title>
      <para>
        Each EML module, with the exception of "eml" itself, has a top level
        choice between the structured content of that modules or a
        &quot;references&quot; field.  This enables the reuse of content
        previously defined elsewhere in the document.  Methods for defining
        and referencing content are described in the
        <link linkend="reusableContent">next</link> section
      </para>
    </section>

    <section id="reusableContent">
      <title>Reusable Content</title>
      <para>
        EML allows the reuse of previously defined structured content (DOM
        sub-trees) through the use of ID/IDREF type references.  In order
        for an EML package to remain cohesive and to allow for the cross
        platform compatability of packages, the following rules with respect
        to packaging must be followed.
      </para>
      <itemizedlist>
        <listitem>
          <para>
          IDs are required on all modules that extend resource.
          </para>
        </listitem>
        <listitem>
          <para>
          IDs are optional on all other modules.
          </para>
        </listitem>
        <listitem>
          <para>
          If an ID is not provided, that content must be interpreted as
          representing a distinct object.
          </para>
        </listitem>
        <listitem>
          <para>
          If an ID is provided for content then that content is distinct from
          all other content except for that content that references its ID.
          </para>
        </listitem>
        <listitem>
          <para>
          If a user wants to reuse content to indicate the repetition of an
          object, a reference must be used.  you cannot have two identical
          ids in a document.
          </para>
        </listitem>
        <listitem>
          <para>
          &quot;Local scope&quot; is defined as identifiers unique only to a
          single instance document (if a document does not have a system or if
          scope is set to 'local' then all ids are defined as distinct content).
          </para>
        </listitem>
        <listitem>
          <para>
          System scope is defined as identifiers unique to an entire data
          management system (if two documents share a system string, then
          any IDs in those two documents that are identical refer to the
          same object).
          </para>
        </listitem>
        <listitem>
          <para>
          If an element references another element, it must not have an ID.
          </para>
        </listitem>
        <listitem>
          <para>
          All EML packages must have the 'eml' module as the root.
          </para>
        </listitem>
        <listitem>
          <para>
          The system and scope attribute are always optional except for at the
          'eml' module where the scope attribute is fixed as 'system'.  The scope
          attribute defaults to 'local' for all other modules.
          </para>
        </listitem>

      </itemizedlist>

      <section>
        <title>ID and Scope Examples</title>
        <section>
          <title>Example Documents</title>
          <para><emphasis>This section is not yet complete.</emphasis>  It should
          contain:</para>
          <para>examples of the various rules described above</para>
        </section>
        <section>
          <title>Explanation</title>
          <para><emphasis>This section is not yet complete.</emphasis>  But it
          should contain:</para>
          <para>
            the explanation of the examples above
          </para>
        </section>
      </section>
    </section>
  </chapter>

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

  <index id="index">
    <title>Index</title>
      <indexdiv>
        <title>A</title>
        <xsl:for-each select="//doc:module">
          <xsl:for-each select="document(.)//xs:element">
            <xsl:sort select="@name" data-type="text"/>
            <xsl:if test="starts-with(@name, 'a')">
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
        <indexentry><primaryie/></indexentry>
      </indexdiv>
  </index>
</book>
</xsl:template>

<xsl:template match="doc:moduleDocs">
<xsl:param name="importedBy"/>
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
  </itemizedlist>
  <para>
    <xsl:value-of select="./doc:moduleDescription"/>
  </para>
</section>
</xsl:template>

<xsl:template match="xs:element" mode="indexentry">
  <indexentry>
    <primaryie>
      <ulink>
        <xsl:attribute name="url">./<xsl:value-of select="//doc:moduleName"/>.html#<xsl:value-of select="@name"/></xsl:attribute>
        <xsl:value-of select="@name"/></ulink>-<xsl:value-of select="//doc:moduleName"/>
    </primaryie>
  </indexentry>
</xsl:template>
</xsl:stylesheet>

