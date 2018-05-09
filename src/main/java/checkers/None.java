package checkers;

class None extends Figure {

	public None(boolean color){
		this.color = color;
	}

	@Override
	public String print(int n) {
		if(this.color)
			return "█████";
		else
			return "     ";
	}

}