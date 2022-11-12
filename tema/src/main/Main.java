package main;

import Decks.Decks;
import GameLogic.Game;
import Players.Player;
import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        DecksInput playerOneDecks = inputData.getPlayerOneDecks();
        DecksInput playerTwoDecks = inputData.getPlayerTwoDecks();
        ArrayList<GameInput> games = inputData.getGames();

        Game.resetScores();
        for (GameInput game : games) {
            game.setGameParameters(inputData);

            Decks newPlayerOneDeck = new Decks(playerOneDecks);
            Decks newPlayerTwoDeck = new Decks(playerTwoDecks);

            Player playerOne = new Player(newPlayerOneDeck, game.getStartGame(), 1, game.getStartGame().getShuffleSeed());
            Player playerTwo = new Player(newPlayerTwoDeck, game.getStartGame(), 2, game.getStartGame().getShuffleSeed());

            Game currentGame = new Game(game.getStartGame(), playerOne, playerTwo, game.getActions());
            currentGame.runGame(output);
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}

/*
class Cards:
    Minion:
        -Sentinel
        -Berserker
        -Goliath
        -Warden
        -Miraj
        -The Ripper
        -Disciple
        -The Cursed One
    Environment
        -Firestorm
        -Winterfell
        -Heart Hound
    Hero
        -Lord Royce
        -Empress Thorina
        -King Mudface
        -General Kocioraw
class Players:
    PlayerOne
    PlayerTwo
class Decks:
class Game:
 */
