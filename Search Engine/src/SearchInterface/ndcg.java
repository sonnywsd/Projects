package SearchInterface;

import java.io.*;

public class ndcg {
	private double [] idealdata = new double[]{5.0,9.0,10.89,11.89,12.32};
	private double[][] matrix = new double[10][5];
	private double [] ndcgat5 = new double[10];
	
	public void readfile(File file)
	{
		BufferedReader reader;
		String text;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			int i=0;
			while ((text = reader.readLine()) != null&& text.length() > 0) 
			{
				String [] words = text.split(",");
				matrix[i] = getvalue(words);
				i++;
			}
			reader.close(); 
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	public double[] getvalue(String [] words)
	{
		double[] onequeryvalue = new double[5] ;
		int i=0;
		for(String word: words)
		{
			if(word.length()>0)
			{
				onequeryvalue[i] = Integer.parseInt(word);
				//System.out.println(onequeryvalue[i]);
				i++;
			}
		}
		return onequeryvalue;
	}
	
	public void calculatendcg()
	{
		for(int i=0;i<10;i++)
		{
			ndcgat5[i] = 0.0;
			ndcgat5[i]+= matrix[i][0];
			
			ndcgat5[i]+= matrix[i][1];
			ndcgat5[i]+= matrix[i][2]/(Math.log(3)/Math.log(2));
			ndcgat5[i]+= matrix[i][3]/2;
			ndcgat5[i]+= matrix[i][4]/(Math.log(5)/Math.log(2));
			ndcgat5[i] = ndcgat5[i]/idealdata[4];
			System.out.printf("%.3f, ", ndcgat5[i]);
		}
		double total=0.0;
		for(int i=0;i<10;i++)
		{
			total += ndcgat5[i];
			
		}
		System.out.println();
		System.out.printf("Average:%.3f \n", total/10);
	}
	
	public static void main(String args[]){
		ndcg test = new ndcg();
		test.readfile(new File("dcg.txt"));
		test.calculatendcg();
	}
}
