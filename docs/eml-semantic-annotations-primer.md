# Semantic Annotations Primer (in progress)

## Introduction
A semantic annotation is the attachment of semantic metadata to a resource. Semantic metadata provides a precise definition of concepts and clarifies the relationships between concepts. Although the process of semantic annotation may seem tedious, the payoff is enhanced information retrieval and discovery. For example, if a dataset is annotated as being about "carbon dioxide flux" and another annotated with "CO2 flux" the information system should recognize that the datasets are about equivalent concepts. In another example, if a user performs a search for datasets about "litter" (as in "plant litter"), the system will disambiguate the term from other meanings of "litter" (as in garbage, the group of animals born at the same time, etc.). Yet another example is if a user searches for datasets about "carbon flux", then datasets about "carbon dioxide flux" will also be returned because "carbon dioxide flux" is considered a type of "carbon flux".

A semantic annotation follows the Resource Description Framework (RDF) data model and uses semantic triples. A semantic triple is composed of a **subject**, **object property (predicate)**, and **object**. In general, the subject and object can be thought of as nouns in a sentence and the object property is akin to a verb or relationship that connects the subject and object. The semantic triple expresses a statement about the associated resource. Ideally, these components should be globally unique and should be resolvable uniform resource identifiers (URIs) from controlled vocabularies so that users can look up the definitions and relationships of the terms to other terms. An example is "http://purl.obolibrary.org/obo/ENVO_01001357", which resolves to the term "desert" in the Environment Ontology (ENVO) when entered into an address bar in a web browser. Users can find the definition for "desert" and determine its relationship to other terms in the ontology. 
* Additional background information on the RDF data model: https://www.w3.org/TR/WD-rdf-syntax-971002/

## Semantic Annotations in EML 2.2.0
In **EML 2.2.0** there are 5 kinds of semantic annotations that can be made in an EML document: **dataset-level**, **entity-level**, **attribute-level**, **/eml/annotations** and **/eml/additionalMetadata**.

### Pattern for dataset-level, entity-level, and attribute-level annotations
Semantic annotations made at the **dataset-level**, **entity-level**, and **attribute-level** follow the same pattern. An annotation made at any of these levels involves inserting an `annotation` element containing a `propertyURI` element and a `valueURI` element within the appropriate element. The *subject* of this annotation is the containing element. *It is recommended to give the subject element an* `id` *attribute and make the subject the value of the* `id`. The `propertyURI` is the *object property* and the `valueURI` is the *object* of the annotation. For example, an attribute-level annotation involves an `attribute` element. Within the `attribute` element are `propertyURI` and `valueURI` elements. The URIs should ideally point to terms in controlled vocabularies.

Multiple `annotation` elements may be embedded in the same dataset, entity-level or attribute element to assert multiple semantic statements.

```
<dataset or entity-level or attribute>             <- subject 
    <annotation>
        <propertyURI>123</propertyURI>             <- object property 1 is "123"
        <valueURI>abc</valueURI>                   <- object 1 is "abc"
    </annotation>
    <annotation>
        <propertyURI>789</propertyURI>             <- object property 2 is "789"
        <valueURI>xyz</valueURI>                   <- object 2 is "xyz"
    </annotation>
</dataset or entity-level or attribute>
```

#### Dataset-level annotations

A dataset is defined as all of the information describing a data collection event. This event may take place over some period of time and include many actual collections (e.g. a time series or remote sensing application) or it could be just one actual collection (e.g. a day in the field). The `dataset` element encompasses all information about a single dataset. It is intended to provide overview information about the dataset: broad information such as the title, abstract, keywords, contacts, maintenance history, purpose, and distribution of the data themselves. A dataset can be (and often is) composed of a series of data entities (see 'entity-level annotation' section below) that are linked together by particular integrity constraints. Further information about datasets may be found at:[insert link].

