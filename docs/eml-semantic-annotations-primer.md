# Semantic Annotation Primer

## Introduction

EML 2.2.0 now provides ways to embed HTTP URI's within several EML elements, which are semantic annotations of 
those elements. 
In general, a semantic annotation is the attachment of additional semantic metadata to a resource, and in our  
 context it is attached to an EML element. By referencing external [*knowledge graphs*] (#TO DO glossary link) 
 (also known as *controlled vocabularies* 
or *ontologies*), annotations provide rigorous, expressive and consistent interpretation of the 
metadata contents.  The annotation provides a computer-usable 
[ pointer](#glossary-pointer) (or [HTTP uniform resource identifier; URI] 
 (#TO DO glossary links)  that  [resolves (and dereferences)]   to a useful description, definition, 
or relationships for that annotated resource. 

### Benefits of annotation
The process of creating semantic annotations for datasets may seem tedious, but the payoff is vastly enhanced data 
discovery and interpretation. Semantic annotations will make it easier for others to find and reuse data 
(and thus give proper credit). 

1. **Equivalent concepts:** one dataset uses the phrase "carbon dioxide flux" and another dataset "CO2 flux". 
If they are annotated, the information system is able to recognize that these datasets are about equivalent concepts, 
because the datasets are annotated with the same same HTTP URI. 
1. **Disambiguation:** if you perform a search for datasets about "litter" (as in "plant litter"), if datasets have been annotated the 
system will be able to understand the different meanings of "litter" (as in garbage, the grouping of 
animals born at the same time to the same mother, etc.). Each type of "litter" would be associated with a
different identifier (HTTP URI). 
1. **Hierarchical searches:** if you search for 
datasets about "carbon flux", then datasets about "carbon dioxide flux" can also be returned because "carbon 
dioxide flux" is a type of "carbon flux".  This is possible because the HTTP URI came from a knowledge graph 
in which the identifier  for "carbon dioxide flux" was a subclass of "carbon flux".



### Organization of this document
The purpose of this primer is to provide an introduction to how semantic annotations are structured 
in EML documents. It is expected that you already have some familiarity with the EML schema,. The focus of this
document then, is explanation and examples of annotations in EML. An addendum contains additional information on 
specific related topics, with links from the main text where appropriate.

1. **[Glossary:]**(#glossary) Glossary of terms 
1. **[Semantic triples:]**(#semantic-triples)  details on their structure, and how structure is leveraged by annotations with examples of their power
1. **[URIs:]**(#uris) defined, and as components of semantic triples
1. **[RDF model:]**(#rdf) the W3C's RDF model with example graphs based on EML annotations
1. **[Logical consistency:]**(#logical-consistency) Common mistakes and how to check for them
1. **[Supplemental background information:]**(#external-resources) The EML annotation approach here is compatible with recommended by the World Wide Web Consortium (W3C) for construction of the Semantic Web



### Take-home messages (could add 1 more sentence to each, but keep it SHORT)
- **Semantic statements must be logically consistent, as they are not simply a set of loosely structured keywords.** 
- **EML 2.2.0 has five places or methods to add annotations.**
- **The best place for advice and feedback on EML annotations is your data management community**


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
an annotation child, an id is required (i.e. the subject element must have an `id` attribute value). Annotations at `eml/annotations` or `eml/additionalMetadata` will have subjects defined with a `references` attribute or `describes` element. For other internal EML references, an `id` is required. The EML-2.2 parser checks for an `id` attribute if an annotation is present. As a reminder, the `id` must be unique within an EML document.

**Labels**: It is recommended that the label field of the annotation is populated by the value from the label field (`rdfs:label`: that should always be present) or preferred label field (`skos:prefLabel`: that sometimes are provided) from the referenced vocabulary.  Note that this assumes the referenced vocabulary is stored as an RDF document, which is best practice.


### Top-level resource, entity-level, and attribute annotations 

Annotations for top-level resources, entities, and attributes follow the same general pattern.

- The *subject* of the semantic statement is the parent element of the annotation. It must have an `id=" "` attribute. 


#### Example 1: Top-level resource annotation (dataset)

In the following dataset annotation, the *subject* of the semantic statement is the `dataset` element containing 
the `id` attribute value `"dataset-01"`. The predicate-- "http://purl.obolibrary.org/obo/IAO_0000136", is an *object property* explicating the relationship of the subject to the object, using a term from the Information Artifact Ontology, IAO (http://www.obofoundry.org/ontology/iao.html). 
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
containing the `id` attribute value "att.4". The predicate is of the statement is an object property
"http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#containsMeasurementsOfType". Note that the URI for the 
object property resolves to a specific term in the OBOE ontology (https://github.com/NCEAS/oboe). Finally, the object (value)in the semantic statement is "http://purl.dataone.org/odo/ECSO_00001197", which resolves to the "Plant Cover Percentage" term in the ECSO Ontology (https://github.com/DataONEorg/sem-prov-ontologies/tree/master/observation). 

Taken together, the semantic statement indicates that "the attribute with the id 'att.4' contains measurements of type plant cover percentage".  Of course, this statement needs to be interpreted in the context of the entity within which 'att.4' occurs.


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

## Appendix
### Semantic triples <a name="semantic-triples"></a>

Semantic annotations enable the creation of what are called *triples*, that are 3-part statements conforming to the W3C recommended *RDF data model* (learn more: <https://www.w3.org/TR/rdf11-primer/>). 

A *triple* is composed of three parts: a **subject**, a **predicate (object property or datatype property)**, and an **object**.

```
[subject] [predicate] [object]
```

These components are analogous to parts of a sentence: the **subject** and **object** can be thought of as nouns in the sentence and the **predicate** (object property or datatype property) is akin to a verb or relationship that connects the **subject** and **object**. The semantic triple expresses a statement about the associated resource, that is generally the **subject**. 

There are (perhaps unfortunately) several other ways that the components of an RDF statement are sometimes described.  One popular "synonymy" for **subject-predicate-object** is **resource-property-value**, i.e. the subject is referred to as the **resource**, the predicate a **property**, and the object a **value**.  This can be confusing, since the usual definition of a *resource* is any identifiable 'thing' or object, especially one assigned a URI; and by this definition, *resources* can and often do occur in all three components of a triple.  But thinking of a triple as a *resource-property-value* does provide an indication of the directionality of the semantics of an RDF statement.  This latter terminology is also somewhat similar to how analogous components are named in JSON-LD.  Note that JSON-LD is closely compatible with RDF, and one format can often be readily translated to the other (although there are some exceptions).

Semantic annotations added to an EML document can be extracted and processed into a semantic web format, such as RDF/XML. These"semantic" statements, i.e. RDF triples, are interpretable by any machines that can process the W3C standard of RDF. Those RDF statements contribute to the Semantic Web.


### URIs <a name="uris"></a>
Ideally, the components of the semantic triple should be globally unique and persistent (unchanging), and consist of resolvable/dereferenceable HTTP uniform resource identifiers (URIs; or more formally, IRI's). The *subjects* of most EML semantic annotations will likely be HTTP URI's that identify the dataset resource itself, or specific attributes or other features within a dataset.  The *objects* of EML semantic annotations, as well as the *predicates* that relate the subject to the object, will most typically be HTTP URI references to terms in controlled vocabularies (also called "knowledge graphs", or "ontologies") accessible through the Web, so that users (or computers) can dereference the URI's and look up precise definitions and relationships of these resources to other terms [^footnote1]. 

An example of a URI is "http://purl.obolibrary.org/obo/ENVO_00000097", when entered into the address bar of a web browser, resolves to the term with a label of "desert area" in the Environment Ontology (EnvO). Users can learn what this URI indicates and explore how the term is related to other terms in the ontology simply by dereferencing its URI in a web browser.  All those other aspects you see on the Web page describing "http://purl.obolibrary.org/obo/ENVO_00000097" are from other RDF statements (triples) related to "ENVO_00000097", and that have been rendered into HTML. From here, you might decide, e.g. that "http://purl.obolibrary.org/obo/ENV0_00000172" ("sandy desert") is a better annotation for your object.

An RDF triple can be constructed as follows, with subject URI, predicate URI, and object URI:

   <<https://doi.org/10.6073/pasta/06db7b16fe62bcce4c43fd9ddbe43575>>
   
   <<http://purl.obolibrary.org/obo/RO_0001025>>
   
   <<http://purl.obolibrary.org/obo/ENVO_00000097>>
   
   .
   
... indicating that the referenced *dataset* (subject/resource) was *"located in"* (predicate/property) a *"desert area"* (object/value). Note that a blank-space must separate the subject, from the predicate, from the object, and that a "period" completes the triple. This is  a valid RDF triple, expressed in N-Triple syntax.  RDF is most often serialized into XML, however, as Web browsers and many applications are good at parsing XML. 

While our focus here is on the semantic annotation of EML documents, it is easy to see how the RDF statements can be used to describe and inter-relate any resources that have unique, persistent HTTP URIs!

Note that the above *RDF triple* consists of three HTTP URIs. While the exact distinction among what is a URI, a URN, and a URL can be debated, for our purposes, these HTTP URI's are can be considered both the *name* and *web location* of a resource. Content negotiation between a Web server and a client (which might be a browser, or a Python or R script) can enable an HTTP URI to dereference in ways optimized for the requesting client -- e.g. in one case, presenting a human-readable view of metadata for a dataset, and in another, activating a download of that dataset for import into a script.

The software needed to extract semantic annotations out of EML, and convert these into valid RDF triples, is under development at NCEAS and EDI, and through the rOpenSci project. The RDF triple described above, however, hopefully gives an idea of how such triples, constructed of dereferenceable HTTP URI's, can be very useful. 

The sections below describe the exact syntax for embedding annotations in EML 2.2.0 documents. 



### RDF Graphs <a name="rdf"></a>

A graph consists of resources linked to other resources. Thus the simplest graph structure is when you specify how one resource (node) is linked to another resource (node).

The parts of a triple (subject, predicate, and object) become nodes and links in a graph. Below are examples of how annotations can be converted to RDF triples in RDF/XML, so that the RDF information is now computer-readable. Be aware that there are several formats for serializing RDF, including RDF/XML, Turtle, N-Triples, and N3, that vary in the level of how human-readable they are. 

This process of converting a semantic annotation in EML into RDF, is done by parsing applications under development at EDI, NCEAS, rOpenSci, and other data repositories. Careful examination of the examples below also show references to "owl:Class", "owl:ObjectProperty", and other statements that may not be familiar. These are fundamental *entities* or building blocks in W3C-recommended Semantic Web languages, and are determined by the relationships that the triple component identifiers (HTTP URI's) have within their native knowledge graph/ontology.

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
_Note: The subject described in the `rdf:Description` `about` attribute should actually be a globally unique HTTP URI for the attribute, rather than 'att.4'. The details of how this HTTP URI GUID is constructed are being developed by EDI, NCEAS, and others._


#### Graph from Example 4 (using `annotations` element):

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
_Note: The subject described in the `rdf:Description` `about` attribute should actually be the globally unique URI issued for 'adam.shepherd'. The details of how this HTTP URI GUID is constructed are being developed by EDI, NCEAS, and others._


### Check for Logical Consistency <a name="logical-consistency"></a>
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






### Glossary -  link terms in the text above to here. <a name="glossary"></a>
**ontology**: <a name="glossary-TODO"></a> A knowledge *graph* representation of a set of terms, including their names, and descriptions of 
the categories, properties, and relationships among those terms.

**Resource Description Framework (RDF)**:<a name="glossary-TODO"></a> A family of World Wide Web Consortium (W3C) recommendations that enable the encoding, exchange, and reuse of structured metadata. The RDF data model employs semantic triples composed of a subject, predicate, and object to share and integrate data across different applications and communities through the Web. 

**uniform resource identifier (URI)**: <a name="glossary-TODO"></a>A string of characters that unambiguously identifies a particular resource. For semantic annotations, the components of semantic triples are ideally HTTP URIs that resolve and describe precise definitions and relationships to other terms, using Web technology.

**Pointer** <a name="glossary-pointer"></a> definition here

### External resources <a name="external-resources"></a>
  
Following are some supplemental background information
- LinkedDataTools tutorial: http://www.linkeddatatools.com/introducing-rdf
- RDF data model: https://www.w3.org/TR/WD-rdf-syntax-971002/
- W3C RDF primer: https://www.w3.org/TR/rdf11-primer/
- A tidyverse lover’s intro to RDF https://ropensci.github.io/rdflib/articles/rdf_intro.html
 
* Tim Berners-Lee's article on the semantic web: ```Berners-Lee, T., Hendler, J., & Lassila, O. (2001). The semantic web. Scientific american, 284(5), 34-43.```


## Footnotes
might not be needed.

[^footnote1]: this might be a footnote about URIs


## parking area

So be careful, and if you have questions, bring them up in your community for feedback.
The examples in this primer should also make clear that inconsistent annotations could create confusion. 
