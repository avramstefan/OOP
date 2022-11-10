package GameLogic;

import Cards.Card;
import Players.Player;
import Table.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

        Card cardToPlace = player.getHand().getCards().get(handIdx);
        System.out.println(game.getTurn() - 1 + " " + player.getMana() + " " + cardToPlace.getMana() + " " + cardToPlace.getName() + "\n\n");
        if (cardToPlace != null && player.getMana() >= cardToPlace.getMana()) {
            game.getTable().placeCardOnTable(game.getTurn(), cardToPlace);
            player.getHand().removeCard(handIdx);
            player.setMana(player.getMana() - cardToPlace.getMana());
        }
//        if (cardToPlace != null) {
//            game.getTable().placeCardOnTable(game.getTurn(), cardToPlace);
//            player.getHand().removeCard(handIdx);
//        }
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
            if (cardsFromRow.size() != 0)
                allCardsFromTable.add(cardsFromRow);
        }

        return actionObj;
    }
}
