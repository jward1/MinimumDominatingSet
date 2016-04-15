/**
 * Graph class. Controls the graph structure and algorithms
 * @author jward1
 */
package graph;

//import tools.RandomGraph;

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
    private int numEdgesCovered;            // The number of Edges marked as 'covered';
                                            // used to search for Vertex Cover.

	/** 
	 * Creates a new Graph object.
	 */
	public Graph()
	{
		nodes = new HashMap<Integer, Node>();
		edges = new HashSet<Edge>();
		numNodesCovered = 0;
        numEdgesCovered = 0;
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
	 *Adds an undirected edge to the graph.
	 * @param v1 The name of the Node at one end of the edge
	 * @param v2 The name of the Node at the other end of the edge
	 */
	public void addEdge(int v1, int v2)
	{	
		// ensure that the from and to nodes are in the Graph; add them if not
		if (!nodes.containsKey(v1))	addNode(v1);
		if (!nodes.containsKey(v2)) addNode(v2);
		
		// extract Node objects from Graph and construct new Edge object
		Node node1 = nodes.get(v1);
		Node node2 = nodes.get(v2);
		Edge newEdge = new Edge(node1, node2);

        // do not add new edge if already contained in graph
        for (Edge edge : node1.getEdges() )
        {
            if (edge.isEqualTo(newEdge)) { return; }
        }

		// if Edge is not in the Graph; add it
        edges.add(newEdge);
        node1.addEdge(newEdge);
        node2.addEdge(newEdge);
        
	}


	/** 
	 * Returns the nodes of the Graph
	 * @return A List of Nodes that contained in the Graph
	 */
	public List<Node> getNodes()
	{	return new ArrayList<Node>(nodes.values());	}


    /** 
     * Returns the total number of nodes in the Graph
     * @return An int representing the number of nodes in the Graph
     */
    public int getNumNodes()
    {   return nodes.size(); }


	/** 
	 * Returns the total number of edges in the Graph
	 * @return An int representing the number of edges in the Graph
	 */
    public int getNumEdges()
    {	return edges.size(); }


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
            	s += " " + (edge.getOtherNode(node)).toString();
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
    private List<Integer> findGreedyDominatingSet()
    {
    	// keep track of number of nodes and number of uncovered nodes
    	int numNodes = nodes.size();

    	// number of nodes visited, e.g., added to dominating set
    	List<Integer> visited = new ArrayList<Integer>();
    	List<Integer> cannotBePartOfSet = new ArrayList<Integer>();

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
     * Resets all Nodes and Edges in graph to uncovered.
     */
    public void resetGraph()
    {
        for (Node node : nodes.values() )
        {
            if (node.isCovered())
            {
                node.setIsCovered(false);
                numNodesCovered--;    
            }
            
        }

        for (Edge edge : edges)
        {
            if (edge.isCovered() )
            {
                edge.setIsCovered(false);
                numEdgesCovered--;
            }
        }
        return;
    }


    /**
     * Tests the methods of this class.
     */
    public static void tests()
    {

		System.out.println("\n\nTest 1: ...");
        Graph test1 = new Graph();
        tools.GraphLoader.loadGraph(test1, "data/small_test_graph.txt");
        System.out.println(test1.exportGraphString());
        List<Integer> dominatingSet1 = test1.findGreedyDominatingSet();
        System.out.print("Dominating Set: ");
        System.out.println(dominatingSet1);

        System.out.println("\n\nTest 2: ... ");
		Graph test2 = new Graph();
		tools.GraphLoader.loadGraph(test2, "data/dominating_set_test.txt");
		System.out.println(test2.exportGraphString());
		List<Integer> dominatingSet2 = test2.findGreedyDominatingSet();
		System.out.print("Dominating Set: ");
		System.out.println(dominatingSet2);

		System.out.println("\n\nTest 3: ...");
		Graph test3 = new Graph();
		tools.GraphLoader.loadGraph(test3, "data/dominating_set_test_2.txt");
		System.out.println(test3.exportGraphString());
		List<Integer> dominatingSet3 = test3.findGreedyDominatingSet();
		System.out.print("Dominating Set: ");
		System.out.println(dominatingSet3);
    }


	public static void main(String[] args)
	{
		tests();

		// Graph testEnron = new Graph();
		// tools.GraphLoader.loadGraph(testEnron, "data/email-Enron.txt");
		
		// long start = System.currentTimeMillis();
		// LinkedList<Integer> enronDominatingSet = testEnron.findGreedyDominatingSet();
		// long end = System.currentTimeMillis();

		// double seconds = end/1000.0 - start/1000.0;
		// System.out.println("The program found a dominating set in " + seconds + " seconds.");
		// System.out.println("The dominating set found is ...");
		// System.out.println(enronDominatingSet);
	}

}