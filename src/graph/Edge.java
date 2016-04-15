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
	private boolean isCovered; 

	public Edge (Node node1, Node node2)
	{	
		// make sure lower value name is always stored in v1
		Node val1;
		Node val2;

		if ( node1.getName() < node2.getName() ) 	  { val1 = node1; val2 = node2; }
		else if (node1.getName() > node2.getName() ) { val2 = node1; val1 = node2; }
		else { throw new IllegalArgumentException("Self loops are not allowed in this Graph."); }

		this.v1 = val1;
		this.v2 = val2;
		this.isCovered = false;
	}

	// Use for direct graphs
	//public Node getEndNode() { return this.end; }

	// Use for directed graphs
	//public Node getStartNode() { return this.start ; }

	public String toString() 
	{ 	
		return v1.getName() + "---" + v2.getName();
	}
	
	/**
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


	public Node getNodev1() { return this.v1; }
	public Node getNodev2() { return this.v2; }

	public boolean isEqualTo(Edge edge)
	{
		if ( this.v1.equals(edge.getNodev1()) && this.v2.equals(edge.getNodev2()) )
			return true;
		return false;
	}

	public boolean isCovered() { return this.isCovered; }

	public void setIsCovered(boolean newState) { this.isCovered = newState; }

}