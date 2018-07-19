package network.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import punchballjump.ClientBoard;
import views.GameFrame;

public class GameClient implements PeerInterface {

	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private static final int PORT = 2048;
	private GameFrame gameFrame;
	private boolean isGameOver;
	private ClientBoard clientBoard;

	public GameClient(InetAddress serverIPAddress, GameFrame gameFrame, ClientBoard clientBoard) {
		this.gameFrame = gameFrame;
		this.clientBoard = clientBoard;

		accessServer(serverIPAddress);
	}

	public void accessServer(InetAddress serverIPAddress) {
		try {
			clientBoard = new ClientBoard(gameFrame);
			socket = new Socket(serverIPAddress, PORT);
			isGameOver = false;

			// Frame clientFrame = new Frame();
			// clientBoard = new ClientBoard(gameFrame);
			// clientBoard.setBackground(Color.BLACK);
			// clientBoard.setVisible(true);
			// clientFrame.add(clientBoard);
			// JPanel jPanel = new JPanel();
			// jPanel.setBackground(Color.RED);
			// clientFrame.setContentPane(jPanel);// clientBoard);
			// clientFrame.setVisible(true);
			// JPanel cardsPanel = gameFrame.getCardsPanel();
			// CardLayout cards = gameFrame.getCards();
			// clientBoard = new ClientBoard(gameFrame);
			// clientBoard.setVisible(true);
			// cardsPanel.add(clientBoard, "board");
			// cards.show(cardsPanel, "board");
			// clientBoard.requestFocus();
			// gameFrame.setCurrentPanel("menuPanel");

			// ObjectOutputStream outputStream = new
			// ObjectOutputStream(socket.getOutputStream());

			while (!isGameOver) {
				System.out.println("Send object to server");
				// outputStream.writeObject(new ClientData(KeyEvent.VK_0));
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				ServerData serverData = (ServerData) inputStream.readObject();
				inputStream = null;
				// int[] ball = { 340, 550 };
				// int[] p1 = { 300, 50 };
				// int[] p2 = { 300, 570 };
				// int[] pow1 = { 325, 50 };
				// int[] pow2 = { 325, 520 };
				// String[] pStrings = { "+1 Heart", "Swap Hearts" };
				// int[] h = { 5, 5 };
				// ServerData serverData = new ServerData(ball, p1, p2, pow1, pow2, pStrings, h,
				// -1);
				clientBoard.updateGraphics(serverData.ballX, serverData.ballY, serverData.player1X, serverData.player1Y,
						serverData.player2X, serverData.player2Y, serverData.p1IsJumping, serverData.p1IsPunching,
						serverData.p2IsJumping, serverData.p2IsPunching, serverData.p1Powerup, serverData.p1PowerupX,
						serverData.p1PowerupY, serverData.p2Powerup, serverData.p2PowerupX, serverData.p2PowerupY,
						serverData.powUpTopMsg, serverData.powUpBotMsg, serverData.p1Hearts, serverData.p2Hearts,
						serverData.p1IsAlive, serverData.p2IsAlive, serverData.p1IsInvincible,
						serverData.p2IsInvincible, serverData.countdown, serverData.round);
				clientBoard.repaint();
				// System.out.println("Ball: " + "(" + serverData.getBall()[0] + ", " +
				// serverData.getBall()[1] + ")");
			}

		} catch (Exception e) {
			System.out.println("Can't connect to server!");
			e.printStackTrace();
		} finally {
			try {
				System.out.println("Closing socket connection in client!");
				socket.close();
			} catch (IOException e) {
				System.out.println("Can't close client...");
				e.printStackTrace();
			}
		}
	}
}
