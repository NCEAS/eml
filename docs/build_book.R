#!/usr/bin/env Rscript

# We customized the build process so we could use the README.md from the root
# of the repo as the index page for the entire book, rather than duplicating
# the content or writing new content.
unlink("../dist", recursive = TRUE)

suppressWarnings(bookdown::render_book("index.Rmd", "bookdown::gitbook"))

# Copy the HTML Bookdown produces from the ../README.md file to index.html
file.copy("../dist/eml-ecological-metadata-language.html", "../dist/index.html", overwrite = TRUE)

# Copy the schema docs from oxygen into the built book
dir.create("../dist/schema")
file.copy(dir("schema", full.names = TRUE), "../dist/schema", recursive = TRUE)

# Copy the images into the built book
dir.create("../dist/images")
file.copy(dir("../img", full.names = TRUE), "../dist/images", recursive = TRUE)

# Copy the XSD schema files into the dist
dir.create("../dist/eml-2.2.0")
file.copy(dir("../xsd", full.names = TRUE), "../dist/eml-2.2.0", recursive = TRUE)
