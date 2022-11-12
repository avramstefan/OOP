package fileio;

import java.util.ArrayList;
import java.util.Random;
import java.util.*;

public final class DecksInput {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<CardInput>> decks;

    public DecksInput() {
    }

    public void shuffleDeck(int deckIdx, int seed) {
        Random randomObject = new Random();
        randomObject.setSeed(seed);

        Collections.shuffle(decks.get(deckIdx), randomObject);
    }

    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    public int getNrDecks() {
        return nrDecks;
    }

    public void setNrDecks(final int nrDecks) {
        this.nrDecks = nrDecks;
    }

    public ArrayList<ArrayList<CardInput>> getDecks() {
        return decks;
    }

    public void setDecks(final ArrayList<ArrayList<CardInput>> decks) {
        this.decks = decks;
    }

    @Override
    public String toString() {
        return "InfoInput{"
                + "nr_cards_in_deck="
                + nrCardsInDeck
                +  ", nr_decks="
                + nrDecks
                + ", decks="
                + decks
                + '}';
    }
}
