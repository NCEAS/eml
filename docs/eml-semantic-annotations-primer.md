# Semantic Annotations Primer

## Table of Contents
tbd, links to sections here.

## Introduction
The purpose of this primer is to provide an introduction to how semantic annotations are structured 
in EML documents. It is expected that you have some familiarity with the EML schema prior to reading this document. 
If you want to read more about the Resource Description Framework (RDF) data model, graphs or the semantic web, 
there is supplemental material at the bottom of this primer. 

A semantic annotation is the attachment of semantic metadata to a resource. It provides precise 
definitions of concepts and clarifies the relationships between concepts, in a computer-usable way. The process of 
creating semantic annotations may seem tedious, but the payoff is enhanced information retrieval and discovery. Semantic 
annotations will make it easier for others to find and reuse your data (and thus give you credit). 

For  example, if a dataset is annotated as being about "carbon dioxide flux" and another annotated with 
"CO2 flux" the information system should recognize that the datasets are about equivalent concepts. 
In another example, if you perform a search for datasets about "litter" (as in "plant litter"), the 
system will be able to disambiguate the term from the many meanings of "litter" (as in garbage, the grouping of 
animals born at the same time, etc.). Yet another example is if you search for datasets about "carbon flux", 
then datasets about "carbon dioxide flux" can also be returned because "carbon dioxide flux" is 
considered a type of "carbon flux". 

**Semantic statements must be logically consistent; they are not simply a set of loosely structured keywords.** 
The examples here should also make clear that inconsistent annotations could have dreadful consequences. 
So be careful, and if you have questions, bring them up in your community for feedback.

### Semantic triples

Semantic annotations follow the RDF data model and use semantic triples to make statements about a
resource. A semantic triple is composed of three parts:
a **subject**, an **object property or data property (predicate)**, and an **object**.

```
[subject] [predicate] [object]
```

These components are analogous to parts of a sentence; the **subject** 
and **object** can be thought of as nouns in the sentence and the **predicate** (object property or data property)
is akin to a verb or relationship that connects the **subject** and **object**. The semantic triple 
expresses the statement about the associated resource. 
After processing the EML into a semantic web format, such as RDF/XML, the semantic 
statement becomes interpretable by machines. 

#### URIs
Ideally, the components of the semantic triple should be globally 
unique and should consist of resolvable uniform resource identifiers (URIs) from controlled vocabularies so 
that users (or computers) can look up precise definitions and relationships to other terms. An example of a 
URI is "http://purl.obolibrary.org/obo/ENVO_01001357", which resolves to the term "desert" in the 
Environment Ontology (ENVO) when entered into the address bar of a web browser. Users can find the 
definition for "desert" and determine its relationship to other terms in the ontology.  

{ to do: need some help here! I think we should mention that not all URIs are URL, and what it might mean to be
computer-interpretable, not just web-resloveable. If this is long, it can be a sentence here that links to a section below }



## Semantic Annotations in EML 2.2.0
In **EML 2.2.0** there are 5 places where annotation elements can appear in an EML document: 

- **top-level resource**  -- an `annotation` element is a child of `dataset`, `literature`, `software`, `protocol` 
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

#### Annotations map to semantic triples

```
[subject] [predicate] [object]
```

|Triple component|EML location |Note  |Example  |
|--|--|--|--|
| `subject` |Parent element of the annotation  |  An element meant to be a subject must have an `id` attribute | `https://example.org/datasets/{dataset-identifier}#element-id` |
| `predicate` | `//annotation/propoertyURI`  | the "verb" in a statement | see below  |
| `object` | `//annotation/valueURI` | "object" of the "verb"  | see below |

**When are IDs required?**
Annotations at the dataset, entity or attribute level presume that the parent element is the *subject*; hence, if an element has
an annotation child, an id is required. Annotations at `eml/annotations` or `eml/additionalMetadata` will have 
subjects defined with a `references` attribute or `describes` element. So as for other internal EML reference an `id` is required.
The EML-2.2 parser checks for an `id` attribute if an annotation is present. 

**Labels**: It is recommended that the label field of the annotation is populated by the value from the preferred label field 
(`skos:prefLabel`) or label field (`rdfs:label`) from the referenced vocabulary.


### Resource level (Top-level) annotations: `dataset`, `literature`, `protocol`, and `software`

The top-level resources in EML are `dataset`, `literature`, `protocol`, and `software`. The resource module 
contains their common information, such as `title` and `creator`, and then each resource type has other content specific
to it. 
Note that the dataset module can import the other top-level resources at different levels. Further information about 
top-level resources may be found in the [eml-resource module] section.

[eml-resource module]: eml-modules-resources.md#the-eml-resource-module---base-information-for-all-resources 

