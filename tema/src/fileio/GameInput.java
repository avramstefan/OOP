package fileio;

import java.util.ArrayList;

public final class GameInput {
        private StartGameInput startGame;
        private ArrayList<ActionsInput> actions;
        private int playerTurn;

        public GameInput() {
        }

        public int getPlayerTurn() {
                return playerTurn;
        }

        public void setPlayerTurn(final int playerTurn) {
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

}
