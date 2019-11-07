package checkers.ui;

import checkers.board.Board;
import checkers.figures.Figure;
import checkers.figures.None;
import checkers.gameplay.RulesSet;
import exceptions.IncorrectMoveFormat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class InGameUI implements Serializable {

    private final transient ListView<Label> movesListView = new ListView<>();
    private final transient Label blackPlayerLabel = new Label("BLACK");
    private final transient Label whitePlayerLabel = new Label("WHITE");
    private final transient Label messageLabel = new Label();

    public void printMovesAndActivePlayer(List<String> moves, boolean player) {
        if (player) {
            blackPlayerLabel.setStyle("-fx-background-color: #000000;-fx-font-weight: bold;");
            blackPlayerLabel.setTextFill(Color.web("#ffffff"));
            whitePlayerLabel.setStyle("-fx-font-weight: bold;");
        } else {
            whitePlayerLabel.setStyle("-fx-background-color: #ffffff; -fx-font-weight: bold;");
            blackPlayerLabel.setStyle("-fx-font-weight: bold;");
            blackPlayerLabel.setTextFill(Color.web("#000000"));
        }

        movesListView.getItems().clear();
        for (String s : moves) {
            movesListView.getItems().add(0, new Label(s));
        }
    }

    public void showInGameInfos(RulesSet rules) {
        AnchorPane boardContainer = (AnchorPane) Window.getGameLayout().getChildren().get(0);
        Label rulesName = new Label("\"" + rules.getName() + "\" rules");
        rulesName.setLayoutX(675);
        rulesName.setLayoutY(30);
        rulesName.setTextFill(Color.web("#595959"));
        rulesName.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(rulesName);
        Label victoryConditions = new Label(rules.isVictoryConditionsReversed() ? "reversed" : "standard");
        victoryConditions.setLayoutX(815);
        victoryConditions.setLayoutY(65);
        victoryConditions.setTextFill(Color.web("#595959"));
        victoryConditions.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(victoryConditions);
        Label capture = new Label(rules.isCaptureAny() ? "any" : "longest");
        capture.setLayoutX(815);
        capture.setLayoutY(100);
        capture.setTextFill(Color.web("#595959"));
        capture.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(capture);
        Label menMoveBackward = new Label(rules.isPawnMoveBackward() ? "yes" : "no");
        menMoveBackward.setLayoutX(815);
        menMoveBackward.setLayoutY(135);
        menMoveBackward.setTextFill(Color.web("#595959"));
        menMoveBackward.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(menMoveBackward);
        Label menCaptureBackward = new Label(rules.isPawnCaptureBackward() ? "yes" : "no");
        menCaptureBackward.setLayoutX(815);
        menCaptureBackward.setLayoutY(170);
        menCaptureBackward.setTextFill(Color.web("#595959"));
        menCaptureBackward.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(menCaptureBackward);
        Label kingRange = new Label(rules.isQueenRangeOne() ? "one field" : "any");
        kingRange.setLayoutX(815);
        kingRange.setLayoutY(205);
        kingRange.setTextFill(Color.web("#595959"));
        kingRange.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(kingRange);
        Label kingMoveAfterCapture = new Label(rules.isQueenRangeOneAfterCapture() ? "next field" : "any");
        kingMoveAfterCapture.setLayoutX(815);
        kingMoveAfterCapture.setLayoutY(240);
        kingMoveAfterCapture.setTextFill(Color.web("#595959"));
        kingMoveAfterCapture.setStyle("-fx-font-weight: bold;");
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

    public void printMakingMove(char x1, int y1, char x2, int y2, boolean isItAITurn, boolean wasCapture) {
        if (!isItAITurn)
            return;
        String message = "Computer made " + (wasCapture ? "capture" : "move") + ": " + x1 + y1 + "-" + x2 + y2;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setX(300);
        alert.setY(300);
        alert.setTitle("Computer move");
        alert.setHeaderText(message);

        alert.showAndWait();
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

    public void endOfGame(List<String> moves, Board board, boolean isDraw, boolean winner) {
        board.clearFiguresOnBoard();
        AnchorPane boardLayout = (AnchorPane) Window.getGameLayout().getChildren().get(0);
        boardLayout.getChildren().remove(0);
        try {
            ImageView gameOverBoardImage = new ImageView();
            FileInputStream input = new FileInputStream("images/game_over_board.png");
            Image img = new Image(input);
            gameOverBoardImage.setImage(img);
            gameOverBoardImage.setX(20);
            gameOverBoardImage.setY(20);
            boardLayout.getChildren().add(gameOverBoardImage);

            ImageView gameOverTextImage = new ImageView();
            Image gameOverImg = new Image(new FileInputStream("images/game_over.png"));
            gameOverTextImage.setImage(gameOverImg);
            gameOverTextImage.setX(65);
            gameOverTextImage.setY(190);
            boardLayout.getChildren().add(gameOverTextImage);

            ImageView gameScore = new ImageView();
            Image imgScore;
            messageLabel.setTextFill(Color.web("#000000"));
            if (isDraw) {
                imgScore = new Image(new FileInputStream("images/draw.png"));
                gameScore.setImage(imgScore);
                gameScore.setX(50);
                gameScore.setY(390);
                boardLayout.getChildren().add(gameScore);
                messageLabel.setText("Each player moved king 15 times in the row.");
            } else if (winner) {
                imgScore = new Image(new FileInputStream("images/black_wins.png"));
                gameScore.setImage(imgScore);
                gameScore.setX(15);
                gameScore.setY(390);
                boardLayout.getChildren().add(gameScore);
                boolean whitePlayerLostAllFigures = true;
                for (int i = 0; i < 8; i++) {
                    for (int j = 1; j < 9; j++) {
                        Figure figure = board.getFigure((char) (i + 65), j);
                        if (!figure.getColor() && !(figure instanceof None)) whitePlayerLostAllFigures = false;
                    }
                }
                if (whitePlayerLostAllFigures) {
                    messageLabel.setText("White player has lost all his figures.");
                } else {
                    messageLabel.setText("White player had no moves.");
                }
            } else {
                imgScore = new Image(new FileInputStream("images/white_wins.png"));
                gameScore.setImage(imgScore);
                gameScore.setX(5);
                gameScore.setY(390);
                boardLayout.getChildren().add(gameScore);
                boolean blackPlayerLostAllFigures = true;
                for (int i = 0; i < 8; i++) {
                    for (int j = 1; j < 9; j++) {
                        Figure figure = board.getFigure((char) (i + 65), j);
                        if (figure.getColor() && !(figure instanceof None)) {
                            blackPlayerLostAllFigures = false;
                        }
                    }
                }
                if (blackPlayerLostAllFigures) {
                    messageLabel.setText("Black has player lost all his figures.");
                } else {
                    messageLabel.setText("Black player had no moves.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        movesListView.getItems().clear();
        for (String s : moves) {
            movesListView.getItems().add(0, new Label(s));
        }
    }

}
