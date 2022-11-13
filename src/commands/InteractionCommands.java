package commands;

import cards.Card;
import game.Game;
import players.Player;
import table.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;
import java.util.ArrayList;

import static game.GameConstants.MAX_NR_OF_CARDS_PER_TABLE_ROW;
import static game.GameConstants.NR_TABLE_ROWS;
import static game.GameConstants.TABLE_ROW_INDEX_0;
import static game.GameConstants.TABLE_ROW_INDEX_1;
import static game.GameConstants.TABLE_ROW_INDEX_2;
import static game.GameConstants.TABLE_ROW_INDEX_3;

public final class InteractionCommands {

    private InteractionCommands() {

    }

    /**
     * Method that is called when a turn is over.
     * All frozen cards are being unfrozen.
     * All cards have the capability of attacking again.
     * The current player is changed.
     * If 2 turns have passed, a new round begins.
     *
     * @param game current game in progress
     * @return JSON object node
     */
    public static ObjectNode endPlayerTurn(final Game game) {
        int playerIdx = game.getTurn();

        game.setNrTurnsTaken(game.getNrTurnsTaken() + 1);
        if (game.getNrTurnsTaken() == 2) {
            game.setNrTurnsTaken(0);
            game.setNewRound(true);
        }

        if (playerIdx == 1) {
            game.setTurn(2);
        } else {
            game.setTurn(1);
        }

        Player player = game.getPlayers().get(playerIdx - 1);
        Table table = game.getTable();

        player.getHero().setHasAttacked(false);

        for (int i = NR_TABLE_ROWS / playerIdx - 2; i < NR_TABLE_ROWS / playerIdx; i++) {
            ArrayList<Card> tableRowCards = table.getCards().get(i);
            for (Card card : tableRowCards) {
                card.setFrozen(false);
                card.setHasAttacked(false);
            }
        }

        return null;
    }

    /**
     * Place card on table from hand. The card is completely
     * removed from hand and the process is using a method
     * from 'Table' class -> "placeCardOnTable'.
     *
     * @param game current game in progress
     * @param command
     * @param handIdx index of card to place from hand
     * @return JSON object node
     */
    public static ObjectNode placeCard(final Game game, final String command, final int handIdx) {
        Player player = game.getPlayers().get(game.getTurn() - 1);

        if (handIdx >= player.getHand().getCards().size()) {
            return null;
        }

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

    /**
     * Helper method which provides information about rather
     * the target row belongs to the opposite player's table rows or not.
     *
     * @param playerIdx current player
     * @param affectedRow table row that is interrogated
     * @return true or false
     */
    public static boolean checkChosenAffectedRow(final int playerIdx, final int affectedRow) {
        return !((playerIdx == 1 && affectedRow > 1) || (playerIdx == 2 && affectedRow < 2));
    }

    /**
     * Helper method used for Heart Hound Environment card.
     * It checks if there is space on the mirrored row to place the chosen card
     * from the affected row.
     *
     * @param table game table
     * @param affectedRow table row that is interrogated
     * @return true or false
     */
    public static boolean checkMirrorRowAvailability(final Table table, final int affectedRow) {
        ArrayList<ArrayList<Card>> cards = table.getCards();
        if ((affectedRow == TABLE_ROW_INDEX_3 && cards.get(TABLE_ROW_INDEX_0).size()
                                                                    < MAX_NR_OF_CARDS_PER_TABLE_ROW)
                || (affectedRow == TABLE_ROW_INDEX_2 && cards.get(TABLE_ROW_INDEX_1).size()
                                                                    < MAX_NR_OF_CARDS_PER_TABLE_ROW)
                || (affectedRow == TABLE_ROW_INDEX_1 && cards.get(TABLE_ROW_INDEX_2).size()
                                                                    < MAX_NR_OF_CARDS_PER_TABLE_ROW)
                || (affectedRow == TABLE_ROW_INDEX_0 && cards.get(TABLE_ROW_INDEX_3).size()
                                                                < MAX_NR_OF_CARDS_PER_TABLE_ROW)) {
            return true;
        }
        return false;
    }

    /**
     * Method used for actioning an Environment card.
     *<pre>
     *     * checks if the card is of type "Environment"
     *     * checks if the player has enough mana to use it
     *     * checks if the affected row belongs to the enemy
     *</pre>
     * After the card is used, it is removed from player's hand
     * and the player's mana is reduced.
     * The abilities that may be used are the following:
     * <pre>
     *      * Firestorm - subtracts 1 units of health for every minion from a table row
     *      * Winterfell - freezes all cards from a row
     *      * Heart Hound - steals the minion with the highest health from a table row
     * </pre>
     *
     * @param game current game in progress
     * @param command
     * @param handIdx index of card to place from hand
     * @param affectedRow table row that is interrogated
     * @return JSON object node
     */
    public static ObjectNode useEnvironmentCard(final Game game, final String command,
                                                final int handIdx, final int affectedRow) {
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
                actionObj.put("error", "Cannot steal enemy card since"
                                + " the player's row is full.");
                return actionObj;
            }
        } else {
            card.useEnvironmentAbility(table, affectedRow);
        }

