package GameLogic;

import Cards.Card;
import Hands.Hand;
import Players.Player;
import Table.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

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

        player.getHero().setHasAttacked(false);

        for (int i = 4 / playerIdx - 2; i < 4 / playerIdx; i++) {
            ArrayList<Card> tableRowCards = table.getCards().get(i);
            for (Card card : tableRowCards) {
                card.setFrozen(false);
                card.setHasAttacked(false);
            }
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

    public static ObjectNode getCardsOnTable(final Game game, String command) {
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
                card.useEnvironmentAbility(table, affectedRow);
            } else {
                actionObj.put("error", "Cannot steal enemy card since the player's row is full.");
                return actionObj;
            }
        } else {
            card.useEnvironmentAbility(table, affectedRow);
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

    public static boolean enemyHasTank(Table table, int frontRow) {
        for (Card card : table.getCards().get(frontRow))
            if (card.getSpecificType().equals("FrontTankMinion"))
                return true;
        return false;
    }

    public static void addCoordinatesOutput(ObjectNode actionObj, Coordinates attackerCoord, Coordinates attackedCoord) {
        ObjectNode attackerObj = (new ObjectMapper()).createObjectNode();
        ObjectNode attackedObj = (new ObjectMapper()).createObjectNode();

        attackerObj.put("x", attackerCoord.getX());
        attackerObj.put("y", attackerCoord.getY());
        attackedObj.put("x", attackedCoord.getX());
        attackedObj.put("y", attackedCoord.getY());

        actionObj.set("cardAttacker", attackerObj);
        actionObj.set("cardAttacked", attackedObj);
    }

    public static boolean checkExistenceOfCard(Game game, Coordinates coordinates) {
        return game.getTable().getCards().get(coordinates.getX()).size() <= coordinates.getY();
    }

    public static boolean checkAttackedCardIsFromEnemy(Coordinates attackerCoord, Coordinates attackedCoord) {
        return (attackerCoord.getX() < 2 && attackedCoord.getX() < 2) ||
                (attackerCoord.getX() > 1 && attackedCoord.getX() > 1);
    }

    public static ObjectNode cardAction(Game game, String command, Coordinates attackerCoord,
                                        Coordinates attackedCoord, String typeOfAction) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);

        addCoordinatesOutput(actionObj, attackerCoord, attackedCoord);

        if (checkExistenceOfCard(game, attackerCoord) || checkExistenceOfCard(game, attackedCoord)) {
            return null;
        }

        Table table = game.getTable();
        Card cardAttacked = table.getCards().get(attackedCoord.getX()).get(attackedCoord.getY());
        Card cardAttacker = table.getCards().get(attackerCoord.getX()).get(attackerCoord.getY());

        if (cardAttacker.isFrozen()) {
            actionObj.put("error", "Attacker card is frozen.");
            return actionObj;
        }

        if (cardAttacker.hasAttacked()) {
            actionObj.put("error", "Attacker card has already attacked this turn.");
            return actionObj;
        }

        if (!(typeOfAction.equals("usesAbility") && cardAttacker.getName().equals("Disciple"))) {
            if (checkAttackedCardIsFromEnemy(attackerCoord, attackedCoord)) {
                actionObj.put("error", "Attacked card does not belong to the enemy.");
                return actionObj;
            }

            int backAttackedRow = 3;
            int frontAttackedRow = 2;
            if (game.getTurn() == 1) {
                backAttackedRow = 0;
                frontAttackedRow = 1;
            }

            if (enemyHasTank(table, frontAttackedRow) && !cardAttacked.getSpecificType().equals("FrontTankMinion")) {
                actionObj.put("error", "Attacked card is not of type 'Tank'.");
                return actionObj;
            }

            if (typeOfAction.equals("usesAttack")) {
                cardAttacker.attack(cardAttacked, table, attackedCoord.getX(), attackedCoord.getY());
            } else {
                cardAttacker.useMinionAbility(cardAttacked);

                if (cardAttacked.getHealth() == 0)
                    table.getCards().get(attackedCoord.getX()).remove(attackedCoord.getY());
            }
        } else {
            if (!checkAttackedCardIsFromEnemy(attackerCoord, attackedCoord)) {
                actionObj.put("error", "Attacked card does not belong to the current player.");
                return actionObj;
            }
            cardAttacker.useMinionAbility(cardAttacked);
        }

        return null;
    }

    public static ObjectNode useAttackHero(Game game, String command, Coordinates attackerCoord) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);

        if (checkExistenceOfCard(game, attackerCoord)) {
            return null;
        }

        ObjectNode attackerObj = (new ObjectMapper()).createObjectNode();
        attackerObj.put("x", attackerCoord.getX());
        attackerObj.put("y", attackerCoord.getY());
        actionObj.set("cardAttacker", attackerObj);

        Card card = game.getTable().getCards().get(attackerCoord.getX()).get(attackerCoord.getY());

        if (card.isFrozen()) {
            actionObj.put("error", "Attacker card is frozen.");
            return actionObj;
        }

        if (card.hasAttacked()) {
            actionObj.put("error", "Attacker card has already attacked this turn.");
            return actionObj;
        }

        int frontAttackedRow = 1;
        if (game.getTurn() == 2)
            frontAttackedRow = 2;

        if (enemyHasTank(game.getTable(), frontAttackedRow)) {
            actionObj.put("error", "Attacked card is not of type 'Tank'.");
            return actionObj;
        }

        Player attackedPlayer;
        if (game.getTurn() == 1)
            attackedPlayer = game.getPlayers().get(1);
        else
            attackedPlayer = game.getPlayers().get(0);

        Card heroCard = attackedPlayer.getHero();
        heroCard.setHealth(heroCard.getHealth() - card.getAttackDamage());

        card.setHasAttacked(true);

        if (heroCard.getHealth() <= 0) {
            ObjectNode endObj = (new ObjectMapper()).createObjectNode();
            if (game.getTurn() == 1) {
                endObj.put("gameEnded", "Player one killed the enemy hero.");
                Game.setPlayerOneWins(Game.getPlayerOneWins() + 1);
            } else {
                endObj.put("gameEnded", "Player two killed the enemy hero.");
                Game.setPlayerTwoWins(Game.getPlayerTwoWins() + 1);
            }
            game.setHeroDied(true);
            return endObj;
        }

        return null;
    }

    public static ObjectNode useHeroAbility(Game game, String command, int affectedRow) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("affectedRow", affectedRow);

        int currentPlayer = game.getTurn();
        Player player = game.getPlayers().get(currentPlayer - 1);
        Card heroCard = player.getHero();

        if (player.getMana() < heroCard.getMana()) {
            actionObj.put("error", "Not enough mana to use hero's ability.");
            return actionObj;
        }

        if (heroCard.hasAttacked()) {
            actionObj.put("error", "Hero has already attacked this turn.");
            return actionObj;
        }

        if (heroCard.getName().equals("Lord Royce") || heroCard.getName().equals("Empress Thorina")) {
            if ((currentPlayer == 1 && affectedRow > 1) || (currentPlayer == 2 && affectedRow < 2)) {
                actionObj.put("error", "Selected row does not belong to the enemy.");
                return actionObj;
            }
            heroCard.useHeroAbility(game.getTable(), affectedRow);
        } else {
            if ((currentPlayer == 1 && affectedRow < 2) || (currentPlayer == 2 && affectedRow > 1)) {
                actionObj.put("error", "Selected row does not belong to the current player.");
                return actionObj;
            }
            heroCard.useHeroAbility(game.getTable(), affectedRow);
        }

        heroCard.setHasAttacked(true);
        player.setMana(player.getMana() - heroCard.getMana());

        return null;
    }

    public static ObjectNode getPlayerWins(Game game, String command) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);

        if (command.equals("getPlayerOneWins"))
            actionObj.put("output", Game.getPlayerOneWins());
        else
            actionObj.put("output", Game.getPlayerTwoWins());

        return actionObj;
    }

    public static ObjectNode getTotalGamesPlayed(Game game, String command) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("output", Game.getTotalGamesPlayed());
        return actionObj;
    }
}

