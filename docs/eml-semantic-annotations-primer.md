# Semantic Annotations Primer

## Introduction
The purpose of this primer is to provide a gentle introduction to how semantic annotations are structured in EML documents. It is expected that you have some familiarity with EML prior to reading this document. If you aren't knowledgeable about the Resource Description Framework (RDF) data model or semantic web, you can refer to the supplemental readings listed after this introduction for some background information. 

A semantic annotation is the attachment of semantic metadata to a resource. Semantic metadata provide precise definitions of concepts and clarify the relationships between concepts. Although the process of creating semantic annotations may seem tedious, the payoff is enhanced information retrieval and discovery. Semantic annotations will make it easier for others to find and reuse your data (and thus give you credit). For example, if a dataset is annotated as being about "carbon dioxide flux" and another annotated with "CO2 flux" the information system should recognize that the datasets are about equivalent concepts. In another example, if you perform a search for datasets about "litter" (as in "plant litter"), the system will disambiguate the term from the many meanings of "litter" (as in garbage, the grouping of animals born at the same time, etc.). Yet another example is if you search for datasets about "carbon flux", then datasets about "carbon dioxide flux" will also be returned because "carbon dioxide flux" is considered a type of "carbon flux".

Semantic annotations follow the RDF data model and use semantic triples. A semantic triple is composed of a **subject**, **object property or data property (predicate)**, and **object**. In general, the subject and object can be thought of as nouns in a sentence and the object property or data property is akin to a verb or relationship that connects the subject and object. The semantic triple expresses a statement about the associated resource. After processing the EML into a semantic web format, such as RDF/XML, the semantic statement becomes interpretable by machines. Ideally, the components of the semantic triple should be globally unique and should consist of resolvable uniform resource identifiers (URIs) from controlled vocabularies so that users can look up precise definitions and relationships of the terms to other terms. An example of a URI is "http://purl.obolibrary.org/obo/ENVO_01001357", which resolves to the term "desert" in the Environment Ontology (ENVO) when entered into the address bar of a web browser. Users can find the definition for "desert" and determine its relationship to other terms in the ontology.

### Supplemental background information
* RDF data model: https://www.w3.org/TR/WD-rdf-syntax-971002/
* Tim Berners-Lee's article on the semantic web: ```Berners-Lee, T., Hendler, J., & Lassila, O. (2001). The semantic web. Scientific american, 284(5), 34-43.```

## Semantic Annotations in EML 2.2.0
In **EML 2.2.0** there are 5 areas where semantic annotations can be made in an EML document: **top-level resource** (e.g. dataset resources, literature resources, software resources, protocol resources), **entity-level**, **attribute**, **annotations** element and **additionalMetadata** element annotations.

### Top-level resource annotations

The top-level resources in EML are dataset resources, literature resources, protocol resources, and software resources. The resource types share some common information, such as title and creator, but also contain information that is specific to a particular resource type. Note that the dataset module can import the other top-level resources at different levels. Further information about top-level resources may be found in the [eml-resource module] section.

[eml-resource module]: eml-modules-resources.md#the-eml-resource-module---base-information-for-all-resources 

A top-level resource annotation represents a precisely-defined semantic statement that applies to a top-level resource. This semantic statement is used to associate precise measurement semantics with the resource. An `annotation` element is embedded in a containing resource element. The *subject* of the semantic statement is the resource element that contains the annotation. *It is recommended to give the subject element an* `id` *attribute and refer to the subject by the value of the* `id`. Each annotation consists of a `propertyURI` element and `valueURI` element, which respectively define an *object property* or *data property* and the *object* (value) of the annotation. *The URIs should ideally point to terms in controlled vocabularies* that provide precise definitions, relationships to other terms, and multiple labels for displaying the statements. The `propertyURI` and `valueURI` elements can each have a `label` attribute that displays a more readable label suitable for application interfaces. *It is recommended that the labels are populated by values from the preferred labels field (skos:prefLabel) or label field (rdfs:label) from a controlled vocabulary*.

