## Utility Modules

The following modules are used to highlight the information being
documented in each of the above modules where prose may be needed to
convey the critical metadata. The eml-text module provides a number of
text-based constructs to enhance a document (including sections,
paragraphs, lists, subscript, superscript, emphasis, etc.)

### The eml-text module - Text field formatting

Links:

- [Module Diagram](./images/eml-text.png)
- [Interactive Module Documentation](./schema/eml-text_xsd.html)

The eml-text module is a wrapper container that allows general text
descriptions to be used within the various modules of eml. It can
include either structured or unstructured text blocks. It isn't really
appropriate to use this module outside of the context of a parent
module, because the parent module determines the appropriate context to
which this text description applies. The eml-text module allows one to
provide structure to a text description in order to convey concepts such
as sections (paragraphs), hierarchy (ordered and unordered lists),
emphasis (bold, superscript, subscript), etc. The structured elements can
be specified using a subset of [DocBook](http://www.docbook.org) so the
predefined DocBook stylesheets can be used to style EML fields that
implement this module, or alternatively can be specified using Markdown
text blocks. Combinations of plain text, docbook sections, and markdown
sections can be interleaved in any order, but most people will likely
find the markdown syntax the easiest to use.

Markdown sections, which are new to EML 2.2.0, provide significant new flexibility
in how to format text for human readability. The following example `introduction`
element that exercises a number of the markdown features.

```xml
<introduction>
    <markdown>
        An introduction goes here, with *italics* and **bold** text, and other markdown niceties.
        
        It can include multiple paragraphs. And these paragraphs should have enough text to wrap in a wide browser. So, repeat that last thought. And these paragraphs should have enough text to wrap in a wide browser. So, repeat that last thought.
        
        Text can also cite other works, such as [@jones_2001], in which case the associated key must be present
        as either the citation identifier in a `bibtex` element in the EML document, or as the `id` attribute on
        one of the `citation` elements in the EML document. These identifiers must be unique across the document. Tools
        such as Pandoc will readily convert these citations and citation entries into various formats, including HTML, PDF,
        and others.
        
        And bulleted lists are also supported:
        
        - Science
        - Engineering
        - Math
        
        It can also include equations:
        
        $$\left( x + a \right)^{n} = \sum_{k = 0}^{n}{\left( \frac{n}{k} \right)x^{k}a^{n - k}}$$
        
        Plus, it can include all of the other features of [Github Flavored Markdown (GFM)](https://github.github.com/gfm/).
    </markdown>
</introduction>
```

Because Markdown treats whitespace as significant in formatting, it is important
to consistently embed Markdown text inside of an EML document.  For example,
bulleted lists and other structures within Markdown are dependent on indenting
the raw markdown text. Thus, authors and processors should pay close attention
to formatting within the markdown block.  In particular, if the XML document
within which the markdown block is embedded is in an indented hierarchy using
whitespace, then the first non-whitespace character of the markdown block
defines the column for the leftmost column of the markdown, and all subsequent
markdown should be indented relative to that column.  For example, if the first
character of the markdown is in column 16 of the document, then all subsequent
markdown lines in that block should also start on column 16.  A bulleted list
would start on column 16, and its sublist would be indented four space to column
20.

In the above example, the first line of the `markdown` block is indented by 8
spaces, and so that becomes the default indenting level for the markdown block.
EML processors should first extract the markdown text from the XML document
without normalizing any whitespace, then remove the default leading whitespace
(in this case, 8 space characters), and then pass the resulting text to a
conforming markdown parser that handles Github Flavored Markdown (GFM). The
resulting parse tree can then be used for display in applications.

Further details about handling and authoring markdown are included in the 
definition of the `markdown` element in the eml-text module.

### The eml-semantics module - Semantic annotations for formalized statements about EML components

Links:

- [Module Diagram](./images/eml-semantics.png)
- [Interactive Module Documentation](./schema/eml-semantics_xsd.html)

The eml-semantics module defines types and elements for
annotating other structures within EML with semantically-precise
statements from various controlled vocabularies.  This is
accomplished by associating the global URI for a property and
value with elements from EML, such as an attribute, an entity,
or a dataset. It is used throughout the other EML modules where
detailed semantic information is needed. For example, given an
EML attribute named "tmpair", one might want to indicate
semantically that the attribute is measuring the property
"Temperature" from a sample of the entity "Air", where both of
those terms are defined precisely in controlled vocabularies.
The eml-semantics module defines an 'annotation' element and
associated type that can be used within EML resources (dataset,
software, etc.), EML Entities (dataTable, spatialRaster,
spatialVector, otherEntity), and EML Attributes.  They can also
be applied within the EML additionalMetadata field to label
arbitrary structures within EML, in which case the subject of
the annotation is the element listed in the describes element
within the additionalMetadata field.


### The eml-access module - Access control rules for resources

**DEPRECATED**

- *While eml-access has been part of the standard for many years, use has been 
extremely limited, and most systems seem to omit and ignore the `access` elements 
in the document in favor of using repository-specific mechanisms to control access.
Therefore, EML 2.2.0 deprecates use of the `access` elements in the main body of EML 
documents, with the exception of use within the `additionalMetadata` element.  As this is a 
backwards incompatible change, the elements are still available in EML 2.2.0, but users 
should expect schema changes to occur in a future release that eliminate the use of `access` 
elements outside of `additionalMetadata`.  In addition, because authorization systems 
are system-dependent, the content within any `access` element should be considered 
advisory and may not reflect the actual authorization policies in place at a given point 
in time in a given repository.*

Links:

- [Module Diagram](./images/eml-access.png)
- [Interactive Module Documentation](./schema/eml-access_xsd.html)

The eml-access module describes the level of access that is to be
allowed or denied to a resource for a particular user or group of users,
and can be described independently for metadata and data. The eml-access
module uses a reference to a particular authentication system to
determine the set of principals (users or groups) that can be specified
in the access rules. The special principal \'public\' can be used to
indicate that any user or group has access permission, thereby making it
easier to specify that anonymous access is allowed.

There are two mechanisms for including access control via the eml-access
module:

1.  The top-level \"eml\" element may have an optional \<access\>
    element that is used to establish the default access control for the
    entire EML package. If this access element is omitted from the
    document, then the package submitter should be given full access to
    the package but all other users should be denied all access. To
    allow the package to be publicly viewable, the EML author must
    explicitly include a rule stating so. Barring the existence of a
    distribution-level \<access\> element (see below), access to data
    entities will be controlled by the package-level \<access\> element
    in the \<eml\> element.

2.  Exceptions for particular entity-level components of the package can
    be controlled at a finer grain by using an access description in
    that entity\'s physical/distribution tree. When access control rules
    are specified at this level, they apply only to the data in the
    parent distribution element, and not to the metadata. Thus, it will
    control access to the content of the \<inline\> element, as well as
    resources that are referenced by the \<online/url\> and
    \<online/connection\> paths. These exceptions to access for
    particular data resources are applied after the default access rules
    at the package-level have been applied, so they effectively override
    the default rules when they overlap.

In previous versions of EML access rules for entity-level distribution
were contained in \<additionalMetadata\> sections and referenced via the
\<describes\> tag. Although in theory these could have referenced any
node, in application such node-level access control is problematic.
Since the most common uses of access control rules were to limit access
to specific data entities, the access tree has been placed there
explicitly in EML 2.1.0.

Access is specified with a choice of child elements, either \<allow\> or
\<deny\>. Within these rules, values can be assigned for each
\<principal\> using the \<permission\> element. Users given \"read\"
permission can view the resource; \"write\" allows changes to the
resource excluding changes to the access rules; \"changePermission\"
includes \"write\" plus the changing of access rules. Users allowed
\"all\" permissions; may do all of the above. Access to data and
metadata is affected by the order attribute of the \<access\> element.
It is possible for a deny rule to override an allow rule, and vice
versa. In the case where the order attribute is set to \"allowFirst\",
and there are rules similar to the following (with non-critical sections
deleted):

```xml
      <deny>
        <principal>public</principal>
        <permission>read</permission>
      </deny>
      <allow>
        <principal>uid=alice,o=NASA,dc=ecoinformatics,dc=org</principal>
        <permission>read</permission>
      </allow>
```

the principal \"uid=alice \...\" will be denied access, because it is a
member of the special \"public\" principal, and the deny rule is
processed second. For this allow rule to truly allow access to that
principal, the order attribute should be set to \"denyFirst\", and the
allow rule will override the deny rule when it is processed second.

An example is given below, with non-critical sections deleted:
```xml
      <eml>
          <access
              authSystem="ldap://ldap.ecoinformatics.org:389/dc=ecoinformatics,dc=org"
              order="allowFirst">
            <allow>
              <principal>uid=alice,o=NASA,dc=ecoinformatics,dc=org</principal>
              <permission>read</permission>
              <permission>write</permission>
            <allow>
          </access>
          <dataset>
          ...
          ...
          <dataTable id="entity123">
          ...
            <physical>
            ...
              <distribution>
              ...
                <access id="access123"
                authSystem="ldap://ldap.ecoinformatics.org:389/dc=ecoinformatics,dc=org"
                order="allowFirst">
                  <deny>
                    <principal>uid=alice,o=NASA,dc=ecoinformatics,dc=org</principal>
                    <permission>write</permission>
                </deny>
              </access>
             </distribution>
           </physical>
          </dataTable>
          <dataTable id="entity234">
            ...
            <physical>
            ...
              <distribution>
                ...
                <access>
                  <references>access123</references>
                </access>
              </distribution>
            </physical>
          </dataTable>
          ...    
        </dataset>
      <eml>
```

In this example, the overall default access is to allow the user=alice
(but no one else) to read and write all metadata and data. However,
under \"entity123\" and \"entity234\", there is an additional rule
saying that user=alice does not have write permission. The net effect is
that Alice can read and make changes to the metadata, but cannot make
changes to the two data entities. In addition, Alice cannot change these
access rules; although the submitter can.

This example also shows how the eml-access module, like other modules,
may be \"referenced\" via the \<references\> tag. This allows an access
control document to be described once, and then used as a reference in
other locations within the EML document via its ID.

In summary, access rules can be applied in two places in an eml
document. Default access rules are established in the top \<access\>
element for the main eml document (e.g., \"/eml/access\"). These default
rules can be overridden for particular data entities by adding
additional \<access\> elements in the physical/distribution trees of
those entities.
