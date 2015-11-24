<?xml version="1.0"?>
<!--
       '$RCSfile: getunits.xsl,v $'
       Copyright: 1997-2002 Regents of the University of California,
                            University of New Mexico, and
                            Arizona State University
        Sponsors: National Center for Ecological Analysis and Synthesis and
                  Partnership for Interdisciplinary Studies of Coastal Oceans,
                     University of California Santa Barbara
                  Long-Term Ecological Research Network Office,
                     University of New Mexico
                  Center for Environmental Studies, Arizona State University
   Other funding: National Science Foundation (see README for details)
                  The David and Lucile Packard Foundation
     For Details: http://knb.ecoinformatics.org/

        '$Author: obrien $'
          '$Date: 2008-08-27 21:30:05 $'
      '$Revision: 1.4 $'

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:stmml="http://www.xml-cml.org/schema/stmml-1.1"
                version="1.0">
<xsl:output method="xml" indent="yes"/>

<xsl:template match="/">
<root>
  <attributeUnitType>
    <xsl:for-each select="/stmml:unitList/unit">
      <xsl:call-template name="unit"/>
    </xsl:for-each>
  </attributeUnitType>

  <lengthUnits>
    <xsl:for-each select="/stmml:unitList/unitType">
      <xsl:if test="count(./dimension) = 1">
        <xsl:if test="./dimension/@name = 'length'">
          <xsl:if test="not(boolean(./dimension/@power))">

            <!--the name of the unitType with only a length dimension-->
            <xsl:variable name="unittypename">
              <xsl:value-of select="./@name"/>
            </xsl:variable>
            <!--go through each unit looking for the base unit-->
            <xsl:for-each select="//unit">
              <xsl:if test="./@unitType = $unittypename">
                <xsl:variable name="baseunit">
                  <xsl:value-of select="./@name"/>
                </xsl:variable>
                <!--list the base unit with dimension = length-->
                <xsl:call-template name="unit"/>
                <!--go through and get each unit derived from the base unit-->
                <xsl:for-each select="//unit">
                  <xsl:if test="./@parentSI=$baseunit">
                    <xsl:call-template name="unit"/>
                  </xsl:if>
                </xsl:for-each>
              </xsl:if>
            </xsl:for-each>
          </xsl:if>
        </xsl:if>
      </xsl:if>
    </xsl:for-each>
  </lengthUnits>

  <angleUnits>
    <xsl:for-each select="/stmml:unitList/unitType">
      <xsl:if test="count(./dimension) = 1">
        <xsl:if test="./dimension/@name = 'angle'">
          <!--the name of the unitType with only a length dimension-->
          <xsl:variable name="unittypename">
            <xsl:value-of select="./@name"/>
          </xsl:variable>
          <!--go through each unit looking for the base unit-->
          <xsl:for-each select="//unit">
            <xsl:if test="./@unitType = $unittypename">
              <xsl:variable name="baseunit">
                <xsl:value-of select="./@name"/>
              </xsl:variable>
              <!--list the base unit with dimension = length-->
              <xsl:call-template name="unit"/>
              <!--go through and get each unit derived from the base unit-->
              <xsl:for-each select="//unit">
                <xsl:if test="./@parentSI=$baseunit">
                  <xsl:call-template name="unit"/>
                </xsl:if>
              </xsl:for-each>
            </xsl:if>
          </xsl:for-each>
        </xsl:if>
      </xsl:if>
    </xsl:for-each>
  </angleUnits>
</root>
</xsl:template>

<xsl:template name="unit">
  <enumeration>
    <xsl:attribute name="value">
      <xsl:value-of select="./@name"/>
    </xsl:attribute>
  </enumeration>
</xsl:template>
</xsl:stylesheet>
