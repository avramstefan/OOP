package Decks;

import Cards.Card;
import Cards.Environment.*;
import Cards.Minion.*;
import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;
import java.util.Objects;

public class Decks {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<Card>> decks;

    public Decks(DecksInput inputDecks) {
        this.nrCardsInDeck = inputDecks.getNrCardsInDeck();
        this.nrDecks = inputDecks.getNrDecks();
        this.decks = new ArrayList<>();

        for (ArrayList<CardInput> inputDeck : inputDecks.getDecks()) {
            ArrayList<Card> newDeck = new ArrayList<>();

            for (CardInput card : inputDeck) {
                Card newCard = assignClassToCard(card);
                newDeck.add(newCard);
            }
            decks.add(newDeck);
        }
    }

    private Card assignClassToCard(CardInput card) {
        if (card.getName().equals("Berserker"))
            return new Berserker(card);
        else if (card.getName().equals("Disciple"))
            return new Disciple(card);
        else if (card.getName().equals("Goliath"))
            return new Goliath(card);
        else if (card.getName().equals("Miraj"))
            return new Miraj(card);
        else if (card.getName().equals("Sentinel"))
            return new Sentinel(card);
        else if (card.getName().equals("The Cursed One"))
            return new TheCursedOne(card);
        else if (card.getName().equals("The Ripper"))
            return new TheRipper(card);
        else if (card.getName().equals("Warden"))
            return new Warden(card);
        else if (card.getName().equals("Firestorm"))
            return new Firestorm(card);
        else if (card.getName().equals("Winterfell"))
            return new Winterfell(card);
        else if (card.getName().equals("Heart Hound"))
            return new HeartHound(card);
        return null;
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

    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    public void setDecks(final ArrayList<ArrayList<Card>> decks) {
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
