<?xml version="1.0"?> 
<!--   
  *  '$RCSfile: eml-entity.xsl,v $'
  *      Authors: Jivka Bojilova
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: berkley $'
  *     '$Date: 2002-04-19 17:08:58 $'
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

  <xsl:output method="html" encoding="iso-8859-1"/>
  
  <xsl:param name="qformat">default</xsl:param>

  <xsl:template match="/">
    <html>       
      <head> 	
          <link rel="stylesheet" type="text/css" 
            href="/brooke/style/{$qformat}.css" />
      </head>   
      <body> 	
        <center>           
          <h2>Table structure description</h2>           
          <h4>Ecological Metadata Language</h4>         
        </center>

        <table width="750" border="0" cellspacing="0" cellpadding="1" 
               class="tablehead">
          <tr>         
            <th width="25%" align="left"><xsl:text>Table Name</xsl:text></th>
            <td><xsl:value-of select="table-entity/entityName"/></td>
          </tr>          
	</table>         

	<table width="750" border="0" cellspacing="0" cellpadding="1">
          <tr>         
            <th width="25%" align="left"><xsl:text>Metadata ID</xsl:text></th>
            <td><xsl:value-of select="table-entity/identifier"/></td>
          </tr>          
          <tr>         
            <th width="25%" align="left"><xsl:text>Table Description</xsl:text></th>
            <td><xsl:value-of select="table-entity/entityDescription"/></td>
          </tr>          
          <tr>          
            <th align="left"><xsl:text>Orientation</xsl:text></th>         
            <td><xsl:apply-templates select="table-entity/orientation"/>               
                </td>
          </tr>          
          <tr>          
            <th align="left"><xsl:text>Case Sensitive</xsl:text></th>         
            <td><xsl:apply-templates select="table-entity/caseSensitive"/>               
                </td>
          </tr>          
          <tr>          
            <th align="left"><xsl:text>Number of Records</xsl:text></th>         
            <td><xsl:apply-templates select="table-entity/numberOfRecords"/>               
                </td>
          </tr>          
<!-- Removed for now until we have a better style for displaying coverage
          <tr>          
            <th align="left"><xsl:text>Geographic Coverage</xsl:text></th>         
            <td>
		<xsl:if test="geographic_coverage/paragraph/text() or geographic_coverage/coordinates/lattitude/text() or geographic_coverage/coordinates/longitude/text()"><ul>
                <xsl:for-each select="geographic_coverage">
                <li><xsl:for-each select="paragraph">
                      <xsl:value-of select="."/><br/>
                    </xsl:for-each>                     
                    <xsl:for-each select="coordinates">                        
                      <xsl:value-of select="lattitude"/>la -                         
                      <xsl:value-of select="longitude"/>lo<br/>
                    </xsl:for-each>                 
                </li>               
                </xsl:for-each>               
                </ul></xsl:if>
                </td>
          </tr>          
-->
<!-- Removed for now until we have a better style for displaying coverage
          <tr>          
            <th align="left"><xsl:text>Temporal Coverage</xsl:text></th>         
            <td>
		<xsl:if test="temporal_coverage/*/*/*/text()"><ul>
                <xsl:for-each select="temporal_coverage">                 
                <li><xsl:text>Start Date </xsl:text>
                    <xsl:apply-templates select="start_date/datetime"/> 
                    <xsl:text> - Stop Date </xsl:text>
                    <xsl:apply-templates select="stop_date/datetime"/>
                </li>
                </xsl:for-each>
                </ul></xsl:if>
                </td>
          </tr>          
-->

        </table>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="table-entity/identifier">
    <table border="0" cellspacing="0" cellpadding="1">
      <tr>
        <td class="highlight">
          <b><xsl:text>Metadata ID:</xsl:text></b>
        </td>
        <td>
          <xsl:value-of select="."/>
        </td>
      </tr>
    </table>
  </xsl:template>

  <xsl:template match="orientation">
      <xsl:value-of select="@columnorrow"/>
  </xsl:template>

  <xsl:template match="caseSensitive">
    <xsl:choose>
      <xsl:when test="@yesorno='no'">No case sensitive fields</xsl:when>
      <xsl:when test="@yesorno='yes'">Fields are case sensitive</xsl:when>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="datetime">
    <xsl:value-of select="year"/><xsl:text>/</xsl:text>
    <xsl:value-of select="month"/><xsl:text>/</xsl:text>
    <xsl:value-of select="day"/><xsl:text> </xsl:text>
    <xsl:value-of select="hour"/><xsl:text>:</xsl:text>
    <xsl:value-of select="minute"/><xsl:text>:</xsl:text>
    <xsl:value-of select="second"/><xsl:text>:</xsl:text>
    <xsl:value-of select="second_fraction"/>
  </xsl:template>

</xsl:stylesheet> 
