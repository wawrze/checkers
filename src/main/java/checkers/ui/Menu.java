package checkers.ui;

import checkers.gameplay.Game;
import checkers.gameplay.RulesSet;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
        Pane ruleSetsMenu = new Pane();
        ruleSetsMenu.setStyle("-fx-background-color: #829eae;");

        try {
            ImageView image = new ImageView();
            FileInputStream input = new FileInputStream("images/menu_image.png");
            Image img = new Image(input);
            image.setImage(img);
            image.setX(20);
            image.setY(20);
            ruleSetsMenu.getChildren().add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Label rulesLabel = new Label("RULES SETS");
        rulesLabel.setAlignment(Pos.CENTER);
        rulesLabel.setFont(Font.font(30));
        rulesLabel.setStyle("-fx-font-weight: bold;");
        rulesLabel.setLayoutX(230);
        rulesLabel.setLayoutY(265);
        rulesLabel.setPrefHeight(60);
        rulesLabel.setPrefWidth(200);
        ruleSetsMenu.getChildren().add(rulesLabel);


        for (int i = 0; i < 3; i++) {
            Separator separator1 = new Separator();
            separator1.setOrientation(Orientation.VERTICAL);
            separator1.setLayoutX(10 + (i * 212));
            separator1.setLayoutY(345);
            separator1.setPrefHeight(280);
            ruleSetsMenu.getChildren().add(separator1);

            Separator separator11 = new Separator();
            separator11.setOrientation(Orientation.HORIZONTAL);
            separator11.setLayoutX(12 + (i * 212));
            separator11.setLayoutY(345);
            separator11.setPrefWidth(210);
            ruleSetsMenu.getChildren().add(separator11);

            Separator separator2 = new Separator();
            separator2.setOrientation(Orientation.HORIZONTAL);
            separator2.setLayoutX(12 + (i * 212));
            separator2.setLayoutY(380);
            separator2.setPrefWidth(210);
            ruleSetsMenu.getChildren().add(separator2);

            Separator separator3 = new Separator();
            separator3.setOrientation(Orientation.HORIZONTAL);
            separator3.setLayoutX(12 + (i * 212));
            separator3.setLayoutY(415);
            separator3.setPrefWidth(210);
            ruleSetsMenu.getChildren().add(separator3);

            Separator separator4 = new Separator();
            separator4.setOrientation(Orientation.VERTICAL);
            separator4.setLayoutX(222 + (i * 212));
            separator4.setLayoutY(345);
            separator4.setPrefHeight(280);
            ruleSetsMenu.getChildren().add(separator4);

            Separator separator5 = new Separator();
            separator5.setOrientation(Orientation.HORIZONTAL);
            separator5.setLayoutX(12 + (i * 212));
            separator5.setLayoutY(450);
            separator5.setPrefWidth(210);
            ruleSetsMenu.getChildren().add(separator5);

            Separator separator6 = new Separator();
            separator6.setOrientation(Orientation.HORIZONTAL);
            separator6.setLayoutX(12 + (i * 212));
            separator6.setLayoutY(485);
            separator6.setPrefWidth(210);
            ruleSetsMenu.getChildren().add(separator6);

            Separator separator7 = new Separator();
            separator7.setOrientation(Orientation.HORIZONTAL);
            separator7.setLayoutX(12 + (i * 212));
            separator7.setLayoutY(520);
            separator7.setPrefWidth(210);
            ruleSetsMenu.getChildren().add(separator7);

            Separator separator8 = new Separator();
            separator8.setOrientation(Orientation.HORIZONTAL);
            separator8.setLayoutX(12 + (i * 212));
            separator8.setLayoutY(555);
            separator8.setPrefWidth(210);
            ruleSetsMenu.getChildren().add(separator8);

            Separator separator9 = new Separator();
            separator9.setOrientation(Orientation.HORIZONTAL);
            separator9.setLayoutX(12 + (i * 212));
            separator9.setLayoutY(590);
            separator9.setPrefWidth(210);
            ruleSetsMenu.getChildren().add(separator9);

            Separator separator10 = new Separator();
            separator10.setOrientation(Orientation.HORIZONTAL);
            separator10.setLayoutX(12 + (i * 212));
            separator10.setLayoutY(625);
            separator10.setPrefWidth(210);
            ruleSetsMenu.getChildren().add(separator10);

            Label victoryConditions = new Label("Victory conditions:");
            victoryConditions.setLayoutX(20 + (i * 212));
            victoryConditions.setLayoutY(425);
            victoryConditions.setStyle("-fx-font-weight: bold;");
            ruleSetsMenu.getChildren().add(victoryConditions);

            Label capture = new Label("Capture:");
            capture.setLayoutX(20 + (i * 212));
            capture.setLayoutY(460);
            capture.setStyle("-fx-font-weight: bold;");
            ruleSetsMenu.getChildren().add(capture);

            Label menMoveBackward = new Label("Men move backward:");
            menMoveBackward.setLayoutX(20 + (i * 212));
            menMoveBackward.setLayoutY(495);
            menMoveBackward.setStyle("-fx-font-weight: bold;");
            ruleSetsMenu.getChildren().add(menMoveBackward);

            Label menCaptureBackward = new Label("Men capture backward:");
            menCaptureBackward.setLayoutX(20 + (i * 212));
            menCaptureBackward.setLayoutY(530);
            menCaptureBackward.setStyle("-fx-font-weight: bold;");
            ruleSetsMenu.getChildren().add(menCaptureBackward);

            Label kingRange = new Label("King range:");
            kingRange.setLayoutX(20 + (i * 212));
            kingRange.setLayoutY(565);
            kingRange.setStyle("-fx-font-weight: bold;");
            ruleSetsMenu.getChildren().add(kingRange);

            Label kingMoveAfterCapture = new Label("King move after capture:");
            kingMoveAfterCapture.setLayoutX(20 + (i * 212));
            kingMoveAfterCapture.setLayoutY(600);
            kingMoveAfterCapture.setStyle("-fx-font-weight: bold;");
            ruleSetsMenu.getChildren().add(kingMoveAfterCapture);


            Label rulesNameValue = new Label("\"" + rules.get(i).getName() + "\" rules");
            rulesNameValue.setLayoutX(20 + (i * 212));
            rulesNameValue.setLayoutY(355);
            rulesNameValue.setStyle("-fx-font-weight: bold;");
            rulesNameValue.setTextFill(Color.web("#595959"));
            ruleSetsMenu.getChildren().add(rulesNameValue);

            Label rulesDescriptionValue = new Label(rules.get(i).getDescription());
            rulesDescriptionValue.setLayoutX(20 + (i * 212));
            rulesDescriptionValue.setLayoutY(390);
            rulesDescriptionValue.setStyle("-fx-font-weight: bold;");
            rulesDescriptionValue.setTextFill(Color.web("#595959"));
            ruleSetsMenu.getChildren().add(rulesDescriptionValue);

            Label rulesVictoryValue = new Label(rules.get(i).isVictoryConditionsReversed() ? "reversed" : "standard");
            rulesVictoryValue.setLayoutX(165 + (i * 212));
            rulesVictoryValue.setLayoutY(425);
            rulesVictoryValue.setStyle("-fx-font-weight: bold;");
            rulesVictoryValue.setTextFill(Color.web("#595959"));
            ruleSetsMenu.getChildren().add(rulesVictoryValue);

            Label rulesCaptureValue = new Label(rules.get(i).isCaptureAny() ? "any" : "longest");
            rulesCaptureValue.setLayoutX(165 + (i * 212));
            rulesCaptureValue.setLayoutY(460);
            rulesCaptureValue.setStyle("-fx-font-weight: bold;");
            rulesCaptureValue.setTextFill(Color.web("#595959"));
            ruleSetsMenu.getChildren().add(rulesCaptureValue);

            Label rulesMenMoveBackwardValue = new Label(rules.get(i).isPawnMoveBackward() ? "yes" : "no");
            rulesMenMoveBackwardValue.setLayoutX(165 + (i * 212));
            rulesMenMoveBackwardValue.setLayoutY(495);
            rulesMenMoveBackwardValue.setStyle("-fx-font-weight: bold;");
            rulesMenMoveBackwardValue.setTextFill(Color.web("#595959"));
            ruleSetsMenu.getChildren().add(rulesMenMoveBackwardValue);

            Label rulesMenCaptureBackwardValue = new Label(rules.get(i).isPawnCaptureBackward() ? "yes" : "no");
            rulesMenCaptureBackwardValue.setLayoutX(165 + (i * 212));
            rulesMenCaptureBackwardValue.setLayoutY(530);
            rulesMenCaptureBackwardValue.setStyle("-fx-font-weight: bold;");
            rulesMenCaptureBackwardValue.setTextFill(Color.web("#595959"));
            ruleSetsMenu.getChildren().add(rulesMenCaptureBackwardValue);

            Label rulesKingRangeValue = new Label(rules.get(i).isQueenRangeOne() ? "one field" : "any");
            rulesKingRangeValue.setLayoutX(165 + (i * 212));
            rulesKingRangeValue.setLayoutY(565);
            rulesKingRangeValue.setStyle("-fx-font-weight: bold;");
            rulesKingRangeValue.setTextFill(Color.web("#595959"));
            ruleSetsMenu.getChildren().add(rulesKingRangeValue);

            Label rulesKingMoveAfterCaptureValue = new Label(rules.get(i).isQueenRangeOneAfterCapture() ? "next field" : "any");
            rulesKingMoveAfterCaptureValue.setLayoutX(165 + (i * 212));
            rulesKingMoveAfterCaptureValue.setLayoutY(600);
            rulesKingMoveAfterCaptureValue.setStyle("-fx-font-weight: bold;");
            rulesKingMoveAfterCaptureValue.setTextFill(Color.web("#595959"));
            ruleSetsMenu.getChildren().add(rulesKingMoveAfterCaptureValue);
        }


        Button backButton = new Button("BACK TO MAIN MENU");
        backButton.setOnAction(e -> start());
        backButton.setLayoutX(230);
        backButton.setLayoutY(660);
        backButton.setPrefHeight(60);
        backButton.setPrefWidth(200);
        ruleSetsMenu.getChildren().add(backButton);

        Scene rulesSetsMenu = new Scene(ruleSetsMenu, 660, 740);
        Window.getWindow().setTitle("Checkers - rule sets");
        Window.getWindow().setScene(rulesSetsMenu);
    }

    public void start() {
        Pane mainMenuLayout = new Pane();
        mainMenuLayout.setStyle("-fx-background-color: #829eae;");

        try {
            ImageView image = new ImageView();
            FileInputStream input = new FileInputStream("images/menu_image.png");
            Image img = new Image(input);
            image.setImage(img);
            image.setX(20);
            image.setY(20);
            mainMenuLayout.getChildren().add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button startGameButton = new Button("Start new game");
        startGameButton.setLayoutX(230);
        startGameButton.setLayoutY(300);
        startGameButton.setPrefHeight(60);
        startGameButton.setPrefWidth(200);
        startGameButton.setOnAction(e -> newGame());
        mainMenuLayout.getChildren().add(startGameButton);

        Button loadGameButton = new Button("Load game");
        loadGameButton.setLayoutX(230);
        loadGameButton.setLayoutY(420);
        loadGameButton.setPrefHeight(60);
        loadGameButton.setPrefWidth(200);
        loadGameButton.setOnAction(e -> loadGame());
        mainMenuLayout.getChildren().add(loadGameButton);

        Button showRulesButton = new Button("Show rules sets");
        showRulesButton.setLayoutX(230);
        showRulesButton.setLayoutY(540);
        showRulesButton.setPrefHeight(60);
        showRulesButton.setPrefWidth(200);
        showRulesButton.setOnAction(e -> printRulesSets());
        mainMenuLayout.getChildren().add(showRulesButton);

        Button exitButton = new Button("Exit");
        exitButton.setLayoutX(230);
        exitButton.setLayoutY(660);
        exitButton.setPrefHeight(60);
        exitButton.setPrefWidth(200);
        exitButton.setOnAction(e -> {
            try {
                exit();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        mainMenuLayout.getChildren().add(exitButton);

        Scene mainMenu = new Scene(mainMenuLayout, 660, 740);
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
        Pane loadGameMenu = new Pane();
        loadGameMenu.setStyle("-fx-background-color: #829eae;");

        try {
            ImageView image = new ImageView();
            FileInputStream input = new FileInputStream("images/menu_image.png");
            Image img = new Image(input);
            image.setImage(img);
            image.setX(20);
            image.setY(20);
            loadGameMenu.getChildren().add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Label savedGamesLabel = new Label("SAVED GAMES");
        savedGamesLabel.setAlignment(Pos.CENTER);
        savedGamesLabel.setFont(Font.font(30));
        savedGamesLabel.setStyle("-fx-font-weight: bold;");
        savedGamesLabel.setLayoutX(210);
        savedGamesLabel.setLayoutY(265);
        savedGamesLabel.setPrefHeight(60);
        savedGamesLabel.setPrefWidth(240);
        loadGameMenu.getChildren().add(savedGamesLabel);

        ScrollPane gamesContainer = new ScrollPane();
        gamesContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        gamesContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        gamesContainer.setLayoutX(10);
        gamesContainer.setLayoutY(345);
        gamesContainer.setPrefWidth(640);
        gamesContainer.setPrefHeight(280);
        ListView<HBox> gameList = new ListView<>();
        gameList.setPrefWidth(640);

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
            gameContainer.getChildren().addAll(loadGameButton, deleteGameButton, gameLabel);
            gameList.getItems().add(gameContainer);
        }
        gamesContainer.setContent(gameList);
        loadGameMenu.getChildren().add(gamesContainer);

        Button backButton = new Button("BACK TO MAIN MENU");
        backButton.setOnAction(e -> start());
        backButton.setLayoutX(230);
        backButton.setLayoutY(660);
        backButton.setPrefHeight(60);
        backButton.setPrefWidth(200);
        loadGameMenu.getChildren().add(backButton);

        Scene rulesSetsMenu = new Scene(loadGameMenu, 660, 740);
        Window.getWindow().setTitle("Checkers - load game");
        Window.getWindow().setScene(rulesSetsMenu);
    }

    private void newGame() {
        Pane newGameLayout = new Pane();
        newGameLayout.setStyle("-fx-background-color: #829eae;");

        try {
            ImageView image = new ImageView();
            FileInputStream input = new FileInputStream("images/menu_image.png");
            Image img = new Image(input);
            image.setImage(img);
            image.setX(20);
            image.setY(20);
            newGameLayout.getChildren().add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Label savedGamesLabel = new Label("CREATE NEW GAME");
        savedGamesLabel.setAlignment(Pos.CENTER);
        savedGamesLabel.setFont(Font.font(30));
        savedGamesLabel.setStyle("-fx-font-weight: bold;");
        savedGamesLabel.setLayoutX(180);
        savedGamesLabel.setLayoutY(265);
        savedGamesLabel.setPrefHeight(60);
        savedGamesLabel.setPrefWidth(300);
        newGameLayout.getChildren().add(savedGamesLabel);

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        separator1.setLayoutX(434);
        separator1.setLayoutY(345);
        separator1.setPrefHeight(280);
        newGameLayout.getChildren().add(separator1);

        Separator separator11 = new Separator();
        separator11.setOrientation(Orientation.HORIZONTAL);
        separator11.setLayoutX(436);
        separator11.setLayoutY(345);
        separator11.setPrefWidth(210);
        newGameLayout.getChildren().add(separator11);

        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.HORIZONTAL);
        separator2.setLayoutX(436);
        separator2.setLayoutY(380);
        separator2.setPrefWidth(210);
        newGameLayout.getChildren().add(separator2);

        Separator separator3 = new Separator();
        separator3.setOrientation(Orientation.HORIZONTAL);
        separator3.setLayoutX(436);
        separator3.setLayoutY(415);
        separator3.setPrefWidth(210);
        newGameLayout.getChildren().add(separator3);

        Separator separator4 = new Separator();
        separator4.setOrientation(Orientation.VERTICAL);
        separator4.setLayoutX(646);
        separator4.setLayoutY(345);
        separator4.setPrefHeight(280);
        newGameLayout.getChildren().add(separator4);

        Separator separator5 = new Separator();
        separator5.setOrientation(Orientation.HORIZONTAL);
        separator5.setLayoutX(436);
        separator5.setLayoutY(450);
        separator5.setPrefWidth(210);
        newGameLayout.getChildren().add(separator5);

        Separator separator6 = new Separator();
        separator6.setOrientation(Orientation.HORIZONTAL);
        separator6.setLayoutX(436);
        separator6.setLayoutY(485);
        separator6.setPrefWidth(210);
        newGameLayout.getChildren().add(separator6);

        Separator separator7 = new Separator();
        separator7.setOrientation(Orientation.HORIZONTAL);
        separator7.setLayoutX(436);
        separator7.setLayoutY(520);
        separator7.setPrefWidth(210);
        newGameLayout.getChildren().add(separator7);

        Separator separator8 = new Separator();
        separator8.setOrientation(Orientation.HORIZONTAL);
        separator8.setLayoutX(436);
        separator8.setLayoutY(555);
        separator8.setPrefWidth(210);
        newGameLayout.getChildren().add(separator8);

        Separator separator9 = new Separator();
        separator9.setOrientation(Orientation.HORIZONTAL);
        separator9.setLayoutX(436);
        separator9.setLayoutY(590);
        separator9.setPrefWidth(210);
        newGameLayout.getChildren().add(separator9);

        Separator separator10 = new Separator();
        separator10.setOrientation(Orientation.HORIZONTAL);
        separator10.setLayoutX(436);
        separator10.setLayoutY(625);
        separator10.setPrefWidth(210);
        newGameLayout.getChildren().add(separator10);

        Label victoryConditions = new Label("Victory conditions:");
        victoryConditions.setLayoutX(444);
        victoryConditions.setLayoutY(425);
        victoryConditions.setStyle("-fx-font-weight: bold;");
        newGameLayout.getChildren().add(victoryConditions);

        Label capture = new Label("Capture:");
        capture.setLayoutX(444);
        capture.setLayoutY(460);
        capture.setStyle("-fx-font-weight: bold;");
        newGameLayout.getChildren().add(capture);

        Label menMoveBackward = new Label("Men move backward:");
        menMoveBackward.setLayoutX(444);
        menMoveBackward.setLayoutY(495);
        menMoveBackward.setStyle("-fx-font-weight: bold;");
        newGameLayout.getChildren().add(menMoveBackward);

        Label menCaptureBackward = new Label("Men capture backward:");
        menCaptureBackward.setLayoutX(444);
        menCaptureBackward.setLayoutY(530);
        menCaptureBackward.setStyle("-fx-font-weight: bold;");
        newGameLayout.getChildren().add(menCaptureBackward);

        Label kingRange = new Label("King range:");
        kingRange.setLayoutX(444);
        kingRange.setLayoutY(565);
        kingRange.setStyle("-fx-font-weight: bold;");
        newGameLayout.getChildren().add(kingRange);

        Label kingMoveAfterCapture = new Label("King move after capture:");
        kingMoveAfterCapture.setLayoutX(444);
        kingMoveAfterCapture.setLayoutY(600);
        kingMoveAfterCapture.setStyle("-fx-font-weight: bold;");
        newGameLayout.getChildren().add(kingMoveAfterCapture);

        Label rulesNameValue = new Label("\"" + rules.get(0).getName() + "\" rules");
        rulesNameValue.setLayoutX(444);
        rulesNameValue.setLayoutY(355);
        rulesNameValue.setStyle("-fx-font-weight: bold;");
        rulesNameValue.setTextFill(Color.web("#595959"));
        newGameLayout.getChildren().add(rulesNameValue);

        Label rulesDescriptionValue = new Label(rules.get(0).getDescription());
        rulesDescriptionValue.setLayoutX(444);
        rulesDescriptionValue.setLayoutY(390);
        rulesDescriptionValue.setStyle("-fx-font-weight: bold;");
        rulesDescriptionValue.setTextFill(Color.web("#595959"));
        newGameLayout.getChildren().add(rulesDescriptionValue);

        Label rulesVictoryValue = new Label(rules.get(0).isVictoryConditionsReversed() ? "reversed" : "standard");
        rulesVictoryValue.setLayoutX(589);
        rulesVictoryValue.setLayoutY(425);
        rulesVictoryValue.setStyle("-fx-font-weight: bold;");
        rulesVictoryValue.setTextFill(Color.web("#595959"));
        newGameLayout.getChildren().add(rulesVictoryValue);

        Label rulesCaptureValue = new Label(rules.get(0).isCaptureAny() ? "any" : "longest");
        rulesCaptureValue.setLayoutX(589);
        rulesCaptureValue.setLayoutY(460);
        rulesCaptureValue.setStyle("-fx-font-weight: bold;");
        rulesCaptureValue.setTextFill(Color.web("#595959"));
        newGameLayout.getChildren().add(rulesCaptureValue);

        Label rulesMenMoveBackwardValue = new Label(rules.get(0).isPawnMoveBackward() ? "yes" : "no");
        rulesMenMoveBackwardValue.setLayoutX(589);
        rulesMenMoveBackwardValue.setLayoutY(495);
        rulesMenMoveBackwardValue.setStyle("-fx-font-weight: bold;");
        rulesMenMoveBackwardValue.setTextFill(Color.web("#595959"));
        newGameLayout.getChildren().add(rulesMenMoveBackwardValue);

        Label rulesMenCaptureBackwardValue = new Label(rules.get(0).isPawnCaptureBackward() ? "yes" : "no");
        rulesMenCaptureBackwardValue.setLayoutX(589);
        rulesMenCaptureBackwardValue.setLayoutY(530);
        rulesMenCaptureBackwardValue.setStyle("-fx-font-weight: bold;");
        rulesMenCaptureBackwardValue.setTextFill(Color.web("#595959"));
        newGameLayout.getChildren().add(rulesMenCaptureBackwardValue);

        Label rulesKingRangeValue = new Label(rules.get(0).isQueenRangeOne() ? "one field" : "any");
        rulesKingRangeValue.setLayoutX(589);
        rulesKingRangeValue.setLayoutY(565);
        rulesKingRangeValue.setStyle("-fx-font-weight: bold;");
        rulesKingRangeValue.setTextFill(Color.web("#595959"));
        newGameLayout.getChildren().add(rulesKingRangeValue);

        Label rulesKingMoveAfterCaptureValue = new Label(rules.get(0).isQueenRangeOneAfterCapture() ? "next field" : "any");
        rulesKingMoveAfterCaptureValue.setLayoutX(589);
        rulesKingMoveAfterCaptureValue.setLayoutY(600);
        rulesKingMoveAfterCaptureValue.setStyle("-fx-font-weight: bold;");
        rulesKingMoveAfterCaptureValue.setTextFill(Color.web("#595959"));
        newGameLayout.getChildren().add(rulesKingMoveAfterCaptureValue);

        Label gameNameLabel = new Label("Game name:");
        gameNameLabel.setStyle("-fx-font-weight: bold;");
        gameNameLabel.setAlignment(Pos.CENTER_RIGHT);
        gameNameLabel.setLayoutX(20);
        gameNameLabel.setLayoutY(345);
        gameNameLabel.setPrefHeight(35);
        gameNameLabel.setPrefWidth(100);
        newGameLayout.getChildren().add(gameNameLabel);

        TextField gameNameInput = new TextField();
        gameNameInput.setLayoutX(140);
        gameNameInput.setLayoutY(345);
        gameNameInput.setPrefHeight(35);
        gameNameInput.setPrefWidth(250);
        newGameLayout.getChildren().add(gameNameInput);

        Label rulesLabel = new Label("Rules set:");
        rulesLabel.setStyle("-fx-font-weight: bold;");
        rulesLabel.setAlignment(Pos.CENTER_RIGHT);
        rulesLabel.setLayoutX(20);
        rulesLabel.setLayoutY(405);
        rulesLabel.setPrefHeight(35);
        rulesLabel.setPrefWidth(100);
        newGameLayout.getChildren().add(rulesLabel);

        ChoiceBox<String> rulesChooser = new ChoiceBox<>();
        rulesChooser.setLayoutX(140);
        rulesChooser.setLayoutY(405);
        rulesChooser.setPrefHeight(35);
        rulesChooser.setPrefWidth(250);
        for (RulesSet rule : rules) rulesChooser.getItems().add(rule.getName());
        rulesChooser.getSelectionModel().selectFirst();
        rulesChooser.setOnAction(e -> {
            for (RulesSet rulesSet : rules) {
                if (rulesChooser.getValue().equals(rulesSet.getName())) {
                    rulesNameValue.setText("\"" + rulesSet.getName() + "\" rules");
                    rulesDescriptionValue.setText(rulesSet.getDescription());
                    rulesVictoryValue.setText(rulesSet.isVictoryConditionsReversed() ? "reversed" : "standard");
                    rulesCaptureValue.setText(rulesSet.isCaptureAny() ? "any" : "longest");
                    rulesMenMoveBackwardValue.setText(rulesSet.isPawnMoveBackward() ? "yes" : "no");
                    rulesMenCaptureBackwardValue.setText(rulesSet.isPawnCaptureBackward() ? "yes" : "no");
                    rulesKingRangeValue.setText(rulesSet.isQueenRangeOne() ? "one field" : "any");
                    rulesKingMoveAfterCaptureValue.setText(rulesSet.isQueenRangeOneAfterCapture() ? "next field" : "any");
                    break;
                }
            }
        });
        newGameLayout.getChildren().add(rulesChooser);

        Label whitePlayerLabel = new Label("White player:");
        whitePlayerLabel.setStyle("-fx-font-weight: bold;");
        whitePlayerLabel.setAlignment(Pos.CENTER_RIGHT);
        whitePlayerLabel.setLayoutX(20);
        whitePlayerLabel.setLayoutY(465);
        whitePlayerLabel.setPrefHeight(35);
        whitePlayerLabel.setPrefWidth(100);
        newGameLayout.getChildren().add(whitePlayerLabel);

        RadioButton whiteHumanButton = new RadioButton("Human");
        whiteHumanButton.setStyle("-fx-font-weight: bold;");
        whiteHumanButton.setAlignment(Pos.CENTER_LEFT);
        whiteHumanButton.setLayoutX(180);
        whiteHumanButton.setLayoutY(465);
        whiteHumanButton.setPrefHeight(35);
        newGameLayout.getChildren().add(whiteHumanButton);

        RadioButton whiteComputerButton = new RadioButton("Computer");
        whiteComputerButton.setStyle("-fx-font-weight: bold;");
        whiteComputerButton.setAlignment(Pos.CENTER_LEFT);
        whiteComputerButton.setLayoutX(280);
        whiteComputerButton.setLayoutY(465);
        whiteComputerButton.setPrefHeight(35);
        newGameLayout.getChildren().add(whiteComputerButton);

        ToggleGroup whiteRadioGroup = new ToggleGroup();
        whiteHumanButton.setToggleGroup(whiteRadioGroup);
        whiteHumanButton.setSelected(true);
        whiteComputerButton.setToggleGroup(whiteRadioGroup);

        Label blackPlayerLabel = new Label("Black player:");
        blackPlayerLabel.setStyle("-fx-font-weight: bold;");
        blackPlayerLabel.setAlignment(Pos.CENTER_RIGHT);
        blackPlayerLabel.setLayoutX(20);
        blackPlayerLabel.setLayoutY(525);
        blackPlayerLabel.setPrefHeight(35);
        blackPlayerLabel.setPrefWidth(100);
        newGameLayout.getChildren().add(blackPlayerLabel);

        RadioButton blackHumanButton = new RadioButton("Human");
        blackHumanButton.setStyle("-fx-font-weight: bold;");
        blackHumanButton.setAlignment(Pos.CENTER_LEFT);
        blackHumanButton.setLayoutX(180);
        blackHumanButton.setLayoutY(525);
        blackHumanButton.setPrefHeight(35);
        newGameLayout.getChildren().add(blackHumanButton);

        RadioButton blackComputerButton = new RadioButton("Computer");
        blackComputerButton.setStyle("-fx-font-weight: bold;");
        blackComputerButton.setAlignment(Pos.CENTER_LEFT);
        blackComputerButton.setLayoutX(280);
        blackComputerButton.setLayoutY(525);
        blackComputerButton.setPrefHeight(35);
        newGameLayout.getChildren().add(blackComputerButton);

        ToggleGroup blackRadioGroup = new ToggleGroup();
        blackHumanButton.setToggleGroup(blackRadioGroup);
        blackHumanButton.setSelected(true);
        blackComputerButton.setToggleGroup(blackRadioGroup);

        Button createGameButton = new Button("CREATE NEW GAME");
        createGameButton.setLayoutX(140);
        createGameButton.setLayoutY(585);
        createGameButton.setPrefHeight(35);
        createGameButton.setPrefWidth(250);
        newGameLayout.getChildren().add(createGameButton);

        createGameButton.setOnAction(e -> {
            String gameName = gameNameInput.getText();
            boolean isWhiteAIPlayer = whiteComputerButton.isSelected();
            boolean isBlackAIPlayer = blackComputerButton.isSelected();
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

        Button backButton = new Button("BACK TO MAIN MENU");
        backButton.setOnAction(e -> start());
        backButton.setLayoutX(230);
        backButton.setLayoutY(660);
        backButton.setPrefHeight(60);
        backButton.setPrefWidth(200);
        newGameLayout.getChildren().add(backButton);

        Scene newGame = new Scene(newGameLayout, 660, 740);
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