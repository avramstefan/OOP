package fileio;

import java.util.ArrayList;

public final class GameInput {
        private StartGameInput startGame;
        private ArrayList<ActionsInput> actions;
        private int playerTurn;

        public GameInput() {
        }

        public void setGameParameters(Input inputData) {
                StartGameInput startGame = getStartGame();

                if (startGame.getPlayerOneHero() != null)
                        startGame.getPlayerOneHero().setHealth(30);

                if (startGame.getPlayerTwoHero() != null)
                        startGame.getPlayerTwoHero().setHealth(30);

                playerTurn = startGame.getStartingPlayer();

//                inputData.getPlayerOneDecks().shuffleDeck(startGame.getPlayerOneDeckIdx(), startGame.getShuffleSeed());
//                inputData.getPlayerTwoDecks().shuffleDeck(startGame.getPlayerTwoDeckIdx(), startGame.getShuffleSeed());
        }

        public int getPlayerTurn() {
                return playerTurn;
        }

        public void setPlayerTurn(int playerTurn) {
                this.playerTurn = playerTurn;
        }

        public StartGameInput getStartGame() {
                return startGame;
        }

        public void setStartGame(final StartGameInput startGame) {
                this.startGame = startGame;
        }

        public ArrayList<ActionsInput> getActions() {
                return actions;
        }

        public void setActions(final ArrayList<ActionsInput> actions) {
                this.actions = actions;
        }

        @Override
        public String toString() {
                return "GameInput{"
                        +  "startGame="
                        + startGame
                        + ", actions="
                        + actions
                        + '}';
        }
}
