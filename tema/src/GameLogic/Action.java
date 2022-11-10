package GameLogic;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.Coordinates;

public class Action {
    private String command;
    private int handIdx;
    private Coordinates cardAttacker;
    private Coordinates cardAttacked;
    private int affectedRow;
    private int playerIdx;
    private int x;
    private int y;
    private Game game;

    public Action() {
    }

    public Action(ActionsInput actionInput, Game game) {
        this.command = actionInput.getCommand();
        this.handIdx = actionInput.getHandIdx();
        this.cardAttacker = actionInput.getCardAttacker();
        this.cardAttacked = actionInput.getCardAttacked();
        this.affectedRow = actionInput.getAffectedRow();
        this.playerIdx = actionInput.getPlayerIdx();
        this.x = actionInput.getX();
        this.y = actionInput.getY();
        this.game = game;
    }

    public ObjectNode run() {
        if (command.equals("getPlayerTurn"))
            return Commands.getPlayerTurn(game, command);
        else if (command.equals("getPlayerHero"))
            return Commands.getPlayerHero(game, playerIdx, command);
        else if (command.equals("getPlayerDeck"))
            return Commands.getPlayerDeck(game, playerIdx, command);
        else if (command.equals("endPlayerTurn"))
            return Commands.endPlayerTurn(game, command);
        else if (command.equals("placeCard"))
            return Commands.placeCard(game, command, handIdx);
        else if (command.equals("getCardsInHand"))
            return Commands.getCardsInHand(game, command, playerIdx);
        else if (command.equals("getPlayerMana"))
            return Commands.getPlayerMana(game, command, playerIdx);
        else if (command.equals("getCardsOnTable"))
            return Commands.getCardsOnTable(game, command);
        return null;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getHandIdx() {
        return handIdx;
    }

    public void setHandIdx(final int handIdx) {
        this.handIdx = handIdx;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public void setCardAttacker(final Coordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    public void setCardAttacked(final Coordinates cardAttacked) {
        this.cardAttacked = cardAttacked;
    }

    public int getAffectedRow() {
        return affectedRow;
    }

    public void setAffectedRow(final int affectedRow) {
        this.affectedRow = affectedRow;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(final int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "ActionsInput{"
                +  "command='"
                + command + '\''
                +  ", handIdx="
                + handIdx
                +  ", cardAttacker="
                + cardAttacker
                +  ", cardAttacked="
                + cardAttacked
                + ", affectedRow="
                + affectedRow
                + ", playerIdx="
                + playerIdx
                + ", x="
                + x
                + ", y="
                + y
                + '}';
    }
}
