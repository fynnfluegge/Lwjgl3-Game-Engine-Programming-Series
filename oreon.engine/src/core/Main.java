package core;

import core.kernel.Game;

public class Main {

	public static void main(String[] args) {
		
		Game game = new Game();
		game.getEngine().createWindow(800, 600);
		game.init();
		game.launch();
	}

}
