package code;
import java.io.*;
import java.util.StringTokenizer;


class NextParse 
{
	public  void nextParse(FileReader fileReader) throws IOException 
	{
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter("Output5.txt",false);
		String strTemp,strTemp1="";
		int i=0;
		int found=0;
		while ((strTemp=bufferedReader.readLine())!=null)
		{
			if (strTemp.trim().startsWith("<FileName>"))
			{
				fileWriter.write(strTemp+"\n");
				while (!(strTemp=bufferedReader.readLine()).startsWith("</FileName>"))
				{
					i= strTemp.indexOf("(");
					if (i!=-1)
					{
						fileWriter.write("<File>\n");
						fileWriter.write(strTemp.substring(0,i-1)+"\n");
						fileWriter.write("</File>\n");
						fileWriter.write("<FileType>\n");
						fileWriter.write(strTemp.substring(i+1,strTemp.indexOf(")")));
						fileWriter.write("\n</FileType>\n");
					}




				}
			}
			if (strTemp.trim().startsWith("<PrincipalInvestigator>"))
			{
				 fileWriter.write("<PrincipalInvestigator>\n");
				while (!(strTemp=bufferedReader.readLine()).startsWith("<Address>"))
				{
					
					try{
					StringTokenizer st = new StringTokenizer(strTemp," ");
				    fileWriter.write("<FirstName>\n");
					fileWriter.write(st.nextToken()+"\n");
					fileWriter.write("</FirstName>\n");
					fileWriter.write("<LastName>\n");
					fileWriter.write(st.nextToken()+"\n");
					fileWriter.write("</LastName>\n");
					}
					catch (Exception e){}
				}

			}

			if (strTemp.trim().startsWith("<Others>"))
			{
				 fileWriter.write("<Others>\n");
				 
				try
				{
				while ((!((strTemp=bufferedReader.readLine()).startsWith("<Address>")))&&(!(strTemp.startsWith("<Email>")))&&(strTemp!=null)&&(!(strTemp.startsWith("</Others>"))))
				{
					
					try
						
					{
						StringTokenizer st = new StringTokenizer(strTemp," ");
						while (((strTemp1=st.nextToken()).trim().length()!=0)||(st.hasMoreTokens()))
						{
							if (strTemp1.trim().startsWith("Not Applicable"))
							{
							}
							else
							{
							fileWriter.write("<FirstName>\n");
							fileWriter.write(strTemp1+"\n");
							fileWriter.write("</FirstName>\n");
							if (st.hasMoreTokens())
							{
								strTemp1=st.nextToken();
							    fileWriter.write("<LastName>\n");
							    fileWriter.write(strTemp1+"\n");
							    fileWriter.write("</LastName>\n");
							}
							}
							
						}
					
					}
					catch (Exception e){}
				}
				}
				catch(NullPointerException e){}
			}
		
			fileWriter.write(strTemp+"\n");
		}
		fileWriter.close();
		bufferedReader.close();
		fileReader.close();
	}
}
