package checkers;

import exceptions.*;

import java.util.LinkedList;
import java.util.Scanner;

public class Game {

	private Board board;
	private LinkedList<Move> moves;
	private boolean player;

	public Game() {
		board = new Board();
		moves = new LinkedList<>();
		player = false;

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

		boolean b;
		do
			b = this.waitForMove();
		while (b);
	}

	private boolean waitForMove() {
		Scanner sc = new Scanner(System.in);
		String s;
		Menu.cls();
		System.out.println(board);
		System.out.print("Player: ");
		if (player)
			System.out.print("black");
		else
			System.out.print("white");
		System.out.println(". Enter your next move, or \"h\" for move history: ");
		try {
            CapturePossibilityValidator.validateCapturePossibility(board,player);
        }
        catch(CapturePossibleException e){
            System.out.println(e.getMessage());
        }
		s = sc.nextLine();
		switch (s) {
		case "h":
			this.printMoveHistory();
			Menu.waitForEnter();
			return true;
		case "x":
			return false;
		default:
			break;
		}
		if (this.validate(s)) {
			this.makeMove(s);
		} else
			System.out.println("Incorrect option!");
		return true;
	}

	private void printMoveHistory() {
		for (Move m : moves)
			System.out.println(m);
	}

	private boolean validate(String s) {
		String[] sArray = s.split("-");
		if (sArray.length != 2)
			return false;
		for (String t : sArray)
			if (t.length() != 2)
				return false;
		return true;
	}

	private void makeMove(String s) {
		String[] sArray = s.split("-");
		char x1 = sArray[0].charAt(0);
		int y1 = Character.getNumericValue(sArray[0].charAt(1));
		char x2 = sArray[1].charAt(0);
		int y2 = Character.getNumericValue(sArray[1].charAt(1));
		System.out.println("Trying to make move: " + x1 + y1 + " to " + x2 + y2 + ".");
		Move move = new Move(x1, y1, x2, y2);
		try {
            MoveValidator.validateMove(move, this.board, this.player);
                moves.add(move);
                move.makeMove(board);
                this.player = !this.player;
            System.out.println("Move done.");
        }catch (CaptureException e){
		    moves.add(move);
		    move.makeCapture(board);
		    this.player = !this.player;
            System.out.println(e.getMessage());
        }catch (IncorrectMoveException e){
            System.out.println("Incorrect move: " + e.getMessage());
        }finally{
            Menu.waitForEnter();
        }
	}

}