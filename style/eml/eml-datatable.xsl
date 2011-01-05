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
  <!-- This module is for datatable module-->

  <xsl:template name="dataTable">
      <xsl:param name="datatablefirstColStyle"/>
      <xsl:param name="datatablesubHeaderStyle"/>
      <xsl:param name="docid"/>
      <xsl:param name="entityindex"/>
      <table class="{$tabledefaultStyle}">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="datatablecommon">
             <xsl:with-param name="datatablefirstColStyle" select="$datatablefirstColStyle"/>
             <xsl:with-param name="datatablesubHeaderStyle" select="$datatablesubHeaderStyle"/>
             <xsl:with-param name="docid" select="$docid"/>
             <xsl:with-param name="entityindex" select="$entityindex"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="datatablecommon">
             <xsl:with-param name="datatablefirstColStyle" select="$datatablefirstColStyle"/>
             <xsl:with-param name="datatablesubHeaderStyle" select="$datatablesubHeaderStyle"/>
             <xsl:with-param name="docid" select="$docid"/>
             <xsl:with-param name="entityindex" select="$entityindex"/>
          </xsl:call-template>
         </xsl:otherwise>
      </xsl:choose>
      </table>
  </xsl:template>

  <xsl:template name="datatablecommon">
    <xsl:param name="datatablefirstColStyle"/>
    <xsl:param name="datatablesubHeaderStyle"/>
    <xsl:param name="docid"/>
    <xsl:param name="entityindex"/>
    <xsl:for-each select="entityName">
       <xsl:call-template name="entityName">
          <xsl:with-param name="entityfirstColStyle" select="$datatablefirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="alternateIdentifier">
       <xsl:call-template name="entityalternateIdentifier">
          <xsl:with-param name="entityfirstColStyle" select="$datatablefirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="entityDescription">
       <xsl:call-template name="entityDescription">
          <xsl:with-param name="entityfirstColStyle" select="$datatablefirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="additionalInfo">
       <xsl:call-template name="entityadditionalInfo">
          <xsl:with-param name="entityfirstColStyle" select="$datatablefirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <!-- call physical moduel without show distribution(we want see it later)-->
    <xsl:if test="physical">
       <tr><td class="{$datatablesubHeaderStyle}" colspan="2">
        Physical Structure Description:
      </td></tr>
    </xsl:if>
    <xsl:for-each select="physical">
       <tr><td colspan="2">
        <xsl:call-template name="physical">
         <xsl:with-param name="physicalfirstColStyle" select="$datatablefirstColStyle"/>
         <xsl:with-param name="notshowdistribution">yes</xsl:with-param>
        </xsl:call-template>
       </td></tr>
    </xsl:for-each>

    <xsl:for-each select="caseSensitive">
       <xsl:call-template name="datatablecaseSensitive">
          <xsl:with-param name="datatablefirstColStyle" select="$datatablefirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="numberOfRecords">
       <xsl:call-template name="datatablenumberOfRecords">
          <xsl:with-param name="datatablefirstColStyle" select="$datatablefirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:if test="coverage">
       <tr><td class="{$datatablesubHeaderStyle}" colspan="2">
        Coverage Description:
      </td></tr>
    </xsl:if>
    <xsl:for-each select="coverage">
      <tr><td colspan="2">
        <xsl:call-template name="coverage">
        </xsl:call-template>
      </td></tr>
    </xsl:for-each>
    <xsl:if test="method">
       <tr><td class="{$datatablesubHeaderStyle}" colspan="2">
        Method Description:
      </td></tr>
    </xsl:if>
    <xsl:for-each select="method">
      <tr><td colspan="2">
        <xsl:call-template name="method">
          <xsl:with-param name="methodfirstColStyle" select="$datatablefirstColStyle"/>
          <xsl:with-param name="methodsubHeaderStyle" select="$datatablesubHeaderStyle"/>
        </xsl:call-template>
      </td></tr>
    </xsl:for-each>
    <xsl:if test="constraint">
       <tr><td class="{$datatablesubHeaderStyle}" colspan="2">
        Constraint:
       </td></tr>
    </xsl:if>
    <xsl:for-each select="constraint">
      <tr><td colspan="2">
        <xsl:call-template name="constraint">
          <xsl:with-param name="constraintfirstColStyle" select="$datatablefirstColStyle"/>
        </xsl:call-template>
      </td></tr>
    </xsl:for-each>
     <xsl:if test="$withAttributes='1'">
      <xsl:for-each select="attributeList">
       <xsl:call-template name="datatableattributeList">
         <xsl:with-param name="datatablefirstColStyle" select="$datatablefirstColStyle"/>
         <xsl:with-param name="datatablesubHeaderStyle" select="$datatablesubHeaderStyle"/>
         <xsl:with-param name="docid" select="$docid"/>
         <xsl:with-param name="entityindex" select="$entityindex"/>
       </xsl:call-template>
      </xsl:for-each>
     </xsl:if>
     <!-- Here to display distribution info-->
    <xsl:for-each select="physical">
       <xsl:call-template name="showdistribution">
          <xsl:with-param name="docid" select="$docid"/>
          <xsl:with-param name="entityindex" select="$entityindex"/>
          <xsl:with-param name="physicalindex" select="position()"/>
          <xsl:with-param name="datatablefirstColStyle" select="$datatablefirstColStyle"/>
          <xsl:with-param name="datatablesubHeaderStyle" select="$datatablesubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>
  </xsl:template>




  <xsl:template name="datatablecaseSensitive">
       <xsl:param name="datatablefirstColStyle"/>
       <tr><td class="{$datatablefirstColStyle}">
       Case Sensitive?</td><td class="{$secondColStyle}">
       <xsl:value-of select="."/></td></tr>

  </xsl:template>

  <xsl:template name="datatablenumberOfRecords">
       <xsl:param name="datatablefirstColStyle"/>
       <tr><td class="{$datatablefirstColStyle}">
            Number Of Records:</td><td class="{$secondColStyle}">
       <xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template name="showdistribution">
     <xsl:param name="datatablefirstColStyle"/>
     <xsl:param name="datatablesubHeaderStyle"/>
     <xsl:param name="docid"/>
     <xsl:param name="level">entitylevel</xsl:param>
     <xsl:param name="entitytype">dataTable</xsl:param>
     <xsl:param name="entityindex"/>
     <xsl:param name="physicalindex"/>

    <xsl:for-each select="distribution">
      <tr><td colspan="2">
        <xsl:call-template name="distribution">
          <xsl:with-param name="docid" select="$docid"/>
          <xsl:with-param name="level" select="$level"/>
          <xsl:with-param name="entitytype" select="$entitytype"/>
          <xsl:with-param name="entityindex" select="$entityindex"/>
          <xsl:with-param name="physicalindex" select="$physicalindex"/>
          <xsl:with-param name="distributionindex" select="position()"/>
          <xsl:with-param name="disfirstColStyle" select="$datatablefirstColStyle"/>
          <xsl:with-param name="dissubHeaderStyle" select="$datatablesubHeaderStyle"/>
        </xsl:call-template>
      </td></tr>
    </xsl:for-each>
  </xsl:template>


  <xsl:template name="datatableattributeList">
    <xsl:param name="datatablefirstColStyle"/>
    <xsl:param name="datatablesubHeaderStyle"/>
    <xsl:param name="docid"/>
    <xsl:param name="entitytype">dataTable</xsl:param>
    <xsl:param name="entityindex"/>
    <tr><td class="{$datatablesubHeaderStyle}" colspan="2">
        <xsl:text>Attribute(s) Info:</xsl:text>
    </td></tr>
    <tr><td colspan="2">
         <xsl:call-template name="attributelist">
           <xsl:with-param name="docid" select="$docid"/>
           <xsl:with-param name="entitytype" select="$entitytype"/>
           <xsl:with-param name="entityindex" select="$entityindex"/>
         </xsl:call-template>
       </td>
    </tr>
  </xsl:template>



</xsl:stylesheet>
