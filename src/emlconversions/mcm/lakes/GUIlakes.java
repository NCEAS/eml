package code;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
public class GUIlakes extends JFrame implements ActionListener
{
	String s="";
	String s12="";
	String a="";
	String s11="";
	String s13="";
	String s14="";
	String s15="";
	String s16="";
	String s3="";
	int i=0;
	boolean t=true;
	boolean f=false;
	String[] fileItem=new String[] { "Open", "Exit"};
	String[] helpItem=new String[] { "Using Parser", "About"};
	String[] dirItem=new String[]{"Select Diretory","Parse Directory"};
	String parseItem="Parse File";
	String viewItem="View EML";
	char[] fileShCuts={'O','E'};
	char[] helpShCuts={'U','A'};
	char parShCuts='S';
	char viewShCuts='V';
	char[] dirShCuts={'D','P'};
	 JFileChooser fc;
	 JTextArea log;
	JMenuBar bar;
	String s1=" ";
	String s2=" ";
	File file;
    File file1;
	int l=0;
	File dir;
	
		public GUIlakes()
	{ 
		log= new JTextArea();
		JScrollPane pane = new JScrollPane(log,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		//log.setMargin(new Insets(5,5,5,5));
        log.setEditable(true);
        //JScrollPane logScrollPane = new JScrollPane(log);
		bar=new JMenuBar();	
		JMenu fileMenu=new JMenu ("Open File");		
		JMenu parseMenu=new JMenu ("Parse File");
		JMenu batch=new JMenu("Batch Parsing");
		JMenu helpMenu=new JMenu ("Help");
		JMenu viewMenu=new JMenu ("View");

	

		 for (int i=0;i<fileItem.length ;i++ )
		 {
			 JMenuItem item=new JMenuItem(fileItem[i]);
			 item.setAccelerator(KeyStroke.getKeyStroke(fileShCuts[i],java.awt.Event.CTRL_MASK,false));
			 item.addActionListener(this);
			 fileMenu.add(item);
		  }

		  for (int i=0;i<dirItem.length ;i++ )
		  {
			  JMenuItem item=new JMenuItem(dirItem[i]);
			  item.setAccelerator(KeyStroke.getKeyStroke(dirShCuts[i],java.awt.Event.CTRL_MASK,false));
			  item.addActionListener(this);
			  
			  batch.add(item);
		  }
		  
		
		for (int i=0;i<helpItem.length ;i++ )
		 {
			 JMenuItem item=new JMenuItem(helpItem[i]);
			 item.setAccelerator(KeyStroke.getKeyStroke(fileShCuts[i],java.awt.Event.CTRL_MASK,false));
			 item.addActionListener(this);
			 helpMenu.add(item);
		  }

			 JMenuItem item=new JMenuItem(parseItem);
			 item.setAccelerator(KeyStroke.getKeyStroke(parShCuts,java.awt.Event.CTRL_MASK,false));
			 item.addActionListener(this);
			 parseMenu.add(item);

			 item=new JMenuItem(viewItem);
			 item.setAccelerator(KeyStroke.getKeyStroke(viewShCuts,java.awt.Event.CTRL_MASK,false));
			 item.addActionListener(this);
			 viewMenu.add(item);
			 //setLayout(new BorderLayout());
			bar.add(fileMenu);
			bar.add(parseMenu);
			bar.add(batch);
			//bar.add(viewMenu);
			bar.add(helpMenu);
			setJMenuBar(bar);
			setContentPane(pane);
	
		
        fc = new JFileChooser();
       
    }
	public void actionPerformed(ActionEvent e)
		{ 
		
		if (( e.getActionCommand()).equals("Open"))
		{
			 fc=new JFileChooser();
			 int returnVal = fc.showOpenDialog(GUIlakes.this);
			// int result=fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
             if (returnVal == JFileChooser.APPROVE_OPTION) 
				{
				try
				{
					String s=" ";
					file = fc.getSelectedFile();
					//s1=file.getPath();
					//s2=file.getName();
					FileReader f1= new FileReader(file);
					BufferedReader br=new BufferedReader(f1);
					while((s=br.readLine())!=null)
					{log.append(s+"\n");
					//System.out.println(s+"*******");
					}
					log.setCaretPosition(1);
					f1.close();
				}
				catch (Exception a)
				{
					a.printStackTrace();
				}
								   
				}
		//System.out.println(" menu item"+e.getActionCommand());
		}
		
		if ((e.getActionCommand()).equals("Select Diretory"))
		{
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fc.showOpenDialog(new JFrame());
			log.setText("");
			if (result == JFileChooser.APPROVE_OPTION) 
				{
					 dir = fc.getSelectedFile();
					String[] contents = dir.list();
					log.append("PARSING THE FOLLOWING FILES\n");
					for (int i = 0; i < contents.length; i++)
						{
							log.append(contents[i]+"\n");
						}
				}
		}
		if ((e.getActionCommand()).equals("Parse Directory"))
		{
			batchParser br=new batchParser();
			br.parsing(dir);
		}
		if (( e.getActionCommand()).equals("Parse File"))
			{
			try
				{
				fc=new JFileChooser();
				log.setText("");
				int returnVal = fc.showSaveDialog(GUIlakes.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) 
					file1 = fc.getSelectedFile();
				FirstParse fp=new FirstParse();
				fp.parsing(file,file1);
				FileReader f13= new FileReader(file1);
				BufferedReader br3=new BufferedReader(f13);
				while((s=br3.readLine())!=null)
					log.append(s+"\n");
				}
				catch(Exception a)
				{
					a.printStackTrace();
				}
			}
		if (( e.getActionCommand()).equals("Using Parser"))
			{
				log.setText("");
				log.append("USING LTERNET PARSER\n\n\n");
				log.append("Save the text version of the file on the hard disk.\n\n");
				log.append("(1) SELECT FILE TO PARSE\n Click on File Menu and click open. Select the file to parse.");
				log.append("\n\n\n 2) PARSING THE FILE\n Click on the Parse Menu and then on Parse File. \nSpecify the file name of the parsed File to be saved.");
			}
			if (( e.getActionCommand()).equals("Exit"))
			{
			
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setVisible( false);
			}

		}
			public static void main(String args[])
		{   GUIlakes gui=new GUIlakes();
			//JFrame frame= new JFrame("Parser of Hubbard");
			//JFrame.setDefaultLookAndFeelDecorated(true);
			//JDialog.setDefaultLookAndFeelDecorated(true);
			 //frame.setContentPane(gui);
			//gui.getContentPane().add(gui.bar,BorderLayout.NORTH);
			// gui.getContentPane().add(gui.log,BorderLayout.CENTER);
			//frame.addWindowListener(new WindowAdapter());
			//gui.setSize(250,250);
			gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gui.setSize(350,350);
			gui.setVisible(true);
		}

}
