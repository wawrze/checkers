package checkers;

import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;

class Move {

	private char row1;
	private char row2;
	private int col1;
	private int col2;

	public Move(char row1, int col1, char row2, int col2) throws IncorrectMoveFormat {
		if (row1 == 'A' || row1 == 'B' || row1 == 'C' || row1 == 'D' || row1 == 'E' || row1 == 'F' || row1 == 'G'
				|| row1 == 'H')
			this.row1 = row1;
		else
			throw new IncorrectMoveFormat();
		if (col1 <= 8 && col1 >= 1)
			this.col1 = col1;
		else
            throw new IncorrectMoveFormat();
		if (row2 == 'A' || row2 == 'B' || row2 == 'C' || row2 == 'D' || row2 == 'E' || row2 == 'F' || row2 == 'G'
				|| row2 == 'H')
			this.row2 = row2;
		else
            throw new IncorrectMoveFormat();
		if (col2 <= 8 && col2 >= 1)
			this.col2 = col2;
		else
            throw new IncorrectMoveFormat();
	}

	private int rowCharToInt(char row){
        switch (row) {
            case 'A':
                return 1;
            case 'B':
                return 2;
            case 'C':
                return 3;
            case 'D':
                return 4;
            case 'E':
                return 5;
            case 'F':
                return 6;
            case 'G':
                return 7;
            case 'H':
                return 8;
            default:
                return 0;
        }
    }

	public char getRow1() {
		return this.row1;
	}
	
	public int getRow1int() {
		return this.rowCharToInt(this.row1);
	}

	public int getCol1() {
		return this.col1;
	}

	public char getRow2() {
		return this.row2;
	}
	
	public int getRow2int() {
        return this.rowCharToInt(this.row2);
	}

	public int getCol2() {
		return this.col2;
	}

	public void makeMove(Board board) {
	    if(board.getFigure(this.row1, this.col1).getColor() && (this.row2) == 'H')
            board.setFigure(this.row2, this.col2, new Queen(true));
	    else if(!board.getFigure(this.row1, this.col1).getColor() && (this.row2) == 'A')
            board.setFigure(this.row2, this.col2, new Queen(false));
	    else
		    board.setFigure(this.row2, this.col2, board.getFigure(this.row1, this.col1));
		board.setFigure(this.row1, this.col1, new None());
	}

	public void makeCapture(Board board){
        this.makeMove(board);
        char x = (char) (((this.getRow1int() + this.getRow2int()) / 2) + 64);
        int y = ((this.col1 + this.col2) / 2);
        board.setFigure(x,y,new None());
    }

	@Override
	public String toString() {
		return "" + row1 + col1 + "-" + row2 + col2;
	}
	
}