package punchballjump;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class Player extends Sprite implements Commons {
	private int dy;
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
		} else if (y <= 80 && !jumping){
			y += 2;
		}
		
		if (y <= 10) {
			jumping = false;
		}
		
		
		
/*		x += dx;
		
		if (x <= 0) {
			x = 0;
		}
		
		if (x == WIDTH - i_width) {
			x = WIDTH - i_width;
		}
*/	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_S && !isPunching() &&
				!jumping) {
			System.out.println("Hello OWlr");
			punching = true;
			punchTimer = new Timer();
			punchTimer.scheduleAtFixedRate(new ScheduleTaskForPunch(), 
					1000, 300);
		} else if (key == KeyEvent.VK_A && y >= 76 &&
				!isPunching() && !jumping) {
			jumping = true;
			//dy = 1;
		}
		
		
		
/*		if (key == KeyEvent.VK_LEFT) {
			dx = -1;
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			dx = 1;
		}
*/	}
	
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
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			dy = 0;
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			dy = 0;
		}
	}
	
	private void resetState() {
		x = INIT_PADDLE_X;
		y = INIT_PADDLE_Y;
		
		jumping = false;
		punching = false;
	}
}
