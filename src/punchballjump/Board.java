package punchballjump;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
	private boolean reversing;
	private long ballPeriod;
	private ScheduleTaskForPlayer scheduleTaskForPlayer;
	private ScheduleTaskForBall scheduleTaskForBall;
	private Timer timerForPlayer;
	private Timer timerForBall;
	private Image img;

	public Board() {
		initBoard();
	}

	private void initBoard() {
		setLayout(null);
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLUE);
		
		img = Toolkit.getDefaultToolkit().createImage("images/bg2.png");
		addEarthAtCenter();

		setDoubleBuffered(true);

		scheduleTaskForPlayer = new ScheduleTaskForPlayer();
		timerForPlayer = new Timer();
		timerForPlayer.schedule(scheduleTaskForPlayer, 1000, 5);

		ballPeriod = 100;
		scheduleTaskForBall = new ScheduleTaskForBall();
		timerForBall = new Timer();
		timerForBall.schedule(scheduleTaskForBall, 3000, ballPeriod);
	}

	private void addEarthAtCenter() {
		JLabel earthLabel = new JLabel();
		ImageIcon ii = new ImageIcon("res/moom.png");

		earthLabel.setIcon(ii);
		earthLabel.setSize(340, 340);
		earthLabel.setLocation(160, 170);
		add(earthLabel);
	}

	public void initBallTimer(long period, Player player) {
		System.out.println("Init ball timer");
		if (period >= 10) {
			System.out.println("ballPeriod adjusting to " + period);
			timerForBall.cancel();
			timerForBall = new Timer();
			scheduleTaskForBall = new ScheduleTaskForBall();
			timerForBall.schedule(scheduleTaskForBall, 0, period);
		}
	}

	@Override
	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	private void gameInit() {
		players = new Player[2];
		players[0] = new Player(INIT_PLAYER_X, INIT_PLAYER_Y, PLAYER);
		players[1] = new Player(INIT_OPPONENT_X, INIT_OPPONENT_Y, OPPONENT);
		ball = new Ball();
		reversing = false;
	}

	@Override
	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);

		g.drawImage(img, 0, 0, null);
		g.drawString(String.valueOf(players[0].getScore()), 10, 10);
		g.drawString(String.valueOf(players[1].getScore()), 210, 10);

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
			g2d.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), this);
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
			for (Player player : players) {
				player.keyPressed(e);
			}
		}
	}

	private class ScheduleTaskForPlayer extends TimerTask {
		@Override
		public void run() {
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
		for (Player player : players) {
			if (ball.getRect().intersects(player.getRect()) && player.isPunching() && !reversing) {
				// ball is hit by punching

				System.out.println("Reversing ball!");
				reversing = true;
				ball.reverseDirection();
				if (ballPeriod >= 30) {
					if (player.getPunchDelay() <= 896) {
						player.setPunchDelay(player.getPunchDelay() + 112);
					} else {
						player.setPunchDelay(0);
					}
					ballPeriod -= 10;
					initBallTimer(ballPeriod, player);
				}
				System.out.println("ball perdio => " + ballPeriod);
			} else if (ball.getRect().intersects(player.getRect()) && !player.isPunching()) {
				// player is hit by ball and is not punching

				if (player.getName().equals("Player")) {
					players[1].incScore();
				} else {
					players[0].incScore();
				}

				System.out.println("Game Over");
				timerForBall.cancel();
				timerForPlayer.cancel();
				initBoard();
				gameInit();
			} else if (!ball.getRect().intersects(player.getRect()) && player.isPunching() && reversing) {
				System.out.println("End reversing");
				reversing = false;
			} else if (!ball.getRect().intersects(player.getRect()) && !player.isPunching() && !reversing) {
				reversing = false;
			}

		}
	}
}
