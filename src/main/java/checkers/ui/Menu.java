package checkers.ui;

import checkers.gameplay.Game;
import checkers.ui.InGameUI;
import checkers.gameplay.RulesSet;
import checkers.ui.STerminal;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {

    private final Map<String, Game> games;
    private final InGameUI inGameUI;
    private List<RulesSet> rules;

    public Menu() throws IOException, ClassNotFoundException {
        games = new HashMap<>();
        rules = new ArrayList<>();
        inGameUI = new InGameUI();
        File file = new File("games.dat");
        if (!file.exists()) {
            boolean isCreated = file.createNewFile();
            if (!isCreated) {
                throw new IOException("Can't create new file!");
            }
            try {
                FileInputStream fis = new FileInputStream(file);
                fis.close();
            } catch (IOException ignored) {
            }
        } else if (file.length() > 0) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream load = new ObjectInputStream(fis);
            Game game;
            do {
                try {
                    game = (Game) load.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    game = null;
                }
                if (game == null)
                    break;
                games.put(game.getName(), game);
            } while (true);
            load.close();
            fis.close();
        }
        file = new File("rules.dat");
        if (!file.exists()) {
            boolean isCreated = file.createNewFile();
            if (!isCreated) {
                throw new IOException("Can't create new file!");
            }
            try {
                FileInputStream fis = new FileInputStream(file);
                fis.close();
            } catch (IOException ignored) {
            }
        } else if (file.length() > 0) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream load = new ObjectInputStream(fis);
            RulesSet rule;
            do {
                rule = (RulesSet) load.readObject();
                rules.add(rule);
            } while (load.available() > 0);
            load.close();
            fis.close();
        }
        if (rules.isEmpty() || rules.size() < 3) {
            rules = new ArrayList<>();
            RulesSet rule = new RulesSet(false, false, false,
                    false, true, false,
                    "classic", "classic (brazilian) draughts");
            rules.add(rule);
            rule = new RulesSet(false, true, true,
                    false, true, true,
                    "english", "english draughts (checkers)");
            rules.add(rule);
            rule = new RulesSet(true, false, false,
                    false, true, false,
                    "poddavki", "standard rules, but reversed victory");
            rules.add(rule);
        }
    }

    static void clearMenu() {
        STerminal.getInstance().setCursorPosition(0, 15);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 16);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 17);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 18);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 19);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 20);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 21);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 22);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 23);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 24);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 25);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 26);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 27);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 28);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 29);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 30);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 31);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 32);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().setCursorPosition(0, 33);
        STerminal.getInstance().putCharMultiplied(' ', 115);
        STerminal.getInstance().update();
    }

    private void printRulesSets() {
        clearMenu();
        STerminal.getInstance().putStringAtPosition("╔═════════════════════════════════════╦═════════════════════════════════════╦═════════════════════════════════════╗", 0, 19);
        STerminal.getInstance().putStringAtPosition("║ ( )                                 ║ ( )                                 ║ ( )                                 ║", 0, 20);
        STerminal.getInstance().putStringAtPosition("╠═════════════════════════════════════╬═════════════════════════════════════╬═════════════════════════════════════╣", 0, 21);
        STerminal.getInstance().putStringAtPosition("║                                     ║                                     ║                                     ║", 0, 22);
        STerminal.getInstance().putStringAtPosition("╠═════════════════════════════════════╬═════════════════════════════════════╬═════════════════════════════════════╣", 0, 23);
        STerminal.getInstance().putStringAtPosition("║ Victory conditions:                 ║ Victory conditions:                 ║ Victory conditions:                 ║", 0, 24);
        STerminal.getInstance().putStringAtPosition("║ Capture:                            ║ Capture:                            ║ Capture:                            ║", 0, 25);
        STerminal.getInstance().putStringAtPosition("║ Men move backward:                  ║ Men move backward:                  ║ Men move backward:                  ║", 0, 26);
        STerminal.getInstance().putStringAtPosition("║ Men capture backward:               ║ Men capture backward:               ║ Men capture backward:               ║", 0, 27);
        STerminal.getInstance().putStringAtPosition("║ King range:                         ║ King range:                         ║ King range:                         ║", 0, 28);
        STerminal.getInstance().putStringAtPosition("║ King move after capture:            ║ King move after capture:            ║ King move after capture:            ║", 0, 29);
        STerminal.getInstance().putStringAtPosition("╚═════════════════════════════════════╩═════════════════════════════════════╩═════════════════════════════════════╝", 0, 30);
        for (int i = 0; i < 3; i++) {
            STerminal.getInstance().replaceStringAtPosition("\"" + rules.get(i).getName() + "\" rules", 31, (i * 38) + 6, 20);
            STerminal.getInstance().replaceStringAtPosition(rules.get(i).getDescription(), 36, (i * 38) + 2, 22);
            STerminal.getInstance().putStringAtPosition(rules.get(i).isVictoryConditionsReversed() ? "reversed" : "standard", (i * 38) + 27, 24);
            STerminal.getInstance().putStringAtPosition(rules.get(i).isCaptureAny() ? "any" : "longest", (i * 38) + 27, 25);
            STerminal.getInstance().putStringAtPosition(rules.get(i).isPawnMoveBackward() ? "yes" : "no", (i * 38) + 27, 26);
            STerminal.getInstance().putStringAtPosition(rules.get(i).isPawnCaptureBackward() ? "yes" : "no", (i * 38) + 27, 27);
            STerminal.getInstance().putStringAtPosition(rules.get(i).isQueenRangeOne() ? "one field" : "any", (i * 38) + 27, 28);
            STerminal.getInstance().putStringAtPosition(rules.get(i).isQueenRangeOneAfterCapture() ? "next field" : "any", (i * 38) + 27, 29);
        }
        STerminal.getInstance().update();
    }

    public void start() throws IncorrectMoveFormat, IncorrectMoveException, IOException {
        String o;
        do {
            printMenu();
            o = getOption();
            option(o);
        } while (!o.equals("x"));
        STerminal.getInstance().close();
    }

    private String getOption() {
        KeyStroke key;
        int activePosition = 0;
        do {
            STerminal.getInstance().putCharAtPosition(' ', 9, 18);
            STerminal.getInstance().putCharAtPosition(' ', 9, 20);
            STerminal.getInstance().putCharAtPosition(' ', 9, 22);
            STerminal.getInstance().putCharAtPosition(' ', 9, 24);
            STerminal.getInstance().putCharAtPosition('●', 9, (activePosition * 2) + 18);
            STerminal.getInstance().update();
            do {
                key = STerminal.getInstance().readInput();
            } while (key == null);
            if (key.getKeyType() == KeyType.ArrowDown) {
                activePosition = (activePosition + 1) % 4;
            } else if (key.getKeyType() == KeyType.ArrowUp) {
                activePosition = (activePosition + 3) % 4;
            } else if (key.getKeyType() == KeyType.Enter) {
                switch (activePosition) {
                    case 0:
                        return "s";
                    case 1:
                        return "l";
                    case 2:
                        return "r";
                    default:
                        return "x";
                }
            }
        } while (true);
    }

    private void printMenu() {
        STerminal.getInstance().clear();
        STerminal.getInstance().putStringAtPosition("╔═════════════════════", 6, 4);
        STerminal.getInstance().putStringAtPosition("║███████       ▓▓▓▓▓░░", 6, 5);
        STerminal.getInstance().putStringAtPosition("║███████       ▓▓░░░░░", 6, 6);
        STerminal.getInstance().putStringAtPosition("║███████       ░░░░░░░", 6, 7);
        STerminal.getInstance().putStringAtPosition("║       ▓▓▓▓▓░░", 6, 8);
        STerminal.getInstance().putStringAtPosition("║       ▓▓░░░   ██  █ █ ███  ██  █  █ ███ ███    ███", 6, 9);
        STerminal.getInstance().putStringAtPosition("║       ░░     █  █ █ █ █   █  █ █ █  █   █  █  █   █", 6, 10);
        STerminal.getInstance().putStringAtPosition("║▓▓▓▓▓░░       █    ███ ██  █    ██   ██  ███    ██", 6, 11);
        STerminal.getInstance().putStringAtPosition("║▓▓░░░░░       █  █ █ █ █   █  █ █ █  █   █ █  █   █", 6, 12);
        STerminal.getInstance().putStringAtPosition("║░░░░░░░        ██  █ █ ███  ██  █  █ ███ █  █  ███", 6, 13);
        STerminal.getInstance().putStringAtPosition("╔══════════════════════╗", 6, 15);
        STerminal.getInstance().putStringAtPosition("║         MENU         ║", 6, 16);
        STerminal.getInstance().putStringAtPosition("╠══════════════════════╣", 6, 17);
        STerminal.getInstance().putStringAtPosition("║ ( ) Start new game   ║", 6, 18);
        STerminal.getInstance().putStringAtPosition("╠══════════════════════╣", 6, 19);
        STerminal.getInstance().putStringAtPosition("║ ( ) Load game        ║", 6, 20);
        STerminal.getInstance().putStringAtPosition("╠══════════════════════╣", 6, 21);
        STerminal.getInstance().putStringAtPosition("║ ( ) Print rules sets ║", 6, 22);
        STerminal.getInstance().putStringAtPosition("╠══════════════════════╣", 6, 23);
        STerminal.getInstance().putStringAtPosition("║ ( ) Exit             ║", 6, 24);
        STerminal.getInstance().putStringAtPosition("╚══════════════════════╝", 6, 25);
        STerminal.getInstance().update();
    }

    private void option(String o) throws IncorrectMoveFormat, IncorrectMoveException, IOException {
        switch (o) {
            case "s":
                Game game = newGame();
                if (game == null) {
                    clearMenu();
                    start();
                } else if (game.play(inGameUI)) {
                    games.put(game.getName(), game);
                }
                break;
            case "l":
                game = loadGame();
                if (game == null) break;
                if (game.play(inGameUI)) {
                    games.remove(game.getName());
                    games.put(game.getName(), game);
                }
                break;
            case "r":
                printRules();
                break;
            case "x":
                exit();
                break;
            default:
                break;
        }
    }

    private void printEscapeInfo() {
        STerminal.getInstance().putStringAtPosition("╔════════════════════════════════════════════╗", 6, 31);
        STerminal.getInstance().putStringAtPosition("║ Press ESCAPE to go back to main menu.      ║", 6, 32);
        STerminal.getInstance().putStringAtPosition("╚════════════════════════════════════════════╝", 6, 33);
    }

    private void printRules() {
        printRulesSets();
        STerminal.getInstance().putStringAtPosition("╔════════════════════════════════════════════╗", 6, 16);
        STerminal.getInstance().putStringAtPosition("║ Available rule sets:                       ║", 6, 17);
        STerminal.getInstance().putStringAtPosition("╚════════════════════════════════════════════╝", 6, 18);
        STerminal.getInstance().putCharAtPosition(' ', 2, 20);
        STerminal.getInstance().putCharAtPosition(' ', 4, 20);
        STerminal.getInstance().putCharAtPosition(' ', 40, 20);
        STerminal.getInstance().putCharAtPosition(' ', 42, 20);
        STerminal.getInstance().putCharAtPosition(' ', 78, 20);
        STerminal.getInstance().putCharAtPosition(' ', 80, 20);
        printEscapeInfo();
        STerminal.getInstance().update();
        KeyStroke key;
        do {
            key = STerminal.getInstance().readInput();
        } while (key.getKeyType() != KeyType.Escape);
    }

    private Game loadGame() {
        clearMenu();
        STerminal.getInstance().putStringAtPosition("╔════════════════════════════════════════════╗", 6, 15);
        STerminal.getInstance().putStringAtPosition("║ Choose game:                               ║", 6, 16);
        STerminal.getInstance().putStringAtPosition("╚════════════════════════════════════════════╝", 6, 17);
        STerminal.getInstance().putStringAtPosition("╔═════════════════════════════════════════════════════════════╗", 6, 31);
        STerminal.getInstance().putStringAtPosition("║ ESCAPE - main menu, ENTER - load game, DELETE - remove game ║", 6, 32);
        STerminal.getInstance().putStringAtPosition("╚═════════════════════════════════════════════════════════════╝", 6, 33);

        int gamesSize = 0;
        ArrayList<Game> gameList = new ArrayList<>();
        for (Map.Entry<String, Game> game : games.entrySet()) {
            gameList.add(game.getValue());
            gamesSize++;
            if (gamesSize == 12) break;
        }
        for (int i = 0; i < gameList.size(); i++) {
            STerminal.getInstance().putStringAtPosition("( ) " + gameList.get(i), 6, i + 18);
        }

        int actualPosition = 0;
        KeyStroke key;
        do {
            for (int i = 0; i < gameList.size(); i++) STerminal.getInstance().putCharAtPosition(' ', 7, i + 18);
            if (gameList.size() > 0) STerminal.getInstance().putCharAtPosition('●', 7, actualPosition + 18);
            STerminal.getInstance().update();
            do {
                key = STerminal.getInstance().readInput();
            } while (key == null);
            if (key.getKeyType() == KeyType.ArrowDown && actualPosition < gameList.size() - 1) {
                actualPosition++;
            } else if (key.getKeyType() == KeyType.ArrowUp && actualPosition > 0) {
                actualPosition--;
            } else if (key.getKeyType() == KeyType.Delete && gameList.size() > 0) {
                games.remove(gameList.get(actualPosition).getName());
                return loadGame();
            } else if (key.getKeyType() == KeyType.Enter && gameList.size() > 0) {
                break;
            } else if (key.getKeyType() == KeyType.Escape) {
                return null;
            }
        } while (true);
        return new Game(gameList.get(actualPosition));
    }

    private Game newGame() {
        String name = getGameName();
        if (name == null) return null;
        RulesSet rulesSet = getRulesSet(name);
        if (rulesSet == null) return null;
        boolean[] players = getPlayerTypes(name, rulesSet.getName());
        if (players == null) return null;
        boolean isBlackAIPlayer = players[0];
        boolean isWhiteAIPlayer = players[1];
        return new Game(name, rulesSet, isBlackAIPlayer, isWhiteAIPlayer);
    }

    private boolean[] getPlayerTypes(String gameName, String rules) {
        boolean[] players = new boolean[2];
        clearMenu();
        STerminal.getInstance().putStringAtPosition("╔════════════════════════════════════════════╗", 6, 15);
        STerminal.getInstance().putStringAtPosition("║ Game                                       ║", 6, 16);
        STerminal.getInstance().putStringAtPosition("\"" + gameName + "\"", 13, 16);
        STerminal.getInstance().putStringAtPosition("║                                            ║", 6, 17);
        STerminal.getInstance().replaceStringAtPosition("Chosen rules: \"" + rules + "\" rules", 42, 8, 17);
        STerminal.getInstance().putStringAtPosition("║ Choose player types (mark with space key): ║", 6, 18);
        STerminal.getInstance().putStringAtPosition("╚════════════════════════════════════════════╝", 6, 19);

        STerminal.getInstance().putStringAtPosition("╔══════════════╦══════════════╦══════════════╗", 6, 21);
        STerminal.getInstance().putStringAtPosition("║ Black player ║ ( ) human    ║ ( ) computer ║", 6, 22);
        STerminal.getInstance().putStringAtPosition("╠══════════════╬══════════════╬══════════════╣", 6, 23);
        STerminal.getInstance().putStringAtPosition("║ White player ║ ( ) human    ║ ( ) computer ║", 6, 24);
        STerminal.getInstance().putStringAtPosition("╚══════════════╩══════════════╩══════════════╝", 6, 25);

        printEscapeInfo();

        boolean markedBlackPlayer = false;
        boolean markedWhitePlayer = false;
        int yPosition = 0;
        int xPosition = 0;
        KeyStroke key;
        do {
            if (markedBlackPlayer) {
                STerminal.getInstance().putCharAtPosition(' ', 24, 22);
                STerminal.getInstance().putCharAtPosition('x', 39, 22);
            } else {
                STerminal.getInstance().putCharAtPosition('x', 24, 22);
                STerminal.getInstance().putCharAtPosition(' ', 39, 22);
            }
            if (markedWhitePlayer) {
                STerminal.getInstance().putCharAtPosition(' ', 24, 24);
                STerminal.getInstance().putCharAtPosition('x', 39, 24);
            } else {
                STerminal.getInstance().putCharAtPosition('x', 24, 24);
                STerminal.getInstance().putCharAtPosition(' ', 39, 24);
            }
            if (yPosition == 0 && xPosition == 0) {
                STerminal.getInstance().putCharAtPosition('●', 24, 22);
            } else if (yPosition == 0 && xPosition == 1) {
                STerminal.getInstance().putCharAtPosition('●', 39, 22);
            } else if (yPosition == 1 && xPosition == 0) {
                STerminal.getInstance().putCharAtPosition('●', 24, 24);
            } else {
                STerminal.getInstance().putCharAtPosition('●', 39, 24);
            }
            STerminal.getInstance().update();

            do {
                key = STerminal.getInstance().readInput();
            } while (key == null);
            if (key.getKeyType() == KeyType.Escape) {
                return null;
            } else if (key.getKeyType() == KeyType.ArrowRight) {
                if (xPosition == 0) xPosition++;
            } else if (key.getKeyType() == KeyType.ArrowLeft) {
                if (xPosition == 1) xPosition--;
            } else if (key.getKeyType() == KeyType.ArrowUp) {
                if (yPosition == 1) yPosition--;
            } else if (key.getKeyType() == KeyType.ArrowDown) {
                if (yPosition == 0) yPosition++;
            } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == ' ') {
                if (yPosition == 0 && xPosition == 0) {
                    markedBlackPlayer = false;
                } else if (yPosition == 0 && xPosition == 1) {
                    markedBlackPlayer = true;
                } else markedWhitePlayer = yPosition != 1 || xPosition != 0;
            } else if (key.getKeyType() == KeyType.Enter) {
                break;
            }
        } while (true);

        players[0] = markedBlackPlayer;
        players[1] = markedWhitePlayer;
        return players;
    }

    private RulesSet getRulesSet(String gameName) {
        printRulesSets();
        STerminal.getInstance().putStringAtPosition("╔════════════════════════════════════════════╗", 6, 15);
        STerminal.getInstance().putStringAtPosition("║ Game                                       ║", 6, 16);
        STerminal.getInstance().putStringAtPosition("\"" + gameName + "\"", 13, 16);
        STerminal.getInstance().putStringAtPosition("║ Choose rules for your game:                ║", 6, 17);
        STerminal.getInstance().putStringAtPosition("╚════════════════════════════════════════════╝", 6, 18);
        printEscapeInfo();
        KeyStroke key;
        int chosenRules = 0;
        do {
            STerminal.getInstance().putCharAtPosition(' ', 3, 20);
            STerminal.getInstance().putCharAtPosition(' ', 41, 20);
            STerminal.getInstance().putCharAtPosition(' ', 79, 20);
            STerminal.getInstance().putCharAtPosition('●', (chosenRules * 38) + 3, 20);
            STerminal.getInstance().update();
            do {
                key = STerminal.getInstance().readInput();
            } while (key == null);
            if (key.getKeyType() == KeyType.Escape) {
                return null;
            } else if (key.getKeyType() == KeyType.ArrowRight) {
                chosenRules = (chosenRules + 1) % 3;
            } else if (key.getKeyType() == KeyType.ArrowLeft) {
                chosenRules = (chosenRules + 2) % 3;
            } else if (key.getKeyType() == KeyType.Enter) {
                break;
            }
        } while (true);

        return rules.get(chosenRules);
    }

    private String getGameName() {
        clearMenu();
        STerminal.getInstance().putStringAtPosition("╔════════════════════════════════════════════╗", 6, 15);
        STerminal.getInstance().putStringAtPosition("║ Enter game name:                           ║", 6, 16);
        STerminal.getInstance().putStringAtPosition("╚════════════════════════════════════════════╝", 6, 17);
        printEscapeInfo();
        STerminal.getInstance().setCursorPosition(25, 16);
        STerminal.getInstance().setCursorVisibility(true);
        STerminal.getInstance().update();
        KeyStroke key;
        int gameNameLength = 0;
        char[] nameBuffer = new char[25];
        do {
            do {
                key = STerminal.getInstance().readInput();
            } while (key == null);
            if (key.getKeyType() == KeyType.Character) {
                STerminal.getInstance().putCharacter(key.getCharacter());
                gameNameLength++;
                if (gameNameLength > 24) {
                    gameNameLength--;
                    STerminal.getInstance().setCursorPosition(25 + gameNameLength, 16);
                }
                nameBuffer[gameNameLength] = key.getCharacter();
                STerminal.getInstance().update();
            } else if (key.getKeyType() == KeyType.Enter) {
                if (gameNameLength > 0) {
                    STerminal.getInstance().setCursorVisibility(false);
                    break;
                }
            } else if (key.getKeyType() == KeyType.Backspace) {
                if (gameNameLength > 0) {
                    gameNameLength--;
                    STerminal.getInstance().setCursorPosition(25 + gameNameLength, 16);
                    STerminal.getInstance().putCharMultiplied(' ', 2);
                    nameBuffer[gameNameLength + 1] = ' ';
                    if (gameNameLength < 23) nameBuffer[gameNameLength + 2] = ' ';
                    STerminal.getInstance().setCursorPosition(25 + gameNameLength, 16);
                    STerminal.getInstance().update();
                }
            } else if (key.getKeyType() == KeyType.Escape) {
                STerminal.getInstance().setCursorVisibility(false);
                return null;
            }
        } while (true);

        StringBuilder n = new StringBuilder();
        for (int i = 0; i < 25; i++) n.append(nameBuffer[i]);

        String name = n.toString();
        if (games.containsKey(name)) {
            STerminal.getInstance().replaceStringAtPosition("Game \"" + name + "\" already exists!", 53, 8, 16);
            do {
                key = STerminal.getInstance().readInput();
            } while (key == null);
            return getGameName();
        }
        return name;
    }

    private void exit() throws IOException {
        FileOutputStream fos = new FileOutputStream("games.dat");
        ObjectOutputStream savedGames = new ObjectOutputStream(fos);
        for (Map.Entry g : games.entrySet()) {
            savedGames.writeObject(g.getValue());
        }
        savedGames.close();
        fos.close();
        fos = new FileOutputStream("rules.dat");
        ObjectOutputStream rulesSets = new ObjectOutputStream(fos);
        for (RulesSet r : rules) {
            rulesSets.writeObject(r);
        }
        rulesSets.close();
        fos.close();
    }

}