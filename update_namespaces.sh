#!/bin/bash

# script: update_namespaces.sh
# a quick way to do several search-and-replace 
# keep the infile intact by sending to standard out, redirect to new name. 
# or redirect sed output to a tempfile here and replace original. Use xargs, or
#	 for schema in `ls *xsd`; do ./update_namespaces.sh $schema; done

infile=$1;


sed    " { s/-2.1.0rc2/-2.1.0rc3/g



		} " < $infile  >tempfile


mv tempfile $infile

