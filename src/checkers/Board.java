package checkers;

import java.util.HashMap;

class Board {

	private HashMap<Character, BoardRow> rows;

	public Board() {
		this.rows = new HashMap<Character, BoardRow>();
		rows.put('A', new BoardRow());
		rows.put('B', new BoardRow());
		rows.put('C', new BoardRow());
		rows.put('D', new BoardRow());
		rows.put('E', new BoardRow());
		rows.put('F', new BoardRow());
		rows.put('G', new BoardRow());
		rows.put('H', new BoardRow());
	}

	public Figure getFigure(char row, int col) {
		return this.rows.get(row).getFigure(col);
	}

	public void setFigure(char row, int col, Figure figure) {
		this.rows.get(row).setFigure(col, figure);
	}

	@Override
	public String toString() {
		String board = "";
		board += "     1    2    3    4    5    6    7    8";
		board += "\n  +----+----+----+----+----+----+----+----+";
		board += rows.get('A').printRow('A');
		board += rows.get('B').printRow('B');
		board += rows.get('C').printRow('C');
		board += rows.get('D').printRow('D');
		board += rows.get('E').printRow('E');
		board += rows.get('F').printRow('F');
		board += rows.get('G').printRow('G');
		board += rows.get('H').printRow('H');
		board += "\n    1    2    3    4    5    6    7    8";
		return board;
	}

}