java -cp ./;xalan.jar;xml-apis.jar;xercesImpl.jar org.apache.xalan.xslt.Process -PARAM packageName jones.204.18 -IN triple_info.xsl -XSL triple_info.xsl -OUT packageStructure.xml
java -cp ./;xalan.jar;xml-apis.jar;xercesImpl.jar org.apache.xalan.xslt.Process -IN packageStructure.xml -XSL emlb6toeml2.xsl -OUT eml2Version.xml
