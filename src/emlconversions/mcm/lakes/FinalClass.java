package code;
import java.io.*;
import java.util.StringTokenizer;
class FinalClass
{
	public void finalcl (FileReader fileReader) throws IOException
	{
	    BufferedReader bufferedReader=new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter("Output10.txt",false);
		String strTemp,strTemp1="";
		int i=0;
		int found=0;
		while ((strTemp=bufferedReader.readLine())!=null)
		{
			strTemp.replaceAll("<missingValueIndicator>0<missi","<missingValueIndicator>zero<missin");
			strTemp.replaceAll("Data File","");
			fileWriter.write(strTemp+"\n");
		}
		fileWriter.close();
		bufferedReader.close();
		fileReader.close();
}

}
