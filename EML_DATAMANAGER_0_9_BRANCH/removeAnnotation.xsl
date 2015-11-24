<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xs="http://www.w3.org/2001/XMLSchema" 
                version="1.0">

  <xsl:output method="xml" indent="yes"/>

  <xsl:strip-space elements="*"/>
  
  <xsl:template match="xs:annotation"/>
  
  <xsl:template match="*">
   <xsl:copy>
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates/>
   </xsl:copy>
  </xsl:template>

</xsl:stylesheet>
