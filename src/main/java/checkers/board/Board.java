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

    public void setAndPrintFigure(char row, int col, Figure figure) {    // TODO: refactor to use terminals wrapper
        this.rows.get(row).setFigure(col, figure);
        int rowInt = (int) row - 65;
        if (figure instanceof None) {
            STerminal.getInstance().setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 2);
            for (int i = 0; i < 5; i++) STerminal.getInstance().putCharacter(' ');
            STerminal.getInstance().setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 3);
            for (int i = 0; i < 5; i++) STerminal.getInstance().putCharacter(' ');
            STerminal.getInstance().setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 4);
            for (int i = 0; i < 5; i++) STerminal.getInstance().putCharacter(' ');
            return;
        }
        STerminal.getInstance().setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 2);
        if (figure instanceof Pawn) {
            STerminal.getInstance().putCharacter('┌');
        } else {
            STerminal.getInstance().putCharacter('╔');
        }
        for (int i = 0; i < 3; i++) {
            if (figure instanceof Pawn) {
                STerminal.getInstance().putCharacter('─');
            } else {
                STerminal.getInstance().putCharacter('═');
            }
        }
        if (figure instanceof Pawn) {
            STerminal.getInstance().putCharacter('┐');
        } else {
            STerminal.getInstance().putCharacter('╗');
        }
        STerminal.getInstance().setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 3);
        if (figure instanceof Pawn) {
            STerminal.getInstance().putCharacter('│');
        } else {
            STerminal.getInstance().putCharacter('║');
        }
        STerminal.getInstance().setCursorPosition(((col - 1) * 7) + 7, (rowInt * 3) + 3);
        if (figure instanceof Pawn) {
            STerminal.getInstance().putCharacter('│');
        } else {
            STerminal.getInstance().putCharacter('║');
        }
        STerminal.getInstance().setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 4);
        if (figure instanceof Pawn) {
            STerminal.getInstance().putCharacter('└');
        } else {
            STerminal.getInstance().putCharacter('╚');
        }
        for (int i = 0; i < 3; i++) {
            if (figure instanceof Pawn) {
                STerminal.getInstance().putCharacter('─');
            } else {
                STerminal.getInstance().putCharacter('═');
            }
        }
        if (figure instanceof Pawn) {
            STerminal.getInstance().putCharacter('┘');
        } else {
            STerminal.getInstance().putCharacter('╝');
        }
        if (!figure.getColor()) {
            STerminal.getInstance().setCursorPosition(((col - 1) * 7) + 5, (rowInt * 3) + 3);
            STerminal.getInstance().putCharacter('█');
        }
    }

    public void printEmptyBoardAndSideMenu() {    // TODO: refactor to use terminals wrapper
        for (int k = 0; k < 4; k++) {
            STerminal.getInstance().setCursorPosition(2, (6 * k) + 2);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 7; j++) STerminal.getInstance().putCharacter('█');
                for (int j = 0; j < 7; j++) STerminal.getInstance().putCharacter(' ');
            }
            STerminal.getInstance().setCursorPosition(2, (6 * k) + 3);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 7; j++) STerminal.getInstance().putCharacter('█');
                for (int j = 0; j < 7; j++) STerminal.getInstance().putCharacter(' ');
            }
            STerminal.getInstance().setCursorPosition(2, (6 * k) + 4);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 7; j++) STerminal.getInstance().putCharacter('█');
                for (int j = 0; j < 7; j++) STerminal.getInstance().putCharacter(' ');
            }
            STerminal.getInstance().setCursorPosition(2, (6 * k) + 5);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 7; j++) STerminal.getInstance().putCharacter(' ');
                for (int j = 0; j < 7; j++) STerminal.getInstance().putCharacter('█');
            }
            STerminal.getInstance().setCursorPosition(2, (6 * k) + 6);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 7; j++) STerminal.getInstance().putCharacter(' ');
                for (int j = 0; j < 7; j++) STerminal.getInstance().putCharacter('█');
            }
            STerminal.getInstance().setCursorPosition(2, (6 * k) + 7);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 7; j++) STerminal.getInstance().putCharacter(' ');
                for (int j = 0; j < 7; j++) STerminal.getInstance().putCharacter('█');
            }
        }
        STerminal.getInstance().setCursorPosition(1, 1);
        STerminal.getInstance().putCharacter('╔');
        for (int j = 0; j < 56; j++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().putCharacter('╗');
        STerminal.getInstance().setCursorPosition(1, 26);
        STerminal.getInstance().putCharacter('╚');
        for (int j = 0; j < 56; j++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().putCharacter('╝');
        for (int i = 2; i < 26; i++) {
            STerminal.getInstance().setCursorPosition(1, i);
            STerminal.getInstance().putCharacter('║');
        }
        for (int i = 2; i < 26; i++) {
            STerminal.getInstance().setCursorPosition(58, i);
            STerminal.getInstance().putCharacter('║');
        }
        for (int i = 0; i < 8; i++) {
            STerminal.getInstance().setCursorPosition((i * 7) + 5, 0);
            STerminal.getInstance().putCharacter((char) (i + 49));
            STerminal.getInstance().setCursorPosition((i * 7) + 5, 27);
            STerminal.getInstance().putCharacter((char) (i + 49));
        }

        STerminal.getInstance().setCursorPosition(0, 3);
        STerminal.getInstance().putCharacter('A');
        STerminal.getInstance().setCursorPosition(0, 6);
        STerminal.getInstance().putCharacter('B');
        STerminal.getInstance().setCursorPosition(0, 9);
        STerminal.getInstance().putCharacter('C');
        STerminal.getInstance().setCursorPosition(0, 12);
        STerminal.getInstance().putCharacter('D');
        STerminal.getInstance().setCursorPosition(0, 15);
        STerminal.getInstance().putCharacter('E');
        STerminal.getInstance().setCursorPosition(0, 18);
        STerminal.getInstance().putCharacter('F');
        STerminal.getInstance().setCursorPosition(0, 21);
        STerminal.getInstance().putCharacter('G');
        STerminal.getInstance().setCursorPosition(0, 24);
        STerminal.getInstance().putCharacter('H');
        STerminal.getInstance().setCursorPosition(59, 3);
        STerminal.getInstance().putCharacter('A');
        STerminal.getInstance().setCursorPosition(59, 6);
        STerminal.getInstance().putCharacter('B');
        STerminal.getInstance().setCursorPosition(59, 9);
        STerminal.getInstance().putCharacter('C');
        STerminal.getInstance().setCursorPosition(59, 12);
        STerminal.getInstance().putCharacter('D');
        STerminal.getInstance().setCursorPosition(59, 15);
        STerminal.getInstance().putCharacter('E');
        STerminal.getInstance().setCursorPosition(59, 18);
        STerminal.getInstance().putCharacter('F');
        STerminal.getInstance().setCursorPosition(59, 21);
        STerminal.getInstance().putCharacter('G');
        STerminal.getInstance().setCursorPosition(59, 24);
        STerminal.getInstance().putCharacter('H');
        printRightMenu();
        printBottomMenu();
    }

    private void printRightMenu() {    // TODO: refactor to use terminals wrapper
        STerminal.getInstance().setCursorPosition(72, 0);
        STerminal.getInstance().putCharacter('╔');
        STerminal.getInstance().setCursorPosition(97, 0);
        STerminal.getInstance().putCharacter('╗');
        STerminal.getInstance().setCursorPosition(72, 27);
        STerminal.getInstance().putCharacter('╚');
        STerminal.getInstance().setCursorPosition(97, 27);
        STerminal.getInstance().putCharacter('╝');
        for (int i = 1; i < 27; i++) {
            STerminal.getInstance().setCursorPosition(72, i);
            STerminal.getInstance().putCharacter('║');
            STerminal.getInstance().setCursorPosition(97, i);
            STerminal.getInstance().putCharacter('║');
        }
        STerminal.getInstance().setCursorPosition(73, 0);
        for (int i = 0; i < 24; i++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().setCursorPosition(73, 2);
        for (int i = 0; i < 24; i++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().setCursorPosition(73, 6);
        for (int i = 0; i < 24; i++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().setCursorPosition(73, 10);
        for (int i = 0; i < 24; i++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().setCursorPosition(73, 15);
        for (int i = 0; i < 24; i++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().setCursorPosition(73, 27);
        for (int i = 0; i < 24; i++) STerminal.getInstance().putCharacter('═');

        STerminal.getInstance().setCursorPosition(72, 2);
        STerminal.getInstance().putCharacter('╠');
        STerminal.getInstance().setCursorPosition(72, 6);
        STerminal.getInstance().putCharacter('╠');
        STerminal.getInstance().setCursorPosition(72, 10);
        STerminal.getInstance().putCharacter('╠');
        STerminal.getInstance().setCursorPosition(72, 15);
        STerminal.getInstance().putCharacter('╠');

        STerminal.getInstance().setCursorPosition(97, 2);
        STerminal.getInstance().putCharacter('╣');
        STerminal.getInstance().setCursorPosition(97, 6);
        STerminal.getInstance().putCharacter('╣');
        STerminal.getInstance().setCursorPosition(97, 10);
        STerminal.getInstance().putCharacter('╣');
        STerminal.getInstance().setCursorPosition(97, 15);
        STerminal.getInstance().putCharacter('╣');

        STerminal.getInstance().setCursorPosition(80, 0);
        STerminal.getInstance().putCharacter('╦');
        STerminal.getInstance().setCursorPosition(88, 0);
        STerminal.getInstance().putCharacter('╦');

        for (int i = 1; i < 10; i++) {
            STerminal.getInstance().setCursorPosition(80, i);
            STerminal.getInstance().putCharacter('║');
            STerminal.getInstance().setCursorPosition(88, i);
            STerminal.getInstance().putCharacter('║');
        }

        STerminal.getInstance().setCursorPosition(80, 2);
        STerminal.getInstance().putCharacter('╬');
        STerminal.getInstance().setCursorPosition(80, 6);
        STerminal.getInstance().putCharacter('╬');
        STerminal.getInstance().setCursorPosition(88, 2);
        STerminal.getInstance().putCharacter('╬');
        STerminal.getInstance().setCursorPosition(88, 6);
        STerminal.getInstance().putCharacter('╬');

        STerminal.getInstance().setCursorPosition(80, 10);
        STerminal.getInstance().putCharacter('╩');
        STerminal.getInstance().setCursorPosition(88, 10);
        STerminal.getInstance().putCharacter('╩');

        STerminal.getInstance().setCursorPosition(74, 1);
        STerminal.getInstance().putCharacter('M');
        STerminal.getInstance().putCharacter('E');
        STerminal.getInstance().putCharacter('N');

        STerminal.getInstance().setCursorPosition(82, 1);
        STerminal.getInstance().putCharacter('K');
        STerminal.getInstance().putCharacter('I');
        STerminal.getInstance().putCharacter('N');
        STerminal.getInstance().putCharacter('G');

        STerminal.getInstance().setCursorPosition(74, 3);
        STerminal.getInstance().putCharacter('┌');
        for (int i = 0; i < 3; i++) STerminal.getInstance().putCharacter('─');
        STerminal.getInstance().putCharacter('┐');
        STerminal.getInstance().setCursorPosition(82, 3);
        STerminal.getInstance().putCharacter('╔');
        for (int i = 0; i < 3; i++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().putCharacter('╗');
        STerminal.getInstance().setCursorPosition(74, 4);
        STerminal.getInstance().putCharacter('│');
        STerminal.getInstance().setCursorPosition(78, 4);
        STerminal.getInstance().putCharacter('│');
        STerminal.getInstance().setCursorPosition(82, 4);
        STerminal.getInstance().putCharacter('║');
        STerminal.getInstance().setCursorPosition(86, 4);
        STerminal.getInstance().putCharacter('║');
        STerminal.getInstance().setCursorPosition(90, 4);
        STerminal.getInstance().putCharacter('B');
        STerminal.getInstance().putCharacter('L');
        STerminal.getInstance().putCharacter('A');
        STerminal.getInstance().putCharacter('C');
        STerminal.getInstance().putCharacter('K');
        STerminal.getInstance().setCursorPosition(74, 5);
        STerminal.getInstance().putCharacter('└');
        for (int i = 0; i < 3; i++) STerminal.getInstance().putCharacter('─');
        STerminal.getInstance().putCharacter('┘');
        STerminal.getInstance().setCursorPosition(82, 5);
        STerminal.getInstance().putCharacter('╚');
        for (int i = 0; i < 3; i++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().putCharacter('╝');

        STerminal.getInstance().setCursorPosition(74, 7);
        STerminal.getInstance().putCharacter('┌');
        for (int i = 0; i < 3; i++) STerminal.getInstance().putCharacter('─');
        STerminal.getInstance().putCharacter('┐');
        STerminal.getInstance().setCursorPosition(82, 7);
        STerminal.getInstance().putCharacter('╔');
        for (int i = 0; i < 3; i++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().putCharacter('╗');
        STerminal.getInstance().setCursorPosition(74, 8);
        STerminal.getInstance().putCharacter('│');
        STerminal.getInstance().setCursorPosition(78, 8);
        STerminal.getInstance().putCharacter('│');
        STerminal.getInstance().setCursorPosition(82, 8);
        STerminal.getInstance().putCharacter('║');
        STerminal.getInstance().setCursorPosition(86, 8);
        STerminal.getInstance().putCharacter('║');
        STerminal.getInstance().setCursorPosition(90, 8);
        STerminal.getInstance().putCharacter('W');
        STerminal.getInstance().putCharacter('H');
        STerminal.getInstance().putCharacter('I');
        STerminal.getInstance().putCharacter('T');
        STerminal.getInstance().putCharacter('E');
        STerminal.getInstance().setCursorPosition(74, 9);
        STerminal.getInstance().putCharacter('└');
        for (int i = 0; i < 3; i++) STerminal.getInstance().putCharacter('─');
        STerminal.getInstance().putCharacter('┘');
        STerminal.getInstance().setCursorPosition(82, 9);
        STerminal.getInstance().putCharacter('╚');
        for (int i = 0; i < 3; i++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().putCharacter('╝');

        STerminal.getInstance().setCursorPosition(74, 11);
        STerminal.getInstance().putCharacter('M');
        STerminal.getInstance().putCharacter('E');
        STerminal.getInstance().putCharacter('N');
        STerminal.getInstance().putCharacter('U');
        STerminal.getInstance().setCursorPosition(73, 13);
        STerminal.getInstance().putCharacter('(');
        STerminal.getInstance().putCharacter('s');
        STerminal.getInstance().putCharacter(')');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('s');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('v');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('n');
        STerminal.getInstance().putCharacter('d');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter('x');
        STerminal.getInstance().putCharacter('i');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().setCursorPosition(73, 14);
        STerminal.getInstance().putCharacter('(');
        STerminal.getInstance().putCharacter('x');
        STerminal.getInstance().putCharacter(')');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter('x');
        STerminal.getInstance().putCharacter('i');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('w');
        STerminal.getInstance().putCharacter('i');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter('h');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('u');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('s');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('v');
        STerminal.getInstance().putCharacter('i');
        STerminal.getInstance().putCharacter('n');
        STerminal.getInstance().putCharacter('g');
        STerminal.getInstance().setCursorPosition(79, 16);
        STerminal.getInstance().putCharacter('L');
        STerminal.getInstance().putCharacter('A');
        STerminal.getInstance().putCharacter('S');
        STerminal.getInstance().putCharacter('T');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('1');
        STerminal.getInstance().putCharacter('0');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('M');
        STerminal.getInstance().putCharacter('O');
        STerminal.getInstance().putCharacter('V');
        STerminal.getInstance().putCharacter('E');
        STerminal.getInstance().putCharacter('S');
    }

    private void printBottomMenu() {    // TODO: refactor to use terminals wrapper
        STerminal.getInstance().setCursorPosition(2, 28);
        for (int i = 0; i < 95; i++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().setCursorPosition(2, 30);
        for (int i = 0; i < 95; i++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().setCursorPosition(2, 32);
        for (int i = 0; i < 95; i++) STerminal.getInstance().putCharacter('═');
        STerminal.getInstance().setCursorPosition(1, 29);
        STerminal.getInstance().putCharacter('║');
        STerminal.getInstance().setCursorPosition(35, 29);
        STerminal.getInstance().putCharacter('║');
        STerminal.getInstance().setCursorPosition(97, 29);
        STerminal.getInstance().putCharacter('║');
        STerminal.getInstance().setCursorPosition(1, 31);
        STerminal.getInstance().putCharacter('║');
        STerminal.getInstance().setCursorPosition(97, 31);
        STerminal.getInstance().putCharacter('║');
        STerminal.getInstance().setCursorPosition(1, 28);
        STerminal.getInstance().putCharacter('╔');
        STerminal.getInstance().setCursorPosition(97, 28);
        STerminal.getInstance().putCharacter('╗');
        STerminal.getInstance().setCursorPosition(1, 30);
        STerminal.getInstance().putCharacter('╠');
        STerminal.getInstance().setCursorPosition(97, 30);
        STerminal.getInstance().putCharacter('╣');
        STerminal.getInstance().setCursorPosition(35, 28);
        STerminal.getInstance().putCharacter('╦');
        STerminal.getInstance().setCursorPosition(35, 30);
        STerminal.getInstance().putCharacter('╩');
        STerminal.getInstance().setCursorPosition(1, 32);
        STerminal.getInstance().putCharacter('╚');
        STerminal.getInstance().setCursorPosition(97, 32);
        STerminal.getInstance().putCharacter('╝');
        STerminal.getInstance().setCursorPosition(3, 29);
        STerminal.getInstance().putCharacter('A');
        STerminal.getInstance().putCharacter('c');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter('i');
        STerminal.getInstance().putCharacter('v');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('p');
        STerminal.getInstance().putCharacter('l');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('y');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter(':');
    }

}