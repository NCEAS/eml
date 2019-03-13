# Semantic Annotation Primer

## Introduction
The purpose of this primer is to provide an introduction to how semantic annotations are structured 
in EML documents. It is expected that you have some familiarity with the EML schema prior to reading this document. It is important to note that our approach of using annotations structured in the Resource Description Framework (RDF) specification is based on recommendations from the World Wide Web Consortium (W3C) about how a Semantic Web should be constructed.
If you want to read more about the W3C's RDF data model, graphs or the semantic web, there is supplemental material at the bottom of this primer. 

A semantic annotation involves the attachment ("annotation") of semantic metadata to a resource -- which in this context would be an EML element. A semantic annotation provides a pointer (HTTP universal resource identifier; URI) that should resolve (and dereference) to useful descriptions, definitions, or relationships that the annotated resource has, relative to other terms or resources, and do so in a computer-usable way. These "pointers" reference terms organized into web-accessible *knowledge graphs* (also called "ontologies"). The process of creating semantic annotations may seem tedious, but the payoff is vastly enhanced information discovery and interpretation. Semantic annotations will make it easier for others to find and reuse your data (and thus give you credit). 

For  example, if a dataset is annotated as being about "carbon dioxide flux" and another dataset is annotated as being about 
"CO2 flux" the information system can recognize that these datasets are about equivalent concepts, because this equivalence can be indicated in a "computer-usable" way through the semantic annotation-- e.g. by sharing the same HTTP URI for their annotation. 

As another example, if you perform a search for datasets about "litter" (as in "plant litter"), the 
system will be able to disambiguate the term from the many meanings of "litter" (as in garbage, the grouping of 
animals born at the same time to the same mother, etc.). These different types of litter could be disambiguated because they would be annotated with different identifiers (HTTP URI). Yet another example is if you search for datasets about "carbon flux", then datasets about "carbon dioxide flux" can also be returned because "carbon dioxide flux" is considered a type of "carbon flux".  This is possible because the identifier (HTTP URI) for "carbon dioxide flux" would be organized into a hierarchy in its knowledge graph, and represented in this case as a subclass of "carbon flux".

**Semantic statements must be logically consistent, as they are not simply a set of loosely structured keywords.** 
The examples in this primer should also make clear that inconsistent annotations could create confusion. 
So be careful, and if you have questions, bring them up in your community for feedback.

If you already understand the basics of how RDF and the Semantic Web work, and are eager to simply learn how to implement semantic annotations in your EML documents, feel free to skip the next two sections, and go directly to the section on "Semantic Annotations in EML 2.2.0".  If you want a refresher on how these semantic annotations will work, and why they are immensely powerful, the next two sections may be helpful. 

### Semantic triples

