<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >	
	<xsl:output method="xml"/>	
	<xsl:template match="/">		
		<eml:eml packageId="eml.1.1" system="knb" xmlns:eml="eml://ecoinformatics.org/eml-2.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ds="eml://ecoinformatics.org/dataset-2.0.0" xmlns:stmml="http://www.xml-cml.org/schema/stmml" xsi:schemaLocation="eml://ecoinformatics.org/eml-2.0.0 eml.xsd">			
			<dataset>				
				<title>					
					<xsl:apply-templates select="//Title"/>				
				</title>				
				<creator>					
					<xsl:apply-templates select="//PrincipalInvestigator"/>				
				</creator>				
								
					<xsl:apply-templates select="//Others"/>				
						
					
				<abstract>					
					<xsl:apply-templates select="//Abstract"/>
				</abstract>
				<keywordSet>					
					<xsl:apply-templates select="//Keywords"/>				
				</keywordSet>		
					<xsl:apply-templates select="//ResearchLocation"/>			
								
				<contact>					
					<xsl:apply-templates select="//PrincipalInvestigator"/>				
				</contact>				
									
				<xsl:apply-templates select="//Methods"/>							
									

				<dataTable>				
					
					<xsl:apply-templates select="//VariableDescription"/>		
				    			
									
				</dataTable>		
		
			
			</dataset>		
		</eml:eml>	
	</xsl:template>	
	<xsl:template match="PrincipalInvestigator">		
		<xsl:for-each select="FirstName">			
			<individualName>				
				<salutation>					
					<xsl:value-of select="../salutation"/>				
				</salutation>				
				<givenName>					
					<xsl:value-of select="."/>				
				</givenName>				
				<surName>					
					<xsl:value-of select="../LastName"/>				
				</surName>			
			</individualName>		
		</xsl:for-each>	
	</xsl:template>	
	<xsl:template match="Others">	
	<associatedParty>
		<xsl:for-each select="FirstName">			
			<individualName>				
				<salutation>					
					<xsl:value-of select="../salutation"/>				
				</salutation>				
				<givenName>					
					<xsl:value-of select="."/>				
				</givenName>				
				<surName>					
					<xsl:value-of select="../LastName"/>				
				</surName>			
			</individualName>		
			<role/>
		</xsl:for-each>	
	</associatedParty>
	</xsl:template>	
	<xsl:template match="Abstract">		
	
		<para>			
			<xsl:value-of select="."/>		
		</para>	
	</xsl:template>	
	<xsl:template match="Keywords">
	<keyword/>		
		<xsl:for-each select="Word">			
			<keyword>				
				<xsl:value-of select="."/>			
			</keyword>		
		</xsl:for-each>	
	</xsl:template>
       
       <xsl:template match="//ResearchLocation">
         <coverage>		
	<geographicCoverage>		
	<geographicDescription>		
		<xsl:value-of select="."/>		
	</geographicDescription>
	<boundingCoordinates>
         <westBoundingCoordinate/>
          <eastBoundingCoordinate/>
            <northBoundingCoordinate/>
            <southBoundingCoordinate/>
            </boundingCoordinates>
			</geographicCoverage>		
		</coverage>	
        </xsl:template>
	<xsl:template match="//Methods">
	<methods>
	<methodStep>
	<description>
	<para>
	<xsl:value-of select="."/>
	</para>
	</description>
	</methodStep>
	</methods>
	
	</xsl:template>	
	
	<xsl:template match="VariableDescription">
	
			<entityName>
				<xsl:value-of select="//FileName/File"/>

			</entityName>
			<entityDescription>
				<xsl:value-of select="//FileName/File"/>
			</entityDescription>

			<physical>
				<objectName>
					<xsl:value-of select="//FileName/File"/>
				</objectName>
				<dataFormat>
					<textFormat>
						<attributeOrientation>column</attributeOrientation>
						<simpleDelimited>
							<fieldDelimiter>,</fieldDelimiter>
						</simpleDelimited>
					</textFormat>
				</dataFormat>
			</physical>
   
		
	
	
	
		
        <coverage>	
		<xsl:for-each select="//Timing/site">
		<xsl:variable name ="counter" select="position()"/>	
			<geographicCoverage>		
				<geographicDescription>		
					<xsl:value-of select="./name"/>		
				</geographicDescription>
			 <boundingCoordinates>
            <westBoundingCoordinate/>
            <eastBoundingCoordinate/>
            <northBoundingCoordinate/>
            <southBoundingCoordinate/>
            </boundingCoordinates>
			</geographicCoverage>	
		   </xsl:for-each>
		
		   <xsl:for-each select="//Timing/site">
          <temporalCoverage>
		   <xsl:variable name ="counter" select="position()"/>	
		
		  <xsl:for-each select="../site[$counter]/date">
		   <singleDateTime>
		
		   <calenderDate>2001-01-01</calenderDate>
		   </singleDateTime>
		   </xsl:for-each>
           </temporalCoverage>
		   </xsl:for-each>
		 
		   </coverage>
			
	
		<method>		
			<methodStep>		
				<description>	
				  <para>
					<xsl:value-of select="../Methods"/>	
					</para>	
				</description>		
			</methodStep>		
		</method>		
		<additionalInfo><para></para></additionalInfo>
		
	

        <attributeList>
		<xsl:for-each select="VARIABLE">		
			
				<attribute>	
				<xsl:variable name ="counter" select="position()"/>		
				<xsl:variable name="checker" select="../TYPE[$counter]"/>
				<xsl:variable name="missing" select="../missingValueIndicator"/>
					<attributeName>		
						<xsl:value-of select="."/>	    
						
					</attributeName>		
					<attributeDefinition>		
						<xsl:value-of select="../DESCRIPTION[$counter]"/>		
					</attributeDefinition>		
					<xsl:if test="contains($checker,'Number')">		
					<xsl:variable name ="val" select="../PRECISION[$counter]"/>		
						<measurementScale>		
							<ratio>		
								<unit>            
									<standardUnit>meter</standardUnit>
								</unit>        
								<precision> 
								    <xsl:choose>
								    <xsl:when test="contains($val,'n/a')">
								    -1.0
								    </xsl:when>
								     <xsl:when test="contains($val,'Tool')">
								    -1.0
								    </xsl:when>
								     <xsl:otherwise>
								     <xsl:value-of select="../PRECISION[$counter]"/>
								     </xsl:otherwise>
								    </xsl:choose>
									
								</precision>         
								<numericDomain>                
									<numberType>real</numberType>         
								</numericDomain>		
							</ratio>		
						</measurementScale>		
					</xsl:if>		
									
					<xsl:if test="contains($checker,'Date')">
						<measurementScale>
						<datetime>
						<formatString>
						<xsl:value-of select="../UNITS[$counter]"/>
						</formatString>
						<dateTimePrecision>
						<xsl:value-of select="../PRECISION[$counter]"/>
						</dateTimePrecision>
						<dateTimeDomain>
						<bounds>
						<minimum exclusive="false"><xsl:value-of select="../MINIMUM[$counter]"/></minimum>
						<maximum exclusive="false"><xsl:value-of select="../MAXIMUM[$counter]"/> </maximum>
						</bounds>
						</dateTimeDomain>
						
						</datetime>
						
						</measurementScale>
					</xsl:if>
					<xsl:if test="contains($checker,'Text')">		
						<measurementScale>
						<nominal>
						<nonNumericDomain>
						  <textDomain>
						    <definition>
						     <xsl:value-of select="../DESCRIPTION[$counter]"/>	
						    </definition>
						  </textDomain>
						</nonNumericDomain>
						</nominal>	
						</measurementScale>	
					</xsl:if>
					<xsl:if test="contains($checker,'String')">		
						<measurementScale>
						<nominal>
						<nonNumericDomain>
						  <textDomain>
						    <definition>
						     <xsl:value-of select="../DESCRIPTION[$counter]"/>	
						    </definition>
						  </textDomain>
						</nonNumericDomain>
						</nominal>	
						</measurementScale>	
					</xsl:if>
					<missingValueCode>
					<xsl:choose>
					<xsl:when test="contains(translate($missing, '012345678', '9'), '9')">
					<code>
					kkl
					</code>
					
					</xsl:when>
					<xsl:otherwise>
					<code>
					<xsl:value-of select="../missingValueIndicator[$counter]"/>
					</code>
					</xsl:otherwise>
					</xsl:choose>
					
					<codeExplanation>
					</codeExplanation>
					</missingValueCode>
				</attribute>		
				
		</xsl:for-each>
		</attributeList>	
	</xsl:template>
</xsl:stylesheet>