# Semantic Annotations Primer

insert introductory text here


## attribute

- describe that this annotation is embedded within an EML `attribute` element and the annotation subject is the `attribute` id attribute value
- describe the propertyURI and valueURI elements in the annotation, the label attributes of these elements, and the content of these elements  
- show an example annotation


## entity

- describe that this annotation is embedded within an EML `dataTable` element and the annotation subject is the `dataTable` id attribute value
- describe the propertyURI and valueURI elements in the annotation, the label attributes of these elements, and the content of these elements  
- show an example annotation

## dataset

- describe that this annotation is embedded within an EML `dataset` element and the subject is the `dataset` id attribute value
- describe the propertyURI and valueURI elements in the annotation, the label attributes of these elements, and the content of these elements  
- show an example annotation


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
