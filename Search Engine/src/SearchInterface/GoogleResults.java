package SearchInterface;

import java.io.*;
import java.net.*;
import java.util.List;

import com.google.gson.Gson;

public class GoogleResults {

    private ResponseData responseData;
    public ResponseData getResponseData() { return responseData; }
    public void setResponseData(ResponseData responseData) { this.responseData = responseData; }
    public String toString() { return "ResponseData[" + responseData + "]"; }

    static class ResponseData {
        private List<Result> results;
        public List<Result> getResults() { return results; }
        public void setResults(List<Result> results) { this.results = results; }
        public String toString() { return "Results[" + results + "]"; }
    }

    static class Result {
        private String url;
        private String title;
        public String getUrl() { return url; }
        public String getTitle() { return title; }
        public void setUrl(String url) { this.url = url; }
        public void setTitle(String title) { this.title = title; }
        public String toString() { return "Result[url:" + url +",title:" + title + "]"; }
    }
    public static void main(String[] args) throws Exception {
        String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
        String[] search =new String [10];
        search[0] = "mondego site:ics.uci.edu";
        search[1] = "machine learning site:ics.uci.edu";
        search[2] = "software engineering site:ics.uci.edu";
        search[3] = "security site:ics.uci.edu";
        search[4] = "student affairs site:ics.uci.edu";
        search[5] = "graduate courses site:ics.uci.edu";
        search[6] = "Crista Lopes site:ics.uci.edu";
        search[7] = "REST site:ics.uci.edu";
        search[8] = "computer games site:ics.uci.edu";
        search[9] = "information retrieval site:ics.uci.edu";

        String charset = "UTF-8";
        
        String fileName = "googleResult.txt";
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.print("Create Subdomain file fail\n");
			}
		} 
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(file, true));
	        for( int i=0;i<10;i++)
	        {
	        	URL[] url = new URL[3];
	        	url[0]= new URL(google + URLEncoder.encode(search[i], charset));
	        	url[1]= new URL(google + URLEncoder.encode(search[i], charset)+"&start=4");
	        	url[2]= new URL(google + URLEncoder.encode(search[i], charset)+"&start=8");
	        	System.out.println(search[i]);
	        	pw.write(search[i]+"\n");
	        	for(int m=0;m<3;m++)
	        	{
	        		URL urls = url[m];
	        		Reader reader = new InputStreamReader(urls.openStream(), charset);
	        		GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
	        		//System.out.println(results.getResponseData().getResults().size());
	        		if(results!=null)	
	        		{	for(int j =0;j<4;j++)
		        		{
		        			// Show title and URL of first 10 result.
		        			//System.out.println(results.getResponseData().getResults().get(j).getTitle());
		        			pw.write(results.getResponseData().getResults().get(j).getUrl()+"\n");
		        			System.out.println(results.getResponseData().getResults().get(j).getUrl());
		        			
		        		}
	        		}//System.out.println("New");
	        	}
	        	
	        }
	        pw.close();
		} catch (IOException e) {
			System.out.print("Create file Printer failed\n");
		}
    }
}