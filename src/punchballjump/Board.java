package punchballjump;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements Commons {
	private String message = "Game Over";
	private Ball ball;
	private Player[] players;
	private Powerup[] powerups;
	private boolean ingame = true;
	private boolean playerReversing;
	private boolean opponentReversing;
	private long ballPeriod;
	private ScheduleTaskForPlayer scheduleTaskForPlayer;
	private ScheduleTaskForBall scheduleTaskForBall;
	private Timer timerForPlayer;
	private Timer timerForBall;
	private Timer timerForBallReset;
	private Timer timerForPlayerPowerups;
	private Timer timerForOpponentPowerups;
	private boolean pressed = false;
	private ArrayList<Powerup> playerPowerupsList;
	private ArrayList<Powerup> opponentPowerupsList;
	private boolean isPlayerPowerupActivated;
	private boolean isOpponentPowerupActivated;
	private Color pCaptionColor;
	private String pCaptionMsg;
	private Color oCaptionColor;
	private String oCaptionMsg;
	private int countdown;
	private Image bg = Toolkit.getDefaultToolkit().createImage("images/test.png");
	private ImageIcon earth = new ImageIcon("res/earth.png");
	private int round = 1;

	public Board() {
		// init Powerups only once
		String[] powerupsArr = { RESTORE, INVINCIBLE, SWAP };
		playerPowerupsList = new ArrayList<Powerup>();
		opponentPowerupsList = new ArrayList<Powerup>();
		for (int i = 0; i < powerupsArr.length; i++) {
			playerPowerupsList.add(new Powerup(INIT_PLAYER_POWERUP_X, INIT_PLAYER_POWERUP_Y, powerupsArr[i]));
			opponentPowerupsList.add(new Powerup(INIT_OPPONENT_POWERUP_X, INIT_OPPONENT_POWERUP_Y, powerupsArr[i]));
		}
		powerups = new Powerup[2];

		initBoard();
	}

	private void initBoard() {
		setLayout(null);
		addKeyListener(new TAdapter());
		setFocusable(true);

		setDoubleBuffered(true);

		scheduleTaskForPlayer = new ScheduleTaskForPlayer();
		timerForPlayer = new Timer();
		timerForPlayer.schedule(scheduleTaskForPlayer, 1000, 5);

		ballPeriod = 100;
		scheduleTaskForBall = new ScheduleTaskForBall();
		timerForBall = new Timer();
		// TODO: Graphics should display a countdown from 3 2 1
		timerForBall.schedule(scheduleTaskForBall, 3000, ballPeriod);
	}

	public void initBallTimer(long period, Player player) {
		System.out.println("Init ball timer");
		if (period >= 20) {
			System.out.println("ballPeriod adjusting to " + period);
			timerForBall.cancel();
			timerForBall = new Timer();
			scheduleTaskForBall = new ScheduleTaskForBall();
			timerForBall.schedule(scheduleTaskForBall, 0, period);
		} else {
			System.out.println(">>>>>>>>>>>>>>>Reseting ballPeriod to 100");
			timerForBallReset = new Timer();
			TimerTask scheduleTaskForBallReset = new TimerTask() {

				@Override
				public void run() {
					ballPeriod = 100;
				}
			};
			timerForBallReset.schedule(scheduleTaskForBallReset, 20000);
		}
	}

	@Override
	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	private void gameInit() {
		int[] hearts = new int[2];
		if (players != null && players[0] != null && players[1] != null) {
			System.out.println("Players 1 & 2 not null so hearts maintain!");
			hearts[0] = players[0].getHearts();
			hearts[1] = players[1].getHearts();
		} else {
			players = new Player[2];
			hearts[0] = 5;
			hearts[1] = 5;
		}
		players[0] = new Player(INIT_PLAYER_X, INIT_PLAYER_Y, PLAYER, hearts[0], IS_NOT_COMPUTER, HUMAN);
		players[1] = new Player(INIT_OPPONENT_X, INIT_OPPONENT_Y, OPPONENT, hearts[1], IS_COMPUTER, HARD);
		ball = new Ball();
		playerReversing = false;
		opponentReversing = false;

		// Prints the values of the lists setup, used for debugging
		for (Powerup x : playerPowerupsList)
			System.out.println(x.getName());
		for (Powerup x : opponentPowerupsList)
			System.out.println(x.getName());

		// Assign timer for time when powerups will appear
		Random random = new Random();
		if (timerForPlayerPowerups != null) {
			timerForPlayerPowerups.cancel();
		}
		if (timerForOpponentPowerups != null) {
			timerForOpponentPowerups.cancel();
		}
		timerForPlayerPowerups = new Timer();
		timerForOpponentPowerups = new Timer();
		TimerTask timerTaskForPlayerPowerups;
		TimerTask timerTaskForOpponentPowerups;
		if (random.nextInt(4) > 0 && !playerPowerupsList.isEmpty()) {
			System.out.println("Planting powerups for player");
			timerTaskForPlayerPowerups = new TimerTask() {

				@Override
				public void run() {
					powerups[0] = playerPowerupsList.get(random.nextInt(3));
				}
			};
			timerForPlayerPowerups.schedule(timerTaskForPlayerPowerups,
					ThreadLocalRandom.current().nextInt(13, 19) * 1000);
		}
		if (random.nextInt(4) > 0 && !opponentPowerupsList.isEmpty()) {
			System.out.println("Planting powerups for opponent");
			timerTaskForOpponentPowerups = new TimerTask() {

				@Override
				public void run() {
					powerups[1] = opponentPowerupsList.get(random.nextInt(3));
				}

			};
			timerForOpponentPowerups.schedule(timerTaskForOpponentPowerups,
					ThreadLocalRandom.current().nextInt(13, 19) * 1000);
		}
		isPlayerPowerupActivated = false;
		isOpponentPowerupActivated = false;
		countdown = 3;
		Timer timerForCountdown = new Timer();
		TimerTask timerTaskForCountdown = new TimerTask() {

			@Override
			public void run() {
				countdown--;
				if (countdown < 0) {
					timerForCountdown.cancel();
				}
			}

		};
		timerForCountdown.schedule(timerTaskForCountdown, 0, 1000);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		g.drawImage(bg, 0, 0, null);

		// draw Earth at the middle
		g2d.drawImage(earth.getImage(), 275, 255, earth.getIconWidth(), earth.getIconHeight(), this);

		g.drawString(String.valueOf(players[0].getHearts()), 10, 10);
		g.drawString(String.valueOf(players[1].getHearts()), 210, 10);

		drawObjects(g2d);
		// countdown
		if (countdown > 0) {
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 64));
			g.drawString("ROUND " + round, 160, 350);
			g.setColor(new Color(0f, 0f, 0f, .25f));
			g.fillRect(0, 0, Commons.WIDTH, Commons.HEIGHT);
		} else if (countdown == 0) {
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 64));
			g.drawString("START!", 210, 350);
		}
		if (!ingame) {
			gameFinished(g2d);
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void drawObjects(Graphics2D g2d) {
		// draw powerups
		for (Powerup powerup : powerups) {
			if (powerup != null) {
				g2d.drawImage(powerup.getImage(), powerup.getX(), powerup.getY(), powerup.getWidth(),
						powerup.getHeight(), this);
			}
		}

		// draw player powerups activated caption
		// TODO: Improve caption design
		if (isPlayerPowerupActivated) {
			g2d.setColor(pCaptionColor);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 16));
			g2d.drawString(pCaptionMsg, 30, 30);
			Timer timerForCaption = new Timer();
			TimerTask timerTaskForCaption = new TimerTask() {

				@Override
				public void run() {
					isPlayerPowerupActivated = false;
				}
			};
			timerForCaption.schedule(timerTaskForCaption, 5000);
		}
		// draw opponent powerups activated caption
		// TODO: Improve caption design
		if (isOpponentPowerupActivated) {
			g2d.setColor(oCaptionColor);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 16));
			g2d.drawString(oCaptionMsg, 350, 30);
			Timer timerForCaption = new Timer();
			TimerTask timerTaskForCaption = new TimerTask() {

				@Override
				public void run() {
					isOpponentPowerupActivated = false;
				}
			};
			timerForCaption.schedule(timerTaskForCaption, 5000);
		}

		for (Player player : players) {
			// Draw bigger rect around image which is used for visualization, debugging, etc
			// NOTE: Uncomment to draw
			// int width = player.getWidth() * 3;
			// int height = player.getHeight() * 3;
			// int x = player.getX() - player.getWidth();
			// int y = player.getY() - player.getHeight();
			// g2d.setColor(Color.RED);
			// g2d.fillRect(x, y, width, height);
			if (player.isInvincible()) {
				// TODO: Display indicator like forcefield if player is invincible
				int w1 = player.getSlightlyBiggerRect().width;
				int h1 = player.getSlightlyBiggerRect().height;
				int x1 = player.getSlightlyBiggerRect().x;
				int y1 = player.getSlightlyBiggerRect().y;
				g2d.setColor(Color.RED);
				g2d.fillRect(x1, y1, w1, h1);
			}

			g2d.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), this);
			// System.out.printf("x=%d, y=%d, w=%d, h=%d\n", x, y, width, height);
		}
		g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), this);

	}

	private void gameFinished(Graphics2D g2d) {
		int winner = players[0].getHearts() <= 0 ? 2 : 1;
		g2d.setColor(Color.RED);
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 64));
		g2d.drawString("PLAYER " + winner + " WINS!", 50, 350);
		g2d.setColor(new Color(0f, 0f, 0f, .25f));
		g2d.fillRect(0, 0, Commons.WIDTH, Commons.HEIGHT);
	}

	private class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (!pressed) {
				for (Player player : players) {
					player.keyPressed(e);
				}
				pressed = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			pressed = false;
		}
	}

	private class ScheduleTaskForPlayer extends TimerTask {
		@Override
		public void run() {
			if (players[1].isComputer) {
				players[1].generateMove(ball);
			}
			for (Player player : players)
				player.move();
			repaint();
			checkCollision(); // Note: checkCollision must be after repaint and it must be assigned to the
								// Timer with the smallest period
		}
	}

	private class ScheduleTaskForBall extends TimerTask {
		@Override
		public void run() {
			ball.move();
			repaint();
		}
	}

	private void checkCollision() {
		// check on both players if ball hits them
		for (Player player : players) {
			if (ball.getRect().intersects(player.getRect()) && !player.isPunching() && !player.isInvincible()) {
				// player is hit by ball and is not punching
				round++;
				if (player.getName().equals(PLAYER)) {
					players[0].decHearts();
				} else {
					players[1].decHearts();
				}
				// clear all displayed powerups
				powerups[0] = null;
				powerups[1] = null;

				System.out.println("Game Over");
				// all 5 hearts have been consumed by a player therefore that player loses
				if (players[0].getHearts() <= 0 || players[1].getHearts() <= 0) {
					ingame = false;
					timerForBall.cancel();
					timerForPlayer.cancel();
					// TODO: Add code for returning back to menu panel
				} else {
					timerForBall.cancel();
					timerForPlayer.cancel();

					initBoard();
					gameInit();
				}
			} else if (powerups[0] != null && player.getRect().intersects(powerups[0].getRect())) { // player powerups
				isPlayerPowerupActivated = true;
				if (powerups[0].getName().equals(RESTORE)) {
					pCaptionColor = Color.RED;
					pCaptionMsg = "+1 Heart";
				} else if (powerups[0].getName().equals(INVINCIBLE)) {
					pCaptionColor = Color.GREEN;
					pCaptionMsg = "INVINCIBLE for 10 sec";
				} else if (powerups[0].getName().equals(SWAP)) {
					pCaptionColor = Color.YELLOW;
					pCaptionMsg = "Swap hearts";
				}
				Powerup powerup = powerups[0];
				powerups[0] = null;
				if (powerup.getName().equals(RESTORE)) {
					if (player.getHearts() < 5) {
						player.incHearts();
					}
				} else if (powerup.getName().equals(INVINCIBLE)) {
					player.setInvincible(true);
					Timer timerForInvincibility = new Timer();
					TimerTask timerTaskForInvincibility = new TimerTask() {

						@Override
						public void run() {
							player.setInvincible(false);
						}
					};
					timerForInvincibility.schedule(timerTaskForInvincibility, 10000);
				} else if (powerup.getName().equals(SWAP)) {
					int playerHearts = players[0].getHearts();
					int opponentHearts = players[1].getHearts();
					players[0].setHearts(opponentHearts);
					players[1].setHearts(playerHearts);
				}
			} else if (powerups[1] != null && player.getRect().intersects(powerups[1].getRect())) { // opponent powerups
				isOpponentPowerupActivated = true;
				if (powerups[1].getName().equals(RESTORE)) {
					oCaptionColor = Color.RED;
					oCaptionMsg = "+1 Heart";
				} else if (powerups[1].getName().equals(INVINCIBLE)) {
					oCaptionColor = Color.GREEN;
					oCaptionMsg = "INVINCIBLE for 10 sec";
				} else if (powerups[1].getName().equals(SWAP)) {
					oCaptionColor = Color.YELLOW;
					oCaptionMsg = "Swap hearts";
				}
				Powerup powerup = powerups[1];
				powerups[1] = null;
				if (powerup.getName().equals(RESTORE)) {
					if (player.getHearts() < 5) {
						player.incHearts();
					}
				} else if (powerup.getName().equals(INVINCIBLE)) {
					player.setInvincible(true);
					Timer timerForInvincibility = new Timer();
					TimerTask timerTaskForInvincibility = new TimerTask() {

						@Override
						public void run() {
							player.setInvincible(false);
						}
					};
					timerForInvincibility.schedule(timerTaskForInvincibility, 10000);
				} else if (powerup.getName().equals(SWAP)) {
					int playerHearts = players[0].getHearts();
					int opponentHearts = players[1].getHearts();
					players[0].setHearts(opponentHearts);
					players[1].setHearts(playerHearts);
				}
			}
		}

		// check player
		if (ball.getRect().intersects(players[0].getRect()) && players[0].isPunching() && !playerReversing) {
			// ball is hit by punching

			System.out.println("Reversing ball!");
			playerReversing = true;
			ball.reverseDirection();
			if (ballPeriod >= 20) {
				if (players[0].getPunchDelay() <= 900) {
					players[0].setPunchDelay(players[0].getPunchDelay() + 100);
				} else {
					players[0].setPunchDelay(0);
				}
				ballPeriod -= 10;
				initBallTimer(ballPeriod, players[0]);
			}
			System.out.println("ball perdio => " + ballPeriod);
		} else if (!ball.getRect().intersects(players[0].getBiggerRect())) {
			// System.out.println("End reversing");
			playerReversing = false;
		}

		// check opponent
		if (ball.getRect().intersects(players[1].getRect()) && players[1].isPunching() && !opponentReversing) {
			// ball is hit by punching

			System.out.println("Reversing ball!");
			opponentReversing = true;
			ball.reverseDirection();
			if (ballPeriod >= 20) {
				if (players[1].getPunchDelay() <= 900) {
					players[1].setPunchDelay(players[1].getPunchDelay() + 100);
				} else {
					players[1].setPunchDelay(0);
				}
				ballPeriod -= 10;
				initBallTimer(ballPeriod, players[1]);
			}
			System.out.println("ball perdio => " + ballPeriod);
		} else if (!ball.getRect().intersects(players[1].getBiggerRect())) {
			// System.out.println("End reversing");
			opponentReversing = false;
		}
	}
}
