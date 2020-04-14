package edu.wit.cs.comp2000.tests;

import java.security.Permission;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import edu.wit.cs.comp2000.BagInterface;
import edu.wit.cs.comp2000.LinkedBag;

import static org.junit.Assert.*;

public class Lab1Tests{
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(5);
	
	@SuppressWarnings("serial")
	private static class ExitException extends SecurityException {}
	
	private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {}
        
        @Override
        public void checkPermission(Permission perm, Object context) {}
        
        @Override
        public void checkExit(int status) { super.checkExit(status); throw new ExitException(); }
    }
	
	@Before
    public void setUp() throws Exception {
        System.setSecurityManager(new NoExitSecurityManager());
    }
	
	@After
    public void tearDown() throws Exception {
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
	
	@Test
	public void testAddAndToArray() {
		String[] arr = getStringArray();
		BagInterface<String> testBag = new LinkedBag<>(); 
		
		// check that each add call is successful
		for (int i = 0; i < arr.length; i++)
			assertTrue("String " + arr[i] + " was not successfully added to bag.", testBag.add(arr[i]));
		
		// Java can't dynamically cast a full array of objects, so we cast them one at a time
		Object[] output_o = testBag.toArray();
		String[] output = new String[output_o.length];
		
		for (int i = 0; i < output.length; i++)
			output[i] = (String) output_o[i];
		
		// check that the output array does not have 0 entries
		assertNotEquals("Array bag should not have 0 entries", 0, output.length);
		
		// check that the output array has as many entries as we put into the bag
		assertEquals("Array from bag does not have the same number of entries as were added",
				arr.length, output.length);
		
		// check that each entry in the bag is in the output
		Arrays.sort(arr);
		Arrays.sort(output);
		for (int i = 0; i < arr.length; i++)
			assertTrue("toArray is missing the entry " + arr[i], arr[i].equals(output[i]));
	}

	
	@Test
	public void testIsEmpty() {
		String[] arr = getStringArray();
		BagInterface<String> testBag = new LinkedBag<>(); 

		assertTrue("Bag should start out empty", testBag.isEmpty());
		
		testBag.add(arr[0]);
		assertFalse("Bag should not be empty with 1 entry", testBag.isEmpty());
		
		testBag.add(arr[1]);
		assertFalse("Bag should not be empty with 2 entries", testBag.isEmpty());
	}
	
	
	@Test
	public void testGetCurrentSize() {
		String[] arr = getStringArray();
		BagInterface<String> testBag = new LinkedBag<>();

		assertEquals("this is an empty bag",0,testBag.getCurrentSize());

		testBag.add(arr[0]);
		assertEquals("Size = 1",1,testBag.getCurrentSize());

		testBag.add(arr[1]);
		assertEquals("Size = 2",2,testBag.getCurrentSize());
	}

	
	@Test
	public void testRemoveRand() {
		String[] arr = getStringArray();
		BagInterface<String> testBag = new LinkedBag<>();

		assertFalse("can't remove from empty bag", testBag.remove(arr[0]));

		testBag.add(arr[2]);
		testBag.add(arr[4]);
		assertTrue("Remove and add same entry",testBag.remove(arr[2]));
	}
	
	@Test
	public void testRemoveSpecific() {
		String[] arr = getStringArray();
		BagInterface<String> testBag = new LinkedBag<>();

		assertFalse("can't remove from empty bag", testBag.remove(arr[0]));

		testBag.add(arr[2]);
		testBag.add(arr[4]);
		testBag.add(arr[1]);

		assertTrue("Remove target entry",testBag.remove(arr[1]));

		assertFalse("Remove target entry when not in bag", testBag.remove(arr[5]));
	}
	
	@Test
	public void testClear() {
		String[] arr = getStringArray();
		BagInterface<String> testBag = new LinkedBag<>();

		testBag.add(arr[0]);
		testBag.add(arr[1]);
		testBag.add(arr[2]);
		testBag.add(arr[3]);
		testBag.clear();

		assertTrue("bag cleared fully",testBag.isEmpty());
	}
	
	@Test
	public void testGetFrequencyOf() {
		String[] arr = getStringArray();
		BagInterface<String> testBag = new LinkedBag<>();

		testBag.add(arr[5]);
        testBag.add(arr[3]);
        testBag.add(arr[3]);
        testBag.add(arr[3]);
        testBag.add(arr[2]);

		assertEquals("Item listed does not exist",0,testBag.getFrequencyOf(arr[1]));

		assertEquals("There is 1 instantce of the item in bag",1,testBag.getFrequencyOf(arr[2]));

		assertEquals("There is 3 instances of item in bag",3,testBag.getFrequencyOf(arr[3]));
	}
	
	@Test
	public void testContains() {
		String[] arr = getStringArray();
		BagInterface<String> testBag = new LinkedBag<>();

		assertFalse("bag does not contain item", testBag.contains(arr[5]));

		testBag.add(arr[0]);
		assertTrue("bag does contain item",testBag.contains(arr[0]));
	}
	
}
