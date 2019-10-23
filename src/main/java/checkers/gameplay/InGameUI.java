package checkers.gameplay;

import checkers.Menu;
import checkers.board.Board;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import exceptions.IncorrectMoveFormat;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class InGameUI implements Serializable {

    private final String[] options;
    private final transient Scanner sc;

    public InGameUI(Scanner sc) {
        options = new String[]{/*"h", */"s", "x"};  // TODO
        this.sc = sc;
    }

    public void printMoveHistory(List<String> moves) {
        if (moves.isEmpty())
            System.out.println("No moves history.");
        else
            for (String m : moves)
                System.out.println(m);
    }

    public String getGameName() {
        System.out.println("Name your game:");
        String name;
        do {
            name = sc.nextLine();
        } while (name.isEmpty());
        return name;
    }

    private void printMovesAndActivePlayer(List<String> moves, boolean player, boolean isItAITurn) {
        if (player) {
            STerminal.getInstance().putCharAtPosition('╔', 89, 3);
            STerminal.getInstance().putCharMultipled('═', 6);
            STerminal.getInstance().putCharacter('╗');
            STerminal.getInstance().putCharAtPosition('║', 89, 4);
            STerminal.getInstance().putCharAtPosition('║', 96, 4);
            STerminal.getInstance().putCharAtPosition('╚', 89, 5);
            STerminal.getInstance().putCharMultipled('═', 6);
            STerminal.getInstance().putCharacter('╝');
            STerminal.getInstance().setCursorPosition(89, 7);
            STerminal.getInstance().putCharMultipled(' ', 8);
            STerminal.getInstance().putCharAtPosition(' ', 89, 8);
            STerminal.getInstance().putCharAtPosition(' ', 96, 8);
            STerminal.getInstance().setCursorPosition(89, 9);
            STerminal.getInstance().putCharMultipled(' ', 8);
            STerminal.getInstance().putStringAtPosition("BLACK", 18, 29);
        } else {
            STerminal.getInstance().putCharAtPosition('╔', 89, 7);
            STerminal.getInstance().putCharMultipled('═', 6);
            STerminal.getInstance().putCharacter('╗');
            STerminal.getInstance().putCharAtPosition('║', 89, 8);
            STerminal.getInstance().putCharAtPosition('║', 96, 8);
            STerminal.getInstance().putCharAtPosition('╚', 89, 9);
            STerminal.getInstance().putCharMultipled('═', 6);
            STerminal.getInstance().putCharacter('╝');
            STerminal.getInstance().setCursorPosition(89, 3);
            STerminal.getInstance().putCharMultipled(' ', 8);
            STerminal.getInstance().putCharAtPosition(' ', 89, 4);
            STerminal.getInstance().putCharAtPosition(' ', 96, 4);
            STerminal.getInstance().setCursorPosition(89, 5);
            STerminal.getInstance().putCharMultipled(' ', 8);
            STerminal.getInstance().putStringAtPosition("WHITE", 18, 29);
        }
        if (isItAITurn) STerminal.getInstance().putStringAtPosition("(computer)", 24, 29);

        for (int i = 0; i < 10; i++) {
            if (i == moves.size()) break;
            String move = moves.get(moves.size() - 1 - i);
            STerminal.getInstance().putStringAtPosition(move, 79, i + 17);
        }
    }

    public void waitForEnter() {
        // TODO : remove ???
    }

    void printBoard(Board board, boolean player, List<String> moves,
                    RulesSet rulesSet, boolean isItAITurn) {    // TODO: refactor to use terminals wrapper
        // Menu.printRulesSet(rulesSet); TODO
        printMovesAndActivePlayer(moves, player, isItAITurn);
        STerminal.getInstance().setCursorPosition(37, 29);
        if (isItAITurn) {
            STerminal.getInstance().putCharacter('P');
            STerminal.getInstance().putCharacter('l');
            STerminal.getInstance().putCharacter('e');
            STerminal.getInstance().putCharacter('a');
            STerminal.getInstance().putCharacter('s');
            STerminal.getInstance().putCharacter('e');
            STerminal.getInstance().putCharacter(' ');
            STerminal.getInstance().putCharacter('w');
            STerminal.getInstance().putCharacter('a');
            STerminal.getInstance().putCharacter('i');
            STerminal.getInstance().putCharacter('t');
            STerminal.getInstance().putCharacter(' ');
            STerminal.getInstance().putCharacter('f');
            STerminal.getInstance().putCharacter('o');
            STerminal.getInstance().putCharacter('r');
            STerminal.getInstance().putCharacter(' ');
            STerminal.getInstance().putCharacter('m');
            STerminal.getInstance().putCharacter('o');
            STerminal.getInstance().putCharacter('v');
            STerminal.getInstance().putCharacter('e');
            STerminal.getInstance().putCharacter('.');
            for (int i = 0; i < 39; i++) STerminal.getInstance().putCharacter(' ');
        } else {
            STerminal.getInstance().putCharacter('E');
            STerminal.getInstance().putCharacter('n');
            STerminal.getInstance().putCharacter('t');
            STerminal.getInstance().putCharacter('e');
            STerminal.getInstance().putCharacter('r');
            STerminal.getInstance().putCharacter(' ');
            STerminal.getInstance().putCharacter('y');
            STerminal.getInstance().putCharacter('o');
            STerminal.getInstance().putCharacter('u');
            STerminal.getInstance().putCharacter('r');
            STerminal.getInstance().putCharacter(' ');
            STerminal.getInstance().putCharacter('n');
            STerminal.getInstance().putCharacter('e');
            STerminal.getInstance().putCharacter('x');
            STerminal.getInstance().putCharacter('t');
            STerminal.getInstance().putCharacter(' ');
            STerminal.getInstance().putCharacter('m');
            STerminal.getInstance().putCharacter('o');
            STerminal.getInstance().putCharacter('v');
            STerminal.getInstance().putCharacter('e');
            STerminal.getInstance().putCharacter('.');
            for (int i = 0; i < 39; i++) STerminal.getInstance().putCharacter(' ');
        }
    }

    public void printMakingMove(char x1, int y1, char x2, int y2, boolean isItAITurn) {    // TODO: refactor to use terminals wrapper
        if (isItAITurn)
            return;
        STerminal.getInstance().setCursorPosition(3, 31);
        STerminal.getInstance().putCharacter('M');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('k');
        STerminal.getInstance().putCharacter('i');
        STerminal.getInstance().putCharacter('n');
        STerminal.getInstance().putCharacter('g');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('m');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('v');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter(':');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter(x1);
        STerminal.getInstance().putCharacter((char) (y1 + 48));
        STerminal.getInstance().putCharacter('-');
        STerminal.getInstance().putCharacter(x2);
        STerminal.getInstance().putCharacter((char) (y2 + 48));
        for (int i = 0; i < 76; i++) STerminal.getInstance().putCharacter(' ');
    }

    public void printMoveDone(boolean isItAITurn) {    // TODO: refactor to use terminals wrapper
        if (isItAITurn)
            return;
        STerminal.getInstance().setCursorPosition(3, 31);
        STerminal.getInstance().putCharacter('M');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('v');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('d');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('n');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter('.');
        for (int i = 0; i < 84; i++) STerminal.getInstance().putCharacter(' ');
    }

    public void printCaptureDone(boolean isItAITurn) {    // TODO: refactor to use terminals wrapper
        if (isItAITurn)
            return;
        STerminal.getInstance().setCursorPosition(3, 31);
        STerminal.getInstance().putCharacter('C');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('p');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter('u');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('d');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('n');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter('.');
        for (int i = 0; i < 81; i++) STerminal.getInstance().putCharacter(' ');
    }

    public void printIncorrectMove(String s, boolean isItAITurn) {    // TODO: refactor to use terminals wrapper
        if (isItAITurn)
            return;
        STerminal.getInstance().setCursorPosition(3, 31);
        STerminal.getInstance().putCharacter('I');
        STerminal.getInstance().putCharacter('n');
        STerminal.getInstance().putCharacter('c');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter('c');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('m');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('v');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter(':');
        STerminal.getInstance().putCharacter(' ');
        for (int i = 0; i < s.length(); i++) STerminal.getInstance().putCharacter(s.charAt(i));
        for (int i = 0; i < 78 - s.length(); i++) STerminal.getInstance().putCharacter(' ');
    }

    public void printCapture(String captures, boolean isItAITurn) {    // TODO: refactor to use terminals wrapper
        if (isItAITurn)
            return;
        STerminal.getInstance().setCursorPosition(3, 31);
        STerminal.getInstance().putCharacter('Y');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('u');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('h');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('v');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('c');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('p');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter('u');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter(':');
        STerminal.getInstance().putCharacter(' ');
        for (int i = 0; i < captures.length(); i++) STerminal.getInstance().putCharacter(captures.charAt(i));
        for (int i = 0; i < 73 - captures.length(); i++) STerminal.getInstance().putCharacter(' ');
    }

    public void printMultiCapture(String captures, boolean isItAITurn) {    // TODO: refactor to use terminals wrapper
        if (isItAITurn)
            return;
        STerminal.getInstance().setCursorPosition(3, 31);
        STerminal.getInstance().putCharacter('P');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('s');
        STerminal.getInstance().putCharacter('s');
        STerminal.getInstance().putCharacter('i');
        STerminal.getInstance().putCharacter('b');
        STerminal.getInstance().putCharacter('l');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('c');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('p');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter('u');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter('s');
        STerminal.getInstance().putCharacter(':');
        STerminal.getInstance().putCharacter(' ');
        for (int i = 0; i < captures.length(); i++) STerminal.getInstance().putCharacter(captures.charAt(i));
        for (int i = 0; i < 75 - captures.length(); i++) STerminal.getInstance().putCharacter(' ');
    }

    public void printCaptureObligatory(boolean isItAITurn) {    // TODO: refactor to use terminals wrapper
        if (isItAITurn)
            return;
        STerminal.getInstance().setCursorPosition(3, 31);
        STerminal.getInstance().putCharacter('C');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('p');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter('u');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('i');
        STerminal.getInstance().putCharacter('s');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('b');
        STerminal.getInstance().putCharacter('l');
        STerminal.getInstance().putCharacter('i');
        STerminal.getInstance().putCharacter('g');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter('y');
        STerminal.getInstance().putCharacter('!');
        for (int i = 0; i < 72; i++) STerminal.getInstance().putCharacter(' ');
    }

    public void printIncorrectMoveFormat(boolean isItAITurn) {    // TODO: refactor to use terminals wrapper
        if (isItAITurn)
            return;
        STerminal.getInstance().setCursorPosition(3, 31);
        STerminal.getInstance().putCharacter('I');
        STerminal.getInstance().putCharacter('n');
        STerminal.getInstance().putCharacter('c');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter('c');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('m');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('v');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('f');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter('m');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter('!');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('P');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('p');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('f');
        STerminal.getInstance().putCharacter('o');
        STerminal.getInstance().putCharacter('r');
        STerminal.getInstance().putCharacter('m');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('t');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter('x');
        STerminal.getInstance().putCharacter('a');
        STerminal.getInstance().putCharacter('m');
        STerminal.getInstance().putCharacter('p');
        STerminal.getInstance().putCharacter('l');
        STerminal.getInstance().putCharacter('e');
        STerminal.getInstance().putCharacter(' ');
        STerminal.getInstance().putCharacter('E');
        STerminal.getInstance().putCharacter('4');
        STerminal.getInstance().putCharacter('-');
        STerminal.getInstance().putCharacter('D');
        STerminal.getInstance().putCharacter('5');
        for (int i = 0; i < 43; i++) STerminal.getInstance().putCharacter(' ');
    }

    private void setCursor(int row, int col, int chosenRow, int chosenCol) {    // TODO: refactor to use terminals wrapper
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                    STerminal.getInstance().setCursorPosition((i * 7) + 2, (j * 3) + 2);
                    STerminal.getInstance().putCharacter(' ');
                    STerminal.getInstance().setCursorPosition((i * 7) + 2, (j * 3) + 3);
                    STerminal.getInstance().putCharacter(' ');
                    STerminal.getInstance().setCursorPosition((i * 7) + 2, (j * 3) + 4);
                    STerminal.getInstance().putCharacter(' ');

                    STerminal.getInstance().setCursorPosition((i * 7) + 8, (j * 3) + 2);
                    STerminal.getInstance().putCharacter(' ');
                    STerminal.getInstance().setCursorPosition((i * 7) + 8, (j * 3) + 3);
                    STerminal.getInstance().putCharacter(' ');
                    STerminal.getInstance().setCursorPosition((i * 7) + 8, (j * 3) + 4);
                    STerminal.getInstance().putCharacter(' ');
                } else {
                    STerminal.getInstance().setCursorPosition((i * 7) + 2, (j * 3) + 2);
                    STerminal.getInstance().putCharacter('█');
                    STerminal.getInstance().setCursorPosition((i * 7) + 2, (j * 3) + 3);
                    STerminal.getInstance().putCharacter('█');
                    STerminal.getInstance().setCursorPosition((i * 7) + 2, (j * 3) + 4);
                    STerminal.getInstance().putCharacter('█');

                    STerminal.getInstance().setCursorPosition((i * 7) + 8, (j * 3) + 2);
                    STerminal.getInstance().putCharacter('█');
                    STerminal.getInstance().setCursorPosition((i * 7) + 8, (j * 3) + 3);
                    STerminal.getInstance().putCharacter('█');
                    STerminal.getInstance().setCursorPosition((i * 7) + 8, (j * 3) + 4);
                    STerminal.getInstance().putCharacter('█');
                }
            }
        }
        if (row != 0 && col != 0) {
            col--;
            row--;
            if ((col % 2 == 0 && row % 2 == 1) || (col % 2 == 1 && row % 2 == 0)) {
                STerminal.getInstance().setCursorPosition((col * 7) + 2, (row * 3) + 2);
                STerminal.getInstance().putCharacter('▐');
                STerminal.getInstance().setCursorPosition((col * 7) + 2, (row * 3) + 3);
                STerminal.getInstance().putCharacter('▐');
                STerminal.getInstance().setCursorPosition((col * 7) + 2, (row * 3) + 4);
                STerminal.getInstance().putCharacter('▐');

                STerminal.getInstance().setCursorPosition((col * 7) + 8, (row * 3) + 2);
                STerminal.getInstance().putCharacter('▌');
                STerminal.getInstance().setCursorPosition((col * 7) + 8, (row * 3) + 3);
                STerminal.getInstance().putCharacter('▌');
                STerminal.getInstance().setCursorPosition((col * 7) + 8, (row * 3) + 4);
                STerminal.getInstance().putCharacter('▌');
            } else {
                STerminal.getInstance().setCursorPosition((col * 7) + 2, (row * 3) + 2);
                STerminal.getInstance().putCharacter('▌');
                STerminal.getInstance().setCursorPosition((col * 7) + 2, (row * 3) + 3);
                STerminal.getInstance().putCharacter('▌');
                STerminal.getInstance().setCursorPosition((col * 7) + 2, (row * 3) + 4);
                STerminal.getInstance().putCharacter('▌');

                STerminal.getInstance().setCursorPosition((col * 7) + 8, (row * 3) + 2);
                STerminal.getInstance().putCharacter('▐');
                STerminal.getInstance().setCursorPosition((col * 7) + 8, (row * 3) + 3);
                STerminal.getInstance().putCharacter('▐');
                STerminal.getInstance().setCursorPosition((col * 7) + 8, (row * 3) + 4);
                STerminal.getInstance().putCharacter('▐');
            }
            if (chosenRow != 0 && chosenCol != 0) {
                chosenCol--;
                chosenRow--;
                if ((chosenCol % 2 == 0 && chosenRow % 2 == 1) || (chosenCol % 2 == 1 && chosenRow % 2 == 0)) {
                    STerminal.getInstance().setCursorPosition((chosenCol * 7) + 2, (chosenRow * 3) + 2);
                    STerminal.getInstance().putCharacter('▐');
                    STerminal.getInstance().setCursorPosition((chosenCol * 7) + 2, (chosenRow * 3) + 3);
                    STerminal.getInstance().putCharacter('▐');
                    STerminal.getInstance().setCursorPosition((chosenCol * 7) + 2, (chosenRow * 3) + 4);
                    STerminal.getInstance().putCharacter('▐');

                    STerminal.getInstance().setCursorPosition((chosenCol * 7) + 8, (chosenRow * 3) + 2);
                    STerminal.getInstance().putCharacter('▌');
                    STerminal.getInstance().setCursorPosition((chosenCol * 7) + 8, (chosenRow * 3) + 3);
                    STerminal.getInstance().putCharacter('▌');
                    STerminal.getInstance().setCursorPosition((chosenCol * 7) + 8, (chosenRow * 3) + 4);
                    STerminal.getInstance().putCharacter('▌');
                } else {
                    STerminal.getInstance().setCursorPosition((chosenCol * 7) + 2, (chosenRow * 3) + 2);
                    STerminal.getInstance().putCharacter('▌');
                    STerminal.getInstance().setCursorPosition((chosenCol * 7) + 2, (chosenRow * 3) + 3);
                    STerminal.getInstance().putCharacter('▌');
                    STerminal.getInstance().setCursorPosition((chosenCol * 7) + 2, (chosenRow * 3) + 4);
                    STerminal.getInstance().putCharacter('▌');

                    STerminal.getInstance().setCursorPosition((chosenCol * 7) + 8, (chosenRow * 3) + 2);
                    STerminal.getInstance().putCharacter('▐');
                    STerminal.getInstance().setCursorPosition((chosenCol * 7) + 8, (chosenRow * 3) + 3);
                    STerminal.getInstance().putCharacter('▐');
                    STerminal.getInstance().setCursorPosition((chosenCol * 7) + 8, (chosenRow * 3) + 4);
                    STerminal.getInstance().putCharacter('▐');
                }
            }
        }
    }

    public String[] getMoveOrOption(String captures, boolean isItAITurn, boolean player) {
        int cursorRow = 0;
        int cursorCol = 0;
        if (!isItAITurn) {
            if (player) {
                cursorCol = 1;
                cursorRow = 1;
            } else {
                cursorCol = 8;
                cursorRow = 8;
            }
        }
        String[] result = new String[4];
        do {
            int chosenRow = 0;
            int chosenCol = 0;
            if (result[0] != null && result[1] != null) {
                chosenRow = result[0].charAt(0) - 64;
                chosenCol = Integer.valueOf(result[1]);
            }
            String s;
            setCursor(cursorRow, cursorCol, chosenRow, chosenCol);
            KeyStroke key;
            do {
                key = STerminal.getInstance().readInput();
            } while (key == null);
            if (key.getKeyType() == KeyType.Character) {
                s = key.getCharacter().toString();
                for (String o : options) {
                    if (s.equals(o)) {
                        result = new String[1];
                        result[0] = s;
                        return result;
                    }
                }
            } else if (key.getKeyType() == KeyType.ArrowDown && cursorRow < 8) {
                cursorRow++;
                setCursor(cursorRow, cursorCol, chosenRow, chosenCol);
            } else if (key.getKeyType() == KeyType.ArrowUp && cursorRow > 1) {
                cursorRow--;
                setCursor(cursorRow, cursorCol, chosenRow, chosenCol);
            } else if (key.getKeyType() == KeyType.ArrowLeft && cursorCol > 1) {
                cursorCol--;
                setCursor(cursorRow, cursorCol, chosenRow, chosenCol);
            } else if (key.getKeyType() == KeyType.ArrowRight && cursorCol < 8) {
                cursorCol++;
                setCursor(cursorRow, cursorCol, chosenRow, chosenCol);
            } else if (key.getKeyType() == KeyType.Enter) {
                System.out.println("GOT ENTER");
                if (result[0] == null && result[1] == null) {
                    result[0] = Character.toString((char) (cursorRow + 64));
                    result[1] = Integer.toString(cursorCol);
                    System.out.println("SETTING FIRST CHOICE: " + result[0] + ", " + result[1]);
                } else {
                    result[2] = Character.toString((char) (cursorRow + 64));
                    result[3] = Integer.toString(cursorCol);
                    System.out.println("SETTING SECOND CHOICE: " + result[2] + ", " + result[3]);
                    String moveToValidate = result[0] +
                            result[1] +
                            "-" +
                            result[2] +
                            result[3];
                    System.out.println("VALIDATING MOVE: " + moveToValidate);
                    try {
                        validate(moveToValidate);
                        if (!captures.isEmpty() && !captures.contains(moveToValidate)) {
                            printCaptureObligatory(isItAITurn);
                            return null;
                        }
                    } catch (IncorrectMoveFormat e) {
                        printIncorrectMoveFormat(isItAITurn);
                        return null;
                    }
                    return result;
                }
            }
        }
        while (true);
//        String s;
//        s = sc.nextLine();
//        s = s.toLowerCase();
//        String[] result;
//        for (String o : options) {
//            if (s.equals(o)) {
//                result = new String[1];
//                result[0] = s;
//                return result;
//            }
//        }
//        s = s.toUpperCase();
//        try {
//            validate(s);
//            if (captures.isEmpty() || captures.contains(s)) {
//                String[] sArray = s.split("-");
//                char x1 = sArray[0].charAt(0);
//                int y1 = Character.getNumericValue(sArray[0].charAt(1));
//                char x2 = sArray[1].charAt(0);
//                int y2 = Character.getNumericValue(sArray[1].charAt(1));
//                result = new String[4];
//                result[0] = "" + x1;
//                result[1] = "" + y1;
//                result[2] = "" + x2;
//                result[3] = "" + y2;
//            } else {
//                printCaptureObligatory(isItAITurn);
//                return null;
//            }
//        } catch (IncorrectMoveFormat e) {
//            printIncorrectMoveFormat(isItAITurn);
//            return null;
//        }
//        return result;
    }

    private void validate(String s) throws IncorrectMoveFormat {
        String[] sArray = s.split("-");
        if (sArray.length != 2)
            throw new IncorrectMoveFormat();
        for (String t : sArray)
            if (t.length() != 2)
                throw new IncorrectMoveFormat();
    }

    public boolean endOfGame(Board board, List<String> moves, boolean player) {
        Menu.cls();
        printMovesAndActivePlayer(moves, player, false);
        if (VictoryValidator.isDraw()) {
            System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println(" ║ ███    █   █     █ ████				 					 ║");
            System.out.println(" ║█   █  █ █  ██   ██ █					 					 ║");
            System.out.println(" ║█     █   █ █ █ █ █ █					 	███   ████    █	  █	█	 ║");
            System.out.println(" ║█  ██ █   █ █  █  █ ███				 	█  █  █	  █  █ █  █	█	 ║");
            System.out.println(" ║█   █ █████ █     █ █      ███  █     █ ████ ████   ██	█   █ █   █ █   █ █	█	 ║");
            System.out.println(" ║ ███  █   █ █     █ ████  █   █  █   █  █    █   █  ██	█   █ ████  █   █ █  █	█	 ║");
            System.out.println(" ║			    █   █  █   █  █    █   █  ██	█  █  █	 █  █████ █ █ █	█	 ║");
            System.out.println(" ║			    █   █   █ █   ███  ████   ██        ███   █	  █ █   █ ██   ██	 ║");
            System.out.println(" ║			    █   █   █ █   █    █  █						 ║");
            System.out.println(" ║			     ███     █    ████ █   █  ██					 ║");
            System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
        } else {
            if (VictoryValidator.getWinner()) {
                System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println(" ║ ███    █   █     █ ████				 	█   █    ██   ██ █		 ║");
                System.out.println(" ║█   █  █ █  ██   ██ █					 	██  █   █  █ █   █ █		 ║");
                System.out.println(" ║█     █   █ █ █ █ █ █					 	█ █ █   ████ █   ██		 ║");
                System.out.println(" ║█  ██ █   █ █  █  █ ███				 	██  ███ █  █  ██ █ █		 ║");
                System.out.println(" ║█   █ █████ █     █ █      ███  █     █ ████ ████   ██					 ║");
                System.out.println(" ║ ███  █   █ █     █ ████  █   █  █   █  █    █   █  ██		█   █ █ █   █  ███	 ║");
                System.out.println(" ║			    █   █  █   █  █    █   █  ██		█   █ █ ██  █ █	 	 ║");
                System.out.println(" ║			    █   █   █ █   ███  ████   ██  		█ █ █ █ █ █ █  ██	 ║");
                System.out.println(" ║			    █   █   █ █   █    █  █			██ ██ █ █  ██    █	 ║");
                System.out.println(" ║			     ███     █    ████ █   █  ██		█   █ █	█   █ ███ 	 ║");
                System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
            } else {
                System.out.println(" ╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println(" ║ ███    █   █     █ ████				 	█   █ █ █ █ ███ ███		 ║");
                System.out.println(" ║█   █  █ █  ██   ██ █					 	█   █ █ █ █  █  █		 ║");
                System.out.println(" ║█     █   █ █ █ █ █ █					 	█ █ █ ███ █  █  ██		 ║");
                System.out.println(" ║█  ██ █   █ █  █  █ ███				 	██ ██ █ █ █  █  ███		 ║");
                System.out.println(" ║█   █ █████ █     █ █      ███  █     █ ████ ████   ██					 ║");
                System.out.println(" ║ ███  █   █ █     █ ████  █   █  █   █  █    █   █  ██		█   █ █ █   █  ███	 ║");
                System.out.println(" ║			    █   █  █   █  █    █   █  ██		█   █ █ ██  █ █	 	 ║");
                System.out.println(" ║			    █   █   █ █   ███  ████   ██  		█ █ █ █ █ █ █  ██	 ║");
                System.out.println(" ║			    █   █   █ █   █    █  █			██ ██ █ █  ██    █	 ║");
                System.out.println(" ║			     ███     █    ████ █   █  ██		█   █ █	█   █ ███	 ║");
                System.out.println(" ╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
            }
        }
        String o;
        do {
            o = sc.nextLine();
            switch (o) {
                // TODO: find solution
//                case "h":
//                    System.out.println("Moves history:");
//                    printMoveHistory(moves);
//                    break;
                case "s":
                    return true;
                case "x":
                    return false;
            }
        } while (true);
    }

}
