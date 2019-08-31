# About this site

This site was build using Bookdown [@xie2015] from Yihui Xie. Thanks!

## Building the site

Source code for the book is maintained in the `docs` directory of the 
[EML repository](https://github.com/NCEAS/eml) in markdown format.
To edit the site, make changes to the markdown files and configuration files in
`docs`, and then run the `build_book.R` script which builds the bookdown version
of the site and places the files in the `dist` directory.  The configuration file 
`_bookdown.yml` controls the inclusion and order of the chapters. Generally, there
is one markdown file for each chapter, and so it should start with a single 
level 1 heading which will act as the chapter title in the table of contents.

Note that if you have
also made changes to the XSD schema files for EML, then you should also regenerate
the schema documentation using Oxygen. This requires a trial or licensed version
of Oxygen, and can be run using the shell script `bin/build_schema_documentation.sh`.
Here are example commands for updating the site after you've changed the 
markdown documentation and XSD files.

```sh
$ ./bin/build_schema_documentation.sh
$ cd docs
$ R -f 'build_book.R'
$ cd ..
```

At this point, you have a local copy of the documentation built that you can view
in a web browser.

## Deploying the site

Once the changes are committed in the docs directory, it is ready to 
be deployed on the web by pushing the files to the `gh_pages` branch.  Publishing 
`gh_pages` content to the web is currently handled 
by [Netlify](https://app.netlify.com/sites/ecometadata/overview) 
which publishes the site to the custom domain 
[https://eml.ecoinformatics.org](https://eml.ecoinformatics.org).  To deploy a new
version of the site, first edit and commit the site changes to the `master` branch
as described in the previous section, and then deploy the site using the provided
deployment shell script which updates the `gh_pages` branch for you:

```sh
$ ./bin/deploy_site.sh
```

This will remove the current `dist` directory, build a clean copy of the `dist`
directory from the documentation source files, then copy the built files in 
the `dist` directory to the `gh_pages` branch, which will be picked up and 
deployed by Netlify shortly thereafter to [https://eml.ecoinformatics.org](https://eml.ecoinformatics.org).
