package checkers;

import checkers.ui.Menu;
import checkers.ui.Window;
import javafx.application.Application;
import javafx.stage.Stage;

public class Checkers extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        (new Window()).setWindow(primaryStage);
        Window.getWindow().setTitle("Checkers");
        Window.getWindow().show();
        (new Menu()).start();
    }

}