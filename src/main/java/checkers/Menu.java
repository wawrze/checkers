package checkers;

import checkers.gameplay.Game;
import checkers.gameplay.InGameUI;
import checkers.gameplay.RulesSet;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;

import java.io.*;
import java.util.*;

public class Menu {

    private final Map<String, Game> games;
    private final InGameUI inGameUI;
    private final Scanner sc = new Scanner(System.in);
    private List<RulesSet> rules;
    private Terminal terminal;

    public Menu() throws IOException, ClassNotFoundException {
        games = new HashMap<>();
        rules = new ArrayList<>();
        inGameUI = new InGameUI(sc, terminal);
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
                    "classic", "classic (brasilian) draughts");
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

    public static void cls() {
        for (int i = 0; i < 100; i++)
            System.out.println();
    }

    public static void printRulesSet(RulesSet rulesSet) {
        System.out.println(" ╔════════════════════════════════════════════════════════╗");
        String s = rulesSet.getName();
        if (s.length() > 46) {
            s = s.substring(0, 45);
            s = "\"" + s + "\" rules";
        } else {
            int i = s.length();
            s = "\"" + s + "\" rules";
            s = getRulesStringComplement(s, i);
        }
        System.out.println(" ║ " + s + " ║");
        System.out.println(" ╠════════════════════════════════════════════════════════╣");
        s = rulesSet.getDescription();
        String[] description = new String[3];
        description[1] = "";
        description[2] = "";
        if (s.length() > 41) {
            description[0] = s.substring(0, 40);
            if (s.length() > 95) {
                description[1] = s.substring(41, 94);
                if (s.length() > 149)
                    description[2] = s.substring(95, 148);
                else
                    description[2] = s.substring(95);
            } else
                description[1] = s.substring(41);
        } else
            description[0] = s;
        description[0] = "Description: " + description[0];
        for (String d : description) {
            int i = d.length();
            if (i < 54) {
                if (i < 53)
                    d += "\t";
                d = getRulesStringComplement(d, i);
            }
            System.out.println(" ║ " + d + " ║ ");
        }
        System.out.println(" ╠════════════════════════════════════════════════════════╣");
        System.out.println(" ║ Victory conditions:\t\t"
                + (rulesSet.isVictoryConditionsReversed() ? "reversed" : "standard") + "\t\t  ║");
        System.out.println(" ║ Capture:\t\t\t"
                + (rulesSet.isCaptureAny() ? "any" : "longest") + "\t\t\t  ║");
        System.out.println(" ║ Men move backward:\t\t"
                + (rulesSet.isPawnMoveBackward() ? "yes" : "no") + "\t\t\t  ║");
        System.out.println(" ║ Men capture backward:\t"
                + (rulesSet.isPawnCaptureBackward() ? "yes" : "no") + "\t\t\t  ║");
        System.out.println(" ║ King range:\t\t\t"
                + (rulesSet.isQueenRangeOne() ? "one field" : "any\t") + "\t\t  ║");
        System.out.println(" ║ King move after capture:\t"
                + (rulesSet.isQueenRangeOneAfterCapture() ? "next field" : "any\t") + "\t\t  ║");
        System.out.println(" ╚════════════════════════════════════════════════════════╝");
    }

    private static String getRulesStringComplement(String s, int i) {
        if (i < 45)
            s += "\t";
        if (i < 37)
            s += "\t";
        if (i < 29)
            s += "\t";
        if (i < 21)
            s += "\t";
        if (i < 13)
            s += "\t";
        if (i < 5)
            s += "\t";
        s += " ";
        return s;
    }

    public void start() throws IncorrectMoveFormat, IncorrectMoveException, IOException {
        String o;
        do {
            this.printMenu();
            o = sc.nextLine();
            this.option(o);
        } while (!o.equals("x"));
    }

