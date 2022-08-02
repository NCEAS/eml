[EML Schema Documentation](./index.html)

[EML FAQs](./eml-faq.html)

Several modifications to the EML schema made in version 2.1.0 will
require changes to how EML documents are structured, and these changes
are highlighted here. EML authors should also refer to the affected
sections in the normative schema documents for complete usage
information and examples. Existing EML 2.0-series documents can be
converted to EML 2.1.0 using the XSL stylesheet that accompanies this
release, as described in section 2 below.

The EML 2.1.0 release addresses several errors with respect to W3C
specifications for XML schema (http://www.w3.org/TR/xml). Although the
changes are small, they are incompatible with EML 2.0.0 and 2.0.1
schemas, which necessitated advancing the version number to "2.1". The
STMML schema was also found to be invalid with respect to XML Schema
language, and the most reasonable fix for this bug also is incompatible
with its earlier versions. EML users should note that the STMML schema
error was *not* related to elements used directly by EML (i.e.,
&lt;unitList&gt; or &lt;unitType&gt;). However, EML imports all of
STMML, and authors of EML documents may have made use of other parts of
that schema. Therefore, it was decided to advance the namespace used for
STMML-related imports to "stmml-1.1", in keeping with the EML version
naming pattern. The STMML authors have been contacted, and they are
interested in our development and use of STMML.

Other features and enhancements were added to this release that
represent significant improvements. The XML data type requirements for
several elements were changed, in some cases to constrain their content,
and in other cases to increase flexibility. The names of two elements
were changed to make them consistent throughout EML. In the literature
schema two elements became optional so that EML could accommodate
in-press publications where the volume and page range are not yet known.
Support for two new optional elements was also added: a 'contact' tree
can now be used in the literature module, and a ‘descriptive’ element
can be used in distribution trees.

For the most part, EML 2.1.0 does not introduce major new features, or
require a shift in use or implementation. There was a deliberate
decision to balance the impact on instance document authors with
necessary schema maintenance, and to prepare the schema for the next
phase of planned improvements and features. Some of the changes to EML
2.1.0 are invisible to document authors; see the 'Readme' that
accompanies the distribution for a complete list of the bugs addressed,
and for information of interest to developers.

Changes and New Features in EML 2.1.0
=====================================

**Q:** EML Schema validity

**A:** EML allows authors to place any XML markup in
&lt;additionalMetadata&gt; sections at the end of the document. The
content model for &lt;additionalMetadata&gt; includes an optional
&lt;describes&gt; element so that references to EML nodes can be
included as necessary. In EML 2.0 this element was placed alongside the
additional XML content; however, this construct is not allowed in XML
Schema, and the error was not reported by XML parsers available at the
time EML 2.0 was released. In EML 2.1.0, the error has been corrected by
adding a required child element to the &lt;additionalMetadata&gt;
section to contain the "&lt;xs:any&gt;" XML content.

&lt;additionalMetadata&gt; sections must include the child
&lt;metadata&gt; to contain the additional XML markup. The optional
&lt;describes&gt; element may still be included to reference a
particular node of the document. Multiple &lt;describes&gt; elements can
be included if needed. Examples of documents written against 2.1.0 and
2.0.1 are below. Also see the [additionalMetadata normative
documentation](eml.html#additionalMetadata).

In EML 2.0.1, an additionalMetadata section looked like this:

```xml
    ...
     <additionalMetadata>
     <describes>123</describes>
      <unitList>
       <unit name="speciesPerSquareMeter" 
             unitType="arealDensity" 
         id="speciesPerSquareMeter" 
         parentSI="numberPerSquareMeter" 
         multiplierToSI="1"/>
      </unitList>
     </additionalMetadata>...
```

In EML 2.1.0, the markup must be enclosed within &lt;metadata&gt; tags:

```xml
    ...
    <additionalMetadata>
    <describes>123</describes>
     <metadata>
      <unitList>
       <unit name="speciesPerSquareMeter" 
             unitType="arealDensity" 
             id="speciesPerSquareMeter" 
             parentSI="numberPerSquareMeter" 
             multiplierToSI="1"/>
      </unitList>
     </metadata>
    </additionalMetadata>
    ...
```            

**Q:** STMML Schema validity

**A:** EML makes use of the Scientific Technical and Medical Markup
Language schema (STMML, stmml.xsd) for describing units, and the STMML
schema was also found to be invalid. The error was not related to
elements used directly by EML (i.e., &lt;unitList&gt; or
&lt;unitType&gt;), however some authors may have used other parts of
stmml.xsd in their documents. The required schema changes were not
compatible with STMML-1.0, and the EML development group is working with
the STMML developers on this issue. Since EML now imports a version of
STMML that is not identical to that available from its authors, it was
decided to advance the namespace used by EML 2.1.0 for stmml-related
files to "stmml-1.1". To import stmml.xsd into one of your EML 2.1.0
documents use the XML namespace declaration for STMML in the code below:

```xml
    <?xml version="1.0"?>
    <eml:eml
     packageId="eml.1.1" system="knb"
     xmlns:eml="eml://ecoinformatics.org/eml-2.1.0"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xmlns:stmml="http://www.xml-cml.org/schema/stmml-1.1"
     xsi:schemaLocation="eml://ecoinformatics.org/eml-2.1.0 eml.xsd">
     <dataset>
        ...
     </dataset>
     <additionalMetadata>
      <metadata>
       <stmml:unitList xmlns:stmml="http://www.xml-cml.org/schema/stmml-1.1"
        xsi:schemaLocation="http://www.xml-cml.org/schema/stmml-1.1 stmml.xsd">
        <stmml:unit name="gramsPerSquareMeter" 
                    unitType="arealMassDensity" 
                    id="gramsPerSquareMeter" 
                    parentSI="kilogramsPerSquareMeter" 
                    multiplierToSI=".001">          
         ..
       </stmml:unitList>
      </metadata>
     </additionalMetadata>
    </eml:eml>
```            

**Q:** Location of Access Control Trees

**A:** In EML 2.0.1 an &lt;access&gt; tree could be included in each
top-level module (i.e. dataset, citation, software, or protocol) to
control access to the entire metadata document. Additionally, to control
access to individual entities, some authors put &lt;access&gt; trees in
&lt;additionalMetadata&gt; sections and used &lt;describes&gt; elements
to reference their &lt;distribution&gt; nodes. Authors may have inferred
that access control could be applied to any node with this practice.
However, node-level access control is problematic to implement, and in
practice only access trees that reference distribution nodes are
recognized (as was stated in EML 2.0.1 documentation). A better solution
is to locate &lt;access&gt; nodes above or near the node to which the
access rules should be applied. This feature has been added to EML
2.1.0.

In EML 2.1.0, access trees can be placed in 2 locations. To control the
entire metadata document (i.e., "document-level access"), an
&lt;access&gt; tree should be placed as a child of the root element
([EML image](eml.png)). If a metadata author wishes to override the
document-level control for a specific entity, an additional access tree
may be placed as the last child of a &lt;distribution&gt; element within
the &lt;physical&gt; tree of that entity ([Physical Distribution Type
image](eml-physicalDistribution.png)). The structure of the access
module itself has not changed ([access module
documentation](eml-access.html) ).

Example 1. To control access to all the metadata and by default to the
data, use an &lt;access&gt; element at the top level:

```xml
    <eml:eml>
     <access>
      ...
     </access>
     <dataset>
       ...
     </dataset>
     <additionalMetadata>
       ...
     </additionalMetadata>
    </eml:eml>  
```            

Example 2. Access rules can still be specified for any data entity by
placing an access tree under that entity's physical/distribution
element. The following example illustrates how a dataTable's access tree
can be used to override permissions set at the document level. If no
access is specified in distribution then the document-level access rules
are applied.

```xml
    <eml:eml>
     <dataset>
      ...
     <dataTable>
       ...
      <physical>
        ...
       <distribution>
        ...
        <access>
         ...
        </access>
       </distribution>
      </physical>
       ...
      </dataTable>
     </dataset>
    </eml:eml>
```

**Q:** Typing of &lt;gRing&gt; corrected

**A:** The content of the &lt;gRing&gt; element was retyped to make
these nodes more usable. This element is generally analogous to the FGDC
component for ring. This element should now contain a string comprised
of a comma-delimited sequence of longitude and latitude values for
vertex coordinates (in decimal degrees), as in the example below. For
more information, see the normative documents for gRing in the [coverage
module](eml-coverage.html#gRing).

```xml
    ..
     <gRing>-119.453,35.0 -125,37.5555 -122,40 -119.453,35.0 </gRing>
    ..
```

**Q:** Entity Attributes: &lt;bounds&gt; minimum and maximum are of type
xs:float

**A:** In EML 2.0.1, &lt;bounds&gt; elements were typed as xs:decimal
and did not support scientific notation. The base data type was changed
to 'xs:float' in EML 2.1.0 to accommodate both decimal and scientific
notation while maintaining backward compatibility. Authors should keep
in mind that there are still advantages to using decimal numbers for
bounds, because the decimal data type maintains precision during storage
while the floating point type does not. An alternative type,
"precisionDecimal" (corresponding to the IEEE type "floating-point
decimal”), may be available in the next version of XML Schema (i.e.,
v1.1, a working draft as of late 2008). It combines features of both the
decimal and float types in that it supports the values and notation of a
float, but is treated as decimal in arithmetic and storage. The typing
of this element may be changed to this new type in a future release of
EML. For more information, see the normative documentation for
[NumericDomainType](eml-attribute.html#NumericDomainType).

In EML 2.1.0, bounds can be written as:

```xml
    <attribute>
     ...
     <numericDomain>
      <numberType>real</numberType>
      <bounds>
       <minimum>0</minimum>
       <maximum>1.234E15</maximum>
     </bounds>
    </numericDomain>
    </attribute>
```

**Q:** Geographic Coverage: &lt;altitudeUnits&gt; use Standard Units of
LengthType

**A:** In EML 2.0.0 and 2.0.1, altitude units were typed as xs:string,
and EML authors were instructed to include a vertical datum along with
the unit. In EML 2.1.0 this has been revised. Altitudes are now
restricted to lengths in Standard Units (e.g. meter, foot, etc), and the
datum should be included as part of the textual geographicDescription
element. Document authors should note that including any additional
content in the &lt;altitudeUnits&gt; element other than a length value,
such as the datum, is not valid in EML 2.1.0. For a list of allowable
units, see [the normative description for
&lt;altitudeUnits&gt;](eml-coverage.html#altitudeUnits).

```xml
    ..
    <boundingCoordinates>
     ...
     <boundingAltitudes>
      <altitudeMinimum>0</altitudeMinimum>
      <altitudeMaximum>120</altitudeMaximum>
      <altitudeUnits>meter</altitudeUnits>
     </boundingAltitudes>
    </boundingCoordinates>
    ..
```

**Q:** Geographic Coverage: Latitude and Longitude are type xs:decimal,
with appropriate ranges

**A:** In EML 2.0.1, latitude and longitude values in
&lt;geographicCoverage&gt; elements were typed as a xs:string. In EML
2.1.0 these values are restricted to decimal numbers with realistic
ranges (-90 to 90, and -180 to 180, respectively). Fractions of a degree
in minutes and seconds should be converted to decimal format, and
strings denoting direction or hemisphere (e.g., 'S' or 'south') are not
allowed. South latitudes and west longitudes must be indicated by a
minus sign (-) in front of the coordinate, as in the example below.
These constraints are consistent with the intended use of this field,
which is to support mapping the general geographic coverage of EML
resources. Authors should keep in mind that very specific descriptions
of spatial data can be accommodated by EML modules dedicated to that
purpose. More information on bounding coordinates can be found in the
[normative technical documents](eml-coverage.html#boundingCoordinates).

```xml
    ..
    <boundingCoordinates>
     <westBoundingCoordinate>-120.2534</westBoundingCoordinate>
     <eastBoundingCoordinate>-119.7550</eastBoundingCoordinate>
     <northBoundingCoordinate>34.2231</northBoundingCoordinate>
     <southBoundingCoordinate>34.1231</southBoundingCoordinate>
    </boundingCoordinates>
    ..
```

**Q:** Element content must be non-empty

**A:** In EML 2.0.1, elements of the string data type were allowed to be
empty or contain only whitespace. This feature was occasionally
exploited as a work-around to force incomplete documents to validate in
XML editors and the Metacat harvester, but this practice may cause
problems in document parsing or for EML tools such as Kepler. In EML
2.1.0, string content is now typed as "NonEmptyString" and string
entities are required to have minimal non-whitespace content. So,
whereas the following content would have been allowed in EML 2.0.1:

```xml
    ...
      <mediumName> </mediumName>
    ...

    or 

    ...
      <attributeName/>
    ...
```

In EML 2.1.0, empty (or whitespace) content is not allowed. Actual
content must be provided.

```xml
    ...
      <attributeName>approx. temperature</attributeName>
    ...
```

**Q:** An offline resource has a minimum of one element required
(&lt;mediumName&gt;)

**A:** In EML 2.0.1, an author could describe an offline data resource,
but include no information about the resource's distribution. In
EML-2.1.0, minimal content (one element) is now required.

In EML 2.0.1, the distribution tree for an offline resource could have
ended with no content:

```xml
    ...
    <distribution>
     <offline/>
    </distribution>
    ...
```

In EML 2.1.0, the element &lt;mediumName&gt; is required:

```xml
    ...
    <distribution>
     <offline>
      <mediumName>Atlas of Lake Erie Shorelines</mediumName>
     </offline>
    </distribution>
    ...
```

**Q:** Methods elements are standardized to &lt;methods&gt;

**A:** In EML 2.0.1, both "&lt;method&gt;" and "&lt;methods&gt;"
elements were included in the schema, which caused confusion for some
authors. In EML 2.1.0, instances of the MethodsType have been
standardized to "methods".

In EML 2.0.1, this path existed:

```
    ...
    /eml/dataset/dataTable/attribute/method/
    ...
```

In EML 2.1.0, this path is now properly constructed as:

```
    ...
    /eml/dataset/dataTable/attribute/methods/
    ...
```

**Q:** Elements for date-time have been standardized to &lt;dateTime&gt;

**A:** In EML 2.0.1, both "&lt;datetime&gt;" and "&lt;dateTime&gt;"
elements were included, which caused confusion for some authors. In EML
2.1.0, these instances have been standardized to "dateTime".

In EML 2.0.1, this path existed:

```
    ...
    /eml/dataset/dataTable/attribute/measurementScale/datetime/
    ...
```

In EML 2.1.0, this path is now properly constructed as:

```
    ...
    /eml/dataset/dataTable/attribute/measurementScale/dateTime/
    ...
```

**Q:** For journal articles, the elements &lt;volume&gt; and
&lt;pageRange&gt; are now optional

**A:** Two elements describing journal articles in the literature schema
(eml-literature.xsd), &lt;volume&gt; and &lt;pageRange&gt;, are now
optional to permit articles-in-press to be described in EML.

**Q:** A Citation may have an optional &lt;contact&gt; tree

**A:** Also in eml-literature.xsd, an optional &lt;contact&gt; tree has
been added to permit a contact to be designated for a publication. For
example, a contact could be provided for reprint requests.

**Q:** New optional element (&lt;onlineDescription&gt;) for a
description of an online resource

**A:** A new element, &lt;onlineDescription&gt;, was added to support
providing a brief description of the content of an online element's
child. This optional element is available for both resource-level and
physical-level distribution nodes, and is typed as a NonEmptyString. One
possible use for the description is to provide optional content for the
HTML anchor element that accompanies a URL.

Converting EML documents from v2.0.0/1 to v2.1.0
================================================

**Q:** About the EML conversion stylesheet

**A:** An XSL stylesheet is provided with the EML Utilities to convert
valid EML 2.0-series documents to EML 2.1.0 (see
<https://knb.ecoinformatics.org/software/eml/>). The stylesheet performs
basic tasks to create a template EML 2.1.0 document (below). For more
information, see the Utilities documentation.

1.  Updates namespaces to eml-2.1.0 and stmml-1.1

2.  Encloses XML markup within &lt;additionalMetadata&gt; sections in
    &lt;metadata&gt; tags

3.  Renames elements whose spelling has changed (&lt;method&gt; and
    &lt;datetime&gt;)

4.  Copies access trees from &lt;additionalMetadata&gt; to other parts
    of the document (for common constructs)

5.  Optionally replaces the content of the "packageId" attribute on the
    root element, &lt;eml:eml&gt;, using a parameter

**Q:** Validity of new EML 2.1.0 documents

**A:** Because of the flexibility allowed in EML, the stylesheet may
encounter EML 2.0.1 structures that cannot be transformed or that may
result in invalid EML 2.1.0 after processing. For example, by design
&lt;additionalMetadata&gt; sections are parsed laxly, and so it is
possible for their content in EML-2.0.0/1 to contain &lt;access&gt;
trees which are invalid. Also, the content of several elements has been
more tightly constrained in EML 2.1.0 (e.g., latitude and longitude),
and data types are not detectable by a stylesheet. Document authors are
advised to check the validity of their new EML 2.1.0 after
transformation. EML instance documents can be validated in these ways:

1.  With the [online EML Parser](https://knb.ecoinformatics.org/emlparser/). The online
    parser will validate all versions of EML.

2.  Using the Parser that comes with EML. To execute it, change into the
    'lib' directory of the EML release and run the 'runEMLParser' script
    passing your EML instance file as a parameter. The script performs
    two actions: it checks the validity of references and id attributes,
    and it validates the document against the EML 2.1 schema. The EML
    parser included with the distribution is capable of checking only
    EML 2.1.0 documents, and *cannot* be used to validate earlier
    versions (e.g., EML 2.0.1).

3.  If you are planning to contribute your EML 2.1.0 document to a
    Metacat repository, note that the Metacat servlet checks all
    versions of incoming EML for validity as part of the insertion
    process.
