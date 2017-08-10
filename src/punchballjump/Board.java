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
	private Timer timer;
	private Timer ballTimer;
	private String message = "Game Over";
	private Ball ball;
	private Player player;
	private boolean ingame = true;
	private boolean reversing;
	private long ballPeriod;
	
	public Board() {
		initBoard();
	}
	
	private void initBoard() {
		setLayout(null);
		addKeyListener(new TAdapter());
		setFocusable(true);
		
		addEarthAtCenter();
		
		setDoubleBuffered(true);
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), 
				1000, 7);
		ballPeriod = 100;
		initBallTimer(ballPeriod);
	}
	
	private void addEarthAtCenter() {
		JLabel earthLabel = new JLabel();
		ImageIcon ii = new ImageIcon("res/bida.png");

		earthLabel.setIcon(ii);
		earthLabel.setSize(150, 150);
		earthLabel.setLocation(170, 170);
		add(earthLabel);
	}
	
	public void initBallTimer(long period) {
		System.out.println("Init ball timer");
		ballTimer = new Timer();
		ballTimer.scheduleAtFixedRate(new ScheduleTaskForBall(), 
				1000, period);
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		gameInit();
	}
	
	private void gameInit() {
		player = new Player();
		ball = new Ball();
		reversing = false;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
				RenderingHints.VALUE_RENDER_QUALITY);
		
		if (ingame) {
			drawObjects(g2d);
		} else {
			gameFinished(g2d);
		}
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawObjects(Graphics2D g2d) {
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), 
				player.getWidth(), player.getHeight(), this);
		g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), 
				ball.getWidth(), ball.getHeight(), this);
	}
	
	private void gameFinished(Graphics2D g2d) {
		Font font = new Font("Verdana", Font.BOLD, 18);
		FontMetrics metr = this.getFontMetrics(font);
		
		g2d.setColor(Color.BLACK);
		g2d.setFont(font);
		g2d.drawString(message, (Commons.WIDTH - metr.stringWidth(message)) / 2, 
				Commons.WIDTH / 2);
	}
	
	private class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}
	}
	
	private class ScheduleTask extends TimerTask {
		@Override
		public void run() {
			player.move();
			repaint();
			checkCollision(); // Note: checkCollision must be after repaint and it must be assigned to the Timer with the smallest period
		}
	}
	
	private class ScheduleTaskForBall extends TimerTask {
		@Override
		public void run() {
			ball.move();
			repaint();
			checkBallTimer();
		}
	}
	
	private void checkBallTimer() {
		if (ballPeriod <= 70 && ball.isSpeedDecreasable(player.x)) {
			System.out.println("period less than ten, " + ballPeriod);
			ballPeriod = 100;
			ballTimer.cancel();
			initBallTimer(ballPeriod);
		}
	}
	
	private void stopGame() {
		ingame = false;
		timer.cancel();
	}
	
	private void checkCollision() {
		if (ball.getRect().intersects(player.getRect()) &&
				player.isPunching() && !reversing) {
			System.out.println("Reversing ball!");
			reversing = true;
			ball.reverseDirection();
			initBallTimer(ballPeriod -= 10);
		} else if (ball.getRect().intersects(player.getRect()) &&
				!player.isPunching()) {
			System.out.println("Game Over");
			stopGame();
		} else if (!ball.getRect().intersects(player.getRect()) &&
				player.isPunching() && reversing) {
			System.out.println("End reversing");
			reversing = false;
		} else if (!ball.getRect().intersects(player.getRect()) &&
				!player.isPunching() && !reversing) {
			reversing = false;
		} 
    }
}
