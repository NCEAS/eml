package code;
import java.io.*;
import java.util.StringTokenizer;

class  KeyWordsParser
{
	public void keywords(FileReader fileReader) throws IOException
	{
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter("Output6.txt",false);
		String strTemp,strTemp1="";
		int i=0;
		int found=0;
		while ((strTemp=bufferedReader.readLine())!=null)
		{
			
			if (strTemp.trim().startsWith("<Keywords>"))
			{
				 fileWriter.write("<Keywords>\n");
				 
				
				while (!(strTemp=bufferedReader.readLine()).startsWith("</Keywords>"))
					{
					
					
						StringTokenizer st = new StringTokenizer(strTemp,",");
						while (st.hasMoreTokens())
						{
							strTemp1=st.nextToken();
							fileWriter.write("<Word>\n");
							fileWriter.write(strTemp1+"\n");
							fileWriter.write("</Word>\n");
							
							
							
						}
					
					
					
				   }
			   
				
			}
			fileWriter.write(strTemp+"\n");
		}
		fileWriter.close();
		bufferedReader.close();
		fileReader.close();
	}
}
