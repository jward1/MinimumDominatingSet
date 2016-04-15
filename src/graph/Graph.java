/**
 * Graph class. Controls the graph structure and algorithms
 * @author jward1
 */
package graph;

import tools.RandomGraph;

import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;


public class Graph
{
	private HashMap<Integer, Node> nodes;	// The nodes of the Graph
	private HashSet<Edge> edges;			// The edges of the Graph; not sure if this is useful
	private int numNodesCovered;			// The number of Nodes marked as 'covered'; 
											// used to search for Dominating Set.

	/** 
	 * Creates a new Graph object.
	 */
	public Graph()
	{
		nodes = new HashMap<Integer, Node>();
		edges = new HashSet<Edge>();
		numNodesCovered = 0;
	}


	/** 
	 * Adds a node to the graph.
	 * @param key An integer that acts as node's name
	 */
	public void addNode(int key)
	{
		if ( nodes.get(key) == null)
		{
			Node newNode = new Node(key);
			nodes.put(key, newNode);
		}
		return;
	}

	
	/** 
	 *Adds an edge to the graph.
	 * @param from The name of the Node at which the edge starts
	 * @param to The name of the Node at which the edge ends 
	 */
	public void addEdge(int from, int to)
	{	
		// ensure that the from and to nodes are in the Graph; add them if not
		if (!nodes.containsKey(from))	addNode(from);
		if (!nodes.containsKey(to)) 	addNode(to);
		
		// extract Node objects from Graph and construct new Edge object
		Node fromNode = nodes.get(from);
		Node toNode = nodes.get(to);
		Edge newEdge = new Edge(fromNode, toNode);

		// if Edge is not in the Graph; add it
		if (!edges.contains(newEdge))	edges.add(newEdge);
		fromNode.addEdge(newEdge); 			// add outgoing Edge to from Node 
		toNode.addIncomingEdge(fromNode); 	// keep track of where Edges come from 
											// (used for preprocessing Dominating Set)
	}


	/** 
	 * Returns the nodes of the Graph
	 * @return A List of Nodes that contained in the Graph
	 */
	public List<Node> getNodes()
	{	return new ArrayList<Node>(nodes.values());	}


	/** 
	 * Returns the total number of edges in the Graph
	 * @return An int representing the number of edges in the Graph
	 */
    public int getNumEdges()
    {	return edges.size(); }


    /** 
     * Returns the total number of nodes in the Graph
     * @return An int representing the number of nodes in the Graph
     */
    public int getNumNodes()
    {	return nodes.size(); }


	/** 
	 * Returns the graph in String format. Runs in O(|V + E|).
	 * The line '0--> [ 1 2 ]' is read Node 0 is the starting point of edges connecting
	 * to Node 1 and Node 2
	 * @return A String representation of the Graph
	 */
	public String exportGraphString()
    {
    	String s = "";
        for (int key : nodes.keySet() )
        {
            s += "\n" + key + "--> [";
            Node node = nodes.get(key);
            
            for (Edge edge : node.getEdges())
            {
            	s += " " + edge.toString();
            }
            s += " ]";
        }
        return s;
    }


    /**
     * Returns a list of the Nodes that are included in the Dominating Set.
     * This implementation uses a Greedy Algorithm to find the dominating set 
     * of the graph. The Dominating Set, D, is a subset of nodes such that every
     * not in D is adjacent to at least one node in D.
     *
     * If the number of nodes in the graph is greater than 32, this implementation
     * a preprocessing algorithm to improve the search time. The preprocessing 
     * algorithm looks for special cases where certain nodes will always be in the
     * the Dominating Set (e.g., Nodes with only outgoing edges will have to be included
     * in the set).
     *
     * Note. This implementation on returns a Dominating Set. It does not return the 
     * minimum (or smallest) dominating set.
     *
     * @return A LinkedList of Integers representing the nodes included in the dominating set.
     */
    public LinkedList<Integer> findGreedyDominatingSet()
    { 
    	if ( getNumNodes() < 33 ) 	{ return findGreedyDominatingSet(false); }
    	else						{ return findGreedyDominatingSet(true);  }
    }


    /**
     * Returns a list of the Nodes that are included in the Dominating Set after preprocessing
     * the Graph. 
     * This method is used in the RandomGraph class in to test algorithm implementations. Users 
     * are encouraged to use the findGreedyDominatingSet() method in the Graph class.
     * @return A LinkedList of Integers representing the nodes included in the dominating set.
     */
    public LinkedList<Integer> testFindGreedyDominatingSetWithPreprocessing()
    { return findGreedyDominatingSet(true); }


