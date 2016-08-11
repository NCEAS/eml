<?xml version="1.0" encoding="utf-8"?>
<!--
  *  '$RCSfile$'
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


<xsl:output method="html" encoding="UTF-8"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  
              
<xsl:param name="annotationId"/>              

<xsl:template name="attributelist">
   <xsl:param name="docid"/>
   <xsl:param name="entitytype"/>
   <xsl:param name="entityindex"/>

   <table class="{$tabledefaultStyle}">
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
  </table>
</xsl:template>


<xsl:template name="attributecommon">
   <xsl:param name="docid"/>
   <xsl:param name="entitytype"/>
   <xsl:param name="entityindex"/>


  <!-- First row for attribute name-->
  <tr>
  	<th class="rowodd">Name</th>
  	<th class="rowodd">Column Label</th>
  	<!--another row for Semantics -->
  	<xsl:if test="$annotationId != ''">
		<th class="rowodd">Measurement</th>
	</xsl:if>
  	<th class="rowodd">Definition</th>
  	<th class="rowodd">Type of Value</th>
  	<th class="rowodd">Measurement Type</th>
  	<th class="rowodd">Measurement Domain</th>
  	<th class="rowodd">Missing Value Code</th>
  	<th class="rowodd">Accuracy Report</th>
  	<th class="rowodd">Accuracy Assessment</th>
  	<th class="rowodd">Coverage</th>
  	<th class="rowodd">Method</th>
  </tr>
  
  <xsl:for-each select="attribute">
  
  	<xsl:variable name="attributeindex" select="position()"/>
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
    
  	<tr class="attributes">
  	
  	<td class="{$stripes}">
    <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:value-of select="attributeName"/>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
        	<xsl:value-of select="attributeName"/>
        </xsl:otherwise>
     </xsl:choose>
     </td>
     
     <td class="{$stripes}">
     <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
             <xsl:choose>
                <xsl:when test="attributeLabel!=''">
                     <xsl:for-each select="attributeLabel">
                       <xsl:value-of select="."/>
                         &#160;<br />
                       </xsl:for-each>
                </xsl:when>
                <xsl:otherwise>
                       &#160;<br />
                </xsl:otherwise>
              </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
             <xsl:choose>
                <xsl:when test="attributeLabel!=''">
                     <xsl:for-each select="attributeLabel">
                       <xsl:value-of select="."/>
                         &#160;<br/>
                       </xsl:for-each>
                </xsl:when>
                <xsl:otherwise>
                       &#160;<br />
                </xsl:otherwise>
              </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
     </td>
     
     <!--another row for Semantics -->
  	<xsl:if test="$annotationId != ''">
	 
		<td class="{$stripes}">
			<!-- handle references -->
			<xsl:variable name="finalAttributeName">
				<xsl:choose>
					<xsl:when test="references!=''">
						<xsl:variable name="ref_id" select="references"/>
						<xsl:variable name="references" select="$ids[@id=$ref_id]" />
						<!-- test this - should only be a single value -->
						<xsl:value-of select="$references/attributeName"/>
					</xsl:when>
			        <xsl:otherwise>
			          	<xsl:value-of select="attributeName"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<!-- load annotion detail for attribute -->
			<div>
           		<xsl:attribute name="id">
           			<xsl:value-of select="$finalAttributeName"/>
           		</xsl:attribute>
           		Loading information for: <xsl:value-of select="$finalAttributeName"/>
           	</div>
           	<script language="JavaScript">
          			var params = 
				{
					'action': 'read',
					'docid': '<xsl:value-of select="$annotationId" />',
					'qformat': '<xsl:value-of select="$qformat" />',
					'attributeLabel': '<xsl:value-of select="$finalAttributeName" />',
					'showEntity': 'true'
				};
				load(
					'<xsl:value-of select="$contextURL" />/metacat',
					params, 
					'<xsl:value-of select="$finalAttributeName" />');
			</script>
		
		</td>
		
		</xsl:if>
		
		<td class="{$stripes}">
			<xsl:choose>
	         <xsl:when test="references!=''">
	          <xsl:variable name="ref_id" select="references"/>
	          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
	           <xsl:for-each select="$references">
	               <xsl:value-of select="attributeDefinition"/>
	           </xsl:for-each>
	        </xsl:when>
	        <xsl:otherwise>
	             <xsl:value-of select="attributeDefinition"/>
	        </xsl:otherwise>
	     </xsl:choose>
		</td>
		
		<td class="{$stripes}">
			<xsl:choose>
	         <xsl:when test="references!=''">
	          <xsl:variable name="ref_id" select="references"/>
	          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
	          <xsl:for-each select="$references">
	            <xsl:choose>
	              <xsl:when test="storageType!=''">
	                    <xsl:for-each select="storageType">
	                      <xsl:value-of select="."/>
	                       &#160;<br/>
	                    </xsl:for-each>
	              </xsl:when>
	              <xsl:otherwise>
	                       &#160;
	              </xsl:otherwise>
	            </xsl:choose>
	          </xsl:for-each>
	        </xsl:when>
	        <xsl:otherwise>
	           <xsl:choose>
	              <xsl:when test="storageType!=''">
	                    <xsl:for-each select="storageType">
	                      <xsl:value-of select="."/>
	                       &#160;<br/>
	                    </xsl:for-each>
	              </xsl:when>
	              <xsl:otherwise>
	                       &#160;
	              </xsl:otherwise>
	            </xsl:choose>
	        </xsl:otherwise>
	     </xsl:choose>
		
		</td>
		
		<td class="{$stripes}">
			<xsl:choose>
	         <xsl:when test="references!=''">
	          <xsl:variable name="ref_id" select="references"/>
	          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
	          <xsl:for-each select="$references">
	              <xsl:for-each select="measurementScale">
	                 <xsl:value-of select="local-name(./*)"/>
	              </xsl:for-each>
	         </xsl:for-each>
	        </xsl:when>
	        <xsl:otherwise>
	              <xsl:for-each select="measurementScale">
	                 <xsl:value-of select="local-name(./*)"/>
	              </xsl:for-each>
	        </xsl:otherwise>
	     </xsl:choose>
		</td>
		
		<td class="{$stripes}">
			<xsl:choose>
	         <xsl:when test="references!=''">
	          <xsl:variable name="ref_id" select="references"/>
	          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
	          <xsl:for-each select="$references">
	              <xsl:for-each select="measurementScale">
	                <xsl:call-template name="measurementscale">
	                    <xsl:with-param name="docid" select="$docid"/>
	                    <xsl:with-param name="entitytype" select="$entitytype"/>
	                    <xsl:with-param name="entityindex" select="$entityindex"/>
	                    <xsl:with-param name="attributeindex" select="$attributeindex"/>
	                    <xsl:with-param name="stripes" select="$innerstripes"/>
	                </xsl:call-template>
	              </xsl:for-each>
	         </xsl:for-each>
	        </xsl:when>
	        <xsl:otherwise>
	              <xsl:for-each select="measurementScale">
	                <xsl:call-template name="measurementscale">
	                      <xsl:with-param name="docid" select="$docid"/>
	                      <xsl:with-param name="entitytype" select="$entitytype"/>
	                      <xsl:with-param name="entityindex" select="$entityindex"/>
	                      <xsl:with-param name="attributeindex" select="$attributeindex"/>
	                      <xsl:with-param name="stripes" select="$innerstripes"/>
	                </xsl:call-template>
	              </xsl:for-each>
	        </xsl:otherwise>
	     </xsl:choose>
		</td>
		
		<td class="{$stripes}">
		
			<xsl:choose>
	         <xsl:when test="references!=''">
	          <xsl:variable name="ref_id" select="references"/>
	          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
	          <xsl:for-each select="$references">
	            <xsl:choose>
	              <xsl:when test="missingValueCode!=''">
	                    <table>
	                       <xsl:for-each select="missingValueCode">
	                          <tr><td class="{$innerstripes}"><b>Code</b></td>
	                              <td class="{$innerstripes}"><xsl:value-of select="code"/></td></tr>
	                          <tr><td class="{$innerstripes}"><b>Expl</b></td>
	                               <td class="{$innerstripes}"><xsl:value-of select="codeExplanation"/></td>
	                          </tr>
	                       </xsl:for-each>
	                   </table>
	              </xsl:when>
	              <xsl:otherwise>
	                   &#160;
	              </xsl:otherwise>
	            </xsl:choose>
	          </xsl:for-each>
	        </xsl:when>
	        <xsl:otherwise>
	           <xsl:choose>
	              <xsl:when test="missingValueCode!=''">
	                    <table>
	                       <xsl:for-each select="missingValueCode">
	                          <tr><td class="{$innerstripes}"><b>Code</b></td>
	                              <td class="{$innerstripes}"><xsl:value-of select="code"/></td></tr>
	                          <tr><td class="{$innerstripes}"><b>Expl</b></td>
	                               <td class="{$innerstripes}"><xsl:value-of select="codeExplanation"/></td>
	                          </tr>
	                       </xsl:for-each>
	                   </table>
	              </xsl:when>
	              <xsl:otherwise>
	                   &#160;
	              </xsl:otherwise>
	            </xsl:choose>
	        </xsl:otherwise>
	     </xsl:choose>
		</td>
		
		<td class="{$stripes}">
			<xsl:choose>
	         <xsl:when test="references!=''">
	          <xsl:variable name="ref_id" select="references"/>
	          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
	          <xsl:for-each select="$references">
	            <xsl:choose>
	               <xsl:when test="accuracy!=''">
	                    <xsl:for-each select="accuracy">
	                          <xsl:value-of select="attributeAccuracyReport"/>
	                    </xsl:for-each>
	              </xsl:when>
	              <xsl:otherwise>
	                  &#160;
	              </xsl:otherwise>
	            </xsl:choose>
	          </xsl:for-each>
	        </xsl:when>
	        <xsl:otherwise>
	           <xsl:choose>
	               <xsl:when test="accuracy!=''">
	                    <xsl:for-each select="accuracy">
	                          <xsl:value-of select="attributeAccuracyReport"/>
	                    </xsl:for-each>
	              </xsl:when>
	              <xsl:otherwise>
	                  &#160;
	              </xsl:otherwise>
	            </xsl:choose>
	        </xsl:otherwise>
	     </xsl:choose>
		</td>
		
		<td class="{$stripes}">
			<xsl:choose>
	         <xsl:when test="references!=''">
	          <xsl:variable name="ref_id" select="references"/>
	          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
	          <xsl:for-each select="$references">
	            <xsl:choose>
	               <xsl:when test="accuracy/quantitativeAttributeAccuracyAssessment!=''">
	                   <xsl:for-each select="accuracy">
	                     <table>
	                       <xsl:for-each select="quantitativeAttributeAccuracyAssessment">
	                          <tr><td class="{$innerstripes}"><b>Value</b></td>
	                              <td class="{$innerstripes}"><xsl:value-of select="attributeAccuracyValue"/></td>
	                          </tr>
	                          <tr><td class="{$innerstripes}"><b>Expl</b></td>
	                              <td class="{$innerstripes}"><xsl:value-of select="attributeAccuracyExplanation"/></td>
	                          </tr>
	                      </xsl:for-each>
	                   </table>
	                 </xsl:for-each>
	             </xsl:when>
	             <xsl:otherwise>
	                  &#160;
	             </xsl:otherwise>
	           </xsl:choose>
	          </xsl:for-each>
	        </xsl:when>
	        <xsl:otherwise>
	           <xsl:choose>
	               <xsl:when test="accuracy/quantitativeAttributeAccuracyAssessment!=''">
	                   <xsl:for-each select="accuracy">
	                     <table>
	                       <xsl:for-each select="quantitativeAttributeAccuracyAssessment">
	                          <tr><td class="{$innerstripes}"><b>Value</b></td>
	                              <td class="{$innerstripes}"><xsl:value-of select="attributeAccuracyValue"/></td>
	                          </tr>
	                          <tr><td class="{$innerstripes}"><b>Expl</b></td>
	                              <td class="{$innerstripes}"><xsl:value-of select="attributeAccuracyExplanation"/></td>
	                          </tr>
	                      </xsl:for-each>
	                   </table>
	                 </xsl:for-each>
	             </xsl:when>
	             <xsl:otherwise>
	                  &#160;
	             </xsl:otherwise>
	           </xsl:choose>
	        </xsl:otherwise>
	     </xsl:choose>
		</td>
		
		<td class="{$stripes}">
			<xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:choose>
               <xsl:when test="coverage!=''">
                    <xsl:for-each select="coverage">
                      <xsl:call-template name="attributecoverage">
                         <xsl:with-param name="docid" select="$docid"/>
                         <xsl:with-param name="entitytype" select="$entitytype"/>
                         <xsl:with-param name="entityindex" select="$entityindex"/>
                         <xsl:with-param name="attributeindex" select="$attributeindex"/>
                      </xsl:call-template>
                    </xsl:for-each>
               </xsl:when>
               <xsl:otherwise>
                   &#160;
               </xsl:otherwise>
            </xsl:choose>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:choose>
               <xsl:when test="coverage!=''">
                    <xsl:for-each select="coverage">
                      <xsl:call-template name="attributecoverage">
                         <xsl:with-param name="docid" select="$docid"/>
                         <xsl:with-param name="entitytype" select="$entitytype"/>
                         <xsl:with-param name="entityindex" select="$entityindex"/>
                         <xsl:with-param name="attributeindex" select="$attributeindex"/>
                      </xsl:call-template>
                    </xsl:for-each>
               </xsl:when>
               <xsl:otherwise>
                   &#160;
               </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
		</td>
		
		<td class="{$stripes}">
			<xsl:choose>
	         <xsl:when test="references!=''">
	          <xsl:variable name="ref_id" select="references"/>
	          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
	          <xsl:for-each select="$references">
	            <xsl:choose>
	               <xsl:when test="method!=''">
	                   <xsl:for-each select="method">
	                     <xsl:call-template name="attributemethod">
	                       <xsl:with-param name="docid" select="$docid"/>
	                       <xsl:with-param name="entitytype" select="$entitytype"/>
	                       <xsl:with-param name="entityindex" select="$entityindex"/>
	                       <xsl:with-param name="attributeindex" select="$attributeindex"/>
	                     </xsl:call-template>
	                   </xsl:for-each>
	               </xsl:when>
	               <xsl:otherwise>
	                   &#160;
	               </xsl:otherwise>
	            </xsl:choose>
	         </xsl:for-each>
	        </xsl:when>
	        <xsl:otherwise>
	           <xsl:choose>
	               <xsl:when test="method!=''">
	                   <xsl:for-each select="method">
	                     <xsl:call-template name="attributemethod">
	                       <xsl:with-param name="docid" select="$docid"/>
	                       <xsl:with-param name="entitytype" select="$entitytype"/>
	                       <xsl:with-param name="entityindex" select="$entityindex"/>
	                       <xsl:with-param name="attributeindex" select="$attributeindex"/>
	                     </xsl:call-template>
	                   </xsl:for-each>
	               </xsl:when>
	               <xsl:otherwise>
	                   &#160;
	               </xsl:otherwise>
	            </xsl:choose>
	        </xsl:otherwise>
	     </xsl:choose>
		
		</td>
		
     
  	</tr>
  </xsl:for-each>


 </xsl:template>


