package checkers.gameplay;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import exceptions.IncorrectMoveFormat;

import java.io.Serializable;
import java.util.List;

public class InGameUI implements Serializable {

    private final String[] options;

    public InGameUI() {
        options = new String[]{"h", "s", "x"};
    }

    void switchToMovesHistory(List<String> moves) {
        if (moves.size() == 0) return;
        int movesToPrintTo = moves.size() - 1;
        int actualPosition = 0;
        do {
            for (int i = 0; i < 10; i++) {
                STerminal.getInstance().putStringAtPosition("       ", 93, i + 17);
            }
            STerminal.getInstance().putCharAtPosition('●', 93, actualPosition + 17);
            for (int i = 0; i < 10; i++) {
                int moveToPrintIndex = movesToPrintTo - i;
                if (moveToPrintIndex == moves.size() || moveToPrintIndex < 0) break;
                String move = moves.get(moveToPrintIndex);
                STerminal.getInstance().putStringAtPosition(move, 95, i + 17);
            }
            STerminal.getInstance().update();
            KeyStroke key;
            do {
                key = STerminal.getInstance().readInput();
            } while (key == null);
            if (key.getKeyType() == KeyType.ArrowDown) {
                if (actualPosition == 9 && movesToPrintTo > 9) {
                    movesToPrintTo--;
                } else if (actualPosition < 9) {
                    actualPosition++;
                }
            } else if (key.getKeyType() == KeyType.ArrowUp) {
                if (actualPosition == 0 && movesToPrintTo < moves.size() - 1) {
                    movesToPrintTo++;
                } else if (actualPosition > 0) {
                    actualPosition--;
                }
            } else if (key.getKeyType() == KeyType.Escape) {
                break;
            } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'h') {
                break;
            }
        } while (true);

