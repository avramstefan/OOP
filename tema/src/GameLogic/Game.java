package GameLogic;

import Cards.Card;
import Decks.Decks;
import Players.Player;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.StartGameInput;

import java.util.ArrayList;

public class Game {
    private int turn; // 1 for playerOne, 2 for playerTwo
    private int shuffleSeed;
    private ArrayList<Player> players;
    private ArrayList<Action> actions;

    public Game(StartGameInput gameParameters, Player playerOne, Player playerTwo, ArrayList<ActionsInput> actions) {
        this.turn = gameParameters.getStartingPlayer();
        this.shuffleSeed = gameParameters.getShuffleSeed();

        this.players = new ArrayList<>();
        this.players.add(playerOne);
        this.players.add(playerTwo);

        this.actions = new ArrayList<>();
        for (ActionsInput actionInput : actions)
            this.actions.add(new Action(actionInput, this));
    }

    public void runGame(ArrayNode output) {
        getCardInHands();
        for (Action action : actions) {

            output.add(action.run());
        }
    }

    private void getCardInHands() {
        ArrayList<Card> deckPlayerOne = players.get(0).getAllDecks().getDecks().get(players.get(0).getDeckIdx());
        ArrayList<Card> deckPlayerTwo = players.get(1).getAllDecks().getDecks().get(players.get(1).getDeckIdx());

        if (deckPlayerOne.size() != 0 && deckPlayerTwo.size() != 0) {

            Card firstCardPlayerOne = deckPlayerOne.get(0);
            Card firstCardPlayerTwo = deckPlayerTwo.get(0);

            deckPlayerOne.remove(0);
            deckPlayerTwo.remove(0);

            players.get(0).getHand().addCard(firstCardPlayerOne);
            players.get(1).getHand().addCard(firstCardPlayerTwo);
        }
        }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }
}
