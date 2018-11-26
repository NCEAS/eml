### The eml-literature module - Citation-specific information

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

*Example of the \<usageCitation\> element:*

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
     </usageCitation>
   </citation>
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
