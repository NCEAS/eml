package code;
import java.io.*;
import java.util.*;
class  ReadTiming
{
	public  void  readTable(FileReader fileReader) throws IOException
	{
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter("Output3.txt",false);
		String strTemp;
		String strTemp1="";
		String strTemp2[]={"","","","","","","","","","","",""}; // this temporary storage for 
		char temp[];
		String strTag1[]={"","","","","","","","","","",""}; // this temporary storage for tags
		int i=0;int k=0;int m=0;
	
	    while ((strTemp=bufferedReader.readLine())!=null)
	    {   
			if (strTemp.startsWith("<Timing>"))
			{  
			  // Inside timing info type1 file
			  fileWriter.write("<Timing>"+"\n");

			  while (!(strTemp=bufferedReader.readLine()).trim().startsWith("</Timi"))
			  {
				 
				  if (strTemp.trim().length()==0){m=0;}
				 	if (strTemp.trim().length()!=0)
				 	{
				 	    strTemp=strTemp.trim();
					  	StringTokenizer st = new StringTokenizer(strTemp,"\t");
					
						//System.out.println(st1.countTokens());
						
						if (i==0)
						{
						
						while (st.hasMoreTokens())
						{
						try
							{
					          strTag1[k]=st.nextToken();
							}
							catch (Exception e)
							{
							}
						 	
						  k++;
				        }
						k=0;
					
						}
						else
						{
								
							while (st.hasMoreTokens())
							{
							  //System.out.println(st.countTokens());
								  strTemp2[k]=st.nextToken();
							  	  //strTemp2[k]=strTemp2[k].trim();
							  //System.out.println(strTemp2[k]);
					          	
							 
						      k++;

				             }
							
								//System.out.println((k));
								for (int g =0;g<k ;g++ )
								{
								 try
									{
								 if (m==0)
								 {
									//System.out.println("<"+strTag1[0]+">" + " " +strTemp2[0] + "</"+strTag1[0]+">");
								   if (g==0)
								   {
									    fileWriter.write("<"+strTag1[0]+">" + " " +strTemp2[0] + "</"+strTag1[0]+">"+"\n");
								   }
								   else  if (strTemp2[g].trim().charAt(0)!='|')
									    fileWriter.write("<site>" + strTag1[g] + " " + "</site>\n" + "<date>"+strTemp2[g] + "</date>"+"\n");
								  
								  
								 }
								 else 
									{
									 if (strTemp2[g].trim().charAt(0)!='|')
									 {
										 
										fileWriter.write("<site>"+strTag1[g+1]+"</site>\n"+  "<date>"+" " +strTemp2[g].trim() + "</date>"+"\n");
									 }
									 
									  
									}
									}
									catch(Exception e){}
								}
							  for (int g=0;g<k ;g++ )
							  {
								  strTemp2[g]="";
							  }
							 // printing them now
							 
						
							 m=1;
					    	k=0; 
						}
						i=1;
					}	
					 
				 
				 
				//fileWriter.write(strTemp+"\n");
				 }

				 
	
			  }
			  fileWriter.write(strTemp+"\n");
			}
				
	    
      
		 bufferedReader.close();
		 fileReader.close();
		 fileWriter.close();

	}

public  void  readTable1(FileReader fileReader) throws IOException
	{
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter("Output3.txt",false);
		String strTemp;
		String strTemp1="";String strTag="";
		String strTemp2[]={"","","","","","","","","","","",""}; // this temporary storage for 
		char temp[];
		String strTag1[]={"","","","","","","","","","",""}; // this temporary storage for tags
		int i=0;int k=0;int m=0;int c=0;
	
	    while ((strTemp=bufferedReader.readLine())!=null)
	    {   
		   if (strTemp.trim().startsWith("<Timing>"))
		   {
			  // fileWriter.write("<Timing>\n");
			   fileWriter.write(strTemp+"\n");
			  while (!(strTemp=bufferedReader.readLine()).trim().startsWith("</Timi"))
			   {
				   
				   if (strTemp.trim().length()!=0)
				   {
						if (c==0)
						{
							fileWriter.write(strTemp+"\n");
							c=1;
							  strTemp=bufferedReader.readLine();
						}
				   }
				   if (strTemp.trim().length()!=0)
				   {
				   
				   if ((strTemp.indexOf("LOCATION")!=-1)||(strTemp.trim().startsWith("DATE")))
				   
					   strTemp=bufferedReader.readLine();
				   
				   if ((strTemp.toUpperCase().startsWith("JAN"))||(strTemp.toUpperCase().startsWith("FEB"))||(strTemp.toUpperCase().startsWith("MAR"))
                   ||(strTemp.toUpperCase().startsWith("APR"))||(strTemp.toUpperCase().startsWith("MAY"))||(strTemp.toUpperCase().startsWith("JUN"))||(strTemp.toUpperCase().startsWith("JUL"))||(strTemp.toUpperCase().startsWith("AUG"))
					   ||(strTemp.toUpperCase().startsWith("SEP"))||(strTemp.toUpperCase().startsWith("OCT"))||(strTemp.toUpperCase().startsWith("NOV"))||(strTemp.toUpperCase().startsWith("DEC")))
					   {
					   fileWriter.write("<site>"+strTag.trim()+"</site>\n");
					   fileWriter.write("<date>"+strTemp.trim()+"</date>\n");
					   }

				   
				   else  if (strTemp.trim().length()!=0)
					            strTag=strTemp;
				
					   
					   
				   }
			   
			   }
		
		   }
              	   fileWriter.write(strTemp+"\n");

			}
				
	    
      
		 bufferedReader.close();
		 fileReader.close();
		 fileWriter.close();

	}


public  void  readTable2(FileReader fileReader) throws IOException
	{
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter("Output3.txt",false);
		String strTemp;
		String strTemp1="";
		String strTemp2[]={"","","","","","","","","","","",""}; // this temporary storage for 
		char temp[];
		String strTag1[]={"","","","","","","","","","",""}; // this temporary storage for tags
		int i=0;int k=0;int m=0;
	   
	    while ((strTemp=bufferedReader.readLine())!=null)
	    {    
		   
			
			 
			  
		   
              	   
		    fileWriter.write(strTemp+"\n");

		}
				
	    
      
		 bufferedReader.close();
		 fileReader.close();
		 fileWriter.close();

	}

	public  void  readTable3(FileReader fileReader) throws IOException
	{
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter("Output3.txt",false);
		String strTemp;
		String strTemp1="";
		String strTemp2[]={"","","","","","","","","","","",""}; // this temporary storage for 
		char temp[];
		String strTag1[]={"","","","","","","","","","",""}; // this temporary storage for tags
		int i=0;int k=0;int m=0;
	   
	    while ((strTemp=bufferedReader.readLine())!=null)
	    {    
		   
			 
              	   
		    fileWriter.write(strTemp+"\n");

		}
				
	    
      
		 bufferedReader.close();
		 fileReader.close();
		 fileWriter.close();

	}




}
