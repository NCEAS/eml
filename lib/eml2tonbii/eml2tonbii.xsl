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
              
              </xsl:element>
            </xsl:if>
             <xsl:if test="$show_optional">
              <xsl:element name="pubinfo">
              
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
      
          </xsl:element>
        </xsl:if>  
      </xsl:element>      
    </xsl:element>
  
  </xsl:template>

  
</xsl:stylesheet>
