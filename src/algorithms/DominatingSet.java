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
        //List<Node> mds = bruteForce(graph);
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
     *          Case 2a: Mark u1 as in D, and v as not in D. Recurse on resulting graph.
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
        // initialize node variables
        Node w1 = null;
        Node u1 = null;
        Node u2 = null;
        Node v  = null;

        // initialize results to all nodes in graph
        List<Node> wResult  = null;
        List<Node> u1Result = null;
        List<Node> u2Result = null;
        List<Node> vResult  = null;

        // set the size of the result sets to the max integer value
        int wSize  = Integer.MAX_VALUE;
        int u1Size = Integer.MAX_VALUE;
        int u2Size = Integer.MAX_VALUE;
        int vSize  = Integer.MAX_VALUE;

        // find nodes with either 1 or 2 uncovered neighbors
        for (Node n : graph.getNodes() )
        {
            if (n.isAssigned() == -1)
            {
                List<Node> unassigned = getNumUnassignedNeighbors(n);
                if (unassigned.size() == 1)
                {   
                    w1 = unassigned.get(0);
                    v = n;
                }
                else if (unassigned.size() == 2)
                {
                    u1 = unassigned.get(0);
                    u2 = unassigned.get(1);
                    v = n;
                }
            }
        }

        // if all are null, then graph only contains nodes with 
        // zero or three or more neighbors
        if (w1==null && u1==null && u2==null && v==null)
        {
            return bruteForce(graph);
        }
            

        //recursive branching

        // case 1
        if (w1 != null)
        {
            w1.setAssignment(1);
            v.setAssignment(0);
            wResult = recursiveSearchTree(graph);
            wSize = wResult.size();
            w1.setAssignment(-1);
            v.setAssignment(-1);
        }

        // case 2
        if (u1 != null)
        {   
            // case 2a
            u1.setAssignment(1);
            u2.setAssignment(-1);
            v.setAssignment(0);
            u1Result = recursiveSearchTree(graph);
            u1Size = u1Result.size();

            // case 2b
            u1.setAssignment(0);
            u2.setAssignment(0);
            v.setAssignment(1);
            vResult = recursiveSearchTree(graph);
            vSize = vResult.size();

            // case 2c
            u1.setAssignment(0);
            u2.setAssignment(1);
            v.setAssignment(0);
            u2Result = recursiveSearchTree(graph);
            u2Size = u2Result.size();

            // reset assignments
            u1.setAssignment(-1);
            u2.setAssignment(-1);
            v.setAssignment(-1);
        }

        // return smallest result
        int minNum = Math.min( wSize, Math.min(u1Size, Math.min(u2Size, vSize)));
        if      (minNum == wSize ) { return wResult;  }
        else if (minNum == u1Size) { return u1Result; }
        else if (minNum == u2Size) { return u2Result; }
        else                       { return vResult;  }
    }


    /**
     * Finds the minimum dominant set of a graph.
     *
     * This uses brute force, that is, it tries every possible combination of assignments,
     * to find a minimum dominant set of a graph. Given that there are 2 possible states for
     * each of the n nodes, this method runs in O(2^n).
     *
     * @param graph The graph object for which a minimum dominating set is to be found.
     * @return A List of Nodes representing the nodes included in the dominating set.
     */
    private static List<Node> bruteForce(Graph graph)
    {   
        
        Node u = null; 

        // check if dominating set is possible, if not return full graph as dominating set
        for (Node node : graph.getNodes() )
        {   
            if (node.isAssigned() == 0)
            {
                boolean allNeighborsSetToZero = true;
                
                for (Node neighbor : node.getNeighbors() ) 
                {   
                    if (neighbor.isAssigned() != 0)
                        allNeighborsSetToZero = false;
                }

                
                if (allNeighborsSetToZero)
                {
                    return (ArrayList<Node>) graph.getNodes();
                }  
            }
        }
        
        // if graph is possible, find unassigned node
        for (Node node : graph.getNodes() )
        {
            if (node.isAssigned() == -1)
                u = node;
        }

        // if all nodes have been assigned and set is valid, return dominating set
        if (u == null)
        {   
            List<Node> ds = new ArrayList<Node>();
            for (Node node : graph.getNodes())
            {
                if (node.isAssigned() == 1)
                    ds.add(node);
            }
            return ds;
        }

        // recursively iterate through all assignments of u
        u.setAssignment(0);
        List<Node> u0 = bruteForce(graph);
        u.setAssignment(1);
        List<Node> u1 = bruteForce(graph);
        u.setAssignment(-1);

        //return the smallest set
        if ( u0.size() < u1.size() )   { return u0; }
        else                           { return u1; }
    }


    /** 
     * Returns a list of unassigned neighbors of a given node.
     *
     * @param node The node for which to get the neighbors that have
     *                  have not been assigned to in or out of the dominating set.
     * @return A List of the unassigned neighbor nodes
     */
    private static List<Node> getNumUnassignedNeighbors(Node node)
    {
        List<Node> unAssigned = new ArrayList<Node>();
        for (Node neighbor : node.getNeighbors())
            if (neighbor.isAssigned() == -1)
                unAssigned.add(neighbor);
        return unAssigned;
    }


    /**
     * Returns a list of the Nodes that are included in the Dominating Set.
     * This implementation uses a Greedy Algorithm to find the dominating set 
     * of the graph. The Dominating Set, D, is a subset of nodes such that every
     * not in D is adjacent to at least one node in D.
     *
     * Note. This implementation on returns a Dominating Set. It is not guaranteed to
     * return the minimum dominating set.
     *
     * @param graph The graph object for which a dominating set is to be found.
     * 
     * @return A List of Nodes representing the nodes included in the dominating set.
     */
    public static List<Node> findGreedyDominatingSet(Graph graph)
    {
    	// keep track of number of nodes and number of uncovered nodes
    	boolean allNodesAreCovered = false;
        Node maxCoverNode;

        // keep track of nodes that have been visited
        List<Node> visited = new ArrayList<Node>();

    	// make sure all nodes become covered
    	while (!allNodesAreCovered)
    	{	
            allNodesAreCovered = false;

            // find the node that will cover the greatest number of uncovered nodes
    		int maxCoverNum = 0;
    		maxCoverNode = null;
			
    		for (Node node : graph.getNodes())
    		{	
    			if (node.isAssigned() != 1 )
    			{
	    			int uncoveredNeighbors = getNumberUncoveredNeighbors(node);
					if (uncoveredNeighbors > maxCoverNum)
					{
						maxCoverNum = uncoveredNeighbors;
						maxCoverNode = node;
					}
				}
    		}

    		// after finding max cover node, cover it an all of its neighbors
    		updateCover(maxCoverNode);

    		// add newMax to list of visited nodes
    		visited.add(maxCoverNode);

            // check that all nodes are covered
            allNodesAreCovered = true;
            for (Node node : graph.getNodes())
            {
                if (node.isAssigned() == -1)
                {
                    allNodesAreCovered = false;
                    break;
                }
            }
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

		if (candidate.isAssigned() != 1) { numUncovered++; }

		for (Node neighbor : candidate.getNeighbors() )
			if ( neighbor.isAssigned() == -1 ) {	numUncovered++; }

		return numUncovered;
    }


    /**
     * Updates the neighborhood of a node that has been assigned to 
     * the dominating set by marking all of its neighbors as out of the
     * dominating set.
     *
     * @param node The Node whose neighborhood needs to be updated.
     */
    private static void updateCover(Node node)
    {   
    	node.setAssignment(1);
        
        for (Node neighbor : node.getNeighbors() )
            if ( neighbor.isAssigned() == -1 ) 
                neighbor.setAssignment(0);
    }


    public static void main(String[] args)
    {   
        Graph aaa = new Graph();
        GraphLoader.loadGraph(aaa, "data/dominating_set_test.txt");
        System.out.println(aaa.exportGraphString());
        System.out.println(findMinimumDominatingSet(aaa));
        System.out.println(findGreedyDominatingSet(aaa));

        Graph bbb = new Graph();
        GraphLoader.loadGraph(bbb, "data/dominating_set_test_2.txt");
        System.out.println(bbb.exportGraphString());
        System.out.println(findMinimumDominatingSet(bbb));
        System.out.println(findGreedyDominatingSet(bbb));

        Graph ccc = new Graph();
        GraphLoader.loadGraph(ccc, "data/super_small_test.txt");
        System.out.println(ccc.exportGraphString());
        System.out.println(findMinimumDominatingSet(ccc));
        System.out.println(findGreedyDominatingSet(ccc));

        Graph ddd = new Graph();
        GraphLoader.loadGraph(ddd, "data/greedy_ds_test.txt");
        System.out.println(ddd.exportGraphString());
        System.out.println(findMinimumDominatingSet(ddd));
        System.out.println(findGreedyDominatingSet(ddd));

        Graph eee = new Graph();
        GraphLoader.loadGraph(eee, "data/small_test_graph.txt");
        System.out.println(eee.exportGraphString());
        System.out.println("Minimum DS: " + findMinimumDominatingSet(eee));
        System.out.println("Greedy DS: " + findGreedyDominatingSet(eee));

        Graph fb = new Graph();
        GraphLoader.loadGraph(fb, "data/facebook_2000.txt");
        System.out.println("Number of Nodes: " + fb.getNumNodes());
        
        System.out.println("\nGreedy Dominating Set ...");
        List<Node> gds = findGreedyDominatingSet(fb);
        System.out.println("Size of gds: " + gds.size() );
        System.out.println("Greedy dominating set: " + gds);

        System.out.println("\nMinimum Dominating Set ...");
        List<Node> mds = findMinimumDominatingSet(fb);
        System.out.println("Size of mds with tree: " + mds.size() );
        System.out.println("Minimum Dominating Set: " + mds);
    }

}