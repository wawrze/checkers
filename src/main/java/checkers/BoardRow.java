package checkers;

import java.util.ArrayList;

class BoardRow {

	private ArrayList<Figure> figures;

	public BoardRow() {
		this.figures = new ArrayList<Figure>();
		//columns numeration 1-8, setting column 0 to null
		this.figures.add(0, null);
		for (int i = 1; i < 9; i++)
			this.figures.add(i, new None());
	}

	public Figure getFigure(int col) {
		return figures.get(col);
	}

	public void setFigure(int col, Figure figure) {
		figures.set(col, figure);
	}

	public String printRow(char c) {
		String row = "\n";
		String tmp = "";
		switch (c) {
		case 'A':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 1)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("A |" + tmp);
			row += ("\n  |" + tmp + " A");
			break;
		case 'B':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 0)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("B |" + tmp);
			row += ("\n  |" + tmp + " B");
			break;
		case 'C':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 1)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("C |" + tmp);
			row += ("\n  |" + tmp + " C");
			break;
		case 'D':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 0)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("D |" + tmp);
			row += ("\n  |" + tmp + " D");
			break;
		case 'E':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 1)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("E |" + tmp);
			row += ("\n  |" + tmp + " E");
			break;
		case 'F':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 0)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("F |" + tmp);
			row += ("\n  |" + tmp + " F");
			break;
		case 'G':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 1)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("G |" + tmp);
			row += ("\n  |" + tmp + " G");
			break;
		case 'H':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 0)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("H |" + tmp);
			row += ("\n  |" + tmp + " H");
			break;
		default:
			System.out.println("Some error!");
			break;
		}
		row += "\n  +----+----+----+----+----+----+----+----+";
		return row;
	}

}