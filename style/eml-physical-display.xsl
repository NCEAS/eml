<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-physical-display.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: berkley $'
  *     '$Date: 2002-04-19 16:16:10 $'
  * '$Revision: 1.1 $'
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
  * convert an XML file that is valid with respect to the eml-variable.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format 
  * suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:import href="eml-global-settings.xsl" />

  <xsl:output method="html" encoding="iso-8859-1"/>

  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" 
              href="{$stylePath}/{$qformat}.css" />
      </head>
      <body>
        <center>
          <h1>Physical Structure Description</h1>
          <h3>Ecological Metadata Language</h3><br />
        </center>
        
        <xsl:apply-templates select="eml-physical/identifier"/>

        <br /><h3>Physical Structure of the Data Set:</h3>
        <table width="100%">
        <tr>
        <td class="tablehead" width="35%"><b><xsl:text>Element</xsl:text></b></td>
        <td class="tablehead" width="65%"><b><xsl:text>Value</xsl:text></b></td>
        </tr>
        <tr>
        <td class="highlight" width="35%"><xsl:text>File Format</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/format"/></b></td>
        </tr>
        <tr>
        <td class="highlight" width="35%"><xsl:text>Character Encoding</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/characterEncoding"/></b></td>
        </tr>
        <tr>
        <td class="highlight" width="35%"><xsl:text>Size in kB</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/size"/></b></td>
        </tr>
        <tr>
        <td class="highlight" width="35%"><xsl:text>Authentication (checksum method &amp; value)</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/authentication"/></b></td>
        </tr>      
        <tr>
        <td class="highlight" width="35%"><xsl:text>Compression Method</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/compressionMethod"/></b></td>
        </tr>  
        <tr>
        <td class="highlight" width="35%"><xsl:text>Encoding Method</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/encodingMethod"/></b></td>
        </tr>  
        <tr>
        <td class="highlight" width="35%"><xsl:text>Number of Header Lines</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/numHeaderLines"/></b></td>
        </tr>  
        <tr>
        <td class="highlight" width="35%"><xsl:text>Record Delimiter</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/recordDelimiter"/></b></td>
        </tr>  
        <tr>
        <td class="highlight" width="35%"><xsl:text>Maximum Record Length</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/maxRecordLength"/></b></td>
        </tr>  
        <tr>
        <td class="highlight" width="35%"><xsl:text>Quote Character</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/quoteCharacter"/></b></td>
        </tr>  
        <tr>
        <td class="highlight" width="35%"><xsl:text>Literal Character</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/literalCharacter"/></b></td>
        </tr>
        <tr>
        <td class="highlight" width="35%"><xsl:text>Field Start Column</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/fieldStartColumn"/></b></td>
        </tr>
        <tr>
        <xsl:if test="eml-physical/fieldDelimiter">
        <td class="highlight" width="35%"><xsl:text>Field Delimeter</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/fieldDelimiter"/></b></td>
        </xsl:if>
        <xsl:if test="eml-physical/fieldWidth">
        <td class="highlight" width="35%"><xsl:text>Field Width</xsl:text></td>
        <td class="tabledefault" width="65%"><b><xsl:value-of select="eml-physical/fieldWidth"/></b></td>
        </xsl:if>
        </tr>
        </table>       
      </body>
    </html>
  </xsl:template>
  
  
  <xsl:template match="identifier">
    <table width="100%">
      <tr>
        <td class="highlight" width="35%">
           <b><xsl:text>Metadata File ID:</xsl:text></b>
        </td>
        <td width="65%">
            <xsl:value-of select="."/>
        </td>
      </tr>
    </table>
  </xsl:template>
</xsl:stylesheet>
