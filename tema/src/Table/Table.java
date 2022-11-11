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
        int backRowIdx = 0;
        int frontRowIdx = 1;

        if (playerIdx == 1) {
            backRowIdx = 3;
            frontRowIdx = 2;
        }

        if (card.getSpecificType().equals("BackMinion"))
            cards.get(backRowIdx).add(card);
        else
            cards.get(frontRowIdx).add(card);

    }

    public boolean checkRowAvailability(int playerIdx, Card card) {
        int backRowIdx = 0;
        int frontRowIdx = 1;

        if (playerIdx == 1) {
            backRowIdx = 3;
            frontRowIdx = 2;
        }

        if (card.getSpecificType().equals("BackMinion")) {
            if (cards.get(backRowIdx).size() == 5)
                return false;
            return true;
        } else {
            if (cards.get(frontRowIdx).size() == 5)
                return false;
            return true;
        }
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
