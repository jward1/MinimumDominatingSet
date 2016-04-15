

import graph.Graph;
import graph.Node;
import graph.DominatingSet;
import tools.GraphLoader;

import java.util.List;

public class App
{
	public static void main(String[] args)
	{
		System.out.println("Running tests ...");
		tests();

		Graph testEnron = new Graph();
		tools.GraphLoader.loadGraph(testEnron, "data/email-Enron.txt");
		
        System.out.println("Undirected Enron graph has " + testEnron.getNumNodes() + " nodes.");
        System.out.println("Undirected Enron graph has " + testEnron.getNumEdges() + " edges.");

		// long start = System.currentTimeMillis();
		// LinkedList<Integer> enronDominatingSet = testEnron.findGreedyDominatingSet();
		// long end = System.currentTimeMillis();

		// double seconds = end/1000.0 - start/1000.0;
		// System.out.println("The program found a dominating set in " + seconds + " seconds.");
		// System.out.println("The dominating set found is ...");
		// System.out.println(enronDominatingSet);
	}


	public static void tests()
	{
		// TEST 1
		System.out.println("\n\nTest 1: ...");
        Graph test1 = new Graph();
        GraphLoader.loadGraph(test1, "data/small_test_graph.txt");
        System.out.println(test1.exportGraphString());

        List<Node> dominatingSet1a = DominatingSet.findGreedyDominatingSet(test1);
        System.out.print("Dominating Set: ");
        System.out.println(dominatingSet1a);


        // TEST 2
        System.out.println("\n\nTest 2: ... ");
		Graph test2 = new Graph();
		GraphLoader.loadGraph(test2, "data/dominating_set_test.txt");
		System.out.println(test2.exportGraphString());

		List<Node> dominatingSet2 = DominatingSet.findGreedyDominatingSet(test2);
		System.out.print("Dominating Set: ");
		System.out.println(dominatingSet2);


		// TEST 3
		System.out.println("\n\nTest 3: ...");
		Graph test3 = new Graph();
		GraphLoader.loadGraph(test3, "data/dominating_set_test_2.txt");
		System.out.println(test3.exportGraphString());

		List<Node> dominatingSet3 = DominatingSet.findGreedyDominatingSet(test3);
		System.out.print("Dominating Set: ");
		System.out.println(dominatingSet3);
	}
}