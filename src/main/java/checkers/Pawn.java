package checkers;

class Pawn extends Figure {
	
	public Pawn(boolean color) {
		this.color = color;
	}

	@Override
	public boolean getColor() {
		return this.color;
	}

	@Override
	public String toString() {
		if(this.color == true)
			return "PPPPP";
		else
			return "ppppp";
	}

}