<xsl:template name="singleattribute">
   <xsl:param name="docid"/>
   <xsl:param name="entitytype"/>
   <xsl:param name="entityindex"/>
   <xsl:param name="attributeindex"/>

   <table class="{$tableattributeStyle}">
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
  <tr><th class="rowodd">Column Name</th>
  <xsl:for-each select="attribute">
   <xsl:if test="position() = $attributeindex">
      <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <th><xsl:value-of select="attributeName"/></th>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <th><xsl:value-of select="attributeName"/></th>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:if>
  </xsl:for-each>
  </tr>

  <!-- Second row for attribute label-->
  <tr><th class="rowodd">Column Label</th>
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
                  <td colspan="1" align="center" class="{$stripes}">
                     <xsl:for-each select="attributeLabel">
                       <xsl:value-of select="."/>
                         &#160;<br />
                       </xsl:for-each>
                  </td>
                </xsl:when>
                <xsl:otherwise>
                   <td colspan="1" align="center" class="{$stripes}">
                       &#160;<br />
                   </td>
                </xsl:otherwise>
              </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
             <xsl:choose>
                <xsl:when test="attributeLabel!=''">
                  <td colspan="1" align="center" class="{$stripes}">
                     <xsl:for-each select="attributeLabel">
                       <xsl:value-of select="."/>
                         &#160;<br/>
                       </xsl:for-each>
                  </td>
                </xsl:when>
                <xsl:otherwise>
                   <td colspan="1" align="center" class="{$stripes}">
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
  <tr><th class="rowodd">Definition</th>
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
             <td colspan="1" align="center" class="{$stripes}">
               <xsl:value-of select="attributeDefinition"/>
             </td>
           </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <td colspan="1" align="center" class="{$stripes}">
             <xsl:value-of select="attributeDefinition"/>
          </td>
        </xsl:otherwise>
     </xsl:choose>
    </xsl:if>
   </xsl:for-each>
  </tr>

  <!-- The fourth row for attribute storage type-->
   <tr><th class="rowodd">Type of Value</th>
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
                 <td colspan="1" align="center" class="{$stripes}">
                    <xsl:for-each select="storageType">
                      <xsl:value-of select="."/>
                       &#160;<br/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                  <td colspan="1" align="center" class="{$stripes}">
                       &#160;
                   </td>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
              <xsl:when test="storageType!=''">
                 <td colspan="1" align="center" class="{$stripes}">
                    <xsl:for-each select="storageType">
                      <xsl:value-of select="."/>
                       &#160;<br/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                  <td colspan="1" align="center" class="{$stripes}">
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
  <tr><th class="rowodd">Measurement Type</th>
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
            <td colspan="1" align="center" class="{$stripes}">
              <xsl:for-each select="measurementScale">
                 <xsl:value-of select="local-name(./*)"/>
              </xsl:for-each>
            </td>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <td colspan="1" align="center" class="{$stripes}">
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
  <tr><th class="rowodd">Measurement Domain</th>
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
            <td colspan="1" align="center" class="{$stripes}">
              <xsl:for-each select="measurementScale">
                <xsl:call-template name="measurementscale">
                    <xsl:with-param name="docid" select="$docid"/>
                    <xsl:with-param name="entitytype" select="$entitytype"/>
                    <xsl:with-param name="entityindex" select="$entityindex"/>
                    <xsl:with-param name="attributeindex" select="position()"/>
                    <xsl:with-param name="stripes" select="$innerstripes"/>
                </xsl:call-template>
              </xsl:for-each>
            </td>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <td colspan="1" align="center" class="{$stripes}">
              <xsl:for-each select="measurementScale">
                <xsl:call-template name="measurementscale">
                      <xsl:with-param name="docid" select="$docid"/>
                      <xsl:with-param name="entitytype" select="$entitytype"/>
                      <xsl:with-param name="entityindex" select="$entityindex"/>
                      <xsl:with-param name="attributeindex" select="position()"/>
                      <xsl:with-param name="stripes" select="$innerstripes"/>
                </xsl:call-template>
              </xsl:for-each>
           </td>
        </xsl:otherwise>
     </xsl:choose>
    </xsl:if>
   </xsl:for-each>
  </tr>


  <!-- The seventh row for missing value code-->
  <tr><th class="rowodd">Missing Value Code</th>
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
                 <td colspan="1" align="center" class="{$stripes}">
                    <table>
                       <xsl:for-each select="missingValueCode">
                          <tr><td class="{$innerstripes}"><b>Code</b></td>
                              <td class="{$innerstripes}"><xsl:value-of select="code"/></td></tr>
                          <tr><td class="{$innerstripes}"><b>Expl</b></td>
                               <td class="{$innerstripes}"><xsl:value-of select="codeExplanation"/></td>
                          </tr>
                       </xsl:for-each>
                   </table>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td colspan="1" class="{$stripes}">
                   &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
              <xsl:when test="missingValueCode!=''">
                 <td colspan="1" align="center" class="{$stripes}">
                    <table>
                       <xsl:for-each select="missingValueCode">
                          <tr><td class="{$innerstripes}"><b>Code</b></td>                              <td class="{$innerstripes}"><xsl:value-of select="code"/></td></tr>
                          <tr><td class="{$innerstripes}"><b>Expl</b></td>
                               <td class="{$innerstripes}"><xsl:value-of select="codeExplanation"/></td>
                          </tr>
                       </xsl:for-each>
                   </table>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td colspan="1" align="center" class="{$stripes}">
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
  <tr><th class="rowodd">Accuracy Report</th>
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
                 <td colspan="1" align="center" class="{$stripes}">
                    <xsl:for-each select="accuracy">
                          <xsl:value-of select="attributeAccuracyReport"/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td colspan="1" align="center" class="{$stripes}">
                  &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
               <xsl:when test="accuracy!=''">
                 <td colspan="1" align="center" class="{$stripes}">
                    <xsl:for-each select="accuracy">
                          <xsl:value-of select="attributeAccuracyReport"/>
                    </xsl:for-each>
                 </td>
              </xsl:when>
              <xsl:otherwise>
                <td colspan="1" align="center" class="{$stripes}">
                  &#160;
                </td>
              </xsl:otherwise>
            </xsl:choose>
        </xsl:otherwise>
     </xsl:choose>
   </xsl:if>
  </xsl:for-each>
  </tr>

  <!-- The nineth row for quality accuracy accessment -->
  <tr><th class="rowodd">Accuracy Assessment</th>
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
                 <td colspan="1" align="center" class="{$stripes}">
                   <xsl:for-each select="accuracy">
                     <table>
                       <xsl:for-each select="quantitativeAttributeAccuracyAssessment">
                          <tr><td class="{$innerstripes}"><b>Value</b></td>
                              <td class="{$innerstripes}"><xsl:value-of select="attributeAccuracyValue"/></td>
                          </tr>
                          <tr><td class="{$innerstripes}"><b>Expl</b></td>
                              <td class="{$innerstripes}"><xsl:value-of select="attributeAccuracyExplanation"/></td>
                          </tr>
                      </xsl:for-each>
                   </table>
                 </xsl:for-each>
               </td>
             </xsl:when>
             <xsl:otherwise>
                <td colspan="1" align="center" class="{$stripes}">
                  &#160;
                </td>
             </xsl:otherwise>
           </xsl:choose>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
               <xsl:when test="accuracy/quantitativeAttributeAccuracyAssessment!=''">
                 <td colspan="1" align="center" class="{$stripes}">
                   <xsl:for-each select="accuracy">
                     <table>
                       <xsl:for-each select="quantitativeAttributeAccuracyAssessment">
                          <tr><td class="{$innerstripes}"><b>Value</b></td>
                              <td class="{$innerstripes}"><xsl:value-of select="attributeAccuracyValue"/></td>
                          </tr>
                          <tr><td class="{$innerstripes}"><b>Expl</b></td>
                              <td class="{$innerstripes}"><xsl:value-of select="attributeAccuracyExplanation"/></td>
                          </tr>
                      </xsl:for-each>
                   </table>
                 </xsl:for-each>
               </td>
             </xsl:when>
             <xsl:otherwise>
                <td colspan="1" align="center" class="{$stripes}">
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
  <tr><th class="rowodd">Coverage</th>
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
                  <td colspan="1" align="center" class="{$stripes}">
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
                  <td colspan="1" align="center" class="{$stripes}">
                   &#160;
                  </td>
               </xsl:otherwise>
            </xsl:choose>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:choose>
               <xsl:when test="coverage!=''">
                  <td colspan="1" align="center" class="{$stripes}">
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
                  <td colspan="1" align="center" class="{$stripes}">
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
  <tr><th class="rowodd">Method</th>
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
                 <td colspan="1" align="center" class="{$stripes}">
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
                 <td colspan="1" align="center" class="{$stripes}">
                   &#160;
                 </td>
               </xsl:otherwise>
            </xsl:choose>
         </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
           <xsl:choose>
               <xsl:when test="method!=''">
                 <td colspan="1" align="center" class="{$stripes}">
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
                 <td colspan="1" align="center" class="{$stripes}">
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
   <table>
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
        <tr><td class="{$stripes}"><b>Def</b></td>
            <td class="{$stripes}"><xsl:value-of select="textDomain/definition"/>
            </td>
        </tr>
        <xsl:for-each select="textDomain/parttern">
          <tr><td class="{$stripes}"><b>Pattern</b></td>
            <td class="{$stripes}"><xsl:value-of select="."/>
            </td>
          </tr>
        </xsl:for-each>
        <xsl:for-each select="textDomain/source">
          <tr><td class="{$stripes}"><b>Source</b></td>
            <td class="{$stripes}"><xsl:value-of select="."/>
            </td>
          </tr>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
         <tr><td colspan="2" align="center" class="{$stripes}" >
           <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/>&amp;displaymodule=attributedomain&amp;entitytype=<xsl:value-of select="$entitytype"/>&amp;entityindex=<xsl:value-of select="$entityindex"/>&amp;attributeindex=<xsl:value-of select="$attributeindex"/></xsl:attribute>
           <b>Domain Info</b></a>
         </td></tr>
      </xsl:otherwise>
    </xsl:choose>
 </xsl:template>

 <xsl:template name="intervalratio">
    <xsl:param name="stripes"/>
    <xsl:if test="unit/standardUnit">
      <tr><td class="{$stripes}"><b>Unit</b></td>
            <td class="{$stripes}"><xsl:value-of select="unit/standardUnit"/>
            </td>
      </tr>
    </xsl:if>
    <xsl:if test="unit/customUnit">
      <tr><td class="{$stripes}"><b>Unit</b></td>
            <td class="{$stripes}"><xsl:value-of select="unit/customUnit"/>
            </td>
      </tr>
   </xsl:if>
   <xsl:for-each select="precision">
      <tr><td class="{$stripes}"><b>Precision</b></td>
            <td class="{$stripes}"><xsl:value-of select="."/>
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
            <tr><td class="{$stripes}"><b>Type</b></td>
                <td class="{$stripes}"><xsl:value-of select="numberType"/>
                </td>
            </tr>
            <xsl:for-each select="bounds">
              <tr><td class="{$stripes}"><b>Min</b></td>
                  <td class="{$stripes}">
                    <xsl:for-each select="minimum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
              <tr><td class="{$stripes}"><b>Max</b></td>
                  <td class="{$stripes}">
                    <xsl:for-each select="maximum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
            </xsl:for-each>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <tr><td class="{$stripes}"><b>Type</b></td>
                <td class="{$stripes}"><xsl:value-of select="numberType"/>
                </td>
            </tr>
            <xsl:for-each select="bounds">
              <tr><td class="{$stripes}"><b>Min</b></td>
                  <td class="{$stripes}">
                    <xsl:for-each select="minimum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
              <tr><td class="{$stripes}"><b>Max</b></td>
                  <td class="{$stripes}">
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
    <tr><td class="{$stripes}"><b>Format</b></td>
         <td class="{$stripes}">
            <xsl:value-of select="formatString"/>
         </td>
    </tr>
     <tr><td class="{$stripes}"><b>Precision</b></td>
         <td class="{$stripes}">
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
              <tr><td class="{$stripes}"><b>Min</b></td>
                  <td class="{$stripes}">
                    <xsl:for-each select="minimum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
              <tr><td class="{$stripes}"><b>Max</b></td>
                  <td class="{$stripes}">
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
              <tr><td class="{$stripes}"><b>Min</b></td>
                  <td class="{$stripes}">
                    <xsl:for-each select="minimum">
                      <xsl:value-of select="."/>&#160;
                    </xsl:for-each>
                  </td>
              </tr>
              <tr><td class="{$stripes}"><b>Max</b></td>
                  <td class="{$stripes}">
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
     <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/>&amp;displaymodule=attributecoverage&amp;entitytype=<xsl:value-of select="$entitytype"/>&amp;entityindex=<xsl:value-of select="$entityindex"/>&amp;attributeindex=<xsl:value-of select="$attributeindex"/></xsl:attribute>
           <b>Coverage Info</b></a>
 </xsl:template>

 <xsl:template name="attributemethod">
    <xsl:param name="docid"/>
    <xsl:param name="entitytype"/>
    <xsl:param name="entityindex"/>
    <xsl:param name="attributeindex"/>
     <a><xsl:attribute name="href"><xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/>&amp;displaymodule=attributemethod&amp;entitytype=<xsl:value-of select="$entitytype"/>&amp;entityindex=<xsl:value-of select="$entityindex"/>&amp;attributeindex=<xsl:value-of select="$attributeindex"/></xsl:attribute>
           <b>Method Info</b></a>
 </xsl:template>

</xsl:stylesheet>