    private void printMenu() {
        cls();
        System.out.println("\t╔═════════════════════");
        System.out.println("\t║███████       ▓▓▓▓▓░░");
        System.out.println("\t║███████       ▓▓░░░░░");
        System.out.println("\t║███████       ░░░░░░░");
        System.out.println("\t║       ▓▓▓▓▓░░");
        System.out.println("\t║       ▓▓░░░   ██  █ █ ███  ██  █  █ ███ ███    ███");
        System.out.println("\t║       ░░     █  █ █ █ █   █  █ █ █  █   █  █  █   █");
        System.out.println("\t║▓▓▓▓▓░░       █    ███ ██  █    ██   ██  ███    ██");
        System.out.println("\t║▓▓░░░░░       █  █ █ █ █   █  █ █ █  █   █ █  █   █");
        System.out.println("\t║░░░░░░░        ██  █ █ ███  ██  █  █ ███ █  █  ███\n");
        System.out.println("\t╔══════════════════════╗");
        System.out.println("\t║         MENU         ║");
        System.out.println("\t╠══════════════════════╣");
        System.out.println("\t║ (s) Start new game   ║");
        System.out.println("\t╠══════════════════════╣");
        System.out.println("\t║ (l) Load game        ║");
        System.out.println("\t╠══════════════════════╣");
        System.out.println("\t║ (r) Print rules sets ║");
        System.out.println("\t╠══════════════════════╣");
        System.out.println("\t║ (x) Exit             ║");
        System.out.println("\t╚══════════════════════╝");
        System.out.println("\tChoose option: ");
    }

    private void option(String o) throws IncorrectMoveFormat, IncorrectMoveException, IOException {
        switch (o) {
            case "s":
                Game game = newGame();
                if (game.play(inGameUI)) games.put(game.getName(), game);
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
                inGameUI.waitForEnter();
                break;
            case "x":
                exit();
                break;
            default:
                break;
        }
    }

    private void printRules() {
        rules.forEach(Menu::printRulesSet);
    }

    private Game loadGame() {
        String name;
        Game game;
        while (true) {
            Menu.cls();
            System.out.println("Saved games:\n");
            games.values().forEach(System.out::println);
            System.out.println("\nEnter game name:");
            name = sc.nextLine();
            if (name.equals("x"))
                break;
            if (games.containsKey(name)) {
                game = games.get(name);
                do {
                    Menu.cls();
                    System.out.println("Saved games:\n");
                    games.values().forEach(System.out::println);
                    System.out.println("\nChosen game: " + name + ". (l) load this game or (d) delete?");
                    System.out.println("(c) to choose another game:");
                    switch (sc.nextLine()) {
                        case "l":
                            return new Game(game);
                        case "d":
                            games.remove(name);
                            name = "";
                            break;
                        case "c":
                            name = "";
                            break;
                        default:
                            break;
                    }
                } while (!name.equals(""));
            }
        }
        return null;
    }

    private Game newGame() {
        cls();
        System.out.println("Starting new game.\n\n");
        String name;
        do {
            System.out.print("Enter game name: ");
            name = sc.nextLine();
            if (games.containsKey(name)) {
                System.out.println("Game \"" + name + "\" already exists!");
                name = "";
            }
        } while (name.isEmpty());
        printRules();
        System.out.println("\nChoose rules for your game. Possible rules sets:");
        rules.forEach(r -> System.out.println("" + (1 + rules.indexOf(r)) + ". " + r.getName()));
        int number = -1;
        do {
            System.out.print("Choose rules number: ");
            try {
                number = Integer.valueOf(sc.nextLine());
                number--;
            } catch (NumberFormatException ignored) {
            }
        } while (number < 0 || number >= rules.size());
        RulesSet rulesSet = rules.get(number);
        System.out.println("Do you want to black player be a computer player?");
        boolean isBlackAIPlayer;
        isBlackAIPlayer = choosePlayerType();
        System.out.println("Do you want to white player be a computer player?");
        boolean isWhiteAIPlayer = choosePlayerType();
        return new Game(name, rulesSet, isBlackAIPlayer, isWhiteAIPlayer);
    }

    private boolean choosePlayerType() {
        String s;
        boolean isBlackAIPlayer;
        do {
            System.out.print("Enter (y) for yes or (n) for no: ");
            s = sc.nextLine();
            s = s.toLowerCase();
        } while (!s.equals("y") && !s.equals("n"));
        isBlackAIPlayer = s.equals("y");
        return isBlackAIPlayer;
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

    public Map<String, Game> getGames() {
        return games;
    }

    public List<RulesSet> getRules() {
        return rules;
    }

}