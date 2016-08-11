<?xml version="1.0" encoding="UTF-8"?>
<!--
  *  '$RCSfile$'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: barteau $'
  *     '$Date: 2008-01-15 17:12:44 -0800 (Tue, 15 Jan 2008) $'
  * '$Revision: 3689 $'
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
  *****************************************************************************
  *
  * This is an XSLT (http://www.w3.org/TR/xslt) stylesheet that provides a
  * single, central location for setting all installation-specific paths for
  * XSLT stylesheets.  It is intended to be imported (using the
  * <xsl:import href="..." /> element) into other XSLT stylesheets used in the
  * transformation of xml files that are valid with respect to the
  * applicable dtd of the Ecological Metadata Language (EML).

  * Some of these paths incorporate values of the form: @token-name@; these are
  * intended to allow an Ant (http://jakarta.apache.org/ant/index.html) build
  * script to replace the tokens automatically with the correct values at build/
  * install time.  If Ant is not used, the tokens may simply be edited by hand
  * to point to the correct resources.
  * Note that the values given below may be overridden by passing parameters to
  * the XSLT processor programatically, although the procedure for doing so is
  * vendor-specific.  Note also that these parameter definitions will be overridden
  * by any identical parameter names declared within xsl stylesheets that import
  * this stylesheet.
  *
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">


<!--
    /**
    *   The filename of the default css stylesheet to be used
    *   (filename only - not the whole path, and no ".css" extension.  The
    *   example below would look for a file named "default.css" in the same
    *   directory as the stylesheets
    */
-->

  <xsl:param name="qformat">default</xsl:param>


<!--
    /**
    *   The module which need to be display in eml2 document. The default
    *   value is dataset
    */
-->
  <xsl:param name="displaymodule">dataset</xsl:param>


<!--
    /**
    *   To show the links for the Entities in the dataset display module.
    */
-->
  <xsl:param name="withEntityLinks">1</xsl:param>


<!--
    /**
    *   To show the link for Additional Metadata in the dataset display module.
    */
-->
  <xsl:param name="withAdditionalMetadataLink">1</xsl:param>


<!--
    /**
    *   To show the link for the Original XML in the dataset display module.
    */
-->
  <xsl:param name="withOriginalXMLLink">1</xsl:param>


<!--
    /**
    *   To show any html links (emails and webpage).
    */
-->
  <xsl:param name="withHTMLLinks">1</xsl:param>

<!--
    /**
    *   To show the Attributes table in the entity display.
    */
-->
  <xsl:param name="withAttributes">1</xsl:param>

<!--
    /**
    *   To insert templates where header and footer are defined.
    */
-->
  <xsl:param name="insertTemplate">1</xsl:param>


<!--the docid of xml which is processed-->
    <xsl:param name="docid"/>
<!-- type of entity, data table or spacial raster or others-->
    <xsl:param name="entitytype"></xsl:param>
<!-- the index of entity in same entity type -->
    <xsl:param name="entityindex"/>
<!-- the index of attribute in same entity -->
    <xsl:param name="attributeindex"/>
<!-- the index of physical part in entity part-->
    <xsl:param name="physicalindex"/>
<!-- the index of distribution in physical part  -->
    <xsl:param name="distributionindex"/>
<!-- the levle of distribution -->
    <xsl:param name="distributionlevel"/>
<!-- the index of additional metadata-->
    <xsl:param name="additionalmetadataindex">1</xsl:param>
<!-- attribute set to get rid of cell spacing-->
    <xsl:attribute-set name="cellspacing">
      <xsl:attribute name="cellpadding">0</xsl:attribute>
      <xsl:attribute name="cellspacing">0</xsl:attribute>
    </xsl:attribute-set>

<!-- determines whether to use metacat or LSID identiers-->
	<xsl:param name="lsidauthority"></xsl:param>
<!-- the url for the data registry of the dataset-->
	<xsl:param name="registryurl"></xsl:param>
<!-- the name of the data registry of the dataset-->
	<xsl:param name="registryname"></xsl:param>

  
<!-- for getting the server context and cgi locations -->  
  <xsl:param name="contextURL"/>
  <xsl:param name="cgi-prefix"/>
  
<!-- for access to "protected" documents/files -->  
  <xsl:param name="sessionid"/>
  
  
<!--
    /**
    *   The base URI to be used for the href link to each document in a
    *   "subject-relationaship-object" triple
    *
    *   EXAMPLE:
    *       <xsl:param name="tripleURI">
    *         <![CDATA[/brooke/servlet/metacat?action=read&qformat=knb&docid=]]>
    *       </xsl:param>
    *
    *   (Note in the above case the "qformat=knb" parameter in the url; a system
    *   could pass this parameter to the XSLT engine to override the local
    *   <xsl:param name="qformat"> tags defined earlier in this document.)
    */
-->

  <xsl:param name="tripleURI"><xsl:value-of select="$contextURL" /><![CDATA[/metacat?action=read&qformat=]]><xsl:value-of select="$qformat" /><![CDATA[&sessionid=]]><xsl:value-of select="$sessionid" /><![CDATA[&docid=]]></xsl:param>

    <!-- URL for xmlformat-->
    <xsl:param name="xmlURI"><xsl:value-of select="$contextURL" /><![CDATA[/metacat?action=read&qformat=xml]]><![CDATA[&sessionid=]]><xsl:value-of select="$sessionid" /><![CDATA[&docid=]]></xsl:param>


<!--
    /**
    *   Most of the html pages are currently laid out as a 2-column table, with
    *   highlights for more-major rows containing subsection titles etc.
    *   The following parameters are used within the
    *           <td width="whateverWidth" class="whateverClass">
    *   tags to define the column widths and (css) styles.
    *
    *   The values of the "xxxColWidth" parameters can be percentages (need to
    *   include % sign) or pixels (number only). Note that if a width is defined
    *   in the CSS stylesheet (see next paragraph), it will override this local
    *   width setting in browsers newer than NN4
    *
    *   The values of the "xxxColStyle" parameters refer to style definitions
    *   listed in the *.css stylesheet that is defined in this xsl document,
    *   above (in the <xsl:param name="qformat"> tag).
    *
    *   (Note that if the "qformat" is changed from the default by passing a
    *   value in the url (see notes for <xsl:param name="qformat"> tag, above),
    *   then the params below must match style names in the "new" CSS stylesheet
    */
-->

<!--    the style for major rows containing subsection titles etc. -->
  <xsl:param name="subHeaderStyle" select="'tablehead'"/>

<!--    the style for major rows containing links, such as additional metadata,
        original xml file etc. -->
  <xsl:param name="linkedHeaderStyle" select="'linkedHeaderStyle'"/>

<!--    the width for the first column (but see note above) -->
  <xsl:param name="firstColWidth" select="'15%'"/>

<!-- the style for the first column -->
  <xsl:param name="firstColStyle" select="'rowodd'"/>

<!--    the width for the second column (but see note above) -->
  <xsl:param name="secondColWidth" select="'85%'"/>

<!-- the style for the second column -->
  <xsl:param name="secondColStyle" select="'roweven'"/>

<!-- the style for the attribute table -->
  <xsl:param name="tableattributeStyle" select="'tableattribute'"/>

<!-- the style for the border -->
  <xsl:param name="borderStyle" select="'bordered'"/>

<!-- the style for the even col in attributes table -->
  <xsl:param name="colevenStyle" select="'coleven'"/>

<!-- the style for the inner even col in attributes table -->
  <xsl:param name="innercolevenStyle" select="'innercoleven'"/>

<!-- the style for the odd col in attributes table -->
  <xsl:param name="coloddStyle" select="'colodd'"/>

<!-- the style for the inner odd col in attributes table -->
  <xsl:param name="innercoloddStyle" select="'innercolodd'"/>


<!-- the default alignment style for the wrapper around the main tables -->
  <!--
  <xsl:param name="mainTableAligmentStyle" select="'mainTableAligmentStyle'"/>
  -->
  <xsl:param name="mainTableAligmentStyle" select="'content'"/>

<!-- the default style for the main container table -->
  <xsl:param name="mainContainerTableStyle" select="'group group_border'"/>

<!-- the default style for all other tables -->
  <xsl:param name="tabledefaultStyle" select="'subGroup subGroup_border onehundred_percent'"/>

<!-- the style for table party -->
  <xsl:param name="tablepartyStyle" select="'tableparty'"/>

<!-- Some html pages use a nested table in the second column.
     Some of these nested tables set their first column to
     the following width: -->
  <xsl:param name="secondColIndent" select="'10%'"/>

<!-- the first column width of attribute table-->
  <xsl:param name="attributefirstColWidth" select="'15%'"/>

<!--
   /**
    *   the path of the directory where the XSL and CSS files reside - starts
    *   with context name, eg: /myContextRoot/styleDirectory.
    *   (As found in "http://hostname:port/myContextRoot/styleDirectory").
    *   Needs leading slash but not trailing slash
    *
    *   EXAMPLE:
    *       <xsl:param name="stylePath">/brooke/style</xsl:param>
    */
-->

    <xsl:param name="stylePath"><xsl:value-of select="$contextURL" />/style/skins</xsl:param>


<!--
   /*
    *   the path of the directory where the common javascript and css files
    *   reside - i.e the files that are not skin-specific. Starts
    *   with context name, eg: /myContextRoot/styleCommonDirectory.
    *   (As found in "http://hostname:port/myContextRoot/styleCommonDirectory").
    *
    *   EXAMPLE
    *       <xsl:param name="styleCommonPath">/brooke/style/common</xsl:param>
    */
-->

    <xsl:param name="styleCommonPath"><xsl:value-of select="$contextURL" />/style/common</xsl:param>

</xsl:stylesheet>
