package core;

import core.kernel.Game;

public class Main {
	
public static void main(String[] args) {
		
		Game game = new Game();
		game.getEngine().createWindow(1280, 720, "oreon worlds");
		game.init();
		game.launch();
	}
}
