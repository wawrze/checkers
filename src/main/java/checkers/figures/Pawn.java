package checkers.figures;

import java.io.Serializable;

public class Pawn extends Figure implements Serializable {

    public Pawn(boolean color) {
        this.color = color;
    }

    @Override
    public String toString() {
        if (this.color)
            return "PP";
        else
            return "pp";
    }

    @Override
    public String print(int n) {
        String temp = "";
        switch (n) {
            case 0:
                temp = "┌───┐";
                break;
            case 1:
                if (this.color)
                    temp = "│   │";
                else
                    temp = "│ █ │";
                break;
            case 2:
                temp = "└───┘";
                break;
        }
        return temp;
    }

}