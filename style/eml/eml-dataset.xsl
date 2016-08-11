<?xml version="1.0" encoding="utf-8"?>
<!--
  *  '$RCSfile$'
  *      Authors: Matt Jones
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
    doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
    indent="yes" />  

  <xsl:template match="dataset" mode="dataset">
      <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
             <xsl:call-template name="datasetmixed"/>
          </xsl:for-each>
       </xsl:when>
       <xsl:otherwise>
             <xsl:call-template name="datasetmixed"/>
       </xsl:otherwise>
      </xsl:choose>

  </xsl:template>
  
  <xsl:template name="datasetmixed">
  	<!-- redundant with the citation, BRL 01/13/2011
		<h3>
	 		<xsl:choose>
	     		<xsl:when test="normalize-space(./title) != ''">
			     	<xsl:for-each select="./title">
			     		<xsl:call-template name="i18n">
			     			<xsl:with-param name="i18nElement" select="."/>
			     		</xsl:call-template>
			     	</xsl:for-each>
			     </xsl:when>
			     <xsl:otherwise>
			     	Data Set Documentation
			     </xsl:otherwise>
	     	</xsl:choose>
	 	</h3>
	 -->	
     <!-- citation -->
     <table class="group group_border onehundred_percent">
     	<xsl:for-each select=".">
	     	<xsl:call-template name="datasetcitation" />
	    </xsl:for-each>		
     </table>
     
     <table class="subGroup onehundred_percent">
       <tr>
         <td>
           <!-- style the identifying information into a small table -->
           <table class="{$tabledefaultStyle}">
             <tr><th colspan="2"><h4>General Information</h4></th></tr>
             <!-- put in the title -->
             <xsl:if test="./title">
               <xsl:for-each select="./title">
                 <xsl:call-template name="resourcetitle">
                   <xsl:with-param name="resfirstColStyle" select="$firstColStyle"/>
                   <xsl:with-param name="ressecondColStyle" select="$secondColStyle"/>
                 </xsl:call-template>
               </xsl:for-each>
             </xsl:if>
             <!-- put in the short name -->
             <xsl:if test="shortName">
             <xsl:for-each select="./shortName">
             <xsl:call-template name="resourceshortName">
               <xsl:with-param name="resfirstColStyle" select="$firstColStyle"/>
               <xsl:with-param name="ressecondColStyle" select="$secondColStyle"/>
             </xsl:call-template>
             </xsl:for-each>
             </xsl:if>
             <!-- put in the identifier and system that the ID belongs to -->
             <xsl:if test="../@packageId">
             <xsl:for-each select="../@packageId">
             	<xsl:choose>
             		<xsl:when test="$docid != ''" >
             			<!-- use docid parameter when we have it -->         	
						<xsl:call-template name="identifier">
							<xsl:with-param name="packageID" select="$docid"/>
							<xsl:with-param name="system" select="../@system"/>
							<xsl:with-param name="IDfirstColStyle" select="$firstColStyle"/>
							<xsl:with-param name="IDsecondColStyle" select="$secondColStyle"/>
						</xsl:call-template>
		             </xsl:when>
		             <xsl:otherwise>
		             	<xsl:call-template name="identifier">
			               <xsl:with-param name="packageID" select="../@packageId"/>
			               <xsl:with-param name="system" select="../@system"/>
			               <xsl:with-param name="IDfirstColStyle" select="$firstColStyle"/>
			               <xsl:with-param name="IDsecondColStyle" select="$secondColStyle"/>
			             </xsl:call-template>
		             </xsl:otherwise>
	             </xsl:choose>
             </xsl:for-each>
             </xsl:if>
             <!-- put in the alternate identifiers -->
             <xsl:if test="keywordSet">
             <xsl:for-each select="alternateIdentifier">
               <xsl:call-template name="resourcealternateIdentifier">
                 <xsl:with-param name="resfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="ressecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </xsl:for-each>
             </xsl:if>
             <!-- put in the text of the abstract-->
             <xsl:if test="./abstract">
             <xsl:for-each select="./abstract">
               <xsl:call-template name="resourceabstract">
                 <xsl:with-param name="resfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="ressecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </xsl:for-each>
             </xsl:if>
             <!-- put in the purpose of the dataset-->
             <xsl:if test="./purpose">
             <xsl:for-each select="./purpose">
               <xsl:call-template name="datasetpurpose">
                 <xsl:with-param name="resfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="ressecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </xsl:for-each>
             </xsl:if>
             <!-- put in the keyword sets -->
             <xsl:if test="keywordSet">
               <tr>
                 <td class="{$firstColStyle}">
                   <xsl:text>Keywords:</xsl:text>
                 </td>
                 <td class="{$secondColStyle}">
                 <xsl:for-each select="keywordSet">
                   <xsl:call-template name="resourcekeywordSet" >
                     <xsl:with-param name="resfirstColStyle" select="$firstColStyle"/>
                     <xsl:with-param name="ressecondColStyle" select="$secondColStyle"/>
                   </xsl:call-template>
                 </xsl:for-each>
                 </td>
               </tr>
             </xsl:if>

             <!-- put in the publication date -->
             <xsl:if test="./pubDate">
               <xsl:for-each select="pubDate">
                <xsl:call-template name="resourcepubDate" >
                  <xsl:with-param name="resfirstColStyle" select="$firstColStyle"/>
                 </xsl:call-template>
               </xsl:for-each>
             </xsl:if>

             <!-- put in the language -->
             <xsl:if test="./language">
               <xsl:for-each select="language">
                 <xsl:call-template name="resourcelanguage" >
                   <xsl:with-param name="resfirstColStyle" select="$firstColStyle"/>
                  </xsl:call-template>
               </xsl:for-each>
             </xsl:if>

             <!-- put in the series -->
             <xsl:if test="./series">
               <xsl:for-each select="series">
                 <xsl:call-template name="resourceseries" >
                   <xsl:with-param name="resfirstColStyle" select="$firstColStyle"/>
                 </xsl:call-template>
               </xsl:for-each>
             </xsl:if>
           </table>
         </td>
         </tr>
         <tr>
         <!-- begin the second column of the 'Data Set Description' section -->
         <td class="fortyfive_percent">
           <!-- create a second easy access table listing the data entities -->
           <xsl:if test="dataTable|spatialRaster|spatialVector|storedProcedure|view|otherEntity">
           <xsl:if test="$withEntityLinks='1' or $displaymodule = 'printall'">
               <table class="{$tabledefaultStyle}">
	             <xsl:call-template name="datasetentity"/>
	           </table>
             </xsl:if>
           </xsl:if>
         </td>
       </tr>
     </table>
     <h3>Involved Parties</h3>

     <!-- this section creates a two column table to present the involved
     parties in boxes across the entire page -->
     <table class="subGroup onehundred_percent">

       <!-- add in the creators using a two column table -->
       <xsl:if test="creator">
         <tr><th colspan="2"><h4>Data Set Creators</h4></th></tr>
         <xsl:for-each select="creator">
         <tr>
           <xsl:if test="position() mod 2 = 1">
             <td class="fortyfive_percent">
               <xsl:call-template name="party">
                 <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="partysecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </td>
             <xsl:for-each select="following-sibling::creator[position()=1]">
             <td class="fortyfive_percent">
               <xsl:call-template name="party">
                 <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="partysecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </td>
             </xsl:for-each>
           </xsl:if>
         </tr>
         </xsl:for-each>
       </xsl:if>

       <!-- add in the contacts using a two column table -->
       <xsl:if test="contact">
         <tr><th colspan="2"><h4>Data Set Contacts</h4></th></tr>
         <xsl:for-each select="contact">
         <tr>
           <xsl:if test="position() mod 2 = 1">
             <td class="fortyfive_percent">
               <xsl:call-template name="party">
                 <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="partysecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </td>
             <xsl:for-each select="following-sibling::contact[position()=1]">
             <td class="fortyfive_percent">
               <xsl:call-template name="party">
                 <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="partysecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </td>
             </xsl:for-each>
           </xsl:if>
         </tr>
         </xsl:for-each>
       </xsl:if>

       <!-- add in the associatedParty using a two column table -->
       <xsl:if test="associatedParty">
         <tr><th colspan="2"><h4>Associated Parties</h4></th></tr>
         <xsl:for-each select="associatedParty">
         <tr>
           <xsl:if test="position() mod 2 = 1">
             <td class="fortyfive_percent">
               <xsl:call-template name="party">
                 <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="partysecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </td>
             <xsl:for-each select="following-sibling::associatedParty[position()=1]">
             <td class="fortyfive_percent">
               <xsl:call-template name="party">
                 <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="partysecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </td>
             </xsl:for-each>
           </xsl:if>
         </tr>
         </xsl:for-each>
       </xsl:if>

       <!-- add in the metadataProviders using a two column table -->
       <xsl:if test="metadataProvider">
         <tr><th colspan="2"><h4>Metadata Providers</h4></th></tr>
         <xsl:for-each select="metadataProvider">
         <tr>
           <xsl:if test="position() mod 2 = 1">
             <td class="fortyfive_percent">
               <xsl:call-template name="party">
                 <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="partysecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </td>
             <xsl:for-each select="following-sibling::metadataProvider[position()=1]">
             <td class="fortyfive_percent">
               <xsl:call-template name="party">
                 <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="partysecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </td>
             </xsl:for-each>
           </xsl:if>
         </tr>
         </xsl:for-each>
       </xsl:if>

       <!-- add in the publishers using a two column table -->
       <xsl:if test="publisher">
         <tr><th colspan="2"><h4>Data Set Publishers</h4></th></tr>
         <xsl:for-each select="publisher">
         <tr>
           <xsl:if test="position() mod 2 = 1">
             <td class="fortyfive_percent">
               <xsl:call-template name="party">
                 <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="partysecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </td>
             <xsl:for-each select="following-sibling::publisher[position()=1]">
             <td class="fortyfive_percent">
               <xsl:call-template name="party">
                 <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="partysecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </td>
             </xsl:for-each>
           </xsl:if>
         </tr>
         </xsl:for-each>
       </xsl:if>
     </table>

     <h3>Data Set Characteristics</h3>

       <!-- add in the coverage info -->
     <table class="subGroup onehundred_percent">  
       <tr>
       <!-- add in the geographic coverage info -->
         <td class="fortyfive_percent">
           <xsl:if test="./coverage/geographicCoverage">
             <xsl:for-each select="./coverage/geographicCoverage">
               <xsl:call-template name="geographicCoverage">
                 <xsl:with-param name="firstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="secondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </xsl:for-each>
           </xsl:if>
         </td>
       <!-- add in the temporal coverage info -->
         <td class="fortyfive_percent">
           <xsl:if test="./coverage/temporalCoverage">
             <xsl:for-each select="./coverage/temporalCoverage">
               <xsl:call-template name="temporalCoverage">
                 <xsl:with-param name="firstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="secondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </xsl:for-each>
           </xsl:if>
         </td>
       </tr>
       <tr>
       <!-- add in the taxonomic coverage info -->
         <td colspan="2" class="onehundred_percent">
           <xsl:if test="./coverage/taxonomicCoverage">
             <xsl:for-each select="./coverage/taxonomicCoverage">
               <xsl:call-template name="taxonomicCoverage">
                 <xsl:with-param name="firstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="secondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </xsl:for-each>
           </xsl:if>
         </td>
       </tr>
     </table>

     <!-- add in the method info -->
     <h3>Sampling, Processing and Quality Control Methods</h3>

     <table class="subGroup onehundred_percent">  
       <tr>
         <td colspan="2" class="onehundred_percent">
           <xsl:if test="./methods">
             <xsl:for-each select="./methods">
               <xsl:call-template name="datasetmethod">
                 <xsl:with-param name="methodfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="methodsecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </xsl:for-each>
           </xsl:if>
         </td>
       </tr>
     </table>

     <h3>Data Set Usage Rights</h3>

       <!-- add in the intellectiual rights info -->
     <table class="subGroup onehundred_percent">  
       <tr>
         <td>
           <xsl:if test="intellectualRights">
             <xsl:for-each select="intellectualRights">
               <xsl:call-template name="resourceintellectualRights">
                 <xsl:with-param name="resfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="ressecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </xsl:for-each>
           </xsl:if>
         </td>
       </tr>
     </table>

       <!-- add in the access control info -->
     <table class="subGroup onehundred_percent">  
       <tr>
         <td>
           <xsl:if test="access">
             <xsl:for-each select="access">
               <xsl:call-template name="access">
                 <xsl:with-param name="accessfirstColStyle" select="$firstColStyle"/>
                 <xsl:with-param name="accesssecondColStyle" select="$secondColStyle"/>
               </xsl:call-template>
             </xsl:for-each>
           </xsl:if>
         </td>
       </tr>
     </table>
  </xsl:template>

  <xsl:template name="datasetresource">
     <tr>
        <td colspan="2">
          <xsl:call-template name="resource">
            <xsl:with-param name="resfirstColStyle" select="$firstColStyle"/>
            <xsl:with-param name="ressubHeaderStyle" select="$subHeaderStyle"/>
          </xsl:call-template>
       </td>
     </tr>
  </xsl:template>



  <xsl:template name="datasetpurpose">
    <xsl:for-each select="purpose">
      <tr><td colspan="2">
           <h4><xsl:text>Purpose</xsl:text></h4>
        </td>
       </tr>
       <tr>
            <td  class="{$firstColStyle}">
            &#160;
            </td>
            <td>
              <xsl:call-template name="text">
                <xsl:with-param name="textfirstColStyle" select="$firstColStyle"/>
              </xsl:call-template>
            </td>
       </tr>
     </xsl:for-each>
  </xsl:template>

  <xsl:template name="datasetmaintenance">
    <xsl:for-each select="maintenance">
      <tr><td colspan="2">
        <h4><xsl:text>Maintenance:</xsl:text></h4>
     </td></tr>
     <xsl:call-template name="mantenancedescription"/>
      <tr>
          <td  class="{$firstColStyle}">
          Frequency:
          </td>
          <td class="{$secondColStyle}" >
           <xsl:value-of select="maintenanceUpdateFrequency"/>
          </td>
     </tr>
     <xsl:call-template name="datasetchangehistory"/>
   </xsl:for-each>
  </xsl:template>

  <xsl:template name="mantenancedescription">
   <xsl:for-each select="description">
     <tr>
          <td  class="{$firstColStyle}">
          Description:
          </td>
          <td>
            <xsl:call-template name="text">
               <xsl:with-param name="textfirstColStyle" select="$firstColStyle"/>
             </xsl:call-template>
          </td>
     </tr>
    </xsl:for-each>
  </xsl:template>

   <xsl:template name="datasetchangehistory">
   <xsl:if test="changeHistory">
     <tr>
          <td class="{$firstColStyle}">
          History:
          </td>
          <td>
            <table class="{$tabledefaultStyle}">
              <xsl:for-each select="changeHistory">
                <xsl:call-template name="historydetails"/>
              </xsl:for-each>
            </table>
          </td>
     </tr>
     </xsl:if>
   </xsl:template>

   <xsl:template name="historydetails">
        <tr><td class="{$firstColStyle}">
            scope:</td>
            <td class="{$secondColStyle}">
            <xsl:value-of select="changeScope"/>
        </td></tr>
        <tr><td class="{$firstColStyle}">
            old value:</td>
            <td class="{$secondColStyle}">
            <xsl:value-of select="oldValue"/>
        </td></tr>
        <tr><td class="{$firstColStyle}">
            change date:</td>
            <td class="{$secondColStyle}">
            <xsl:value-of select="changeDate"/>
        </td></tr>
        <xsl:if test="comment and normalize-space(comment)!=''">
          <tr><td class="{$firstColStyle}">
            comment:</td><td class="{$secondColStyle}">
            <xsl:value-of select="comment"/>
          </td></tr>
        </xsl:if>
  </xsl:template>

  <xsl:template name="datasetcontact">
    <tr><td colspan="2">
        <h4><xsl:text>Contact:</xsl:text></h4>
     </td></tr>
    <xsl:for-each select="contact">
     <tr><td colspan="2">
       <xsl:call-template name="party">
              <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
       </xsl:call-template>
     </td></tr>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="datasetpublisher">
   <xsl:for-each select="publisher">
     <tr><td colspan="2">
        <h4><xsl:text>Publisher:</xsl:text></h4>
     </td></tr>
     <tr><td colspan="2">
       <xsl:call-template name="party">
              <xsl:with-param name="partyfirstColStyle" select="$firstColStyle"/>
       </xsl:call-template>
     </td></tr>
   </xsl:for-each>
  </xsl:template>

  <xsl:template name="datasetpubplace">
    <xsl:for-each select="pubPlace">
      <tr><td class="{$firstColStyle}">
           Publish Place:</td>
          <td class="{$secondColStyle}">
          <xsl:value-of select="."/>
          </td>
      </tr>
   </xsl:for-each>
  </xsl:template>

  <xsl:template name="datasetmethod">
     <xsl:for-each select=".">
        <xsl:call-template name="method">
          <xsl:with-param name="methodfirstColStyle" select="$firstColStyle"/>
        </xsl:call-template>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="datasetproject">
    <xsl:for-each select="project">
     <tr>
       <td colspan="2">
         <h3><xsl:text>Parent Project Information:</xsl:text></h3>
       </td>
     </tr>
     <tr>
       <td colspan="2">
       <xsl:call-template name="project">
         <xsl:with-param name="projectfirstColStyle" select="$firstColStyle"/>
       </xsl:call-template>
       </td>
     </tr>
    </xsl:for-each>
  </xsl:template>

   <xsl:template name="datasetaccess">
    <xsl:for-each select="access">
      <tr>
        <td colspan="2">
        <xsl:call-template name="access">
          <xsl:with-param name="accessfirstColStyle" select="$firstColStyle"/>
          <xsl:with-param name="accesssubHeaderStyle" select="$subHeaderStyle"/>
        </xsl:call-template>
        </td>
      </tr>
    </xsl:for-each>
  </xsl:template>
  
	<xsl:template name="datasetentity">
		<xsl:if test="dataTable or spatialRaster or spatialVector or storedProcedures or view or otherEntity">
			<tr>
				<th colspan="2">
					<h4><xsl:text>Data Table, Image, and Other Data Details:</xsl:text></h4>
				</th>
			</tr>
		</xsl:if>
		
		<xsl:call-template name="xml" />
			
		<xsl:choose>
			<xsl:when test="$displaymodule!='printall'">
				<xsl:for-each select="dataTable">
					<xsl:call-template name="entityurl">
						<xsl:with-param name="type">dataTable</xsl:with-param>
						<xsl:with-param name="showtype">Data Table</xsl:with-param>
						<xsl:with-param name="index" select="position()" />
					</xsl:call-template>
				</xsl:for-each>
				<xsl:for-each select="spatialRaster">
					<xsl:call-template name="entityurl">
						<xsl:with-param name="type">spatialRaster</xsl:with-param>
						<xsl:with-param name="showtype">Spatial Raster</xsl:with-param>
						<xsl:with-param name="index" select="position()" />
					</xsl:call-template>
				</xsl:for-each>
				<xsl:for-each select="spatialVector">
					<xsl:call-template name="entityurl">
						<xsl:with-param name="type">spatialVector</xsl:with-param>
						<xsl:with-param name="showtype">Spatial Vector</xsl:with-param>
						<xsl:with-param name="index" select="position()" />
					</xsl:call-template>
				</xsl:for-each>
				<xsl:for-each select="storedProcedure">
					<xsl:call-template name="entityurl">
						<xsl:with-param name="type">storedProcedure</xsl:with-param>
						<xsl:with-param name="showtype">Stored Procedure</xsl:with-param>
						<xsl:with-param name="index" select="position()" />
					</xsl:call-template>
				</xsl:for-each>
				<xsl:for-each select="view">
					<xsl:call-template name="entityurl">
						<xsl:with-param name="type">view</xsl:with-param>
						<xsl:with-param name="showtype">View</xsl:with-param>
						<xsl:with-param name="index" select="position()" />
					</xsl:call-template>
				</xsl:for-each>
				<xsl:for-each select="otherEntity">
					<xsl:call-template name="entityurl">
						<xsl:with-param name="type">otherEntity</xsl:with-param>
						<xsl:with-param name="showtype">Other Data</xsl:with-param>
						<xsl:with-param name="index" select="position()" />
					</xsl:call-template>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:for-each select="dataTable">
					<xsl:variable name="currentNode" select="position()" />
					<xsl:for-each select="../.">
						<tr>
							<td class="{$subHeaderStyle}" colspan="2">
								<xsl:text>Data Table:</xsl:text>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<xsl:call-template name="chooseentity">
									<xsl:with-param name="entitytype">dataTable</xsl:with-param>
									<xsl:with-param name="entityindex" select="$currentNode" />
								</xsl:call-template>
							</td>
						</tr>
					</xsl:for-each>
				</xsl:for-each>
				<xsl:for-each select="spatialRaster">
					<xsl:variable name="currentNode" select="position()" />
					<xsl:for-each select="../.">
						<tr>
							<td class="{$subHeaderStyle}" colspan="2">
								<xsl:text>Spatial Raster:</xsl:text>
							</td>
						</tr>
						<tr>
							<td>
								<xsl:call-template name="chooseentity">
									<xsl:with-param name="entitytype">spatialRaster</xsl:with-param>
									<xsl:with-param name="entityindex" select="$currentNode" />
								</xsl:call-template>
							</td>
						</tr>
					</xsl:for-each>
				</xsl:for-each>
				<xsl:for-each select="spatialVector">
					<xsl:variable name="currentNode" select="position()" />
					<xsl:for-each select="../.">
						<tr>
							<td class="{$subHeaderStyle}" colspan="2">
								<xsl:text>Spatial Vector:</xsl:text>
							</td>
						</tr>
						<tr>
							<td>
								<xsl:call-template name="chooseentity">
									<xsl:with-param name="entitytype">spatialVector</xsl:with-param>
									<xsl:with-param name="entityindex" select="$currentNode" />
								</xsl:call-template>
							</td>
						</tr>
					</xsl:for-each>
				</xsl:for-each>
				<xsl:for-each select="storedProcedure">
					<xsl:variable name="currentNode" select="position()" />
					<xsl:for-each select="../.">
						<tr>
							<td class="{$subHeaderStyle}" colspan="2">
								<xsl:text>Stored Procedure:</xsl:text>
							</td>
						</tr>
						<tr>
							<td>
								<xsl:call-template name="chooseentity">
									<xsl:with-param name="entitytype">storedProcedure</xsl:with-param>
									<xsl:with-param name="entityindex" select="$currentNode" />
								</xsl:call-template>
							</td>
						</tr>
					</xsl:for-each>
				</xsl:for-each>
				<xsl:for-each select="view">
					<xsl:variable name="currentNode" select="position()" />
					<xsl:for-each select="../.">
						<tr>
							<td class="{$subHeaderStyle}" colspan="2">
								<xsl:text>View:</xsl:text>
							</td>
						</tr>
						<tr>
							<td>
								<xsl:call-template name="chooseentity">
									<xsl:with-param name="entitytype">view</xsl:with-param>
									<xsl:with-param name="entityindex" select="$currentNode" />
								</xsl:call-template>
							</td>
						</tr>
					</xsl:for-each>
				</xsl:for-each>
				<xsl:for-each select="otherEntity">
					<xsl:variable name="currentNode" select="position()" />
					<xsl:for-each select="../.">
						<tr>
							<td class="{$subHeaderStyle}" colspan="2">
								<xsl:text>Other Entity:</xsl:text>
							</td>
						</tr>
						<tr>
							<td>
								<xsl:call-template name="chooseentity">
									<xsl:with-param name="entitytype">otherEntity</xsl:with-param>
									<xsl:with-param name="entityindex" select="$currentNode" />
								</xsl:call-template>
							</td>
						</tr>
					</xsl:for-each>
				</xsl:for-each>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="entityurl">
		<xsl:param name="showtype" />
		<xsl:param name="type" />
		<xsl:param name="index" />
		<xsl:choose>
			<xsl:when test="references!=''">
				<xsl:variable name="ref_id" select="references" />
				<xsl:variable name="references" select="$ids[@id=$ref_id]" />
				<xsl:for-each select="$references">
					<tr>
						<td class="{$firstColStyle}">
							Metadata:
							<a>
								<xsl:attribute name="href">
									<xsl:value-of select="$tripleURI" /><xsl:value-of select="$docid" />&amp;displaymodule=entity&amp;entitytype=<xsl:value-of select="$type"/>&amp;entityindex=<xsl:value-of select="$index"/>
								</xsl:attribute>
								<b><xsl:value-of select="./physical/objectName"/></b>
							</a>
						</td>
					</tr>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<tr>
					<td width="{$firstColWidth}" class="{$firstColStyle}">
						<xsl:value-of select="$showtype"/>:</td>
					<td width="{$secondColWidth}" class="{$secondColStyle}"> 
						<xsl:value-of select="./entityName"/> 
						(<a>
						<xsl:attribute name="href">
						<xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/>&amp;displaymodule=entity&amp;entitytype=<xsl:value-of select="$type"/>&amp;entityindex=<xsl:value-of select="$index"/></xsl:attribute>
						View Metadata</a> 
						<xsl:text> </xsl:text>
					    <xsl:choose>
						    <xsl:when test="./physical/distribution/online/url"> 
						    	| 
						    	<xsl:variable name="URL" select="./physical/distribution/online/url"/>
					            <a>
									<xsl:choose>
										<xsl:when test="starts-with($URL,'ecogrid')">
											<xsl:variable name="URL1" select="substring-after($URL, 'ecogrid://')"/>
											<xsl:variable name="dataDocID" select="substring-after($URL1, '/')"/>
											<xsl:attribute name="href"><xsl:value-of select="$tripleURI"/><xsl:value-of select="$dataDocID"/></xsl:attribute>
										</xsl:when>
										<xsl:otherwise>
											<xsl:attribute name="href"><xsl:value-of select="$URL"/></xsl:attribute>
										</xsl:otherwise>
									</xsl:choose>
								<xsl:attribute name="target">_blank</xsl:attribute>
								Download File <img src="{$stylePath}/images/page_white_put.png" style="margin:0px 0px; padding:0px;" border="0" alt="download"/></a>
							</xsl:when>
						</xsl:choose>)
					</td>
				</tr>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

  <xsl:template match="text()" mode="dataset" />
  <xsl:template match="text()" mode="resource" />

</xsl:stylesheet>
