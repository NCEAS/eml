package code;
import java.io.*;

class  Temp
{
	public static void main(String[] args) throws IOException
	{
		char a=13;
		FileWriter fileWriter = new FileWriter("hel",false);
		System.out.println("hhe");
		fileWriter.write(a);
		fileWriter.close();
	}
}
