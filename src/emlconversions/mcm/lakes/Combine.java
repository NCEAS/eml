package code;
import java.io.*;

class Combine 
{
	public void combine(File fout)
	{
		try
		{
			FileReader fr=new FileReader("Output11.txt");
			FileReader fr1=new FileReader("Output10.txt");
			BufferedReader br=new BufferedReader(fr);
			BufferedReader br1=new BufferedReader(fr1);
			FileWriter fw=new FileWriter("Output.xml");
			String s="";

			while (!((s=br1.readLine()).startsWith("<Timing>")))
			{
				fw.write(s+"\n");

			}
			fw.write("<Timing>\n");
			while ((s=br.readLine())!=null)
			{
				fw.write(s+"\n");
			}
			fw.write("</Timing>\n");
			while (!(s=br1.readLine()).equals("</Timing>"))
			{
			} 
			while ((s=br1.readLine())!=null)
			{
				fw.write(s+"\n");
			}
			fw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
