package checkers.board;

import checkers.figures.Figure;

import java.io.Serializable;
import java.util.HashMap;

public class Board implements Serializable {

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

	public Board(Board board){
        this.rows = new HashMap<Character, BoardRow>();
        rows.put('A', new BoardRow(true));
        for(int i = 1;i<9;i++)
            rows.get('A').setFigure(i,board.getFigure('A',i));
        rows.put('B', new BoardRow(false));
        for(int i = 1;i<9;i++)
            rows.get('B').setFigure(i,board.getFigure('B',i));
        rows.put('C', new BoardRow(true));
        for(int i = 1;i<9;i++)
            rows.get('C').setFigure(i,board.getFigure('C',i));
        rows.put('D', new BoardRow(false));
        for(int i = 1;i<9;i++)
            rows.get('D').setFigure(i,board.getFigure('D',i));
        rows.put('E', new BoardRow(true));
        for(int i = 1;i<9;i++)
            rows.get('E').setFigure(i,board.getFigure('E',i));
        rows.put('F', new BoardRow(false));
        for(int i = 1;i<9;i++)
            rows.get('F').setFigure(i,board.getFigure('F',i));
        rows.put('G', new BoardRow(true));
        for(int i = 1;i<9;i++)
            rows.get('G').setFigure(i,board.getFigure('G',i));
        rows.put('H', new BoardRow(false));
        for(int i = 1;i<9;i++)
            rows.get('H').setFigure(i,board.getFigure('H',i));
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