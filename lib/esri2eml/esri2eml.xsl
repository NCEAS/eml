<?xml version="1.0" encoding="UTF-8"?>
<!--
       '$RCSfile: esri2eml.xsl,v $'
        '$Author: mccartne $'
          '$Date: 2003-05-21 21:32:23 $'
      '$Revision: 1.1 $'


    Copyright: 2003 Arizona State University
    
    This material is based upon work supported by the National Science Foundation 
    under Grant No. 9983132 and 0219310. Any opinions, findings and conclusions or recommendation 
    expressed in this material are those of the author(s) and do not necessarily 
    reflect the views of the National Science Foundation (NSF).  
                  
    For Details: http://ces.asu.edu/bdi

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
-->

<xsl:stylesheet version="1.0" xml:lang="en" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes"/>
	<xsl:variable name="sdtstype.tf">
		<sdtstype name="ring" gmltype="LinearRing"/>
		<sdtstype name="Point" gmltype="Point"/>
		<sdtstype name="Entity point" gmltype="Point"/>
		<sdtstype name="Label point" gmltype="Point"/>
		<sdtstype name="Area point" gmltype="Point"/>
		<sdtstype name="Node, planar graph" gmltype="Point"/>
		<sdtstype name="Node, network" gmltype="Point"/>
		<sdtstype name="String" gmltype="LineString"/>
		<sdtstype name="Link" gmltype="polygon"/>
		<sdtstype name="Complete chain" gmltype="LineString"/>
		<sdtstype name="Area chain" gmltype="LinearRing"/>
		<sdtstype name="Network chain, planar graph" gmltype="LineString"/>
		<sdtstype name="Network chain, nonplanar graph" gmltype="LineString"/>
		<sdtstype name="Circular arc, three point center" gmltype="LineString"/>
		<sdtstype name="Elliptical arc" gmltype="LineString"/>
		<sdtstype name="Uniform B-spline" gmltype="LineString"/>
		<sdtstype name="Piecewise Bezier" gmltype="LineString"/>
		<sdtstype name="Ring with mixed composition" gmltype="LinearRing"/>
		<sdtstype name="Ring composed of strings" gmltype="LinearRing"/>
		<sdtstype name="Ring composed of chains" gmltype="LinearRing"/>
		<sdtstype name="Ring composed of arcs" gmltype="LinearRing"/>
		<sdtstype name="G-polygon" gmltype="Polygon"/>
		<sdtstype name="GT-polygon composed of rings" gmltype="Polygon"/>
		<sdtstype name="GT-polygon composed of chains" gmltype="Polygon"/>
		<sdtstype name="Universe polygon composed of rings" gmltype="Polygon"/>
		<sdtstype name="Universe polygon composed of chains" gmltype="Polygon"/>
		<sdtstype name="Void polygon composed of rings" gmltype="Polygon"/>
		<sdtstype name="Void polygon composed of chains" gmltype="Polygon"/>
	</xsl:variable>
	<xsl:template match="/">
		<xsl:element name="eml:eml" namespace="eml://ecoinformatics.org/eml-2.0.0">
			<xsl:attribute name="xmlns:xsi">http://www.w3.org/2001/XMLSchema-instance</xsl:attribute>
			<xsl:attribute name="xsi:schemaLocation">eml://ecoinformatics.org/eml-2.0.0 http://ces.asu.edu/eml/eml.xsd</xsl:attribute>
			<xsl:attribute name="packageId">
				<xsl:value-of select="metadata/Esri/MetaID"/>
			</xsl:attribute>
			<xsl:attribute name="system">ESRI MetaID</xsl:attribute>
			<dataset>
				<xsl:attribute name="id">
					<xsl:value-of select="metadata/Esri/MetaID"/>
				</xsl:attribute>
				<xsl:attribute name="system">ESRI MetaID</xsl:attribute>
				<xsl:for-each select="metadata/idinfo/citation/citeinfo/ftname">
					<shortName>
						<xsl:value-of select="."/>
					</shortName>
				</xsl:for-each>

				<xsl:for-each select="metadata/idinfo/citation/citeinfo/title">
					<title>
						<xsl:value-of select="."/>
					</title>
				</xsl:for-each>
				<xsl:for-each select="/metadata/idinfo/citation/citeinfo/origin">
					<xsl:element name="creator">
						<individualName>
							<surName>
								<xsl:value-of select="."/>
							</surName>
						</individualName>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="metadata/idinfo/citation/citeinfo/pubdate">
					<pubDate>
						<xsl:value-of select="."/>
					</pubDate>
				</xsl:for-each>
				<xsl:for-each select="metadata/idinfo/descript/langdata">
					<language>
						<xsl:value-of select="."/>
					</language>
				</xsl:for-each>
				<xsl:for-each select="metadata/idinfo/citation/citeinfo/serinfo/sername">
					<series>
						<xsl:value-of select="."/>
					</series>
				</xsl:for-each>

				<xsl:for-each select="metadata/idinfo/descript/abstract">
					<abstract>
						<para>
							<xsl:value-of select="."/>
						</para>
					</abstract>
				</xsl:for-each>
				<xsl:for-each select="//metadata/idinfo/keywords/theme">
						<keywordSet>
							<keywordThesaurus>
								<xsl:value-of select="./themekt"/>
							</keywordThesaurus>
							<xsl:for-each select="./themekey">
								<keyword keywordType="theme">
									<xsl:value-of select="."/>
								</keyword>
							</xsl:for-each>
					</keywordSet>
				</xsl:for-each>
				<xsl:for-each select="//metadata/idinfo/keywords/place">
					<keywordSet>
							<keywordThesaurus>
								<xsl:value-of select="./placekt"/>
							</keywordThesaurus>
							<xsl:for-each select="./placekey">
								<keyword keywordType="place">
									<xsl:value-of select="."/>
								</keyword>
							</xsl:for-each>
					</keywordSet>
				</xsl:for-each>

				<distribution>
					<xsl:choose>
						<xsl:when test="/metadata/idinfo/citation/citeinfo[starts-with(onlink,'Server')]">

							<online>
								<connection>
									<connectionDefinition id="sde.connection1">
										<schemeName system="http://ces.asu.edu/eml/ces/connectionDictionary.xml">Spatial Database Engine</schemeName>
										<description>Connection Definition for ESRI Spatial Database Engine.</description>
										<xsl:for-each select="/metadata/idinfo/citation/citeinfo/onlink">
											<parameterDefinition>
												<name>host</name>
												<description>Host name or ip number of the computer running the service.</description>
												<defaultValue>
													<xsl:value-of select="substring-before(substring-after(.,'Server='),';')"/>
												</defaultValue>
											</parameterDefinition>
											<parameterDefinition>
												<name>port</name>
												<description>The port number where the service is listening.</description>
												<defaultValue>
													<xsl:value-of select="substring-before(substring-after(.,'port:'),';')"/>
												</defaultValue>
											</parameterDefinition>
											<parameterDefinition>
												<name>catalog</name>
												<description>The name of the database or catalog.</description>
												<defaultValue>
													<xsl:value-of select="substring-before(substring-after(.,'Database='),';')"/>
												</defaultValue>
											</parameterDefinition>
											<parameterDefinition>
												<name>owner</name>
												<description>The owner or schema for the object</description>
											</parameterDefinition>
											<parameterDefinition>
												<name>object</name>
												<description>The name of the data object.</description>
											</parameterDefinition>

											<parameterDefinition>
												<name>shapeColumn</name>
												<description>The name of table column containing shape id.</description>
												<defaultValue>
													<xsl:value-of select="substring-before(substring-after(.,'User='),';')"/>
												</defaultValue>
											</parameterDefinition>
										</xsl:for-each>
									</connectionDefinition>
								</connection>
							</online>
						</xsl:when>

						<xsl:otherwise>
							<online>
								<url>
									<xsl:value-of select="/metadata/idinfo/citation/citeinfo/onlink"/>
								</url>
							</online>
						</xsl:otherwise>
					</xsl:choose>
				</distribution>

				<coverage>
					<xsl:for-each select="metadata/idinfo/timeperd">
						<temporalCoverage>
							<xsl:for-each select="./timeinfo/sngdate">
								<singleDateTime>
									<xsl:for-each select="./caldate">
										<calendarDate>
											<xsl:value-of select="."/>
										</calendarDate>
									</xsl:for-each>
									<xsl:for-each select="./time">
										<time>
											<xsl:value-of select="."/>
										</time>
									</xsl:for-each>
								</singleDateTime>
							</xsl:for-each>
							<xsl:for-each select="metadata/idinfo/timeperd/timeinfo/mdattim/sngdate">
								<singleDateTime>
									<xsl:for-each select="./caldate">
										<calendarDate>
											<xsl:value-of select="."/>
										</calendarDate>
									</xsl:for-each>
									<xsl:for-each select="./time">
										<time>
											<xsl:value-of select="."/>
										</time>
									</xsl:for-each>
								</singleDateTime>
							</xsl:for-each>
							<xsl:for-each select="metadata/idinfo/timeperd/timeinfo/rngdates">
								<rangeOfDates>
									<xsl:for-each select="./begdate">
										<singleDateTime>
											<calendarDate>
												<xsl:value-of select="."/>
											</calendarDate>
											<xsl:for-each select="./begtime">
												<time>
													<xsl:value-of select="."/>
												</time>
											</xsl:for-each>
										</singleDateTime>
									</xsl:for-each>
									<xsl:for-each select="./enddate">
										<singleDateTime>
											<calendarDate>
												<xsl:value-of select="."/>
											</calendarDate>
											<xsl:for-each select="./endtime">
												<endtime>
													<xsl:value-of select="."/>
												</endtime>
											</xsl:for-each>
										</singleDateTime>
									</xsl:for-each>
								</rangeOfDates>
							</xsl:for-each>
						</temporalCoverage>
					</xsl:for-each>
					<xsl:for-each select="metadata/idinfo/spdom">
						<geographicCoverage>
							<xsl:for-each select="./bounding">
								<boundingCoordinates>
									<xsl:for-each select="./westbc">
										<westBoundingCoordinate>
											<xsl:value-of select="."/>
										</westBoundingCoordinate>
									</xsl:for-each>
									<xsl:for-each select="./eastbc">
										<eastBoundingCoordinate>
											<xsl:value-of select="."/>
										</eastBoundingCoordinate>
									</xsl:for-each>
									<xsl:for-each select="./northbc">
										<northBoundingCoordinate>
											<xsl:value-of select="."/>
										</northBoundingCoordinate>
									</xsl:for-each>
									<xsl:for-each select="./southbc">
										<southBoundingCoordinate>
											<xsl:value-of select="."/>
										</southBoundingCoordinate>
									</xsl:for-each>
								</boundingCoordinates>
							</xsl:for-each>
							<xsl:for-each select="metadata/idinfo/spdom/dsgpoly">
								<datasetGPolygon>
									<xsl:for-each select="./dsgpolyo">
										<datasetGPolygonOuterRing>
											<xsl:for-each select="./grngpoin">
												<gRingPoint>
													<gRingLatitude>
														<xsl:value-of select="./gringlat"/>
													</gRingLatitude>
													<gRingLongitude>
														<xsl:value-of select="./gringlong"/>
													</gRingLongitude>
												</gRingPoint>
											</xsl:for-each>
											<xsl:for-each select="metadata/idinfo/spdom/dsgpoly/dsgpolyo/gring">
												<gring>
													<xsl:value-of select="."/>
												</gring>
											</xsl:for-each>
										</datasetGPolygonOuterRing>
									</xsl:for-each>
									<xsl:for-each select="metadata/idinfo/spdom/dsgpoly/dsgpolyx">
										<datasetGPolygonExclusionRing>
											<xsl:for-each select="./grngpoin">
												<gRingPoint>
													<xsl:for-each select="./gringlat">
														<gringlat>
															<xsl:value-of select="."/>
														</gringlat>
													</xsl:for-each>
													<xsl:for-each select="./gringlon">
														<gringlon>
															<xsl:value-of select="."/>
														</gringlon>
													</xsl:for-each>
												</gRingPoint>
											</xsl:for-each>
											<xsl:for-each select="metadata/idinfo/spdom/dsgpoly/dsgpolyx/gring">
												<gring>
													<xsl:value-of select="."/>
												</gring>
											</xsl:for-each>
										</datasetGPolygonExclusionRing>
									</xsl:for-each>
								</datasetGPolygon>
							</xsl:for-each>
						</geographicCoverage>
					</xsl:for-each>
				</coverage>



				<xsl:for-each select="metadata/idinfo/descript/purpose">
					<purpose>
						<para>
							<xsl:value-of select="."/>
						</para>
					</purpose>
				</xsl:for-each>

				<xsl:for-each select="/metadata/idinfo/ptcontac">
					<contact>

						<xsl:for-each select="./cntinfo/cntorgp/cntorg">
							<organizationName>
								<xsl:value-of select="."/>
							</organizationName>
						</xsl:for-each>

						<xsl:if test="./cntinfo/cntperp/cntper">
							<individualName>
								<surName>
									<xsl:value-of select="./cntinfo/cntperp/cntper"/>
								</surName>
							</individualName>
							<xsl:for-each select="cntinfo/cntperp/cntorg">
								<organizationName>
									<xsl:value-of select="."/>
								</organizationName>
							</xsl:for-each>
						</xsl:if>
						<xsl:for-each select="./cntinfo/cntpos">
							<positionName>
								<xsl:value-of select="."/>
							</positionName>
						</xsl:for-each>

						<xsl:for-each select="cntinfo/cntaddr">
							<address>
								<deliveryPoint>
									<xsl:value-of select="."/>
								</deliveryPoint>
								<xsl:for-each select="city">
									<city>
										<xsl:value-of select="."/>
									</city>
								</xsl:for-each>
							</address>
						</xsl:for-each>
					</contact>
				</xsl:for-each>




				<xsl:for-each select="/metadata/idinfo/citation/citeinfo/pubinfo/publish">
					<publisher>
						<organizationName>
							<xsl:value-of select="."/>
						</organizationName>
					</publisher>
				</xsl:for-each>

				<xsl:for-each select="/metadata/distinfo/distrib/cntinfo">
					<contact>
						<individualName>
							<xsl:for-each select="./cntperp/cntper">
								<surName>
									<xsl:value-of select="."/>
								</surName>
							</xsl:for-each>
						</individualName>
						<xsl:for-each select="./cntorgp/cntorg">
							<organizationName>
								<xsl:value-of select="."/>
							</organizationName>
						</xsl:for-each>
						<xsl:for-each select="./cntpos">
							<positionName>
								<xsl:value-of select="."/>
							</positionName>
						</xsl:for-each>
						<address>
							<xsl:for-each select="./cntaddr/address">
								<deliveryPoint>
									<xsl:value-of select="."/>
								</deliveryPoint>
							</xsl:for-each>
							<xsl:for-each select="./cntaddr/city">
								<city>
									<xsl:value-of select="."/>
								</city>
							</xsl:for-each>
							<xsl:for-each select="./cntaddr/state">
								<administrativeArea>
									<xsl:value-of select="."/>
								</administrativeArea>
							</xsl:for-each>
							<xsl:for-each select="./cntaddr/postal">
								<postalCode>
									<xsl:value-of select="."/>
								</postalCode>
							</xsl:for-each>
							<xsl:for-each select="./cntaddr/country">
								<country>
									<xsl:value-of select="."/>
								</country>
							</xsl:for-each>
						</address>
						<xsl:for-each select="./cntvoice">
							<phone phonetype="voice">
								<xsl:value-of select="."/>
							</phone>
						</xsl:for-each>
						<xsl:for-each select="./cntfax">
							<phone phonetype="fax">
								<xsl:value-of select="."/>
							</phone>
						</xsl:for-each>
						<xsl:for-each select="./cntemail">
							<electronicMailAddress>
								<xsl:value-of select="."/>
							</electronicMailAddress>
						</xsl:for-each>
					</contact>
				</xsl:for-each>
				<xsl:choose>
					<xsl:when test="/metadata/spdoinfo/direct = 'Vector'">
						<spatialVector>
							<xsl:attribute name="id">
								<xsl:value-of select="metadata/eainfo/detailed/@Name"/>
							</xsl:attribute>

							<xsl:for-each select="metadata/eainfo/detailed/@Name">
								<entityName>
									<xsl:value-of select="."/>
								</entityName>
							</xsl:for-each>
							<xsl:call-template name="physical"/>
							<xsl:call-template name="method"/>
							<xsl:call-template name="attr"/>
							<geometry>
								<xsl:variable name="geotype" select="metadata/spdoinfo/ptvctinf/sdtsterm/sdtstype"/>
								<xsl:value-of select="$sdtstype.tf/*[@name=$geotype]/@gmltype"/>
							</geometry>
							<xsl:for-each select="metadata/spdoinfo/ptvctinf/sdtsterm/ptvctcnt">
								<geometricObjectCount>
									<xsl:value-of select="."/>
								</geometricObjectCount>
							</xsl:for-each>
							<xsl:call-template name="spref"/>

							<xsl:call-template name="horizAccuracy"/>
							<xsl:call-template name="vertAccuracy"/>
						</spatialVector>
					</xsl:when>
					<xsl:when test="metadata/spdoinfo/direct = 'Raster'">

						<spatialRaster>
							<xsl:for-each select="/metadata/eainfo/detailed/@Name">
								<xsl:attribute name="id">
									<xsl:value-of select="."/>
								</xsl:attribute>
								<entityName>
									<xsl:value-of select="."/>
								</entityName>
							</xsl:for-each>
							<xsl:call-template name="physical"/>
							<xsl:call-template name="method"/>
							<xsl:call-template name="attr"/>
							<xsl:call-template name="spref"/>
							<xsl:call-template name="horizAccuracy"/>
							<xsl:call-template name="vertAccuracy"/>
							<xsl:for-each select="metadata/spdoinfo/rastinfo/rastxsz">
								<cellSizeXDirection>
									<xsl:value-of select="."/>
								</cellSizeXDirection>
							</xsl:for-each>
							<xsl:for-each select="metadata/spdoinfo/rastinfo/rastysz">
								<cellSizeYDirection>
									<xsl:value-of select="."/>
								</cellSizeYDirection>
							</xsl:for-each>

							<xsl:for-each select="metadata/spdoinfo/rastinfo/rastband">
								<numberOfBands>
									<xsl:value-of select="."/>
								</numberOfBands>
							</xsl:for-each>
							<xsl:for-each select="metadata/spdoinfo/rastinfo/rastorig">
								<rasterOrigin>
									<xsl:value-of select="."/>
								</rasterOrigin>
							</xsl:for-each>
							<xsl:for-each select="metadata/spdoinfo/rastinfo/rowcount">
								<rows>
									<xsl:value-of select="."/>
								</rows>
							</xsl:for-each>
							<xsl:for-each select="metadata/spdoinfo/rastinfo/colcount">
								<columns>
									<xsl:value-of select="."/>
								</columns>
							</xsl:for-each>
							<xsl:for-each select="metadata/spdoinfo/rastinfo/vrtcount">
								<verticals>
									<xsl:value-of select="."/>
								</verticals>
							</xsl:for-each>

							<cellGeometry>
								<xsl:choose>
									<xsl:when test="/metadata/spdoinfo/rastinfo/rasttype ='Grid Cell'">
										<xsl:value-of select="'matrix'"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="'pixel'"/>
									</xsl:otherwise>
								</xsl:choose>
							</cellGeometry>
						</spatialRaster>
					</xsl:when>
				</xsl:choose>
			</dataset>
		</xsl:element>
	</xsl:template>
	<xsl:template name="method" match="/metadata/dataqual">
		<xsl:for-each select="./lineage">
			<methods>
				<methodStep>
					<xsl:value-of select="procstep"/>
					<xsl:for-each select="procstep/procdesc">
						<description>
							<para>
								<xsl:value-of select="."/>
							</para>
						</description>
					</xsl:for-each>
					<xsl:for-each select="procstep/srcused">
						<dataSource>
							<title>
								<xsl:value-of select="./"/>
							</title>
						</dataSource>
					</xsl:for-each>
				</methodStep>
			</methods>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="attr" match="/metadata/eainfo/detailed">
		<attributeList>
			<xsl:for-each select="/metadata/eainfo/detailed/attr">
				<attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="attrlabl"/>
					</xsl:attribute>
					<xsl:for-each select="./attrlabl">
						<attributeName>
							<xsl:value-of select="."/>
						</attributeName>
					</xsl:for-each>
					<xsl:choose>
						<xsl:when test="./attrdef">

							<attributeDefinition>
								<xsl:value-of select="./attrdef"/>
							</attributeDefinition>
						</xsl:when>
						<xsl:otherwise>

							<attributeDefinition>
								<xsl:value-of select="./attrlabl"/>
							</attributeDefinition>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:choose>
						<xsl:when test="attrtype='Number'">
							<xsl:choose>
								<xsl:when test="./atnumdec &gt; 1">
									<storageType typeSystem="http://www.w3.org/2001/XMLSchema-datatypes">float</storageType>
								</xsl:when>
								<xsl:otherwise>
									<storageType typeSystem="http://www.w3.org/2001/XMLSchema-datatypes">integer</storageType>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:when test="attrtype = 'String'">
							<storageType typeSystem="http://www.w3.org/2001/XMLSchema-datatypes">string</storageType>
						</xsl:when>
						<xsl:otherwise>
							<storageType typeSystem="http://www.esri.com/metadata/esriprof80.html">
								<xsl:value-of select="./attrtype"/>
							</storageType>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:for-each select="./attrprecis">
						<precision>
							<xsl:value-of select="."/>
						</precision>
					</xsl:for-each>
					<xsl:for-each select="./attrdomv">
						<xsl:choose>
							<xsl:when test="./edom">

								<measurementScale>
									<nominal>
										<nonNumericDomain>
											<enumeratedDomain>
												<xsl:for-each select="./edom">
													<codeDefinition>
														<xsl:for-each select="./edomv">
															<code>
																<xsl:value-of select="."/>
															</code>
														</xsl:for-each>
														<xsl:for-each select="./edomvd">
															<definition>
																<xsl:value-of select="."/>
															</definition>
														</xsl:for-each>
														<xsl:for-each select="metadata/eainfo/detailed/attr/attrdomv/edom/edomvds">
															<source>
																<xsl:value-of select="."/>
															</source>
														</xsl:for-each>
													</codeDefinition>
												</xsl:for-each>
											</enumeratedDomain>
										</nonNumericDomain>
									</nominal>
								</measurementScale>
							</xsl:when>


							<xsl:when test="./rdom">

								<measurementScale>
									<xsl:choose>
										<xsl:when test="./rdom/rdommin &lt; 0">
											<interval>

												<xsl:for-each select="./rdom/unit">
													<unit>
														<customUnit>
															<xsl:value-of select="."/>
														</customUnit>
													</unit>
												</xsl:for-each>
												<xsl:for-each select="../atprecis">
													<precision>
														<xsl:value-of select="."/>
													</precision>
												</xsl:for-each>
												<numericDomain>
													<numberType>
														<xsl:choose>
															<xsl:when test="../atprecis = 0">whole</xsl:when>
															<xsl:otherwise>real</xsl:otherwise>
														</xsl:choose>
													</numberType>
													<bounds>
														<xsl:for-each select="./rdom/rdommin">
															<minimum inclusive="true">
																<xsl:value-of select="."/>
															</minimum>
														</xsl:for-each>
														<xsl:for-each select="./rdom/rdommax">
															<maximum inclusive="true">
																<xsl:value-of select="."/>
															</maximum>
														</xsl:for-each>
													</bounds>
												</numericDomain>
											</interval>
										</xsl:when>
										<xsl:otherwise>
											<ratio>
												<xsl:for-each select="./rdom/unit">
													<unit>
														<customUnit>
															<xsl:value-of select="."/>
														</customUnit>
													</unit>
												</xsl:for-each>
												<xsl:for-each select="./rdom/attrres">
													<precision>
														<xsl:value-of select="."/>
													</precision>
												</xsl:for-each>
												<numericDomain>
													<xsl:choose>
														<xsl:when test="../atprecis = 0">whole</xsl:when>
														<xsl:otherwise>real</xsl:otherwise>
													</xsl:choose>

													<bounds>
														<xsl:for-each select="./rdom/rdommin">
															<minimum inclusive="true">
																<xsl:value-of select="."/>
															</minimum>
														</xsl:for-each>
														<xsl:for-each select="./rdom/rdommax">
															<maximum inclusive="true">
																<xsl:value-of select="."/>
															</maximum>
														</xsl:for-each>
													</bounds>
												</numericDomain>
											</ratio>
										</xsl:otherwise>
									</xsl:choose>
								</measurementScale>
							</xsl:when>
							<xsl:when test="./codesetd">
								<measurementScale>
									<nominal>
										<nonNumericDomain>
											<enumeratedDomain>

												<externalCodeset>
													<codesetName>
														<xsl:value-of select="./codesetn"/>
													</codesetName>
													<citation>
														<xsl:value-of select="./codesets"/>
													</citation>
												</externalCodeset>
											</enumeratedDomain>
										</nonNumericDomain>
									</nominal>
								</measurementScale>
							</xsl:when>
							<xsl:when test="./udom">
								<measurementScale>
									<nominal>
										<nonNumericDomain>
											<textDomain>
												<definition>
													<xsl:value-of select="."/>
												</definition>
											</textDomain>
										</nonNumericDomain>
									</nominal>
								</measurementScale>
							</xsl:when>
						</xsl:choose>
					</xsl:for-each>
					<xsl:for-each select="./begdatea">
						<coverage>
							<temporalCoverage>
								<rangeOfDates>
									<xsl:for-each select=".">
										<calendarDate>
											<xsl:value-of select="."/>
										</calendarDate>
									</xsl:for-each>
									<xsl:for-each select="../enddatea">
										<calendarDate>
											<xsl:value-of select="."/>
										</calendarDate>
									</xsl:for-each>
								</rangeOfDates>
							</temporalCoverage>
						</coverage>
					</xsl:for-each>
					<xsl:for-each select="metadata/dataqual/attrac">
						<attributeAccuracy>
							<xsl:for-each select="./attraccr">
								<accuracyReport>
									<xsl:value-of select="."/>
								</accuracyReport>
							</xsl:for-each>
							<xsl:for-each select="qattrac">
								<quantitativeAccuracyReport>
									<xsl:for-each select="attracv">
										<attributeAccuracyValue>
											<xsl:value-of select="."/>
										</attributeAccuracyValue>
									</xsl:for-each>
									<xsl:for-each select="attrace">
										<attributeAccuracyMethod>
											<xsl:value-of select="."/>
										</attributeAccuracyMethod>
									</xsl:for-each>
								</quantitativeAccuracyReport>
							</xsl:for-each>
						</attributeAccuracy>
					</xsl:for-each>
				</attribute>
			</xsl:for-each>
		</attributeList>
	</xsl:template>


	<xsl:template name="spref" match="/metadata/spref">
		<spatialReference>
			<xsl:choose>
				<xsl:when test="//horizsys/cordsysn/projcsn">
					<horizCoordSysName>
						<xsl:value-of select=".//projcsn"/>
					</horizCoordSysName>
				</xsl:when>
				<xsl:otherwise>
					<horizCoordSysName>
						<xsl:value-of select=".//geogcsn"/>
					</horizCoordSysName>
				</xsl:otherwise>
			</xsl:choose>

			<xsl:for-each select="vertdef">
				<vertCoordSys>
					<xsl:for-each select="altsys">
						<altitudeSysDef>
							<xsl:for-each select="altdatum">
								<altitudeDatumName>
									<xsl:value-of select="."/>
								</altitudeDatumName>
							</xsl:for-each>
							<xsl:for-each select="altres">
								<altitudeResolution>
									<xsl:value-of select="."/>
								</altitudeResolution>
							</xsl:for-each>
							<xsl:for-each select="altunits">
								<altitudeDistanceUnits>
									<xsl:value-of select="."/>
								</altitudeDistanceUnits>
							</xsl:for-each>
							<xsl:for-each select="altenc">
								<altitudeEncodingMethod>
									<xsl:value-of select="."/>
								</altitudeEncodingMethod>
							</xsl:for-each>
						</altitudeSysDef>
					</xsl:for-each>
					<xsl:for-each select="depthsys">
						<depthSysDef>
							<xsl:for-each select="depthdn">
								<depthDatumName>
									<xsl:value-of select="."/>
								</depthDatumName>
							</xsl:for-each>
							<xsl:for-each select="depthres">
								<depthResolution>
									<xsl:value-of select="."/>
								</depthResolution>
							</xsl:for-each>
							<xsl:for-each select="depthdu">
								<depthDistanceUnits>
									<xsl:value-of select="."/>
								</depthDistanceUnits>
							</xsl:for-each>
							<xsl:for-each select="depthem">
								<depthEncodingMethod>
									<xsl:value-of select="."/>
								</depthEncodingMethod>
							</xsl:for-each>
						</depthSysDef>
					</xsl:for-each>
				</vertCoordSys>
			</xsl:for-each>
		</spatialReference>
	</xsl:template>
	<xsl:template name="vertAccuracy" match="/metadata/dataqual/posacc/vertacc">
		<verticalAccuracy>
			<xsl:for-each select="vertaccr">
				<accuracyReport>
					<xsl:value-of select="."/>
				</accuracyReport>
			</xsl:for-each>
			<xsl:for-each select="qvertpa">
				<quantAccReport>
					<xsl:for-each select="vertaccv">
						<quantAccVal>
							<xsl:value-of select="."/>
						</quantAccVal>
					</xsl:for-each>
					<xsl:for-each select="vertacce">
						<quantAccMethod>
							<xsl:value-of select="."/>
						</quantAccMethod>
					</xsl:for-each>
				</quantAccReport>
			</xsl:for-each>
		</verticalAccuracy>
	</xsl:template>
	<xsl:template name="horizAccuracy" match="/metadata/dataqual/posacc/horizpa">
		<horizontalAccuracy>
			<xsl:for-each select="horizpar">
				<accuracyReport>
					<xsl:value-of select="."/>
				</accuracyReport>
			</xsl:for-each>
			<xsl:for-each select="qhorizpa">
				<quantAccReport>
					<xsl:for-each select="horizpav">
						<quantAccVal>
							<xsl:value-of select="."/>
						</quantAccVal>
					</xsl:for-each>
					<xsl:for-each select="horizpae">
						<quantAccMethod>
							<xsl:value-of select="."/>
						</quantAccMethod>
					</xsl:for-each>
				</quantAccReport>
			</xsl:for-each>
		</horizontalAccuracy>
	</xsl:template>
	<xsl:template name="physical" match="/metadata">
		<physical>
			<xsl:for-each select="/metadata/eainfo/detailed/@Name">
				<objectName>
					<xsl:value-of select="."/>
				</objectName>
			</xsl:for-each>

			<dataFormat>
				<xsl:choose>

					<xsl:when test="//idinfo/natvform">
						<externallyDefinedFormat>
							<formatName>
								<xsl:value-of select="//idinfo/natvform"/>
							</formatName>
						</externallyDefinedFormat>
					</xsl:when>

					<xsl:when test="//distributor/distorFormat/formatName">
						<externallyDefinedFormat>
							<formatType>
								<xsl:value-of select="//distributor/distorFormat/formatName"/>
							</formatType>
						</externallyDefinedFormat>
					</xsl:when>
					<xsl:when test="/metadata/distinfo/stdorder/digform/formName">
						<xsl:for-each select="/metadata/distinfo/stdorder/digform/">
							<externallyDefinedFormat>
								<formatName>
									<xsl:value-of select="./formName"/>
								</formatName>
								<xsl:for-each select="./formvern">
									<formatVersion>
										<xsl:value-of select="."/>
									</formatVersion>
								</xsl:for-each>
								<!--
						<xsl:for-each select="formverd">
							<documentation>
								<xsl:value-of select="."/>
							</documentation>
						</xsl:for-each>
