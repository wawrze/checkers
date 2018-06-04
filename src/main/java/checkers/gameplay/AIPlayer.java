package checkers.gameplay;

import checkers.board.Board;
import checkers.figures.None;
import checkers.figures.Pawn;
import checkers.figures.Queen;
import checkers.moves.CapturePossibilityValidator;
import checkers.moves.Move;
import checkers.moves.MoveValidator;
import exceptions.*;

import java.util.*;

public class AIPlayer {

    private final int MAX_DEPTH = 8;

    private Board board;
    private boolean AIPlayer;
    private boolean activePlayer;
    private RulesSet rulesSet;
    private int whiteQueenMoves;
    private int blackQueenMoves;
    private int depth;
    private Map<Move,Integer> possibleMoves;

    public AIPlayer(Board board, boolean player, RulesSet rulesSet, int whiteQueenMoves, int blackQueenMoves) {
        this.board = board;
        this.AIPlayer = player;
        this.activePlayer = player;
        this.rulesSet = rulesSet;
        this.whiteQueenMoves = whiteQueenMoves;
        this.blackQueenMoves = blackQueenMoves;
        this.depth = 1;
        possibleMoves = new HashMap<>();
        getPossibleMoves();
        evaluateMoves();
    }

    public AIPlayer(Board board, boolean player, RulesSet rulesSet, int whiteQueenMoves, int blackQueenMoves, char row,
                    int col) {
        this.board = board;
        this.AIPlayer = player;
        this.activePlayer = player;
        this.rulesSet = rulesSet;
        this.whiteQueenMoves = whiteQueenMoves;
        this.blackQueenMoves = blackQueenMoves;
        this.depth = 1;
        possibleMoves = new HashMap<>();
        getPossibleMovesMultiCapture(row, col);
        evaluateMoves();
    }

    private AIPlayer(Board board, boolean AIPlayer, boolean activePlayer, RulesSet rulesSet, int whiteQueenMoves,
                     int blackQueenMoves, int depth) {
        this.board = board;
        this.AIPlayer = AIPlayer;
        this.activePlayer = activePlayer;
        this.rulesSet = rulesSet;
        this.whiteQueenMoves = whiteQueenMoves;
        this.blackQueenMoves = blackQueenMoves;
        this.depth = depth + 1;
        possibleMoves = new HashMap<>();
        getPossibleMoves();
        evaluateMoves();
    }

    private AIPlayer(Board board, boolean AIPlayer, boolean activePlayer, RulesSet rulesSet, int whiteQueenMoves,
                     int blackQueenMoves, int depth, char row, int col) {
        this.board = board;
        this.AIPlayer = AIPlayer;
        this.activePlayer = activePlayer;
        this.rulesSet = rulesSet;
        this.whiteQueenMoves = whiteQueenMoves;
        this.blackQueenMoves = blackQueenMoves;
        this.depth = depth + 1;
        possibleMoves = new HashMap<>();
        getPossibleMovesMultiCapture(row, col);
        evaluateMoves();
    }

    private void evaluateMoves(){
        Map<Move,Integer> moves = new HashMap<>(possibleMoves);
        boolean capture;
        for(Map.Entry<Move,Integer> entry : moves.entrySet()){
            Board tmpBoard = new Board(board);
            capture = false;
            try{
                MoveValidator.validateMove(entry.getKey(), tmpBoard, activePlayer, rulesSet);
                entry.getKey().makeMove(tmpBoard);
                if(tmpBoard.getFigure(entry.getKey().getRow2(), entry.getKey().getCol2()) instanceof Queen){
                    if(activePlayer)
                        blackQueenMoves++;
                    else
                        whiteQueenMoves++;
                }
                if(VictoryValidator.validateEndOfGame(tmpBoard, whiteQueenMoves, blackQueenMoves, activePlayer,
                        rulesSet)) {
                    possibleMoves.remove(entry.getKey());
                    possibleMoves.put(entry.getKey(), evaluateWhenEndOfGame());
                }
                else {
                    possibleMoves.remove(entry.getKey());
                    possibleMoves.put(entry.getKey(), 0);
                }
            }
            catch(CaptureException e) {
                entry.getKey().makeCapture(tmpBoard, e.getRow(), e.getCol());
                try {
                    (new CapturePossibilityValidator(tmpBoard, activePlayer, rulesSet))
                            .validateCapturePossibilityForOneFigure(entry.getKey().getRow2(), entry.getKey().getCol2());
                    if(VictoryValidator.validateEndOfGame(tmpBoard, whiteQueenMoves, blackQueenMoves, activePlayer,
                            rulesSet)) {
                        possibleMoves.remove(entry.getKey());
                        possibleMoves.put(entry.getKey(), evaluateWhenEndOfGame());
                    }
                    else {
                        if(activePlayer == AIPlayer) {
                            possibleMoves.remove(entry.getKey());
                            possibleMoves.put(entry.getKey(), 1);
                        }
                        else {
                            possibleMoves.remove(entry.getKey());
                            possibleMoves.put(entry.getKey(), -1);
                        }
                    }
                }
                catch(CapturePossibleException e1) {
                    if(activePlayer == AIPlayer) {
                        if(rulesSet.isCaptureAny()) {
                            possibleMoves.remove(entry.getKey());
                            possibleMoves.put(entry.getKey(), 10);
                        }
                        else {
                            possibleMoves.remove(entry.getKey());
                            possibleMoves.put(entry.getKey(), 100);
                        }
                    }
                    else {
                        possibleMoves.remove(entry.getKey());
                        possibleMoves.put(entry.getKey(), -100);
                    }
                    capture = true;
                }
            }
            catch(IncorrectMoveException e) {
                possibleMoves.remove(entry.getKey());
                continue;
            }
            possibleMoves.remove(entry.getKey());
            possibleMoves.put(entry.getKey(), entry.getValue() + getFigureSetEvaluation(tmpBoard));
            if(depth < MAX_DEPTH){
                if(capture) {
                    AIPlayer next_move = new AIPlayer(tmpBoard, AIPlayer, activePlayer, rulesSet, whiteQueenMoves,
                            blackQueenMoves, depth, entry.getKey().getRow2(), entry.getKey().getCol2());
                    possibleMoves.remove(entry.getKey());
                    possibleMoves.put(entry.getKey(), entry.getValue() + next_move.getMovesMapValue());
                }
                else {
                    AIPlayer next_move = new AIPlayer(tmpBoard, AIPlayer, !activePlayer, rulesSet, whiteQueenMoves,
                            blackQueenMoves, depth);
                    possibleMoves.remove(entry.getKey());
                    possibleMoves.put(entry.getKey(), entry.getValue() + next_move.getMovesMapValue());
                }
            }
        }
    }

