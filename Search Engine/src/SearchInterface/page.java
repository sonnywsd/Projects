package SearchInterface;

public class page {
	private int id;
	private String url;
	private String title;
	private String anchor;
	private Double rank;
	
	public page(int id,String url, String title, String anchor,Double rank)
	{
		this.id = id;
		this.url=url;
		this.title=title;
		this.anchor=anchor;
		this.rank=rank;
	}
	public int getid()
	{
		return id;
	}
	public String geturl()
	{
		return url;
	}
	public String gettitle()
	{
		return title;
	}
	public String getanchor()
	{
		return anchor;
	}
	public Double getrank()
	{
		return rank;
	}
	public void setrank(Double rank)
	{
		this.rank = rank;
	}
}
