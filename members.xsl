<?xml version="1.0"?>
<!--
 *     '$RCSfile: members.xsl,v $'
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
 *        '$Date: 2002-10-07 22:46:51 $'
 *    '$Revision: 1.4 $'
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
                version="1.0">
  <xsl:output method="html"/>
  <xsl:template match="/">
    <html>
      <head>
        <title>EML Project Members</title>
        <link rel="stylesheet" type="text/css" href="default.css"/>
      </head>
      <body>
        <!-- display the module name and description -->
        <table border="0" width="100%">
        <tr valign="top"><td>
        <div class="title">EML Project Members</div>
        </td><td>
        <a href="index.html" class="navlink">Back to EML Contents</a>
        </td></tr>
        <tr><td>
          <p>
          The EML project is an open source, community oriented project
          dedicated to providing a high-quality metadata specification
          for describing data relevant to the ecological discipline.
          The project is completely comprised of voluntary project
          members who donate their time and experience in order to advance
          information management for ecology. Project decisions are made by
          concensus according to the voting procedures described in the <a
          href="http://www.ecoinformatics.org/charter.html">ecoinformatics.org
          Charter</a>.
          </p>
          <p>
          We welcome contributions to this work in any form.  Individuals
          who invest substantial amounts of time and make valuable
          contributions to the development and maintenance of EML (in the
          opinion of current project members) will be invited to become
          EML project members according to the rules set forth in the <a
          href="http://www.ecoinformatics.org/charter.html">ecoinformatics.org
          Charter</a>. Contributions can take many forms, including the
          development of the EML schemas, writing documentation, and helping
          with maintenance, among others.
          </p>
          <p>
          Write access to the EML source code repository is reserved for
          EML project members.
          </p>
          <p> </p>
        </td></tr>
        </table>

        <table border="0" class="tabledefault">
          <xsl:apply-templates select="/memberList/member" mode="documentation">
            <xsl:sort order="ascending" select="individualName/surName" />
          </xsl:apply-templates>
        </table>
        <p class="contact">Web Contact:
          <a href="mailto:eml-dev@ecoinformatics.org">
          eml-dev@ecoinformatics.org</a>
        </p>
      </body>
    </html>
  </xsl:template>

  <!-- step through the each FAQ Item -->
  <xsl:template match="member" mode="documentation">
      <tr>
        <xsl:apply-templates mode="documentation"/>
      </tr>
  </xsl:template>

  <xsl:template match="individualName" mode="documentation">
        <td>
            <strong>
            <xsl:value-of select="givenName" />
            <xsl:text> </xsl:text>
            <xsl:value-of select="surName" />
            </strong>
        </td>
  </xsl:template>

  <xsl:template match="organizationName" mode="documentation">
        <td>
            <xsl:value-of select="."/>
        </td>
  </xsl:template>

  <xsl:template match="electronicMailAddress" mode="documentation">
        <td>
           <a>
             <xsl:attribute 
             name="href">mailto:<xsl:value-of select="." /></xsl:attribute>
            <xsl:value-of select="." />
           </a>
        </td>
  </xsl:template>

</xsl:stylesheet>
