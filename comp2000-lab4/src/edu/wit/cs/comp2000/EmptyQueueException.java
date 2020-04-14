package edu.wit.cs.comp2000;

/**
   A class of runtime exceptions thrown by methods to
   indicate that a queue is empty.
 */
public class EmptyQueueException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyQueueException() {
		this(null);
	} // end default constructor

	public EmptyQueueException(String message) {
		super(message);
	} // end constructor

} // end class EmptyQueueException
