# Chapter 3. Technical Architecture (Normative)

## Introduction {#introduction-1 .title style="clear: both"}

This section explains the rules of EML. There are some rules that cannot
be written directly into the XML Schemas nor enforced by an XML parser.
These are guidelines that every EML package must follow in order for it
to be considered EML compliant.

## Module Structure

Each EML module, with the exception of \"eml\" itself, has a top level
choice between the structured content of that modules or a
\"references\" field. This enables the reuse of content previously
defined elsewhere in the document. Methods for defining and referencing
content are described in the
[next](#reusableContent "3.3. Reusable Content") section

## Reusable Content

EML allows the reuse of previously defined structured content (DOM
sub-trees) through the use of key/keyRef type references. In order for
an EML package to remain cohesive and to allow for the cross platform
compatibility of packages, the following rules with respect to packaging
must be followed.

-   An ID is required on the eml root element.
-   IDs are optional on all other elements.
-   If an ID is not provided, that content must be interpreted as
-   If an ID is provided for content then that content is distinct from
    all other content except for that content that references its ID.
-   If a user wants to reuse content to indicate the repetition of an
    object, a reference must be used. Two identical ids with the same
    system attribute cannot exist in a single document.
-   \"Document\" scope is defined as identifiers unique only to a single
    instance document (if a document does not have a system attribute or
    if scope is set to \'document\' then all IDs are defined as distinct
    content).
-   \"System\" scope is defined as identifiers unique to an entire data
    management system (if two documents share a system string, then any
    IDs in those two documents that are identical refer to the same
    object).
-   If an element references another element, it must not have an ID
    itself. The system attribute must have the same value in both the
    target and referencing elements or it must be absent in both.
-   All EML packages must have the \'eml\' module as the root.
-   The system and scope attribute are always optional except for at the
    \'eml\' module where the scope attribute is fixed as \'system\'. The
    scope attribute defaults to \'document\' for all other modules.

### ID and Scope Examples

#### EML Parser

Because some of these rules cannot be enforced in XML-Schema, we have
written a parser which checks the validity of the references and IDs
used in your document. This parser is included with the 2.1.0 release of
EML. To run the parser, you must have Java 1.3.1 or higher. To execute
it change into the lib directory of the release and run the
\'runEMLParser\' script passing your EML instance file as a parameter.
There is also an [online
version](https://knb.ecoinformatics.org/emlparser) of this parser which
is publicly accessible. The online parser will both validate your XML
document against the schema as well as check the integrity of your
references.

#### Example Documents

**Example 3.1. Invalid EML due to duplicate identifiers**

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
            <surName>Myer</surName>
          </individualName>
        </creator>
        ...
      </dataset>
    </eml:eml>
```

This instance document is invalid because both creator elements have the
same id. No two elements can have the same string as an id.

**Example 3.2. Invalid EML due to a non-existent reference**

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
an id that does not exist. Any referenced id must exist.

**Example 3.3. Invalid EML due to a conflicting id attribute and a
\<references\> element**

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

**Example 3.4. A valid EML document**

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
creators above and all the ids are unique.
