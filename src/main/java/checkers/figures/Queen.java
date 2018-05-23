package checkers.figures;

public class Queen extends Figure {

	public Queen(boolean color) {
		this.color = color;
	}

	@Override
	public String toString() {
		if(this.color == true)
			return "QQ";
		else
			return "qq";
	}

}