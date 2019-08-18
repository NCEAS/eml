#!/bin/sh

# Usage: get-issues-list.sh <milestone-number>

# Get the list of issues for a milestone from GitHub, and extract the essential bits needed for creating the release notes document
# Note that this is simplistic and may not work if there are more than 100 issues in the release, etc. Also note the milestone number
# is not the same as the milestone label in GitHub

curl -H "Accept: text/json" https://api.github.com/repos/NCEAS/eml/issues?milestone=$1\&state=all\&per_page=100 > eml-$1-issues-01.json
jq '[ .[] | [.number, .state, .title, .html_url ]] | sort | .[] | @csv' eml-$1-issues-01.json > eml-$1-issues-01.csv
echo "List of issues are in file: eml-$1-issues-01.csv"
