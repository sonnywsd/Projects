package BuildIndex;

public class term {
	String word;
	String tf;
	
	public term(String word,String tf)
	{
		this.word = word;
		this.tf =tf;
	}
	
	public String getword()
	{
		return word;
	}
	public String gettf()
	{
		return tf;
	}
}
