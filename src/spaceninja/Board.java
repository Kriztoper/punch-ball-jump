package spaceninja;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import network.entities.ServerData;
import views.GameFrame;

public class Board extends JPanel implements Commons {
	public Ball ball;
	public Player[] players;
	public Powerup[] powerups;
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
	public boolean isPlayerPowerupActivated;
	public boolean isOpponentPowerupActivated;
	private Color pCaptionColor;
	public String pCaptionMsg;
	private Color oCaptionColor;
	public String oCaptionMsg;
	public int countdown;
	private ImageIcon bg;
	private ImageIcon earth;
	public int round = 1;
	private boolean playerTop;
	private ArrayList<Sprite> p1Hearts;
	private ArrayList<Sprite> p2Hearts;
	private ArrayList<Sprite> playerHeads;
	private GameFrame gameFrame;
	private boolean isComputer;
	private int difficulty;
	public boolean isPaused;
	public boolean clientIsPaused;
	public int dialogResult;

	// Server field types
	public DatagramSocket socket;
	private boolean listening;

	public Board(GameFrame gameFrame, boolean isComputer, int difficulty) {
		this.gameFrame = gameFrame;
		this.isComputer = isComputer;
		this.difficulty = difficulty;

		// Init bg and earth images
		Random random = new Random();
		// String bgName = random.nextBoolean() ? "test" : "test2";
		// String earthName = random.nextBoolean() ? "earth" : "earth2";
		bg = new ImageIcon(getClass().getClassLoader().getResource("images/test2.png"));// " + bgName + ".png"));
		earth = new ImageIcon(getClass().getClassLoader().getResource("images/earth2.png"));// + earthName + ".png"));
		repaint();

		// init Powerups only once
		String[] powerupsArr = { RESTORE, INVINCIBLE, SWAP };
		playerPowerupsList = new ArrayList<Powerup>();
		opponentPowerupsList = new ArrayList<Powerup>();
		for (int i = 0; i < powerupsArr.length; i++) {
			playerPowerupsList.add(new Powerup(INIT_PLAYER_POWERUP_X, INIT_PLAYER_POWERUP_Y, powerupsArr[i]));
			opponentPowerupsList.add(new Powerup(INIT_OPPONENT_POWERUP_X, INIT_OPPONENT_POWERUP_Y, powerupsArr[i]));
		}
		powerups = new Powerup[2];

		// Init players' hearts
		p1Hearts = new ArrayList<Sprite>();
		p2Hearts = new ArrayList<Sprite>();

		for (int i = 0, x = 10, y = 13; i < 5; i++, x += 40) {
			p1Hearts.add(new Sprite(x + 45, y, "images/heart.png"));
			p2Hearts.add(new Sprite(x + 390, y, "images/heart.png"));
		}

		playerHeads = new ArrayList<Sprite>();
		playerHeads.add(new Sprite(5, 10, "images/player1_head.png"));
		playerHeads.add(new Sprite(600, 10, "images/player2_head.png"));

		repaint();
		initBoard();
		gameInit();

		if (isComputer == IS_NOT_COMPUTER) {
			listening = true;
			Handler handler = new Handler();
			handler.start();
		}
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
		timerForBall.schedule(scheduleTaskForBall, 3000, ballPeriod);

		repaint();
	}

