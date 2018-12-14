# Semantic Annotations Primer

insert introductory text here
   * URIs should be resolvable

## dataset level

- describe that this annotation is embedded within an EML `dataset` element and the subject is the `dataset` id attribute value
- describe the propertyURI and valueURI elements in the annotation, the label attributes of these elements, and the content of these elements  

Example `dataset` annotation:

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
    <annotation>
        <propertyURI label="Subject">http://purl.org/dc/elements/1.1/subject</propertyURI>
        <valueURI label="grassland biome">http://purl.obolibrary.org/obo/ENVO_01000177</valueURI>
    </annotation>
</dataset>  

```

## entity level

- describe that this annotation is embedded within an EML `dataTable` (?) element and the annotation subject is the `dataTable` id attribute value
- describe the propertyURI and valueURI elements in the annotation, the label attributes of these elements, and the content of these elements  
- show an example annotation



## attribute level

An attribute is a characteristic that describes a 'field' or 'variable' in a data entity, such as a column name in a spreadsheet. An attribute annotation represents a precisely-defined semantic statement that applies to an attribute. This semantic statement is used to associate precise measurement semantics with the attribute, such as the property being measured, the entity being measured, and the measurement standard for interpreting values for the attribute.

A typical attribute annotation is embedded in a containing EML `attribute` element. Each annotation consists of a propertyURI and valueURI that define the property and value of the semantic statement. The subject of the statement is the EML `attribute` that contains the annotation. The associated labels can be used to display the statement to users. Each URI is resolvable to a controlled vocabulary that provides a definition, relationships to other terms, and multiple labels for displaying the statement.

In the following EML `attribute` element annotation below, the subject of the semantic statement is the `id` element attribute value, "att.4". The object property of the statement is `http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#containsMeasurementsOfType`. Note that the URI for the object property resolves to a specific term in the OBOE ontology (https://github.com/NCEAS/oboe). Finally, the object in the semantic statement is `http://purl.dataone.org/odo/ECSO_00001197`, which resolves to the "Plant Cover Percentage" term in the ECSO Ontology (https://github.com/DataONEorg/sem-prov-ontologies/tree/master/observation). Taken together, the semantic statement can be interpreted as "att.4 contains measurements of type plant cover percentage".

Example EML `attribute` element annotation:

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


## /eml/annotations

- describe how a `references` attribute (of the annotation element) points to the `id` of the subject of the annotation
- describe the propertyURI and valueURI elements in the annotation, the label attributes of these elements, and the content of these elements  

Example `/eml/annotations` annotation:

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


## /eml/additionalMetadata

- describe how the `describes` element contains the `id` of the annotation subject
- describe the propertyURI and valueURI elements in the annotation, the label attributes of these elements, and the content of these elements  

Example `/eml/additionalMetadata` annotation:

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
