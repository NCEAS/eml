package code;
import java.io.*;
import java.util.StringTokenizer;
class TextToXml
{
	public void readTxt(FileReader fileReader) throws IOException
	{
		 BufferedReader bufferedReader=new BufferedReader(fileReader);
		 FileWriter fileWriter = new FileWriter("Output1.txt",false);
		 String strTemp;
		 String strTemp1 ="";
		 String prevTag="";
		 String prevTag1="";
		 int flag=0;
		 System.out.println("HERE i am ");
		 while ((strTemp = bufferedReader.readLine())!=null)
		 {
			    strTemp1 = strTemp;   
			 	strTemp=strTemp.trim();
			 
		      
				if (strTemp.startsWith("FILE NAME"))
				{
					
					System.out.println(strTemp);

					fileWriter.write("<FileName>\n");
				    strTemp=strTemp.replaceFirst("FILE NAME:"," ");
					System.out.println(strTemp);
					prevTag="</FileName>\n";
				}

				if (strTemp.startsWith("PRINCIPAL INVE"))
				{
					
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<PrincipalInvestigator>\n");
				    strTemp= strTemp.replaceAll("PRINCIPAL INVESTIGATOR:","");
					fileWriter.write(strTemp+"\n");
				
				    strTemp=bufferedReader.readLine();
					prevTag1="</PrincipalInvestigator>\n";
				

				}
				if ((strTemp.startsWith("&"))&&(flag==0))
			    {
					fileWriter.write(prevTag+"\n");
					fileWriter.write("</PrincipalInvestigator>\n");
					fileWriter.write("<PrincipalInvestigator>\n");
			
					fileWriter.write(strTemp);
				
				    strTemp=bufferedReader.readLine();
					prevTag1="</PrincipalInvestigator>\n";
					flag=1;
				}
				if (strTemp.startsWith("Address:"))
				{
					
				
					fileWriter.write("<Address>\n");
				    strTemp=strTemp.replaceAll("Address:","");
					prevTag="</Address>\n";

				}

				if (strTemp.startsWith("Phone"))
				{
					fileWriter.write(prevTag+"\n");
					
					fileWriter.write("<Phone>\n");
				    strTemp=strTemp.replaceAll("Phone:","");
					prevTag="</Phone>\n";
				}


				if ((strTemp.startsWith("E-mail"))||(strTemp.startsWith("E-Mail")))
				{
					if ((prevTag!="</Email>\n")&&(prevTag!="</Others>\n"))
					{
						fileWriter.write(prevTag+"\n");
					}
					if (!(strTemp.trim().startsWith("Not Applicable"))&&(strTemp.trim().length()!=0))
					{
						fileWriter.write("<Email>\n");
				    strTemp=strTemp.replaceAll("E-mail:","");
					prevTag="</Email>\n";

					}
					
				}

				if (strTemp.startsWith("OTHERS:"))
				{
                    flag=2;
					fileWriter.write(prevTag+"\n");
					fileWriter.write(prevTag1+"\n");
					fileWriter.write("<Others>\n");
				    strTemp= strTemp.replaceAll("OTHERS:","");
					if (strTemp.indexOf("Not Applicable")!=-1)
					{
						strTemp=bufferedReader.readLine();
					}
				    prevTag ="</Others>\n";
					
				}

				if (strTemp.startsWith("KEYWORDS:"))
				{
				    if (prevTag!="</Others>\n")
				    {
						System.out.println("fick");
						System.out.println(prevTag);
						fileWriter.write(prevTag+"\n");
				    }
					fileWriter.write("</Others>\n");
					fileWriter.write("<Keywords>\n");
				    strTemp= strTemp.replaceAll("KEYWORDS:","");
					prevTag="</Keywords>\n";

				}

				if (strTemp.startsWith("ABSTRACT:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Abstract>\n");
				    strTemp= strTemp.replaceAll("ABSTRACT:","");
					prevTag="</Abstract>\n";

				}

				if (strTemp.startsWith("VARIABLES:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Variables>\n");
				    strTemp= strTemp.replaceAll("VARIABLES:","");
					prevTag="</Variables>\n";

				}

				if (strTemp.startsWith("RESEARCH LOCATION:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<ResearchLocation>\n");
				    strTemp= strTemp.replaceAll("RESEARCH LOCATION:","");
					prevTag="</ResearchLocation>\n";

				}
                if (strTemp.startsWith("METHODS:"))
				{
					
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Methods>\n");
				    strTemp= strTemp.replaceAll("METHODS:","");
					prevTag="</Methods>\n";

				}

				if (strTemp.startsWith("TIMING:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Timing>");
				    strTemp= strTemp.replaceAll("TIMING:","");
					fileWriter.write(strTemp+"\n");
					while (!(strTemp=bufferedReader.readLine()).trim().startsWith("CITATIONS"))
					{
						fileWriter.write(strTemp+"\n");
					}

					prevTag="</Timing>\n";
					
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Citations>\n");
				    strTemp= strTemp.replaceAll("CITATIONS:","");
					prevTag="</Citations>\n";

				}

				if (strTemp.startsWith("CITATIONS"))
				{

				}
                 if (strTemp.startsWith("COMMENTS:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Comments>\n");
				    strTemp= strTemp.replaceAll("COMMENTS:","");
					prevTag="</Comments>\n";

				}
				if (strTemp.startsWith("STATUS:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Status>\n");
				    strTemp= strTemp.replaceAll("STATUS:","");
					prevTag="</Status>\n";

				}
				if (strTemp.startsWith("VARIABLE DESCRIPTION:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<VariableDescription>\n");
				    strTemp= strTemp.replaceAll("VARIABLE DESCRIPTION:","");
					prevTag="</VariableDescription>\n";

     			}

				if (strTemp.startsWith("LOG:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Log>\n");
				    strTemp= strTemp.replaceAll("LOG:","");
					prevTag="</Log>\n";

				}

				if (strTemp.startsWith("NOTE:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Note>\n");
				    strTemp= strTemp.replaceAll("NOTE:","");
			


				}



				fileWriter.write(strTemp+"\n");
			

			
		
			
		 }
		 fileWriter.write("</Note>\n");
		 bufferedReader.close();
		 fileReader.close();
		 fileWriter.close();
	}

