package code;
import java.io.*;


class FinalOutput 
{
	public void finalOutput(FileReader fileReader, int StatusUL) throws IOException
	{
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter("Output4.txt",false);
		String strTemp,strTemp1="";
		int i=0;
		int found=0;
		fileWriter.write("<LakesDatabase>\n");
		while ((strTemp=bufferedReader.readLine())!=null)
		{
		  strTemp=strTemp.replaceAll("MISSING VALUE INDICATOR","missingValueIndicator");
		  strTemp=strTemp.replaceAll("&micro;","mu ");
		  strTemp=strTemp.replaceAll("&deg;","degree ");
		  strTemp=strTemp.replaceAll("\u00B5","mu ");
		  strTemp=strTemp.replaceAll("\u00B0","degree ");
		  strTemp=strTemp.replaceAll(";è","egrave ");
            strTemp=strTemp.replaceAll("m²","square meters ");

		  strTemp=strTemp.replaceAll("&","");

		  
		  strTemp=strTemp.replaceAll("<30","&lt;30");
		   strTemp=strTemp.replaceAll("<0","&lt;0");
		  strTemp=strTemp.replaceAll("<A\r","");
		  strTemp=strTemp.replaceAll("<A\r\n","");
		  strTemp=strTemp.replaceAll("<A\t","");
          strTemp=strTemp.replaceAll("<A"+10,"");
		  strTemp=strTemp.replaceAll("HREF=\"http://huey.colorado.edu/LTER/abstracts/lakes/biology/Parry97.html\">","");
		  strTemp=strTemp.replaceAll("HREF=\"http://huey.colorado.edu/LTER/datasets/lakes/limnhole.html\">","");
		  strTemp1=strTemp;
		  
		  
		  if ((strTemp.trim().length()!=0)&&(i==0)&&(StatusUL==0))
		  {
			  i=1;
			  fileWriter.write("<Title>"+"\n"+strTemp + "\n"+"</Title>\n");	
			  strTemp=bufferedReader.readLine();
		  }
		  strTemp=strTemp.trim();
		   if (strTemp.startsWith("KEYWORDS:"))
		   {	 
			 fileWriter.write("<Keywords>");
			 strTemp= strTemp.replaceAll("KEYWORDS:","");
			 fileWriter.write(strTemp+"\n");
		
			 while (!(strTemp=bufferedReader.readLine()).trim().startsWith("<"))
			   {
				  fileWriter.write(strTemp+"\n");
			   }
           	 fileWriter.write("</Keywords>");
		   }

		      if (strTemp.startsWith("LOCATION"))
		      {	 
				  strTemp=bufferedReader.readLine();
			 
		      }
			  
			  if (strTemp.startsWith("<Note>"))
		      {	 
				  found=1;
			 
		      }



									
		  if (strTemp.length()!=0)
		  {
			  	 fileWriter.write(strTemp+"\n");
		  }
	
		
		}
		if (!(strTemp1.startsWith("</Note>"))&&(found==1))

		{
			 fileWriter.write("</Note>\n");
		}
		fileWriter.write("</LakesDatabase>");
         bufferedReader.close();
		 fileReader.close();
		 fileWriter.close();





	}
}
