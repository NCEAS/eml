<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-dataset-2.0.0beta4.xsl,v $'
  *      Authors: Matt Jones
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: tao $'
  *     '$Date: 2009-03-13 17:19:55 $'
  * '$Revision: 1.4.8.1 $'
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
               
  <xsl:param name="qformat">default</xsl:param>

  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css"
                href="{$stylePath}/{$qformat}/{$qformat}.css" />
        <script language="Javascript" type="text/JavaScript"
                src="{$stylePath}/{$qformat}/{$qformat}.js"></script>
        <script language="Javascript" type="text/JavaScript"
                src="{$styleCommonPath}/branding.js"></script>      
      </head>
      <body>

        <script language="JavaScript">insertTemplateOpening();</script> 
        
        <center>
          <h1>Data set description</h1>
          <h3>Ecological Metadata Language</h3>
        </center>
        <table width="100%">
        <xsl:apply-templates select="/dataset/title" mode="layout"/>
        <xsl:apply-templates select="/dataset/shortName" mode="layout"/>
        <xsl:apply-templates select="/dataset/identifier" mode="layout"/>
        </table>

        <xsl:apply-templates/>      


        <table width="100%">
        <tr><td class="highlight">
        <b><xsl:text>Related Metadata and Data Files:</xsl:text></b>
        </td></tr>
        <tr><td>
        <ul>
          <xsl:for-each select="//triple">
            <li>
             <a><xsl:attribute name="href"><xsl:value-of select="$contextURL" /><![CDATA[/metacat?action=read&qformat=]]><xsl:value-of select="$qformat" /><![CDATA[&docid=]]><xsl:value-of select="./subject"/></xsl:attribute><xsl:value-of select="./subject"/></a>
             <xsl:text> </xsl:text>
             <xsl:value-of select="./relationship"/>
             <xsl:text> </xsl:text>
             <a><xsl:attribute name="href"><xsl:value-of select="$contextURL" /><![CDATA[/metacat?action=read&qformat=]]><xsl:value-of select="$qformat" /><![CDATA[&docid=]]><xsl:value-of select="./object"/></xsl:attribute><xsl:value-of select="./object"/></a>
            </li>
          </xsl:for-each>
        </ul>
        </td></tr>
        </table>

        
        <script language="JavaScript">insertTemplateClosing();</script>
        
      </body> 
    </html>
  </xsl:template>

  <xsl:template match="identifier"/>
  <xsl:template match="identifier" mode="layout">
     <tr>
       <td class="highlight"><b><xsl:text>Metadata Identifier:</xsl:text></b></td>
       <td><xsl:value-of select="."/>
       <xsl:if test="./@system">
         (Metadata system=<xsl:value-of select="./@system"/>)
       </xsl:if>
       </td>
     </tr>
  </xsl:template>

  <xsl:template match="title"/>
  <xsl:template match="title" mode="layout">
     <tr>
       <td class="highlight"><b><xsl:text>Title:</xsl:text></b></td>
       <td><b><xsl:value-of select="."/></b></td>
     </tr>
  </xsl:template>

  <xsl:template match="shortName"/>
  <xsl:template match="shortName" mode="layout">
     <tr>
       <td class="highlight"><b><xsl:text>Short Name:</xsl:text></b></td>
       <td><xsl:value-of select="."/></td>
     </tr>
  </xsl:template>

  <xsl:template match="originator">
    <p class="indent">
      <xsl:apply-templates/>
    </p>
  </xsl:template>

  <xsl:template match="originator[1]">
      <p><xsl:text> </xsl:text></p>
      <table width="100%">
      <tr><td class="highlight">
      <b><xsl:text>Data Set Owner(s):</xsl:text></b>
      </td></tr>
      </table>
      <p class="indent">
        <xsl:apply-templates/>
      </p>
  </xsl:template>

  <xsl:template match="organizationName">
    <b><xsl:value-of select="."/></b><br />
  </xsl:template>

  <xsl:template match="individualName">
    <b>
       <xsl:value-of select="./salutation"/>
       <xsl:text> </xsl:text>
       <xsl:value-of select="./givenName"/>
       <xsl:text> </xsl:text>
       <xsl:value-of select="./surName"/>
    </b><br />
  </xsl:template>

  <xsl:template match="address">
    <table>
    <xsl:for-each select="./deliveryPoint">
      <tr>
      <td><xsl:value-of select="."/></td>
      </tr>
    </xsl:for-each>
    <tr>
    <td><xsl:value-of select="./city"/>
        <xsl:text>, </xsl:text>
        <xsl:value-of select="./administrativeArea"/>
        <xsl:text> </xsl:text>
        <xsl:value-of select="./postalCode"/>
    </td>
    </tr>
    <xsl:if test="./country">
      <tr><td><xsl:value-of select="./country"/></td></tr>
    </xsl:if>
    </table>
  </xsl:template>

  <xsl:template match="phone">
      <xsl:text>Phone: </xsl:text><xsl:value-of select="."/><br />
  </xsl:template>

  <xsl:template match="electronicMailAddress">
      <xsl:text>Email: </xsl:text><xsl:value-of select="."/><br />
  </xsl:template>

  <xsl:template match="onlineLink">
      <xsl:text>URL: </xsl:text><xsl:value-of select="."/><br />
  </xsl:template>

  <xsl:template match="role">
      <xsl:text>Role: </xsl:text><xsl:value-of select="."/><br />
  </xsl:template>

  <xsl:template match="abstract">
    <table width="100%">
    <tr>
    <td class="highlight"><b><xsl:text>Abstract:</xsl:text></b></td>
    </tr>
    <tr>
    <td><xsl:value-of select="./paragraph"/></td>
    </tr></table>
  </xsl:template>

  <xsl:template match="rights">
    <table width="100%">
    <tr>
    <td class="highlight"><b><xsl:text>License and Usage Rights:</xsl:text></b></td>
    </tr>
    <tr>
    <td><xsl:value-of select="./paragraph"/></td>
    </tr></table>
  </xsl:template>

  <xsl:template match="additionalInfo">
    <table width="100%">
    <tr>
    <td class="highlight"><b><xsl:text>Additional Information:</xsl:text></b></td>
    </tr>
    <tr>
    <td><xsl:value-of select="./paragraph"/></td>
    </tr></table>
  </xsl:template>

  <xsl:template match="onlineURL">
    <table width="100%">
    <tr>
    <td class="highlight"><b><xsl:text>Online Distribution information:</xsl:text></b></td>
    </tr>
    <tr>
    <td><a>
          <xsl:attribute name="href"><xsl:value-of select="."/></xsl:attribute>
          <xsl:value-of select="."/>
        </a>
    </td>
    </tr></table>
  </xsl:template>

  <xsl:template match="offlineMedium">
    <table width="100%">
    <tr>
    <td class="highlight"><b><xsl:text>Offline Distribution information:</xsl:text></b></td>
    </tr>
    <xsl:apply-templates/>
    </table>
  </xsl:template>

  <xsl:template match="medName">
    <tr><td><xsl:text>Medium: </xsl:text><xsl:value-of select="."/></td></tr>
  </xsl:template>

  <xsl:template match="temporalCov">
    <table width="100%">
    <tr>
    <td class="highlight"><b><xsl:text>Temporal Coverage:</xsl:text></b></td>
    </tr>
    <xsl:apply-templates/>
    </table>
  </xsl:template>

  <xsl:template match="rngdates">
    <tr><td>
    <xsl:text>Date Range: </xsl:text>
    <xsl:apply-templates select="begdate"/>
    <xsl:text> </xsl:text><xsl:apply-templates select="begtime"/>
    <xsl:text> </xsl:text><xsl:apply-templates select="beggeol"/>
    <xsl:text> to </xsl:text>
    <xsl:apply-templates select="enddate"/>
    <xsl:text> </xsl:text><xsl:apply-templates select="endtime"/>
    <xsl:text> </xsl:text><xsl:apply-templates select="endgeol"/>
    </td></tr>
  </xsl:template>

  <xsl:template match="sngdate">
    <tr><td>
    <xsl:text>Date: </xsl:text>
    <xsl:apply-templates select="caldate"/>
    <xsl:text> </xsl:text><xsl:apply-templates select="time"/>
    <xsl:text> </xsl:text><xsl:apply-templates select="geolage"/>
    </td></tr>
  </xsl:template>

  <xsl:template match="mdattim">
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="geographicCov">
    <table width="100%">
    <tr>
    <td class="highlight"><b><xsl:text>Geographic Coverage:</xsl:text></b></td>
    </tr>
    <tr><td><xsl:apply-templates/></td></tr>
    </table>
  </xsl:template>

  <xsl:template match="descgeog">
    <p>
    <xsl:text>Description: </xsl:text>
    <xsl:value-of select="."/>
    </p>
  </xsl:template>

  <xsl:template match="westbc">
    <xsl:text>West Bounding coordinate: </xsl:text>
    <xsl:value-of select="."/> degrees<br />
  </xsl:template>

  <xsl:template match="eastbc">
    <xsl:text>East Bounding coordinate: </xsl:text>
    <xsl:value-of select="."/> degrees<br />
  </xsl:template>

  <xsl:template match="northbc">
    <xsl:text>North Bounding coordinate: </xsl:text>
    <xsl:value-of select="."/> degrees<br />
  </xsl:template>

  <xsl:template match="southbc">
    <xsl:text>South Bounding coordinate: </xsl:text>
    <xsl:value-of select="."/> degrees<br />
  </xsl:template>

  <xsl:template match="taxonomicCov">
    <table width="100%">
    <tr>
    <td class="highlight"><b><xsl:text>Taxomomic Coverage:</xsl:text></b></td>
    </tr>
    <tr><td><xsl:apply-templates/></td></tr>
    </table>
  </xsl:template>

  <xsl:template match="keywtax">
    <p>
    <xsl:text>Taxon Keywords: </xsl:text>
    <xsl:apply-templates select="taxonkey"/>
    </p>
  </xsl:template>

  <xsl:template match="taxoncl">
    <p class="indent">
    <xsl:text>Name: </xsl:text><xsl:value-of select="taxonrv"/>
    <a target="itisca">
      <xsl:attribute name="href"><xsl:text><![CDATA[http://sis.agr.gc.ca/pls/itisca/taxastep?king=every&p_action=containing&p_ifx=aafc&taxa=]]></xsl:text><xsl:value-of select="taxonrv"/></xsl:attribute>
      Check ITIS*ca for this taxon
    </a>
    <br />
    <xsl:text>Rank: </xsl:text><xsl:value-of select="taxonrn"/><br />
    <xsl:text>Common name: </xsl:text><xsl:value-of select="common"/><br />
    <xsl:apply-templates select="taxoncl"/>
    </p>
  </xsl:template>

  <xsl:template match="keywordSet">
    <table width="100%">
    <tr><td class="highlight">
    <b><xsl:text>Keywords:</xsl:text></b>
    </td></tr>
    <xsl:if test="./keywordThesaurus">
      <tr><td>Thesaurus: <xsl:value-of select="keywordThesaurus"/></td></tr>
    </xsl:if>
    <tr><td>
      <ul>
        <xsl:for-each select="keyword">
          <li><xsl:value-of select="."/> 
          <xsl:if test="./@keywordType">
            (<xsl:value-of select="./@keywordType"/>)
          </xsl:if>
          </li>
        </xsl:for-each>
      </ul>
    </td></tr>
    </table>
  </xsl:template>
         
  <xsl:template match="triple"/>

</xsl:stylesheet>
