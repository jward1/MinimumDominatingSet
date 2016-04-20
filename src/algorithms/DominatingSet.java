package algorithms;

import graph.*;
import tools.GraphLoader;

import java.util.List;
import java.util.ArrayList;

public class DominatingSet
{   
	/**
     * Returns a minimum dominating set of a graph.
     * Implements the aglorithm discussed in "Exact (exponential) algorithms for the 
     * dominating set problem", by Fomin, Kratsch, and Woeginger.
     * 
     * This algorithm runs in O(1.93782^n) where n is the number of nodes (or vertices)
     * in the graph.
     * 
     * @param graph The graph object for which a minimum dominating set is to be found.
     * @return A List of Nodes representing the nodes included in the dominating set.
     */
    public static List<Node> findMinimumDominatingSet(Graph graph)
    {   
        List<Node> mds = recursiveSearchTree(graph);
        return mds;
    }


    /**
     * This method implements the core of the algorithm developed by Fomin, et. al. 
     * 
     * The recursive search tree works finding all nodes with degree 1 and 2 (that is,
     * nodes with only 1 or 2 neighbors). 
     *      Case 1: If node v is of degree 1, then this algorithm marks its sole 
     *      neighbor w as in the dominating set and all of w's neighbors as out of the
     *      dominating set (0).
     *      Case 2: If the node v is of degree 2, then this algorithm splits into three
     *      subcases. Let the two neighbors be denoted as u1 and u2
     *          Case 2a: Mark u1 as in D, and v and u2 as not in D. Recurse on resulting graph.
     *          Case 2b: Mark v as in D, and u1 and u2 as not in D. Recurse on resulting graph.
     *          Case 2c: Mark u2 as in D, and u1 and v as not in D. Recurse on resulting graph.
     *      In each subcase, when a node is marked as in D, all of its neighbors are marked as not
     *      being in D.
     * After the search tree has dealt with all nodes of degree one and two, it calls the brute force
     * search to find the minimum dominating set on the "reduced" graph.
     * 
     * @param graph The graph object for which a minimum dominating set is to be found.
     * @return A List of Nodes representing the nodes included in the dominating set.
     */
    private static List<Node> recursiveSearchTree(Graph graph)
    {   
        Node w = null;
        Node u1 = null;
        Node u2 = null;
        Node v = null;

        List<Node> w1 = (List<Node>) graph.getNodes();
        List<Node> u11 = (List<Node>) graph.getNodes();
        List<Node> u21 = (List<Node>) graph.getNodes();
        List<Node> v1 = (List<Node>) graph.getNodes();

        for (Node n : graph.getNodes() )
        {
            int num = getNumUncoveredNeighborsSearchTree(n);
            if (num == 1 && n.isCovered() == -1)
            {
                w = (new ArrayList<Node>(n.getNeighbors())).get(0);
                updateGraph(graph, w);
            }
            else if (num == 2 && n.isCovered() == -1)
            {
                ArrayList<Node> uv = new ArrayList<Node>(n.getNeighbors());
                u1 = uv.get(0);
                u2 = uv.get(1);
                v  = n;
            }
        }

        if (w==null && u1==null && u2==null && v==null)
            return bruteForce(graph);

        if (w != null)
            w1 = recursiveSearchTree(graph);

        if (u1 != null)
        {
            updateGraph(graph, u1);
            u2.setIsCovered(0);
            u11 = recursiveSearchTree(graph);

            updateGraph(graph, v);
            v1 = recursiveSearchTree(graph);

            updateGraph(graph, u2);
            u1.setIsCovered(0);
            u21 = recursiveSearchTree(graph);
        }

        int minNum = Math.min( w1.size(), Math.min(u11.size(), Math.min(u21.size(), v1.size())));
        //int minNum = Math.min( u11.size(), Math.min(u21.size(), v1.size()));

        if (minNum == w1.size())  { return w1; }
        else if (minNum == u11.size()) { return u11; }
        else if (minNum == u21.size()) { return u21; }
        else                           { return v1; }
    }


    /**
     *
     */
    private static List<Node> bruteForce(Graph graph)
    {   
        
        Node u = null; 

        // check if dominating set is possible, if not return full graph as dominating set
        for (Node node : graph.getNodes() )
        {
            if (node.isCovered() == 0)
            {
                boolean allNeighborsAreSetToUncovered = true;
                
                for (Node neighbor : node.getNeighbors() )

                    if (neighbor.isCovered() != 0)
                    {
                        allNeighborsAreSetToUncovered = false;
                        break;
                    }

                if (allNeighborsAreSetToUncovered)
                    return (List<Node>) graph.getNodes();
            }
            else if (node.isCovered() == -1)
            {
                u = node;
            }
        }

        // if all nodes have been assigned and set is valid, return dominating set
        if (u == null)
        {
            List<Node> mds = new ArrayList<Node>();
            for (Node node : graph.getNodes())
            {
                if (node.isCovered() == 1)
                    mds.add(node);
            }
            return mds;
        }

        // recursively iterate through all assignments
        u.setIsCovered(0);
        List<Node> u0 = bruteForce(graph);
        u.setIsCovered(1);
        List<Node> u1 = bruteForce(graph);

        // return the smallest set
        if (u0.size() <= u1.size() ) { return u0; }
        else                         { return u1; }
    }

    /**
     *
     */
    private static void updateGraph(Graph graph, Node node)
    {
        for (Node neighbor : node.getNeighbors() )
            neighbor.setIsCovered(0);
        node.setIsCovered(1);
    }


    /** 
     *
     */
    private static int getNumUncoveredNeighborsSearchTree(Node node)
    {
        int num = 0;
        for (Node neighbor : node.getNeighbors())
            if (neighbor.isCovered() != 1)
                num++;
        return num;
    }









    /**
     * Returns a list of the Nodes that are included in the Dominating Set.
     * This implementation uses a Greedy Algorithm to find the dominating set 
     * of the graph. The Dominating Set, D, is a subset of nodes such that every
     * not in D is adjacent to at least one node in D.
     *
     * Note. This implementation on returns a Dominating Set. It does not return the 
     * minimum (or smallest) dominating set.
     *
     * @param graph The graph object for which a dominating set is to be found.
     * 
     * @return A List of Nodes representing the nodes included in the dominating set.
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

		if (candidate.isCovered() != 1) { numUncovered++; }

		for (Node neighbor : candidate.getNeighbors() )
			if ( neighbor.isCovered() != 1 ) {	numUncovered++; }

		return numUncovered;
    }


    /**
     *
     */
    private static int updateCover(Node node)
    {   
        int numUpdated = 0;

    	if ( node.isCovered() != 1 )  
    	{
    		node.setIsCovered(1);
    		numUpdated++;
    	}
        
        for (Node neighbor : node.getNeighbors() )
        {
            if ( neighbor.isCovered() != 1 ) 
            {
                neighbor.setIsCovered(1);
                numUpdated++;
            }
        }
        return numUpdated;
    }


    public static void main(String[] args)
    {
        Graph g = new Graph();
        GraphLoader.loadGraph(g, "data/dominating_set_test.txt");
        System.out.println(g.exportGraphString());
        System.out.println(findMinimumDominatingSet(g));

        Graph k = new Graph();
        GraphLoader.loadGraph(k, "data/dominating_set_test_2.txt");
        System.out.println(k.exportGraphString());
        System.out.println(findMinimumDominatingSet(k));

        Graph t = new Graph();
        GraphLoader.loadGraph(t, "data/small_test_graph.txt");
        System.out.println(t.exportGraphString());
        System.out.println(findMinimumDominatingSet(t));
    }

}