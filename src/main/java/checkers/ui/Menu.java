package checkers.ui;

import checkers.gameplay.Game;
import checkers.gameplay.RulesSet;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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
        VBox mainMenuLayout = new VBox();

        AnchorPane menuLabelContainer = new AnchorPane();
        menuLabelContainer.setMinHeight(10.0);
        menuLabelContainer.setPrefHeight(100.0);
        menuLabelContainer.setMinWidth(0);
        menuLabelContainer.setPrefWidth(160.0);
        Label menuLabel = new Label("MENU");
        menuLabel.setAlignment(Pos.CENTER);
        menuLabel.setContentDisplay(ContentDisplay.CENTER);
        menuLabelContainer.getChildren().add(menuLabel);
        AnchorPane.setBottomAnchor(menuLabel, 20.0);
        AnchorPane.setTopAnchor(menuLabel, 20.0);
        AnchorPane.setLeftAnchor(menuLabel, 20.0);
        AnchorPane.setRightAnchor(menuLabel, 20.0);

        AnchorPane menuButtonsSection = new AnchorPane();
        VBox buttonsSection = new VBox();
        buttonsSection.setPrefHeight(0.0);
        buttonsSection.setPrefWidth(280.0);
        buttonsSection.setSpacing(50.0);
        buttonsSection.setPadding(new Insets(50.0));
        AnchorPane.setBottomAnchor(buttonsSection, 0.0);
        AnchorPane.setTopAnchor(buttonsSection, 0.0);
        AnchorPane.setLeftAnchor(buttonsSection, 0.0);
        AnchorPane.setRightAnchor(buttonsSection, 0.0);

        AnchorPane startGameButtonContainer = new AnchorPane();
        startGameButtonContainer.setPrefHeight(50.0);
        startGameButtonContainer.setPrefWidth(300.0);
        Button startGameButton = new Button("Start new game");
        startGameButton.setAlignment(Pos.CENTER);
        startGameButton.setContentDisplay(ContentDisplay.CENTER);
        AnchorPane.setBottomAnchor(startGameButton, 0.0);
        AnchorPane.setTopAnchor(startGameButton, 0.0);
        AnchorPane.setLeftAnchor(startGameButton, 0.0);
        AnchorPane.setRightAnchor(startGameButton, 0.0);
        startGameButton.setOnAction(e -> newGame());
        startGameButtonContainer.getChildren().add(startGameButton);
        buttonsSection.getChildren().add(startGameButtonContainer);

        AnchorPane loadGameButtonContainer = new AnchorPane();
        loadGameButtonContainer.setPrefHeight(50.0);
        loadGameButtonContainer.setPrefWidth(300.0);
        Button loadGameButton = new Button("Load game");
        loadGameButton.setAlignment(Pos.CENTER);
        loadGameButton.setContentDisplay(ContentDisplay.CENTER);
        AnchorPane.setBottomAnchor(loadGameButton, 0.0);
        AnchorPane.setTopAnchor(loadGameButton, 0.0);
        AnchorPane.setLeftAnchor(loadGameButton, 0.0);
        AnchorPane.setRightAnchor(loadGameButton, 0.0);
        loadGameButton.setOnAction(e -> loadGame());
        loadGameButtonContainer.getChildren().add(loadGameButton);
        buttonsSection.getChildren().add(loadGameButtonContainer);

        AnchorPane showRulesButtonContainer = new AnchorPane();
        showRulesButtonContainer.setPrefHeight(50.0);
        showRulesButtonContainer.setPrefWidth(300.0);
        Button showRulesButton = new Button("Show rules sets");
        showRulesButton.setAlignment(Pos.CENTER);
        showRulesButton.setContentDisplay(ContentDisplay.CENTER);
        AnchorPane.setBottomAnchor(showRulesButton, 0.0);
        AnchorPane.setTopAnchor(showRulesButton, 0.0);
        AnchorPane.setLeftAnchor(showRulesButton, 0.0);
        AnchorPane.setRightAnchor(showRulesButton, 0.0);
        showRulesButton.setOnAction(e -> printRulesSets());
        showRulesButtonContainer.getChildren().add(showRulesButton);
        buttonsSection.getChildren().add(showRulesButtonContainer);

        AnchorPane exitButtonContainer = new AnchorPane();
        exitButtonContainer.setPrefHeight(50.0);
        exitButtonContainer.setPrefWidth(300.0);
        Button exitButton = new Button("Exit");
        exitButton.setAlignment(Pos.CENTER);
        exitButton.setContentDisplay(ContentDisplay.CENTER);
        AnchorPane.setBottomAnchor(exitButton, 0.0);
        AnchorPane.setTopAnchor(exitButton, 0.0);
        AnchorPane.setLeftAnchor(exitButton, 0.0);
        AnchorPane.setRightAnchor(exitButton, 0.0);
        exitButton.setOnAction(e -> {
            try {
                exit();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        exitButtonContainer.getChildren().add(exitButton);
        buttonsSection.getChildren().add(exitButtonContainer);

        menuButtonsSection.getChildren().add(buttonsSection);
        mainMenuLayout.getChildren().addAll(menuLabelContainer, menuButtonsSection);

        Scene mainMenu = new Scene(mainMenuLayout, 300, 600);
        Window.getWindow().setTitle("Checkers - main menu");
        Window.getWindow().setScene(mainMenu);
        Window.getWindow().setOnCloseRequest(e -> {
            try {
                exit();
            } catch (IOException exception) {
                exception.printStackTrace();
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
                    Game gameToLoad = new Game(game.getValue(), this);
                    gameToLoad.play(inGameUI);
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
    }

    private void newGame() {
        VBox newGameLayout = new VBox();

        AnchorPane chosenRulesSection = new AnchorPane();
        VBox selectedRulesDetailsSection = new VBox();
        AnchorPane.setBottomAnchor(selectedRulesDetailsSection, 0.0);
        AnchorPane.setTopAnchor(selectedRulesDetailsSection, 0.0);
        AnchorPane.setLeftAnchor(selectedRulesDetailsSection, 0.0);
        AnchorPane.setRightAnchor(selectedRulesDetailsSection, 0.0);
        AnchorPane chosenRulesLabelContainer = new AnchorPane();
        Label chosenRulesLabel = new Label("CHOSEN RULES SET");
        chosenRulesLabel.setAlignment(Pos.CENTER);
        chosenRulesLabel.setContentDisplay(ContentDisplay.CENTER);
        chosenRulesLabelContainer.getChildren().add(chosenRulesLabel);
        AnchorPane.setLeftAnchor(chosenRulesLabel, 20.0);
        AnchorPane.setRightAnchor(chosenRulesLabel, 20.0);
        AnchorPane.setTopAnchor(chosenRulesLabel, 20.0);
        AnchorPane.setBottomAnchor(chosenRulesLabel, 20.0);
        AnchorPane rulesDetailsSection = new AnchorPane();
        VBox rulesDetailsContainer = new VBox(20);
        AnchorPane.setBottomAnchor(rulesDetailsContainer, 0.0);
        AnchorPane.setTopAnchor(rulesDetailsContainer, 0.0);
        AnchorPane.setLeftAnchor(rulesDetailsContainer, 0.0);
        AnchorPane.setRightAnchor(rulesDetailsContainer, 0.0);
        rulesDetailsSection.getChildren().add(rulesDetailsContainer);

        HBox rulesRow1 = new HBox();
        AnchorPane rulesRow1Col1LabelContainer = new AnchorPane();
        rulesRow1Col1LabelContainer.setPadding(new Insets(0, 20, 0, 0));
        Label rulesRow1Col1Label = new Label("Rules name:");
        rulesRow1Col1Label.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(rulesRow1Col1Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow1Col1Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow1Col1Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow1Col1Label, 0.0);
        rulesRow1Col1LabelContainer.getChildren().add(rulesRow1Col1Label);
        AnchorPane rulesRow1Col2LabelContainer = new AnchorPane();
        Label rulesRow1Col2Label = new Label("no rules selected");
        rulesRow1Col2Label.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(rulesRow1Col2Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow1Col2Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow1Col2Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow1Col2Label, 0.0);
        rulesRow1Col2LabelContainer.getChildren().add(rulesRow1Col2Label);
        rulesRow1.getChildren().addAll(rulesRow1Col1LabelContainer, rulesRow1Col2LabelContainer);

        HBox rulesRow2 = new HBox();
        AnchorPane rulesRow2Col1LabelContainer = new AnchorPane();
        rulesRow2Col1LabelContainer.setPadding(new Insets(0, 20, 0, 0));
        Label rulesRow2Col1Label = new Label("Rules description:");
        rulesRow2Col1Label.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(rulesRow2Col1Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow2Col1Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow2Col1Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow2Col1Label, 0.0);
        rulesRow2Col1LabelContainer.getChildren().add(rulesRow2Col1Label);
        AnchorPane rulesRow2Col2LabelContainer = new AnchorPane();
        Label rulesRow2Col2Label = new Label("no rules selected");
        rulesRow2Col2Label.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(rulesRow2Col2Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow2Col2Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow2Col2Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow2Col2Label, 0.0);
        rulesRow2Col2LabelContainer.getChildren().add(rulesRow2Col2Label);
        rulesRow2.getChildren().addAll(rulesRow2Col1LabelContainer, rulesRow2Col2LabelContainer);

        HBox rulesRow3 = new HBox();
        AnchorPane rulesRow3Col1LabelContainer = new AnchorPane();
        rulesRow3Col1LabelContainer.setPadding(new Insets(0, 20, 0, 0));
        Label rulesRow3Col1Label = new Label("Victory conditions:");
        rulesRow3Col1Label.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(rulesRow3Col1Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow3Col1Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow3Col1Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow3Col1Label, 0.0);
        rulesRow3Col1LabelContainer.getChildren().add(rulesRow3Col1Label);
        AnchorPane rulesRow3Col2LabelContainer = new AnchorPane();
        Label rulesRow3Col2Label = new Label("no rules selected");
        rulesRow3Col2Label.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(rulesRow3Col2Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow3Col2Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow3Col2Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow3Col2Label, 0.0);
        rulesRow3Col2LabelContainer.getChildren().add(rulesRow3Col2Label);
        rulesRow3.getChildren().addAll(rulesRow3Col1LabelContainer, rulesRow3Col2LabelContainer);

        HBox rulesRow4 = new HBox();
        AnchorPane rulesRow4Col1LabelContainer = new AnchorPane();
        rulesRow4Col1LabelContainer.setPadding(new Insets(0, 20, 0, 0));
        Label rulesRow4Col1Label = new Label("Capture:");
        rulesRow4Col1Label.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(rulesRow4Col1Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow4Col1Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow4Col1Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow4Col1Label, 0.0);
        rulesRow4Col1LabelContainer.getChildren().add(rulesRow4Col1Label);
        AnchorPane rulesRow4Col2LabelContainer = new AnchorPane();
        Label rulesRow4Col2Label = new Label("no rules selected");
        rulesRow4Col2Label.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(rulesRow4Col2Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow4Col2Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow4Col2Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow4Col2Label, 0.0);
        rulesRow4Col2LabelContainer.getChildren().add(rulesRow4Col2Label);
        rulesRow4.getChildren().addAll(rulesRow4Col1LabelContainer, rulesRow4Col2LabelContainer);

        HBox rulesRow5 = new HBox();
        AnchorPane rulesRow5Col1LabelContainer = new AnchorPane();
        rulesRow5Col1LabelContainer.setPadding(new Insets(0, 20, 0, 0));
        Label rulesRow5Col1Label = new Label("Men move backward:");
        rulesRow5Col1Label.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(rulesRow5Col1Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow5Col1Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow5Col1Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow5Col1Label, 0.0);
        rulesRow5Col1LabelContainer.getChildren().add(rulesRow5Col1Label);
        AnchorPane rulesRow5Col2LabelContainer = new AnchorPane();
        Label rulesRow5Col2Label = new Label("no rules selected");
        rulesRow5Col2Label.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(rulesRow5Col2Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow5Col2Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow5Col2Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow5Col2Label, 0.0);
        rulesRow5Col2LabelContainer.getChildren().add(rulesRow5Col2Label);
        rulesRow5.getChildren().addAll(rulesRow5Col1LabelContainer, rulesRow5Col2LabelContainer);

        HBox rulesRow6 = new HBox();
        AnchorPane rulesRow6Col1LabelContainer = new AnchorPane();
        rulesRow6Col1LabelContainer.setPadding(new Insets(0, 20, 0, 0));
        Label rulesRow6Col1Label = new Label("Men capture backward:");
        rulesRow6Col1Label.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(rulesRow6Col1Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow6Col1Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow6Col1Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow6Col1Label, 0.0);
        rulesRow6Col1LabelContainer.getChildren().add(rulesRow6Col1Label);
        AnchorPane rulesRow6Col2LabelContainer = new AnchorPane();
        Label rulesRow6Col2Label = new Label("no rules selected");
        rulesRow6Col2Label.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(rulesRow6Col2Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow6Col2Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow6Col2Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow6Col2Label, 0.0);
        rulesRow6Col2LabelContainer.getChildren().add(rulesRow6Col2Label);
        rulesRow6.getChildren().addAll(rulesRow6Col1LabelContainer, rulesRow6Col2LabelContainer);

        HBox rulesRow7 = new HBox();
        AnchorPane rulesRow7Col1LabelContainer = new AnchorPane();
        rulesRow7Col1LabelContainer.setPadding(new Insets(0, 20, 0, 0));
        Label rulesRow7Col1Label = new Label("King range:");
        rulesRow7Col1Label.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(rulesRow7Col1Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow7Col1Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow7Col1Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow7Col1Label, 0.0);
        rulesRow7Col1LabelContainer.getChildren().add(rulesRow7Col1Label);
        AnchorPane rulesRow7Col2LabelContainer = new AnchorPane();
        Label rulesRow7Col2Label = new Label("no rules selected");
        rulesRow7Col2Label.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(rulesRow7Col2Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow7Col2Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow7Col2Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow7Col2Label, 0.0);
        rulesRow7Col2LabelContainer.getChildren().add(rulesRow7Col2Label);
        rulesRow7.getChildren().addAll(rulesRow7Col1LabelContainer, rulesRow7Col2LabelContainer);

        HBox rulesRow8 = new HBox();
        AnchorPane rulesRow8Col1LabelContainer = new AnchorPane();
        rulesRow8Col1LabelContainer.setPadding(new Insets(0, 20, 0, 0));
        Label rulesRow8Col1Label = new Label("King move after capture:");
        rulesRow8Col1Label.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(rulesRow8Col1Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow8Col1Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow8Col1Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow8Col1Label, 0.0);
        rulesRow8Col1LabelContainer.getChildren().add(rulesRow8Col1Label);
        AnchorPane rulesRow8Col2LabelContainer = new AnchorPane();
        Label rulesRow8Col2Label = new Label("no rules selected");
        rulesRow8Col2Label.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(rulesRow8Col2Label, 0.0);
        AnchorPane.setTopAnchor(rulesRow8Col2Label, 0.0);
        AnchorPane.setLeftAnchor(rulesRow8Col2Label, 0.0);
        AnchorPane.setRightAnchor(rulesRow8Col2Label, 0.0);
        rulesRow8Col2LabelContainer.getChildren().add(rulesRow8Col2Label);
        rulesRow8.getChildren().addAll(rulesRow8Col1LabelContainer, rulesRow8Col2LabelContainer);

        rulesDetailsContainer.getChildren().addAll(
                rulesRow1,
                rulesRow2,
                rulesRow3,
                rulesRow4,
                rulesRow5,
                rulesRow6,
                rulesRow7,
                rulesRow8
        );
        selectedRulesDetailsSection.getChildren().addAll(chosenRulesLabelContainer, rulesDetailsSection);

        AnchorPane createNewGameLabelContainer = new AnchorPane();
        Label createNewGameLabel = new Label("CREATE NEW GAME");
        createNewGameLabel.setAlignment(Pos.CENTER);
        createNewGameLabel.setContentDisplay(ContentDisplay.CENTER);
        createNewGameLabelContainer.getChildren().add(createNewGameLabel);
        AnchorPane.setBottomAnchor(createNewGameLabel, 20.0);
        AnchorPane.setTopAnchor(createNewGameLabel, 20.0);
        AnchorPane.setLeftAnchor(createNewGameLabel, 20.0);
        AnchorPane.setRightAnchor(createNewGameLabel, 20.0);

        HBox createNewGamePanel = new HBox();

        AnchorPane newGameSettingsSection = new AnchorPane();
        VBox newGameSettingsContainer = new VBox(20);
        AnchorPane.setBottomAnchor(newGameSettingsContainer, 0.0);
        AnchorPane.setTopAnchor(newGameSettingsContainer, 0.0);
        AnchorPane.setLeftAnchor(newGameSettingsContainer, 0.0);
        AnchorPane.setRightAnchor(newGameSettingsContainer, 0.0);

        HBox gameNameSection = new HBox();
        AnchorPane gameNameLabelContainer = new AnchorPane();
        gameNameLabelContainer.setPadding(new Insets(0, 20, 0, 0));
        Label gameNameLabel = new Label("Game name:");
        gameNameLabel.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(gameNameLabel, 0.0);
        AnchorPane.setTopAnchor(gameNameLabel, 0.0);
        AnchorPane.setLeftAnchor(gameNameLabel, 0.0);
        AnchorPane.setRightAnchor(gameNameLabel, 0.0);
        gameNameLabelContainer.getChildren().add(gameNameLabel);
        AnchorPane gameNameValueContainer = new AnchorPane();
        TextField gameNameInput = new TextField();
        AnchorPane.setBottomAnchor(gameNameInput, 0.0);
        AnchorPane.setTopAnchor(gameNameInput, 0.0);
        AnchorPane.setLeftAnchor(gameNameInput, 0.0);
        AnchorPane.setRightAnchor(gameNameInput, 0.0);
        gameNameValueContainer.getChildren().add(gameNameInput);
        gameNameSection.getChildren().addAll(gameNameLabelContainer, gameNameValueContainer);

        HBox rulesSection = new HBox();
        AnchorPane rulesLabelContainer = new AnchorPane();
        rulesLabelContainer.setPadding(new Insets(0, 20, 0, 0));
        Label rulesLabel = new Label("Rules set:");
        rulesLabel.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(rulesLabel, 0.0);
        AnchorPane.setTopAnchor(rulesLabel, 0.0);
        AnchorPane.setLeftAnchor(rulesLabel, 0.0);
        AnchorPane.setRightAnchor(rulesLabel, 0.0);
        rulesLabelContainer.getChildren().add(rulesLabel);
        AnchorPane rulesValueContainer = new AnchorPane();
        ChoiceBox<String> rulesChooser = new ChoiceBox<>();
        for (RulesSet rule : rules) rulesChooser.getItems().add(rule.getName());
        rulesChooser.setOnAction(e -> {
            for (RulesSet rulesSet : rules) {
                if (rulesChooser.getValue().equals(rulesSet.getName())) {
                    rulesRow1Col2Label.setText(rulesSet.getName());
                    rulesRow2Col2Label.setText(rulesSet.getDescription());
                    rulesRow3Col2Label.setText(rulesSet.isVictoryConditionsReversed() ? "reversed" : "standard");
                    rulesRow4Col2Label.setText(rulesSet.isCaptureAny() ? "any" : "longest");
                    rulesRow5Col2Label.setText(rulesSet.isPawnMoveBackward() ? "yes" : "no");
                    rulesRow6Col2Label.setText(rulesSet.isPawnCaptureBackward() ? "yes" : "no");
                    rulesRow7Col2Label.setText(rulesSet.isQueenRangeOne() ? "one field" : "any");
                    rulesRow8Col2Label.setText(rulesSet.isQueenRangeOneAfterCapture() ? "next field" : "any");
                    break;
                }
            }
        });
        AnchorPane.setBottomAnchor(rulesChooser, 0.0);
        AnchorPane.setTopAnchor(rulesChooser, 0.0);
        AnchorPane.setLeftAnchor(rulesChooser, 0.0);
        AnchorPane.setRightAnchor(rulesChooser, 0.0);
        rulesValueContainer.getChildren().add(rulesChooser);
        rulesSection.getChildren().addAll(rulesLabelContainer, rulesValueContainer);

        HBox whitePlayerSection = new HBox();
        AnchorPane whitePlayerLabelContainer = new AnchorPane();
        whitePlayerLabelContainer.setPadding(new Insets(0, 20, 0, 0));
        Label whitePlayerLabel = new Label("White player:");
        whitePlayerLabel.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(whitePlayerLabel, 0.0);
        AnchorPane.setTopAnchor(whitePlayerLabel, 0.0);
        AnchorPane.setLeftAnchor(whitePlayerLabel, 0.0);
        AnchorPane.setRightAnchor(whitePlayerLabel, 0.0);
        whitePlayerLabelContainer.getChildren().add(whitePlayerLabel);
        AnchorPane whitePlayerValueContainer = new AnchorPane();
        ChoiceBox<String> whitePlayerChooser = new ChoiceBox<>();
        whitePlayerChooser.getItems().addAll("Human", "Computer");
        AnchorPane.setBottomAnchor(whitePlayerChooser, 0.0);
        AnchorPane.setTopAnchor(whitePlayerChooser, 0.0);
        AnchorPane.setLeftAnchor(whitePlayerChooser, 0.0);
        AnchorPane.setRightAnchor(whitePlayerChooser, 0.0);
        whitePlayerValueContainer.getChildren().add(whitePlayerChooser);
        whitePlayerSection.getChildren().addAll(whitePlayerLabelContainer, whitePlayerValueContainer);

        HBox blackPlayerSection = new HBox();
        AnchorPane blackPlayerLabelContainer = new AnchorPane();
        blackPlayerLabelContainer.setPadding(new Insets(0, 20, 0, 0));
        Label blackPlayerLabel = new Label("Black player:");
        blackPlayerLabel.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(blackPlayerLabel, 0.0);
        AnchorPane.setTopAnchor(blackPlayerLabel, 0.0);
        AnchorPane.setLeftAnchor(blackPlayerLabel, 0.0);
        AnchorPane.setRightAnchor(blackPlayerLabel, 0.0);
        blackPlayerLabelContainer.getChildren().add(blackPlayerLabel);
        AnchorPane blackPlayerValueContainer = new AnchorPane();
        ChoiceBox<String> blackPlayerChooser = new ChoiceBox<>();
        blackPlayerChooser.getItems().addAll("Human", "Computer");
        AnchorPane.setBottomAnchor(blackPlayerChooser, 0.0);
        AnchorPane.setTopAnchor(blackPlayerChooser, 0.0);
        AnchorPane.setLeftAnchor(blackPlayerChooser, 0.0);
        AnchorPane.setRightAnchor(blackPlayerChooser, 0.0);
        blackPlayerValueContainer.getChildren().add(blackPlayerChooser);
        blackPlayerSection.getChildren().addAll(blackPlayerLabelContainer, blackPlayerValueContainer);

        HBox buttonsSection = new HBox();
        AnchorPane backButtonContainer = new AnchorPane();
        Button backButton = new Button("BACK TO MAIN MENU");
        backButton.setOnAction(e -> start());
        AnchorPane.setBottomAnchor(backButton, 0.0);
        AnchorPane.setTopAnchor(backButton, 0.0);
        AnchorPane.setLeftAnchor(backButton, 0.0);
        AnchorPane.setRightAnchor(backButton, 0.0);
        backButtonContainer.getChildren().add(backButton);
        AnchorPane createGameButtonContainer = new AnchorPane();
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
            Game game = new Game(gameName, chosenRulesSet, isBlackAIPlayer, isWhiteAIPlayer, this);
            try {
                game.play(inGameUI);
            } catch (IncorrectMoveException | IncorrectMoveFormat ignored) {
            }
        });
        AnchorPane.setBottomAnchor(createGameButton, 0.0);
        AnchorPane.setTopAnchor(createGameButton, 0.0);
        AnchorPane.setLeftAnchor(createGameButton, 0.0);
        AnchorPane.setRightAnchor(createGameButton, 0.0);
        createGameButtonContainer.getChildren().add(createGameButton);
        buttonsSection.getChildren().addAll(backButtonContainer, createGameButtonContainer);

        newGameSettingsContainer.getChildren().addAll(
                gameNameSection,
                rulesSection,
                whitePlayerSection,
                blackPlayerSection,
                buttonsSection
        );
        newGameSettingsSection.getChildren().add(newGameSettingsContainer);

        chosenRulesSection.getChildren().add(selectedRulesDetailsSection);
        createNewGamePanel.getChildren().addAll(newGameSettingsSection, chosenRulesSection);
        newGameLayout.getChildren().addAll(createNewGameLabelContainer, createNewGamePanel);

        Scene newGame = new Scene(newGameLayout, 900, 600);
        Window.getWindow().setTitle("Checkers - create new game");
        Window.getWindow().setScene(newGame);
    }

    public Map<String, Game> getGames() {
        return games;
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