Semantic annotations enable the creation of what are called *triples*, that are 3-part statements conforming to the W3C recommended *RDF data model* (learn more: <https://www.w3.org/TR/rdf11-primer/>). 

A *triple* is composed of three parts: a **subject**, a **predicate (object property or data property)**, and an **object**.

```
[subject] [predicate] [object]
```

These components are analogous to parts of a sentence: the **subject** and **object** can be thought of as nouns in the sentence and the **predicate** (object property or data property) is akin to a verb or relationship that connects the **subject** and **object**. The semantic triple expresses a statement about the associated resource, that is generally the **subject**. 

There are (perhaps unfortunately) several other ways that the components of an RDF statement are sometimes described.  One popular "synonymy" for **subject-predicate-object** is **resource-property-value**, i.e. the subject is referred to as the **resource**, the predicate a **property**, and the object a **value**.  This can be confusing, since the usual definition of a *resource* is any identifiable 'thing' or object, especially one assigned a URI; and by this definition, *resources* can and often do occur in all three components of a triple.  But thinking of a triple as a *resource-property-value* does provide an indication of the directionality of the semantics of an RDF statement.  This latter terminology is also somewhat similar to how analogous components are named in JSON-LD.  Note that JSON-LD is closely compatible with RDF, and one format can often be readily translated to the other (although there are some exceptions).

Semantic annotations added to an EML document can be extracted and processed into a semantic web format, such as RDF/XML, such that the semantic statement(s), i.e. RDF triples, become interpretable by any machines that can process the W3C standard of RDF. Those RDF statements contribute to the Semantic Web.

#### URIs
Ideally, the components of the semantic triple should be globally 
unique and persistent (unchanging), and consist of resolvable/dereferenceable HTTP uniform resource identifiers (URIs; or more formally, IRI's). The *subjects* of most EML semantic annotations will likely be HTTP URI's that identify the dataset resource itself, or specific attributes or other features within a dataset.  The *objects* of EML semantic annotations, as well as the *predicates* that relate the subject to the object, will most typically be HTTP URI references to terms in controlled vocabularies (also called "knowledge graphs", or "ontologies") accessible through the Web, so that users (or computers) can dereference the URI's and look up precise definitions and relationships of these resources to other terms. 

An example of a URI is "http://purl.obolibrary.org/obo/ENVO_00000097", when entered into the address bar of a web browser, resolves to the term with a label of "desert area" in the Environment Ontology (EnvO). Users can learn what this URI indicates and explore how the term is related to other terms in the ontology simply by dereferencing its URI in a web browser.  All those other aspects you see on the Web page describing "http://purl.obolibrary.org/obo/ENVO_00000097" are from RDF statements (triples) that have been rendered into HTML. From here, you might realize that "http://purl.obolibrary.org/obo/ENV0_00000172" ("sandy desert") is a better annotation for your object.

An RDF triple can be constructed as follows, with subject URI, predicate URI, and object URI:

   <<https://doi.org/10.6073/pasta/06db7b16fe62bcce4c43fd9ddbe43575>>
   
   <<http://purl.obolibrary.org/obo/RO_0001025>>
   
   <<http://purl.obolibrary.org/obo/ENVO_00000097>>
   
   .
   
... indicating that the referenced *dataset* (subject/resource) was *"located in"* (predicate/property) a *"desert area"* (object/value).
Note that a blank-space must separate the subject, from the predicate, from the object, and that a "period" completes the triple. This is  a valid RDF triple, expressed in N-Triple syntax.  RDF is most often serialized into XML, however, as Web browsers and many applications are good at parsing XML. 

While the *essence* of the RDF data model is as simple as having URI's indicating the subject, predicate, and object constituting a *triple*, there are also *blank nodes* that can occur in the subject and object positions, and *literals* can occur as objects-- but these are complexities beyond the scope of this Primer, and not necessary to know in order to do extremely useful semantic annotation of EML elements. While our focus here is on semantic annotation of EML documents, it is easy to see how the RDF model can be used to describe, in a triple, any resource that has a URI.

Note that the above *RDF triple* consists of three HTTP URIs. While the exact distinction among what is a URI, a URN, and a URL can be debated, essentially all URLs (Uniform Resource Locators) are URIs -- they point to a location where some resource exists (in the case of an HTTP URL, on the Web) and can be resolved or dereferenced. But a URI can also serve as, ideally, a (globally) *unique and persistent name* of a resource, i.e., it is a URN (Uniform Resource Name). While URIs, URNs, and URLs don't necessarily have to work with the HTTP protocol, for practical purposes in the present, these are most useful if they work well with the Web, and thus HTTP. Having an HTTP URI, however, does not mean that these are only useful for viewing in a Web browser. Content negotiation between a Web server and a client (which might be a browser, or a Python or R script) can enable an HTTP URI to dereference in ways optimized for the requesting client -- e.g. in one case, presenting a human-readable view of metadata for a dataset, and in another, activating a download of that dataset for import into a script.

EML 2.2.0 now provides ways to embed HTTP URI's into several EML elements, thus serving as semantic annotations of those elements, such that those EML snippets can be extracted and serialized into the formal Semantic Web vocabulary of RDF.  These latter functions, i.e. extracting the semantic annotations and converting these into valid RDF triples as in the example provided above, will rely on software tools that are under development at NCEAS and EDI, and through the rOpenSci project. The RDF triple described above, however, hopefully gives an idea of how triples, constructed of dereferenceable HTTP URI's, can be very useful. The sections below describe the exact syntax for embedding annotations in EML 2.2.0 documents. 

## Semantic Annotations in EML 2.2.0
In **EML 2.2.0** there are 5 places where annotation elements can appear in an EML document: 

- **top-level resource**  -- an `annotation` element is a child of the `dataset`, `literature`, `software`, or `protocol` elements
- **entity-level** -- an `annotation` element is a child of a dataset's entity (e.g., `dataTable` )
- **attribute** -- an `annotation` element is a child of a dataset entity's `attribute` element
- **eml/annotations** -- a container for a group of `annotation` elements, using references
- **eml/additionalMetadata** -- `annotation` elements that reference a main-body element by its id

### Annotation element structure
All annotation nodes are defined as an XML type, so they have the same structure anywhere they appear
in the EML record. Here is the basic structure. Sections below have more examples.

```xml
    <annotation>
        <propertyURI label="property label here">property URI here</propertyURI>
        <valueURI label="value label here">value URI here</valueURI>
    </annotation>
```

- `propertyURI` and `valueURI` elements  
  - must each have a `label` attribute that is suitable for application interfaces
  - are recommended to have labels populated by values from the label field (`rdfs:label`) or preferred labels field (`skos:prefLabel`) from the referenced vocabulary
  - should contain URIs that point to terms in controlled vocabularies providing precise definitions, relationships to other terms, and multiple labels for display 



**When are IDs required?**
Annotations at the dataset, entity or attribute level presume that the parent element is the *subject*. If an element has
an annotation child, an id is required (i.e. the subject element must have an `id` attribute value). Annotations at `eml/annotations` or `eml/additionalMetadata` will have 
subjects defined with a `references` attribute or `describes` element. For other internal EML references, an `id` is required.
The EML-2.2 parser checks for an `id` attribute if an annotation is present. As a reminder, the `id` must be unique within an EML document.

**Labels**: It is recommended that the label field of the annotation is populated by the value from the label field (`rdfs:label`: that should always be present) or preferred label field (`skos:prefLabel`: that sometimes are provided) from the referenced vocabulary.


### Top-level resource, entity-level, and attribute annotations 

Annotations for top-level resources, entities, and attributes follow the same general pattern.

- The *subject* of the semantic statement is the parent element of the annotation. It must have an `id=" "` attribute. 


#### Example 1: Top-level resource annotation (dataset)

In the following dataset annotation, the *subject* of the semantic statement is the `dataset` element containing 
the `id` attribute value `"dataset-01"`. The predicate-- "http://purl.obolibrary.org/obo/IAO_0000136", is an *object property* explicating the relationship of the subject to the object. 
Finally, the *object* (value) in the semantic statement is "http://purl.obolibrary.org/obo/ENVO_01000177", which resolves 
to the "grassland biome" term in the EnvO ontology (http://www.obofoundry.org/ontology/envo.html). 

Taken together, the semantic statement can be read as "the dataset with the id 'dataset-01' is about grassland biome(s)".


```xml
<dataset id="dataset-01">
    <title>Data from Cedar Creek LTER on productivity and species richness for use in a workshop titled
    "An Analysis of the Relationship between Productivity and Diversity using Experimental Results from
    the Long-Term Ecological Research Network" held at NCEAS in September 1996.</title>
    <creator id="clarence.lehman">
        <individualName>
            <salutation>Mr.</salutation>
            <givenName>Clarence</givenName>
            <surName>Lehman</surName>
        </individualName>
    </creator>
    ...    
    <coverage> 
        ...
    </coverage>    
    <annotation>
        <propertyURI label="is about">http://purl.obolibrary.org/obo/IAO_0000136</propertyURI>
        <valueURI label="grassland biome">http://purl.obolibrary.org/obo/ENVO_01000177</valueURI>
    </annotation>
      ...    
</dataset>  
```

 #### Example 2: Entity-level annotation

In the following entity-level annotation, the subject of the semantic statement refers to the `otherEntity` 
element's `id` attribute value, "urn:uuid:9f0eb128-aca8-4053-9dda-8e7b2c43a81b". The object property of the 
statement is "http://purl.obolibrary.org/obo/IAO_0000136". Finally, the object (value) in the semantic statement 
is "http://purl.obolibrary.org/obo/NCBITaxon_40674", which resolves to the "Mammalia" term in the NCBITaxon 
ontology (http://www.ontobee.org/ontology/NCBITaxon). 

Taken together, the semantic statement indicates that "the entity with the id 'urn:uuid:9f0eb128-aca8-4053-9dda-8e7b2c43a81b' is about Mammalia".


```xml
<otherEntity id="urn:uuid:9f0eb128-aca8-4053-9dda-8e7b2c43a81b" scope="document">
    <entityName>DBO_MMWatch_SWL2016_MooreGrebmeierVagle.xlsx</entityName>
    <entityDescription>Data contained in the file DBO_MMWatch_SWL2016_MooreGrebmeierVagle.xlsx are marine mammal observations and observation conditions from CCGS Sir Wilfrid Laurier July 10-20, 2016.  Data observations and locations are part of the Distributed Biological Observatory (DBO).</entityDescription>
    <physical scope="document">
        <objectName>DBO_MMWatch_SWL2016_MooreGrebmeierVagle.xlsx</objectName>
        <size unit="bytes">24635</size>
    </physical>
    <entityType>Other</entityType>
    <annotation>
        <propertyURI label="is about">http://purl.obolibrary.org/obo/IAO_0000136</propertyURI>
        <valueURI label="Mammalia">http://purl.obolibrary.org/obo/NCBITaxon_40674</valueURI>  
    <annotation>
</otherEntity>
```

#### Example 3: Attribute annotation

In the following attribute annotation, the subject of the semantic statement is the `attribute` element 
containing the `id` attribute value "att.4". The object property of the statement is 
"http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#containsMeasurementsOfType". Note that the URI for the 
object property resolves to a specific term in the OBOE ontology (https://github.com/NCEAS/oboe). Finally, the object (value) 
in the semantic statement is "http://purl.dataone.org/odo/ECSO_00001197", which resolves to the "Plant Cover Percentage" 
term in the ECSO Ontology (https://github.com/DataONEorg/sem-prov-ontologies/tree/master/observation). 

Taken together, the semantic statement indicates that "the attribute with the id 'att.4' contains measurements of type plant cover percentage".


```xml
<attribute id="att.4">
    <attributeName>pctcov</attributeName>
    <attributeLabel>percent cover</attributeLabel>
    <attributeDefinition>The percent ground cover on the field</attributeDefinition>
    <annotation>
        <propertyURI label="contains measurements of type">http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#containsMeasurementsOfType</propertyURI>
        <valueURI label="Plant Cover Percentage">http://purl.dataone.org/odo/ECSO_00001197</valueURI>
    </annotation>
</attribute>
```

### `eml/annotations` element annotation
An annotation in the `annotations` element differs from other types of annotations in that the subject is directly referred to by a `references` attribute. Each `annotation` element has a `references` attribute that 
points to the `id` attribute of the element being annotated. Stated another way, what is listed in the `references` attribute is the id of the subject of the semantic annotation. Any of the EML modules may be referenced by the `references` 
attribute and because ids are unique within an EML document, this is a single subject. 

- The *subject* of the semantic statement is implictly the element containing the referenced `id`. 


#### Example 4: `annotations` element annotation

Example 4 contains 3 different annotations. For the first annotation, 
the subject of the semantic triple is the `dataTable` element with the `id` attribute "CDF-biodiv-table". Notice 
that the annotation has a `references` attribute that points to the subject id. The object property of the triple 
is "http://purl.obolibrary.org/obo/IAO_0000136". Finally, the value (object) in the semantic triple 
is "http://purl.obolibrary.org/obo/ENVO_01000177", which resolves to the "grassland biome" term in the 
EnvO ontology (http://www.obofoundry.org/ontology/envo.html). Taken together, the first semantic statement could be 
read as "the dataTable with the id 'CDR-biodiv-table' is about grassland biome(s)".

The second annotation has as its subject the `creator` element that has the id "adam.shepherd", 
the object property "http://www.w3.org/1999/02/22-rdf-syntax-ns#type" and the value (object) "https://schema.org/Person". 
This statement can be interpreted as "'adam.shepherd', the creator (of the dataset), is a person".

The third annotation also has as its subject the `creator` element that has the id "adam.shepherd". 
The object property is "https://schema.org/memberOf" and the object (value) is "https://doi.org/10.17616/R37P4C". 
This statement can be read as "'adam.shepherd', the creator (of the dataset), is a member of BCO-DMO".


```xml
<eml>
   ...
    <dataset id="dataset-01">
        <title>Data from Cedar Creek LTER on productivity and species richness for use in a workshop titled "An Analysis of the Relationship between Productivity and Diversity using Experimental Results from the Long-Term Ecological Research Network" held at NCEAS in September 1996.</title>
        <creator id="adam.shepherd">
            <individualName>
                <salutation>Mr.</salutation>
                <givenName>Adam</givenName>
                <surName>Shepherd</surName>
            </individualName>
        </creator>
        <dataTable id="CDR-biodiv-table">
            <entityName>CDR LTER-patterns among communities.txt</entityName>  
        ...
       </dataTable>  
    </dataset>
    ...
    <annotations>
        <annotation references="CDR-biodiv-table">
            <propertyURI label="is about">http://purl.obolibrary.org/obo/IAO_0000136</propertyURI>
            <valueURI label="grassland biome">http://purl.obolibrary.org/obo/ENVO_01000177</valueURI>
        </annotation>
        <annotation references="adam.shepherd">
            <propertyURI label="is a">http://www.w3.org/1999/02/22-rdf-syntax-ns#type</propertyURI>
            <valueURI label="Person">https://schema.org/Person</valueURI>
        </annotation>
        <annotation references="adam.shepherd">
            <propertyURI label="member of">https://schema.org/memberOf</propertyURI>
            <valueURI label="BCO-DMO">https://doi.org/10.17616/R37P4C</valueURI>
        </annotation>
    </annotations>
   ...
</eml>
```

### `eml/additionalMetadata` element annotation

If an `additionalMetadata` section holds a semantic annotation, it must have a `describes` element (to hold the subject) with a `metadata` element containing at least one `annotation` element. 

- The *subject* of the semantic statement has its id contained in the `describes` element. 
- The annotation itself is within the `additionalMetadata` `metadata` section
- Multiple `annotation` elements may be embedded in the same `metadata` element to assert multiple semantic statements about the same subject.
- To annotate different subjects it's best to use additional `additionalMetadata` sections, each with a single subject


#### Example 5: `additionalMetadata` element annotation

The following `additionalMetadata` annotation describes a semantic statement where the subject is the `creator` element with the `id` attribute "adam.shepherd". The object property of the statement is "https://schema.org/memberOf". 
Finally, the object (value) in the semantic statement is the DOI for BCO-DMO, "https://doi.org/10.17616/R37P4C". 
Taken together, the semantic statement could be read as "'adam.shepherd' (the creator of the dataset) is a member of BCO-DMO".


```xml
<eml>
    ...
    <dataset id="dataset-01">
        <title>Data from Cedar Creek LTER on productivity and species richness for use in a workshop titled "An Analysis of the Relationship between Productivity and Diversity using Experimental Results from the Long-Term Ecological Research Network" held at NCEAS in September 1996.</title>
        <creator id="adam.shepherd">
            <individualName>
                <salutation>Mr.</salutation>
                <givenName>Adam</givenName>
                <surName>Shepherd</surName>
            </individualName>
        </creator>
        <dataTable id="CDR-biodiv-table">
            <entityName>CDR LTER-patterns among communities.txt</entityName>  
         ...
       </dataTable>  
    </dataset>
    ...
     <additionalMetadata>
         <describes>adam.shepherd</describes>
         <metadata>
             <annotation>
                 <propertyURI label="member of">https://schema.org/memberOf</propertyURI>
                 <valueURI label="BCO-DMO">https://doi.org/10.17616/R37P4C</valueURI>
             </annotation>
         </metadata>
     </additionalMetadata>
</eml>
```

## RDF Graphs 

A graph consists of resources linked to other resources. There isn't a root or hierarchy structure, indicating that no single resource is more important than another. 

The parts of a triple (subject, predicate, and object) become nodes and links in a graph. Below are examples of how annotations can be converted to RDF triples in RDF/XML, so that the RDF information is now computer-readable. Be aware that there are several formats for serializing RDF, including RDF/XML, Turtle, N-Triples, and N3, that vary in the level of how human-readable they are.

### Graph from Example 3 (attribute annotation):


![RDF example A](images/RDF_example_a.png "Graph from Example 3 (attribute annotation):") 

```xml
<rdf:RDF
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    xmlns:owl="http://www.w3.org/2002/07/owl#">
    
    <rdf:Description rdf:about="att.4"> ### See note below
        <owl:ObjectProperty rdf:about="http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#containsMeasurementsOfType">
            <owl:Class rdf:about="http://purl.dataone.org/odo/ECSO_00001197" />
        </owl:ObjectProperty> 
    </rdf>
</rdf:RDF>

```
_Note: The subject described in the `rdf:Description` `about` attribute should actually be the globally unique URI for the attribute, rather than 'att.4'_


### Graph from Example 4 (using `annotations` element):

![RDF example B](images/RDF_example_b.png "Graph from Example 4 (using <annotations> element):")

```xml
<rdf:RDF
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    xmlns:owl="http://www.w3.org/2002/07/owl#">
    
    <rdf:Description rdf:about="adam.shepherd"> ### See note below 
        <owl:ObjectProperty rdf:about="http://www.w3.org/1999/02/22-rdf-syntax-ns#type">
            <owl:Class rdf:about="https://schema.org/Person" />
        </owl:ObjectProperty> 
        <owl:ObjectProperty rdf:about="https://schema.org/memberOf">
            <owl:Class rdf:about="https://doi.org/10.17616/R37P4C" />
        </owl:ObjectProperty> 
    </rdf>
    
</rdf:RDF>

```
_Note: The subject described in the `rdf:Description` `about` attribute should actually be the globally unique URI issued for 'adam.shepherd'._


### Check for Logical Consistency
With semantic annotation, you are adding precise definitions of concepts and relationships that can be traversed 
with computer logic. Annotations are not simply a set of loosely structured keywords!  This is a really powerful 
addition to EML, and so it comes with some risk. The main thing you should ensure is that your annotations are 
**logically consistent**.

**The simplest way to check your logic is to write out the RDF triple components and see if it makes sense as a sentence**. 

```
[subject (element-id)]  [predicate (propertyURI)]          [object (valueURI)]
[att.4]                 [contains measurements of type]    [plant cover percentage]

```
The graph examples above make 'true' statements that are logically consistent:

- att.4 contains measurements of the type plant cover percentage
- adam.shepherd is a person
- adam.shepherd, member of BCO-DMO

However, below is the kind of statement you would NOT want to make:
```
[adam.shepherd] [is a type of] [measurement]
```
If you suspect your RDF triple might look like this, you should go back and examine the way you structured the annotation.

Things to check:

1. Be sure you have used the right classes, properties, or vocabularies for your annotation components
  1. Become familiar with the vocabularies in your annotation, especially definitions and relationships 
  1. Check with your community for specific recommendations on annotations at different levels 
1. In `additionalMetadata`, don't combine `annotations` with more than one `describes` element. EML allows 1:many `describes` elements in one `additionalMetadata` section. So if you have 2 `describes` and 2 `annotations`, you will have 4 RDF statements. Make sure they are all true, and if not, break them up.



## Supplemental background information

External resources:
- LinkedDataTools tutorial: http://www.linkeddatatools.com/introducing-rdf
- RDF data model: https://www.w3.org/TR/WD-rdf-syntax-971002/
- W3C RDF primer: https://www.w3.org/TR/rdf11-primer/
- A tidyverse lover’s intro to RDF https://ropensci.github.io/rdflib/articles/rdf_intro.html
 
* Tim Berners-Lee's article on the semantic web: ```Berners-Lee, T., Hendler, J., & Lassila, O. (2001). The semantic web. Scientific american, 284(5), 34-43.```

## Glossary
**ontology**: A representation that formally names and definition of the categories, properties, and relations between the concepts, data, and entities that substantiate one, many, or all domains.

**Resource Description Framework (RDF)**: A family of World Wide Web Consortium (W3C) specifications that enable the encoding, exchange, and reuse of structured metadata. The RDF data model employs semantic triples composed of a subject, predicate, and object to share and integrate data across different applications and communities. 

**uniform resource identifier (URI)**: A string of characters that unambiguously identifies a particular resource. For semantic annotations, the components of semantic triples are ideally URIs that resolve and describe precise definitions and relationships to other terms.
