package checkers;

import checkers.board.*;
import checkers.figures.*;
import checkers.moves.*;
import exceptions.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private Board board;
    private List<String> moves;
    private boolean player;
    private boolean simplePrint;

    public Game() {
        board = new Board();
        moves = new LinkedList<>();
        player = false;
        simplePrint = false;

        board.setFigure('A', 2, new Queen(true));
		/*board.setFigure('A', 2, new Pawn(true));
		board.setFigure('A', 4, new Pawn(true));
		board.setFigure('A', 6, new Pawn(true));
		board.setFigure('A', 8, new Pawn(true));
		board.setFigure('B', 1, new Pawn(true));
		board.setFigure('B', 3, new Pawn(true));
		board.setFigure('B', 5, new Pawn(true));
		board.setFigure('B', 7, new Pawn(true));
		board.setFigure('C', 2, new Pawn(true));*/
        board.setFigure('C', 4, new Pawn(true));
        board.setFigure('C', 6, new Pawn(true));
		/*board.setFigure('C', 8, new Pawn(true));

		board.setFigure('F', 1, new Pawn(false));
		board.setFigure('F', 3, new Pawn(false));*/
        board.setFigure('F', 5, new Pawn(false));
        board.setFigure('F', 7, new Pawn(false));
        board.setFigure('G', 2, new Pawn(false));
        board.setFigure('G', 4, new Pawn(false));
		/*board.setFigure('G', 6, new Pawn(false));
		board.setFigure('G', 8, new Pawn(false));
		board.setFigure('H', 1, new Pawn(false));
		board.setFigure('H', 3, new Pawn(false));
		board.setFigure('H', 5, new Pawn(false));
		board.setFigure('H', 7, new Pawn(false));*/
        board.setFigure('H', 7, new Queen(false));

        boolean b;
        do
            b = this.waitForMove();
        while (b);
    }

    private boolean waitForMove() {
        Scanner sc = new Scanner(System.in);
        String s;
        String captures = "";
        Menu.cls();
        if(this.simplePrint)
            board.printSimple();
        else
            System.out.println(board);
        System.out.print("Player: ");
        if (player)
            System.out.print("BLACK");
        else
            System.out.print("WHITE");
        System.out.println(". Enter your next move, or \"h\" for move history: ");
        try {
            CapturePossibilityValidator.validateCapturePossibility(board,player);
        }
        catch(CapturePossibleException e){
            captures = e.getMessage();
            System.out.println("You have to capture: " + captures);
        }
        s = sc.nextLine();
        if(inGameMenu(s)) {
            if (s.equals("x"))
                return false;
            else
                return true;
        }
        s = s.toUpperCase();
        try{
            validate(s);
            if(captures.isEmpty() || captures.contains(s))
                this.makeMove(s);
            else{
                System.out.println("You have to capture!");
                Menu.waitForEnter();
                return true;
            }
        }
        catch(IncorrectMoveFormat e){
            System.out.println("Incorrect move format! Proper format example: E4-D5");
            Menu.waitForEnter();
            return true;
        }
        return true;
    }

    private void printMoveHistory() {
        if(moves.isEmpty())
            System.out.println("No moves history.");
        else
            for (String m : moves)
                System.out.println(m);
    }

    private void validate(String s) throws IncorrectMoveFormat {
        String[] sArray = s.split("-");
        if (sArray.length != 2)
            throw new IncorrectMoveFormat();
        for (String t : sArray)
            if (t.length() != 2)
                throw new IncorrectMoveFormat();
    }

    private void makeMove(String s) throws IncorrectMoveFormat{
        String[] sArray = s.split("-");
        char x1 = sArray[0].charAt(0);
        int y1 = Character.getNumericValue(sArray[0].charAt(1));
        char x2 = sArray[1].charAt(0);
        int y2 = Character.getNumericValue(sArray[1].charAt(1));
        System.out.println("Trying to make move: " + x1 + y1 + " to " + x2 + y2 + ".");
        Move move = new Move(x1, y1, x2, y2);
        try {
            MoveValidator.validateMove(move, this.board, this.player);
            moves.add((player ? "black: " : "white: ") + move);
            move.makeMove(board);
            this.player = !this.player;
            System.out.println("Move done.");
        }catch (CaptureException e){
            moves.add((player ? "black: " : "white: ") + move);
            move.makeCapture(board,e.getRow(),e.getCol());
            boolean b;
            do
                b = waitForMoveMultiCapture(move);
            while (b);
            this.player = !this.player;
            System.out.println("Capture done.");
        }catch (IncorrectMoveException e){
            System.out.println("Incorrect move: " + e.getMessage());
        }finally{
            Menu.waitForEnter();
        }
    }

    private boolean waitForMoveMultiCapture(Move move) {
        try{
            CapturePossibilityValidator.validateCapturePossibilityForOneFigure(board,move.getRow2(),move.getCol2());
            return false;
        }
        catch(CapturePossibleException e){
            Scanner sc = new Scanner(System.in);
            String s;
            Menu.cls();
            if(simplePrint)
                board.printSimple();
            else
                System.out.println(board);
            System.out.print("Player: ");
            if (player)
                System.out.print("BLACK");
            else
                System.out.print("WHITE");
            System.out.println(". You have to continue capturing, enter your move or \"h\" for move history: ");
            System.out.println("Possible captures: " + e.getMessage());
            s = sc.nextLine();
            if(inGameMenu(s)) {
                if (s.equals("x"))
                    return false;
                else
                    return true;
            }
            s = s.toUpperCase();
            try{
                validate(s);
                if(e.getMessage().contains(s)){
                    String[] sArray = s.split("-");
                    char x1 = sArray[0].charAt(0);
                    int y1 = Character.getNumericValue(sArray[0].charAt(1));
                    char x2 = sArray[1].charAt(0);
                    int y2 = Character.getNumericValue(sArray[1].charAt(1));
                    move = new Move(x1, y1, x2, y2);
                    try{
                        MoveValidator.validateMove(move,this.board,this.player);
                        System.out.println("Incorrect move!");
                        Menu.waitForEnter();
                    }
                    catch(CaptureException e1){
                        moves.add((player ? "black: " : "white: ") + move);
                        move.makeCapture(board,e1.getRow(),e1.getCol());
                    }
                    catch(IncorrectMoveException e1){
                        System.out.println("Incorrect move!");
                        Menu.waitForEnter();
                    }
                }
                else {
                    System.out.println("Incorrect move!");
                    Menu.waitForEnter();
                }
            }
            catch(IncorrectMoveFormat e1){
                System.out.println("Incorrect move format! Proper format example: E4-D5");
                Menu.waitForEnter();
                return true;
            }
            return true;
        }
    }

    private boolean inGameMenu(String s){
        switch (s) {
            case "h":
                this.printMoveHistory();
                Menu.waitForEnter();
                return true;
            case "p":
                this.simplePrint = !this.simplePrint;
                return true;
            case "x":
                return true;
            default:
                break;
        }
        return false;
    }

}