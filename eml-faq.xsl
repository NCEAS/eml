<?xml version="1.0"?>
<!--
 *     '$RCSfile: eml-faq.xsl,v $'
 *     Copyright: 1997-2002 Regents of the University of California,
 *                          University of New Mexico, and
 *                          Arizona State University
 *      Sponsors: National Center for Ecological Analysis and Synthesis and
 *                Partnership for Interdisciplinary Studies of Coastal Oceans,
 *                   University of California Santa Barbara
 *                Long-Term Ecological Research Network Office,
 *                   University of New Mexico
 *                Center for Environmental Studies, Arizona State University
 * Other funding: National Science Foundation (see README for details)
 *                The David and Lucile Packard Foundation
 *   For Details: http://knb.ecoinformatics.org/
 * 
 *      '$Author: jones $'
 *        '$Date: 2002-09-06 16:01:12 $'
 *    '$Revision: 1.1 $'
 * 
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:doc="eml://ecoinformatics.org/documentation-2.0.0beta9"
                version="1.0">
  <xsl:output method="html"/>
  <xsl:template match="/">
    <html>
      <head>
        <title>EML Frequently Asked Questions</title>
        <link rel="stylesheet" type="text/css" href="default.css"/>
      </head>
      <body>
        <!-- display the module name and description -->
        <table border="0" width="100%">
        <tr valign="top"><td>
        <div class="title">EML Frequently Asked Questions</div>
        </td><td>
        <a href="index.html" class="navlink">Back to EML Contents</a>
        </td></tr>
        </table>

        <table border="0" class="tabledefault">
          <xsl:apply-templates select="//faq-item" mode="documentation"/>
        </table>
        <p class="contact">Web Contact:
          <a href="mailto:eml-dev@ecoinformatics.org">
          eml-dev@ecoinformatics.org</a>
        </p>
      </body>
    </html>
  </xsl:template>

  <!-- step through the each FAQ Item -->
  <xsl:template match="faq-item" mode="documentation">
      <tr>
        <td colspan="2" class="tablehead">
          <!--give each faq an anchor name-->
          <a class="sitelink">
            <xsl:attribute name="name">
              question-<xsl:value-of select="./@id"/>
            </xsl:attribute>
            <!-- and display the name of the element-->
            <strong>
            Question <xsl:value-of select="./@id"/>: 
            <xsl:value-of select="./question" />
            </strong>
          </a>
        </td>
      </tr>
      <xsl:apply-templates mode="documentation"/>
  </xsl:template>

  <xsl:template match="question" mode="documentation">
      <!--
      <tr valign="top">
        <td class="tablepanel">
            <strong>Question: </strong>
        </td>
        <td class="tablepanel">
            <strong><xsl:value-of select="." /></strong>
        </td>
      </tr>
      -->
  </xsl:template>

  <xsl:template match="answer" mode="documentation">
      <tr valign="top">
        <td class="tablepanel">
            Answer:
        </td>
        <td class="tablepanel">
            <xsl:value-of select="." />
        </td>
      </tr>
  </xsl:template>

  <xsl:template match="long-answer" mode="documentation">
      <tr valign="top">
        <td class="tablepanel">
            Long Answer:
        </td>
        <td class="tablepanel">
            <xsl:value-of select="." />
        </td>
      </tr>
  </xsl:template>

</xsl:stylesheet>
