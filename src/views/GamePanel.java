package views;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.Player;
import punchballjump.Commons;

public class GamePanel extends JPanel implements Commons, KeyListener {

	private Player player;

	public GamePanel() {
		setLayout(null);
		setBackground(new Color(204, 255, 255));
		setSize(Commons.WIDTH, Commons.HEIGHT);

		initPlayer();
	}

	private void initPlayer() {
		player = new Player();

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) {
			JOptionPane.showMessageDialog(null, "Hello");
			System.out.println("Hello World");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) {
			JOptionPane.showMessageDialog(null, "Hello");
			System.out.println("Hello World");
		}
	}
}
