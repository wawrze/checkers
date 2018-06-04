package checkers;

import checkers.gameplay.Game;
import checkers.gameplay.RulesSet;
import exceptions.UnknownException;

import java.io.*;
import java.util.*;

public class Menu {

    private Map<String,Game> games;
    private List<RulesSet> rules;

    Scanner sc = new Scanner(System.in);

    public Menu(){
        games = new HashMap<>();
        rules = new ArrayList<>();
        File file = new File("games.dat");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
        try {
            ObjectInputStream load = new ObjectInputStream(new FileInputStream(file));
            Game game;
            do{
                game = (Game) load.readObject();
                if(game == null)
                    break;
                games.put(game.getName(),game);
            }while(true);
            load.close();
        }
        catch(IOException | ClassNotFoundException e){}
        file = new File("rules.dat");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
        try {
            ObjectInputStream load = new ObjectInputStream(new FileInputStream(file));
            RulesSet rule;
            do{
                rule = (RulesSet) load.readObject();
                rules.add(rule);
            }while(load.available() > 0);
            load.close();
        }
        catch(IOException | ClassNotFoundException e){}
        if(rules.isEmpty() || rules.size() < 3) {
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

    public void start() {
        String o;
        do {
            this.printMenu();
            o = sc.nextLine();
            this.option(o);
        } while (!o.equals("x"));
    }

    private void printMenu() {
        this.cls();
        System.out.println("Choose option:");
        System.out.println("(s) Start new game");
        System.out.println("(l) Load game");
        System.out.println("(r) Print rules sets");
        System.out.println("(x) Exit");
    }

    private void option(String o) {
        switch (o) {
            case "s":
                Game game = newGame();
                if(game.play())
                    games.put(game.getName(), game);
                break;
            case "l":
                game = loadGame();
                if(game == null)
                    break;
                if(game.play()) {
                    if(games.containsKey(game.getName()))
                        games.remove(game.getName());
                    games.put(game.getName(), game);
                }
                break;
            case "r":
                printRules();
                waitForEnter();
                break;
            case "x":
                exit();
                break;
            default:
                break;
        }
    }

    private void printRules(){
        rules.stream().forEach(Menu::printRulesSet);
    }


    public static void cls() {
        for (int i = 0; i < 100; i++)
            System.out.println();
    }

    public static void waitForEnter() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Press \"Enter\" to continue.");
        sc.nextLine();
    }

