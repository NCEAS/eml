#!/bin/bash

# usage: ./bin/prep_documentation.sh
# run script from the top of EML checkout
 
# actions:
# 1. runs a transform on all the schema files, to prep them for buiding documentation 
# e.g., this cmd:  xsltproc style/eml_appinfo2documentation.xsl xsd/eml-party.xsd > tmp/eml-party.xsd
# it could use the xsltranform.sh script in this dir, but I am missing the java, so using built-in xsltproc

# 2. runs the Oxygen documentation generator. assumes you have a license; 30 day license should be fine
# Oxygen info here:
# https://www.oxygenxml.com/doc/versions/20.1/ug-editor/topics/documentation-XML-Schema-command-line.html

# 3. copies O2 output to the $ROOT/docs/schema)

# 4. tmp files do not need to be tracked in git


INPUT='./xsd';
TMP_OUT='./tmp';
TEMPLATE='./style/eml_appinfo2documentation.xsl';

# this is the default install location for a mac (OS 10). your install may be different
O2_SCRIPT='/Applications/Oxygen XML Editor/schemaDocumentation.sh';

# Oxygen script will place its output at this dir, starting from same dir as it fines the input xsd.
# only seem to be able to control output to a dir below the dir holding the xsd files
# we will use the dir name later, to move output
O2_OUTPUT_DIR='docs/schema';

# The name of the documentation index file right now is called "index.html". 
# could default to "eml.html" (imported schemas docs will be have respective schema's basename)
O2_OUTPUT_INDEX='index.html';
O2_OUTPUT="$O2_OUTPUT_DIR"/"$O2_OUTPUT_INDEX";

# locaton of final documentation (with other EML docs, note that this is anchored at PWD, root of the checkout)
OUTPUT='./docs/schema';




# loop through schema files and xform
COUNTER=0;

# 1. for Oxygen, transform the xsd files, moving appinfo node to documentation, with basic text formatting (see xsl)
for inputfile in `ls $INPUT/*xsd` ;

do
	# echo $inputfile;
	filename=`basename $inputfile`;
	echo ${filename}; 
	xsltproc $TEMPLATE $INPUT/$filename > $TMP_OUT/$filename
	(( COUNTER ++ ));
done

 echo "processed $COUNTER files from $INPUT to $TMP_OUT using $TEMPLATE"; echo;
 
# run the Oxygen XML editor's documenation generator and put output in a tmp area that mirrors the docs dir 
echo "O2_OUTPUT = $O2_OUTPUT";
"$O2_SCRIPT" $TMP_OUT/eml.xsd -out:$O2_OUTPUT -format:html -split:namespace 


# copy O2 output to the main documentation area

# cp -r "$TMP_OUT"/"$O2_OUTPUT_DIR" "$OUTPUT" ;
echo "cp -r $TMP_OUT/$O2_OUTPUT_DIR $OUTPUT ";
echo "Top of schema documentation is $OUTPUT/$O2_OUTPUT_INDEX ";


# tmp files are not tracked in git.

