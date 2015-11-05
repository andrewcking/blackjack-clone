package blackjack;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;
import java.awt.Color;

public class Card extends GCompound {

    private int suit;
    private int face;
    private GImage image;
    private GRect cardBack;
    private boolean faceUp;

    public Card(int suit, int face) {
        this.suit = suit;
        this.face = face;
        String imagePath = "cardgifs/" + SUIT[suit].substring(0, 1).toLowerCase() + (face + 2) + ".gif";
        image = new GImage(imagePath);
        cardBack = new GRect(image.getWidth() + 2, image.getHeight() + 2);
        cardBack.setFilled(true);
        cardBack.setFillColor(Color.blue);
        add(cardBack);
        add(image, 1, 1);
        setFaceUp(true);
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public int getFace() {
        return face;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
        image.setVisible(faceUp);
    }

    @Override
    public String toString() {
        return FACE[face] + " of " + SUIT[suit];
    }

    public static String[] SUIT = {"Clubs", "Spades", "Diamonds", "Hearts"};
    public static String[] FACE = {"Two", "Three", "Four", "Five", "Six",
        "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
}
