<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-attribute.xsl,v $'
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: sgarg $'
  *     '$Date: 2006-06-15 18:18:51 $'
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


<xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />

<xsl:template name="attributelist">
   <xsl:param name="docid"/>
   <xsl:param name="entitytype"/>
   <xsl:param name="entityindex"/>

   <table xsl:use-attribute-sets="cellspacing" class="{$tableattributeStyle}" width="100%">
     <xsl:choose>
      <xsl:when test="$displaymodule!='printall'">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="attributecommon">
               <xsl:with-param name="docid" select="$docid"/>
               <xsl:with-param name="entitytype" select="$entitytype"/>
               <xsl:with-param name="entityindex" select="$entityindex"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="attributecommon">
               <xsl:with-param name="docid" select="$docid"/>
               <xsl:with-param name="entitytype" select="$entitytype"/>
               <xsl:with-param name="entityindex" select="$entityindex"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
     </xsl:when>
     <xsl:otherwise>
     <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="attributecommonvertical">
               <xsl:with-param name="docid" select="$docid"/>
               <xsl:with-param name="entitytype" select="$entitytype"/>
               <xsl:with-param name="entityindex" select="$entityindex"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="attributecommonvertical">
               <xsl:with-param name="docid" select="$docid"/>
               <xsl:with-param name="entitytype" select="$entitytype"/>
               <xsl:with-param name="entityindex" select="$entityindex"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
     </xsl:otherwise>
    </xsl:choose>
  </table>
</xsl:template>


