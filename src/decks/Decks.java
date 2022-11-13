package decks;

import cards.Card;
import cards.Environment.Firestorm;
import cards.Environment.Winterfell;
import cards.Environment.HeartHound;
import cards.Hero.EmpressThorina;
import cards.Hero.GeneralKocioraw;
import cards.Hero.KingMudface;
import cards.Hero.LordRoyce;
import cards.Minion.Berserker;
import cards.Minion.Disciple;
import cards.Minion.Goliath;
import cards.Minion.Miraj;
import cards.Minion.Sentinel;
import cards.Minion.TheCursedOne;
import cards.Minion.TheRipper;
import cards.Minion.Warden;
import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

public final class Decks {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<Card>> decks;

    public Decks(final DecksInput inputDecks) {
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

    /**
     * Method used to parse cards and assign their classes
     * based on their names.
     *
     * @param card to be parsed from CardInput class to Card Clas
     * @return assigned card
     */
    public static Card assignClassToCard(final CardInput card) {
        if (card.getName().equals("Berserker")) {
            return new Berserker(card);
        } else if (card.getName().equals("Disciple")) {
            return new Disciple(card);
        } else if (card.getName().equals("Goliath")) {
            return new Goliath(card);
        } else if (card.getName().equals("Miraj")) {
            return new Miraj(card);
        } else if (card.getName().equals("Sentinel")) {
            return new Sentinel(card);
        } else if (card.getName().equals("The Cursed One")) {
            return new TheCursedOne(card);
        } else if (card.getName().equals("The Ripper")) {
            return new TheRipper(card);
        } else if (card.getName().equals("Warden")) {
            return new Warden(card);
        } else if (card.getName().equals("Firestorm")) {
            return new Firestorm(card);
        } else if (card.getName().equals("Winterfell")) {
            return new Winterfell(card);
        } else if (card.getName().equals("Heart Hound")) {
            return new HeartHound(card);
        } else if (card.getName().equals("General Kocioraw")) {
            return new GeneralKocioraw(card);
        } else if (card.getName().equals("Lord Royce")) {
            return new LordRoyce(card);
        } else if (card.getName().equals("King Mudface")) {
            return new KingMudface(card);
        } else if (card.getName().equals("Empress Thorina")) {
            return new EmpressThorina(card);
        }
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
}
