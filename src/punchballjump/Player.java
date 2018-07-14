package punchballjump;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Random;
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
	private int hearts;
	private int suspendOnAir;
	private int punchDelay;
	public final boolean isComputer;
	private Random random;
	private boolean invincible;
	private int difficulty;
	private Image punchingImage;
	private boolean isMoving = false;
	public long rectBorder;

	public Player(int initX, int initY, String name, int hearts, boolean isComputer, int difficulty) {
		INIT_X = initX;
		INIT_Y = initY;
		setName(name);

		ImageIcon ii = new ImageIcon("images/player.png");
		image = ii.getImage();

		// punching image
		ii = new ImageIcon("images/player_punch.png");
		punchingImage = ii.getImage();

		i_width = image.getWidth(null);
		i_height = image.getHeight(null);

		punchDelay = 0;

		resetState(hearts);

		this.isComputer = isComputer;
		if (isComputer) {
			random = new Random();
			this.difficulty = difficulty;
		}
	}

	public Image getPunchingImage() {
		return punchingImage;
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

	public void generateMove(Ball ball, long ballPeriod) {
		// Prints pos and dim of ball and players and is used for debugging
		// NOTE: Uncomment to print
		// System.out.printf("ball = %f, %f, %f, %f\nplayer = %f, %f, %f, %f\n",
		// ball.getRect().getX(),
		// ball.getRect().getY(), ball.getRect().getWidth(), ball.getRect().getHeight(),
		// getRect().getX(),
		// getRect().getY(), getRect().getWidth(), getRect().getHeight());
		boolean isJump = random.nextBoolean();// move <= 14 ? true : false;
		rectBorder = (110 - ballPeriod) / 2;
		if (getLongBiggerRect(rectBorder).intersects(ball.getRect()) && !isMoving) {
			Timer timer = new Timer();
			TimerTask timerTask;
			if (!isJump) {
				// punch
				timerTask = new TimerTask() {

					@Override
					public void run() {
						jumping = false;
						punching = true;
						punchTimer = new Timer();
						punchTimer.schedule(new ScheduleTaskForPunch(), 1000 - punchDelay);
					}
				};
			} else {
				// jump
				timerTask = new TimerTask() {

					@Override
					public void run() {
						punching = false;
						jumping = true;
					}
				};
			}
			timer.schedule(timerTask, difficulty);
			isMoving = true;
		} else if (!getLongBiggerRect(ballPeriod).intersects(ball.getRect()) && isMoving) {
			isMoving = false;
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (getName().equals(PLAYER)) {
			if (key == KeyEvent.VK_S && !isPunching() && !jumping) {
				System.out.println("Player punch");
				punching = true;
				punchTimer = new Timer();
				punchTimer.schedule(new ScheduleTaskForPunch(), 1000 - punchDelay);
			} else if (key == KeyEvent.VK_A && y >= 120 && !isPunching() && !jumping) {
				System.out.println("Player jump");
				jumping = true;
			}
		} else if (getName().equals(OPPONENT) && isComputer == false) {
			if (key == KeyEvent.VK_L && !isPunching() && !jumping) {
				System.out.println("Opponent punch");
				punching = true;
				punchTimer = new Timer();
				punchTimer.schedule(new ScheduleTaskForPunch(), 1000 - punchDelay);
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

	private void resetState(int hearts) {
		x = INIT_X;
		y = INIT_Y;

		jumping = false;
		punching = false;

		setHearts(hearts);

		setInvincible(false);
	}

	public void incHearts() {
		this.hearts++;
	}

	public void decHearts() {
		this.hearts--;
	}

	public int getHearts() {
		return hearts;
	}

	public void setHearts(int hearts) {
		this.hearts = hearts;
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

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}
}
