[EML Schema Documentation](./index.html)

[EML FAQs](./eml-faq.html)

EML 2.1.1 introduces internationalization capabilities that can be used
in most text-based elements. Version 2.1.1 remains backward-compatible
with the previous 2.1.0 release. Authors can safely upgrade existing
2.1.0 documents to 2.1.1 without altering any content, though adding
additional language translations is encouraged.

By allowing mixed element content, nested translation elements can be
included without altering or introducing ambiguity with respect to EML
element cardinality. Translation elements use standard xml:lang
attributes to specify the language used for their content. Translation
elements can be nested such that child elements may inherit or override
the language used by their ancestors. The top-level EML element may
include an xml:lang attribute which will apply to every element in the
document unless a child element includes a different xml:lang attribute
to override the document default.

Multi-lingual authors of EML should carefully consider their primary
target audience when deciding the default document language. Early
adopters should be aware search tools like Metacat will require custom
configuration in order to search arbitrarily nested translations.

Internationalization in EML 2.1.1
=================================

**Q:** Including translations

**A:** The internationalization feature allows authors to place any
language in \<value\> tags nested within most EML text fields. The
xml:lang attribute should be used to explicitly declare the language
used.

Additional documentation and examples are available in the EML
specification. The
[i18nNonEmptyStringType](eml-resource.html#i18nNonEmptyStringType) is
used for simple text, while [i18nString](eml-text.html#i18nString) for
more structured text elements.