In the following dataset annotation (Example 1), the subject of the semantic statement is the `dataset` element containing the `id` attribute value "dataset-01". The object property of the statement is "http://purl.org/dc/elements/1.1/subject". Finally, the object (value) in the semantic statement is "http://purl.obolibrary.org/obo/ENVO_01000177", which resolves to the "grassland biome" term in the ENVO ontology (http://www.obofoundry.org/ontology/envo.html). Taken together, the semantic statement could be read as "the dataset with the id 'dataset-01' is about the subject grassland biome".

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
    <annotation>
        <propertyURI label="Subject">http://purl.org/dc/elements/1.1/subject</propertyURI>
        <valueURI label="grassland biome">http://purl.obolibrary.org/obo/ENVO_01000177</valueURI>
    </annotation>
</dataset>  
```

### Entity-level annotations

The entity-level elements include the `dataTable`, `spatialRaster`, `spatialVector`, `storedProcedure`, `view`, and `otherEntity` elements, in addition to custom modules. Entities are often tables of data (`dataTable`). Data tables may describe ascii text files, relational database tables, spreadsheets, or other type of tabular data with a fixed logical structure. Related to data tables are views (`view`) and stored procedures (`storedProcedure`). Views and stored procedures are produced by a relational database management system or related system. Other types of data such as raster (`spatialRaster`), vector (`spatialVector`) or spatialReference image data are also data entities. An `otherEntity` element should be used to describe types of entities that are not described by any other entity type. The entity-level elements are nested under `dataset` elements. Further information about entities may be found in the [eml-entity module] section.

[eml-entity module]: eml-modules-data-structure.md#the-eml-entity-module---entity-level-information-within-datasets

An entity-level annotation represents a precisely-defined semantic statement that applies to an entity. This semantic statement is used to associate precise measurement semantics with the entity. An `annotation` element is embedded in a containing entity-level element and the *subject* of the semantic statement is the element that contains the annotation. *It is recommended to give the subject element an* `id` *attribute and refer to the subject by the value of the* `id`. Each annotation consists of a `propertyURI` element and `valueURI` element, which respectively define an *object property* or *data property* and an *object* (value) that apply to the entity. *The URIs should ideally point to terms in controlled vocabularies* that provide precise definitions, relationships to other terms, and multiple labels for displaying the statements. The associated `label` attribute for each URI can be used to display the property and value in a more readable format suitable for application interfaces. *It is recommended that the labels are populated by values from the preferred labels field (skos:prefLabel) or label field (rdfs:label) from a controlled vocabulary*.

In the following entity-level annotation (Example 2), the subject of the semantic statement refers to the `otherEntity` element's `id` attribute value, "urn:uuid:9f0eb128-aca8-4053-9dda-8e7b2c43a81b". The object property of the statement is "http://purl.org/dc/elements/1.1/subject". Finally, the object (value) in the semantic statement is "http://purl.obolibrary.org/obo/NCBITaxon_40674", which resolves to the "Mammalia" term in the NCBITaxon ontology (http://www.ontobee.org/ontology/NCBITaxon). Taken together, the semantic statement indicates that "the entity with the id 'urn:uuid:9f0eb128-aca8-4053-9dda-8e7b2c43a81b' is about the subject Mammalia".

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

### Attribute annotations

An attribute annotation represents a precisely-defined semantic statement that applies to an attribute, such as a column name in a spreadsheet. This semantic statement is used to associate precise measurement semantics with the attribute, such as the property being measured, the entity being measured, and the measurement standard for interpreting values for the attribute. `attribute` elements may be nested in entity-level elements, including the `dataTable`, `spatialRaster`, `spatialVector`, `storedProcedure`, `view`, or `otherEntity` elements, in addition to custom modules. Refer to the [eml-attribute module] section for additional information about attributes.

[eml-attribute module]: eml-modules-data-structure.md#the-eml-attribute-module---attribute-level-information-within-dataset-entities

A typical attribute annotation involves an `annotation` element that is embedded in a containing `attribute` element. The *subject* of the semantic statement is the `attribute` element that contains the annotation. *It is recommended to give the subject element an* `id` *attribute and refer to the subject by the value of the* `id`. Each annotation consists of a `propertyURI` element and `valueURI` element that respectively define the *object property* or *data property* and *object* (value) of the semantic statement. *Each URI should ideally resolve to a controlled vocabulary that provides a precise definition, relationships to other terms, and multiple labels for displaying the statement*. Note that when annotating measurement attributes contained in tabular formats the suggested "default" object property is "http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#containsMeasurementsOfType" ("contains measurements of type" from the OBOE ontology). The associated `label` attribute for each URI can be used to display the property and value in a more readable format suitable for application interfaces. *It is recommended that the labels are populated by values from the preferred labels field (skos:prefLabel) or label field (rdfs:label) from a controlled vocabulary*.

In the following attribute annotation (Example 3), the subject of the semantic statement is the `attribute` element containing the `id` attribute value "att.4". The object property of the statement is "http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#containsMeasurementsOfType". Note that the URI for the object property resolves to a specific term in the OBOE ontology (https://github.com/NCEAS/oboe). Finally, the object (value) in the semantic statement is "http://purl.dataone.org/odo/ECSO_00001197", which resolves to the "Plant Cover Percentage" term in the ECSO Ontology (https://github.com/DataONEorg/sem-prov-ontologies/tree/master/observation). Taken together, the semantic statement indicates that "the attribute with the id 'att.4' contains measurements of type plant cover percentage".

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

### Annotations in the `annotations` element
The `annotations` element is nested under the `eml` root element and contains a list of annotations defining precise semantic statements for parts of a resource. An annotation represents a precisely-defined semantic statement that applies to the resource.

The `annotations` element contains a set of `annotation` elements. Each `annotation` element has a `references` attribute that points to the `id` attribute of the element being annotated. Any of the EML modules may be referenced by the `references` attribute and the id must point to a unique id within the EML document. In the semantic statement, the *subject* is implicitly the element containing the id that is referenced. In other words, what is listed in the `references` attribute is the id of the subject of the semantic annotation. Each annotation also consists of a `propertyURI` element and `valueURI` element that respectively define the *object property* or *data property* and *object* (value) that apply to the resource. *A URI should ideally resolve to a controlled vocabulary that provides a precise definition, relationships to other terms, and multiple labels for displaying the statement*. Each `propertyURI` and `valueURI` element can have a `label` attribute that displays a label associated with each URI. Labels are intended to provide a more readable format for users and may be displayed in application interfaces. *It is recommended that labels are populated with values from the preferred labels field (skos:prefLabel) or label field (rdfs:label) from a controlled vocabulary*. 

The following `annotations` element example (Example 4) has 3 different annotations. For the first annotation, the subject of the semantic triple is the `dataTable` element with the `id` attribute "CDF-biodiv-table". Notice that the annotation has a `references` attribute that points to the subject id. The object property of the triple is "http://purl.org/dc/elements/1.1/subject". Finally, the value (object) in the semantic triple is "http://purl.obolibrary.org/obo/ENVO_01000177", which resolves to the "grassland biome" term in the ENVO ontology (http://www.obofoundry.org/ontology/envo.html). Taken together, the first semantic statement could be read as "the dataTable with the id 'CDR-biodiv-table' is about the subject grassland biome".

The second annotation has as its subject the `creator` element that has the id "adam.shepherd", the object property "http://www.w3.org/1999/02/22-rdf-syntax-ns#type" and the value (object) "https://schema.org/Person". This statement can be interpreted as "'adam.shepherd', the creator (of the dataset), is a person".

The third annotation also has as its subject the `creator` element that has the id "adam.shepherd". The object property is "https://schema.org/memberOf" and the object (value) is "https://doi.org/10.17616/R37P4C". This statement can be read as "'adam.shepherd', the creator (of the dataset), is a member of BCO-DMO".

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

### Annotations in the `additionalMetadata` element

Semantic annotations may also be inserted in the `additionalMetadata` element that is nested under the `eml` root element. This element is a container for supplemental non-EML metadata that pertains to the subject of interest. Only metadata that is XML-based can be inserted here. Additional information may be found in the [eml-semantics module] section.

[eml-semantics module]: eml-modules-utility.md#the-eml-semantics-module---semantic-annotations-for-formalized-statements-about-eml-components

Each `additionalMetadata` element semantic annotation has `describes` elements and `metadata` elements for annotations, with each`metadata` element containing at least one `annotation` element. The content of the `describes` element is the id of the *subject* of the semantic annotation. The subject is the element referred to by the id. It is the `metadata` element that holds the additional metadata to be included in the document. This `metadata` field describes the element referenced in the `describes` element preceding it. Contained within the `annotation` element are `propertyURI` and `valueURI` elements. The `propertyURI` is the *object property* or *data property* and the `valueURI` is the *object* of the annotation. *The URIs should ideally point to terms in controlled vocabularies*. Each `propertyURI` and `valueURI` element can have a `label` attribute that displays a label associated with each URI. Labels are intended to provide a more readable format for users and may be displayed in application interfaces. *It is recommended that labels are populated with values from the preferred labels field (skos:prefLabel) or label field (rdfs:label) from a controlled vocabulary.* 

Multiple `annotation` elements may be embedded in the same `metadata` element to assert multiple semantic statements about the same subject. Annotating different subjects requires using additional `describes` elements.

The `additionalMetadata` annotation below (Example 5) describes a semantic statement with the subject being a `creator` element containing the `id` attribute "adam.shepherd". The object property of the statement is "https://schema.org/memberOf". Finally, the object (value) in the semantic statement is "https://doi.org/10.17616/R37P4C". Taken together, the semantic statement could be read as "'adam.shepherd', the creator (of the dataset), is a member of BCO-DMO".

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
    ...
</eml>
```
