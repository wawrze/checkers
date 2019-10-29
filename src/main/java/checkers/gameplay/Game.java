package checkers.gameplay;

import checkers.board.Board;
import checkers.figures.Figure;
import checkers.figures.Pawn;
import checkers.figures.Queen;
import checkers.moves.CapturePossibilityValidator;
import checkers.moves.Move;
import checkers.moves.MoveValidator;
import checkers.ui.InGameUI;
import checkers.ui.Menu;
import checkers.ui.Window;
import exceptions.CaptureException;
import exceptions.CapturePossibleException;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private transient Menu menu;
    private char clickedFigureRow = 0;
    private int clickedFigureCol = 0;
    private char targetRow = 0;
    private int targetCol = 0;

    public Game(Game game, Menu menu) {
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
        this.menu = menu;
    }

    public Game(String name, RulesSet rulesSet, boolean isBlackAIPlayer, boolean isWhiteAIPlayer, Menu menu) {
        board = new Board();
        moves = new LinkedList<>();
        this.menu = menu;
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
        board.setFigure('A', 2, new Pawn(true));
        board.setFigure('A', 4, new Pawn(true));
        board.setFigure('A', 6, new Pawn(true));
        board.setFigure('A', 8, new Pawn(true));
        board.setFigure('B', 1, new Pawn(true));
        board.setFigure('B', 3, new Pawn(true));
        board.setFigure('B', 5, new Pawn(true));
        board.setFigure('B', 7, new Pawn(true));
        board.setFigure('C', 2, new Pawn(true));
        board.setFigure('C', 4, new Pawn(true));
        board.setFigure('C', 6, new Pawn(true));
        board.setFigure('C', 8, new Pawn(true));

        board.setFigure('F', 1, new Pawn(false));
        board.setFigure('F', 3, new Pawn(false));
        board.setFigure('F', 5, new Pawn(false));
        board.setFigure('F', 7, new Pawn(false));
        board.setFigure('G', 2, new Pawn(false));
        board.setFigure('G', 4, new Pawn(false));
        board.setFigure('G', 6, new Pawn(false));
        board.setFigure('G', 8, new Pawn(false));
        board.setFigure('H', 1, new Pawn(false));
        board.setFigure('H', 3, new Pawn(false));
        board.setFigure('H', 5, new Pawn(false));
        board.setFigure('H', 7, new Pawn(false));
    }

    public void play(InGameUI inGameUI) throws IncorrectMoveFormat, IncorrectMoveException {
        this.inGameUI = inGameUI;
        board.printEmptyBoardAndSideMenu();
        board.refreshFigures();
        prepareButtons();
        inGameUI.showInGameInfos(rulesSet);
        waitForMove();
        date = LocalDate.now();
        time = LocalTime.now();
    }

    private void prepareButtons() {
        AnchorPane boardContainer = (AnchorPane) Window.getGameLayout().getChildren().get(0);

        Button saveAndExitButton = new Button("SAVE\nAND\nEXIT");
        saveAndExitButton.setLayoutX(775);
        saveAndExitButton.setLayoutY(480);
        saveAndExitButton.setPrefHeight(60);
        saveAndExitButton.setPrefWidth(103);
        saveAndExitButton.setOnAction(e -> saveAndExit());
        boardContainer.getChildren().add(saveAndExitButton);

        Button exitWithoutSaveButton = new Button("    EXIT\nWITHOUT\n SAVING");
        exitWithoutSaveButton.setLayoutX(775);
        exitWithoutSaveButton.setLayoutY(571);
        exitWithoutSaveButton.setPrefHeight(60);
        exitWithoutSaveButton.setPrefWidth(103);
        exitWithoutSaveButton.setOnAction(e -> exitWithoutSaving());
        boardContainer.getChildren().add(exitWithoutSaveButton);
    }

    private void saveAndExit() {
        menu.getGames().put(name, this);
        Window.clearGameLayout();
        menu.start();
    }

    private void exitWithoutSaving() {
        Window.clearGameLayout();
        menu.start();
    }

    private void waitForMove() throws IncorrectMoveFormat, IncorrectMoveException {
        String captures = "";
        boolean isItAITurn = false;
        if (isBlackAIPlayer && activePlayer)
            isItAITurn = true;
        if (isWhiteAIPlayer && !activePlayer)
            isItAITurn = true;
        inGameUI.printMovesAndActivePlayer(moves, activePlayer);
        try {
            (new CapturePossibilityValidator(board, activePlayer, rulesSet)).validateCapturePossibility();
        } catch (CapturePossibleException e) {
            captures = e.getMessage();
            inGameUI.printCapture(captures, isItAITurn);
        }
        String[] s;
        if (isBlackAIPlayer && activePlayer) {
            s = (new AIPlayer1(board, true, rulesSet, whiteQueenMoves, blackQueenMoves)).getAIMove();
            try {
                this.makeMove(s);
            } catch (IncorrectMoveFormat e) {
                inGameUI.printIncorrectMoveFormat(isItAITurn);
            }
        } else if (isWhiteAIPlayer && !activePlayer) {
            s = (new AIPlayer2(board, false, rulesSet, whiteQueenMoves, blackQueenMoves)).getAIMove();
            try {
                this.makeMove(s);
            } catch (IncorrectMoveFormat e) {
                inGameUI.printIncorrectMoveFormat(isItAITurn);
            }
        } else {
            getMoveOrOption(captures, isItAITurn, board, false);
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
            inGameUI.printMakingMove(x1, y1, x2, y2, isItAITurn, false);
        } catch (CaptureException e) {
            moves.add((activePlayer ? "black: " : "white: ") + move);
            move.makeCapture(board, e.getRow(), e.getCol(), true);
            inGameUI.printMakingMove(x1, y1, x2, y2, isItAITurn, true);
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
        }
        isFinished = VictoryValidator.validateEndOfGame(board, whiteQueenMoves, blackQueenMoves, activePlayer, rulesSet);
        if (isFinished) {
            isDraw = VictoryValidator.isDraw();
            winner = VictoryValidator.getWinner();
            inGameUI.endOfGame(moves);
        } else {
            waitForMove();
        }
    }

    private void multiCapture(Move move) throws IncorrectMoveFormat, IncorrectMoveException {
        try {
            (new CapturePossibilityValidator(board, activePlayer, rulesSet))
                    .validateCapturePossibilityForOneFigure(move.getRow2(), move.getCol2());
            return;
        } catch (CapturePossibleException e) {
            boolean isItAITurn = false;
            if (isBlackAIPlayer && activePlayer)
                isItAITurn = true;
            if (isWhiteAIPlayer && !activePlayer)
                isItAITurn = true;
//                inGameUI.printBoard(activePlayer, moves, isItAITurn); TODO
            inGameUI.printMultiCapture(e.getMessage(), isItAITurn);
            String[] s;
            if (isBlackAIPlayer && activePlayer) {
                s = (new AIPlayer1(board, true, rulesSet, whiteQueenMoves, blackQueenMoves,
                        move.getRow2(), move.getCol2())).getAIMove();
                if (s != null) {
                    char x1 = s[0].charAt(0);
                    int y1 = Character.getNumericValue(s[1].charAt(0));
                    char x2 = s[2].charAt(0);
                    int y2 = Character.getNumericValue(s[3].charAt(0));
                    move = new Move(x1, y1, x2, y2);
                    try {
                        MoveValidator.validateMove(move, this.board, this.activePlayer, rulesSet);
                    } catch (CaptureException e1) {
                        moves.add((activePlayer ? "black: " : "white: ") + move);
                        move.makeCapture(board, e1.getRow(), e1.getCol(), true);
                        inGameUI.printMakingMove(x1, y1, x2, y2, isItAITurn, true);
                    }
                }
            } else if (isWhiteAIPlayer && !activePlayer) {
                s = (new AIPlayer2(board, false, rulesSet, whiteQueenMoves, blackQueenMoves,
                        move.getRow2(), move.getCol2())).getAIMove();
                if (s != null) {
                    char x1 = s[0].charAt(0);
                    int y1 = Character.getNumericValue(s[1].charAt(0));
                    char x2 = s[2].charAt(0);
                    int y2 = Character.getNumericValue(s[3].charAt(0));
                    move = new Move(x1, y1, x2, y2);
                    try {
                        MoveValidator.validateMove(move, this.board, this.activePlayer, rulesSet);
                    } catch (CaptureException e1) {
                        moves.add((activePlayer ? "black: " : "white: ") + move);
                        move.makeCapture(board, e1.getRow(), e1.getCol(), true);
                        inGameUI.printMakingMove(x1, y1, x2, y2, isItAITurn, true);
                    }
                }
            } else {
                activePlayer = !activePlayer;
                getMoveOrOption(e.getMessage(), isItAITurn, board, true);
            }
        }
        if (
                (board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                        && board.getFigure(move.getRow2(), move.getCol2()).getColor()
                        && (move.getRow2()) == 'H'
        ) {
            board.setAndPrintFigure('H', move.getCol2(), new Queen(true));
        } else if (
                (board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                        && !board.getFigure(move.getRow2(), move.getCol2()).getColor()
                        && (move.getRow2()) == 'A'
        ) {
            board.setAndPrintFigure('A', move.getCol2(), new Queen(false));
        }
    }

    public String getName() {
        return name;
    }

    private void getMoveOrOption(String captures, boolean isItAITurn, Board board, boolean isMultiCapture) {
        AnchorPane boardLayout = (AnchorPane) Window.getGameLayout().getChildren().get(0);
        Map<Character, ImageView[]> figuresOnBoard = board.getFiguresOnBoard();

        boardLayout.setOnMouseClicked(e -> {
            if (clickedFigureRow != 0 && clickedFigureCol != 0) {
                if (e.getSceneX() > 38 && e.getSceneX() < 114) {
                    targetCol = 1;
                } else if (e.getSceneX() > 114 && e.getSceneX() < 189) {
                    targetCol = 2;
                } else if (e.getSceneX() > 189 && e.getSceneX() < 264) {
                    targetCol = 3;
                } else if (e.getSceneX() > 264 && e.getSceneX() < 344) {
                    targetCol = 4;
                } else if (e.getSceneX() > 344 && e.getSceneX() < 419) {
                    targetCol = 5;
                } else if (e.getSceneX() > 419 && e.getSceneX() < 494) {
                    targetCol = 6;
                } else if (e.getSceneX() > 494 && e.getSceneX() < 569) {
                    targetCol = 7;
                } else if (e.getSceneX() > 569 && e.getSceneX() < 644) {
                    targetCol = 8;
                }
                if (e.getSceneY() > 38 && e.getSceneY() < 114) {
                    targetRow = 'A';
                } else if (e.getSceneY() > 114 && e.getSceneY() < 189) {
                    targetRow = 'B';
                } else if (e.getSceneY() > 189 && e.getSceneY() < 264) {
                    targetRow = 'C';
                } else if (e.getSceneY() > 264 && e.getSceneY() < 344) {
                    targetRow = 'D';
                } else if (e.getSceneY() > 344 && e.getSceneY() < 419) {
                    targetRow = 'E';
                } else if (e.getSceneY() > 419 && e.getSceneY() < 494) {
                    targetRow = 'F';
                } else if (e.getSceneY() > 494 && e.getSceneY() < 569) {
                    targetRow = 'G';
                } else if (e.getSceneY() > 569 && e.getSceneY() < 644) {
                    targetRow = 'H';
                }
                if (board.getFiguresOnBoard().get(targetRow)[targetCol - 1] != null) {
                    targetRow = 0;
                    targetCol = 0;
                }

                if (targetCol != 0 && targetRow != 0) {
                    String[] result = new String[4];
                    result[0] = "" + clickedFigureRow;
                    result[1] = "" + clickedFigureCol;
                    result[2] = "" + targetRow;
                    result[3] = "" + targetCol;
                    ImageView markedFigure = figuresOnBoard.get(clickedFigureRow)[clickedFigureCol - 1];
                    if (markedFigure != null) {
                        Figure fig = board.getFigure(clickedFigureRow, clickedFigureCol);
                        String imagePath = "";
                        if (fig instanceof Pawn && fig.getColor()) {
                            imagePath = "images/black_pawn.png";
                        } else if (fig instanceof Pawn && !fig.getColor()) {
                            imagePath = "images/white_pawn.png";
                        } else if (fig instanceof Queen && fig.getColor()) {
                            imagePath = "images/black_queen.png";
                        } else if (fig instanceof Queen && !fig.getColor()) {
                            imagePath = "images/white_queen.png";
                        }
                        try {
                            FileInputStream input = new FileInputStream(imagePath);
                            Image img = new Image(input);
                            markedFigure.setImage(img);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                    clickedFigureRow = 0;
                    clickedFigureCol = 0;
                    targetRow = 0;
                    targetCol = 0;
                    String moveString = "" + result[0] + result[1] + "-" + result[2] + result[3];
                    if (isMultiCapture) {
                        try {
                            char x1 = result[0].charAt(0);
                            int y1 = Character.getNumericValue(result[1].charAt(0));
                            char x2 = result[2].charAt(0);
                            int y2 = Character.getNumericValue(result[3].charAt(0));
                            Move move = new Move(x1, y1, x2, y2);
                            try {
                                MoveValidator.validateMove(move, this.board, this.activePlayer, rulesSet);
                            } catch (CaptureException e1) {
                                moves.add((activePlayer ? "black: " : "white: ") + move);
                                move.makeCapture(board, e1.getRow(), e1.getCol(), true);
                                inGameUI.printMakingMove(x1, y1, x2, y2, isItAITurn, true);
                            } catch (IncorrectMoveException exception) {
                                exception.printStackTrace();
                            }
                        } catch (IncorrectMoveFormat exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        try {
                            inGameUI.validate(moveString);
                            if (!captures.isEmpty() && !captures.contains(moveString)) {
                                inGameUI.printCaptureObligatory(isItAITurn);
                                return;
                            }
                        } catch (IncorrectMoveFormat exception) {
                            inGameUI.printIncorrectMoveFormat(isItAITurn);
                            exception.printStackTrace();
                            return;
                        }
                        try {
                            this.makeMove(result);
                        } catch (IncorrectMoveFormat exception) {
                            inGameUI.printIncorrectMoveFormat(isItAITurn);
                            exception.printStackTrace();
                        } catch (IncorrectMoveException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        });
        for (Map.Entry<Character, ImageView[]> row : figuresOnBoard.entrySet()) {
            for (ImageView figure : row.getValue()) {
                if (figure == null) continue;
                figure.setOnMouseClicked(e -> {
                    char newClickedFigureRow = (char) ((figure.getY() / 75) + 65);
                    int newClickedFigureCol = (int) ((figure.getX() / 75) + 1);
                    if (newClickedFigureCol == clickedFigureCol && newClickedFigureRow == clickedFigureRow) {
                        clickedFigureRow = 0;
                        clickedFigureCol = 0;
                    } else if (clickedFigureCol == 0 && clickedFigureRow == 0) {
                        clickedFigureRow = newClickedFigureRow;
                        clickedFigureCol = newClickedFigureCol;
                    } else {
                        return;
                    }
                    Figure fig = board.getFigure(newClickedFigureRow, newClickedFigureCol);
                    String imagePath;
                    if (fig instanceof Pawn && fig.getColor()) {
                        if (!activePlayer) {
                            clickedFigureRow = 0;
                            clickedFigureCol = 0;
                            return;
                        }
                        if (clickedFigureRow == 0 && clickedFigureCol == 0) {
                            imagePath = "images/black_pawn.png";
                        } else {
                            imagePath = "images/black_pawn_clicked.png";
                        }
                    } else if (fig instanceof Pawn && !fig.getColor()) {
                        if (activePlayer) {
                            clickedFigureRow = 0;
                            clickedFigureCol = 0;
                            return;
                        }
                        if (clickedFigureRow == 0 && clickedFigureCol == 0) {
                            imagePath = "images/white_pawn.png";
                        } else {
                            imagePath = "images/white_pawn_clicked.png";
                        }
                    } else if (fig instanceof Queen && fig.getColor()) {
                        if (!activePlayer) {
                            clickedFigureRow = 0;
                            clickedFigureCol = 0;
                            return;
                        }
                        if (clickedFigureRow == 0 && clickedFigureCol == 0) {
                            imagePath = "images/black_queen.png";
                        } else {
                            imagePath = "images/black_queen_clicked.png";
                        }
                    } else if (fig instanceof Queen && !fig.getColor()) {
                        if (activePlayer) {
                            clickedFigureRow = 0;
                            clickedFigureCol = 0;
                            return;
                        }
                        if (clickedFigureRow == 0 && clickedFigureCol == 0) {
                            imagePath = "images/white_queen.png";
                        } else {
                            imagePath = "images/white_queen_clicked.png";
                        }
                    } else {
                        return;
                    }
                    try {
                        FileInputStream input = new FileInputStream(imagePath);
                        Image img = new Image(input);
                        figure.setImage(img);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                });
            }
        }
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