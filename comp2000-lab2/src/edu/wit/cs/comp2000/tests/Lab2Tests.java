package edu.wit.cs.comp2000.tests;

import java.lang.reflect.Array;
import java.security.Permission;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;

//import com.sun.source.tree.AssertTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import edu.wit.cs.comp2000.StackInterface;
import edu.wit.cs.comp2000.ArrayStack;

import static org.junit.Assert.*;

public class Lab2Tests{

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

	private String[] getStringArray() {
		String[] ret = new String[6];

		ret[0] = "abc";
		ret[1] = "123";
		ret[2] = "hello";
		ret[3] = "abc";
		ret[4] = "123";
		ret[5] = "goodbye";

		return ret;
	}


	/*
	 * push does not return a value, so we check to make sure 
	 * our implementation doesn't crash and check exceptions
	 */
	@Test
	public void testPush() {
		String[] arr = getStringArray();
		StackInterface<String> testStack;

		boolean exceptionDetected;

		testStack = new ArrayStack<>(3);

		// check that each add call is successful
		for (int i = 0; i < 3; i++)
			testStack.push(arr[i]);

		exceptionDetected = false;
		
		try {
			testStack.push(arr[3]);
		} catch (SecurityException e) {
			exceptionDetected = true;
		}

		assertTrue("Pushing onto a full stack should throw SecurityException", exceptionDetected);

	}


	@Test
	public void testIsEmpty() {
		StackInterface<String> testStack = new ArrayStack<>(); 

		assertTrue("Stack should start out empty", testStack.isEmpty());

		testStack.push("testing");
		assertFalse("Stack should not be empty with 1 entry", testStack.isEmpty());

		testStack.push("more testing");
		assertFalse("Stack should not be empty with 2 entries", testStack.isEmpty());
	}


	@Test
	public void testPop() {
		String[] arr = getStringArray();
		StackInterface<String> testStack = new ArrayStack<>(6);
		boolean exceptionDetected = false;

		try {
			testStack.pop();
		} catch (EmptyStackException e) {
			exceptionDetected = true;
		}
		assertTrue("Unable to pop from empty stack", exceptionDetected);

        for (String s : arr) testStack.push(s);
		assertEquals("Pop removes topIndex and returns",arr[arr.length-1],testStack.pop());


		testStack.clear();
		// Tests if stack is functioning correctly
		// Add multiple elements to stack
		String[] stackarr = Arrays.copyOf(arr, arr.length);
        for (String s : arr) testStack.push(s);
		System.out.println(Arrays.toString(stackarr));
		// See if items pop in reverse order
		for (int i = 0; i < arr.length; i++)
			System.out.print(testStack.pop()+" ");
		assertTrue("Stack will be empty after each item is popped",testStack.isEmpty());
	}


	@Test
	public void testPeek() {
		String[] arr = getStringArray();
		StackInterface<String> testStack = new ArrayStack<>(6);

		boolean exceptionDetected = false;
		try {
			testStack.peek();
		} catch (EmptyStackException e) {
			exceptionDetected = true;
		}
		assertTrue("Peek on empty stack throws error",exceptionDetected);

        for (String s : arr) testStack.push(s);
		assertEquals("Peek is topIndex",arr[arr.length-1],testStack.peek());

		testStack.pop();
		assertNotEquals("Peek has been popped, checking if pointer has updated",arr[arr.length-1],testStack.peek());


	}

	@Test
	public void testClear() {
		String[] arr = getStringArray();
		StackInterface<String> testStack = new ArrayStack<>(6);
		boolean exceptionDetected = false;

		try {
			testStack.clear();
		} catch (EmptyStackException e) {
			exceptionDetected = true;
		}
		assertTrue("Unable to clear empty stack",exceptionDetected);

        for (String s : arr) testStack.push(s);
		testStack.clear();
		assertTrue("Stack of size n is empty after clear",testStack.isEmpty());
	}

}
