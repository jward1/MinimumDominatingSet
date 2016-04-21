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
        //List<Node> mds = recursiveSearchTree(graph);
        List<Node> mds = bruteForce(graph);
        graph.resetGraph();
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
        Node w1  = null;
        Node u1 = null;
        Node u2 = null;
        Node v  = null;
        Node w2 = null;

        // initialize results to all nodes in graph
        List<Node> wResult  = null;
        List<Node> u1Result = null;
        List<Node> u2Result = null;
        List<Node> vResult  = null;

        int wSize = Integer.MAX_VALUE;
        int u1Size = Integer.MAX_VALUE;
        int u2Size = Integer.MAX_VALUE;
        int vSize = Integer.MAX_VALUE;

        // find nodes with either 1 or 2 uncovered neighbors
        for (Node n : graph.getNodes() )
        {
            //int num = getNumUncoveredNeighborsSearchTree(n);
            int num = n.numNeighbors();
            if (num == 1 && n.isCovered() == -1)
            {
                w1 = (new ArrayList<Node>(n.getNeighbors())).get(0);
                w2 = n;
            }
            else if (num == 2 && n.isCovered() == -1)
            {
                ArrayList<Node> uv = new ArrayList<Node>(n.getNeighbors());
                u1 = uv.get(0);
                u2 = uv.get(1);
                v  = n;
            }
        }

        // if all are null, then graph only contains nodes with 
        // zero or three or more neighbors
        if (w1==null && u1==null && u2==null && v==null)
            return bruteForce(graph);

        // recursive branching
        if (w1 != null)
        {
            updateGraph(graph, w1);
            wResult = recursiveSearchTree(graph);
            wSize = wResult.size();
            resetNeighborhood(graph, w1);
        }

        if (u1 != null)
        {
            updateGraph(graph, u1);
            u1.setIsCovered(1);
            u2.setIsCovered(0);
            v.setIsCovered(0);
            u1Result = recursiveSearchTree(graph);
            u1Size = u1Result.size();
            resetNeighborhood(graph, u1);

            updateGraph(graph,  v);
            u1.setIsCovered(0);
            u2.setIsCovered(0);
            v.setIsCovered(1);
            vResult = recursiveSearchTree(graph);
            vSize = vResult.size();
            resetNeighborhood(graph, v);

            updateGraph(graph, u2);
            u1.setIsCovered(0);
            u2.setIsCovered(1);
            v.setIsCovered(0);
            u2Result = recursiveSearchTree(graph);
            u2Size = u2Result.size();
            resetNeighborhood(graph, u2);

            u1.setIsCovered(-1);
            u2.setIsCovered(-1);
            v.setIsCovered(-1);
        }

        // return smallest result
        //int minNum = Math.min( wResult.size(), Math.min(u1Result.size(), Math.min(u2Result.size(), vResult.size())));
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
            if (node.isCovered() == 0)
            {
                boolean allNeighborsSetToZero = true;
                for (Node neighbor : node.getNeighbors() ) 
                {
                    if (neighbor.isCovered() != 0)
                        allNeighborsSetToZero = false;
                }
                
                if (allNeighborsSetToZero)
                {
                    return (ArrayList<Node>) graph.getNodes();
                }  
            }
        }

        for (Node node : graph.getNodes() )
        {
            if (node.isCovered() == -1) {
                u = node;
            }
        }

        // if all nodes have been assigned and set is valid, return dominating set
        if (u == null)
        {   
            List<Node> ds = new ArrayList<Node>();
            for (Node node : graph.getNodes())
            {
                if (node.isCovered() == 1)
                    ds.add(node);
            }
            return ds;
        }

        // recursively iterate through all assignments of u
        u.setIsCovered(1);
        List<Node> u0 = bruteForce(graph);
        u.setIsCovered(0);
        List<Node> u1 = bruteForce(graph);
        u.setIsCovered(-1);

        //return the smallest set
        if ( u0.size() < u1.size() )   { return u0; }
        else                           { return u1; }
    }


    /**
     * Updates the graph by marking the node as in the Dominating Set and
     * all of its neighbors as out of the dominating set.
     *
     * @param graph The graph object that is being updated.
     * @param node The node to mark as covered and its neighbors as uncovered.
     */
    private static void updateGraph(Graph graph, Node node)
    {
        for (Node neighbor : node.getNeighbors() )
        {
            if (neighbor.isCovered() == -1)
                neighbor.setIsCovered(0);
        }
        node.setIsCovered(1);
    }

    /**
     *
     */
    private static void resetNeighborhood(Graph graph, Node center)
    {
        for (Node neighbor : center.getNeighbors())
            neighbor.setIsCovered(-1);
        center.setIsCovered(-1);
    }


    /** 
     * Returns the number of uncovered neighbors of a given node.
     *
     * @param node The node for which to count the number of neighbors that are not in the 
     *             dominating set.
     * @return The number of a node's neighbors that are not in the dominating set.
     */
    private static int getNumUncoveredNeighborsSearchTree(Node node)
    {
        int num = 0;
        for (Node neighbor : node.getNeighbors())
            if (neighbor.isCovered() == -1)
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
     * minimum dominating set.
     *
     * @param graph The graph object for which a dominating set is to be found.
     * 
     * @return A List of Nodes representing the nodes included in the dominating set.
     */
    public static List<Node> findGreedyDominatingSet(Graph graph)
    {
    	// keep track of number of nodes and number of uncovered nodes
    	boolean allNodesAreCovered = false;


    	// number of nodes visited, e.g., added to dominating set
    	List<Node> visited = new ArrayList<Node>();
        Node maxCoverNode;


    	// make sure all nodes become covered
    	while (!allNodesAreCovered)
    	{	
            allNodesAreCovered = false;

            // find the node that will cover the greatest number of uncovered nodes
    		int maxCoverNum = 0;
    		maxCoverNode = null;
			
    		for (Node node : graph.getNodes())
    		{	
    			if (node.isCovered() != 1 )
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

            // see if all nodes are covered
            
            allNodesAreCovered = true;
            for (Node node : graph.getNodes())
            {
                if (node.isCovered() == -1)
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

		if (candidate.isCovered() != 1) { numUncovered++; }

		for (Node neighbor : candidate.getNeighbors() )
			if ( neighbor.isCovered() == -1 ) {	numUncovered++; }

		return numUncovered;
    }


    /**
     * 
     */
    private static void updateCover(Node node)
    {   
    	node.setIsCovered(1);
        
        for (Node neighbor : node.getNeighbors() )
            if ( neighbor.isCovered() == -1 ) 
                neighbor.setIsCovered(0);
    }


    public static void main(String[] args)
    {
        // Graph zzz = new Graph();
        // GraphLoader.loadGraph(zzz, "data/super_small_test.txt");
        // System.out.println(zzz.exportGraphString());
        // System.out.println(findMinimumDominatingSet(zzz));
        
        // Graph aaa = new Graph();
        // GraphLoader.loadGraph(aaa, "data/dominating_set_test.txt");
        // System.out.println(aaa.exportGraphString());
        // System.out.println(findMinimumDominatingSet(aaa));
        // System.out.println(findGreedyDominatingSet(aaa));

        // Graph bbb = new Graph();
        // GraphLoader.loadGraph(bbb, "data/dominating_set_test_2.txt");
        // System.out.println(bbb.exportGraphString());
        // System.out.println(findMinimumDominatingSet(bbb));
        // System.out.println(findGreedyDominatingSet(bbb));

        // Graph ccc = new Graph();
        // GraphLoader.loadGraph(ccc, "data/small_test_graph.txt");
        // System.out.println(ccc.exportGraphString());
        // System.out.println(findMinimumDominatingSet(ccc));
        // System.out.println(findGreedyDominatingSet(ccc));

        Graph ddd = new Graph();
        GraphLoader.loadGraph(ddd, "data/facebook_1000.txt");
        System.out.println("Number of Nodes: " + ddd.getNumNodes());
        //System.out.println(ddd.exportGraphString());
        
        System.out.println("\nMinimum Dominating Set ...");
        List<Node> mds = findMinimumDominatingSet(ddd);
        System.out.println("Size of mds with tree: " + mds.size() );
        
        List<Node> mdsbf = bruteForce(ddd);
        System.out.println("Size of brute force: " + mdsbf.size() );

        System.out.println("\nGreedy Dominating Set ...");
        List<Node> gds = findGreedyDominatingSet(ddd);
        System.out.println("Size of gds: " + gds.size() );
    }

}