package punchballjump;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class PunchBallJump extends JFrame {

	public PunchBallJump() {
		initUI();
	}

	private void initUI() {
		add(new Board());
		setTitle("Punch Ball Jump");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Commons.WIDTH, Commons.HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				PunchBallJump game = new PunchBallJump();
				game.setVisible(true);
			}
		});
	}
}
