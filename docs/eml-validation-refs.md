# Validation and Content references

This section explains the validation rules of EML. While most of the validation
rules are expressed as constraints within the XML Schema definition files, there are 
some rules that cannot be written directly into the XML Schemas nor enforced 
by an XML parser. These additional validation rules MUST be enforced by every 
EML package in order for it to be considered EML-compliant.

## Validation rules

For a document to be EML-valid, all of the following constraints must hold true:

- The document MUST validate using a compliant XML Schema validating parser
- All EML documents MUST have the 'eml' module as the root
- A `packageId` attribute MUST be present on the root `eml` element
- All `id` attributes within the document MUST be unique
- Elements which contain an `annotation` child element MUST contain an `id` attribute,
  unless the containing `annotation` element contains a `references` attribute
- If an element references another using a child `references` element, 
  another element with that value in its `id` attribute MUST exist in the document
- When `references` is used, the `system` attribute MUST have 
  the same value in both the target and source elements, or it must be absent in both.
  Frequently it is absent in both.
- If an element references another using a child `references` element, 
  it MUST not have an `id` attribute itself
- If an `additionalMetadata` element references another using a child `describes` element, 
  another element with that value in its `id` attribute MUST exist in the document

## Validation algorithm

One reasonable algorithm for assessing these constraints without loading the XML into 
a DOM structure could be implemented by checking `id` and `references` fields while 
parsing the document and storing their values in `identifierHash` and `referencesHash` data
structures in order to do the final consistency check. For example, in pseudocode:

- Parse the XML document using an XML Schema-compliant parser
    - If the root element is not `eml`, then the document is invalid
    - For each element, record whether it has an `id` attribute or not
        - If an element does not contain an `id`, but it has a child `annotation` 
          element, and that child annotation does not contain a `references` attribute, then the document is invalid
    - For each `id` attribute
        - If `id` is not in `identifiersHash` then add it as the key of `identifiersHash`, with its `system` as the value
        - If `id` is already in `identifiersHash` then the document is invalid
        - If the element containing the id contains a `references` element as an 
          immediate child then the document is invalid
    - For each `references` element
        - If the `references` key is not in `referencesHash`,
          then add it as a key with the `system` value to `referencesHash`
        - If the `references` key is in `referencesHash`, but the current `system` 
          value does not match the value for that key, then the document is invalid
    - For each `references` attribute on an `annotation` element
        - If the `references` key is not in `referencesHash`, 
          then add it as a key with the empty string '' value to `referencesHash`
    - For each `describes` element within an `additionalMetadata` element
        - If the `describes` key is not in `referencesHash`, 
          then add it as a key with the empty string '' value to `referencesHash`
    - Once document processing is complete, for each `key` in `referencesHash`
        - If `!identifierHash.hasKey(key) OR 'referencesHash[key] != identifierHash[key]'` then the document is invalid
    - If no validity errors are found above or by the parser, then the document is valid

## Content references

Each EML module, with the exception of "eml" itself, has a top level
choice between the structured content of that element or a
"references" field. This enables the reuse of content previously
defined elsewhere in the document.  This allows, for example, an author to
create a single `<creator id='m.jones'>` element with all of its child detail, 
and then reference that as `<contact><references>m.jones</references></contact>` 
to indicate that the same person is both the creator and contact.  This creates
an unambiguous linkage via the `id` field that the two elements refer to the same 
entity, in this case a person, and avoids having to re-enter the same information
multiple times in the document.  Another common location for re-use is when a single
`attributeList` is defined with a set of variables and their metadata, and then
that list is referenced in multiple `dataTable` elements to show that they are 
structured identically.

The reuse of structured content is accomplished through the 
use of `id`/`references` pairs. Each element that is to be reused will contain a
unique `id` attribute on the element. Because this identifier is guaranteed to
be unique within the EML document, any other location that wants to point at that 
content can do so using the `references` element, as shown in the example above.
These types of references can also be used in the `references` attribute of 
`annotation` elements, and in the `describes` element within the `additionalMetadata`
element.

If an `id` attribute is provided for content, then that content is considered 
to represent a different entity than all other elements that are defined in 
the document, except for those that include its `id` in the `references` child.
This is useful to indicate, for example, that two people with similar names 
(e.g., "D. Clark" and "D. Clark") are in fact distinct individuals 
(e.g., "Deborah Clark" and "David Clark"), or that two variables with the same 
`attributeName` are in fact different variables. While it would be bad practice
to reuse attribute names like this, it does happen and EML needs to be able to document it
when it does.

