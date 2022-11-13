package game;

import commands.DebuggingCommands;
import commands.InteractionCommands;
import commands.StatisticsCommands;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.Coordinates;

public final class Action {
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

    public Action(final ActionsInput actionInput, final Game game) {
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

    /**
     * A Default Gateway method used for calling the right functions.
     *
     * @return JSON object node
     */
    public ObjectNode run() {
        return switch (command) {
            case "getPlayerTurn" -> DebuggingCommands.getPlayerTurn(game, command);
            case "getPlayerHero" -> DebuggingCommands.getPlayerHero(game, playerIdx, command);
            case "getPlayerDeck" -> DebuggingCommands.getPlayerDeck(game, playerIdx, command);
            case "endPlayerTurn" -> InteractionCommands.endPlayerTurn(game);
            case "placeCard" -> InteractionCommands.placeCard(game, command, handIdx);
            case "getCardsInHand" -> DebuggingCommands.getCardsInHand(game, command, playerIdx);
            case "getPlayerMana" -> DebuggingCommands.getPlayerMana(game, command, playerIdx);
            case "getCardsOnTable" -> DebuggingCommands.getCardsOnTable(game, command);
            case "useEnvironmentCard" -> InteractionCommands.useEnvironmentCard(game, command,
                                                                            handIdx, affectedRow);
            case "getEnvironmentCardsInHand" -> DebuggingCommands.getEnvironmentCardsInHand(game,
                                                                                command, playerIdx);
            case "getCardAtPosition" -> DebuggingCommands.getCardAtPosition(game, command, x, y);
            case "getFrozenCardsOnTable" -> DebuggingCommands.getFrozenCardsOnTable(game, command);
            case "cardUsesAttack" -> InteractionCommands.cardAction(game, command,
                                                cardAttacker, cardAttacked,
                                                "usesAttack");
            case "cardUsesAbility" -> InteractionCommands.cardAction(game, command, cardAttacker,
                                                            cardAttacked, "usesAbility");
            case "useAttackHero" -> InteractionCommands.useAttackHero(game, command, cardAttacker);
            case "useHeroAbility" -> InteractionCommands.useHeroAbility(game, command, affectedRow);
            case "getPlayerOneWins", "getPlayerTwoWins" -> StatisticsCommands.
                                                                        getPlayerWins(command);
            case "getTotalGamesPlayed" -> StatisticsCommands.getTotalGamesPlayed(command);
            default -> null;
        };
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
}
