package checkers.figures;

import java.io.Serializable;

public abstract class Figure implements Serializable {

	//true - black, false - white
	protected boolean color;

	public boolean getColor() {
		return this.color;
	}

    public String print(int n){return "";}

}