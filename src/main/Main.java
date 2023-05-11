package main;

public class Main {
	public static void main(String[] args) {
		// open GPU
		System.setProperty("sun.java2d.opengl", "true");

		// make swing more better
		System.setProperty("swing.aatext", "true");

		Game game = new Game();
		game.runGame();
	}
}
