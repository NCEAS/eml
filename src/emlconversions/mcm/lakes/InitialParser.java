package code;
import java.io.*;
import java.util.*;

class  InitialParser
{
	public int status=0;
	public int statusPrincipal=0;
	public int statusTiming=0;
	public int statusUL=1;


	public void readHtmlforUL(FileReader fileReader) throws IOException
	{
	    BufferedReader bufferedReader=new BufferedReader(fileReader);
		
		int timeTag;
		String strTemp;
		StringTokenizer  strTokenizer;
		String strTemp1 = "</UL>";

		System.out.println(strTemp1.length());

		char tempChar[]= strTemp1.toCharArray();
	
		int k =0;int k1=0;
		while ((strTemp=bufferedReader.readLine())!=null)
		{
			strTemp=strTemp.trim();
			//System.out.println(strTemp);
			for (int j =0;j<strTemp.length() ;j++ )
			{
			   try
			   {
				 if (tempChar[k]==strTemp.charAt(j))
				 {
					k++;
				 }
			   }
			   catch (Exception e)
				{

				}
				
				
			}
			if (k==strTemp1.length())
				{	
				  System.out.println("here1");
				  statusUL=0;
				

				}
				
				
					k=0;
					k1=0;
				}
    }
	

	
	public void readHtmlforPrincipal(FileReader fileReader) throws IOException
	{
	    BufferedReader bufferedReader=new BufferedReader(fileReader);
		
		int timeTag;
		String strTemp;
		StringTokenizer  strTokenizer;
		String strTemp1 = "PRINCIPAL INVESTIGATOR:";

		System.out.println(strTemp1.length());

		char tempChar[]= strTemp1.toCharArray();
	
		int k =0;int k1=0;
		while ((strTemp=bufferedReader.readLine())!=null)
		{
			for (int j =0;j<strTemp.length() ;j++ )
			{
			   try
			   {
				 if (tempChar[k]==strTemp.charAt(j))
				 {
					k++;
				 }
			   }
			   catch (Exception e)
				{

				}
				
				
			}
			if (k==strTemp1.length())
				{	
				  strTemp=bufferedReader.readLine();
				  if (strTemp.trim().toLowerCase().startsWith("<table"))
				  {
					   statusPrincipal=1;
				  }
				  
				 
				   break;
				  } 
				  
				
		   k=0;k1=0;
		}
    }
	
	
	
	
	
	
	
	
	
	
	
	public void readHtmlforVariable(FileReader fileReader) throws IOException
	{
	    BufferedReader bufferedReader=new BufferedReader(fileReader);
		
		int timeTag;
		String strTemp;
		StringTokenizer  strTokenizer;
		String strTemp1 = "VARIABLE DESCRIPTION:";

		System.out.println(strTemp1.length());

		char tempChar[]= strTemp1.toCharArray();
	
		int k =0;int k1=0;
		while ((strTemp=bufferedReader.readLine())!=null)
		{
			for (int j =0;j<strTemp.length() ;j++ )
			{
			   try
			   {
				 if (tempChar[k]==strTemp.charAt(j))
				 {
					k++;
				 }
			   }
			   catch (Exception e)
				{

				}
				
				
			}
			if (k==strTemp1.length())
				{	
				  strTemp=bufferedReader.readLine();
				  if (strTemp.trim().toLowerCase().startsWith("<table"))
				  {
					   status=1;
				  }
				 
				   break;
				  } 
				  
				
		   k=0;k1=0;
		}
    }
	public void readHtmlforTiming(FileReader fileReader) throws IOException
	{
	    BufferedReader bufferedReader=new BufferedReader(fileReader);
		
		int timeTag;
		String strTemp;
		StringTokenizer  strTokenizer;
		
		String strTemp1 = "TIMING:";
		System.out.println(strTemp1.length());

		char tempChar[]= strTemp1.toCharArray();
	
		int k =0;
		while ((strTemp=bufferedReader.readLine())!=null)
		{
			for (int j =0;j<strTemp.length() ;j++ )
			{
			   try
			   {
				 if (tempChar[k]==strTemp.charAt(j))
				 {
					k++;
				 }
			   }
			   catch (Exception e)
				{

				}
			
			}
			if (k==strTemp1.length())
				{
				  if (strTemp.indexOf("pre")!=-1)
				  {
					  statusTiming =0;
					  
				  }
				  else
				 {
				  strTemp=bufferedReader.readLine();
				  System.out.println(strTemp);
				  if (strTemp.trim().toLowerCase().startsWith("<table"))
				  {
					statusTiming=1;
					break;
				  }
				  else if (strTemp.trim().toLowerCase().startsWith("<hr"))
				  {
					statusTiming=0; 
					break;
				  }
				  else if (strTemp.indexOf("pre")!=-1)
				  {
					  statusTiming =0;
				      break;
				  }
				  else if (strTemp.startsWith("<SPACER"))
				  {

					  // this status for only one file
					  statusTiming =2;
				      break;
				  }
				  else 
				  {

					  
					  statusTiming =3;
				      break;
				  }

				 }
				   break;
				  } 
			
				
		   k=0;
		}
		 
		 bufferedReader.close();
		 fileReader.close();
		

	}
}

