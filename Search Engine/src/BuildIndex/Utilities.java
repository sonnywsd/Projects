package BuildIndex;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of utility methods for text processing.
 */
public class Utilities {
		static List<String> commonwords;
		
	public static ArrayList<String> tokenizeFile(File input){
		// TODO Write body!
		BufferedReader reader;
		ArrayList<String> tokenwords = new ArrayList<String>();
		String text;
	
		File file = new File("commonwordlist.txt");
		commonwords =Utilities.readfile(file);
		
		
		try {
			reader = new BufferedReader(new FileReader(input));
		
			while ((text = reader.readLine()) != null) 
			{
				String [] words = text.toLowerCase().split("\\W");
				for(int i = 0; i < words.length; i++)
				{
					if(words[i].length()>1 && !filter(words[i]))
						tokenwords.add(words[i]);
				}
			
			}
			reader.close(); 
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tokenwords;
		
	}
	
	
	public static ArrayList<String> readfile(File input){
		// TODO Write body!
		BufferedReader reader;
		ArrayList<String> tokenwords = new ArrayList<String>();
		String text;
		try {
			reader = new BufferedReader(new FileReader(input));
		
			while ((text = reader.readLine()) != null) 
			{
				String [] words = text.toLowerCase().split("\\W");
				for(int i = 0; i < words.length; i++)
				{
					if(words[i].length()>0)
						tokenwords.add(words[i]);
				}
			
			}
			reader.close(); 
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tokenwords;
		
	}
	
	public static boolean filter(String checkword)
	{
		
		if(checkword.matches(".*\\d.*"))
			return true;
		
		for(String city: commonwords) {  
	        if(checkword.equals(city)) {  
	            return true; 
	        }
	    }
		return false;
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
}
