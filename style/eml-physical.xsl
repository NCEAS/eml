<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-physical.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2002-05-22 20:17:22 $'
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
  * convert an XML file that is valid with respect to the eml-variable.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format 
  * suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:import href="eml-settings.xsl" />
<xsl:import href="eml-identifier.xsl" />

  <xsl:output method="html" encoding="iso-8859-1"/>

  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" href="{$stylePath}/{$qformat}.css" />
      </head>
      <body>
        <center>
          <h1>Physical Structure Description</h1>
          <h3>Ecological Metadata Language</h3><br />
        </center>
        <table class="tabledefault" width="100%"><!-- width needed for NN4 - doesn't recognize width in css -->
        <xsl:apply-templates select="eml-physical/identifier" mode="resource"/>
        <tr>
        <td class="tablehead" colspan="2">Physical Structure:</td></tr>
        <xsl:apply-templates select="eml-physical/*"/>
        </table>       
      </body>
    </html>
  </xsl:template>
  
  
  <xsl:template match="format">
        <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">File Format</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>
  
  <xsl:template match="characterEncoding">
        <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">Character Encoding</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="size">
        <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">Size</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/>
        <xsl:if test="normalize-space(./@units)!=''">
            ( <xsl:value-of select="./@units"/> )
        </xsl:if></td>
        </tr>
  </xsl:template>

  <xsl:template match="authentication">
        <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">Authentication (checksum value &amp; method)</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}">
        <xsl:value-of select="."/> (method: <xsl:value-of select="./@method"/>)</td></tr>      
  </xsl:template>

  <xsl:template match="compressionMethod">
        <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">Compression Method</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/></td>
        </tr>  
  </xsl:template>

  <xsl:template match="encodingMethod">
        <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">Encoding Method</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/></td>
        </tr>  
  </xsl:template>

  <xsl:template match="numHeaderLines">
        <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">Number of Header Lines</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/></td>
        </tr>  
  </xsl:template>

  <xsl:template match="recordDelimiter">
        <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">Record Delimiter</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/></td>
        </tr>  
  </xsl:template>

  <xsl:template match="maxRecordLength">
        <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">Maximum Record Length</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/></td>
        </tr>  
  </xsl:template>

  <xsl:template match="quoteCharacter">
        <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">Quote Character</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/></td>
        </tr>  
  </xsl:template>

   <xsl:template match="literalCharacter">
       <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">Literal Character</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="fieldStartColumn">
        <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">Field Start Column</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  
  <xsl:template match="fieldDelimiter">
        <tr>
        <td class="{$firstColStyle}" width="{$firstColWidth}">Field Delimeter</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>
  
  <xsl:template match="fieldWidth">
        <tr><td class="{$firstColStyle}" width="{$firstColWidth}">Field Width</td>
        <td class="{$secondColStyle}" width="{$secondColWidth}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

</xsl:stylesheet>