    /**
     * Returns a list of the Nodes that are included in the Dominating Set withou preprocessing
     * the Graph. 
     * This method is used in the RandomGraph class in to test algorithm implementations. Users 
     * are encouraged to use the findGreedyDominatingSet() method in the Graph class.
     * @return A LinkedList of Integers representing the nodes included in the dominating set.
     */
    public LinkedList<Integer> testFindGreedyDominatingSetWithoutPreprocessing()
    { return findGreedyDominatingSet(false); }

    /**
     * Resets all Nodes in graph to uncovered.
     */
    public void resetGraph()
    {
    	for (Node node : nodes.values() )
    	{
    		node.setIsCovered(false);
    		numNodesCovered--;
    	}
    	return;
    }


    /**
     * Implements the findGreedyDominatingSet algorithm above.
     * @param preprocess A boolean indicating whether the Graph should be preprocessed.
     * @return A LinkedList of Integers representing the nodes included in the dominating set.
     */
    private LinkedList<Integer> findGreedyDominatingSet(boolean preprocess)
    {
    	// keep track of number of nodes and number of uncovered nodes
    	int numNodes = nodes.size();

    	// number of nodes visited, e.g., added to dominating set
    	LinkedList<Integer> visited = new LinkedList<Integer>();
    	LinkedList<Integer> cannotBePartOfSet = new LinkedList<Integer>();

    	// find Nodes that must be included in Dominating Set (e.g., nodes with only outgoing
    	// edges or nodes with only one incoming edge and no outgoing edges)
    	if (preprocess) { proprocessGraph(visited); }

    	// make sure all nodes become covered
    	while (numNodesCovered != numNodes )
    	{	
    		int maxCoverNum = 0;
    		int maxCoverKey = -1;
    		
			// find the node that will cover that greatest amount of other nodes
    		for (int key : nodes.keySet())
    		{	
    			if (!visited.contains(key) && !cannotBePartOfSet.contains(key))
    			{
	    			int uncoveredNeighbors = getNumberUncoveredNeighbors(key);
					if (uncoveredNeighbors > maxCoverNum)
					{
						maxCoverNum = uncoveredNeighbors;
						maxCoverKey = key;
					}
					
					// if key cannot add any new nodes to coverage, remove from future searches
					if (uncoveredNeighbors == 0) {	cannotBePartOfSet.add(key); }
				}
    		}

    		// after finding max cover node, cover it an all of its neighbors
    		Node newMax = nodes.get(maxCoverKey);
    		updateCover(newMax);
    		for (Node neighbor : newMax.getNeighbors() )
    			updateCover(neighbor);
    		// add newMax to list of visited nodes
    		visited.add(maxCoverKey);
    	
    	}
    	
    	Collections.sort(visited);
    	return visited;
    }


    /**
     * Returns the number of neighbors of a given node that are uncovered.
     * @param name An integer representing the name of a node
     * @return An integer representing the number of uncovered neighbors
     */
    private int getNumberUncoveredNeighbors(int name)
    {
		Node candidate = nodes.get(name);
		int numUncovered = 0;

		if (!candidate.isCovered()) { numUncovered++; }

		for (Node neighbor : candidate.getNeighbors() )
			if (!neighbor.isCovered() ) {	numUncovered++; }

		return numUncovered;
    }


    /**
     * Finds nodes that must be included in the dominating set.
     * Nodes could that must be included in the dominating set must meet one of the 
     * following three conditions:
     *		1) 	Node has zero outgoing edges and only one incoming edge; therefore, the
	 *		neighbor from which the incoming edge originates must be in the dominating set
	 *		2)	Node has zero incoming edges and one or more outgoing edges; therefore, the only
	 *			way of this node to see a message is if it sends the message
	 *		3)	Node has zero neighbors (i.e., no incoming or outgoing edges)
	 * This method runs in O(|V|+|E|) in the worst case and O(|V|) in the best case.
	 * @param The LinkedList of visited nodes.	
     */
    private void proprocessGraph(LinkedList<Integer> visitedInPreprocess)
    {	
 
    	for (Node node : nodes.values() )
    	{	
    		Node nodeToCover = null;

    		// case 1: node only has one incoming edge; therefore the other edge must be in cover
    		if (node.getNumOutgoingEdges() == 0 && node.getNumIncomingEdges() == 1)
    		{
    			HashSet<Node> neighbors = node.getIncomingEdges();
    			// NOTE! Only one node will be in neighbors; need to find better way of extracting node
    			for (Node neighbor : neighbors)
    				nodeToCover = neighbor;
    		}
    		// case 2: node only has outgoing edges and therefore must be included in set
    		else if (node.getNumOutgoingEdges() > 0 && node.getNumIncomingEdges() == 0)
	    		nodeToCover = node;
    		// case 3: node does not have any neighbors and therefore must be in set
    		else if (node.getNumOutgoingEdges() == 0 && node.getNumIncomingEdges() == 0)
	    		nodeToCover = node;

    		if (nodeToCover != null && !visitedInPreprocess.contains(nodeToCover.getName() ) ) 
	    	{
		    	updateCover(nodeToCover) ; 
				coverNodesNeighbors(nodeToCover);
				visitedInPreprocess.add(nodeToCover.getName());
	    	}
    	}
    	return;
    }


