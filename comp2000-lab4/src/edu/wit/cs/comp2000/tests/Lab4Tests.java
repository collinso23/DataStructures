package edu.wit.cs.comp2000.tests;

import java.lang.reflect.Array;
import java.security.Permission;
import java.util.Arrays;

import com.sun.org.apache.xml.internal.security.transforms.implementations.TransformEnvelopedSignature;
import com.sun.xml.internal.ws.api.model.ExceptionType;
import edu.wit.cs.comp2000.EmptyQueueException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import edu.wit.cs.comp2000.QueueInterface;
import edu.wit.cs.comp2000.ArrayQueue;

import static org.junit.Assert.*;

public class Lab4Tests{

	@Rule
	public Timeout globalTimeout = Timeout.seconds(5);

	@SuppressWarnings("serial")
	private static class ExitException extends SecurityException {}

	private static class NoExitSecurityManager extends SecurityManager 
	{
		@Override
		public void checkPermission(Permission perm) {}

		@Override
		public void checkPermission(Permission perm, Object context) {}

		@Override
		public void checkExit(int status) { super.checkExit(status); throw new ExitException(); }
	}

	@Before
	public void setUp() throws Exception 
	{
		System.setSecurityManager(new NoExitSecurityManager());
	}

	@After
	public void tearDown() throws Exception 
	{
		System.setSecurityManager(null);
	}



	/** exception testing pattern:
	 *
	 boolean exceptionDetected = false;

	 try {
	 queue.operation("hi");
	 } catch (ExceptionType e) {
	 exceptionDetected = true;
	 }

	 assertTrue("Performing operation in current state should throw ExceptionType", exceptionDetected);
	 */

	/*
	 * enqueue does not return a value, so we check to make sure 
	 * our implementation doesn't crash and check exceptions
	 */
	@Test
	public void testEnqueue() {
		QueueInterface<String> testQueue = null;
		testQueue = new ArrayQueue<>(3);

		// check that each enqueue call is successful
		for (int i = 0; i < 3; i++) {
			testQueue.enqueue("item " + i);
		}
		assertFalse("Queue contains items",testQueue.isEmpty());

		// check that enqueue works with resizing
		boolean exceptionDetected = false;
		try {
			testQueue.enqueue("resizing time");
		} catch (SecurityException e){
			exceptionDetected = true;
		}
		assertFalse("Resizing array does not throw exception",exceptionDetected);
	}

	@Test
	public void testIsEmpty() {
		QueueInterface<String> testQueue = new ArrayQueue<>(); 

		assertTrue("Queue should start out empty", testQueue.isEmpty());

		testQueue.enqueue("testing");
		assertFalse("Queue should not be empty with 1 entry", testQueue.isEmpty());

		testQueue.enqueue("more testing");
		assertFalse("Queue should not be empty with 2 entries", testQueue.isEmpty());
	}

	@Test
	public void testDequeue() {
		// init testQueue as a new arrayQue that uses queinterface
		QueueInterface<String> testQueue = new ArrayQueue<>(3);

		boolean exceptionDetected = false;
		try {
			testQueue.dequeue();
		} catch (EmptyQueueException e) {
			exceptionDetected = true;
		}
		assertTrue("Deque from empty que throws exception",exceptionDetected);

		for (int i=0; i< 3; i++)
			testQueue.enqueue("item " + i);
		assertEquals("Dequeue removes the first entry",testQueue.getFront(),testQueue.dequeue());

	}

	@Test
	public void testGetFront() {
		QueueInterface<String> testQueue = new ArrayQueue<>(3);

		boolean exceptionDetected = false;
		try {
			testQueue.getFront();
		} catch (EmptyQueueException e) {
			exceptionDetected = true;
		}
		assertTrue("Attempting to getFront() of empty queue returns exception", exceptionDetected);

		// Init testArray with the same values as ArrayQueue to test is getFront() is returning the proper value
		String[] testArray = new String[3];
		for (int i=0; i< testArray.length; i++)
			testArray[i] = "item " + i;

		// enqueue values from testArr to testQueue
		for (String s : testArray)
			testQueue.enqueue(s);
		assertEquals("getFront() returns testQueue[0]", testQueue.getFront(),testArray[0]);

	}


	@Test
	public void testClear() {
		QueueInterface<String> testQueue = new ArrayQueue<>(3);

		for (int i = 0; i < 3; i++)
			testQueue.enqueue("item " + i);
		assertFalse("Queue is successfully initialized and ready to be cleared", testQueue.isEmpty());

		testQueue.clear();
		assertTrue("queue is empty after clear operation",testQueue.isEmpty());

	}

}
