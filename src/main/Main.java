package main;

public class Main {
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");

		Game game = new Game();
		game.runGame();
	}
}
