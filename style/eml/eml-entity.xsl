<?xml version="1.0"?>
<!--
  *  '$RCSfile$'
  *      Authors: Jivka Bojilova
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
  * convert an XML file that is valid with respect to the eml-file.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format
  * suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
 

  <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  
  <!-- This module only provide some templates. They can be called by other templates-->


  <xsl:template name="entityName">
    <xsl:param name="entityfirstColStyle"/>
    <tr><td class="{$entityfirstColStyle}">
    Name:</td><td class="{$secondColStyle}">
    <b><xsl:value-of select="."/></b></td></tr>
  </xsl:template>
  
  <xsl:template name="entityalternateIdentifier">
     <xsl:param name="entityfirstColStyle"/>
     <tr><td class="{$entityfirstColStyle}">
            Identifier:</td><td class="{$secondColStyle}">
            <xsl:value-of select="."/></td></tr>
  </xsl:template>
  
  <xsl:template name="entityDescription">
      <xsl:param name="entityfirstColStyle"/> 
      <tr><td class="{$entityfirstColStyle}">
      Description:</td><td class="{$secondColStyle}">
      <xsl:value-of select="."/></td></tr>
  </xsl:template>
  
  <xsl:template name="entityadditionalInfo">
      <xsl:param name="entityfirstColStyle"/> 
      <tr><td class="{$entityfirstColStyle}">
      Additional Info:</td><td>
        <xsl:call-template name="text"/>
      </td></tr>
  </xsl:template>
  

</xsl:stylesheet>
