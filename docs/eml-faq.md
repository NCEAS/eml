[Back to EML Contents](./index.html)

**Q:** What is EML?

**A:** EML stands for Ecological Metadata Language. It exists as a set
of XML Schema documents that allow for the structural expression of
metadata necessary to document a typical data set in the ecological
sciences.

**Q:** Who is responsible for EML?

**A:** The first two released versions of EML, EML 1.0 and EML 1.4.1
were developed at the National Center for Ecological Analysis and
Synthesis (NCEAS), University of California at Santa Barbara, in Santa
Barbara, California USA. The effort to produce EML 2.x (and all of the
beta releases preceding it) is organized through the EML Project, an
open source, community oriented project dedicated to providing a
high-quality metadata specification for describing data relevant to the
ecological discipline. The project is completely comprised of [voluntary
project members](https://raw.github.com/NCEAS/eml/master/members.md) who
donate their time and experience in order to advance information
management for ecology. Project decisions are made by consensus
according to the voting procedures described in the [ecoinformatics.org
Charter](http://www.ecoinformatics.org/charter.html). Significant
contributions for these recent releases have come from individuals at
NCEAS, the Long Term Ecological Research Program (CAP, NET, KBS, JRN),
and the Joseph W. Jones Ecological Research Center in Newton, GA.

**Q:** Why would I want to use EML when FGDC now supports biological
data through the CSDGM?

**A:** EML is modular and extensible.

**A:** The Content Standard for Digital Geospatial Metadata (CSDGM)
developed by the Federal Geographic Data Committee (FGDC) is a
monolithic standard, and so it is difficult to mix and match parts of it
with other standards -- mainly because of all of the spatial
requirements. So, we built EML as a series of modules that can be linked
together and can be linked to other metadata standards. This gives us
the most flexibility, and given that we can easily translate into CSDGM
compliant documents, there is little cost. Second, we're building
advanced data processing tools that can automatically parse data sets
and analyze them based on the EML metadata descriptions. Due to various
shortcomings in the FGDC standard, mostly oriented around its tight
focus on spatial data, we have found that the CSDGM isn't adequate for
these needs, e.g., how can one add machine parsable, semantically
oriented attribute tags to CSDGM? Answer, you can't, because it is
monolithic and doesn't permit dynamic ties to other metadata specs --
the only extension method is via the administrative challenge of
creating a superset of the CSDGM -- not very maintainable. In addition,
the level of granularity for metadata in FGDC is very patchy -- it goes
into great detail for spatial projections, etc., but is incredibly terse
with respect to describing methods and non-standard data formats. This
is appropriate in the spatial world where there are few data formats
(&lt; 100, many sensor derived streams), but not so good in ecology
where there is no standardization of data formats (&gt;&gt;&gt;5000,
very few sensor derived).

**Q:** Is there documentation for EML in English?

**A:** Yes, there is a [formal specification](index.html) of EML
describing its development history, architecture, and modules. The
intent of each module is described in narrative and there is a technical
description of each module in XML notation. Included as part of the
technical description is an element-by-element description of the
module. We will eventually provide examples on usage.

**Q:** Why is EML such an important development?

**A:** The last decade has witnessed a tremendous explosion of
ecological and environmental data, catalyzed by societal concerns and
facilitated by advancing technologies. These data have the potential to
greatly enhance understanding of the complexity of the biosphere.
However, broad-scale or synthetic research is stymied because data are
largely unorganized and inaccessible as a consequence of their
tremendous heterogeneity, complexity, and spatial dispersion in many
separate repositories. EML is the first content standard designed
specifically to address these issues for ecological data. Wide adoption
and use of EML will create exciting new opportunities for data
discovery, access, integration and synthesis.

**Q:** How do I get EML?

**A:** All the documents associated with the EML development effort are
available via the project web server at
<http://knb.ecoinformatics.org/software/eml/>. These projects are
licensed under the GPL (Gnu Public License) agreement and can be freely
distributed and modified.

**Q:** The EML Schema documents are quite complex. An average ecologist
probably cannot and more likely does not want to mark up content in an
XML editor. How then do you get content into EML?

**A:** The Knowledge Network for Biocomplexity project has developed a
software client specifically to address this need.
[Morpho](http://knb.ecoinformatics.org/software/) (after the butterfly
genus) is written in java (making portable across computer platforms)
combines an easy to use interface to EML with a number of tools to make
it easier for ecologists to document data. These include a
reverse-engineering wizard. Morpho is available from
<http://knb.ecoinformatics.org/software>. Morpho currently supports the
EML 2.1.0 release.

**Q:** EML contains provisions for communication. Is it possible to
document in EML dynamic online data resources?

**A:** Yes, there are provisions in the eml-physical module for
descriptions of online data resources. The eml-physical module describes
the structural characteristics of data formats as delivered over the
wire or as found in a file system. One physical object (which can be a
bytestream or an object in a file system) might contain multiple
entities (for example, this would be typical in a MS Access file that
contained multiple tables of data). However, it is typically used to
describe a file or stream that is in some text-based format such as
ASCII or UTF-8, and includes the information needed to parse the data
stream to extract the entity and its attributes from the stream. There
are 3 distribution types, online, offline, and inline. To describe an
online dataset in EML you would populate the online element with the
distribution information.

**Q:** Do I need to download special software to use EML?

**A:** No, but there is software available to work with EML. See [FAQ
7](#id.7) .

**Q:** How can I get my existing metadata into EML?

**A:** There are several approaches that can be used to convert existing
metadata into EML depending on what form your existing metadata take.

-   Case 1: If your metadata is currently in a text format (not stored
    in a database) use the following conversion methods.

    1.  Write a script (PERL, PHP, JAVA, etc.) to convert the text into
        EML compliant XML.

    2.  Convert the text metadata into XHTML (HTML that is XML
        compliant). Write an XSLT script to transform the XHTML file
        into EML compliant XML.

    3.  Use an special purpose XML editor that generates EML (
        [Morpho](http://knb.ecoinformatics.org/software) or
        [Xylographa](http://ces.asu.edu/bdi/Subjects/xylographa) ) and
        manually retype the metadata.

    4.  Use a general purpose XML development tool such as XML Spy that
        can create a sample document from an XML Schema and retype the
        metadata manually.

    5.  Use a simple text editor and do everything from scratch.

    6.  Use specialized data transformation software such as the Data
        Junction suite to extract text data and then map it into an EML
        structure.

-   Case 2: If your metadata is stored in a relational database use the
    following conversion methods.

    1.  Both Microsoft SQL Server and Oracle have utilities to generate
        XML from their database. If you use a tool like that, then you
        will have to write an XSLT script to transform the generated XML
        into EML.

    2.  Use a vendor neutral Database-to-XML generator such as
        [Cocoon](http://cocoon.apache.org/) (an Apache open source free
        tool). Cocoon can query the database, generate XML, and has a
        tool for creating the XSL Transformation scripts to convert the
        first stage XML output into EML format.

    3.  Use a specialized tool such as
        [Xanthoria](http://ces.asu.edu/bdi/Subjects/Xanthoria/) (like
        Cocoon in may respects, but is easier to use) to generate XML
        from the database. Then use a tool such as XML Spy or Stylus
        Studio to develop the XSLT script to convert the generated XML
        into EML compliant XML.

    4.  Use specialized data transformation software such as the Data
        Junction query the database and map it into an EML structure.

-   Case 3: If your metadata is already in XML but in some other form
    such as NBII or FGDC use the following conversion method.

    1.  Write an XSLT script to convert from the current format to EML
        (e.g. FGDC to EML).

NOTE: In each of the cases it may be necessary to add some additional
metadata in order to produce EML compliant documents.
[Morpho](http://knb.ecoinformatics.org/software) will automatically
create EML compliant metadata either by adding it for you or indicating
that certain fields are mandatory.

**Q:** Once I convert my metadata into EML, what do I do with it? If I
am storing all my metadata in text-based EML files, how am I supposed to
query them or use them for data management?

**A:** EML is an exchange standard for communication of metadata but it
can be used as the framework for a data management system.
[Metacat](http://knb.ecoinformatics.org/software) is a multipurpose XML
metadata and data repository that is optimized for use with EML. If you
store your metadata in a relational database management system or plan
to then there are also solutions.
[Cocoon](http://xml.apache.org/cocoon/) and
[Xanthoria](http://ces.asu.edu/bdi/Subjects/Xanthoria/) are examples of
programs that can get EML out of an RDBMS. Cocoon and Xanthoria are both
java applications that use java database connection hooks and style
sheets to retrieve and format data. Xanthoria is a light-weight solution
and the XSLT stylesheets for EML 2.0 have already been written. This
solution lets a site stick with the RDBMS system that they probably have
integrated with their site management activities, yet also have their
metadata exposed via EML.

**Q:** Does the modularity of EML mean that one description can be
shared by many documents?

**A:** In a previous version, EML packages (via RDF like triples)
supported linking across packages, so you could re-use the same document
in multiple packages. In EML 2.0.0 Release Candidate 1 we redesigned the
packaging structure to only allow linking within a single package. Thus,
one could re-use a party description or attribute list within a package,
but not across several. This is a compromise that keeps some reusability
but has fewer management problems. Along with this change is an ability
to put all metadata and data in a single document for transport -- while
still not limiting ourselves to a monolithic structure. This has
benefits (akin to db normalization) and costs (access control,
ownership, and multiple update problems).

**Q:** How are EML modules linked together?

**A:** With "id" attributes and "references" elements in each module.
Certain modules within EML allow you to identify specific sub-trees with
a unique identifier (id). This identifier can then be used in place of
content in other parts of the EML document by placing it in a
"references" element.

**A:** Our general approach in EML has been to create ComplexTypes (CT)
when we wanted a particular block to be reusable. This concept was
extended for linking modules together by adding an optional attribute
named "id" of type "xs:string" for each ComplexType. This allows us to
uniquely address each block defined by a CT. For the "ResourceBase" CT,
this id element replaces the "identifier" element and acts as the
overall identifier for the package. The content model for each CT is a
choice between the existing content model and a new element named
"references" of type "xs:string". This element is used to hold a
reference to an existing sub-tree identified by its id. This
relationship between the "references" element and the "id" identifiers
is enforced by defining a "key" for the "id" elements and a "keyref" for
the "references" elements. This use of a key and keyref differs slightly
from the XML Schema case because in XML Schema, keys can not be null,
whereas we want people to be able to optionally omit the "id" attribute.
Consequently, we have incorporated the rules about the correspondence
between keys and keyrefs into the EML specification, but not into the
schemas directly. Thus, in order to validate that an EML document is
valid EML, you must use a parser that understands the referencing system
in EML and can check that it is used correctly. An example system that
handles this key validation is shipped with the EML distribution (see
the "EML Parser"). Here's a fragment of an example xml doc to
illustrate:

```xml
<creator id="id.p1">
  <individualName><surName>Jones</surName></individualName>
</creator>
<associatedParty>
  <references>id.p1</references>
  <role>lackey</role>
</associatedParty>
<contact>
  <references>id.p1</references>
</contact>
```            

This even works for types that extend other types as long as the
subclass is the one that does the referencing (e.g., associatedParty can
reference creator, but not vice versa).

**Q:** Can I put data into EML as well as metadata?

**A:** Yes, there are provisions in the eml-physical module for
inclusion of data. The module describes the structural characteristics
of data formats as delivered over the wire or as found in a file system.
One physical object (which can be a bytestream or an object in a file
system) might contain multiple entities (for example, this would be
typical in a MS Access file that contained multiple tables of data).
However, it is typically used to describe a file or stream that is in
some text-based format such as ASCII or UTF-8, and includes the
information needed to parse the data stream to extract the entity and
its attributes from the stream. There are 3 distribution types, online,
offline, and inline. To include data in EML you would populate the
inline element with the data file described in the data format element.
The data that is in the inline element should conform to the description
provided by the eml-physical module. Binary data files can be included
using Base64 encoding.

**Q:** What can I do with my EML structured metadata?

**A:** Tools are currently being developed to allow automated
heterogeneous data integration, analytical processing and quality
testing based on EML metadata. In general, using a metadata standard
such as EML will lessen your data entropy and make it more useful to you
and others in the future.

**Q:** Can I validate my EML documents against the DTD?

**A:** No. As of EML 2.0.0 we are no longer creating DTDs as part of the
EML release. Only [XML Schemas](http://www.w3.org/XML/Schema) will be
released. Even then, there are some EML rules which are not expressible
in XML Schema and for which you must use a specialized validator, such
as the "EML Parser" that ships with the distribution.

**Q:** Are there required elements in EML?

**A:** Yes, although we've made every attempt to limit required elements
in the cause of flexibility there are a number of pieces of information
required to make sense of the metadata document. To make the metadata
more useful we do have recommended usages on the modules. See
specification for details about required fields and recommended usage.
In the future we may provide usage compliance information such that if
you want your data and metadata to be useful in a particular analytical
context you will be provided with those elements of EML that are
required for that purpose.

**Q:** There appear to be multiple places to put some types of metadata
in EML. How do I know which of these places is the right place for my
information?

**A:** The [EML Specification](index.html) describes each element in a
detailed normative manner. EML is hierarchical so where you use
different elements is very important. For instance, if you use a
TemporalCoverage element and reference it to a dataset element, you are
saying that that entire dataset took place during that time. If,
instead, you reference it to a dataTable, you are saying that only that
table was covered by that time period. You must gauge exactly what you
are trying to describe in the structure that you are using. Questions
about possible bugs in the definitions of elements can be posed via
email to the [eml-dev mailing list](mailto:eml-dev@ecoinformatics.org)

**Q:** The differences between "method" and "protocol" seem to be very
subtle in EML. How do I distinguish between the two?

**A:** The eml-methods module describes the methods followed in the
creation of the dataset being described, including description of field,
laboratory and processing steps, sampling methods and units, quality
control procedures. The eml-methods module is used to describe the
"actual" procedures that are used in the creation or the subsequent
processing of a dataset. Likewise, eml-methods is used to describe
processes that have been used to define / improve the quality of a data
file, or to identify potential problems with the data file. The
eml-protocol module is intended to be used to document a "prescribed"
procedure, whereas the eml-method module is used to describe procedures
that were actually performed. The distinction is that the use of the
term "protocol" is used in the "prescriptive" sense, and the term
"method" is used in the "descriptive" sense. This distinction allows
managers to build a protocol library of well-known, established
protocols (procedures), but also document what procedure was truly
performed in relation to the established protocol. The method may have
diverged from the protocol purposefully, or perhaps incidentally, but
the procedural lineage is still preserved and understandable.

The eml-methods module, like other modules, may be referenced via the
&lt;references&gt; tag. This allows a method to be described once, and
then used as a reference in other locations within the EML document via
it's ID.

**Q:** How can 'references' be treated in XSLT transformations of EML?

**A:** XSLT can be used to transform EML to other formats, but the
treatment of 'references' elements is somewhat complicated. A text file
describing the details of one method for handling the 'references'
elements is available at
<http://knb.ecoinformatics.org/software/eml/eml-2.0.1/references_XSLT.txt>

**Q:** How do the elements of the Dublin Core Metadata Initiative map
onto EML?

**A:** EML attempts to capture the metadata elements of the Dublin Core
in its specification, while still maintaining the flexibility to be able
to document the full range of ecological resources. More detailed
information on the representation of the Dublin Core elements in EML can
be found at
<http://knb.ecoinformatics.org/software/eml/eml-2.0.1/eml-dublinCore.html>

**Q:** I'm interested in contributing to EML. Can I?

**A:** We welcome contributions to this work in any form. Individuals
who invest substantial amounts of time and make valuable contributions
to the development and maintenance of EML (in the opinion of current
project members) will be invited to become EML project members according
to the rules set forth in the [ecoinformatics.org
Charter](http://www.ecoinformatics.org/charter.html). Contributions can
take many forms, including the development of the EML schemas, writing
documentation, and helping with maintenance, among others.

You can contact the [eml-dev mailing
list](mailto:eml-dev@ecoinformatics.org) if you would like to make a
contribution in person-hours to this project and would like to discuss
how that might occur. In general, we want all of the help we can get!

**Q:** Where can I get EML?

**A:** You can download archived releases from
<http://knb.ecoinformatics.org/software/eml/> or you can check out the
latest development version from [GitHub](https://github.com/NCEAS/eml/)
server.

**Q:** How is EML 2.1.0 different from EML 2.0.0/1, and how do I upgrade
my documents?

**A:** There are several improvements to EML in version 2.1.0 -- too
many to put here. For more information, see the EML 2.1.0 Information
page: [Information for EML 2.1.0 Document Authors.](./eml-210info.html)
That page also contains instructions for using the transformation
stylesheet that comes with EML2.1.0 to convert v2.0.0/1 documents to
v2.1.0.


**Q:** 

**A:**


**Q:** What does ‘dereferenced’ mean?  (context: a URI in an annotation)

**A:** Within the context of semantic annotation, "dereferencing" refers to the process of interpreting a URI, and providing "useful information" back about the Resource of interest. The phrase "resolving a URI" is often used synonymously with "dereferencing", but technically "resolution" refers to the process of determining HOW and WHAT to do with the URI, whereas "dereferencing" is explicitly about the action taken, which is typically retrieving a representation of the Resource of interest. The formal specification for these terms and what they mean is found in the IETF's (Internet Engineering Task Force) RFC (Request for Comment) 3986 (<https://tools.ietf.org/html/rfc3986>). 


**Q:** Explain the difference between an URI and a URL. (context: sample URIs look a lot like a URL.  What makes it a URI?)

**A:** The distinctions among URIs (Uniform Resource Identifiers), URLs (Uniform Resource Locators), and URNs (Uniform Resource Names), relate to differentiating the functionalities of *identifying* a Resource, as opposed to *locating* a Resource, or doing both. URLs are all URIs (with some edge case exceptions subject to argument), and URNs are also URIs.  In many cases, URIs serve both to name and locate a Resource.  Within the vision of the Semantic Web, URIs are ideally unique, persistent URNs identifying some Web Resource, that can also serve to locate and retrieve (dereference) a representation of that Resource (URLs). The formal specification for these terms and what they mean is found in the IETF's RFC 3986, section 1.1.3 (<https://tools.ietf.org/html/rfc3986#section-1.1.3>). Another acronym one may encounter with increasing frequency is IRI (Internationalized Resource Identifier) that simply extends the concept of a URI to include full Unicode character set, rather than just ASCII in its construction (<https://tools.ietf.org/html/rfc3987>)


**Q:** oveeview (“When are ID’s required in the EML”) - context: annotations

**A:**


**Q:** What is SKOS?

**A:** SKOS (Simple Knowledge Management System) is a W3C recommendation for organizing a vocabulary in thesauri, taxonomies, and other classification schemes. SKOS provides a set of concepts and properties, that, when expressed in a formal RDF-compatible syntax, can assist with interpreting the relationship of terms with one another, such as defining some category as *broader than* another. For example, one could state in SKOS syntax, that "animals" is a *broader concept than* "mammals". Definitive specification of SKOS can be found at <https://www.w3.org/TR/2009/REC-skos-reference-20090818/>. SKOS does not provide strong semantics (see RDFS example below), but SKOS concepts and properties can be used within more expressive knowledge organization frameworks, such as RDFS/OWL ontologies.


**Q:** What is RDFS?

**A:** RDFS (Resource Description Framework Schema; <https://www.w3.org/TR/rdf-schema/>) is a W3C recommendation that extends the formal vocabulary for describing Resources expressed in an RDF data model (i.e., in a *graph*). "Base" RDF <https://www.w3.org/TR/2014/REC-rdf11-concepts-20140225/> provides a set of concepts for creating a *graph* model of data-- consisting of one or more *triples* relating a *subject*, *predicate*, and *object*.  RDFS adds to the base RDF model by specifying a number of well-defined concepts and properties, such as *rdfs:Class* and *rdfs:subClassOf*. These and other RDFS classes and properties, enable data and knowledge modellers to express many relationships between the Subject and Object of a *Triple*. In the context of the Semantic Web, the RDF model relies extensively on dereferenceable URIs in the subject and predicate positions, and URIs or literals in the object position (there are small formal exceptions to this not immediately relevant here). RDF triples can be expressed in several syntaxes, including XML, JSON-LD, and Turtle, among others. RDFS then can be used to enrich the precision and expressivity of the components of a triple, as well as clarify the relationships among these.


**Q:** An example of a controlled vocabulary with a *rdfs label* or *skos label* would be helpful here in this text about labels.  

**A:** Most Semantic Web vocabularies make extensive use of *rdfs:label* or SKOS label properties.  For example, this URI:
<http://purl.dataone.org/odo/ECSO_00000536> is from the ECSO8.owl ontology, under development by NSF's DataONE and Arctic Data Center. Within that ontology, the URI is associated with an *rdfs:label* of "Carbon Dioxide Flux", and a *skos:altLabel* of "CO2 flux". If you dereference the URI, you will see how the BioPortal ontology repository displays this information-- providing a human-readable representation of the underlying RDF/OWL language in which the ontology is stored.


**Q:** RDF Graph: An image is great, but a computer doesn't parse that.  What does the RDF look like?

**A:**  As mentioned above, RDF is a data model based on *triples*, each of which consists of a *subject*, *predicate*, and *object*.  In order to function interoperably on the Web, however, there is the need for these triple components to be constructed of dereferenceable URIs, although the *object* value can also be a *literal*. RDF triples can be "serialized" in several syntaxes, including XML, JSON-LD, Turtle, N-Triples, and others. These syntaxes are isomorphic, such that translations of RDF graphs from one serialization to another are available-- enabling consistent interpretation by machines.

Perhaps the most straigthforward serialization of RDF graphs for human interpretation is N-Triples, where an RDF triple could look like this:

 <http://purl.obolibrary.org/obo/CHEBI_16526>   <http://purl.obolibrary.org/obo/RO_0000087>  <http://purl.obolibrary.org/obo/CHEBI_76413>  .
 
These are three URIs-- representing the Subject, Predicate, and Object of a Triple. The "." indicates the end of the Triple. Dereferencing these URIs (e.g. a Web browser or specialized application) one can see that this Triple represents the statement:

*"Carbon dioxide"*(Subject) *"has role"*(Predicate) *"Greenhouse Gas"*(Object)

While the phrasing is a bit awkward sounding, the meaning is clear by simply depicting the *rdfs:Labels* of those terms from the ChEBI (Chemical Entities of Biological Interest) and RO (Relation) ontologies, that are both robust OBO Foundry ontologies.

As another example:
<http://purl.obolibrary.org/obo/NCIT_C20461> <http://purl.org/dc/elements/1.1/creator> <https://orcid.org/0000-0003-1279-3709> .

that asserts:

*"World Wide Web"*(Subject) *"creator"*(Predicate)  *"Timothy Berners Lee"*(Object) .

...although some semantic purists might question whether the Dublin Core property "Creator" can be used in this way as an RDF predicate, since it is not semantically defined-- would its rdfs:label be "creatorOf" or "hasCreator"?. Regardless of the formal semantic well-formedness  of this Triple, however, one can see the expressive power of the RDF data model, and the value of dereferenceable URIs.

A better solution would be to use the semantically defined term from SIO (the Semantic Science Integrated Ontology) <http://semanticscience.org/resource/SIO_000364> as the predicate, with an rdfs:label *"has creator"*

<http://purl.obolibrary.org/obo/NCIT_C20461> <http://semanticscience.org/resource/SIO_000364> <https://orcid.org/0000-0003-1279-3709> .

...that would translate as (based on content of the rdfs:label):

*World Wide Web*(Subject) *has creator*(Predicate)  *Tim Berners Lee*(Object)

or inversely, one could use <http://semanticscience.org/resource/SIO_000365> as the predicate, that has rdfs:Label *"is creator of"*

 *Tim Berners Lee*(Subject) *is creator of*(Predicate)  *World Wide Web*(Object)

<https://orcid.org/0000-0003-1279-3709> <http://semanticscience.org/resource/SIO_000365> <http://purl.obolibrary.org/obo/NCIT_C20461>.
 
 Within the SIO ontology, SIO_000364 and SIO_000365 are defined as inverses of one another.  This enables one to ask both questions-- "who created the Web?" (A: Tim Berners Lee), and "what did Tim Berners Lee create" (A: the Web)-- even though you only asserted one of the Triples.
 
 Finally, it is worth noting that one's choice of which Ontologies to use is important.  Within the Ecological and Environmental sciences, there are several highly-recommended vocabularies, including those from the OBO Foundry (e.g. ChEBI, EnvO), as well as SIO.  Specifically for annotating scientific *measurements*, the Arctic Data Center and DataONE are developing an Ontology for Ecosystem Measurements, ECSO. 

**Q:** Are there tools are available to help data managers select subjects, predicates, and objects to annotate with? 

**A:** Yes, tools are being built to assist with the semantic annotation of EML documents,  within the DataONE and Arctic Data Center data repository projects, and others.
