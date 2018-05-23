package checkers.board;

import checkers.figures.Figure;

import java.util.HashMap;

public class Board {

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

	public void printSimple(){
        System.out.print("     1    2    3    4    5    6    7    8");
        System.out.print("\n  +----+----+----+----+----+----+----+----+");
        System.out.print(rows.get('A').printRowSimple('A'));
        System.out.print(rows.get('B').printRowSimple('B'));
        System.out.print(rows.get('C').printRowSimple('C'));
        System.out.print(rows.get('D').printRowSimple('D'));
        System.out.print(rows.get('E').printRowSimple('E'));
        System.out.print(rows.get('F').printRowSimple('F'));
        System.out.print(rows.get('G').printRowSimple('G'));
        System.out.print(rows.get('H').printRowSimple('H'));
        System.out.println("\n    1    2    3    4    5    6    7    8\n");
    }

}