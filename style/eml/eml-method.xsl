<?xml version="1.0"?>
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

  <xsl:output method="html" encoding="iso-8859-1"
    doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
    indent="yes" />  

 <xsl:template name="method">
  <xsl:param name="methodfirstColStyle"/>
  <xsl:param name="methodsubHeaderStyle"/>
  <table class="{$tabledefaultStyle}">
    <tr>
      <th colspan="2">
        Step by Step Procedures
      </th>
    </tr>
    <xsl:for-each select="methodStep">
     <tr><td class="{$methodfirstColStyle}">
          <b>Step<xsl:text> </xsl:text><xsl:value-of select="position()"/>:</b>
         </td>
         <td width="${secondColWidth}" class="{$secondColStyle}">
           &#160;
         </td>
     </tr>
     <xsl:call-template name="methodStep">
        <xsl:with-param name="methodfirstColStyle" select="$methodfirstColStyle"/>
        <xsl:with-param name="methodsubHeaderStyle" select="$methodsubHeaderStyle"/>
     </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="sampling">
      <xsl:call-template name="sampling">
         <xsl:with-param name="methodfirstColStyle" select="$methodfirstColStyle"/>
         <xsl:with-param name="methodsubHeaderStyle" select="$methodsubHeaderStyle"/>
     </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="qualityControl">
      <tr><td class="{$methodfirstColStyle}">
          <b>Quality Control Step<xsl:text> </xsl:text><xsl:value-of select="position()"/>:</b>
         </td>
         <td width="${secondColWidth}" class="{$secondColStyle}">
           &#160;
         </td>
     </tr>
      <xsl:call-template name="qualityControl">
         <xsl:with-param name="methodfirstColStyle" select="$methodfirstColStyle"/>
         <xsl:with-param name="methodsubHeaderStyle" select="$methodsubHeaderStyle"/>
     </xsl:call-template>
    </xsl:for-each>
   </table>
 </xsl:template>

 <!-- ******************************************
      Method step
      *******************************************-->

 <xsl:template name="methodStep">
   <xsl:param name="methodfirstColStyle"/>
   <xsl:param name="methodsubHeaderStyle"/>
   <xsl:call-template name="step">
     <xsl:with-param name="protocolfirstColStyle" select="$methodfirstColStyle"/>
     <xsl:with-param name="protocolsubHeaderStyle" select="$methodsubHeaderStyle"/>
   </xsl:call-template>
   <xsl:for-each select="dataSource">
       <tr><td colspan="2">
             <xsl:apply-templates mode="dataset">
             </xsl:apply-templates>
           </td>
        </tr>
   </xsl:for-each>
 </xsl:template>

 <!-- *********************************************
      Sampling
      *********************************************-->

 <xsl:template name="sampling">
   <xsl:param name="methodfirstColStyle"/>
   <xsl:param name="methodsubHeaderStyle"/>
   <xsl:for-each select="studyExtent">
      <xsl:call-template name="studyExtent">
         <xsl:with-param name="methodfirstColStyle" select="$methodfirstColStyle"/>
      </xsl:call-template>
   </xsl:for-each>
   <xsl:for-each select="samplingDescription">
     <tr><td class="{$methodfirstColStyle}">
         Sampling Description:
         </td>
         <td width="${secondColWidth}">
          <xsl:call-template name="text">
           <xsl:with-param name="textfirstColStyle" select="$methodfirstColStyle"/>
         </xsl:call-template>
         </td>
     </tr>
   </xsl:for-each>
   <xsl:for-each select="spatialSamplingUnits">
      <xsl:call-template name="spatialSamplingUnits">
         <xsl:with-param name="methodfirstColStyle" select="$methodfirstColStyle"/>
      </xsl:call-template>
   </xsl:for-each>
   <xsl:for-each select="citation">
      <tr><td class="{$methodfirstColStyle}">
         Sampling Citation:
         </td>
         <td width="${secondColWidth}">
           <xsl:call-template name="citation">
            <xsl:with-param name="citationfirstColStyle" select="$methodfirstColStyle"/>
            <xsl:with-param name="citationsubHeaderStyle" select="$methodsubHeaderStyle"/>
         </xsl:call-template>
         </td>
      </tr>
    </xsl:for-each>
 </xsl:template>

 <xsl:template name="studyExtent">
    <xsl:param name="methodfirstColStyle"/>
    <xsl:param name="methodsubHeaderStyle"/>
    <xsl:for-each select="coverage">
        <tr><td class="{$methodfirstColStyle}">
         Sampling Coverage:
         </td>
         <td width="${secondColWidth}">
            <xsl:call-template name="coverage">
            </xsl:call-template>
         </td>
       </tr>
    </xsl:for-each>
    <xsl:for-each select="description">
      <tr><td class="{$methodfirstColStyle}">
         Sampling Area And Frequency:
         </td>
         <td width="${secondColWidth}" >
           <xsl:call-template name="text">
              <xsl:with-param name="textfirstColStyle" select="$methodfirstColStyle"/>
           </xsl:call-template>
         </td>
       </tr>
    </xsl:for-each>
 </xsl:template>

 <xsl:template name="spatialSamplingUnits">
   <xsl:param name="methodfirstColStyle"/>
   <xsl:for-each select="referenceEntityId">
      <tr><td class="{$methodfirstColStyle}">
         Sampling Unit Reference:
         </td>
         <td width="${secondColWidth}" class="{$secondColStyle}">
          <xsl:value-of select="."/>
         </td>
      </tr>
   </xsl:for-each>
   <xsl:for-each select="coverage">
      <tr><td class="{$methodfirstColStyle}">
         Sampling Unit Location:
         </td>
         <td width="${secondColWidth}">
            <xsl:call-template name="coverage">
          </xsl:call-template>
         </td>
      </tr>
   </xsl:for-each>
 </xsl:template>

 <!-- ***************************************
      quality control
      ***************************************-->
  <xsl:template name="qualityControl">
   <xsl:param name="methodfirstColStyle"/>
   <xsl:param name="methodsubHeaderStyle"/>
   <xsl:call-template name="step">
     <xsl:with-param name="protocolfirstColStyle" select="$methodfirstColStyle"/>
     <xsl:with-param name="protocolsubHeaderStyle" select="$methodsubHeaderStyle"/>
   </xsl:call-template>
  </xsl:template>

 </xsl:stylesheet>

