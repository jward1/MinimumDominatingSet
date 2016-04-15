/**
 * @author jward1
 * 
 * Edge class to control graph edges
 *
 */

package graph;

public class Edge
{
	private Node start;
	private Node end;

	public Edge (Node start, Node end)
	{
		this.start = start;
		this.end = end;
	}

	public Node getEndNode() { return this.end; }

	public Node getStartNode() { return this.start; }

	public String toString() 
	{ 
		return Integer.toString(this.end.getName());
	}

}