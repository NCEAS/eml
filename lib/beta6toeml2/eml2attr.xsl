<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes"/>
<xsl:output encoding="ISO-8859-1"/>
<xsl:strip-space elements="*"/>


  <xsl:template match="/">

	<!-- assign variables for input doc i.e. beta6 attribute module -->
	<xsl:variable name="attb6" select="document('att1.atb6')"/>
  
    <attributeList>
      <xsl:for-each select="$attb6/eml-attribute/attribute">
        <xsl:element name="attribute">
        
          <xsl:element name="attributeName">
            <xsl:value-of select="./attributeName"/>
          </xsl:element>
          
          <xsl:if test="$attb6/eml-attribute/attribute/attributeLabel!=''">
            <xsl:for-each select="./attributeLabel">
              <xsl:element name="attributeLabel">
                <xsl:value-of select="."/>
              </xsl:element>
            </xsl:for-each>  
          </xsl:if>
          
          <xsl:element name="attributeDefinition">
            <xsl:value-of select="./attributeDefinition"/>
          </xsl:element>
          
          <xsl:if test="$attb6/eml-attribute/attribute/dataType!=''">
            <xsl:element name="storageType">
              <xsl:value-of select="./dataType"/>
            </xsl:element>
          </xsl:if>
          
          <xsl:element name="measurementScale">
     <!-- must determine which of 5 measurementScales to create -->     
            <xsl:if test="((./attributeDomain/enumeratedDomain!='')or(./attributeDomain/textDomain!=''))">
              <xsl:if test="./dataType='Date'"> <!-- need other string checks for time, etc here -->
                <xsl:element name="datetime">
              
                </xsl:element>
              </xsl:if>
              <xsl:if test="./dataType!='Date'"> <!-- need other string checks for time, etc here -->
                <xsl:element name="nominal">
              
                </xsl:element>
              </xsl:if>
              <!-- don't see how to determine if data is ordinal !! -->
            </xsl:if>
            
            <xsl:if test="./attributeDomain/numericDomain!=''">
            <!-- must be ratio or interval -->
              <xsl:if test="((./attributeDomain/numericDomain/minimum!='')and((./attributeDomain/numericDomain/minimum)&gt;=0.0))">
                <xsl:element name="ratio">
                  <xsl:element name="unit">
                    <xsl:element name="customUnit"> <!-- maybe a standard unit? -->
                      <xsl:value-of select="./unit"/>
                    </xsl:element>
                  </xsl:element>
                  <xsl:element name="precision">
                    <xsl:value-of select="./precision"/>
                  </xsl:element>
                  <xsl:element name="numericDomain">
                    <xsl:element name="numberType">not available</xsl:element>
                    <xsl:element name="bounds">
                      <xsl:element name="minimum">
                        <xsl:value-of select="./attributeDomain/numericDomain/minimum"/>
                      </xsl:element>
                      <xsl:element name="maximum">
                        <xsl:value-of select="./attributeDomain/numericDomain/maximum"/>
                      </xsl:element>
                    </xsl:element>
                  </xsl:element>
                </xsl:element>
              </xsl:if>
              <xsl:if test="((./attributeDomain/numericDomain/minimum!='')and((./attributeDomain/numericDomain/minimum)&lt;0.0))">
                <xsl:element name="interval">
                  <xsl:element name="unit">
                    <xsl:element name="customUnit"> <!-- maybe a standard unit? -->
                      <xsl:value-of select="./unit"/>
                    </xsl:element>
                  </xsl:element>
                  <xsl:element name="precision">
                    <xsl:value-of select="./precision"/>
                  </xsl:element>
                  <xsl:element name="numericDomain">
                    <xsl:element name="numberType">not available</xsl:element>
                    <xsl:element name="bounds">
                      <xsl:element name="minimum">
                        <xsl:value-of select="./attributeDomain/numericDomain/minimum"/>
                      </xsl:element>
                      <xsl:element name="maximum">
                        <xsl:value-of select="./attributeDomain/numericDomain/maximum"/>
                      </xsl:element>
                    </xsl:element>
                  </xsl:element>
                </xsl:element>
              </xsl:if>
            </xsl:if>
          </xsl:element>
          <xsl:if test="$attb6/eml-attribute/attribute/missingValueCode!=''">
            <xsl:for-each select="./missingValueCode">
              <xsl:element name="missingValueCode">
                <xsl:element name="code">
                  <xsl:value-of select="."/>
                </xsl:element>
                <xsl:element name="codeExplanation">not available</xsl:element>
              </xsl:element>
            </xsl:for-each>  
          </xsl:if>
          
        </xsl:element><!-- end of attribute element-->
      </xsl:for-each>
    </attributeList>
  
  </xsl:template>
  
</xsl:stylesheet>  