A dataset-level annotation represents a precisely-defined semantic statement that applies to a dataset. This semantic statement is used to associate precise measurement semantics with the dataset. A dataset-level `annotation` element is embedded in a containing `dataset` element. The subject of the semantic statement is the `dataset` element that contains the annotation. If the `dataset` element contains an `id` attribute, then the subject should be the value of the `id` attribute. Each annotation consists of a `propertyURI` element and `valueURI` element, which respectively define a property and a value (object) that apply to the dataset. The associated labels can be used to display the property and value in a more readable format to users. Each URI should be resolvable to a controlled vocabulary that provides a precise definition, relationships to other terms, and multiple labels for displaying the statement.

In the following dataset-level annotation (Example 1), the subject of the semantic statement is the `dataset` element's `id` attribute value, "dataset-01". The object property of the statement is `http://purl.org/dc/elements/1.1/subject`. Finally, the value (object) in the semantic statement is `http://purl.obolibrary.org/obo/ENVO_01000177`, which resolves to the "grassland biome" term in the ENVO ontology (http://www.obofoundry.org/ontology/envo.html). Taken together, the semantic statement could be read as "the dataset with the id 'dataset-01' is about the subject grassland biome".

* Example 1: dataset-level annotation

```
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

#### Entity-level annotations

Entities are usually tables of data (EML element `dataTable`). Data tables may be ascii text files, relational database tables, spreadsheets or other type of tabular data with a fixed logical structure. Related to data tables are views (EML element `view`) and stored procedures (EML element `storedProcedure`). Views and stored procedures are produced by an RDBMS or related system. Other types of data such as raster (EML element `spatialRaster`), vector (EML element `spatialVector`) or spatialReference image data are also data entities. An `otherEntity` element should be used to describe types of entities that are not described by any other entity type. Entity-level EML elements are nested under `dataset` elements. Further information about entities may be found at: [insert link].

An entity-level annotation represents a precisely-defined semantic statement that applies to an entity. This semantic statement is used to associate precise measurement semantics with the entity. An entity-level `annotation` element is embedded in a containing entity-level element. The subject of the semantic statement is the entity-level element that contains the annotation. If the entity-level element contains an `id` attribute, then the subject should be the value of the `id` attribute. Each annotation consists of a `propertyURI` element and `valueURI` element, which respectively define a property and a value (object) that apply to the entity. The associated labels can be used to display the property and value in a more readable format to users. Each URI should be resolvable to a controlled vocabulary that provides a precise definition, relationships to other terms, and multiple labels for displaying the statement.

In the following entity-level annotation (Example 2), the subject of the semantic statement is the `otherEntity` element's `id` attribute value, "urn:uuid:9f0eb128-aca8-4053-9dda-8e7b2c43a81b". The object property of the statement is `http://purl.org/dc/elements/1.1/subject`. Finally, the value (object) in the semantic statement is `http://purl.obolibrary.org/obo/NCBITaxon_40674`, which resolves to the "Mammalia" term in the NCBITaxon ontology (http://www.ontobee.org/ontology/NCBITaxon). Taken together, the semantic statement indicates that "the entity with the id 'urn:uuid:9f0eb128-aca8-4053-9dda-8e7b2c43a81b' is about the subject Mammalia".

 * Example 2: entity-level annotation

