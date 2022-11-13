package table;

import cards.Card;
import fileio.Coordinates;

import java.util.ArrayList;

import static game.GameConstants.MAX_NR_OF_CARDS_PER_TABLE_ROW;
import static game.GameConstants.NR_TABLE_ROWS;
import static game.GameConstants.SECOND_PLAYER_IDX;
import static game.GameConstants.TABLE_ROW_INDEX_0;
import static game.GameConstants.TABLE_ROW_INDEX_1;
import static game.GameConstants.TABLE_ROW_INDEX_2;
import static game.GameConstants.TABLE_ROW_INDEX_3;

public final class Table {
    private int nrCardsInHand;
    private ArrayList<ArrayList<Card>> cards;
    private ArrayList<Coordinates> coordinates;

    public Table() {
        this.nrCardsInHand = 0;
        this.cards = new ArrayList<>();
        for (int i = 0; i < NR_TABLE_ROWS; i++) {
            ArrayList<Card> rowCards = new ArrayList<>();
            this.cards.add(rowCards);
        }
    }

    /**
     * Method that takes a card from hand and places it in the right
     * place on the table.
     *
     * @param playerIdx current player
     * @param card card to be placed
     */
    public void placeCardOnTable(final int playerIdx, final Card card) {
        int backRowIdx = TABLE_ROW_INDEX_0;
        int frontRowIdx = TABLE_ROW_INDEX_1;

        if (playerIdx == SECOND_PLAYER_IDX) {
            backRowIdx = TABLE_ROW_INDEX_3;
            frontRowIdx = TABLE_ROW_INDEX_2;
        }

        if (card.getSpecificType().equals("BackMinion")) {
            cards.get(backRowIdx).add(card);
        } else {
            cards.get(frontRowIdx).add(card);
        }

    }

    /**
     * Method that checks if the row is available for placing a card.
     *
     * @param playerIdx current player
     * @param card card to be placed
     * @return true or false
     */
    public boolean checkRowAvailability(final int playerIdx, final Card card) {

        int backRowIdx = TABLE_ROW_INDEX_0;
        int frontRowIdx = TABLE_ROW_INDEX_1;
        if (playerIdx == SECOND_PLAYER_IDX) {
            backRowIdx = TABLE_ROW_INDEX_3;
            frontRowIdx = TABLE_ROW_INDEX_2;
        }

        if (card.getSpecificType().equals("BackMinion")) {
            if (cards.get(backRowIdx).size() == MAX_NR_OF_CARDS_PER_TABLE_ROW) {
                return false;
            }
            return true;
        } else {
            if (cards.get(frontRowIdx).size() == MAX_NR_OF_CARDS_PER_TABLE_ROW) {
                return false;
            }
            return true;
        }
    }

    public ArrayList<Coordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(final ArrayList<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    public int getNrCardsInHand() {
        return nrCardsInHand;
    }

    public void setNrCardsInHand(final int nrCardsInHand) {
        this.nrCardsInHand = nrCardsInHand;
    }

    public ArrayList<ArrayList<Card>> getCards() {
        return cards;
    }

    public void setCards(final ArrayList<ArrayList<Card>> cards) {
        this.cards = cards;
    }
}
