#!/bin/bash

# usage: ./bin/deploy_site.sh
# run script from the top of EML checkout
 
# actions:
# 1. delete the current gh_pages branch
# 2. push the current docs/dist directory to gh_pages

git push origin :gh-pages && git subtree push --prefix docs/dist origin gh-pages

