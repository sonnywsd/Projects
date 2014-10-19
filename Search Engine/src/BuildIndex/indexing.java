package BuildIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;


public class indexing {
	static List<String> commonwords;
	
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
	
	public static Long getLong(String string)
	{
		
		try{
			Long number;
			number = Long.parseLong(string);
			return number;

			}
			catch (NumberFormatException e)
			{
				System.out.print("skip\n");// log e if you want...
			}
		return null;		
	}
	
	public static void pageindexing(BufferedReader textreader, Long start, Long end, String url,int num)
	{
		try {
		String pagetext = textreader.readLine();
		pagetext +="\n";
		List<Word> wordlist =  new ArrayList<Word>();
		

		for(int i=0; i<=(end-start); i++ )
		{
			String text;
			text = textreader.readLine();
			pagetext +="\n";
			pagetext +=text;
		}
		
			String [] words = pagetext.toLowerCase().split("\\W");
		if(words.length>1)
		{	
			Map<String, Integer> countMap = new HashMap<>();
			//HashMap<String, ArrayList<Integer>> locationmap = new HashMap<>();
			for (String word : words) {
			    if(word.length()>0&&!filter(word))
			    {
					Integer count = countMap.get(word);
				    
				    if(count == null) {
				        count = 0;
				    }
				    countMap.put(word, (count.intValue()+1));
			    }
			}	
			
			for(Entry<String, Integer> entry : countMap.entrySet()) {
			//rebuild location function	
				ArrayList<Integer> location = new ArrayList<Integer>();
				String word =entry.getKey().toLowerCase();
				pagetext = pagetext.toLowerCase();
				int count=0;
				for (int i = -1; (i = pagetext.indexOf(word, i + 1)) != -1; )
				{
					int j= word.length()+i;
					count++;
					if(i==0||j == pagetext.length()
							||(!Character.isLetter(pagetext.charAt(i-1)) 
							&& !Character.isLetter(pagetext.charAt(j))))
							 location.add(i);  
					if(count>=entry.getValue())
						break;
				} 
				Word item = new Word(entry.getValue(),entry.getKey(),location);
				wordlist.add(item);
			}
			Collections.sort(wordlist, new Comparator<Word>() {
				@Override
				public int compare(Word o1, Word o2) {
					// TODO Auto-generated method stub
					return o2.getfrequency() - (o1.getfrequency());
				}
		    });
		}
		else
		{
			ArrayList<Integer> x = new ArrayList<Integer>();
			x.add(0);
			Word item = new Word(0,"Null Page "+url,x);
			wordlist.add(item);
		}
	
		//printWords(wordlist);
		writetifile(wordlist,url,num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void printWords(List<Word> words) {
		for (int i = 0; i < words.size();i++)
		{
			Word item = words.get(i);	
			System.out.print(item.getword() + "  " + item.getfrequency());
			
			for(Integer locationposition :item.getlocationlist())
				System.out.print("  "+ locationposition);
			System.out.println();
		}
		System.out.println("Next Page");
	}
	public static void writetifile(List<Word> words,String url,int num) {
		String fileName = "td-idf"+ num + ".txt";
		File file = new File("TF-IDF",fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.print("Create Subdomain file fail\n");
			}
		} 
		
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(file, true));
			//pw.write(url + "\n");
			for (int i = 0; i < words.size();i++)
			{
				Word item = words.get(i);	
				//System.out.print(item.getword() + "  " + item.getfrequency());
				String locationlist="";
				for(Integer location :item.getlocationlist())
				{
					locationlist += " ";
					locationlist += location;
				}
				pw.write(item.getword() + "  "+ url + " " + item.getfrequency()+ " " +locationlist+"\n");
			}
			pw.write("Next Page\n");
			
			pw.close();
		} catch (IOException e) {
			System.out.print("Create file Printer failed\n");
		}
	}
	
	public static boolean filter(String checkword)
	{
		
		//if(checkword=="null")
			//return true;
		if(checkword.matches(".*\\d.*"))
			return true;
		
		for(String city: commonwords) {  
	        if(checkword.equals(city)) {  
	            return true; 
	        }
	    }
		return false;
	}
	
