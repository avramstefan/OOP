package players;

import cards.Card;
import decks.Decks;
import game.Game;
import hands.Hand;
import fileio.StartGameInput;
import java.util.Collections;
import java.util.Random;

import static game.GameConstants.SECOND_PLAYER_IDX;
import static game.GameConstants.START_HERO_HEALTH;

public final class Player {
    private Decks allDecks;
    private Hand hand;
    private int deckIdx;
    private Card hero;
    private Game currentGame;
    private int mana;

    public Player(final Decks decks, final StartGameInput gameParameters,
                  final int player, final int shuffleSeed) {
        this.allDecks = decks;

        if (player == SECOND_PLAYER_IDX) {
            this.deckIdx = gameParameters.getPlayerOneDeckIdx();
            this.hero = Decks.assignClassToCard(gameParameters.getPlayerOneHero());
        } else {
            this.deckIdx = gameParameters.getPlayerTwoDeckIdx();
            this.hero = Decks.assignClassToCard(gameParameters.getPlayerTwoHero());
        }

        this.hero.setHealth(START_HERO_HEALTH);

        Random randomObject = new Random();
        randomObject.setSeed(shuffleSeed);
        Collections.shuffle(allDecks.getDecks().get(deckIdx), randomObject);

        this.hand = new Hand();
        this.currentGame = null;
        this.mana = 0;
    }


    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(final Game currentGame) {
        this.currentGame = currentGame;
    }

    public Decks getAllDecks() {
        return allDecks;
    }

    public void setAllDecks(final Decks allDecks) {
        this.allDecks = allDecks;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(final Hand hand) {
        this.hand = hand;
    }

    public int getDeckIdx() {
        return deckIdx;
    }

    public void setDeckIdx(final int deckIdx) {
        this.deckIdx = deckIdx;
    }

    public Card getHero() {
        return hero;
    }

    public void setHero(final Card hero) {
        this.hero = hero;
    }

}
