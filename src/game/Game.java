package game;

import cards.Card;
import players.Player;
import table.Table;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.StartGameInput;

import java.util.ArrayList;

import static game.GameConstants.FIRST_PLAYER_IDX;
import static game.GameConstants.MAX_ROUND_FOR_INCREMENT;
import static game.GameConstants.SECOND_PLAYER_IDX;

public final class Game {
    private boolean newRound;
    private int nrTurnsTaken;
    private int nrOfRound;
    private int turn; // 1 for playerOne, 2 for playerTwo
    private int shuffleSeed;
    private ArrayList<Player> players;
    private ArrayList<Action> actions;
    private Table table;
    private boolean heroDied;
    private static int playerOneWins;
    private static int playerTwoWins;
    private static int totalGamesPlayed;

    public Game(final StartGameInput gameParameters, final Player playerOne,
                final Player playerTwo, final ArrayList<ActionsInput> actions) {
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
        for (ActionsInput actionInput : actions) {
            this.actions.add(new Action(actionInput, this));
        }

        this.table = new Table();
        this.nrOfRound = 0;
        this.heroDied = false;
        Game.totalGamesPlayed++;
    }

    /**
     * The linkage between the entry point of the game and the process of actually
     * running the game, taking every action in part.
     *
     * @param output JSON ArrayNode that contains commands' results
     */
    public void runGame(final ArrayNode output) {

        for (Action action : actions) {

            if (newRound) {
                setRoundParameters();
            }

            ObjectNode actionObj = action.run();
            if (actionObj != null) {
                output.add(actionObj);
            }
        }
    }

    /**
     * Method used for transferring a card from deck to hand.
     */
    private void getCardInHandsFromDeck() {
        ArrayList<Card> deckPlayerOne = players.get(0).getAllDecks().getDecks().
                                        get(players.get(0).getDeckIdx());
        ArrayList<Card> deckPlayerTwo = players.get(1).getAllDecks().getDecks().
                                        get(players.get(1).getDeckIdx());

        if (deckPlayerOne.size() != 0) {
            Card firstCardPlayerOne = deckPlayerOne.get(0);
            deckPlayerOne.remove(0);
            players.get(FIRST_PLAYER_IDX).getHand().addCard(firstCardPlayerOne);
        }
        if (deckPlayerTwo.size() != 0) {
            Card firstCardPlayerTwo = deckPlayerTwo.get(0);
            deckPlayerTwo.remove(0);
            players.get(SECOND_PLAYER_IDX).getHand().addCard(firstCardPlayerTwo);
        }
    }

    /**
     * Method used to set round's parameters:
     * <pre>
     *     * players get a card in the hands from their decks
     *     * newRound variable is set to false
     *     * players receive new mana</pre>
     */
    private void setRoundParameters() {
        getCardInHandsFromDeck();
        newRound = false;

        if (nrOfRound < MAX_ROUND_FOR_INCREMENT) {
            nrOfRound++;
            players.get(FIRST_PLAYER_IDX).setMana(players.get(FIRST_PLAYER_IDX).
                                                            getMana() + nrOfRound);
            players.get(SECOND_PLAYER_IDX).setMana(players.get(SECOND_PLAYER_IDX).
                                                            getMana() + nrOfRound);
        } else {
            players.get(FIRST_PLAYER_IDX).setMana(players.get(FIRST_PLAYER_IDX).
                                                            getMana() + MAX_ROUND_FOR_INCREMENT);
            players.get(SECOND_PLAYER_IDX).setMana(players.get(SECOND_PLAYER_IDX).
                                                            getMana() + MAX_ROUND_FOR_INCREMENT);
        }
    }

    /**
     * Reset scores for statistic variables.
     */
    public static void resetScores() {
        Game.setTotalGamesPlayed(0);
        Game.setPlayerOneWins(0);
        Game.setPlayerTwoWins(0);
    }

    public static int getPlayerOneWins() {
        return playerOneWins;
    }

    public static void setPlayerOneWins(final int playerOneWins) {
        Game.playerOneWins = playerOneWins;
    }

    public static int getPlayerTwoWins() {
        return playerTwoWins;
    }

    public static void setPlayerTwoWins(final int playerTwoWins) {
        Game.playerTwoWins = playerTwoWins;
    }

    public static int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public static void setTotalGamesPlayed(final int totalGamesPlayed) {
        Game.totalGamesPlayed = totalGamesPlayed;
    }

    public boolean isHeroDied() {
        return heroDied;
    }

    public void setHeroDied(final boolean heroDied) {
        this.heroDied = heroDied;
    }

    public int getNrOfRound() {
        return nrOfRound;
    }

    public void setNrOfRound(final int nrOfRound) {
        this.nrOfRound = nrOfRound;
    }

    public int getNrTurnsTaken() {
        return nrTurnsTaken;
    }

    public void setNrTurnsTaken(final int nrTurnsTaken) {
        this.nrTurnsTaken = nrTurnsTaken;
    }

    public boolean isNewRound() {
        return newRound;
    }

    public void setNewRound(final boolean newRound) {
        this.newRound = newRound;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(final Table table) {
        this.table = table;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(final int turn) {
        this.turn = turn;
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(final ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<Action> actions) {
        this.actions = actions;
    }
}