        player.getHand().getCards().remove(handIdx);
        player.setMana(player.getMana() - card.getMana());
        return null;
    }

    /**
     * Helper method to check if the enemy has any tanks on the table.
     *
     * @param table game table
     * @param frontRow index of the front row (1 or 2)
     * @return true or false
     */
    public static boolean enemyHasTank(final Table table, final int frontRow) {
        for (Card card : table.getCards().get(frontRow)) {
            if (card.getSpecificType().equals("FrontTankMinion")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper methods add the coordinates of a card to JSON object.
     *
     * @param actionObj JSON object to add the coordinates
     * @param attackerCoord coordinates of the attacker card
     * @param attackedCoord coordinates of the attacked card
     */
    public static void addCoordinatesOutput(final ObjectNode actionObj,
                                            final Coordinates attackerCoord,
                                            final Coordinates attackedCoord) {
        ObjectNode attackerObj = (new ObjectMapper()).createObjectNode();
        ObjectNode attackedObj = (new ObjectMapper()).createObjectNode();

        attackerObj.put("x", attackerCoord.getX());
        attackerObj.put("y", attackerCoord.getY());
        attackedObj.put("x", attackedCoord.getX());
        attackedObj.put("y", attackedCoord.getY());

        actionObj.set("cardAttacker", attackerObj);
        actionObj.set("cardAttacked", attackedObj);
    }

    /**
     * Helper method to check if the coordinates of the given card are valid.
     *
     * @param game current game in progress
     * @param coordinates of the interrogated card
     * @return true or false
     */
    public static boolean checkExistenceOfCard(final Game game, final Coordinates coordinates) {
        return game.getTable().getCards().get(coordinates.getX()).size() <= coordinates.getY();
    }

    /**
     * Helper method to check if the attacked card belongs to the enemy.
     *
     * @param attackerCoord coordinates of the attacker card
     * @param attackedCoord coordinates of the attacked card
     * @return true or false
     */
    public static boolean checkAttackedCardIsFromEnemy(final Coordinates attackerCoord,
                                                       final Coordinates attackedCoord) {
        return (attackerCoord.getX() < 2 && attackedCoord.getX() < 2)
                || (attackerCoord.getX() > 1 && attackedCoord.getX() > 1);
    }

    /**
     * Method used for "usesAttack" and "usesAbility" commands.
     * Function used only for minions.
     * <pre>
     *     * checks if the given cards are valid
     *     * checks if the attacker is frozen or not
     *     * checks if the attacker has already attacked this turn
     *     * checks type of action and type of attacker card
     *     * checks if enemy has any tanks</pre>
     * <pre>
     * The attacks work as the following: attacked card's health
     * is reduced with the attacker's attack damage.
     * </pre>
     * The abilities that may be used are the following:
     * <pre>
     *     * Weak Knees - The Ripper - subtracts 2 units of attack damage for an enemy minion
     *     * Skyjack - Miraj - swap attacker's health with attacked card's health
     *     * Shapeshift - The Cursed One - swap attacked card's health with its attack damage
     *     * God's Plan - Disicple - adds 2 units of health to an allied minion</pre>
     *
     * @param game current game in progress
     * @param command
     * @param attackerCoord coordinates of the attacker card
     * @param attackedCoord coordinates of the attacked card
     * @param typeOfAction "usesAttack" or "usesAbility"
     * @return JSON object node
     */
    public static ObjectNode cardAction(final Game game, final String command,
                                        final Coordinates attackerCoord,
                                        final Coordinates attackedCoord,
                                        final String typeOfAction) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);

        addCoordinatesOutput(actionObj, attackerCoord, attackedCoord);

        if (checkExistenceOfCard(game, attackerCoord)
                || checkExistenceOfCard(game, attackedCoord)) {
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

            int backAttackedRow = TABLE_ROW_INDEX_3;
            int frontAttackedRow = TABLE_ROW_INDEX_2;
            if (game.getTurn() == 1) {
                backAttackedRow = TABLE_ROW_INDEX_0;
                frontAttackedRow = TABLE_ROW_INDEX_1;
            }

            if (enemyHasTank(table, frontAttackedRow)
                    && !cardAttacked.getSpecificType().equals("FrontTankMinion")) {
                actionObj.put("error", "Attacked card is not of type 'Tank'.");
                return actionObj;
            }

            if (typeOfAction.equals("usesAttack")) {
                cardAttacker.attack(cardAttacked, table, attackedCoord.getX(),
                                                            attackedCoord.getY());
            } else {
                cardAttacker.useMinionAbility(cardAttacked);

                if (cardAttacked.getHealth() == 0) {
                    table.getCards().get(attackedCoord.getX()).remove(attackedCoord.getY());
                }
            }
        } else {
            if (!checkAttackedCardIsFromEnemy(attackerCoord, attackedCoord)) {
                actionObj.put("error", "Attacked card does not"
                        + " belong to the current player.");
                return actionObj;
            }
            cardAttacker.useMinionAbility(cardAttacked);
        }

        return null;
    }

    /**
     * Method used for "useAttackHero" command.
     * <pre>
     *     * checks if the given cards are valid
     *     * checks if the attacker is frozen or not
     *     * checks if enemy has tank
     *     * checks enemy has any tanks</pre>
     * This function works as a simple "useAttack" method.
     * If hero's health gets below 0, then he dies and the current
     * player wins the game.
     * @param game current game in progress
     * @param command
     * @param attackerCoord coordinates of the attacker card
     * @return JSON object node
     */
    public static ObjectNode useAttackHero(final Game game, final String command,
                                           final Coordinates attackerCoord) {
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
        if (game.getTurn() == 2) {
            frontAttackedRow = 2;
        }

        if (enemyHasTank(game.getTable(), frontAttackedRow)) {
            actionObj.put("error", "Attacked card is not of type 'Tank'.");
            return actionObj;
        }

        Player attackedPlayer;
        if (game.getTurn() == 1) {
            attackedPlayer = game.getPlayers().get(1);
        } else {
            attackedPlayer = game.getPlayers().get(0);
        }

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

    /**
     * Method used for "useHeroAbility" command.
     * <pre>
     *     * checks if there is enough mana for the player to use his Hero's ability
     *     * checks if the Hero card already used his ability this turn
     *     * checks type of Hero</pre>
     * In the end, the player's mana is reduced with the cost of using the Hero's
     * ability.
     * <pre>
     * The abilities that may be used are the following:</pre>
     * <pre>
     *       * Sub-Zero - Lord Royce - freezes the minion with the biggest attack damage from a
     *       table row
     *       * Low Blow - Empress Thorina - destroys the enemy card with the highest health from a
     *       table row
     *       * Earth Born - King Mudface - applied on ally table row and increases minions' health
     *       with 1 unit
     *       * Blood Thirst - General Kocioraw - applied on ally table row and increases minion's
     *       attack damage with 1 unit</pre>
     *
     * @param game current game in progress
     * @param command
     * @param affectedRow table row that is interrogated
     * @return JSON object node
     */
    public static ObjectNode useHeroAbility(final Game game, final String command,
                                            final int affectedRow) {
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

        if (heroCard.getName().equals("Lord Royce")
                || heroCard.getName().equals("Empress Thorina")) {
            if ((currentPlayer == 1 && affectedRow > 1)
                || (currentPlayer == 2 && affectedRow < 2)) {
                actionObj.put("error", "Selected row does not belong to the enemy.");
                return actionObj;
            }
        } else {
            if ((currentPlayer == 1 && affectedRow < 2)
                    || (currentPlayer == 2 && affectedRow > 1)) {
                actionObj.put("error", "Selected row does not"
                        + " belong to the current player.");
                return actionObj;
            }
        }
        heroCard.useHeroAbility(game.getTable(), affectedRow);

        heroCard.setHasAttacked(true);
        player.setMana(player.getMana() - heroCard.getMana());

        return null;
    }
}

