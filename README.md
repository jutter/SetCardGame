# SetCardGame

Set is a card game where a group of players try to identify a "set" of cards from those placed face-up on a table.
Each card has an image on it with 4 orthogonal attributes:
* Color (red, green, or purple)
* Shape (diamond, squiggle, or oval)
* Shading (solid, empty, or striped)
* Number (one, two, or three)
  

Three cards are part of a set if, for each property, the values are all the same or all different.
This project requires Java 8 and Maven.  To build the libraries, run:

    mvn clean install
    
The classes involved are related to the domain objects that are part of the game itself:
* Game
* Player
* Board
* Deck
* Card

The Player class contains two key methods:
* a method that takes three cards and determines whether the three cards make a set
* a method that given a "board" of cards will either find a set or dermine if there are no sets on the table

The Game class contains a method that will play an entire game of Set from beginning to end, and return a list of each valid sets you removed from the board.  It also allows you to choose the number of players.

The project uses junit to test the play method using various numbers of users, validating the total number of sets found per game.
