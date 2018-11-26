# Features

The architecture of EML was designed to serve the needs of the
research community, and has benefitted from previous work in other
related metadata languages. EML has adopted the strengths of many of
these languages, but also addresses a number of shortcomings that have
inhibited the automated processing and integration of dataset
resources via their metadata.

The following list represents some of the features of EML:

-   Modularity: EML was designed as a collection of modules rather than
    one large standard to facilitate future growth of the language in
    both breadth and depth. By implementing EML with an extensible
    architecture, groups may choose which of the core modules are
    pertinent to describing their data, literature, and software
    resources. Also, if EML falls short in a particular area, it may be
    extended by creating a new module that describes the resource (e.g.
    a detailed soils metadata profile that extends eml-dataset). The
    intent is to provide a common set of core modules for information
    exchange, but to allow for future customizations of the language
    without the need of going through a lengthy approval process.

-   Detailed Structure: EML strives to balance the tradeoff of too much
    detail with enough detail to enable advanced services in terms of
    processing data through the parsing of accompanied metadata.
    Therefore, a driving question throughout the design was: 'Will this
    particular piece of information be machine-processed, just human
    readable, or both?' Information was then broken down into more
    highly structured elements when the answer involved machine
    processing.

-   Compatibility: EML adopts much of it's syntax from the other
    metadata standards that have evolved from the expertise of groups in
    other disciplines. Whenever possible, EML adopted entire trees of
    information in order to facilitate conversion of EML documents into
    other metadata languages. EML was designed with the following
    standards in mind: Dublin Core Metadata Initiative, the Content
    Standard for Digital Geospatial Metadata (CSDGM from the
    Federal Geographic Data Committee (FGDC)), the
    Biological Profile of the CSDGM (from the National Biological
    Information Infrastructure), the International Standards
    Organization's Geographic Information Standard (ISO 19115), the ISO
    8601 Date and Time Standard, the OpenGIS Consortiums's Geography
    Markup Language (GML), the Scientific, Technical, and Medical Markup
    Language (STMML), and the Extensible Scientific Interchange Language
    (XSIL).

-   Strong Typing: EML is implemented in an Extensible Markup Language
    (XML) known as [XML Schema](http://www.w3.org/XML/Schema), which is
    a language that defines the rules that govern the EML syntax. XML
    Schema is an internet recommendation from the [World Wide Web
    Consortium](http://www.w3.org), and so a metadata document complies
    with the syntax of EML will structurally meet the
    criteria defined in the XML Schema documents for EML. Over and above
    the structure (what elements can be nested within others,
    cardinality, etc.), XML Schema provides the ability to use strong
    data typing within elements. This allows for finer validation of the
    contents of the element, not just it's structure. For instance, an
    element may be of type 'date', and so the value that is inserted
    in the field will be checked against XML Schema's definition of a
    date. Traditionally, XML documents (including previous versions of
    EML) have been validated against Document Type Definitions (DTDs),
    which do not provide a means to employ strong validation on field
    values through typing.

-   There is a distinction between the content model (i.e. the concepts
    behind the structure of a document - which fields go where,
    cardinality, etc.) and the syntactic implementation of that model
    (the technology used to express the concepts defined in the content
    model). The normative sections below define the content model and
    the XML Schema documents distributed with EML define the syntactic
    implementation. For the foreseeable future, XML Schema will be the
    syntactic specification, although it is reasonable to create other
    syntactic representations of the vocabularly, such as in JSON-LD or RDF.