        for (int i = 0; i < 10; i++) {
            STerminal.getInstance().putStringAtPosition("       ", 93, i + 17);
        }
        for (int i = 0; i < 10; i++) {
            if (i == moves.size()) break;
            String move = moves.get(moves.size() - 1 - i);
            STerminal.getInstance().putStringAtPosition(move, 95, i + 17);
        }
        STerminal.getInstance().update();
    }

    private void printMovesAndActivePlayer(List<String> moves, boolean player, boolean isItAITurn) {
        if (player) {
            STerminal.getInstance().putCharAtPosition('╔', 105, 3);
            STerminal.getInstance().putCharMultiplied('═', 6);
            STerminal.getInstance().putCharacter('╗');
            STerminal.getInstance().putCharAtPosition('║', 105, 4);
            STerminal.getInstance().putCharAtPosition('║', 112, 4);
            STerminal.getInstance().putCharAtPosition('╚', 105, 5);
            STerminal.getInstance().putCharMultiplied('═', 6);
            STerminal.getInstance().putCharacter('╝');
            STerminal.getInstance().setCursorPosition(105, 7);
            STerminal.getInstance().putCharMultiplied(' ', 8);
            STerminal.getInstance().putCharAtPosition(' ', 105, 8);
            STerminal.getInstance().putCharAtPosition(' ', 112, 8);
            STerminal.getInstance().setCursorPosition(105, 9);
            STerminal.getInstance().putCharMultiplied(' ', 8);
            STerminal.getInstance().replaceStringAtPosition("BLACK", 17, 18, 29);
        } else {
            STerminal.getInstance().putCharAtPosition('╔', 105, 7);
            STerminal.getInstance().putCharMultiplied('═', 6);
            STerminal.getInstance().putCharacter('╗');
            STerminal.getInstance().putCharAtPosition('║', 105, 8);
            STerminal.getInstance().putCharAtPosition('║', 112, 8);
            STerminal.getInstance().putCharAtPosition('╚', 105, 9);
            STerminal.getInstance().putCharMultiplied('═', 6);
            STerminal.getInstance().putCharacter('╝');
            STerminal.getInstance().setCursorPosition(105, 3);
            STerminal.getInstance().putCharMultiplied(' ', 8);
            STerminal.getInstance().putCharAtPosition(' ', 105, 4);
            STerminal.getInstance().putCharAtPosition(' ', 112, 4);
            STerminal.getInstance().setCursorPosition(105, 5);
            STerminal.getInstance().putCharMultiplied(' ', 8);
            STerminal.getInstance().replaceStringAtPosition("WHITE", 17, 18, 29);
        }
        if (isItAITurn) STerminal.getInstance().putStringAtPosition("(computer)", 24, 29);

        for (int i = 0; i < 10; i++) {
            if (i == moves.size()) break;
            String move = moves.get(moves.size() - 1 - i);
            STerminal.getInstance().putStringAtPosition(move, 95, i + 17);
        }
    }

    void waitForEnter() {
        STerminal.getInstance().replaceStringAtPosition("Press any key.", 60, 37, 29);
        STerminal.getInstance().update();
        KeyStroke key;
        do {
            key = STerminal.getInstance().readInput();
        } while (key == null);
        STerminal.getInstance().replaceStringAtPosition("", 60, 37, 29);
        STerminal.getInstance().replaceStringAtPosition("", 94, 3, 31);
        STerminal.getInstance().update();
    }

    void printBoard(boolean player, List<String> moves, boolean isItAITurn) {
        printMovesAndActivePlayer(moves, player, isItAITurn);
        if (isItAITurn) {
            STerminal.getInstance().replaceStringAtPosition("Please wait for move.", 60, 37, 29);
        } else {
            STerminal.getInstance().replaceStringAtPosition("Choose your next move.", 60, 37, 29);
        }
        STerminal.getInstance().update();
    }

    void printMakingMove(char x1, int y1, char x2, int y2, boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Making move: " + x1 + y1 + "-" + x2 + y2, 94, 3, 31);
        STerminal.getInstance().update();
    }

    void printMoveDone(boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Move done.", 94, 3, 31);
        STerminal.getInstance().update();
    }

    void printCaptureDone(boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Capture done.", 94, 3, 31);
        STerminal.getInstance().update();
    }

    void printIncorrectMove(String s, boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Incorrect move: " + s, 94, 3, 31);
        STerminal.getInstance().update();
    }

    void printCapture(String captures, boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("You have to capture: " + captures, 94, 3, 31);
        STerminal.getInstance().update();
    }

    void printMultiCapture(String captures, boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Possible captures: " + captures, 94, 3, 31);
        STerminal.getInstance().update();
    }

    private void printCaptureObligatory(boolean isItAITurn) {
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Capture is obligatory!", 94, 3, 31);
        STerminal.getInstance().update();
    }

    void printIncorrectMoveFormat(boolean isItAITurn) {
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

    String[] getMoveOrOption(String captures, boolean isItAITurn, boolean player) {
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

    boolean endOfGame(List<String> moves) {
        STerminal.getInstance().clear();
        STerminal.getInstance().putStringAtPosition("╔═════════════════════", 6, 4);
        STerminal.getInstance().putStringAtPosition("║███████       ▓▓▓▓▓░░", 6, 5);
        STerminal.getInstance().putStringAtPosition("║███████       ▓▓░░░░░", 6, 6);
        STerminal.getInstance().putStringAtPosition("║███████       ░░░░░░░", 6, 7);
        STerminal.getInstance().putStringAtPosition("║       ▓▓▓▓▓░░", 6, 8);
        STerminal.getInstance().putStringAtPosition("║       ▓▓░░░   ██  █ █ ███  ██  █  █ ███ ███    ███", 6, 9);
        STerminal.getInstance().putStringAtPosition("║       ░░     █  █ █ █ █   █  █ █ █  █   █  █  █   █", 6, 10);
        STerminal.getInstance().putStringAtPosition("║▓▓▓▓▓░░       █    ███ ██  █    ██   ██  ███    ██", 6, 11);
        STerminal.getInstance().putStringAtPosition("║▓▓░░░░░       █  █ █ █ █   █  █ █ █  █   █ █  █   █", 6, 12);
        STerminal.getInstance().putStringAtPosition("║░░░░░░░        ██  █ █ ███  ██  █  █ ███ █  █  ███", 6, 13);

        if (VictoryValidator.isDraw()) {
            STerminal.getInstance().putStringAtPosition("╔═══════════════════════════════════════════════════════════════════════════════════════════════╗", 6, 15);
            STerminal.getInstance().putStringAtPosition("║ ███    █   █     █ ████                                                                       ║", 6, 16);
            STerminal.getInstance().putStringAtPosition("║█   █  █ █  ██   ██ █                                                                          ║", 6, 17);
            STerminal.getInstance().putStringAtPosition("║█     █   █ █ █ █ █ █                                                                          ║", 6, 18);
            STerminal.getInstance().putStringAtPosition("║█     █   █ █  █  █ ███                                        ███   ████    █   █     █       ║", 6, 19);
            STerminal.getInstance().putStringAtPosition("║█  ██ █   █ █     █ █                                          █  █  █   █  █ █  █     █       ║", 6, 20);
            STerminal.getInstance().putStringAtPosition("║█   █ █████ █     █ █       ███  █     █ ████ ████   ██        █   █ █   █ █   █ █     █       ║", 6, 21);
            STerminal.getInstance().putStringAtPosition("║ ███  █   █ █     █ ████   █   █  █   █  █    █   █  ██        █   █ ████  █   █ █  █  █       ║", 6, 22);
            STerminal.getInstance().putStringAtPosition("║                           █   █  █   █  █    █   █  ██        █  █  █  █  █████ █ █ █ █       ║", 6, 23);
            STerminal.getInstance().putStringAtPosition("║                           █   █   █ █   ███  ████   ██        ███   █   █ █   █ ██   ██       ║", 6, 24);
            STerminal.getInstance().putStringAtPosition("║                           █   █   █ █   █    █  █                                             ║", 6, 25);
            STerminal.getInstance().putStringAtPosition("║                            ███     █    ████ █   █  ██                                        ║", 6, 26);
            STerminal.getInstance().putStringAtPosition("╚═══════════════════════════════════════════════════════════════════════════════════════════════╝", 6, 27);
        } else if (VictoryValidator.getWinner()) {
            STerminal.getInstance().putStringAtPosition("╔═══════════════════════════════════════════════════════════════════════════════════════════════╗", 6, 15);
            STerminal.getInstance().putStringAtPosition("║ ███    █   █     █ ████                                       ██  █    ██   ██ █  █           ║", 6, 16);
            STerminal.getInstance().putStringAtPosition("║█   █  █ █  ██   ██ █                                          █ █ █   █  █ █   █ █            ║", 6, 17);
            STerminal.getInstance().putStringAtPosition("║█     █   █ █ █ █ █ █                                          ██  █   █  █ █   ██             ║", 6, 18);
            STerminal.getInstance().putStringAtPosition("║█     █   █ █  █  █ ███                                        █ █ █   ████ █   █ █            ║", 6, 19);
            STerminal.getInstance().putStringAtPosition("║█  ██ █   █ █     █ █                                          ██   ██ █  █  ██ █  █           ║", 6, 20);
            STerminal.getInstance().putStringAtPosition("║█   █ █████ █     █ █           ███  █     █ ████ ████   ██                                    ║", 6, 21);
            STerminal.getInstance().putStringAtPosition("║ ███  █   █ █     █ ████       █   █  █   █  █    █   █  ██            █   █ █ █   █  ███      ║", 6, 22);
            STerminal.getInstance().putStringAtPosition("║                               █   █  █   █  █    █   █  ██            █   █ █ ██  █ █         ║", 6, 23);
            STerminal.getInstance().putStringAtPosition("║                               █   █   █ █   ███  ████   ██            █ █ █ █ █ █ █  ██       ║", 6, 24);
            STerminal.getInstance().putStringAtPosition("║                               █   █   █ █   █    █  █                 ██ ██ █ █  ██    █      ║", 6, 25);
            STerminal.getInstance().putStringAtPosition("║                                ███     █    ████ █   █  ██            █   █ █ █   █ ███       ║", 6, 26);
            STerminal.getInstance().putStringAtPosition("╚═══════════════════════════════════════════════════════════════════════════════════════════════╝", 6, 27);
        } else {
            STerminal.getInstance().putStringAtPosition("╔═══════════════════════════════════════════════════════════════════════════════════════════════╗", 6, 15);
            STerminal.getInstance().putStringAtPosition("║ ███    █   █     █ ████                                       █   █ █ █ █ ███ ███             ║", 6, 16);
            STerminal.getInstance().putStringAtPosition("║█   █  █ █  ██   ██ █                                          █   █ █ █ █  █  █               ║", 6, 17);
            STerminal.getInstance().putStringAtPosition("║█     █   █ █ █ █ █ █                                          █   █ ███ █  █  ██              ║", 6, 18);
            STerminal.getInstance().putStringAtPosition("║█     █   █ █  █  █ ███                                        █ █ █ █ █ █  █  █               ║", 6, 19);
            STerminal.getInstance().putStringAtPosition("║█  ██ █   █ █     █ █                                          ██ ██ █ █ █  █  ███             ║", 6, 20);
            STerminal.getInstance().putStringAtPosition("║█   █ █████ █     █ █           ███  █     █ ████ ████   ██                                    ║", 6, 21);
            STerminal.getInstance().putStringAtPosition("║ ███  █   █ █     █ ████       █   █  █   █  █    █   █  ██            █   █ █ █   █  ███      ║", 6, 22);
            STerminal.getInstance().putStringAtPosition("║                               █   █  █   █  █    █   █  ██            █   █ █ ██  █ █         ║", 6, 23);
            STerminal.getInstance().putStringAtPosition("║                               █   █   █ █   ███  ████   ██            █ █ █ █ █ █ █  ██       ║", 6, 24);
            STerminal.getInstance().putStringAtPosition("║                               █   █   █ █   █    █  █                 ██ ██ █ █  ██    █      ║", 6, 25);
            STerminal.getInstance().putStringAtPosition("║                                ███     █    ████ █   █  ██            █   █ █ █   █ ███       ║", 6, 26);
            STerminal.getInstance().putStringAtPosition("╚═══════════════════════════════════════════════════════════════════════════════════════════════╝", 6, 27);
        }

        STerminal.getInstance().putCharAtPosition('╔', 88, 1);
        STerminal.getInstance().putCharAtPosition('╗', 113, 1);
        STerminal.getInstance().putCharAtPosition('╚', 88, 13);
        STerminal.getInstance().putCharAtPosition('╝', 113, 13);
        for (int i = 2; i < 13; i++) {
            STerminal.getInstance().putCharAtPosition('║', 88, i);
            STerminal.getInstance().putCharAtPosition('║', 113, i);
        }
        STerminal.getInstance().setCursorPosition(89, 1);
        STerminal.getInstance().putCharMultiplied('═', 24);
        STerminal.getInstance().setCursorPosition(89, 13);
        STerminal.getInstance().putCharMultiplied('═', 24);
        STerminal.getInstance().putStringAtPosition("MOVES HISTORY", 94, 2);

        STerminal.getInstance().putStringAtPosition("╔═════════════════════════════════════════════════╗", 6, 31);
        STerminal.getInstance().putStringAtPosition("║ x - exit without saving, s - save game and exit ║", 6, 32);
        STerminal.getInstance().putStringAtPosition("╚═════════════════════════════════════════════════╝", 6, 33);

        if (moves.size() == 0) return false;
        int movesToPrintTo = moves.size() - 1;
        int actualPosition = 0;
        do {
            for (int i = 0; i < 10; i++) {
                STerminal.getInstance().putStringAtPosition("       ", 93, i + 3);
            }
            STerminal.getInstance().putCharAtPosition('●', 93, actualPosition + 3);
            for (int i = 0; i < 10; i++) {
                int moveToPrintIndex = movesToPrintTo - i;
                if (moveToPrintIndex == moves.size() || moveToPrintIndex < 0) break;
                String move = moves.get(moveToPrintIndex);
                STerminal.getInstance().putStringAtPosition(move, 95, i + 3);
            }
            STerminal.getInstance().update();
            KeyStroke key;
            do {
                key = STerminal.getInstance().readInput();
            } while (key == null);
            if (key.getKeyType() == KeyType.ArrowDown) {
                if (actualPosition == 9 && movesToPrintTo > 9) {
                    movesToPrintTo--;
                } else if (actualPosition < 9) {
                    actualPosition++;
                }
            } else if (key.getKeyType() == KeyType.ArrowUp) {
                if (actualPosition == 0 && movesToPrintTo < moves.size() - 1) {
                    movesToPrintTo++;
                } else if (actualPosition > 0) {
                    actualPosition--;
                }
            } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 's') {
                return true;
            } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'x') {
                return false;
            }
        } while (true);
    }

}
