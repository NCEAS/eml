package code;
import java.io.*;
import java.util.*;

class  ReadVariableDescription
{
	public void readTable(FileReader fileReader) throws IOException
    {

        BufferedReader bufferedReader=new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter("Output2.txt",false);
		String strTemp;
		String strTags[]={"","","","","","","",""};
		int i=0;

		while ((strTemp=bufferedReader.readLine())!=null)
		{
			if (strTemp.startsWith("<VariableDes"))
			{
			   fileWriter.write(strTemp+'\n');
				
				while (!(strTemp.startsWith("</VariableDescription>")))
				{
				  strTemp=bufferedReader.readLine();
					//read the first eight stuff
					if ((strTemp.length()>0)&&(i<8))
					{
					  
					  strTags[i]=strTemp;
					  i++;
					  
					}
					else
					{
						if ((strTemp.length()>0)&&!(strTemp.startsWith("</VariableDescription>")))
						{
							fileWriter.write("<"+strTags[i % 8]+">"+strTemp+"</"+strTags[i % 8] + ">" + '\n');
							i++;
						}
					}
					
				}
				
			
			}
			 fileWriter.write(strTemp+'\n');
		}

  
		 bufferedReader.close();
		 fileReader.close();
		 fileWriter.close();
	}


  public void readTable1(FileReader fileReader) throws IOException
    {
	   BufferedReader bufferedReader=new BufferedReader(fileReader);
	   FileWriter fileWriter = new FileWriter("Output2.txt",false);
	   String strTemp;

		String strTemp1="";
		String strTemp2[]={"","","","","","","","","","","",""}; // this temporary storage for 
		char temp[];
		String strTag1[]={"","","","","","","","","","","",""}; // this temporary storage for tags
		int i=0;int k=0;int m=0;
	
	    while ((strTemp=bufferedReader.readLine())!=null)
	    {   
			if (strTemp.startsWith("<VariableDescription>"))
			{  fileWriter.write("<VariableDescription>\n");
				strTemp=bufferedReader.readLine();
			
			  // Inside timing info type1 file
        

			  while (!(strTemp.trim().startsWith("</VariableDescription>")))
			  {
				 
				 
				 	if (strTemp.trim().length()!=0)
				 	{
				 	  
						StringTokenizer st = new StringTokenizer(strTemp,"\t");
						//StringTokenizer st1 = new StringTokenizer(strTemp," ");
						//System.out.println(st1.countTokens());
						
					       
						if (i==0)
						{
						
						while (st.hasMoreTokens())
						{
						 try
							{
					          strTag1[k]=st.nextToken();
							  strTag1[k]=strTag1[k].trim();
							//  System.out.println(strTag1[k]);
							}
						    catch (Exception e)
							{
							}
						 	k++;
				        }
						    k=0;			
						    i=1;
							
						}
						else
						{		
							while (st.hasMoreTokens())
							{
							  
							  strTemp2[k]=st.nextToken();
							  k++;

				             }
								for (int g =0;g<k ;g++ )
								{
								 try
									{					 
									  fileWriter.write("<"+strTag1[g]+">" + " " +strTemp2[g] + "</"+strTag1[g]+">"+"\n");
									
									}
									catch(Exception e){}
								}
							    for (int g=0;g<k ;g++ )
							    {
								  strTemp2[g]="";
							     }
							 // printing them now
							 
						
							
					    	k=0; 
						}
				
						
					 
					}
				  	  strTemp=bufferedReader.readLine();
				
				 }

				
	
			  }
			  fileWriter.write(strTemp+"\n");
		
			}
				
	    
      
		 bufferedReader.close();
		 fileReader.close();
		 fileWriter.close();

	
	}


	public void readTable2(FileReader fileReader) throws IOException
    {

        BufferedReader bufferedReader=new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter("Output2.txt",false);
		String strTemp;
	
	
	
	
	
	     bufferedReader.close();
		 fileReader.close();
		 fileWriter.close();
	
	
	}
}
