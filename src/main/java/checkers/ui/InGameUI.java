package checkers.ui;

import checkers.gameplay.RulesSet;
import exceptions.IncorrectMoveFormat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.List;

public class InGameUI implements Serializable {

    private transient ListView movesListView = new ListView();
    private transient Label blackPlayerLabel = new Label("BLACK");
    private transient Label whitePlayerLabel = new Label("WHITE");
    private transient Label messageLabel = new Label();

    public void printMovesAndActivePlayer(List<String> moves, boolean player) {
        if (player) {
            blackPlayerLabel.setStyle("-fx-background-color: #000000;");
            blackPlayerLabel.setTextFill(Color.web("#ffffff"));
            whitePlayerLabel.setStyle("");
        } else {
            whitePlayerLabel.setStyle("-fx-background-color: #ffffff;");
            blackPlayerLabel.setStyle("");
            blackPlayerLabel.setTextFill(Color.web("#000000"));
        }

        movesListView.getItems().clear();
        for (String s : moves) {
            //noinspection unchecked
            movesListView.getItems().add(0, new Label(s));
        }
    }

    public void showInGameInfos(RulesSet rules) {
        AnchorPane boardContainer = (AnchorPane) Window.getGameLayout().getChildren().get(0);
        Label rulesName = new Label("\"" + rules.getName() + "\" rules");
        rulesName.setLayoutX(670);
        rulesName.setLayoutY(30);
        boardContainer.getChildren().add(rulesName);
        Label victoryConditions = new Label(rules.isVictoryConditionsReversed() ? "reversed" : "standard");
        victoryConditions.setLayoutX(810);
        victoryConditions.setLayoutY(65);
        boardContainer.getChildren().add(victoryConditions);
        Label capture = new Label(rules.isCaptureAny() ? "any" : "longest");
        capture.setLayoutX(810);
        capture.setLayoutY(100);
        boardContainer.getChildren().add(capture);
        Label menMoveBackward = new Label(rules.isPawnMoveBackward() ? "yes" : "no");
        menMoveBackward.setLayoutX(810);
        menMoveBackward.setLayoutY(135);
        boardContainer.getChildren().add(menMoveBackward);
        Label menCaptureBackward = new Label(rules.isPawnCaptureBackward() ? "yes" : "no");
        menCaptureBackward.setLayoutX(810);
        menCaptureBackward.setLayoutY(170);
        boardContainer.getChildren().add(menCaptureBackward);
        Label kingRange = new Label(rules.isQueenRangeOne() ? "one field" : "any");
        kingRange.setLayoutX(810);
        kingRange.setLayoutY(205);
        boardContainer.getChildren().add(kingRange);
        Label kingMoveAfterCapture = new Label(rules.isQueenRangeOneAfterCapture() ? "next field" : "any");
        kingMoveAfterCapture.setLayoutX(810);
        kingMoveAfterCapture.setLayoutY(240);
        boardContainer.getChildren().add(kingMoveAfterCapture);

        ScrollPane movesContainer = new ScrollPane();
        movesContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        movesContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        movesContainer.setLayoutX(662);
        movesContainer.setLayoutY(487);
        movesContainer.setPrefWidth(110);
        movesContainer.setPrefHeight(169);
        movesListView.setPrefWidth(110);
        movesContainer.setContent(movesListView);
        boardContainer.getChildren().add(movesContainer);

        blackPlayerLabel.setAlignment(Pos.CENTER);
        blackPlayerLabel.setLayoutX(810);
        blackPlayerLabel.setLayoutY(312);
        blackPlayerLabel.setPrefHeight(68);
        blackPlayerLabel.setPrefWidth(70);
        boardContainer.getChildren().add(blackPlayerLabel);

        whitePlayerLabel.setAlignment(Pos.CENTER);
        whitePlayerLabel.setLayoutX(810);
        whitePlayerLabel.setLayoutY(382);
        whitePlayerLabel.setPrefHeight(70);
        whitePlayerLabel.setPrefWidth(70);
        boardContainer.getChildren().add(whitePlayerLabel);

        messageLabel.setStyle("-fx-background-color: #ffffff;");
        messageLabel.setLayoutX(20);
        messageLabel.setLayoutY(675);
        messageLabel.setPadding(new Insets(10));
        messageLabel.setPrefWidth(635);
        boardContainer.getChildren().add(messageLabel);
    }

