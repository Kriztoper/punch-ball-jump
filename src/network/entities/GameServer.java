package network.entities;

import java.awt.CardLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JPanel;

import controllers.GameManager;
import punchballjump.Board;
import punchballjump.Commons;
import views.GameFrame;

public class GameServer implements PeerInterface, Commons {

	private static final int PORT = 2048;
	private ServerSocket serverSocket;
	private Socket client;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private boolean listening;
	private GameManager gameManager;
	private Board board;
	private GameFrame gameFrame;

	public GameServer(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		listening = true;

		try {
			serverSocket = new ServerSocket(PORT);
			client = serverSocket.accept();
			System.out.println("New client accepted...");
		} catch (IOException e) {
			System.out.println("Socket closed");
			e.printStackTrace();
		}

		JPanel cardsPanel = gameFrame.getCardsPanel();
		CardLayout cards = gameFrame.getCards();
		board = new Board(gameFrame, IS_NOT_COMPUTER, HUMAN);
		board.setVisible(true);
		cardsPanel.add(board, "board");
		cards.show(cardsPanel, "board");
		board.requestFocus();

		Handler handler = new Handler();
		handler.start();
	}

	private class Handler extends Thread {
		private boolean terminate;

		public Handler() {
			terminate = false;
		}

		@Override
		public void run() {
			try {
				if (client.isConnected()) {
					outputStream = new ObjectOutputStream(client.getOutputStream());

					inputStream = new ObjectInputStream(client.getInputStream());
					while (listening) {
						System.out.println("Send to client...");
						if (board.getPlayers()[0].getHearts() == 0 || board.getPlayers()[1].getHearts() == 0) {
							listening = false;
						}

						ClientData clientData = (ClientData) inputStream.readObject();

						if (clientData != null)
							System.out.println("Client pressed " + clientData.getKeyPressed());

						String p1Powerup = null;
						int p1PowerupX = -1;
						int p1PowerupY = -1;
						String p2Powerup = null;
						int p2PowerupX = -1;
						int p2PowerupY = -1;
						String pUpTopMsg = board.isPlayerPowerupActivated ? board.pCaptionMsg : null;
						String pUpBotMsg = board.isOpponentPowerupActivated ? board.oCaptionMsg : null;
						if (board.powerups[0] != null) {
							p1Powerup = board.powerups[0].getName();
							p1PowerupX = board.powerups[0].getX();
							p1PowerupY = board.powerups[0].getY();
						}
						if (board.powerups[1] != null) {
							p2Powerup = board.powerups[1].getName();
							p2PowerupX = board.powerups[1].getX();
							p2PowerupY = board.powerups[1].getY();
						}

						outputStream.writeObject(new ServerData(board.ball.getX(), board.ball.getY(),
								board.players[0].getX(), board.players[0].getY(), board.players[1].getX(),
								board.players[1].getY(), board.players[0].isJumping(), board.players[0].isPunching(),
								board.players[1].isJumping(), board.players[1].isPunching(), p1Powerup, p1PowerupX,
								p1PowerupY, p2Powerup, p2PowerupX, p2PowerupY, pUpTopMsg, pUpBotMsg,
								board.players[0].getHearts(), board.players[1].getHearts(), board.players[0].isAlive(),
								board.players[1].isAlive(), board.players[0].isInvincible(),
								board.players[1].isInvincible(), board.countdown, board.round));
						outputStream.flush();
					}
				} else {
					System.out.println("Client is Disconnected...");
				}
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}

		public void terminate() {
			terminate = true;
		}
	}
}
