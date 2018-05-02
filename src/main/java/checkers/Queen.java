package checkers;

class Queen extends Figure {

	public Queen(boolean color) {
		this.color = color;
	}

	@Override
	public boolean getColor() {
		return this.color;
	}

	@Override
	public String toString() {
		if(this.color == true)
			return "QQ";
		else
			return "qq";
	}

}