package checkers.moves;

import checkers.board.Board;
import checkers.figures.None;
import checkers.figures.Pawn;
import checkers.figures.Queen;
import checkers.gameplay.RulesSet;
import exceptions.CaptureException;
import exceptions.CapturePossibleException;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CapturePossibilityValidator implements Serializable {

    private final List<String> listOfCaptures;
    private final Board board;
    private final boolean player;
    private final RulesSet rulesSet;
    private int[] counter;

    public CapturePossibilityValidator(Board board, boolean player, RulesSet rulesSet) {
        this.listOfCaptures = new ArrayList<>();
        this.board = board;
        this.player = player;
        this.rulesSet = rulesSet;
    }

    private void findMaxCaptures() throws IncorrectMoveFormat, IncorrectMoveException {
        counter = new int[listOfCaptures.size()];
        for (String s : listOfCaptures) {
            Board tmpBoard = new Board(this.board);
            String[] sArray = s.split("-");
            char x1 = sArray[0].charAt(0);
            int y1 = Character.getNumericValue(sArray[0].charAt(1));
            char x2 = sArray[1].charAt(0);
            int y2 = Character.getNumericValue(sArray[1].charAt(1));
            Move move = new Move(x1, y1, x2, y2);
            try {
                MoveValidator.validateMove(move, tmpBoard, player, rulesSet);
            } catch (CaptureException e) {
                move.makeCapture(tmpBoard, e.getRow(), e.getCol());
            }
            CapturePossibilityValidator validator = new CapturePossibilityValidator(tmpBoard, this.player, rulesSet);
            try {
                validator.validateCapturePossibilityForOneFigure(x2, y2);
                counter[listOfCaptures.indexOf(s)] = 1;
            } catch (CapturePossibleException e) {
                counter[listOfCaptures.indexOf(s)] += (validator.getMaxDepth() + 1);
            }
        }
        for (int i = (listOfCaptures.size() - 1); i >= 0; i--) {
            if (counter[i] < getMaxDepth()) {
                listOfCaptures.remove(i);
            }
        }
    }

    private int getMaxDepth() {
        int maxDepth = 0;
        for (int i : counter)
            if (i > maxDepth)
                maxDepth = i;
        return maxDepth;
    }

    public void validateCapturePossibility()
            throws CapturePossibleException, IncorrectMoveException, IncorrectMoveFormat {
        for (int i = 65; i < 73; i++) {
            for (int j = 1; j < 9; j++) {
                if (board.getFigure((char) i, j) instanceof Pawn
                        && board.getFigure((char) i, j).getColor() == player) {
                    validatePawnCapture((char) i, j, board);
                } else if (board.getFigure((char) i, j) instanceof Queen
                        && board.getFigure((char) i, j).getColor() == player) {
                    validateQueenCapture((char) i, j, board);
                }
            }
        }
        if (!rulesSet.isCaptureAny()) {
            findMaxCaptures();
        }
        listCheck();
    }

    public void validateCapturePossibilityForOneFigure(char row, int col)
            throws CapturePossibleException, IncorrectMoveException, IncorrectMoveFormat {
        if (board.getFigure(row, col) instanceof Pawn)
            validatePawnCapture(row, col, board);
        else
            validateQueenCapture(row, col, board);
        if (!rulesSet.isCaptureAny()) {
            findMaxCaptures();
        }
        listCheck();
    }

    private void listCheck() throws CapturePossibleException {
        if (!listOfCaptures.isEmpty()) {
            StringBuilder m = new StringBuilder();
            for (String s : listOfCaptures)
                m.append(s).append(" ");
            throw new CapturePossibleException(m.toString());
        }
    }

    private void validateQueenCapture(char row1, int col1, Board board) {
        char row2;
        int col2;
        Move move;
        for (int i = 2; i < 9; i++) {
            //left-up
            row2 = (char) (((int) row1) - i);
            col2 = col1 - i;
            try {
                move = new Move(row1, col1, row2, col2);
            } catch (IncorrectMoveFormat e) {
                break;
            }
            try {
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(), rulesSet);
            } catch (IncorrectMoveException ignored) {
            } catch (CaptureException e) {
                listOfCaptures.add("" + row1 + col1 + "-" + row2 + col2);
            }
        }
        for (int i = 2; i < 9; i++) {
            //right-up
            row2 = (char) (((int) row1) - i);
            col2 = col1 + i;
            try {
                move = new Move(row1, col1, row2, col2);
            } catch (IncorrectMoveFormat e) {
                break;
            }
            try {
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(), rulesSet);
            } catch (IncorrectMoveException ignored) {
            } catch (CaptureException e) {
                listOfCaptures.add("" + row1 + col1 + "-" + row2 + col2);
            }
        }
        for (int i = 2; i < 9; i++) {
            //right-down
            row2 = (char) (((int) row1) + i);
            col2 = col1 + i;
            try {
                move = new Move(row1, col1, row2, col2);
            } catch (IncorrectMoveFormat e) {
                break;
            }
            try {
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(), rulesSet);
            } catch (IncorrectMoveException ignored) {
            } catch (CaptureException e) {
                listOfCaptures.add("" + row1 + col1 + "-" + row2 + col2);
            }
        }
        for (int i = 2; i < 9; i++) {
            //left-down
            row2 = (char) (((int) row1) + i);
            col2 = col1 - i;
            try {
                move = new Move(row1, col1, row2, col2);
            } catch (IncorrectMoveFormat e) {
                break;
            }
            try {
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(), rulesSet);
            } catch (IncorrectMoveException ignored) {
            } catch (CaptureException e) {
                listOfCaptures.add("" + row1 + col1 + "-" + row2 + col2);
            }
        }
    }

    private void validatePawnCapture(char row, int col, Board board) {
        char rowCaptureTo;
        int colCaptureTo;
        char rowCaptured;
        int colCaptured;
        //left-up
        rowCaptureTo = (char) (((int) row) - 2);
        colCaptureTo = col - 2;
        rowCaptured = (char) (((int) row) - 1);
        colCaptured = col - 1;
        if (isOnBoard((int) rowCaptureTo, colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if (validate(row, col, rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, board))
                listOfCaptures.add("" + row + col + "-" + rowCaptureTo + colCaptureTo);
        //right-up
        rowCaptureTo = (char) (((int) row) - 2);
        colCaptureTo = col + 2;
        rowCaptured = (char) (((int) row) - 1);
        colCaptured = col + 1;
        if (isOnBoard((int) rowCaptureTo, colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if (validate(row, col, rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, board))
                listOfCaptures.add("" + row + col + "-" + rowCaptureTo + colCaptureTo);
        //left-down
        rowCaptureTo = (char) (((int) row) + 2);
        colCaptureTo = col - 2;
        rowCaptured = (char) (((int) row) + 1);
        colCaptured = col - 1;
        if (isOnBoard((int) rowCaptureTo, colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if (validate(row, col, rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, board))
                listOfCaptures.add("" + row + col + "-" + rowCaptureTo + colCaptureTo);
        //right-down
        rowCaptureTo = (char) (((int) row) + 2);
        colCaptureTo = col + 2;
        rowCaptured = (char) (((int) row) + 1);
        colCaptured = col + 1;
        if (isOnBoard((int) rowCaptureTo, colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if (validate(row, col, rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, board))
                listOfCaptures.add("" + row + col + "-" + rowCaptureTo + colCaptureTo);
    }

    private boolean isOnBoard(int row, int col) {
        return row > 64 && row < 73 && col > 0 && col < 9;
    }

    private boolean validate(char row1, int col1, char row2, int col2, char row3, int col3, Board board) {
        return !(board.getFigure(row3, col3) instanceof None)
                && board.getFigure(row1, col1).getColor() != board.getFigure(row3, col3).getColor()
                && board.getFigure(row2, col2) instanceof None;
    }

}
