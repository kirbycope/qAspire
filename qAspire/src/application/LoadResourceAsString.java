package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class LoadResourceAsString
{
	/**
	 * <b>sourceFileToString</b><br>
	 * <br>
	 * Imports a given file from the project's source code as a String.<br>
	 * <br>
	 * @param fileName - fileName/path of the source code file
	 * @return String - Contents of the given file
	 */
	public static String sourceFileToString(String fileName)
	{
		// http://stackoverflow.com/a/15161665/1106708
		StringBuilder builder = new StringBuilder();
		InputStream is = Settings.class.getResourceAsStream(fileName);
		int ch;
		try
		{
			while((ch = is.read()) != -1)
			{
			    builder.append((char)ch);
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	/**
	 * <b>localFileToString</b><br>
	 * <br>
	 * Imports a given file from the user's local file system as a String<br>
	 * <br>
	 * @param fileName - fileName/path of the local system file
	 * @return String - Contents of the given file
	 * @throws IOException - If the file is not found so the FileReader throws an exception
	 */
	public static String localFileToString(String fileName) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try
	    {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null)
	        {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        
	        return sb.toString();
	    }
	    finally
	    {
	        br.close();
	    }
	}
}