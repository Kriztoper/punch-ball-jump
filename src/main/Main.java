package main;

import java.awt.Font;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controllers.GameController;
import views.GameFrame;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					System.out.println("Unable to Initialize Look and Feel");
				}
				
				GameFrame gameFrame = new GameFrame();
				GameController gameController = 
						new GameController(gameFrame);
				gameController.start();
			}
		});
	}
}