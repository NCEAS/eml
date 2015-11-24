<?xml version="1.0" encoding="UTF-8"?>
<!--
  
 $Date: 2012-05-17 09:52:27 -0700 (Thu, 17 May 2012) $
 $Author: mservilla $
 $Revision: 2213 $
 
 Copyright 2011,2012 the University of New Mexico.
 
 This work was supported by National Science Foundation Cooperative
 Agreements #DEB-0832652 and #DEB-0936498.
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0.
 
 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 either express or implied. See the License for the specific
 language governing permissions and limitations under the License.
 
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:output method="xml"/>


  <!-- Match all nodes and attributes -->
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
  
  <!-- Expand each "references" element with the referenced children -->
  <xsl:template match="references">
    <xsl:variable name="ref" select="."/>
    
    <!-- Remove "id" attribute from all children nodes to be EML legal -->
    <xsl:apply-templates select="@id" mode="ref" />
    
    <xsl:copy-of select="//*[@id = $ref]/*" />
  </xsl:template>
  
  <xsl:template match="@id" mode="ref" />    
 
</xsl:stylesheet>
