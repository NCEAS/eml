<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes"/>
<xsl:output encoding="ISO-8859-1"/>
<xsl:strip-space elements="*"/>

  <xsl:variable name="show_optional" select="0"/>
  <xsl:template match="/">
  
    <xsl:element name="metadata">
      <!-- only the 'idinfo' and 'metainfo' elements are required -->
      <xsl:element name="idinfo">
        <xsl:element name="citation">
          <xsl:element name="citeinfo">
            <xsl:element name="origin">
              1 to inf
            </xsl:element>
            <xsl:element name="pubdate">
            
            </xsl:element>
            <xsl:if test="$show_optional">
              <xsl:element name="pubtime">
              
              </xsl:element>
            </xsl:if>
            <xsl:element name="title">
            
            </xsl:element>
             <xsl:if test="$show_optional">
              <xsl:element name="edition">
              
              </xsl:element>
            </xsl:if>
            <xsl:element name="geoform">
            
            </xsl:element>
             <xsl:if test="$show_optional">
              <xsl:element name="serinfo">
                <xsl:element name="sername">
                
                </xsl:element>
                <xsl:element name="issue">
                
                </xsl:element>
              </xsl:element>
            </xsl:if>
             <xsl:if test="$show_optional">
              <xsl:element name="pubinfo">
                <xsl:element name="pubplace">
                
                </xsl:element>
                <xsl:element name="publish">
                
                </xsl:element>
              </xsl:element>
            </xsl:if>
             <xsl:if test="$show_optional">
              <xsl:element name="othercit">
              
              </xsl:element>
            </xsl:if>
             <xsl:if test="$show_optional">
              <xsl:element name="onlink">
              
              </xsl:element>
            </xsl:if>
             <xsl:if test="$show_optional">
              <xsl:element name="lworkcit">
                recursive link to citinfo
              </xsl:element>
            </xsl:if>
         </xsl:element>
        </xsl:element>
        <xsl:element name="descript">
          <xsl:element name="abstract">
          
          </xsl:element>
          <xsl:element name="purpose">
          
          </xsl:element>
          <xsl:if test="$show_optional">
            <xsl:element name="supplinf">
          
            </xsl:element>
          </xsl:if>
        </xsl:element>
        <xsl:element name="timeperd">
          <xsl:element name="timeinfo">
            <xsl:choose>
              <xsl:when test="1">
                <xsl:element name="sngdate">
                  <xsl:choose>
                    <xsl:when test="1">
                      <xsl:element name="caldate">
                      
                      </xsl:element>
                      <xsl:if test="$show_optional">
                        <xsl:element name="time">
                      
                        </xsl:element>
                      </xsl:if>  
                    </xsl:when>
                    <xsl:when test="0">
                      <xsl:element name="geolage">
                        <xsl:element name="geolscal">
                        
                        </xsl:element>
                        <xsl:element name="geolest">
                        
                        </xsl:element>
                        <xsl:if test="$show_optional">
                          <xsl:element name="geolun">
                          
                          </xsl:element>
                        </xsl:if>
                        <xsl:if test="$show_optional">
                          <xsl:element name="geolexpl">
                          
                          </xsl:element>
                        </xsl:if>
                      </xsl:element>
                    </xsl:when>
                  </xsl:choose>
                </xsl:element>
              </xsl:when>
              <xsl:when test="0">
                <xsl:element name="mdattim">
                  <xsl:element name="sngdate">
                    <xsl:choose>
                      <xsl:when test="1">
                        <xsl:element name="caldate">
                        
                        </xsl:element>
                        <xsl:if test="$show_optional">
                          <xsl:element name="time">
                           
                          </xsl:element>
                        </xsl:if>  
                      </xsl:when>
                      <xsl:when test="0">
                        <xsl:element name="geolage">
                          <xsl:element name="geolscal">
                        
                          </xsl:element>
                          <xsl:element name="geolest">
                        
                          </xsl:element>
                          <xsl:if test="$show_optional">
                            <xsl:element name="geolun">
                          
                            </xsl:element>
                          </xsl:if>
                          <xsl:if test="$show_optional">
                            <xsl:element name="geolexpl">
                          
                            </xsl:element>
                          </xsl:if>
                        </xsl:element>
                      </xsl:when>
                    </xsl:choose>  
                  </xsl:element>
                </xsl:element>
              </xsl:when>
              <xsl:when test="0">
                <xsl:element name="rngdates">
                  <xsl:choose>
                    <xsl:when test="1">
                      <xsl:element name="begdate">
                      
                      </xsl:element>
                      <xsl:if test="$show_optional">
                        <xsl:element name="begtime">
                      
                        </xsl:element>
                      </xsl:if>
                      <xsl:element name="enddate">
                      
                      </xsl:element>
                      <xsl:if test="$show_optional">
                        <xsl:element name="endtime">
                      
                        </xsl:element>
                      </xsl:if>
                    </xsl:when>
                    <xsl:when test="0">
                      <xsl:element name="beggeol">
                      
                      </xsl:element>
                      <xsl:element name="endgeol">
                      
                      </xsl:element>
                    </xsl:when>
                  </xsl:choose>
                </xsl:element>
              </xsl:when>
            </xsl:choose>
          </xsl:element>
          <xsl:element name="current">
          
          </xsl:element>
        </xsl:element>
        <xsl:element name="status">
          <xsl:element name="progress">
          
          </xsl:element>
          <xsl:element name="update">
          
          </xsl:element>
        </xsl:element>
        <xsl:if test="$show_optional">
          <xsl:element name="spdom">
          </xsl:element>
        </xsl:if>
        <xsl:element name="keywords">
          <xsl:element name="theme">
            <xsl:element name="themekt">
          
            </xsl:element>
            <xsl:element name="themekey">
          
            </xsl:element>
          </xsl:element>
        <xsl:if test="$show_optional">
          <xsl:element name="place">
            <xsl:element name="placekt">
          
            </xsl:element>
            <xsl:element name="placekey">
          
            </xsl:element>
          </xsl:element>
        </xsl:if>
        <xsl:if test="$show_optional">
          <xsl:element name="stratum">
            <xsl:element name="stratkt">
          
            </xsl:element>
            <xsl:element name="stratkey">
          
            </xsl:element>
          </xsl:element>
        </xsl:if>
        <xsl:if test="$show_optional">
          <xsl:element name="temporal">
            <xsl:element name="tempkt">
          
            </xsl:element>
            <xsl:element name="tempkey">
          
            </xsl:element>
          </xsl:element>
        </xsl:if>
        </xsl:element>
        <xsl:if test="$show_optional">
          <xsl:element name="taxonomy">
          </xsl:element>
        </xsl:if>
        <xsl:element name="accconst">
          access constraints
        </xsl:element>
        <xsl:element name="useconst">
          use constraints
        </xsl:element>
        <xsl:if test="$show_optional">
          <xsl:element name="ptcontac">
          </xsl:element>
        </xsl:if>
        <xsl:if test="$show_optional">
          <xsl:element name="browse">
          </xsl:element>
        </xsl:if>
        <xsl:if test="$show_optional">
          <xsl:element name="datacred">
          </xsl:element>
        </xsl:if>
        <xsl:if test="$show_optional">
          <xsl:element name="secinfo">
          </xsl:element>
        </xsl:if>
        <xsl:if test="$show_optional">
          <xsl:element name="native">
          </xsl:element>
        </xsl:if>
        <xsl:if test="$show_optional">
          <xsl:element name="crossref">
          </xsl:element>
        </xsl:if>
        <xsl:if test="$show_optional">
          <xsl:element name="tool">
          </xsl:element>
        </xsl:if>
     </xsl:element>
      
      <xsl:if test="$show_optional">
        <xsl:element name="dataqual">
      
        </xsl:element>
      </xsl:if>
      <xsl:if test="$show_optional">
        <xsl:element name="spdoinfo">
      
        </xsl:element>
      </xsl:if>
      <xsl:if test="$show_optional">
        <xsl:element name="spref">
      
        </xsl:element>
      </xsl:if>
      <xsl:if test="$show_optional">
        <xsl:element name="eainfo">
      
        </xsl:element>
      </xsl:if>
      <xsl:if test="$show_optional">
        <xsl:element name="distinfo">
      
        </xsl:element>
      </xsl:if>
      <xsl:element name="metainfo">
        <xsl:element name="metd">
      
        </xsl:element>
        <xsl:element name="metrd">
      
        </xsl:element>
        <xsl:if test="$show_optional">
          <xsl:element name="metfrd">
      
          </xsl:element>
        </xsl:if>  
        <xsl:element name="metc">
          <xsl:element name="cntinfo">
            <xsl:choose>
              <xsl:when test="1">
                <xsl:element name="cntperp">
                  <xsl:element name="cntper">
                  
                  </xsl:element>
                  <xsl:if test="$show_optional">
                    <xsl:element name="cntorg">
                  
                    </xsl:element>
                  </xsl:if>  
                </xsl:element>
              </xsl:when>
              <xsl:when test="0">
                <xsl:element name="cntorgp">
                  <xsl:element name="cntorg">
                  
                  </xsl:element>
                  <xsl:if test="$show_optional">
                    <xsl:element name="cntper">
                  
                    </xsl:element>
                  </xsl:if>  
                </xsl:element>
              </xsl:when>
            </xsl:choose>
            <xsl:if test="$show_optional">
              <xsl:element name="cntpos">
              
              </xsl:element>
            </xsl:if>
            <xsl:element name="cntaddr">
               <xsl:element name="addrtype">
                 
               </xsl:element>
               <xsl:if test="$show_optional">
                 <xsl:element name="address">
                 
                 </xsl:element>
               </xsl:if>
               <xsl:element name="city">
                 
               </xsl:element>
               <xsl:element name="state">
                 
               </xsl:element>
               <xsl:element name="postal">
                 
               </xsl:element>
               <xsl:if test="$show_optional">
                 <xsl:element name="country">
                 
                 </xsl:element>
               </xsl:if>
            </xsl:element>
            <xsl:element name="cntvoice">
              
            </xsl:element>
            <xsl:if test="$show_optional">
              <xsl:element name="cnttdd">
              
              </xsl:element>
            </xsl:if>
            <xsl:if test="$show_optional">
              <xsl:element name="cntfax">
              
              </xsl:element>
            </xsl:if>
            <xsl:if test="$show_optional">
              <xsl:element name="cntemail">
              
              </xsl:element>
            </xsl:if>
            <xsl:if test="$show_optional">
              <xsl:element name="hours">
              
              </xsl:element>
            </xsl:if>
            <xsl:if test="$show_optional">
              <xsl:element name="cntinst">
              
              </xsl:element>
            </xsl:if>
          </xsl:element>
        </xsl:element>
        <xsl:element name="metstdn">
      
        </xsl:element>
        <xsl:element name="metstdv">
      
        </xsl:element>
        <xsl:if test="$show_optional">
          <xsl:element name="mettc">
      
          </xsl:element>
        </xsl:if>  
        <xsl:if test="$show_optional">
          <xsl:element name="mettac">
      
          </xsl:element>
        </xsl:if>  
        <xsl:if test="$show_optional">
          <xsl:element name="mettuc">
      
          </xsl:element>
        </xsl:if>  
        <xsl:if test="$show_optional">
          <xsl:element name="metsi">
      
          </xsl:element>
        </xsl:if>  
        <xsl:if test="$show_optional">
          <xsl:element name="metextns">
            <xsl:element name="onlink">
            
            </xsl:element>
            <xsl:element name="metprof">
            
            </xsl:element>
          </xsl:element>
        </xsl:if>  
      </xsl:element>      
    </xsl:element>
  
  </xsl:template>

  
</xsl:stylesheet>
