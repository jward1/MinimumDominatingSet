/**
 * 
 */
package graph;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

/**
 * @author jward
 *
 */

public class GraphTester {

	Graph smallGraph;
	Graph dominatingSetGraph1;
	Graph dominatingSetGraph2;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		smallGraph = new Graph();
		tools.GraphLoader.loadGraph(test3a, "data/small_test_graph.txt");

		dominatingSetGraph1 = new Graph();
		tools.GraphLoader.loadGraph(test3b, "data/dominating_set_test.txt");

		dominatingSetGraph2 = new Graph();
		tools.GraphLoader.loadGraph(test4a, "data/dominating_set_test_2.txt");
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGraphLoader()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	

// 	/** Test removing an element from the list.
// 	 * We've included the example from the concept challenge.
// 	 * You will want to add more tests.  */
// 	@Test
// 	public void testRemove()
// 	{
// 		int a = list1.remove(0);
// 		assertEquals("Remove: check a is correct ", 65, a);
// 		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
// 		assertEquals("Remove: check size is correct ", 2, list1.size());
		
// 		// test empty set
// 		try {
// 			emptyList.get(0);
// 			fail("Check out of bounds");
// 		}
// 		catch (IndexOutOfBoundsException e) {
			
// 		}
		
//         // test out of bounds cases
// 		try {
// 			shortList.remove(-1);
// 			fail("Check out of bounds");
// 		}
// 		catch (IndexOutOfBoundsException e) {
		
// 		}
// 		try {
// 			shortList.remove(2);
// 			fail("Check out of bounds");
// 		}
// 		catch (IndexOutOfBoundsException e) {
		
// 		}
// 	}

// 	/** Test adding an element into the end of the list, specifically
// 	 *  public boolean add(E element)
// 	 * */
// 	@Test
// 	public void testAddEnd()
// 	{
//         list1.add(1234);
//         assertEquals("Add element to end: check element was added", 
//         		(Integer)1234, list1.get(list1.size()-1));
//         assertEquals("Add element to end: check size is correct ", 4, list1.size());
//         assertEquals("Add element to end: check second to last element", 
//         		(Integer)42, list1.get(list1.size()-2));
        
//         shortList.add("C");
//         assertEquals("Add element to end: check element was added", 
//         		(String)"C", shortList.get(shortList.size()-1));
//         assertEquals("Add element to end: check size is correct ", 3, shortList.size());
//         assertEquals("Add element to end: check second to last element", 
//         		(String)"B", shortList.get(shortList.size()-2));
        
//         try{
//         	shortList.add(null);
//         	fail("Method should not accept null value");
//         }
//         catch (NullPointerException e){
// 		}
        
// 	}

	
// 	/** Test the size of the list */
// 	@Test
// 	public void testSize()
// 	{
// 		assertEquals("Check size: check accurate size return", LONG_LIST_LENGTH, longerList.size() );
// 		assertEquals("Check size: check empty list", 0, emptyList.size());
		
// 		for(int i=0; i<10; i++){
// 			longerList.add(LONG_LIST_LENGTH+i, LONG_LIST_LENGTH+i);
// 			assertEquals("Check size: check size after add", LONG_LIST_LENGTH+i+1, longerList.size());
// 		}
		
// 		for(int i=LONG_LIST_LENGTH+10; i>LONG_LIST_LENGTH+5; i--){
// 			longerList.remove(i-1);
// 			assertEquals("Check size: check size after remove", i-1, longerList.size());
// 		}
// 	}

	
	
// 	/** Test adding an element into the list at a specified index,
// 	 * specifically:
// 	 * public void add(int index, E element)
// 	 * */
// 	@Test
// 	public void testAddAtIndex()
// 	{
//         // add as first element
// 		list1.add(0,123);
//         assertEquals("Add at index: check element added as first element", 
//         		(Integer)123, list1.get(0));
//         assertEquals("Add at index: check second element", 
//         		(Integer)65, list1.get(1));
//         assertEquals("Add at index: check size is correct ", 4, list1.size());
        
//         // add to middle of list
// 		list1.add(1,111);
//         assertEquals("Add at index: check element added as element 2", 
//         		(Integer)111, list1.get(1));
//         assertEquals("Add at index: check second element", 
//         		(Integer)65, list1.get(2));
//         assertEquals("Add at index: check size is correct ", 5, list1.size());
        
//         // add to end of listed tested above in testAddEnd method
        
//         // test out of bounds cases
// 		try {
// 			shortList.add(-1, "F");
// 			fail("Check out of bounds");
// 		}
// 		catch (IndexOutOfBoundsException e) {
		
// 		}
// 		try {
// 			shortList.add(3, "F");
// 			fail("Check out of bounds");
// 		}
// 		catch (IndexOutOfBoundsException e) {
		
// 		}
		
//         try{
//         	shortList.add(null);
//         	fail("Method should not accept null value");
//         }
//         catch (NullPointerException e){
// 		}
		
        		
// 	}
	
// 	/** Test setting an element in the list */
// 	@Test
// 	public void testSet()
// 	{
// 		// test standard case
// 		list1.set(1, 321);
//         assertEquals("Set at index: check element set to correct element", 
//         		(Integer)321, list1.get(1));
        
//         // test null case
// 		try {
// 			list1.set(1, null);
// 			fail("Check NullPointerException handling");
// 		}
// 		catch (NullPointerException e) {
		
// 		}
		
// 		// test out of bounds index
// 		try {
// 			longerList.set(-1, 555);
// 			fail("Check out of bounds");
// 		}
// 		catch (IndexOutOfBoundsException e) {
		
// 		}
		
// 		try {
// 			longerList.set(LONG_LIST_LENGTH, 555);
// 			fail("Check out of bounds");
// 		}
// 		catch (IndexOutOfBoundsException e) {
		
// 		}
	    
// 	}

	
// }
