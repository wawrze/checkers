package checkers.board;

import checkers.figures.Figure;
import checkers.figures.None;
import checkers.figures.Pawn;
import checkers.figures.Queen;
import checkers.ui.Window;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Board implements Serializable {

    private final HashMap<Character, BoardRow> rows;

    private transient Map<Character, ImageView[]> figuresOnBoard;

    public Board() {
        this.rows = new HashMap<>();
        rows.put('A', new BoardRow(true));
        rows.put('B', new BoardRow(false));
        rows.put('C', new BoardRow(true));
        rows.put('D', new BoardRow(false));
        rows.put('E', new BoardRow(true));
        rows.put('F', new BoardRow(false));
        rows.put('G', new BoardRow(true));
        rows.put('H', new BoardRow(false));
    }

    public Board(Board board) {
        rows = new HashMap<>();
        rows.put('A', new BoardRow(true));
        for (int i = 1; i < 9; i++)
            rows.get('A').setFigure(i, board.getFigure('A', i));
        rows.put('B', new BoardRow(false));
        for (int i = 1; i < 9; i++)
            rows.get('B').setFigure(i, board.getFigure('B', i));
        rows.put('C', new BoardRow(true));
        for (int i = 1; i < 9; i++)
            rows.get('C').setFigure(i, board.getFigure('C', i));
        rows.put('D', new BoardRow(false));
        for (int i = 1; i < 9; i++)
            rows.get('D').setFigure(i, board.getFigure('D', i));
        rows.put('E', new BoardRow(true));
        for (int i = 1; i < 9; i++)
            rows.get('E').setFigure(i, board.getFigure('E', i));
        rows.put('F', new BoardRow(false));
        for (int i = 1; i < 9; i++)
            rows.get('F').setFigure(i, board.getFigure('F', i));
        rows.put('G', new BoardRow(true));
        for (int i = 1; i < 9; i++)
            rows.get('G').setFigure(i, board.getFigure('G', i));
        rows.put('H', new BoardRow(false));
        for (int i = 1; i < 9; i++)
            rows.get('H').setFigure(i, board.getFigure('H', i));
    }

    public Figure getFigure(char row, int col) {
        return this.rows.get(row).getFigure(col);
    }

    public void setFigure(char row, int col, Figure figure) {
        this.rows.get(row).setFigure(col, figure);
    }

    public void clearFiguresOnBoard() {
        AnchorPane board = (AnchorPane) Window.getGameLayout().getChildren().get(0);
        for (Map.Entry<Character, ImageView[]> iv : figuresOnBoard.entrySet()) {
            for (int i = 0; i < 8; i++) {
                if (iv.getValue()[i] != null) board.getChildren().remove(iv.getValue()[i]);
            }
        }
    }

    public void setAndPrintFigure(char row, int col, Figure figure) {
        this.rows.get(row).setFigure(col, figure);
        int xPos = ((col - 1) * 75) + 42;
        int yPos = (((int) row - 65) * 75) + 42;
        AnchorPane board = (AnchorPane) Window.getGameLayout().getChildren().get(0);

        ImageView figureRepresentation = figuresOnBoard.get(row)[col - 1];
        if (figureRepresentation != null) board.getChildren().remove(figureRepresentation);

        if (figure instanceof Pawn && figure.getColor()) {
            figureRepresentation = new ImageView();
            InputStream input = getClass().getClassLoader().getResourceAsStream("black_pawn.png");
            assert input != null;
            Image img = new Image(input);
            figureRepresentation.setImage(img);
            figureRepresentation.setX(xPos);
            figureRepresentation.setY(yPos);
        } else if (figure instanceof Pawn && !figure.getColor()) {
            figureRepresentation = new ImageView();
            InputStream input = getClass().getClassLoader().getResourceAsStream("white_pawn.png");
            assert input != null;
            Image img = new Image(input);
            figureRepresentation.setImage(img);
            figureRepresentation.setX(xPos);
            figureRepresentation.setY(yPos);
        } else if (figure instanceof Queen && figure.getColor()) {
            figureRepresentation = new ImageView();
            InputStream input = getClass().getClassLoader().getResourceAsStream("black_queen.png");
            assert input != null;
            Image img = new Image(input);
            figureRepresentation.setImage(img);
            figureRepresentation.setX(xPos);
            figureRepresentation.setY(yPos);
        } else if (figure instanceof Queen && !figure.getColor()) {
            figureRepresentation = new ImageView();
            InputStream input = getClass().getClassLoader().getResourceAsStream("white_queen.png");
            assert input != null;
            Image img = new Image(input);
            figureRepresentation.setImage(img);
            figureRepresentation.setX(xPos);
            figureRepresentation.setY(yPos);
        } else {
            figureRepresentation = null;
        }

        figuresOnBoard.get(row)[col - 1] = figureRepresentation;
        if (figureRepresentation != null) board.getChildren().add(figureRepresentation);
    }

    public void refreshFigures() {
        for (Map.Entry<Character, BoardRow> row : rows.entrySet()) {
            for (int i = 1; i < 9; i++) {
                if (!(row.getValue().getFigure(i) instanceof None)) {
                    setAndPrintFigure(row.getKey(), i, row.getValue().getFigure(i));
                }
            }
        }
    }

    public void printEmptyBoardAndSideMenu() {
        Pane boardLayout = Window.getGameLayout();

        AnchorPane boardContainer = new AnchorPane();
        boardLayout.getChildren().add(boardContainer);

        ImageView image = new ImageView();
        InputStream input = getClass().getClassLoader().getResourceAsStream("board.png");
        assert input != null;
        Image img = new Image(input);
        image.setImage(img);
        image.setX(20);
        image.setY(20);
        boardContainer.getChildren().add(image);

        Scene gameScene = new Scene(boardLayout, 900, 740);
        Window.getWindow().setTitle("Checkers");
        Window.getWindow().setScene(gameScene);
        figuresOnBoard = new HashMap<>();
        figuresOnBoard.put('A', new ImageView[8]);
        figuresOnBoard.put('B', new ImageView[8]);
        figuresOnBoard.put('C', new ImageView[8]);
        figuresOnBoard.put('D', new ImageView[8]);
        figuresOnBoard.put('E', new ImageView[8]);
        figuresOnBoard.put('F', new ImageView[8]);
        figuresOnBoard.put('G', new ImageView[8]);
        figuresOnBoard.put('H', new ImageView[8]);
        printRightMenu();
        printRulesTable();
    }

    private void printRulesTable() {
        AnchorPane boardContainer = (AnchorPane) Window.getGameLayout().getChildren().get(0);

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        separator1.setLayoutX(660);
        separator1.setLayoutY(20);
        separator1.setPrefHeight(245);
        boardContainer.getChildren().add(separator1);

        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.HORIZONTAL);
        separator2.setLayoutX(662);
        separator2.setLayoutY(20);
        separator2.setPrefWidth(220);
        boardContainer.getChildren().add(separator2);

        Separator separator3 = new Separator();
        separator3.setOrientation(Orientation.HORIZONTAL);
        separator3.setLayoutX(662);
        separator3.setLayoutY(55);
        separator3.setPrefWidth(220);
        boardContainer.getChildren().add(separator3);

        Separator separator4 = new Separator();
        separator4.setOrientation(Orientation.VERTICAL);
        separator4.setLayoutX(880);
        separator4.setLayoutY(20);
        separator4.setPrefHeight(245);
        boardContainer.getChildren().add(separator4);

        Separator separator5 = new Separator();
        separator5.setOrientation(Orientation.HORIZONTAL);
        separator5.setLayoutX(662);
        separator5.setLayoutY(90);
        separator5.setPrefWidth(220);
        boardContainer.getChildren().add(separator5);

        Separator separator6 = new Separator();
        separator6.setOrientation(Orientation.HORIZONTAL);
        separator6.setLayoutX(662);
        separator6.setLayoutY(125);
        separator6.setPrefWidth(220);
        boardContainer.getChildren().add(separator6);

        Separator separator7 = new Separator();
        separator7.setOrientation(Orientation.HORIZONTAL);
        separator7.setLayoutX(662);
        separator7.setLayoutY(160);
        separator7.setPrefWidth(220);
        boardContainer.getChildren().add(separator7);

        Separator separator8 = new Separator();
        separator8.setOrientation(Orientation.HORIZONTAL);
        separator8.setLayoutX(662);
        separator8.setLayoutY(195);
        separator8.setPrefWidth(220);
        boardContainer.getChildren().add(separator8);

        Separator separator9 = new Separator();
        separator9.setOrientation(Orientation.HORIZONTAL);
        separator9.setLayoutX(662);
        separator9.setLayoutY(230);
        separator9.setPrefWidth(220);
        boardContainer.getChildren().add(separator9);

        Separator separator10 = new Separator();
        separator10.setOrientation(Orientation.HORIZONTAL);
        separator10.setLayoutX(662);
        separator10.setLayoutY(265);
        separator10.setPrefWidth(220);
        boardContainer.getChildren().add(separator10);

        Label victoryConditions = new Label("Victory conditions:");
        victoryConditions.setLayoutX(670);
        victoryConditions.setLayoutY(65);
        victoryConditions.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(victoryConditions);

        Label capture = new Label("Capture:");
        capture.setLayoutX(670);
        capture.setLayoutY(100);
        capture.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(capture);

        Label menMoveBackward = new Label("Men move backward:");
        menMoveBackward.setLayoutX(670);
        menMoveBackward.setLayoutY(135);
        menMoveBackward.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(menMoveBackward);

        Label menCaptureBackward = new Label("Men capture backward:");
        menCaptureBackward.setLayoutX(670);
        menCaptureBackward.setLayoutY(170);
        menCaptureBackward.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(menCaptureBackward);

        Label kingRange = new Label("King range:");
        kingRange.setLayoutX(670);
        kingRange.setLayoutY(205);
        kingRange.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(kingRange);

        Label kingMoveAfterCapture = new Label("King move after capture:");
        kingMoveAfterCapture.setLayoutX(670);
        kingMoveAfterCapture.setLayoutY(240);
        kingMoveAfterCapture.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(kingMoveAfterCapture);
    }

    private void printRightMenu() {
        AnchorPane boardContainer = (AnchorPane) Window.getGameLayout().getChildren().get(0);

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        separator1.setLayoutX(660);
        separator1.setLayoutY(275);
        separator1.setPrefHeight(381);
        boardContainer.getChildren().add(separator1);

        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.VERTICAL);
        separator2.setLayoutX(734);
        separator2.setLayoutY(275);
        separator2.setPrefHeight(177);
        boardContainer.getChildren().add(separator2);

        Separator separator6 = new Separator();
        separator6.setOrientation(Orientation.VERTICAL);
        separator6.setLayoutX(808);
        separator6.setLayoutY(275);
        separator6.setPrefHeight(177);
        boardContainer.getChildren().add(separator6);

        Separator separator7 = new Separator();
        separator7.setOrientation(Orientation.VERTICAL);
        separator7.setLayoutX(880);
        separator7.setLayoutY(275);
        separator7.setPrefHeight(381);
        boardContainer.getChildren().add(separator7);

        Separator separator11 = new Separator();
        separator11.setOrientation(Orientation.VERTICAL);
        separator11.setLayoutX(770);
        separator11.setLayoutY(452);
        separator11.setPrefHeight(204);
        boardContainer.getChildren().add(separator11);

        Separator separator3 = new Separator();
        separator3.setOrientation(Orientation.HORIZONTAL);
        separator3.setLayoutX(662);
        separator3.setLayoutY(275);
        separator3.setPrefWidth(220);
        boardContainer.getChildren().add(separator3);

        Separator separator4 = new Separator();
        separator4.setOrientation(Orientation.HORIZONTAL);
        separator4.setLayoutX(662);
        separator4.setLayoutY(310);
        separator4.setPrefWidth(220);
        boardContainer.getChildren().add(separator4);

        Separator separator5 = new Separator();
        separator5.setOrientation(Orientation.HORIZONTAL);
        separator5.setLayoutX(662);
        separator5.setLayoutY(380);
        separator5.setPrefWidth(220);
        boardContainer.getChildren().add(separator5);

        Separator separator8 = new Separator();
        separator8.setOrientation(Orientation.HORIZONTAL);
        separator8.setLayoutX(662);
        separator8.setLayoutY(452);
        separator8.setPrefWidth(220);
        boardContainer.getChildren().add(separator8);

        Separator separator9 = new Separator();
        separator9.setOrientation(Orientation.HORIZONTAL);
        separator9.setLayoutX(662);
        separator9.setLayoutY(656);
        separator9.setPrefWidth(220);
        boardContainer.getChildren().add(separator9);

        Separator separator10 = new Separator();
        separator10.setOrientation(Orientation.HORIZONTAL);
        separator10.setLayoutX(662);
        separator10.setLayoutY(487);
        separator10.setPrefWidth(110);
        boardContainer.getChildren().add(separator10);

        Label menLabel = new Label("MEN");
        menLabel.setLayoutX(685);
        menLabel.setLayoutY(285);
        menLabel.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(menLabel);

        Label kingLabel = new Label("KING");
        kingLabel.setLayoutX(755);
        kingLabel.setLayoutY(285);
        kingLabel.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(kingLabel);

        ImageView image = new ImageView();
        InputStream input = getClass().getClassLoader().getResourceAsStream("black_pawn.png");
        assert input != null;
        Image img = new Image(input);
        image.setImage(img);
        image.setX(664);
        image.setY(312);
        boardContainer.getChildren().add(image);

        image = new ImageView();
        input = getClass().getClassLoader().getResourceAsStream("white_pawn.png");
        assert input != null;
        img = new Image(input);
        image.setImage(img);
        image.setX(664);
        image.setY(384);
        boardContainer.getChildren().add(image);

        image = new ImageView();
        input = getClass().getClassLoader().getResourceAsStream("black_queen.png");
        assert input != null;
        img = new Image(input);
        image.setImage(img);
        image.setX(738);
        image.setY(312);
        boardContainer.getChildren().add(image);

        image = new ImageView();
        input = getClass().getClassLoader().getResourceAsStream("white_queen.png");
        assert input != null;
        img = new Image(input);
        image.setImage(img);
        image.setX(738);
        image.setY(384);
        boardContainer.getChildren().add(image);

        Label victoryConditions = new Label("MOVES HISTORY:");
        victoryConditions.setLayoutX(670);
        victoryConditions.setLayoutY(462);
        victoryConditions.setStyle("-fx-font-weight: bold;");
        boardContainer.getChildren().add(victoryConditions);
    }

    public Map<Character, ImageView[]> getFiguresOnBoard() {
        return figuresOnBoard;
    }

}