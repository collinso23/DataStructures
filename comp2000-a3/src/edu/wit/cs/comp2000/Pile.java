package edu.wit.cs.comp2000;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Orion Collins
 */
public class Pile {
	private List<Card> cards;
	private int numberOfEntries;
	private int capacity = 52;

	//constructor
	Pile() {
		cards = new ArrayList<>(capacity);
	}

	private void setCapacity(int cap){
		capacity = cap;
	}

	//size() method
	int pileSize(){
		return numberOfEntries;
	}

	// get()
	Card getFromPile(int cardIndex){
		return cards.get(cardIndex);
	}
	
	public static void main(String[] args) {
		Pile testPile = new Pile();
		//add all combinations of 52 cards in deck to pile.
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values()) {
				Card c = new Card(s, r);
				testPile.addToPile(c);
			}
		}
		
		//Print out graphic for deck and size
		System.out.printf("Size of deck: %s\n%s\n",
						testPile.pileSize(), testPile.toString());
		
		//Shuffle deck and print
		testPile.shufflePile();
		System.out.println(testPile.toString());
		
		//Sort pile cards 10+FaceCards will be sorted in suit order as they all share same point value
		testPile.sortPile();
		System.out.println(testPile.toString());
		
		
		//remove all cards from the deck and print out count afterwords
		testPile.resetPile();

		System.out.println("Size of deck after clear(): " + testPile.pileSize()
				+"\nHere is the pile post-clear(): "+ testPile.toString());
		
		System.out.println("Testing search Function\n" + "_____________________");
		//search for testCard in testPile
		//init new cards and pile
		Pile testPile2 = new Pile();
		Card testCard = new Card(Suit.DIAMONDS, Rank.TEN);
		Card testCard2 = new Card(Suit.CLUBS, Rank.ACE);
		Card testCard3 = new Card(Suit.HEARTS, Rank.KING);
		Card testCard4 = new Card(Suit.SPADES, Rank.NINE);
		Card testCard5 = new Card(Suit.SPADES, Rank.SEVEN);
		testPile2.addToPile(testCard2);
		testPile2.addToPile(testCard3);
		testPile2.addToPile(testCard4);
		testPile2.addToPile(testCard5);
		
		//run search method
		System.out.println("testPile2: " + testPile2.toString());
		Card result = testPile2.searchPile(testCard);
		System.out.println("Lets search for testCard: " + testCard.toString() +
						"\ntestCard was found here it is: " + result);
		
		Card result2 = testPile2.searchPile(testCard2);
		System.out.println("Lets search for testCard2: " + testCard2.toString() +
						"\ntestCard was found here it is: " + result2);
		
		//Split the pile into a Pile[] that contains both the sub piles
		System.out.println(Arrays.toString(testPile2.splitPile()));
	}

	//remove(T)
	void removeFromPile(Card pileEntry){
		cards.remove(pileEntry);
		numberOfEntries--;
	}

	//Shuffle the pile
	void shufflePile(){
		Collections.shuffle(cards);
	}

	// Sort the pile in ascending order based off value and suit priority
	private void sortPile(){
		cards.sort(Card::compareTo);
		//cards.sort(Card::compareTo);
	}
	//search for card in pile and return it if found otherwise return null
	private Card searchPile(Card t){
		for (Card card : cards) {
			if (card.cardEquals(t)) {
				//t.compareTo(p.getFromPile(i))
				return t;
			}
		}
		return null;
		//return Collections.binarySearch(p,t);
	}
	private void resetPile(){
		cards.clear();
		numberOfEntries = 0;
	}

	//print out displayName() from Card obj
	public String toString(){
		StringBuilder result = new StringBuilder();

		for (Card c: cards) {
			result.append(c.toString()).append(" ");
		}

		return result.toString();
	}
	private Pile[] splitPile(){
		int size = cards.size();
		List<Card> first = new ArrayList<>(cards.subList(0,(size)/2));
		List<Card> second = new ArrayList<>(cards.subList((size)/2,size));
		Pile p1 = new Pile();
		Pile p2 = new Pile();
		for (Card f: first)
			p1.addToPile(f);
		for (Card s: second)
			p2.addToPile(s);
		//System.out.println(p1.toString());
		//System.out.println(p2.toString());
		return new Pile[] {p1,p2};
	}

	//helper iterator to assist with accessing elements in pile
	Iterator<Card> pileIterator(){ return cards.iterator(); }
	
	// add()
	void addToPile(Card pileEntry) {
		cards.add(pileEntry);
		numberOfEntries++;
	}

}
