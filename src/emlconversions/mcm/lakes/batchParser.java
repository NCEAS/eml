package code;
import java.io.*;
import java.util.*;
class batchParser 
{
	public  void parsing(File dir) 
	{
		try
		{
			File dir1=dir;
			String name=dir1.getAbsolutePath();
			System.out.println("PARSING name--"+name);
			String dirName=name+"Parsed";
			String s1="";
			String s2="";
			File []files=dir1.listFiles();
			String []fileName=dir1.list();
			//boolean success=(new File(dirName)).mkdir();
			for (int i=0;i<files.length ;i++ )
			{
				StringTokenizer st=new StringTokenizer(fileName[i],".");
				s1=st.nextToken();

				//File f1=new File(files[i]);
				
				File f2=new File(name,s1+"parsed.xml");
				System.out.println("File created");
				FirstParse fp=new FirstParse();
				fp.parsing(files[i],f2);

				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
