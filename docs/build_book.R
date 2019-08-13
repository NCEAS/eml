#!/usr/bin/env Rscript

# We customized the build process so we could use the README.md from the root
# of the repo as the index page for the entire book, rather than duplicating
# the content or writing new content.

bookdown::render_book("index.Rmd", "bookdown::gitbook")

# Copy the HTML Bookdown produces from the ../README.md file to index.html
file.copy("dist/eml-ecological-metadata-language.html", "dist/index.html", overwrite = TRUE)

# Copy the Oxygen XML schema docs into the book
system(paste("cp -r", "./schema", "./dist/schema"))
