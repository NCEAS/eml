<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-entity-2.0.0.xsl,v $'
  *      Authors: Jivka Bojilova
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
  * convert an XML file that is valid with respect to the eml-file.dtd
  * module of the Ecological Metadata Language (EML) into an HTML format
  * suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:import href="eml-identifier-2.0.0beta6-@name@.xsl" />
  <xsl:import href="eml-coverage-2.0.0beta6-@name@.xsl" />

  <xsl:output method="html" encoding="iso-8859-1"/>

  <xsl:template match="/">
  <html>
    <head>
        <link rel="stylesheet" type="text/css" href="{$stylePath}/@name@.css" />
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
                 <h1>Table structure description</h1>
                 <!--h3>Ecological Metadata Language</h3-->
               </center>
               <table class="tabledefault" width="100%">
               <!-- width needed for NN4 - doesn't recognize width in css -->
                 <!--xsl:apply-templates select="table-entity/identifier" mode="resource"/-->
                 <tr class="{$subHeaderStyle}">
                   <td class="{$subHeaderStyle}" colspan="2">Table Information:</td>
                 </tr>
                 <xsl:apply-templates select="table-entity/entityName"/>
                 <xsl:apply-templates select="table-entity/entityDescription"/>
                 <xsl:apply-templates select="table-entity/caseSensitive"/>
                 <xsl:apply-templates select="table-entity/numberOfRecords"/>
                 <xsl:apply-templates select="table-entity/orientation"/>
                 <xsl:if test="table-entity/temporalCov">
                   <xsl:apply-templates select="table-entity/temporalCov"/>
                 </xsl:if>
                 <xsl:if test="table-entity/geographicCov">
                   <xsl:apply-templates select="table-entity/geographicCov"/>
                 </xsl:if>
                 <xsl:if test="table-entity/taxonomicCov">
                   <xsl:apply-templates select="table-entity/taxonomicCov"/>
                 </xsl:if>
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

  <xsl:template match="entityName">
    <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
    Name:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
    <b><xsl:value-of select="."/></b></td></tr>
  </xsl:template>

  <xsl:template match="entityDescription">
        <xsl:if test="../entityDescription and normalize-space(../entityDescription)!=''">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Description:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="."/></td></tr>
        </xsl:if>
  </xsl:template>

  <xsl:template match="caseSensitive">
        <xsl:if test="../caseSensitive and normalize-space(../caseSensitive/@yesorno)!=''">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Case Sensitive?:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="./@yesorno"/></td></tr>
        </xsl:if>
  </xsl:template>

  <xsl:template match="numberOfRecords">
        <xsl:if test="../numberOfRecords and normalize-space(../numberOfRecords)!=''">
            <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
            Number Of Records:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
            <xsl:value-of select="."/></td></tr>
        </xsl:if>
  </xsl:template>

  <xsl:template match="orientation">
    <xsl:if test="(../orientation)">
      <tr><td width="{$firstColWidth}" class="{$firstColStyle}">
          Orientation:</td><td width="{$secondColWidth}" class="{$secondColStyle}">
      <xsl:if test="normalize-space(@columnorrow)='columnmajor'">
        <xsl:text>Column-major</xsl:text>
      </xsl:if>
      <xsl:if test="normalize-space(@columnorrow)='rownmajor'">
        <xsl:text>Row-major</xsl:text>
      </xsl:if></td></tr>
    </xsl:if>
  </xsl:template>

</xsl:stylesheet>