A top-level resource annotation represents a precisely-defined semantic statement that applies to the entire resource, to associate precise measurement semantics with it. The `annotation` element is 
the last element of the resource group (i.e., it appears right after `coverage`). 

- The *subject* of the semantic statement is the parent element of the annotation. It must have an `id=" "` attribute. 
- Each annotation consists of a `propertyURI` element and `valueURI` element, which respectively define an *object property* or *data property* and the *object* (value) of the annotation. 
- `propertyURI` and `valueURI` elements  
  - must have a `label` attribute that is suitable for application interfaces. 
  - should have URIs that point to terms in controlled vocabularies providing precise definitions, relationships to other terms, and multiple labels for display. 
- It is recommended that the labels are populated by values from the preferred labels field (`skos:prefLabel`) or label field (`rdfs:label`) from the referenced vocabulary.

In the following dataset annotation (Example 1), the *subject* of the semantic statement is the `dataset` element containing 
the `id` attribute value `"dataset-01"`. The *object property* of the statement is "http://purl.org/dc/elements/1.1/subject". 
Finally, the *object* (value) in the semantic statement is "http://purl.obolibrary.org/obo/ENVO_01000177", which resolves 
to the "grassland biome" term in the ENVO ontology (http://www.obofoundry.org/ontology/envo.html). 

Taken together, the semantic statement can be read as "the dataset with the id 'dataset-01' is about the subject grassland biome"

* Example 1: Top-level resource annotation (dataset)

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
        <propertyURI label="Subject">http://purl.org/dc/elements/1.1/subject</propertyURI>
        <valueURI label="grassland biome">http://purl.obolibrary.org/obo/ENVO_01000177</valueURI>
    </annotation>
      ...    
</dataset>  
```

### Entity-level annotations: `dataTable`, `otherEntity`, `spatialRaster`, etc

The entity-level elements include the `dataTable`, `spatialRaster`, `spatialVector`, `storedProcedure`, `view`, and `otherEntity` elements, in addition to custom modules. Entities are often tables of data (`dataTable`). Data tables may describe ascii text files, relational database tables, spreadsheets, or other type of tabular data with a fixed logical structure. Related to data tables are views (`view`) and stored procedures (`storedProcedure`). Views and stored procedures are produced by a relational database management system or related system. Other types of data such as raster (`spatialRaster`), vector (`spatialVector`) or spatialReference image data are also data entities. An `otherEntity` element should be used to describe types of entities that are not described by any other entity type. The entity-level elements are nested under `dataset` elements. Further information about entities may be found in the [eml-entity module] section.

[eml-entity module]: eml-modules-data-structure.md#the-eml-entity-module---entity-level-information-within-datasets

An entity-level annotation represents a precisely-defined semantic statement that applies to an entity. 
This semantic statement is used to associate precise measurement semantics with the entity. An `annotation` element is 
embedded in a containing entity-level element.

- The *subject* of the semantic statement is the parent entity element (e.g., `dataTable`). It must have an `id=" "`. 
- Each annotation consists of a `propertyURI` element and `valueURI` element, which respectively define an *object property* or *data property* and the *object* (value) of the annotation. 
- `propertyURI` and `valueURI` elements  
  - must have a `label` attribute that is suitable for application interfaces. 
  - should have URIs that point to terms in controlled vocabularies providing precise definitions, relationships to other terms, and multiple labels for display. 
- It is recommended that the labels are populated by values from the preferred labels field (`skos:prefLabel`) or label field (`rdfs:label`) from the referenced vocabulary.

In the following entity-level annotation (Example 2), the subject of the semantic statement refers to the `otherEntity` 
element's `id` attribute value, "urn:uuid:9f0eb128-aca8-4053-9dda-8e7b2c43a81b". The object property of the 
statement is "http://purl.org/dc/elements/1.1/subject". Finally, the object (value) in the semantic statement 
is "http://purl.obolibrary.org/obo/NCBITaxon_40674", which resolves to the "Mammalia" term in the NCBITaxon 
ontology (http://www.ontobee.org/ontology/NCBITaxon). 

Taken together, the semantic statement indicates that "the entity with the id 'urn:uuid:9f0eb128-aca8-4053-9dda-8e7b2c43a81b' is about the subject Mammalia"

 * Example 2: entity-level annotation

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
        <propertyURI label="Subject">http://purl.org/dc/elements/1.1/subject</propertyURI>
        <valueURI label="Mammalia">http://purl.obolibrary.org/obo/NCBITaxon_40674</valueURI>  
    <annotation>
</otherEntity>
```

### Attribute-level annotations:  `attribute` 

An attribute annotation is a precisely-defined semantic statement that applies to a data entity attribute, such as a column 
name in a spreadsheet or table. This semantic statement is used to associate precise measurement semantics with the attribute, 
such as the property being measured, the thing being measured, and the measurement standard for interpreting values for the attribute. `attribute` elements may be nested in entity-level elements, including the `dataTable`, `spatialRaster`, `spatialVector`, `storedProcedure`, `view`, or `otherEntity` elements, in addition to custom modules. Refer to the [eml-attribute module] section for additional information about attributes.

[eml-attribute module]: eml-modules-data-structure.md#the-eml-attribute-module---attribute-level-information-within-dataset-entities

A attribute annotation is an `annotation` element contained by an `attribute` element. 

- The *subject* of the semantic statement is the parent `attribute` element. It must have an `id=" "`. 
- Each annotation consists of a `propertyURI` element and `valueURI` element, which respectively define an *object property* or *data property* and the *object* (value) of the annotation. 
- `propertyURI` and `valueURI` elements  
  - must have a `label` attribute that is suitable for application interfaces. 
  - should have URIs that point to terms in controlled vocabularies providing precise definitions, relationships to other terms, and multiple labels for display. 
- It is recommended that the labels are populated by values from the preferred labels field (`skos:prefLabel`) or label field (`rdfs:label`) from the referenced vocabulary.


In the following attribute annotation (Example 3), the subject of the semantic statement is the `attribute` element 
containing the `id` attribute value "att.4". The object property of the statement is 
"http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#containsMeasurementsOfType". Note that the URI for the 
object property resolves to a specific term in the OBOE ontology (https://github.com/NCEAS/oboe). Finally, the object (value) 
in the semantic statement is "http://purl.dataone.org/odo/ECSO_00001197", which resolves to the "Plant Cover Percentage" 
term in the ECSO Ontology (https://github.com/DataONEorg/sem-prov-ontologies/tree/master/observation). 

Taken together, the semantic statement indicates that "the attribute with the id 'att.4' contains measurements of type plant cover percentage".

* Example 3: attribute annotation

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

### `eml/annotations` element
The `annotations` element is nested under the `eml` root element and contains a list of annotations defining precise 
semantic statements for parts of a resource. An annotation represents a precisely-defined semantic statement that applies to the resource.

The `annotations` element contains a set of `annotation` elements. Each `annotation` element has a `references` attribute that 
points to the `id` attribute of the element being annotated. Hence, what is listed in the `references` attribute is the id of 
the subject of the semantic annotation. Any of the EML modules may be referenced by the `references` 
attribute and because ids are unique within an EML document, this is a single subject. 

- The *subject* of the semantic statement is implictly the element containing the referenced `id`. 
- Each annotation consists of a `propertyURI` element and `valueURI` element, which respectively define an *object property* or *data property* and the *object* (value) of the annotation. 
- `propertyURI` and `valueURI` elements  
  - must have a `label` attribute that is suitable for application interfaces. 
  - should have URIs that point to terms in controlled vocabularies providing precise definitions, relationships to other terms, and multiple labels for display. 
- It is recommended that the labels are populated by values from the preferred labels field (`skos:prefLabel`) or label field (`rdfs:label`) from the referenced vocabulary.

Example 4 has 3 different annotations inder `annotations`. For the first annotation, 
the subject of the semantic triple is the `dataTable` element with the `id` attribute "CDF-biodiv-table". Notice 
that the annotation has a `references` attribute that points to the subject id. The object property of the triple 
is "http://purl.org/dc/elements/1.1/subject". Finally, the value (object) in the semantic triple 
is "http://purl.obolibrary.org/obo/ENVO_01000177", which resolves to the "grassland biome" term in the 
ENVO ontology (http://www.obofoundry.org/ontology/envo.html). Taken together, the first semantic statement could be 
read as "the dataTable with the id 'CDR-biodiv-table' is about the subject grassland biome".

The second annotation has as its subject the `creator` element that has the id "adam.shepherd", 
the object property "http://www.w3.org/1999/02/22-rdf-syntax-ns#type" and the value (object) "https://schema.org/Person". 
This statement can be interpreted as "'adam.shepherd', the creator (of the dataset), is a person".

The third annotation also has as its subject the `creator` element that has the id "adam.shepherd". 
The object property is "https://schema.org/memberOf" and the object (value) is "https://doi.org/10.17616/R37P4C". 
This statement can be read as "'adam.shepherd', the creator (of the dataset), is a member of BCO-DMO".

* Example 4: `annotations` element annotation

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
        </dataTable  
    </dataset>
    ...
    <annotations>
        <annotation references="CDR-biodiv-table">
            <propertyURI label="Subject">http://purl.org/dc/elements/1.1/subject</propertyURI>
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

### `eml/additionalMetadata` element

Semantic annotations may also be added to a `additionalMetadata` element that is nested under the `eml` root element. 
This element is a container for any supplemental non-EML metadata that pertains to the resource, and can reference any
element in the EML record that has an id. Additional information may be found in the [eml-semantics module] section.

An annotation in `additionalMetadata` uses the `describes` element as the subject, so can reference any EML
element(s) with an id. 


[eml-semantics module]: eml-modules-utility.md#the-eml-semantics-module---semantic-annotations-for-formalized-statements-about-eml-components

By definition, an `additionalMetadata` element contains a `describes` element and a `metadata` element, and 
annotations make use of these. If an `additionalMetadata` section holds a semantic annotation it must have a `describes` element 
(to hold the subject) with a `metadata` element containing at least one `annotation` element. 

- The *subject* of the semantic statement is implictly the element named in the `additionalMetadata` `describes` element, by its id. 
- The annotation itself is within the `additionalMetadata` `metadata` section
- Each annotation consists of a `propertyURI` element and `valueURI` element, which respectively define an *object property* or *data property* and the *object* (value) of the annotation. 
- `propertyURI` and `valueURI` elements  
  - must have a `label` attribute that is suitable for application interfaces. 
  - should have URIs that point to terms in controlled vocabularies providing precise definitions, relationships to other terms, and multiple labels for display. 
- It is recommended that the labels are populated by values from the preferred labels field (`skos:prefLabel`) or label field (`rdfs:label`) from the referenced vocabulary.
- Multiple `annotation` elements may be embedded in the same `metadata` element to assert multiple semantic statements about the same subject.
- To annotate different subjects it's best to use additional `additionalMetadata` sections, each with a single subject

The `additionalMetadata` annotation (Example 5) describes a semantic statement where the subject is the `creator` element with the `id` attribute "adam.shepherd". 
The object property of the statement is "https://schema.org/memberOf". 
Finally, the object (value) in the semantic statement is the DOI for BCO-DMO, "https://doi.org/10.17616/R37P4C". 
Taken together, the semantic statement could be read as "'adam.shepherd' (the creator of the dataset) is a member of BCO-DMO".

* Example 5: `additionalMetadata` element annotation

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
        </dataTable  
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

### RDF Graphs 
Below are examples of how annotations can be converted to RDF triples. The parts of a triple (subject, predicate, and object) 
bbecome nodes and links in a graph.

![RDF example A](images/RDF_example_a.png "Graph from Example 3 (attribute annotation):")

```
the RDF/XML ? here ?? Steven? to do

```
![RDF example B](images/RDF_example_b.png "Graph from Example 4 (using <annotations> element):")

```
the RDF/XML ? here ?? Steven? to do
```

### Logical Consistency
Sounds easy, right? What could possibly go wrong?
With semantic annotation, you are adding precise definitions of concepts and relationships that can be traversed 
with computer logic. Annotations are not simply a set of loosely structured keywords!  This is a really powerful 
addition to EML, and so comes with some risk. The main thing you should ensure is that your annotations are 
**logically consistent**.

**The simplest way to check your logic is to write out the RDF triple components and see if it makes sense as a sentence**. 

```
[subject (element-id)]  [predicate (propertyURI)]     [object (valueURI)]
[att.4]                 [contains measurements of]    [plant cover percentage]

```
The graph examples above make 'true' statements; they are logically consistent:

- att.4 contains measurements of plant cover percentage
- adam.shepherd is a person
- adam.shepherd, member of BCO-DMO

However, below is the kind of statement you would NOT want to make:
```
[adam.shepard] [is a type of] [measurement]
```
If you suspect your RDF triple might look like this, you should go back and examine the way you structured the annotation.

Things to check

1. Be sure you have used the right classes, properties or vocabularies for your annotation components.
  1. Become familiar with the vocabularies in your annotation, especially definitions and relationships 
  1. Check with your communitiy for specific recommendations on annotations at different levels 
1. in additionalMetadata, don't combine `<annotations>` with more than one `<describes>` element. EML allows 1:many `<describes>` elements in one additionalMetadata section. So if you have 2 `<describes>` and 2 `<annotations>`, you will have 4 RDF statements. make sure they are all true, and if not, break them up



## Supplemental background information

External resources:
- http://www.linkeddatatools.com/introducing-rdf
- RDF data model: https://www.w3.org/TR/WD-rdf-syntax-971002/
- W3C RDF primer: https://dvcs.w3.org/hg/rdf/raw-file/default/rdf-primer/
 
* Tim Berners-Lee's article on the semantic web: ```Berners-Lee, T., Hendler, J., & Lassila, O. (2001). The semantic web. Scientific american, 284(5), 34-43.```

## Glossary