<xsl:template name="attributecommon">
   <xsl:param name="docid"/>
   <xsl:param name="entitytype"/>
   <xsl:param name="entityindex"/>


  <!-- First row for attribute name-->
  <tr><th  class="{$borderStyle}">Attribute Name</th>
  <xsl:for-each select="attribute">
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <th  align="center"  class="{$borderStyle}"><xsl:value-of select="attributeName"/></th>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <th  align="center"  class="{$borderStyle}"><xsl:value-of select="attributeName"/></th>
        </xsl:otherwise>
     </xsl:choose>
  </xsl:for-each>
  </tr>

  <!-- Second row for attribute label-->
  <tr><th  class="{$borderStyle}" >Column Label<br/></th>
   <xsl:for-each select="attribute">
    <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
              </xsl:choose>
    </xsl:variable>
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
             <xsl:choose>
                <xsl:when test="attributeLabel!=''">
                  <td  align="center" class="{$stripes}">
                     <xsl:for-each select="attributeLabel">
                       <xsl:value-of select="."/>
                         &#160;<br />
                       </xsl:for-each>
                  </td>
                </xsl:when>
                <xsl:otherwise>
                   <td  align="center" class="{$stripes}">
                       &#160;<br />
                   </td>
                </xsl:otherwise>
              </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
             <xsl:choose>
                <xsl:when test="attributeLabel!=''">
                  <td  align="center" class="{$stripes}">
                     <xsl:for-each select="attributeLabel">
                       <xsl:value-of select="."/>
                         &#160;<br/>
                       </xsl:for-each>
                  </td>
                </xsl:when>
                <xsl:otherwise>
                   <td  align="center" class="{$stripes}">
                       &#160;<br />
                   </td>
                </xsl:otherwise>
              </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:for-each>
  </tr>

  <!-- Third row for attribute defination-->
  <tr><th  class="{$borderStyle}">Definition</th>
    <xsl:for-each select="attribute">
      <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
              </xsl:choose>
      </xsl:variable>
      <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
           <xsl:for-each select="$references">
             <td  align="center" class="{$stripes}">
               <xsl:value-of select="attributeDefinition"/>
             </td>
           </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <td  align="center" class="{$stripes}">
             <xsl:value-of select="attributeDefinition"/>
          </td>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:for-each>
  </tr>

  <!-- The fourth row for attribute storage type-->
   <tr><th  class="{$borderStyle}">Type of Value<br/></th>
     <xsl:for-each select="attribute">
      <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
              </xsl:choose>
      </xsl:variable>
      <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
              <xsl:when test="storageType!=''">
                 <td  align="center" class="{$stripes}">
                    <xsl:for-each select="storageType">
                      <xsl:value-of select="."/>
                       &#160;<br/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                  <td  align="center" class="{$stripes}">
                       &#160;
                   </td>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
              <xsl:when test="storageType!=''">
                 <td  align="center" class="{$stripes}">
                    <xsl:for-each select="storageType">
                      <xsl:value-of select="."/>
                       &#160;<br/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                  <td  align="center" class="{$stripes}">
                       &#160;
                   </td>
              </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:for-each>
  </tr>

  <!-- The fifth row for meaturement type-->
  <tr><th  class="{$borderStyle}">Measurement Type</th>
   <xsl:for-each select="attribute">
    <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
              </xsl:choose>
    </xsl:variable>
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <td  align="center" class="{$stripes}">
              <xsl:for-each select="measurementScale">
                 <xsl:value-of select="local-name(./*)"/>
              </xsl:for-each>
            </td>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <td  align="center" class="{$stripes}">
              <xsl:for-each select="measurementScale">
                 <xsl:value-of select="local-name(./*)"/>
              </xsl:for-each>
           </td>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:for-each>
  </tr>

  <!-- The sixth row for meaturement domain-->
  <tr><th  class="{$borderStyle}">Measurement Domain</th>
   <xsl:for-each select="attribute">
    <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
              </xsl:choose>
    </xsl:variable>
     <xsl:variable name="innerstripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$innercolevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$innercoloddStyle"/></xsl:when>
              </xsl:choose>
    </xsl:variable>
    <xsl:variable name="attributeindex" select="position()"/>
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <td  align="center" class="{$stripes}">
              <xsl:for-each select="measurementScale">
                <xsl:call-template name="measurementscale">
                    <xsl:with-param name="docid" select="$docid"/>
                    <xsl:with-param name="entitytype" select="$entitytype"/>
                    <xsl:with-param name="entityindex" select="$entityindex"/>
                    <xsl:with-param name="attributeindex" select="$attributeindex"/>
                    <xsl:with-param name="stripes" select="$innerstripes"/>
                </xsl:call-template>
              </xsl:for-each>
            </td>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <td  align="center" class="{$stripes}">
              <xsl:for-each select="measurementScale">
                <xsl:call-template name="measurementscale">
                      <xsl:with-param name="docid" select="$docid"/>
                      <xsl:with-param name="entitytype" select="$entitytype"/>
                      <xsl:with-param name="entityindex" select="$entityindex"/>
                      <xsl:with-param name="attributeindex" select="$attributeindex"/>
                      <xsl:with-param name="stripes" select="$innerstripes"/>
                </xsl:call-template>
              </xsl:for-each>
           </td>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:for-each>
  </tr>


  <!-- The seventh row for missing value code-->
  <tr><th  class="{$borderStyle}">Missing Value Code</th>
    <xsl:for-each select="attribute">
      <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
              </xsl:choose>
     </xsl:variable>
     <xsl:variable name="innerstripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$innercolevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$innercoloddStyle"/></xsl:when>
              </xsl:choose>
     </xsl:variable>
     <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
              <xsl:when test="missingValueCode!=''">
                 <td  align="center" class="{$stripes}">
                    <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
                       <xsl:for-each select="missingValueCode">
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Code</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="code"/></td></tr>
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Expl</b></td>
                               <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="codeExplanation"/></td>
                          </tr>
                       </xsl:for-each>
                   </table>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td  class="{$stripes}">
                   &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
              <xsl:when test="missingValueCode!=''">
                 <td  align="center" class="{$stripes}">
                    <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
                       <xsl:for-each select="missingValueCode">
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Code</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="code"/></td></tr>
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Expl</b></td>
                               <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="codeExplanation"/></td>
                          </tr>
                       </xsl:for-each>
                   </table>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td  align="center" class="{$stripes}">
                   &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:for-each>
  </tr>


  <!-- The eighth row for accuracy report-->
  <tr><th  class="{$borderStyle}">Accuracy Report</th>
     <xsl:for-each select="attribute">
     <xsl:variable name="stripes">
         <xsl:choose>
             <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
             <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
         </xsl:choose>
    </xsl:variable>
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
               <xsl:when test="accuracy!=''">
                 <td  align="center" class="{$stripes}">
                    <xsl:for-each select="accuracy">
                          <xsl:value-of select="attributeAccuracyReport"/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td  align="center" class="{$stripes}">
                  &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
               <xsl:when test="accuracy!=''">
                 <td  align="center" class="{$stripes}">
                    <xsl:for-each select="accuracy">
                          <xsl:value-of select="attributeAccuracyReport"/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td  align="center" class="{$stripes}">
                  &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
  </xsl:for-each>
  </tr>

  <!-- The ninth row for quality accuracy accessment -->
  <tr><th  class="{$borderStyle}">Accuracy Assessment</th>
     <xsl:for-each select="attribute">
     <xsl:variable name="stripes">
         <xsl:choose>
             <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
             <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
         </xsl:choose>
    </xsl:variable>
    <xsl:variable name="innerstripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$innercolevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$innercoloddStyle"/></xsl:when>
              </xsl:choose>
     </xsl:variable>
     <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
               <xsl:when test="accuracy/quantitativeAttributeAccuracyAssessment!=''">
                 <td  align="center" class="{$stripes}">
                   <xsl:for-each select="accuracy">
                     <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
                       <xsl:for-each select="quantitativeAttributeAccuracyAssessment">
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Value</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="attributeAccuracyValue"/></td>
                          </tr>
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Expl</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="attributeAccuracyExplanation"/></td>
                          </tr>
                      </xsl:for-each>
                   </table>
                 </xsl:for-each>
               </td>
             </xsl:when>
             <xsl:otherwise>
                <td  align="center" class="{$stripes}">
                  &#160;
                </td>
             </xsl:otherwise>
           </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
               <xsl:when test="accuracy/quantitativeAttributeAccuracyAssessment!=''">
                 <td  align="center" class="{$stripes}">
                   <xsl:for-each select="accuracy">
                     <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
                       <xsl:for-each select="quantitativeAttributeAccuracyAssessment">
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Value</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="attributeAccuracyValue"/></td>
                          </tr>
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Expl</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="attributeAccuracyExplanation"/></td>
                          </tr>
                      </xsl:for-each>
                   </table>
                 </xsl:for-each>
               </td>
             </xsl:when>
             <xsl:otherwise>
                <td  align="center" class="{$stripes}">
                  &#160;
                </td>
             </xsl:otherwise>
           </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
  </xsl:for-each>
  </tr>

   <!-- The tenth row for coverage-->
  <tr><th  class="{$borderStyle}">Coverage</th>
   <xsl:for-each select="attribute">
    <xsl:variable name="index" select="position()"/>
    <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
              </xsl:choose>
    </xsl:variable>
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
               <xsl:when test="coverage!=''">
                  <td  align="center" class="{$stripes}">
                    <xsl:for-each select="coverage">
                      <xsl:call-template name="attributecoverage">
                         <xsl:with-param name="docid" select="$docid"/>
                         <xsl:with-param name="entitytype" select="$entitytype"/>
                         <xsl:with-param name="entityindex" select="$entityindex"/>
                         <xsl:with-param name="attributeindex" select="$index"/>
                      </xsl:call-template>
                    </xsl:for-each>
                  </td>
               </xsl:when>
               <xsl:otherwise>
                  <td  align="center" class="{$stripes}">
                   &#160;
                  </td>
               </xsl:otherwise>
            </xsl:choose>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:choose>
               <xsl:when test="coverage!=''">
                  <td  align="center" class="{$stripes}">
                    <xsl:for-each select="coverage">
                      <xsl:call-template name="attributecoverage">
                         <xsl:with-param name="docid" select="$docid"/>
                         <xsl:with-param name="entitytype" select="$entitytype"/>
                         <xsl:with-param name="entityindex" select="$entityindex"/>
                         <xsl:with-param name="attributeindex" select="$index"/>
                      </xsl:call-template>
                    </xsl:for-each>
                  </td>
               </xsl:when>
               <xsl:otherwise>
                  <td  align="center" class="{$stripes}">
                   &#160;
                  </td>
               </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:for-each>
  </tr>


   <!-- The eleventh row for method-->
  <tr><th  class="{$borderStyle}">Method</th>
   <xsl:for-each select="attribute">
    <xsl:variable name="index" select="position()"/>
    <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
              </xsl:choose>
    </xsl:variable>
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
               <xsl:when test="method!=''">
                 <td  align="center" class="{$stripes}">
                   <xsl:for-each select="method">
                     <xsl:call-template name="attributemethod">
                       <xsl:with-param name="docid" select="$docid"/>
                       <xsl:with-param name="entitytype" select="$entitytype"/>
                       <xsl:with-param name="entityindex" select="$entityindex"/>
                       <xsl:with-param name="attributeindex" select="$index"/>
                     </xsl:call-template>
                   </xsl:for-each>
                 </td>
               </xsl:when>
               <xsl:otherwise>
                 <td  align="center" class="{$stripes}">
                   &#160;
                 </td>
               </xsl:otherwise>
            </xsl:choose>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
               <xsl:when test="method!=''">
                 <td  align="center" class="{$stripes}">
                   <xsl:for-each select="method">
                     <xsl:call-template name="attributemethod">
                       <xsl:with-param name="docid" select="$docid"/>
                       <xsl:with-param name="entitytype" select="$entitytype"/>
                       <xsl:with-param name="entityindex" select="$entityindex"/>
                       <xsl:with-param name="attributeindex" select="$index"/>
                     </xsl:call-template>
                   </xsl:for-each>
                 </td>
               </xsl:when>
               <xsl:otherwise>
                 <td  align="center" class="{$stripes}">
                   &#160;
                 </td>
               </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:for-each>
  </tr>
 </xsl:template>




