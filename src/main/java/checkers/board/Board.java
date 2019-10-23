package checkers.board;

import checkers.figures.Figure;
import checkers.figures.None;
import checkers.figures.Pawn;
import checkers.gameplay.STerminal;

import java.io.Serializable;
import java.util.HashMap;

public class Board implements Serializable {

    private final HashMap<Character, BoardRow> rows;

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
        int rowInt = (int) row - 65;
        if (figure instanceof None) {
            STerminal.getInstance().setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 2);
            STerminal.getInstance().putCharMultiplied(' ', 5);
            STerminal.getInstance().setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 3);
            STerminal.getInstance().putCharMultiplied(' ', 5);
            STerminal.getInstance().setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 4);
            STerminal.getInstance().putCharMultiplied(' ', 5);
            STerminal.getInstance().update();
            return;
        }

        STerminal.getInstance().setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 2);
        if (figure instanceof Pawn) {
            STerminal.getInstance().putCharacter('┌');
            STerminal.getInstance().putCharMultiplied('─', 3);
            STerminal.getInstance().putCharacter('┐');
            STerminal.getInstance().putCharAtPosition('│', ((col - 1) * 7) + 3, (rowInt * 3) + 3);
            STerminal.getInstance().putCharAtPosition('│', ((col - 1) * 7) + 7, (rowInt * 3) + 3);
            STerminal.getInstance().putCharAtPosition('└', ((col - 1) * 7) + 3, (rowInt * 3) + 4);
            STerminal.getInstance().putCharMultiplied('─', 3);
            STerminal.getInstance().putCharacter('┘');
            STerminal.getInstance().putCharacter('╝');
        } else {
            STerminal.getInstance().putCharacter('╔');
            STerminal.getInstance().putCharMultiplied('═', 3);
            STerminal.getInstance().putCharacter('╗');
            STerminal.getInstance().putCharAtPosition('║', ((col - 1) * 7) + 3, (rowInt * 3) + 3);
            STerminal.getInstance().putCharAtPosition('║', ((col - 1) * 7) + 7, (rowInt * 3) + 3);
            STerminal.getInstance().putCharAtPosition('╚', ((col - 1) * 7) + 3, (rowInt * 3) + 4);
            STerminal.getInstance().putCharMultiplied('═', 3);
        }

        if (!figure.getColor()) {
            STerminal.getInstance().putCharAtPosition('█', ((col - 1) * 7) + 5, (rowInt * 3) + 3);
        }
        STerminal.getInstance().update();
    }

    public void printEmptyBoardAndSideMenu() {
        for (int k = 0; k < 4; k++) {
            STerminal.getInstance().setCursorPosition(2, (6 * k) + 2);
            for (int i = 0; i < 4; i++) {
                STerminal.getInstance().putCharMultiplied('█', 7);
                STerminal.getInstance().putCharMultiplied(' ', 7);
            }
            STerminal.getInstance().setCursorPosition(2, (6 * k) + 3);
            for (int i = 0; i < 4; i++) {
                STerminal.getInstance().putCharMultiplied('█', 7);
                STerminal.getInstance().putCharMultiplied(' ', 7);
            }
            STerminal.getInstance().setCursorPosition(2, (6 * k) + 4);
            for (int i = 0; i < 4; i++) {
                STerminal.getInstance().putCharMultiplied('█', 7);
                STerminal.getInstance().putCharMultiplied(' ', 7);
            }
            STerminal.getInstance().setCursorPosition(2, (6 * k) + 5);
            for (int i = 0; i < 4; i++) {
                STerminal.getInstance().putCharMultiplied(' ', 7);
                STerminal.getInstance().putCharMultiplied('█', 7);
            }
            STerminal.getInstance().setCursorPosition(2, (6 * k) + 6);
            for (int i = 0; i < 4; i++) {
                STerminal.getInstance().putCharMultiplied(' ', 7);
                STerminal.getInstance().putCharMultiplied('█', 7);
            }
            STerminal.getInstance().setCursorPosition(2, (6 * k) + 7);
            for (int i = 0; i < 4; i++) {
                STerminal.getInstance().putCharMultiplied(' ', 7);
                STerminal.getInstance().putCharMultiplied('█', 7);
            }
        }
        STerminal.getInstance().putCharAtPosition('╔', 1, 1);
        STerminal.getInstance().putCharMultiplied('═', 56);
        STerminal.getInstance().putCharacter('╗');
        STerminal.getInstance().putCharAtPosition('╚', 1, 26);
        STerminal.getInstance().putCharMultiplied('═', 56);
        STerminal.getInstance().putCharacter('╝');

        for (int i = 2; i < 26; i++) {
            STerminal.getInstance().putCharAtPosition('║', 1, i);
        }
        for (int i = 2; i < 26; i++) {
            STerminal.getInstance().putCharAtPosition('║', 58, i);
        }
        for (int i = 0; i < 8; i++) {
            STerminal.getInstance().putCharAtPosition((char) (i + 49), (i * 7) + 5, 0);
            STerminal.getInstance().putCharAtPosition((char) (i + 49), (i * 7) + 5, 27);
        }

        STerminal.getInstance().putCharAtPosition('A', 0, 3);
        STerminal.getInstance().putCharAtPosition('B', 0, 6);
        STerminal.getInstance().putCharAtPosition('C', 0, 9);
        STerminal.getInstance().putCharAtPosition('D', 0, 12);
        STerminal.getInstance().putCharAtPosition('E', 0, 15);
        STerminal.getInstance().putCharAtPosition('F', 0, 18);
        STerminal.getInstance().putCharAtPosition('G', 0, 21);
        STerminal.getInstance().putCharAtPosition('H', 0, 24);
        STerminal.getInstance().putCharAtPosition('A', 59, 3);
        STerminal.getInstance().putCharAtPosition('B', 59, 6);
        STerminal.getInstance().putCharAtPosition('C', 59, 9);
        STerminal.getInstance().putCharAtPosition('D', 59, 12);
        STerminal.getInstance().putCharAtPosition('E', 59, 15);
        STerminal.getInstance().putCharAtPosition('F', 59, 18);
        STerminal.getInstance().putCharAtPosition('G', 59, 21);
        STerminal.getInstance().putCharAtPosition('H', 59, 24);
        printRightMenu();
        printBottomMenu();
        STerminal.getInstance().update();
    }

    private void printRightMenu() {
        STerminal.getInstance().putCharAtPosition('╔', 72, 0);
        STerminal.getInstance().putCharAtPosition('╗', 97, 0);
        STerminal.getInstance().putCharAtPosition('╚', 72, 27);
        STerminal.getInstance().putCharAtPosition('╝', 97, 27);
        for (int i = 1; i < 27; i++) {
            STerminal.getInstance().putCharAtPosition('║', 72, i);
            STerminal.getInstance().putCharAtPosition('║', 97, i);
        }
        STerminal.getInstance().setCursorPosition(73, 0);
        STerminal.getInstance().putCharMultiplied('═', 24);
        STerminal.getInstance().setCursorPosition(73, 2);
        STerminal.getInstance().putCharMultiplied('═', 24);
        STerminal.getInstance().setCursorPosition(73, 6);
        STerminal.getInstance().putCharMultiplied('═', 24);
        STerminal.getInstance().setCursorPosition(73, 10);
        STerminal.getInstance().putCharMultiplied('═', 24);
        STerminal.getInstance().setCursorPosition(73, 15);
        STerminal.getInstance().putCharMultiplied('═', 24);
        STerminal.getInstance().setCursorPosition(73, 27);
        STerminal.getInstance().putCharMultiplied('═', 24);

        STerminal.getInstance().putCharAtPosition('╠', 72, 2);
        STerminal.getInstance().putCharAtPosition('╠', 72, 6);
        STerminal.getInstance().putCharAtPosition('╠', 72, 10);
        STerminal.getInstance().putCharAtPosition('╠', 72, 15);
        STerminal.getInstance().putCharAtPosition('╣', 97, 2);
        STerminal.getInstance().putCharAtPosition('╣', 97, 6);
        STerminal.getInstance().putCharAtPosition('╣', 97, 10);
        STerminal.getInstance().putCharAtPosition('╣', 97, 15);
        STerminal.getInstance().putCharAtPosition('╦', 80, 0);
        STerminal.getInstance().putCharAtPosition('╦', 88, 0);

        for (int i = 1; i < 10; i++) {
            STerminal.getInstance().putCharAtPosition('║', 80, i);
            STerminal.getInstance().putCharAtPosition('║', 88, i);
        }
        STerminal.getInstance().putCharAtPosition('╬', 80, 2);
        STerminal.getInstance().putCharAtPosition('╬', 80, 6);
        STerminal.getInstance().putCharAtPosition('╬', 88, 2);
        STerminal.getInstance().putCharAtPosition('╬', 88, 6);
        STerminal.getInstance().putCharAtPosition('╩', 80, 10);
        STerminal.getInstance().putCharAtPosition('╩', 88, 10);

        STerminal.getInstance().putStringAtPosition("MEN", 74, 1);
        STerminal.getInstance().putStringAtPosition("KING", 82, 1);

        STerminal.getInstance().putCharAtPosition('┌', 74, 3);
        STerminal.getInstance().putCharMultiplied('─', 3);
        STerminal.getInstance().putCharacter('┐');
        STerminal.getInstance().putCharAtPosition('╔', 82, 3);
        STerminal.getInstance().putCharMultiplied('═', 3);
        STerminal.getInstance().putCharacter('╗');
        STerminal.getInstance().putCharAtPosition('│', 74, 4);
        STerminal.getInstance().putCharAtPosition('│', 78, 4);
        STerminal.getInstance().putCharAtPosition('║', 82, 4);
        STerminal.getInstance().putCharAtPosition('║', 86, 4);

        STerminal.getInstance().putStringAtPosition("BLACK", 90, 4);

        STerminal.getInstance().putCharAtPosition('└', 74, 5);
        STerminal.getInstance().putCharMultiplied('─', 3);
        STerminal.getInstance().putCharacter('┘');
        STerminal.getInstance().putCharAtPosition('╚', 82, 5);
        STerminal.getInstance().putCharMultiplied('═', 3);
        STerminal.getInstance().putCharacter('╝');
        STerminal.getInstance().putCharAtPosition('┌', 74, 7);
        STerminal.getInstance().putCharMultiplied('─', 3);
        STerminal.getInstance().putCharacter('┐');
        STerminal.getInstance().putCharAtPosition('╔', 82, 7);
        STerminal.getInstance().putCharMultiplied('═', 3);
        STerminal.getInstance().putCharacter('╗');
        STerminal.getInstance().putCharAtPosition('│', 74, 8);
        STerminal.getInstance().putCharAtPosition('│', 78, 8);
        STerminal.getInstance().putCharAtPosition('║', 82, 8);
        STerminal.getInstance().putCharAtPosition('║', 86, 8);

        STerminal.getInstance().putStringAtPosition("WHITE", 90, 8);

        STerminal.getInstance().putCharAtPosition('└', 74, 9);
        STerminal.getInstance().putCharMultiplied('─', 3);
        STerminal.getInstance().putCharacter('┘');
        STerminal.getInstance().putCharAtPosition('╚', 82, 9);
        STerminal.getInstance().putCharMultiplied('═', 3);
        STerminal.getInstance().putCharacter('╝');

        STerminal.getInstance().putStringAtPosition("MENU", 74, 11);
        STerminal.getInstance().putStringAtPosition("(s) save and exit", 73, 13);
        STerminal.getInstance().putStringAtPosition("(x) exit without saving", 73, 14);
        STerminal.getInstance().putStringAtPosition("LAST 10 MOVES", 79, 16);
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

}