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

  <xsl:template name="view">
      <xsl:param name="viewfirstColStyle"/>
      <xsl:param name="viewsubHeaderStyle"/>
      <xsl:param name="docid"/>
      <xsl:param name="entityindex"/>
      <table class="{$tabledefaultStyle}">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="viewCommon">
             <xsl:with-param name="viewfirstColStyle" select="$viewfirstColStyle"/>
             <xsl:with-param name="viewsubHeaderStyle" select="$viewsubHeaderStyle"/>
             <xsl:with-param name="docid" select="$docid"/>
             <xsl:with-param name="entityindex" select="$entityindex"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:call-template name="viewCommon">
             <xsl:with-param name="viewfirstColStyle" select="$viewfirstColStyle"/>
             <xsl:with-param name="viewsubHeaderStyle" select="$viewsubHeaderStyle"/>
             <xsl:with-param name="docid" select="$docid"/>
             <xsl:with-param name="entityindex" select="$entityindex"/>
            </xsl:call-template>
         </xsl:otherwise>
      </xsl:choose>
      </table>
  </xsl:template>

  <xsl:template name="viewCommon">
    <xsl:param name="viewfirstColStyle"/>
    <xsl:param name="viewsubHeaderStyle"/>
    <xsl:param name="docid"/>
    <xsl:param name="entityindex"/>
    <xsl:for-each select="entityName">
       <xsl:call-template name="entityName">
          <xsl:with-param name="entityfirstColStyle" select="$viewfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="queryStatement">
       <tr><td class="{$viewfirstColStyle}">
            Query Statement:
            </td>
            <td class="{$secondColStyle}">
            <xsl:value-of select="."/>
            </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="alternateIdentifier">
       <xsl:call-template name="entityalternateIdentifier">
          <xsl:with-param name="entityfirstColStyle" select="$viewfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="entityDescription">
       <xsl:call-template name="entityDescription">
          <xsl:with-param name="entityfirstColStyle" select="$viewfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="additionalInfo">
       <xsl:call-template name="entityadditionalInfo">
          <xsl:with-param name="entityfirstColStyle" select="$viewfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <!-- call physical moduel without show distribution(we want see it later)-->
    <xsl:if test="physical">
       <tr><td class="{$viewsubHeaderStyle}" colspan="2">
        Physical Structure Description:
      </td></tr>
    </xsl:if>
    <xsl:for-each select="physical">
       <tr><td colspan="2">
        <xsl:call-template name="physical">
         <xsl:with-param name="physicalfirstColStyle" select="$viewfirstColStyle"/>
         <xsl:with-param name="notshowdistribution">yes</xsl:with-param>
        </xsl:call-template>
        </td></tr>
    </xsl:for-each>

    <xsl:if test="coverage">
       <tr><td class="{$viewsubHeaderStyle}" colspan="2">
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
       <tr><td class="{$viewsubHeaderStyle}" colspan="2">
        Method Description:
      </td></tr>
    </xsl:if>
    <xsl:for-each select="method">
      <tr><td colspan="2">
        <xsl:call-template name="method">
          <xsl:with-param name="methodfirstColStyle" select="$viewfirstColStyle"/>
          <xsl:with-param name="methodsubHeaderStyle" select="$viewsubHeaderStyle"/>
        </xsl:call-template>
      </td></tr>
    </xsl:for-each>
    <xsl:if test="constraint">
       <tr><td class="{$viewsubHeaderStyle}" colspan="2">
        Constraint:
      </td></tr>
    </xsl:if>
    <xsl:for-each select="constraint">
      <tr><td colspan="2">
        <xsl:call-template name="constraint">
          <xsl:with-param name="constraintfirstColStyle" select="$viewfirstColStyle"/>
        </xsl:call-template>
      </td></tr>
    </xsl:for-each>
    <xsl:if test="$withAttributes='1'">
     <xsl:for-each select="attributeList">
       <xsl:call-template name="viewAttributeList">
         <xsl:with-param name="viewfirstColStyle" select="$viewfirstColStyle"/>
         <xsl:with-param name="viewsubHeaderStyle" select="$viewsubHeaderStyle"/>
         <xsl:with-param name="docid" select="$docid"/>
         <xsl:with-param name="entityindex" select="$entityindex"/>
       </xsl:call-template>
     </xsl:for-each>
    </xsl:if>
    <xsl:if test="$withAttributes='1'">
     <!-- Here to display distribution info-->
    <xsl:for-each select="physical">
       <xsl:call-template name="viewShowDistribution">
          <xsl:with-param name="docid" select="$docid"/>
          <xsl:with-param name="entityindex" select="$entityindex"/>
          <xsl:with-param name="physicalindex" select="position()"/>
          <xsl:with-param name="viewfirstColStyle" select="$viewfirstColStyle"/>
          <xsl:with-param name="viewsubHeaderStyle" select="$viewsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>
   </xsl:if>
  </xsl:template>

  <xsl:template name="viewShowDistribution">
     <xsl:param name="viewfirstColStyle"/>
     <xsl:param name="viewsubHeaderStyle"/>
     <xsl:param name="docid"/>
     <xsl:param name="level">entitylevel</xsl:param>
     <xsl:param name="entitytype">view</xsl:param>
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
          <xsl:with-param name="disfirstColStyle" select="$viewfirstColStyle"/>
          <xsl:with-param name="dissubHeaderStyle" select="$viewsubHeaderStyle"/>
        </xsl:call-template>
      </td></tr>
    </xsl:for-each>
  </xsl:template>


  <xsl:template name="viewAttributeList">
    <xsl:param name="viewfirstColStyle"/>
    <xsl:param name="viewsubHeaderStyle"/>
    <xsl:param name="docid"/>
    <xsl:param name="entitytype">view</xsl:param>
    <xsl:param name="entityindex"/>
    <tr><td class="{$viewsubHeaderStyle}" colspan="2">
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
