#!/bin/bash

# script: update_namespaces.sh
# a quick way to do several search-and-replace 
# keep the infile intact by sending to standard out, redirect to new name, 
# or redirect sed output to a tempfile here and replace original.

infile=$1;


sed    " { s/-2.0.1/-2.1.0rc1/g


		} " < $infile  >tempfile


mv tempfile $infile

