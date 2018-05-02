package checkers;

class MoveValidator {

	public static boolean validateMove(Move move, Board board) {
		return validateRange(move) && validateBias(move) && validateField1(move, board) && validateField2(move, board);
	}

	private static boolean validateField1(Move move, Board board) {
		return !(board.getFigure(move.getRow1(), move.getCol1()) instanceof None);
	}

	private static boolean validateField2(Move move, Board board) {
		return (board.getFigure(move.getRow2(), move.getCol2()) instanceof None);
	}

	private static boolean validateBias(Move move) {
		int x1 = move.getRow1int();
		int y1 = move.getCol1();
		int x2 = move.getRow2int();
		int y2 = move.getCol2();
		return (Math.abs(x1 - x2) == Math.abs(y1 - y2));
	}

	private static boolean validateRange(Move move) {
		int x1 = move.getRow1int();
		int y1 = move.getCol1();
		int x2 = move.getRow2int();
		int y2 = move.getCol2();
		return (Math.abs(x1 - x2) == 1) && (Math.abs(y1 - y2) == 1);
	}

}