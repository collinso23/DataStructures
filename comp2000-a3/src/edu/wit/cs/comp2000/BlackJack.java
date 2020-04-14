package edu.wit.cs.comp2000;

import java.util.Scanner;

/**
 * @author Tomas Surna
 */
public class BlackJack {
  
  private static boolean isRoundOver;
  private static Player dealer;
  private static Player player;
  private static Deck deck;
  
  public static void main(String[] args) {
    /*
     Blackjack Game:
     Simple version: Player can Hit or Stay, when they stay then it's dealers choice to do the same.
     From there who ever has the best score wins

     1. Draw cards for Player and Dealer, 2 each
     2. Ask the Player if he wants to
    */
    
    // INITIALIZE Stuff
    System.out.print("Input: ");
    Scanner scanner = new Scanner(System.in);
    String input;
    isRoundOver = true;
    
    dealer = new Player("Dealer");
    player = new Player("Player");
    
    // Start game
    System.out.printf("%nWelcome to Simple BlackJack, you are Player 1.");
    // Print out the command options
    System.out.printf(
            "%nCommands: %n\t'quit' - Quit %n\t'stay' - Stay %n\t'hit' - Hit %n\t'info' - Info %n\t'begin' - Begin%n%n");
    
    while (true) {
      System.out.printf("%nInput: ");
      input = scanner.next().toLowerCase().trim();
      
      switch (input) {
        case "quit": {
          System.exit(0);
        }
        case "stay": {
          if (!isRoundOver) {
            stay();
          }
          break;
        }
        case "hit": {
          if (!isRoundOver) {
            hit(player);
          }
          break;
        }
        case "begin": {
          if (isRoundOver) {
            begin();
            break;
          }
        }
        case "info": {
          info();
          break;
        }
        default: {
          defualt();
          break;
        }
      }
    }
  }
  
  /**
   * Hit, the player object has chosen to add a card to their pile
   *
   * @param player
   */
  private static void hit(Player player) {
    System.out.printf("%n\tAdded card to hand!");
    player.getHand().addToPile(deck.dealNext());//true
    
    printBlackJackHand(player);
    processHand(player);
  }
  
  /**
   * Initializes the deck and gives the player and dealer cards.
   */
  private static void begin() {
    // Deal 2 cards to the players
    deck = new Deck();
    deck.shufflePile();
    
    player.setHand(new Hand().addToHand(deck.dealNext()).addToHand(deck.dealNext()));
    printBlackJackHand(player);
    
    dealer.setHand(new Hand().addToHand(deck.dealNext()).addToHand(deck.dealNext()));
    
    isRoundOver = false;
  }
  
  /**
   * Game info
   */
  private static void info() {
    // Print out the command options
    System.out.printf(
            "%nCommands: %n\t'quit' - Quit the game "
                    + "%n\t'stay' - Stay, end your turn/round"
                    + "%n\t'hit' - Hit, take another card from deck"
                    + "%n\t'begin' - Begin, start a round of BlackJack");
    System.out.printf(
            "%nIn Simple BlackJack the only possible options are to 'hit' or 'stay'. Once you chose to 'stay' the dealer will decide their actions. Aces are considered as 1 point, instead of the normal 1 or 11 points. %n\tIf you get 21 in points from your hand, you win. %n\tIf you have more points then the dealer, you win. %n\tIf you go over 21, you lose. %n\tIf the dealer has more points then you, you lose. ");
  }
  
  /**
   * Player decides to stay, therefore dealer takes over and performs their hit or stay actions
   */
  private static void stay() {
    // Player ends round, time for dealer to play
    printBlackJackHand(dealer);
    
    // Use a random number generator to decide if the dealer should hit, or stay
    while (!isRoundOver) {
      // dealer an see players hand, so while the dealer's hand is less then the players lets add a
      // card
      if (dealer.getHand().getHandTotal() < player.getHand().getHandTotal()) {
        hit(dealer);
      } else {
        System.out.printf("%n\tDealer has decided to stay!");
        break;
      }
    }
  }
  
  /**
   * default is misspelled on purpose Default option for when player puts in an invalid command
   */
  private static void defualt() {
    System.out.printf("%nInvalid Command. Type 'info' for list of commands.");
  }
  
  /**
   * Print out the player's hand and score
   */
  private static void printBlackJackHand(Player player) {
    System.out.printf(
            "%n\t%s's hand: %s, Points: %s",
            player.getName(), player.getHand().toString(), player.getHand().getHandTotal());
  }
  
  /**
   * Process the hand of the player to see if they won, lose, or neither
   *
   * @param player either the player or the dealer
   */
  private static void processHand(Player player) {
    Hand hand = player.getHand();
    /*
    Possible outcomes:

    if: value == 21: Player Wins
    if: value > 21: Player loses
    if: value < 21: Nothing, round continues
     */
    
    if (hand.getHandTotal() == 21) {
      // Player wins
      isRoundOver = true;
      System.out.printf("%n\t%s Wins! with a score of %s", player.getName(), hand.getHandTotal());
    } else if (hand.getHandTotal() > 21) {
      // Player loses
      isRoundOver = true;
      System.out.printf(
              "%n\tBust! %s loses with a score of %s", player.getName(), hand.getHandTotal());
    }
    
    // else nothing happens
  }
}
