<?xml version="1.0"?>
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

  <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  


   <xsl:template name="project">
      <xsl:param name="projectfirstColStyle"/>
      <table class="{$tabledefaultStyle}">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="projectcommon">
             <xsl:with-param name="projectfirstColStyle" select="$projectfirstColStyle"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="projectcommon">
             <xsl:with-param name="projectfirstColStyle" select="$projectfirstColStyle"/>
           </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
      </table>
  </xsl:template>



  <xsl:template name="projectcommon">
    <xsl:param name="projectfirstColStyle"/>
    <xsl:call-template name="projecttitle">
      <xsl:with-param name="projectfirstColStyle" select="$projectfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="projectpersonnel">
      <xsl:with-param name="projectfirstColStyle" select="$projectfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="projectabstract">
      <xsl:with-param name="projectfirstColStyle" select="$projectfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="projectfunding">
      <xsl:with-param name="projectfirstColStyle" select="$projectfirstColStyle"/>
    </xsl:call-template>
   <xsl:call-template name="projectstudyareadescription">
      <xsl:with-param name="projectfirstColStyle" select="$projectfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="projectdesigndescription">
      <xsl:with-param name="projectfirstColStyle" select="$projectfirstColStyle"/>
    </xsl:call-template>
    <xsl:call-template name="projectrelatedproject">
      <xsl:with-param name="projectfirstColStyle" select="$projectfirstColStyle"/>
    </xsl:call-template>
  </xsl:template>



   <xsl:template name="projecttitle">
     <xsl:param name="projectfirstColStyle"/>
     <xsl:for-each select="title">
        <tr><td class="{$projectfirstColStyle}">
             Title:
             </td>
             <td class="{$secondColStyle}" >
              <xsl:value-of select="../title"/>
             </td>
       </tr>
     </xsl:for-each>
  </xsl:template>



  <xsl:template name="projectpersonnel">
     <xsl:param name="projectfirstColStyle"/>
     <tr><td class="{$projectfirstColStyle}">
          Personnel:
          </td>
          <td>
             <table>
                 <xsl:for-each select="personnel">
                       <tr><td colspan="2">
                              <xsl:call-template name="party">
                                 <xsl:with-param name="partyfirstColStyle" select="$projectfirstColStyle"/>
                              </xsl:call-template>
                       </td></tr>
                       <xsl:for-each select="role">
                          <tr><td class="{$projectfirstColStyle}">
                                 Role:
                               </td>
                               <td>
                                 <table class="{$tablepartyStyle}">
                                     <tr>
                                         <td class="{$secondColStyle}">
                                            <xsl:value-of select="."/>
                                          </td>
                                      </tr>
                                  </table>
                               </td>
                          </tr>
                      </xsl:for-each>
                </xsl:for-each>
             </table>
         </td>
     </tr>
  </xsl:template>


   <xsl:template name="projectabstract">
     <xsl:param name="projectfirstColStyle"/>
     <xsl:for-each select="abstract">
       <tr><td class="{$projectfirstColStyle}">
          Abstract:
          </td>
          <td>
             <xsl:call-template name="text">
                <xsl:with-param name="textfirstColStyle" select="$projectfirstColStyle"/>
             </xsl:call-template>
         </td>
       </tr>
     </xsl:for-each>
  </xsl:template>

  <xsl:template name="projectfunding">
     <xsl:param name="projectfirstColStyle"/>
     <xsl:for-each select="funding">
       <tr><td class="{$projectfirstColStyle}">
          Funding:
          </td>
          <td>
              <xsl:call-template name="text">
                 <xsl:with-param name="textfirstColStyle" select="$projectfirstColStyle"/>
              </xsl:call-template>
         </td>
       </tr>
    </xsl:for-each>
  </xsl:template>



   <xsl:template name="projectstudyareadescription">
     <xsl:param name="projectfirstColStyle"/>
     <xsl:for-each select="studyAreaDescription">
       <tr><td class="{$projectfirstColStyle}">
           <xsl:text>Study Area:</xsl:text>
          </td>
          <td>
              <table class="{$tabledefaultStyle}">
                  <xsl:for-each select="descriptor">
                      <xsl:for-each select="descriptorValue">
                      <tr><td class="{$projectfirstColStyle}">
                            <xsl:value-of select="../@name"/>
                          </td>
                          <td class="{$secondColStyle}">
                             <xsl:choose>
                                <xsl:when test="./@citableClassificationSystem">
                                  <xsl:value-of select="."/>&#160;<xsl:value-of select="./@name_or_id"/>
                                </xsl:when>
                                <xsl:otherwise>
                                  <xsl:value-of select="."/>&#160;<xsl:value-of select="./@name_or_id"/>&#160;(No Citable Classification System)
                                </xsl:otherwise>
                              </xsl:choose>
                          </td>
                      </tr>
                      </xsl:for-each>
                      <xsl:for-each select="citation">
                        <tr><td class="{$projectfirstColStyle}">
                              Citation:
                            </td>
                            <td>
                             <xsl:call-template name="citation">
                                  <xsl:with-param name="citationfirstColStyle" select="projectfirstColStyle"/>
                             </xsl:call-template>
                           </td>
                       </tr>
                    </xsl:for-each>
               </xsl:for-each>
            </table>
         </td>
       </tr>

       <xsl:for-each select="citation">
         <tr><td class="{$projectfirstColStyle}">
          Study Area Citation:
          </td>
          <td>
              <xsl:call-template name="citation">
                   <xsl:with-param name="citationfirstColStyle" select="projectfirstColStyle"/>
               </xsl:call-template>
          </td>
        </tr>
      </xsl:for-each>

       <xsl:for-each select="coverage">
        <tr><td class="{$projectfirstColStyle}">
          Study Area Coverage:
          </td>
          <td>
             <xsl:call-template name="coverage"/>
          </td>
        </tr>
      </xsl:for-each>
    </xsl:for-each>
   </xsl:template>



  <xsl:template name="projectdesigndescription">
    <xsl:param name="projectfirstColStyle"/>
    <xsl:for-each select="designDescription">
       <xsl:for-each select="description">
        <tr><td class="{$projectfirstColStyle}">
          Design Description:
          </td>
          <td>
             <xsl:call-template name="text"/>
         </td>
       </tr>
      </xsl:for-each>
      <xsl:for-each select="citation">
        <tr><td class="{$projectfirstColStyle}">
          Design Citation:
          </td>
          <td >
             <xsl:call-template name="citation"/>
         </td>
       </tr>
      </xsl:for-each>
    </xsl:for-each>
  </xsl:template>



  <xsl:template name="projectrelatedproject">
    <xsl:param name="projectfirstColStyle"/>
    <xsl:for-each select="relatedProject">
       <tr><td class="{$projectfirstColStyle}">
          Related Project:
          </td>
          <td>
            <xsl:call-template name="project">
              <xsl:with-param name="projectfirstColStyle" select="$projectfirstColStyle"/>
            </xsl:call-template>
         </td>
       </tr>
    </xsl:for-each>
  </xsl:template>


</xsl:stylesheet>
