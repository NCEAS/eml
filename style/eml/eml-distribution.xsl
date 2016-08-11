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
  <xsl:import href="eml-text.xsl" />
  <xsl:output method="html" encoding="UTF-8"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  


<!-- This module is for distribution and it is self-contained-->

  <xsl:template name="distribution">
      <xsl:param name="disfirstColStyle"/>
      <xsl:param name="dissubHeaderStyle"/>
      <xsl:param name="docid"/>
      <xsl:param name="level">entitylevel</xsl:param>
      <xsl:param name="entitytype"/>
      <xsl:param name="entityindex"/>
      <xsl:param name="physicalindex"/>
      <xsl:param name="distributionindex"/>
      <table class="{$tabledefaultStyle}">
       <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:apply-templates select="online">
              <xsl:with-param name="dissubHeaderStyle" select="$dissubHeaderStyle"/>
              <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
            </xsl:apply-templates>
            <xsl:apply-templates select="offline">
              <xsl:with-param name="dissubHeaderStyle" select="$dissubHeaderStyle"/>
              <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
            </xsl:apply-templates>
            <xsl:apply-templates select="inline">
              <xsl:with-param name="dissubHeaderStyle" select="$dissubHeaderStyle"/>
              <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
               <xsl:with-param name="docid" select="$docid"/>
               <xsl:with-param name="level" select="$level"/>
               <xsl:with-param name="entitytype" select="$entitytype"/>
               <xsl:with-param name="entityindex" select="$entityindex"/>
               <xsl:with-param name="physicalindex" select="$physicalindex"/>
               <xsl:with-param name="distributionindex" select="$distributionindex"/>
             </xsl:apply-templates>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
            <xsl:apply-templates select="online">
              <xsl:with-param name="dissubHeaderStyle" select="$dissubHeaderStyle"/>
              <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
            </xsl:apply-templates>
            <xsl:apply-templates select="offline">
              <xsl:with-param name="dissubHeaderStyle" select="$dissubHeaderStyle"/>
              <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
            </xsl:apply-templates>
            <xsl:apply-templates select="inline">
              <xsl:with-param name="dissubHeaderStyle" select="$dissubHeaderStyle"/>
              <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
               <xsl:with-param name="docid" select="$docid"/>
               <xsl:with-param name="level" select="$level"/>
               <xsl:with-param name="entitytype" select="$entitytype"/>
               <xsl:with-param name="entityindex" select="$entityindex"/>
               <xsl:with-param name="physicalindex" select="$physicalindex"/>
               <xsl:with-param name="distributionindex" select="$distributionindex"/>
            </xsl:apply-templates>
        </xsl:otherwise>
       </xsl:choose>
      </table>
  </xsl:template>

  <!-- ********************************************************************* -->
  <!-- *******************************  Online data  *********************** -->
  <!-- ********************************************************************* -->
  <xsl:template match="online">
    <xsl:param name="disfirstColStyle"/>
    <xsl:param name="dissubHeaderStyle"/>
    <tr><td class="{$dissubHeaderStyle}" colspan="2">
        <xsl:text>Online Distribution Info:</xsl:text>
    </td></tr>
    <xsl:apply-templates select="url">
      <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
    </xsl:apply-templates>
    <xsl:apply-templates select="connection">
      <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
    </xsl:apply-templates>
    <xsl:apply-templates select="connectionDefinition">
      <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
    </xsl:apply-templates>
  </xsl:template>

  <xsl:template match="url">
    <xsl:param name="disfirstColStyle"/>
    <xsl:variable name="URL" select="."/>
    <tr>
      <td class="{$disfirstColStyle}">
        <xsl:text>&#160;</xsl:text>
      </td>
      <td class="{$secondColStyle}">
         <a>
           <xsl:choose>
           <xsl:when test="starts-with($URL,'ecogrid')">
		<xsl:variable name="URL1" select="substring-after($URL, 'ecogrid://')"/>
		<xsl:variable name="docID" select="substring-after($URL1, '/')"/>
		<xsl:attribute name="href"><xsl:value-of select="$tripleURI"/><xsl:value-of select="$docID"/></xsl:attribute>
           </xsl:when>
           <xsl:otherwise>
		<xsl:attribute name="href"><xsl:value-of select="$URL"/></xsl:attribute>
           </xsl:otherwise>
          </xsl:choose>
          <xsl:attribute name="target">_blank</xsl:attribute>
          <xsl:value-of select="."/>
        </a>
        <xsl:if test="$withHTMLLinks = '1'">
	        <!-- stats for hosted documents loaded with ajax call -->
	        <xsl:if test="starts-with($URL,'ecogrid')">
				<xsl:variable name="URL1" select="substring-after($URL, 'ecogrid://')"/>
				<xsl:variable name="docID" select="substring-after($URL1, '/')"/>
				<xsl:variable name="divID">dataStats</xsl:variable>
				<span>
	          		<xsl:attribute name="id">
	          			<xsl:value-of select="$divID" />
	          		</xsl:attribute>
				</span>
				<script language="JavaScript">
					if (window.loadStats) {
						loadStats(
							'<xsl:value-of select="$divID" />', 
							'<xsl:value-of select="$docID" />', 
							'<xsl:value-of select="$contextURL" />/metacat', 
							'<xsl:value-of select="$qformat" />');
					}
				</script>
			</xsl:if>
		</xsl:if>		
       </td>
    </tr>
  </xsl:template>

  <xsl:template match="connection">
    <xsl:param name="disfirstColStyle"/>
    <xsl:choose>
      <xsl:when test="references!=''">
        <xsl:variable name="ref_id" select="references"/>
        <xsl:variable name="references" select="$ids[@id=$ref_id]" />
        <xsl:for-each select="$references">
          <xsl:call-template name="connectionCommon">
            <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle"/>
          </xsl:call-template>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="connectionCommon">
          <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- A template shared by connection references and connection in line-->
  <xsl:template name="connectionCommon">
    <xsl:param name="disfirstColStyle"/>
    <xsl:if test="parameter">
      <tr>
        <td class="{$disfirstColStyle}">
          <xsl:text>Parameter(s):</xsl:text>
        </td>
        <td class="{$secondColStyle}"><xsl:text>&#160;</xsl:text>
        </td>
      </tr>
      <xsl:call-template name="renderParameters">
        <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
      </xsl:call-template>
    </xsl:if>
    <xsl:apply-templates select="connectionDefinition">
      <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
    </xsl:apply-templates>
  </xsl:template>

  <xsl:template name="renderParameters">
    <xsl:param name="disfirstColStyle"/>
    <xsl:for-each select="parameter" >
      <tr>
        <td class="{$disfirstColStyle}">
          <xsl:text>&#160;&#160;&#160;&#160;&#160;</xsl:text><xsl:value-of select="name" />
        </td>
        <td class="{$secondColStyle}">
         <xsl:value-of select="value" />
        </td>
      </tr>
    </xsl:for-each>
  </xsl:template>

   <xsl:template match="connectionDefinition">
    <xsl:param name="disfirstColStyle"/>
    <xsl:choose>
      <xsl:when test="references!=''">
        <xsl:variable name="ref_id" select="references"/>
        <xsl:variable name="references" select="$ids[@id=$ref_id]" />
        <xsl:for-each select="$references">
          <xsl:call-template name="connectionDefinitionCommon">
            <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle"/>
          </xsl:call-template>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="connectionDefinitionCommon">
          <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
   </xsl:template>

   <!-- This template will be shared by both reference and inline connectionDefinition-->
   <xsl:template name="connectionDefinitionCommon">
    <xsl:param name="disfirstColStyle"/>
      <tr>
          <td class="{$disfirstColStyle}">
            <xsl:text>Schema Name:</xsl:text>
          </td>
          <td class="{$secondColStyle}">
            <xsl:value-of select="schemeName" />
          </td>
       </tr>
       <tr>
          <td class="{$disfirstColStyle}">
            <xsl:text>Description:</xsl:text>
          </td>
          <td>
           <xsl:apply-templates select="description">
              <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
            </xsl:apply-templates>
          </td>
       </tr>
       <xsl:for-each select="parameterDefinition">
          <xsl:call-template name="renderParameterDefinition">
            <xsl:with-param name="disfirstColStyle" select="$disfirstColStyle" />
          </xsl:call-template>
       </xsl:for-each>
   </xsl:template>

   <xsl:template match="description">
     <xsl:param name="disfirstColStyle"/>
     <xsl:call-template name="text">
        <xsl:with-param name="textfirstColStyle" select="$secondColStyle" />
     </xsl:call-template>
   </xsl:template>

   <xsl:template name="renderParameterDefinition">
     <xsl:param name="disfirstColStyle"/>
     <tr>
        <td class="{$disfirstColStyle}">
          <xsl:text>&#160;&#160;&#160;&#160;&#160;</xsl:text><xsl:value-of select="name" /><xsl:text>:</xsl:text>
        </td>
        <td>
          <table class="{$tabledefaultStyle}">
            <tr>
              <td class="{$disfirstColStyle}">
                <xsl:choose>
                  <xsl:when test="defaultValue">
                    <xsl:value-of select="defaultValue" />
                  </xsl:when>
                  <xsl:otherwise>
                    &#160;
                  </xsl:otherwise>
                </xsl:choose>

              </td>
              <td class="{$secondColStyle}">
                <xsl:value-of select="definition" />
              </td>
            </tr>
          </table>
        </td>
      </tr>
   </xsl:template>

  <!-- ********************************************************************* -->
  <!-- *******************************  Offline data  ********************** -->
  <!-- ********************************************************************* -->

  <xsl:template match="offline">
    <xsl:param name="disfirstColStyle"/>
    <xsl:param name="dissubHeaderStyle"/>
    <tr><td class="{$dissubHeaderStyle}" colspan="2">
        <xsl:text>Offline Distribution Info:</xsl:text>
    </td></tr>
    <xsl:if test="(mediumName) and normalize-space(mediumName)!=''">
      <tr><td class="{$disfirstColStyle}"><xsl:text>Medium:</xsl:text></td>
      <td class="{$secondColStyle}"><xsl:value-of select="mediumName"/></td></tr>
    </xsl:if>
    <xsl:if test="(mediumDensity) and normalize-space(mediumDensity)!=''">
    <tr><td class="{$disfirstColStyle}"><xsl:text>Medium Density:</xsl:text></td>
    <td class="{$secondColStyle}"><xsl:value-of select="mediumDensity"/>
    <xsl:if test="(mediumDensityUnits) and normalize-space(mediumDensityUnits)!=''">
    <xsl:text> (</xsl:text><xsl:value-of select="mediumDensityUnits"/><xsl:text>)</xsl:text>
    </xsl:if>
    </td></tr>
    </xsl:if>
    <xsl:if test="(mediumVol) and normalize-space(mediumVol)!=''">
    <tr><td class="{$disfirstColStyle}"><xsl:text>Volume:</xsl:text></td>
    <td class="{$secondColStyle}"><xsl:value-of select="mediumVol"/></td></tr>
    </xsl:if>
    <xsl:if test="(mediumFormat) and normalize-space(mediumFormat)!=''">
    <tr><td class="{$disfirstColStyle}"><xsl:text>Format:</xsl:text></td>
    <td class="{$secondColStyle}"><xsl:value-of select="mediumFormat"/></td></tr>
    </xsl:if>
    <xsl:if test="(mediumNote) and normalize-space(mediumNote)!=''">
    <tr><td class="{$disfirstColStyle}"><xsl:text>Notes:</xsl:text></td>
    <td class="{$secondColStyle}"><xsl:value-of select="mediumNote"/></td></tr>
    </xsl:if>
  </xsl:template>

  <!-- ********************************************************************* -->
  <!-- *******************************  Inline data  *********************** -->
  <!-- ********************************************************************* -->


  <xsl:template match="inline">
    <xsl:param name="disfirstColStyle"/>
    <xsl:param name="dissubHeaderStyle"/>
    <xsl:param name="docid"/>
    <xsl:param name="level">entity</xsl:param>
    <xsl:param name="entitytype"/>
    <xsl:param name="entityindex"/>
    <xsl:param name="physicalindex"/>
    <xsl:param name="distributionindex"/>

    <tr><td class="{$dissubHeaderStyle}" colspan="2">
        <xsl:text>Inline Data:</xsl:text>
    </td></tr>
    <tr><td class="{$disfirstColStyle}">
      <xsl:text>&#160;</xsl:text></td>
      <td class="{$secondColStyle}">
      <!-- for top top distribution-->
      <xsl:if test="$level='toplevel'">
        <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/>&amp;displaymodule=inlinedata&amp;distributionlevel=<xsl:value-of select="$level"/>&amp;distributionindex=<xsl:value-of select="$distributionindex"/></xsl:attribute>
        <b>Inline Data</b></a>
      </xsl:if>
      <xsl:if test="$level='entitylevel'">
        <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/>&amp;displaymodule=inlinedata&amp;distributionlevel=<xsl:value-of select="$level"/>&amp;entitytype=<xsl:value-of select="$entitytype"/>&amp;entityindex=<xsl:value-of select="$entityindex"/>&amp;physicalindex=<xsl:value-of select="$physicalindex"/>&amp;distributionindex=<xsl:value-of select="$distributionindex"/></xsl:attribute>
        <b>Inline Data</b></a>
      </xsl:if>
     </td></tr>
  </xsl:template>


</xsl:stylesheet>
