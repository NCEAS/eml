package code;
import java.util.*;
import java.io.*;

class Organize 
{
	public void organize(FileReader fileReader)
	{
		try
		{
			String s="";
			String s1="";
			String s2="";
			boolean b;
			String name="";
			String value="";
			
			BufferedReader bufferedReader=new BufferedReader(fileReader);
			FileWriter fw=new FileWriter("Output11.txt");
			Hashtable ht=new Hashtable();
			Vector vc=new Vector();
			while ((s=bufferedReader.readLine())!=null)
			{
				if (s.startsWith("<Timing>"))
				{
					//System.out.println("inside timings");
					while (((s=bufferedReader.readLine())!="</Timing>")&&(s!=null))
					{
						
						//System.out.println(s);
						
					
						if (s.startsWith("<site>"))
						{
							
							s1=s.replaceAll("<site>","");
							s1=s1.replaceAll("</site>","");
							s1=s1.trim();
							s2=bufferedReader.readLine();
							s2=s2.replaceAll("<date>","");
							s2=s2.replaceAll("</date>","");
							s2=s2.trim();
							//System.out.println(s2+"........"+s1);
							ht.put(s2,s1);
							if (!vc.contains(s1))
							{
								b=vc.add(s1);
							}
				
						}
						
						

					}
					
				}
				

			}
			Object ob[]=vc.toArray();
			String arr[]=new String[ob.length];
			//System.out.println("size of arr"+arr.length);
			for (int i=0;ob.length>i ;i++ )
			{
				arr[i]=ob[i].toString();
			if (ht.contains(arr[i]))
			{
				fw.write("<site>\n"+"<name>\n"+arr[i]+"\n</name>\n");
				System.out.println(arr[i]);
				for (Enumeration e=ht.keys(); e.hasMoreElements();)
				{
				    name = (String)e.nextElement();
				 value = (String)ht.get(name);
					if (value.equals(arr[i]))
					{
						System.out.println(name);
						fw.write("<date>"+name+"</date>\n");
					}
				
		        }
				fw.write("</site>\n");
			}
			
			}
			
		fw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
