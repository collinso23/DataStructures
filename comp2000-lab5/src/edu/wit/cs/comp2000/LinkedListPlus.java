package edu.wit.cs.comp2000;

import java.util.NoSuchElementException;
import java.util.Iterator;


/**
 * A class that implements the ADT list by using a chain of nodes that has both a head
 * reference and a tail reference.
 * 
 * @param
 * `	the class (or an ancestor class) of objects to be stored in the list such that T
 *        implements the Comparable interface.
 */

// Insertion sort the best for sorting linked lists
public class LinkedListPlus<T extends Comparable<T>> implements ListInterface<T>, Iterable<T> {

	private Node firstNode;    // Head reference to first node
	private Node lastNode;      // Tail reference to last node
	private int  numberOfEntries;


	public LinkedListPlus() {
		initializeDataFields();
	}  // end default constructor


	@Override
	public void clear() {
		initializeDataFields();
	}  // end clear()


	@Override
	public void add(T newEntry) {
		Node newNode = new Node(newEntry);

		if (isEmpty())
			firstNode = newNode;
		else
			lastNode.setNextNode(newNode);

		lastNode = newNode;
		numberOfEntries++;
	}  // end add()


	@Override
	public void add(int newPosition, T newEntry) {

		if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
			Node newNode = new Node(newEntry);

			if (isEmpty()) {
				firstNode = newNode;
				lastNode = newNode;
			}
			else if (newPosition == 1) {
				newNode.setNextNode(firstNode);
				firstNode = newNode;
			}
			else if (newPosition == numberOfEntries + 1) {
				lastNode.setNextNode(newNode);
				lastNode = newNode;
			}
			else {
				Node nodeBefore = getNodeAt(newPosition - 1);
				Node nodeAfter = nodeBefore.getNextNode();
				newNode.setNextNode(nodeAfter);
				nodeBefore.setNextNode(newNode);
			} // end if
			numberOfEntries++;
		}
		else
			throw new IndexOutOfBoundsException("Illegal position given to add operation.");
	}  // end add()


	@Override
	public T remove(int givenPosition) {
		T result;                           // Return value

		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
		{
			assert !isEmpty();
			if (givenPosition == 1) {              // Case 1: Remove first entry
				result = firstNode.getData();      // Save entry to be removed
				firstNode = firstNode.getNextNode();
				if (numberOfEntries == 1)
					lastNode = null;               // Solitary entry was removed
			}
			else {                                 // Case 2: Not first entry
				Node nodeBefore = getNodeAt(givenPosition - 1);
				Node nodeToRemove = nodeBefore.getNextNode();
				Node nodeAfter = nodeToRemove.getNextNode();
				nodeBefore.setNextNode(nodeAfter);// Disconnect the node to be removed
				result = nodeToRemove.getData();  // Save entry to be removed

				if (givenPosition == numberOfEntries)
					lastNode = nodeBefore;         // Last node was removed
			} // end if

			numberOfEntries--;
		}
		else
			throw new IndexOutOfBoundsException("Illegal position given to remove operation.");

		return result;                       // Return removed entry
	}  // end remove()


	@Override
	public T replace(int givenPosition, T newEntry) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			assert !isEmpty();

			Node desiredNode = getNodeAt(givenPosition);
			T originalEntry = desiredNode.getData();
			desiredNode.setData(newEntry);
			return originalEntry;
		}
		else
			throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
	}  // end replace()


	@Override
	public T getEntry(int givenPosition) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
		{
			assert !isEmpty();
			return getNodeAt(givenPosition).getData();
		}
		else
			throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
	}  // end getEntry()


	public T[] toArray() {
		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Comparable[numberOfEntries];

		int index = 0;
		Node currentNode = firstNode;
		while ((index < numberOfEntries) && (currentNode != null))
		{
			result[index] = currentNode.getData();
			currentNode = currentNode.getNextNode();
			index++;
		} // end while

		return result;
	}  // end toArray()


	@Override
	public boolean contains(T anEntry) {
		boolean found = false;
		Node currentNode = firstNode;

		while (!found && (currentNode != null))
		{
			if (anEntry.equals(currentNode.getData()))
				found = true;
			else
				currentNode = currentNode.getNextNode();
		} // end while

		return found;
	}  // end contains()


	@Override
	public int getLength() {
		return numberOfEntries;
	}  // end getLength()


	@Override
	public boolean isEmpty() {
		boolean result;

		if (numberOfEntries == 0) // Or getLength() == 0
		{
			assert (firstNode == null) && (lastNode == null);
			result = true;
		}
		else
		{
			assert (firstNode != null) && (lastNode != null);
			result = false;
		} // end if

		return result;
	}  // end isEmpty()


	// User Insertion sort most effective with LL

	@Override
	public void sort() {
		if (getLength()>1){
			assert firstNode !=null;
			Node unsortedPart = firstNode.getNextNode();
			assert unsortedPart != null;
			firstNode.setNextNode(null);

			while (unsortedPart!=null){
				Node nodeToInsert = unsortedPart;
				unsortedPart = unsortedPart.getNextNode();
				insertInOrder(nodeToInsert);
			}
		}
	}  // end sort()

	private void insertInOrder(Node nodeToInsert){
		T item = nodeToInsert.getData();
		Node currentNode = firstNode;
		Node previousNode = null;

		while (currentNode!=null && item.compareTo(currentNode.getData())>0){
			previousNode = currentNode;
			currentNode = currentNode.getNextNode();
		}
		if (previousNode!=null){
			previousNode.setNextNode(nodeToInsert);
			nodeToInsert.setNextNode(currentNode);
		}
		else {
			nodeToInsert.setNextNode(firstNode);
			firstNode = nodeToInsert;
		}
	}


	// set first to last, and flip pointer to create reversed list
	@Override
	public void reverse() {
		// TODO Auto-generated method stub
		Node prev = null;
		Node current = firstNode;
		Node next;
		while (current != null){
			next = current.getNextNode();
			current.next = prev;
			prev = current;
			current = next;
		}
		firstNode = prev;
	}  // end reverse()


	/* 
	 * @see java.lang.Iterable#iterator()
	 */

	// need defined iterator class to implement iterator
	@Override
	public Iterator<T> iterator() {
		//return iterator object (slides have how to create new object and return it)
		return new LLIterator();
	}  // end iterator()


	// Initializes the class's data fields to indicate an empty list.
	private void initializeDataFields() {
		firstNode = null;
		lastNode = null;
		numberOfEntries = 0;

	}  // end initializeDataFields()


	// Returns a reference to the node at a given position.
	// Precondition: List is not empty; 1 <= givenPosition <= numberOfEntries.
	private Node getNodeAt(int givenPosition) {
		assert (firstNode != null) && (1 <= givenPosition) &&
		(givenPosition <= numberOfEntries);
		Node currentNode = firstNode;

		if (givenPosition == numberOfEntries)
			currentNode = lastNode;
		else if (givenPosition > 1) {
			assert givenPosition < numberOfEntries;
			// Traverse the chain to locate the desired node
			for (int counter = 1; counter < givenPosition; counter++)
				currentNode = currentNode.getNextNode();
		} // end if

		assert currentNode != null;
		return currentNode;
	}  // end getNodeAt()

	private class LLIterator implements Iterator<T> {
		private Node nextNode;

		//LLIterator constructor
		private LLIterator() { nextNode=firstNode; }

		public boolean hasNext(){ return nextNode!=null; }

		public T next(){
			T result;
			if (hasNext()){
				result = nextNode.getData();
				nextNode = nextNode.getNextNode();
			}
			else
				throw new NoSuchElementException("Illegal call to next(); " +
						 "iterator is after end of list.");
			return result;
		}

		public void remove(){
			throw new UnsupportedOperationException("remove() is not supported");
		}

	}

	private class Node {

		private T   data;     // Data portion
		private Node next;    // Next to next node


		private Node(T dataPortion) {  // PRIVATE or PUBLIC is OK
			data = dataPortion;
			next = null;
		}  // end constructor


		private Node(T dataPortion, Node nextNode) { // PRIVATE or PUBLIC is OK
			data = dataPortion;
			next = nextNode;
		}  // end constructor


		private T getData() {
			return data;
		}  // end getData()


		private void setData(T newData) {
			data = newData;
		}  // end setData()


		private Node getNextNode() {
			return next;
		}  // end getNextNode()


		private void setNextNode(Node nextNode) {
			next = nextNode;
		}  // end setNextNode()

	}  // end inner class Node


} // end LinkedListPlus
