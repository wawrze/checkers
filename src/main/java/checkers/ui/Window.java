package checkers.ui;

import javafx.stage.Stage;

public class Window {

    private static Stage window;

    public void setWindow(Stage window) {
        Window.window = window;
    }

    public static Stage getWindow() {
        return window;
    }

}
