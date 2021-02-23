# What's New in EML 2.2.0

This document highlights changes and new features in EML 2.2.  EML authors
should also refer to the affected sections in the normative schema documents
for complete usage information and examples.

EML 2.2 is backward compatible, i.e., EML 2.0 and 2.1 documents could be
relabled as EML 2.2 without violating the schema. However, some predefined values, 
particularly units, are deprecated in favor of new values to fix mispellings or 
inconsistencies. EML authors are encouraged to migrate away from deprecated values.

> Existing EML 2.1.1 documents can be converted to EML 2.2.0 using the XSL
> stylesheet that accompanies this release.

See the 'README.md' that accompanies the distribution for a complete list of the
bugs addressed, and for information of interest to developers.

## Unit Additions and Changes

Many units were added for EML 2.2.0, including units supporting
oceanography, climatology, forestry and limnology. The following unit types from
the unit dictionary have been renamed to better reflect their dimensionality:

* massFlux is now massRate
* arealMassDensityRate is now massFlux
* amountOfSubstanceWeightFlux is now amountOfSubstanceWeightRate

Dozens of units are now deprecated in favor other units to address issues such
as naming consistency.  Use the "deprecatedInFavorOf" attribute in
eml-unitDictionary.xml to identify deprecated units.

## BibTeX Support

CitationType elements can use the new &lt;bibtex&gt; element as an
alternative to encoding citations in the EML XML structures. BibTeX entries
generally play well inside of XML structures, but XML escaping is still needed
for special characters, so consider embedding BibTeX entries in CDATA blocks if
XML escaping becomes cumbersome.

```xml
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
```


## Citation Lists

EML 2.2.0 includes a new &lt;literatureCited&gt; element as a
CitationListType that represents one or more citations. These citations can be a
series of &lt;citation&gt; elements or a &lt;bibtex&gt; element with a list of
citations.

Example of &lt;literatureCited&gt; element in EML 2.2.0:

```xml
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
   @article{hampton_2017,
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

   @article{collins_2018,
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
```

## Markdown Support

