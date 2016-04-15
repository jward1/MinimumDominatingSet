
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
				if (!edge.isCovered() )
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
			if (!edge.isCovered())
			{
				edge.setIsCovered(true);
				numEdgesUpdated++;
			}
		}
		return numEdgesUpdated;
	}



}