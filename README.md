# EML: Ecological Metadata Language

[![Build Status](https://travis-ci.org/NCEAS/eml.svg?branch=BRANCH_EML_2_2)](https://travis-ci.org/NCEAS/eml)
[![EML](https://img.shields.io/badge/eml-2.2.0-blue.svg?style=plastic)](http://github.com/NCEAS/eml)

*Cite as*:
Matthew B. Jones,  Margaret O'Brien, Bryce Mecum, Carl Boettiger, Mark Schildhauer, Mitchell Maier, Timothy Whitaker. 2018. **Ecological Metadata Language version 2.2.0.** KNB Data Repository. **DOI TO BE ISSUED**

*Abstract:*
The Ecological Metadata Language (EML) defines a comprehensive vocabulary and a readable XML markup syntax for documenting research data.  It is in widespread use in the earth and environmental sciences, and increasingly in other research disciplines as well.  EML is a community-maintained specification, and evolves to meet the data documentation needs of researchers who want to openly document, preserve, and share data and outputs.  EML includes modules for identifying and citing data packages, for describing the spatial, temporal, taxonomic, and thematic extent of data, for describing research methods and protocols, for describing the structure and content of data within sometimes complex packages of data, and for precisely annotating data with semantic vocabularies. EML includes metadata fields to fully detail data papers that are published in journals specializing in scientific data sharing and preservation.

- **EML 2.2.0 Specification**
    - [Read it online](docs/)
    - [Download EML](https://knb.ecoinformatics.org/software/dist) - The download consists of the
      EML modules, described in the [XML Schema](http://www.w3.org/XML/Schema) language.
      In addition, the full documentation on the modules is provided in HTML format.
    - [Changes to EML in version 2.1.1](docs/eml-211info.md)
    - [EML Frequently Asked Questions (FAQ)](docs/eml-faq.md)

- **Version**: 2.2.0 (**In development, not yet released**)
- **DOI**: (**NOT YET ISSUED**)
- **Feedback**: [eml-dev@ecoinformatics.org](mailto:eml-dev@ecoinformatics.org)
- **Bug reports**: http://github.com/NCEAS/eml/issues
- **Task Board**: https://waffle.io/NCEAS/eml
- **Web site**: http://knb.ecoinformatics.org/software/eml
- **Source code**: http://github.com/NCEAS/eml
- **Validation service**: https://knb.ecoinformatics.org/emlparser/
- **Slack Discussion channel**: #eml on http://slack.nceas.ucsb.edu

## History

EML was originally based on work done by the [ESA Committee on the Future of Long-Term Ecological Data](https://web.archive.org/web/20040213204322/http://esa.sdsc.edu/FLED/FLED.html) and on a related paper on ecological metadata by Michener et al. (see Michener, William K., et al., 1997. Ecological Applications, "Nongeospatial metadata for the ecological sciences" Vol 7(1). pp. 330-342.).  Version 1.0 was released at NCEAS in 1997, with further internal releases of versions 1.2, 1.3, and 1.4, all of which followed the FLED recommendations closely in its content implementation. Version 2 was modified substantially after experience using the specification and from feedback from the ecological community, and versions 2.1 and 2.2 introduce significant new features like internationalization, semantic annotations, and support for data papers.

## Validation
Some of the rules regarding EML that are important are expressed only in the EML specification, not in the XML Schema files.  This is because XML Schema was not able to express some of the constraints that we felt were important to creating a coherent metadata document, particularly with respect to identifiers and references to identifiers.  Thus, we have included a EML Validity Parser.  It checks both EML rules and the schema rules to be sure an XML document is a valid EML document.  It is included with the release, and provided as an online service. See docs/index.html.

## Files

The files in this directory include a "docs" directory that contains the EML Specification and all documentation for the modules, and an xsd directory with an XML Schema document for each of the modules that make up EML.  Also included is a schema for supporting documentation, and an instance document defining standard attribute units for EML. The "lib/sample/eml-sample.xml" file is a sample document illustrating the use of EML. The "test" directory contains a number of sample instance documents used for testing the schemas, and an application for checking document validity under JUnit (see http://junit.org).

## About the EML Project

The EML project is an open source, community oriented project dedicated to providing a high-quality metadata specification for describing data relevant to the ecological discipline. The project is completely comprised of [voluntary project members](docs/contributors.md) who donate their time and experience in order to advance information management for ecology. Project decisions are made by consensus according to the voting procedures described in the [ecoinformatics.org Charter](http://www.ecoinformatics.org/charter.html).

We welcome contributions to this work in any form. Individuals who invest substantial amounts of time and make valuable contributions to the development and maintenance of EML (in the opinion of current project members) will be invited to become EML project members according to the rules set forth in the [ecoinformatics.org Charter](http://www.ecoinformatics.org/charter.html). Contributions can take many forms, including the development of the EML schemas, writing documentation, and helping with maintenance, among others.

## Development Information

Developers may be interested in browsing the [source code repository](https://github.com/NCEAS/eml/) that we use in developing EML. This always contains the most recent development version of EML, and therefore may be in flux, or otherwise broken. It is unlikely that it will contain the same files that are in the current release (@version@). Use at your own risk. Write access to this repository is reserved for current project maintainers. Please submit contributions as pull requests. We welcome contributions to this work in any form.  Contributions can take many forms, including the development of the EML schemas, writing documentation, and helping with maintenance, among others. Non-project members can contribute by submitting their feedback, revisions, fixes, code, or any other contribution through pull requests at GitHub. Discussion of issues occurs on the [eml-dev@ecoinformatics.org](https://groups.google.com/a/ecoinformatics.org/forum/#!forum/ecoinfoeml-dev) mailing list, or through the [EML Issue Tracking system](http://github.com/NCEAS/eml/issues). The preferred way to submit problems with EML or feature requests is the issue tracking system.

## Older versions (deprecated)

The following versions are still available for reference purposes, although they have been superseded by the current version (2.2.0).  Please make every effort to use the current version.

- [EML 2.1.1](http://knb.ecoinformatics.org/software/dist/eml-2.1.1.tar.gz)
- [EML 2.1.0](http://knb.ecoinformatics.org/software/dist/eml-2.1.0.tar.gz)
- [EML 2.0.1](http://knb.ecoinformatics.org/software/dist/eml-2.0.1.tar.gz)
- [EML 2.0.0](http://knb.ecoinformatics.org/software/dist/eml-2.0.0.tar.gz)
- [EML 1.4.1](http://knb.ecoinformatics.org/software/dist/eml-1.4.1.tar.gz)

## Copyright and License
Copyright: 1997-2018 Regents of the University of California

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

This material is based upon work supported by the National Science Foundation
under Grant No. 0225676, DEB-9980154, DBI-9904777, DEB-0072909, DBI-9983132,
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
