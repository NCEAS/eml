package code;
import java.io.*;
import java.util.StringTokenizer;
class  FirstParse
{
	public void parsing(File fin, File fout) throws IOException
	{ 
		int i=0;
		String strTemp;
		FileReader fileReader = new FileReader(fin);
		FileReader fileReader0 = new FileReader(fin);

		InitialParser initialParser = new InitialParser();
		
		initialParser.readHtmlforVariable(fileReader0);
		fileReader0.close();
		 fileReader0 = new FileReader(fin);
		initialParser.readHtmlforTiming(fileReader0);
		fileReader0.close();	
		
		fileReader0 = new FileReader(fin);
		initialParser.readHtmlforPrincipal(fileReader0);
		fileReader0 = new FileReader(fin);
		initialParser.readHtmlforUL(fileReader0);
		

		HtmlToTxt html = new HtmlToTxt();
	    if (initialParser.statusUL==1)		html.readHtml1(fileReader);
	    else  html.readHtml(fileReader);		
		FileReader fileReader1 = new FileReader("Output.txt");
		 fileReader0 = new FileReader("Output.txt");
		TextToXml xml = new TextToXml();
		if (fin.getPath().indexOf("ciliates.html")!=-1)
		{
			initialParser.statusPrincipal=1;
			System.out.println("fusscck");
			System.out.println(initialParser.statusPrincipal);

		}

		if (initialParser.statusPrincipal==1) 	xml.readTxt1(fileReader1);
		else
		xml.readTxt(fileReader1);
		
		
		
		
		ReadVariableDescription table = new ReadVariableDescription ();
		FileReader fileReader2 = new FileReader("Output1.txt");
		if (initialParser.status==1)
		table.readTable(fileReader2); else table.readTable1(fileReader2);
		FileReader fileReader3 = new FileReader("Output2.txt");
		ReadTiming timing= new ReadTiming();
	
		
		
		if (initialParser.statusTiming==0) timing.readTable(fileReader3); 
		else if (initialParser.statusTiming==1)
		 timing.readTable1(fileReader3);
		else if ((initialParser.statusTiming==2)) timing.readTable2(fileReader3);
		else if ((initialParser.statusTiming==3)) timing.readTable3(fileReader3);
	
			
		
		
		
		fileReader3 = new FileReader("Output3.txt");
		FinalOutput finalOutput = new FinalOutput();
		finalOutput.finalOutput(fileReader3,initialParser.statusUL);
		fileReader3 = new FileReader("Output4.txt");
		NextParse nextParser = new NextParse();
		nextParser.nextParse(fileReader3);
		fileReader3 = new FileReader("Output5.txt");
		KeyWordsParser keyParser = new KeyWordsParser();
		keyParser.keywords(fileReader3);
		fileReader3 = new FileReader("Output6.txt");
		AddressPreParser addressParser = new AddressPreParser();
		addressParser.Address(fileReader3);
			fileReader3 = new FileReader("Output7.txt");
		OtherRemover other = new OtherRemover();
	
		other.otherRemover(fileReader3);
			fileReader3 = new FileReader("Output8.txt");
	    EmailBreaker email = new EmailBreaker();
	
		email.emailBreaker(fileReader3);
		fileReader3 = new FileReader("Output9.txt");
	    FinalClass fin1 = new FinalClass();
		fin1.finalcl(fileReader3);

			fileReader3 = new FileReader("Output10.txt");
			Organize or = new Organize();
			or.organize(fileReader3);

			Combine cr = new Combine();
			cr.combine(fout);
			
        Stylizer s = new Stylizer();
		String argv[]={"LakeTransformFinal3.xsl","Output.xml",fout.getAbsolutePath()};
		s.transform(argv);
	}
}
