/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jutter.setcardgame;

import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jutter
 */
public class GameTest {

    private Game instance;

    public GameTest() {
    }

    @Before
    public void setUp() {
        instance = new Game();
    }

    /**
     * Test of play method, of class Game.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPlayZeroUsers() {
        System.out.println("testPlayZeroUsers");
        Map<String, List<Set<Card>>> results = instance.play(0);
    }

    /**
     * Test of play method, of class Game.
     */
    @Test
    public void testPlayOneUser() {
        System.out.println("testPlayOneUser");
        Map<String, List<Set<Card>>> results = instance.play(1);
        validate(1, 27, results);
    }

    /**
     * Test of play method, of class Game.
     */
    @Test
    public void testPlayTwoUsers() {
        System.out.println("testPlayTwoUsers");
        Map<String, List<Set<Card>>> results = instance.play(2);
        validate(2, 27, results);
    }

    /**
     * Test of play method, of class Game.
     */
    @Test
    public void testPlay5Users() {
        System.out.println("testPlay5Users");
        Map<String, List<Set<Card>>> results = instance.play(5);
        validate(5, 27, results);
    }
    
    private void validate(int expectedKeySize, int expectedSetSize, Map<String, List<Set<Card>>> actual) {
        assertEquals(expectedKeySize, actual.keySet().size());
        int cummulativeSize = 0;
        cummulativeSize = actual.values().stream().map((vals) -> vals.size()).reduce(cummulativeSize, Integer::sum);
        assertEquals(expectedSetSize, cummulativeSize);
    }
}
