package jutter.setcardgame;

import java.util.LinkedList;

/**
 * Represents the playing board shared by the players
 *
 * @author jutter
 */
public class Board {

    private final Deck deck;
    private final LinkedList<Card> turnedCards;

    /**
     * Initializes a new board from a deck of turnedCards
     *
     * @param deck
     */
    public Board(Deck deck) {
        this.deck = deck;
        this.turnedCards = new LinkedList<>();
    }

    /**
     * Deals (turns) the next set of 3 cards from the top of the deck or does
     * nothing if no cards remain
     *
     * @return true if cards were dealt, false if the deck was empty
     */
    public final boolean dealNextSet() {
        synchronized (this) {
            for (int i = 0; i < 3; i++) {
                Card nextCard = deck.draw();
                if (nextCard == null) {
                    return false;
                } else {
                    turnedCards.addLast(nextCard);
                }
            }
            System.out.println("dealt cards: " + turnedCards.size());
            return true;
        }
    }

    /**
     * Returns the current set of turned cards on the board
     *
     * @return the current set of turned cards on the board
     */
    public LinkedList<Card> getTurnedCards() {
        return turnedCards;
    }

}