<xsl:template name="attributecommonvertical">
   <xsl:param name="docid"/>
   <xsl:param name="entitytype"/>
   <xsl:param name="entityindex"/>

  <tr><th  class="{$firstColStyle}">Attribute Name</th>
      <th  class="{$firstColStyle}">Column Label</th>
      <th  class="{$firstColStyle}">Definition</th>
      <th  class="{$firstColStyle}">Type of Value</th>
      <th  class="{$firstColStyle}">Measurement Type</th>
      <th  class="{$firstColStyle}">Measurement Domain</th>
      <th  class="{$firstColStyle}">Missing Value Code</th>
      <th  class="{$firstColStyle}">Accuracy Report</th>
      <th  class="{$firstColStyle}">Accuracy Assessment</th>
      <th  class="{$firstColStyle}">Coverage</th>
      <th  class="{$firstColStyle}">Method</th>
  </tr>

  <xsl:for-each select="attribute">
     <xsl:variable name="index" select="position()"/>
     <xsl:variable name="stripes">
     	<xsl:choose>
          <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
          <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
        </xsl:choose>
     </xsl:variable>
    <xsl:variable name="innerstripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$innercolevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$innercoloddStyle"/></xsl:when>
              </xsl:choose>
     </xsl:variable>

    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <tr>
              <th  align="center"  class="{$borderStyle}"><xsl:value-of select="attributeName"/></th>

             <xsl:choose>
                <xsl:when test="attributeLabel!=''">
                  <td  align="center" class="{$colevenStyle}">
                     <xsl:for-each select="attributeLabel">
                       <xsl:value-of select="."/>
                         &#160;<br />
                       </xsl:for-each>
                  </td>
                </xsl:when>
                <xsl:otherwise>
                   <td  align="center" class="{$colevenStyle}">
                       &#160;<br />
                   </td>
                </xsl:otherwise>
              </xsl:choose>

             <td  align="center" class="{$coloddStyle}">
               <xsl:value-of select="attributeDefinition"/>
             </td>

            <xsl:choose>
              <xsl:when test="storageType!=''">
                 <td  align="center" class="{$colevenStyle}">
                    <xsl:for-each select="storageType">
                      <xsl:value-of select="."/>
                       &#160;<br/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                  <td  align="center" class="{$colevenStyle}">
                       &#160;
                   </td>
              </xsl:otherwise>
            </xsl:choose>

    <td  align="center" class="{$coloddStyle}">
              <xsl:for-each select="measurementScale">
                 <xsl:value-of select="local-name(./*)"/>
              </xsl:for-each>
            </td>


      	<td  align="center" class="{$colevenStyle}">
              <xsl:for-each select="measurementScale">
                <xsl:call-template name="measurementscale">
                    <xsl:with-param name="docid" select="$docid"/>
                    <xsl:with-param name="entitytype" select="$entitytype"/>
                    <xsl:with-param name="entityindex" select="$entityindex"/>
                    <xsl:with-param name="attributeindex" select="$attributeindex"/>
                    <xsl:with-param name="stripes" select="$innerstripes"/>
                </xsl:call-template>
              </xsl:for-each>
 	</td>

            <xsl:choose>
              <xsl:when test="missingValueCode!=''">
                 <td  align="center" class="{$coloddStyle}">
                    <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
                       <xsl:for-each select="missingValueCode">
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Code</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="code"/></td></tr>
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Expl</b></td>
                               <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="codeExplanation"/></td>
                          </tr>
                       </xsl:for-each>
                   </table>
                 </td>
              </xsl:when>
              <xsl:otherwise>
               <td  class="{$coloddStyle}">
                   &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>


            <xsl:choose>
               <xsl:when test="accuracy!=''">
                 <td  align="center" class="{$colevenStyle}">
                    <xsl:for-each select="accuracy">
                          <xsl:value-of select="attributeAccuracyReport"/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td  align="center" class="{$colevenStyle}">
                  &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>


            <xsl:choose>
               <xsl:when test="accuracy/quantitativeAttributeAccuracyAssessment!=''">
                 <td  align="center" class="{$coloddStyle}">
                   <xsl:for-each select="accuracy">
                     <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
                       <xsl:for-each select="quantitativeAttributeAccuracyAssessment">
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Value</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="attributeAccuracyValue"/></td>
                          </tr>
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Expl</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="attributeAccuracyExplanation"/></td>
                          </tr>
                      </xsl:for-each>
                   </table>
                 </xsl:for-each>
               </td>
             </xsl:when>
             <xsl:otherwise>
                <td  align="center" class="{$coloddStyle}">
                  &#160;
                </td>
             </xsl:otherwise>
           </xsl:choose>


            <xsl:choose>
               <xsl:when test="coverage!=''">
                  <td  align="center" class="{$colevenStyle}">
                    <xsl:for-each select="coverage">
                      <xsl:call-template name="attributecoverage">
                         <xsl:with-param name="docid" select="$docid"/>
                         <xsl:with-param name="entitytype" select="$entitytype"/>
                         <xsl:with-param name="entityindex" select="$entityindex"/>
                         <xsl:with-param name="attributeindex" select="$index"/>
                      </xsl:call-template>
                    </xsl:for-each>
                  </td>
               </xsl:when>
               <xsl:otherwise>
                  <td  align="center" class="{$colevenStyle}">
                   &#160;
                  </td>
               </xsl:otherwise>
            </xsl:choose>


	    	<xsl:choose>
               	<xsl:when test="method!=''">
                 <td  align="center" class="{$coloddStyle}">
                   <xsl:for-each select="method">
                     <xsl:call-template name="attributemethod">
                       <xsl:with-param name="docid" select="$docid"/>
                       <xsl:with-param name="entitytype" select="$entitytype"/>
                       <xsl:with-param name="entityindex" select="$entityindex"/>
                       <xsl:with-param name="attributeindex" select="$index"/>
                     </xsl:call-template>
                   </xsl:for-each>
                 </td>
               </xsl:when>
               <xsl:otherwise>
                 <td  align="center" class="{$coloddStyle}">
                   &#160;
                 </td>
               </xsl:otherwise>
            </xsl:choose>
            </tr>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
	 <tr>
          <th  align="center"  class="{$borderStyle}"><xsl:value-of select="attributeName"/></th>

             <xsl:choose>
                <xsl:when test="attributeLabel!=''">
                  <td  align="center" class="{$colevenStyle}">
                     <xsl:for-each select="attributeLabel">
                       <xsl:value-of select="."/>
                         &#160;<br />
                       </xsl:for-each>
                  </td>
                </xsl:when>
                <xsl:otherwise>
                   <td  align="center" class="{$colevenStyle}">
                       &#160;<br />
                   </td>
                </xsl:otherwise>
              </xsl:choose>

             <td  align="center" class="{$coloddStyle}">
               <xsl:value-of select="attributeDefinition"/>
             </td>

            <xsl:choose>
              <xsl:when test="storageType!=''">
                 <td  align="center" class="{$colevenStyle}">
                    <xsl:for-each select="storageType">
                      <xsl:value-of select="."/>
                       &#160;<br/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                  <td  align="center" class="{$colevenStyle}">
                       &#160;
                   </td>
              </xsl:otherwise>
            </xsl:choose>

    <td  align="center" class="{$coloddStyle}">
              <xsl:for-each select="measurementScale">
                 <xsl:value-of select="local-name(./*)"/>
              </xsl:for-each>
            </td>

	<td  align="center" class="{$coloddStyle}">
              <xsl:for-each select="measurementScale">
                <xsl:call-template name="measurementscale">
                    <xsl:with-param name="docid" select="$docid"/>
                    <xsl:with-param name="entitytype" select="$entitytype"/>
                    <xsl:with-param name="entityindex" select="$entityindex"/>
                    <xsl:with-param name="attributeindex" select="$attributeindex"/>
                    <xsl:with-param name="stripes" select="$innerstripes"/>
                </xsl:call-template>
              </xsl:for-each>
	</td>

            <xsl:choose>
              <xsl:when test="missingValueCode!=''">
                 <td  align="center" class="{$colevenStyle}">
                    <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
                       <xsl:for-each select="missingValueCode">
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Code</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="code"/></td></tr>
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Expl</b></td>
                               <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="codeExplanation"/></td>
                          </tr>
                       </xsl:for-each>
                   </table>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td  class="{$colevenStyle}">
                   &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>

            <xsl:choose>
               <xsl:when test="accuracy!=''">
                 <td  align="center" class="{$coloddStyle}">
                    <xsl:for-each select="accuracy">
                          <xsl:value-of select="attributeAccuracyReport"/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td  align="center" class="{$coloddStyle}">
                  &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>

            <xsl:choose>
               <xsl:when test="accuracy/quantitativeAttributeAccuracyAssessment!=''">
                 <td  align="center" class="{$colevenStyle}">
                   <xsl:for-each select="accuracy">
                     <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
                       <xsl:for-each select="quantitativeAttributeAccuracyAssessment">
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Value</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="attributeAccuracyValue"/></td>
                          </tr>
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Expl</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="attributeAccuracyExplanation"/></td>
                          </tr>
                      </xsl:for-each>
                   </table>
                 </xsl:for-each>
               </td>
             </xsl:when>
             <xsl:otherwise>
                <td  align="center" class="{$colevenStyle}">
                  &#160;
                </td>
             </xsl:otherwise>
           </xsl:choose>


            <xsl:choose>
               <xsl:when test="coverage!=''">
                  <td  align="center" class="{$coloddStyle}">
                    <xsl:for-each select="coverage">
                      <xsl:call-template name="attributecoverage">
                         <xsl:with-param name="docid" select="$docid"/>
                         <xsl:with-param name="entitytype" select="$entitytype"/>
                         <xsl:with-param name="entityindex" select="$entityindex"/>
                         <xsl:with-param name="attributeindex" select="$index"/>
                      </xsl:call-template>
                    </xsl:for-each>
                  </td>
               </xsl:when>
               <xsl:otherwise>
                  <td  align="center" class="{$coloddStyle}">
                   &#160;
                  </td>
               </xsl:otherwise>
            </xsl:choose>


	    	<xsl:choose>
               	<xsl:when test="method!=''">
                 <td  align="center" class="{$colevenStyle}">
                   <xsl:for-each select="method">
                     <xsl:call-template name="attributemethod">
                       <xsl:with-param name="docid" select="$docid"/>
                       <xsl:with-param name="entitytype" select="$entitytype"/>
                       <xsl:with-param name="entityindex" select="$entityindex"/>
                       <xsl:with-param name="attributeindex" select="$index"/>
                     </xsl:call-template>
                   </xsl:for-each>
                 </td>
               </xsl:when>
               <xsl:otherwise>
                 <td  align="center" class="{$colevenStyle}">
                   &#160;
                 </td>
               </xsl:otherwise>
            </xsl:choose>

         </tr>
        </xsl:otherwise>
     </xsl:choose>
  </xsl:for-each>

 </xsl:template>




