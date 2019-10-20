package checkers.board;

import checkers.figures.Figure;
import checkers.figures.None;
import checkers.gameplay.InGameUI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class BoardRow implements Serializable {

    private final ArrayList<Figure> figures;

    public BoardRow(boolean startColor) {
        this.figures = new ArrayList<>();
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

    String printRowSimple(char c, int line, List<String> moves, InGameUI inGameUI) {
        String row = "\n";
        StringBuilder tmp = new StringBuilder();
        switch (c) {
            case 'A':
                for (int i = 1; i < 9; i++)
                    if ((i % 2) == 1)
                        tmp.append(" ").append(this.figures.get(i)).append(" |");
                    else
                        tmp.append("#").append(this.figures.get(i)).append("#|");
                row += ("A |" + tmp + inGameUI.sideMenuSimple(line, moves));
                row += ("\n  |" + tmp + " A" + inGameUI.sideMenuSimple(line + 1, moves));
                break;
            case 'B':
                for (int i = 1; i < 9; i++)
                    if ((i % 2) == 0)
                        tmp.append(" ").append(this.figures.get(i)).append(" |");
                    else
                        tmp.append("#").append(this.figures.get(i)).append("#|");
                row += ("B |" + tmp + inGameUI.sideMenuSimple(line, moves));
                row += ("\n  |" + tmp + " B" + inGameUI.sideMenuSimple(line + 1, moves));
                break;
            case 'C':
                for (int i = 1; i < 9; i++)
                    if ((i % 2) == 1)
                        tmp.append(" ").append(this.figures.get(i)).append(" |");
                    else
                        tmp.append("#").append(this.figures.get(i)).append("#|");
                row += ("C |" + tmp + inGameUI.sideMenuSimple(line, moves));
                row += ("\n  |" + tmp + " C" + inGameUI.sideMenuSimple(line + 1, moves));
                break;
            case 'D':
                for (int i = 1; i < 9; i++)
                    if ((i % 2) == 0)
                        tmp.append(" ").append(this.figures.get(i)).append(" |");
                    else
                        tmp.append("#").append(this.figures.get(i)).append("#|");
                row += ("D |" + tmp + inGameUI.sideMenuSimple(line, moves));
                row += ("\n  |" + tmp + " D" + inGameUI.sideMenuSimple(line + 1, moves));
                break;
            case 'E':
                for (int i = 1; i < 9; i++)
                    if ((i % 2) == 1)
                        tmp.append(" ").append(this.figures.get(i)).append(" |");
                    else
                        tmp.append("#").append(this.figures.get(i)).append("#|");
                row += ("E |" + tmp + inGameUI.sideMenuSimple(line, moves));
                row += ("\n  |" + tmp + " E" + inGameUI.sideMenuSimple(line + 1, moves));
                break;
            case 'F':
                for (int i = 1; i < 9; i++)
                    if ((i % 2) == 0)
                        tmp.append(" ").append(this.figures.get(i)).append(" |");
                    else
                        tmp.append("#").append(this.figures.get(i)).append("#|");
                row += ("F |" + tmp + inGameUI.sideMenuSimple(line, moves));
                row += ("\n  |" + tmp + " F" + inGameUI.sideMenuSimple(line + 1, moves));
                break;
            case 'G':
                for (int i = 1; i < 9; i++)
                    if ((i % 2) == 1)
                        tmp.append(" ").append(this.figures.get(i)).append(" |");
                    else
                        tmp.append("#").append(this.figures.get(i)).append("#|");
                row += ("G |" + tmp + inGameUI.sideMenuSimple(line, moves));
                row += ("\n  |" + tmp + " G" + inGameUI.sideMenuSimple(line + 1, moves));
                break;
            case 'H':
                for (int i = 1; i < 9; i++)
                    if ((i % 2) == 0)
                        tmp.append(" ").append(this.figures.get(i)).append(" |");
                    else
                        tmp.append("#").append(this.figures.get(i)).append("#|");
                row += ("H |" + tmp + inGameUI.sideMenuSimple(line, moves));
                row += ("\n  |" + tmp + " H" + inGameUI.sideMenuSimple(line + 1, moves));
                break;
        }
        row += ("\n  +----+----+----+----+----+----+----+----+" + inGameUI.sideMenuSimple(line + 2, moves));
        return row;
    }

    String printRow(char c, int line, List<String> moves, boolean player, InGameUI inGameUI) {
        String row = "";
        StringBuilder tmp = new StringBuilder();
        if ((c == 'A') || (c == 'C') || (c == 'E') || (c == 'G')) {
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 1)
                    tmp.append("█").append(this.figures.get(i).print(0)).append("█");
                else
                    tmp.append(" ").append(this.figures.get(i).print(0)).append(" ");
            row += ("\n ║" + tmp + "║" + inGameUI.sideMenu(line, moves, player));
            tmp = new StringBuilder();
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 1)
                    tmp.append("█").append(this.figures.get(i).print(1)).append("█");
                else
                    tmp.append(" ").append(this.figures.get(i).print(1)).append(" ");
            row += ("\n" + c + "║" + tmp + "║" + c + inGameUI.sideMenu(line + 1, moves, player));
            tmp = new StringBuilder();
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 1)
                    tmp.append("█").append(this.figures.get(i).print(2)).append("█");
                else
                    tmp.append(" ").append(this.figures.get(i).print(2)).append(" ");
            row += ("\n ║" + tmp + "║" + inGameUI.sideMenu(line + 2, moves, player));
        } else if ((c == 'B') || (c == 'D') || (c == 'F') || (c == 'H')) {
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 0)
                    tmp.append("█").append(this.figures.get(i).print(0)).append("█");
                else
                    tmp.append(" ").append(this.figures.get(i).print(0)).append(" ");
            row += ("\n ║" + tmp + "║" + inGameUI.sideMenu(line, moves, player));
            tmp = new StringBuilder();
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 0)
                    tmp.append("█").append(this.figures.get(i).print(1)).append("█");
                else
                    tmp.append(" ").append(this.figures.get(i).print(1)).append(" ");
            row += ("\n" + c + "║" + tmp + "║" + c + inGameUI.sideMenu(line + 1, moves, player));
            tmp = new StringBuilder();
            for (int i = 1; i < 9; i++)
                if ((i % 2) == 0)
                    tmp.append("█").append(this.figures.get(i).print(2)).append("█");
                else
                    tmp.append(" ").append(this.figures.get(i).print(2)).append(" ");
            row += ("\n ║" + tmp + "║" + inGameUI.sideMenu(line + 2, moves, player));
        }
        return row;
    }

}