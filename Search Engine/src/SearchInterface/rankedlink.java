package SearchInterface;

public class rankedlink {
	private String url;
	private Double score;
	
	public rankedlink(String url,Double score)
	{
		this.url = url;
		this.score = score;
	}
	public String geturl()
	{
		return url;
	}
	public Double getscore()
	{
		return score;
	}
}
