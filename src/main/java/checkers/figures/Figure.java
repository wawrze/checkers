package checkers.figures;

import java.io.Serializable;

public abstract class Figure implements Serializable {

    //true - black, false - white
    boolean color;

    public boolean getColor() {
        return this.color;
    }

}