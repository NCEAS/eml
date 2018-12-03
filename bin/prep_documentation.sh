#!/bin/bash

# usage: bin/prep_documentation.sh
# run script from the top of checkout
# script runs a transform on all the schema files, to prep them for buiding documentation 
# e.g., this cmd:  xsltproc style/eml_appinfo2documentation.xsl xsd/eml-party.xsd > tmp/eml-party.xsd
# it could use the xsltranform.sh script in this dir, but I am missing the java, so using built-in xsltproc

INPUT='./xsd';
OUTPUT='./tmp';
TEMPLATE='./style/eml_appinfo2documentation.xsl';

COUNTER=0;

for inputfile in `ls $INPUT/*xsd` ;

do
	# echo $inputfile;
	filename=`basename $inputfile`;
	echo ${filename}; 
	xsltproc $TEMPLATE $INPUT/$filename > $OUTPUT/$filename
	(( COUNTER ++ ));
done

 echo "processed $COUNTER files from $INPUT to $OUTPUT using $TEMPLATE"

