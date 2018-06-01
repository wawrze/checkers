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
        rules.stream()
                .forEach(r -> System.out.println("" + (1 + rules.indexOf(r)) + ". " + r));
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
        System.out.println("\nChoose rules for your game. Possible rules sets:");
        printRules();
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
        System.out.println("Choose if you want to print board simple."
                + "You can change it at any time during game by entering \"p\"");
        boolean simplePrint;
        String s;
        do {
            System.out.print("Enter yes or no: ");
            s = sc.nextLine();
            s = s.toLowerCase();
        }while(!s.equals("yes") && !s.equals("no"));
        if(s.equals("yes"))
            simplePrint = true;
        else
            simplePrint = false;
        Game game = new Game(name, rulesSet, simplePrint);
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

}