	public void readTxt1(FileReader fileReader) throws IOException
	{
		 BufferedReader bufferedReader=new BufferedReader(fileReader);
		 FileWriter fileWriter = new FileWriter("Output1.txt",false);
		 String strTemp;
		 String strTemp1 ="";
		 String prevTag="";
		 int flag=0; int flag1=0;
		 int k=0;int k1=0;
		 while ((strTemp = bufferedReader.readLine())!=null)
		 {
			    strTemp1 = strTemp;   
			 	strTemp=strTemp.trim();
			 
		
				if (strTemp.startsWith("FILE NAME"))
				{
					
					System.out.println(strTemp);

					fileWriter.write("<FileName>");
				    strTemp=strTemp.replaceFirst("FILE NAME:"," ");
					fileWriter.write(strTemp);
					 
					   
					 
					prevTag="</FileName>";
				}

				if (strTemp.trim().startsWith("PRINCIPAL INVE"))
				{
					
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<PrincipalInvestigator>");
				    strTemp= strTemp.replaceAll("PRINCIPAL INVESTIGATOR:","");
					fileWriter.write(strTemp+"\n");
				
				    while (!(strTemp=bufferedReader.readLine()).trim().startsWith("OTHERS"))
				    {
						if (flag==0)
						{
							if ((strTemp.indexOf("Address:")==-1)&&(strTemp.indexOf("E-Ma")==-1)&&(strTemp.indexOf("Phone:")==-1)&&(strTemp.trim().length()!=0))
							{
								System.out.println("herew");
								fileWriter.write(strTemp+"\n");
								flag=1;
								k=0;
							}
						}
						else
						{
						   if ((strTemp.indexOf("Address:")==-1)&&(strTemp.indexOf("E-Ma")==-1)&&(strTemp.indexOf("Phone:")==-1)&&(strTemp.trim().length()!=0))
							{
								
								while ((strTemp.indexOf("@")==-1)&&(strTemp.indexOf("(")==-1)&&(strTemp.indexOf("&")==-1)&&((strTemp.indexOf("_")==-1))&&(strTemp.indexOf("+")==-1))
								{ 
								if (k==0)
								{
									fileWriter.write("\n<Address>\n"+strTemp+"\n");
									k=1;
									strTemp=bufferedReader.readLine();
								}
								else
								{
								  fileWriter.write(strTemp+"\n");
								  strTemp=bufferedReader.readLine();
								
								}
								
								}
								if ((strTemp.indexOf("(")!=-1)||(strTemp.indexOf("+")!=-1))
								{
								 fileWriter.write("</Address>\n");
								 strTemp=strTemp.replaceAll("Phone:","");
								fileWriter.write("<Phone>\n"+strTemp+"\n"+"</Phone>\n");
								 
								}
								if (strTemp.indexOf("@")!=-1)
								{
								   strTemp=strTemp.replaceAll("E-Mail:","");
								  fileWriter.write("<Email>\n"+strTemp+"\n"+"</Email>\n");
								  flag=1;
								}
								
							    if (strTemp.indexOf("&")!=-1)
								{ fileWriter.write("</PrincipalInvestigator>\n");
								   fileWriter.write("<PrincipalInvestigator>\n");
									flag=0;
								}
							}
							
						}


				    }

					prevTag="</PrincipalInvestigator>";

				}
				

				if (strTemp.trim().startsWith("OTHERS:"))
				{
					fileWriter.write(prevTag+"\n");
				
				    strTemp= strTemp.replaceAll("OTHERS:","");
					prevTag="</Others>";
					fileWriter.write("<Others>");
					try
						{
				         while  (((strTemp=bufferedReader.readLine())!=null)&&(!(strTemp.startsWith(" KEYWORDS:"))))
				     	 {
						   System.out.println(strTemp);
					       if (flag1==0)
						   {
							
							 if ((strTemp.indexOf("Address:")==-1)&&(strTemp.indexOf("Phone:")==-1)&&(strTemp.trim().length()!=0)&&(strTemp.indexOf("E-Mail")==-1))
							 {
								System.out.println("Howdy");
								fileWriter.write(strTemp+"\n");
								flag1=1;
								k1=0;
							 }
						   }
						   else
						   {
						     if ((strTemp.indexOf("Address:")==-1)&&(strTemp.indexOf("Phone:")==-1)&&(strTemp.trim().length()!=0)&&(strTemp.indexOf("E-Mail:")==-1))
							 {
								System.out.println(strTemp);
							   	try
								{
								
								  while ((strTemp.indexOf("@")==-1)&&(strTemp.indexOf("(")==-1)&&(strTemp.indexOf("&")==-1)&&(strTemp!=null)&&((strTemp.indexOf("+")==-1)))
								  { 
								    if (k1==0)
								    {
									 fileWriter.write("<Address>\n"+strTemp+"\n");
									 k1=1;
									 strTemp=bufferedReader.readLine();
							  	    }
								    else
								   {  
								     fileWriter.write(strTemp+"\n");
								     strTemp=bufferedReader.readLine();
							       }
								
								  }

								    
								
								   if (strTemp.indexOf("@")!=-1)
								   {
								    fileWriter.write("<Email>\n"+strTemp+"</Email>\n");
								    flag1=1;
								    }
								    if ((strTemp.indexOf("(")!=-1))
								    {
								     fileWriter.write("</Address>\n");
								     fileWriter.write("<Phone>\n"+strTemp+"</Phone>\n");
									 fileWriter.write("</Others>\n");
								 	}
							        if ((strTemp.indexOf("&")!=-1)||(strTemp.trim().indexOf("AND")!=-1))
								    { 
									 flag1=0;
									 	 fileWriter.write("<Others>\n");
									}
							   }
							
							    catch (Exception e){}
							}
									
						   }
						  
							  
						}
				    
					
					}
					
					   catch (Exception e){}
						

				}
                if (strTemp!=null)
                {
               

				if (strTemp.startsWith("ABSTRACT:"))
				{
					//fileWriter.write(prevTag+"\n");
					fileWriter.write("<Abstract>");
				    strTemp= strTemp.replaceAll("ABSTRACT:","");
					prevTag="</Abstract>";

				}

				if (strTemp.startsWith("VARIABLES:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Variables>");
				    strTemp= strTemp.replaceAll("VARIABLES:","");
					prevTag="</Variables>";

				}

				if (strTemp.startsWith("RESEARCH LOCATION:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<ResearchLocation>");
				    strTemp= strTemp.replaceAll("RESEARCH LOCATION:","");
					prevTag="</ResearchLocation>";

				}
                if (strTemp.startsWith("METHODS:"))
				{
					
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Methods>");
				    strTemp= strTemp.replaceAll("METHODS:","");
					prevTag="</Methods>";

				}

				if (strTemp.startsWith("TIMING:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Timing>");
				    strTemp= strTemp.replaceAll("TIMING:","");
					fileWriter.write(strTemp+"\n");
					while (!(strTemp=bufferedReader.readLine()).trim().startsWith("CITATIONS"))
					{
						fileWriter.write(strTemp+"\n");
					}

					prevTag="</Timing>";
					
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Citations>");
				    strTemp= strTemp.replaceAll("CITATIONS:","");
					prevTag="</Citations>";

				}

				if (strTemp.startsWith("CITATIONS"))
				{

				}
                 if (strTemp.startsWith("COMMENTS:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Comments>");
				    strTemp= strTemp.replaceAll("COMMENTS:","");
					prevTag="</Comments>";

				}
				if (strTemp.startsWith("STATUS:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Status>");
				    strTemp= strTemp.replaceAll("STATUS:","");
					prevTag="</Status>";

				}
				if (strTemp.startsWith("VARIABLE DESCRIPTION:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<VariableDescription>\n");
				    strTemp= strTemp.replaceAll("VARIABLE DESCRIPTION:","");
					prevTag="</VariableDescription>";

     			}

				if (strTemp.startsWith("LOG:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Log>");
				    strTemp= strTemp.replaceAll("LOG:","");
					prevTag="</Log>";

				}

				if (strTemp.startsWith("NOTE:"))
				{
					fileWriter.write(prevTag+"\n");
					fileWriter.write("<Note>");
				    strTemp= strTemp.replaceAll("NOTE:","");
			


				}
                }


			
			
            if (strTemp!=null)
            {
				fileWriter.write(strTemp+"\n");
            }
			
		
			
		 }

		 bufferedReader.close();
		 fileReader.close();
		 fileWriter.close();
	}





}
