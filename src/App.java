

import graph.*;
import algorithms.*;
import tools.GraphLoader;

import java.util.List;

public class App
{
	public static void main(String[] args)
	{
		System.out.println("Running tests ...");
		tests();

		// Graph enron = new Graph();
		// tools.GraphLoader.loadGraph(enron, "data/email-Enron.txt");
		
  		// System.out.println("The undirected Enron graph has " + enron.getNumNodes() + " nodes.");
		// System.out.println("Undirected facebook_1000 graph has " + enron.getNumEdges() + " edges.");

		// long start = System.currentTimeMillis();
		// List<Node> enronVertexCover = VertexCover.smartTree(enron);
		// long end = System.currentTimeMillis();

		// double seconds = end/1000.0 - start/1000.0;
		// System.out.println("The program a MINIMUM vertex cover set in " + seconds + " seconds.");
		// System.out.println("The minimum vertex cover has " + enronVertexCover.size() + " nodes.");
		// System.out.println("The minimum vertex cover that was found is ...");
		// System.out.println(enronVertexCover);

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

        List<Node> takeTwoVertexCover1 = VertexCover.takeTwoApproximation(test1);
        System.out.print("Vertex Cover with take two approximation: ");
        System.out.println(takeTwoVertexCover1);

        List<Node> treeVertexCover1 = VertexCover.smartTree(test1);
        System.out.print("Vertex Cover with search tree: ");
        System.out.println(treeVertexCover1);

        
        // TEST 2
        System.out.println("\n\nTest 2: ... ");
		Graph test2 = new Graph();
		GraphLoader.loadGraph(test2, "data/dominating_set_test.txt");
		System.out.println(test2.exportGraphString());

		List<Node> dominatingSet2 = DominatingSet.findGreedyDominatingSet(test2);
		System.out.print("Dominating Set: ");
		System.out.println(dominatingSet2);

		List<Node> takeTwoVertexCover2 = VertexCover.takeTwoApproximation(test2);
        System.out.print("Vertex Cover with take two approximation: ");
        System.out.println(takeTwoVertexCover2);

        List<Node> treeVertexCover2 = VertexCover.smartTree(test2);
        System.out.print("Vertex Cover with search tree: ");
        System.out.println(treeVertexCover2);


		// TEST 3
		System.out.println("\n\nTest 3: ...");
		Graph test3 = new Graph();
		GraphLoader.loadGraph(test3, "data/facebook_1000.txt");
		System.out.println(test3.exportGraphString());

		List<Node> dominatingSet3 = DominatingSet.findGreedyDominatingSet(test3);
		System.out.print("Dominating Set: ");
		System.out.println(dominatingSet3);

		List<Node> takeTwoVertexCover3 = VertexCover.takeTwoApproximation(test3);
        System.out.println("Vertex Cover with take two approximation: ");
        System.out.println("Size: " + takeTwoVertexCover3.size());
        System.out.println(takeTwoVertexCover3);

        List<Node> treeVertexCover3 = VertexCover.smartTree(test3);
        System.out.println("Vertex Cover with search tree: ");
        System.out.println("Size: " + treeVertexCover3.size());
        System.out.println(treeVertexCover3);
	}
}