package punchballjump;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel implements Commons {
	private String message = "Game Over";
	private Ball ball;
	private Player[] players;
	private boolean ingame = true;
	private boolean playerReversing;
	private boolean opponentReversing;
	private long ballPeriod;
	private ScheduleTaskForPlayer scheduleTaskForPlayer;
	private ScheduleTaskForBall scheduleTaskForBall;
	private Timer timerForPlayer;
	private Timer timerForBall;
	private Timer timerForBallReset;
	private boolean pressed = false;

	public Board() {
		initBoard();
	}

	private void initBoard() {
		setLayout(null);
		addKeyListener(new TAdapter());
		setFocusable(true);

		addEarthAtCenter();

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

	private void addEarthAtCenter() {
		JLabel earthLabel = new JLabel();
		ImageIcon ii = new ImageIcon("res/bida.png");

		earthLabel.setIcon(ii);
		earthLabel.setSize(150, 150);
		earthLabel.setLocation(275, 255);
		add(earthLabel);
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
			ScheduleTaskForBallReset scheduleTaskForBallReset = new ScheduleTaskForBallReset();
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
		players[0] = new Player(INIT_PLAYER_X, INIT_PLAYER_Y, PLAYER, hearts[0], false);
		players[1] = new Player(INIT_OPPONENT_X, INIT_OPPONENT_Y, OPPONENT, hearts[1], IS_COMPUTER);
		ball = new Ball();
		playerReversing = false;
		opponentReversing = false;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawString(String.valueOf(players[0].getHearts()), 10, 10);
		g.drawString(String.valueOf(players[1].getHearts()), 210, 10);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		if (ingame) {
			drawObjects(g2d);
		} else {
			gameFinished(g2d);
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void drawObjects(Graphics2D g2d) {
		for (Player player : players) {
			// Draw bigger rect around image which is used for visualization, debugging, etc
			// NOTE: Uncomment to draw
			// int width = player.getWidth() * 3;
			// int height = player.getHeight() * 3;
			// int x = player.getX() - player.getWidth();
			// int y = player.getY() - player.getHeight();
			// g2d.setColor(Color.RED);
			// g2d.fillRect(x, y, width, height);

			g2d.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), this);
			// System.out.printf("x=%d, y=%d, w=%d, h=%d\n", x, y, width, height);
		}
		g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), this);

	}

	private void gameFinished(Graphics2D g2d) {
		Font font = new Font("Verdana", Font.BOLD, 18);
		FontMetrics metr = this.getFontMetrics(font);

		g2d.setColor(Color.BLACK);
		g2d.setFont(font);
		g2d.drawString(message, (Commons.WIDTH - metr.stringWidth(message)) / 2, Commons.WIDTH / 2);
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

	private class ScheduleTaskForBallReset extends TimerTask {
		@Override
		public void run() {
			ballPeriod = 100;
		}
	}

	private void checkCollision() {
		// check on both players if ball hits them
		for (Player player : players) {
			if (ball.getRect().intersects(player.getRect()) && !player.isPunching()) {
				// player is hit by ball and is not punching

				if (player.getName().equals("Player")) {
					players[0].decHearts();
				} else {
					players[1].decHearts();
				}

				System.out.println("Game Over");
				// all 5 hearts have been consumed by a player therefore that player loses
				if (players[0].getHearts() <= 0 || players[1].getHearts() <= 0) {
					System.exit(1); // TODO: Change this to code for returning back to menu panel
				}
				timerForBall.cancel();
				timerForPlayer.cancel();

				initBoard();
				gameInit();

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