	public static String[] filterblank(String [] words)
	{
		int j=0;
		String [] tokenwords = words;
		for(int i = 0; i < words.length; i++)
		{
			//words[i]= words[i].replaceAll("[^\\p{Alnum}]+", "");
			//System.out.println(words[i]);
			if(words[i].length()>0)
			{
				tokenwords[j] = words[i];
				j++;
			}
		}
		return tokenwords;
	}
	/*public void locationmy()
	{
		int i=0,j;
		String word;
		while(i<pagetext.length())
		{
			if(Character.isLetter(pagetext.charAt(i)))
			{
				j=i+1;
				while(j<pagetext.length())
				{
					if(Character.isLetter(pagetext.charAt(j)))
					{
						word = pagetext.substring(i, j+1);
						if(countMap.get(word)!=null)
						{
							if(i==0||j == pagetext.length()
									||(!Character.isLetter(pagetext.charAt(i-1)) 
									&& !Character.isLetter(pagetext.charAt(j))))
							{
								ArrayList<Integer> location = locationmap.get(word);
								 if(location == null) {
								    	location = new ArrayList<Integer>();
								    	location.add(i);
								 }
								 else
									 location.add(i);  
								 locationmap.put(word,location);
							}
						}
					}		
					else
					{
						i=j+1;
						j = pagetext.length();
					}
				}
				
				
			}
			else
			{
				i++;
			}
		}
	}
	*/
	//main
	public static void main(String args[]){
		Scanner input = new Scanner(System.in);
		String foldername;
		System.out.print("Enter Folder name: ");
		foldername = input.next();
		File folder = new File(foldername);
		
	
		System.out.print("Read File....\n");
		
		File file = new File("commonwordlist.txt");
		commonwords =Utilities.readfile(file);
		for (int i=1; i <=folder.list().length/2;i++) 
		{
			File textfile = new File(foldername,"text"+i+".txt");
			File docfile = new File(foldername,"Docid_URL"+i+".txt");
			System.out.println(docfile.getName());
			System.out.println(textfile.getName());
			try{
			BufferedReader reader = new BufferedReader(new FileReader(docfile));
			BufferedReader textreader = new BufferedReader(new FileReader(textfile));
			
			String line1,line2,url1,url2;
			Long startline,endline;
			String [] words1,words2;
			
			if((line1 = reader.readLine())!=null)
			{
				words1 = line1.split("\\s+");
				words1 = filterblank(words1);
				url1  = words1[2];
				url2 = "";
				
				startline = getLong(words1[words1.length-2]);
				
				while ((line2 = reader.readLine()) != null) 
				{
					
					words2 = line2.split("\\s+");
					words2 = filterblank(words2);
					//System.out.println(words1[2]+" "+startline);
					//if((!words1[2].contains(".csv")) && (startline < getLong(words2[words2.length-2])))
					if(startline < getLong(words2[words2.length-2]))
					{	
						url2  = words2[2];
						
						endline = getLong(words2[words2.length-2]);
						//System.out.println(words1[2]+" "+startline+" "+endline);
						pageindexing(textreader,startline,endline-1,url1,i);
						
						url1 =url2;
						startline = endline;
						words1 = words2;
					}
					else if(startline > getLong(words2[words2.length-2]))
					{
						System.out.println(words1[2]+" "+startline+" "+getLong(words2[words2.length-2]));
					}
					else
					{	
						endline = getLong(words2[words2.length-2]);
						url1 =url2;
						startline = endline;
						words1 = words2;
					}
						
				}
				LineNumberReader  lnrtext = new LineNumberReader(new FileReader(textfile));
				lnrtext.skip(Long.MAX_VALUE);
				endline=  Long.valueOf(lnrtext.getLineNumber());
				pageindexing(textreader,startline,endline,url1,i);
				
				lnrtext.close();
				reader.close();
				textreader.close();
				input.close();
			}
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		Date date = new Date();
		System.out.print(date+"\n");
		
	}
}
	
