package SearchInterface;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;


public class functions {
	
	public static boolean isUpper(String s)
	{
	    for(char c : s.toCharArray())
	    {
	        if(! Character.isUpperCase(c))
	            return false;
	    }

	    return true;
	}
	public static boolean checktitle(String title, String word)
	{
		if(title.toLowerCase().contains(word)
				&& !Character.isLetter(title.charAt(title.indexOf(word)-1))
				&& !Character.isLetter(title.charAt(title.indexOf(word)+word.length()))
				)
		{
				return true;
		}
		
		return false;
	}
	//
	public static int checkachor(String abchor, String[] words) {
		// TODO Auto-generated method stub
		if(abchor.toLowerCase().contains(words[0]+" "+words[1]))
		{
				return 5;
		}
		else if(abchor.toLowerCase().contains(words[0])&&abchor.toLowerCase().contains(words[0]))
			return 2;
		else if(abchor.toLowerCase().contains(words[0])||abchor.toLowerCase().contains(words[0]))
			return 2;
		else
			return 0;
	}
	
	
	public static int numoftwogram(String[] words,ArrayList<Integer> word1, ArrayList<Integer> word2)
	{
		int m=0,j=0;
		int num=0;
		while (m < word1.size() && j < word2.size())
	    {
	        if (word1.get(m) < word2.get(j))       
	        {
	        	int x = word2.get(j)-words[0].length()-1;
	        	
	        	if(word1.get(m)==x)
	        	{
	        		
	        		num++;
	        		m++;
	        		j++;
	        	}
	        	else
	        		m++;
	        }
	        else
	        	j++;
	    }
		return num;
	}
	 public static void getleastfrequecy(Map<String, Double>frequencymap,Map<String, Double>frequencymap1,Map<String, Double>frequencymap2)
	 {
		 for(Entry<String, Double> entry : frequencymap.entrySet()) 
		 {
			 if(frequencymap1.get(entry.getKey())!=null&&frequencymap2.get(entry.getKey())!=null)
			 {
				 Double newvalue =frequencymap.get(entry.getKey())-frequencymap1.get(entry.getKey())-frequencymap2.get(entry.getKey());
				 if(frequencymap1.get(entry.getKey())>frequencymap2.get(entry.getKey()))
		    		frequencymap.put(entry.getKey(), newvalue+frequencymap2.get(entry.getKey()));
				 else
		    		frequencymap.put(entry.getKey(), newvalue+frequencymap1.get(entry.getKey()));
			 }
		 }
	 }
	 
	
	public static boolean checkduplicate(String x, String previous)
    {
    	if(x.length()>previous.length())
		{
			String diff =x.substring(previous.length());
			//System.out.print(x+" "+ previous +" "+diff+"\n");
			if(diff.equals("index") || diff.equals("index.php") || diff.equals(".php"))
				return true;
			else
    			return false;
		}
    	else if(x.length()<previous.length())
		{
			String diff = previous.substring(x.length());
			//System.out.print(x+" "+ previous +" "+diff+"\n");
			if(diff.equals("index") || diff.equals("index.php") || diff.equals(".php"))
				return true;
			else
    			return false;		
		}else {
    		if(x.equals(previous))
    			return true;
    		else
    			return false;
    	}
    	
    }
	
	 public static boolean isInteger(String s) {
	        try { 
	            Integer.parseInt(s); 
	        } catch(NumberFormatException e) { 
	            return false; 
	        }
	        // only got here if we didn't return false
	        return true;
	    }

	public static void printmap(Map<Integer, page> pagemap) {
		for(Entry<Integer, page> entry : pagemap.entrySet()) {
			//rebuild location function	
				
				Integer id =entry.getKey();
				String a = entry.getValue().geturl();
				String b = entry.getValue().getanchor();
				Double c = entry.getValue().getrank();
				String d = entry.getValue().gettitle();
				System.out.println(id+" "+a+" "+d+" "+b+" "+c);
		}	
		
	}


	
    
}
