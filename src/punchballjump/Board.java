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
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel implements Commons {
	private Timer timer;
	private Timer ballTimer;
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
	private ExecutorService pool = Executors.newFixedThreadPool(2);
	ArrayList<Future> futures = new ArrayList<Future>(2);
	private String message = "Game Over";
	private Ball ball;
	private Player[] players;
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
		executor = new ScheduledThreadPoolExecutor(2);
		ScheduleTask scheduleTask = new ScheduleTask();
		executor.scheduleAtFixedRate(scheduleTask, 1000, 5, TimeUnit.MILLISECONDS);
		ScheduleTaskForBall scheduleTaskForBall = new ScheduleTaskForBall();
		executor.scheduleAtFixedRate(scheduleTaskForBall, 1000, 100, TimeUnit.MILLISECONDS);
		ballPeriod = 100;
		
		futures.add(pool.submit(scheduleTask));
		futures.add(pool.submit(scheduleTaskForBall));
//		timer = new Timer();
//		timer.scheduleAtFixedRate(new ScheduleTask(), 
//				1000, 5);
//		ballPeriod = 100;
//		ballTimer = new Timer();
//		ballTimer.schedule(new ScheduleTaskForBall(), 
//				1000, ballPeriod);
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
//		ballTimer = new Timer(); 
//		executor.shutdown();
		executor.scheduleAtFixedRate(new ScheduleTaskForBall(), 
				1000, period, TimeUnit.MILLISECONDS);
//		ballTimer.schedule(new ScheduleTaskForBall(), 
//				1000, period);
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		gameInit();
	}
	
	private void gameInit() {
		players = new Player[2];
		players[0] = new Player(INIT_PLAYER_X, INIT_PLAYER_Y, PLAYER);
		players[1] = new Player(210, 360, OPPONENT);
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
		for (Player player: players) {
			g2d.drawImage(player.getImage(), player.getX(), player.getY(), 
					player.getWidth(), player.getHeight(), this);
		}
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
			for (Player player: players) {
				player.keyPressed(e);
			}
		}
	}
	
	private class ScheduleTask implements Runnable {
		@Override
		public void run() {
			for (Player player: players)
				player.move();
			repaint();
			checkCollision(); // Note: checkCollision must be after repaint and it must be assigned to the Timer with the smallest period
		}
	}
	
	private class ScheduleTaskForBall implements Runnable {
		@Override
		public void run() {
			ball.move();
			repaint();
			checkBallTimer();
		}
	}
	
	private void checkBallTimer() {
		if (ballPeriod <= 50/* && ball.isSpeedDecreasable(players[0].x)*/) {
			System.out.println("period less than ten, " + ballPeriod);
			ballPeriod = 100;
			futures.get(1).cancel(true);
			ScheduleTaskForBall scheduleTaskForBall = 
					new ScheduleTaskForBall();
			executor.scheduleAtFixedRate(scheduleTaskForBall, 
					1000, ballPeriod, TimeUnit.MILLISECONDS);
			futures.set(1, (Future) scheduleTaskForBall);
			//ballTimer.cancel();
//			initBallTimer(ballPeriod);
		}
	}
	
	private void stopGame() {
		ingame = false;
		timer.cancel();
	}
	
	private void checkCollision() {
		for (Player player: players) {
			if (ball.getRect().intersects(player.getRect()) &&
					player.isPunching() && !reversing) {
				System.out.println("Reversing ball!");
				reversing = true;
				ball.reverseDirection();
				ballPeriod -= 10;
				initBallTimer(ballPeriod);
				//ballTimer.cancel();
				//initBallTimer(ballPeriod -= 10);
				
				System.out.println("ball perdio => " + ballPeriod);
//				if (ballPeriod <= 70) {
//					System.out.println("period less than ten, " + ballPeriod);
//					ballPeriod = 100;
//					ballTimer.cancel();
//					//initBallTimer(ballPeriod);
//				}
			} else if (ball.getRect().intersects(player.getRect()) &&
					!player.isPunching()) {
	//			System.out.println("Game Over");
	//			stopGame();
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
}