<xsl:template name="singleattribute">
   <xsl:param name="docid"/>
   <xsl:param name="entitytype"/>
   <xsl:param name="entityindex"/>
   <xsl:param name="attributeindex"/>

   <table xsl:use-attribute-sets="cellspacing" class="{$tableattributeStyle}" width="100%">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="singleattributecommon">
               <xsl:with-param name="docid" select="$docid"/>
               <xsl:with-param name="entitytype" select="$entitytype"/>
               <xsl:with-param name="entityindex" select="$entityindex"/>
               <xsl:with-param name="attributeindex" select="$attributeindex"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="singleattributecommon">
               <xsl:with-param name="docid" select="$docid"/>
               <xsl:with-param name="entitytype" select="$entitytype"/>
               <xsl:with-param name="entityindex" select="$entityindex"/>
               <xsl:with-param name="attributeindex" select="$attributeindex"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
  </table>
</xsl:template>


<xsl:template name="singleattributecommon">
   <xsl:param name="docid"/>
   <xsl:param name="entitytype"/>
   <xsl:param name="entityindex"/>
   <xsl:param name="attributeindex"/>

  <!-- First row for attribute name-->
  <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Column Name</td>
  <xsl:for-each select="attribute">
   <xsl:if test="position() = $attributeindex">
      <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <td class="{$borderStyle}"><b><xsl:value-of select="attributeName"/></b></td>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <td class="{$borderStyle}"><b><xsl:value-of select="attributeName"/></b></td>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:if>
  </xsl:for-each>
  </tr>

  <!-- Second row for attribute label-->
  <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Column Label<br/></td>
   <xsl:for-each select="attribute">
    <xsl:if test="position() = $attributeindex">
    <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
              </xsl:choose>
    </xsl:variable>
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
             <xsl:choose>
                <xsl:when test="attributeLabel!=''">
                  <td  >
                     <xsl:for-each select="attributeLabel">
                       <xsl:value-of select="."/>
                         &#160;<br />
                       </xsl:for-each>
                  </td>
                </xsl:when>
                <xsl:otherwise>
                   <td  >
                       &#160;<br />
                   </td>
                </xsl:otherwise>
              </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
             <xsl:choose>
                <xsl:when test="attributeLabel!=''">
                  <td  >
                     <xsl:for-each select="attributeLabel">
                       <xsl:value-of select="."/>
                         &#160;<br/>
                       </xsl:for-each>
                  </td>
                </xsl:when>
                <xsl:otherwise>
                   <td  >
                       &#160;<br />
                   </td>
                </xsl:otherwise>
              </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
    </xsl:if>
   </xsl:for-each>
  </tr>

  <!-- Third row for attribute defination-->
  <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Definition</td>
    <xsl:for-each select="attribute">
     <xsl:if test="position() = $attributeindex">
      <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
              </xsl:choose>
      </xsl:variable>
      <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
           <xsl:for-each select="$references">
             <td  >
               <xsl:value-of select="attributeDefinition"/>
             </td>
           </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <td  >
             <xsl:value-of select="attributeDefinition"/>
          </td>
        </xsl:otherwise>
     </xsl:choose>
    </xsl:if>
   </xsl:for-each>
  </tr>

  <!-- The fourth row for attribute storage type-->
   <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Type of Value<br/></td>
     <xsl:for-each select="attribute">
      <xsl:if test="position() = $attributeindex">
      <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
              </xsl:choose>
      </xsl:variable>
      <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
              <xsl:when test="storageType!=''">
                 <td  >
                    <xsl:for-each select="storageType">
                      <xsl:value-of select="."/>
                       &#160;<br/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                  <td  >
                       &#160;
                   </td>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
              <xsl:when test="storageType!=''">
                 <td  >
                    <xsl:for-each select="storageType">
                      <xsl:value-of select="."/>
                       &#160;<br/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                  <td  >
                       &#160;
                   </td>
              </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
    </xsl:if>
   </xsl:for-each>
  </tr>

  <!-- The fifth row for meaturement type-->
  <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Measurement Type</td>
   <xsl:for-each select="attribute">
    <xsl:if test="position() = $attributeindex">
    <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
              </xsl:choose>
    </xsl:variable>
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <td  >
              <xsl:for-each select="measurementScale">
                 <xsl:value-of select="local-name(./*)"/>
              </xsl:for-each>
            </td>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <td  >
              <xsl:for-each select="measurementScale">
                 <xsl:value-of select="local-name(./*)"/>
              </xsl:for-each>
           </td>
        </xsl:otherwise>
     </xsl:choose>
    </xsl:if>
   </xsl:for-each>
  </tr>

  <!-- The sixth row for meaturement domain-->
  <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Measurement Domain</td>
   <xsl:for-each select="attribute">
    <xsl:if test="position() = $attributeindex">
    <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
              </xsl:choose>
    </xsl:variable>
     <xsl:variable name="innerstripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$innercolevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$innercoloddStyle"/></xsl:when>
              </xsl:choose>
    </xsl:variable>
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <td  >
              <xsl:for-each select="measurementScale">
                <xsl:call-template name="measurementscale">
                    <xsl:with-param name="docid" select="$docid"/>
                    <xsl:with-param name="entitytype" select="$entitytype"/>
                    <xsl:with-param name="entityindex" select="$entityindex"/>
                    <xsl:with-param name="attributeindex" select="$attributeindex"/>
                    <xsl:with-param name="stripes" select="$innercoloddStyle"/>
                </xsl:call-template>
              </xsl:for-each>
            </td>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <td  >
              <xsl:for-each select="measurementScale">
                <xsl:call-template name="measurementscale">
                      <xsl:with-param name="docid" select="$docid"/>
                      <xsl:with-param name="entitytype" select="$entitytype"/>
                      <xsl:with-param name="entityindex" select="$entityindex"/>
                      <xsl:with-param name="attributeindex" select="$attributeindex"/>
                      <xsl:with-param name="stripes" select="$innercoloddStyle"/>
                </xsl:call-template>
              </xsl:for-each>
           </td>
        </xsl:otherwise>
     </xsl:choose>
    </xsl:if>
   </xsl:for-each>
  </tr>


  <!-- The seventh row for missing value code-->
  <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Missing Value Code</td>
    <xsl:for-each select="attribute">
     <xsl:if test="position() = $attributeindex">
      <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
              </xsl:choose>
     </xsl:variable>
     <xsl:variable name="innerstripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$innercolevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$innercoloddStyle"/></xsl:when>
              </xsl:choose>
     </xsl:variable>
     <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
              <xsl:when test="missingValueCode!=''">
                 <td  >
                    <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
                       <xsl:for-each select="missingValueCode">
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Code</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="code"/></td></tr>
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Expl</b></td>
                               <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="codeExplanation"/></td>
                          </tr>
                       </xsl:for-each>
                   </table>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td >
                   &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
              <xsl:when test="missingValueCode!=''">
                 <td  >
                    <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
                       <xsl:for-each select="missingValueCode">
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Code</b></td>                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="code"/></td></tr>
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Expl</b></td>
                               <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="codeExplanation"/></td>
                          </tr>
                       </xsl:for-each>
                   </table>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td  >
                   &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
     </xsl:if>
   </xsl:for-each>
  </tr>


  <!-- The eighth row for accuracy report-->
  <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Accuracy Report</td>
     <xsl:for-each select="attribute">
     <xsl:if test="position() = $attributeindex">
     <xsl:variable name="stripes">
         <xsl:choose>
             <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
             <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
         </xsl:choose>
    </xsl:variable>
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
               <xsl:when test="accuracy!=''">
                 <td  >
                    <xsl:for-each select="accuracy">
                          <xsl:value-of select="attributeAccuracyReport"/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td  >
                  &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
               <xsl:when test="accuracy!=''">
                 <td  >
                    <xsl:for-each select="accuracy">
                          <xsl:value-of select="attributeAccuracyReport"/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td  >
                  &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:if>
  </xsl:for-each>
  </tr>

  <!-- The ninth row for quality accuracy accessment -->
  <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Accuracy Assessment</td>
     <xsl:for-each select="attribute">
     <xsl:if test="position() = $attributeindex">
     <xsl:variable name="stripes">
         <xsl:choose>
             <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
             <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
         </xsl:choose>
    </xsl:variable>
    <xsl:variable name="innerstripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$innercolevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$innercoloddStyle"/></xsl:when>
              </xsl:choose>
     </xsl:variable>
     <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
               <xsl:when test="accuracy/quantitativeAttributeAccuracyAssessment!=''">
                 <td  >
                   <xsl:for-each select="accuracy">
                     <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
                       <xsl:for-each select="quantitativeAttributeAccuracyAssessment">
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Value</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="attributeAccuracyValue"/></td>
                          </tr>
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Expl</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="attributeAccuracyExplanation"/></td>
                          </tr>
                      </xsl:for-each>
                   </table>
                 </xsl:for-each>
               </td>
             </xsl:when>
             <xsl:otherwise>
                <td  >
                  &#160;
                </td>
             </xsl:otherwise>
           </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
               <xsl:when test="accuracy/quantitativeAttributeAccuracyAssessment!=''">
                 <td  >
                   <xsl:for-each select="accuracy">
                     <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
                       <xsl:for-each select="quantitativeAttributeAccuracyAssessment">
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Value</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="attributeAccuracyValue"/></td>
                          </tr>
                          <tr><td width="{$firstColWidth}" class="{$innerstripes}"><b>Expl</b></td>
                              <td width="{$secondColWidth}" class="{$innerstripes}"><xsl:value-of select="attributeAccuracyExplanation"/></td>
                          </tr>
                      </xsl:for-each>
                   </table>
                 </xsl:for-each>
               </td>
             </xsl:when>
             <xsl:otherwise>
                <td  >
                  &#160;
                </td>
             </xsl:otherwise>
           </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:if>
  </xsl:for-each>
  </tr>

   <!-- The tenth row for coverage-->
  <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Coverage</td>
   <xsl:for-each select="attribute">
    <xsl:if test="position() = $attributeindex">
    <xsl:variable name="index" select="position()"/>
    <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
              </xsl:choose>
    </xsl:variable>
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
               <xsl:when test="coverage!=''">
                  <td  >
                    <xsl:for-each select="coverage">
                      <xsl:call-template name="attributecoverage">
                         <xsl:with-param name="docid" select="$docid"/>
                         <xsl:with-param name="entitytype" select="$entitytype"/>
                         <xsl:with-param name="entityindex" select="$entityindex"/>
                         <xsl:with-param name="attributeindex" select="$index"/>
                      </xsl:call-template>
                    </xsl:for-each>
                  </td>
               </xsl:when>
               <xsl:otherwise>
                  <td  >
                   &#160;
                  </td>
               </xsl:otherwise>
            </xsl:choose>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:choose>
               <xsl:when test="coverage!=''">
                  <td  >
                    <xsl:for-each select="coverage">
                      <xsl:call-template name="attributecoverage">
                         <xsl:with-param name="docid" select="$docid"/>
                         <xsl:with-param name="entitytype" select="$entitytype"/>
                         <xsl:with-param name="entityindex" select="$entityindex"/>
                         <xsl:with-param name="attributeindex" select="$index"/>
                      </xsl:call-template>
                    </xsl:for-each>
                  </td>
               </xsl:when>
               <xsl:otherwise>
                  <td  >
                   &#160;
                  </td>
               </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
    </xsl:if>
   </xsl:for-each>
  </tr>


   <!-- The eleventh row for method-->
  <tr><td width="{$firstColWidth}" class="{$firstColStyle}">Method</td>
   <xsl:for-each select="attribute">
    <xsl:if test="position() = $attributeindex">
    <xsl:variable name="index" select="position()"/>
    <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 0"><xsl:value-of select="$colevenStyle"/></xsl:when>
                <xsl:when test="position() mod 2 = 1"><xsl:value-of select="$coloddStyle"/></xsl:when>
              </xsl:choose>
    </xsl:variable>
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
               <xsl:when test="method!=''">
                 <td  >
                   <xsl:for-each select="method">
                     <xsl:call-template name="attributemethod">
                       <xsl:with-param name="docid" select="$docid"/>
                       <xsl:with-param name="entitytype" select="$entitytype"/>
                       <xsl:with-param name="entityindex" select="$entityindex"/>
                       <xsl:with-param name="attributeindex" select="$index"/>
                     </xsl:call-template>
                   </xsl:for-each>
                 </td>
               </xsl:when>
               <xsl:otherwise>
                 <td  >
                   &#160;
                 </td>
               </xsl:otherwise>
            </xsl:choose>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
               <xsl:when test="method!=''">
                 <td  >
                   <xsl:for-each select="method">
                     <xsl:call-template name="attributemethod">
                       <xsl:with-param name="docid" select="$docid"/>
                       <xsl:with-param name="entitytype" select="$entitytype"/>
                       <xsl:with-param name="entityindex" select="$entityindex"/>
                       <xsl:with-param name="attributeindex" select="$index"/>
                     </xsl:call-template>
                   </xsl:for-each>
                 </td>
               </xsl:when>
               <xsl:otherwise>
                 <td  >
                   &#160;
                 </td>
               </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
    </xsl:if>
   </xsl:for-each>
  </tr>
 </xsl:template>


