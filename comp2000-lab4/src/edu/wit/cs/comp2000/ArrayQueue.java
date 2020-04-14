package edu.wit.cs.comp2000;

import com.sun.xml.internal.ws.api.model.ExceptionType;

import java.util.concurrent.ExecutionException;

/**
 * A class that implements the ADT queue by using an expandable circular array
 * 
 */

/*need to keep track of front and back of list (circular array)
when you reach end loop back to beginning
last element in array needs a buffer of one space*/
public final class ArrayQueue<T> implements QueueInterface<T> {

	private T[] queue;		// Circular array of queue entries and one unused location
	private int frontIndex;
	private int backIndex;
	private boolean initialized = false;
	private int countOfEntries = -1;
	private static final int DEFAULT_CAPACITY = 50;
	private static final int MAX_CAPACITY = 10000;


	public ArrayQueue() {
		this(DEFAULT_CAPACITY);
	} // end default constructor


	public ArrayQueue(int initialCapacity) {
		checkCapacity(initialCapacity);
		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] tempQueue = ( T[] ) new Object[initialCapacity];
		queue = tempQueue;
		frontIndex = 0;
		backIndex = initialCapacity - 1;
		initialized = true;
	} // end constructor


	private void checkInitialization() {
		if (!initialized)
			throw new SecurityException("Array Queue has not been initialized!");
	}	// end checkInitialization()


	private void checkCapacity(int initialCapacity) {
		// STUB
		if (initialCapacity > MAX_CAPACITY)
			throw new IllegalStateException();
	}	// end checkCapacity()


	// Doubles the size of the array queue if it is full
	// Precondition: checkInitialization has been called.
	// Modified 
	private void ensureCapacity() {
		checkInitialization();
		// if array is full, double size of array
		try {
			if (countOfEntries == backIndex) {
				T[] oldQueue = queue;
				int oldSize = oldQueue.length;
				int newSize = 2 * oldSize;
				checkCapacity(newSize);

				// The cast is safe because the new array contains null entries
				@SuppressWarnings("unchecked")
				T[] tempQueue = (T[]) new Object[2 * oldSize];
				queue = tempQueue;
				for (int index = 0; index < oldSize; index++) {
					queue[index] = oldQueue[frontIndex];
					frontIndex = (frontIndex + 1) % oldSize;
				} // end for

				frontIndex = 0;
				backIndex = oldSize - 1;
			}
		} catch (Exception e) {
			throw new SecurityException("Failed attempt to resize array");
		}// end if
	}	// end ensureCapacity()


	@Override
	public void enqueue(T newEntry) {
		checkInitialization();
		ensureCapacity();
		backIndex = (backIndex + 1) % queue.length;
		queue[backIndex] = newEntry;
		countOfEntries++;
	}	// end enqueue()


	@Override
	public T dequeue() {
		checkInitialization();
		if (isEmpty())
			throw new EmptyQueueException();
		else {
			T front = queue[frontIndex];
			queue[frontIndex] = null;
			frontIndex = (frontIndex + 1) % queue.length;
			countOfEntries--;
			return front;
		} // end if

	}	// end dequeue()


	@Override
	public T getFront() {
		if (isEmpty())
			throw new EmptyQueueException();
		return queue[frontIndex];
	}	// end getFront()


	@Override
	public boolean isEmpty() {
		checkInitialization();
		if (countOfEntries < 0)
			return true;
		return (frontIndex) == ((backIndex + 1) % queue.length);

	}	// end isEmpty()


	@Override
	public void clear() {
		queue[frontIndex] = null;
		queue[backIndex] = null;
		countOfEntries = -1;

	}	// end clear()

} // end class ArrayQueue
