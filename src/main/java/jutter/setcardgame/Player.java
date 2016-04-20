package jutter.setcardgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Represents a 'player' in the Set card game.
 *
 * It implements the threading Callable interface and implements the player's
 * decision making logic to apply during the game. This includes the logic for:
 * * how efficiently it scans the board for any sets * how it determines if
 * three cards comprise a set
 *
 *
 * @author jutter
 */
public class Player implements Callable<Set<Card>> {

    private final String name;
    private final Board board;
    private final List<Set<Card>> matchedSets = new ArrayList<>();

    /**
     * Creates a new player in participating in the game on the provided board
     *
     * @param name the name of the player (does not need to be unique)
     * @param board the board this player is playing on
     */
    public Player(String name, Board board) {
        if (name == null || name.trim().equals("")) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        this.name = name;
        this.board = board;
    }

    /**
     * Returns the name of this player
     *
     * @return the name of this player
     */
    public String getName() {
        return name;
    }

    /**
     * The method the player uses to scan the board and 'find' the first set of
     * three cards comprising a set. The cards can be in any order or position.
     *
     * @param board The board the player is scanning
     *
     * @return the first set of matching cards found or null if no matches are
     * present
     */
    protected Set<Card> findSet(Board board) {
        List<Card> cards = new ArrayList(board.getTurnedCards());
        Collections.sort(cards);
        int max = cards.size() - 1;
        Card c1, c2, c3;
        for (int i1 = 0; i1 < max; i1++) {
            c1 = cards.get(i1);
            for (int i3 = max; i3 > i1 + 1; i3--) {
                c3 = cards.get(i3);
                for (int i2 = i3 - 1; i2 > i1 && i2 < i3; i2--) {
                    c2 = cards.get(i2);
                    if (isSet(c1, c2, c3)) {
                        return new HashSet(Arrays.asList(new Card[]{c1, c2, c3}));
                    }
                }
            }
        }
        return null;
    }

    /**
     * Given three cards, it determines whether or not the three cards are all
     * part of a set. The result is true if for each property, the values are
     * either all the same or all different..
     *
     * @param card1 the first card to evaluate in the set
     * @param card2 the second card to evaluate in the set
     * @param card3 the third card to evaluate in the set
     * @return
     */
    protected boolean isSet(Card card1, Card card2, Card card3) {
        if (sameOrDiff(card1.number, card2.number, card3.number)) {
            if (sameOrDiff(card1.color, card2.color, card3.color)) {
                if (sameOrDiff(card1.shading, card2.shading, card3.shading)) {
                    if (sameOrDiff(card1.shape, card2.shape, card3.shape)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Given three enumerations, it determines whether all instance are the same
     * or all different by using bitwise evaluation via the EnumSet class.
     *
     * @param e1 the first enumeration for comparison
     * @param e2 the second enumeration for comparison
     * @param e3 the third enumeration for comparison
     * @return true if either all enums are the same or all are different
     */
    protected boolean sameOrDiff(Enum e1, Enum e2, Enum e3) {
        // do a bitwise set of enums then the complement should be zero if all are diff or 2 if all are same
        return EnumSet.complementOf(EnumSet.of(e1, e2, e3)).size() != 1;
    }

    /**
     *
     * @return
     */
    public List<Set<Card>> getMatchedSets() {
        return matchedSets;
    }

    /**
     * Runnable method that determines what this player does when he gets cpu
     * time. Ideally, he will attempt to scan the board and remove a set of
     * cards before any other players but ultimately each player is responsible
     * for their own actions.
     *
     * @throws java.lang.Exception if anything fails while the player is
     * attempting to perform an action
     */
    @Override
    public Set<Card> call() throws Exception {
        // the container for any cards found
        Set<Card> cardSet;

        // TODO: ideally, all users should be able to scan the board at the same time
        // but that would allow collision of cards attempting to be removed as multiple
        // players could identify the same card in different sets at the same time .
        // this synchronized block ensures that collision doesn't happen
        synchronized (board) {
            cardSet = this.findSet(board);

            // if cards are found, this player takes over the board and will 
            // remove any cards found in his set
            if (cardSet != null) {
                // cards were found, the user yells "Set!" and takes the cards
                // adding to their score
                System.out.println(getName() + ":  \"SET!\" - " + cardSet);
                this.matchedSets.add(cardSet);
                board.getTurnedCards().removeAll(cardSet);
            }
        }

        // return any cards found
        return cardSet;
    }

}