TextType elements can include GitHub Flavored Markdown using the new
&lt;markdown&gt; element.  See the description for the &lt;markdown&gt; element
in [eml-text.xsd](https://github.com/NCEAS/eml/blob/master/xsd/eml-text.xsd) for
more details on embedded images, inline citations, and formatting considerations
regarding special characters and indentation.

Example of markdown in EML 2.2.0:

```xml
<markdown>
   An introduction goes here.

   It can include multiple paragraphs. And these paragraphs should have enough text to wrap in a wide browser.  So, repeat that last thought. And these paragraphs should have enough text to wrap in a wide browser.  So, repeat that last thought.

   Text can cite other works, such as [@jones_2001], in which case the
   associated key must be present as either the citation identifier in a
   `bibtex` element in the EML document, or as the `id` attribute on one of the
   `citation` elements in the EML document.  These identifiers must be unique
   across the document.  

   And bulleted lists are also supported:

   - Science
   - Engineering
   - Math

   It can also include equations:

   $$\left( x + a \right)^{n} = \sum_{k = 0}^{n}{\left( \frac{n}{k} \right)x^{k}a^{n - k}}$$

   Plus, it can include all of the other features of [Github Flavored Markdown (GFM)](https://github.github.com/gfm/).
</markdown>
```

## Usage Citations

EML 2.2.0 documents can include a non-comprehensive list of citations in
which the data were explicitly used via a &lt;usageCitation&gt; element, which
is a CitationType.

Example usageCitation element:

```xml
<dataset>
   ...
   <usageCitation>
      <bibtex>
         @article{jones_2001,
            title = {Managing scientific metadata},
            volume = {5},
            issn = {10897801},
            url = {http://ieeexplore.ieee.org/lpdocs/epic03/wrapper.htm?arnumber=957896},
            doi = {10.1109/4236.957896},
            number = {5},
            journal = {IEEE Internet Computing},
            author = {Jones, Matthew B. and Berkley, Chad and Bojilova, Jivka and Schildhauer, Mark},
            year = {2001},
            pages = {59--68}
         }
      </bibtex>
   </usageCitation>
   ...
</dataset>
```

## Reference Publications

For cases when a research paper or data paper illustrates the usage of a dataset, the
new &lt;referencePublication&gt; element can be used to associate the dataset
with the publication.

```xml
<dataset>
   ...
   <referencePublication>
      <bibtex>
         @article{ludwig_2018,
            title = {Permafrost carbon and nitrogen, Yukon-Kuskokwim Delta, Alaska},
            url = {http://ecosphere.esa.org/article/yyyy.zzzzzzz},
            doi = {10.xxxx/yyyy.zzzzzz},
            journal = {EcoSphere},
            author = {Ludwig, Sarah},
            year = {2018}
         }
      </bibtex>
   </referencePublication>
   ...
</dataset>
```

## Data Paper Support

ESA and other societies are moving towards the publication of data papers
that include more complete narratives about a data set and its importance and
use. To support data papers, EML 2.2.0 includes new optional fields for
Introduction, Getting Started, and Acknowledgements, as well as markdown and
citation-related elements described elsewhere in this document.

See the [example EML document for a data
paper](https://github.com/NCEAS/eml/blob/master/src/test/resources/eml-data-paper.xml).

## Dataset license

Datasets can now include a &lt;licensed&gt; element along with a URL to a
license.  The URLs are best provided using the SPDX license vocabulary for 
consistency, but other license URIs are allowed.

```xml
<dataset>
   ...
   <licensed>
      <licenseName>Creative Commons Attribution 4.0 International</licenseName>
      <url>https://spdx.org/licenses/CC-BY-4.0.html</url>
      <identifier>CC-BY-4.0</identifier>
   </licensed>
   ...
</dataset>
```

## Structured Funding Information

EML 2.2.0 sees the addition of an `<award>` element to support
structured funding information for a research project. This is used as an
alternative or in addition to the &lt;funding&gt; element which is a text type. 
Generally a textual description of funding sources would go into `<funding>`, while
structured names and identifiers for the funding agency and award would go into the
`<award>` element.

```xml
<project>
   ...
   <funding><para>Funding is from a grant from the National Science Foundation.</para></funding>
   <award>
      <funderName>National Science Foundation</funderName>
      <funderIdentifier>https://doi.org/10.13039/00000001</funderIdentifier>
      <awardNumber>1546024</awardNumber>
      <title>Scientia Arctica: A Knowledge Archive for Discovery and Reproducible Science in the Arctic</title>
      <awardUrl>https://www.nsf.gov/awardsearch/showAward?AWD_ID=1546024</awardUrl>
   </award>
</project>
```

## "unkown" deprecated as <maintenanceUpdateFrequency> element value

Some EML documents have specified "unkown" instead of "unknown" for the
`<maintenanceUpdateFrequency>` because there is a typo in the list of allowed values
in eml-dataset.xsd. For EML 2.2.0, a value of `unknown` is added to the list,
while the the original value of `unkown` is kept in the enumeration for
backwards compatibility with a note that its use is deprecated.

## access elements deprecated

While eml-access has been part of the standard for many years, use has been 
extremely limited, and most systems seem to omit and ignore the `access` elements 
in the document in favor of using repository-specific mechanisms to control access.
Therefore, EML 2.2.0 deprecates use of the `access` elements in the main body of EML 
documents, with the exception of use within the `additionalMetadata` element.  As this is a 
backwards incompatible change, the elements are still available in EML 2.2.0, but users 
should expect schema changes to occur in a future release that eliminate the use of `access` 
elements outside of `additionalMetadata`.  In addition, because authorization systems 
are system-dependent, the content within any `access` element should be considered 
advisory and may not reflect the actual authorization policies in place at a given point 
in time in a given repository.

## Supporting Semantic Metadata

See the [EML Semantic Annotation Primer](semantic-annotation-primer.html)
for a thorough overview. In brief, EML 2.2.0 supports entering terms from an
ontology via `<annotation>` elements to precisely describe items such as data
attributes/variables, datasets, and people.  Annotations are allowed in five
locations in the EML document:

* in `attribute`, `entity`, and `dataset` (or other resource) elements
* in an `/eml/annotations` root element
* in `/eml/additionalMetadata`


When the annotation is embedded in a containing EML `attribute` element, the
annotation's subject is that `attribute`:

```xml
<attribute id="att.12">
   <attributeName>biomass</attributeName>
   ...
   <annotation>
      <propertyURI label="of characteristic">http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#ofCharacteristic</propertyURI>
      <valueURI label="Mass">http://ecoinformatics.org/oboe/oboe.1.2/oboe-characteristics.owl#Mass</valueURI>
   </annotation>
   <annotation>
      <propertyURI label="of entity">http://ecoinformatics.org/oboe/oboe.1.2/oboe-core.owl#ofEntity</propertyURI>
      <valueURI label="Plant Sample">http://example.com/example-vocab-1.owl#PlantSample</valueURI>
   </annotation>
</attribute>
```

For annotations in `/eml/annotations`, the subject of the annotation is
established using a references attribute that points at the id of the subject of
the annotation:

```xml
<annotations>
   <annotation references="CDR-biodiv-table">
      <propertyURI label="Subject">http://purl.org/dc/elements/1.1/subject</propertyURI>
      <valueURI label="grassland biome">http://purl.obolibrary.org/obo/ENVO_01000177</valueURI>
   </annotation>
</annotations>
```

For annotations in `/eml/additionalMetadata`, the subject is the element with
the id listed within the associated &lt;describes&gt; element:

```xml
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

## Support for ids in taxonomicClassification

`taxonomicClassification` elements can now include a reference to
external identifiers, e.g., to a code from a system like ITIS or WoRMS.

```xml
<taxonomicClassification id="taxon_MAPY">
   <taxonRankName>species</taxonRankName>
   <taxonRankValue>Macrocystis pyrifera</taxonRankValue>
   <commonName>Giant Kelp</commonName>
   <taxonId provider="ITIS">11274</taxonId>
   <taxonId provider="https://www.ncbi.nlm.nih.gov/taxonomy">35122</taxonId>
</taxonomicClassification>
```

## How can I convert from earlier versions of EML?

An XSL stylesheet is provided with the EML Utilities to convert valid EML
2.1-series documents to EML 2.2.0 (see <http://knb.ecoinformatics.org/software/eml/>). 
The stylesheet performs basic tasks to create a template EML 2.2.0 document. 
For more information, see the Utilities documentation.

## Validity of new EML 2.2.0 documents

EML instance documents can be validated in these ways:

1. Using the command-line validator that comes with EML. To execute it, from the top-level
   directory of the EML release, run the `./bin/validate.sh` script passing your
   EML document filepath as a parameter. The script performs two actions: it checks
   the validity of references and id attributes, and it validates the document
   against the EML 2.2 schema.
   
2. Within R, use the `EML::validate()` function.

3. If you are planning to contribute your EML 2.2.0 document to a Metacat
   repository, note that the Metacat servlet checks all versions of incoming EML
   for validity as part of the insertion process.
