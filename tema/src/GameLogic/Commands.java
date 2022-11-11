package GameLogic;

import Cards.Card;
import Hands.Hand;
import Players.Player;
import Table.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Commands {

    private static void getSingleCard(ObjectNode cardNode, Card card) {
        cardNode.put("mana", card.getMana());

        if (card.getType().equals("Minion")) {
            cardNode.put("attackDamage", card.getAttackDamage());
            cardNode.put("health", card.getHealth());
        }
        cardNode.put("description", card.getDescription());

        ArrayNode colorsNode = cardNode.putArray("colors");
        for (String color : card.getColors())
            colorsNode.add(color);

        cardNode.put("name", card.getName());
    }
    private static void getCardsGeneric(ArrayNode outputNode, ArrayList<Card> cards) {
        for (Card card : cards) {
            ObjectNode cardNode = (new ObjectMapper()).createObjectNode();
            getSingleCard(cardNode, card);
            outputNode.add(cardNode);
        }
    }
    public static ObjectNode getPlayerTurn(Game game, String command) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("output", game.getTurn());
        return actionObj;
    }

    public static ObjectNode getPlayerHero(Game game, int playerIdx, String command) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("playerIdx", playerIdx);

        Card heroCard = game.getPlayers().get(playerIdx - 1).getHero();

        ObjectNode outputObject = (new ObjectMapper()).createObjectNode();
        outputObject.put("mana", heroCard.getMana());
        outputObject.put("description", heroCard.getDescription());

        ArrayNode colorsNode = outputObject.putArray("colors");
        for (String color : heroCard.getColors())
            colorsNode.add(color);

        outputObject.put("name", heroCard.getName());
        outputObject.put("health", heroCard.getHealth());

        actionObj.set("output", outputObject);
        return actionObj;
    }

    public static ObjectNode getPlayerDeck(Game game, int playerIdx, String command) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("playerIdx", playerIdx);

        Player player = game.getPlayers().get(playerIdx - 1);
        ArrayList<Card> deck = player.getAllDecks().getDecks().get(player.getDeckIdx());

        ArrayNode outputNode = actionObj.putArray("output");
        getCardsGeneric(outputNode, deck);

        return actionObj;
    }

    public static ObjectNode endPlayerTurn(Game game, String command) {
        int playerIdx = game.getTurn();

        game.setNrTurnsTaken(game.getNrTurnsTaken() + 1);
        if (game.getNrTurnsTaken() == 2) {
            game.setNrTurnsTaken(0);
            game.setNewRound(true);
        }

        if (playerIdx == 1)
            game.setTurn(2);
        else
            game.setTurn(1);

        Player player = game.getPlayers().get(playerIdx - 1);
        Table table = game.getTable();

        for (int i = 4 / playerIdx - 2; i < 4 / playerIdx; i++) {
            ArrayList<Card> tableRowCards = table.getCards().get(i);
            for (Card card : tableRowCards)
                if (card.isFrozen())
                    card.setFrozen(false);
        }

        return null;
    }

    public static ObjectNode placeCard(Game game, String command, int handIdx) {
        Player player = game.getPlayers().get(game.getTurn() - 1);

        if (handIdx >= player.getHand().getCards().size())
            return null;

        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("handIdx", handIdx);

        Card cardToPlace = player.getHand().getCards().get(handIdx);

        if (cardToPlace.getType().equals("Environment")) {
            actionObj.put("error", "Cannot place environment card on table.");
            return actionObj;
        }

        if (player.getMana() >= cardToPlace.getMana()) {
            if (!game.getTable().checkRowAvailability(game.getTurn(), cardToPlace)) {
                actionObj.put("error", "Cannot place card on table since row is full.");
                return actionObj;
            }
            game.getTable().placeCardOnTable(game.getTurn(), cardToPlace);
            player.getHand().removeCard(handIdx);
            player.setMana(player.getMana() - cardToPlace.getMana());
        } else {
            actionObj.put("error", "Not enough mana to place card on table.");
            return actionObj;
        }

        return null;
    }

    public static ObjectNode getCardsInHand(Game game, String command, int playerIdx) {
        Player player = game.getPlayers().get(playerIdx - 1);
        ArrayList<Card> cards = player.getHand().getCards();

        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("playerIdx", playerIdx);

        ArrayNode outputNode = actionObj.putArray("output");
        getCardsGeneric(outputNode, cards);

        return actionObj;
    }

    public static ObjectNode getPlayerMana(Game game, String command, int playerIdx) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();

        actionObj.put("command", command);
        actionObj.put("playerIdx", playerIdx);
        actionObj.put("output", game.getPlayers().get(playerIdx - 1).getMana());

        return actionObj;
    }

    public static ObjectNode getCardsOnTable(Game game, String command) {
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


    public static boolean checkChosenAffectedRow(int playerIdx, int affectedRow) {
        return !((playerIdx == 1 && affectedRow > 1) || (playerIdx == 2 && affectedRow < 2));
    }

    public static boolean checkMirrorRowAvailability(Table table, int affectedRow) {
        ArrayList<ArrayList<Card>> cards = table.getCards();
        if ((affectedRow == 0 && cards.get(3).size() < 5)  ||
                (affectedRow == 1 && cards.get(2).size() < 5) ||
                (affectedRow == 2 && cards.get(1).size() < 5) ||
                (affectedRow == 3 && cards.get(0).size() < 5))
            return true;
        return false;
    }
    public static ObjectNode useEnvironmentCard(Game game, String command, int handIdx, int affectedRow) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("handIdx", handIdx);
        actionObj.put("affectedRow", affectedRow);

        Table table = game.getTable();
        Player player = game.getPlayers().get(game.getTurn() - 1);
        Card card = player.getHand().getCards().get(handIdx);

        if (!card.getType().equals("Environment")) {
            actionObj.put("error", "Chosen card is not of type environment.");
            return actionObj;
        }

        if (player.getMana() < card.getMana()) {
            actionObj.put("error", "Not enough mana to use environment card.");
            return actionObj;
        }

        if (!checkChosenAffectedRow(game.getTurn(), affectedRow)) {
            actionObj.put("error", "Chosen row does not belong to the enemy.");
            return actionObj;
        }

        if (card.getSpecificType().equals("HeartHound")) {
            if (checkMirrorRowAvailability(table, affectedRow)) {
                card.useSpecialAbility(table, affectedRow);
            } else {
                actionObj.put("error", "Cannot steal enemy card since the player's row is full.");
                return actionObj;
            }
        } else {
            card.useSpecialAbility(table, affectedRow);
        }

        player.getHand().getCards().remove(handIdx);
        player.setMana(player.getMana() - card.getMana());
        return null;
    }

    public static ObjectNode getEnvironmentCardsInHand(Game game, String command, int playerIdx) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("playerIdx", playerIdx);

        ArrayNode output = actionObj.putArray("output");
        System.out.println(playerIdx);
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

    public static ObjectNode getCardAtPosition(Game game, String command, int x, int y) {;
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);

        if (game.getTable().getCards().get(x).size() <= y) {
            actionObj.put("error", "No card at that position.");
            return actionObj;
        }

        Card card = game.getTable().getCards().get(x).get(y);

        ObjectNode cardNode = (new ObjectMapper()).createObjectNode();
        getSingleCard(cardNode, card);

        actionObj.set("output", cardNode);

        return actionObj;
    }

    public static ObjectNode getFrozenCardsOnTable(Game game, String command) {
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
