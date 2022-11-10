package Table;

import Cards.Card;
import fileio.Coordinates;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Table {
    private int nrCardsInHand;
    private ArrayList<ArrayList<Card>> cards;
    private ArrayList<Coordinates> coordinates;

    public Table() {
        this.nrCardsInHand = 0;
        this.cards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ArrayList<Card> rowCards = new ArrayList<>();
            this.cards.add(rowCards);
        }
    }

    public void placeCardOnTable(int playerIdx, Card card) {
        if (playerIdx == 1)
            cards.get(3).add(card);
        else
            cards.get(0).add(card);
    }

    public ArrayList<Coordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    public int getNrCardsInHand() {
        return nrCardsInHand;
    }

    public void setNrCardsInHand(int nrCardsInHand) {
        this.nrCardsInHand = nrCardsInHand;
    }

    public ArrayList<ArrayList<Card>> getCards() {
        return cards;
    }

    public void setCards(ArrayList<ArrayList<Card>> cards) {
        this.cards = cards;
    }
}
