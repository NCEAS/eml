package code;
import java.io.*;
import java.util.StringTokenizer;
class EmailBreaker 
{
	public void emailBreaker(FileReader fileReader) throws IOException
	{
	    BufferedReader bufferedReader=new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter("Output9.txt",false);
		String strTemp,strTemp1="";
		int i=0;
		int found=0;
		while ((strTemp=bufferedReader.readLine())!=null)
		{
			strTemp.replaceAll("<missingValueIndicator>0<missi","<missingValueIndicator>zero<missin");
			strTemp.replaceAll("Data File","");
			if (strTemp.trim().startsWith("<Email>"))
			{
				 strTemp1=bufferedReader.readLine();
				 strTemp1=strTemp1.replaceAll("E-Mail:","");
				 if ((i=strTemp1.indexOf("or "))!=-1)
				 {
					fileWriter.write("<Email>\n");
					String str=strTemp1.substring(0,i-1);
					
					fileWriter.write(str+"\n");
					fileWriter.write("</Email>\n");
					fileWriter.write("<Email>\n");
					fileWriter.write(strTemp1.substring(i+2,strTemp1.length()-1)+"\n");
				    fileWriter.write("</Email>\n");
				    strTemp=bufferedReader.readLine();
					strTemp=bufferedReader.readLine();
					 
				 }
				 else
				{
					 fileWriter.write(strTemp+"\n");
					 fileWriter.write(strTemp1+"\n");
					 strTemp=bufferedReader.readLine();

				}
				

				
			}
		
			fileWriter.write(strTemp+"\n");
		}
		fileWriter.close();
		bufferedReader.close();
		fileReader.close();
	}
	
}
