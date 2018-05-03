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
	public String print(int n) {
	    String temp = "";
	    switch(n){
            case 0:
                temp = "╔═══╗";
                break;
            case 1:
                if(this.color)
                    temp = "║   ║";
                else
                    temp = "║ █ ║";
                break;
            case 2:
                temp = "╚═══╝";
                break;
            default:
                break;                  //MIEJSCE NA WYJATEK
        }
        return temp;
	}

}