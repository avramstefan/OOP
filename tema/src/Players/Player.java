package Players;

import Cards.Card;
import Decks.Decks;
import GameLogic.Game;
import Hands.Hand;
import fileio.GameInput;
import fileio.StartGameInput;

public class Player {
    private Decks allDecks;
    private Hand hand;
    private int deckIdx;
    private Card hero;
    private Game currentGame;
    private int mana;

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
        this.currentGame = null;
        this.mana = 1;
    }


    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public Decks getAllDecks() {
        return allDecks;
    }

    public void setAllDecks(Decks allDecks) {
        this.allDecks = allDecks;
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
