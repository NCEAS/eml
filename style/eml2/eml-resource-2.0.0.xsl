<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-resource-2.0.0.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: berkley $'
  *     '$Date: 2003-06-03 21:41:35 $'
  * '$Revision: 1.1 $'
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
  <xsl:import href="eml-party-2.0.0.xsl"/>
  <xsl:import href="eml-distribution-2.0.0.xsl"/>
  <xsl:output method="html" encoding="iso-8859-1"/>
  
  <!-- This module is for resouce and it is self-contained (it is table)-->
  <xsl:template name="resource">
    <xsl:param name="resfirstColStyle"/>
    <xsl:param name="ressubHeaderStyle"/>
    <table class="tabledefault" width="100%">
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
      
      <xsl:for-each select="title">
        <xsl:call-template name="resourcetitle">
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
          <xsl:with-param name="ressubHeaderStyle" select="$ressubHeaderStyle"/>
        </xsl:call-template>
      </xsl:for-each>
      
      <xsl:if test="creator">
        <tr><td class="{$ressubHeaderStyle}" colspan="2">
        <xsl:text>Data Set Owner(s):</xsl:text>
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
             <xsl:text>Associate Party:</xsl:text>
         </td></tr>
      </xsl:if>
      <xsl:for-each select="associatedParty">
        <xsl:call-template name="resourceassociatedParty">
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
        </xsl:call-template>
      </xsl:for-each>
      
      <xsl:for-each select="pubDate">
        <xsl:call-template name="resourcepubDate" >
          <xsl:with-param name="resfirstColStyle" select="$resfirstColStyle"/>
         </xsl:call-template>
      </xsl:for-each>
      
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
        <xsl:value-of select="."/></td></tr>
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
      <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">&#160;</td>
       <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;</td></tr>
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
      <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">&#160;</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;
      </td></tr>
  </xsl:template>  
  
  
  <!--<xsl:template match="associateParty[1]" mode="resource">
    <xsl:param name="ressubHeaderStyle"/>
    <xsl:param name="resfirstColStyle"/>
    <tr><td class="{$ressubHeaderStyle}" colspan="2">
      <xsl:text>Associate Party(s):</xsl:text>
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

  <xsl:template name="resourceassociateParty">
      <xsl:param name="resfirstColStyle"/>
      <tr><td colspan="2">
      <xsl:call-template name="party">
          <xsl:with-param name="partyfirstColStyle" select="$resfirstColStyle"/>
      </xsl:call-template>
      </td></tr>
      <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">&#160;</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;
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
     <tr><td colspan="2" width="100%">
        <xsl:call-template name="text">
          <xsl:with-param name="textfirstColStyle" select="$resfirstColStyle"/>
        </xsl:call-template>
     </td></tr>
     <tr><td width="{$firstColWidth}" class="{$resfirstColStyle}">&#160;</td>
     <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;</td></tr>
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
     <tr><td colspan="2" width="100%">    
        <xsl:call-template name="text">
          <xsl:with-param name="textfirstColStyle" select="$resfirstColStyle"/>
        </xsl:call-template>
     </td></tr>
  </xsl:template>

   
   <xsl:template name="resourceintellectualRights">
     <xsl:param name="ressubHeaderStyle"/>
     <xsl:param name="resfirstColStyle"/>
     <tr><td class="{$ressubHeaderStyle}" colspan="2">
        <xsl:text>License and Usage Rights:</xsl:text>
     </td></tr>
     <tr><td colspan="2" width="100%">    
        <xsl:call-template name="text">
          <xsl:with-param name="textfirstColStyle" select="$resfirstColStyle"/>
        </xsl:call-template>
     </td></tr>  
  </xsl:template>
  
   <xsl:template name="resourcedistribution">
     <xsl:param name="ressubHeaderStyle"/>
     <xsl:param name="resfirstColStyle"/>
     <tr><td colspan="2" width="100%">    
        <xsl:call-template name="distribution">
          <xsl:with-param name="disfirstColStyle" select="$resfirstColStyle"/>
          <xsl:with-param name="dissubHeaderStyle" select="$ressubHeaderStyle"/>
        </xsl:call-template>
     </td></tr>  
  </xsl:template>


  <xsl:template match="triple"/>
  <xsl:template match="triple[1]" mode="resource">
    <tr><td class="{$subHeaderStyle}" colspan="2">
    <xsl:text>Related Metadata and Data Files:</xsl:text></td></tr>
    <xsl:call-template name="renderTriple">
  <xsl:with-param name="indentation" select="1"></xsl:with-param>
    </xsl:call-template>

  </xsl:template>

  <xsl:template match="triple" mode="resource">
  <xsl:if test="(./subject)!=(./object)">
      <!-- render only for subsequent data files -->
      <xsl:if test="starts-with(./relationship,'isDataFileFor')">
    <xsl:call-template name="renderTriple">
      <xsl:with-param name="indentation" select="1"></xsl:with-param>
    </xsl:call-template>
      </xsl:if>
      <xsl:if test="starts-with(./relationship,'provides eml-project information')">
    <xsl:call-template name="renderTriple">
      <xsl:with-param name="indentation" select="1"></xsl:with-param>
    </xsl:call-template>
      </xsl:if>

  </xsl:if>
  </xsl:template>

  <!--xsl:template name="renderTriple">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
      <xsl:text>&#160;</xsl:text></td><td width="{$secondColWidth}" class="{$secondColStyle}">
      <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI" /><xsl:value-of select="./subject"/></xsl:attribute><xsl:value-of select="./subject"/></a>
         <xsl:text> &#160;&#160;</xsl:text>
         <xsl:value-of select="./relationship"/>
         <xsl:text> &#160;&#160;</xsl:text>
      <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI" /><xsl:value-of select="./object"/></xsl:attribute><xsl:value-of select="./object"/></a>
    </td></tr>
    </xsl:template-->

 <xsl:template name="renderTriple">
    <xsl:param name="indentation">1</xsl:param>
    <tr></tr><tr> <td></td>
    <td width="{$secondColWidth}" class="{$secondColStyle}" padding-left="{$indentation}em">
      <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI" /><xsl:value-of select="./subject"/></xsl:attribute>
   <xsl:if test="$indentation=2">
    &#160;&#160;&#160;&#160;&#160;&#149;
   </xsl:if>
   <xsl:if test="$indentation=3">
    &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#149;
   </xsl:if>
   <xsl:if test="starts-with(./relationship,'isDataFileFor')">
         <xsl:text>Data file for this document</xsl:text>
   <xsl:variable name="subj"> <xsl:value-of select="./subject"/> </xsl:variable>
   <xsl:for-each select="../triple">
    <xsl:if test="(./object)=$subj">
      <xsl:if test="starts-with(./relationship,'provides access control')">
          &#160;&#160;&#160;
          <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI" /><xsl:value-of select="./subject"/></xsl:attribute>
          &#040;Access Rules&#041;</a>
      </xsl:if>
    </xsl:if>
  </xsl:for-each>
  <xsl:for-each select="../triple">
    <xsl:if test="(./object)=$subj">
      <xsl:if test="not(starts-with(./relationship,'provides access control'))">
        <xsl:call-template name="renderTriple">
        <xsl:with-param name="indentation" select="($indentation + 1)">
        </xsl:with-param>
        </xsl:call-template>
      </xsl:if>
    </xsl:if>
   </xsl:for-each>
  </xsl:if>

   <xsl:if test="starts-with(./relationship,'provides table-entity information')">
    <xsl:text>Information about the data file</xsl:text>
    <xsl:variable name="subj"> <xsl:value-of select="./subject"/> </xsl:variable>
     <xsl:for-each select="../triple">
          <xsl:if test="(./object)=$subj">
      <xsl:if test="starts-with(./relationship,'provides access control')">
          &#160;&#160;&#160;
          <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI" /><xsl:value-of select="./subject"/></xsl:attribute>
          &#040;Access Rules&#041;</a>
      </xsl:if>
        </xsl:if>
    </xsl:for-each>
    <xsl:for-each select="../triple">
             <xsl:if test="(./object)=$subj">
        <xsl:if test="not(starts-with(./relationship,'provides access control'))">
        <xsl:call-template name="renderTriple">
        <xsl:with-param name="indentation" select="($indentation + 1)">
        </xsl:with-param>
        </xsl:call-template>
        </xsl:if>
       </xsl:if>
    </xsl:for-each>
  </xsl:if>

   <xsl:if test="starts-with(./relationship,'provides eml-attribute information')">
    <xsl:text>Definition of each column in the data file</xsl:text>
    <xsl:variable name="subj"> <xsl:value-of select="./subject"/> </xsl:variable>
    <xsl:for-each select="../triple">
          <xsl:if test="(./object)=$subj">
      <xsl:if test="starts-with(./relationship,'provides access control')">
          &#160;&#160;&#160;
          <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI" /><xsl:value-of select="./subject"/></xsl:attribute>
          &#040;Access Rules&#041;</a>
      </xsl:if>
        </xsl:if>
    </xsl:for-each>
    <xsl:for-each select="../triple">
       <xsl:if test="(./object)=$subj">
      <xsl:if test="not(starts-with(./relationship,'provides access control'))">
        <xsl:call-template name="renderTriple">
        <xsl:with-param name="indentation" select="($indentation + 1)">
        </xsl:with-param>
        </xsl:call-template>
      </xsl:if>
       </xsl:if>
    </xsl:for-each>
  </xsl:if>
   <xsl:if test="starts-with(./relationship,'provides eml-physical information')">
    <xsl:text>Physical data file layout information</xsl:text>
    <xsl:variable name="subj"> <xsl:value-of select="./subject"/> </xsl:variable>
     <xsl:for-each select="../triple">
          <xsl:if test="(./object)=$subj">
      <xsl:if test="starts-with(./relationship,'provides access control')">
          &#160;&#160;&#160;
          <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI" /><xsl:value-of select="./subject"/></xsl:attribute>
          &#040;Access Rules&#041;</a>
      </xsl:if>
        </xsl:if>
    </xsl:for-each>
    <xsl:for-each select="../triple">
       <xsl:if test="(./object)=$subj">
      <xsl:if test="not(starts-with(./relationship,'provides access control'))">
        <xsl:call-template name="renderTriple">
        <xsl:with-param name="indentation" select="($indentation + 1)">
        </xsl:with-param>
        </xsl:call-template>
      </xsl:if>
       </xsl:if>
    </xsl:for-each>
  </xsl:if>
    <xsl:if test="starts-with(./relationship,'provides eml-project information')">
    <xsl:text>Project information</xsl:text>
    <xsl:variable name="subj"> <xsl:value-of select="./subject"/> </xsl:variable>
     <xsl:for-each select="../triple">
          <xsl:if test="(./object)=$subj">
      <xsl:if test="starts-with(./relationship,'provides access control')">
          &#160;&#160;&#160;
          <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI" /><xsl:value-of select="./subject"/></xsl:attribute>
          &#040;Access Rules&#041;</a>
      </xsl:if>
        </xsl:if>
    </xsl:for-each>
    <xsl:for-each select="../triple">
       <xsl:if test="(./object)=$subj">
      <xsl:if test="not(starts-with(./relationship,'provides access control'))">
        <xsl:call-template name="renderTriple">
        <xsl:with-param name="indentation" select="($indentation + 1)">
        </xsl:with-param>
        </xsl:call-template>
      </xsl:if>
       </xsl:if>
    </xsl:for-each>
  </xsl:if>
  </a>
     </td></tr>
    </xsl:template>

</xsl:stylesheet>
