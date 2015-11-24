# /usr/bin/perl
# removeAnnotaion.pl
# scripte to remove all of the annotation tags (xs:annotation) 
# from an XML schema file.
# -------------------------------------------------------------------
# usage: perl removeAnnotation.pl <inputfile.xsd >outputfile.xsd

while(<>)
{
  if(m!<xs:annotation>*!)
  {
    while(not m!</xs:annotation>*!)
    {
      s!!!;
      $_ = <>;
    }
    # print '<!-- Annotation for this element removed -->';
  }
  s!</xs:annotation>!!;
  print;
}
s!\n\s*!!g;