	public void initBallTimer(long period, Player player) {
		if (!isPaused && !clientIsPaused) {
			if (period >= 20) {
				timerForBall.cancel();
				timerForBall = new Timer();
				scheduleTaskForBall = new ScheduleTaskForBall();
				timerForBall.schedule(scheduleTaskForBall, 0, period);
			} else {
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
	}

	@Override
	public void addNotify() {
		super.addNotify();
	}

	private void gameInit() {
		int[] hearts = new int[2];
		if (players != null && players[0] != null && players[1] != null) {
			// Players 1 & 2 not null so hearts maintain
			hearts[0] = players[0].getHearts();
			hearts[1] = players[1].getHearts();
		} else {
			players = new Player[2];
			hearts[0] = 5;
			hearts[1] = 5;
		}
		playerTop = playerTop ? false : true;
		if (playerTop) {
			players[0] = new Player(INIT_PLAYER_X, INIT_PLAYER_Y, PLAYER, hearts[0], IS_NOT_COMPUTER, HUMAN, playerTop);
			players[1] = new Player(INIT_OPPONENT_X, INIT_OPPONENT_Y, OPPONENT, hearts[1], isComputer, difficulty,
					!playerTop);
		} else {
			players[0] = new Player(INIT_OPPONENT_X, INIT_OPPONENT_Y, PLAYER, hearts[0], IS_NOT_COMPUTER, HUMAN,
					playerTop);
			players[1] = new Player(INIT_PLAYER_X, INIT_PLAYER_Y, OPPONENT, hearts[1], isComputer, difficulty,
					!playerTop);
		}
		ball = new Ball();
		playerReversing = false;
		opponentReversing = false;
		repaint();

		// Prints the values of the lists setup, used for debugging
		// for (Powerup x : playerPowerupsList)
		// System.out.println(x.getName());
		// for (Powerup x : opponentPowerupsList)
		// System.out.println(x.getName());

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
			// Planting powerups for player
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
			// Planting powerups for opponent
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

		isPaused = false;
		clientIsPaused = false;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (!isPaused && !clientIsPaused) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;

			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

			// draw background image
			g2d.drawImage(bg.getImage(), 0, 0, bg.getIconWidth(), bg.getIconHeight(), this);

			// [ESC] Back to Menu
			g2d.setColor(Color.WHITE);
			g2d.drawString("[ESC] Back to Menu", 500, 640);

			// draw player heads
			for (int i = 0; i < playerHeads.size(); i++) {
				Sprite playerHead = playerHeads.get(i);
				g2d.drawImage(playerHead.getImage(), playerHead.getX(), playerHead.getY(), playerHead.getWidth(),
						playerHead.getHeight(), this);
			}

			// draw Earth at the middle
			g2d.drawImage(earth.getImage(), 160, 170, earth.getIconWidth(), earth.getIconHeight(), this);

			drawObjects(g2d);
			// countdown
			if (countdown > 0) {
				g.setColor(Color.RED);
				g.setFont(new Font("Open Sans", Font.PLAIN, 64));
				g.drawString("ROUND " + round, 180, 350);
				g.setColor(new Color(0f, 0f, 0f, .25f));
				g.fillRect(0, 0, Commons.WIDTH, Commons.HEIGHT);
			} else if (countdown == 0) {
				g.setColor(Color.RED);
				g.setFont(new Font("Open Sans", Font.PLAIN, 64));
				g.drawString("START!", 230, 350);
			}
			if (!ingame) {
				gameFinished(g2d);
			}

			Toolkit.getDefaultToolkit().sync();
		}
	}

	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img
	 *            The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
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
		if (isPlayerPowerupActivated || isOpponentPowerupActivated) {
			g2d.setColor(new Color(0f, 0f, 0f, 0.5f));
			g2d.fillRect(0, 55, Commons.WIDTH, 40);
		}
		if (isPlayerPowerupActivated) {
			g2d.setColor(pCaptionColor);
			g2d.setFont(new Font("Open Sans", Font.PLAIN, 20));
			g2d.drawString(pCaptionMsg, 30, 80);
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
		if (isOpponentPowerupActivated) {
			g2d.setColor(oCaptionColor);
			g2d.setFont(new Font("Open Sans", Font.PLAIN, 20));
			g2d.drawString(oCaptionMsg, 400, 80);
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
			// draw hearts
			for (int i = 0; i < player.getHearts(); i++) {
				Sprite heart = null;
				if (player.getName().equals(PLAYER)) {
					heart = p1Hearts.get(i);
				} else if (player.getName().equals(OPPONENT)) {
					heart = p2Hearts.get(i);
				}
				g2d.drawImage(heart.getImage(), heart.getX(), heart.getY(), heart.getWidth(), heart.getHeight(), this);
			}

			// Draw bigger rect around image which is used for visualization, debugging, etc
			// NOTE: Uncomment to draw
			// int width = player.getWidth() * 3;
			// int height = player.getHeight() * 3;
			// int x = player.getX() - player.getWidth();
			// int y = player.getY() - player.getHeight();
			// g2d.setColor(Color.RED);
			// g2d.fillRect(x, y, width, height);
			if (player.isAlive()) {
				if (player.isInvincible()) {
					// Displays indicator like forcefield if player is invincible
					int x = player.getSlightlyBiggerRect().x;
					int y = player.getSlightlyBiggerRect().y;
					int w = player.getSlightlyBiggerRect().width;
					int h = player.getSlightlyBiggerRect().height;
					g2d.setColor(new Color(0f, 1f, 0f, 0.5f));
					g2d.fillOval(x, y, w, h);
				}

				// Draw bigger rect around image which is used for visualization, debugging, etc
				// NOTE: Uncomment to draw
				// if (player.getName().equals(OPPONENT)) {
				// int x = player.getX() - player.getWidth() - (int) player.rectBorder;
				// int y = player.getY() - player.getHeight() * 3;
				// int width = player.getWidth() * 3 + 2 * (int) player.rectBorder;
				// int height = player.getHeight() * 7;
				// g2d.setColor(new Color(1f, 0f, 0f, 0.5f));
				// g2d.fillRect(x, y, width, height);
				// }

				if (!player.isTop()) {
					BufferedImage image = toBufferedImage(player.isPunching() ? player.getPunchingImage()
							: (player.isJumping() ? player.getJumpingImage()
									: (player.isInvincible() ? player.getWithShieldImage() : player.getImage())));
					double rotationRequired = Math.toRadians(180);
					double locationX = image.getWidth(null) / 2;
					double locationY = image.getHeight(null) / 2;
					AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
					AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

					if (player.isPunching() || player.isJumping()) {
						if (player.getX() < ball.getX()) {
							// punch/jump right
							g2d.drawImage(op.filter(image, null), player.getX() + player.getWidth(), player.getY(),
									-player.getWidth(), player.getHeight(), null);
						} else {
							// punch/jump left
							g2d.drawImage(op.filter(image, null), player.getX(), player.getY(), player.getWidth(),
									player.getHeight(), this);
						}
					} else {
						if (player.isInvincible()) {
							if (player.getX() < ball.getX()) {
								// face right
								g2d.drawImage(op.filter(image, null), player.getX(), player.getY(), player.getWidth(),
										player.getHeight(), this);
							} else {
								// face left
								g2d.drawImage(op.filter(image, null), player.getX() + player.getWidth(), player.getY(),
										-player.getWidth(), player.getHeight(), null);
							}
						} else {
							if (player.getX() < ball.getX()) {
								// face right
								g2d.drawImage(op.filter(image, null), player.getX(), player.getY(), player.getWidth(),
										player.getHeight(), this);
							} else {
								// face left
								g2d.drawImage(op.filter(image, null), player.getX() + player.getWidth(), player.getY(),
										-player.getWidth(), player.getHeight(), null);
							}
						}
					}
				} else {
					if (player.isPunching()) {
						if (player.getX() < ball.getX()) {
							// punch right
							g2d.drawImage(player.getPunchingImage(), player.getX(), player.getY(), player.getWidth(),
									player.getHeight(), this);
						} else {
							// punch left
							g2d.drawImage(player.getPunchingImage(), player.getX() + player.getWidth(), player.getY(),
									-player.getWidth(), player.getHeight(), null);
						}
					} else if (player.isJumping()) {
						if (player.getX() < ball.getX()) {
							// punch right
							g2d.drawImage(player.getJumpingImage(), player.getX(), player.getY(), player.getWidth(),
									player.getHeight(), this);
						} else {
							// punch left
							g2d.drawImage(player.getJumpingImage(), player.getX() + player.getWidth(), player.getY(),
									-player.getWidth(), player.getHeight(), null);
						}
					} else {
						if (player.isInvincible()) {
							if (player.getX() < ball.getX()) {
								// face right
								g2d.drawImage(player.getWithShieldImage(), player.getX() + player.getWidth(),
										player.getY(), -player.getWidth(), player.getHeight(), null);
							} else {
								// face left
								g2d.drawImage(player.getWithShieldImage(), player.getX(), player.getY(),
										player.getWidth(), player.getHeight(), this);
							}
						} else {
							if (player.getX() < ball.getX()) {
								// face right
								g2d.drawImage(player.getImage(), player.getX() + player.getWidth(), player.getY(),
										-player.getWidth(), player.getHeight(), null);
							} else {
								// face left
								g2d.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth(),
										player.getHeight(), this);
							}
						}
					}
				}
			} else {
				if (!player.isTop()) {
					BufferedImage image = toBufferedImage(player.getDeadImage());
					double rotationRequired = Math.toRadians(180);
					double locationX = image.getWidth(null) / 2;
					double locationY = image.getHeight(null) / 2;
					AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
					AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

					if (player.getX() < ball.getX()) {
						g2d.drawImage(op.filter(image, null), player.getX(), INIT_OPPONENT_Y - player.getHeight() / 2,
								player.getWidth(), player.getHeight(), this);
					} else {
						g2d.drawImage(op.filter(image, null), player.getX() + player.getWidth(),
								INIT_OPPONENT_Y - player.getHeight() / 2, -player.getWidth(), player.getHeight(), null);
					}
				} else {
					if (player.getX() < ball.getX()) {
						g2d.drawImage(player.getDeadImage(), player.getX() + player.getWidth(),
								INIT_PLAYER_Y + player.getHeight() / 2, -player.getWidth(), player.getHeight(), null);
					} else {
						g2d.drawImage(player.getDeadImage(), player.getX(), INIT_PLAYER_Y + player.getHeight() / 2,
								player.getWidth(), player.getHeight(), this);
					}
				}
			}
		}
		g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), this);

	}

	private void gameFinished(Graphics2D g2d) {
		int winner = players[0].getHearts() <= 0 ? 2 : 1;
		g2d.setColor(Color.RED);
		g2d.setFont(new Font("Open Sans", Font.PLAIN, 64));
		g2d.drawString("PLAYER " + winner + " WINS!", 90, 350);
		g2d.setColor(new Color(0f, 0f, 0f, .25f));
		g2d.fillRect(0, 0, Commons.WIDTH, Commons.HEIGHT);
	}

	private class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				isPaused = true;
				gameFrame.menuDialog.getYesButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						gameFrame.menuDialog.setModal(false);
						gameFrame.menuDialog.setVisible(false);
						gameFrame.menuDialog.dispose();
						dialogResult = 1;
						gameFrame.setCurrentPanel("menuPanel");
						players[0].setHearts(0);
						players[1].setHearts(0);
						listening = false;
						if (socket != null && !socket.isClosed()) {
							socket.close();
						}
					}
				});
				gameFrame.menuDialog.getNoButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						gameFrame.menuDialog.setModal(false);
						gameFrame.menuDialog.setVisible(false);
						gameFrame.menuDialog.dispose();
						isPaused = false;
					}
				});
				gameFrame.menuDialog.setVisible(true);
				gameFrame.menuDialog.requestFocus();
			}
			if (!isPaused && !clientIsPaused) {
				if (!pressed) {
					for (Player player : players) {
						player.keyPressed(e);
					}
					pressed = true;
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (!isPaused && !clientIsPaused) {
				pressed = false;
			}
		}
	}

	private class ScheduleTaskForPlayer extends TimerTask {
		@Override
		public void run() {
			if (!isPaused && !clientIsPaused) {
				if (players[1].isComputer) {
					players[1].generateMove(ball, ballPeriod);
				}
				for (Player player : players)
					player.move();
				repaint();
				checkCollision(); // Note: checkCollision must be after repaint and it must be assigned to the
									// Timer with the smallest period
			}
		}
	}

	private class ScheduleTaskForBall extends TimerTask {
		@Override
		public void run() {
			if (!isPaused && !clientIsPaused) {
				ball.move();
				repaint();
			}
		}
	}

	private void checkCollision() {
		// check on both players if ball hits them
		for (Player player : players) {
			if (ball.getRect().intersects(player.getRect()) && !player.isPunching() && !player.isInvincible()) {
				// player is hit by ball and is not punching
				player.setAlive(false);
				round++;
				if (player.getName().equals(PLAYER)) {
					players[0].decHearts();
				} else {
					players[1].decHearts();
				}
				// clear all displayed powerups
				powerups[0] = null;
				powerups[1] = null;

				// all 5 hearts have been consumed by a player therefore that player loses
				if (players[0].getHearts() <= 0 || players[1].getHearts() <= 0) {
					ingame = false;
					timerForBall.cancel();
					timerForPlayer.cancel();
				} else {
					timerForBall.cancel();
					timerForPlayer.cancel();

					Timer timerForReset = new Timer();
					TimerTask timerTaskForReset = new TimerTask() {

						@Override
						public void run() {
							initBoard();
							gameInit();
						}
					};
					timerForReset.schedule(timerTaskForReset, 1000);
				}
			} else if (powerups[0] != null && player.getRect().intersects(powerups[0].getRect())) { // top powerups
				if (player.getName().equals(PLAYER)) {
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
				} else {
					isOpponentPowerupActivated = true;
					if (powerups[0].getName().equals(RESTORE)) {
						oCaptionColor = Color.RED;
						oCaptionMsg = "+1 Heart";
					} else if (powerups[0].getName().equals(INVINCIBLE)) {
						oCaptionColor = Color.GREEN;
						oCaptionMsg = "INVINCIBLE for 10 sec";
					} else if (powerups[0].getName().equals(SWAP)) {
						oCaptionColor = Color.YELLOW;
						oCaptionMsg = "Swap hearts";
					}
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
			} else if (powerups[1] != null && player.getRect().intersects(powerups[1].getRect())) { // bot powerups
				if (player.getName().equals(PLAYER)) {
					isPlayerPowerupActivated = true;
					if (powerups[1].getName().equals(RESTORE)) {
						pCaptionColor = Color.RED;
						pCaptionMsg = "+1 Heart";
					} else if (powerups[1].getName().equals(INVINCIBLE)) {
						pCaptionColor = Color.GREEN;
						pCaptionMsg = "INVINCIBLE for 10 sec";
					} else if (powerups[1].getName().equals(SWAP)) {
						pCaptionColor = Color.YELLOW;
						pCaptionMsg = "Swap hearts";
					}
				} else {
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
		} else if (!ball.getRect().intersects(players[0].getBiggerRect())) {
			playerReversing = false;
		}

		// check opponent
		if (ball.getRect().intersects(players[1].getRect()) && players[1].isPunching() && !opponentReversing) {
			// ball is hit by punching

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
		} else if (!ball.getRect().intersects(players[1].getBiggerRect())) {
			opponentReversing = false;
		}
	}

	public Ball getBall() {
		return ball;
	}

	public Player[] getPlayers() {
		return players;
	}

	public Powerup[] getPowerups() {
		return powerups;
	}

	public String[] getCaptions() {
		String[] captions = { pCaptionMsg, oCaptionMsg };
		return captions;
	}

	// Server code
	private class Handler extends Thread {
		@Override
		public void run() {
			try {
				socket = new DatagramSocket(PORT1);
				while (listening && !socket.isClosed()) {
					byte[] buffer = new byte[14];
					DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
					if (!socket.isClosed()) {
						socket.receive(datagramPacket);
					}

					String[] recData = new String(datagramPacket.getData()).split(",");
					clientIsPaused = Boolean.parseBoolean(recData[1].trim());
					if (recData[0] != null && (recData[0].trim().equals("75") || recData[0].trim().equals("76"))) {
						getPlayers()[1].keyPressed(Integer.parseInt(recData[0].trim()));
					}

					// send to client
					String p1Powerup = null;
					int p1PowerupX = -1;
					int p1PowerupY = -1;
					String p2Powerup = null;
					int p2PowerupX = -1;
					int p2PowerupY = -1;
					String pUpTopMsg = isPlayerPowerupActivated ? pCaptionMsg : null;
					String pUpBotMsg = isOpponentPowerupActivated ? oCaptionMsg : null;
					if (powerups[0] != null) {
						p1Powerup = powerups[0].getName();
						p1PowerupX = powerups[0].getX();
						p1PowerupY = powerups[0].getY();
					}
					if (powerups[1] != null) {
						p2Powerup = powerups[1].getName();
						p2PowerupX = powerups[1].getX();
						p2PowerupY = powerups[1].getY();
					}

					String data = new ServerData(ball.getX(), ball.getY(), players[0].getX(), players[0].getY(),
							players[1].getX(), players[1].getY(), players[0].isJumping(), players[0].isPunching(),
							players[1].isJumping(), players[1].isPunching(), p1Powerup, p1PowerupX, p1PowerupY,
							p2Powerup, p2PowerupX, p2PowerupY, pUpTopMsg, pUpBotMsg, players[0].getHearts(),
							players[1].getHearts(), players[0].isAlive(), players[1].isAlive(),
							players[0].isInvincible(), players[1].isInvincible(), countdown, round, isPaused)
									.getCommaSeparatedStringData();
					datagramPacket = new DatagramPacket(data.getBytes(), data.length(), datagramPacket.getAddress(),
							datagramPacket.getPort());
					if (!socket.isClosed()) {
						socket.send(datagramPacket);
					}
				}
			} catch (SocketException e) {
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				socket.close();
				System.out.println("Closing server socket connection...");
			}
		}
	}
}
