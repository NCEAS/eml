## The EML Module and Resources

### The eml module - A metadata container

Links:

- [Module Diagram](./images/eml.png)
- [Interactive Module Documentation](./schema/eml_xsd.html)

The eml module is a wrapper container that allows the inclusion of any
metadata content in a single EML document. The eml module is used as a
container to hold structured descriptions of ecological resources. In
EML, the definition of a resource comes from the [*The Dublin Core
Metadata Initiative*](http://dublincore.org/documents/usageguide/),
which describes a general element set used to describe \"networked
digital resources\". The top-level structure of EML has been designed to
be compatible with the Dublin Core syntax. In general, dataset
resources, literature resources, software resources, and protocol
resources comprise the list of information that may be described in EML.
EML is largely designed to describe digital resources, however, it may
also be used to describe non-digital resources such as paper maps and
other non-digital media. [ *In EML, the definition of a \"Data Package\"
is the combination of both the data and metadata for a resource.*
]{.emphasis} So, data packages are built by using the \<eml\> wrapper,
which will include all of the metadata, and optionally the data (or
references to them). All EML packages must begin with the \<eml\> tag
and end with the \</eml\> tag.

The eml module may be extended to describe other resources by means of
its optional sub-field, \<additionalMetadata\>. This field is largely
reserved for the inclusion of metadata that may be highly discipline
specific and not covered in this version of EML, or it may be used to
internally extend fields within the EML standard.

### The eml-resource module - Base information for all resources

Links:

- [Module Diagram](./images/eml-resource.png)
- [Interactive Module Documentation](./schema/eml-resource_xsd.html)

The eml-resource module contains general information that describes
dataset resources, literature resources, protocol resources, and
software resources. Each of the above four types of resources share a
common set of information, but also have information that is unique to
that particular resource type. Each resource type uses the eml-resource
module to document the information common to all resources, but then
extend eml-resource with modules that are specific to that particular
resource type. For instance, all resources have creators, titles, and
perhaps keywords, but only the dataset resource would have a \"data
table\" within it. Likewise, a literature resource may have an \"ISBN\"
number associated with it, whereas the other resource types would not.

The eml-resource module is exclusively used by other modules, and is
therefore not a stand-alone module. The following four modules are used 
to describe separate resources:
datasets, literature, software, and protocols. However, note that the
dataset module makes use of the other top-level modules by importing
them at different levels. For instance, a dataset may have been produced
using a particular protocol, and that protocol may come from a protocol
document in a library of protocols. Likewise, citations are used
throughout the top-level resource modules by importing the literature
module.

### The eml-dataset module - Dataset specific information

Links:

- [Module Diagram](./images/eml-dataset.png)
- [Interactive Module Documentation](./schema/eml-dataset_xsd.html)

The eml-dataset module contains general information that describes
dataset resources. It is intended to provide overview information about
the dataset: broad information such as the title, abstract, keywords,
contacts, maintenance history, purpose, and distribution of the data
themselves. The eml-dataset module also imports many other modules that
are used to describe the dataset in fine detail. Specifically, it uses
the eml-methods module to describe methodology used in collecting or
processing the dataset, the eml-project module to describe the
overarching research context and experimental design, the eml-access
module to define access control rules for the data and metadata, and the
eml-entity module to provide detailed information about the logical
structure of the dataset. A dataset can be (and often is) composed of a
series of data entities (tables) that are linked together by particular
integrity constraints.

The eml-dataset module, like other modules, may be \"referenced\" via
the \<references\> tag. This allows a dataset to be described once, and
then used as a reference in other locations within the EML document via
its ID.

### The eml-literature module - Citation-specific information

Links:

- [Module Diagram](./images/eml-literature.png)
- [Interactive Module Documentation](./schema/eml-literature_xsd.html)

The eml-literature module contains information that describes literature resources. It is intended to provide overview information about the literature citation, including title, abstract, keywords, and contacts. Citation types follow the conventions laid out by [EndNote](http://www.endnote.com), and there is an attempt to represent a compatible subset of the EndNote citation types. These citation types include: article, book, chapter, edited book, manuscript, report, thesis, conference proceedings, personal communication, map, generic, audio visual, and presentation. The generic citation type would be used when one of the other types will not work.

There are three unique CitationType elements that may be employed within a eml-dataset module, including the \<literatureCited\>, \<usageCitation\>, and \<referencePublication\> elements. The purpose and examples of each CitationType element type are detailed below. 

Similar to other eml modules, each of the CitationType elements may be referenced via the \<references\> tag. The \<references\> tag allows a citation to be described once then used as a reference in other locations within the EML document via its reference ID.

As of EML 2.2.0, each CitationType element can use the \<bibtex\> element as an alternative to encoding citations in the EML XML structures. BibTeX entries generally play well inside of XML structures, but XML escaping is still needed for special characters so consider embedding BibTeX entries in CDATA blocks if XML escaping is cumbersome.


#### eml-literature module - literature cited

Citations to articles or other resources that are referenced in the data set or its associated metadata should be included in a \<literatureCited\> element. \<literatureCited\> is a CitationListType cataloging one or more citations that represent a bibliography of works related to the data set for reference, comparison, or other purposes. These citations can be a series of \<citation\> elements, a \<bibtex\> element featuring one or more BibTeX-style citations, or a mix of the two types. 

*Example of the \<literatureCited\> element:*

```xml
<dataset>
  ...
  <literatureCited>
     <citation>
        <bibtex>
           @article{fegraus_2005,
              title = {Maximizing the {Value} of {Ecological} {Data} with {Structured} {Metadata}: {An} {Introduction} to {Ecological} {Metadata} {Language} ({EML}) and {Principles} for {Metadata} {Creation}},
              journal = {Bulletin of the Ecological Society of America},
              author = {Fegraus, Eric H. and Andelman, Sandy and Jones, Matthew B. and Schildhauer, Mark},
              year = {2005},
              pages = {158--168}
           }
        </bibtex>
     </citation>
     <citation>
        <title>Title of a paper that this dataset, or its metadata, references.</title>
        <creator>
           <individualName>
              <givenName>Mark</givenName>
              <surName>Jarkady</surName>
           </individualName>
        </creator>
        <pubDate>2017</pubDate>
        <article>
           <journal>EcoSphere</journal>
           <publicationPlace>https://doi.org/10.1002/ecs2.2166</publicationPlace>
        </article>
     </citation>
     <bibtex>
     @article{hampton\_2017,
        title = {Skills and {Knowledge} for {Data}-{Intensive} {Environmental} {Research}},
        volume = {67},
        copyright = {All rights reserved},
        issn = {0006-3568, 1525-3244},
        url = {https://academic.oup.com/bioscience/article-lookup/doi/10.1093/biosci/bix025},
        doi = {10.1093/biosci/bix025},
        language = {en},
        number = {6},
        urldate = {2018-02-15},
        journal = {BioScience},
        author = {Hampton, Stephanie E. and Jones, Matthew B. and Wasser, Leah A. and Schildhauer, Mark P. and Supp, Sarah R. and Brun, Julien and Hernandez, Rebecca R. and Boettiger, Carl and Collins, Scott L. and Gross, Louis J. and Fernández, Denny S. and Budden, Amber and White, Ethan P. and Teal, Tracy K. and Labou, Stephanie G. and Aukema, Juliann E.},
        month = jun,
        year = {2017},
        pages = {546--557}
     }

     @article{collins\_2018,
        title = {Temporal heterogeneity increases with spatial heterogeneity in ecological communities},
        volume = {99},
        copyright = {All rights reserved},
        issn = {00129658},
        url = {http://doi.wiley.com/10.1002/ecy.2154},
        doi = {10.1002/ecy.2154},
        language = {en},
        number = {4},
        urldate = {2018-04-16},
        journal = {Ecology},
        author = {Collins, Scott L. and Avolio, Meghan L. and Gries, Corinna and Hallett, Lauren M. and Koerner, Sally E. and La Pierre, Kimberly J. and Rypel, Andrew L. and Sokol, Eric R. and Fey, Samuel B. and Flynn, Dan F. B. and Jones, Sydney K. and Ladwig, Laura M. and Ripplinger, Julie and Jones, Matt B.},
        month = apr,
        year = {2018},
        pages = {858--865}
     }
     </bibtex>
  </literatureCited>
  ...
</dataset>
```

#### eml-literature module - usage citation

A citation to an article or other resource in which the data set is used or referenced should be included in a \<usageCitation\> element, a CitationType detailing a literature resource that has used or references this data set. It is not expected that one or more usage citations will necessarily be an exhaustive list of resources that employ the data set, but rather will serve as a example(s) and pointer(s) to scholarly works in which this data set has been used. The \<usageCitation\> element can be a \<citation\> or \<bibtex\> element.

*Example of the <usageCitation> element:*

```xml
<dataset>
    ...
    <usageCitation>
        <citation>
            <title>Title of a paper that uses this dataset or its metadata</title>
            <creator>
                <individualName>
                    <givenName>Mark</givenName>
                    <surName>Jarkady</surName>
                </individualName>
            </creator>
            <pubDate>2017</pubDate>
            <article>
                <journal>EcoSphere</journal>
                <publicationPlace>https://doi.org/10.1002/ecs2.2166</publicationPlace>
            </article>
        </citation>
    </usageCitation>
    <usageCitation>
        <bibtex>
        <![CDATA[
          @article{doi:10.1890/10-0423.1,
          author = {Lerman, Susannah B. and Warren, Paige S.},
          title = {The conservation value of residential yards: linking birds and people},
          journal = {Ecological Applications},
          volume = {21},
          number = {4},
          pages = {1327-1339},
          keywords = {Arizona, USA, CAP LTER, human–wildlife interactions, long-term ecological research, native landscaping, residential yards, socio-ecology, urban birds},
          doi = {10.1890/10-0423.1},
          url = {https://esajournals.onlinelibrary.wiley.com/doi/abs/10.1890/10-0423.1},
          eprint = {https://esajournals.onlinelibrary.wiley.com/doi/pdf/10.1890/10-0423.1}
          }
        ]]>
        </bibtex>
    </usageCitation>
    ...
</dataset>
```

#### eml-literature module - reference publication 

A citation to an article or other resource that serves as an important reference for a data set should be documented in a \<referencePublication\> element. Anyone using the data set should generally cite the data set itself (using the creator, pubDate, title, publisher, and packageId fields), and consider providing an additional citation to the reference publication. The \<referencePublication\> element will typically be used when the data set and a companion or associated paper are published near concurrently. Common cases where a reference publication may be useful include when a data paper is published that describes the dataset, or when a paper is intended to be the canonical or exemplar reference to the dataset – these are features that distinguish the \<referencePublication\> CitationType from the \<usageCitation\> CitationType.

*Example of the \<referencePublication\> element:*

```xml
<dataset>
   ...
   <referencePublication>
      <bibtex>
         @article{doi:10.1890/14-2252.1,
            author = {Edwards, Kyle F. and Klausmeier, Christopher A. and Litchman, Elena},
            title = {Nutrient utilization traits of phytoplankton},
            journal = {Ecology},
            volume = {96},
            number = {8},
            pages = {2311-2311},
            keywords = {algae, allometry, competition, Droop, Monod, nitrogen, phosphorus, physiology, stoichiometry, uptake kinetics},
            doi = {10.1890/14-2252.1},
            url = {https://esajournals.onlinelibrary.wiley.com/doi/abs/10.1890/14-2252.1},
            eprint = {https://esajournals.onlinelibrary.wiley.com/doi/pdf/10.1890/14-2252.1}
          }
      </bibtex>
   </referencePublication>
   ...
</dataset>
```


### The eml-software module - Software specific information

Links:

- [Module Diagram](./images/eml-software.png)
- [Interactive Module Documentation](./schema/eml-software_xsd.html)

The eml-software module contains general information that describes
software resources. This module is intended to fully document software
that is needed in order to view a resource (such as a dataset) or to
process a dataset. The software module is also imported into the
eml-methods module in order to document what software was used to
process or perform quality control procedures on a dataset.

The eml-software module, like other modules, may be \"referenced\" via
the \<references\> tag. This allows a software resource to be described
once, and then used as a reference in other locations within the EML
document via its ID.

### The eml-protocol module - Research protocol specific information

Links:

- [Module Diagram](./images/eml-protocol.png)
- [Interactive Module Documentation](./schema/eml-protocol_xsd.html)

The EML Protocol Module is used to define abstract, prescriptive
procedures for generating or processing data. Conceptually, a protocol
is a standardized method.

Eml-protocol resembles eml-methods; however, eml-methods is descriptive
(often written in the declarative mood: \"I took five subsamples\...\")
whereas eml-protocol is prescriptive (often written in the imperative
mood: \"Take five subsamples\...\"). A protocol may have versions,
whereas methods (as used in eml-methods) should not.
