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
	
	public Player(int initX, int initY, String name) {
		INIT_X = initX;
		INIT_Y = initY;
		this.name = name;
		
		ImageIcon ii = new ImageIcon("images/star.png");
		image = ii.getImage();
		
		i_width = image.getWidth(null);
		i_height = image.getHeight(null);
		
		resetState();
	}
	
	public void move() {
		if (name.equals(PLAYER)) {
			if (jumping) {
				y -= 2;
			} else if (y <= 130 && !jumping){
				y += 2;
			}
			
			if (y <= 30) {
				jumping = false;
			}
		} else if (name.equals(OPPONENT)) {
			if (jumping) {
				y += 2;
			} else if (y >= 360 && !jumping){
				y -= 2;
			}
			
			if (y >= 460) {
				jumping = false;
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (name.equals(PLAYER)) {
			if (key == KeyEvent.VK_S && !isPunching() &&
					!jumping) {
				System.out.println("Player punch");
				punching = true;
				punchTimer = new Timer();
				punchTimer.scheduleAtFixedRate(new ScheduleTaskForPunch(), 
						1000, 300);
			} else if (key == KeyEvent.VK_A && y >= 126 &&
					!isPunching() && !jumping) {
				System.out.println("Player jump");
				jumping = true;
			} 
		} else if (name.equals(OPPONENT)) {
			if (key == KeyEvent.VK_L && !isPunching() &&
				!jumping) {
			System.out.println("Opponent punch");
			punching = true;
			punchTimer = new Timer();
			punchTimer.scheduleAtFixedRate(new ScheduleTaskForPunch(), 
					1000, 300);
			} else if (key == KeyEvent.VK_K && y <= 366 &&
					!isPunching() && !jumping) {
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
	
	public boolean isPunching() {
		return punching;
	}

	private void resetState() {
		x = INIT_X;
		y = INIT_Y;
		
		jumping = false;
		punching = false;
	}
}
