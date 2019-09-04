## Discovery and Interpretation Modules

The following five modules are used to qualify the resources being
described in more detail. They are used to describe the 
distribution of the metadata and data themselves, parties
associated with the resource, the geographic, temporal, and taxonomic
extents of the resource, the overall research context of the resource,
and detailed methodology used for creating the resource. Some of these
modules are imported directly into the top-level resource modules, often
in many locations in order to limit the scope of the description. For
instance, the eml-coverage module may be used for a particular column of
a dataset, rather than the entire dataset as a whole.


### The eml-physical module - Physical file format

Links:

- [Module Diagram](./images/eml-physical.png)
- [Interactive Module Documentation](./schema/eml-physical_xsd.html)

The eml-physical module describes the external and internal physical
characteristics of a data object as well as the information required for
its distribution. Examples of the external physical characteristics of a
data object would be the filename, size, compression, encoding methods,
and authentication of a file or byte stream. Internal physical
characteristics describe the format of the data object being described.
Both named binary or otherwise proprietary formats can be cited (e.g.,
Microsoft Access 2000), or text formats can be precisely described
(e.g., ASCII text delimited with commas). For these text formats, it
also includes the information needed to parse the data object to extract
the entity and its attributes from the data object. Distribution
information describes how to retrieve the data object. The retrieval
information can be either online (e.g., a URL or other connection
information) or offline (e.g., a data object residing on an archival
tape).

The eml-physical module, like other modules, may be \"referenced\" via
the \<references\> tag. This allows a physical document to be described
once, and then used as a reference in other locations within the EML
document via its ID.

### The eml-party module - People and organization information

Links:

- [Module Diagram](./images/eml-party.png)
- [Interactive Module Documentation](./schema/eml-party_xsd.html)

The eml-party module describes a responsible party and is typically used
to name the creator of a resource or metadata document. A responsible
party may be an individual person, an organization or a named position
within an organization. The eml-party module contains detailed contact
information. It is used throughout the other EML modules where detailed
contact information is needed.

The eml-party module, like other modules, may be \"referenced\" via the
\<references\> tag. This allows a party to be described once, and then
used as a reference in other locations within the EML document via its
ID.

### The eml-coverage module - Geographic, temporal, and taxonomic extents of resources

Links:

- [Module Diagram](./images/eml-coverage.png)
- [Interactive Module Documentation](./schema/eml-coverage_xsd.html)

The eml-coverage module contains fields for describing the coverage of a
resource in terms of time, space, and taxonomy. These coverages
(temporal, spatial, and taxonomic) represent the extent of applicability
of the resource in those domains. The Geographic coverage section allows
for 2 means of expressing coverage on the surface of the earth: 1) via a
set of bounding coordinates that define the North, South, East and West
points in a rectangular area, optionally including a bounding altitude,
and 2) using a G-Ring polygon definition, where an irregularly shaped
area may be defined using a ordered list of latitude/longitude
coordinates. A G-Ring may also include an \"inner G-Ring\" that defines
one or more \"cut-outs\" in the area, i.e. the donut hole concept.

The temporal coverage section allows for the definition of either a
single date or time, or a range of dates or times. These may be
expressed as a calendar date according to the ISO 8601 Date and Time
Specification, or by using an alternate time scale, such as the geologic
time scale. Currently, EML does not have specific fields to indicate
that a data resource may be \"ongoing.\" Two examples are data tables
that are planned to be appended in the future, or resources with complex
connection definitions (such as to a database) which may return data in
real time. It is important that EML be able to handle data from both the
\"producer\" and \"consumer\" points of view, although currently the
temporal coverage modules are designed for the latter. There is no
universally acceptable recommendation for describing \"ongoing\" data
within EML. Some groups have chosen to use the \<alternateTimeScale\>
node for the end date, with a value of \"ongoing,\" although this
practice is not endorsed by the EML authors. A better solution could be
to use very general content for the endDate (such as only the current
year) so that the data are accurately described, and searches return
datasets as expected. A future version of EML will accommodate such data
types with coverage elements specific to their needs.

The taxonomic coverage section allows for detailed description of the
taxonomic extent of the dataset or resource. The taxonomic
classification consists of a recursive set of taxon rank names, their
values, and their common names. This construct allows for a taxonomic
hierarchy to be built to show the level of identification (e.g. Rank
Name = Kingdom, Rank Value = Animalia, Common Name = Animals, and so on
down the hierarchy.) The taxonomic coverage module also allows for the
definition of the classification system in cases where alternative
systems are used.

The eml-coverage module, like other modules, may be \"referenced\" via
the \<references\> tag. This allows the coverage extent to be described
once, and then used as a reference in other locations within the EML
document via its ID.

### The eml-project module - Research context information for resources

Links:

- [Module Diagram](./images/eml-project.png)
- [Interactive Module Documentation](./schema/eml-project_xsd.html)

The eml-project module describes the research context in which the
dataset was created, including descriptions of over-all motivations and
goals, funding, personnel, description of the study area etc. This is
also the module to describe the design of the project: the scientific
questions being asked, the architecture of the design, etc. This module
is used to place the dataset that is being documented into its larger
research context.

The eml-project module, like other modules, may be \"referenced\" via
the \<references\> tag. This allows a research project to be described
once, and then used as a reference in other locations within the EML
document via its ID.

### The eml-methods module - Methodological information for resources

Links:

- [Module Diagram](./images/eml-methods.png)
- [Interactive Module Documentation](./schema/eml-methods_xsd.html)

The eml-methods module describes the methods followed in the creation of
the dataset, including description of field, laboratory and processing
steps, sampling methods and units, quality control procedures. The
eml-methods module is used to describe the [*actual*]{.emphasis}
procedures that are used in the creation or the subsequent processing of
a dataset. Likewise, eml-methods is used to describe processes that have
been used to define / improve the quality of a data file, or to identify
potential problems with the data file. Note that the eml-protocol module
is intended to be used to document a [*prescribed*]{.emphasis}
procedure, whereas the eml-method module is used to describe procedures
that [*were actually performed*]{.emphasis}. The distinction is that the
use of the term \"protocol\" is used in the \"prescriptive\" sense, and
the term \"method\" is used in the \"descriptive\" sense. This
distinction allows managers to build a protocol library of well-known,
established protocols (procedures), but also document what procedure was
truly performed in relation to the established protocol. The method may
have diverged from the protocol purposefully, or perhaps incidentally,
but the procedural lineage is still preserved and understandable.
