package code;
import java.io.*;
import java.util.StringTokenizer;
class OtherRemover 
{
	public void otherRemover(FileReader fileReader) throws IOException
	{
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter("Output8.txt",false);
		String strTemp,strTemp1="";
		int i=0;
		int found=0;
		while ((strTemp=bufferedReader.readLine())!=null)
		{
			if (strTemp.startsWith("<Others"))
			{
				strTemp1=bufferedReader.readLine();
				if (strTemp1.startsWith("</Others>"))
				{
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
