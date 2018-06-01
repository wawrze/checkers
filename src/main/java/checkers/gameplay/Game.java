package checkers.gameplay;

import checkers.Menu;
import checkers.board.*;
import checkers.figures.*;
import checkers.moves.*;
import exceptions.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Game implements Serializable {

    private Board board;
    private List<String> moves;
    private boolean player;
    private boolean simplePrint;
    private int whiteQueenMoves;
    private int blackQueenMoves;
    private boolean isFinished;
    private boolean isDraw;
    private boolean winner;
    private String name;
    private boolean save;
    private LocalDate date;
    private LocalTime time;
    private RulesSet rulesSet;

    public Game(Game game) {
        this.board = new Board(game.getBoard());
        this.moves = new LinkedList<>(game.getMoves());
        this.player = game.isPlayer();
        this.simplePrint = game.isSimplePrint();
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
    }

    public Game(String name, RulesSet rulesSet, boolean simplePrint) {
        board = new Board();
        moves = new LinkedList<>();
        player = false;
        this.simplePrint = simplePrint;
        whiteQueenMoves = 0;
        blackQueenMoves = 0;
        isFinished = false;
        isDraw = false;
        this.name = name;
        save = false;
        this.rulesSet = rulesSet;

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

    public boolean play(){
        boolean b;
        do {
            isFinished = VictoryValidator.validateEndOfGame(board, whiteQueenMoves, blackQueenMoves, player);
            if (isFinished) {
                save = InGameUI.endOfGame(board,simplePrint,moves);
                isDraw = VictoryValidator.isDraw();
                winner = VictoryValidator.getWinner();
                break;
            }
            b = this.waitForMove();
        } while (b);
        if(save && name.isEmpty()) {
            name = InGameUI.getGameName();
        }
        date = LocalDate.now();
        time = LocalTime.now();
        return save;
    }

    private boolean waitForMove() {
        String captures = "";
        InGameUI.printBoard(board, simplePrint, player);
        try {
            (new CapturePossibilityValidator(board,player)).validateCapturePossibility();
        }
        catch(CapturePossibleException e){
            captures = e.getMessage();
            InGameUI.printCapture(captures);
        }
        String[] s = InGameUI.getMoveOrOption(captures);
        if(s == null)
            return true;
        else if(s.length == 1 && inGameMenu(s[0])){
            if (s[0].equals("x") || s[0].equals("s"))
                return false;
            else
                return true;
        }
        else if(s.length == 4){
            try {
                this.makeMove(s);
                return true;
            }
            catch(IncorrectMoveFormat e){
                InGameUI.printIncorrectMoveFormat();
                return true;
            }
        }
        else
            throw new UnknownException();
    }

    private void makeMove(String[] s) throws IncorrectMoveFormat{
        char x1 = s[0].charAt(0);
        int y1 = Character.getNumericValue(s[1].charAt(0));
        char x2 = s[2].charAt(0);
        int y2 = Character.getNumericValue(s[3].charAt(0));
        InGameUI.printMakingMove(x1,y1,x2,y2);
        Move move = new Move(x1, y1, x2, y2);
        try {
            MoveValidator.validateMove(move, this.board, this.player);
            moves.add((player ? "black: " : "white: ") + move);
            move.makeMove(board);
            if(board.getFigure(move.getRow2(),move.getCol2()) instanceof Queen){
                if(player)
                    blackQueenMoves++;
                else
                    whiteQueenMoves++;
            }
            else{
                if(player)
                    blackQueenMoves = 0;
                else
                    whiteQueenMoves = 0;
            }
            this.player = !this.player;
            InGameUI.printMoveDone();

        }catch (CaptureException e){
            moves.add((player ? "black: " : "white: ") + move);
            move.makeCapture(board,e.getRow(),e.getCol());
            multiCapture(move);
            InGameUI.printCaptureDone();
            if(player)
                blackQueenMoves = 0;
            else
                whiteQueenMoves = 0;
            this.player = !this.player;
        }catch (IncorrectMoveException e){
            InGameUI.printIncorrectMove(e.getMessage());
        }finally{
            if((board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                    && board.getFigure(move.getRow2(), move.getCol2()).getColor()
                    && (move.getRow2()) == 'H')
                board.setFigure('H', move.getCol2(), new Queen(true));
            if((board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                    && !board.getFigure(move.getRow2(), move.getCol2()).getColor()
                    && (move.getRow2()) == 'A')
                board.setFigure('A', move.getCol2(), new Queen(false));
            Menu.waitForEnter();
        }
    }

    private void multiCapture(Move move) {
        do {
            try{
                (new CapturePossibilityValidator(board,player))
                        .validateCapturePossibilityForOneFigure(move.getRow2(),move.getCol2());
                break;
            }
            catch(CapturePossibleException e){
                InGameUI.printBoard(board,simplePrint,player);
                InGameUI.printMultiCapture(e.getMessage());
                String[] s = InGameUI.getMoveOrOption(e.getMessage());
                if(s == null)
                    continue;
                else if(s.length == 1 && inGameMenu(s[0])){
                    continue;
                }
                else if(s.length == 4){
                    char x1 = s[0].charAt(0);
                    int y1 = Character.getNumericValue(s[1].charAt(0));
                    char x2 = s[2].charAt(0);
                    int y2 = Character.getNumericValue(s[3].charAt(0));
                    try {
                        move = new Move(x1, y1, x2, y2);
                        try{
                            MoveValidator.validateMove(move,this.board,this.player);
                            InGameUI.printIncorrectMove("continue capturing is obligatory!");
                        }
                        catch(CaptureException e1){
                            moves.add((player ? "black: " : "white: ") + move);
                            move.makeCapture(board,e1.getRow(),e1.getCol());
                        }
                        catch(IncorrectMoveException e1){
                            InGameUI.printIncorrectMove("continue capturing is obligatory!");
                        }
                    }
                    catch(IncorrectMoveFormat e1){
                        InGameUI.printIncorrectMoveFormat();
                        continue;
                    }
                }
                else
                    throw new UnknownException();
                continue;
            }
        }while (true);
        if((board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                && board.getFigure(move.getRow2(), move.getCol2()).getColor()
                && (move.getRow2()) == 'H')
            board.setFigure('H', move.getCol2(), new Queen(true));
        if((board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                && !board.getFigure(move.getRow2(), move.getCol2()).getColor()
                && (move.getRow2()) == 'A')
            board.setFigure('A', move.getCol2(), new Queen(false));
    }

    private boolean inGameMenu(String s){
        switch (s) {
            case "h":
                InGameUI.printMoveHistory(moves);
                Menu.waitForEnter();
                return true;
            case "p":
                this.simplePrint = !this.simplePrint;
                return true;
            case "s":
                save = true;
                return true;
            case "x":
                save = false;
                return true;
            default:
                break;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        String s = "";
        s += (date.getDayOfMonth() < 10 ? ("0" + date.getDayOfMonth()) : date.getDayOfMonth());
        s += ("." + (date.getMonthValue() < 10 ? ("0" + date.getMonthValue()) : date.getMonthValue()));
        s += ("." + date.getYear());
        s += (" " + (time.getHour() < 10 ? ("0" + time.getHour()) : time.getHour()));
        s += (":" + (time.getMinute() < 10 ? ("0" + time.getMinute()) : time.getMinute()));
        return name + " (\"" + rulesSet.getName() + "\" rules, " + moves.size() + " moves done, "
                + (isFinished ? ("finished, " + (isDraw ? "draw)" : ("winner: "
                + (winner ? "black" : "white")))) : ("not finished")) + ", date and time of save: " + s + ")";
    }

    public Board getBoard() {
        return board;
    }

    public List<String> getMoves() {
        return moves;
    }

    public boolean isPlayer() {
        return player;
    }

    public boolean isSimplePrint() {
        return simplePrint;
    }

    public int getWhiteQueenMoves() {
        return whiteQueenMoves;
    }

    public int getBlackQueenMoves() {
        return blackQueenMoves;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isSave() {
        return save;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public RulesSet getRulesSet() {
        return rulesSet;
    }

}