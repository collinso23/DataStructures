package edu.wit.cs.comp2000.StackAndQueuePackage;
/**
   A class of runtime exceptions thrown by methods to
   indicate that a queue is empty.
 */

public class EmptyQueueException extends RuntimeException {
	public EmptyQueueException() {
		this(null);
	} // end default constructor

	public EmptyQueueException(String message) {
		super(message);
	} // end constructor
} // end EmptyQueueException
