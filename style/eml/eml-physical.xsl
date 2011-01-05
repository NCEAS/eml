<?xml version="1.0"?>
<!--
  *  '$RCSfile$'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: cjones $'
  *     '$Date: 2006-11-17 13:37:07 -0800 (Fri, 17 Nov 2006) $'
  * '$Revision: 3094 $'
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

  <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  

   <xsl:template name="physical">
      <xsl:param name="docid"/>
      <xsl:param name="level">entity</xsl:param>
      <xsl:param name="entitytype"/>
      <xsl:param name="entityindex"/>
      <xsl:param name="physicalindex"/>
      <xsl:param name="distributionindex"/>
      <xsl:param name="physicalfirstColStyle"/>
      <xsl:param name="notshowdistribution"/>
      <table class="{$tabledefaultStyle}">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="physicalcommon">
              <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
              <xsl:with-param name="notshowdistribution" select="$notshowdistribution"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="physicalcommon">
             <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
             <xsl:with-param name="notshowdistribution" select="$notshowdistribution"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
      </table>
  </xsl:template>

  <xsl:template name="physicalcommon">
    <xsl:param name="physicalfirstColStyle"/>
    <xsl:param name="notshowdistribution"/>
    <xsl:param name="docid"/>
    <xsl:param name="level">entity</xsl:param>
    <xsl:param name="entitytype"/>
    <xsl:param name="entityindex"/>
    <xsl:param name="physicalindex"/>
    <xsl:param name="distributionindex"/>

    <xsl:call-template name="physicalobjectName">
      <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="physicalsize">
      <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="physicalauthentication">
      <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="physicalcompressionMethod">
      <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="physicalencodingMethod">
      <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="physicalcharacterEncoding">
      <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="physicaltextFormat">
      <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="physicalexternallyDefinedFormat">
      <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="physicalbinaryRasterFormat">
      <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
    </xsl:call-template>
    <xsl:if test="$notshowdistribution=''">
      <xsl:for-each select="distribution">
        <xsl:call-template name="distribution">
          <xsl:with-param name="disfirstColStyle" select="$physicalfirstColStyle"/>
          <xsl:with-param name="dissubHeaderStyle" select="$subHeaderStyle"/>
          <xsl:with-param name="docid" select="$docid"/>
          <xsl:with-param name="level">entitylevel</xsl:with-param>
          <xsl:with-param name="entitytype" select="$entitytype"/>
          <xsl:with-param name="entityindex" select="$entityindex"/>
          <xsl:with-param name="physicalindex" select="$physicalindex"/>
          <xsl:with-param name="distributionindex" select="position()"/>
        </xsl:call-template>
      </xsl:for-each>
    </xsl:if>

  </xsl:template>

  <xsl:template name="physicalobjectName">
    <xsl:param name="physicalfirstColStyle"/>
    <xsl:for-each select="objectName">
      <tr>
        <td class="{$physicalfirstColStyle}">
        Object Name:</td>
        <td class="{$secondColStyle}">
        <xsl:value-of select="."/></td>
      </tr>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="physicalsize">
    <xsl:param name="physicalfirstColStyle"/>
    <xsl:for-each select="size">
      <tr>
        <td class="{$physicalfirstColStyle}">
        Size:</td>
        <td class="{$secondColStyle}">
        <xsl:value-of select="."/><xsl:text> </xsl:text><xsl:value-of select="./@unit"/></td>
      </tr>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="physicalauthentication">
    <xsl:param name="physicalfirstColStyle"/>
    <xsl:for-each select="authentication">
      <tr>
        <td class="{$physicalfirstColStyle}">
        Authentication:</td>
        <td class="{$secondColStyle}">
          <xsl:value-of select="."/><xsl:text> </xsl:text>
          <xsl:if test="./@method">
            Caculated By<xsl:text> </xsl:text><xsl:value-of select="./@method"/>
          </xsl:if>
        </td>
      </tr>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="physicalcompressionMethod">
    <xsl:param name="physicalfirstColStyle"/>
    <xsl:for-each select="compressionMethod">
      <tr>
        <td class="{$physicalfirstColStyle}">
        Compression Method:</td>
        <td class="{$secondColStyle}">
        <xsl:value-of select="."/></td>
      </tr>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="physicalencodingMethod">
    <xsl:param name="physicalfirstColStyle"/>
    <xsl:for-each select="encodingMethod">
      <tr>
        <td class="{$physicalfirstColStyle}">
        Encoding Method:</td>
        <td class="{$secondColStyle}">
        <xsl:value-of select="."/></td>
      </tr>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="physicalcharacterEncoding">
    <xsl:param name="physicalfirstColStyle"/>
    <xsl:for-each select="characterEncoding">
      <tr>
        <td class="{$physicalfirstColStyle}">
        Character Encoding:</td>
        <td class="{$secondColStyle}">
        <xsl:value-of select="."/></td>
      </tr>
    </xsl:for-each>
  </xsl:template>

  <!--***********************************************************
      TextFormat templates
      ***********************************************************-->

  <xsl:template name="physicaltextFormat">
   <xsl:param name="physicalfirstColStyle"/>
   <xsl:for-each select="dataFormat/textFormat">
      <tr>
        <td class="{$physicalfirstColStyle}">
        Text Format:</td>
        <td>
          <table class="{$tabledefaultStyle}">
            <xsl:apply-templates>
              <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
            </xsl:apply-templates>
          </table>
        </td>
      </tr>

   </xsl:for-each>

  </xsl:template>


  <xsl:template match="numHeaderLines">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$physicalfirstColStyle}">Number of Header Lines:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="numFooterLines">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$physicalfirstColStyle}">Number of Foot Lines:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="recordDelimiter">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$physicalfirstColStyle}">Record Delimiter:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="physicalLineDelimiter">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$physicalfirstColStyle}">Line Delimiter:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="numPhysicalLinesPerRecord">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$physicalfirstColStyle}">Line Number For One Record:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="maxRecordLength">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$physicalfirstColStyle}">Maximum Record Length:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="attributeOrientation">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$physicalfirstColStyle}">Maximum Record Length:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="simpleDelimited">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$physicalfirstColStyle}">Simple Delimited:</td>
        <td >
          <table class="{$tabledefaultStyle}">
            <xsl:apply-templates>
              <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
            </xsl:apply-templates>
          </table>
        </td>
        </tr>
  </xsl:template>

  <xsl:template match="complex">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$physicalfirstColStyle}">Complex Delimited:</td>
        <td >
           <table class="{$tabledefaultStyle}">
             <xsl:call-template name="textFixed">
                <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
             </xsl:call-template>
             <xsl:call-template name="textDelimited">
               <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
             </xsl:call-template>
           </table>
         </td>
        </tr>
  </xsl:template>


  <xsl:template name="textFixed">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$physicalfirstColStyle}">Text Fixed:</td>
        <td >
          <table class="{$tabledefaultStyle}">
            <xsl:apply-templates>
              <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
            </xsl:apply-templates>
          </table>
        </td>
        </tr>
  </xsl:template>

  <xsl:template name="textDelimited">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$physicalfirstColStyle}">Text Delimited:</td>
        <td >
          <table class="{$tabledefaultStyle}">
            <xsl:apply-templates>
              <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
            </xsl:apply-templates>
          </table>
        </td>
        </tr>
  </xsl:template>

  <xsl:template match="quoteCharacter">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$firstColStyle}">Quote Character:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

   <xsl:template match="literalCharacter">
       <xsl:param name="physicalfirstColStyle"/>
       <tr>
        <td class="{$firstColStyle}">Literal Character:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>


  <xsl:template match="fieldDelimiter">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$firstColStyle}">Field Delimeter:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="fieldWidth">
        <xsl:param name="physicalfirstColStyle"/>
        <tr><td class="{$firstColStyle}">Field Width:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="lineNumber">
        <xsl:param name="physicalfirstColStyle"/>
        <tr><td class="{$firstColStyle}">Line Number:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="fieldStartColumn">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$firstColStyle}">Field Start Column:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>


  <!--***********************************************************
      externallyDefinedFormat templates
      ***********************************************************-->
 <xsl:template name="physicalexternallyDefinedFormat">
    <xsl:param name="physicalfirstColStyle"/>
    <xsl:for-each select="dataFormat/externallyDefinedFormat">
      <tr>
        <td class="{$physicalfirstColStyle}">
        Externally Defined Format:</td>
        <td>
          <table class="{$tabledefaultStyle}">
            <xsl:apply-templates>
              <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
            </xsl:apply-templates>
          </table>
        </td>
      </tr>
    </xsl:for-each>
  </xsl:template>
  <xsl:template match="formatName">
    <xsl:param name="physicalfirstColStyle"/>
    <xsl:if test="normalize-space(.)!=''">
        <tr>
        <td class="{$firstColStyle}">Format Name:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
    </xsl:if>
  </xsl:template>

  <xsl:template match="formatVersion">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$firstColStyle}">Format Version:</td>
        <td class="{$secondColStyle}"><xsl:value-of select="."/></td>
        </tr>
  </xsl:template>

  <xsl:template match="citation">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$physicalfirstColStyle}">Citation: </td>
        <td>
          <xsl:call-template name="citation">
            <xsl:with-param name="citationfirstColStyle" select="$physicalfirstColStyle"/>
             <xsl:with-param name="citationsubHeaderStyle" select="$subHeaderStyle"/>
          </xsl:call-template>
        </td>
        </tr>
  </xsl:template>

  <!--***********************************************************
      binaryRasterFormat templates
      ***********************************************************-->
  <xsl:template name="physicalbinaryRasterFormat">
    <xsl:param name="physicalfirstColStyle"/>
    <xsl:for-each select="dataFormat/binaryRasterFormat">
      <tr>
        <td class="{$physicalfirstColStyle}">
        Binary Raster Format:</td>
        <td>
           <table class="{$tabledefaultStyle}">
             <xsl:apply-templates>
               <xsl:with-param name="physicalfirstColStyle" select="$physicalfirstColStyle"/>
             </xsl:apply-templates>
           </table>
        </td>
      </tr>

   </xsl:for-each>
  </xsl:template>

  <xsl:template match="rowColumnOrientation">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$firstColStyle}">Orientation:</td>
        <td class="{$secondColStyle}">
          <xsl:value-of select="."/>
        </td>
        </tr>
  </xsl:template>

  <xsl:template match="multiBand">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$firstColStyle}">Multiple Bands:</td>
        <td>
         <table class="{$tabledefaultStyle}">
            <tr>
                <td class="{$firstColStyle}">Number of Spectral Bands:</td>
                <td class="{$secondColStyle}">
                <xsl:value-of select="./nbands"/>
              </td>
            </tr>
            <tr>
               <td class="{$firstColStyle}">Layout:</td>
               <td class="{$secondColStyle}">
               <xsl:value-of select="./layout"/>
               </td>
           </tr>
        </table>
        </td>
        </tr>
  </xsl:template>


  <xsl:template match="nbits">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$firstColStyle}">Number of Bits (/pixel/band):</td>
        <td class="{$secondColStyle}">
          <xsl:value-of select="."/>
        </td>
        </tr>
  </xsl:template>

  <xsl:template match="byteorder">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$firstColStyle}">Byte Order:</td>
        <td class="{$secondColStyle}">
          <xsl:value-of select="."/>
        </td>
        </tr>
  </xsl:template>

  <xsl:template match="skipbytes">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$firstColStyle}">Skipped Bytes:</td>
        <td class="{$secondColStyle}">
          <xsl:value-of select="."/>
        </td>
        </tr>
  </xsl:template>

  <xsl:template match="bandrowbytes">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$firstColStyle}">Number of Bytes (/band/row):</td>
        <td class="{$secondColStyle}">
          <xsl:value-of select="."/>
        </td>
        </tr>
  </xsl:template>

  <xsl:template match="totalrowbytes">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$firstColStyle}">Total Number of Byte (/row):</td>
        <td class="{$secondColStyle}">
          <xsl:value-of select="."/>
        </td>
        </tr>
  </xsl:template>

  <xsl:template match="bandgapbytes">
        <xsl:param name="physicalfirstColStyle"/>
        <tr>
        <td class="{$firstColStyle}">Number of Bytes between Bands:</td>
        <td class="{$secondColStyle}">
          <xsl:value-of select="."/>
        </td>
        </tr>
  </xsl:template>

</xsl:stylesheet>
