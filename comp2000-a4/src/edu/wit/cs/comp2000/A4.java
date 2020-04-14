package edu.wit.cs.comp2000;
import java.util.Iterator;
import static org.junit.Assert.*;
/**
 * A class to test the functionality of the BinarySearchTree class 
 */

public class A4 {

	public static void main(String[] args) {
		// STUB

		// init tree and add nodes to it
        BinarySearchTree<Integer> testBST = new BinarySearchTree<>();
        testBST.add(5);
		testBST.add(3);
        testBST.add(6);
        testBST.add(10);
		printTree(testBST);
		assertFalse(testBST.isEmpty());

		// get rootNode data for easy access
		int rootNodeData = testBST.getRootData();

		// _____Begin Testing Section_____
		System.out.println("\n___Tree Statistics___");
		System.out.println("Root Data: " + rootNodeData);
		System.out.println("Number of nodes: " + testBST.getNumberOfNodes());
		System.out.println("Tree Height: " + testBST.getHeight());

		System.out.println("\n___Testing Search___");
		assertTrue(testBST.contains(rootNodeData));
		System.out.println("contains(6):" + testBST.contains(6));
		assertTrue(testBST.contains(6));
		System.out.println("contains(11):"+testBST.contains(11));
		assertFalse(testBST.contains(11));


		System.out.println("\n___Testing next() and prev() methods___");
		System.out.println("next(root): "+testBST.next(rootNodeData));
		System.out.println("prev(root): "+testBST.prev(rootNodeData));
		assertEquals(6, (int) testBST.next(rootNodeData));
		assertEquals(3, (int) testBST.prev(rootNodeData));

		System.out.println("prev(6): "+testBST.prev(6));
		System.out.println("next(10): "+testBST.next(10));
		System.out.println("prev(3): "+testBST.prev(3));
		assertNull("Test Next: ",testBST.next(10));
		assertNull("Test Prev: ",testBST.prev(3));
		// _____Enc Testing Section_____
	}

	/** Prints out every element in a supplied BST
	 * 
	 * @param b The binary tree to print
	 */

	public static <T extends Comparable<? super T>> void printTree(BinarySearchTree<T> b) {
		Iterator<T> i = b.getInorderIterator();
		
		System.out.println("Printing tree: ");
		while (i.hasNext()) {
			System.out.println(i.next());
		}
		System.out.println();
	}

}
