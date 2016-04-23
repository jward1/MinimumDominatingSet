/**
 * @author jward1
 * Node class to control graph node (vertex) behavior
 */
package graph;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Node
{
	private int name;
	private HashSet<Edge> nodesEdges;
	private int assignment;

	/**
	 * Creates a Node object for the Graph class.
	 * @param name The Integer name of the node.
	 */
	public Node(int name)
	{
		this.name = name;
		this.nodesEdges = new HashSet<Edge>();
		this.assignment = -1;
	}

	/**
	 * Returns the Integer name of the node.
	 * @return int The name of the node.
	 */
	public int getName() { return this.name; }


	/**
	 * Get the neighbors of a node.
	 * @return a HashSet of Nodes.
	 */
	public List<Node> getNeighbors() 
	{
		List<Node> neighbors = new ArrayList<Node>();
		for (Edge edge : nodesEdges) {
			neighbors.add(edge.getOtherNode(this));
		}
		return neighbors;
	}


	/**
	 * Get the number of a neighbors of a node.
	 * @return int the number of a node's neighbors
	 */
	public int numNeighbors() { return nodesEdges.size(); }


	/**
	 * Returns the Edges attached to the node.
	 * @return A HashSet of Edge objects connected to the Node.
	 */
	public HashSet<Edge> getEdges() { return this.nodesEdges; }
	

	/**
	 * Returns whether the node is assigned to the Dominating Set
	 * or Vertex Cover. { -1: Not assigned, 0: not in set, 1: assigned
	 * to set}
	 * @return int of the nodes assignement
	 */
	public int isAssigned() { return this.assignment; }


	/**
	 * Updates a node's assignment
	 * @param newAssignement The new assignement for a node. Takes -1, 0, or 1.
	 * @throws IllegalArgumentException
	 */
	public void setAssignment(int newAssignment) 
	{ 
		if (newAssignment == -1 || newAssignment == 0 || newAssignment == 1)
			this.assignment = newAssignment;
		else
			throw new IllegalArgumentException("assignment must be set to -1, 0, or 1.");
	}


	/**
	 * Adds an edge to the Node.
	 * @param newEdge the new Edge to be added to a Node.
	 */
	public void addEdge(Edge newEdge) 
	{ 
		if ( !nodesEdges.contains(newEdge) )
			nodesEdges.add(newEdge); 
	}


	/**
	 * Returns the name of the Node as a String.
	 * @return String of the Node's name.
	 */
	public String toString() { return Integer.toString(this.name); }
	
}