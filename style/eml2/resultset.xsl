<?xml version="1.0"?>
<!--
  *  '$RCSfile: resultset.xsl,v $'
  *      Authors: Matt Jones, CHad Berkley
  *    Copyright: 2000 Regents of the University of California and the
  *               National Center for Ecological Analysis and Synthesis
  *  For Details: http://www.nceas.ucsb.edu/
  *
  *   '$Author: berkley $'
  *     '$Date: 2003-06-03 21:41:35 $'
  * '$Revision: 1.1 $'
  *
  * This program is free software; you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation; either version 2 of the License, or
  * (at your option) any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with this program; if not, write to the Free Software
  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
  *
  * This is an XSLT (http://www.w3.org/TR/xslt) stylesheet designed to
  * convert an XML file showing the resultset of a query
  * into an HTML format suitable for rendering with modern web browsers.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html"/>
  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css"
              href="@style-path@/@name@.css" />
        <script language="JavaScript">
          <![CDATA[
          function submitform(action,form_ref) {
              form_ref.action.value=action;
              form_ref.abstractpath.value="";
              form_ref.qformat.value="@name@";
              form_ref.submit();
          }
          function submitform1(action, abstractpath, form_ref) {
              form_ref.action.value=action;
              form_ref.abstractpath.value=abstractpath;
              form_ref.qformat.value="";
              form_ref.submit();
          }
          function submitform2(action, qformat, form_ref) {
              form_ref.action.value=action;
              form_ref.qformat.value=qformat;
              form_ref.abstractpath.value="";
              form_ref.submit();
          }
          ]]>
        </script>
      </head>

      <body>
        <div class="spacing" align="center">
          <table cellspacing="0" width="100%" border="0">
            <tr>
              <td class="header-title">
                <div class="header-title">
                  @header-title@
                </div>
                 <div class="header-subtitle">
                  @header-subtitle@
                </div>
              </td>
              <td class="header-image" rowspan="1">
                <div class="header-image">
                  <img class="header" src="@html-path@/@header-image@"
                    alt="@header-image-alt@"
                     border="0" />
                </div>
              </td>
            </tr>
            <tr>
              <td class="header-menu" colspan="2">
                <div class="header-menu">
                  @header-menu@
                </div>
              </td>
            </tr>
            <tr>
              <td class="header-submenu" colspan="2">
                <div class="header-submenu">
                  &#160; <!-- &nbsp; that is XML compliant -->
                </div>
              </td>
            </tr>
          </table>
        </div>

        <table width="760" border="0" cellspacing="0" cellpadding="0">
          <tr><td colspan="4"><hr /></td></tr>
          <tr><td colspan="2"><br /><b>View MARINE Datasets</b></td>
              <td colspan="2"><br />
            <form action="@html-path@/servlet/metacat" method="POST">
             <input type="hidden" name="operator" value="INTERSECT" />
             Refine data search:
               <input type="text" name="anyfield" size="14">
                 <xsl:attribute name="value">
                 <xsl:value-of
                      select="resultset/query/pathquery/querygroup/queryterm/value" />
                 </xsl:attribute>
               </input>
             <input type="hidden" name="originator/organizationName"
                    value="MARINE"/>
             <input type="hidden" name="action" value="query" />
             <input type="hidden" name="qformat" value="@name@" />
             <input type="hidden" name="operator" value="UNION" />

             <input type="hidden" name="returnfield"
              value="originator/individualName/surName" />
             <input type="hidden" name="returnfield"
              value="originator/individualName/givenName" />
             <input type="hidden" name="returnfield"
              value="originator/organizationName" />
             <input type="hidden" name="returnfield"
              value="title" />
             <input type="hidden" name="returnfield"
              value="keyword" />
             <input type="hidden" name="returndoctype"
              value="-//ecoinformatics.org//eml-dataset-2.0.0beta6//EN" />
             <input type="hidden" name="returndoctype"
              value="-//ecoinformatics.org//eml-dataset-2.0.0beta4//EN" />
             <input type="hidden" name="returndoctype"
              value="-//NCEAS//resource//EN" />
             <input type="hidden" name="returndoctype"
              value="-//NCEAS//eml-dataset//EN" />
           </form>
          </td>
        </tr>
       </table>

       <p><xsl:number value="count(resultset/document)" /> data sets found.</p>
       <!-- This tests to see if there are returned documents,
            if there are not then don't show the query results -->
       <xsl:if test="count(resultset/document) &gt; 0">
        <table width="760">
           <tr>
             <th class="tablehead">Title</th>
             <th class="tablehead">Contacts</th>
             <th class="tablehead">Organization</th>
             <th class="tablehead">Keywords</th>
           </tr>

         <xsl:for-each select="resultset/document">
           <xsl:sort select="title"/>
           <tr valign="top">
             <xsl:attribute name="class">
               <xsl:choose>
                 <xsl:when test="position() mod 2 = 1">rowodd</xsl:when>
                 <xsl:when test="position() mod 2 = 0">roweven</xsl:when>
               </xsl:choose>
             </xsl:attribute>

             <td>
               <xsl:value-of select="./param[@name='title']"/>
               <br/>
               <p>

               <form action="@html-path@/servlet/metacat" method="POST">
                 <xsl:attribute name="name">
                   <xsl:value-of select="translate(./docid,'.','')"/>
                 </xsl:attribute>
                 <!-- abstractpath tells the servlet where the abstract is in
                      the document.  The % sign is used because the path could
                      be resource/literature or resource/dataset. -->
                 <!-- specified down IN javascript:submitform1('read',abstractpath,docid) -->
                 <input type="hidden" name="abstractpath" />
                 <!-- specified down IN javascript:submitform2('read','zip',docid) -->
                 <input type="hidden" name="qformat" value="@name@"/>
                 <input type="hidden" name="action" value="read"/>
                 <input type="hidden" name="docid">
                   <xsl:attribute name="value">
                     <xsl:value-of select="./docid"/>
                   </xsl:attribute>
                 </input>
                 <xsl:for-each select="./relation">
                   <input type="hidden" name="docid">
                     <xsl:attribute name="value" >
                       <xsl:value-of select="./relationdoc" />
                     </xsl:attribute>
                   </input>
                 </xsl:for-each>

                 <a>
                   <xsl:attribute name="href">javascript:submitform('read',document.<xsl:value-of select="translate(./docid, '.', '')"/>)</xsl:attribute>
                   Detailed Dataset Information
                 </a><br />
               </form>
               </p>
             </td>
             <td>
               <xsl:for-each select="./param[@name='originator/individualName/surName']" >
                 <xsl:value-of select="." />
                 <br/>

               </xsl:for-each>
               <xsl:text> </xsl:text>
             </td>
             <td>
                 <xsl:value-of select=
                 "./param[@name='originator/organizationName']"/>
                 <xsl:text> </xsl:text>
             </td>

             <td>
               <xsl:for-each
                select="./param[@name='keyword']">
                 <xsl:value-of select="." />
                 <br/>
               </xsl:for-each>
               <xsl:text> </xsl:text>
             </td>
           </tr>

          </xsl:for-each>
          </table>

       </xsl:if>
      </body>
    </html>
  </xsl:template>

</xsl:stylesheet>
