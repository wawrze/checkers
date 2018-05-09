package checkers;

import java.util.HashMap;

class Board {

	private HashMap<Character, BoardRow> rows;

	public Board() {
		this.rows = new HashMap<Character, BoardRow>();
		rows.put('A', new BoardRow(true));
		rows.put('B', new BoardRow(false));
		rows.put('C', new BoardRow(true));
		rows.put('D', new BoardRow(false));
		rows.put('E', new BoardRow(true));
		rows.put('F', new BoardRow(false));
		rows.put('G', new BoardRow(true));
		rows.put('H', new BoardRow(false));
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
		board += "     1      2      3      4      5      6      7      8";
		board += "\n ╔════════════════════════════════════════════════════════╗";
		board += rows.get('A').printRow('A');
		board += rows.get('B').printRow('B');
		board += rows.get('C').printRow('C');
		board += rows.get('D').printRow('D');
		board += rows.get('E').printRow('E');
		board += rows.get('F').printRow('F');
		board += rows.get('G').printRow('G');
		board += rows.get('H').printRow('H');
		board += "\n ╚════════════════════════════════════════════════════════╝";
		board += "\n     1      2      3      4      5      6      7      8";
		return board;
	}

}