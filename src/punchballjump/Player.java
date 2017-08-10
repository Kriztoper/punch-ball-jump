package punchballjump;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class Player extends Sprite implements Commons {
	private boolean jumping;
	private boolean punching;
	private Timer punchTimer;
	
	public Player() {
		ImageIcon ii = new ImageIcon("images/star.png");
		image = ii.getImage();
		
		i_width = image.getWidth(null);
		i_height = image.getHeight(null);
		
		resetState();
	}
	
	public void move() {
		if (jumping) {
			y -= 2;
		} else if (y <= 130 && !jumping){
			y += 2;
		}
		
		if (y <= 30) {
			jumping = false;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_S && !isPunching() &&
				!jumping) {
			System.out.println("Hello OWlr");
			punching = true;
			punchTimer = new Timer();
			punchTimer.scheduleAtFixedRate(new ScheduleTaskForPunch(), 
					1000, 300);
		} else if (key == KeyEvent.VK_A && y >= 126 &&
				!isPunching() && !jumping) {
			jumping = true;
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
		x = INIT_PLAYER_X;
		y = INIT_PLAYER_Y;
		
		jumping = false;
		punching = false;
	}
}
