package commands;

import cards.Card;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;
import hands.Hand;
import players.Player;
import table.Table;

import java.util.ArrayList;

public final class DebuggingCommands {

    private DebuggingCommands() {

    }

    /**
     * Method used for creating a JSON node with card's data.
     *
     * @param cardNode is a bigger node that will contain the json node that is being created
     * @param card card that will be parsed as a JSON node
     */
    private static void getSingleCard(final ObjectNode cardNode, final Card card) {
        cardNode.put("mana", card.getMana());

        if (card.getType().equals("Minion")) {
            cardNode.put("attackDamage", card.getAttackDamage());
            cardNode.put("health", card.getHealth());
        }
        cardNode.put("description", card.getDescription());

        ArrayNode colorsNode = cardNode.putArray("colors");
        for (String color : card.getColors()) {
            colorsNode.add(color);
        }

        cardNode.put("name", card.getName());
    }

    /**
     * Method that add every single card from an ArrayList to an ArrayNode.
     * Used in getPlayerDeck() and getCardsInHand().
     *
     * @param outputNode JSON array node that will contain multiple single cards JSON nodes
     * @param cards cards that will be parsed as JSON nodes
     */
    private static void getCardsGeneric(final ArrayNode outputNode, final ArrayList<Card> cards) {
        for (Card card : cards) {
            ObjectNode cardNode = (new ObjectMapper()).createObjectNode();
            getSingleCard(cardNode, card);
            outputNode.add(cardNode);
        }
    }

    /**
     *
     * @param game current game in progress
     * @param command
     * @return JSON object node
     */
    public static ObjectNode getPlayerTurn(final Game game, final String command) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("output", game.getTurn());
        return actionObj;
    }

    /**
     * Method that returns an object containing data about a player's Hero.
     *
     * @param game current game in progress
     * @param playerIdx current player
     * @param command
     * @return JSON object node
     */
    public static ObjectNode getPlayerHero(final Game game, final int playerIdx,
                                           final String command) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("playerIdx", playerIdx);

        Card heroCard = game.getPlayers().get(playerIdx - 1).getHero();

        ObjectNode outputObject = (new ObjectMapper()).createObjectNode();
        outputObject.put("mana", heroCard.getMana());
        outputObject.put("description", heroCard.getDescription());

        ArrayNode colorsNode = outputObject.putArray("colors");
        for (String color : heroCard.getColors()) {
            colorsNode.add(color);
        }

        outputObject.put("name", heroCard.getName());
        outputObject.put("health", heroCard.getHealth());

        actionObj.set("output", outputObject);
        return actionObj;
    }

    /**
     * Method that returns a player's deck.
     *
     * @param game current game in progress
     * @param playerIdx current player
     * @param command
     * @return JSON object node
     */
    public static ObjectNode getPlayerDeck(final Game game, final int playerIdx,
                                           final String command) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("playerIdx", playerIdx);

        Player player = game.getPlayers().get(playerIdx - 1);
        ArrayList<Card> deck = player.getAllDecks().getDecks().get(player.getDeckIdx());

        ArrayNode outputNode = actionObj.putArray("output");
        getCardsGeneric(outputNode, deck);

        return actionObj;
    }

    /**
     * Method that returns player's cards from hand.
     *
     * @param game current game in progress
     * @param command
     * @param playerIdx current player
     * @return JSON object node
     */
    public static ObjectNode getCardsInHand(final Game game, final String command,
                                            final int playerIdx) {
        Player player = game.getPlayers().get(playerIdx - 1);
        ArrayList<Card> cards = player.getHand().getCards();

        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("playerIdx", playerIdx);

        ArrayNode outputNode = actionObj.putArray("output");
        getCardsGeneric(outputNode, cards);

        return actionObj;
    }

    /**
     * Method that returns player's mana.
     *
     * @param game current game in progress
     * @param command
     * @param playerIdx current player
     * @return JSON object node
     */
    public static ObjectNode getPlayerMana(final Game game, final String command,
                                           final int playerIdx) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();

        actionObj.put("command", command);
        actionObj.put("playerIdx", playerIdx);
        actionObj.put("output", game.getPlayers().get(playerIdx - 1).getMana());

        return actionObj;
    }

    /**
     * Method that returns the cards from the table.
     * It starts with row no. 0 and ends with row no. 3.
     *
     * @param game current game in progress
     * @param command
     * @return JSON object node.
     */
    public static ObjectNode getCardsOnTable(final Game game, final String command) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        ArrayNode allCardsFromTable = actionObj.putArray("output");

        Table table = game.getTable();
        for (ArrayList<Card> rowCards : table.getCards()) {
            ArrayNode cardsFromRow = (new ObjectMapper()).createArrayNode();
            for (Card card : rowCards) {
                ObjectNode cardNode = (new ObjectMapper()).createObjectNode();
                getSingleCard(cardNode, card);
                cardsFromRow.add(cardNode);
            }
            allCardsFromTable.add(cardsFromRow);
        }

        return actionObj;
    }

    /**
     * Method that gets all the Environment cards from player hand.
     *
     * @param game current game in progress
     * @param command
     * @param playerIdx current player
     * @return JSON object node
     */
    public static ObjectNode getEnvironmentCardsInHand(final Game game, final String command,
                                                       final int playerIdx) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("playerIdx", playerIdx);

        ArrayNode output = actionObj.putArray("output");
        Hand hand = game.getPlayers().get(playerIdx - 1).getHand();
        for (Card card : hand.getCards()) {
            if (card.getType().equals("Environment")) {
                ObjectNode cardNode = (new ObjectMapper()).createObjectNode();
                getSingleCard(cardNode, card);
                output.add(cardNode);
            }
        }

        return actionObj;
    }

    /**
     * Method that returns an object containing data about the
     * card from the given position.
     *
     * @param game current game in progress
     * @param command
     * @param x index of table row
     * @param y index of table column
     * @return JSON object node
     */
    public static ObjectNode getCardAtPosition(final Game game, final String command,
                                               final int x, final int y) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);

        if (game.getTable().getCards().get(x).size() <= y) {
            actionObj.put("output", "No card available at that position.");
            actionObj.put("x", x);
            actionObj.put("y", y);
            return actionObj;
        }

        Card card = game.getTable().getCards().get(x).get(y);

        ObjectNode cardNode = (new ObjectMapper()).createObjectNode();
        getSingleCard(cardNode, card);

        actionObj.set("output", cardNode);
        actionObj.put("x", x);
        actionObj.put("y", y);

        return actionObj;
    }

    /**
     * Method that gets all the frozen cards from the table.
     *
     * @param game current game in progress
     * @param command
     * @return JSON object node
     */
    public static ObjectNode getFrozenCardsOnTable(final Game game, final String command) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);

        ArrayNode output = actionObj.putArray("output");
        for (ArrayList<Card> rowCard : game.getTable().getCards()) {
            for (Card card : rowCard) {
                if (card.isFrozen()) {
                    ObjectNode cardNode = (new ObjectMapper()).createObjectNode();
                    getSingleCard(cardNode, card);
                    output.add(cardNode);
                }
            }
        }

        return actionObj;
    }
}
