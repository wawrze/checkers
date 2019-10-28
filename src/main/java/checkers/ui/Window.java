package checkers.ui;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Window {

    private static Stage window;
    private static Pane gameLayout;

    public static Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        Window.window = window;
    }

    public static Pane getGameLayout() {
        if (gameLayout == null) {
            gameLayout = new Pane();
            gameLayout.setStyle("-fx-background-color: #829eae;");
        }
        return gameLayout;
    }

    public static void clearGameLayout() {
        gameLayout = null;
    }

}