    /**
     *
     */
    private void coverNodesNeighbors(Node node)
    {
    	for (Node neighbor : node.getNeighbors() )
    		updateCover(neighbor) ;
    	return;
    }


    /**
     *
     */
    private void updateCover(Node node)
    {
    	if (!node.isCovered() )  
    	{
    		node.setIsCovered(true);
    		numNodesCovered++;
    	}
    }


    /**
     * Tests the methods of this class.
     */
    public static void tests()
    {
    	System.out.println("\n\nTest 1a: Create graph with 6 vertices; run Greedy Algo.");
    	Graph r1 = RandomGraph.getRandomGraph(6, 4);
    	System.out.println(r1.exportGraphString());
    	LinkedList<Integer> ds1a = r1.testFindGreedyDominatingSetWithoutPreprocessing();
    	System.out.print("Dominating Set: ");
		System.out.println(ds1a);

		r1.resetGraph();

		System.out.println("\n\nTest 1b: Create graph with 6 vertices; run Greedy Algo.");
    	LinkedList<Integer> ds1b = r1.testFindGreedyDominatingSetWithPreprocessing();
    	System.out.print("Dominating Set: ");
		System.out.println(ds1b);

		System.out.println("\n\nTest 2: Create random graph with 20 vertices; run Greedy Algo");
		Graph r2 = RandomGraph.getRandomGraph(20, 5);
		System.out.println(r2.exportGraphString());
		LinkedList<Integer> ds2 = r2.testFindGreedyDominatingSetWithoutPreprocessing() ;
		System.out.print("Dominating Set: ");
		System.out.println(ds2);

		System.out.println("\n\nTest 3a: ... ");
		Graph test3a = new Graph();
		tools.GraphLoader.loadGraph(test3a, "data/dominating_set_test_2.txt");
		System.out.println(test3a.exportGraphString());
		LinkedList<Integer> dominatingSet3a = test3a.findGreedyDominatingSet();
		System.out.print("Dominating Set: ");
		System.out.println(dominatingSet3a);

		System.out.println("\n\nTest 3b: ...");
		Graph test3b = new Graph();
		tools.GraphLoader.loadGraph(test3b, "data/dominating_set_test_2.txt");
		System.out.println(test3b.exportGraphString());
		LinkedList<Integer> dominatingSet3b = test3b.findGreedyDominatingSet();
		System.out.print("Dominating Set with Preprocessing: ");
		System.out.println(dominatingSet3b);

		System.out.println("\n\nTest 4a: ...");
		Graph test4a = new Graph();
		tools.GraphLoader.loadGraph(test4a, "data/small_test_graph.txt");
		System.out.println(test4a.exportGraphString());
		LinkedList<Integer> dominatingSet4a = test4a.findGreedyDominatingSet();
		System.out.print("Dominating Set: ");
		System.out.println(dominatingSet4a);

		System.out.println("\n\nTest 4b: ...");
		Graph test4b = new Graph();
		tools.GraphLoader.loadGraph(test4b, "data/small_test_graph.txt");
		System.out.println(test4b.exportGraphString());
		LinkedList<Integer> dominatingSet4b = test4b.findGreedyDominatingSet();
		System.out.print("Dominating Set: ");
		System.out.println(dominatingSet4b);

		System.out.println("\n\nTest 5: ...");
		Graph test2 = new Graph();
		tools.GraphLoader.loadGraph(test2, "data/dominating_set_test.txt");
		System.out.println(test2.exportGraphString());
		LinkedList<Integer> dominatingSet2 = test2.findGreedyDominatingSet();
		System.out.print("Dominating Set: ");
		System.out.println(dominatingSet2);
    }


	public static void main(String[] args)
	{
		// tests();

		Graph testEnron = new Graph();
		tools.GraphLoader.loadGraph(testEnron, "data/email-Enron.txt");
		
		long start = System.currentTimeMillis();
		LinkedList<Integer> enronDominatingSet = testEnron.findGreedyDominatingSet();
		long end = System.currentTimeMillis();

		double seconds = end/1000.0 - start/1000.0;
		System.out.println("The program found a dominating set in " + seconds + " seconds.");
		System.out.println("The dominating set found is ...");
		System.out.println(enronDominatingSet);
	}

}