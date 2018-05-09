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
        String row = ""; //String row = "\n ║";
		String tmp = "";
		if((c == 'A') || (c == 'C') || (c == 'E') || (c == 'G')){
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 1)
                    tmp += "█" + this.figures.get(i).print(0) + "█";
                else
                    tmp += " " + this.figures.get(i).print(0) + " ";
            row += ("\n ║" + tmp + "║");
            tmp = "";
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 1)
                    tmp += "█" + this.figures.get(i).print(1) + "█";
                else
                    tmp += " " + this.figures.get(i).print(1) + " ";
            row += ("\n" + c + "║" + tmp + "║" + c);
            tmp = "";
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 1)
                    tmp += "█" + this.figures.get(i).print(2) + "█";
                else
                    tmp += " " + this.figures.get(i).print(2) + " ";
            row += ("\n ║" + tmp + "║");
        }else if((c == 'B') || (c == 'D') || (c == 'F') || (c == 'H')) {
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 0)
                    tmp += "█" + this.figures.get(i).print(0) + "█";
                else
                    tmp += " " + this.figures.get(i).print(0) + " ";
            row += ("\n ║" + tmp + "║");
            tmp = "";
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 0)
                    tmp += "█" + this.figures.get(i).print(1) + "█";
                else
                    tmp += " " + this.figures.get(i).print(1) + " ";
            row += ("\n" + c + "║" + tmp + "║" + c);
            tmp = "";
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 0)
                    tmp += "█" + this.figures.get(i).print(2) + "█";
                else
                    tmp += " " + this.figures.get(i).print(2) + " ";
            row += ("\n ║" + tmp + "║");
        }else
            ;               // MIEJSCE NA WYJATEK
		return row;
	}

}