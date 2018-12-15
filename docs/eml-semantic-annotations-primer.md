# Semantic Annotations Primer

insert introductory text here
   * why annotate?
   * describe semantic triples
   * URIs should be resolvable
   * annotations may be made at the dataset-level, entity-level attribute-level, in `/eml/annotations`, and in `/eml/additionalMetadata`


## dataset-level annotation

A dataset is defined as all of the information describing a data collection event. This event may take place over some period of time and include many actual collections (e.g. a time series or remote sensing application) or it could be just one actual collection (e.g. a day in the field). The `dataset` EML element encompasses all information about a single dataset. A dataset can be (and often is) composed of a series of data entities (see 'entity-level annotation' section below) that are linked together by particular integrity constraints. Further information about datasets may be found at:[insert link].



- describe that this annotation is embedded within an EML `dataset` element and the subject is the `dataset` id attribute value
- describe the propertyURI and valueURI elements in the annotation, the label attributes of these elements, and the content of these elements  

Example 1: dataset-level annotation

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

## entity-level annotation

Entities are usually tables of data (EML element `dataTable`). Data tables may be ascii text files, relational database tables, spreadsheets or other type of tabular data with a fixed logical structure. Related to data tables are views (EML element `view`) and stored procedures (EML element `storedProcedure`). Views and stored procedures are produced by an RDBMS or related system. Other types of data such as: raster (EML element `spatialRaster`), vector (EML element `spatialVector`) or spatialReference image data are also data entities. An `otherEntity` element would be used to describe types of entities that are not described by any other entity type. Entity-level EML elements are nested under `dataset` elements. Further information about entities may be found at: [insert link].

An entity-level annotation represents a precisely-defined semantic statement that applies to an entity. This semantic statement is used to associate precise measurement semantics with the entity. An entity-level annotation is embedded in a containing entity-level element. The subject of the semantic statement is the entity-level element that contains the annotation. Each annotation consists of a `propertyURI` element and `valueURI` element, which respectively define a property and a value (object) that apply to the entity. The associated labels can be used to display the property and value in a more readable format to users. Each URI should be resolvable to a controlled vocabulary that provides a precise definition, relationships to other terms, and multiple labels for displaying the statement. 

In the following entity-level annotation (Example 2), the subject of the semantic statement is the EML `otherEntity` element's `id` attribute value, "urn:uuid:9f0eb128-aca8-4053-9dda-8e7b2c43a81b". The object property of the statement is `http://purl.org/dc/elements/1.1/subject`. Finally, the value (object) in the semantic statement is `http://purl.obolibrary.org/obo/NCBITaxon_40674`, which resolves to the "Mammalia" term in the NCBITaxon ontology (http://www.ontobee.org/ontology/NCBITaxon). Taken together, the semantic statement indicates that "the entity with the id 'urn:uuid:9f0eb128-aca8-4053-9dda-8e7b2c43a81b' is about the subject Mammalia".

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

## attribute-level annotation

An attribute is a characteristic that describes a 'field' or 'variable' in a data entity, such as a column name in a spreadsheet. An attribute annotation represents a precisely-defined semantic statement that applies to an attribute. This semantic statement is used to associate precise measurement semantics with the attribute, such as the property being measured, the entity being measured, and the measurement standard for interpreting values for the attribute. `attribute` elements may be nested in entity-level elements, including the `dataTable`, `spatialRaster`, `spatialVector`, `storedProcedure`, `view`, or `otherEntity` EML elements, in addition to custom modules. Refer to the Data Structures Modules documentation for additional information about attributes [insert link].  

A typical attribute annotation is embedded in a containing EML `attribute` element. The subject of the semantic statement is the `attribute` element that contains the annotation. Each annotation consists of a `propertyURI` element and `valueURI` element that respectively define the property and value (object) of the semantic statement. The associated labels can be used to display the property and value in a more readable format to users. Each URI should be resolvable to a controlled vocabulary that provides a precise definition, relationships to other terms, and multiple labels for displaying the statement. Note that for annotating attributes that are measurements contained in tabular formats the preferred "default" object property is "contains measurements of type" (`http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#containsMeasurementsOfType`).

In the following attribute annotation (Example 3), the subject of the semantic statement is the EML `attribute` element's `id` attribute value, "att.4". The object property of the statement is `http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#containsMeasurementsOfType`. Note that the URI for the object property resolves to a specific term in the OBOE ontology (https://github.com/NCEAS/oboe). Finally, the value(object) in the semantic statement is `http://purl.dataone.org/odo/ECSO_00001197`, which resolves to the "Plant Cover Percentage" term in the ECSO Ontology (https://github.com/DataONEorg/sem-prov-ontologies/tree/master/observation). Taken together, the semantic statement indicates that "att.4 contains measurements of type plant cover percentage".

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


## annotations in /eml/annotations

- describe how a `references` attribute (of the annotation element) points to the `id` of the subject of the annotation
- describe the propertyURI and valueURI elements in the annotation, the label attributes of these elements, and the content of these elements  

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


## annotations in /eml/additionalMetadata

- describe how the `describes` element contains the `id` of the annotation subject
- describe the propertyURI and valueURI elements in the annotation, the label attributes of these elements, and the content of these elements  

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