    private int getMovesMapValue(){
        return possibleMoves.entrySet().stream()
                .mapToInt(entry -> entry.getValue())
                .sum();
    }

    private int evaluateWhenEndOfGame(){
        if (VictoryValidator.isDraw())
            return 0;
        else if(VictoryValidator.getWinner() == AIPlayer)
            return 1000;
        else
            return -1000;
    }

    private void getPossibleMovesMultiCapture(char row, int col){
        try {
            (new CapturePossibilityValidator(board, activePlayer, rulesSet))
                    .validateCapturePossibilityForOneFigure(row, col);
        }
        catch(CapturePossibleException e) {
            moveListFromCaptures(e.getMessage());
        }
    }

    private void moveListFromCaptures(String captureList){
        String[] sArray = captureList.split(" ");
        for(String s : sArray){
            String[] sA = s.split("-");
            char x1 = sA[0].charAt(0);
            int y1 = Character.getNumericValue(sA[0].charAt(1));
            char x2 = sA[1].charAt(0);
            int y2 = Character.getNumericValue(sA[1].charAt(1));
            try {
                Move move = new Move(x1, y1, x2, y2);
                possibleMoves.put(move, 1);
            }
            catch(IncorrectMoveFormat e1){}
        }
    }

    private void getPossibleMoves() {
        try {
            (new CapturePossibilityValidator(board, activePlayer, rulesSet)).validateCapturePossibility();
            for(int i = 1;i<9;i++) {
                if (!(board.getFigure('A', i) instanceof None) && board.getFigure('A', i).getColor() == activePlayer){
                    getFigureMovePossibility('A', i);
                }
                if (!(board.getFigure('B', i) instanceof None) && board.getFigure('B', i).getColor() == activePlayer){
                    getFigureMovePossibility('B', i);
                }
                if (!(board.getFigure('C', i) instanceof None) && board.getFigure('C', i).getColor() == activePlayer){
                    getFigureMovePossibility('C', i);
                }
                if (!(board.getFigure('D', i) instanceof None) && board.getFigure('D', i).getColor() == activePlayer){
                    getFigureMovePossibility('D', i);
                }
                if (!(board.getFigure('E', i) instanceof None) && board.getFigure('E', i).getColor() == activePlayer){
                    getFigureMovePossibility('E', i);
                }
                if (!(board.getFigure('F', i) instanceof None) && board.getFigure('F', i).getColor() == activePlayer){
                    getFigureMovePossibility('F', i);
                }
                if (!(board.getFigure('G', i) instanceof None) && board.getFigure('G', i).getColor() == activePlayer){
                    getFigureMovePossibility('G', i);
                }
                if (!(board.getFigure('H', i) instanceof None) && board.getFigure('H', i).getColor() == activePlayer){
                    getFigureMovePossibility('H', i);
                }
            }
        }
        catch(CapturePossibleException e) {
            moveListFromCaptures(e.getMessage());
        }
    }

