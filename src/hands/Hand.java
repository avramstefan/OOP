package hands;

import cards.Card;
import java.util.ArrayList;

public final class Hand {
    private int nrCardsInHand;
    private ArrayList<Card> cards;

    public Hand() {
        this.nrCardsInHand = 0;
        this.cards = new ArrayList<>();
    }

    /**
     * Method used for removing a card from hand.
     *
     * @param handIdx index of card to be removed from hand
     */
    public void removeCard(final int handIdx) {
        cards.remove(handIdx);
    }

    /**
     * Method that adds a card in hand.
     *
     * @param card to be added in hand
     */
    public void addCard(final Card card) {
        cards.add(card);
    }

    public int getNrCardsInHand() {
        return nrCardsInHand;
    }

    public void setNrCardsInHand(final int nrCardsInHand) {
        this.nrCardsInHand = nrCardsInHand;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(final ArrayList<Card> cards) {
        this.cards = cards;
    }
}
