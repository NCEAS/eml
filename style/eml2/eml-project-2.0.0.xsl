<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-project-2.0.0.xsl,v $'
  *      Authors: Matthew Brooke
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: berkley $'
  *     '$Date: 2003-06-03 21:41:35 $'
  * '$Revision: 1.1 $'
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

<xsl:import href="eml-protocol-2.0.0beta6-@name@.xsl"/>
<xsl:import href="eml-coverage-2.0.0beta6-@name@.xsl"/>

  <xsl:output method="html" encoding="iso-8859-1"/>

  <xsl:template match="/">
   <html>
     <head>
        <link rel="stylesheet" type="text/css" href="{$stylePath}/{$qformat}.css" />
      <style type="text/css">
        @import url(@style-path@/sbclter-advanced.css);
      </style>
        <script language="JavaScript">
          <![CDATA[
          function submitform(action,form_ref) {
              form_ref.action.value=action;
              form_ref.abstractpath.value="";
              form_ref.qformat.value="sbclter";
              form_ref.submit();
          }
          function submitform1(action, abstractpath, form_ref) {
              form_ref.action.value=action;
              form_ref.abstractpath.value=abstractpath;
              form_ref.qformat.value="";
              form_ref.submit();
          }
          function submitform2(action, qformat, form_ref) {
              form_ref.action.value=action;
              form_ref.qformat.value=qformat;
              form_ref.abstractpath.value="";
              form_ref.submit();
          }
          ]]>
        </script>
     </head>
     <body>
      <!--<div class="header-logo">
        <img src="/catalog/img/catalog/sbclter-logo.gif" alt="SBC LTER" border="0" />
      </div>-->
      <div class="spacing" align="center">
        <table cellspacing="0" width="100%" border="0">
          <tr>
            <td class="header-title">
              <div class="header-title">
                @header-title@
              </div>
               <div class="header-subtitle">
                @header-subtitle@
              </div>
            </td>
            <td class="header-image" rowspan="1">
              <div class="header-image">
                <img class="header" src="@html-path@/@header-image@"
                  alt="@header-image-alt@"
                   border="0" />
              </div>
            </td>
          </tr>
          <tr>
            <td class="header-menu" colspan="2">
              <div class="header-menu">
                @header-menu@
              </div>
            </td>
          </tr>
          <tr>
            <td class="header-submenu" colspan="2">
              <div class="header-submenu">
                &#160; <!-- &nbsp; that is XML compliant -->
              </div>
            </td>
          </tr>
        </table>
      </div>
      <div class="content-spacing" align="center">
        <table cellspacing="0" width="100%" height="100%" border="0">
          <tr>
           <td class="content-area" colspan="2">
            <!-- END SBCLTER PAGE TOP HTML -->
             <div class="content-area">
               <center>
                 <h1>Project Description</h1>
                 <!--h3>Ecological Metadata Language</h3--><br />
               </center>
               <table class="tabledefault" width="100%">
               <!-- width needed for NN4 - doesn't recognize width in css -->
                 <!--xsl:apply-templates select="eml-project/identifier" mode="resource"/-->
                 <xsl:apply-templates select="eml-project/researchProject"/>
               </table>
             </div>
           </td>
          </tr>
          <tr>
            <td colspan="2">
              <!--div class="nsf" >This material is based on the upon work
              supported by the National Science Foundation under Cooperative
              Agreement #OCE-9982105. Any opinions, findings, or recommendations
              expressed in the material are those of author(s) and do not
              necessarily reflect the view of the National Science Foundation.
              </div-->
            </td>
          </tr>
          <tr>
            <td class="footer-menu" colspan="2">
              <div class="footer-menu">
                @footer-menu@
              </div>
            </td>
          </tr>
          <tr>
            <td class="footer-left">
              <div class="footer-left">
                @copyright@
              </div>
            </td>
            <td class="footer-right">
              <div class="footer-right">
               Contact: <a class="footer-link"
                        href="@mailto-address@">
                        @header-subtitle@data@nceas.ucsb.edu
                        </a> |
                <a class="footer-link"
                   href="@intranet-address@"
                   target="offline">Internal Login
                </a>
              </div>
            </td>
          </tr>
        </table>
      </div>
     </body>
    </html>
  </xsl:template>

  <xsl:template match="researchProject">
      <xsl:apply-templates select="./title" mode="resource"/>
      <tr><td class="{$subHeaderStyle}" colspan="2">Personnel:</td></tr>
      <xsl:for-each select="personnel">
          <xsl:apply-templates select="." mode="party"/>
      </xsl:for-each>
      <xsl:apply-templates select="./temporalCov"/>
      <xsl:apply-templates select="./geographicCov"/>
      <xsl:apply-templates select="./abstract" mode="resource"/>
      <xsl:apply-templates select="./funding"/>
      <xsl:apply-templates select="./siteDescription"/>
      <xsl:apply-templates select="./designDescription"/>
      <xsl:apply-templates select="./researchProject" mode="recursive"/>
  </xsl:template>

  <xsl:template match="funding">
    <tr class="{$subHeaderStyle}"><td colspan="2">
      <xsl:text>Funding:</xsl:text></td></tr>
      <xsl:call-template name="renderParagsCits"/>
  </xsl:template>

  <xsl:template match="siteDescription">
    <tr class="{$subHeaderStyle}"><td colspan="2">
      <xsl:text>Site Description:</xsl:text></td></tr>
      <xsl:call-template name="renderParagsCits"/>
  </xsl:template>


  <xsl:template match="title" mode="resource">
      <xsl:if test="normalize-space(../title)!=''">
      <tr><td width="{$firstColWidth}">
        <b>Title:</b></td><td width="{$secondColWidth}" >
        <b><xsl:value-of select="../title"/></b></td></tr>
      </xsl:if>
  </xsl:template>

  <xsl:template match="designDescription">
    <tr class="{$subHeaderStyle}"><td colspan="2">
      <xsl:text>DESIGN DESCRIPTION:</xsl:text></td></tr>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">&#160;</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">&#160;</td></tr>
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">&#160;</td>
        <td width="{$secondColWidth}" class="{$secondColStyle}">
    <table width="100%">
      <xsl:apply-templates select="protocol"/>

      <xsl:apply-templates select="sampling"/>

      <xsl:for-each select="./paragraph">
          <xsl:if test="normalize-space(./paragraph)!=''">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Additional Info:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="./paragraph"/></td></tr>
          </xsl:if>
      </xsl:for-each>

    <tr class="{$subHeaderStyle}"><td colspan="2">
      <xsl:text>Literature Citation:</xsl:text></td></tr>
      <xsl:apply-templates select="citation"/>

    </table></td></tr>
  </xsl:template>


  <xsl:template match="sampling">

    <tr class="{$subHeaderStyle}"><td colspan="2">Sampling:</td></tr>

      <xsl:if test="normalize-space(./frequency)!=''">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}" valign="top">
            Frequency</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="./frequency"/></td></tr>
      </xsl:if>
      <xsl:for-each select="./temporalCov">
          <tr><td width="{$firstColWidth}" class="{$firstColStyle}" valign="top">
          Coverage</td><td width="{$secondColWidth}" class="{$secondColStyle}">
          <table width="100%">
            <xsl:apply-templates select="."/>
          </table></td></tr>
      </xsl:for-each>

      <xsl:for-each select="./paragraph">
          <xsl:if test="normalize-space(./paragraph)!=''">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Additional Info:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="./paragraph"/></td></tr>
          </xsl:if>
      </xsl:for-each>
      <xsl:for-each select="./citation">
          <tr><td width="{$firstColWidth}" class="{$firstColStyle}" valign="top">
          Literature Citation</td><td width="{$secondColWidth}" class="{$secondColStyle}">
          <table width="100%">
            <xsl:apply-templates select="."/>
          </table></td></tr>
      </xsl:for-each>
  </xsl:template>


  <xsl:template match="researchProject" mode="recursive">
      <xsl:for-each select="../researchProject">
        <tr><td width="{$firstColWidth}" class="{$firstColStyle}" valign="top">
        Additional Project:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
        <table width="100%">
        <xsl:apply-templates select="."/>
        </table></td></tr>
      </xsl:for-each>
  </xsl:template>



<!-- these are elements we need to suppress (they are displayed by templates without a "mode=" parameter) -->

   <xsl:template match="//personnel" mode="resource"/>



</xsl:stylesheet>
