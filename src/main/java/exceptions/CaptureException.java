package exceptions;

public class CaptureException extends Exception {

    private final char row;
    private final int col;

    public CaptureException(char row, int col) {
        this.row = row;
        this.col = col;
    }

    public char getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
