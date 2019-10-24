package checkers;

import checkers.ui.Menu;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;

import java.io.IOException;

class Checkers {

    public static void main(String[] args)
            throws IncorrectMoveFormat, IncorrectMoveException, IOException, ClassNotFoundException {
        (new Menu()).start();
    }

}

//♙♘♗♖♕♔♟♞♝♜♛♚