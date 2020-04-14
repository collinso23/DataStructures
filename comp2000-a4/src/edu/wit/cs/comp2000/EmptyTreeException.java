package edu.wit.cs.comp2000;
/**
   A class of runtime exceptions thrown by methods to
   indicate that a tree is empty.
 */

public class EmptyTreeException extends RuntimeException {
	public EmptyTreeException() {
		this(null);
	} // end default constructor

	public EmptyTreeException(String message) {
		super(message);
	} // end constructor
} // end EmptyTreeException
