package checkers;

import checkers.ui.Menu;
import checkers.ui.Window;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Checkers extends Application {

    private Scene scene1, scene2;

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