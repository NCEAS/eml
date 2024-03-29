# How to handle EML2 `references` elements in XSLT transformations

One of the features in EML2.0 that adds a great deal of flexibility is the use of the `references` element. This element can occur in a number of places. It basic purpose is to allow the use of a "pointer" to another part of the EML2 document so that information in a subtree need not be duplicated. An obvious use is to avoid repeating the information in a 'responsibleParty' module. If `references` to some individual occurs in several places (e.g. as both the "Creator" and a 'Contact") then it is desirable to only have the information in a single place. This not only makes editing/revision easier and reduces the size of an EML2 document but also indicates that the descriptions are of the SAME party (not someone with the same name).

The mechanism for using `references` is to assign some 'id' attribute to the head of a subtree at one location and then make this 'id' value the content of a `references` node elsewhere in the EML2 document where a subtree of the same type (eg ResponsibleParty) occurs. A look at the XMLSchema for EML2 shows that the `references` element just appears as alternate choice to some subtree. The 'id value' contained in the `references` element should correspond to the 'id' attribute of some other element in the eml document (and these assigned id values are assumed to be unique).

A common task that one may want to do with an EML2 document is to apply an XSLT transformation to extract data or display its contents. One example is to create an html display of the document's content. Another is to convert the EML2 to another format like fgdc/nbii. 

Typically in such XSLT transformations, one want to copy data from some Xpath in the original document to other location or structure. But how does one handle the case where a `references` node appears rather than the subtree with the wanted data? The transformation can specify an XPath down to some subnode and then continue down the subtree if the informatin is inline, but how do you handle the case where only a `references` node appears rather than the subtree? The following describes one method that can be used. (There may be much better ones.)

I started by defining an XSLT variable called 'ids' at the top of my stylesheet.

  <xsl:variable name="ids" select="//*[@id!='']"/>

The $ids variable is now a nodeset of all nodes in the document that have 'id' attributes. One can then loook for nodes with a given id from within this variable rather than having to search the entire document every time a reference is encountered. [The ids are supposed to all be unique, so the list should have all of them.]   

Now consider an example as shown in the XSLT fragment below which is creating an element called 'citeinfo' and then trying to get information from each "/eml:eml/dataset/creator" in an eml-document. The example fragment creates an xsl:variable called 'cc'. When there is no `references` child of the "/eml:eml/dataset/creator" element, the variable is just set to a copy of the current node ("."). When './references' is NOT an empty string, a copy of the referenced subtree is created by the 

<xsl:copy-of select="$ids[@id=$ref_id]"/>

statement. 


          <xsl:element name="citeinfo">
            <xsl:for-each select="/eml:eml/dataset/creator">
              <xsl:variable name="cc">
                <xsl:choose>
                  <xsl:when test="./references!=''">
                    <xsl:variable name="ref_id" select="./references"/>
                    <!-- current element just references its contents 
                    There should only be a single node with an id attribute
                    which matches the value of the references element -->
                    <xsl:copy-of select="$ids[@id=$ref_id]"/>
                  </xsl:when>
                  <xsl:otherwise>
                    <!-- no references tag, thus use the current node -->
                    <xsl:copy-of select="."/>
                  </xsl:otherwise>
                </xsl:choose>
              </xsl:variable>

              <xsl:element name="origin">
              <!-- 'origin' should correspond to the name of the 'creator' RP in eml2 -->
                  <xsl:choose>
                    <xsl:when test="xalan:nodeset($cc)//individualName/surName!=''">
                      <xsl:value-of select="xalan:nodeset($cc)//individualName/surName"/>
                    </xsl:when>
              ...



Once the varible $cc has been created, it should contain either the current subtree or the one referenced in the `references` element. The above code fragment then creates an element named 'origin' and then tries to get information from the children of the $cc variable to set the value(s) of the 'origin' subtree. Note the statement      

        <xsl:value-of select="xalan:nodeset($cc)//individualName/surName"/>

The first part of this will be explained below, but basically the expression before the '//' just gives the top node of the subtree in the '$cc' variable, while the rest of the expression obtains the value of the element 'surname' in the subtree path 'individualName/surName'. 


Now consider the 'xalan:nodeset($cc) part of this path. I originally thought that I could just use

          $cc//individualName/surName        

to specify the path, but in XSLT1.0, a variable is a NodeSet rather than a tree. There is thus an error due to some type incompatibilties when tries to specify child nodes. This problem is (reportedly) being corrected in the newer XSLT specs, but in the meantime most of the XSLT processors have an extension function that converts a NodeSet to a tree so that expressions for child elements will operate. The expression 'xalan:nodeset($cc)' just converts the variable $cc to the correct type for use in a path to get a subelement. This is a Xalan specific function, but other XSLT engines have similar extensions.

Dan Higgins
15 May 2003

