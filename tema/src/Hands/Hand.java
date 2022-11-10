package Hands;

import Cards.Card;

import java.util.ArrayList;

public class Hand {
    private int nrCardsInHand;
    private ArrayList<Card> cards;

    public Hand() {
        this.nrCardsInHand = 0;
        this.cards = new ArrayList<>();
    }

    public Card placeFromHandToTable(int handIdx) {
        if (handIdx < cards.size()) {
            Card cardToPlace = cards.get(handIdx);
            cards.remove(handIdx);
            return cardToPlace;
        }
        return null;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getNrCardsInHand() {
        return nrCardsInHand;
    }

    public void setNrCardsInHand(int nrCardsInHand) {
        this.nrCardsInHand = nrCardsInHand;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
