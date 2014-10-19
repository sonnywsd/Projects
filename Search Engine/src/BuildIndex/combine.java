package BuildIndex;
//-Xmx4096M
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;



public class combine {
	
	public static ArrayList<String> tokenizeFile(File input){
		// TODO Write body!
		BufferedReader reader;
		ArrayList<String> tokenwords = new ArrayList<String>();
		String text;
		
		try {
			reader = new BufferedReader(new FileReader(input));
		
			while ((text = reader.readLine()) != null&& text.length() > 0) 
			{
				if(text!="Next Page")
					tokenwords.add(text);
			
			}
			reader.close(); 
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tokenwords;
	}	
	
	
	public static void TFIDF(ArrayList<String> wordlist)
	{
		//ArrayList<String> word = new ArrayList<String>(); 
		//ArrayList<String> tf = new ArrayList<String>(); 
		
		HashMap<String, String> locationmap = new HashMap<>();
		System.out.print("Start Split and Hashing\n");
		
		for(int i=0;i<wordlist.size();i++)
		{
			int x = wordlist.get(i).indexOf(' ');
			String term = wordlist.get(i).substring(0,x);
			String tf =wordlist.get(i).substring(x+1);
			//System.out.print(wordlist.get(i).substring(x+1)+"\n");
			if(term.length()>0&& isAlpha(term))
			{
				String location = locationmap.get(term);
				if(location == null) {
			    	location = new String();
			    	location+=tf;
				}
				else
				{
					location+=" "; 
					location+=tf;  
				}
				locationmap.put(term,location);
			}
			
		}
		System.out.print("Finish Split and Hashing\n");
		
		System.out.print(locationmap.size()+"\n");
		
		System.out.print("start write\n");
		try {
			
			for(Entry<String, String> entry : locationmap.entrySet()) 
			{
				String fileName = "TF-IDF"+ entry.getKey().toLowerCase().charAt(0)+ ".txt";
				File file = new File("final",fileName);
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
					System.out.print("Create file fail"+fileName+"\n");
					}
				} 
				PrintWriter pw = new PrintWriter(new FileWriter(file, true));
				
				pw.write(entry.getKey() + "  " + entry.getValue()+ "\n");
				pw.close();
			}
			
		} catch (IOException e) {
			System.out.print("Create file Printer failed\n");
		}	
		System.out.print("Finish....\n");
	}
	public static boolean isAlpha(String name) {
	    char[] chars = name.toCharArray();

	    for (char c : chars) {
	        if(!Character.isLetter(c)) {
	            return false;
	        }
	    }

	    return true;
	}
	public static void main(String args[]){
		
		Scanner input = new Scanner(System.in);
		String foldername;
		System.out.print("Enter Folder name: ");
		foldername = input.next();
		File folder = new File(foldername);
		ArrayList<String> allword = new ArrayList<String>(); 
	
		
		System.out.print("Read File....\n");
		
		for (int i=1; i <folder.list().length;i++) 
		{
			File textfile = new File(foldername,"td-idf"+i+".txt");
			System.out.println(textfile.getName());
			allword.addAll(tokenizeFile(textfile));
		}
		
		TFIDF(allword);
		input.close();
	}
}
