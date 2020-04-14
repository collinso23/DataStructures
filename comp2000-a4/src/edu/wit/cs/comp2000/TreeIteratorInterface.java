package edu.wit.cs.comp2000;
import java.util.Iterator;
/**
   An interface of iterators for the ADT tree.
 */

public interface TreeIteratorInterface<T> {
	public Iterator<T> getPreorderIterator();
	public Iterator<T> getPostorderIterator();
	public Iterator<T> getInorderIterator();
	public Iterator<T> getLevelOrderIterator();
} // end TreeIteratorInterface
