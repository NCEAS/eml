<?xml version="1.0" encoding="utf-8"?>
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

  <xsl:output method="html" encoding="UTF-8"
    doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
    indent="yes" />  

<!-- This module is for text module in eml2 document. It is a table and self contained-->

  <xsl:template name="text">
        <xsl:param name="textfirstColStyle" />
        <xsl:param name="textsecondColStyle" />
        <xsl:if test="(section and normalize-space(section)!='') or (para and normalize-space(para)!='') or (.!='')">
          <xsl:apply-templates mode="text">
            <xsl:with-param name="textfirstColStyle" select="$textfirstColStyle"/>
            <xsl:with-param name="textsecondColStyle" select="$textsecondColStyle" />
          </xsl:apply-templates>
      </xsl:if>
  </xsl:template>


  <!-- *********************************************************************** -->
  <!-- Template for section-->
   <xsl:template match="section" mode="text">
      <xsl:if test="normalize-space(.)!=''">
      	<div class="sectionText">
        <xsl:if test="title and normalize-space(title)!=''">
              <h4 class="bold"><xsl:value-of select="title"/></h4>
        </xsl:if>
        <xsl:if test="para and normalize-space(para)!=''">
              <xsl:apply-templates select="para" mode="lowlevel"/>
         </xsl:if>
         <xsl:if test="section and normalize-space(section)!=''">
              <xsl:apply-templates select="section" mode="lowlevel"/>
        </xsl:if>
        </div>
      </xsl:if>
  </xsl:template>

  <!-- Section template for low level. Cteate a nested table and second column -->
  <xsl:template match="section" mode="lowlevel">
     <div class="section">
      <xsl:if test="title and normalize-space(title)!=''">
        <h4 class="bold"><xsl:value-of select="title"/></h4>
      </xsl:if>
      <xsl:if test="para and normalize-space(para)!=''">
        <xsl:apply-templates select="para" mode="lowlevel"/>
      </xsl:if>
      <xsl:if test="section and normalize-space(section)!=''">
        <xsl:apply-templates select="section" mode="lowlevel"/>
      </xsl:if>
     </div>
  </xsl:template>

  <!-- para template for text mode-->
   <xsl:template match="para" mode="text">
    	<xsl:param name="textfirstColStyle"/>
    	<div class="para">
   			<xsl:apply-templates mode="lowlevel"/>
   		</div>	
  </xsl:template>

  <!-- para template without table structure. It does actually transfer.
       Currently, only get the text and it need more revision-->
  <xsl:template match="para" mode="lowlevel">
      <xsl:if test="normalize-space(./text()) = ''">
      	<xsl:apply-templates mode="lowlevel"/>
      </xsl:if>
      <p>
      	<xsl:call-template name="i18n">
   			<xsl:with-param name="i18nElement" select="."/>
   		</xsl:call-template>
      </p>
  </xsl:template>
  
  <!-- match any translation values -->
  <xsl:template match="value" mode="lowlevel">
      <span class="translation">
		<!-- the primary value -->
		<xsl:if test="./text() != ''">
			<xsl:if test="./@xml:lang != ''">
				(<xsl:value-of select="./@xml:lang"/>)
			</xsl:if>
			<xsl:value-of select="./text()"/>
		</xsl:if>
      </span>
  </xsl:template>

</xsl:stylesheet>
