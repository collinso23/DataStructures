package edu.wit.cs.comp2000;
/**
   An interface of basic methods for the ADT tree.
 */

public interface TreeInterface<T>
{
	public T getRootData();
	public int getHeight();
	public int getNumberOfNodes();
	public boolean isEmpty();
	public void clear();
} // end TreeInterface
