<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes"/>
<xsl:output encoding="ISO-8859-1"/>
<xsl:strip-space elements="*"/>

	<xsl:variable name="enb6" select="document('ent1.enb6')"/>
	<xsl:variable name="phb6" select="document('phy1.phb6')"/>

  <xsl:template match="/">
   <entity>
    <xsl:element name="entityName">
      <xsl:value-of select="$enb6/table-entity/entityName"/>
    </xsl:element>

    <xsl:if test="$enb6/table-entity/entityDescription!=''">
      <xsl:element name="entityDescription">
        <xsl:value-of select="$enb6/table-entity/entityDescription"/>
      </xsl:element>
    </xsl:if>

     <xsl:if test="$phb6/eml-physical/!=''">
      <xsl:element name="physical">
        <xsl:element name="objectName">
          <!-- beta6 physical objects do not have names; need to pull out of triple data
            for now just insert the current id -->
          <xsl:value-of select="$phb6/eml-physical/identifier"/>
        </xsl:element>
        
        <xsl:if test="$phb6/eml-physical/size!=''">
          <xsl:element name="size">
            <xsl:value-of select="$phb6/eml-physical/size"/>
          </xsl:element>
        </xsl:if>
        
        <xsl:if test="$phb6/eml-physical/authentication!=''">
          <xsl:for-each select="$phb6/eml-physical/authentication">
            <xsl:element name="authentication">
              <xsl:value-of select="."/>
            </xsl:element>
          </xsl:for-each>  
        </xsl:if>  
        
      </xsl:element>
   </xsl:if>
      
    
   </entity>
  </xsl:template>
</xsl:stylesheet>
