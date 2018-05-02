package checkers;

public class Checkers {

	public static void main(String[] args) {

		Board board = new Board();

		System.out.println(board);
		System.out.println();

		board.setFigure('A', 1, new Pawn(true));
		board.setFigure('B', 6, new Pawn(false));
		board.setFigure('C', 3, new Queen(true));
		board.setFigure('D', 7, new Queen(false));
		board.setFigure('E', 2, new Pawn(true));
		board.setFigure('F', 8, new Pawn(false));
		board.setFigure('G', 5, new Queen(true));
		board.setFigure('H', 4, new Queen(false));

		System.out.println(board);

		char x1 = 'G';
		int y1 = 5;
		char x2 = 'F';
		int y2 = 6;
		Move move = new Move(x1, y1, x2, y2);
		System.out.println(" " + x1 + y1 + "-" + x2 + y2);
		if (MoveValidator.validateMove(move, board))
			System.out.println("Ruch mozliwy");
		else
			System.out.println("Ruch niemozliwy");

	}

}