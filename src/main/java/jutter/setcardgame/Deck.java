package jutter.setcardgame;

import java.util.EnumSet;
import java.util.LinkedList;

/**
 * Represents the deck of cards used Set card game 
 * @author jutter
 */
public class Deck {

    /**
     * Stores all cards of the deck in order
     */
    public final LinkedList<Card> cards;

    /**
     * The default constructor that initiates all possible variants of the Card 
     * object
     */
    public Deck() {
        cards = new LinkedList<>();
        
        // init all possible values
        // this uses the EnumSet to iterate over all possible values of the Card Enums
        // and java8 function calls to stream through the iterations
        EnumSet.allOf(Card.Color.class).stream().forEach((color) -> {
            EnumSet.allOf(Card.Number.class).stream().forEach((number) -> {
                EnumSet.allOf(Card.Shading.class).stream().forEach((shading) -> {
                    EnumSet.allOf(Card.Shape.class).stream().forEach((shape) -> {
                        // use add last so it doesn't scan for previous existence
                        cards.addLast(new Card(color, shape, shading, number));
                    });
                });
            });
        });
    }

    /**
     * Takes the next card off the top of the deck for turning over onto the board
     * @return
     */
    public Card draw() {
        return cards.poll();
    }

}
