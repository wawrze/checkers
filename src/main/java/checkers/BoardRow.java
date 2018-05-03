package checkers;

import java.util.ArrayList;

class BoardRow {

	ArrayList<Figure> figures;

	public BoardRow(boolean startColor) {
		this.figures = new ArrayList<Figure>();
		//columns numeration 1-8, setting column 0 to null
		this.figures.add(0, null);
		for (int i = 1; i < 9; i++) {
			this.figures.add(i, new None(startColor));
			startColor = !startColor;
		}
	}

	public Figure getFigure(int col) {
		return figures.get(col);
	}

	public void setFigure(int col, Figure figure) {
		figures.set(col, figure);
	}

	public String printRow(char c) {
		String row = "\n ║";
		String tmp = "";
		switch (c) {
		case 'A':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 1)
					tmp += "█" + this.figures.get(i) + "█";
				else
					tmp += " " + this.figures.get(i) + " ";
			row += (tmp + "║");
			row += ("\nA║" + tmp + "║A");
			break;
		case 'B':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 0)
                    tmp += "█" + this.figures.get(i) + "█";
				else
                    tmp += " " + this.figures.get(i) + " ";
            row += (tmp + "║");
            row += ("\nB║" + tmp + "║B");
			break;
		case 'C':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 1)
                    tmp += "█" + this.figures.get(i) + "█";
				else
                    tmp += " " + this.figures.get(i) + " ";
            row += (tmp + "║");
            row += ("\nC║" + tmp + "║C");
			break;
		case 'D':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 0)
                    tmp += "█" + this.figures.get(i) + "█";
				else
                    tmp += " " + this.figures.get(i) + " ";
            row += (tmp + "║");
			row += ("\nD║" + tmp + "║D");
			break;
		case 'E':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 1)
                    tmp += "█" + this.figures.get(i) + "█";
				else
                    tmp += " " + this.figures.get(i) + " ";
            row += (tmp + "║");
			row += ("\nE║" + tmp + "║E");
			break;
		case 'F':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 0)
                    tmp += "█" + this.figures.get(i) + "█";
				else
                    tmp += " " + this.figures.get(i) + " ";
            row += (tmp + "║");
			row += ("\nF║" + tmp + "║F");
			break;
		case 'G':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 1)
                    tmp += "█" + this.figures.get(i) + "█";
				else
                    tmp += " " + this.figures.get(i) + " ";
            row += (tmp + "║");
			row += ("\nG║" + tmp + "║G");
			break;
		case 'H':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 0)
                    tmp += "█" + this.figures.get(i) + "█";
				else
                    tmp += " " + this.figures.get(i) + " ";
            row += (tmp + "║");
			row += ("\nH║" + tmp + "║H");
			break;
		default:
			System.out.println("Some error!");                          //MIEJSCE NA WYJĄTEK
			break;
		}
        row += ("\n ║" + tmp + "║");
		return row;
	}

}