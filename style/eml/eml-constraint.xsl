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
  * convert an XML file that is valid with respect to the eml-dataset.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format
  * suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html" encoding="UTF-8"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  

  <!-- This module is for constraint. And it is self contained-->
  <xsl:template name="constraint">
     <xsl:param name="constraintfirstColStyle"/>
     <table class="{$tabledefaultStyle}">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="constraintCommon">
             <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:call-template name="constraintCommon">
             <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
            </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </table>
  </xsl:template>

  <xsl:template name="constraintCommon">
    <xsl:param name="constraintfirstColStyle"/>
    <xsl:for-each select="primaryKey">
       <xsl:call-template name="primaryKey">
          <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="uniqueKey">
       <xsl:call-template name="uniqueKey">
          <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="checkConstraint">
       <xsl:call-template name="checkConstraint">
          <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="foreignKey">
       <xsl:call-template name="foreignKey">
          <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="joinCondition">
       <xsl:call-template name="joinCondition">
          <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="notNullConstraint">
       <xsl:call-template name="notNullConstraint">
          <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
       </xsl:call-template>
    </xsl:for-each>
  </xsl:template>

  <!--Keys part-->
  <xsl:template name="primaryKey">
    <xsl:param name="constraintfirstColStyle"/>
    <tr><td class="{$constraintfirstColStyle}">
          Primary Key:</td>
          <td>
            <table class="{$tabledefaultStyle}">
                 <xsl:call-template name="constraintBaseGroup">
                    <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
                 </xsl:call-template>
                 <xsl:for-each select="key/attributeReference">
                      <tr><td class="{$constraintfirstColStyle}">
                            <xsl:text>Key:</xsl:text></td>
                          <td class="{$secondColStyle}">
                            <xsl:value-of select="."/></td>
                      </tr>
                 </xsl:for-each>
            </table>
          </td>
     </tr>

  </xsl:template>

  <xsl:template name="uniqueKey">
    <xsl:param name="constraintfirstColStyle"/>
    <tr><td class="{$constraintfirstColStyle}">
          Unique Key:</td>
          <td>
             <table class="{$tabledefaultStyle}">
                  <xsl:call-template name="constraintBaseGroup">
                     <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
                  </xsl:call-template>
                  <xsl:for-each select="key/attributeReference">
                     <tr><td class="{$constraintfirstColStyle}">
                             <xsl:text>Key:</xsl:text></td>
                          <td class="{$secondColStyle}">
                            <xsl:value-of select="."/></td>
                      </tr>
                  </xsl:for-each>
             </table>
          </td>
     </tr>
  </xsl:template>

   <xsl:template name="checkConstraint">
    <xsl:param name="constraintfirstColStyle"/>
    <tr><td class="{$constraintfirstColStyle}">
          Checking Constraint: </td>
          <td>
              <table class="{$tabledefaultStyle}">
                    <xsl:call-template name="constraintBaseGroup">
                       <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
                    </xsl:call-template>
                    <xsl:for-each select="checkCondition">
                         <tr><td class="{$constraintfirstColStyle}">
                                   <xsl:text>Check Condition:</xsl:text></td>
                             <td class="{$secondColStyle}">
                                   <xsl:value-of select="."/></td>
                         </tr>
                    </xsl:for-each>
              </table>
          </td>
     </tr>
   </xsl:template>

  <xsl:template name="foreignKey">
     <xsl:param name="constraintfirstColStyle"/>
    <tr><td class="{$constraintfirstColStyle}">
          Foreign Key:</td>
          <td>
              <table class="{$tabledefaultStyle}">
                  <xsl:call-template name="constraintBaseGroup">
                        <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
                   </xsl:call-template>
                   <xsl:for-each select="key/attributeReference">
                      <tr><td class="{$constraintfirstColStyle}">
                             <xsl:text>Key:</xsl:text></td>
                          <td class="{$secondColStyle}">
                             <xsl:value-of select="."/></td>
                      </tr>
                  </xsl:for-each>
                  <tr><td class="{$constraintfirstColStyle}">
                          <xsl:text>Data Object Reference:</xsl:text></td>
                       <td class="{$secondColStyle}">
                           <xsl:value-of select="entityReference"/></td>
                   </tr>
                   <xsl:if test="relationshipType and normalize-space(relationshipType)!=''">
                        <tr><td class="{$constraintfirstColStyle}">
                                <xsl:text>Relationship:</xsl:text></td>
                             <td class="{$secondColStyle}">
                                 <xsl:value-of select="relationshipType"/></td>
                         </tr>
                    </xsl:if>
                    <xsl:if test="cardinality and normalize-space(cardinality)!=''">
                          <tr><td class="{$constraintfirstColStyle}">
                                 <xsl:text>Cardinality:</xsl:text></td>
                              <td>
                                  <table class="{$tabledefaultStyle}">
                                        <tr><td class="{$constraintfirstColStyle}">
                                                <xsl:text>Parent:</xsl:text></td>
                                             <td class="{$secondColStyle}">
                                                 <xsl:value-of select="cardinality/parentOccurences"/></td>
                                        </tr>
                                        <tr><td class="{$constraintfirstColStyle}">
                                                <xsl:text>Children</xsl:text></td>
                                            <td class="{$secondColStyle}">
                                                 <xsl:value-of select="cardinality/childOccurences"/></td>
                                         </tr>
                                   </table>
                               </td>
                          </tr>
                   </xsl:if>
             </table>
          </td>
     </tr>

  </xsl:template>

  <xsl:template name="joinCondition">
    <xsl:param name="constraintfirstColStyle"/>
    <tr><td class="{$constraintfirstColStyle}">
          Join Condition:</td>
          <td>
              <table class="{$tabledefaultStyle}">
                   <xsl:call-template name="foreignKey">
                        <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
                   </xsl:call-template>
                   <xsl:for-each select="referencedKey/attributeReference">
                      <tr><td class="{$constraintfirstColStyle}">
                             <xsl:text>Referenced Key:</xsl:text></td>
                          <td class="{$secondColStyle}">
                              <xsl:value-of select="."/></td>
                       </tr>
                   </xsl:for-each>
             </table>
          </td>
    </tr>
  </xsl:template>

  <xsl:template name="notNullConstraint">
    <xsl:param name="constraintfirstColStyle"/>
    <tr><td class="{$constraintfirstColStyle}">
          Not Null Constraint:</td>
          <td>
              <table class="{$tabledefaultStyle}">
                   <xsl:call-template name="constraintBaseGroup">
                       <xsl:with-param name="constraintfirstColStyle" select="$constraintfirstColStyle"/>
                   </xsl:call-template>
                   <xsl:for-each select="key/attributeReference">
                        <tr><td class="{$constraintfirstColStyle}">
                                 <xsl:text>Key:</xsl:text></td>
                            <td class="{$secondColStyle}">
                                 <xsl:value-of select="."/></td>
                        </tr>
                   </xsl:for-each>
              </table>
          </td>
     </tr>
   </xsl:template>

  <xsl:template name="constraintBaseGroup">
    <xsl:param name="constraintfirstColStyle"/>
     <tr><td class="{$constraintfirstColStyle}">
          <xsl:text>Name:</xsl:text></td>
          <td class="{$secondColStyle}">
         <xsl:value-of select="constraintName"/></td>
     </tr>
     <xsl:if test="constraintDescription and normalize-space(constraintDescription)!=''">
       <tr><td class="{$constraintfirstColStyle}">
          <xsl:text>Description:</xsl:text></td>
          <td class="{$secondColStyle}">
          <xsl:value-of select="constraintDescription"/></td>
      </tr>
     </xsl:if>
  </xsl:template>

</xsl:stylesheet>
