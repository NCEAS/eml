<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes"/>
<xsl:output encoding="ISO-8859-1"/>
<xsl:strip-space elements="*"/>


  <xsl:template match="/">

	<!-- assign variables for input docs i.e. beta6 dataset and access -->
	<xsl:variable name="dsb6" select="document('obfs.dsb6')"/>
	<xsl:variable name="acb6" select="document('obfs.acb6')"/>
  
  <xsl:variable name="eName" select="'creator'"/>

    <eml:eml
        packageId = "{$dsb6/dataset/identifier}"
        system= "knb"
      xmlns:eml="eml://ecoinformatics.org/eml-2.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:ds="eml://ecoinformatics.org/dataset-2.0.0"
      xmlns:stmml="http://www.xml-cml.org/schema/stmml"
      xsi:schemaLocation="eml://ecoinformatics.org/eml-2.0.0 eml.xsd"> 

      <dataset>
        <xsl:if test="$dsb6/dataset/shortName!=''">
          <xsl:element name="shortName">
            <xsl:value-of select="$dsb6/dataset/shortName"/>
          </xsl:element>
        </xsl:if>  
        <xsl:if test="$dsb6/dataset/title!=''">
          <xsl:element name="title">
            <xsl:value-of select="$dsb6/dataset/title"/>
          </xsl:element>
        </xsl:if> 
        
        <!-- eml2 has a 'creator' tag with 'ResponsibleParty' content while           -->
        <!-- beta6 has an 'originator' tag with a 'role' element                      -->
        <!-- eml2 also has separate elements for metadataProvider and associatedParty -->
        <!-- use the 'role' value from emlb6 to detemine where info goes in eml2      -->
        <xsl:for-each select="$dsb6/dataset/originator/role">
          <xsl:if test="((.='Originator')or(.='originator'))">
            <xsl:element name="creator">
              <xsl:call-template name="responsibleParty"/>
            </xsl:element>  
          </xsl:if>
        </xsl:for-each>
          
        <xsl:for-each select="$dsb6/dataset/originator/role">
          <xsl:if test="(.='Metadata Provider')">
            <xsl:element name="metadataProvider">
              <xsl:call-template name="responsibleParty"/>
            </xsl:element>
          </xsl:if>
        </xsl:for-each>

          <xsl:for-each select="$dsb6/dataset/originator/role">
          <xsl:if test="((.!='Metadata Provider')and(.!='Originator'))">
            <xsl:element name="associatedParty">
              <xsl:call-template name="responsibleParty"/>
              <xsl:element name="role">
                <xsl:value-of select="."/>
              </xsl:element>
            </xsl:element>
          </xsl:if>
          </xsl:for-each>
          <xsl:if test="$dsb6/dataset/pubDate!=''">
            <xsl:element name="pubDate">
              <xsl:value-of select="$dsb6/dataset/pubDate"/>
            </xsl:element>
          </xsl:if>
    <!-- Note - b6 has a 'pubPlace' element that occurs later in eml2 -->      


          <xsl:if test="$dsb6/dataset/series!=''">
            <xsl:element name="series">
              <xsl:value-of select="$dsb6/dataset/series"/>
            </xsl:element>
          </xsl:if>

          <xsl:if test="$dsb6/dataset/abstract!=''">
            <xsl:element name="abstract">
              <xsl:for-each select="$dsb6/dataset/abstract/paragraph">
                <xsl:element name="para">
                  <xsl:value-of select="."/>
                </xsl:element>
              </xsl:for-each>  
            </xsl:element>
          </xsl:if>

          <xsl:if test="$dsb6/dataset/keywordSet!=''">
            <xsl:element name="keywordSet">
              <xsl:for-each select="$dsb6/dataset/keywordSet/keyword">
                <keyword keywordType="{./@keywordType}">
                  <xsl:value-of select="."/>
                </keyword>  
              </xsl:for-each>
              <xsl:if test="$dsb6/dataset/keywordSet/keywordThesaurus!=''">
                <xsl:element name="keywordThesaurus">
                  <xsl:value-of select="."/>
                </xsl:element>
              </xsl:if>
            </xsl:element>
          </xsl:if>


          
          <xsl:if test="$dsb6/dataset/additionalInfo!=''">
            <xsl:element name="additionalInfo">
              <xsl:for-each select="$dsb6/dataset/additionalInfo/paragraph">
                <xsl:element name="para">
                  <xsl:value-of select="."/>
                </xsl:element>
              </xsl:for-each>  
            </xsl:element>
          </xsl:if>

          <xsl:if test="$dsb6/dataset/rights!=''">
            <xsl:element name="intellectualRights">
              <xsl:for-each select="$dsb6/dataset/rights/paragraph">
                <xsl:element name="para">
                  <xsl:value-of select="."/>
                </xsl:element>
              </xsl:for-each>  
            </xsl:element>
          </xsl:if>
          
          <xsl:if test="(($dsb6/dataset/onlineURL!='')or($dsb6/dataset/offlineMedium!=''))">
            <xsl:if test="($dsb6/dataset/onlineURL!='')">
              <xsl:element name="distribution">
                <xsl:element name="online">
                  <xsl:element name="url">
                    <xsl:value-of select="$dsb6/dataset/onlineURL/url"/>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
            </xsl:if>
          <xsl:if test="($dsb6/dataset/offlineMedium!='')">
              <xsl:element name="distribution">
                <xsl:element name="offline">
                  <xsl:if test="$dsb6/dataset/offlineMedium/medName!=''"> 
                    <xsl:element name="mediumName">
                      <xsl:value-of select="$dsb6/dataset/offlineMedium/medName"/>
                    </xsl:element>
                  </xsl:if>
                  <xsl:if test="$dsb6/dataset/offlineMedium/medDensity!=''"> 
                    <xsl:element name="mediumDensity">
                      <xsl:value-of select="$dsb6/dataset/offlineMedium/medDensity"/>
                    </xsl:element>
                  </xsl:if>
                  <xsl:if test="$dsb6/dataset/offlineMedium/medDensityUnits!=''"> 
                    <xsl:element name="mediumDensityUnits">
                      <xsl:value-of select="$dsb6/dataset/offlineMedium/medDensityUnits"/>
                    </xsl:element>
                  </xsl:if>
                  <xsl:if test="$dsb6/dataset/offlineMedium/medVolume!=''"> 
                    <xsl:element name="mediumVolume">
                      <xsl:value-of select="$dsb6/dataset/offlineMedium/medVolume"/>
                    </xsl:element>
                  </xsl:if>
                  <xsl:if test="$dsb6/dataset/offlineMedium/medFormat!=''"> 
                    <xsl:element name="mediumFormat">
                      <xsl:value-of select="$dsb6/dataset/offlineMedium/medFormat"/>
                    </xsl:element>
                  </xsl:if>
                  <xsl:if test="$dsb6/dataset/offlineMedium/medNote!=''"> 
                    <xsl:element name="mediumNote">
                      <xsl:value-of select="$dsb6/dataset/offlineMedium/medNote"/>
                    </xsl:element>
                  </xsl:if>
                </xsl:element>
              </xsl:element>
            </xsl:if>
          </xsl:if>

          
        <xsl:if test="(($dsb6/dataset/temporalCov!='')or($dsb6/dataset/geographicCov!='')or($dsb6/dataset/taxonomicCov!=''))">
          <xsl:element name="coverage">
            
            <xsl:if test="$dsb6/dataset/geographicCov!=''">            
              <xsl:element name="geographicCoverage">
                <xsl:for-each select="$dsb6/dataset/geographicCov">
                  <xsl:call-template name="geographicCoverage"/>
                </xsl:for-each>
              </xsl:element> 
            </xsl:if>

            <xsl:if test="$dsb6/dataset/temporalCov!=''">
              <xsl:element name="temporalCoverage">
                <xsl:for-each select="$dsb6/dataset/temporalCov">
                  <xsl:call-template name="temporalCoverage"/>
                </xsl:for-each>
              </xsl:element> 
            </xsl:if>
            
            <xsl:if test="$dsb6/dataset/taxonomicCov!=''">
              <xsl:element name="taxonomicCoverage">
                <xsl:for-each select="$dsb6/dataset/taxonomicCov">
                  <xsl:call-template name="taxonomicCoverage"/>
                </xsl:for-each>
              </xsl:element> 
            </xsl:if>
          </xsl:element> 
        </xsl:if>  
         
        <xsl:for-each select="$dsb6/dataset/originator/role">
          <xsl:if test="(contains(.,'ontact'))">
            <xsl:element name="contact">
              <xsl:call-template name="responsibleParty"/>
            </xsl:element>
          </xsl:if>
        </xsl:for-each>

          <xsl:if test="$dsb6/dataset/pubPlace!=''">
            <xsl:element name="pubPlace">
              <xsl:value-of select="$dsb6/dataset/pubPlace"/>
            </xsl:element>
          </xsl:if>
          
          <access
            order="{$acb6/acl/@order}"
            authSystem="{$acb6/acl/@authSystem}" >
            <xsl:for-each select="$acb6/acl/*">
              <xsl:if test="name(.)='allow'">
                <xsl:element name="allow">
                  <xsl:element name="principal">
                    <xsl:value-of select="./principal"/>
                  </xsl:element>
                  <xsl:element name="permission">
                    <xsl:value-of select="./permission"/>
                  </xsl:element>
                </xsl:element>
              </xsl:if>  
              <xsl:if test="name(.)='deny'">
                <xsl:element name="deny">
                    <xsl:element name="principal">
                      <xsl:value-of select="./principal"/>
                    </xsl:element>
                    <xsl:element name="permission">
                    <xsl:value-of select="../deny/permission"/>
                  </xsl:element>
                </xsl:element>
              </xsl:if>
          </xsl:for-each>
          </access>
            
          </dataset>

    </eml:eml>
	</xsl:template>
  
  <!--templates to be called from main template              -->
  <xsl:template name="responsibleParty">
              <xsl:if test="../individualName!=''">
                <xsl:element name="individualName">
                  <xsl:if test="../individualName/salutation!=''">
                    <xsl:element name="salutation">
                      <xsl:value-of select="../individualName/salutation"/>
                    </xsl:element>
                  </xsl:if>
                  <xsl:if test="../individualName/givenName!=''">
                    <xsl:element name="givenName">
                      <xsl:value-of select="../individualName/givenName"/>
                    </xsl:element>
                  </xsl:if>
                  <xsl:if test="../individualName/surName!=''">
                    <xsl:element name="surName">
                      <xsl:value-of select="../individualName/surName"/>
                    </xsl:element>
                  </xsl:if>
                </xsl:element>
              </xsl:if>
                
                <xsl:if test="../organizationName!=''">
                  <xsl:element name="organizationName">
                   <xsl:value-of select="../organizationName"/>
                  </xsl:element>
                </xsl:if>

                <xsl:if test="../positionName!=''">
                  <xsl:element name="positionName">
                   <xsl:value-of select="../positionName"/>
                  </xsl:element>
                </xsl:if>

                <xsl:if test="../address!=''">
                  <xsl:element name="address">
                    <xsl:if test="../address/deliveryPoint!=''">
                      <xsl:element name="deliveryPoint">
                        <xsl:value-of select="../address/deliveryPoint"/>
                      </xsl:element>
                    </xsl:if>

                    <xsl:if test="../address/city!=''">
                      <xsl:element name="city">
                        <xsl:value-of select="../address/city"/>
                      </xsl:element>
                    </xsl:if>

                    <xsl:if test="../address/administrativeArea!=''">
                      <xsl:element name="administrativeArea">
                        <xsl:value-of select="../address/administrativeArea"/>
                      </xsl:element>
                    </xsl:if>

                    <xsl:if test="../address/postalCode!=''">
                      <xsl:element name="postalCode">
                        <xsl:value-of select="../address/postalCode"/>
                      </xsl:element>
                    </xsl:if>

                    <xsl:if test="../address/country!=''">
                      <xsl:element name="country">
                        <xsl:value-of select="../address/country"/>
                      </xsl:element>
                    </xsl:if>
                  </xsl:element>
                </xsl:if>

                <xsl:if test="../phone!=''">
                  <xsl:element name="phone">
                   <xsl:value-of select="../phone"/>
                  </xsl:element>
                </xsl:if>

                <xsl:if test="../electronicMailAddress!=''">
                  <xsl:element name="electronicMailAddress">
                   <xsl:value-of select="../electronicMailAddress"/>
                  </xsl:element>
                </xsl:if>
              
                <xsl:if test="../onlineLink!=''">
                  <xsl:element name="onLineUrl">
                   <xsl:value-of select="../onlineLink"/>
                  </xsl:element>
                </xsl:if>
  </xsl:template>

