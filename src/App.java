

import graph.*;
import algorithms.*;
import tools.GraphLoader;

import java.util.List;

public class App
{
	public static void main(String[] args)
	{
		// System.out.println("Running tests ...");
		// tests();

		Graph fb = new Graph();
		tools.GraphLoader.loadGraph(fb, "data/facebook_2000.txt");
		
  		System.out.println("The undirected facebook_2000 graph has " + fb.getNumNodes() + " nodes.");
		System.out.println("Undirected facebook_2000 graph has " + fb.getNumEdges() + " edges.");

		long start = System.currentTimeMillis();
		List<Node> fbDominatingSet = DominatingSet.findMinimumDominatingSet(fb);
		long end = System.currentTimeMillis();

		double seconds = end/1000.0 - start/1000.0;
		System.out.println("The program found a minimum dominating set in " + seconds + " seconds.");
		System.out.println("The dominating set found is ...");
		System.out.println(fbDominatingSet);
	}


	public static void tests()
	{
		// TEST 1
		System.out.println("\n\nTest 1: ...");
        Graph test1 = new Graph();
        GraphLoader.loadGraph(test1, "data/small_test_graph.txt");
        System.out.println(test1.exportGraphString());

        List<Node> dominatingSet1 = DominatingSet.findGreedyDominatingSet(test1);
        System.out.print("Greedy Dominating Set: ");
        System.out.println(dominatingSet1);

        List<Node> minDominatingSet1 = DominatingSet.findMinimumDominatingSet(test1);
        System.out.print("Minimum Dominating Set: ");
        System.out.println(minDominatingSet1);


        
        // TEST 2
        System.out.println("\n\nTest 2: ... ");
		Graph test2 = new Graph();
		GraphLoader.loadGraph(test2, "data/dominating_set_test.txt");
		System.out.println(test2.exportGraphString());

		List<Node> dominatingSet2 = DominatingSet.findGreedyDominatingSet(test2);
		System.out.print("Dominating Set: ");
		System.out.println(dominatingSet2);

		List<Node> minDominatingSet2 = DominatingSet.findMinimumDominatingSet(test2);
		System.out.print("Minimum Dominating Set: ");
		System.out.println(minDominatingSet2);



		// TEST 3
		System.out.println("\n\nTest 3: ...");
		Graph test3 = new Graph();
		GraphLoader.loadGraph(test3, "data/dominating_set_test_2.txt");
		System.out.println(test3.exportGraphString());

		List<Node> dominatingSet3 = DominatingSet.findGreedyDominatingSet(test3);
		System.out.print("Dominating Set: ");
		System.out.println(dominatingSet3);

		List<Node> minDominatingSet3 = DominatingSet.findMinimumDominatingSet(test3);
		System.out.print("Minimum DominatingSet: ");
		System.out.println(minDominatingSet3);

	}
}