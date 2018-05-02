package checkers;

class Move {

	private char row1;
	private char row2;
	private int col1;
	private int col2;

	public Move(char row1, int col1, char row2, int col2) {
		if (row1 == 'A' || row1 == 'B' || row1 == 'C' || row1 == 'D' || row1 == 'E' || row1 == 'F' || row1 == 'G'
				|| row1 == 'H')
			this.row1 = row1;
		else
			return;
		if (col1 <= 8 && col1 >= 1)
			this.col1 = col1;
		else
			return;
		if (row2 == 'A' || row2 == 'B' || row2 == 'C' || row2 == 'D' || row2 == 'E' || row2 == 'F' || row2 == 'G'
				|| row2 == 'H')
			this.row2 = row2;
		else
			return;
		if (col2 <= 8 && col2 >= 1)
			this.col2 = col2;
		else
			return;
	}

	public char getRow1() {
		return this.row1;
	}
	
	public int getRow1int() {
		switch (this.row1) {
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

	public int getCol1() {
		return this.col1;
	}

	public char getRow2() {
		return this.row2;
	}
	
	public int getRow2int() {
		switch (this.row2) {
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

	public int getCol2() {
		return this.col2;
	}

}