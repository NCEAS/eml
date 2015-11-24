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

    <!--<xsl:import href="eml-resource.xsl"/>-->
    <xsl:output method="html" encoding="iso-8859-1"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />  

    <xsl:template name="citation">
      <xsl:param name="citationfirstColStyle"/>
      <xsl:param name="citationsubHeaderStyle"/>
      <table class="{$tabledefaultStyle}">
        <xsl:choose>
         <xsl:when test="references!=''">
          <xsl:variable name="ref_id" select="references"/>
          <xsl:variable name="references" select="$ids[@id=$ref_id]" />
          <xsl:for-each select="$references">
            <xsl:call-template name="citationCommon">
             <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
             <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="citationCommon">
            <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
            <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
      </table>
  </xsl:template>

  <xsl:template name="citationCommon">
    <xsl:param name="citationfirstColStyle"/>
    <xsl:param name="citationsubHeaderStyle"/>
    <tr><td colspan="2">
        <xsl:call-template name="resource">
          <xsl:with-param name="resfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="ressubHeaderStyle" select="$citationsubHeaderStyle"/>
          <xsl:with-param name="creator">Author(s):</xsl:with-param>
        </xsl:call-template>
       </td>
    </tr>

    <xsl:for-each select="article">
       <xsl:call-template name="citationarticle">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:for-each select="book">
       <xsl:call-template name="citationbook">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:for-each select="chapter">
       <xsl:call-template name="citationchapter">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:for-each select="editedBook">
       <xsl:call-template name="citationeditedBook">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:for-each select="manuscript">
       <xsl:call-template name="citationmanuscript">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:for-each select="report">
       <xsl:call-template name="citationreport">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:for-each select="thesis">
       <xsl:call-template name="citationthesis">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:for-each select="conferenceProceedings">
       <xsl:call-template name="citationconferenceProceedings">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:for-each select="personalCommunication">
       <xsl:call-template name="citationpersonalCommunication">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:for-each select="map">
       <xsl:call-template name="citationmap">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:for-each select="generic">
       <xsl:call-template name="citationgeneric">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:for-each select="audioVisual">
       <xsl:call-template name="citationaudioVisual">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:for-each select="presentation">
       <xsl:call-template name="citationpresentation">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
    </xsl:for-each>

    <xsl:if test="access and normalize-space(access)!=''">
      <tr><td colspan="2">
        <xsl:for-each select="access">
          <xsl:call-template name="access">
            <xsl:with-param name="accessfirstColStyle" select="$citationfirstColStyle"/>
            <xsl:with-param name="accesssubHeaderStyle" select="$citationsubHeaderStyle"/>
         </xsl:call-template>
        </xsl:for-each>
      </td>
     </tr>
   </xsl:if>
  </xsl:template>


  <xsl:template name="citationarticle">
     <xsl:param name="citationfirstColStyle"/>
     <xsl:param name="citationsubHeaderStyle"/>

        <tr><td class="{$citationsubHeaderStyle}" colspan="2"><xsl:text>ARTICLE:</xsl:text></td></tr>
           <tr><td class="{$citationfirstColStyle}">
            Journal:</td><td class="{$secondColStyle}">
            <xsl:value-of select="journal"/></td></tr>
           <tr><td class="{$citationfirstColStyle}">
            Volume:</td><td class="{$secondColStyle}">
            <xsl:value-of select="volume"/></td></tr>
        <xsl:if test="issue and normalize-space(issue)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Issue:</td><td class="{$secondColStyle}">
            <xsl:value-of select="issue"/></td></tr>
        </xsl:if>
           <tr><td class="{$citationfirstColStyle}">
            Page Range:</td><td class="{$secondColStyle}">
            <xsl:value-of select="pageRange"/></td></tr>
        <xsl:if test="publisher and normalize-space(publisher)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Publisher:</td><td class="{$secondColStyle}">
            &#160;</td></tr>
           <xsl:for-each select="publisher">
            <tr><td colspan="2">
              <xsl:call-template name="party">
                <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
                <xsl:with-param name="partysubHeaderStyle" select="$citationsubHeaderStyle"/>
              </xsl:call-template>
            </td></tr>
          </xsl:for-each>
        </xsl:if>
         <xsl:if test="publicationPlace and normalize-space(publicationPlace)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Publication Place:</td><td class="{$secondColStyle}">
            <xsl:value-of select="publicationPlace"/></td></tr>
        </xsl:if>
        <xsl:if test="ISSN and normalize-space(ISSN)!=''">
           <tr><td class="{$citationfirstColStyle}">
            ISSN:</td><td class="{$secondColStyle}">
            <xsl:value-of select="ISSN"/></td></tr>
        </xsl:if>

  </xsl:template>



  <xsl:template name="citationbook">
    <xsl:param name="citationfirstColStyle"/>
    <xsl:param name="citationsubHeaderStyle"/>
    <xsl:param name="notshow" />
       <xsl:if test="$notshow =''">
          <tr><td colspan="2" class="{$citationsubHeaderStyle}"><xsl:text>BOOK:</xsl:text></td></tr>
        </xsl:if>
        <tr><td class="{$citationfirstColStyle}">
            Publisher:</td><td>
           <xsl:for-each select="publisher">
             <xsl:call-template name="party">
                <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
              </xsl:call-template>
          </xsl:for-each>
        </td></tr>
        <xsl:if test="publicationPlace and normalize-space(publicationPlace)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Publication Place:</td><td class="{$secondColStyle}">
            <xsl:value-of select="publicationPlace"/></td></tr>
        </xsl:if>

        <xsl:if test="edition and normalize-space(edition)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Edition:</td><td class="{$secondColStyle}">
            <xsl:value-of select="edition"/></td></tr>
        </xsl:if>

        <xsl:if test="volume and normalize-space(volume)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Volume:</td><td class="{$secondColStyle}">
            <xsl:value-of select="volume"/></td></tr>
        </xsl:if>

         <xsl:if test="numberOfVolumes and normalize-space(numberOfVolumes)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Number of Volumes:</td><td class="{$secondColStyle}">
            <xsl:value-of select="numberOfVolumes"/></td></tr>
        </xsl:if>

        <xsl:if test="totalPages and normalize-space(totalPages)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Total Pages:</td><td class="{$secondColStyle}">
            <xsl:value-of select="totalPages"/></td></tr>
        </xsl:if>

        <xsl:if test="totalFigures and normalize-space(totalFigures)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Total Figures:</td><td class="{$secondColStyle}">
            <xsl:value-of select="totalFigures"/></td></tr>
        </xsl:if>

        <xsl:if test="totalTables and normalize-space(totalTables)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Total Tables:</td><td class="{$secondColStyle}">
            <xsl:value-of select="totalTables"/></td></tr>
        </xsl:if>

        <xsl:if test="ISBN and normalize-space(ISBN)!=''">
           <tr><td class="{$citationfirstColStyle}">
            ISBN:</td><td class="{$secondColStyle}">
            <xsl:value-of select="ISBN"/></td></tr>
        </xsl:if>

   </xsl:template>



   <xsl:template name="citationchapter">
      <xsl:param name="citationfirstColStyle"/>
      <xsl:param name="citationsubHeaderStyle"/>
       <tr><td colspan="2" class="{$citationsubHeaderStyle}"><xsl:text>CHAPTER:</xsl:text></td></tr>
        <xsl:if test="chapterNumber and normalize-space(chapterNumber)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Chapter Number:</td><td class="{$secondColStyle}">
            <xsl:value-of select="chapterNumber"/></td></tr>
        </xsl:if>

       <tr><td class="{$citationfirstColStyle}">
        Book Editor:</td><td class="{$secondColStyle}">
        &#160;</td></tr>
        <xsl:for-each select="editor">
          <tr><td colspan="2">
            <xsl:call-template name="party">
              <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
            </xsl:call-template>
          </td></tr>
        </xsl:for-each>

       <tr><td class="{$citationfirstColStyle}">
        Book Title:</td><td class="{$secondColStyle}">
        <xsl:value-of select="bookTitle"/></td></tr>

        <xsl:if test="pageRange and normalize-space(pageRange)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Page Range:</td><td class="{$secondColStyle}">
            <xsl:value-of select="pageRange"/></td></tr>
        </xsl:if>

        <xsl:call-template name="citationbook">
          <xsl:with-param name="notshow" select="yes"/>
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
        </xsl:call-template>
   </xsl:template>



   <xsl:template name="citationeditedBook">
      <xsl:param name="citationfirstColStyle"/>
      <xsl:param name="citationsubHeaderStyle"/>
       <xsl:call-template name="citationbook">
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
        </xsl:call-template>
   </xsl:template>



   <xsl:template name="citationmanuscript">
     <xsl:param name="citationfirstColStyle"/>
     <xsl:param name="citationsubHeaderStyle"/>
       <tr><td colspan="2" class="{$citationsubHeaderStyle}"><xsl:text>MANUSCRIPT:</xsl:text></td></tr>
       <tr><td class="{$citationfirstColStyle}">
            Institution:
            </td>
            <td class="{$secondColStyle}">
              &#160;
            </td>
       </tr>
       <xsl:for-each select="institution">
        <tr><td colspan="2">
              <xsl:call-template name="party">
                <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
              </xsl:call-template>
           </td>
        </tr>
       </xsl:for-each>

       <xsl:if test="totalPages and normalize-space(totalPages)!=''">
         <tr><td class="{$citationfirstColStyle}">
            Total Pages:</td><td class="{$secondColStyle}">
            <xsl:value-of select="totalPages"/></td></tr>
       </xsl:if>
   </xsl:template>



   <xsl:template name="citationreport">
     <xsl:param name="citationfirstColStyle"/>
     <xsl:param name="citationsubHeaderStyle"/>
     <tr><td colspan="2" class="{$citationsubHeaderStyle}"><xsl:text>REPORT:</xsl:text></td></tr>
       <xsl:if test="reportNumber and normalize-space(reportNumber)!=''">
          <tr><td class="{$citationfirstColStyle}">
            Report Number:</td><td class="{$secondColStyle}">
          <xsl:value-of select="reportNumber"/></td></tr>
       </xsl:if>

       <xsl:if test="publisher and normalize-space(publisher)!=''">
          <tr><td class="{$citationfirstColStyle}">
            Publisher:</td><td class="{$secondColStyle}">
            &#160;</td></tr>
          <xsl:for-each select="publisher">
           <tr><td colspan="2">
              <xsl:call-template name="party">
                <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>

              </xsl:call-template>
           </td></tr>
          </xsl:for-each>
       </xsl:if>

       <xsl:if test="publicationPlace and normalize-space(publicationPlace)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Publication Place:</td><td class="{$secondColStyle}">
            <xsl:value-of select="publicationPlace"/></td></tr>
       </xsl:if>

       <xsl:if test="totalPages and normalize-space(totalPages)!=''">
         <tr><td class="{$citationfirstColStyle}">
            Total Pages:</td><td class="{$secondColStyle}">
            <xsl:value-of select="totalPages"/></td></tr>
       </xsl:if>
   </xsl:template>


   <xsl:template name="citationthesis">
     <xsl:param name="citationfirstColStyle"/>
     <xsl:param name="citationsubHeaderStyle"/>
     <tr><td colspan="2" class="{$citationsubHeaderStyle}"><xsl:text>THESIS:</xsl:text></td></tr>
        <tr><td class="{$citationfirstColStyle}">
        Degree:</td><td class="{$secondColStyle}">
        <xsl:value-of select="degree"/></td></tr>

       <tr><td class="{$citationfirstColStyle}">
        Degree Institution:</td><td class="{$secondColStyle}">
        &#160;</td></tr>
        <xsl:for-each select="institution">
          <tr><td colspan="2">
              <xsl:call-template name="party">
                <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
                <xsl:with-param name="partysubHeaderStyle" select="$citationsubHeaderStyle"/>
              </xsl:call-template>
          </td></tr>
        </xsl:for-each>

       <xsl:if test="totalPages and normalize-space(totalPages)!=''">
         <tr><td class="{$citationfirstColStyle}">
         Total Pages:</td><td class="{$secondColStyle}">
         <xsl:value-of select="totalPages"/></td></tr>
       </xsl:if>
   </xsl:template>

   <xsl:template name="citationconferenceProceedings">
     <xsl:param name="citationfirstColStyle"/>
     <xsl:param name="citationsubHeaderStyle"/>
     <tr><td colspan="2" class="{$citationsubHeaderStyle}"><xsl:text>CONFERENCE PROCEEDINGS:</xsl:text></td></tr>
      <xsl:if test="conferenceName and normalize-space(conferenceName)!=''">
         <tr><td class="{$citationfirstColStyle}">
         Conference Name:</td><td class="{$secondColStyle}">
         <xsl:value-of select="conferenceName"/></td></tr>
       </xsl:if>

       <xsl:if test="conferenceDate and normalize-space(conferenceDate)!=''">
         <tr><td class="{$citationfirstColStyle}">
         Date:</td><td class="{$secondColStyle}">
         <xsl:value-of select="conferenceDate"/></td></tr>
       </xsl:if>

       <xsl:if test="conferenceLocation and normalize-space(conferenceLocation)!=''">
        <tr><td class="{$citationfirstColStyle}">
         Location:</td><td class="{$secondColStyle}">
         &#160;</td></tr>
         <tr><td colspan="2">
           <xsl:for-each select="conferenceLocation">
            <xsl:call-template name="party">
             <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
            </xsl:call-template>
           </xsl:for-each>
          </td>
        </tr>
       </xsl:if>

       <xsl:call-template name="citationchapter">
          <xsl:with-param name="notshow" select="yes"/>
          <xsl:with-param name="citationfirstColStyle" select="$citationfirstColStyle"/>
          <xsl:with-param name="citationsubHeaderStyle" select="$citationsubHeaderStyle"/>
       </xsl:call-template>
  </xsl:template>

  <xsl:template name="citationpersonalCommunication">
    <xsl:param name="citationfirstColStyle"/>
    <xsl:param name="citationsubHeaderStyle"/>
    <tr><td colspan="2" class="{$citationsubHeaderStyle}"><xsl:text>PERSONAL COMMUNICATION:</xsl:text></td></tr>
     <xsl:if test="publisher and normalize-space(publisher)!=''">
          <tr><td class="{$citationfirstColStyle}">
            Publisher:</td><td class="{$secondColStyle}">
            &#160;</td></tr>
          <xsl:for-each select="publisher">
           <tr><td colspan="2">
              <xsl:call-template name="party">
                <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
               </xsl:call-template>
           </td></tr>
          </xsl:for-each>
       </xsl:if>


       <xsl:if test="publicationPlace and normalize-space(publicationPlace)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Publication Place:</td><td class="{$secondColStyle}">
            <xsl:value-of select="publicationPlace"/></td></tr>
       </xsl:if>

       <xsl:if test="communicationType and normalize-space(communicationType)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Communication Type:</td><td class="{$secondColStyle}">
            <xsl:value-of select="communicationType"/></td></tr>
       </xsl:if>

      <xsl:if test="recipient and normalize-space(recipient)!=''">
          <tr><td class="{$citationfirstColStyle}">
            Recipient:</td><td class="{$secondColStyle}">
            &#160;</td></tr>
          <xsl:for-each select="recipient">
           <tr><td colspan="2">
              <xsl:call-template name="party">
                <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
              </xsl:call-template>
           </td></tr>
          </xsl:for-each>
      </xsl:if>
  </xsl:template>


  <xsl:template name="citationmap">
    <xsl:param name="citationfirstColStyle"/>
    <xsl:param name="citationsubHeaderStyle"/>
    <tr><td colspan="2" class="{$citationsubHeaderStyle}"><xsl:text>MAP:</xsl:text></td></tr>
      <xsl:if test="publisher and normalize-space(publisher)!=''">
          <tr><td class="{$citationfirstColStyle}">
            Publisher:</td><td class="{$secondColStyle}">
            &#160;</td></tr>
          <xsl:for-each select="publisher">
           <tr><td colspan="2">
              <xsl:call-template name="party">
                <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
               </xsl:call-template>
           </td></tr>
          </xsl:for-each>
       </xsl:if>

       <xsl:if test="edition and normalize-space(edition)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Edition:</td><td class="{$secondColStyle}">
            <xsl:value-of select="edition"/></td></tr>
       </xsl:if>

       <xsl:if test="geographicCoverage and normalize-space(geographicCoverage)!=''">
          <xsl:for-each select="geographicCoverage">
            <xsl:call-template name="geographicCoverage">
            </xsl:call-template>
          </xsl:for-each>
       </xsl:if>

       <xsl:if test="scale and normalize-space(scale)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Scale:</td><td class="{$secondColStyle}">
            <xsl:value-of select="scale"/></td></tr>
       </xsl:if>
 </xsl:template>


  <xsl:template name="citationgeneric">
    <xsl:param name="citationfirstColStyle"/>
    <xsl:param name="citationsubHeaderStyle"/>
    <tr><td colspan="2" class="{$citationsubHeaderStyle}"><xsl:text>Generic Citation:</xsl:text></td></tr>
    <tr><td class="{$citationfirstColStyle}">
            Publisher:</td><td class="{$secondColStyle}">
            &#160;
      </td></tr>
      <xsl:for-each select="publisher">
         <tr><td colspan="2">
              <xsl:call-template name="party">
                <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
              </xsl:call-template>
         </td></tr>
      </xsl:for-each>

      <xsl:if test="publicationPlace and normalize-space(publicationPlace)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Publication Place:</td><td class="{$secondColStyle}">
            <xsl:value-of select="publicationPlace"/></td></tr>
      </xsl:if>

      <xsl:if test="referenceType and normalize-space(referenceType)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Reference Type:</td><td class="{$secondColStyle}">
            <xsl:value-of select="referenceType"/></td></tr>
      </xsl:if>

      <xsl:if test="volume and normalize-space(volume)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Volume:</td><td class="{$secondColStyle}">
            <xsl:value-of select="volume"/></td></tr>
      </xsl:if>

      <xsl:if test="numberOfVolumes and normalize-space(numberOfVolumes)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Number of Volumes:</td><td class="{$secondColStyle}">
            <xsl:value-of select="numberOfVolumes"/></td></tr>
      </xsl:if>

      <xsl:if test="totalPages and normalize-space(totalPages)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Total Pages:</td><td class="{$secondColStyle}">
            <xsl:value-of select="totalPages"/></td></tr>
      </xsl:if>

      <xsl:if test="totalFigures and normalize-space(totalFigures)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Total Figures:</td><td class="{$secondColStyle}">
            <xsl:value-of select="totalFigures"/></td></tr>
      </xsl:if>

      <xsl:if test="totalTables and normalize-space(totalTables)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Total Tables:</td><td class="{$secondColStyle}">
            <xsl:value-of select="totalTables"/></td></tr>
      </xsl:if>

      <xsl:if test="edition and normalize-space(edition)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Edition:</td><td class="{$secondColStyle}">
            <xsl:value-of select="edition"/></td></tr>
      </xsl:if>

      <xsl:if test="originalPublication and normalize-space(originalPublication)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Supplemental Info for Original Publication:</td><td class="{$secondColStyle}">
            <xsl:value-of select="originalPublication"/></td></tr>
      </xsl:if>

      <xsl:if test="reprintEdition and normalize-space(reprintEdition)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Reprint Edition:</td><td class="{$secondColStyle}">
            <xsl:value-of select="reprintEdition"/></td></tr>
      </xsl:if>

      <xsl:if test="reviewedItem and normalize-space(reviewedItem)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Review Item:</td><td class="{$secondColStyle}">
            <xsl:value-of select="reviewedItem"/></td></tr>
      </xsl:if>

      <xsl:if test="ISBN and normalize-space(ISBN)!=''">
           <tr><td class="{$citationfirstColStyle}">
            ISBN:</td><td class="{$secondColStyle}">
            <xsl:value-of select="ISBN"/></td></tr>
      </xsl:if>

      <xsl:if test="ISSN and normalize-space(ISSN)!=''">
           <tr><td class="{$citationfirstColStyle}">
            ISSN:</td><td class="{$secondColStyle}">
            <xsl:value-of select="ISSN"/></td></tr>
      </xsl:if>
  </xsl:template>

  <xsl:template name="citationaudioVisual">
    <xsl:param name="citationfirstColStyle"/>
    <xsl:param name="citationsubHeaderStyle"/>
    <tr><td colspan="2" class="{$citationsubHeaderStyle}"><xsl:text>Media Citation:</xsl:text></td></tr>
      <tr><td class="{$citationfirstColStyle}">
            Publisher:</td><td class="{$secondColStyle}">
            &#160;
      </td></tr>
       <xsl:for-each select="publisher">
         <tr><td colspan="2">
              <xsl:call-template name="party">
                <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
              </xsl:call-template>
         </td></tr>
      </xsl:for-each>

      <xsl:if test="publicationPlace and normalize-space(publicationPlace)!=''">
           <tr><td class="{$citationfirstColStyle}">
            Publication Place:</td><td class="{$secondColStyle}">
            &#160;</td></tr>
            <xsl:for-each select="publicationPlace">
                <tr><td class="{$citationfirstColStyle}">
                    &#160;</td>
                    <td class="{$secondColStyle}">
                    <xsl:value-of select="."/>
                </td></tr>
            </xsl:for-each>
      </xsl:if>

      <xsl:if test="performer and normalize-space(performer)!=''">
            <tr><td class="{$citationfirstColStyle}">
            Performer:</td><td class="{$secondColStyle}">
            &#160;</td></tr>
            <xsl:for-each select="performer">
                <tr><td colspan="2">
                   <xsl:call-template name="party">
                     <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
                   </xsl:call-template>
                </td></tr>
            </xsl:for-each>
      </xsl:if>

      <xsl:if test="ISBN and normalize-space(ISBN)!=''">
           <tr><td class="{$citationfirstColStyle}">
            ISBN:</td><td class="{$secondColStyle}">
            <xsl:value-of select="ISBN"/></td></tr>
      </xsl:if>
  </xsl:template>

  <xsl:template name="citationpresentation">
    <xsl:param name="citationfirstColStyle"/>
    <xsl:param name="citationsubHeaderStyle"/>
    <tr><td colspan="2" class="{$citationsubHeaderStyle}"><xsl:text>Presentation:</xsl:text></td></tr>
      <xsl:if test="conferenceName and normalize-space(conferenceName)!=''">
         <tr><td class="{$citationfirstColStyle}">
         Conference Name:</td><td class="{$secondColStyle}">
         <xsl:value-of select="conferenceName"/></td></tr>
       </xsl:if>

       <xsl:if test="conferenceDate and normalize-space(conferenceDate)!=''">
         <tr><td class="{$citationfirstColStyle}">
         Date:</td><td class="{$secondColStyle}">
         <xsl:value-of select="conferenceDate"/></td></tr>
       </xsl:if>

         <tr><td class="{$citationfirstColStyle}">
         Location:</td><td class="{$secondColStyle}">
         &#160;</td></tr>
         <tr><td colspan="2">
           <xsl:for-each select="conferenceLocation">
            <xsl:call-template name="party">
             <xsl:with-param name="partyfirstColStyle" select="$citationfirstColStyle"/>
            </xsl:call-template>
           </xsl:for-each>
          </td>
        </tr>
  </xsl:template>


 </xsl:stylesheet>
