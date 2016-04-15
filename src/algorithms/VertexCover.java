
package algorithms;

import graph.*;

import java.util.List;
import java.util.ArrayList;

public class VertexCover
{
	public static List<Node> takeTwoApproximation(Graph graph)
	{
		List<Node> takeTwoVertexCover = new ArrayList<Node>();

		int numberOfEdges = graph.getNumEdges();
		int numCoveredEdges = 0;

		while (numCoveredEdges < numberOfEdges)
		{	
			for (Edge edge : graph.getEdges() )
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

		graph.resetGraph();
		return takeTwoVertexCover;
	}


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

	/*
	 *
	 * TODO: NEED TO FIND A BETTER REPRESENTATION. SWITCH TO ADJACENCY MATRIX 
	 * 		 WITH AN ASSIGNMENT ARRAY?
	 *
	 */
	public static List<Node> smartTree(Graph graph)
	{	
		List<Node> vertexCover = new ArrayList<Node>();
		int size = recursiveSmartTree(graph);

		for (Node node : graph.getNodes())
		{
			if (node.isCovered() == 1)
				vertexCover.add(node);
		}

		return vertexCover;
	}

	private static int recursiveSmartTree(Graph graph)
	{
	    // Make sure it is still possible to assign a valid vertex cover
	    // That is, loop through all of the edges in the graph.
	    // If there is one edge where both of its nodes have been assigned to "not in cover"
	    // return largest int
		Node u = null;
		Node v = null;

	    for (Edge edge : graph.getEdges() )
	    {	
	    	Node v1 = edge.getNodev1();
	    	Node v2 = edge.getNodev2();
	    	
	    	if ( v1.isCovered() == 0 && v2.isCovered() == 0 )
	    	{
	    		return Integer.MAX_VALUE;
	    	}
	    	
	    	// Find two unassigned vertices and assign them ...
	    	else if ( v1.isCovered() == -1 && v2.isCovered() == -1 )
	    	{
	    		u = v1;
	    		v = v2;
	    	}
	    }


		// If two vertices cannot be found, return the size of the vertex cover
		if (u == null)
		{
			int size = 0;
			for (Node node : graph.getNodes() )
			{
				if (node.isCovered() == 1)
				{
					size++;
				}
				else if (node.isCovered() == -1)
				{
					for (Edge e : node.getEdges() )
					{
						if (e.isCovered() != 1)
							node.setIsCovered(1);
					}
				}
			}
			return size;
		}

		// recursive paths
		u.setIsCovered(0);
		v.setIsCovered(1);
		int size01 = recursiveSmartTree(graph);
		u.setIsCovered(1);
		v.setIsCovered(0);
		int size10 = recursiveSmartTree(graph);
		u.setIsCovered(1);
		v.setIsCovered(1);
		int size11 = recursiveSmartTree(graph);

		// return smallest cover
		if (size01 <= size10 && size01 <= size11)		{ return size01; }
		else if (size10 <= size01 && size10 <= size11)	{ return size10; }
		else											{ return size11; }

	}



}