package checkers.gameplay;

import checkers.Menu;
import checkers.board.Board;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;
import exceptions.IncorrectMoveFormat;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class InGameUI implements Serializable {

    private final String[] options;
    private final transient Scanner sc;
    private Terminal terminal;

    public InGameUI(Scanner sc, Terminal terminal) {
        options = new String[]{"h", "p", "s", "x"};
        this.sc = sc;
        this.terminal = terminal;
    }

    public void printMoveHistory(List<String> moves) {
        if (moves.isEmpty())
            System.out.println("No moves history.");
        else
            for (String m : moves)
                System.out.println(m);
    }

    void setTerminal(Terminal terminal) {
        this.terminal = terminal;
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
        try {
            if (player) {
                terminal.setCursorPosition(89, 3);
                terminal.putCharacter('╔');
                for (int i = 0; i < 6; i++) terminal.putCharacter('═');
                terminal.putCharacter('╗');
                terminal.setCursorPosition(89, 4);
                terminal.putCharacter('║');
                terminal.setCursorPosition(96, 4);
                terminal.putCharacter('║');
                terminal.setCursorPosition(89, 5);
                terminal.putCharacter('╚');
                for (int i = 0; i < 6; i++) terminal.putCharacter('═');
                terminal.putCharacter('╝');

                terminal.setCursorPosition(89, 7);
                for (int i = 0; i < 8; i++) terminal.putCharacter(' ');
                terminal.setCursorPosition(89, 8);
                terminal.putCharacter(' ');
                terminal.setCursorPosition(96, 8);
                terminal.putCharacter(' ');
                terminal.setCursorPosition(89, 9);
                for (int i = 0; i < 8; i++) terminal.putCharacter(' ');

                terminal.setCursorPosition(18, 29);
                terminal.putCharacter('B');
                terminal.putCharacter('L');
                terminal.putCharacter('A');
                terminal.putCharacter('C');
                terminal.putCharacter('K');
            } else {
                terminal.setCursorPosition(89, 7);
                terminal.putCharacter('╔');
                for (int i = 0; i < 6; i++) terminal.putCharacter('═');
                terminal.putCharacter('╗');
                terminal.setCursorPosition(89, 8);
                terminal.putCharacter('║');
                terminal.setCursorPosition(96, 8);
                terminal.putCharacter('║');
                terminal.setCursorPosition(89, 9);
                terminal.putCharacter('╚');
                for (int i = 0; i < 6; i++) terminal.putCharacter('═');
                terminal.putCharacter('╝');

                terminal.setCursorPosition(89, 3);
                for (int i = 0; i < 8; i++) terminal.putCharacter(' ');
                terminal.setCursorPosition(89, 4);
                terminal.putCharacter(' ');
                terminal.setCursorPosition(96, 4);
                terminal.putCharacter(' ');
                terminal.setCursorPosition(89, 5);
                for (int i = 0; i < 8; i++) terminal.putCharacter(' ');

                terminal.setCursorPosition(18, 29);
                terminal.putCharacter('W');
                terminal.putCharacter('H');
                terminal.putCharacter('I');
                terminal.putCharacter('T');
                terminal.putCharacter('E');
            }
            if (isItAITurn) {
                terminal.putCharacter(' ');
                terminal.putCharacter('(');
                terminal.putCharacter('c');
                terminal.putCharacter('o');
                terminal.putCharacter('m');
                terminal.putCharacter('p');
                terminal.putCharacter('u');
                terminal.putCharacter('t');
                terminal.putCharacter('e');
                terminal.putCharacter('r');
                terminal.putCharacter(')');
            }
            for (int i = 0; i < 10; i++) {
                if (i == moves.size()) break;
                String move = moves.get(moves.size() - 1 - i);
                terminal.setCursorPosition(79, i + 17);
                for (int j = 0; j < move.length(); j++) terminal.putCharacter(move.charAt(j));
            }
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    public void waitForEnter() {
        // TODO : remove ???
    }

    void printBoard(Board board, boolean player, List<String> moves,
                    RulesSet rulesSet, boolean isItAITurn) {
        // Menu.printRulesSet(rulesSet); TODO
        printMovesAndActivePlayer(moves, player, isItAITurn);
        try {
            terminal.setCursorPosition(37, 29);
            if (isItAITurn) {
                terminal.putCharacter('P');
                terminal.putCharacter('l');
                terminal.putCharacter('e');
                terminal.putCharacter('a');
                terminal.putCharacter('s');
                terminal.putCharacter('e');
                terminal.putCharacter(' ');
                terminal.putCharacter('w');
                terminal.putCharacter('a');
                terminal.putCharacter('i');
                terminal.putCharacter('t');
                terminal.putCharacter(' ');
                terminal.putCharacter('f');
                terminal.putCharacter('o');
                terminal.putCharacter('r');
                terminal.putCharacter(' ');
                terminal.putCharacter('m');
                terminal.putCharacter('o');
                terminal.putCharacter('v');
                terminal.putCharacter('e');
                terminal.putCharacter('.');
                for (int i = 0; i < 39; i++) terminal.putCharacter(' ');
            } else {
                terminal.putCharacter('E');
                terminal.putCharacter('n');
                terminal.putCharacter('t');
                terminal.putCharacter('e');
                terminal.putCharacter('r');
                terminal.putCharacter(' ');
                terminal.putCharacter('y');
                terminal.putCharacter('o');
                terminal.putCharacter('u');
                terminal.putCharacter('r');
                terminal.putCharacter(' ');
                terminal.putCharacter('n');
                terminal.putCharacter('e');
                terminal.putCharacter('x');
                terminal.putCharacter('t');
                terminal.putCharacter(' ');
                terminal.putCharacter('m');
                terminal.putCharacter('o');
                terminal.putCharacter('v');
                terminal.putCharacter('e');
                terminal.putCharacter('.');
                for (int i = 0; i < 39; i++) terminal.putCharacter(' ');
            }
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    public void printMakingMove(char x1, int y1, char x2, int y2, boolean isItAITurn) {
        if (isItAITurn)
            return;
        try {
            terminal.setCursorPosition(3, 31);
            terminal.putCharacter('M');
            terminal.putCharacter('a');
            terminal.putCharacter('k');
            terminal.putCharacter('i');
            terminal.putCharacter('n');
            terminal.putCharacter('g');
            terminal.putCharacter(' ');
            terminal.putCharacter('m');
            terminal.putCharacter('o');
            terminal.putCharacter('v');
            terminal.putCharacter('e');
            terminal.putCharacter(':');
            terminal.putCharacter(' ');
            terminal.putCharacter(x1);
            terminal.putCharacter((char) (y1 + 48));
            terminal.putCharacter('-');
            terminal.putCharacter(x2);
            terminal.putCharacter((char) (y2 + 48));
            for (int i = 0; i < 76; i++) terminal.putCharacter(' ');
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    public void printMoveDone(boolean isItAITurn) {
        if (isItAITurn)
            return;
        try {
            terminal.setCursorPosition(3, 31);
            terminal.putCharacter('M');
            terminal.putCharacter('o');
            terminal.putCharacter('v');
            terminal.putCharacter('e');
            terminal.putCharacter(' ');
            terminal.putCharacter('d');
            terminal.putCharacter('o');
            terminal.putCharacter('n');
            terminal.putCharacter('e');
            terminal.putCharacter('.');
            for (int i = 0; i < 84; i++) terminal.putCharacter(' ');
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    public void printCaptureDone(boolean isItAITurn) {
        if (isItAITurn)
            return;
        try {
            terminal.setCursorPosition(3, 31);
            terminal.putCharacter('C');
            terminal.putCharacter('a');
            terminal.putCharacter('p');
            terminal.putCharacter('t');
            terminal.putCharacter('u');
            terminal.putCharacter('r');
            terminal.putCharacter('e');
            terminal.putCharacter(' ');
            terminal.putCharacter('d');
            terminal.putCharacter('o');
            terminal.putCharacter('n');
            terminal.putCharacter('e');
            terminal.putCharacter('.');
            for (int i = 0; i < 81; i++) terminal.putCharacter(' ');
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    public void printIncorrectMove(String s, boolean isItAITurn) {
        if (isItAITurn)
            return;
        try {
            terminal.setCursorPosition(3, 31);
            terminal.putCharacter('I');
            terminal.putCharacter('n');
            terminal.putCharacter('c');
            terminal.putCharacter('o');
            terminal.putCharacter('r');
            terminal.putCharacter('r');
            terminal.putCharacter('e');
            terminal.putCharacter('c');
            terminal.putCharacter('t');
            terminal.putCharacter(' ');
            terminal.putCharacter('m');
            terminal.putCharacter('o');
            terminal.putCharacter('v');
            terminal.putCharacter('e');
            terminal.putCharacter(':');
            terminal.putCharacter(' ');
            for (int i = 0; i < s.length(); i++) terminal.putCharacter(s.charAt(i));
            for (int i = 0; i < 78 - s.length(); i++) terminal.putCharacter(' ');
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    public void printCapture(String captures, boolean isItAITurn) {
        if (isItAITurn)
            return;
        try {
            terminal.setCursorPosition(3, 31);
            terminal.putCharacter('Y');
            terminal.putCharacter('o');
            terminal.putCharacter('u');
            terminal.putCharacter(' ');
            terminal.putCharacter('h');
            terminal.putCharacter('a');
            terminal.putCharacter('v');
            terminal.putCharacter('e');
            terminal.putCharacter(' ');
            terminal.putCharacter('t');
            terminal.putCharacter('o');
            terminal.putCharacter(' ');
            terminal.putCharacter('c');
            terminal.putCharacter('a');
            terminal.putCharacter('p');
            terminal.putCharacter('t');
            terminal.putCharacter('u');
            terminal.putCharacter('r');
            terminal.putCharacter('e');
            terminal.putCharacter(':');
            terminal.putCharacter(' ');
            for (int i = 0; i < captures.length(); i++) terminal.putCharacter(captures.charAt(i));
            for (int i = 0; i < 73 - captures.length(); i++) terminal.putCharacter(' ');
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    public void printMultiCapture(String captures, boolean isItAITurn) {
        if (isItAITurn)
            return;
        try {
            terminal.setCursorPosition(3, 31);
            terminal.putCharacter('P');
            terminal.putCharacter('o');
            terminal.putCharacter('s');
            terminal.putCharacter('s');
            terminal.putCharacter('i');
            terminal.putCharacter('b');
            terminal.putCharacter('l');
            terminal.putCharacter('e');
            terminal.putCharacter(' ');
            terminal.putCharacter('c');
            terminal.putCharacter('a');
            terminal.putCharacter('p');
            terminal.putCharacter('t');
            terminal.putCharacter('u');
            terminal.putCharacter('r');
            terminal.putCharacter('e');
            terminal.putCharacter('s');
            terminal.putCharacter(':');
            terminal.putCharacter(' ');
            for (int i = 0; i < captures.length(); i++) terminal.putCharacter(captures.charAt(i));
            for (int i = 0; i < 75 - captures.length(); i++) terminal.putCharacter(' ');
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    public void printCaptureObligatory(boolean isItAITurn) {
        if (isItAITurn)
            return;
        try {
            terminal.setCursorPosition(3, 31);
            terminal.putCharacter('C');
            terminal.putCharacter('a');
            terminal.putCharacter('p');
            terminal.putCharacter('t');
            terminal.putCharacter('u');
            terminal.putCharacter('r');
            terminal.putCharacter('e');
            terminal.putCharacter(' ');
            terminal.putCharacter('i');
            terminal.putCharacter('s');
            terminal.putCharacter(' ');
            terminal.putCharacter('o');
            terminal.putCharacter('b');
            terminal.putCharacter('l');
            terminal.putCharacter('i');
            terminal.putCharacter('g');
            terminal.putCharacter('a');
            terminal.putCharacter('t');
            terminal.putCharacter('o');
            terminal.putCharacter('r');
            terminal.putCharacter('y');
            terminal.putCharacter('!');
            for (int i = 0; i < 72; i++) terminal.putCharacter(' ');
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    public void printIncorrectMoveFormat(boolean isItAITurn) {
        if (isItAITurn)
            return;
        try {
            terminal.setCursorPosition(3, 31);
            terminal.putCharacter('I');
            terminal.putCharacter('n');
            terminal.putCharacter('c');
            terminal.putCharacter('o');
            terminal.putCharacter('r');
            terminal.putCharacter('r');
            terminal.putCharacter('e');
            terminal.putCharacter('c');
            terminal.putCharacter('t');
            terminal.putCharacter(' ');
            terminal.putCharacter('m');
            terminal.putCharacter('o');
            terminal.putCharacter('v');
            terminal.putCharacter('e');
            terminal.putCharacter(' ');
            terminal.putCharacter('f');
            terminal.putCharacter('o');
            terminal.putCharacter('r');
            terminal.putCharacter('m');
            terminal.putCharacter('a');
            terminal.putCharacter('t');
            terminal.putCharacter('!');
            terminal.putCharacter(' ');
            terminal.putCharacter('P');
            terminal.putCharacter('r');
            terminal.putCharacter('o');
            terminal.putCharacter('p');
            terminal.putCharacter('e');
            terminal.putCharacter('r');
            terminal.putCharacter(' ');
            terminal.putCharacter('f');
            terminal.putCharacter('o');
            terminal.putCharacter('r');
            terminal.putCharacter('m');
            terminal.putCharacter('a');
            terminal.putCharacter('t');
            terminal.putCharacter(' ');
            terminal.putCharacter('e');
            terminal.putCharacter('x');
            terminal.putCharacter('a');
            terminal.putCharacter('m');
            terminal.putCharacter('p');
            terminal.putCharacter('l');
            terminal.putCharacter('e');
            terminal.putCharacter(' ');
            terminal.putCharacter('E');
            terminal.putCharacter('4');
            terminal.putCharacter('-');
            terminal.putCharacter('D');
            terminal.putCharacter('5');
            for (int i = 0; i < 43; i++) terminal.putCharacter(' ');
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    private void setCursor(int row, int col, int chosenRow, int chosenCol) {
        try {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                            terminal.setCursorPosition((i * 7) + 2, (j * 3) + 2);
                            terminal.putCharacter(' ');
                            terminal.setCursorPosition((i * 7) + 2, (j * 3) + 3);
                            terminal.putCharacter(' ');
                            terminal.setCursorPosition((i * 7) + 2, (j * 3) + 4);
                            terminal.putCharacter(' ');

                            terminal.setCursorPosition((i * 7) + 8, (j * 3) + 2);
                            terminal.putCharacter(' ');
                            terminal.setCursorPosition((i * 7) + 8, (j * 3) + 3);
                            terminal.putCharacter(' ');
                            terminal.setCursorPosition((i * 7) + 8, (j * 3) + 4);
                            terminal.putCharacter(' ');
                        } else {
                            terminal.setCursorPosition((i * 7) + 2, (j * 3) + 2);
                            terminal.putCharacter('█');
                            terminal.setCursorPosition((i * 7) + 2, (j * 3) + 3);
                            terminal.putCharacter('█');
                            terminal.setCursorPosition((i * 7) + 2, (j * 3) + 4);
                            terminal.putCharacter('█');

                            terminal.setCursorPosition((i * 7) + 8, (j * 3) + 2);
                            terminal.putCharacter('█');
                            terminal.setCursorPosition((i * 7) + 8, (j * 3) + 3);
                            terminal.putCharacter('█');
                            terminal.setCursorPosition((i * 7) + 8, (j * 3) + 4);
                            terminal.putCharacter('█');
                        }
                    }
                }
            if (row != 0 && col != 0) {
                col--;
                row--;
                if ((col % 2 == 0 && row % 2 == 1) || (col % 2 == 1 && row % 2 == 0)) {
                    terminal.setCursorPosition((col * 7) + 2, (row * 3) + 2);
                    terminal.putCharacter('▐');
                    terminal.setCursorPosition((col * 7) + 2, (row * 3) + 3);
                    terminal.putCharacter('▐');
                    terminal.setCursorPosition((col * 7) + 2, (row * 3) + 4);
                    terminal.putCharacter('▐');

                    terminal.setCursorPosition((col * 7) + 8, (row * 3) + 2);
                    terminal.putCharacter('▌');
                    terminal.setCursorPosition((col * 7) + 8, (row * 3) + 3);
                    terminal.putCharacter('▌');
                    terminal.setCursorPosition((col * 7) + 8, (row * 3) + 4);
                    terminal.putCharacter('▌');
                } else {
                    terminal.setCursorPosition((col * 7) + 2, (row * 3) + 2);
                    terminal.putCharacter('▌');
                    terminal.setCursorPosition((col * 7) + 2, (row * 3) + 3);
                    terminal.putCharacter('▌');
                    terminal.setCursorPosition((col * 7) + 2, (row * 3) + 4);
                    terminal.putCharacter('▌');

                    terminal.setCursorPosition((col * 7) + 8, (row * 3) + 2);
                    terminal.putCharacter('▐');
                    terminal.setCursorPosition((col * 7) + 8, (row * 3) + 3);
                    terminal.putCharacter('▐');
                    terminal.setCursorPosition((col * 7) + 8, (row * 3) + 4);
                    terminal.putCharacter('▐');
                }
                if (chosenRow != 0 && chosenCol != 0) {
                    chosenCol--;
                    chosenRow--;
                    if ((chosenCol % 2 == 0 && chosenRow % 2 == 1) || (chosenCol % 2 == 1 && chosenRow % 2 == 0)) {
                        terminal.setCursorPosition((chosenCol * 7) + 2, (chosenRow * 3) + 2);
                        terminal.putCharacter('▐');
                        terminal.setCursorPosition((chosenCol * 7) + 2, (chosenRow * 3) + 3);
                        terminal.putCharacter('▐');
                        terminal.setCursorPosition((chosenCol * 7) + 2, (chosenRow * 3) + 4);
                        terminal.putCharacter('▐');

                        terminal.setCursorPosition((chosenCol * 7) + 8, (chosenRow * 3) + 2);
                        terminal.putCharacter('▌');
                        terminal.setCursorPosition((chosenCol * 7) + 8, (chosenRow * 3) + 3);
                        terminal.putCharacter('▌');
                        terminal.setCursorPosition((chosenCol * 7) + 8, (chosenRow * 3) + 4);
                        terminal.putCharacter('▌');
                    } else {
                        terminal.setCursorPosition((chosenCol * 7) + 2, (chosenRow * 3) + 2);
                        terminal.putCharacter('▌');
                        terminal.setCursorPosition((chosenCol * 7) + 2, (chosenRow * 3) + 3);
                        terminal.putCharacter('▌');
                        terminal.setCursorPosition((chosenCol * 7) + 2, (chosenRow * 3) + 4);
                        terminal.putCharacter('▌');

                        terminal.setCursorPosition((chosenCol * 7) + 8, (chosenRow * 3) + 2);
                        terminal.putCharacter('▐');
                        terminal.setCursorPosition((chosenCol * 7) + 8, (chosenRow * 3) + 3);
                        terminal.putCharacter('▐');
                        terminal.setCursorPosition((chosenCol * 7) + 8, (chosenRow * 3) + 4);
                        terminal.putCharacter('▐');
                    }
                }
            }
            terminal.flush();
        } catch (IOException ignored) {
        }
    }

    public String[] getMoveOrOption(String captures, boolean isItAITurn, boolean player) {
        try {
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
                    key = terminal.readInput();
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
        } catch (IOException ignored) {
        }
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
        return null;
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
