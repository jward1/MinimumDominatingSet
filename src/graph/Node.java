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
	private HashSet<Edge> edgesFrom;
	private HashSet<Node> edgesTo; // necessary metadata for Dominating Set Preprocessing
	private boolean isCovered;

	public Node(int name)
	{
		this.name = name;
		this.edgesFrom = new HashSet<Edge>();
		this.edgesTo = new HashSet<Node>();
		this.isCovered = false;
	}

	public int getName() { return this.name; }

	public HashSet<Node> getNeighbors() 
	{
		HashSet<Node> neighbors = new HashSet<Node>();
		for (Edge edge : edgesFrom) {
			neighbors.add(edge.getEndNode());
		}
		return neighbors;
	}

	public HashSet<Edge> getEdges() { return this.edgesFrom; }
	
	public boolean isCovered() { return this.isCovered; }

	public void setIsCovered(boolean newState) { this.isCovered = newState; }

	public void addEdge(Edge newEdge) { (this.edgesFrom).add(newEdge); }

	public void addIncomingEdge(Node newNode) { (this.edgesTo).add(newNode); }

	public HashSet<Node> getIncomingEdges() { return this.edgesTo; }

	public String toString() { return Integer.toString(this.name); }

	public int getNumIncomingEdges() { return this.edgesTo.size(); }

	public int getNumOutgoingEdges() { return this.edgesFrom.size(); }
	
}