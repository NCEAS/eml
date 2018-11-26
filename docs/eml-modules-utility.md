# Utility Modules

The following modules are used to highlight the information being
documented in each of the above modules where prose may be needed to
convey the critical metadata. The eml-text module provides a number of
text-based constructs to enhance a document (including sections,
paragraphs, lists, subscript, superscript, emphasis, etc.)

## The eml-text module - Text field formatting

The eml-text module is a wrapper container that allows general text
descriptions to be used within the various modules of eml. It can
include either structured or unstructured text blocks. It isn\'t really
appropriate to use this module outside of the context of a parent
module, because the parent module determines the appropriate context to
which this text description applies. The eml-text module allows one to
provide structure to a text description in order to convey concepts such
as sections (paragraphs), hierarchy (ordered and unordered lists),
emphasis (bold, superscript, subscript) etc. The structured elements can
be specified using a subset of [DocBook](http://www.docbook.org) so the
predefined DocBook stylesheets can be used to style EML fields that
implement this module, or alternatively can be specified using Markdown
text blocks. Combinations of plain text, docbook sections, and markdown
sections can be interleaved in any order, but most people will likely
find the markdown syntax the easiest to use.

## The eml-semantics module - Semantic annotations for formalized statements about EML components
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
