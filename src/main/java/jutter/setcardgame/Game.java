package jutter.setcardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 *
 * @author jutter
 */
public class Game {

    /**
     *
     */
    public Game() {
    }

    /**
     *
     * @param numberOfPlayers
     * @return
     */
    public Map<String, List<Set<Card>>> play(int numberOfPlayers) {
        return play(numberOfPlayers, numberOfPlayers);
    }

    /**
     * This method will play a game from beginning to end and return a map of
     * Lists of Sets of Cards found by the player id.
     *
     * @param numberOfPlayers the number of players who will be polling and
     * guessing
     * @param threadCount How many worker threads to span the players across
     * @return a map of Lists of Sets of Cards found by the player id.
     */
    protected Map<String, List<Set<Card>>> play(int numberOfPlayers, int threadCount) {
        if (numberOfPlayers < 1) {
            throw new IllegalArgumentException("Number of players must be greater than zero");
        }
        if (threadCount < 1) {
            throw new IllegalArgumentException("Thread count must be greater than zero");
        }
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // initialize the deck of cards
        Deck deck = new Deck();

        // initialize the board
        Board board = new Board(deck);

        // lay out the initial 12 cards
        IntStream.range(0, 4).forEach(i -> {
            board.dealNextSet();
        });

        // init the players
        List<Player> players = new ArrayList<>();
        IntStream.range(0, numberOfPlayers).forEach(i -> {
            players.add(new Player("player " + i, board));
        });

        // randomize who get's first
        Collections.shuffle(players);

        // play the game
        Map<String, List<Set<Card>>> result = new HashMap<>();
        try {

            int foundThisPass = 1;
            // ensure there are either cards left in the deck or no sets were found
            while (!deck.cards.isEmpty() || foundThisPass > 0) {

                // queue the worker threads
                List<Future<Set<Card>>> futures = executor.invokeAll(players);
                foundThisPass = 0;

                // wait for all the workers to have a chance (orer isn't implied in the docs - users call "Set!" when they find it)
                for (Future<Set<Card>> future : futures) {
                    // see what they find
                    Set<Card> s = future.get();
                    if (s != null) {
                        // not null - this prevents an exit
                        foundThisPass++;
                    }
                }

                // deal the next set
                if (!board.dealNextSet()) {
                    // nothing was dealt (deck was empty)
                    if (foundThisPass == 0) {
                        break;
                    }
                }
            }

            executor.awaitTermination(1000, TimeUnit.MILLISECONDS);

            // see who wins - order by descending score
            Collections.sort(players, new Comparator<Player>() {
                @Override
                public int compare(Player o1, Player o2) {
                    int sets = o2.getMatchedSets().size() - o1.getMatchedSets().size();
                    if (sets == 0) {
                        return o2.hashCode() - o1.hashCode();
                    }
                    return sets;
                }
            });

            // print a summary
            System.out.println("Game Over!");
            System.out.println(players.get(0).getName() + " WINS!!");
            AtomicInteger i = new AtomicInteger();
            players.stream().forEach(p -> {
                result.put(p.getName(), p.getMatchedSets());
                System.out.println(p.getName() + " had " + p.getMatchedSets().size() + " matches");
            });

            return result;

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return result;

    }
}
