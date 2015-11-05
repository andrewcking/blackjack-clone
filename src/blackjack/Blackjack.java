package blackjack;

import acm.program.GraphicsProgram;
import acm.graphics.GLabel;
import java.awt.Color;
import svu.csc213.Dialog;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

/**
 *
 * @author Adrix (Andrew King)
 */
public class Blackjack extends GraphicsProgram {

    @Override
    public void init() {

        setBackground(new Color(64, 180, 64));

        wagerButton = new JButton("Wager");
        add(wagerButton, SOUTH);
        playButton = new JButton("Play");
        add(playButton, SOUTH);
        hitButton = new JButton("Hit");
        add(hitButton, SOUTH);
        standButton = new JButton("Stand");
        add(standButton, SOUTH);
        quitButton = new JButton("Quit");
        add(quitButton, SOUTH);
        addActionListeners();
        deck.shuffle();

        hitButton.setEnabled(false);
        playButton.setEnabled(true);
        quitButton.setEnabled(true);
        wagerButton.setEnabled(true);
        standButton.setEnabled(false);

        for (int m = 0; m <= 19; m++) {
            cards[m] = new Card(0, 0);
            cards[m].setVisible(false);
        }
        for (int m = 0; m <= 19; m++) {
            playerHand[m] = 0;
            dealerHand[m] = 0;
        }

        balanceLabel = new GLabel("The Current Balance is $" + balance + ".");
        balanceLabel.setFont("Times-bold-25");
        add(balanceLabel, 30, 255);
        wagerLabel = new GLabel("Your Wager is $" + wager + ".");
        wagerLabel.setFont("Times-bold-25");
        add(wagerLabel, 430, 255);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Wager":
                wager();
                break;
            case "Play":
                playHand();
                break;
            case "Hit":
                hit();
                break;
            case "Stand":
                stand();
                break;
            case "Quit":
                quit();
                break;
        }
    }

    public static void main(String[] args) {
        new Blackjack().start();
    }

    private void wager() {
        boolean validWager = false;
        while (!validWager) {
            wager = Dialog.getInteger(this, "Enter Wager: ");
            if (wager > 0 && wager <= balance) {
                validWager = true;
            }
        }
        wagerLabel.setLabel("Your Wager is $" + wager + ".");
    }

    private void playHand() {
        if (balance < wager) {
            Dialog.showMessage(this, "Please Re-evaluate your Wager");
        } else {
            count = 0;
            dealCard(count, 0);
            dealCard(count, 0);
            count++;
            dealCard(10, 1);
            cards[10].setFaceUp(false);
            dealCard(11, 1);
            standButton.setEnabled(true);
            hitButton.setEnabled(true);
            playButton.setEnabled(false);
            quitButton.setEnabled(false);
            wagerButton.setEnabled(false);
            this.update(this.getGraphics());
        }
    }

    private void hit() {
        dealCard(count, 0);
        if (getTotal(0) > 21) {
            Dialog.showMessage(this, "Ooo... Busted");
            balance -= wager;
            balanceLabel.setLabel("The Current Balance is $" + balance + ".");
            clear();
            standButton.setEnabled(false);
            hitButton.setEnabled(false);
            playButton.setEnabled(true);
            quitButton.setEnabled(true);
            wagerButton.setEnabled(true);
            count = 0;
            this.update(this.getGraphics());
        }
    }

    private void stand() {
        cards[10].setFaceUp(true);
        for (int r = 0; getTotal(1) < 16; r++) {
            dealCard(12 + r, 1);
        }
        for (int m = 0; m <= 19; m++) {
            if (playerHand[m] == 1 && getTotal(0) + 10 <= 21) {
                playerHand[m] += 10;
            }
        }
        for (int m = 0; m <= 19; m++) {
            if (dealerHand[m] == 1 && getTotal(0) + 10 <= 21) {
                dealerHand[m] += 10;
            }
        }
        if (getTotal(0) > getTotal(1) || getTotal(1) >= 21) {
            Dialog.showMessage(this, "W00t! You won!");
            balance += wager;
            balanceLabel.setLabel("The Current Balance is $" + balance + ".");
        } else {
            Dialog.showMessage(this, "All your money are belong to us");
            balance -= wager;
            balanceLabel.setLabel("The Current Balance is $" + balance + ".");
        }
        clear();
        standButton.setEnabled(false);
        hitButton.setEnabled(false);
        playButton.setEnabled(true);
        quitButton.setEnabled(true);
        wagerButton.setEnabled(true);
        count = 0;
        this.update(this.getGraphics());
    }

    private void quit() {
        System.exit(0);
    }

    private void dealCard(int t, int player) {
        cards[t] = deck.deal();
        if (player == 0) {
            int cardValue = cards[t].getFace() + 2;
            if (cardValue > 9 && cardValue != 14) {
                cardValue = 10;
            }
            if (cardValue == 14) {
                cardValue = 1;
            }
            playerHand[t] = cardValue;
            add(cards[t], 250 + (35 * t), 290);
            cards[t].setVisible(true);
            count++;
        } else {
            int cardValue = cards[t].getFace() + 2;
            if (cardValue > 9 && cardValue != 14) {
                cardValue = 10;
            }
            if (cardValue == 14) {
                cardValue = 1;
            }
            dealerHand[t] = cardValue;
            add(cards[t], 250 + (35 * (t - 10)), 45);
            cards[t].setVisible(true);
        }
    }

    private void clear() {
        for (int index = 0; index <= 19; index++) {
            remove(cards[index]);
        }
        for (int m = 0; m <= 19; m++) {
            playerHand[m] = 0;
            dealerHand[m] = 0;
        }
        if (balance == 0) {
            Dialog.showMessage(this, "You ran out of money =( Game over");
            quit();
        }
    }

    private int getTotal(int person) {
        int total = 0;
        if (person == 0) {
            for (int i : playerHand) {
                total += i;
            }
        } else {
            for (int i : dealerHand) {
                total += i;
            }
        }
        return total;
    }

    private JButton wagerButton;
    private JButton playButton;
    private JButton hitButton;
    private JButton standButton;
    private JButton quitButton;
    private int balance = 2000;
    private int wager = 100;
    private Card[] cards = new Card[20];
    private Deck deck = new Deck();
    private GLabel balanceLabel;
    private GLabel wagerLabel;
    private int[] playerHand = new int[20];
    private int[] dealerHand = new int[20];
    private int count = 0;

}
