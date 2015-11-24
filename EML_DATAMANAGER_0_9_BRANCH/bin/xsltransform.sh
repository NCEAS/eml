#!/bin/bash
# usage: xslttransform file.xml style.xsl output.xml
# A simple wrapper on xalan to run an XSL transform
#
LIB=../lib/apache
PARSER=$LIB/xalan.jar:$LIB/xercesImpl.jar:$LIB/xml-apis.jar
java -cp $PARSER org.apache.xalan.xslt.Process -IN $1 -XSL $2 -OUT $3
