package edu.wit.cs.comp2000;

/* Solves all the moves in Tower of Hanoi 
 * 
 * Wentworth Institute of Technology
 * COMP 2000
 * Lab 3
 * 
 */

import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class TowerOfHanoi {

	// CREATE a temporary Pole/Stack


	// Moves all of the discs from the startTower to endTower without ever stacking a
	// larger disc over a smaller one.
	public static void moveTower(int numberOfDiscs, StackInterface<Integer> startTower, StackInterface<Integer> endTower) {
		StackInterface<Integer> tempTower = new VectorStack<>();
		tempTower.setName("temp tower");
		solveTower(numberOfDiscs, startTower, tempTower, endTower);
	}

	public static void solveTower(int numberOfDiscs, StackInterface<Integer> startTower, StackInterface<Integer> tempTower, StackInterface<Integer> endTower) {
		if (numberOfDiscs==1) {
			//mv disk from start to end
			endTower.push(startTower.pop());
			System.out.printf("Move %s, %s -> %s\n",numberOfDiscs,startTower.getName(),endTower.getName());
		} else {
			//setname and getname on stacks
			solveTower(numberOfDiscs-1, startTower, endTower, tempTower);
			//mv disk from start to end pole
			endTower.push(startTower.pop());
			System.out.printf("Move %s, %s -> %s\n",numberOfDiscs,startTower.getName(),endTower.getName());
			solveTower(numberOfDiscs-1, tempTower, startTower, endTower);
		}

	}

	public static void main(String[] args) {
		
		System.out.print("How many discs would you like to move? Enter an integer: ");
		Scanner in = new Scanner(System.in);
		
		int count = in.nextInt();
		if (count > 38 || count < 1) {
			System.out.println("You can't build a stack with that height!");
			System.exit(0);
		}
		
		StackInterface<Integer> startTower = new VectorStack<>();
		StackInterface<Integer> endTower = new VectorStack<>();

		startTower.setName("start tower");
		endTower.setName("end tower");
		
		System.out.println("Adding discs to start tower...");
		for (int i = count; i > 0; i--)
			startTower.push(i);
		

		moveTower(count, startTower, endTower);
		
		System.out.println("Done!");
		
		in.close();
	}


}