    private int getFigureSetEvaluation(Board board) {
        int value = 0;
        int tmp;
        for(int i = 1;i<9;i++) {
            if (!(board.getFigure('A', i) instanceof None)) {
                if(board.getFigure('A', i) instanceof Queen) {
                    tmp = 15;
                }
                else {
                    if (board.getFigure('A', i).getColor())
                        tmp = 1;
                    else
                        tmp = 8;
                }
                if(AIPlayer != board.getFigure('A', i).getColor())
                    tmp *= -1;
                value += tmp;
            }
            if (!(board.getFigure('B', i) instanceof None)) {
                if(board.getFigure('B', i) instanceof Queen) {
                    tmp = 15;
                }
                else {
                    if (board.getFigure('B', i).getColor())
                        tmp = 2;
                    else
                        tmp = 7;
                }
                if(AIPlayer != board.getFigure('B', i).getColor())
                    tmp *= -1;
                value += tmp;
            }
            if (!(board.getFigure('C', i) instanceof None)) {
                if(board.getFigure('C', i) instanceof Queen) {
                    tmp = 15;
                }
                else {
                    if (board.getFigure('C', i).getColor())
                        tmp = 3;
                    else
                        tmp = 6;
                }
                if(AIPlayer != board.getFigure('C', i).getColor())
                    tmp *= -1;
                value += tmp;
            }
            if (!(board.getFigure('D', i) instanceof None)) {
                if(board.getFigure('D', i) instanceof Queen) {
                    tmp = 15;
                }
                else {
                    if (board.getFigure('D', i).getColor())
                        tmp = 4;
                    else
                        tmp = 5;
                }
                if(AIPlayer != board.getFigure('D', i).getColor())
                    tmp *= -1;
                value += tmp;
            }
            if (!(board.getFigure('E', i) instanceof None)) {
                if(board.getFigure('E', i) instanceof Queen) {
                    tmp = 15;
                }
                else {
                    if (board.getFigure('E', i).getColor())
                        tmp = 5;
                    else
                        tmp = 4;
                }
                if(AIPlayer != board.getFigure('E', i).getColor())
                    tmp *= -1;
                value += tmp;
            }
            if (!(board.getFigure('F', i) instanceof None)) {
                if(board.getFigure('F', i) instanceof Queen) {
                    tmp = 15;
                }
                else {
                    if (board.getFigure('F', i).getColor())
                        tmp = 6;
                    else
                        tmp = 3;
                }
                if(AIPlayer != board.getFigure('F', i).getColor())
                    tmp *= -1;
                value += tmp;
            }
            if (!(board.getFigure('G', i) instanceof None)) {
                if(board.getFigure('G', i) instanceof Queen) {
                    tmp = 15;
                }
                else {
                    if (board.getFigure('G', i).getColor())
                        tmp = 7;
                    else
                        tmp = 2;
                }
                if(AIPlayer != board.getFigure('G', i).getColor())
                    tmp *= -1;
                value += tmp;
            }
            if (!(board.getFigure('H', i) instanceof None)) {
                if(board.getFigure('H', i) instanceof Queen) {
                    tmp = 15;
                }
                else {
                    if (board.getFigure('H', i).getColor())
                        tmp = 8;
                    else
                        tmp = 1;
                }
                if(AIPlayer != board.getFigure('H', i).getColor())
                    tmp *= -1;
                value += tmp;
            }
        }
        return value;
    }

    private void getFigureMovePossibility(char row, int col) {
        int range = 8;
        if(board.getFigure(row,col) instanceof Pawn || rulesSet.isQueenRangeOne())
            range = 3;
        char row2;
        int col2;
        for(int i = 1;i<range;i++) {
            //left-up
            row2 = (char) (((int) row) - i);
            col2 = col - i;
            if(!validateMove(row, col, row2, col2))
                break;
        }
        for(int i = 1;i<range;i++) {
            //right-up
            row2 = (char) (((int) row) - i);
            col2 = col + i;
            if(!validateMove(row, col, row2, col2))
                break;
        }
        for(int i = 1;i<range;i++) {
            //right-down
            row2 = (char) (((int) row) + i);
            col2 = col + i;
            if(!validateMove(row, col, row2, col2))
                break;
        }
        for(int i = 1;i<range;i++) {
            //left-down
            row2 = (char) (((int) row) + i);
            col2 = col - i;
            if(!validateMove(row, col, row2, col2))
                break;
        }
    }

    private boolean validateMove(char row, int col, char row2, int col2){
        Move move;
        try {
            move = new Move(row, col, row2, col2);
        }
        catch (IncorrectMoveFormat e) {
            return false;
        }
        try {
            MoveValidator.validateMove(move, board, board.getFigure(row, col).getColor(), rulesSet);
            possibleMoves.put(move, 0);
        } catch (IncorrectMoveException e) {}
        catch (CaptureException e) {
            possibleMoves.put(move, 1);
        }
        return true;
    }

    public String[] getAIMove(){
        if(possibleMoves.isEmpty())
            throw new UnknownException();
        int max = -100000;
        for(Map.Entry e : possibleMoves.entrySet())
            if(((int) e.getValue()) > max)
                max = (int) e.getValue();
        List<Move> moves = new ArrayList<>();
        for(Map.Entry e : possibleMoves.entrySet())
            if(((int) e.getValue()) == max)
                moves.add((Move) e.getKey());
        Random r = new Random();
        Move bestMove = moves.get(r.nextInt(moves.size()));
        String[] s = new String[4];
        s[0] = "" + bestMove.getRow1();
        s[1] = "" + bestMove.getCol1();
        s[2] = "" + bestMove.getRow2();
        s[3] = "" + bestMove.getCol2();
        return s;
    }

}