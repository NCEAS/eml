<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:template match="/">
       <table border="1" cellpadding="5">
         <tr><td><B>Element Name</B></td><td><B>Value</B></td></tr> 
        <xsl:apply-templates />   
      </table>
  </xsl:template>

  <xsl:template match="*">
    <xsl:for-each select="./*">
        <tr><td><xsl:value-of select="local-name()"/></td>
            <td><xsl:value-of select="."/></td></tr>
    </xsl:for-each>
  </xsl:template>

</xsl:stylesheet>