```
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

#### Attribute-level annotations

An attribute is a characteristic that describes a 'field' or 'variable' in a data entity, such as a column name in a spreadsheet. An attribute annotation represents a precisely-defined semantic statement that applies to an attribute. This semantic statement is used to associate precise measurement semantics with the attribute, such as the property being measured, the entity being measured, and the measurement standard for interpreting values for the attribute. `attribute` elements may be nested in entity-level elements, including the `dataTable`, `spatialRaster`, `spatialVector`, `storedProcedure`, `view`, or `otherEntity` EML elements, in addition to custom modules. Refer to the Data Structures Modules documentation for additional information about attributes [insert link].  

A typical attribute annotation involves an `annotation` element that is embedded in a containing `attribute` element. The subject of the semantic statement is the `attribute` element that contains the annotation. If the `attribute` element contains an `id` attribute, then the subject should be the value of the `id` attribute. Each annotation consists of a `propertyURI` element and `valueURI` element that respectively define the property and value (object) of the semantic statement. The associated labels can be used to display the property and value in a more readable format to users. Each URI should be resolvable to a controlled vocabulary that provides a precise definition, relationships to other terms, and multiple labels for displaying the statement. Note that for annotating attributes that are measurements contained in tabular formats the preferred "default" object property is "contains measurements of type" (`http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#containsMeasurementsOfType`).

In the following attribute annotation (Example 3), the subject of the semantic statement is the `attribute` element's `id` attribute value, "att.4". The object property of the statement is `http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#containsMeasurementsOfType`. Note that the URI for the object property resolves to a specific term in the OBOE ontology (https://github.com/NCEAS/oboe). Finally, the value (object) in the semantic statement is `http://purl.dataone.org/odo/ECSO_00001197`, which resolves to the "Plant Cover Percentage" term in the ECSO Ontology (https://github.com/DataONEorg/sem-prov-ontologies/tree/master/observation). Taken together, the semantic statement indicates that "att.4 contains measurements of type plant cover percentage".

* Example 3: attribute-level annotation

```
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

### Pattern for /eml/annotations annotations
Semantic annotations may also be inserted in the `annotations` element that is nested under the `eml` root element. This type of semantic annotation involves an `annotation` element that has a `references` attribute. What is listed in the `references` attribute is the *subject* of the semantic annotation. Within the `annotation` element are `propertyURI` and `valueURI` elements. The `propertyURI` is the *object property* and the `valueURI` is the *object* of the annotation. The URIs should ideally point to terms in controlled vocabularies. 

Multiple `annotation` elements can be used to create multiple annotations about the same subject. 
```
<eml>
  ...
    <annotations>
        <annotation references="test_data">         <- subject 1 is "test_data"
            <propertyURI>abc</propertyURI>          <- object property 1 is "abc"
            <valueURI>123</valueURI>                <- object 1 is "123"
        </annotation>
        <annotation references="test_data">         <- subject 2 is "test_data" (same as subject 1)
            <propertyURI>xyz</propertyURI>          <- object property 2 is "xyz"
            <valueURI>789</valueURI>                <- object 2 is "789"
        </annotation>
    </annotations>
  ...  
</eml>
```


#### Annotations in /eml/annotations

The `annotations` element is nested under the `eml` root element and contains a list of annotations defining precise semantic statements for parts of a resource. An annotation represents a precisely-defined semantic statement that applies to the resource. This statement is used to associate precise semantics with a particular element in the EML document. For additional details, refer to [insert link]

The `annotations` element contains a set of `annotation` elements. Each `annotation` element has a `references` attribute that points to the `id` attribute of the element being annotated. The id of the element being annotated is listed in the `references` attribute, and must point to a unique id within the EML document. In the semantic statement, the subject is implicitly the id that is referenced. Each annotation also consists of a `propertyURI` element and `valueURI` element that respectively define a property and value (object) that apply to the resource. The associated labels can be used to display the statement in a more readable format to users. Each URI should resolve to a controlled vocabulary that provides a definition, relationships to other terms, and multiple labels for displaying the statement.

The following `/eml/annotations` example (Example 4) has 3 different annotations. For the first annotation, the subject of the semantic statement is "CDF-biodiv-table", which is the id of another element in the EML document. The object property of the statement is `http://purl.org/dc/elements/1.1/subject`. Finally, the value (object) in the semantic statement is `http://purl.obolibrary.org/obo/ENVO_01000177`, which resolves to the "grassland biome" term in the ENVO ontology (`http://www.obofoundry.org/ontology/envo.html`). Taken together, the first semantic statement could be read as "CDR-biodiv-table is about the subject grassland biome".

