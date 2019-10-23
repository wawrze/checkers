package checkers.gameplay;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class STerminal {

    private static STerminal instance;
    private Terminal terminal;

    private STerminal() {
        try {
            terminal = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(100, 33))
                    .createTerminal();
            terminal.setCursorVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static STerminal getInstance() {
        if (instance == null) instance = new STerminal();
        return instance;
    }

    public void putCharacter(char character) {
        try {
            terminal.putCharacter(character);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putCharMultiplied(char character, int positions) {
        try {
            for (int i = 0; i < positions; i++) terminal.putCharacter(character);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCursorPosition(int x, int y) {
        try {
            terminal.setCursorPosition(x, y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putCharAtPosition(char character, int x, int y) {
        try {
            terminal.setCursorPosition(x, y);
            terminal.putCharacter(character);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putStringAtPosition(String string, int x, int y) {
        try {
            terminal.setCursorPosition(x, y);
            for (int i = 0; i < string.length(); i++) terminal.putCharacter(string.charAt(i));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replaceStringAtPosition(String string, int stringMaxLength, int x, int y) {
        try {
            terminal.setCursorPosition(x, y);
            for (int i = 0; i < string.length(); i++) terminal.putCharacter(string.charAt(i));
            for (int i = 0; i < stringMaxLength - string.length(); i++) terminal.putCharacter(' ');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void close() {
        try {
            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        terminal = null;
    }

    public void update() {
        try {
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    KeyStroke readInput() {
        KeyStroke key = null;
        try {
            key = terminal.readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return key;
    }

}
