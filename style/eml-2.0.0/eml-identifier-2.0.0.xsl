<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-identifier-2.0.0.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2003-12-06 01:43:31 $'
  * '$Revision: 1.4 $'
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
    
    <xsl:template name="identifier">
        <xsl:param name="packageID"/>
        <xsl:param name="system"/>
        <xsl:if test="normalize-space(.)">
           <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Identifier:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
             <xsl:value-of select="$packageID"/></td></tr>
             <xsl:if test="normalize-space(../@system)!=''">
                <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
                  Catalog System:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
                  <xsl:value-of select="$system"/></td></tr>
             </xsl:if>
        </xsl:if>
    </xsl:template>
    
 </xsl:stylesheet>
