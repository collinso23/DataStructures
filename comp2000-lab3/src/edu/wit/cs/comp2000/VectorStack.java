package edu.wit.cs.comp2000;import java.util.Vector;import java.util.EmptyStackException;/**    A class of stacks whose entries are stored in a vector.    @author Frank M. Carrano    @author Timothy M. Henry    @version 4.0 */public final class VectorStack<T> implements StackInterface<T>{	private Vector<T> stack;   // Last element is the top entry in stack	private boolean initialized = false;	private static final int DEFAULT_CAPACITY = 50;	private static final int MAX_CAPACITY = 10000;	private String name;	public VectorStack()	{		this(DEFAULT_CAPACITY);	} // end default constructor	public VectorStack(int initialCapacity)	{		checkCapacity(initialCapacity);		stack = new Vector<>(initialCapacity); // Size doubles as needed		initialized = true;	} // end constructor	// 6.17	public void push(T newEntry)	{		checkInitialization();		stack.add(newEntry);	} // end push	// 6.18	public T peek()	{		checkInitialization();		if (isEmpty())			throw new EmptyStackException();		else			return stack.lastElement();	} // end peek	// 6.19	public T pop()	{		checkInitialization();		if (isEmpty())			throw new EmptyStackException();		else			return stack.remove(stack.size() - 1);	} // end pop	// 6.20	public boolean isEmpty()	{		return stack.isEmpty();	} // end isEmpty	// 6.20	public void clear()	{		stack.clear();		} // end clear	// Throws an exception if this object is not initialized.	private void checkInitialization()	{		if (!initialized)			throw new SecurityException ("VectorStack object is not initialized properly.");	} // end checkInitialization	// Throws an exception if the client requests a capacity that is too large.	private void checkCapacity(int capacity)	{		if (capacity > MAX_CAPACITY)			throw new IllegalStateException("Attempt to create a stack " +					"whose capacity exceeds " +					"allowed maximum.");	} // end checkCapacity		// Allows the name of a stack to be set or reset	public void setName(String n) {		name = n;	}		// Gets the current name of a stack	public String getName() {		return name;	}} // end VectorStack