<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-attribute-enumeratedDomain-2.0.0.xsl,v $'
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2003-11-13 19:47:00 $'
  * '$Revision: 1.3 $'
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
 
  <xsl:output method="html" encoding="iso-8859-1"/>
  
   <xsl:template name="nonNumericDomain">
     <xsl:param name="nondomainfirstColStyle"/>
     <table xsl:use-attribute-sets="cellspacing" class="tabledefault" width="100%">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="nonNumericDomainCommon">
             <xsl:with-param name="nondomainfirstColStyle" select="$nondomainfirstColStyle"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="nonNumericDomainCommon">
             <xsl:with-param name="nondomainfirstColStyle" select="$nondomainfirstColStyle"/>
           </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </table>
  </xsl:template>

  
  <xsl:template name="nonNumericDomainCommon">
    <xsl:param name="nondomainfirstColStyle"/>
    <xsl:for-each select="enumeratedDomain">
      <xsl:call-template name="enumeratedDomain">
        <xsl:with-param name="nondomainfirstColStyle" select="$nondomainfirstColStyle"/>
      </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="textDomain">
      <xsl:call-template name="enumeratedDomain">
        <xsl:with-param name="nondomainfirstColStyle" select="$nondomainfirstColStyle"/>
      </xsl:call-template>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template name="textDomain">
       <xsl:param name="nondomainfirstColStyle"/>
       <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}"><b>Text Domain</b></td>
            <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;
            </td>
       </tr> 
       <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">Definition</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="definition"/>
            </td>
        </tr>
        <xsl:for-each select="parttern">
          <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">Pattern</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="."/>
            </td>
          </tr>
        </xsl:for-each>
        <xsl:if test="source">
          <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">Source</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="source"/>
            </td>
          </tr>
        </xsl:if>
  </xsl:template>

  <xsl:template name="enumeratedDomain">
     <xsl:param name="nondomainfirstColStyle"/>
     <xsl:if test="codeDefinition">
        <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}"><b>Enumerated Domain</b></td>
            <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;
            </td>
       </tr>
       <xsl:for-each select="codeDefinition">
              <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">Code Definition</td>
                   <td width="{$secondColWidth}">
                      <table xsl:use-attribute-sets="cellspacing" class="tabledefault" width="100%">
                          <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">
                               Code
                              </td>
                               <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="code"/></td>
                              
                           </tr>
                           <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">
                               Definition
                              </td>
                               <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="definition"/></td>
                               
                           </tr>
                           <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">
                               Source
                              </td>
                               <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="source"/></td>
                          </tr>
                      </table>
                   </td>
               </tr> 
         </xsl:for-each>
     </xsl:if>  
     <xsl:if test="externalCodeSet">
        <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}"><b>Enumerated Domain(External Set)</b></td>
            <td width="{$secondColWidth}">&#160;
           </td>
        </tr>
        <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">Set Name:</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="externalCodeSet/codesetName"/>
           </td>
        </tr>
        <xsl:for-each select="externalCodeSet/citation">
           <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">Citation:</td>
               <td width="{$secondColWidth}">
                  <xsl:call-template name="citation">
                      <xsl:with-param name="citationfirstColStyle" select="$nondomainfirstColStyle"/>
                   </xsl:call-template>
               </td>
           </tr>
        </xsl:for-each>
        <xsl:for-each select="externalCodeSet/codesetURL">
           <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">URL</td>
               <td width="{$secondColWidth}" class="{$secondColStyle}">
                 <a><xsl:attribute name="href"><xsl:value-of select="."/></xsl:attribute><xsl:value-of select="."/></a>
              </td>
           </tr>
        </xsl:for-each>
     </xsl:if>
     <xsl:if test="entityCodeList">
        <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}"><b>Enumerated Domain(Entity)</b></td>
            <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;
            </td>
       </tr>
        <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">Entity Reference</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="entityCodeList/entityReference"/>
            </td>
       </tr>
       <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">Attribute Value Reference</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="entityCodeList/valueAttributeReference"/>
            </td>
       </tr>
       <tr><td width="{$firstColWidth}" class="{$nondomainfirstColStyle}">Attribute Definition Reference</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}"><xsl:value-of select="entityCodeList/definitionAttributeReference"/>
            </td>
       </tr>
     </xsl:if>  
     
  </xsl:template>

  
</xsl:stylesheet>