<!-- temporal coverage template -->
  <xsl:template name="temporalCoverage">
    <xsl:if test="./sngdate!=''">
      <xsl:element name="singleDateTime">
        <xsl:element name="calendarDate">
           <xsl:call-template name="convertDate">
             <xsl:with-param name="string" select="./sngdate/caldate"/>
           </xsl:call-template>  
        </xsl:element>
        <xsl:if test="./sngdate/time!=''">
          <xsl:element name="time">
            <xsl:value-of select="./sngdate/time"/>
          </xsl:element>
        </xsl:if>  
      </xsl:element>
    </xsl:if>
   <xsl:if test="./mdattim!=''">
     <xsl:for-each select="./mdattim/*">
       <xsl:element name="singleDateTime">
          <xsl:element name="calendarDate">
           <xsl:call-template name="convertDate">
             <xsl:with-param name="string" select="./sngdate/caldate"/>
           </xsl:call-template>  
          </xsl:element>
          <xsl:if test="./sngdate/time!=''">
            <xsl:element name="time">
              <xsl:value-of select="./sngdate/time"/>
            </xsl:element>
            </xsl:if>
       </xsl:element>
     </xsl:for-each>
   </xsl:if>
   <xsl:if test="./rngdates!=''">
     <xsl:element name="rangeOfDates">
       <xsl:element name="beginDate">
         <xsl:element name="calendarDate">
           <xsl:call-template name="convertDate">
             <xsl:with-param name="string" select="./rngdates/begdate"/>
           </xsl:call-template>  
         </xsl:element>
         <xsl:if test="./rngdates/time!=''">
           <xsl:element name="time">
             <xsl:value-of select="./rngdates/begtime"/>
           </xsl:element>
         </xsl:if>  
       </xsl:element>
       <xsl:element name="endDate">
         <xsl:element name="calendarDate">
            <xsl:call-template name="convertDate">
             <xsl:with-param name="string" select="./rngdates/enddate"/>
           </xsl:call-template>  
         </xsl:element>
         <xsl:if test="./rngdates/time!=''">
           <xsl:element name="time">
             <xsl:value-of select="./rngdates/endtime"/>        
           </xsl:element>
         </xsl:if>
       </xsl:element>
     </xsl:element>
   </xsl:if>    
   </xsl:template>

  <!-- geographic coverage template -->
  <xsl:template name="geographicCoverage">
    <xsl:if test="./descgeog!=''">
      <xsl:element name="geographicDescription">
        <xsl:value-of select="./descgeog"/>
      </xsl:element>
    </xsl:if>
    <xsl:if test="./descgeog=''">
      <xsl:element name="geographicDescription">
        *
      </xsl:element>
    </xsl:if>
    <xsl:if test="./bounding!=''">
      <xsl:element name="boundingCoordinates">
        <xsl:element name="westBoundingCoordinate">
          <xsl:value-of select="./bounding/westbc"/>
        </xsl:element>
        <xsl:element name="eastBoundingCoordinate">
          <xsl:value-of select="./bounding/eastbc"/>
        </xsl:element>
        <xsl:element name="northBoundingCoordinate">
          <xsl:value-of select="./bounding/northbc"/>
        </xsl:element>
        <xsl:element name="southBoundingCoordinate">
          <xsl:value-of select="./bounding/southbc"/>
        </xsl:element>
      </xsl:element>
    </xsl:if>
  </xsl:template>

   
  <!-- taxonomic coverage template -->
  <xsl:template name="taxonomicCoverage">
    <xsl:if test="./taxoncl!=''">
      <xsl:for-each select="./taxoncl">
        <xsl:element name="taxonomicClassification">
          <xsl:element name="taxonRankName">
            <xsl:value-of select="./taxonrn"/>
          </xsl:element>
          <xsl:element name="taxonRankValue">
            <xsl:value-of select="./taxonrv"/>
          </xsl:element>
          <xsl:element name="commonName">
            <xsl:value-of select="./common"/>
          </xsl:element>
          <!-- need a recursive call here for another taxonomicClassification -->
        </xsl:element>
      </xsl:for-each>
    </xsl:if>
  </xsl:template>
  
  <!-- convert date format - assumes input in form ddMonthYear-->
  <!-- this routine only works for strings in the form above  -->
  <!-- need to add '0' for days less than 10; other month names -->
  <xsl:template name="convertDate">
    <xsl:param name="string"/>
    <xsl:variable name="ucstring" select="translate($string, 'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
    <xsl:if test="contains($ucstring,'-')">
      <xsl:value-of select="$string"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'JAN')">
      <xsl:value-of select="concat(substring-after($ucstring,'JAN'),'-01-',substring-before($ucstring,'JAN'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'FEB')">
      <xsl:value-of select="concat(substring-after($ucstring,'FEB'),'-02-',substring-before($ucstring,'FEB'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'MAR')">
      <xsl:value-of select="concat(substring-after($ucstring,'MAR'),'-03-',substring-before($ucstring,'MAR'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'APR')">
      <xsl:value-of select="concat(substring-after($ucstring,'APR'),'-04-',substring-before($ucstring,'APR'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'MAY')">
      <xsl:value-of select="concat(substring-after($ucstring,'MAY'),'-05-',substring-before($ucstring,'MAY'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'JUN')">
      <xsl:value-of select="concat(substring-after($ucstring,'JUN'),'-06-',substring-before($ucstring,'JUN'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'JUNE')">
      <xsl:value-of select="concat(substring-after($ucstring,'JUNE'),'-06-',substring-before($ucstring,'JUNE'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'JUL')">
      <xsl:value-of select="concat(substring-after($ucstring,'JUL'),'-07-',substring-before($ucstring,'JUL'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'JULY')">
      <xsl:value-of select="concat(substring-after($ucstring,'JULY'),'-07-',substring-before($ucstring,'JULY'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'AUG')">
      <xsl:value-of select="concat(substring-after($ucstring,'AUG'),'-08-',substring-before($ucstring,'AUG'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'SEP')">
      <xsl:value-of select="concat(substring-after($ucstring,'SEP'),'-09-',substring-before($ucstring,'SEP'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'OCT')">
      <xsl:value-of select="concat(substring-after($ucstring,'OCT'),'-10-',substring-before($ucstring,'OCT'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'NOV')">
      <xsl:value-of select="concat(substring-after($ucstring,'NOV'),'-11-',substring-before($ucstring,'NOV'))"/>
    </xsl:if>
    <xsl:if test="contains($ucstring,'DEC')">
      <xsl:value-of select="concat(substring-after($ucstring,'DEC'),'-12-',substring-before($ucstring,'DEC'))"/>
    </xsl:if>
  </xsl:template>

  
</xsl:stylesheet>
