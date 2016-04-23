/**
 * @author UCSD MOOC development team
 * 
 * Utility class to add vertices and edges to a graph
 *
 */
package tools;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GraphLoader 
{
    /**
     * Loads graph from text file.
     * 
     * @param g An empty Graph object.
     * @param filename The file containg the graph data to load. The file should
     *                 consist of two columns of integers, a "from" vertex and
     *                  a "to" vertex.
     * @return A Graph object containg the nodes and edges from the file.
     */ 
    public static void loadGraph(graph.Graph g, String filename) {
        
        Set<Integer> seen = new HashSet<Integer>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
        while (sc.hasNext()) 
        {
        	if (sc.hasNextInt())
        	{
	            int v1 = sc.nextInt();
	            int v2 = sc.nextInt();
	            if (!seen.contains(v1)) {
	                g.addNode(v1);
	                seen.add(v1);
	            }
	            if (!seen.contains(v2)) {
	                g.addNode(v2);
	                seen.add(v2);
	            }
	            g.addEdge(v1, v2);
	        }
	        else
	        {
	        	sc.nextLine();
	        }
        }
        
        sc.close();
    }
}
