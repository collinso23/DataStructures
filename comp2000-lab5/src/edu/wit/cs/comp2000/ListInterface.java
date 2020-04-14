package edu.wit.cs.comp2000;


/**
 * An interface for the ADT List. Entries in the list have positions that begin with 1.

 * @param 
 *        the class (or an ancestor class) of objects to be stored in the list such that T
 *        implements the Comparable interface.
 */
public interface ListInterface< T extends Comparable<? super T> > {
	/**
	 * Task: Adds a new entry to the end of the list. Entries currently in the
	 * list are unaffected. The list's size is increased by 1.
	 * 
	 * @param newEntry the object to be added as a new entry
	 */
	public void add(T newEntry);


	/**
	 * Task: Adds a new entry at a specified position within the list. Entries
	 * originally at and above the specified position move to the next higher
	 * position within the list. The list's size is increased by 1.
	 * 
	 * @param newPosition an integer that specifies the desired position of the
	 *          new entry
	 * @param newEntry the object to be added as a new entry
	 */
	public void add(int newPosition, T newEntry);


	/**
	 * Task: Removes all entries from the list.
	 */
	public void clear();


	/**
	 * Task: Removes the entry at a given position from the list. Entries
	 * originally at positions higher than the given position move to the next
	 * lower position within the list, and the list's size is decreased by 1.
	 * 
	 * @param givenPosition an integer that indicates the position of the entry
	 *            to be removed
	 * @return if successful, a reference to the removed entry, otherwise null (e.g. 
	 *         givenPosition &lt; 0, or givenPosition &ge; getLength()
	 */
	public T remove(int givenPosition);


	/**
	 * Task: Replaces the entry at a given position in the list.
	 * 
	 * @param givenPosition
	 *        an integer that indicates the position of the entry to be replaced
	 * @param newEntry
	 *        the object that will replace the entry at the position givenPosition
	 * @return a reference to the replaced entry if the replacement is successful,
	 *         otherwise null (e.g. givenPosition &lt; 1, givenPosition &ge; getLength(), or
	 *         newEntry isn't valid/acceptable)
	 */
	public T replace(int givenPosition, T newEntry);


	/**
	 * Task: Retrieves the entry at a given position in the list.
	 * 
	 * @param givenPosition an integer that indicates the position of the
	 *            desired entry
	 * @return if successful, a reference to the indicated entry, otherwise null (e.g. 
	 *          givenPosition &lt; 1 or givenPosition &ge; getLength())
	 */
	public T getEntry(int givenPosition);


	/**
	 * Task: Sees whether the list contains a given entry.
	 * 
	 * @param anEntry the object that is the desired entry
	 * @return true if the list contains anEntry, or false if not
	 */
	public boolean contains(T anEntry);


	/**
	 * Task: Gets the length of the list.
	 * 
	 * @return the integer number of entries currently in the list
	 */
	public int getLength();


	/**
	 * Task: Sees whether the list is empty.
	 * 
	 * @return true if the list is empty, or false if not
	 */
	public boolean isEmpty();


	/**
	 * Task: Rearrange the entries in the list in non-descending order using a
	 * sorting algorithm.
	 */
	public void sort();


	/**
	 * Task: Reverse the order of the entries in the list.
	 */
	public void reverse();



} // end ListInterface
