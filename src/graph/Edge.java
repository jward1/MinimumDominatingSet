/**
 * @author jward1
 * 
 * Edge class to control graph edges
 *
 */

package graph;

public class Edge
{
	private Node v1;
	private Node v2;
	private int isCovered; 

	/**
	 * Creates an Edge object between two nodes.
	 * @param node1 One end of the Edge.
	 * @param node2 The other end of the Edge.
	 */
	public Edge (Node node1, Node node2)
	{	
		// make sure lower value name is always stored in v1
		Node val1;
		Node val2;

		if      ( node1.getName() < node2.getName() )  { val1 = node1; val2 = node2; }
		else if ( node1.getName() > node2.getName() )  { val2 = node1; val1 = node2; }
		else { throw new IllegalArgumentException("Self loops are not allowed in this Graph."); }

		this.v1 = val1;
		this.v2 = val2;
		this.isCovered = -1;
	}


	/**
	 * Converts the edge object to a String
	 * @return A String representation of the Edge object.
	 */
	public String toString() 
	{ 	
		return v1.getName() + "---" + v2.getName();
	}
	

	/**
	 * Gets the node at the other end of the Edge.
	 * @param node One node contained in the edge
	 * @return The node at the other end of the edge
	 * @throws IllegalArgumentExepction if the node passed to the method
	 * 		   is not contained in the Edge.
	 */
	public Node getOtherNode(Node node)
	{
		if (node.equals(v1)) 
			return v2;
		else if (node.equals(v2))
			return v1;
		throw new IllegalArgumentException("Looking for " +
			"a point that is not in the edge");
	}


	/**
	 * Returns the v1 node.
	 * @return the v1 node of an edge.
	 */
	public Node getNodev1() { return this.v1; }


	/**
	 * Returns the v2 node.
	 * @return the v2 node of an edge.
	 */
	public Node getNodev2() { return this.v2; }


	/**
	 * Tests whether an Edge is equal to an Edge already in the graph.
	 * @param The other edge to be tested if equal to this Edge object.
	 * @return True if the Edge objects represent the same edge; otherwise False
	 */
	public boolean isEqualTo(Edge edge)
	{
		if ( this.v1.equals(edge.getNodev1()) && this.v2.equals(edge.getNodev2()) )
			return true;
		return false;
	}


	/**
	 * Returns the state of an Edge's coverage when looking for a
	 * Vertex Cover {-1: not assigned, 0: not covered, 1:covered}
	 * @return An integer representing an Edge's coverage.
	 */
	public int isCovered() { return this.isCovered; }


	/**
	 * Updates a node's coverage state
	 * @param newState The new state for an edge. Takes -1, 0, or 1.
	 * @throws IllegalArgumentException
	 */
	public void setIsCovered(int newState) 
	{ 
		if (newState == -1 || newState == 0 || newState == 1)
			this.isCovered = newState;
		else
			throw new IllegalArgumentException("isCovered must be set to -1, 0, or 1.");
	}

}