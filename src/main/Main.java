package main;

import javax.swing.SwingUtilities;
import controllers.GameController;
import views.GameFrame;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				GameFrame gameFrame = new GameFrame();
				GameController gameController = 
						new GameController(gameFrame);
				gameController.start();
			}
		});
	}

}
