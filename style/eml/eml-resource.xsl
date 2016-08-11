<?xml version="1.0" encoding="UTF-8"?>
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
  <!--<xsl:import href="eml-party.xsl"/>
  <xsl:import href="eml-distribution.xsl"/>
  <xsl:import href="eml-coverage.xsl"/>-->
  <xsl:output method="html" encoding="UTF-8"
    doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
    indent="yes" />  

  <!-- This module is for resouce and it is self-contained (it is table)-->
  <xsl:template name="resource">
    <xsl:param name="resfirstColStyle"/>
    <xsl:param name="ressubHeaderStyle"/>
    <xsl:param name="creator">Data Set Creator(s):</xsl:param>
  </xsl:template>

  <!-- style the alternate identifier elements -->
  <xsl:template name="resourcealternateIdentifier" >
      <xsl:param name="resfirstColStyle"/>
      <xsl:param name="ressecondColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
      <tr>
        <td class="{$resfirstColStyle}">Alternate Identifier:</td>
        <td class="{$ressecondColStyle}">
        	<xsl:call-template name="i18n">
       			<xsl:with-param name="i18nElement" select="."/>
       		</xsl:call-template>
        </td>
      </tr>
      </xsl:if>
  </xsl:template>


  <!-- style the short name elements -->
  <xsl:template name="resourceshortName">
      <xsl:param name="resfirstColStyle"/>
      <xsl:param name="ressecondColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
      <tr>
        <td class="{$resfirstColStyle}">Short Name:</td>
        <td class="{$ressecondColStyle}">
        	<xsl:call-template name="i18n">
       			<xsl:with-param name="i18nElement" select="."/>
       		</xsl:call-template>
        </td>
      </tr>
      </xsl:if>
  </xsl:template>


  <!-- style the title element -->
  <xsl:template name="resourcetitle" >
      <xsl:param name="resfirstColStyle"/>
      <xsl:param name="ressecondColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
      <tr>
        <td class="{$resfirstColStyle}">Title:</td>
        <td class="{$ressecondColStyle}">
          <em class="bold">
       		<xsl:call-template name="i18n">
       			<xsl:with-param name="i18nElement" select="."/>
       		</xsl:call-template>	
          </em>
        </td>
      </tr>
      </xsl:if>
  </xsl:template>

  <xsl:template name="resourcecreator" >
      <xsl:param name="resfirstColStyle"/>
      <tr><td colspan="2">
       <xsl:call-template name="party">
              <xsl:with-param name="partyfirstColStyle" select="$resfirstColStyle"/>
       </xsl:call-template>
      </td></tr>
   </xsl:template>

  <xsl:template name="resourcemetadataProvider" >
      <xsl:param name="resfirstColStyle"/>
      <tr><td colspan="2">
      <xsl:call-template name="party">
            <xsl:with-param name="partyfirstColStyle" select="$resfirstColStyle"/>
      </xsl:call-template>
      </td></tr>
  </xsl:template>

  <xsl:template name="resourceassociatedParty">
      <xsl:param name="resfirstColStyle"/>
      <tr><td colspan="2">
      <xsl:call-template name="party">
          <xsl:with-param name="partyfirstColStyle" select="$resfirstColStyle"/>
      </xsl:call-template>
      </td></tr>
  </xsl:template>


  <xsl:template name="resourcepubDate">
      <xsl:param name="resfirstColStyle"/>
      <xsl:if test="normalize-space(../pubDate)!=''">
      <tr><td class="{$resfirstColStyle}">
        Publication Date:</td><td class="{$secondColStyle}">
        <xsl:value-of select="../pubDate"/></td></tr>
      </xsl:if>
  </xsl:template>


  <xsl:template name="resourcelanguage">
      <xsl:param name="resfirstColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
      <tr>
      	<td class="{$resfirstColStyle}">
        	Language:
        </td>
        <td class="{$secondColStyle}">
        	<xsl:call-template name="i18n">
       			<xsl:with-param name="i18nElement" select="."/>
       		</xsl:call-template>
        </td>
        </tr>
      </xsl:if>
  </xsl:template>


  <xsl:template name="resourceseries">
      <xsl:param name="resfirstColStyle"/>
      <xsl:if test="normalize-space(../series)!=''">
      <tr><td class="{$resfirstColStyle}">
        Series:</td><td class="{$secondColStyle}">
        <xsl:value-of select="../series"/></td></tr>
      </xsl:if>
  </xsl:template>


  <xsl:template name="resourceabstract">
     <xsl:param name="resfirstColStyle"/>
     <xsl:param name="ressecondColStyle"/>
     <tr>
       <td class="{$resfirstColStyle}"><xsl:text>Abstract:</xsl:text></td>
       <td>
         <xsl:call-template name="text">
           <xsl:with-param name="textfirstColStyle" select="$resfirstColStyle"/>
           <xsl:with-param name="textsecondColStyle" select="$ressecondColStyle"/>
         </xsl:call-template>
       </td>
     </tr>
  </xsl:template>


  <!--<xsl:template match="keywordSet[1]" mode="resource">
        <xsl:param name="ressubHeaderStyle"/>
        <xsl:param name="resfirstColStyle"/>
        <tr><td class="{$ressubHeaderStyle}" colspan="2">
        <xsl:text>Keywords:</xsl:text></td></tr>
        <xsl:call-template name="renderKeywordSet">
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
        </xsl:call-template>
  </xsl:template>-->

  <xsl:template name="resourcekeywordSet">
      <xsl:for-each select="keywordThesaurus">
        <xsl:if test="normalize-space(.)!=''">
          <xsl:value-of select="."/>
          <xsl:text>: </xsl:text>
        </xsl:if>
        <xsl:if test="normalize-space(keyword)!=''">
        <ul>
          <xsl:for-each select="keyword">
            <li>
            	<xsl:call-template name="i18n">
	       			<xsl:with-param name="i18nElement" select="."/>
	       		</xsl:call-template>
	            <xsl:if test="./@keywordType and normalize-space(./@keywordType)!=''">
	              (<xsl:value-of select="./@keywordType"/>)
	            </xsl:if>
            </li>
          </xsl:for-each>
        </ul>
        </xsl:if>
      </xsl:for-each>
        <xsl:if test="normalize-space(keyword)!=''">
        <ul>
          <xsl:for-each select="keyword">
            <li>
            <xsl:call-template name="i18n">
       			<xsl:with-param name="i18nElement" select="."/>
       		</xsl:call-template>
            <xsl:if test="./@keywordType and normalize-space(./@keywordType)!=''">
              (<xsl:value-of select="./@keywordType"/>)
            </xsl:if>
            </li>
          </xsl:for-each>
        </ul>
        </xsl:if>
  </xsl:template>

   <xsl:template name="resourceadditionalInfo">
     <xsl:param name="ressubHeaderStyle"/>
     <xsl:param name="resfirstColStyle"/>
     <tr><td class="{$ressubHeaderStyle}" colspan="2">
        <xsl:text>Additional Information:</xsl:text>
     </td></tr>
     <tr><td class="{$resfirstColStyle}">&#160;</td>
         <td>
           <xsl:call-template name="text">
             <xsl:with-param name="textfirstColStyle" select="$resfirstColStyle"/>
           </xsl:call-template>
         </td>
     </tr>
  </xsl:template>


   <xsl:template name="resourceintellectualRights">
     <xsl:param name="resfirstColStyle"/>
     <xsl:param name="ressecondColStyle"/>
       <xsl:call-template name="text">
         <xsl:with-param name="textsecondColStyle" select="$ressecondColStyle"/>
       </xsl:call-template>
  </xsl:template>

   <xsl:template name="resourcedistribution">
     <xsl:param name="ressubHeaderStyle"/>
     <xsl:param name="resfirstColStyle"/>
     <xsl:param name="index"/>
     <xsl:param name="docid"/>
     <tr><td colspan="2">
        <xsl:call-template name="distribution">
          <xsl:with-param name="disfirstColStyle" select="$resfirstColStyle"/>
          <xsl:with-param name="dissubHeaderStyle" select="$ressubHeaderStyle"/>
          <xsl:with-param name="level">toplevel</xsl:with-param>
          <xsl:with-param name="distributionindex" select="$index"/>
          <xsl:with-param name="docid" select="$docid"/>
        </xsl:call-template>
     </td></tr>
  </xsl:template>

  <xsl:template name="resourcecoverage">
     <xsl:param name="ressubHeaderStyle"/>
     <xsl:param name="resfirstColStyle"/>
     <tr><td colspan="2">
        <xsl:call-template name="coverage">
        </xsl:call-template>
     </td></tr>
  </xsl:template>
  
	<!-- for displaying any nested translation element for i18nNonEmptyString type -->
	<xsl:template name="i18n">
		<xsl:param name="i18nElement"/>
		<!-- the primary value -->
		<xsl:if test="$i18nElement/text() != ''">
			<xsl:if test="./@xml:lang != ''">
				(<xsl:value-of select="./@xml:lang"/>)
			</xsl:if>
			<xsl:value-of select="$i18nElement/."/>
		</xsl:if>
		<!-- any translations -->
		<xsl:if test="count($i18nElement/value) > 0">
			<br/>
			<xsl:for-each select="$i18nElement/value">
				<xsl:if test="./@xml:lang != ''">
					(<xsl:value-of select="./@xml:lang"/>)
				</xsl:if>
				<xsl:value-of select="."/>
				<xsl:if test="position() != last()">
					<br/>
				</xsl:if>	
			</xsl:for-each>
		</xsl:if>
	</xsl:template>


</xsl:stylesheet>
