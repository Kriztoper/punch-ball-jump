package punchballjump;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class Player extends Sprite implements Commons {
	private int INIT_X;
	private int INIT_Y;
	private boolean jumping;
	private boolean punching;
	private Timer punchTimer;
	private String name;
	private int score;
	private int suspendOnAir;
	private int punchDelay;

	public Player(int initX, int initY, String name) {
		INIT_X = initX;
		INIT_Y = initY;
		this.setName(name);

		ImageIcon ii = new ImageIcon("images/star.png");
		image = ii.getImage();

		i_width = image.getWidth(null);
		i_height = image.getHeight(null);

		punchDelay = 0;

		resetState();
	}

	public void move() {
		if (getName().equals(PLAYER)) {
			if (jumping) {
				y -= 2;
			} else if (y <= INIT_PLAYER_Y && !jumping) {
				y += 2;
			}

			if (y <= 5) {
				jumping = false;
			}
		} else if (getName().equals(OPPONENT)) {
			if (jumping) {
				y += 2;
			} else if (y >= INIT_OPPONENT_Y && !jumping) {
				y -= 2;
			}

			if (y >= 620) {
				jumping = false;
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (getName().equals(PLAYER)) {
			if (key == KeyEvent.VK_S && !isPunching() && !jumping) {
				System.out.println("Player punch");
				punching = true;
				punchTimer = new Timer();
				punchTimer.scheduleAtFixedRate(new ScheduleTaskForPunch(), 1000 - punchDelay, 300);
			} else if (key == KeyEvent.VK_A && y >= 120 && !isPunching() && !jumping) {
				System.out.println("Player jump");
				jumping = true;
			}
		} else if (getName().equals(OPPONENT)) {
			if (key == KeyEvent.VK_L && !isPunching() && !jumping) {
				System.out.println("Opponent punch");
				punching = true;
				punchTimer = new Timer();
				punchTimer.scheduleAtFixedRate(new ScheduleTaskForPunch(), 1000 - punchDelay, 300);
			} else if (key == KeyEvent.VK_K && y <= 500 && !isPunching() && !jumping) {
				System.out.println("JUMPIIIIIINGGGGG!!!!!");
				jumping = true;
			}
		}
	}

	private class ScheduleTaskForPunch extends TimerTask {
		@Override
		public void run() {
			punching = false;
			punchTimer.cancel();
			System.out.println("done punching");

		}
	}

	public void setPunchDelay(int punchDelay) {
		this.punchDelay = punchDelay;
	}

	public int getPunchDelay() {
		return punchDelay;
	}

	public Timer getPunchTimer() {
		return punchTimer;
	}

	public boolean isPunching() {
		return punching;
	}

	private void resetState() {
		x = INIT_X;
		y = INIT_Y;

		jumping = false;
		punching = false;

		setScore(0);
	}

	public void incScore() {
		this.score++;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSuspendOnAir() {
		return suspendOnAir;
	}

	public void setSuspendOnAir(int suspendOnAir) {
		this.suspendOnAir = suspendOnAir;
	}
}
