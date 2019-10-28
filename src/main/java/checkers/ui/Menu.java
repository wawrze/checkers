package checkers.ui;

import checkers.gameplay.Game;
import checkers.gameplay.RulesSet;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {

    private final Map<String, Game> games;
    private final InGameUI inGameUI;
    private List<RulesSet> rules;

    public Menu() throws IOException, ClassNotFoundException {
        games = new HashMap<>();
        rules = new ArrayList<>();
        inGameUI = new InGameUI();
        File file = new File("games.dat");
        if (!file.exists()) {
            boolean isCreated = file.createNewFile();
            if (!isCreated) {
                throw new IOException("Can't create new file!");
            }
            try {
                FileInputStream fis = new FileInputStream(file);
                fis.close();
            } catch (IOException ignored) {
            }
        } else if (file.length() > 0) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream load = new ObjectInputStream(fis);
            Game game;
            do {
                try {
                    game = (Game) load.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    game = null;
                }
                if (game == null)
                    break;
                games.put(game.getName(), game);
            } while (true);
            load.close();
            fis.close();
        }
        file = new File("rules.dat");
        if (!file.exists()) {
            boolean isCreated = file.createNewFile();
            if (!isCreated) {
                throw new IOException("Can't create new file!");
            }
            try {
                FileInputStream fis = new FileInputStream(file);
                fis.close();
            } catch (IOException ignored) {
            }
        } else if (file.length() > 0) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream load = new ObjectInputStream(fis);
            RulesSet rule;
            do {
                rule = (RulesSet) load.readObject();
                rules.add(rule);
            } while (load.available() > 0);
            load.close();
            fis.close();
        }
        if (rules.isEmpty() || rules.size() < 3) {
            rules = new ArrayList<>();
            RulesSet rule = new RulesSet(false, false, false,
                    false, true, false,
                    "classic", "classic (brazilian) draughts");
            rules.add(rule);
            rule = new RulesSet(false, true, true,
                    false, true, true,
                    "english", "english draughts (checkers)");
            rules.add(rule);
            rule = new RulesSet(true, false, false,
                    false, true, false,
                    "poddavki", "standard rules, but reversed victory");
            rules.add(rule);
        }
    }

    private static void clearMenu() {
        STerminal.getInstance().setCursorPosition(0, 15);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 16);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 17);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 18);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 19);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 20);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 21);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 22);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 23);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 24);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 25);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 26);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 27);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 28);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 29);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 30);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 31);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 32);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 33);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().update();
    }

    private void printRulesSets() {
        clearMenu();
        STerminal.getInstance().putStringAtPosition("╔═════════════════════════════════════╦═════════════════════════════════════╦═════════════════════════════════════╗", 0, 19);
        STerminal.getInstance().putStringAtPosition("║ ( )                                 ║ ( )                                 ║ ( )                                 ║", 0, 20);
        STerminal.getInstance().putStringAtPosition("╠═════════════════════════════════════╬═════════════════════════════════════╬═════════════════════════════════════╣", 0, 21);
        STerminal.getInstance().putStringAtPosition("║                                     ║                                     ║                                     ║", 0, 22);
        STerminal.getInstance().putStringAtPosition("╠═════════════════════════════════════╬═════════════════════════════════════╬═════════════════════════════════════╣", 0, 23);
        STerminal.getInstance().putStringAtPosition("║ Victory conditions:                 ║ Victory conditions:                 ║ Victory conditions:                 ║", 0, 24);
        STerminal.getInstance().putStringAtPosition("║ Capture:                            ║ Capture:                            ║ Capture:                            ║", 0, 25);
        STerminal.getInstance().putStringAtPosition("║ Men move backward:                  ║ Men move backward:                  ║ Men move backward:                  ║", 0, 26);
        STerminal.getInstance().putStringAtPosition("║ Men capture backward:               ║ Men capture backward:               ║ Men capture backward:               ║", 0, 27);
        STerminal.getInstance().putStringAtPosition("║ King range:                         ║ King range:                         ║ King range:                         ║", 0, 28);
        STerminal.getInstance().putStringAtPosition("║ King move after capture:            ║ King move after capture:            ║ King move after capture:            ║", 0, 29);
        STerminal.getInstance().putStringAtPosition("╚═════════════════════════════════════╩═════════════════════════════════════╩═════════════════════════════════════╝", 0, 30);
        for (int i = 0; i < 3; i++) {
            STerminal.getInstance().replaceStringAtPosition("\"" + rules.get(i).getName() + "\" rules", 31, (i * 38) + 6, 20);
            STerminal.getInstance().replaceStringAtPosition(rules.get(i).getDescription(), 36, (i * 38) + 2, 22);
            STerminal.getInstance().putStringAtPosition(rules.get(i).isVictoryConditionsReversed() ? "reversed" : "standard", (i * 38) + 27, 24);
            STerminal.getInstance().putStringAtPosition(rules.get(i).isCaptureAny() ? "any" : "longest", (i * 38) + 27, 25);
            STerminal.getInstance().putStringAtPosition(rules.get(i).isPawnMoveBackward() ? "yes" : "no", (i * 38) + 27, 26);
            STerminal.getInstance().putStringAtPosition(rules.get(i).isPawnCaptureBackward() ? "yes" : "no", (i * 38) + 27, 27);
            STerminal.getInstance().putStringAtPosition(rules.get(i).isQueenRangeOne() ? "one field" : "any", (i * 38) + 27, 28);
            STerminal.getInstance().putStringAtPosition(rules.get(i).isQueenRangeOneAfterCapture() ? "next field" : "any", (i * 38) + 27, 29);
        }
        STerminal.getInstance().update();
    }

    public void start() {
        BorderPane mainMenuLayout = new BorderPane();

        Label menuLabel = new Label("MENU");
        StackPane menuLabelContainer = new StackPane();
        menuLabelContainer.getChildren().add(menuLabel);
        menuLabelContainer.setPadding(new Insets(20, 20, 20, 20));
        mainMenuLayout.setTop(menuLabelContainer);

        VBox buttonsMenuSection = new VBox(20);
        Button startGameButton = new Button("Start new game");
        startGameButton.setOnAction(e -> {
            newGame();
        });
        StackPane startGameButtonContainer = new StackPane();
        startGameButtonContainer.getChildren().add(startGameButton);
        Button loadGameButton = new Button("Load game");
        loadGameButton.setOnAction(e -> {
            Game game = loadGame();
            try {
                if (game != null && game.play(inGameUI)) {
                    games.remove(game.getName());
                    games.put(game.getName(), game);
                }
            } catch (IncorrectMoveException | IncorrectMoveFormat ignored) {
            }
        });
        StackPane loadGameButtonContainer = new StackPane();
        loadGameButtonContainer.getChildren().add(loadGameButton);
        Button showRulesButton = new Button("Print rules sets");
        showRulesButton.setOnAction(e -> printRules());
        StackPane showRulesButtonContainer = new StackPane();
        showRulesButtonContainer.getChildren().add(showRulesButton);
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            try {
                exit();
            } catch (IOException ignored) {
            }
        });
        StackPane exitButtonContainer = new StackPane();
        exitButtonContainer.getChildren().add(exitButton);
        buttonsMenuSection.getChildren().addAll(
                menuLabelContainer,
                startGameButtonContainer,
                loadGameButtonContainer,
                showRulesButtonContainer,
                exitButtonContainer
        );
        mainMenuLayout.setCenter(buttonsMenuSection);

        Scene mainMenu = new Scene(mainMenuLayout, 300, 300);
        Window.getWindow().setTitle("Checkers - main menu");
        Window.getWindow().setScene(mainMenu);
        Window.getWindow().setOnCloseRequest(e -> {
            try {
                exit();
            } catch (IOException ignored) {
            }
        });
    }

    private void printRules() {
        System.out.println("PRINT RULES CLICKED");
//        printRulesSets();
//        STerminal.getInstance().putStringAtPosition("╔════════════════════════════════════════════╗", 6, 16);
//        STerminal.getInstance().putStringAtPosition("║ Available rule sets:                       ║", 6, 17);
//        STerminal.getInstance().putStringAtPosition("╚════════════════════════════════════════════╝", 6, 18);
//        STerminal.getInstance().putCharAtPosition(' ', 2, 20);
//        STerminal.getInstance().putCharAtPosition(' ', 4, 20);
//        STerminal.getInstance().putCharAtPosition(' ', 40, 20);
//        STerminal.getInstance().putCharAtPosition(' ', 42, 20);
//        STerminal.getInstance().putCharAtPosition(' ', 78, 20);
//        STerminal.getInstance().putCharAtPosition(' ', 80, 20);
//        printEscapeInfo();
//        STerminal.getInstance().update();
//        KeyStroke key;
//        do {
//            key = STerminal.getInstance().readInput();
//        } while (key.getKeyType() != KeyType.Escape);
    }

    private Game loadGame() {
        System.out.println("LOAD GAME CLICKED");
        return null;
//        clearMenu();
//        STerminal.getInstance().putStringAtPosition("╔════════════════════════════════════════════╗", 6, 15);
//        STerminal.getInstance().putStringAtPosition("║ Choose game:                               ║", 6, 16);
//        STerminal.getInstance().putStringAtPosition("╚════════════════════════════════════════════╝", 6, 17);
//        STerminal.getInstance().putStringAtPosition("╔═════════════════════════════════════════════════════════════╗", 6, 31);
//        STerminal.getInstance().putStringAtPosition("║ ESCAPE - main menu, ENTER - load game, DELETE - remove game ║", 6, 32);
//        STerminal.getInstance().putStringAtPosition("╚═════════════════════════════════════════════════════════════╝", 6, 33);
//
//        int gamesSize = 0;
//        ArrayList<Game> gameList = new ArrayList<>();
//        for (Map.Entry<String, Game> game : games.entrySet()) {
//            gameList.add(game.getValue());
//            gamesSize++;
//            if (gamesSize == 12) break;
//        }
//        for (int i = 0; i < gameList.size(); i++) {
//            STerminal.getInstance().putStringAtPosition("( ) " + gameList.get(i), 6, i + 18);
//        }
//
//        int actualPosition = 0;
//        KeyStroke key;
//        do {
//            for (int i = 0; i < gameList.size(); i++) STerminal.getInstance().putCharAtPosition(' ', 7, i + 18);
//            if (gameList.size() > 0) STerminal.getInstance().putCharAtPosition('●', 7, actualPosition + 18);
//            STerminal.getInstance().update();
//            do {
//                key = STerminal.getInstance().readInput();
//            } while (key == null);
//            if (key.getKeyType() == KeyType.ArrowDown && actualPosition < gameList.size() - 1) {
//                actualPosition++;
//            } else if (key.getKeyType() == KeyType.ArrowUp && actualPosition > 0) {
//                actualPosition--;
//            } else if (key.getKeyType() == KeyType.Delete && gameList.size() > 0) {
//                games.remove(gameList.get(actualPosition).getName());
//                return loadGame();
//            } else if (key.getKeyType() == KeyType.Enter && gameList.size() > 0) {
//                break;
//            } else if (key.getKeyType() == KeyType.Escape) {
//                return null;
//            }
//        } while (true);
//        return new Game(gameList.get(actualPosition));
    }

    private void newGame() {
        BorderPane newGameLayout = new BorderPane();

        Label createNewGameLabel = new Label("CREATE NEW GAME");
        StackPane createNewGameLabelContainer = new StackPane();
        createNewGameLabelContainer.getChildren().add(createNewGameLabel);
        createNewGameLabelContainer.setPadding(new Insets(20, 20, 20, 20));
        newGameLayout.setTop(createNewGameLabelContainer);

        VBox newGameOptionsSection = new VBox(20);

        HBox gameNameSection = new HBox(20);
        Label gameNameLabel = new Label("Game name:");
        TextField gameNameInput = new TextField();
        gameNameSection.getChildren().addAll(gameNameLabel, gameNameInput);

        HBox rulesSection = new HBox(20);
        Label rulesLabel = new Label("Rules set:");
        ChoiceBox<String> rulesChooser = new ChoiceBox<>();
        for (RulesSet rule : rules) {
            rulesChooser.getItems().add(rule.getName());
        }
        rulesChooser.setOnAction(e -> {
            Label chosenRulesName = new Label("Chosen rules: " + rulesChooser.getValue());
            newGameLayout.setRight(chosenRulesName);
        });
        rulesSection.getChildren().addAll(rulesLabel, rulesChooser);
        HBox whitePlayerSection = new HBox(20);
        Label whitePlayerLabel = new Label("White player:");
        ChoiceBox<String> whitePlayerChooser = new ChoiceBox<>();
        whitePlayerChooser.getItems().addAll("Human", "Computer");
        whitePlayerSection.getChildren().addAll(whitePlayerLabel, whitePlayerChooser);
        HBox blackPlayerSection = new HBox(20);
        Label blackPlayerLabel = new Label("Black player:");
        ChoiceBox<String> blackPlayerChooser = new ChoiceBox<>();
        blackPlayerChooser.getItems().addAll("Human", "Computer");
        blackPlayerSection.getChildren().addAll(blackPlayerLabel, blackPlayerChooser);
        HBox buttonsSection = new HBox(20);
        Button backButton = new Button("BACK TO MAIN MENU");
        backButton.setOnAction(e -> start());
        Button createGameButton = new Button("CREATE NEW GAME");
        createGameButton.setOnAction(e -> {
            String gameName = gameNameInput.getText();
            boolean isWhiteAIPlayer = whitePlayerChooser.getValue().equals("Computer");
            boolean isBlackAIPlayer = blackPlayerChooser.getValue().equals("Computer");
            RulesSet chosenRulesSet = rules.get(0);
            for (RulesSet rulesSet : rules) {
                if (rulesChooser.getValue().equals(rulesSet.getName())) {
                    chosenRulesSet = rulesSet;
                    break;
                }
            }
            Game game = new Game(gameName, chosenRulesSet, isBlackAIPlayer, isWhiteAIPlayer);
            try {
                if (game.play(inGameUI)) {
                    games.put(game.getName(), game);
                }
                start();
            } catch (IncorrectMoveException | IncorrectMoveFormat ignored) {
            }
        });
        buttonsSection.getChildren().addAll(backButton, createGameButton);
        newGameOptionsSection.getChildren().addAll(
                createNewGameLabelContainer,
                gameNameSection,
                rulesSection,
                whitePlayerSection,
                blackPlayerSection,
                buttonsSection
        );
        newGameLayout.setCenter(newGameOptionsSection);

        Scene mainMenu = new Scene(newGameLayout, 300, 300);
        Window.getWindow().setTitle("Checkers - create new game");
        Window.getWindow().setScene(mainMenu);
    }

    private void exit() throws IOException {
        FileOutputStream fos = new FileOutputStream("games.dat");
        ObjectOutputStream savedGames = new ObjectOutputStream(fos);
        for (Map.Entry g : games.entrySet()) {
            savedGames.writeObject(g.getValue());
        }
        savedGames.close();
        fos.close();
        fos = new FileOutputStream("rules.dat");
        ObjectOutputStream rulesSets = new ObjectOutputStream(fos);
        for (RulesSet r : rules) {
            rulesSets.writeObject(r);
        }
        rulesSets.close();
        fos.close();
        Window.getWindow().close();
    }

}