<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-dataset-2.0.0.xsl,v $'
  *      Authors: Matt Jones
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: berkley $'
  *     '$Date: 2004-07-02 21:15:57 $'
  * '$Revision: 1.6 $'
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


  <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />

  <xsl:template match="dataset" mode="dataset">
    <table xsl:use-attribute-sets="cellspacing" class="{$tabledefaultStyle}" width="100%">
      <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
             <xsl:call-template name="datasetresource"/>
             <xsl:call-template name="datasetaccess"/>
             <xsl:call-template name="datasetpurpose"/>
             <xsl:call-template name="datasetmaintenance"/>
             <xsl:call-template name="datasetcontact"/>
             <xsl:call-template name="datasetpublisher"/>
             <xsl:call-template name="datasetpubplace"/>
             <xsl:call-template name="datasetmethod"/>
             <xsl:call-template name="datasetproject"/>
             <xsl:if test="$withEntityLinks='1' or $displaymodule='printall'">
               <xsl:call-template name="datasetentity"/>
             </xsl:if>
          </xsl:for-each>
       </xsl:when>
       <xsl:otherwise>
             <xsl:call-template name="datasetresource"/>
             <xsl:call-template name="datasetaccess"/>
             <xsl:call-template name="datasetpurpose"/>
             <xsl:call-template name="datasetmaintenance"/>
             <xsl:call-template name="datasetcontact"/>
             <xsl:call-template name="datasetpublisher"/>
             <xsl:call-template name="datasetpubplace"/>
             <xsl:call-template name="datasetmethod"/>
             <xsl:call-template name="datasetproject"/>
             <xsl:if test="$withEntityLinks='1' or $displaymodule='printall'">
               <xsl:call-template name="datasetentity"/>
             </xsl:if>
       </xsl:otherwise>
      </xsl:choose>
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
      <tr><td class="{$subHeaderStyle}" colspan="2">
           <xsl:text>Purpose:</xsl:text>
        </td>
       </tr>
       <tr>
            <td width="{$firstColWidth}"  class="{$firstColStyle}">
            &#160;
            </td>
            <td width="{$secondColWidth}">
              <xsl:call-template name="text">
                <xsl:with-param name="textfirstColStyle" select="$firstColStyle"/>
              </xsl:call-template>
            </td>
       </tr>
     </xsl:for-each>
  </xsl:template>

  <xsl:template name="datasetmaintenance">
    <xsl:for-each select="maintenance">
      <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Maintenance:</xsl:text>
     </td></tr>
     <xsl:call-template name="mantenancedescription"/>
      <tr>
          <td width="{$firstColWidth}"  class="{$firstColStyle}">
          Frequency:
          </td>
          <td width="{$secondColWidth}" class="{$secondColStyle}" >
           <xsl:value-of select="maintenanceUpdateFrequency"/>
          </td>
     </tr>
     <xsl:call-template name="datasetchangehistory"/>
   </xsl:for-each>
  </xsl:template>

  <xsl:template name="mantenancedescription">
   <xsl:for-each select="description">
     <tr>
          <td width="{$firstColWidth}"  class="{$firstColStyle}">
          Description:
          </td>
          <td width="{$secondColWidth}">
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
          <td width="{$firstColWidth}" class="{$firstColStyle}">
          History:
          </td>
          <td width="{$secondColWidth}">
            <table xsl:use-attribute-sets="cellspacing" class="{$tabledefaultStyle}" width="100%">
              <xsl:for-each select="changeHistory">
                <xsl:call-template name="historydetails"/>
              </xsl:for-each>
            </table>
          </td>
     </tr>
     </xsl:if>
   </xsl:template>

   <xsl:template name="historydetails">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            scope:</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="changeScope"/>
        </td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            old value:</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="oldValue"/>
        </td></tr>
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            change date:</td>
            <td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="changeDate"/>
        </td></tr>
        <xsl:if test="comment and normalize-space(comment)!=''">
          <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            comment:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="comment"/>
          </td></tr>
        </xsl:if>
  </xsl:template>

  <xsl:template name="datasetcontact">
    <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Contact:</xsl:text>
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
     <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Publisher:</xsl:text>
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
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
           Publish Place:</td>
          <td width="{$secondColWidth}" class="{$secondColStyle}">
          <xsl:value-of select="."/>
          </td>
      </tr>
   </xsl:for-each>
  </xsl:template>

  <xsl:template name="datasetmethod">
     <xsl:for-each select="methods">
     <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Methods Info:</xsl:text>
     </td></tr>
      <tr>
        <td colspan="2">
        <xsl:call-template name="method">
          <xsl:with-param name="methodfirstColStyle" select="$firstColStyle"/>
        </xsl:call-template>
        </td>
      </tr>
    </xsl:for-each>
  </xsl:template>

   <xsl:template name="datasetproject">
    <xsl:for-each select="project">
     <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Project Info:</xsl:text>
     </td></tr>
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



  <xsl:template name="datasetentity" >
   <xsl:if test="dataTable or spatialRaster or spatialVector or storedProcedures or view or otherEntity">
      <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Data Tables, Images, and Other Entities:</xsl:text>
     </td></tr>
   </xsl:if>
 <xsl:choose>
   <xsl:when test="$displaymodule!='printall'">
    <xsl:for-each select="dataTable">
      <xsl:call-template name="entityurl">
        <xsl:with-param name="type">dataTable</xsl:with-param>
        <xsl:with-param name="showtype">Data Table</xsl:with-param>
        <xsl:with-param name="index" select="position()"/>
      </xsl:call-template>
   </xsl:for-each>
   <xsl:for-each select="spatialRaster">
     <xsl:call-template name="entityurl">
        <xsl:with-param name="type">spatialRaster</xsl:with-param>
        <xsl:with-param name="showtype">Spatial Raster</xsl:with-param>
        <xsl:with-param name="index" select="position()"/>
      </xsl:call-template>
   </xsl:for-each>
   <xsl:for-each select="spatialVector">
      <xsl:call-template name="entityurl">
        <xsl:with-param name="type">spatialVector</xsl:with-param>
        <xsl:with-param name="showtype">Spatial Vector</xsl:with-param>
        <xsl:with-param name="index" select="position()"/>
      </xsl:call-template>
   </xsl:for-each>
   <xsl:for-each select="storedProcedure">
     <xsl:call-template name="entityurl">
        <xsl:with-param name="type">storedProcedure</xsl:with-param>
        <xsl:with-param name="showtype">Stored Procedure</xsl:with-param>
        <xsl:with-param name="index" select="position()"/>
      </xsl:call-template>
   </xsl:for-each>
   <xsl:for-each select="view">
      <xsl:call-template name="entityurl">
        <xsl:with-param name="type">view</xsl:with-param>
        <xsl:with-param name="showtype">View</xsl:with-param>
        <xsl:with-param name="index" select="position()"/>
      </xsl:call-template>
   </xsl:for-each>
   <xsl:for-each select="otherEntity">
      <xsl:call-template name="entityurl">
        <xsl:with-param name="type">otherEntity</xsl:with-param>
        <xsl:with-param name="showtype">Other Entity</xsl:with-param>
        <xsl:with-param name="index" select="position()"/>
      </xsl:call-template>
   </xsl:for-each>
   </xsl:when>
   <xsl:otherwise>
    <xsl:for-each select="dataTable">
    <xsl:variable name="currentNode" select="position()"/>
    <xsl:for-each select="../.">
      <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Data Table:</xsl:text>
     </td></tr>
     <tr><td>
      <xsl:call-template name="chooseentity">
        <xsl:with-param name="entitytype">dataTable</xsl:with-param>
        <xsl:with-param name="entityindex" select="$currentNode"/>
      </xsl:call-template>
     </td></tr>
   </xsl:for-each>
   </xsl:for-each>
   <xsl:for-each select="spatialRaster">
     <xsl:variable name="currentNode" select="position()"/>
     <xsl:for-each select="../.">
     <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Spatial Raster:</xsl:text>
     </td></tr>
     <tr><td>
     <xsl:call-template name="chooseentity">
        <xsl:with-param name="entitytype">spatialRaster</xsl:with-param>
        <xsl:with-param name="entityindex" select="$currentNode"/>
      </xsl:call-template>
     </td></tr>
   </xsl:for-each>
   </xsl:for-each>
   <xsl:for-each select="spatialVector">
    <xsl:variable name="currentNode" select="position()"/>
     <xsl:for-each select="../.">
     <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Spatial Vector:</xsl:text>
     </td></tr>
     <tr><td>
      <xsl:call-template name="chooseentity">
        <xsl:with-param name="entitytype">spatialVector</xsl:with-param>
        <xsl:with-param name="entityindex" select="$currentNode"/>
      </xsl:call-template>
     </td></tr>
   </xsl:for-each>
   </xsl:for-each>
   <xsl:for-each select="storedProcedure">
    <xsl:variable name="currentNode" select="position()"/>
     <xsl:for-each select="../.">
     <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Stored Procedure:</xsl:text>
     </td></tr>
     <tr><td>
     <xsl:call-template name="chooseentity">
        <xsl:with-param name="entitytype">storedProcedure</xsl:with-param>
        <xsl:with-param name="entityindex" select="$currentNode"/>
      </xsl:call-template>
     </td></tr>
   </xsl:for-each>
   </xsl:for-each>
   <xsl:for-each select="view">
    <xsl:variable name="currentNode" select="position()"/>
     <xsl:for-each select="../.">
     <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>View:</xsl:text>
     </td></tr>
     <tr><td>
      <xsl:call-template name="chooseentity">
        <xsl:with-param name="entitytype">view</xsl:with-param>
        <xsl:with-param name="entityindex" select="$currentNode"/>
      </xsl:call-template>
     </td></tr>
   </xsl:for-each>
   </xsl:for-each>
   <xsl:for-each select="otherEntity">
    <xsl:variable name="currentNode" select="position()"/>
     <xsl:for-each select="../.">
     <tr><td class="{$subHeaderStyle}" colspan="2">
        <xsl:text>Other Entity:</xsl:text>
     </td></tr>
     <tr><td>
      <xsl:call-template name="chooseentity">
        <xsl:with-param name="entitytype">otherEntity</xsl:with-param>
        <xsl:with-param name="entityindex" select="$currentNode"/>
      </xsl:call-template>
     </td></tr>
   </xsl:for-each>
   </xsl:for-each>
   </xsl:otherwise>
   </xsl:choose>
  </xsl:template>

  <xsl:template name="entityurl">
     <xsl:param name="showtype"/>
     <xsl:param name="type"/>
     <xsl:param name="index"/>
      <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
             &#160;</td>
            <td width="{$secondColWidth}" class="{$firstColStyle}">
             <a><xsl:attribute name="href">
              <xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/>&amp;displaymodule=entity&amp;entitytype=<xsl:value-of select="$type"/>&amp;entityindex=<xsl:value-of select="$index"/></xsl:attribute>
             <b><xsl:value-of select="./entityName"/> (<xsl:value-of select="$showtype"/>)</b></a>
            </td>
         </tr>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
         <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
             &#160;</td>
            <td width="{$secondColWidth}" class="{$firstColStyle}">
             <a><xsl:attribute name="href">
              <xsl:value-of select="$tripleURI"/><xsl:value-of select="$docid"/>&amp;displaymodule=entity&amp;entitytype=<xsl:value-of select="$type"/>&amp;entityindex=<xsl:value-of select="$index"/></xsl:attribute>
             <b><xsl:value-of select="./entityName"/> (<xsl:value-of select="$showtype"/>)</b></a>
            </td>
         </tr>
       </xsl:otherwise>
     </xsl:choose>
  </xsl:template>

  <xsl:template match="text()" mode="dataset" />
  <xsl:template match="text()" mode="resource" />

</xsl:stylesheet>
