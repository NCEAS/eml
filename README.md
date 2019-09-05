# EML: Ecological Metadata Language

[![Build Status](https://travis-ci.org/NCEAS/eml.svg?branch=BRANCH_EML_2_2)](https://travis-ci.org/NCEAS/eml)
[![EML](https://img.shields.io/badge/eml-2.2.0-blue.svg?style=plastic)](https://github.com/NCEAS/eml)

*Cite as*:

[Matthew B. Jones](https://orcid.org/0000-0003-0077-4738),  [Margaret O'Brien](https://orcid.org/0000-0002-1693-8322), [Bryce Mecum](https://orcid.org/0000-0002-0381-3766), [Carl Boettiger](https://orcid.org/0000-0002-1642-628X), [Mark Schildhauer](https://orcid.org/0000-0003-0632-7576), [Mitchell Maier](https://orcid.org/0000-0001-6955-0535), [Timothy Whiteaker](https://orcid.org/0000-0002-1940-4158), [Stevan Earl](https://orcid.org/0000-0002-4465-452X), [Steven Chong](https://orcid.org/0000-0003-1264-1166). 2019. **Ecological Metadata Language version 2.2.0.** KNB Data Repository. [doi:10.5063/F11834T2](https://doi.org/10.5063/F11834T2) <button id="bibtext-button">Copy BibTeX</button>

<input type="text" id="placeholder"/>
<span id="bibtex">
&#x0040;article{EML_2019,
  title={Ecological Metadata Language version 2.2.0},
  url={https://eml.ecoinformatics.org},
  DOI={10.5063/f11834t2},
  publisher={KNB Data Repository}, 
  author={Jones, Matthew and O’Brien, Margaret and Mecum, Bryce and Boettiger, Carl and Schildhauer, Mark and Maier, Mitchell and Whiteaker, Timothy and Earl, Stevan and Chong, Steven},
  year={2019}
}
</span>

The Ecological Metadata Language (EML) defines a comprehensive vocabulary and a readable XML markup syntax for documenting research data.  It is in widespread use in the earth and environmental sciences, and increasingly in other research disciplines as well.  EML is a community-maintained specification, and evolves to meet the data documentation needs of researchers who want to openly document, preserve, and share data and outputs.  EML includes modules for identifying and citing data packages, for describing the spatial, temporal, taxonomic, and thematic extent of data, for describing research methods and protocols, for describing the structure and content of data within sometimes complex packages of data, and for precisely annotating data with semantic vocabularies. EML includes metadata fields to fully detail data papers that are published in journals specializing in scientific data sharing and preservation.

- **EML 2.2.0 Specification**
    - [Read it online](https://eml.ecoinformatics.org/)
    - [Browse the interactive EML schema documentation](https://eml.ecoinformatics.org/schema)
    - [Download EML](http://knb.ecoinformatics.org/software/dist/eml-2.2.0.tar.gz) - The download consists of the
      EML modules, described in the [XML Schema](http://www.w3.org/XML/Schema) language.
      In addition, the full documentation on the modules is provided in HTML format.
    - [Changes to EML in version 2.2.0](https://eml.ecoinformatics.org/whats-new-in-eml-2-2-0.html)

- **Version**: 2.2.0
- **DOI**: https://doi.org/10.5063/F11834T2 
- **Feedback**: [eml-dev@ecoinformatics.org](mailto:eml-dev@ecoinformatics.org)
- **Bug reports**: https://github.com/NCEAS/eml/issues
- **Web site**: https://eml.ecoinformatics.org/
- **Source code**: https://github.com/NCEAS/eml
- **Slack Discussion channel**: #eml on https://slack.nceas.ucsb.edu

## Getting Started

Composing an EML document can be done in a simple text editor (e.g., Atom), 
via scripting languages like R and python (e.g., the R [eml](https://github.com/ropensci/eml) package), 
in general-purpose XML authoring tools (e.g., Oxygen), and in custom web-based metadata editing tools 
(e.g., MetacatUI). While these tools expand and shift over time, the core metadata language
has been consistent and backwards compatible, allowing for decades of seamless
interoperability of data sets in many repositories.

EML documents can be started simply, and then additional detail added over time.
On the simple end, an EML document that provides basic bibliographic information
would be sufficient for citing a data set and for simple discovery in catalogs:

```xml
<?xml version="1.0"?>
<eml:eml
    packageId="doi:10.xxxx/eml.1.1" system="https://doi.org"
    xmlns:eml="https://eml.ecoinformatics.org/eml-2.2.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:stmml="http://www.xml-cml.org/schema/stmml-1.1"
    xsi:schemaLocation="https://eml.ecoinformatics.org/eml-2.2.0 xsd/eml.xsd">
    
    <dataset>
        <title>Primary production of algal species from Southeast Alaska, 1990-2002</title>
        <creator id="https://orcid.org/0000-0003-0077-4738">
            <individualName>
                <givenName>Matthew</givenName>
                <givenName>B.</givenName>
                <surName>Jones</surName>
            </individualName>
            <electronicMailAddress>jones@nceas.ucsb.edu</electronicMailAddress>
            <userId directory="https://orcid.org">https://orcid.org/0000-0003-0077-4738</userId>
        </creator>
        <keywordSet>
            <keyword>biomass</keyword>
            <keyword>productivity</keyword>
        </keywordSet>
        <contact>
            <references>https://orcid.org/0000-0003-0077-4738</references>
        </contact>
    </dataset>
</eml:eml>
```

This document can then be supplemented with additional metadata describing research
projects and methods, structural information about the data, and much more.

## About the EML Project

The EML project is an open source, community oriented project dedicated to providing a high-quality metadata specification for describing data relevant to diverse disciplines that involve observational research like ecology, earth, and environmental science. The specification is maintained by [voluntary project members](docs/contributors.md) who donate their time and experience in order to advance information management for ecology. Project decisions are made by consensus of the current maintainers on the project.

We welcome contributions to this work in any form. Individuals who invest substantial amounts of time and make valuable contributions to the development and maintenance of EML (in the opinion of current project maintainers) will be invited to become EML project maintainers. Contributions can take many forms, including the development of the EML schemas, writing documentation, and helping with maintenance, among others.

## Contributing

Developers may be interested in browsing the [source code repository](https://github.com/NCEAS/eml/) that we use in developing EML. Starting with EML 2.1.1, the master branch reflects the current stable release of EML. Development occurs in development branches (e.g., BRANCH_EML_2_2), which allows experimental additions as they are being proposed by the community.  This always contains the most recent development version of EML, and therefore may be in flux, or otherwise broken. It is unlikely that it will contain the same files that are in the current release. Development branches are virtually guaranteed to change before they are released, and so they should not be used in production environments. Use development branches at your own risk for testing.  Write access to this repository is reserved for current project maintainers. Please submit contributions as pull requests. We welcome contributions to this work in any form.  Contributions can take many forms, including the development of the EML schema, writing documentation, and helping with maintenance, among others. Non-project members can contribute by submitting their feedback, revisions, fixes, code, or any other contribution through pull requests at GitHub. Discussion of issues occurs on the [Slack channel](https://slack.nceas.ucsb.edu), or through the [EML Issue Tracking system](https://github.com/NCEAS/eml/issues). The preferred way to submit problems with EML or feature requests is the issue tracking system.

## History

EML was originally developed by Matthew Jones at NCEAS based on a report by the [ESA Committee on the Future of Long-Term Ecological Data](https://web.archive.org/web/20040213204322/http://esa.sdsc.edu/FLED/FLED.html) and on a related paper on ecological metadata by Michener et al. (see Michener, William K., et al., 1997. Ecological Applications, "Nongeospatial metadata for the ecological sciences" Vol 7(1). pp. 330-342.).  Version 1.0 was released at NCEAS in 1997 and used internally, with further internal releases of versions 1.2, 1.3, and 1.4, all of which followed the FLED recommendations closely in its content implementation. Version 2 became a community-maintained, open specification. Substantial modifications for EML 2.x came from experience using the earlier specification at NCEAS and from feedback from the ecological community, particularly information managers from the Long Term Ecological Research Network. Versions 2.1 and 2.2 introduce significant new features like internationalization, semantic annotations, and support for data papers.

## Older versions (deprecated)

The following versions are still available for reference purposes, although they have been superseded by the current version (2.2.0).  Please make every effort to use the current version.

- [EML 2.2.0](http://knb.ecoinformatics.org/software/dist/eml-2.2.0.tar.gz)
- [EML 2.1.1](http://knb.ecoinformatics.org/software/dist/eml-2.1.1.tar.gz)
- [EML 2.1.0](http://knb.ecoinformatics.org/software/dist/eml-2.1.0.tar.gz)
- [EML 2.0.1](http://knb.ecoinformatics.org/software/dist/eml-2.0.1.tar.gz)
- [EML 2.0.0](http://knb.ecoinformatics.org/software/dist/eml-2.0.0.tar.gz)
- [EML 1.4.1](http://knb.ecoinformatics.org/software/dist/eml-1.4.1.tar.gz)

## Copyright and License
Copyright: 1997-2019 Regents of the University of California

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


## Funding and Acknowledgements

EML was developed and is maintained with support from the [National Center for 
Ecological Analysis and Synthesis (NCEAS)](https://www.nceas.ucsb.edu/), a Center
funded by the University of California Santa Barbara and the state of California. 

This material is based upon work supported by the US [National Science Foundation](https://nsf.gov)
under Grant No. DEB-9980154, DBI-9904777, 0225676, DEB-0072909, DBI-9983132,
and DEB-9634135.  Any opinions, findings and conclusions or recommendations
expressed in this material are those of the author(s) and do not necessarily
reflect the views of the National Science Foundation (NSF).

This product includes software developed by the Apache Software
Foundation (http://www.apache.org/). See the LICENSE file in lib/apache
for details.

The source code, object code, and documentation in the com.oreilly.servlet
package is copyright and owned by Jason Hunter. See the cos-license.html file
for details of the license.  Licensor retains title to and ownership of the
Software and all enhancements, modifications, and updates to the Software.

This product includes software developed by the JDOM Project
(http://www.jdom.org/). See jdom-LICENSE.txt for details.
