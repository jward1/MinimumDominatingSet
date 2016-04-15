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
	private boolean isCovered;

	public Node(int name)
	{
		this.name = name;
		this.nodesEdges = new HashSet<Edge>();
		this.isCovered = false;
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
	
	public boolean isCovered() { return this.isCovered; }

	public void setIsCovered(boolean newState) { this.isCovered = newState; }

	public void addEdge(Edge newEdge) 
	{ 
		if ( !nodesEdges.contains(newEdge) )
			nodesEdges.add(newEdge); 
	}

	public String toString() { return Integer.toString(this.name); }

	public int getNumOutgoingEdges() { return this.nodesEdges.size(); }
	
}