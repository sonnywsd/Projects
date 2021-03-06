package BuildIndex;

import java.io.*;

import edu.uci.ics.jung.algorithms.scoring.PageRank;

import java.util.Set;
import java.util.TreeSet;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

public class PageRankCalc {

    DirectedGraph<Integer, String> g =
        new DirectedSparseGraph<Integer, String>();
    
    private void readFile(String folder,String filename) throws IOException {
       
    	File file = new File(folder,filename);
    	FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;
        while ((line = br.readLine()) != null) {
            String[] result = line.split("\\s+");
            g.addEdge(result[0] + " " + result[1],
                Integer.parseInt(result[0]),
                Integer.parseInt(result[1]));
        }
        
        br.close();
    }
    
    public static void main(String args[]) throws IOException {
    	String filename = "pagerank.txt";
		
		File wfile = new File("graph",filename);
		if (!wfile.exists()) {
			try {
				wfile.createNewFile();
			} catch (IOException e) {
				System.out.print("Create write file fail\n");
			}
		} 
		
        PageRankCalc prc = new PageRankCalc();
        
        prc.readFile("graph","idgraph.txt");
        PageRank<Integer, String> pr =  new PageRank<Integer, String>(prc.g, 0.15);
        pr.evaluate();
        double sum = 0;
        Set<Integer> sortedVerticesSet =
            new TreeSet<Integer>(prc.g.getVertices());
        
        
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(wfile, true));
        for (Integer v : sortedVerticesSet) {
            double score = pr.getVertexScore(v);
            sum += score;
            //System.out.println(v + " = " + score);
            pw.write(v + " " + score + "\n");
        }
        pw.close();
        System.out.println("s = " + sum);
		}catch (IOException e) {
			e.printStackTrace();
		}
    }
}