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
        <!-- Note: size unit attribute is required in b6 but optional in 2.0 ? -->
          <size unit="{$phb6/eml-physical/size/@unit}">
            <xsl:value-of select="$phb6/eml-physical/size"/>
          </size>
        </xsl:if>
        
        <xsl:if test="$phb6/eml-physical/authentication!=''">
          <xsl:for-each select="$phb6/eml-physical/authentication">
            <xsl:element name="authentication">
              <xsl:value-of select="."/>
            </xsl:element>
          </xsl:for-each>  
        </xsl:if>  

        <xsl:if test="$phb6/eml-physical/compressionMethod!=''">
          <xsl:element name="compressionMethod">
            <xsl:value-of select="$phb6/eml-physical/compressionMethod"/>
          </xsl:element>
        </xsl:if>  

        <xsl:if test="$phb6/eml-physical/encodingMethod!=''">
          <xsl:element name="encodingMethod">
            <xsl:value-of select="$phb6/eml-physical/encodingMethod"/>
          </xsl:element>
        </xsl:if>  

        <xsl:call-template name="dataFormat">
          <xsl:with-param name="string" select="$phb6/eml-physical/format"/>
        </xsl:call-template>
        
        <!-- 'distribution information is inserted here -->
        <!-- 'coverage information is inserted here -->
        <!-- 'attribute list information is inserted here -->
        <!-- 'constraint information is inserted here -->
        
        <xsl:if test="$enb6/table-entity/caseSensitive/@yesorno!=''">
          <xsl:element name="caseSensitive">
            <xsl:value-of select="$enb6/table-entity/caseSensitive/@yesorno"/>
          </xsl:element>
        </xsl:if>  

        <xsl:if test="$enb6/table-entity/numberOfRecords!=''">
          <xsl:element name="numberOfRecords">
            <xsl:value-of select="$enb6/table-entity/numberOfRecords"/>
          </xsl:element>
        </xsl:if>  

      </xsl:element>
   </xsl:if>
      
    
   </entity>
  </xsl:template>
  
  <!-- creates the dataFormat tree of eml2.0 based on input string -->
  <xsl:template name="dataFormat">
    <xsl:param name="string"/>
    <xsl:variable name="ucstring" select="translate($string, 'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
    <xsl:choose>
      <xsl:when test="((contains($ucstring, 'STRING'))or(contains($ucstring,'TEXT'))or(contains($ucstring,'ASCI')))">
        <xsl:element name="dataFormat">
          <xsl:element name="textFormat">
            <xsl:if test="$phb6/eml-physical/numHeaderLines!=''">
              <xsl:element name="numHeaderLines">
                <xsl:value-of select="$phb6/eml-physical/numHeaderLines"/>
              </xsl:element>
            </xsl:if>  
            <xsl:if test="$phb6/eml-physical/recordDelimiter!=''">
              <xsl:element name="recordDelimiter">
                <xsl:value-of select="$phb6/eml-physical/recordDelimiter"/>
              </xsl:element>
            </xsl:if>  
            <xsl:if test="$phb6/eml-physical/maxRecordLength!=''">
              <xsl:element name="maxRecordLength">
                <xsl:value-of select="$phb6/eml-physical/maxRecordLength"/>
              </xsl:element>
            </xsl:if>
            <xsl:element name="attributeOrientation">
              <xsl:choose>
                <xsl:when test="$enb6/table-entity/orientation/@columnorrow='columnmajor'">
                  <xsl:value-of select="'column'"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:value-of select="'row'"/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:element>
            
            <xsl:if test="$phb6/eml-physical/fieldDelimiter!=''">
              <xsl:element name="simpleDelimited">
                <xsl:element name="fieldDelimiter">
                  <xsl:value-of select="$phb6/eml-physical/fieldDelimiter"/>
                </xsl:element>
                <xsl:if test="$phb6/eml-physical/quoteCharacter!=''">
                  <xsl:element name="quoteCharacter">
                    <xsl:value-of select="$phb6/eml-physical/quoteCharacter"/>
                  </xsl:element>
                </xsl:if>
                <xsl:if test="$phb6/eml-physical/literalCharacter!=''">
                  <xsl:element name="literalCharacter">
                    <xsl:value-of select="$phb6/eml-physical/literalCharacter"/>
                  </xsl:element>
                </xsl:if>
                
              </xsl:element>
            </xsl:if>

          </xsl:element>
        </xsl:element>
      </xsl:when>
      <xsl:otherwise>
        <xsl:element name="dataFormat">
          <xsl:element name="externallyDefinedFormat">
            <xsl:element name="formatName">
              <xsl:value-of select="$string"/>
            </xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
</xsl:stylesheet>
