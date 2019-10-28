package checkers.board;

import checkers.figures.Figure;
import checkers.figures.None;
import checkers.figures.Pawn;
import checkers.figures.Queen;
import checkers.ui.STerminal;
import checkers.ui.Window;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Board implements Serializable {

    private final HashMap<Character, BoardRow> rows;
    private Map<Character, ImageView[]> figuresOnBoard;

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

    public void setAndPrintFigure(char row, int col, Figure figure) {
        this.rows.get(row).setFigure(col, figure);
        try {
            int xPos = ((col - 1) * 75) + 42;
            int yPos = (((int) row - 65) * 75) + 42;
            AnchorPane board = (AnchorPane) Window.getGameLayout().getChildren().get(0);

            ImageView figureRepresentation = figuresOnBoard.get(row)[col - 1];
            if (figureRepresentation != null) board.getChildren().remove(figureRepresentation);

            if (figure instanceof Pawn && figure.getColor()) {
                figureRepresentation = new ImageView();
                FileInputStream input = new FileInputStream("images/black_pawn.png");
                Image img = new Image(input);
                figureRepresentation.setImage(img);
                figureRepresentation.setX(xPos);
                figureRepresentation.setY(yPos);
            } else if (figure instanceof Pawn && !figure.getColor()) {
                figureRepresentation = new ImageView();
                FileInputStream input = new FileInputStream("images/white_pawn.png");
                Image img = new Image(input);
                figureRepresentation.setImage(img);
                figureRepresentation.setX(xPos);
                figureRepresentation.setY(yPos);
            } else if (figure instanceof Queen && figure.getColor()) {
                figureRepresentation = new ImageView();
                FileInputStream input = new FileInputStream("images/black_queen.png");
                Image img = new Image(input);
                figureRepresentation.setImage(img);
                figureRepresentation.setX(xPos);
                figureRepresentation.setY(yPos);
            } else if (figure instanceof Queen && !figure.getColor()) {
                figureRepresentation = new ImageView();
                FileInputStream input = new FileInputStream("images/white_queen.png");
                Image img = new Image(input);
                figureRepresentation.setImage(img);
                figureRepresentation.setX(xPos);
                figureRepresentation.setY(yPos);
            } else {
                figureRepresentation = null;
            }

            figuresOnBoard.get(row)[col - 1] = figureRepresentation;
            if (figureRepresentation != null) board.getChildren().add(figureRepresentation);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        try {
            ImageView image = new ImageView();
            FileInputStream input = new FileInputStream("images/board.png");
            Image img = new Image(input);
            image.setImage(img);
            image.setX(20);
            image.setY(20);
            boardContainer.getChildren().add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene gameScene = new Scene(boardLayout, 1200, 800);
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


//        STerminal.getInstance().clear();
//        for (int k = 0; k < 4; k++) {
//            STerminal.getInstance().setCursorPosition(2, (6 * k) + 2);
//            for (int i = 0; i < 4; i++) {
//                STerminal.getInstance().putCharMultiplied('█', 7);
//                STerminal.getInstance().putCharMultiplied(' ', 7);
//            }
//            STerminal.getInstance().setCursorPosition(2, (6 * k) + 3);
//            for (int i = 0; i < 4; i++) {
//                STerminal.getInstance().putCharMultiplied('█', 7);
//                STerminal.getInstance().putCharMultiplied(' ', 7);
//            }
//            STerminal.getInstance().setCursorPosition(2, (6 * k) + 4);
//            for (int i = 0; i < 4; i++) {
//                STerminal.getInstance().putCharMultiplied('█', 7);
//                STerminal.getInstance().putCharMultiplied(' ', 7);
//            }
//            STerminal.getInstance().setCursorPosition(2, (6 * k) + 5);
//            for (int i = 0; i < 4; i++) {
//                STerminal.getInstance().putCharMultiplied(' ', 7);
//                STerminal.getInstance().putCharMultiplied('█', 7);
//            }
//            STerminal.getInstance().setCursorPosition(2, (6 * k) + 6);
//            for (int i = 0; i < 4; i++) {
//                STerminal.getInstance().putCharMultiplied(' ', 7);
//                STerminal.getInstance().putCharMultiplied('█', 7);
//            }
//            STerminal.getInstance().setCursorPosition(2, (6 * k) + 7);
//            for (int i = 0; i < 4; i++) {
//                STerminal.getInstance().putCharMultiplied(' ', 7);
//                STerminal.getInstance().putCharMultiplied('█', 7);
//            }
//        }
//        STerminal.getInstance().putCharAtPosition('╔', 1, 1);
//        STerminal.getInstance().putCharMultiplied('═', 56);
//        STerminal.getInstance().putCharacter('╗');
//        STerminal.getInstance().putCharAtPosition('╚', 1, 26);
//        STerminal.getInstance().putCharMultiplied('═', 56);
//        STerminal.getInstance().putCharacter('╝');
//
//        for (int i = 2; i < 26; i++) {
//            STerminal.getInstance().putCharAtPosition('║', 1, i);
//        }
//        for (int i = 2; i < 26; i++) {
//            STerminal.getInstance().putCharAtPosition('║', 58, i);
//        }
//        for (int i = 0; i < 8; i++) {
//            STerminal.getInstance().putCharAtPosition((char) (i + 49), (i * 7) + 5, 0);
//            STerminal.getInstance().putCharAtPosition((char) (i + 49), (i * 7) + 5, 27);
//        }
//
//        STerminal.getInstance().putCharAtPosition('A', 0, 3);
//        STerminal.getInstance().putCharAtPosition('B', 0, 6);
//        STerminal.getInstance().putCharAtPosition('C', 0, 9);
//        STerminal.getInstance().putCharAtPosition('D', 0, 12);
//        STerminal.getInstance().putCharAtPosition('E', 0, 15);
//        STerminal.getInstance().putCharAtPosition('F', 0, 18);
//        STerminal.getInstance().putCharAtPosition('G', 0, 21);
//        STerminal.getInstance().putCharAtPosition('H', 0, 24);
//        STerminal.getInstance().putCharAtPosition('A', 59, 3);
//        STerminal.getInstance().putCharAtPosition('B', 59, 6);
//        STerminal.getInstance().putCharAtPosition('C', 59, 9);
//        STerminal.getInstance().putCharAtPosition('D', 59, 12);
//        STerminal.getInstance().putCharAtPosition('E', 59, 15);
//        STerminal.getInstance().putCharAtPosition('F', 59, 18);
//        STerminal.getInstance().putCharAtPosition('G', 59, 21);
//        STerminal.getInstance().putCharAtPosition('H', 59, 24);
//        printRightMenu();
//        printBottomMenu();
//        printRulesTable();
//        STerminal.getInstance().update();
    }

    private void printRulesTable() {
        STerminal.getInstance().putCharAtPosition('╦', 88, 0);
        STerminal.getInstance().putCharAtPosition('╬', 88, 2);
        STerminal.getInstance().putCharAtPosition('╣', 88, 5);
        STerminal.getInstance().putCharAtPosition('╣', 88, 8);
        STerminal.getInstance().putCharAtPosition('╣', 88, 11);
        STerminal.getInstance().putCharAtPosition('╣', 88, 14);
        STerminal.getInstance().putCharAtPosition('╣', 88, 17);
        STerminal.getInstance().putCharAtPosition('╣', 88, 20);
        STerminal.getInstance().putCharAtPosition('║', 61, 1);
        STerminal.getInstance().putCharAtPosition('╔', 61, 0);
        STerminal.getInstance().putCharMultiplied('═', 26);
        for (int i = 0; i < 6; i++) {
            STerminal.getInstance().putCharAtPosition('╠', 61, (i * 3) + 2);
            STerminal.getInstance().putCharMultiplied('═', 26);
            STerminal.getInstance().putCharAtPosition('║', 61, (i * 3) + 3);
            STerminal.getInstance().putCharAtPosition('║', 61, (i * 3) + 4);
        }
        STerminal.getInstance().putCharAtPosition('╚', 61, 20);
        STerminal.getInstance().putCharMultiplied('═', 26);
        STerminal.getInstance().putStringAtPosition("Victory conditions:", 63, 3);
        STerminal.getInstance().putStringAtPosition("Capture:", 63, 6);
        STerminal.getInstance().putStringAtPosition("Men move backward:", 63, 9);
        STerminal.getInstance().putStringAtPosition("Men capture backward:", 63, 12);
        STerminal.getInstance().putStringAtPosition("King range:", 63, 15);
        STerminal.getInstance().putStringAtPosition("King move after capture:", 63, 18);
    }

    private void printRightMenu() {
        STerminal.getInstance().putCharAtPosition('╔', 88, 0);
        STerminal.getInstance().putCharAtPosition('╗', 113, 0);
        STerminal.getInstance().putCharAtPosition('╚', 88, 27);
        STerminal.getInstance().putCharAtPosition('╝', 113, 27);
        for (int i = 1; i < 27; i++) {
            STerminal.getInstance().putCharAtPosition('║', 88, i);
            STerminal.getInstance().putCharAtPosition('║', 113, i);
        }
        STerminal.getInstance().setCursorPosition(89, 0);
        STerminal.getInstance().putCharMultiplied('═', 24);
        STerminal.getInstance().setCursorPosition(89, 2);
        STerminal.getInstance().putCharMultiplied('═', 24);
        STerminal.getInstance().setCursorPosition(89, 6);
        STerminal.getInstance().putCharMultiplied('═', 24);
        STerminal.getInstance().setCursorPosition(89, 10);
        STerminal.getInstance().putCharMultiplied('═', 24);
        STerminal.getInstance().setCursorPosition(89, 15);
        STerminal.getInstance().putCharMultiplied('═', 24);
        STerminal.getInstance().setCursorPosition(89, 27);
        STerminal.getInstance().putCharMultiplied('═', 24);

        STerminal.getInstance().putCharAtPosition('╠', 88, 2);
        STerminal.getInstance().putCharAtPosition('╠', 88, 6);
        STerminal.getInstance().putCharAtPosition('╠', 88, 10);
        STerminal.getInstance().putCharAtPosition('╠', 88, 15);
        STerminal.getInstance().putCharAtPosition('╣', 113, 2);
        STerminal.getInstance().putCharAtPosition('╣', 113, 6);
        STerminal.getInstance().putCharAtPosition('╣', 113, 10);
        STerminal.getInstance().putCharAtPosition('╣', 113, 15);
        STerminal.getInstance().putCharAtPosition('╦', 96, 0);
        STerminal.getInstance().putCharAtPosition('╦', 104, 0);

        for (int i = 1; i < 10; i++) {
            STerminal.getInstance().putCharAtPosition('║', 96, i);
            STerminal.getInstance().putCharAtPosition('║', 104, i);
        }
        STerminal.getInstance().putCharAtPosition('╬', 96, 2);
        STerminal.getInstance().putCharAtPosition('╬', 96, 6);
        STerminal.getInstance().putCharAtPosition('╬', 104, 2);
        STerminal.getInstance().putCharAtPosition('╬', 104, 6);
        STerminal.getInstance().putCharAtPosition('╩', 96, 10);
        STerminal.getInstance().putCharAtPosition('╩', 104, 10);

        STerminal.getInstance().putStringAtPosition("MEN", 90, 1);
        STerminal.getInstance().putStringAtPosition("KING", 98, 1);

        STerminal.getInstance().putCharAtPosition('┌', 90, 3);
        STerminal.getInstance().putCharMultiplied('─', 3);
        STerminal.getInstance().putCharacter('┐');
        STerminal.getInstance().putCharAtPosition('╔', 98, 3);
        STerminal.getInstance().putCharMultiplied('═', 3);
        STerminal.getInstance().putCharacter('╗');
        STerminal.getInstance().putCharAtPosition('│', 90, 4);
        STerminal.getInstance().putCharAtPosition('│', 94, 4);
        STerminal.getInstance().putCharAtPosition('║', 98, 4);
        STerminal.getInstance().putCharAtPosition('║', 102, 4);
        STerminal.getInstance().putStringAtPosition("BLACK", 106, 4);
        STerminal.getInstance().putCharAtPosition('└', 90, 5);
        STerminal.getInstance().putCharMultiplied('─', 3);
        STerminal.getInstance().putCharacter('┘');
        STerminal.getInstance().putCharAtPosition('╚', 98, 5);
        STerminal.getInstance().putCharMultiplied('═', 3);
        STerminal.getInstance().putCharacter('╝');

        STerminal.getInstance().putCharAtPosition('┌', 90, 7);
        STerminal.getInstance().putCharMultiplied('─', 3);
        STerminal.getInstance().putCharacter('┐');
        STerminal.getInstance().putCharAtPosition('╔', 98, 7);
        STerminal.getInstance().putCharMultiplied('═', 3);
        STerminal.getInstance().putCharacter('╗');
        STerminal.getInstance().putCharAtPosition('│', 90, 8);
        STerminal.getInstance().putCharAtPosition('█', 92, 8);
        STerminal.getInstance().putCharAtPosition('│', 94, 8);
        STerminal.getInstance().putCharAtPosition('║', 98, 8);
        STerminal.getInstance().putCharAtPosition('█', 100, 8);
        STerminal.getInstance().putCharAtPosition('║', 102, 8);
        STerminal.getInstance().putStringAtPosition("WHITE", 106, 8);
        STerminal.getInstance().putCharAtPosition('└', 90, 9);
        STerminal.getInstance().putCharMultiplied('─', 3);
        STerminal.getInstance().putCharacter('┘');
        STerminal.getInstance().putCharAtPosition('╚', 98, 9);
        STerminal.getInstance().putCharMultiplied('═', 3);
        STerminal.getInstance().putCharacter('╝');

        STerminal.getInstance().putStringAtPosition("MENU", 90, 11);
        STerminal.getInstance().putStringAtPosition("(h) scroll moves history", 89, 12);
        STerminal.getInstance().putStringAtPosition("(s) save and exit", 89, 13);
        STerminal.getInstance().putStringAtPosition("(x) exit without saving", 89, 14);
        STerminal.getInstance().putStringAtPosition("MOVES HISTORY", 94, 16);
    }

    private void printBottomMenu() {
        STerminal.getInstance().setCursorPosition(2, 28);
        STerminal.getInstance().putCharMultiplied('═', 95);
        STerminal.getInstance().setCursorPosition(2, 30);
        STerminal.getInstance().putCharMultiplied('═', 95);
        STerminal.getInstance().setCursorPosition(2, 32);
        STerminal.getInstance().putCharMultiplied('═', 95);
        STerminal.getInstance().putCharAtPosition('║', 1, 29);
        STerminal.getInstance().putCharAtPosition('║', 35, 29);
        STerminal.getInstance().putCharAtPosition('║', 97, 29);
        STerminal.getInstance().putCharAtPosition('║', 1, 31);
        STerminal.getInstance().putCharAtPosition('║', 97, 31);
        STerminal.getInstance().putCharAtPosition('╔', 1, 28);
        STerminal.getInstance().putCharAtPosition('╗', 97, 28);
        STerminal.getInstance().putCharAtPosition('╠', 1, 30);
        STerminal.getInstance().putCharAtPosition('╣', 97, 30);
        STerminal.getInstance().putCharAtPosition('╦', 35, 28);
        STerminal.getInstance().putCharAtPosition('╩', 35, 30);
        STerminal.getInstance().putCharAtPosition('╚', 1, 32);
        STerminal.getInstance().putCharAtPosition('╝', 97, 32);
        STerminal.getInstance().putStringAtPosition("Active player:", 3, 29);
    }

    public Map<Character, ImageView[]> getFiguresOnBoard() {
        return figuresOnBoard;
    }

}