package main;

import controllers.GameController;
import views.GameFrame;

public class Main {

	public static void main(String[] args) {
		GameFrame gameFrame = new GameFrame();
		GameController gameController = 
				new GameController(gameFrame);
		gameController.start();
	}

}
