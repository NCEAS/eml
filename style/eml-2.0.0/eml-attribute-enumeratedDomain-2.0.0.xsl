<?xml version="1.0"?>
<!--
  *  '$RCSfile: eml-attribute-enumeratedDomain-2.0.0.xsl,v $'
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
  <xsl:import href="eml-settings-2.0.0beta6-@name@.xsl" />
  <xsl:output method="html" encoding="iso-8859-1"/>
  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" href="{$stylePath}/@name@.css"/>
      </head>

      <body>
        <table width="100%" cellspacing="0" cellpadding="0" border="1">
          <tr>
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
                      alt="@header-image-alt@" border="0" />
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
          </tr>
          <tr>
            <td colspan="7" valign="top" align="left">
              <table width="85%" border="0">
                <tr>
                  <td class="tablehead">Code Definitions for Allowed Values</td>
                </tr>
                <tr>
                  <td>
                    <table border="0" width="95%" cellspacing="0"
                    align="center">
                      <tr>
                        <th colspan="3" valign="center" align="center" class="bordered">
                          List of Acceptable Values
                        </th>
                      </tr>
                      <tr>
                        <th class="bordered">Code</th>
                        <th class="bordered">Definition</th>
                        <th class="bordered">Source</th>
                      </tr>
                      <tr>
                        <xsl:for-each select="eml-attribute/attribute">
                          <xsl:for-each select="attributeDomain">
                            <xsl:for-each select="enumeratedDomain">
                              <xsl:variable name="stripes">
                                <xsl:choose>
                                  <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
                                  <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
                                </xsl:choose>
                              </xsl:variable>
                              <tr>
                                <td class="{$stripes}" align="center"><xsl:value-of select="code"/></td>
                                <td class="{$stripes}" align="center"><xsl:value-of select="definition"/></td>
                                <td class="{$stripes}" align="center"><xsl:value-of select="source"/></td> &#160;
                              </tr>
                            </xsl:for-each>
                          </xsl:for-each>
                        </xsl:for-each>
                      </tr>
                      <tr>
                        <td>&#160;</td>
                      </tr>
                      <tr>
                        <td>&#160;</td>
                      </tr>
                      <tr>
                        <th colspan="3" valign="center" align="center" class="bordered">Acceptable Text Values</th>
                      </tr>
                      <tr>
                        <th class="bordered">Definition</th>
                        <th class="bordered">Pattern</th>
                        <th class="bordered">Source</th>
                      </tr>
                      <tr>
                      <xsl:for-each select="eml-attribute/attribute">
                        <xsl:for-each select="attributeDomain">
                          <xsl:for-each select="textDomain">
                            <xsl:variable name="stripes">
                              <xsl:choose>
                                <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
                                <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
                              </xsl:choose>
                            </xsl:variable>
                            <tr>
                              <td class="$stripes" align="center"><xsl:value-of select="code"/></td>
                              <td class="$stripes" align="center"><xsl:value-of select="definition"/></td>
                              <td class="$stripes" align="center"><xsl:value-of select="source"/></td> &#160;
                            </tr>
                          </xsl:for-each>
                        </xsl:for-each>
                      </xsl:for-each>
                    </tr>
                  </table>
                </td>
               </tr>
             </table>
           </td>
         </tr>
       </table>
      </body>
    </html>
  </xsl:template>


  <xsl:template match="enumeratedDomain">
      <tr>
        <td><xsl:value-of select="code"/></td>
        <td><xsl:value-of select="definition"/></td>
        <td><xsl:value-of select="source"/>&#160;</td>
      </tr>
  </xsl:template>

  <xsl:template match="textDomain">
     <tr>
       <td><xsl:value-of select="definition"/></td>
       <td><xsl:value-of select="pattern"/></td>
       <td><xsl:value-of select="source"/>&#160;</td>
     </tr>
  </xsl:template>

  <xsl:template match="numericDomain">
    <tr>
      <td><xsl:value-of select="minimum"/></td>
      <td><xsl:value-of select="maximum"/></td>
    </tr>
  </xsl:template>

</xsl:stylesheet>
