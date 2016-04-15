package algorithms;

import graph.*;

import java.util.List;
import java.util.ArrayList;

public class DominatingSet
{   
	/**
     * Returns a list of the Nodes that are included in the Dominating Set.
     * This implementation uses a Greedy Algorithm to find the dominating set 
     * of the graph. The Dominating Set, D, is a subset of nodes such that every
     * not in D is adjacent to at least one node in D.
     *
     * If the number of nodes in the graph is greater than 32, this implementation
     * a preprocessing algorithm to improve the search time. The preprocessing 
     * algorithm looks for special cases where certain nodes will always be in the
     * the Dominating Set (e.g., Nodes with only outgoing edges will have to be included
     * in the set).
     *
     * Note. This implementation on returns a Dominating Set. It does not return the 
     * minimum (or smallest) dominating set.
     *
     * @param graph The graph object for which a dominating set is to be found.
     * 
     * @return A LinkedList of Integers representing the nodes included in the dominating set.
     */
    public static List<Node> findGreedyDominatingSet(Graph graph)
    {
    	// keep track of number of nodes and number of uncovered nodes
    	int numNodes = graph.getNumNodes();
        int numNodesCovered = 0;

    	// number of nodes visited, e.g., added to dominating set
    	List<Node> visited = new ArrayList<Node>();
    	List<Node> cannotBePartOfSet = new ArrayList<Node>();
        Node maxCoverNode;

    	// make sure all nodes become covered
    	while (numNodesCovered != numNodes )
    	{	
    		int maxCoverNum = 0;
    		maxCoverNode = null;
			// find the node that will cover that greatest amount of other nodes
    		for (Node node : graph.getNodes())
    		{	
    			if (!visited.contains(node) && !cannotBePartOfSet.contains(node))
    			{
	    			int uncoveredNeighbors = getNumberUncoveredNeighbors(node);
					if (uncoveredNeighbors > maxCoverNum)
					{
						maxCoverNum = uncoveredNeighbors;
						maxCoverNode = node;
					}
					
					// if key cannot add any new nodes to coverage, remove from future searches
					if (uncoveredNeighbors == 0) {	cannotBePartOfSet.add(node); }
				}
    		}

    		// after finding max cover node, cover it an all of its neighbors
    		numNodesCovered += updateCover(maxCoverNode);

    		// add newMax to list of visited nodes
    		visited.add(maxCoverNode);
    	
    	}
    	
    	return visited;
    }


    /**
     * Returns the number of neighbors of a given node that are uncovered.
     * @param name An integer representing the name of a node
     * @return An integer representing the number of uncovered neighbors
     */
    private static int getNumberUncoveredNeighbors(Node candidate)
    {
		int numUncovered = 0;

		if (!candidate.isCovered()) { numUncovered++; }

		for (Node neighbor : candidate.getNeighbors() )
			if (!neighbor.isCovered() ) {	numUncovered++; }

		return numUncovered;
    }


    /**
     *
     */
    private static int updateCover(Node node)
    {   
        int numUpdated = 0;

    	if (!node.isCovered() )  
    	{
    		node.setIsCovered(true);
    		numUpdated++;
    	}
        
        for (Node neighbor : node.getNeighbors() )
        {
            if (!neighbor.isCovered() ) 
            {
                neighbor.setIsCovered(true);
                numUpdated++;
            }
        }
        return numUpdated;
    }
}