package code;
import java.io.*;
import java.util.StringTokenizer;
class  HtmlToTxt
{
	void readHtml(FileReader fileReader) throws IOException
	{
		int i=0;
		int k = 0;
		String strTemp;
			
		    FileWriter fileWriter = new FileWriter("Output.txt",false);
		    BufferedReader bufferedReader=new BufferedReader(fileReader);
		    while (!((strTemp = bufferedReader.readLine()).startsWith("</UL>")))
		    {
		    }   
			
		    while((strTemp = bufferedReader.readLine())!=null)
            {
			
					strTemp=strTemp.replaceAll("\\<.*?\\>","");
					strTemp=strTemp.replaceAll("&nbsp;?","");
					strTemp=strTemp.replaceAll("\\n\n?","");
					strTemp=strTemp.replaceAll("\n","");
					

					if ((strTemp!="")||(strTemp!="\n")||(strTemp!="\t"))
					{
						fileWriter.write(strTemp+"\n");
					}

			}
			
			
			FileReader fileReader1 = new FileReader("Output.txt");
			BufferedReader bufferedReader1=new BufferedReader(fileReader1);
		
			while ( (strTemp= bufferedReader1.readLine())!=null)
			{
			  //System.out.println(strTemp);
			}

		
		
		fileWriter.close();
		bufferedReader.close();
	    fileReader.close();
	}

	void readHtml1(FileReader fileReader) throws IOException
	{
		int i=0;
		int k = 0;
		String strTemp;
			
		    FileWriter fileWriter = new FileWriter("Output.txt",false);
		    BufferedReader bufferedReader=new BufferedReader(fileReader);
		  
			
		    while((strTemp = bufferedReader.readLine())!=null)
            {
			
					strTemp=strTemp.replaceAll("\\<.*?\\>","");
					strTemp=strTemp.replaceAll("&nbsp;?","");
					strTemp=strTemp.replaceAll("\\n\n?","");
					strTemp=strTemp.replaceAll("\n","");
					

					if ((strTemp!="")||(strTemp!="\n")||(strTemp!="\t"))
					{
						fileWriter.write(strTemp+"\n");
					}

			}
			
			
			FileReader fileReader1 = new FileReader("Output.txt");
			BufferedReader bufferedReader1=new BufferedReader(fileReader1);
		
			while ( (strTemp= bufferedReader1.readLine())!=null)
			{
			  //System.out.println(strTemp);
			}

		
		
		fileWriter.close();
		bufferedReader.close();
	    fileReader.close();
	}

}
	
