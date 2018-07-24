package spaceninja;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import network.entities.ServerData;
import views.GameFrame;

public class ClientBoard extends JPanel implements Commons {
	private Ball ball;
	private Player[] players;
	private Powerup[] powerups;
	private boolean pressed = false;
	private String pCaptionMsg;
	private String oCaptionMsg;
	private int countdown;
	private ImageIcon bg;
	private ImageIcon earth;
	private int round = 1;
	private ArrayList<Sprite> p1Hearts;
	private ArrayList<Sprite> p2Hearts;
	private ArrayList<Sprite> playerHeads;
	private GameFrame gameFrame;
	private int key;
	public boolean isPaused;

	// Client field types
	DatagramPacket datagramPacket;
	private boolean isGameOver;
	private InetAddress serverIPAddress;
	public DatagramSocket socket;
	Socket tcpSocket;

	public ClientBoard(GameFrame gameFrame, InetAddress serverIPAddress) {
		this.gameFrame = gameFrame;
		this.serverIPAddress = serverIPAddress;

		// Init bg and earth images
		Random random = new Random();
		// String bgName = random.nextBoolean() ? "test" : "test2";
		// String earthName = random.nextBoolean() ? "earth" : "earth2";
		bg = new ImageIcon(getClass().getClassLoader().getResource("images/test2.png"));// " + bgName + ".png"));
		earth = new ImageIcon(getClass().getClassLoader().getResource("images/earth2.png"));// " + earthName + ".png"));
		repaint();

		// init Powerups only once
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

		Handler handler = new Handler();
		handler.start();
	}

	private void initBoard() {
		setLayout(null);
		addKeyListener(new TAdapter());
		setFocusable(true);

		setDoubleBuffered(true);

		repaint();
	}

	@Override
	public void addNotify() {
		super.addNotify();
	}