    private Game loadGame(){
        Scanner sc = new Scanner(System.in);
        String name;
        Game game;
        while (true){
            Menu.cls();
            System.out.println("Saved games:\n");
            games.entrySet().stream()
                    .map(entry -> entry.getValue())
                    .forEach(System.out::println);
            System.out.println("\nEnter game name:");
            name = sc.nextLine();
            if(name.equals("x"))
                break;
            if(games.containsKey(name)) {
                game = games.get(name);
                while(true){
                    Menu.cls();
                    System.out.println("Saved games:\n");
                    games.entrySet().stream()
                            .map(entry -> entry.getValue())
                            .forEach(System.out::println);
                    System.out.println("\nChosen game: " + name + ". (l) load this game or (d) delete?");
                    System.out.println("(c) to choose another game:");
                    switch(sc.nextLine()){
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
                    if(name.equals(""))
                        break;
                }
            }
        }
        return null;
    }

    private Game newGame(){
        cls();
        System.out.println("Starting new game.\n\n");
        String name;
        do {
            System.out.print("Enter game name: ");
            name = sc.nextLine();
            if(games.containsKey(name)) {
                System.out.println("Game \"" + name + "\" already exists!");
                name = "";
            }
        }while(name.isEmpty());
        printRules();
        System.out.println("\nChoose rules for your game. Possible rules sets:");
        rules.stream()
                .forEach(r -> System.out.println("" + (1 + rules.indexOf(r)) + ". " + r.getName()));
        int number = -1;
        do {
            System.out.print("Choose rules number: ");
            try {
                number = Integer.valueOf(sc.nextLine());
                number--;
            }
            catch(NumberFormatException e){}
        }while(number < 0 || number >= rules.size());
        RulesSet rulesSet = rules.get(number);
        System.out.println("Do you want to play with computer player?");
        boolean isAIPlayer;
        String s;
        do {
            System.out.print("Enter (y) for yes or (n) for no: ");
            s = sc.nextLine();
            s = s.toLowerCase();
        }while(!s.equals("y") && !s.equals("n"));
        if(s.equals("y"))
            isAIPlayer = true;
        else
            isAIPlayer = false;
        System.out.println("Which color do you choose?");
        boolean aiPlayer = false;
        if(isAIPlayer) {
            do {
                System.out.print("Enter (b) for black or (w) for white: ");
                s = sc.nextLine();
                s = s.toLowerCase();
            }while(!s.equals("b") && !s.equals("w"));
            if(s.equals("b"))
                aiPlayer = false;
            else
                aiPlayer = true;
        }
        Game game = new Game(name, rulesSet, isAIPlayer, aiPlayer);
        return game;
    }

    private void exit(){
        try{
            ObjectOutputStream savedGames = new ObjectOutputStream(new FileOutputStream("games.dat"));
            for(Map.Entry g: games.entrySet()) {
                savedGames.writeObject(g.getValue());
            }
            savedGames.close();
        }
        catch(IOException e){
            System.out.println(e);
            throw new UnknownException();
        }
        try{
            ObjectOutputStream rulesSets = new ObjectOutputStream(new FileOutputStream("rules.dat"));
            for(RulesSet r: rules) {
                rulesSets.writeObject(r);
            }
            rulesSets.close();
        }
        catch(IOException e){
            System.out.println(e);
            throw new UnknownException();
        }
    }

    public static void printRulesSet(RulesSet rulesSet){
        System.out.println(" ╔════════════════════════════════════════════════════════╗");
        String s = new String(rulesSet.getName());
        if(s.length() > 46) {
            s = s.substring(0, 45);
            s = "\"" + s + "\" rules";
        }
        else {
            int i = s.length();
            s = "\"" + s + "\" rules";
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
        }
        System.out.println(" ║ " + s + " ║");
        System.out.println(" ╠════════════════════════════════════════════════════════╣");
        s = new String(rulesSet.getDescription());
        String[] description = new String[3];
        description[1] = "";
        description[2] = "";
        if(s.length() > 41) {
            description[0] = s.substring(0, 40);
            if(s.length() > 95) {
                description[1] = s.substring(41, 94);
                if(s.length() > 149)
                    description[2] = s.substring(95, 148);
                else
                    description[2] = s.substring(95);
            }
            else
                description[1] = s.substring(41);
        }
        else
            description[0] = s;
        description[0] = "Description: " + description[0];
        for(String d : description) {
            int i = d.length();
            if(i < 54){
                if(i < 53)
                    d += "\t";
                if(i < 45)
                    d += "\t";
                if(i < 37)
                    d += "\t";
                if(i < 29)
                    d += "\t";
                if(i < 21)
                    d += "\t";
                if(i < 13)
                    d += "\t";
                if(i < 5)
                    d += "\t";
                d += " ";
            }
            System.out.println(" ║ " + d + " ║ ");
        }
        System.out.println(" ╠════════════════════════════════════════════════════════╣");
        System.out.println(" ║ Victory conditions:\t\t"
                + (rulesSet.isVictoryConditionsReversed() ? "reversed" : "standard") + "\t\t  ║");
        System.out.println(" ║ Capture:\t\t\t"
                + (rulesSet.isCaptureAny() ? "any" : "longest") + "\t\t\t  ║");
        System.out.println(" ║ Pawn move backward:\t\t"
                + (rulesSet.isPawnMoveBackward() ? "yes" : "no") + "\t\t\t  ║");
        System.out.println(" ║ Pawn capture backward:\t"
                + (rulesSet.isPawnCaptureBackward() ? "yes" : "no") + "\t\t\t  ║");
        System.out.println(" ║ Queen range:\t\t\t"
                + (rulesSet.isQueenRangeOne() ? "one field" : "any\t") + "\t\t  ║");
        System.out.println(" ║ Queen move after capture:\t"
                + (rulesSet.isQueenRangeOneAfterCapture() ? "next field" : "any\t") + "\t\t  ║");
        System.out.println(" ╚════════════════════════════════════════════════════════╝");
    }

}