<xsl:template name="measurementscale">
   <xsl:param name="stripes"/>
   <xsl:param name="docid"/>
   <xsl:param name="entitytype"/>
   <xsl:param name="entityindex"/>
   <xsl:param name="attributeindex"/>
   <table xsl:use-attribute-sets="cellspacing" class="tableinattribute" width="100%">
    <xsl:for-each select="nominal">
         <xsl:call-template name="attributenonnumericdomain">
               <xsl:with-param name="docid" select="$docid"/>
               <xsl:with-param name="entitytype" select="$entitytype"/>
               <xsl:with-param name="entityindex" select="$entityindex"/>
               <xsl:with-param name="attributeindex" select="$attributeindex"/>
               <xsl:with-param name="stripes" select="$stripes"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="ordinal">
       <xsl:call-template name="attributenonnumericdomain">
               <xsl:with-param name="docid" select="$docid"/>
               <xsl:with-param name="entitytype" select="$entitytype"/>
               <xsl:with-param name="entityindex" select="$entityindex"/>
               <xsl:with-param name="attributeindex" select="$attributeindex"/>
               <xsl:with-param name="stripes" select="$stripes"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="interval">
       <xsl:call-template name="intervalratio">
         <xsl:with-param name="stripes" select="$stripes"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="ratio">
       <xsl:call-template name="intervalratio">
         <xsl:with-param name="stripes" select="$stripes"/>
       </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="datetime">
       <xsl:call-template name="datetime">
          <xsl:with-param name="stripes" select="$stripes"/>
       </xsl:call-template>
    </xsl:for-each>
   </table>
 </xsl:template>

 <xsl:template name="attributenonnumericdomain">
   <xsl:param name="stripes"/>
   <xsl:param name="docid"/>
   <xsl:param name="entitytype"/>
   <xsl:param name="entityindex"/>
   <xsl:param name="attributeindex"/>
   <xsl:for-each select="nonNumericDomain">
     <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="attributenonnumericdomaincommon">
                <xsl:with-param name="docid" select="$docid"/>
                <xsl:with-param name="entitytype" select="$entitytype"/>
                <xsl:with-param name="entityindex" select="$entityindex"/>
                <xsl:with-param name="attributeindex" select="$attributeindex"/>
                <xsl:with-param name="stripes" select="$stripes"/>
            </xsl:call-template>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
            <xsl:call-template name="attributenonnumericdomaincommon">
               <xsl:with-param name="docid" select="$docid"/>
               <xsl:with-param name="entitytype" select="$entitytype"/>
               <xsl:with-param name="entityindex" select="$entityindex"/>
               <xsl:with-param name="attributeindex" select="$attributeindex"/>
               <xsl:with-param name="stripes" select="$stripes"/>
            </xsl:call-template>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:for-each>
 </xsl:template>

 <xsl:template name="attributenonnumericdomaincommon">
    <xsl:param name="stripes"/>
    <xsl:param name="docid"/>
    <xsl:param name="entitytype"/>
    <xsl:param name="entityindex"/>
    <xsl:param name="attributeindex"/>
    <!-- if numericdomain only has one test domain,
        it will be displayed inline otherwith will be show a link-->
    <xsl:choose>
      <xsl:when test="count(textDomain)=1 and not(enumeratedDomain)">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Def</b></td>
            <td width="{$secondColWidth}" class="{$stripes}"><xsl:value-of select="textDomain/definition"/>
            </td>
        </tr>
        <xsl:for-each select="textDomain/parttern">
          <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Pattern</b></td>
            <td width="{$secondColWidth}" class="{$stripes}"><xsl:value-of select="."/>
            </td>
          </tr>
        </xsl:for-each>
        <xsl:for-each select="textDomain/source">
          <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Source</b></td>
            <td width="{$secondColWidth}" class="{$stripes}"><xsl:value-of select="."/>
            </td>
          </tr>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
       <tr><td colspan="2" align="center" class="{$stripes}" >
       <xsl:if test="$withHTMLLinks='1'">
           <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/>&amp;displaymodule=attributedomain&amp;entitytype=<xsl:value-of select="$entitytype"/>&amp;entityindex=<xsl:value-of select="$entityindex"/>&amp;attributeindex=<xsl:value-of select="$attributeindex"/></xsl:attribute>
           <b>Domain Info</b>
           </a>
       </xsl:if>
       <xsl:if test="$withHTMLLinks='0' and $displaymodule!='printall'">
         <xsl:call-template name="nonNumericDomain">
           <xsl:with-param name="displaymodule" select="$displaymodule"/>
           <xsl:with-param name="entitytype" select="$entitytype"/>
           <xsl:with-param name="entityindex" select="$entityindex"/>
           <xsl:with-param name="attributeindex" select="$attributeindex"/>
           <xsl:with-param name="nondomainfirstColStyle" select="$firstColStyle"/>
         </xsl:call-template>
       </xsl:if>
        <xsl:if test="$withHTMLLinks='0' and $displaymodule='printall'">
         <xsl:call-template name="nonNumericDomain">
           <xsl:with-param name="displaymodule" select="$displaymodule"/>
           <xsl:with-param name="entitytype" select="$entitytype"/>
           <xsl:with-param name="entityindex" select="$entityindex"/>
           <xsl:with-param name="attributeindex" select="$attributeindex"/>
           <xsl:with-param name="nondomainfirstColStyle"> </xsl:with-param>
         </xsl:call-template>
       </xsl:if>
       </td></tr>
      </xsl:otherwise>
    </xsl:choose>
 </xsl:template>

 <xsl:template name="intervalratio">
    <xsl:param name="stripes"/>
    <xsl:if test="unit/standardUnit">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Unit</b></td>
            <td width="{$secondColWidth}" class="{$stripes}"><xsl:value-of select="unit/standardUnit"/>
            </td>
      </tr>
    </xsl:if>
    <xsl:if test="unit/customUnit">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Unit</b></td>
            <td width="{$secondColWidth}" class="{$stripes}"><xsl:value-of select="unit/customUnit"/>
            </td>
      </tr>
   </xsl:if>
   <xsl:for-each select="precision">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Precision</b></td>
            <td width="{$secondColWidth}" class="{$stripes}"><xsl:value-of select="."/>
            </td>
      </tr>
   </xsl:for-each>
   <xsl:for-each select="numericDomain">
      <xsl:call-template name="numericDomain">
         <xsl:with-param name="stripes" select="$stripes"/>
      </xsl:call-template>
    </xsl:for-each>
  </xsl:template>


 <xsl:template name="numericDomain">
     <xsl:param name="stripes"/>
       <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Type</b></td>
                <td width="{$secondColWidth}" class="{$stripes}"><xsl:value-of select="numberType"/>
                </td>
            </tr>
            <xsl:for-each select="bounds">
              <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Min</b></td>
                  <td width="{$secondColWidth}" class="{$stripes}">
                    <xsl:for-each select="minimum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
              <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Max</b></td>
                  <td width="{$secondColWidth}" class="{$stripes}">
                    <xsl:for-each select="maximum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
            </xsl:for-each>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Type</b></td>
                <td width="{$secondColWidth}" class="{$stripes}"><xsl:value-of select="numberType"/>
                </td>
            </tr>
            <xsl:for-each select="bounds">
              <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Min</b></td>
                  <td width="{$secondColWidth}" class="{$stripes}">
                    <xsl:for-each select="minimum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
              <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Max</b></td>
                  <td width="{$secondColWidth}" class="{$stripes}">
                    <xsl:for-each select="maximum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
            </xsl:for-each>
        </xsl:otherwise>
      </xsl:choose>
  </xsl:template>

 <xsl:template name="datetime">
    <xsl:param name="stripes"/>
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Format</b></td>
         <td width="{$secondColWidth}" class="{$stripes}">
            <xsl:value-of select="formatString"/>
         </td>
    </tr>
     <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Precision</b></td>
         <td width="{$secondColWidth}" class="{$stripes}">
            <xsl:value-of select="dateTimePrecision"/>
         </td>
    </tr>
    <xsl:call-template name="timedomain"/>
 </xsl:template>


 <xsl:template name="timedomain">
    <xsl:param name="stripes"/>
      <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:for-each select="bounds">
              <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Min</b></td>
                  <td width="{$secondColWidth}" class="{$stripes}">
                    <xsl:for-each select="minimum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
              <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Max</b></td>
                  <td width="{$secondColWidth}" class="{$stripes}">
                    <xsl:for-each select="maximum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
            </xsl:for-each>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:for-each select="bounds">
              <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Min</b></td>
                  <td width="{$secondColWidth}" class="{$stripes}">
                    <xsl:for-each select="minimum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
              <tr><td width="{$firstColWidth}" class="{$firstColStyle}"><b>Max</b></td>
                  <td width="{$secondColWidth}" class="{$stripes}">
                    <xsl:for-each select="maximum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
            </xsl:for-each>
        </xsl:otherwise>
      </xsl:choose>
  </xsl:template>

 <xsl:template name="attributecoverage">
    <xsl:param name="docid"/>
    <xsl:param name="entitytype"/>
    <xsl:param name="entityindex"/>
    <xsl:param name="attributeindex"/>
       <xsl:if test="$withHTMLLinks='1'">
          <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/>&amp;displaymodule=attributecoverage&amp;entitytype=<xsl:value-of select="$entitytype"/>&amp;entityindex=<xsl:value-of select="$entityindex"/>&amp;attributeindex=<xsl:value-of select="$attributeindex"/></xsl:attribute>
           <b>Coverage Info</b></a>
       </xsl:if>
       <xsl:if test="$withHTMLLinks='0'">
          <xsl:call-template name="coverage">
          </xsl:call-template>
       </xsl:if>
 </xsl:template>

 <xsl:template name="attributemethod">
    <xsl:param name="docid"/>
    <xsl:param name="entitytype"/>
    <xsl:param name="entityindex"/>
    <xsl:param name="attributeindex"/>
    <xsl:if test="$withHTMLLinks='1'">
       <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/>&amp;displaymodule=attributemethod&amp;entitytype=<xsl:value-of select="$entitytype"/>&amp;entityindex=<xsl:value-of select="$entityindex"/>&amp;attributeindex=<xsl:value-of select="$attributeindex"/></xsl:attribute>
       <b>Method Info</b></a>
    </xsl:if>
    <xsl:if test="$withHTMLLinks='0'">
      <xsl:call-template name="method">
        <xsl:with-param name="methodfirstColStyle" select="$firstColStyle"/>
        <xsl:with-param name="methodsubHeaderStyle" select="$firstColStyle"/>
      </xsl:call-template>
    </xsl:if>
</xsl:template>

</xsl:stylesheet>
