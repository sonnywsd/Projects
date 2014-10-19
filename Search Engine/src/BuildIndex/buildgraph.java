package BuildIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class buildgraph {
	static Map<String, Integer> urlid = new HashMap<String, Integer>();
	Map<String, Integer> idurl = new HashMap<String, Integer>();
	
	public static void init(String input){
		// TODO Write body!
		BufferedReader reader;
		File rfile = new File("graph",input);
		
		try {
			reader = new BufferedReader(new FileReader(rfile));
			String text;
			while ((text = reader.readLine()) != null) 
			{
				String [] words = text.toLowerCase().split("\\s+");
				int id = Integer.parseInt(words[0]);
				urlid.put(words[1], id);
			}
			reader.close(); 
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String args[]){
		init("Docid_URL.txt");
		String filename = "graph.txt";
		File rfile = new File("graph",filename);
		
		File wfile = new File("graph","idgraph.txt");
		if (!wfile.exists()) {
			try {
				wfile.createNewFile();
			} catch (IOException e) {
				System.out.print("Create write file fail\n");
			}
		} 
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(wfile, true));
			BufferedReader reader = new BufferedReader(new FileReader(rfile));
			String text;
			//System.out.println(urlid.get("http://www.ics.uci.edu/"));
			while ((text = reader.readLine()) != null) 
			{
				String [] urls = text.split("\\s+");
				
				if(urlid.get(urls[0].toLowerCase())!=null && urlid.get(urls[1].toLowerCase())!=null)
				{
					
					int id1 = urlid.get(urls[0].toLowerCase());
					int id2 = urlid.get(urls[1].toLowerCase());
					pw.write(id1 + " "+ id2 + "\n");	
				}
				
			}
			pw.close();
			reader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
