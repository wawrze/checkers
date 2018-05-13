package checkers.moves;

import checkers.board.*;
import checkers.figures.*;
import exceptions.*;

public class MoveValidator {

	public static void validateMove(Move move, Board board, boolean player) throws CaptureException, IncorrectMoveException{
	    validateBias(move);
	    validateField1(move, board);
	    validateField2(move, board);
	    validatePlayer(move, board, player);
	    if(board.getFigure(move.getRow1(),move.getCol1()) instanceof Pawn)
            validatePawnMove(move, board, player);
	    else if(board.getFigure(move.getRow1(),move.getCol1()) instanceof Queen)
            validateQueenMove(move,board,player);
	}

	private static void validateField1(Move move, Board board) throws IncorrectMoveException{
	    if(board.getFigure(move.getRow1(), move.getCol1()) instanceof None)
	        throw new IncorrectMoveException("No figure to move!");
	}

	private static void validateField2(Move move, Board board) throws IncorrectMoveException{
		if(!(board.getFigure(move.getRow2(), move.getCol2()) instanceof None))
		    throw new IncorrectMoveException("A figure on field you want move to!");
	}

	private static void validateBias(Move move) throws IncorrectMoveException{
		int x1 = move.getRow1int();
		int y1 = move.getCol1();
		int x2 = move.getRow2int();
		int y2 = move.getCol2();
		if(!(Math.abs(x1 - x2) == Math.abs(y1 - y2)))
		    throw new IncorrectMoveException("Fields are not in bias position!");
	}

	private static void validatePlayer(Move move, Board board, boolean player) throws IncorrectMoveException{
		int x1 = move.getRow1int();
		int y1 = move.getCol1();
		if(!(board.getFigure(move.getRow1(), move.getCol1()).getColor() == player))
			throw new IncorrectMoveException("Not your figure!");
	}

    private static void validatePawnMove(Move move, Board board, boolean player) throws CaptureException, IncorrectMoveException{
        validateDirection(move, player);
        validateRange(move,board);
    }

    private static void validateRange(Move move, Board board) throws CaptureException, IncorrectMoveException{
        int x1 = move.getRow1int();
        int y1 = move.getCol1();
        int x2 = move.getRow2int();
        int y2 = move.getCol2();
        if((Math.abs(x1 - x2) == 2) && (Math.abs(y1 - y2) == 2)) {
            char x = (char) (((x1 + x2) / 2) + 64);
            int y = ((y1 + y2) / 2);
            if(!(board.getFigure(x,y) instanceof None) && board.getFigure(x,y).getColor() != board.getFigure(move.getRow1(),move.getCol1()).getColor())
                throw new CaptureException("Capture done.");
            else
                throw new IncorrectMoveException("Invalid range!");
        }
        else if ((Math.abs(x1 - x2) != 1) || (Math.abs(y1 - y2) != 1))
            throw new IncorrectMoveException("Invalid range!");
    }

    private static void validateDirection(Move move, boolean player) throws IncorrectMoveException{
	    if(player) {
            if ((move.getRow2int() - move.getRow1int()) < 0)
                throw new IncorrectMoveException("Invalid direction!");
        }
        else {
            if ((move.getRow2int() - move.getRow1int()) > 0)
                throw new IncorrectMoveException("Invalid direction!");
        }
    }

    private static void validateQueenMove(Move move, Board board, boolean player) throws IncorrectMoveException, CaptureException {
        validateOnWay(move,board);
        //to validate: is it capture
    }

    private static void validateOnWay(Move move, Board board)throws IncorrectMoveException{
        int x1 = move.getRow1int();
        int y1 = move.getCol1();
        int x2 = move.getRow2int();
        int y2 = move.getCol2();
        //left-up
        if(x1 > x2 && y1 > y2)
            for(int i = 1;i < (x1 - x2);i++)
                if (!(board.getFigure((char) (64 + x1 - i), y1 - i) instanceof None))
                    throw new IncorrectMoveException("Some figure on the way!");
        //right-up
        if(x1 > x2 && y1 < y2)
            for(int i = 1;i < (x1 - x2);i++)
                if (!(board.getFigure((char) (64 + x1 - i), y1 + i) instanceof None))
                    throw new IncorrectMoveException("Some figure on the way!");
        //left-down
        if(x1 < x2 && y1 > y2)
            for(int i = 1;i < (x2 - x1);i++)
                if (!(board.getFigure((char) (64 + x1 + i), y1 - i) instanceof None))
                    throw new IncorrectMoveException("Some figure on the way!");
        //right-down
        if(x1 < x2 && y1 < y2)
            for(int i = 1;i < (x2 - x1);i++)
                if (!(board.getFigure((char) (64 + x1 + i), y1 + i) instanceof None))
                    throw new IncorrectMoveException("Some figure on the way!");
    }

}