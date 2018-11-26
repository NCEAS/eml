# Internationalization - Metadata in multiple languages

EML supports internationalization using the `i18nNonEmptyStringType`. This allows
an element in EML to contain text in a default language, as well as optional
representations of that element in other languages.

Fields defined as this type include:

-   Title
-   Keyword
-   Contact information (e.g. names, organizations, addresses)

TextType fields also support language translations. These fields
include:

-   Abstract
-   Methods
-   Protocol

Core metadata should be provided in English. The core elements can be
augmented with translations in a native language. Detailed metadata can
be provided in the native language as declared using the xml:lang
attribute. Authors can opt to include English translations of this
detailed metadata as they see fit.

**Example 2.1. Internationalization techniques**

The following example metadata document is provided primarily in
Portuguese but includes English translations of core metadata fields.
The xml:lang=\"pt\_BR\" attribute at the root of the EML document
indicates that, unless otherwise specified, the content of the document
is supplied in Portuguese (Brazil). The xml:lang=\"en\_US\" attributes
on child elements denote that the content of that element is provided in
English. Core metadata (i.e. title) is provided in English, supplemented
with a Portuguese translation using the value tag with an xml:lang
attribute. Note that child elements can override the root language
declaration of the document as well as the language declaration of their
containing elements. The abstract element is primarily given in
Portuguese (as inherited from the root language declaration), with an
English translation.

Many EML fields are repeatable (i.e. keyword) so that multiple values
can be provided for the same concept. Translations for these fields
should be included as nested value tags to indicate that they are
equivalent concepts expressed in different languages rather than
entirely different concepts.

```xml
    <?xml version="1.0"?>
    <eml:eml
        packageId="eml.1.1" system="knb"
        xml:lang="pt_BR"
        xmlns:eml="eml://ecoinformatics.org/eml-2.2.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="eml://ecoinformatics.org/eml-2.2.0 eml.xsd">

      <dataset id="ds.1">

        <!-- English title with Portuguese translation -->    
        <title xml:lang="en-US">
            Sample Dataset Description
            <value xml:lang="pt-BR">Exemplo Descrição Dataset</value>
        </title>
        ...
        <!-- Portuguese abstract with English translation -->    
        <abstract>
            <para>
                Neste exemplo, a tradução em Inglês é secundário
                <value xml:lang="">In this example, the English translation is secondary</value>
            </para>
        </abstract>
        ...
        <!-- two keywords, each with an equivalent translation -->    
        <keywordSet>
            <keyword keywordType="theme">
                árvore
                <value xml:lang="en-US">tree</value>
            </keyword>
            <keyword keywordType="theme">
                água
                <value xml:lang="en-US">water</value>
            </keyword>
        </keywordSet>
        ...
      </dataset>
    </eml:eml>
```
