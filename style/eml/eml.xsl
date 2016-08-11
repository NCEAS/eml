<?xml version="1.0" encoding="utf-8"?>
<!--
  *  '$RCSfile: eml.xsl,v $'
  *      Authors: Matt Jones
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: obrien $'
  *     '$Date: 2008-08-28 22:59:33 $'
  * '$Revision: 1.8 $'
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
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fn="http://www.w3.org/2005/02/xpath-function" xmlns:eml="eml://ecoinformatics.org/eml-2.1.1" version="1.0">
  <xsl:import href="emlroot.xsl"/>

  <xsl:output method="html" encoding="UTF-8"
              doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
              doctype-system="http://www.w3.org/TR/html4/loose.dtd"
              indent="yes" />

  <xsl:template match="/">
    <html>
      <head>
        <title><xsl:value-of select="./eml:eml/dataset/title"/></title>
        <link rel="stylesheet" type="text/css"
              href="{$stylePath}/{$qformat}/{$qformat}.css"></link>
        <script language="Javascript" type="text/JavaScript"
                src="{$stylePath}/{$qformat}/{$qformat}.js"></script>
        <script language="Javascript"
                type="text/JavaScript"
                src="{$styleCommonPath}/branding.js"></script>
      </head>
      <body>

        <div id="{$mainTableAligmentStyle}">
		<xsl:if test="$insertTemplate='1'">
			<script language="JavaScript" type="text/JavaScript">
				insertTemplateOpening('<xsl:value-of select="$contextURL" />');
			</script>
		</xsl:if>

          <table xsl:use-attribute-sets="cellspacing" width="100%"
                                        class="{$mainContainerTableStyle}">
			<tr>
				<td>
					<xsl:apply-templates select="*[local-name()='eml']"/>
				</td>
			</tr>		
          </table>

		<xsl:if test="$insertTemplate='1'">
			<script language="JavaScript" type="text/JavaScript">
                 insertTemplateClosing('<xsl:value-of select="$contextURL" />');
			</script>
		</xsl:if>
        </div>
      </body>
    </html>
   </xsl:template>

</xsl:stylesheet>
