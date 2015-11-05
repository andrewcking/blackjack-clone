package blackjack;

import acm.util.RandomGenerator;

public class Deck {

    private Card[] cards;
    private int top = 0;
    private RandomGenerator rand = RandomGenerator.getInstance();

    public Deck() {
        cards = new Card[52];
        int index = 0;
        for (int suit = 0; suit < Card.SUIT.length; ++suit) {
            for (int face = 0; face < Card.FACE.length; ++face) {
                Card c = new Card(suit, face);
                cards[index++] = c;
            }
        }
    }

    public void shuffle() {
        for (int index = 0; index < cards.length; ++index) {
            int newPos = rand.nextInt(52);
            Card temp = cards[index];
            cards[index] = cards[newPos];
            cards[newPos] = temp;
        }
        top = 0;
    }

    public Card deal() {
        if (top >= cards.length) {
            shuffle();
        }
        return cards[top++];
    }

    public String toString() {
        StringBuilder sbuild = new StringBuilder();
        sbuild.append("[");
        for (int i = 0; i < cards.length; ++i) {
            sbuild.append(cards[i]);
            if (i < cards.length - 1) {
                sbuild.append(",");
            }
        }
        sbuild.append("]");
        return sbuild.toString();
    }

}
