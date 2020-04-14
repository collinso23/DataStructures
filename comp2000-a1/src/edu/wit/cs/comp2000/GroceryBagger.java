package edu.wit.cs.comp2000;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GroceryBagger {

	// An example method to open a file
	private static void exampleOpenFile(String fName) {

		try (Scanner s = new Scanner(new File(fName))) {
			// loop over all grocery items
		} catch (FileNotFoundException e) {
			System.err.println("Cannot open file " + fName + ". Exiting.");
			System.exit(0);
		}
	}


	public static void main(String[] args) {
		exampleOpenFile("data/groceries.txt");

	}

}
