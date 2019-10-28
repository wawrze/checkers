package checkers.ui;

import checkers.gameplay.VictoryValidator;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import exceptions.IncorrectMoveFormat;

import java.io.Serializable;
import java.util.List;

public class InGameUI implements Serializable {

    public void switchToMovesHistory(List<String> moves) {//TODO
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
            KeyStroke key = new KeyStroke(KeyType.Escape);

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

    private void waitForKey() {
//        STerminal.getInstance().replaceStringAtPosition("Press any key.", 60, 37, 29);
//        STerminal.getInstance().update();
//        KeyStroke key;
//        do {
//            key = STerminal.getInstance().readInput();
//        } while (key == null);
//        STerminal.getInstance().replaceStringAtPosition("", 60, 37, 29);
//        STerminal.getInstance().replaceStringAtPosition("", 94, 3, 31);
//        STerminal.getInstance().update();
    }

    public void printBoard(boolean player, List<String> moves, boolean isItAITurn) {//TODO
        printMovesAndActivePlayer(moves, player, isItAITurn);
        if (isItAITurn) {
            STerminal.getInstance().replaceStringAtPosition("Please wait for move.", 60, 37, 29);
        } else {
            STerminal.getInstance().replaceStringAtPosition("Choose your next move.", 94, 3, 31);
        }
        STerminal.getInstance().update();
    }

    public void printMakingMove(char x1, int y1, char x2, int y2, boolean isItAITurn, boolean wasCapture) {//TODO
        if (!isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Computer made " + (wasCapture ? "capture" : "move") + ": " + x1 + y1 + "-" + x2 + y2, 94, 3, 31);
//        setCursor(x2 - 64, y2, x1 - 64, y1);
        STerminal.getInstance().update();
        waitForKey();
    }

    public void printMoveDone(boolean isItAITurn) {//TODO
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Move done.", 94, 3, 31);
        STerminal.getInstance().update();
    }

    public void printCaptureDone(boolean isItAITurn) {//TODO
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Capture done.", 94, 3, 31);
        STerminal.getInstance().update();
    }

    public void printIncorrectMove(String s, boolean isItAITurn) {//TODO
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Incorrect move: " + s, 94, 3, 31);
        STerminal.getInstance().update();
        waitForKey();
    }

    public void printCapture(String captures, boolean isItAITurn) {//TODO
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("You have to capture: " + captures, 94, 3, 31);
        STerminal.getInstance().update();
    }

    public void printMultiCapture(String captures, boolean isItAITurn) {//TODO
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Possible captures: " + captures, 94, 3, 31);
        STerminal.getInstance().update();
    }

    public void printCaptureObligatory(boolean isItAITurn) {//TODO
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Move not done - capture is obligatory!", 94, 3, 31);
        STerminal.getInstance().update();
        waitForKey();
    }

    public void printIncorrectMoveFormat(boolean isItAITurn) {//TODO
        if (isItAITurn)
            return;
        STerminal.getInstance().replaceStringAtPosition("Incorrect move format! Proper format example: E4-D5", 94, 3, 31);
        STerminal.getInstance().update();
    }

    public void validate(String s) throws IncorrectMoveFormat {//TODO
        String[] sArray = s.split("-");
        if (sArray.length != 2)
            throw new IncorrectMoveFormat();
        for (String t : sArray)
            if (t.length() != 2)
                throw new IncorrectMoveFormat();
    }

    public void endOfGame(List<String> moves) {//TODO
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
            KeyStroke key = new KeyStroke(KeyType.Escape);
//            do {
//                key = STerminal.getInstance().readInput();
//            } while (key == null);
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
                // TODO: menu.games.put, menu.start
            } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'x') {
                // TODO: menu.start
            }
        } while (true);
    }

}
