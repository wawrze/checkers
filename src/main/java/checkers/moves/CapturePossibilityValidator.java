package checkers.moves;

import checkers.board.*;
import checkers.figures.*;
import exceptions.*;

import java.util.LinkedList;
import java.util.List;

public class CapturePossibilityValidator {

    private static List<String> listOfCaptures;

    public static void validateCapturePossibility(Board board, boolean player) throws CapturePossibleException {
        listOfCaptures = new LinkedList<>();
        for(int i = 65;i<73;i++)
            for (int j = 1; j < 9; j++)
                if (board.getFigure((char) i, j) instanceof Pawn && board.getFigure((char) i, j).getColor() == player)
                    validatePawnCapture((char) i, j, board);
                else if (board.getFigure((char) i, j) instanceof Queen && board.getFigure((char) i, j).getColor() == player)
                    validateQueenCapture((char) i, j, board);
        listCheck();
    }

    public static void validateCapturePossibilityForOneFigure(Board board,char row,int col) throws CapturePossibleException{
        listOfCaptures = new LinkedList<>();
        if(board.getFigure(row,col) instanceof Pawn)
            validatePawnCapture(row,col,board);
        else
            validateQueenCapture(row,col,board);
        listCheck();
    }

    private static void listCheck() throws CapturePossibleException{
        if(!listOfCaptures.isEmpty()) {
            String m = "";
            for (String s : listOfCaptures)
                m += s + " ";
            throw new CapturePossibleException(m);
        }
    }

    private static void validateQueenCapture(char row, int col, Board board){
        char rowCaptureTo;
        int colCaptureTo;
        char rowCaptured;
        int colCaptured;
        for(int i = 2;i<9;i++) {
            //left-up
            rowCaptureTo = (char) (((int) row) - i);
            colCaptureTo = col - i;
            rowCaptured = (char) (((int) row) - i + 1);
            colCaptured = col - i + 1;
            if(isOnBoard((int) rowCaptureTo,colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
                if(validate(row,col,rowCaptureTo,colCaptureTo,rowCaptured,colCaptured,board))
                    listOfCaptures.add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
            //right-up
            rowCaptureTo = (char) (((int) row) - i);
            colCaptureTo = col + i;
            rowCaptured = (char) (((int) row) - i + 1);
            colCaptured = col + i - 1;
            if(isOnBoard((int) rowCaptureTo,colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
                if(validate(row,col,rowCaptureTo,colCaptureTo,rowCaptured,colCaptured,board))
                    listOfCaptures.add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
            //right-down
            rowCaptureTo = (char) (((int) row) + i);
            colCaptureTo = col + i;
            rowCaptured = (char) (((int) row) + i - 1);
            colCaptured = col + i - 1;
            if(isOnBoard((int) rowCaptureTo,colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
                if(validate(row,col,rowCaptureTo,colCaptureTo,rowCaptured,colCaptured,board))
                    listOfCaptures.add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
            //left-down
            rowCaptureTo = (char) (((int) row) + i);
            colCaptureTo = col - i;
            rowCaptured = (char) (((int) row) + i - 1);
            colCaptured = col - i + 1;
            if(isOnBoard((int) rowCaptureTo,colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
                if(validate(row,col,rowCaptureTo,colCaptureTo,rowCaptured,colCaptured,board))
                    listOfCaptures.add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
        }
    }

    private static void validatePawnCapture(char row,int col, Board board){
        char rowCaptureTo;
        int colCaptureTo;
        char rowCaptured;
        int colCaptured;
        //left-up
        rowCaptureTo = (char) (((int) row) - 2);
        colCaptureTo = col - 2;
        rowCaptured = (char) (((int) row) - 1);
        colCaptured = col - 1;
        if(isOnBoard((int) rowCaptureTo,colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if(validate(row,col,rowCaptureTo,colCaptureTo,rowCaptured,colCaptured,board))
                listOfCaptures.add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
        //right-up
        rowCaptureTo = (char) (((int) row) - 2);
        colCaptureTo = col + 2;
        rowCaptured = (char) (((int) row) - 1);
        colCaptured = col + 1;
        if(isOnBoard((int) rowCaptureTo,colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if(validate(row,col,rowCaptureTo,colCaptureTo,rowCaptured,colCaptured,board))
                listOfCaptures.add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
        //left-down
        rowCaptureTo = (char) (((int) row) + 2);
        colCaptureTo = col - 2;
        rowCaptured = (char) (((int) row) + 1);
        colCaptured = col - 1;
        if(isOnBoard((int) rowCaptureTo,colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if(validate(row,col,rowCaptureTo,colCaptureTo,rowCaptured,colCaptured,board))
                listOfCaptures.add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
        //right-down
        rowCaptureTo = (char) (((int) row) + 2);
        colCaptureTo = col + 2;
        rowCaptured = (char) (((int) row) + 1);
        colCaptured = col + 1;
        if(isOnBoard((int) rowCaptureTo,colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if(validate(row,col,rowCaptureTo,colCaptureTo,rowCaptured,colCaptured,board))
                listOfCaptures.add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
    }

    private static boolean isOnBoard(int row,int col){
        if(row > 64 && row < 73 && col > 0 && col < 9)
            return true;
        else return false;
    }

    private static boolean validate(char row1,int col1,char row2, int col2, char row3, int col3, Board board){
        if(!(board.getFigure(row3,col3) instanceof None) && board.getFigure(row1,col1).getColor() != board.getFigure(row3,col3).getColor() && board.getFigure(row2,col2) instanceof None)
            return true;
        else
            return false;
    }

}
