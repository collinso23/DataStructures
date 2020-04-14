package edu.wit.cs.comp2000;

//import com.sun.tools.attach.AgentLoadException;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class SpellChecker {

	private static LinkedBag<String> genLinkedFile(String fName) {
		LinkedBag<String> curList = new LinkedBag<>();
		try (Scanner s = new Scanner(new File(fName))) {
			while ((s.hasNext())) {
				String currWord = s.next();
					curList.add(currWord.toLowerCase());
			}
		} catch (Exception e) {
			System.err.println("Cannot open file " + fName + ". Error:"+e);
			System.exit(0);
		}
		return curList;
	}
	public s	tatic void main(String[] args) {

		LinkedBag<String> dictBag = genLinkedFile("data/sowpods.txt");
		LinkedBag<String> usrBag = genLinkedFile("data/the-lancashire-cotton-famine.txt");
		LinkedBag<String> wrongBag = checkSpelling("data/the-lancashire-cotton-famine.txt",dictBag);
		System.out.printf("User's Bag %s\n", Arrays.deepToString(usrBag.toArray()));
		System.out.printf("wrong Bag %s\n", Arrays.deepToString(wrongBag.toArray()));

	}
	
	private static LinkedBag<String> checkSpelling(String fName, LinkedBag<String> dictBag) {
		LinkedBag<String> wrongBag = new LinkedBag<>();

		try (Scanner s = new Scanner(new File(fName))) {
			while ((s.hasNext())) {
				String curWord = s.next().toLowerCase().replaceAll("^[\\W]|[\\W]$|[\\d]","");

				if(!dictBag.contains(curWord) && !curWord.replaceAll("[\\W]", "").equals("")) {
					wrongBag.add(curWord);
				}
			}
		} catch (Exception e) {
			System.err.println("Cannot open file " + fName + ". Error:"+e);
			System.exit(0);
		}
		return wrongBag;
	}

}