The second semantic statement contains the subject "adam.shepherd", the object property `http://www.w3.org/1999/02/22-rdf-syntax-ns#type` and the value (object) `https://schema.org/Person`. This statement can be interpreted as "adam.shepherd is a person".

The third semantic statement also has the subject "adam.shepherd". The object property is "https://schema.org/memberOf" and the value (object) is "https://doi.org/10.17616/R37P4C". This statement can be read as "adam.shepherd is a member of BCO-DMO".

* Example 4: `/eml/annotations` annotation

```
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

```

### Pattern for /eml/additionalMetadata annotations
Semantic annotations may also be inserted in the `additionalMetadata` element that is nested under the `eml` root element. This type of semantic annotation has a `describes` element and a `metadata` element containing the annotation. The `metadata` element has an `annotation` element. The content of the `describes` element is the *subject* of the semantic annotation. Within the `annotation` element are `propertyURI` and `valueURI` elements. The `propertyURI` is the *object property* and the `valueURI` is the *object* of the annotation. The URIs should ideally point to terms in controlled vocabularies.

Multiple `annotation` elements may be embedded in the same `metadata` element to assert multiple semantic statements about the same subject.
```
<eml>
  ...
    <additionalMetadata>
        <describes>john.smith</describes>               <- subject is "john.smith"
        <metadata>
            <annotation>
                <propertyURI>123</propertyURI>          <- object property 1 is "123"    
                <valueURI>ABC</valueURI>                <- object 1 is "ABC"
            </annotation>
            <annotation>
                <propertyURI>789</propertyURI>          <- object property 2 is "789"
                <valueURI>XYZ</valueURI>                <- object 2 is "XYZ"
            </annotation>
        </metadata>
    </additionalMetadata>
  ...
</eml>
```


#### Annotations in /eml/additionalMetadata

The `additionalMetadata` element is nested under the `eml` root element and contains metadata that is not suitable for other parts of the EML document. It is intended to extend EML to include metadata that is not already available in another part of the EML specification, or to include site- or system-specific extensions that are needed beyond the core metadata.  The content of this field is any well-formed XML fragment. Additional information may be found at [insert link].

The `additionalMetadata` element contains `describes` elements, `metadata` elements, and `annotation` elements. The `describes` element has a pointer to the `id` attribute for the sub-portion of the resource that is described by the additional metadata. It is the `metadata` element that holds the additional metadata to be included in the document. This additional metadata field describes the element referenced in the `describes` element preceding it. Nested under the `metadata` element is the `annotation` element.  An annotation is a precisely-defined semantic statement about an element in the EML document. The subject of the semantic statement is the id being referenced in the `describes` element that precedes the `metadata` element. Each `annotation` element consists of a `propertyURI` element and `valueURI` element that respectively define the property and value (object) of the semantic statement. The associated labels can be used to display the property and value in a more readable format to users. Each URI should be resolvable to a controlled vocabulary that provides a precise definition, relationships to other terms, and multiple labels for displaying the statement. 

The following `/eml/additionalMetadata` example (Example 5) describes a semantic statement having the subject "adam.shepherd", which is the id of another element in the EML document. The object property of the statement is `https://schema.org/memberOf`. Finally, the value (object) in the semantic statement is `https://doi.org/10.17616/R37P4C`. Taken together, the semantic statement could be read as "adam.shepherd is a member of BCO-DMO".


* Example 5: `/eml/additionalMetadata` annotation

```
<additionalMetadata>
    <describes>adam.shepherd</describes>
    <metadata>
        <annotation>
            <propertyURI label="member of">https://schema.org/memberOf</propertyURI>
            <valueURI label="BCO-DMO">https://doi.org/10.17616/R37P4C</valueURI>
        </annotation>
    </metadata>
</additionalMetadata>

```
