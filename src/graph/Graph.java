/**
 * Graph class. Controls the graph structure and algorithms
 * @author jward1
 */
package graph;

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

	/** 
	 * Creates a new Graph object.
	 */
	public Graph()
	{
		nodes = new HashMap<Integer, Node>();
		edges = new HashSet<Edge>();
	}


	/** 
	 * Adds a node to the graph. Runs in O(1)
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
	 * Removes a node and all of its Edges from a Graph.
	 * @param nodeToRemove The Integer name of the node to be removed.
	 */
	public void removeNode(int nodeToRemove)
	{
		Node node = nodes.get(nodeToRemove);
		removeNodesEdges(node);
		nodes.put(nodeToRemove, null);
	}

	
	/**
	 * Removes a node and all of its Edges from a Graph.
	 * @param nodeToRemove The Node object to be removed.
	 */
	public void removeNode(Node nodeToRemove)
	{
		removeNodesEdges(nodeToRemove);
		nodes.put(nodeToRemove.getName(), null);
	}

	
	/**
	 * Removes the Edges from a graph for a given Node.
	 * @nodeToRemove The Integer name of the node to be removed.
	 */
	public void removeNodeEdges(int nodeToRemove)
	{
		Node node = nodes.get(nodeToRemove);
		removeNodesEdges(node);

	}


	/**
	 * Removes the Edges from a graph for a given Node.
	 * @param nodeToRemove The Node object to be removed.
	 */
	public void removeNodesEdges(Node nodeToRemove)
	{
		for (Edge edge : nodeToRemove.getEdges())
			edges.remove(edge);
	}

	
	/** 
	 * Adds an undirected edge to the graph.
	 * @param v1 The name of the Node at one end of the edge
	 * @param v2 The name of the Node at the other end of the edge
	 */
	public void addEdge(int v1, int v2)
	{	
		// ensure that the from and to nodes are in the Graph
		if (!nodes.containsKey(v1))	
            throw new NullPointerException("Node " + v1 + " is not contained in the Graph.");
		if (!nodes.containsKey(v2))
            throw new NullPointerException("Node " + v2 + " is not contained in the Graph.");
		
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
	 * Adds an undirected edge to the graph. Useful for building 
	 * copies of a graph or separate subgraphs.
	 * @param newEdge The edge to be added to the graph.
	 */
	public void addEdge(Edge newEdge)
	{
		edges.add(newEdge);
		Node v1 = newEdge.getNodev1();
		Node v2 = newEdge.getNodev2();
		v1.addEdge(newEdge);
		v2.addEdge(newEdge);
	}


	/** 
	 * Returns the nodes of the Graph.
	 * @return A List of Nodes that contained in the Graph
	 */
	public List<Node> getNodes()
	{	return new ArrayList<Node>( nodes.values() );	}


    /** 
     * Returns the total number of nodes in the Graph.
     * @return An int representing the number of nodes in the Graph
     */
    public int getNumNodes()
    {   return nodes.size(); }


    /**
     *
     */
    public List<Edge> getEdges()
    {	return new ArrayList<Edge>(edges); }


	/** 
	 * Returns the total number of edges in the Graph.
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
     * Resets all Nodes and Edges in graph to uncovered.
     * Runs in O(|V| + |E|).
     */
    public void resetGraph()
    {
        for (Node node : nodes.values() )
            node.setAssignment(-1);

        for (Edge edge : edges)
        	edge.setIsCovered(-1);
        
        return;
    }
}