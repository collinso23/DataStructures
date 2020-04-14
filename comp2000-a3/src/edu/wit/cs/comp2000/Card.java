/**
 * 
 */
package edu.wit.cs.comp2000;

/**
 * @author Orion Collins
 *
 */

public class Card implements Comparable<Card> {

	/**
	 * 
	 */
	private Rank cardRank;
	private Suit cardSuit;

	//Card constructor
	Card(Suit cSuit, Rank cRank) {
		cardRank = cRank;
		cardSuit = cSuit;
	}

	//displays graphic from Suit and Rank classes
	public String toString() {
		return cardRank.getGraphic() + cardSuit.getGraphic();
	}

	/*
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */

	// compare two cards objects for sorting
	//Integer.compare returns (-1,0,1) depending on input (<,=,>)

    @Override
	public int compareTo(Card o) {
		if(suitPriority()==(o.suitPriority())) {
			return Integer.compare(getCardPoints(), o.getCardPoints());
		}
		if (suitPriority()<o.suitPriority()){
			return Integer.compare(getCardPoints(), o.getCardPoints());
		}
		return Integer.compare(getCardPoints(),o.getCardPoints());
	}

	// for seeing if card objects are the same
	boolean cardEquals(Card o){
		return (getCardPoints() == o.getCardPoints() && getCardRank().equals(o.getCardRank()));
	}

	//getter methods for card parameters
	private String getCardSuit(){
	    return cardSuit.getDisplayName();
    }

    private int suitPriority(){return cardSuit.getPriority();}

	private String getCardRank(){
	    return cardRank.getDisplayName();
    }

    //ACE can represent 11 or 1, or we can opt out of this rule for our implementation of BlackJack
	int  getCardPoints(){
	    return cardRank.getPoints();
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	    // Create new test Card
		Card testCard = new Card(Suit.CLUBS,Rank.ACE);
        Card testCard2 = new Card(Suit.DIAMONDS,Rank.TWO);
        Card testCard3 = new Card(Suit.HEARTS,Rank.FOUR);

        //display cards graphic
        System.out.println(testCard.toString());

        //points
        System.out.println(testCard2.getCardPoints());
        //rank
        System.out.println(testCard3.getCardRank());

        //card suit
        System.out.printf("The suit of %s is %s\n",testCard2.toString(),testCard2.getCardSuit());

        //compare cards
        System.out.printf("Which is bigger %s or %s : %s\n",
                testCard2.getCardRank(),testCard3.getCardRank(),testCard2.compareTo(testCard3));

        System.out.println(testCard.toString());
	}

}
