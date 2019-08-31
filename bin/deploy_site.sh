#!/bin/bash

# usage: ./bin/deploy_site.sh
# run script from the top of EML checkout
 
# 1. create a temporary branch for the dist files
CURRENT_BRANCH=`git branch | grep \* | cut -d ' ' -f2`
TEMP_BRANCH=dist_$$
echo "CURRENT: ${CURRENT_BRANCH}"
echo "   TEMP: ${TEMP_BRANCH}"
git checkout -b ${TEMP_BRANCH}

# 2. build the Bookdown book into the dist directory
cd docs
R -f 'build_book.R'
cd ..

# 3. Add and commit the build site to dist on the temp branch
git add dist
git commit -m "Deploy site to netlify"

# 4. delete the current gh_pages branch
# 5. push the temp dist directory to origin gh_pages
git push origin :gh-pages && git subtree push --prefix dist origin gh-pages

# 6. switch back to the original branch
git checkout ${CURRENT_BRANCH}

# 7. delete the temporary branch
git branch -D ${TEMP_BRANCH}

echo "Site deployed. Check https://eml.ecoinformatics.org "
