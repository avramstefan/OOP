package Players;

import Cards.Card;
import Decks.Decks;
import Hands.Hand;
import fileio.GameInput;
import fileio.StartGameInput;

public class Player {
    private Decks allDecks;
    private Hand hand;
    private int deckIdx;
    private Card hero;

    public Player(Decks decks, StartGameInput gameParameters, int player) {
        this.allDecks = decks;
        if (player == 1) {
            this.deckIdx = gameParameters.getPlayerOneDeckIdx();
            this.hero = new Card(gameParameters.getPlayerOneHero());
        } else {
            this.deckIdx = gameParameters.getPlayerTwoDeckIdx();
            this.hero = new Card(gameParameters.getPlayerTwoHero());
        }
        this.hand = new Hand();
    }

    public Decks getAllDecks() {
        return allDecks;
    }

    public void setAllDecks(Decks decks) {
        this.allDecks = decks;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public int getDeckIdx() {
        return deckIdx;
    }

    public void setDeckIdx(int deckIdx) {
        this.deckIdx = deckIdx;
    }

    public Card getHero() {
        return hero;
    }

    public void setHero(Card hero) {
        this.hero = hero;
    }

}
