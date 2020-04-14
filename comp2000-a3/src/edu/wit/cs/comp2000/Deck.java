/**
 * 
 */
package edu.wit.cs.comp2000;

/**
 * @author Orion Collins
 */
public class Deck extends Pile {
	private int nextCardPos;

	//Constructor init all 52 cards into deck and set cursor at pos 0
	Deck() {
		for (Suit s:Suit.values()){
			for (Rank r: Rank.values()){
				Card c = new Card(s,r);
				super.addToPile(c);

			}
		}
		nextCardPos=0;
	}

	//get the index of next card
	private void setNextCardPos(int nextPos){
		nextCardPos = nextPos;
	}

	// flip next card from the top
	Card dealNext() {
		if (nextCardPos > -1 && nextCardPos < super.pileSize()) {
			super.getFromPile(nextCardPos);
			Card card = super.getFromPile(nextCardPos);
			nextCardPos++;
			return card;
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Init new deck shuffle it and print out contents
		Deck testDeck = new Deck();
		testDeck.shufflePile();
		System.out.println("Shuffled Deck: " + testDeck.toString());
		testDeck.setNextCardPos(testDeck.pileSize()-1);
		System.out.println(testDeck.pileSize());

		// test dealOneCard() and check if card was removed properly
		Card oneCard = testDeck.dealNext();
		System.out.println("The card dealt was: " + oneCard);
	}

}
