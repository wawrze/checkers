package checkers;

import exceptions.*;

class MoveValidator {

	public static boolean validateMove(Move move, Board board, boolean player) throws CaptureException{
	    boolean validate;
	    validate = validateBias(move) && validateField1(move, board) && validateField2(move, board) && validatePlayer(move, board, player);
	    if(board.getFigure(move.getRow1(),move.getCol1()) instanceof Pawn)
	        validate = validate && validatePawnMove(move,board,player);
	    else if(board.getFigure(move.getRow1(),move.getCol1()) instanceof Queen)
            validate = validate && validateQueenMove(move,board,player);
		return validate;
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

	private static boolean validatePlayer(Move move, Board board, boolean player) {
		int x1 = move.getRow1int();
		int y1 = move.getCol1();
		if(board.getFigure(move.getRow1(), move.getCol1()).getColor() == player)
			return true;
		else
			return false;
	}

    private static boolean validatePawnMove(Move move, Board board, boolean player) throws CaptureException{
        return validateDirection(move,player) && validateRange(move, board);
    }

    private static boolean validateRange(Move move, Board board) throws CaptureException{
        int x1 = move.getRow1int();
        int y1 = move.getCol1();
        int x2 = move.getRow2int();
        int y2 = move.getCol2();
        if ((Math.abs(x1 - x2) == 1) && (Math.abs(y1 - y2) == 1))
            return true;
        else if((Math.abs(x1 - x2) == 2) && (Math.abs(y1 - y2) == 2)) {
            char x = (char) (((x1 + x2) / 2) + 64);
            int y = ((y1 + y2) / 2);
            if(board.getFigure(x,y).getColor() != board.getFigure(move.getRow1(),move.getCol1()).getColor())
                throw new CaptureException();
            else
                return false;
        }
        else
            return false;
    }

    private static boolean validateDirection(Move move, boolean player){
	    if(player)
	        if((move.getRow2int() - move.getRow1int()) > 0)
                return true;
            else
                return false;
	    else
            if((move.getRow2int() - move.getRow1int()) < 0)
                return true;
            else
                return false;
    }

    private static boolean validateQueenMove(Move move, Board board, boolean player) {
        return true;
    }

}