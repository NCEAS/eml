<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-resource.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: jones $'
  *     '$Date: 2005-12-13 20:03:23 $'
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
  <!--<xsl:import href="eml-party.xsl"/>
  <xsl:import href="eml-distribution.xsl"/>
  <xsl:import href="eml-coverage.xsl"/>-->
  <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  

  <!-- This module is for resouce and it is self-contained (it is table)-->
  <xsl:template name="resource">
    <xsl:param name="resfirstColStyle"/>
    <xsl:param name="ressubHeaderStyle"/>
	<xsl:param name="hideCitationInfo"/>
    <xsl:param name="creator">Data Set Owner(s):</xsl:param>
    <table xsl:use-attribute-sets="cellspacing" class="{$tabledefaultStyle}" width="100%">
      <xsl:for-each select="alternateIdentifier">
        <xsl:call-template name="resourcealternateIdentifier">
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
        </xsl:call-template>
      </xsl:for-each>

      <xsl:for-each select="shortName">
        <xsl:call-template name="resourceshortName">
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
         </xsl:call-template>
      </xsl:for-each>
	  
	  
	  <xsl:if test="not($hideCitationInfo)">
      	<xsl:for-each select="title">
        	<xsl:call-template name="resourcetitle">
				<xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
				<xsl:with-param name="ressubHeaderStyle" select="$ressubHeaderStyle"/>
			</xsl:call-template>
		</xsl:for-each>

        <xsl:for-each select="pubDate">
			<xsl:call-template name="resourcepubDate" >
          		<xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
			</xsl:call-template>
		</xsl:for-each>
	 </xsl:if>
	 
	 
      <xsl:for-each select="language">
        <xsl:call-template name="resourcelanguage" >
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
         </xsl:call-template>
      </xsl:for-each>

      <xsl:for-each select="series">
        <xsl:call-template name="resourceseries" >
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
        </xsl:call-template>
      </xsl:for-each>

      <xsl:if test="creator">
        <tr><td class="{$ressubHeaderStyle}" colspan="2">
        <xsl:value-of select="$creator"/>
      </td></tr>
      </xsl:if>
      <xsl:for-each select="creator">
        <xsl:call-template name="resourcecreator">
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
        </xsl:call-template>
      </xsl:for-each>

      <xsl:if test="metadataProvider">
        <tr><td class="{$ressubHeaderStyle}" colspan="2">
        <xsl:text>Metadata Provider(s):</xsl:text>
      </td></tr>
      </xsl:if>
       <xsl:for-each select="metadataProvider">
        <xsl:call-template name="resourcemetadataProvider">
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
        </xsl:call-template>
      </xsl:for-each>

      <xsl:if test="associatedParty">
        <tr><td class="{$ressubHeaderStyle}" colspan="2">
             <xsl:text>Associated Party:</xsl:text>
         </td></tr>
      </xsl:if>
      <xsl:for-each select="associatedParty">
        <xsl:call-template name="resourceassociatedParty">
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
        </xsl:call-template>
      </xsl:for-each>

      <xsl:for-each select="abstract">
        <xsl:call-template name="resourceabstract" >
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
          <xsl:with-param name="ressubHeaderStyle" select="$ressubHeaderStyle"/>
        </xsl:call-template>
      </xsl:for-each>

      <xsl:if test="keywordSet">
        <tr><td class="{$ressubHeaderStyle}" colspan="2">
             <xsl:text>Keywords:</xsl:text></td></tr>
      </xsl:if>
      <xsl:for-each select="keywordSet">
        <xsl:call-template name="resourcekeywordSet" >
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
        </xsl:call-template>
      </xsl:for-each>

      <xsl:for-each select="additionalInfo">
        <xsl:call-template name="resourceadditionalInfo" >
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
          <xsl:with-param name="ressubHeaderStyle" select="$ressubHeaderStyle"/>
        </xsl:call-template>
      </xsl:for-each>

      <xsl:for-each select="intellectualRights">
        <xsl:call-template name="resourceintellectualRights" >
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
          <xsl:with-param name="ressubHeaderStyle" select="$ressubHeaderStyle"/>
        </xsl:call-template>
      </xsl:for-each>

      <xsl:for-each select="distribution">
        <xsl:call-template name="resourcedistribution">
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
          <xsl:with-param name="ressubHeaderStyle" select="$ressubHeaderStyle"/>
          <xsl:with-param name="index" select="position()"/>
          <xsl:with-param name="docid" select="$docid"/>
        </xsl:call-template>
      </xsl:for-each>

      <xsl:for-each select="coverage">
        <xsl:call-template name="resourcecoverage">
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
          <xsl:with-param name="ressubHeaderStyle" select="$ressubHeaderStyle"/>
        </xsl:call-template>
      </xsl:for-each>
    </table>

  </xsl:template>

  <xsl:template name="resourcealternateIdentifier" >
      <xsl:param name="resfirstColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
      <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">
        Alternate Identifier:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
      </xsl:if>
  </xsl:template>


  <xsl:template name="resourceshortName">
      <xsl:param name="resfirstColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
      <tr><td width="{$firstColWidth}"  class="{$resfirstColStyle}" >
        Short Name:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
      </xsl:if>
  </xsl:template>


  <xsl:template name="resourcetitle" >
      <xsl:param name="resfirstColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
      <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">
        Title:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <b><xsl:value-of select="."/></b></td></tr>
      </xsl:if>
  </xsl:template>


  <!--<xsl:template match="creator[1]" mode="resource">
    <xsl:param name="ressubHeaderStyle"/>
    <xsl:param name="resfirstColStyle"/>
    <tr><td class="{$ressubHeaderStyle}" colspan="2">
      <xsl:text>Data Set Owner(s):</xsl:text>
    </td></tr>
    <tr><td colspan="2">
    <xsl:call-template  name="party">
      <xsl:with-param name="partyfirstColStyle" select="$resfirstColStyle"/>
    </xsl:call-template>
    </td></tr>
    <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">&#160;</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;
    </td></tr>
  </xsl:template>-->

  <xsl:template name="resourcecreator" >
      <xsl:param name="resfirstColStyle"/>
      <tr><td colspan="2">
       <xsl:call-template name="party">
              <xsl:with-param name="partyfirstColStyle" select="$resfirstColStyle"/>
       </xsl:call-template>
      </td></tr>
   </xsl:template>

  <!--<xsl:template match="metadataProvider[1]" mode="resource">
    <xsl:param name="ressubHeaderStyle"/>
    <xsl:param name="resfirstColStyle"/>
    <tr><td class="{$ressubHeaderStyle}" colspan="2">
          <xsl:text>Metadata Provider(s):</xsl:text></td></tr>
    <tr><td colspan="2">
    <xsl:call-template name="party">
      <xsl:with-param name="partyfirstColStyle" select="$resfirstColStyle"/>
    </xsl:call-template>
    </td></tr>
    <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">&#160;</td>
    <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;</td></tr>
  </xsl:template>-->

  <xsl:template name="resourcemetadataProvider" >
      <xsl:param name="resfirstColStyle"/>
      <tr><td colspan="2">
      <xsl:call-template name="party">
            <xsl:with-param name="partyfirstColStyle" select="$resfirstColStyle"/>
      </xsl:call-template>
      </td></tr>
  </xsl:template>


  <!--<xsl:template match="associateParty[1]" mode="resource">
    <xsl:param name="ressubHeaderStyle"/>
    <xsl:param name="resfirstColStyle"/>
    <tr><td class="{$ressubHeaderStyle}" colspan="2">
      <xsl:text>Associated Party(s):</xsl:text>
    </td></tr>
    <tr><td colspan="2">
    <xsl:call-template name="party">
            <xsl:with-param name="partyfirstColStyle" select="$resfirstColStyle"/>
    </xsl:call-template>
    </td></tr>
    <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">&#160;</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;
    </td></tr>
  </xsl:template>-->

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
      <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">
        Publication Date:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../pubDate"/></td></tr>
      </xsl:if>
  </xsl:template>


  <xsl:template name="resourcelanguage">
      <xsl:param name="resfirstColStyle"/>
      <xsl:if test="normalize-space(.)!=''">
      <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">
        Language:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
      </xsl:if>
  </xsl:template>


  <xsl:template name="resourceseries">
      <xsl:param name="resfirstColStyle"/>
      <xsl:if test="normalize-space(../series)!=''">
      <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">
        Series:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="../series"/></td></tr>
      </xsl:if>
  </xsl:template>


  <xsl:template name="resourceabstract">
     <xsl:param name="resfirstColStyle"/>
     <xsl:param name="ressubHeaderStyle"/>
     <tr><td class="{$ressubHeaderStyle}" colspan="2">
        <xsl:text>Abstract:</xsl:text>
     </td></tr>
     <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">&#160;</td>
         <td width="{$secondColWidth}">
           <xsl:call-template name="text">
             <xsl:with-param name="textfirstColStyle" select="$resfirstColStyle"/>
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
        <xsl:param name="resfirstColStyle"/>
        <xsl:call-template name="renderKeywordSet">
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
        </xsl:call-template>
  </xsl:template>

  <xsl:template name="renderKeywordSet">
    <xsl:param name="resfirstColStyle"/>
    <xsl:for-each select="keywordThesaurus">
    <xsl:if test="normalize-space(.)!=''">
      <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">
        <xsl:text>Thesaurus:</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <xsl:value-of select="."/></td></tr>
    </xsl:if>
    </xsl:for-each>
    <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">&#160;
        </td><td width="{$secondColWidth}" class="indent-col">
      <xsl:if test="normalize-space(keyword)!=''">
      <UL>
        <xsl:for-each select="keyword">
          <LI><xsl:value-of select="."/>
          <xsl:if test="./@keywordType and normalize-space(./@keywordType)!=''">
            (<xsl:value-of select="./@keywordType"/>)
          </xsl:if>
          </LI>
        </xsl:for-each>
      </UL>
      </xsl:if>
    </td></tr>
  </xsl:template>


   <xsl:template name="resourceadditionalInfo">
     <xsl:param name="ressubHeaderStyle"/>
     <xsl:param name="resfirstColStyle"/>
     <tr><td class="{$ressubHeaderStyle}" colspan="2">
        <xsl:text>Additional Information:</xsl:text>
     </td></tr>
     <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">&#160;</td>
         <td width="{$secondColWidth}">
           <xsl:call-template name="text">
             <xsl:with-param name="textfirstColStyle" select="$resfirstColStyle"/>
           </xsl:call-template>
         </td>
     </tr>
  </xsl:template>


   <xsl:template name="resourceintellectualRights">
     <xsl:param name="ressubHeaderStyle"/>
     <xsl:param name="resfirstColStyle"/>
     <tr><td class="{$ressubHeaderStyle}" colspan="2">
        <xsl:text>License and Usage Rights:</xsl:text>
     </td></tr>
     <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">&#160;</td>
         <td width="{$secondColWidth}">
           <xsl:call-template name="text">
             <xsl:with-param name="textfirstColStyle" select="$resfirstColStyle"/>
           </xsl:call-template>
         </td>
     </tr>
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


</xsl:stylesheet>