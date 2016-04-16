
package algorithms;

import graph.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
		List<Node> vertexCover = recursiveSmartTree(graph);
		// System.out.println(size);
		// for (Node node : graph.getNodes())
		// {
		// 	if (node.isCovered() == 1)
		// 		vertexCover.add(node);
		// }

		return vertexCover;
	}

	private static ArrayList<Node> recursiveSmartTree(Graph graph)
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
	    		return (ArrayList<Node>) graph.getNodes();
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
			ArrayList<Node> vCover = new ArrayList<Node>();
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

		// recursive paths
		u.setIsCovered(0);
		v.setIsCovered(1);
		ArrayList<Node> vc01 = recursiveSmartTree(graph);
		u.setIsCovered(1);
		v.setIsCovered(0);
		ArrayList<Node> vc10 = recursiveSmartTree(graph);
		u.setIsCovered(1);
		v.setIsCovered(1);
		ArrayList<Node> vc11 = recursiveSmartTree(graph);

		// return smallest cover
		if (vc01.size() <= vc10.size() && vc01.size() <= vc11.size() )		{ return vc01; }
		else if (vc10.size() <= vc01.size() && vc10.size() <= vc11.size() )	{ return vc10; }
		else																{ return vc11; }

	}



}