/**
 * 
 */
package edu.wit.cs.comp2000;

/**
 * @author Tomas Surna
 */

class Player {
	private Hand hand;
  private String name;

  Player(String name) {
    this.name = name;
  }
  
  Hand getHand() {
    return hand;
  }
  
  void setHand(Hand hand) {
    this.hand = hand;
  }
  
  String getName() {
    return name;
  }
}
