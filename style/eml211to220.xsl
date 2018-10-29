<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:eml="eml://ecoinformatics.org/eml-2.2.0" version="1.0">
  <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
  <!--<xsl:strip-space elements="*"></xsl:strip-space>-->
  <xsl:param name="package-id" select="'newID'"/>

  <xsl:template match="/*">
    <!--handle top level element-->
    <xsl:element name="eml:eml">
      <xsl:for-each select="@*">
        <xsl:choose>
          <xsl:when test="namespace-uri() = 'http://www.w3.org/2001/XMLSchema-instance'">
            <xsl:attribute name="xsi:{local-name()}" namespace="{namespace-uri()}">
              <xsl:variable name="value" select="."/>
              <xsl:choose>
                <!--change eml210 to eml211 in attribute-->
                <xsl:when test="contains($value, &quot;eml://ecoinformatics.org/eml-2.1.1&quot;)">
                  <xsl:variable name="first-replace">
                    <xsl:call-template name="replace-string">
                      <xsl:with-param name="text" select="$value"/>
                      <xsl:with-param name="replace" select="'eml://ecoinformatics.org/eml-2.1.1'"/>
                      <xsl:with-param name="with" select="'eml://ecoinformatics.org/eml-2.2.0'"/>
                    </xsl:call-template>
                  </xsl:variable>
                  <xsl:variable name="second-replace">
                    <xsl:call-template name="replace-string">
                      <xsl:with-param name="text" select="$first-replace"/>
                      <xsl:with-param name="replace" select="'http://www.xml-cml.org/schema/stmml-1.1'"/>
                      <xsl:with-param name="with" select="'http://www.xml-cml.org/schema/stmml-1.2'"
                      />
                    </xsl:call-template>
                  </xsl:variable>
                  <xsl:value-of select="$second-replace"/>
                </xsl:when>
                <!--change eml210 to eml211 in attribute-->
                <xsl:when test="contains($value, 'eml://ecoinformatics.org/eml-2.1.1')">
                  <xsl:variable name="first-replace">
                    <xsl:call-template name="replace-string">
                      <xsl:with-param name="text" select="$value"/>
                      <xsl:with-param name="replace" select="'eml://ecoinformatics.org/eml-2.1.1'"/>
                      <xsl:with-param name="with" select="'eml://ecoinformatics.org/eml-2.2.0'"/>
                    </xsl:call-template>
                  </xsl:variable>
                  <xsl:variable name="second-replace">
                    <xsl:call-template name="replace-string">
                      <xsl:with-param name="text" select="$first-replace"/>
                      <xsl:with-param name="replace" select="'http://www.xml-cml.org/schema/stmml-1.1'"/>
                      <xsl:with-param name="with" select="'http://www.xml-cml.org/schema/stmml-1.2'"
                      />
                    </xsl:call-template>
                  </xsl:variable>
                  <xsl:value-of select="$second-replace"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:value-of select="."/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:attribute>
          </xsl:when>
          <xsl:otherwise>
            <xsl:choose>
              <xsl:when test="name() = 'packageId'">
                <!-- handle package id: if there is no given packageid, it will use the old one. Otherwise, using the given id-->
                <xsl:attribute name="{name()}" namespace="{namespace-uri()}">
                  <xsl:choose>
                    <xsl:when test="$package-id = 'newID'">
                      <xsl:value-of select="."/>
                    </xsl:when>
                    <xsl:otherwise>
                      <xsl:value-of select="$package-id"/>
                    </xsl:otherwise>
                  </xsl:choose>
                </xsl:attribute>
              </xsl:when>
              <xsl:otherwise>
                <xsl:attribute name="{name()}" namespace="{namespace-uri()}">
                  <xsl:value-of select="."/>
                </xsl:attribute>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:for-each>

      <xsl:for-each select="/*/*">
        <xsl:choose>
          <xsl:when test="name() = 'access'">
            <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
              <xsl:copy-of select="@*"/>
              <xsl:apply-templates mode="handle-elements-under-main-module" select="."/>
            </xsl:element>
          </xsl:when>

          <xsl:when test="name() = 'dataset'">
            <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
              <xsl:copy-of select="@*"/>
              <xsl:apply-templates mode="handle-elements-under-main-module" select="."/>
            </xsl:element>
          </xsl:when>

          <xsl:when test="name() = 'citation'">
            <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
              <xsl:copy-of select="@*"/>
              <xsl:apply-templates mode="handle-elements-under-main-module" select="."/>
            </xsl:element>
          </xsl:when>

          <xsl:when test="name() = 'software'">
            <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
              <xsl:copy-of select="@*"/>
              <xsl:apply-templates mode="handle-elements-under-main-module" select="."/>
            </xsl:element>
          </xsl:when>

          <xsl:when test="name() = 'protocol'">
            <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
              <xsl:copy-of select="@*"/>
              <xsl:apply-templates mode="handle-elements-under-main-module" select="."/>
            </xsl:element>
          </xsl:when>

          <xsl:when test="name() = 'additionalMetadata'">
            <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
              
              <xsl:copy-of select="@*"/>
              <xsl:apply-templates mode="handle-elements-under-main-module" select="."/>
            </xsl:element>
          </xsl:when>

        </xsl:choose>
      </xsl:for-each>
    </xsl:element>
    <!--  
	 <xsl:message terminate="no">
		 <xsl:call-template name="output_message4_warn"/>
	  </xsl:message>
	  -->
  </xsl:template>

  <!-- handle make changes under main module (dataset, citation, protocol and software) -->
  <xsl:template mode="handle-elements-under-main-module" match="*">
    <xsl:for-each select="./*">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <!-- main template which will copy nodes recursively-->
  <xsl:template match="*">
    <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
      <xsl:copy-of select="@*"/>
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <!--Template to replace string "replace" by string "with" in given string "text"-->
  <xsl:template name="replace-string">
    <xsl:param name="text"/>
    <xsl:param name="replace"/>
    <xsl:param name="with"/>
    <xsl:choose>
      <xsl:when test="contains($text, $replace)">
        <xsl:value-of select="substring-before($text, $replace)"/>
        <xsl:value-of select="$with"/>
        <xsl:call-template name="replace-string">
          <xsl:with-param name="text" select="substring-after($text, $replace)"/>
          <xsl:with-param name="replace" select="$replace"/>
          <xsl:with-param name="with" select="$with"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$text"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- get full path of given element-->
  <xsl:template match="node()" mode="get-full-path">
    <xsl:for-each select="ancestor-or-self::*">
      <xsl:text>/</xsl:text>
      <xsl:value-of select="name()"/>
    </xsl:for-each>
  </xsl:template>

</xsl:stylesheet>
