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
	private Image jumpingImage;
	private Image deadImage;
	private boolean isMoving = false;
	public long rectBorder;
	private boolean isAlive;
	private boolean isTop;

	public Player(int initX, int initY, String name, int hearts, boolean isComputer, int difficulty, boolean isTop) {
		INIT_X = initX;
		INIT_Y = initY;
		setName(name);

		if (name.equals(PLAYER)) {
			ImageIcon ii = new ImageIcon("images/player1.png");
			image = ii.getImage();

			// punching image
			ii = new ImageIcon("images/player1_kick.png");
			punchingImage = ii.getImage();

			// jumping image
			ii = new ImageIcon("images/player1_jump.png");
			jumpingImage = ii.getImage();

			// dead image
			ii = new ImageIcon("images/player1_dead.png");
			deadImage = ii.getImage();
		} else if (name.equals(OPPONENT)) {
			ImageIcon ii = new ImageIcon("images/player2.png");
			image = ii.getImage();

			// punching image
			ii = new ImageIcon("images/player2_kick.png");
			punchingImage = ii.getImage();

			// jumping image
			ii = new ImageIcon("images/player2_jump.png");
			jumpingImage = ii.getImage();

			// dead image
			ii = new ImageIcon("images/player2_dead.png");
			deadImage = ii.getImage();
		}

		i_width = image.getWidth(null);
		i_height = image.getHeight(null);

		punchDelay = 0;

		resetState(hearts, isTop);

		this.isComputer = isComputer;
		if (isComputer) {
			random = new Random();
			this.difficulty = difficulty;
		}
	}

	public Image getPunchingImage() {
		return punchingImage;
	}

	public Image getJumpingImage() {
		return jumpingImage;
	}

	public Image getDeadImage() {
		return deadImage;
	}

	public void move() {
		if (isTop) {
			if (jumping) {
				y -= 2;
			} else if (y <= INIT_PLAYER_Y && !jumping) {
				y += 2;
			}

			if (y <= 5) {
				jumping = false;
			}
		} else if (!isTop) {
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

		if (isTop && getName().equals(PLAYER)) {// && !isComputer) {
			if (key == KeyEvent.VK_S && !isPunching() && !jumping) {
				System.out.println("Player punch");
				punching = true;
				punchTimer = new Timer();
				punchTimer.schedule(new ScheduleTaskForPunch(), 1000 - punchDelay);
			} else if (key == KeyEvent.VK_A && y >= 120 && !isPunching() && !jumping) {
				System.out.println("Player jump");
				jumping = true;
			}
		}
		// else if (!isTop && getName().equals(OPPONENT) && isComputer) {
		// if (key == KeyEvent.VK_L && !isPunching() && !jumping) {
		// System.out.println("Opponent punch");
		// punching = true;
		// punchTimer = new Timer();
		// punchTimer.schedule(new ScheduleTaskForPunch(), 1000 - punchDelay);
		// } else if (key == KeyEvent.VK_K && y <= 500 && !isPunching() && !jumping) {
		// System.out.println("JUMPIIIIIINGGGGG!!!!!");
		// jumping = true;
		// }
		// } else if (isTop && getName().equals(OPPONENT) && isComputer) {
		// if (key == KeyEvent.VK_L && !isPunching() && !jumping) {
		// System.out.println("Opponent punch");
		// punching = true;
		// punchTimer = new Timer();
		// punchTimer.schedule(new ScheduleTaskForPunch(), 1000 - punchDelay);
		// } else if (key == KeyEvent.VK_K && y >= 120 && !isPunching() && !jumping) {
		// System.out.println("Opponent jump");
		// jumping = true;
		// }
		// }
		else if (!isTop && getName().equals(PLAYER)) {// && !isComputer) {
			if (key == KeyEvent.VK_S && !isPunching() && !jumping) {
				System.out.println("Player punch");
				punching = true;
				punchTimer = new Timer();
				punchTimer.schedule(new ScheduleTaskForPunch(), 1000 - punchDelay);
			} else if (key == KeyEvent.VK_A && y <= 500 && !isPunching() && !jumping) {
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

	public void setPunching(boolean punching) {
		this.punching = punching;
	}

	public boolean isPunching() {
		return punching;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public boolean isJumping() {
		return jumping;
	}

	private void resetState(int hearts, boolean isTop) {
		x = INIT_X;
		y = INIT_Y;

		jumping = false;
		punching = false;

		setHearts(hearts);
		setAlive(true);
		setTop(isTop);

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

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}
}
