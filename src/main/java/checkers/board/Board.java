package checkers.board;

import checkers.figures.Figure;
import checkers.figures.None;
import checkers.figures.Pawn;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class Board implements Serializable {

    private final HashMap<Character, BoardRow> rows;
    private Terminal terminal;

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
        terminal = null;
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

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public Figure getFigure(char row, int col) {
        return this.rows.get(row).getFigure(col);
    }

    public void setFigure(char row, int col, Figure figure) {
        this.rows.get(row).setFigure(col, figure);
        printFigure(row, col, figure);
    }

    private void printFigure(char row, int col, Figure figure) {
        if (terminal == null) return;
        try {
            int rowInt = (int) row - 65;
            if (figure instanceof None) {
                terminal.setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 2);
                for (int i = 0; i < 5; i++) terminal.putCharacter(' ');
                terminal.setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 3);
                for (int i = 0; i < 5; i++) terminal.putCharacter(' ');
                terminal.setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 4);
                for (int i = 0; i < 5; i++) terminal.putCharacter(' ');
                return;
            }
            terminal.setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 2);
            if (figure instanceof Pawn) {
                terminal.putCharacter('┌');
            } else {
                terminal.putCharacter('╔');
            }
            for (int i = 0; i < 3; i++) {
                if (figure instanceof Pawn) {
                    terminal.putCharacter('─');
                } else {
                    terminal.putCharacter('═');
                }
            }
            if (figure instanceof Pawn) {
                terminal.putCharacter('┐');
            } else {
                terminal.putCharacter('╗');
            }
            terminal.setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 3);
            if (figure instanceof Pawn) {
                terminal.putCharacter('│');
            } else {
                terminal.putCharacter('║');
            }
            terminal.setCursorPosition(((col - 1) * 7) + 7, (rowInt * 3) + 3);
            if (figure instanceof Pawn) {
                terminal.putCharacter('│');
            } else {
                terminal.putCharacter('║');
            }
            terminal.setCursorPosition(((col - 1) * 7) + 3, (rowInt * 3) + 4);
            if (figure instanceof Pawn) {
                terminal.putCharacter('└');
            } else {
                terminal.putCharacter('╚');
            }
            for (int i = 0; i < 3; i++) {
                if (figure instanceof Pawn) {
                    terminal.putCharacter('─');
                } else {
                    terminal.putCharacter('═');
                }
            }
            if (figure instanceof Pawn) {
                terminal.putCharacter('┘');
            } else {
                terminal.putCharacter('╝');
            }
            if (!figure.getColor()) {
                terminal.setCursorPosition(((col - 1) * 7) + 5, (rowInt * 3) + 3);
                terminal.putCharacter('█');
            }
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    public void printEmptyBoardAndSideMenu() {
        try {
            for (int k = 0; k < 4; k++) {
                terminal.setCursorPosition(2, (6 * k) + 2);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 7; j++) terminal.putCharacter('█');
                    for (int j = 0; j < 7; j++) terminal.putCharacter(' ');
                }
                terminal.setCursorPosition(2, (6 * k) + 3);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 7; j++) terminal.putCharacter('█');
                    for (int j = 0; j < 7; j++) terminal.putCharacter(' ');
                }
                terminal.setCursorPosition(2, (6 * k) + 4);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 7; j++) terminal.putCharacter('█');
                    for (int j = 0; j < 7; j++) terminal.putCharacter(' ');
                }
                terminal.setCursorPosition(2, (6 * k) + 5);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 7; j++) terminal.putCharacter(' ');
                    for (int j = 0; j < 7; j++) terminal.putCharacter('█');
                }
                terminal.setCursorPosition(2, (6 * k) + 6);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 7; j++) terminal.putCharacter(' ');
                    for (int j = 0; j < 7; j++) terminal.putCharacter('█');
                }
                terminal.setCursorPosition(2, (6 * k) + 7);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 7; j++) terminal.putCharacter(' ');
                    for (int j = 0; j < 7; j++) terminal.putCharacter('█');
                }
            }
            terminal.setCursorPosition(1, 1);
            terminal.putCharacter('╔');
            for (int j = 0; j < 56; j++) terminal.putCharacter('═');
            terminal.putCharacter('╗');
            terminal.setCursorPosition(1, 26);
            terminal.putCharacter('╚');
            for (int j = 0; j < 56; j++) terminal.putCharacter('═');
            terminal.putCharacter('╝');
            for (int i = 2; i < 26; i++) {
                terminal.setCursorPosition(1, i);
                terminal.putCharacter('║');
            }
            for (int i = 2; i < 26; i++) {
                terminal.setCursorPosition(58, i);
                terminal.putCharacter('║');
            }
            for (int i = 0; i < 8; i++) {
                terminal.setCursorPosition((i * 7) + 5, 0);
                terminal.putCharacter((char) (i + 49));
                terminal.setCursorPosition((i * 7) + 5, 27);
                terminal.putCharacter((char) (i + 49));
            }

            terminal.setCursorPosition(0, 3);
            terminal.putCharacter('A');
            terminal.setCursorPosition(0, 6);
            terminal.putCharacter('B');
            terminal.setCursorPosition(0, 9);
            terminal.putCharacter('C');
            terminal.setCursorPosition(0, 12);
            terminal.putCharacter('D');
            terminal.setCursorPosition(0, 15);
            terminal.putCharacter('E');
            terminal.setCursorPosition(0, 18);
            terminal.putCharacter('F');
            terminal.setCursorPosition(0, 21);
            terminal.putCharacter('G');
            terminal.setCursorPosition(0, 24);
            terminal.putCharacter('H');
            terminal.setCursorPosition(59, 3);
            terminal.putCharacter('A');
            terminal.setCursorPosition(59, 6);
            terminal.putCharacter('B');
            terminal.setCursorPosition(59, 9);
            terminal.putCharacter('C');
            terminal.setCursorPosition(59, 12);
            terminal.putCharacter('D');
            terminal.setCursorPosition(59, 15);
            terminal.putCharacter('E');
            terminal.setCursorPosition(59, 18);
            terminal.putCharacter('F');
            terminal.setCursorPosition(59, 21);
            terminal.putCharacter('G');
            terminal.setCursorPosition(59, 24);
            terminal.putCharacter('H');
            printRightMenu();
            printBottomMenu();
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    private void printRightMenu() throws IOException {
        terminal.setCursorPosition(72, 0);
        terminal.putCharacter('╔');
        terminal.setCursorPosition(97, 0);
        terminal.putCharacter('╗');
        terminal.setCursorPosition(72, 27);
        terminal.putCharacter('╚');
        terminal.setCursorPosition(97, 27);
        terminal.putCharacter('╝');
        for (int i = 1; i < 27; i++) {
            terminal.setCursorPosition(72, i);
            terminal.putCharacter('║');
            terminal.setCursorPosition(97, i);
            terminal.putCharacter('║');
        }
        terminal.setCursorPosition(73, 0);
        for (int i = 0; i < 24; i++) terminal.putCharacter('═');
        terminal.setCursorPosition(73, 2);
        for (int i = 0; i < 24; i++) terminal.putCharacter('═');
        terminal.setCursorPosition(73, 6);
        for (int i = 0; i < 24; i++) terminal.putCharacter('═');
        terminal.setCursorPosition(73, 10);
        for (int i = 0; i < 24; i++) terminal.putCharacter('═');
        terminal.setCursorPosition(73, 15);
        for (int i = 0; i < 24; i++) terminal.putCharacter('═');
        terminal.setCursorPosition(73, 27);
        for (int i = 0; i < 24; i++) terminal.putCharacter('═');

        terminal.setCursorPosition(72, 2);
        terminal.putCharacter('╠');
        terminal.setCursorPosition(72, 6);
        terminal.putCharacter('╠');
        terminal.setCursorPosition(72, 10);
        terminal.putCharacter('╠');
        terminal.setCursorPosition(72, 15);
        terminal.putCharacter('╠');

        terminal.setCursorPosition(97, 2);
        terminal.putCharacter('╣');
        terminal.setCursorPosition(97, 6);
        terminal.putCharacter('╣');
        terminal.setCursorPosition(97, 10);
        terminal.putCharacter('╣');
        terminal.setCursorPosition(97, 15);
        terminal.putCharacter('╣');

        terminal.setCursorPosition(80, 0);
        terminal.putCharacter('╦');
        terminal.setCursorPosition(88, 0);
        terminal.putCharacter('╦');

        for (int i = 1; i < 10; i++) {
            terminal.setCursorPosition(80, i);
            terminal.putCharacter('║');
            terminal.setCursorPosition(88, i);
            terminal.putCharacter('║');
        }

        terminal.setCursorPosition(80, 2);
        terminal.putCharacter('╬');
        terminal.setCursorPosition(80, 6);
        terminal.putCharacter('╬');
        terminal.setCursorPosition(88, 2);
        terminal.putCharacter('╬');
        terminal.setCursorPosition(88, 6);
        terminal.putCharacter('╬');

        terminal.setCursorPosition(80, 10);
        terminal.putCharacter('╩');
        terminal.setCursorPosition(88, 10);
        terminal.putCharacter('╩');

        terminal.setCursorPosition(74, 1);
        terminal.putCharacter('M');
        terminal.putCharacter('E');
        terminal.putCharacter('N');

        terminal.setCursorPosition(82, 1);
        terminal.putCharacter('K');
        terminal.putCharacter('I');
        terminal.putCharacter('N');
        terminal.putCharacter('G');

        terminal.setCursorPosition(74, 3);
        terminal.putCharacter('┌');
        for (int i = 0; i < 3; i++) terminal.putCharacter('─');
        terminal.putCharacter('┐');
        terminal.setCursorPosition(82, 3);
        terminal.putCharacter('╔');
        for (int i = 0; i < 3; i++) terminal.putCharacter('═');
        terminal.putCharacter('╗');
        terminal.setCursorPosition(74, 4);
        terminal.putCharacter('│');
        terminal.setCursorPosition(78, 4);
        terminal.putCharacter('│');
        terminal.setCursorPosition(82, 4);
        terminal.putCharacter('║');
        terminal.setCursorPosition(86, 4);
        terminal.putCharacter('║');
        terminal.setCursorPosition(90, 4);
        terminal.putCharacter('B');
        terminal.putCharacter('L');
        terminal.putCharacter('A');
        terminal.putCharacter('C');
        terminal.putCharacter('K');
        terminal.setCursorPosition(74, 5);
        terminal.putCharacter('└');
        for (int i = 0; i < 3; i++) terminal.putCharacter('─');
        terminal.putCharacter('┘');
        terminal.setCursorPosition(82, 5);
        terminal.putCharacter('╚');
        for (int i = 0; i < 3; i++) terminal.putCharacter('═');
        terminal.putCharacter('╝');

        terminal.setCursorPosition(74, 7);
        terminal.putCharacter('┌');
        for (int i = 0; i < 3; i++) terminal.putCharacter('─');
        terminal.putCharacter('┐');
        terminal.setCursorPosition(82, 7);
        terminal.putCharacter('╔');
        for (int i = 0; i < 3; i++) terminal.putCharacter('═');
        terminal.putCharacter('╗');
        terminal.setCursorPosition(74, 8);
        terminal.putCharacter('│');
        terminal.setCursorPosition(78, 8);
        terminal.putCharacter('│');
        terminal.setCursorPosition(82, 8);
        terminal.putCharacter('║');
        terminal.setCursorPosition(86, 8);
        terminal.putCharacter('║');
        terminal.setCursorPosition(90, 8);
        terminal.putCharacter('W');
        terminal.putCharacter('H');
        terminal.putCharacter('I');
        terminal.putCharacter('T');
        terminal.putCharacter('E');
        terminal.setCursorPosition(74, 9);
        terminal.putCharacter('└');
        for (int i = 0; i < 3; i++) terminal.putCharacter('─');
        terminal.putCharacter('┘');
        terminal.setCursorPosition(82, 9);
        terminal.putCharacter('╚');
        for (int i = 0; i < 3; i++) terminal.putCharacter('═');
        terminal.putCharacter('╝');

        terminal.setCursorPosition(74, 11);
        terminal.putCharacter('M');
        terminal.putCharacter('E');
        terminal.putCharacter('N');
        terminal.putCharacter('U');
        terminal.setCursorPosition(73, 13);
        terminal.putCharacter('(');
        terminal.putCharacter('s');
        terminal.putCharacter(')');
        terminal.putCharacter(' ');
        terminal.putCharacter('s');
        terminal.putCharacter('a');
        terminal.putCharacter('v');
        terminal.putCharacter('e');
        terminal.putCharacter(' ');
        terminal.putCharacter('a');
        terminal.putCharacter('n');
        terminal.putCharacter('d');
        terminal.putCharacter(' ');
        terminal.putCharacter('e');
        terminal.putCharacter('x');
        terminal.putCharacter('i');
        terminal.putCharacter('t');
        terminal.setCursorPosition(73, 14);
        terminal.putCharacter('(');
        terminal.putCharacter('x');
        terminal.putCharacter(')');
        terminal.putCharacter(' ');
        terminal.putCharacter('e');
        terminal.putCharacter('x');
        terminal.putCharacter('i');
        terminal.putCharacter('t');
        terminal.putCharacter(' ');
        terminal.putCharacter('w');
        terminal.putCharacter('i');
        terminal.putCharacter('t');
        terminal.putCharacter('h');
        terminal.putCharacter('o');
        terminal.putCharacter('u');
        terminal.putCharacter('t');
        terminal.putCharacter(' ');
        terminal.putCharacter('s');
        terminal.putCharacter('a');
        terminal.putCharacter('v');
        terminal.putCharacter('i');
        terminal.putCharacter('n');
        terminal.putCharacter('g');
        terminal.setCursorPosition(79, 16);
        terminal.putCharacter('L');
        terminal.putCharacter('A');
        terminal.putCharacter('S');
        terminal.putCharacter('T');
        terminal.putCharacter(' ');
        terminal.putCharacter('1');
        terminal.putCharacter('0');
        terminal.putCharacter(' ');
        terminal.putCharacter('M');
        terminal.putCharacter('O');
        terminal.putCharacter('V');
        terminal.putCharacter('E');
        terminal.putCharacter('S');
    }

    private void printBottomMenu() throws IOException {
        terminal.setCursorPosition(2, 28);
        for (int i = 0; i < 95; i++) terminal.putCharacter('═');
        terminal.setCursorPosition(2, 30);
        for (int i = 0; i < 95; i++) terminal.putCharacter('═');
        terminal.setCursorPosition(2, 32);
        for (int i = 0; i < 95; i++) terminal.putCharacter('═');
        terminal.setCursorPosition(1, 29);
        terminal.putCharacter('║');
        terminal.setCursorPosition(35, 29);
        terminal.putCharacter('║');
        terminal.setCursorPosition(97, 29);
        terminal.putCharacter('║');
        terminal.setCursorPosition(1, 31);
        terminal.putCharacter('║');
        terminal.setCursorPosition(97, 31);
        terminal.putCharacter('║');
        terminal.setCursorPosition(1, 28);
        terminal.putCharacter('╔');
        terminal.setCursorPosition(97, 28);
        terminal.putCharacter('╗');
        terminal.setCursorPosition(1, 30);
        terminal.putCharacter('╠');
        terminal.setCursorPosition(97, 30);
        terminal.putCharacter('╣');
        terminal.setCursorPosition(35, 28);
        terminal.putCharacter('╦');
        terminal.setCursorPosition(35, 30);
        terminal.putCharacter('╩');
        terminal.setCursorPosition(1, 32);
        terminal.putCharacter('╚');
        terminal.setCursorPosition(97, 32);
        terminal.putCharacter('╝');
        terminal.setCursorPosition(3, 29);
        terminal.putCharacter('A');
        terminal.putCharacter('c');
        terminal.putCharacter('t');
        terminal.putCharacter('i');
        terminal.putCharacter('v');
        terminal.putCharacter('e');
        terminal.putCharacter(' ');
        terminal.putCharacter('p');
        terminal.putCharacter('l');
        terminal.putCharacter('a');
        terminal.putCharacter('y');
        terminal.putCharacter('e');
        terminal.putCharacter('r');
        terminal.putCharacter(':');
    }

}