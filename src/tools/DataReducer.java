package tools;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class DataReducer
{
	
	public static void reduceSizeOfData(String filename, int numNodes, int numEdges)
	{
        int edgeCount = 0;

        // create scanner object and open file
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
        while (sc.hasNext() && edgeCount <= numEdges) 
        {
        	if (sc.hasNextInt())
        	{
	            int v1 = sc.nextInt();
	            int v2 = sc.nextInt();

	            if (v1 >= numNodes)
	            	v1 = v1 % numNodes;

	            if (v2 >= numNodes)
	            	v2 = v2 % numNodes;

	            if (v1 != v2) 
	            {
		            System.out.print(v1 + " ");
		            System.out.println(v2);
		            edgeCount++;	            	
	            }
	        }
	        else
	        {
	        	sc.nextLine();
	        }
        }
        
        sc.close();
	}


	public static void main(String[] args)
	{
		String fileToReduce = args[0];
		int numberOfNodes = Integer.parseInt(args[1]);
		int numberOfEdges = Integer.parseInt(args[2]);

		System.out.println("File to reduce: " + fileToReduce);
		System.out.println("Number of nodes: " + numberOfNodes);
		System.out.println("Number of edges: " + numberOfEdges);

		reduceSizeOfData(fileToReduce, numberOfNodes, numberOfEdges);
	}
}