-->
							</externallyDefinedFormat>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			</dataFormat>
			<distribution>
				<xsl:choose>
					<xsl:when test="/metadata/idinfo/citation/citeinfo[starts-with(onlink, 'Server')] ">
						<online>
							<connection>
								<connectionDefinition>
									<references>sde.connection1</references>
								</connectionDefinition>
								<parameter>
									<name>owner</name>
									<value>
										<xsl:value-of select="substring-before(substring-after(//citeinfo/onlink,'User='),';')"/>
									</value>
								</parameter>
								<parameter>
									<name>object</name>
									<value>
										<xsl:value-of select="substring-after(substring-after(//citeinfo/ftname,'.'),'.')"/>
									</value>
								</parameter>
								<parameter>
									<name>shapeColumn</name>
									<value>
										<xsl:value-of select="/metadata/eainfo/detailed/attr/attalias[../attrdef = 'Feature geometry.']"/>
									</value>
								</parameter>
							</connection>
						</online>
					</xsl:when>
					<xsl:otherwise>
						<online>
							<url>
								<xsl:value-of select="/metadata/idinfo/citation/citeinfo/onlink"/>
							</url>
						</online>
					</xsl:otherwise>
				</xsl:choose>
			</distribution>
		</physical>
	</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c)1998-2002 eXcelon Corp.
