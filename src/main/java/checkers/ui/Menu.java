package checkers.ui;

import checkers.gameplay.Game;
import checkers.gameplay.RulesSet;
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

    private void printRulesSets() {
        BorderPane ruleSetsMenu = new BorderPane();

        Label rulesLabel = new Label("RULES SETS");
        StackPane rulesLabelContainer = new StackPane();
        rulesLabelContainer.getChildren().add(rulesLabel);
        rulesLabelContainer.setPadding(new Insets(20, 20, 20, 20));
        ruleSetsMenu.setTop(rulesLabelContainer);

        VBox ruleSetsContainer = new VBox(20);

        for (int i = 0; i < 3; i++) {
            VBox ruleSetContainer = new VBox(20);

            HBox rulesNameContainer = new HBox(20);
            Label rulesNameLabel = new Label("Rules name:");
            Label rulesNameValue = new Label(rules.get(i).getName());
            rulesNameContainer.getChildren().addAll(rulesNameLabel, rulesNameValue);
            HBox rulesDescriptionContainer = new HBox(20);
            Label rulesDescriptionLabel = new Label("Description:");
            Label rulesDescriptionValue = new Label(rules.get(i).getDescription());
            rulesDescriptionContainer.getChildren().addAll(rulesDescriptionLabel, rulesDescriptionValue);
            HBox rulesVictoryContainer = new HBox(20);
            Label rulesVictoryLabel = new Label("Victory conditions:");
            Label rulesVictoryValue = new Label(rules.get(i).isVictoryConditionsReversed() ? "reversed" : "standard");
            rulesVictoryContainer.getChildren().addAll(rulesVictoryLabel, rulesVictoryValue);
            HBox rulesCaptureContainer = new HBox(20);
            Label rulesCaptureLabel = new Label("Capture:");
            Label rulesCaptureValue = new Label(rules.get(i).isCaptureAny() ? "any" : "longest");
            rulesCaptureContainer.getChildren().addAll(rulesCaptureLabel, rulesCaptureValue);
            HBox rulesMenMoveBackwardContainer = new HBox(20);
            Label rulesMenMoveBackwardLabel = new Label("Men move backward:");
            Label rulesMenMoveBackwardValue = new Label(rules.get(i).isPawnMoveBackward() ? "yes" : "no");
            rulesMenMoveBackwardContainer.getChildren().addAll(rulesMenMoveBackwardLabel, rulesMenMoveBackwardValue);
            HBox rulesMenCaptureBackwardContainer = new HBox(20);
            Label rulesMenCaptureBackwardLabel = new Label("Men capture backward:");
            Label rulesMenCaptureBackwardValue = new Label(rules.get(i).isPawnCaptureBackward() ? "yes" : "no");
            rulesMenCaptureBackwardContainer.getChildren().addAll(rulesMenCaptureBackwardLabel, rulesMenCaptureBackwardValue);
            HBox rulesKingRangeContainer = new HBox(20);
            Label rulesKingRangeLabel = new Label("King range:");
            Label rulesKingRangeValue = new Label(rules.get(i).isQueenRangeOne() ? "one field" : "any");
            rulesKingRangeContainer.getChildren().addAll(rulesKingRangeLabel, rulesKingRangeValue);
            HBox rulesKingMoveAfterCaptureContainer = new HBox(20);
            Label rulesKingMoveAfterCaptureLabel = new Label("King move after capture:");
            Label rulesKingMoveAfterCaptureValue = new Label(rules.get(i).isQueenRangeOneAfterCapture() ? "next field" : "any");
            rulesKingMoveAfterCaptureContainer.getChildren().addAll(rulesKingMoveAfterCaptureLabel, rulesKingMoveAfterCaptureValue);

            ruleSetContainer.getChildren().addAll(
                    rulesNameContainer,
                    rulesDescriptionContainer,
                    rulesVictoryContainer,
                    rulesCaptureContainer,
                    rulesMenMoveBackwardContainer,
                    rulesMenCaptureBackwardContainer,
                    rulesKingRangeContainer,
                    rulesKingMoveAfterCaptureContainer
            );
            ruleSetsContainer.getChildren().add(ruleSetContainer);
        }
        ruleSetsMenu.setCenter(ruleSetsContainer);

        Button backButton = new Button("BACK TO MAIN MENU");
        backButton.setOnAction(e -> start());
        ruleSetsMenu.setBottom(backButton);

        Scene rulesSetsMenu = new Scene(ruleSetsMenu, 300, 1000);
        Window.getWindow().setTitle("Checkers - rule sets");
        Window.getWindow().setScene(rulesSetsMenu);
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
        startGameButton.setOnAction(e -> newGame());
        StackPane startGameButtonContainer = new StackPane();
        startGameButtonContainer.getChildren().add(startGameButton);
        Button loadGameButton = new Button("Load game");
        loadGameButton.setOnAction(e -> loadGame());
        StackPane loadGameButtonContainer = new StackPane();
        loadGameButtonContainer.getChildren().add(loadGameButton);
        Button showRulesButton = new Button("Show rules sets");
        showRulesButton.setOnAction(e -> printRulesSets());
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

    private void loadGame() {
        BorderPane loadGameMenu = new BorderPane();

        Label loadGameLabel = new Label("SAVED GAMES");
        StackPane loadGameLabelContainer = new StackPane();
        loadGameLabelContainer.getChildren().add(loadGameLabel);
        loadGameLabelContainer.setPadding(new Insets(20, 20, 20, 20));
        loadGameMenu.setTop(loadGameLabelContainer);

        VBox savedGamesContainer = new VBox(20);

        for (Map.Entry<String, Game> game : games.entrySet()) {
            HBox gameContainer = new HBox(20);
            Label gameLabel = new Label(game.getValue().toString());
            Button loadGameButton = new Button("LOAD");
            loadGameButton.setOnAction(e -> {
                try {
                    if (game.getValue().play(inGameUI)) {
                        games.remove(game.getKey());
                        games.put(game.getKey(), game.getValue());
                    }
                    start();
                } catch (IncorrectMoveException | IncorrectMoveFormat ignored) {
                }
            });
            Button deleteGameButton = new Button("DELETE");
            deleteGameButton.setOnAction(e -> {
                games.remove(game.getKey());
                loadGame();
            });
            gameContainer.getChildren().addAll(gameLabel, loadGameButton, deleteGameButton);

            savedGamesContainer.getChildren().add(gameContainer);
        }
        loadGameMenu.setCenter(savedGamesContainer);

        Button backButton = new Button("BACK TO MAIN MENU");
        backButton.setOnAction(e -> start());
        loadGameMenu.setBottom(backButton);

        Scene rulesSetsMenu = new Scene(loadGameMenu, 300, 1000);
        Window.getWindow().setTitle("Checkers - load game");
        Window.getWindow().setScene(rulesSetsMenu);


        System.out.println("LOAD GAME CLICKED");
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