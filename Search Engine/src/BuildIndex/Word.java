package BuildIndex;

import java.util.ArrayList;


public class Word {
	
	int frequency;
	String word;
	ArrayList<Integer> locations =  new ArrayList<Integer>();
	
	public Word(int frequency, String word, ArrayList<Integer> locations){
		this.word =word;
		this.frequency =frequency;
		this.locations = locations;
	}
	public int getfrequency()
	{
	 return frequency;
	}
	public String getword()
	{
	 return word;
	}
	
	public ArrayList<Integer> getlocationlist()
	{
		return locations;
	}
}
