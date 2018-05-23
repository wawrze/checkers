package checkers.figures;

public class Pawn extends Figure {
	
	public Pawn(boolean color) {
		this.color = color;
	}

	@Override
	public String toString() {
		if(this.color == true)
			return "PP";
		else
			return "pp";
	}

}