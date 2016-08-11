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
  * convert an XML file that is valid with respect to the eml-variable.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format
  * suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:output method="html" encoding="UTF-8"
    doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
    indent="yes" />  
    
    <!-- style the identifier and system -->
    <xsl:template name="identifier">
      <xsl:param name="IDfirstColStyle"/>
      <xsl:param name="IDsecondColStyle"/>
      <xsl:param name="packageID"/>
      <xsl:param name="system"/>
      <xsl:if test="normalize-space(.)">
        <tr>
          <td class="{$IDfirstColStyle}">Identifier:</td>
          <td class="{$IDsecondColStyle}">
          	<xsl:value-of select="$packageID"/>
			<xsl:if test="$withHTMLLinks = '1'">
	          	<!-- stats loaded with ajax call -->
				<span id="stats"></span>
				<script language="JavaScript">
					if (window.loadStats) {
						loadStats(
							'stats', 
							'<xsl:value-of select="$packageID" />', 
							'<xsl:value-of select="$contextURL" />/metacat',
							'<xsl:value-of select="$qformat" />');
					}
				</script>
			</xsl:if>
			<!--  BRL - removing this section per MBJ 20101210
	          <xsl:if test="normalize-space(../@system)!=''">
	            <xsl:text> (in the </xsl:text>
	            <em class="italic">
	              <xsl:value-of select="$system"/>
	            </em>
	            <xsl:text> Catalog System)</xsl:text>
	          </xsl:if>
	         --> 
          </td>
        </tr>
      </xsl:if>
    </xsl:template>
    
    <!-- for citation information -->
    <xsl:template name="datasetcitation">
        <tr>
        	<th>
        		<h4>Data Set Citation</h4>
        		<h5>When using this data, please cite the data package</h5>
        	</th>
        </tr>
        <tr>
        	<td class="citation">
        		<cite>
		        	<xsl:for-each select="creator">
		        		<xsl:if test="position() &gt; 1">
		        			<xsl:if test="last() &gt; 2">, </xsl:if>
		        			<xsl:if test="position() = last()"> and</xsl:if>
		        			<xsl:text> </xsl:text>
		        		</xsl:if>
		        		<xsl:call-template name="creatorCitation" />
		        		<xsl:if test="position() = last()">.</xsl:if>
		        		<xsl:text> </xsl:text>    		
		        	</xsl:for-each>
		        	
		        	<xsl:value-of select="substring(string(pubDate),1,4)"/>
		        	<xsl:if test="substring(string(pubDate),1,4) != ''">.</xsl:if>
		        	
		        	<br/>
		        	<!-- title -->
					<b>
					<xsl:for-each select="./title">
			     		<xsl:call-template name="i18n">
			     			<xsl:with-param name="i18nElement" select="."/>
			     		</xsl:call-template>
			     	</xsl:for-each>				
					</b>
					<br/>
					<xsl:if test="boolean($registryname)">
						<xsl:value-of select="$registryname"/>: 
					</xsl:if>
					
	                <span class="lsid">
					    <xsl:choose>
						    <xsl:when test="boolean($lsidauthority)">
							    <xsl:call-template name="lsid"/>
						    </xsl:when>
						    <xsl:otherwise>
							    <xsl:value-of select="../@packageId"/>
						    </xsl:otherwise>
					    </xsl:choose>
	                </span>
					
					<!-- show link? -->
					<xsl:if test="$withHTMLLinks = '1'">
			        	<xsl:choose>
			        		<xsl:when test="boolean($registryurl)">
			        			(<a> <xsl:attribute name="href"><xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/></xsl:attribute> <xsl:value-of select="$registryurl"/>/metacat/<xsl:value-of select="../@packageId"/>/<xsl:value-of select="$qformat"/></a>)
			        		</xsl:when>
			        		<xsl:otherwise>
			        			(<a> <xsl:attribute name="href"><xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/></xsl:attribute> <xsl:value-of select="$contextURL"/>/metacat/<xsl:value-of select="../@packageId"/>/<xsl:value-of select="$qformat"/></a>)				
			        		</xsl:otherwise>
			        	</xsl:choose>
						<br />
					</xsl:if>	
				</cite>
        </td>
     </tr>
   </xsl:template>
   
   <!--************** creates lsid dataset id **************-->
   <xsl:template name="lsid">
		<xsl:variable name="lsidString1" select="concat('urn:lsid:',string($lsidauthority),':')"/>
		<xsl:variable name="lsidString2" select="concat($lsidString1, substring-before(string(../@packageId),'.'), ':')"/>
		<xsl:variable name="lsidString3" select="concat($lsidString2, substring-before(substring-after(string(../@packageId),'.'),'.'), ':')"/>
		<xsl:variable name="lsidString4" select="concat($lsidString3, substring-after(substring-after(string(../@packageId),'.'),'.'))"/>
		<xsl:value-of select="$lsidString4"/>
   </xsl:template>
   
   <!--************** creates citation for a creator in "Last FM" format **************-->
   <xsl:template name="creatorCitation">
	   	<xsl:for-each select="individualName">	
	   		
	   		<xsl:value-of select="surName/text()"/>
	   		<xsl:text> </xsl:text>
	   		
	   		<xsl:for-each select="givenName">
	   			<xsl:value-of select="substring(string(.),1,1)"/>
	   		</xsl:for-each>
	   	</xsl:for-each>
	   	
	   	<!-- only show organization if the person is omitted  -->
	   	<xsl:if test="string(individualName/surName) = ''"> 
		   	<xsl:for-each select="organizationName">
		   		<xsl:value-of select="."/>
		   	</xsl:for-each>
	   	</xsl:if>
	   	
   </xsl:template>
    
 </xsl:stylesheet>
