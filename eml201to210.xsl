<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
  xmlns:eml="eml://ecoinformatics.org/eml-2.1.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:output method="xml" indent="yes"></xsl:output>
  <!--<xsl:strip-space elements="*"></xsl:strip-space> -->

  <xsl:template match="/* ">
    <!--handle top level element-->
    <xsl:element name="eml:eml">
      <xsl:copy-of select="@*"></xsl:copy-of>
      <xsl:attribute name="xsi:schemaLocation">eml://ecoinformatics.org/eml-2.1.0 ../eml.xsd</xsl:attribute>

      <!-- move the access that is sub tree of the resource to the top level -->
      <xsl:apply-templates mode="copy-top-access-tree" select="/*/dataset/access"></xsl:apply-templates>
      <xsl:apply-templates mode="copy-top-access-tree" select="/*/citation/access"></xsl:apply-templates>
      <xsl:apply-templates mode="copy-top-access-tree" select="/*/software/access"></xsl:apply-templates>
      <xsl:apply-templates mode="copy-top-access-tree" select="/*/protocol/access"></xsl:apply-templates>

      <xsl:for-each select="/*/*">
        <xsl:choose>
          <xsl:when test="name()='dataset'">
            <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
              <xsl:copy-of select="@*"></xsl:copy-of>
              <xsl:apply-templates mode="handle-elements-under-main-module" select="."
              ></xsl:apply-templates>
            </xsl:element>
          </xsl:when>

          <xsl:when test="name()='citation'">
            <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
              <xsl:copy-of select="@*"></xsl:copy-of>
              <xsl:apply-templates mode="handle-elements-under-main-module" select="."
              ></xsl:apply-templates>
            </xsl:element>
          </xsl:when>

          <xsl:when test="name()='software'">
            <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
              <xsl:copy-of select="@*"></xsl:copy-of>
              <xsl:apply-templates mode="handle-elements-under-main-module" select="."
              ></xsl:apply-templates>
            </xsl:element>
          </xsl:when>

          <xsl:when test="name()='protocol'">
            <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
              <xsl:copy-of select="@*"></xsl:copy-of>
              <xsl:apply-templates mode="handle-elements-under-main-module" select="."
              ></xsl:apply-templates>
            </xsl:element>
          </xsl:when>

          <xsl:when test="name()='additionalMetadata'">
            <!--    <xsl:apply-templates mode="handle-additionalMetadata-TEST" select="."
            ></xsl:apply-templates> -->
            <xsl:call-template name="handle-additionalMetadata-TEST"></xsl:call-template>
          </xsl:when>

        </xsl:choose>
      </xsl:for-each>
    </xsl:element>
  </xsl:template>

  <!-- handle make changes under main module (dataset, citation, protocol and software) -->
  <xsl:template mode="handle-elements-under-main-module" match="*">
    <xsl:for-each select="./*">
      <xsl:choose>
        <xsl:when test="name()='access'">
          <xsl:apply-templates mode="do-nothing" select="."></xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="."></xsl:apply-templates>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:for-each>
  </xsl:template>

  <!-- main template which will copy nodes recursively-->
  <xsl:template match="*">
    <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
      <xsl:copy-of select="@*"></xsl:copy-of>
      <xsl:apply-templates></xsl:apply-templates>
    </xsl:element>
  </xsl:template>

  <!-- fixing attributeList/attribute/measurementScale/datetime -> .../dateTime -->
  <xsl:template match="attributeList/attribute/measurementScale/datetime">
    <xsl:element name="dateTime" namespace="{namespace-uri(.)}">
      <xsl:copy-of select="@*"></xsl:copy-of>
      <xsl:apply-templates mode="copy-no-ns" select="./*"></xsl:apply-templates>
    </xsl:element>
  </xsl:template>

  <!-- change the name of element from method to methods -->
  <xsl:template match="dataTable/method">
    <xsl:element name="methods" namespace="{namespace-uri(.)}">
      <xsl:copy-of select="@*"></xsl:copy-of>
      <xsl:apply-templates mode="copy-no-ns" select="./*"></xsl:apply-templates>
    </xsl:element>
  </xsl:template>

  <!-- change the name of element from method to methods -->
  <xsl:template match="spatialRaster/method">
    <xsl:element name="methods" namespace="{namespace-uri(.)}">
      <xsl:copy-of select="@*"></xsl:copy-of>
      <xsl:apply-templates mode="copy-no-ns" select="./*"></xsl:apply-templates>
    </xsl:element>
  </xsl:template>

  <!-- change the name of element from method to methods -->
  <xsl:template match="spatialVector/method">
    <xsl:element name="methods" namespace="{namespace-uri(.)}">
      <xsl:copy-of select="@*"></xsl:copy-of>
      <xsl:apply-templates mode="copy-no-ns" select="./*"></xsl:apply-templates>
    </xsl:element>
  </xsl:template>

  <!-- change the name of element from method to methods -->
  <xsl:template match="view/method">
    <xsl:element name="methods" namespace="{namespace-uri(.)}">
      <xsl:copy-of select="@*"></xsl:copy-of>
      <xsl:apply-templates mode="copy-no-ns" select="./*"></xsl:apply-templates>
    </xsl:element>
  </xsl:template>

  <!-- change the name of element from method to methods -->
  <xsl:template match="storedProcedure/method">
    <xsl:element name="methods" namespace="{namespace-uri(.)}">
      <xsl:copy-of select="@*"></xsl:copy-of>
      <xsl:apply-templates mode="copy-no-ns" select="./*"></xsl:apply-templates>
    </xsl:element>
  </xsl:template>

  <!-- change the name of element from method to methods -->
  <xsl:template match="otherEntity/method">
    <xsl:element name="methods" namespace="{namespace-uri(.)}">
      <xsl:copy-of select="@*"></xsl:copy-of>
      <xsl:apply-templates mode="copy-no-ns" select="./*"></xsl:apply-templates>
    </xsl:element>
  </xsl:template>




  <!-- copy access tree under dataset(or protocol, software and citation) to the top level -->
  <xsl:template mode="copy-top-access-tree" match="*">
    <xsl:apply-templates mode="copy-no-ns" select="."></xsl:apply-templates>
  </xsl:template>

  <!-- do nothing for this element (removing it)-->
  <xsl:template mode="do-nothing" match="*"> </xsl:template>

  <!-- copy node and children without namespace -->
  <xsl:template mode="copy-no-ns" match="*">
    <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
      <xsl:copy-of select="@*"></xsl:copy-of>
      <xsl:apply-templates mode="copy-no-ns"></xsl:apply-templates>
    </xsl:element>
  </xsl:template>


  <!--  
  handle additionalMetadata. 
  for content that is an access tree, 4 things can happen, in combinations:a+b, a+c
 a. additionalMetadata/access is coped to a distribution/access
 b. additionalMetadata/describes is deleted
 c. entire additionalMetadata section is deleted 
 or
 d. additionalMetadata section not touched. default treatment (add warning or msg as needed, see below)

