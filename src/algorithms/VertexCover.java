
package algorithms;

import graph.*;
import tools.GraphLoader;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class VertexCover
{	
	/**
	 * An approximation algorithm for Vertex Cover.
	 * 
	 * The appox. algorithm cycles through all of the edges of a graph.
	 * If the edge is uncovered, the algorithm addes the edge's two vertices
	 * to the vertex cover and marks the edge and all other edges of the two 
	 * vertices as covered. It continues until all edges are covered.
	 * 
	 * The Take Two Approximation algorithm will return a vertex cover of size
	 * between k and 2*k where k is the (unknown) minimum vertex cover. The 
	 * algorithm runs in O(2*|E|).
	 *
	 * @param graph The input graph for which a vertex cover should be found.
	 * @return A List of Nodes that form a vertex cover for the input graph.
	 */
	public static List<Node> takeTwoApproximation(Graph graph)
	{
		List<Node> takeTwoVertexCover = new ArrayList<Node>();

		int numberOfEdges = graph.getNumEdges();
		int numCoveredEdges = 0;
	
		for (Edge edge : graph.getEdges() )
		{	
			// terminate search if all edges are covered
			if (numCoveredEdges < numberOfEdges)
			{
				if ( edge.isCovered() != 1 )
				{	
					// mark edges of nodes as covered
					Node v1 = edge.getNodev1();
					Node v2 = edge.getNodev2();
					numCoveredEdges += coverNodesEdges( v1 );
					numCoveredEdges += coverNodesEdges( v2 );

					// add nodes to vertex cover
					takeTwoVertexCover.add(v1);
					takeTwoVertexCover.add(v2);
				}
			}
		}

		// reset graph --> O(|V| + |E|)
		graph.resetGraph();
		return takeTwoVertexCover;
	}

	/**
	 * Marks all uncovered edges of a given node as 'covered'.
	 * Runs in O( |N(node)| ) where N(x) is the neighborhood of node x.
	 * @param node The node whose edges need to be marked as covered.
	 * @return An integer of the number of edges that were marked as covered.
	 */
	private static int coverNodesEdges(Node node)
	{
		int numEdgesUpdated = 0;
		for (Edge edge : node.getEdges() )
		{
			if (edge.isCovered() != 1)
			{
				edge.setIsCovered(1);
				numEdgesUpdated++;
			}
		}
		return numEdgesUpdated;
	}

	/**
	 * 
	 * @param graph The input graph for which a minimum vertex cover should be found.
	 * @return A List of Nodes that form a minimum vertex cover for the input graph.
	 */
	public static List<Node> smartTree(Graph graph)
	{	
		List<Node> vertexCover = recursiveSmartTree(graph);

		return vertexCover;
	}

	private static List<Node> recursiveSmartTree(Graph graph)
	{
	    // Make sure it is still possible to assign a valid vertex cover
	    // That is, loop through all of the edges in the graph.
	    // If there is one edge where both of its nodes have been assigned to "not in cover"
	    // return largest size graph
		Node u = null;
		Node v = null;

	    for (Edge edge : graph.getEdges() )
	    {	
	    	Node v1 = edge.getNodev1();
	    	Node v2 = edge.getNodev2();
	    	
	    	if ( v1.isCovered() == 0 && v2.isCovered() == 0 )
	    	{
	    		return (List<Node>) graph.getNodes();
	    	}
	    	
	    	// Find two unassigned vertices and assign them ...
	    	else if ( v1.isCovered() == -1 && v2.isCovered() == -1 )
	    	{
	    		u = v1;
	    		v = v2;
	    	}
	    }


		// If two vertices cannot be found, complete and return the vertex cover
		if (u == null)
		{
			return constructVertexCover(graph);
		}

		// recursive paths
		u.setIsCovered(0);
		v.setIsCovered(1);
		List<Node> vc01 = recursiveSmartTree(graph);
		u.setIsCovered(1);
		v.setIsCovered(0);
		List<Node> vc10 = recursiveSmartTree(graph);
		u.setIsCovered(1);
		v.setIsCovered(1);
		List<Node> vc11 = recursiveSmartTree(graph);
		u.setIsCovered(-1);
		v.setIsCovered(-1);

		// return smallest cover
		if      (vc01.size() <= vc10.size() && vc01.size() <= vc11.size() )	{ return vc01; }
		else if (vc10.size() <= vc01.size() && vc10.size() <= vc11.size() )	{ return vc10; }
		else																{ return vc11; }

	}

	public static List<Node> recursiveTree(Graph graph)
	{
	    // Make sure it is still possible to assign a valid vertex cover
	    // That is, loop through all of the edges in the graph.
	    // If there is one edge where both of its nodes have been assigned to "not in cover"
	    // return largest size graph
		Node u = null;

	    for (Node node: graph.getNodes() )
	    {
	    	if (node.isCovered() == -1)
	    		u = node;

	    	for (Edge edge : node.getEdges())
	    	{
	    		Node v2 = edge.getOtherNode(node);
	    		if (v2.isCovered() == 0 && node.isCovered() == 0)
	    			return (List<Node>) graph.getNodes();
	    	}
	    }

		// If all vertices are assigned and cover is valid
		if (u == null)
		{
			List<Node> vCover = new ArrayList<Node>();
			for (Node node : graph.getNodes() )
				if (node.isCovered() == 1)
					vCover.add(node);
			return vCover;
		}

		// recursive paths
		u.setIsCovered(0);
		List<Node> u0 = recursiveSmartTree(graph);
		u.setIsCovered(1);
		List<Node> u1 = recursiveSmartTree(graph);
		u.setIsCovered(-1);

		// return smallest cover
		if (u0.size() < u1.size() )	{ return u0; }
		else 						{ return u1; }

	}




	/**
	 *
	 */
	private static List<Node> constructVertexCover(Graph graph)
	{
		List<Node> vCover = new ArrayList<Node>();
		for (Node node : graph.getNodes() )
		{
			if (node.isCovered() == 1)
			{
				vCover.add(node);
			}
			else if (node.isCovered() == -1)
			{
				for (Edge e : node.getEdges() )
				{
					if (e.isCovered() != 1 && (e.getOtherNode(node)).isCovered() == 0 )
					{
						node.setIsCovered(1);
						vCover.add(node);
						break;
					}
				}
			}
		}
		return vCover;
	}


	public static void main(String[] args)
	{
		Graph g = new Graph();
		GraphLoader.loadGraph(g, "data/facebook_1000.txt");
		
		long start = System.currentTimeMillis();
		List<Node> vtxCvr = smartTree(g);
		long end = System.currentTimeMillis();
		double runtime = end/1000.0 - start/1000.0;
		
		System.out.println("Found vertex cover of size " + vtxCvr.size() + " in " + runtime + " seconds.");
		System.out.println("The vertex cover is ...");
		System.out.println(vtxCvr);
	}



}