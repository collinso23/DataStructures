package edu.wit.cs.comp2000.tests;

import java.lang.reflect.Array;
import java.security.Permission;
import java.util.Iterator;
import java.util.Arrays;
import com.sun.javafx.collections.ListListenerHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import edu.wit.cs.comp2000.LinkedListPlus;
import edu.wit.cs.comp2000.ListInterface;

import static org.junit.Assert.*;

public class Lab5Tests{

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

	@Test
	public void testClear() {
		ListInterface<String> l = new LinkedListPlus<>();

		l.add("entry1");
		l.add("entry2");
		l.add("entry3");
		l.clear();
		assertTrue("LL+ after clear()",l.isEmpty());
	}

	@Test
	public void testAdd() {
		ListInterface<String> l = new LinkedListPlus<>();

		l.add("entry1");
		l.add("entry2");
		l.add("entry3");

		l.add(1, "entry1");
		l.add(5, "entry5");

		boolean exceptionDetected = false;

		try {
			l.add(12, "hi");
		} catch (IndexOutOfBoundsException e) {
			exceptionDetected = true;
		}

		assertTrue("There is no position 12 in the list, exception should be thrown", exceptionDetected);
	}

	@Test
	public void testRemove() {
		ListInterface<String> l = new LinkedListPlus<>();

		l.add("index 1");
		l.add("index 2");
		l.add("index 3");
		assertEquals("Remove index returns valid result from position",l.remove(1),"index 1");

	}

	@Test
	public void testReplace() {
		LinkedListPlus<String> l = new LinkedListPlus<>();

		l.add("index 1");
		l.add("index 2");
		l.add("index 3");
		l.add("index 4");
		l.add("index 5");
		boolean exceptionDetected = false;

		try {
			l.replace(4,"replace");
		} catch (IndexOutOfBoundsException e){
			exceptionDetected = true;
		}
		assertFalse("Running replace did not throw exception", exceptionDetected);

		try {
			l.replace(10,"replace");
		} catch (IndexOutOfBoundsException e){
			exceptionDetected = true;
		}
		assertTrue("replace on index out of range of LL+ throws expection",exceptionDetected);

		assertEquals("Entry inserted at right place",l.getEntry(4),"replace");
		assertTrue("New entry is in the list",l.contains("replace"));

	}

	@Test
	public void testGetEntry() {
		ListInterface<String> l = new LinkedListPlus<>();

		l.add("index 1");
		l.add("index 2");
		l.add("index 3");

		assertEquals("Strings should match", "index 1", l.getEntry(1));
		assertEquals("Strings should match", "index 3", l.getEntry(3));

		boolean exceptionDetected = false;

		try {
			l.getEntry(12);
		} catch (IndexOutOfBoundsException e) {
			exceptionDetected = true;
		}

		assertTrue("There is no position 12 in the list, exception should be thrown", exceptionDetected);
	}

	@Test
	public void testToArray() {
		LinkedListPlus<String> l = new LinkedListPlus<>();
		l.add("index 1");
		l.add("index 2");
		l.add("index 3");

		Comparable<String>[] arr = l.toArray();

		for (int i = 1; i <= 3; i++)
			assertEquals("Unexpected value at index " + i, arr[i - 1], "index " + i);
	}

	@Test
	public void testContains() {
		LinkedListPlus<String> l = new LinkedListPlus<>();
		l.add("index 1");
		l.add("index 2");
		l.add("index 2");
		assertTrue("Does contains worK?",l.contains("index 1"));
	}

	@Test
	public void testGetLength() {
		ListInterface<String> l = new LinkedListPlus<>();

		assertEquals("list should be empty", 0, l.getLength());
		l.add("entry");
		assertEquals("list should not be empty", 1, l.getLength());
	}

	@Test
	public void testIsEmpty() {
		ListInterface<String> l = new LinkedListPlus<>();

		assertTrue("list should be empty", l.isEmpty());
		l.add("entry");
		assertFalse("list should not be empty", l.isEmpty());
	}

	@Test
	public void testSort() {
		LinkedListPlus<Integer> testL1 = new LinkedListPlus<>();
		testL1.add(1);
		testL1.add(2);
		testL1.add(7);
		testL1.add(5);
		testL1.add(11);
		testL1.add(3);
		System.out.println("unsorted Array: "+ Arrays.toString(testL1.toArray()));

		testL1.sort();

		Integer[] sortedArr = new Integer[]{1,2,3,5,7,11};
		assertEquals("post sort() LL+ = sortedArr",testL1.toArray(),sortedArr);

	}

	@Test
	public void testReverse() {
		LinkedListPlus<String> testL1 = new LinkedListPlus<>();

		testL1.add("x");
		testL1.add("y");
		testL1.add("z");
		String[] testStrArr = new String[]{"z","y","x"};
		System.out.println("pre-reverse() testL1 " + Arrays.toString(testL1.toArray()));

		testL1.reverse();
		assertEquals("post-reverse() testL1 = testStrArr ",testL1.toArray(),testStrArr);

	}

	@Test
	public void testIterator() {
		LinkedListPlus<String> l = new LinkedListPlus<>();

		l.add("first");
		l.add("second");
		l.add("third");

		Iterator<String> i = l.iterator();

		assertTrue("iterator should have next", i.hasNext());
		assertEquals("iterator should find first", "first", i.next());
		assertTrue("iterator should have next", i.hasNext());
		assertEquals("iterator should find second", "second", i.next());
		assertTrue("iterator should have next", i.hasNext());
		assertEquals("iterator should find third", "third", i.next());
		assertFalse("iterator should not have next", i.hasNext());
	}

}