	private void gameInit() {
		players = new Player[2];
		players[0] = new Player(INIT_PLAYER_X, INIT_PLAYER_Y, PLAYER, 5, IS_NOT_COMPUTER, HUMAN, true);
		players[1] = new Player(INIT_OPPONENT_X, INIT_OPPONENT_Y, OPPONENT, 5, IS_NOT_COMPUTER, HUMAN, false);
		ball = new Ball();
		key = -1;
		powerups[0] = null;
		powerups[1] = null;
		pCaptionMsg = null;
		oCaptionMsg = null;
		isPaused = false;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (!isPaused) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;

			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

			// draw background image
			g2d.drawImage(bg.getImage(), 0, 0, bg.getIconWidth(), bg.getIconHeight(), this);
			repaint();

			// [ESC] Back to Menu
			g2d.setColor(Color.WHITE);
			g2d.drawString("[ESC] Back to Menu", 500, 640);
			repaint();

			// draw player heads
			for (int i = 0; i < playerHeads.size(); i++) {
				Sprite playerHead = playerHeads.get(i);
				g2d.drawImage(playerHead.getImage(), playerHead.getX(), playerHead.getY(), playerHead.getWidth(),
						playerHead.getHeight(), this);
				repaint();
			}

			// draw Earth at the middle
			g2d.drawImage(earth.getImage(), 160, 170, earth.getIconWidth(), earth.getIconHeight(), this);
			repaint();

			drawObjects(g2d);
			// countdown
			if (countdown > 0) {
				g.setColor(Color.RED);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 64));
				g.drawString("ROUND " + round, 160, 350);
				g.setColor(new Color(0f, 0f, 0f, .25f));
				g.fillRect(0, 0, Commons.WIDTH, Commons.HEIGHT);
				repaint();
			} else if (countdown == 0) {
				g.setColor(Color.RED);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 64));
				g.drawString("START!", 210, 350);
				repaint();
			}
			if (players[0].getHearts() <= 0 || players[1].getHearts() <= 0) {
				gameFinished(g2d);
				repaint();
			}

			if (socket != null && !socket.isClosed()) {
				DatagramPacket datagramPacket = new DatagramPacket((key + "").getBytes(), (key + "").length(),
						serverIPAddress, PORT1);
				try {
					if (!socket.isClosed()) {
						socket.send(datagramPacket);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
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
				repaint();
			}
		}

		// draw player powerups activated caption
		if (pCaptionMsg != null || oCaptionMsg != null) {
			g2d.setColor(new Color(0f, 0f, 0f, 0.5f));
			g2d.fillRect(0, 55, Commons.WIDTH, 40);
			repaint();
		}
		if (pCaptionMsg != null) {
			if (pCaptionMsg.equals("+1 Heart")) {
				g2d.setColor(Color.RED);
			} else if (pCaptionMsg.equals("INVINCIBLE for 10 sec")) {
				g2d.setColor(Color.GREEN);
			} else if (pCaptionMsg.equals("Swap hearts")) {
				g2d.setColor(Color.YELLOW);
			}
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g2d.drawString(pCaptionMsg, 30, 80);
			repaint();
		}
		// draw opponent powerups activated caption
		if (oCaptionMsg != null) {
			if (oCaptionMsg.equals("+1 Heart")) {
				g2d.setColor(Color.RED);
			} else if (oCaptionMsg.equals("INVINCIBLE for 10 sec")) {
				g2d.setColor(Color.GREEN);
			} else if (oCaptionMsg.equals("Swap hearts")) {
				g2d.setColor(Color.YELLOW);
			}
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g2d.drawString(oCaptionMsg, 400, 80);
			repaint();
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
				repaint();
			}

			if (player.isAlive()) {
				if (player.isInvincible()) {
					int x = player.getSlightlyBiggerRect().x;
					int y = player.getSlightlyBiggerRect().y;
					int w = player.getSlightlyBiggerRect().width;
					int h = player.getSlightlyBiggerRect().height;
					g2d.setColor(new Color(0f, 1f, 0f, 0.5f));
					g2d.fillOval(x, y, w, h);
					repaint();
				}

				if (player.getY() >= 325) {
					BufferedImage image = toBufferedImage(player.isPunching() ? player.getPunchingImage()
							: (player.isJumping() ? player.getJumpingImage() : player.getImage()));
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
							repaint();
						} else {
							// punch/jump left
							g2d.drawImage(op.filter(image, null), player.getX(), player.getY(), player.getWidth(),
									player.getHeight(), this);
							repaint();
						}
					} else {
						if (player.getX() < ball.getX()) {
							// face right
							g2d.drawImage(op.filter(image, null), player.getX(), player.getY(), player.getWidth(),
									player.getHeight(), this);
							repaint();
						} else {
							// face left
							g2d.drawImage(op.filter(image, null), player.getX() + player.getWidth(), player.getY(),
									-player.getWidth(), player.getHeight(), null);
							repaint();
						}
					}
				} else {
					if (player.isPunching()) {
						if (player.getX() < ball.getX()) {
							// punch right
							g2d.drawImage(player.getPunchingImage(), player.getX(), player.getY(), player.getWidth(),
									player.getHeight(), this);
							repaint();
						} else {
							// punch left
							g2d.drawImage(player.getPunchingImage(), player.getX() + player.getWidth(), player.getY(),
									-player.getWidth(), player.getHeight(), null);
							repaint();
						}
					} else if (player.isJumping()) {
						if (player.getX() < ball.getX()) {
							// punch right
							g2d.drawImage(player.getJumpingImage(), player.getX(), player.getY(), player.getWidth(),
									player.getHeight(), this);
							repaint();
						} else {
							// punch left
							g2d.drawImage(player.getJumpingImage(), player.getX() + player.getWidth(), player.getY(),
									-player.getWidth(), player.getHeight(), null);
							repaint();
						}
					} else {
						// stand still
						if (player.getX() < ball.getX()) {
							g2d.drawImage(player.getImage(), player.getX() + player.getWidth(), player.getY(),
									-player.getWidth(), player.getHeight(), null);
							repaint();
						} else {
							g2d.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth(),
									player.getHeight(), this);
							repaint();
						}
					}
				}
			} else {
				if (player.getY() >= 325) {
					BufferedImage image = toBufferedImage(player.getDeadImage());
					double rotationRequired = Math.toRadians(180);
					double locationX = image.getWidth(null) / 2;
					double locationY = image.getHeight(null) / 2;
					AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
					AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

					if (player.getX() < ball.getX()) {
						g2d.drawImage(op.filter(image, null), player.getX(), INIT_OPPONENT_Y - player.getHeight() / 2,
								player.getWidth(), player.getHeight(), this);
						repaint();
					} else {
						g2d.drawImage(op.filter(image, null), player.getX() + player.getWidth(),
								INIT_OPPONENT_Y - player.getHeight() / 2, -player.getWidth(), player.getHeight(), null);
						repaint();
					}
				} else {
					if (player.getX() < ball.getX()) {
						g2d.drawImage(player.getDeadImage(), player.getX() + player.getWidth(),
								INIT_PLAYER_Y + player.getHeight() / 2, -player.getWidth(), player.getHeight(), null);
						repaint();
					} else {
						g2d.drawImage(player.getDeadImage(), player.getX(), INIT_PLAYER_Y + player.getHeight() / 2,
								player.getWidth(), player.getHeight(), this);
						repaint();
					}
				}
			}
		}
		g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), this);
		repaint();
	}

	public void updateGraphics(int ballX, int ballY, int player1X, int player1Y, int player2X, int player2Y,
			boolean p1IsJumping, boolean p1IsPunching, boolean p2IsJumping, boolean p2IsPunching, String p1Powerup,
			int p1PowerupX, int p1PowerupY, String p2Powerup, int p2PowerupX, int p2PowerupY, String powUpTopMsg,
			String powUpBotMsg, int p1Hearts, int p2Hearts, boolean p1IsAlive, boolean p2IsAlive,
			boolean p1IsInvincible, boolean p2IsInvincible, int countdown, int round, boolean isPaused) {
		if (ball != null) {
			ball.setX(ballX);
			ball.setY(ballY);
		}
		if (players != null) {
			if (players[0] != null) {
				players[0].setX(player1X);
				players[0].setY(player1Y);
				players[0].setJumping(p1IsJumping);
				players[0].setPunching(p1IsPunching);
				players[0].setHearts(p1Hearts);
				players[0].setAlive(p1IsAlive);
				players[0].setInvincible(p1IsInvincible);
			}
			if (players[1] != null) {
				players[1].setX(player2X);
				players[1].setY(player2Y);
				players[1].setJumping(p2IsJumping);
				players[1].setPunching(p2IsPunching);
				players[1].setHearts(p2Hearts);
				players[1].setAlive(p2IsAlive);
				players[1].setInvincible(p2IsInvincible);
			}
		}
		if (powerups != null) {
			if (p1Powerup != null && !p1Powerup.equals("null")) {
				powerups[0] = new Powerup(p1PowerupX, p1PowerupY, p1Powerup);
			} else {
				powerups[0] = null;
			}
			if (p2Powerup != null && !p2Powerup.equals("null")) {
				powerups[1] = new Powerup(p2PowerupX, p2PowerupY, p2Powerup);
			} else {
				powerups[1] = null;
			}
		}
		if (powUpTopMsg != null && !powUpTopMsg.equals("null")) {
			pCaptionMsg = powUpTopMsg;
		} else {
			pCaptionMsg = null;
		}
		if (powUpBotMsg != null && !powUpBotMsg.equals("null")) {
			oCaptionMsg = powUpBotMsg;
		} else {
			oCaptionMsg = null;
		}
		this.countdown = countdown;
		this.round = round;
		this.isPaused = isPaused;
		repaint();
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
			if (!isPaused) {
				if (!pressed) {
					if (e.getKeyCode() == KeyEvent.VK_K) {
						// K Pressed!
						key = e.getKeyCode();
					} else if (e.getKeyCode() == KeyEvent.VK_L) {
						// L Pressed!
						key = e.getKeyCode();
					} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						isPaused = true;
						int dialogResult = -1;
						if (JOptionPane.WHEN_FOCUSED != JOptionPane.WHEN_IN_FOCUSED_WINDOW) {
							dialogResult = JOptionPane.showConfirmDialog(null, "Return to Main Menu?", "Warning",
									JOptionPane.YES_NO_OPTION);
						}
						isPaused = false;
						if (dialogResult == JOptionPane.YES_OPTION) {
							gameFrame.setCurrentPanel("menuPanel");
							isGameOver = true;
							if (socket != null && !socket.isClosed()) {
								socket.close();
							}
						}
					}
					pressed = true;
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (!isPaused) {
				key = -1;
				pressed = false;
			}
		}
	}

	private class Handler extends Thread {
		@Override
		public void run() {
			try {
				// tcpSocket = new Socket(serverIPAddress, TCP_PORT);

				// if (tcpSocket.isConnected()) {
				socket = new DatagramSocket();
				// clientSocket.connect(serverIPAddress, PORT1);
				// clientSocketToSend = new DatagramSocket();
				byte[] buffer;
				isGameOver = false;
				while (!isGameOver && !socket.isClosed()) {
					// System.out.println("1 server: " + socket.getInetAddress() + " " +
					// socket.getPort() + " client: "
					// + socket.getLocalAddress() + " " + socket.getLocalPort());

					// System.out.println("2 server: " + socket.getInetAddress() + " " +
					// socket.getPort() + " client: "
					// + socket.getLocalAddress() + " " + socket.getLocalPort());
					buffer = new byte[65507];
					datagramPacket = new DatagramPacket(buffer, buffer.length);
					if (!socket.isClosed()) {
						socket.receive(datagramPacket);
					}
					// System.out.println("3 server: " + socket.getInetAddress() + " " +
					// socket.getPort() + " client: "
					// + socket.getLocalAddress() + " " + socket.getLocalPort());

					String data = new String(datagramPacket.getData());
					// if (data != null) {
					// System.out.println("Received from server!");
					// }
					ServerData sd = new ServerData(data);
					updateGraphics(sd.ballX, sd.ballY, sd.player1X, sd.player1Y, sd.player2X, sd.player2Y,
							sd.p1IsJumping, sd.p1IsPunching, sd.p2IsJumping, sd.p2IsPunching, sd.p1Powerup,
							sd.p1PowerupX, sd.p1PowerupY, sd.p2Powerup, sd.p2PowerupX, sd.p2PowerupY, sd.powUpTopMsg,
							sd.powUpBotMsg, sd.p1Hearts, sd.p2Hearts, sd.p1IsAlive, sd.p2IsAlive, sd.p1IsInvincible,
							sd.p2IsInvincible, sd.countdown, sd.round, sd.isPaused);
				}
				// }
			} catch (SocketException e) {
				// e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// try {
				// tcpSocket.close();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				socket.close();
				// clientSocketToSend.close();
				System.out.println("Client connection closed.");
			}
		}
	}
}
