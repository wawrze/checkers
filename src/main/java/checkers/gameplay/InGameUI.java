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
            STerminal.getInstance().putCharMultiplied('═', 6);
            STerminal.getInstance().putCharacter('╗');
            STerminal.getInstance().putCharAtPosition('║', 89, 4);
            STerminal.getInstance().putCharAtPosition('║', 96, 4);
            STerminal.getInstance().putCharAtPosition('╚', 89, 5);
            STerminal.getInstance().putCharMultiplied('═', 6);
            STerminal.getInstance().putCharacter('╝');
            STerminal.getInstance().setCursorPosition(89, 7);
            STerminal.getInstance().putCharMultiplied(' ', 8);
            STerminal.getInstance().putCharAtPosition(' ', 89, 8);
            STerminal.getInstance().putCharAtPosition(' ', 96, 8);
            STerminal.getInstance().setCursorPosition(89, 9);
            STerminal.getInstance().putCharMultiplied(' ', 8);
            STerminal.getInstance().putStringAtPosition("BLACK", 18, 29);
        } else {
            STerminal.getInstance().putCharAtPosition('╔', 89, 7);
            STerminal.getInstance().putCharMultiplied('═', 6);
            STerminal.getInstance().putCharacter('╗');
            STerminal.getInstance().putCharAtPosition('║', 89, 8);
            STerminal.getInstance().putCharAtPosition('║', 96, 8);
            STerminal.getInstance().putCharAtPosition('╚', 89, 9);
            STerminal.getInstance().putCharMultiplied('═', 6);
            STerminal.getInstance().putCharacter('╝');
            STerminal.getInstance().setCursorPosition(89, 3);
            STerminal.getInstance().putCharMultiplied(' ', 8);
            STerminal.getInstance().putCharAtPosition(' ', 89, 4);
            STerminal.getInstance().putCharAtPosition(' ', 96, 4);
            STerminal.getInstance().setCursorPosition(89, 5);
            STerminal.getInstance().putCharMultiplied(' ', 8);
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
                    RulesSet rulesSet, boolean isItAITurn) {
        // Menu.printRulesSet(rulesSet); TODO
        printMovesAndActivePlayer(moves, player, isItAITurn);
        if (isItAITurn) {
            STerminal.getInstance().replaceStringAtPosition("Please wait for move.", 60, 37, 29);
        } else {
            STerminal.getInstance().replaceStringAtPosition("Choose your next move.", 60, 37, 29);
        }
        STerminal.getInstance().update();
    }

    public void printMakingMove(char x1, int y1, char x2, int y2, boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Making move: " + x1 + y1 + "-" + x2 + y2, 94, 3, 31);
        STerminal.getInstance().update();
    }

    public void printMoveDone(boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Move done.", 94, 3, 31);
        STerminal.getInstance().update();
    }

    public void printCaptureDone(boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Capture done.", 94, 3, 31);
        STerminal.getInstance().update();
    }

    public void printIncorrectMove(String s, boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Incorrect move: " + s, 94, 3, 31);
        STerminal.getInstance().update();
    }

    public void printCapture(String captures, boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("You have to capture: " + captures, 94, 3, 31);
        STerminal.getInstance().update();
    }

    public void printMultiCapture(String captures, boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Possible captures: " + captures, 94, 3, 31);
        STerminal.getInstance().update();
    }

    public void printCaptureObligatory(boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Capture is obligatory!", 94, 3, 31);
        STerminal.getInstance().update();
    }

    public void printIncorrectMoveFormat(boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Incorrect move format! Proper format example: E4-D5", 94, 3, 31);
        STerminal.getInstance().update();
    }

    private void setCursor(int row, int col, int chosenRow, int chosenCol) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                    STerminal.getInstance().putCharAtPosition(' ', (i * 7) + 2, (j * 3) + 2);
                    STerminal.getInstance().putCharAtPosition(' ', (i * 7) + 2, (j * 3) + 3);
                    STerminal.getInstance().putCharAtPosition(' ', (i * 7) + 2, (j * 3) + 4);
                    STerminal.getInstance().putCharAtPosition(' ', (i * 7) + 8, (j * 3) + 2);
                    STerminal.getInstance().putCharAtPosition(' ', (i * 7) + 8, (j * 3) + 3);
                    STerminal.getInstance().putCharAtPosition(' ', (i * 7) + 8, (j * 3) + 4);
                } else {
                    STerminal.getInstance().putCharAtPosition('█', (i * 7) + 2, (j * 3) + 2);
                    STerminal.getInstance().putCharAtPosition('█', (i * 7) + 2, (j * 3) + 3);
                    STerminal.getInstance().putCharAtPosition('█', (i * 7) + 2, (j * 3) + 4);
                    STerminal.getInstance().putCharAtPosition('█', (i * 7) + 8, (j * 3) + 2);
                    STerminal.getInstance().putCharAtPosition('█', (i * 7) + 8, (j * 3) + 3);
                    STerminal.getInstance().putCharAtPosition('█', (i * 7) + 8, (j * 3) + 4);
                }
            }
        }
        if (row != 0 && col != 0) {
            col--;
            row--;
            if ((col % 2 == 0 && row % 2 == 1) || (col % 2 == 1 && row % 2 == 0)) {
                STerminal.getInstance().putCharAtPosition('▐', (col * 7) + 2, (row * 3) + 2);
                STerminal.getInstance().putCharAtPosition('▐', (col * 7) + 2, (row * 3) + 3);
                STerminal.getInstance().putCharAtPosition('▐', (col * 7) + 2, (row * 3) + 4);
                STerminal.getInstance().putCharAtPosition('▌', (col * 7) + 8, (row * 3) + 2);
                STerminal.getInstance().putCharAtPosition('▌', (col * 7) + 8, (row * 3) + 3);
                STerminal.getInstance().putCharAtPosition('▌', (col * 7) + 8, (row * 3) + 4);
            } else {
                STerminal.getInstance().putCharAtPosition('▌', (col * 7) + 2, (row * 3) + 2);
                STerminal.getInstance().putCharAtPosition('▌', (col * 7) + 2, (row * 3) + 3);
                STerminal.getInstance().putCharAtPosition('▌', (col * 7) + 2, (row * 3) + 4);
                STerminal.getInstance().putCharAtPosition('▐', (col * 7) + 8, (row * 3) + 2);
                STerminal.getInstance().putCharAtPosition('▐', (col * 7) + 8, (row * 3) + 3);
                STerminal.getInstance().putCharAtPosition('▐', (col * 7) + 8, (row * 3) + 4);
            }
            if (chosenRow != 0 && chosenCol != 0) {
                chosenCol--;
                chosenRow--;
                if ((chosenCol % 2 == 0 && chosenRow % 2 == 1) || (chosenCol % 2 == 1 && chosenRow % 2 == 0)) {
                    STerminal.getInstance().putCharAtPosition('▐', (chosenCol * 7) + 2, (chosenRow * 3) + 2);
                    STerminal.getInstance().putCharAtPosition('▐', (chosenCol * 7) + 2, (chosenRow * 3) + 3);
                    STerminal.getInstance().putCharAtPosition('▐', (chosenCol * 7) + 2, (chosenRow * 3) + 4);
                    STerminal.getInstance().putCharAtPosition('▌', (chosenCol * 7) + 8, (chosenRow * 3) + 2);
                    STerminal.getInstance().putCharAtPosition('▌', (chosenCol * 7) + 8, (chosenRow * 3) + 3);
                    STerminal.getInstance().putCharAtPosition('▌', (chosenCol * 7) + 8, (chosenRow * 3) + 4);
                } else {
                    STerminal.getInstance().putCharAtPosition('▌', (chosenCol * 7) + 2, (chosenRow * 3) + 2);
                    STerminal.getInstance().putCharAtPosition('▌', (chosenCol * 7) + 2, (chosenRow * 3) + 3);
                    STerminal.getInstance().putCharAtPosition('▌', (chosenCol * 7) + 2, (chosenRow * 3) + 4);
                    STerminal.getInstance().putCharAtPosition('▐', (chosenCol * 7) + 8, (chosenRow * 3) + 2);
                    STerminal.getInstance().putCharAtPosition('▐', (chosenCol * 7) + 8, (chosenRow * 3) + 3);
                    STerminal.getInstance().putCharAtPosition('▐', (chosenCol * 7) + 8, (chosenRow * 3) + 4);
                }
            }
        }
        STerminal.getInstance().update();
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
            } else if (key.getKeyType() == KeyType.Escape) {
                result[0] = null;
                result[1] = null;
            } else if (key.getKeyType() == KeyType.Enter) {
                if (result[0] == null && result[1] == null) {
                    result[0] = Character.toString((char) (cursorRow + 64));
                    result[1] = Integer.toString(cursorCol);
                } else {
                    result[2] = Character.toString((char) (cursorRow + 64));
                    result[3] = Integer.toString(cursorCol);
                    String moveToValidate = result[0] +
                            result[1] +
                            "-" +
                            result[2] +
                            result[3];
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
        STerminal.getInstance().update();
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
