<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-attribute-2.0.0.xsl,v $'
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: brooke $'
  *     '$Date: 2003-11-13 19:35:03 $'
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
<!-- IMPORTANT: IMPORT EML-RESOURCE *AFTER* EML-COVERAGE!!! -->
<xsl:import href="eml-settings-2.0.0beta6-@name@.xsl" />
<xsl:import href="eml-identifier-2.0.0beta6-@name@.xsl" />

<xsl:output method="html" encoding="iso-8859-1"/>

<xsl:template match="/">
<!-- BEGIN @header-subtitle@ PAGE TOP HTML -->
<html>
<!--
      '$RCSfile: eml-attribute-2.0.0.xsl,v $'
       Copyright: 2001 Regents of the University of California and the
                 @header-title@ LTER
      '$Author: brooke $'
      '$Date: 2003-11-13 19:35:03 $'
      '$Revision: 1.1 $'
-->
    <head>
      <title>@header-title@ LTER</title>
      <link rel="stylesheet" type="text/css"
            href="@style-path@/@name@.css" />
      <style type="text/css">
          @import url(@style-path@/@header-subtitle@-advanced.css);
      </style>
        <script language="JavaScript">
         <![CDATA[
          function submitform(action,form_ref) {
            form_ref.action.value=action;
            form_ref.abstractpath.value="";
            form_ref.qformat.value="@header-subtitle@";
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
    function openWindow(request)
          {
                window.open(request,"attr");
          }
        ]]>
      </script>
    </head>
    <body>
      <!--<div class="header-logo">
        <img src="/catalog/img/catalog/@header-subtitle@-logo.gif" alt="SBC LTER" border="0" />
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
            <!-- END @header-subtitle@ PAGE TOP HTML -->
             <div class="content-area">
  <table width="85%">
  <tr><td class="tablehead"> Attribute Definitions</td></tr><tr></tr>
  <tr><td>
        <table class="border1" cellspacing="0">
  <!-- width needed for NN4 - doesn't recognize width in css -->

        <tr><th colspan="1" valign="center" align="center" class="bordered">Columns</th>

  <xsl:for-each select="eml-attribute/attribute">
   <th colspan="1" align="center"  class="bordered"><xsl:value-of select="attributeName"/></th>
  </xsl:for-each>
  </tr>
  <tr><th colspan="1" align="center"  class="bordered" >Label(s) of the Column</th>
        <xsl:for-each select="eml-attribute/attribute">
      <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
                <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
              </xsl:choose>
            </xsl:variable>
    <td colspan="1" align="center" class="{$stripes}">
    <xsl:for-each select="attributeLabel">
      <xsl:value-of select="."/>
      &#160;<br />
    </xsl:for-each>
    </td>
  </xsl:for-each>
  </tr>
  <tr><th colspan="1" align="center"  class="bordered">Meaning</th>

  <xsl:for-each select="eml-attribute/attribute">
      <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
                <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
              </xsl:choose>
            </xsl:variable>
    <td colspan="1" align="center" class="{$stripes}">
      <xsl:value-of select="attributeDefinition"/>
    </td>
  </xsl:for-each>
  </tr>

  <tr><th colspan="1" align="center"  class="bordered">Unit</th>
        <xsl:for-each select="eml-attribute/attribute">
    <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
                <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
              </xsl:choose>
            </xsl:variable>
    <td colspan="1" align="center" class="{$stripes}">
      <xsl:value-of select="unit"/>
    </td>
  </xsl:for-each>
  </tr>

  <tr><th colspan="1" align="center"  class="bordered">Type of Value</th>
        <xsl:for-each select="eml-attribute/attribute">
  <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
                <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
              </xsl:choose>
            </xsl:variable>
    <td colspan="1" align="center" class="{$stripes}">
      <xsl:value-of select="dataType"/>
    </td>
  </xsl:for-each>
  </tr>

  <tr><th colspan="1" align="center"  class="bordered">Missing Value Code</th>
        <xsl:for-each select="eml-attribute/attribute">
     <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
                <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
              </xsl:choose>
            </xsl:variable>
    <td colspan="1" align="center" class="{$stripes}">
      <xsl:for-each select="missingValueCode">
      <xsl:value-of select="."/>
      </xsl:for-each>
    </td>
  </xsl:for-each>
  </tr>

  <tr><th colspan="1" align="center"  class="bordered">Precision &#160;</th>
        <xsl:for-each select="eml-attribute/attribute">
      <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
                <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
              </xsl:choose>
            </xsl:variable>
    <td colspan="1" align="center" class="{$stripes}">
      <xsl:value-of select="precision"/>&#160;
    </td>
  </xsl:for-each>
  </tr>

  <tr valign="middle"><th align="center" valign="middle"  class="bordered" >Allowed Values</th>
    <xsl:for-each select="eml-attribute/attribute">
       <xsl:variable name="stripes">
              <xsl:choose>
                <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
                <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
              </xsl:choose>
            </xsl:variable>
     <td class="{$stripes}">
     <xsl:for-each select="attributeDomain">
      <table width="100%" border="0" cols="2">
    <xsl:if test="count(enumeratedDomain) > 0">
    <tr><td colspan="2" align="center" width="100%"><u>List of Values</u></td></tr>
    <tr><td valign="center" align="center" colspan="2">
    <select class="{$stripes}">
    <option>--Select code--</option>
    <xsl:for-each select="enumeratedDomain">
      <xsl:variable name="id1">
      <xsl:value-of select="//identifier"/>
      </xsl:variable>
       <option onClick="openWindow('http://@server@/@context@/servlet/metacat?action=read&amp;qformat=@name@-enumeratedDomain&amp;docid={$id1}')">
                <xsl:value-of select="code"/>&#160;
       </option>
          </xsl:for-each>
    </select>
    </td></tr>
    </xsl:if>
    <xsl:if test="count(textDomain) > 0">
      <tr><td align="center" colspan="2"><u>Text Patterns</u></td></tr>
      <tr><td valign="center" align="center" colspan="2">
    <select class="{$stripes}">
    <option>--Select code--</option>
                <xsl:for-each select="textDomain">
      <xsl:variable name="id1">
      <xsl:value-of select="//identifier"/>
      </xsl:variable>
       <option onClick="openWindow('http://@server@/@context@/servlet/metacat?action=read&amp;qformat=@name@-enumeratedDomain&amp;docid={$id1}')">
      <xsl:value-of select="definition"/>&#160;
     </option>
                </xsl:for-each>
    </select>
    </td></tr>
    </xsl:if>
    <xsl:if test="count(numericDomain) > 0">
      <tr><td align="center" colspan="2"><u>Numeric</u></td></tr>
      <tr><td align="center">min:</td><td align="center">max:</td></tr>
    </xsl:if>
                <xsl:for-each select="numericDomain">
        <xsl:apply-templates select="."/>
                </xsl:for-each>
    </table>

    </xsl:for-each>
    </td>
   </xsl:for-each>
  </tr>

        </table>
  </td>
  </tr>
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
                        href="mailto:@mailto-address@">
                        @mailto-address@
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


  <xsl:template match="numericDomain">
      <tr><td align="center"><xsl:value-of select="minimum"/></td>
          <td align="center"><xsl:value-of select="maximum"/></td></tr>
  </xsl:template>

</xsl:stylesheet>