<metaInformation>
<scenarios ><scenario default="no" name="ESRIProf80" userelativepaths="yes" externalpreview="no" url="file://g:\BDI\metadata\esri\esriprof80.dtd" htmlbaseurl="file://g:\BDI\metadata\esri\" processortype="internal" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext=""/><scenario default="no" name="dem" userelativepaths="yes" externalpreview="no" url="azdemutm.xml" htmlbaseurl="" processortype="internal" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext=""/><scenario default="no" name="counties" userelativepaths="yes" externalpreview="no" url="file://g:\BDI\metadata\esri\counties.xml" htmlbaseurl="file://g:\BDI\metadata\esri\" processortype="internal" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext=""/><scenario default="no" name="aeroimag" userelativepaths="yes" externalpreview="no" url="file://\\mohave\data\alris\UTM2712\gapveg.shp.xml" htmlbaseurl="file://\\maricopa\proj\BDI\metadata\esri\" processortype="internal" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext=""/><scenario default="no" name="Scenario1" userelativepaths="yes" externalpreview="no" url="..\..\..\LTER\dm\metadata\census_data.marigrp_dd.shp.xml" htmlbaseurl="" processortype="internal" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext=""/><scenario default="no" name="Scenario2" userelativepaths="yes" externalpreview="no" url="file://\\mohave\data\CENSUS_DATA\Census Blocks\cblock_utm.shp.xml" htmlbaseurl="" processortype="internal" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext=""/><scenario default="no" name="sde" userelativepaths="yes" externalpreview="no" url="file://\\maricopa\proj\BDI\metadata\esri\test\sde_XML.xml" htmlbaseurl="" processortype="internal" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext=""/><scenario default="yes" name="Scenario3" userelativepaths="yes" externalpreview="no" url="file://\\mohave\data\Landsat\classification\stef_expert_max_98.img.xml" htmlbaseurl="" processortype="internal" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext=""/></scenarios><MapperInfo srcSchemaPath="file://g:\BDI\metadata\esri\azdemutm.xml" srcSchemaRoot="metadata" srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="file://g:\cvs\eml\eml.dtd" destSchemaRoot="eml" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/>
</metaInformation>
-->