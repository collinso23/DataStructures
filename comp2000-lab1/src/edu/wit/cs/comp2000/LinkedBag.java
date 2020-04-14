package edu.wit.cs.comp2000;
/**
    A class of bags whose entries are stored in a chain of linked nodes.
    The bag is never full.
    @author Frank M. Carrano
    @version 4.0
 */
public final class LinkedBag<T> implements BagInterface<T> {
	private Node firstNode;       // Reference to first node
	private int numberOfEntries;

	public LinkedBag() {
		firstNode = null;
		numberOfEntries = 0;
	} // end default constructor

	/** Adds a new entry to this bag.
        @param newEntry  The object to be added as a new entry.
        @return  True. */
	public boolean add(T newEntry) { // OutOfMemoryError possible
		// Add to beginning of chain:
		try {
			Node newNode = new Node(newEntry);
			newNode.next = firstNode; // Make new node reference rest of chain
			// (firstNode is null if chain is empty)
			firstNode = newNode;      // New node is at beginning of chain
			numberOfEntries++;
		}
		catch(OutOfMemoryError err) {
			System.out.println("Error: "+err+" occurred the array is to large!");
			return false;
		}

		return true;
	} // end add

	/** Retrieves all entries that are in this bag.
       @return  A newly allocated array of all the entries in this bag. */
	public T[] toArray() {
		// The cast is safe because the new array contains null entries.
		@SuppressWarnings("unchecked")
		T[] result = (T[])new Object[numberOfEntries]; // Unchecked cast

		int index = 0;
		Node currentNode = firstNode;
		while ((index < numberOfEntries) && (currentNode != null)) {
			result[index] = currentNode.data;
			index++;
			currentNode = currentNode.next;
		} // end while

		return result;
	} // end toArray

	/** Sees whether this bag is empty.
       @return  True if the bag is empty, or false if not. */
	public boolean isEmpty() {
		return numberOfEntries == 0;
	} // end isEmpty

	/** Gets the number of entries currently in this bag.
       @return  The integer number of entries currently in the bag. */
	public int getCurrentSize() {
		return numberOfEntries;
	} // end getCurrentSize
	
	/** Removes one unspecified entry from this bag, if possible.
       @return  Either the removed entry, if the removal
                was successful, or null. */
	public T remove() {
		T result = null;
		if(firstNode!=null) {
			result = firstNode.data;
			firstNode = firstNode.next;
			numberOfEntries--;
		}
		return result; // STUB
	} // end remove

	/** Removes one occurrence of a given entry from this bag.
       @param anEntry  The entry to be removed.
       @return  True if the removal was successful, or false otherwise. */
	public boolean remove(T anEntry) {
		boolean result = false;
		Node nextNode = getReferenceTo(anEntry);
		if (nextNode != null){
			nextNode.data = firstNode.data;
			firstNode = firstNode.next;
			numberOfEntries--;
			result = true;
		}

		return result; // STUB
	} // end remove

	/** Removes all entries from this bag.
	 * @return*/
	public void clear() {
		while(!isEmpty()){
			remove();
		}
	} // end clear

	/** Counts the number of times a given entry appears in this bag.
         @param anEntry  The entry to be counted.
         @return  The number of times anEntry appears in the bag. */
	public int getFrequencyOf(T anEntry) {
		int frequency = 0;
		Node currNode = firstNode;
		while (currNode != null){
			if(anEntry.equals(currNode.data))
				frequency++;
			currNode = currNode.next;
		}
		return frequency; // STUB
	} // end getFrequencyOf

	/** Tests whether this bag contains a given entry.
         @param anEntry  The entry to locate.
         @return  True if the bag contains anEntry, or false otherwise. */
	public boolean contains(T anEntry) {
		boolean found = false;
		Node currNode = firstNode;
		while(!found && (currNode != null)){
			if(anEntry.equals(currNode.data))
				found = true;
			else
				currNode = currNode.next;
		}
		return found;
	} // end contains

	//Private getReference method used to identify the node next in list
	private Node getReferenceTo(T anEntry){
		boolean found = false;
		Node currNode = firstNode;

		while (!found && (currNode != null)){
			if (anEntry.equals(currNode.data)){
				found = true;
			}
			else
				currNode = currNode.next;
		}
		return currNode;
	}
	private class Node
	{
		private T    data; // Entry in bag
		private Node next; // Link to next node

		private Node(T dataPortion) {
			this(dataPortion, null);    
		} // end constructor

		private Node(T dataPortion, Node nextNode) {
			data = dataPortion;
			next = nextNode;    
		} // end constructor
	} // end Node
} // end LinkedBag
