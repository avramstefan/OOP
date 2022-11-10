package GameLogic;

import Cards.Card;
import Decks.Decks;
import Players.Player;
import Table.Table;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.StartGameInput;

import java.util.ArrayList;

public class Game {
    private boolean newRound;
    private int nrTurnsTaken;
    private int turn; // 1 for playerOne, 2 for playerTwo
    private int shuffleSeed;
    private ArrayList<Player> players;
    private ArrayList<Action> actions;
    private Table table;

    public Game(StartGameInput gameParameters, Player playerOne, Player playerTwo, ArrayList<ActionsInput> actions) {
        this.turn = gameParameters.getStartingPlayer();
        this.newRound = true;
        this.nrTurnsTaken = 0;
        this.shuffleSeed = gameParameters.getShuffleSeed();

        playerOne.setCurrentGame(this);
        playerTwo.setCurrentGame(this);

        this.players = new ArrayList<>();
        this.players.add(playerOne);
        this.players.add(playerTwo);

        this.actions = new ArrayList<>();
        for (ActionsInput actionInput : actions)
            this.actions.add(new Action(actionInput, this));

        this.table = new Table();
    }

    public void runGame(ArrayNode output) {

        for (Action action : actions) {

            if (newRound)
                setRoundParameters();

            ObjectNode actionObj = action.run();
            if (actionObj != null)
                output.add(actionObj);
        }
    }

    private void getCardInHandsFromDeck() {
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

    private void setRoundParameters() {
        getCardInHandsFromDeck();
        newRound = false;

        if (players.get(0).getMana() < 10)
            players.get(0).setMana(players.get(0).getMana() + 1);
        if (players.get(1).getMana() < 10)
            players.get(1).setMana(players.get(1).getMana() + 1);
    }

    public int getNrTurnsTaken() {
        return nrTurnsTaken;
    }

    public void setNrTurnsTaken(int nrTurnsTaken) {
        this.nrTurnsTaken = nrTurnsTaken;
    }

    public boolean isNewRound() {
        return newRound;
    }

    public void setNewRound(boolean newRound) {
        this.newRound = newRound;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
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