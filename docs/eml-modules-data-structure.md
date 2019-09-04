## Data Structure Modules

The following three modules are used to document the logical layout of a
dataset. Many datasets are comprised of multiple entities (e.g. a series
of tabular data files, or a set of GIS features, or a number of tables
in a relational database). Each entity within a dataset may contain one
or more attributes (e.g. multiple columns in a data file, multiple
attributes of a GIS feature, or multiple columns of a database table).
Lastly, there may be both simple or complex relationships among the
entities within a dataset. The relationships, or the constraints that
are to be enforced in the dataset, are described using the
eml-constraint module. All entities share a common set of information
(described using eml-entity), but some discipline specific entities have
characteristics that are unique to that entity type. Therefore, the
eml-entity module is extended for each of these types (dataTable,
spatialRaster, spatialVector, etc\...) which are described in the next
section.

### The eml-entity module - Entity level information within datasets

Links:

- [Module Diagram](./images/eml-entity.png)
- [Interactive Module Documentation](./schema/eml-entity_xsd.html)

The eml-entity module defines the logical characteristics of each entity
in the dataset. Entities are usually tables of data (eml-dataTable).
Data tables may be ascii text files, relational database tables,
spreadsheets or other type of tabular data with a fixed logical
structure. Related to data tables are views (eml-view) and stored
procedures (eml-storedProcedure). Views and stored procedures are
produced by an RDBMS or related system. Other types of data such as:
raster (eml-spatialRaster), vector (eml-spatialVector) or
spatialReference image data are also data entities. An otherEntity
element would be used to describe types of entities that are not
described by any other entity type. Each of these entity type uses the eml-entity
module elements as it's base set of elements, but then extends the base
with entity-specific elements. Note that the eml-spatialReference module
is not an entity type, but is rather a common set of elements used to
describe spatial reference systems in both eml-spatialRaster and
eml-spatialVector. It is described here in relation to those two
modules.

The eml-entity module, like other modules, may be "referenced" via the
`<references>` tag. This allows an entity document to be described once,
and then used as a reference in other locations within the EML document
via its ID.

### The eml-attribute module - Attribute level information within dataset entities

Links:

- [Module Diagram](./images/eml-attribute.png)
- [Interactive Module Documentation](./schema/eml-attribute_xsd.html)

The eml-attribute module describes all attributes (variables) in a data
entity: dataTable, spatialRaster, spatialVector, storedProcedure, view
or otherEntity. The description includes the name and definition of each
attribute, its domain, definitions of coded values, definitions of missing
values, and other pertinent
information. Two structures exist in this module: 1. `attribute` is used
to define a single attribute; 2. `attributeList` is used to define a list
of attributes that go together in some logical way.

The eml-attribute module, like other modules, may be \"referenced\" via
the \<references\> tag. This allows an attribute document to be
described once, and then used as a reference in other locations within
the EML document via its ID.

#### Philosophy of Attribute Units

