<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-settings.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2002-05-01 01:02:19 $'
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
  * This is an XSLT (http://www.w3.org/TR/xslt) stylesheet that provides a 
  * single, central location for setting all installation-specific paths for 
  * XSLT stylesheets.  It is intended to be imported (using the 
  * <xsl:import href="..." /> element) into other XSLT stylesheets used in the
  * transformation of xml files that are valid with respect to the 
  * eml-variable.dtd module of the Ecological Metadata Language (EML).
  
  * Some of these paths incorporate values of the form: @token-name@; these are 
  * intended to allow an Ant (http://jakarta.apache.org/ant/index.html) build 
  * script to replace the tokens automatically with the correct values at build/
  * install time.  If Ant is not used, the tokens may simply be edited by hand 
  * to point to the correct resources.   
  * Note that the values given below may be overridden by passing parameters to 
  * the XSLT processor, although the procedure for doing so is vendor-specific.
  * 
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<!-- the filename (not the path) of the default css stylesheet to be used-->
  <xsl:param name="qformat">default</xsl:param>
  
<!-- 
  * the path of the directory where the XSL and CSS files reside - starts with 
  * context name, eg: /myContextRoot/style  Needs leading slash but not 
  * trailing slash 
-->

<!-- <xsl:param name="stylePath">@style-path@</xsl:param> -->
  <xsl:param name="stylePath">/brooke/style</xsl:param>  

  
<!-- 
  * the base URI to be used for the href link to each document in a 
  * "subject-relationaship-object" triple
-->

<!-- <xsl:param name="tripleURI"><![CDATA[@html-path@/metacat?action=read&qformat=knb&docid=]]></xsl:param> -->
  <xsl:param name="tripleURI"><![CDATA[/brooke/servlet/metacat?action=read&qformat=knb&docid=]]></xsl:param>  
  
  
  <xsl:param name="subHeaderStyle" select="'tablehead'"/>
  <xsl:param name="firstColWidth" select="'15%'"/>
  <xsl:param name="firstColStyle" select="'highlight'"/>
  <xsl:param name="secondColWidth" select="'85%'"/>
  <xsl:param name="secondColStyle" select="''"/>  
  <xsl:param name="secondColIndent" select="'10%'"/> 

</xsl:stylesheet>
