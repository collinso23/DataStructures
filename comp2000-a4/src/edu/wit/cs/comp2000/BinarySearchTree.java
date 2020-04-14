package edu.wit.cs.comp2000;

import java.beans.BeanInfo;

/**
 A class that implements the ADT binary search tree by extending BinaryTree.
 */

public class BinarySearchTree<T extends Comparable<? super T>> extends BinaryTree<T> implements SearchTreeInterface<T> {

	public BinarySearchTree() {
		super();
	} // end default constructor

	public BinarySearchTree(T rootEntry) {
		super();
		setRootNode(new BinaryNode<T>(rootEntry));
	} // end constructor

	// Disable setTree (see Segment 26.6)
	public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree) {
		throw new UnsupportedOperationException();
	} // end setTree

	public T getEntry(T anEntry) {
		return findEntry(getRootNode(), anEntry);
	} // end getEntry

	private T findEntry(BinaryNode<T> rootNode, T anEntry) {
		T result = null;

		if (rootNode != null) {
			T rootEntry = rootNode.getData();

			if (anEntry.equals(rootEntry))
				result = rootEntry;
			else if (anEntry.compareTo(rootEntry) < 0)
				result = findEntry(rootNode.getLeftChild(), anEntry);
			else
				result = findEntry(rootNode.getRightChild(), anEntry);
		} // end if

		return result;
	} // end findEntry

	// looks for the next node given a valid entry else returns null
	public T next(T anEntry) {
		BinaryNode<T> currNode = findNode(getRootNode(),anEntry);

		if (currNode.hasRightChild())
			currNode = min(currNode.getRightChild());
		else {
			currNode = up(currNode);
		}

		// prevents NullPointerExceptions for end nodes, by returning null instead
		if (currNode==null)
			return null;
		return currNode.getData();
	} // end next

	// looks for previous node given a valid entry else returns null
	public T prev(T anEntry) {
		BinaryNode<T> currNode = findNode(getRootNode(),anEntry);

		if (currNode.hasLeftChild())
			currNode = min(currNode.getLeftChild());
		else
			currNode = down(currNode);

		// prevents NullPointerExceptions for end nodes, by returning null instead
		if (currNode==null)
			return null;
		return currNode.getData();
	} // end prev

	// _____Private Helper Methods for Next and Prev_____

	// finds the min child given a node
	private BinaryNode<T> min(BinaryNode<T> aNode){
		if (aNode == null)
			return null;
		if (!aNode.hasLeftChild())
			return aNode;
		return min(aNode.getLeftChild());
	} //end min

	// traverses tree in the up direction
	private BinaryNode<T> up(BinaryNode<T> aNode){
		BinaryNode<T> parN = aNode.getParent();
		if (parN == null || aNode==parN.getLeftChild())
			return parN;
		if (aNode.isLeaf())
			return null;
		return up(parN);
	} //end up

	// traverses tree in the down direction
	private BinaryNode<T> down(BinaryNode<T> aNode){
		BinaryNode<T> parN = aNode.getParent();
		if (parN==null || aNode==parN.getRightChild())
			return parN;
		if (aNode.isLeaf())
			return null;
		return down(parN);
	} //end down

	// finds a BinaryNode in the BST and returns that node for next/prev operations
	// if node not found returns null
	public BinaryNode<T> findNode(BinaryNode<T> currentNode, T anEntry) {
		if (currentNode!= null) {
			if (anEntry.equals(currentNode.getData()))
				return currentNode;
			else if (anEntry.compareTo(currentNode.getData()) < 0)
				return findNode(currentNode.getLeftChild(), anEntry);
			else
				return findNode(currentNode.getRightChild(), anEntry);
		}
		return null;
	}
	//_____End Private Helper Sections_____



	public boolean contains(T entry) {
		return getEntry(entry) != null;
	} // end contains

	public T add(T newEntry) {
		T result = null;

		if (isEmpty())
			setRootNode(new BinaryNode<>(newEntry));
		else
			result = addEntry(getRootNode(), newEntry);

		return result;
	} // end add

	// Adds anEntry to the nonempty subtree rooted at rootNode.
	private T addEntry(BinaryNode<T> rootNode, T anEntry) {
		// Assertion: rootNode != null
		T result = null;
		int comparison = anEntry.compareTo(rootNode.getData());

		if (comparison == 0) {
			result = rootNode.getData();
			rootNode.setData(anEntry);
		}
		else if (comparison < 0) {
			if (rootNode.hasLeftChild())
				result = addEntry(rootNode.getLeftChild(), anEntry);
			else {
				rootNode.setLeftChild(new BinaryNode<>(anEntry));
				rootNode.getLeftChild().setParent(rootNode);
			}
		}
		else {
			// Assertion: comparison > 0

			if (rootNode.hasRightChild())
				result = addEntry(rootNode.getRightChild(), anEntry);
			else {
				rootNode.setRightChild(new BinaryNode<>(anEntry));
				rootNode.getRightChild().setParent(rootNode);
			}
		} // end if

		return result;
	} // end addEntry

	public T remove(T anEntry) {
		ReturnObject oldEntry = new ReturnObject(null);
		BinaryNode<T> newRoot = removeEntry(getRootNode(), anEntry, oldEntry);
		setRootNode(newRoot);

		return oldEntry.get();
	} // end remove

	// Removes an entry from the tree rooted at a given node.
	// Parameters:
	//    rootNode A reference to the root of a tree.
	//    anEntry  The object to be removed.
	//    oldEntry An object whose data field is null.
	//    Returns: The root node of the resulting tree; if anEntry matches
	//             an entry in the tree, oldEntry's data field is the entry
	//             that was removed from the tree; otherwise it is null.
	private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T anEntry, ReturnObject oldEntry) {

		if (rootNode != null) {
			T rootData = rootNode.getData();
			int comparison = anEntry.compareTo(rootData);

			if (comparison == 0) {      // anEntry == root entry
				oldEntry.set(rootData);
				rootNode = removeFromRoot(rootNode);
			}
			else if (comparison < 0) {  // anEntry < root entry
				BinaryNode<T> leftChild = rootNode.getLeftChild();
				BinaryNode<T> subtreeRoot = removeEntry(leftChild, anEntry, oldEntry);
				rootNode.setLeftChild(subtreeRoot);
				subtreeRoot.setParent(rootNode);
			}
			else {                      // anEntry > root entry
				BinaryNode<T> rightChild = rootNode.getRightChild();
				BinaryNode<T> subtreeRoot = removeEntry(rightChild, anEntry, oldEntry);
				// A different way of coding than for left child:
				rootNode.setRightChild(subtreeRoot);
				subtreeRoot.setParent(rootNode);
			} // end if
		} // end if

		return rootNode;
	} // end removeEntry

	// Removes the entry in a given root node of a subtree.
	// rootNode is the root node of the subtree.
	// Returns the root node of the revised subtree.
	private BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode) {
		// Case 1: rootNode has two children
		if (rootNode.hasLeftChild() && rootNode.hasRightChild()) {
			// Find node with largest entry in left subtree
			BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
			BinaryNode<T> largestNode = findLargest(leftSubtreeRoot);

			// Replace entry in root
			rootNode.setData(largestNode.getData());

			// Remove node with largest entry in left subtree
			rootNode.setLeftChild(removeLargest(leftSubtreeRoot));
		} // end if

		// Case 2: rootNode has at most one child
		else if (rootNode.hasRightChild()) {
			rootNode.getRightChild().setParent(rootNode.getParent());
			rootNode = rootNode.getRightChild();
		}
		else {
			rootNode.getLeftChild().setParent(rootNode.getParent());
			rootNode = rootNode.getLeftChild();
		}

		// Assertion: If rootNode was a leaf, it is now null

		return rootNode;
	} // end removeFromRoot

	// Finds the node containing the largest entry in a given tree.
	// rootNode is the root node of the tree.
	// Returns the node containing the largest entry in the tree.
	private BinaryNode<T> findLargest(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild())
			rootNode = findLargest(rootNode.getRightChild());

		return rootNode;
	} // end findLargest

	// Removes the node containing the largest entry in a given tree.
	// rootNode is the root node of the tree.
	// Returns the root node of the revised tree.
	private BinaryNode<T> removeLargest(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild()) {
			BinaryNode<T> rightChild = rootNode.getRightChild();
			rightChild = removeLargest(rightChild);
			rootNode.setRightChild(rightChild);
			rootNode.getRightChild().setParent(rootNode);
		}
		else {
			rootNode = rootNode.getLeftChild();
		}
		return rootNode;
	} // end removeLargest

	private class ReturnObject {
		private T item;

		private ReturnObject(T entry) {
			item = entry;
		} // end constructor

		public T get() {
			return item;
		} // end get

		public void set(T entry) {
			item = entry;
		} // end set
	} // end ReturnObject
} // end BinarySearchTree