for all other content
default: add the <metadata> element - if it isnt already there.
  -->
  <!-- 
    These scenarios need warnings - default action
  1.  <access> without a <describes> sibling (or auntie)  = warning: doc-level access belongs elsewhere
  2.  referenced distribution already has an access. leave this one here
     -->

  <!-- 
this template is the logic, and calls other templates to do the actual work. -->
  <xsl:template name="handle-additionalMetadata-TEST">
    <xsl:choose>
      <!-- test if this additionalMetadata section has a complete EML access tree -->
      <xsl:when
        test="./descendant::access[ allow/principal and allow/permission]  or
        ./descendant::access[ deny/principal and deny/permission]  ">
        <!-- it has an EML access tree -->
        <xsl:choose>
          <!--  test it has a describes element -->
          <xsl:when test="describes">
            <!--  it has a describes -->
            <!-- look at each describes. if content matches a physical/distribution or 
              software/implementation/idstribution, then copy access tree there and remove the describes. 
              -->
            <xsl:for-each select="describes">
              <xsl:variable name="describes-content">
                <xsl:value-of select="."></xsl:value-of>
              </xsl:variable>
              <xsl:text>test -------------% </xsl:text>
              <xsl:value-of select="$describes-content"></xsl:value-of>
              <xsl:text> %---------------test                         </xsl:text>
              <!-- look for a matching distribution id -->
              <!-- call a template that matches physical/distribution, pass the variable $describes-content
                  2 checks: 
                    1. distribution node does not already have an access tree, 
                    2. its id content matches the $describes-content 
                  then copy the describes sibling::access or sibling::metadata[child::access] -->




            </xsl:for-each>



          </xsl:when>
          <xsl:otherwise>
            <!-- no describes, cant copy the access tree. default action, set warning2 -->
            <xsl:call-template name="default-additionalMetadata-add-metadata-element"></xsl:call-template>
            <!-- is this a with-param?  -->
            <xsl:variable name="warning2">true</xsl:variable>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <!--  this is not EML markup, default action  -->
        <xsl:call-template name="default-additionalMetadata-add-metadata-element"
        ></xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- 
    
default action for additionalMetadata:
wrap the content (except <describes>) in <metadata> tags  unless it has been done already -->
  <xsl:template name="default-additionalMetadata-add-metadata-element" match="additionalMetadata">
    <additionalMetadata>
      <xsl:for-each select="./*">
        <xsl:choose>
          <xsl:when test="name()='describes'">
            <xsl:apply-templates mode="copy-no-ns" select="."></xsl:apply-templates>
          </xsl:when>
          <xsl:when test="name()='metadata'">
            <xsl:apply-templates mode="copy-no-ns" select="."></xsl:apply-templates>
          </xsl:when>
          <xsl:otherwise>
            <metadata>
              <xsl:apply-templates mode="copy-no-ns" select="."></xsl:apply-templates>
            </metadata>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:for-each>
    </additionalMetadata>
  </xsl:template>


<xsl:template name="copy-access-tree" match="physical/distribution|software/implementation/distribution">
  
  
</xsl:template>


</xsl:stylesheet>
