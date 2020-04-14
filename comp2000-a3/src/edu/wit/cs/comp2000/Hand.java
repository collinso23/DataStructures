/**
 *
 */
package edu.wit.cs.comp2000;

import java.util.Iterator;
import java.util.stream.StreamSupport;

/** @author Orion Collins */
public class Hand extends Pile {
    private int cardsInHand;

    Hand() {
		super();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//declare new hand obj
	    Hand testHand = new Hand();
        Hand testHand2 = new Hand();

        //declare testCards
		Card testCard1 = new Card(Suit.CLUBS, Rank.FOUR);
		Card testCard2 = new Card(Suit.DIAMONDS, Rank.NINE);
        Card testCard3 = new Card(Suit.SPADES, Rank.KING);

        //init testHand
        testHand.addToHand(testCard1);
		testHand.addToHand(testCard2);

        //init testHand2
        testHand2.addToHand(testCard1);
        testHand2.addToHand(testCard2);
        testHand2.addToHand(testCard3);

        //get the total number of points in a Hand
        System.out.println(testHand.getHandTotal());

        //check if a hand is valid(total points <= 21)
        System.out.println("Is the testHand valid?: " + testHand.handValid());
        System.out.println("Is the testHand2 valid?: " + testHand2.handValid());


        //test hand reset()
        System.out.println("Size of the hand before resetHand() "+ testHand.getCardsInHand());
		testHand.resetHand();
		System.out.println("Size of the hand after resetHand() "+ testHand.getCardsInHand());


	}

	// count number of cards in the hand
    private int getCardsInHand() {
		return cardsInHand;
	}
	
	// total the number of points from the hand
	int getHandTotal() {
		Iterable<Card> iterable = super::pileIterator;
		return StreamSupport.stream(iterable.spliterator(), false).mapToInt(Card::getCardPoints).sum();
	}
	
	public Hand addToHand(Card card) {
		super.addToPile(card);
		cardsInHand++;
		return this;
	}
	
	// check if hand has surpassed 21 point threshold
	public boolean handValid() {
		int MAX_POINTS = 21;
		return getHandTotal() <= MAX_POINTS;
	}
	
	// reset cards in hand to 0 by removing each card from the hand
	private void resetHand() {
		Iterator<Card> iterator = super.pileIterator();
		while (iterator.hasNext())  super.removeFromPile(iterator.next());
		cardsInHand = 0;
	}
}
