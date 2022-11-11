package GameLogic;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.Coordinates;

import java.security.cert.CertPath;

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
        switch (command) {
            case "getPlayerTurn":
                return Commands.getPlayerTurn(game, command);
            case "getPlayerHero":
                return Commands.getPlayerHero(game, playerIdx, command);
            case "getPlayerDeck":
                return Commands.getPlayerDeck(game, playerIdx, command);
            case "endPlayerTurn":
                return Commands.endPlayerTurn(game, command);
            case "placeCard":
                return Commands.placeCard(game, command, handIdx);
            case "getCardsInHand":
                return Commands.getCardsInHand(game, command, playerIdx);
            case "getPlayerMana":
                return Commands.getPlayerMana(game, command, playerIdx);
            case "getCardsOnTable":
                return Commands.getCardsOnTable(game, command);
            case "useEnvironmentCard":
                return Commands.useEnvironmentCard(game, command, handIdx, affectedRow);
            case "getEnvironmentCardsInHand":
                return Commands.getEnvironmentCardsInHand(game, command, playerIdx);
            case "getCardAtPosition":
                return Commands.getCardAtPosition(game, command, x, y);
            case "getFrozenCardsOnTable":
                return Commands.getFrozenCardsOnTable(game, command);
        }
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
