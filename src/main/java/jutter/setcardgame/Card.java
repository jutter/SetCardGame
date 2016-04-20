package jutter.setcardgame;

import java.util.Objects;

/**
 * A card object for the Set card game that consists of 4 orthogonal attributes
 * of color, shape, shading, and number.
 *
 * @author jutter
 */
public class Card implements Comparable<Card> {

    /**
     * The possible colors of the card
     */
    public enum Color {
        red, green, purple
    }

    /**
     * The possible shapes of the card
     */
    public enum Shape {
        diamond, squiggle, oval
    }

    /**
     * The possible shadings of the card
     */
    public enum Shading {
        solid, empty, striped
    }

    /**
     * The possible numbers of the card
     */
    public enum Number {
        one, two, three
    }

    /**
     * The color of this card
     */
    public final Color color;

    /**
     * The shape of this card
     */
    public final Shape shape;

    /**
     * The shading of this card
     */
    public final Shading shading;

    /**
     * The number of this card
     */
    public final Number number;

    /**
     * The default constructor of the card that initializes all values.
     *
     * @param color The color of this card
     * @param shape The shape of this card
     * @param shading The shading of this card
     * @param number The number of this card
     */
    public Card(Color color, Shape shape, Shading shading, Number number) {
        this.color = color;
        this.shape = shape;
        this.shading = shading;
        this.number = number;
    }

    @Override
    public int compareTo(Card o) {
        if (o == null) {
            return -1;
        }
        // sort by number, color, shading, shape
        int result = number.compareTo(o.number);
        if (result == 0) {
            result = color.compareTo(o.color);
        }
        if (result == 0) {
            result = shading.compareTo(o.shading);
        }
        if (result == 0) {
            result = shape.compareTo(o.shape);
        }
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.color);
        hash = 37 * hash + Objects.hashCode(this.shape);
        hash = 37 * hash + Objects.hashCode(this.shading);
        hash = 37 * hash + Objects.hashCode(this.number);
        return hash;
    }

    @Override
    public boolean equals(Object obj
    ) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.color != other.color) {
            return false;
        }
        if (this.shape != other.shape) {
            return false;
        }
        if (this.shading != other.shading) {
            return false;
        }
        return this.number == other.number;
    }

    @Override
    public String toString() {
        // largest possible size
        StringBuilder str = new StringBuilder(28);
        str.append(this.number)
                .append(' ')
                .append(this.color)
                .append(' ')
                .append(this.shading)
                .append(' ')
                .append(this.shape);
        return str.toString();
    }

}
