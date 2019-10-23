package checkers.board;

import checkers.figures.Figure;
import checkers.figures.None;

import java.io.Serializable;
import java.util.ArrayList;

class BoardRow implements Serializable {

    private final ArrayList<Figure> figures;

    BoardRow(boolean startColor) {
        this.figures = new ArrayList<>();
        //columns numeration 1-8, setting column 0 to null
        this.figures.add(0, null);
        for (int i = 1; i < 9; i++) {
            this.figures.add(i, new None(startColor));
            startColor = !startColor;
        }
    }

    Figure getFigure(int col) {
        return figures.get(col);
    }

    void setFigure(int col, Figure figure) {
        figures.set(col, figure);
    }

}