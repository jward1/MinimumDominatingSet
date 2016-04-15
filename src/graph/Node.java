/**
 * @author jward1
 * 
 * Node class to control graph node (vertex) behavior
 *
 */
package graph;


import java.util.HashSet;

public class Node
{
	private int name;
	private HashSet<Edge> nodesEdges;
	private int isCovered;

	public Node(int name)
	{
		this.name = name;
		this.nodesEdges = new HashSet<Edge>();
		this.isCovered = -1;
	}

	public int getName() { return this.name; }

	public HashSet<Node> getNeighbors() 
	{
		HashSet<Node> neighbors = new HashSet<Node>();
		for (Edge edge : nodesEdges) {
			neighbors.add(edge.getOtherNode(this));
		}
		return neighbors;
	}

	public HashSet<Edge> getEdges() { return this.nodesEdges; }
	
	public int isCovered() { return this.isCovered; }

	public void setIsCovered(int newState) 
	{ 
		if (newState == -1 || newState == 0 || newState == 1)
			this.isCovered = newState;
		else
			throw new IllegalArgumentException("isCovered must be set to -1, 0, or 1.");
	}

	public void addEdge(Edge newEdge) 
	{ 
		if ( !nodesEdges.contains(newEdge) )
			nodesEdges.add(newEdge); 
	}

	public String toString() { return Integer.toString(this.name); }

	public int getNumOutgoingEdges() { return this.nodesEdges.size(); }
	
}