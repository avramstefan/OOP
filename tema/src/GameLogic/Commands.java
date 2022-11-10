package GameLogic;

import Cards.Card;
import Players.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;


public class Commands {
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

        for (Card card : deck) {
            ObjectNode cardNode = (new ObjectMapper()).createObjectNode();
            System.out.println(card.getMana());
            cardNode.put("mana", card.getMana());

            if (card.getType().equals("Minion")) {
                cardNode.put("attackDamage", card.getAttackDamage());
                cardNode.put("health", card.getHealth());
            }
            cardNode.put("description", card.getDescription());
            System.out.println(card.getName());
            ArrayNode colorsNode = cardNode.putArray("colors");
            for (String color : card.getColors())
                colorsNode.add(color);

            cardNode.put("name", card.getName());

            outputNode.add(cardNode);
        }

        return actionObj;
    }
}
