package tools;

import graph.Graph;
import graph.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.LinkedList;

public class RandomGraph extends Graph
{	
	/**
	 * Returns a randomly generated Graph object.
	 * @param numNodes The number of nodes to be created in the Graph.
	 * @param maxNumberNeighbors The maximum number of neighbors any node can have.
	 * @return A Graph object with randomly generated Nodes and Edges
	 */
	public static Graph getRandomGraph(int numNodes, int maxNumberNeighbors)
		throws IllegalArgumentException
	{	
		if (maxNumberNeighbors > numNodes || maxNumberNeighbors < 1)
			throw new IllegalArgumentException("Maximum number of neighbors must be between 1 and (number of nodes - 1) in graph.");
		
		Graph randomGraph = new Graph();
		createRandomGraph(randomGraph, numNodes, maxNumberNeighbors);
		return randomGraph;
	}


	/**
	 * Creates the Random Graph
	 * @param rGraph An empty Graph object
	 * @param numberNodes The number of Nodes to be created in the Graph
	 * @param maxNeighbors The maximum number of neighbors any node can have.
	 */
	private static void createRandomGraph(Graph rGraph, int numberNodes, int maxNeighbors)
	{	
		Random rand = new Random();



		for (int i=0; i<numberNodes; i++)
			rGraph.addNode(i);

		for (int fromNode=0; fromNode<numberNodes; fromNode++)
		{	
			ArrayList<Integer> potentialNeighbors = getPotentialNeighbors(fromNode, numberNodes);
			int numNeighbors = rand.nextInt(maxNeighbors);

			Collections.shuffle(potentialNeighbors);

			for (int j=0; j<numNeighbors; j++)
				rGraph.addEdge(fromNode, potentialNeighbors.get(j) );
		}
		return;
	}

	/**
	 * Creates a List of all the potential neighbors that a Node can have.
	 * @param origin The Node for which a list of potential neighbors needs to be generated.
	 * @param numberOfNodes The number of Nodes in the Graph
	 * @return An ArrayList of Integers
	 */
	private static ArrayList<Integer> getPotentialNeighbors(int origin, int numberOfNodes)
	{
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		for (int i=0; i<numberOfNodes; i++)
		{
			if (i != origin)
				neighbors.add(new Integer(i));
		}
		return neighbors;

	}


	/** 
	 * Creates a series of random graphs and calculates the amount of time it takes to find the Dominating Set.
	 * The method works by creating an a graph of size 4, running the dominating set algorithms on that graph,
	 * and then prints the results. After it completes one size, it double the size of the graph and repeats. 
	 * This method runs 10 trials for each size of graph, with a maximum graph size of 5000 nodes. Users can 
	 * specify their own number of trials and max size of graph by calling doublingTest(int, int).
	 */
	public static void doublingTest()
	{	doublingTest(10, 5000); }
	

	/** 
	 * Creates a series of random graphs and calculates the amount of time it takes to find the Dominating Set.
	 * The method works by creating an a graph of size 4, running the dominating set algorithms on that graph,
	 * and then prints the results. After it completes one size, it double the size of the graph and repeats.
	 */
	public static void doublingTest(int iterationsPerSize, int maxSize)
	{
		int num = 4;
		
		System.out.println("NumberNodes, NumberEdges, SecondsWithoutPreprocessing, SecondsWithPreprocessing, " 
			+  "SizeOfDomSetWithout, SizeOfDomSetWithPrepocessing, ProducedSameDomSet, "
			+  "LowerBound, UpperBound");
		
		while (num <= maxSize)
		{
			for (int i=0; i<iterationsPerSize; i++)
			{	
				// store results in order to check both algos reach same solution
				LinkedList<Integer> wo;
				LinkedList<Integer> w;

				// initialize variables to store amount of time each algo takes
				long withoutPre;
				long withPre;

				// create a random graph object
				Graph r = getRandomGraph(num, num/4);
				
				// run greedy search without preprocessing
				long start1 = System.currentTimeMillis();
				wo = r.testFindGreedyDominatingSetWithoutPreprocessing();
				long end1 = System.currentTimeMillis();
				withoutPre = (end1 - start1);

				// reset all nodes
				r.resetGraph();

				// run greedy search with preprocessing
				long start2 = System.currentTimeMillis();
				w = r.testFindGreedyDominatingSetWithPreprocessing();
				long end2 = System.currentTimeMillis();
				withPre = (end2 - start2);
				
				// output results
				boolean sameDomSet = w.equals(wo);
				int numEdges = r.getNumEdges();
				int lowerBound = num + numEdges;
				double upperBound = Math.pow(lowerBound, 2);
				System.out.println(num + ", "
									+ numEdges + ", "
									+ withoutPre + ", " 
									+ withPre + ", " 
									+ wo.size() + ", " 
									+ w.size() + ", " 
									+ sameDomSet + ", "
									+ lowerBound + ", "
									+ upperBound );
				
				// FOR DEBUGGING PURPOSES
				// System.out.println(r.exportGraphString());
				// if ( w.size() > wo.size() )
				// {
				// 	System.out.println(r.exportGraphString());
				// 	System.out.println("Solution with preprocessing was larger than solution without.");
				// 	System.out.println("Solution w/o preprocessing: " + wo);
				// 	System.out.println("Solution w/ preprocessing: " + w);
				// }
			}
			
			num = num*2;
		}
	}


	public static void main(String[] args)
	{	
		// System.out.println("\nTest 1: Create random graph with five vertices ...");
		// Graph r1 = getRandomGraph(5, 4);
		// System.out.println(r1.exportGraphString());

		// System.out.println("\nTest 2: Create random graph with ten vertices ...");
		// Graph r2 = getRandomGraph(10, 5);
		// System.out.println(r2.exportGraphString());

		// System.out.println("\nTest 3: Create random graph with 20 vertices ...");
		// Graph r3 = getRandomGraph(20, 2);
		// System.out.println(r3.exportGraphString());

		System.out.println("\nTest 4: Run doubling test ...");
		doublingTest(10, 5000);

	}
}