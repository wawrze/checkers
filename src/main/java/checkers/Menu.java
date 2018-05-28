package checkers;

import checkers.gameplay.Game;
import exceptions.UnknownException;

import java.io.*;
import java.util.*;

public class Menu {

    private Map<String,Game> games;

    public Menu(){
        games = new HashMap<>();
        File file = new File("games.dat");
        try {
            file.createNewFile();
        }
        catch(IOException e){}
        try {
            ObjectInputStream load = new ObjectInputStream(new FileInputStream(file));
            Game game;
            do{
                game = (Game) load.readObject();
                if(game.equals(null))
                    break;
                games.put(game.getName(),game);
            }while(true);
            load.close();
        }
        catch(IOException | ClassNotFoundException e){
            if(e instanceof ClassNotFoundException)
            throw new UnknownException();
        }
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
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
        System.out.println("(x) Exit");
    }

    private void option(String o) {
        switch (o) {
            case "s":
                Game game = new Game();
                if(game.play())
                    games.put(game.getName(), game);
                break;
            case "l":
                game = loadGame();
                if(game == null)
                    break;
                if(game.play())
                    games.put(game.getName(), game);
                break;
            case "x":
                exit();
                break;
            default:
                break;
        }
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
                            return game;
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

    private void exit(){
        try{
            ObjectOutputStream savedGames = new ObjectOutputStream(new FileOutputStream("games.dat"));
            for(Map.Entry g: games.entrySet()) {
                savedGames.writeObject(g.getValue());
            }
            savedGames.close();
        }
        catch(IOException e){
            throw new UnknownException();
        }
    }

}