    public void printMakingMove(char x1, int y1, char x2, int y2, boolean isItAITurn, boolean wasCapture) {//TODO as separate window???
        if (!isItAITurn)
            return;
//        setCursor(x2 - 64, y2, x1 - 64, y1);
        String message = "Computer made " + (wasCapture ? "capture" : "move") + ": " + x1 + y1 + "-" + x2 + y2;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setX(300);
        alert.setY(300);
        alert.setTitle("Computer move");
        alert.setHeaderText(message);

        alert.showAndWait();
//        messageLabel.setTextFill(Color.web("#000000"));
//        messageLabel.setText("Computer made " + (wasCapture ? "capture" : "move") + ": " + x1 + y1 + "-" + x2 + y2);
    }

    public void printMoveDone(boolean isItAITurn) {
        if (isItAITurn)
            return;
        messageLabel.setTextFill(Color.web("#000000"));
        messageLabel.setText("Move done.");
    }

    public void printCaptureDone(boolean isItAITurn) {
        if (isItAITurn)
            return;
        messageLabel.setTextFill(Color.web("#000000"));
        messageLabel.setText("Capture done.");
    }

    public void printIncorrectMove(String s, boolean isItAITurn) {
        if (isItAITurn)
            return;
        String message = "You've tried to make incorrect move: " + s;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setX(300);
        alert.setY(300);
        alert.setTitle("Incorrect move");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void printCapture(String captures, boolean isItAITurn) {
        if (isItAITurn)
            return;
        messageLabel.setTextFill(Color.web("#FF0000"));
        messageLabel.setText("You have to capture: " + captures);
    }

    public void printMultiCapture(String captures, boolean isItAITurn) {
        if (isItAITurn)
            return;
        messageLabel.setTextFill(Color.web("#FF0000"));
        messageLabel.setText("Possible captures: " + captures);
    }

    public void printCaptureObligatory(boolean isItAITurn) {
        if (isItAITurn)
            return;
        String message = "Move not done - capture is obligatory!";
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setX(300);
        alert.setY(300);
        alert.setTitle("Capture");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void printIncorrectMoveFormat(boolean isItAITurn) {
        if (isItAITurn)
            return;
        messageLabel.setTextFill(Color.web("#FF0000"));
        messageLabel.setText("Incorrect move format! Proper format example: E4-D5");
    }

    public void validate(String s) throws IncorrectMoveFormat {
        String[] sArray = s.split("-");
        if (sArray.length != 2)
            throw new IncorrectMoveFormat();
        for (String t : sArray)
            if (t.length() != 2)
                throw new IncorrectMoveFormat();
    }

    public void endOfGame(List<String> moves) {//TODO
//        STerminal.getInstance().clear();
//        STerminal.getInstance().putStringAtPosition("╔═════════════════════", 6, 4);
//        STerminal.getInstance().putStringAtPosition("║███████       ▓▓▓▓▓░░", 6, 5);
//        STerminal.getInstance().putStringAtPosition("║███████       ▓▓░░░░░", 6, 6);
//        STerminal.getInstance().putStringAtPosition("║███████       ░░░░░░░", 6, 7);
//        STerminal.getInstance().putStringAtPosition("║       ▓▓▓▓▓░░", 6, 8);
//        STerminal.getInstance().putStringAtPosition("║       ▓▓░░░   ██  █ █ ███  ██  █  █ ███ ███    ███", 6, 9);
//        STerminal.getInstance().putStringAtPosition("║       ░░     █  █ █ █ █   █  █ █ █  █   █  █  █   █", 6, 10);
//        STerminal.getInstance().putStringAtPosition("║▓▓▓▓▓░░       █    ███ ██  █    ██   ██  ███    ██", 6, 11);
//        STerminal.getInstance().putStringAtPosition("║▓▓░░░░░       █  █ █ █ █   █  █ █ █  █   █ █  █   █", 6, 12);
//        STerminal.getInstance().putStringAtPosition("║░░░░░░░        ██  █ █ ███  ██  █  █ ███ █  █  ███", 6, 13);
//
//        if (VictoryValidator.isDraw()) {
//            STerminal.getInstance().putStringAtPosition("╔═══════════════════════════════════════════════════════════════════════════════════════════════╗", 6, 15);
//            STerminal.getInstance().putStringAtPosition("║ ███    █   █     █ ████                                                                       ║", 6, 16);
//            STerminal.getInstance().putStringAtPosition("║█   █  █ █  ██   ██ █                                                                          ║", 6, 17);
//            STerminal.getInstance().putStringAtPosition("║█     █   █ █ █ █ █ █                                                                          ║", 6, 18);
//            STerminal.getInstance().putStringAtPosition("║█     █   █ █  █  █ ███                                        ███   ████    █   █     █       ║", 6, 19);
//            STerminal.getInstance().putStringAtPosition("║█  ██ █   █ █     █ █                                          █  █  █   █  █ █  █     █       ║", 6, 20);
//            STerminal.getInstance().putStringAtPosition("║█   █ █████ █     █ █       ███  █     █ ████ ████   ██        █   █ █   █ █   █ █     █       ║", 6, 21);
//            STerminal.getInstance().putStringAtPosition("║ ███  █   █ █     █ ████   █   █  █   █  █    █   █  ██        █   █ ████  █   █ █  █  █       ║", 6, 22);
//            STerminal.getInstance().putStringAtPosition("║                           █   █  █   █  █    █   █  ██        █  █  █  █  █████ █ █ █ █       ║", 6, 23);
//            STerminal.getInstance().putStringAtPosition("║                           █   █   █ █   ███  ████   ██        ███   █   █ █   █ ██   ██       ║", 6, 24);
//            STerminal.getInstance().putStringAtPosition("║                           █   █   █ █   █    █  █                                             ║", 6, 25);
//            STerminal.getInstance().putStringAtPosition("║                            ███     █    ████ █   █  ██                                        ║", 6, 26);
//            STerminal.getInstance().putStringAtPosition("╚═══════════════════════════════════════════════════════════════════════════════════════════════╝", 6, 27);
//        } else if (VictoryValidator.getWinner()) {
//            STerminal.getInstance().putStringAtPosition("╔═══════════════════════════════════════════════════════════════════════════════════════════════╗", 6, 15);
//            STerminal.getInstance().putStringAtPosition("║ ███    █   █     █ ████                                       ██  █    ██   ██ █  █           ║", 6, 16);
//            STerminal.getInstance().putStringAtPosition("║█   █  █ █  ██   ██ █                                          █ █ █   █  █ █   █ █            ║", 6, 17);
//            STerminal.getInstance().putStringAtPosition("║█     █   █ █ █ █ █ █                                          ██  █   █  █ █   ██             ║", 6, 18);
//            STerminal.getInstance().putStringAtPosition("║█     █   █ █  █  █ ███                                        █ █ █   ████ █   █ █            ║", 6, 19);
//            STerminal.getInstance().putStringAtPosition("║█  ██ █   █ █     █ █                                          ██   ██ █  █  ██ █  █           ║", 6, 20);
//            STerminal.getInstance().putStringAtPosition("║█   █ █████ █     █ █           ███  █     █ ████ ████   ██                                    ║", 6, 21);
//            STerminal.getInstance().putStringAtPosition("║ ███  █   █ █     █ ████       █   █  █   █  █    █   █  ██            █   █ █ █   █  ███      ║", 6, 22);
//            STerminal.getInstance().putStringAtPosition("║                               █   █  █   █  █    █   █  ██            █   █ █ ██  █ █         ║", 6, 23);
//            STerminal.getInstance().putStringAtPosition("║                               █   █   █ █   ███  ████   ██            █ █ █ █ █ █ █  ██       ║", 6, 24);
//            STerminal.getInstance().putStringAtPosition("║                               █   █   █ █   █    █  █                 ██ ██ █ █  ██    █      ║", 6, 25);
//            STerminal.getInstance().putStringAtPosition("║                                ███     █    ████ █   █  ██            █   █ █ █   █ ███       ║", 6, 26);
//            STerminal.getInstance().putStringAtPosition("╚═══════════════════════════════════════════════════════════════════════════════════════════════╝", 6, 27);
//        } else {
//            STerminal.getInstance().putStringAtPosition("╔═══════════════════════════════════════════════════════════════════════════════════════════════╗", 6, 15);
//            STerminal.getInstance().putStringAtPosition("║ ███    █   █     █ ████                                       █   █ █ █ █ ███ ███             ║", 6, 16);
//            STerminal.getInstance().putStringAtPosition("║█   █  █ █  ██   ██ █                                          █   █ █ █ █  █  █               ║", 6, 17);
//            STerminal.getInstance().putStringAtPosition("║█     █   █ █ █ █ █ █                                          █   █ ███ █  █  ██              ║", 6, 18);
//            STerminal.getInstance().putStringAtPosition("║█     █   █ █  █  █ ███                                        █ █ █ █ █ █  █  █               ║", 6, 19);
//            STerminal.getInstance().putStringAtPosition("║█  ██ █   █ █     █ █                                          ██ ██ █ █ █  █  ███             ║", 6, 20);
//            STerminal.getInstance().putStringAtPosition("║█   █ █████ █     █ █           ███  █     █ ████ ████   ██                                    ║", 6, 21);
//            STerminal.getInstance().putStringAtPosition("║ ███  █   █ █     █ ████       █   █  █   █  █    █   █  ██            █   █ █ █   █  ███      ║", 6, 22);
//            STerminal.getInstance().putStringAtPosition("║                               █   █  █   █  █    █   █  ██            █   █ █ ██  █ █         ║", 6, 23);
//            STerminal.getInstance().putStringAtPosition("║                               █   █   █ █   ███  ████   ██            █ █ █ █ █ █ █  ██       ║", 6, 24);
//            STerminal.getInstance().putStringAtPosition("║                               █   █   █ █   █    █  █                 ██ ██ █ █  ██    █      ║", 6, 25);
//            STerminal.getInstance().putStringAtPosition("║                                ███     █    ████ █   █  ██            █   █ █ █   █ ███       ║", 6, 26);
//            STerminal.getInstance().putStringAtPosition("╚═══════════════════════════════════════════════════════════════════════════════════════════════╝", 6, 27);
//        }
//
//        STerminal.getInstance().putCharAtPosition('╔', 88, 1);
//        STerminal.getInstance().putCharAtPosition('╗', 113, 1);
//        STerminal.getInstance().putCharAtPosition('╚', 88, 13);
//        STerminal.getInstance().putCharAtPosition('╝', 113, 13);
//        for (int i = 2; i < 13; i++) {
//            STerminal.getInstance().putCharAtPosition('║', 88, i);
//            STerminal.getInstance().putCharAtPosition('║', 113, i);
//        }
//        STerminal.getInstance().setCursorPosition(89, 1);
//        STerminal.getInstance().putCharMultiplied('═', 24);
//        STerminal.getInstance().setCursorPosition(89, 13);
//        STerminal.getInstance().putCharMultiplied('═', 24);
//        STerminal.getInstance().putStringAtPosition("MOVES HISTORY", 94, 2);
//
//        STerminal.getInstance().putStringAtPosition("╔═════════════════════════════════════════════════╗", 6, 31);
//        STerminal.getInstance().putStringAtPosition("║ x - exit without saving, s - save game and exit ║", 6, 32);
//        STerminal.getInstance().putStringAtPosition("╚═════════════════════════════════════════════════╝", 6, 33);

        int movesToPrintTo = moves.size() - 1;
        int actualPosition = 0;
        do {
            for (int i = 0; i < 10; i++) {
                int moveToPrintIndex = movesToPrintTo - i;
                if (moveToPrintIndex == moves.size() || moveToPrintIndex < 0) break;
                String move = moves.get(moveToPrintIndex);
//                STerminal.getInstance().putStringAtPosition(move, 95, i + 3);
            }
//            KeyStroke key = new KeyStroke(KeyType.Escape);
//            do {
//                key = STerminal.getInstance().readInput();
//            } while (key == null);
//            if (key.getKeyType() == KeyType.ArrowDown) {
            if (actualPosition == 9 && movesToPrintTo > 9) {
                movesToPrintTo--;
            } else if (actualPosition < 9) {
                actualPosition++;
            }
//            } else if (key.getKeyType() == KeyType.ArrowUp) {
            if (actualPosition == 0 && movesToPrintTo < moves.size() - 1) {
                movesToPrintTo++;
            } else if (actualPosition > 0) {
                actualPosition--;
            }
//            } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 's') {
            // TODO: menu.games.put, menu.start
//            } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'x') {
            // TODO: menu.start
//            }
        } while (true);
    }

}
