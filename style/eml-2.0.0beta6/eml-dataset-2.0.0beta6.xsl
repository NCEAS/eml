<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-dataset-2.0.0beta6.xsl,v $'
  *      Authors: Matt Jones
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2003-11-17 17:49:08 $'
  * '$Revision: 1.2 $'
  * 
  * This program is free software; you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation; either version 2 of the License, or
  * (at your option) any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with this program; if not, write to the Free Software
  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
  *
  * This is an XSLT (http://www.w3.org/TR/xslt) stylesheet designed to
  * convert an XML file that is valid with respect to the eml-dataset.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format 
  * suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:import href="eml-coverage-2.0.0beta6.xsl"/>
<!-- IMPORTANT: IMPORT EML-RESOURCE *AFTER* EML-COVERAGE!!! -->
<xsl:import href="eml-resource-2.0.0beta6.xsl"/>

  <xsl:output method="html" encoding="iso-8859-1"/>
  
  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" 
              href="{$stylePath}/{$qformat}/{$qformat}.css" />
      </head>
      <body>
        <center>
          <h1>Data set description</h1>
          <h3>Ecological Metadata Language</h3>
        </center>
        <table class="tabledefault" width="100%"><!-- width needed for NN4 - doesn't recognize width in css -->
          <xsl:apply-templates select="." mode="resource"/>
          <xsl:apply-templates select="dataset"/>
          </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
