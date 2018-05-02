package checkers;

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
		for (int i = 0; i < 50; i++)
			System.out.println();
	}

	public void newGame() {
		new Game();
	}

	public static void waitForX() {
		Scanner sc = new Scanner(System.in);
		String o;
		do {
			System.out.println("Enter \"x\" to continue.");
			o = sc.nextLine();
		} while (!o.equals("x"));
	}
}
