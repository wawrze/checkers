package checkers.gameplay;

import checkers.board.Board;
import checkers.figures.Pawn;
import checkers.figures.Queen;
import checkers.moves.CapturePossibilityValidator;
import checkers.moves.Move;
import checkers.moves.MoveValidator;
import checkers.ui.InGameUI;
import checkers.ui.STerminal;
import exceptions.CaptureException;
import exceptions.CapturePossibleException;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Game implements Serializable {

    private final Board board;
    private final List<String> moves;
    private final RulesSet rulesSet;
    private final boolean isBlackAIPlayer;
    private final boolean isWhiteAIPlayer;
    private final String name;
    private boolean activePlayer;
    private int whiteQueenMoves;
    private int blackQueenMoves;
    private boolean isFinished;
    private boolean isDraw;
    private boolean winner;
    private boolean save;
    private LocalDate date;
    private LocalTime time;
    private InGameUI inGameUI;

    public Game(Game game) {
        this.board = new Board(game.getBoard());
        this.moves = new LinkedList<>(game.getMoves());
        this.activePlayer = game.isActivePlayer();
        this.whiteQueenMoves = game.getWhiteQueenMoves();
        this.blackQueenMoves = game.getBlackQueenMoves();
        this.isFinished = game.isFinished();
        this.isDraw = game.isDraw();
        this.winner = game.isWinner();
        this.name = game.getName();
        this.save = game.isSave();
        this.date = game.getDate();
        this.time = game.getTime();
        this.rulesSet = game.getRulesSet();
        this.isBlackAIPlayer = game.isBlackAIPlayer();
        this.isWhiteAIPlayer = game.isWhiteAIPlayer();
        this.inGameUI = game.getInGameUI();
    }

    public Game(String name, RulesSet rulesSet, boolean isBlackAIPlayer, boolean isWhiteAIPlayer) {
        board = new Board();
        moves = new LinkedList<>();
        activePlayer = false;
        whiteQueenMoves = 0;
        blackQueenMoves = 0;
        isFinished = false;
        isDraw = false;
        this.isBlackAIPlayer = isBlackAIPlayer;
        this.isWhiteAIPlayer = isWhiteAIPlayer;
        this.name = name;
        save = false;
        this.rulesSet = rulesSet;
        initFigures();
    }

    private void initFigures() {
        board.setAndPrintFigure('A', 2, new Pawn(true));
        board.setAndPrintFigure('A', 4, new Pawn(true));
        board.setAndPrintFigure('A', 6, new Pawn(true));
        board.setAndPrintFigure('A', 8, new Pawn(true));
        board.setAndPrintFigure('B', 1, new Pawn(true));
        board.setAndPrintFigure('B', 3, new Pawn(true));
        board.setAndPrintFigure('B', 5, new Pawn(true));
        board.setAndPrintFigure('B', 7, new Pawn(true));
        board.setAndPrintFigure('C', 2, new Pawn(true));
        board.setAndPrintFigure('C', 4, new Pawn(true));
        board.setAndPrintFigure('C', 6, new Pawn(true));
        board.setAndPrintFigure('C', 8, new Pawn(true));

        board.setAndPrintFigure('F', 1, new Pawn(false));
        board.setAndPrintFigure('F', 3, new Pawn(false));
        board.setAndPrintFigure('F', 5, new Pawn(false));
        board.setAndPrintFigure('F', 7, new Pawn(false));
        board.setAndPrintFigure('G', 2, new Pawn(false));
        board.setAndPrintFigure('G', 4, new Pawn(false));
        board.setAndPrintFigure('G', 6, new Pawn(false));
        board.setAndPrintFigure('G', 8, new Pawn(false));
        board.setAndPrintFigure('H', 1, new Pawn(false));
        board.setAndPrintFigure('H', 3, new Pawn(false));
        board.setAndPrintFigure('H', 5, new Pawn(false));
        board.setAndPrintFigure('H', 7, new Pawn(false));
    }

    public boolean play(InGameUI inGameUI) throws IncorrectMoveFormat, IncorrectMoveException {
        this.inGameUI = inGameUI;
        board.printEmptyBoardAndSideMenu();
        board.refreshFigures();
        printRules();

        boolean b;
        do {
            isFinished = VictoryValidator.validateEndOfGame(board, whiteQueenMoves, blackQueenMoves, activePlayer, rulesSet);
            if (isFinished) {
                save = inGameUI.endOfGame(moves);
                isDraw = VictoryValidator.isDraw();
                winner = VictoryValidator.getWinner();
                break;
            }
            b = this.waitForMove();
        } while (b);
        date = LocalDate.now();
        time = LocalTime.now();
        return save;
    }

    private void printRules() {
        STerminal.getInstance().putStringAtPosition("\"" + rulesSet.getName() + "\" rules", 63, 1);
        STerminal.getInstance().putStringAtPosition(rulesSet.isVictoryConditionsReversed() ? "reversed" : "standard", 63, 4);
        STerminal.getInstance().putStringAtPosition(rulesSet.isCaptureAny() ? "any" : "longest", 63, 7);
        STerminal.getInstance().putStringAtPosition(rulesSet.isPawnMoveBackward() ? "yes" : "no", 63, 10);
        STerminal.getInstance().putStringAtPosition(rulesSet.isPawnCaptureBackward() ? "yes" : "no", 63, 13);
        STerminal.getInstance().putStringAtPosition(rulesSet.isQueenRangeOne() ? "one field" : "any", 63, 16);
        STerminal.getInstance().putStringAtPosition(rulesSet.isQueenRangeOneAfterCapture() ? "next field" : "any", 63, 19);
    }

    private boolean waitForMove() throws IncorrectMoveFormat, IncorrectMoveException {
        String captures = "";
        boolean isItAITurn = false;
        if (isBlackAIPlayer && activePlayer)
            isItAITurn = true;
        if (isWhiteAIPlayer && !activePlayer)
            isItAITurn = true;
        inGameUI.printBoard(activePlayer, moves, isItAITurn);
        try {
            (new CapturePossibilityValidator(board, activePlayer, rulesSet)).validateCapturePossibility();
        } catch (CapturePossibleException e) {
            captures = e.getMessage();
            inGameUI.printCapture(captures, isItAITurn);
        }
        String[] s;
        if (isBlackAIPlayer && activePlayer)
            s = (new AIPlayer1(board, true, rulesSet, whiteQueenMoves, blackQueenMoves)).getAIMove();
        else if (isWhiteAIPlayer && !activePlayer)
            s = (new AIPlayer2(board, false, rulesSet, whiteQueenMoves, blackQueenMoves)).getAIMove();
        else {
            s = inGameUI.getMoveOrOption(captures, isItAITurn, activePlayer);
        }
        if (s == null)
            return true;
        else if (s.length == 1 && inGameMenu(s[0])) {
            return !s[0].equals("x") && !s[0].equals("s");
        } else {
            try {
                this.makeMove(s);
                return true;
            } catch (IncorrectMoveFormat e) {
                inGameUI.printIncorrectMoveFormat(isItAITurn);
                return true;
            }
        }
    }

    private void makeMove(String[] s) throws IncorrectMoveFormat, IncorrectMoveException {
        char x1 = s[0].charAt(0);
        int y1 = Character.getNumericValue(s[1].charAt(0));
        char x2 = s[2].charAt(0);
        int y2 = Character.getNumericValue(s[3].charAt(0));
        boolean isItAITurn = false;
        if (isBlackAIPlayer && activePlayer)
            isItAITurn = true;
        if (isWhiteAIPlayer && !activePlayer)
            isItAITurn = true;
        inGameUI.printMakingMove(x1, y1, x2, y2, isItAITurn);
        Move move = new Move(x1, y1, x2, y2);
        try {
            MoveValidator.validateMove(move, this.board, this.activePlayer, rulesSet);
            moves.add((activePlayer ? "black: " : "white: ") + move);
            move.makeMove(board, true);
            if (board.getFigure(move.getRow2(), move.getCol2()) instanceof Queen) {
                if (activePlayer)
                    blackQueenMoves++;
                else
                    whiteQueenMoves++;
            } else {
                if (activePlayer)
                    blackQueenMoves = 0;
                else
                    whiteQueenMoves = 0;
            }
            this.activePlayer = !this.activePlayer;
            inGameUI.printMoveDone(isItAITurn);

        } catch (CaptureException e) {
            moves.add((activePlayer ? "black: " : "white: ") + move);
            move.makeCapture(board, e.getRow(), e.getCol(), true);
            multiCapture(move);
            inGameUI.printCaptureDone(isItAITurn);
            if (activePlayer)
                blackQueenMoves = 0;
            else
                whiteQueenMoves = 0;
            this.activePlayer = !this.activePlayer;
        } catch (IncorrectMoveException e) {
            inGameUI.printIncorrectMove(e.getMessage(), isItAITurn);
        } finally {
            if ((board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                    && board.getFigure(move.getRow2(), move.getCol2()).getColor()
                    && (move.getRow2()) == 'H')
                board.setAndPrintFigure('H', move.getCol2(), new Queen(true));
            if ((board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                    && !board.getFigure(move.getRow2(), move.getCol2()).getColor()
                    && (move.getRow2()) == 'A')
                board.setAndPrintFigure('A', move.getCol2(), new Queen(false));
            if (!isItAITurn)
                inGameUI.waitForEnter();
        }
    }

    private void multiCapture(Move move) throws IncorrectMoveFormat, IncorrectMoveException {
        do {
            try {
                (new CapturePossibilityValidator(board, activePlayer, rulesSet))
                        .validateCapturePossibilityForOneFigure(move.getRow2(), move.getCol2());
                break;
            } catch (CapturePossibleException e) {
                boolean isItAITurn = false;
                if (isBlackAIPlayer && activePlayer)
                    isItAITurn = true;
                if (isWhiteAIPlayer && !activePlayer)
                    isItAITurn = true;
                inGameUI.printBoard(activePlayer, moves, isItAITurn);
                inGameUI.printMultiCapture(e.getMessage(), isItAITurn);
                String[] s;
                if (isBlackAIPlayer && activePlayer) {
                    s = (new AIPlayer1(board, true, rulesSet, whiteQueenMoves, blackQueenMoves,
                            move.getRow2(), move.getCol2())).getAIMove();
                } else if (isWhiteAIPlayer && !activePlayer) {
                    s = (new AIPlayer2(board, false, rulesSet, whiteQueenMoves, blackQueenMoves,
                            move.getRow2(), move.getCol2())).getAIMove();
                } else {
                    s = inGameUI.getMoveOrOption(e.getMessage(), isItAITurn, activePlayer);
                }
                if (s != null && (s.length != 1 || !inGameMenu(s[0]))) {
                    char x1 = s[0].charAt(0);
                    int y1 = Character.getNumericValue(s[1].charAt(0));
                    char x2 = s[2].charAt(0);
                    int y2 = Character.getNumericValue(s[3].charAt(0));
                    inGameUI.printMakingMove(x1, y1, x2, y2, isItAITurn);
                    move = new Move(x1, y1, x2, y2);
                    try {
                        MoveValidator.validateMove(move, this.board, this.activePlayer, rulesSet);
                    } catch (CaptureException e1) {
                        moves.add((activePlayer ? "black: " : "white: ") + move);
                        move.makeCapture(board, e1.getRow(), e1.getCol(), true);
                    }
                }
            }
        } while (true);
        if ((board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                && board.getFigure(move.getRow2(), move.getCol2()).getColor()
                && (move.getRow2()) == 'H')
            board.setAndPrintFigure('H', move.getCol2(), new Queen(true));
        if ((board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                && !board.getFigure(move.getRow2(), move.getCol2()).getColor()
                && (move.getRow2()) == 'A')
            board.setAndPrintFigure('A', move.getCol2(), new Queen(false));
    }

    private boolean inGameMenu(String s) {
        switch (s) {
            case "h":
                inGameUI.switchToMovesHistory(moves);
                return true;
            case "s":
                save = true;
                return true;
            default:
                save = false;
                return true;
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (date == null || time == null)
            return "";
        String s = new StringBuilder()
                .append(date.getDayOfMonth() < 10 ? ("0" + date.getDayOfMonth()) : date.getDayOfMonth())
                .append(".")
                .append(date.getMonthValue() < 10 ? ("0" + date.getMonthValue()) : date.getMonthValue())
                .append(".")
                .append(date.getYear())
                .append(" ")
                .append(time.getHour() < 10 ? ("0" + time.getHour()) : time.getHour())
                .append(":")
                .append(time.getMinute() < 10 ? ("0" + time.getMinute()) : time.getMinute())
                .toString();
        return name + " (" + s + ")";
    }

    private Board getBoard() {
        return board;
    }

    private List<String> getMoves() {
        return moves;
    }

    private boolean isActivePlayer() {
        return activePlayer;
    }

    private int getWhiteQueenMoves() {
        return whiteQueenMoves;
    }

    private int getBlackQueenMoves() {
        return blackQueenMoves;
    }

    private boolean isFinished() {
        return isFinished;
    }

    private boolean isDraw() {
        return isDraw;
    }

    private boolean isWinner() {
        return winner;
    }

    private boolean isSave() {
        return save;
    }

    private LocalDate getDate() {
        return date;
    }

    private LocalTime getTime() {
        return time;
    }

    private RulesSet getRulesSet() {
        return rulesSet;
    }

    private boolean isBlackAIPlayer() {
        return isBlackAIPlayer;
    }

    private boolean isWhiteAIPlayer() {
        return isWhiteAIPlayer;
    }

    private InGameUI getInGameUI() {
        return inGameUI;
    }

}