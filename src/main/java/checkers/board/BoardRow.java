package checkers.board;

import checkers.figures.*;
import checkers.gameplay.InGameUI;
import exceptions.UnknownException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class BoardRow implements Serializable {

	private ArrayList<Figure> figures;

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

	public String printRowSimple(char c, int line, List<String> moves) {
		String row = "\n";
		String tmp = "";
		switch (c) {
		case 'A':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 1)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("A |" + tmp + InGameUI.sideMenuSimple(line, moves));
			row += ("\n  |" + tmp + " A" + InGameUI.sideMenuSimple(line + 1, moves));
			break;
		case 'B':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 0)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("B |" + tmp  + InGameUI.sideMenuSimple(line, moves));
			row += ("\n  |" + tmp + " B"  + InGameUI.sideMenuSimple(line + 1, moves));
			break;
		case 'C':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 1)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("C |" + tmp + InGameUI.sideMenuSimple(line, moves));
			row += ("\n  |" + tmp + " C" + InGameUI.sideMenuSimple(line + 1, moves));
			break;
		case 'D':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 0)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("D |" + tmp + InGameUI.sideMenuSimple(line, moves));
			row += ("\n  |" + tmp + " D" + InGameUI.sideMenuSimple(line + 1, moves));
			break;
		case 'E':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 1)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("E |" + tmp + InGameUI.sideMenuSimple(line, moves));
			row += ("\n  |" + tmp + " E" + InGameUI.sideMenuSimple(line + 1, moves));
			break;
		case 'F':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 0)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("F |" + tmp + InGameUI.sideMenuSimple(line, moves));
			row += ("\n  |" + tmp + " F" + InGameUI.sideMenuSimple(line + 1, moves));
			break;
		case 'G':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 1)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("G |" + tmp + InGameUI.sideMenuSimple(line, moves));
			row += ("\n  |" + tmp + " G" + InGameUI.sideMenuSimple(line + 1, moves));
			break;
		case 'H':
			for (int i = 1; i < 9; i++)
				if ((i % 2) == 0)
					tmp += " " + this.figures.get(i) + " |";
				else
					tmp += "#" + this.figures.get(i) + "#|";
			row += ("H |" + tmp + InGameUI.sideMenuSimple(line, moves));
			row += ("\n  |" + tmp + " H" + InGameUI.sideMenuSimple(line + 1, moves));
			break;
		default:
			System.out.println("Some error!");
			break;
		}
		row += ("\n  +----+----+----+----+----+----+----+----+"  + InGameUI.sideMenuSimple(line + 2, moves));
		return row;
	}

    public String printRow(char c, int line, List<String> moves, boolean player) {
        String row = "";
        String tmp = "";
        if((c == 'A') || (c == 'C') || (c == 'E') || (c == 'G')){
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 1)
                    tmp += "█" + this.figures.get(i).print(0) + "█";
                else
                    tmp += " " + this.figures.get(i).print(0) + " ";
            row += ("\n ║" + tmp + "║" + InGameUI.sideMenu(line, moves, player));
            tmp = "";
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 1)
                    tmp += "█" + this.figures.get(i).print(1) + "█";
                else
                    tmp += " " + this.figures.get(i).print(1) + " ";
            row += ("\n" + c + "║" + tmp + "║" + c + InGameUI.sideMenu(line + 1, moves, player));
            tmp = "";
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 1)
                    tmp += "█" + this.figures.get(i).print(2) + "█";
                else
                    tmp += " " + this.figures.get(i).print(2) + " ";
            row += ("\n ║" + tmp + "║" + InGameUI.sideMenu(line + 2, moves, player));
        }else if((c == 'B') || (c == 'D') || (c == 'F') || (c == 'H')) {
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 0)
                    tmp += "█" + this.figures.get(i).print(0) + "█";
                else
                    tmp += " " + this.figures.get(i).print(0) + " ";
            row += ("\n ║" + tmp + "║" + InGameUI.sideMenu(line, moves, player));
            tmp = "";
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 0)
                    tmp += "█" + this.figures.get(i).print(1) + "█";
                else
                    tmp += " " + this.figures.get(i).print(1) + " ";
            row += ("\n" + c + "║" + tmp + "║" + c + InGameUI.sideMenu(line + 1, moves, player));
            tmp = "";
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 0)
                    tmp += "█" + this.figures.get(i).print(2) + "█";
                else
                    tmp += " " + this.figures.get(i).print(2) + " ";
            row += ("\n ║" + tmp + "║" + InGameUI.sideMenu(line + 2, moves, player));
        }else
            throw new UnknownException();
        return row;
    }

}