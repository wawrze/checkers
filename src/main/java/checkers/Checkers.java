package checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Checkers extends Application {

    private Stage window;
    private Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Checkers");
        button = new Button("Button");
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 1200, 800);
        window.setScene(scene);
        window.show();
    }

}

//import checkers.ui.Menu;
//import exceptions.IncorrectMoveException;
//import exceptions.IncorrectMoveFormat;
//
//import java.io.IOException;
//
//class Checkers {
//
//    public static void main(String[] args)
//            throws IncorrectMoveFormat, IncorrectMoveException, IOException, ClassNotFoundException {
//        (new Menu()).start();
//    }
//
//}

//♙♘♗♖♕♔♟♞♝♜♛♚