<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" indent="yes"/>
<xsl:output encoding="ISO-8859-1"/>
<xsl:strip-space elements="*"/>

<xsl:variable name="projectb6" select="document('higgins.4108.1')"/>

<xsl:template match="/">
<!-- beta6 project has elements that go in both the methods and project elements in eml2 -->


<xsl:if test="($projectb6/researchProject/designDescription/protocol!='') or
              ($projectb6/researchProject/designDescription/protocol/qualityControl!='') or
              ($projectb6/researchProject/designDescription/sampling!='')">
  <xsl:element name="methods">
  <!-- in beta6, both the 'method' element and the 'protocol' element are repeatable; -->
  <!-- it is not clear which repetition corresponds to 'methodStep' in eml2!!! -->
  <!-- and we must have at least one methodStep in eml2 -->
    <xsl:element name="methodStep">
      <xsl:element name="description">
        <xsl:choose>
          <xsl:when test="$projectb6/researchProject/designDescription/protocol/method/paragraph!=''">
            <xsl:for-each select="$projectb6/researchProject/designDescription/protocol/method/paragraph">
              <xsl:element name="para">
                <xsl:value-of select="."/>
              </xsl:element>
            </xsl:for-each>
          </xsl:when>
          <xsl:otherwise>
            <xsl:element name="para">
              <xsl:value-of select="'not available'"/>
            </xsl:element>
          </xsl:otherwise>  
        </xsl:choose>
      </xsl:element>
      <xsl:for-each select="$projectb6/researchProject/designDescription/protocol/method/citation">
          <!-- insert call to citation copy template here -->
      </xsl:for-each>
    </xsl:element><!-- a single methodStep element has now been created, as required; multiple methodSteps are allowed, however-->
  <xsl:if test="$projectb6/researchProject/designDescription/protocol/method[last()>1">
    <xsl:element name="methodStep">
      <xsl:element name="description">
        <xsl:choose>
          <xsl:when test="$projectb6/researchProject/designDescription/protocol/method/paragraph!=''">
            <xsl:for-each select="$projectb6/researchProject/designDescription/protocol/method/paragraph">
              <xsl:element name="para">
                <xsl:value-of select="."/>
              </xsl:element>
            </xsl:for-each>
          </xsl:when>
          <xsl:otherwise>
            <xsl:element name="para">
              <xsl:value-of select="'not available'"/>
            </xsl:element>
          </xsl:otherwise>  
        </xsl:choose>
      </xsl:element>
      <xsl:for-each select="$projectb6/researchProject/designDescription/protocol/method/citation">
          <!-- insert call to citation copy template here -->
      </xsl:for-each>
    </xsl:element><!-- repeating methodStep as needed-->
  </xsl:if>
  
  <xsl:if test="$projectb6/researchProject/designDescription/sampling!=''">
    <xsl:element name="sampling">
      <xsl:element name="spatialExtent">
        <xsl:for-each select="$projectb6/researchProject/designDescription/sampling/temporalCov">
           <xsl:element name="coverage">
             <xsl:element name="temporalCoverage">
               <!-- insert temporal template here -->
             </xsl:element>
           </xsl:element>
        </xsl:for-each>
        <xsl:if test="$projectb6/researchProject/designDescription/sampling/temporalCov=''">
          <xsl:element name="description">
            <xsl:element name="para">
              <xsl:choose>
                <xsl:when test="$projectb6/researchProject/designDescription/sampling/frequency!=''">
                  <xsl:value-of select="$projectb6/researchProject/designDescription/sampling/frequency"/>
                  <!-- cannot find an appropriate element for frequency information in eml2 sampling!! -->
                </xsl:when>
                <xsl:otherwise>
                  <xsl:value-of select="'no information'"/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:element>
          ></xsl:element>
        </xsl:if>
      
      </xsl:element>
      <xsl:element name="samplingDescription">
        <xsl:element name="para">
          <xsl:choose>
            <xsl:when test="$projectb6/researchProject/designDescription/sampling/paragraph!=''">
              <xsl:value-of select="$projectb6/researchProject/designDescription/sampling/paragraph"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="'not available'"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:element>
      </xsl:element><!-- end of 'samplingDescription' -->
      
      <xsl:if test="$projectb6/researchProject/designDescription/sampling/citation!=''">
        <xsl:for-each select="$projectb6/researchProject/designDescription/sampling/citation">
          <xsl:element name="citation">
            <!-- call citation template here -->
          </xsl:element>
        </xsl:for-each>
      </xsl:if>
    </xsl:element><!-- end of 'sampling --> 
  </xsl:if>
    
    
  </xsl:element><!-- end ot methods element -->
</xsl:if>

<project>
  <xsl:for-each select="$projectb6/eml-project/researchProject/title">
    <xsl:element name="title">
      <xsl:value-of select="."/>
    </xsl:element>
  </xsl:for-each>

   <xsl:for-each select="$projectb6/eml-project/researchProject/personnel">
    <xsl:element name="personnel">
      <!-- responsible party info goes here -->
    </xsl:element>
   </xsl:for-each>
   
   <xsl:if test="$projectb6/eml-project/researchProject/abstract!=''">
     <xsl:element name="abstract">
       <xsl:element name="para">
         <xsl:value-of select="$projectb6/eml-project/researchProject/abstract/paragraph"/>
       </xsl:element>
     </xsl:element>
   </xsl:if>
   
   <xsl:if test="$projectb6/eml-project/researchProject/funding!=''">
     <xsl:element name="funding">
       <xsl:element name="para">
         <xsl:value-of select="$projectb6/eml-project/researchProject/funding/paragraph"/>
       </xsl:element>
     </xsl:element>
   </xsl:if>
   
</project>

</xsl:template>
</xsl:stylesheet>
