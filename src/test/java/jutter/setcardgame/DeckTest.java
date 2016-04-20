/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jutter.setcardgame;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jutter
 */
public class DeckTest {

    public DeckTest() {
    }
    /**
     * Test of draw method, of class Deck.
     */
    @Test
    public void testDraw() {
        System.out.println("draw");
        Deck deck = new Deck();
        assertEquals(81, deck.cards.size());
        Card card = deck.draw();
        assertNotNull(card);
        assertEquals(80, deck.cards.size());
    }

}
