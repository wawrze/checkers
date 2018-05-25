package checkers;

import checkers.gameplay.Game;

import java.util.Scanner;

public class Menu {

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
		System.out.println("(1) Start new game");
		System.out.println("(x) Exit");
	}

    private void option(String o) {
        switch (o) {
        case "1":
        this.newGame();
        	break;
        case "x":
        	break;
        default:
        	break;
        }
    }

	public static void cls() {
		for (int i = 0; i < 100; i++)
			System.out.println();
	}

	public void newGame() {
	    Game game = new Game();
	    game.play();
	}

	public static void waitForEnter() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Press \"Enter\" to continue.");
		sc.nextLine();
	}

}