## EML Validity Parser

Because some of these rules cannot be enforced in XML-Schema, we have
written a parser which checks the validity of the references and `id`s
used in a document. This parser is included with the release of
EML. To run the parser, you must have Java installed. To execute
it change into the lib directory of the release and run the
'runEMLParser' script passing your EML instance file as a parameter.
There may also be an [online
version](https://knb.ecoinformatics.org/emlparser) of this parser, which
is publicly accessible. The online parser will both validate your XML
document against the schema as well as check the integrity of your
references.

## id and Scope Examples

**Example: Invalid EML due to duplicate identifiers**

```xml
    <?xml version="1.0"?>
    <eml:eml
        packageId="eml.1.1" system="knb"
        xmlns:eml="eml://ecoinformatics.org/eml-2.2.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="eml://ecoinformatics.org/eml-2.2.0 eml.xsd">

      <dataset id="ds.1">
        <title>Sample Dataset Description</title>
        <!-- the two creators have the same id.  this should be an error-->
        <creator id="23445" scope="document">
          <individualName>
            <surName>Smith</surName>
          </individualName>
        </creator>
        <creator id="23445" scope="document">
          <individualName>
            <surName>Smith</surName>
          </individualName>
        </creator>
        ...
      </dataset>
    </eml:eml>
```

This instance document is invalid because both creator elements have the
same id. No two elements can have the same string as an id.

**Example: Invalid EML due to a non-existent reference**

```xml
    <?xml version="1.0"?>
    <eml:eml
        packageId="eml.1.1" system="knb"
        xmlns:eml="eml://ecoinformatics.org/eml-2.2.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="eml://ecoinformatics.org/eml-2.2.0 eml.xsd">

      <dataset id="ds.1">
        <title>Sample Dataset Description</title>
        <creator id="23445" scope="document">
          <individualName>
            <surName>Smith</surName>
          </individualName>
        </creator>
        <creator id="23446" scope="document">
          <individualName>
            <surName>Myer</surName>
          </individualName>
        </creator>
        ...
        <contact>
          <references>23447</references>
        </contact>
      </dataset>
    </eml:eml>
```

This instance document is invalid because the contact element references
an `id` that does not exist. Any referenced `id` must exist in the document.

**Example: Invalid EML due to a conflicting id attribute and a
`<references>` element**

```xml
    <?xml version="1.0"?>
    <eml:eml
        packageId="eml.1.1" system="knb"
        xmlns:eml="eml://ecoinformatics.org/eml-2.2.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="eml://ecoinformatics.org/eml-2.2.0 eml.xsd">

      <dataset id="ds.1">
        <title>Sample Dataset Description</title>
        <creator id="23445" scope="document">
          <individualName>
            <surName>Smith</surName>
          </individualName>
        </creator>
        <creator id="23446" scope="document">
          <individualName>
            <surName>Meyer</surName>
          </individualName>
        </creator>
        ...
        <contact id="522">
          <references>23445</references>
        </contact>
      </dataset>
    </eml:eml>
```

This instance document is invalid because the contact element both
references another element and has an id itself. If an element
references another element, it may not have an id. This prevents
circular references.

**Example: A valid EML document**

```xml
    <?xml version="1.0"?>
    <eml:eml
        packageId="eml.1.1" system="knb"
        xmlns:eml="eml://ecoinformatics.org/eml-2.2.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="eml://ecoinformatics.org/eml-2.2.0 eml.xsd">

      <dataset id="ds.1">
        <title>Sample Dataset Description</title>
        <creator id="23445" scope="document">
          <individualName>
            <surName>Smith</surName>
          </individualName>
        </creator>
        <creator id="23446" scope="document">
          <individualName>
            <surName>Smith</surName>
          </individualName>
        </creator>
        ...
        <contact>
          <references>23446</references>
        </contact>
        <contact>
          <references>23445</references>
        </contact>
      </dataset>
    </eml:eml>
```

This instance document is valid. Each contact is referencing one of the
creators above and all the ids are unique.  The each creator has a its own `id`
indicates that they are different people, even though they have the same
`surName` and there is no other distinguishing metadata.
