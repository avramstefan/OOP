package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;

public final class StatisticsCommands {

    private StatisticsCommands() {
    }

    /**
     * Gets the number of wins that a player has achieved in total.
     *
     * @param command
     * @return JSON object node
     */
    public static ObjectNode getPlayerWins(final String command) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);

        if (command.equals("getPlayerOneWins")) {
            actionObj.put("output", Game.getPlayerOneWins());
        } else {
            actionObj.put("output", Game.getPlayerTwoWins());
        }

        return actionObj;
    }

    /**
     * Gets the total number of games two players had.
     *
     * @param command
     * @return JSON object node
     */
    public static ObjectNode getTotalGamesPlayed(final String command) {
        ObjectNode actionObj = (new ObjectMapper()).createObjectNode();
        actionObj.put("command", command);
        actionObj.put("output", Game.getTotalGamesPlayed());
        return actionObj;
    }
}
