package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public final class ActionsInput {
    private String command;
    private int handIdx;
    private Coordinates cardAttacker;
    private Coordinates cardAttacked;
    private int affectedRow;
    private int playerIdx;
    private int x;
    private int y;

    public void run(ObjectNode newActionObject, ObjectMapper objectMapper, Input inputData) {
        newActionObject.put("command", getCommand());
        if (command.equals("getPlayerDeck"))
            getPlayerDeck(newActionObject, objectMapper, inputData);
        else if (command.equals("getPlayerHero"))
            getPlayerHero(newActionObject, objectMapper, inputData);
        else if (command.equals("getPlayerTurn"))
            getPlayerTurn(newActionObject, objectMapper, inputData);
    }

    public void getPlayerTurn(ObjectNode newActionObject, ObjectMapper objectMapper, Input inputData) {
        newActionObject.put("output", inputData.getGames().get(inputData.getGameIdx()).getPlayerTurn());
    }

    public void getPlayerHero(ObjectNode newActionObject, ObjectMapper objectMapper, Input inputData) {
        newActionObject.put("playerIdx", getPlayerIdx());

        ArrayNode output = objectMapper.createArrayNode();

        CardInput card;
        if (playerIdx == 1)
            card = inputData.getGames().get(inputData.getGameIdx()).getStartGame().getPlayerOneHero();
        else
            card = inputData.getGames().get(inputData.getGameIdx()).getStartGame().getPlayerTwoHero();

        ObjectNode cardObject = objectMapper.createObjectNode();
        cardObject.put("mana", card.getMana());
        cardObject.put("description", card.getDescription());

        ArrayNode colorsNode = cardObject.putArray("colors");
        for (String color : card.getColors())
            colorsNode.add(color);

        cardObject.put("name", card.getName());
        cardObject.put("health", card.getHealth());

        newActionObject.set("output", cardObject);
    }

    public void getPlayerDeck(ObjectNode newActionObject, ObjectMapper objectMapper, Input inputData) {
        newActionObject.put("playerIdx", getPlayerIdx());

        ArrayNode output = objectMapper.createArrayNode();

        ArrayList<CardInput> deck;
        StartGameInput startGame = inputData.getGames().get(inputData.getGameIdx()).getStartGame();
        if (playerIdx == 1) {
            deck = inputData.getPlayerOneDecks().getDecks().get(startGame.getPlayerOneDeckIdx());
        } else {
            deck = inputData.getPlayerTwoDecks().getDecks().get(startGame.getPlayerTwoDeckIdx());
        }

        for (int i = 1; i < deck.size(); i++) {
            ObjectNode cardObject = objectMapper.createObjectNode();
            CardInput card = deck.get(i);

            cardObject.put("mana", card.getMana());
            cardObject.put("attackDamage", card.getAttackDamage());
            cardObject.put("health", card.getHealth());
            cardObject.put("description", card.getDescription());

            ArrayNode colorsNode = cardObject.putArray("colors");
            for (String color : card.getColors())
                colorsNode.add(color);

            cardObject.put("name", card.getName());

            output.add(cardObject);
        }

        newActionObject.set("output", output);
    }
    public ActionsInput() {
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