The concept of \"unit\" represents one of the most fundamental
categories of metadata. The classic example of data entropy is the case
in which a reported numeric value loses meaning due to lack of
associated units. Much of ecology is driven by measurement, and most
measurements are inherently comparative. Good data description requires
a representation of the basis for comparison, i.e., the unit. In
modeling the attribute element, the authors of EML drew inspiration from
the [NIST Reference on Constants, Units, and
Uncertainty](http://physics.nist.gov/cuu/Units/introduction.html). This
document defines a unit as \"a particular physical quantity, defined and
adopted by convention, with which other particular quantities of the
same kind are compared to express their value.\" The authors of the EML
2.0 specification (hereafter \"the authors\") decided to make the unit
element required, wherever possible.

Units may also be one of the most problematic categories of metadata.
For instance, there are many candidate attributes that clearly have no
units, such as named places and letter grades. There are other candidate
attributes for which units are difficult to identify, despite some
suspicion that they should exist (e.g. pH, dates, times). In still other
cases, units may be meaningful, but apparently absent due to dimensional
analysis (e.g. grams of carbon per gram of soil). The relationship
between units and dimensions likewise is not completely clear.

The authors decided to sharpen the model of attribute by nesting unit
under measurementScale. Measurement Scale is a data typology, borrowed
from Statistics, that was introduced in the 1940\'s. Under the adopted
model, attributes are classified as nominal, ordinal, interval, and
ratio. Though widely criticized, this classification is well-known and
provides at least first-order utility in EML. For example, nesting unit
under measurementScale allows EML to prevent its meaningless inclusion
for categorical data \-- an approach judged superior to making unit
universally required or universally optional.

The sharpening of the attribute model allowed the elimination of the
unit type \"undefined\" from the standard unit dictionary (see
eml-unitDictionary.xml). It seemed self-defeating to require the unit
element exactly where appropriate, yet still allow its content to be
undefined. An attribute that requires a unit definition is malformed
until one is provided. The unit type \"dimensionless\" is preserved,
however. In EML 2.0, it is synonymous with \"unitless\" and represents
the case in which units cannot be associated with an attribute for some
reason, despite the proper classification of that attribute as interval
or ratio. Dimensionless may itself be an anomaly arising from the
limitations of the adopted measurement scale typology.

Closely related to the concept of unit is the concept of attribute
domain. The authors decided that a well-formed description of an
attribute must include some indication of the set of possible values for
that attribute. The set of possible values is useful, perhaps necessary,
for interpreting any particular observed value. While universally
required, attribute domain has different forms, depending on the
associated measurement scale.

The element storageType has an obvious relationship to domain. It gives
some indication of the range of possible values of an attribute, and
also gives some (potentially critical) operability information about the
way the attribute is represented or construed in the local storage
system. The storageType element seems to fall in a gray area between the
logical and physical aspects of stored data. Neither comfortable with
eliminating it nor with making it required, the authors left it
available but optional under attribute. In addition, it is repeatable so
that different storage types can be provided for various systems (e.g.,
different databases might use different types for columns, even though
the domain of the attribute is the same regardless of which database is
used).

Attributes representing dates, times, or combinations thereof (hereafter
\"dateTime\") were the most difficult to model in EML. Is dateTime of
type interval or ordinal? Does it have units or not? Strong cases can be
made on each side of the issue. The confusion may reflect the
limitations of the measurement scale typology. The final resolution of
the dateTime model is probably somewhat arbitrary. There was clearly a
need, however, to allow for the interoperability of dateTime formats.
EML 2.0 tries to provide an unambiguous mechanism for describing the
format of dateTime values by providing a separate category for date and
time values. This \"dateTime\" measurement scale allows users to
explicitly label attributes that contain Gregorian date and time values,
and allows them to provide the information needed to parse these values
into their appropriate components (e.g., days, months, years).

Representations of both coded values (for nominal and ordinal attributes) 
and missing value codes are critical metadata.  Coded values need to be
defined in metadata for proper interpretation.  EML provides the `enumeratedDomain`
field for explicitly listing codes and their definitions, or through elements
to reference external codesets and codes that are defined in other tables in
a dataset.  Missing values can also be coded and defined in the 
`missingValueCode` element.  Providing an explicit indication of 
"missingness" as a coded value that has an explicit
interpretation eliminates the guesswork that happens when missing values are
expressed simply as empty cells with NULL values and no explanation.  While it
might seem simple to provide an emty cell in a csv file, the interpreation of
that value is left ambiguous. Researchers would be better served to define explicit
missing value codes, e.g., one for 'Data not collected' and another for
'Data corrupted during network transfer'.

### The eml-constraint module - Relationships among and within dataset entities

Links:

- [Module Diagram](./images/eml-constraint.png)
- [Interactive Module Documentation](./schema/eml-constraint_xsd.html)

The eml-constraint schema defines the integrity constraints between
entities (e.g., data tables) as they would be maintained in a relational
management system. These constraints include primary key constraints,
foreign key constraints, unique key constraints, check constraints, and
not null constraints, among potential others.

### The eml-dataTable module - Logical information about data table entities

Links:

- [Module Diagram](./images/eml-dataTable.png)
- [Interactive Module Documentation](./schema/eml-dataTable_xsd.html)

The eml-dataTable module is used to describe the logical characteristics
of each tabular set of information in a dataset. A series of
comma-separated text files may be considered a dataset, and each file
would subsequently be considered a dataTable entity within the dataset.
Since the eml-dataTable module extends the eml-entity module, it uses
all of the common entity elements to describe the table, along with a
few elements specific to just data table entities. The eml-dataTable
module allows for the description of each attribute
(column/field/variable) within the data table through the use of the
eml-attribute module. Likewise, there are fields used to describe the
physical distribution of the data table, its overall coverage, the
methodology used in creating the data, and other logical structure
information such as its orientation, case sensitivity, etc.

### The eml-spatialRaster module - Logical information about regularly gridded geospatial image data

Links:

- [Module Diagram](./images/eml-spatialRaster.png)
- [Interactive Module Documentation](./schema/eml-spatialRaster_xsd.html)

The eml-spatialRaster module allows for the description of entities
composed of rectangular grids of data values that are usually
georeferenced to a portion of the earth\'s surface. Specific attributes
of a spatial raster can be documented here including the spatial
organization of the raster cells, the cell data values, and if derived
via imaging sensors, characteristics about the image and its individual
bands.

### The eml-spatialVector module - Logical information about non-gridded geospatial image data

Links:

- [Module Diagram](./images/eml-spatialVector.png)
- [Interactive Module Documentation](./schema/eml-spatialVector_xsd.html)

The eml-spatialVector module allows for the description of spatial
objects in a GIS system that are not defined in a regularly gridded
pattern. These geometries include points and vectors and the
relationships among them. Specific attributes of a spatial vector can be
documented here including the vector\'s geometry type, count and
topology level.

### Schema for validating spatial referencing descriptions

This module defines both projected and unprojected coordinate systems
for referencing the spatial coordinates of a dataset to the earth. The
schema is based on that used by Environmental Systems Research Inc
(ESRI) for its .prj file format. EML provides a library of pre-defined
coordinate systems that may be referred to by name in the
horizCoordSysName element. A custom projection may be defined using this
schema for any projection that does not appear in this dictionary.

### The eml-storedProcedure module - Data tables resulting from procedures stored in a database

Links:

- [Module Diagram](./images/eml-storedProcedure.png)
- [Interactive Module Documentation](./schema/eml-storedProcedure_xsd.html)

The storedProcedure module is meant to capture information on procedures
that produce data output in the form of a data table. In an RDBMS one
can code complex queries and transactions into stored procedures and
then invoke them directly from front-end applications. It allows the
optional description of any parameters that are expected to be passed to
the procedure when it is called.

### The eml-view module - Data tables resulting from a database query

Links:

- [Module Diagram](./images/eml-view.png)
- [Interactive Module Documentation](./schema/eml-view_xsd.html)

The eml-view module describes a view from a database management system.
A view is a query statement that is stored as a database object and
executed each time the view is called.

