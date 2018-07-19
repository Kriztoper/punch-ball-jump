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
				serverSocket = new ServerSocket(PORT);

				client = serverSocket.accept();
				System.out.println("New client accepted...");
				JPanel cardsPanel = gameFrame.getCardsPanel();
				CardLayout cards = gameFrame.getCards();
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						board = new Board(gameFrame, IS_NOT_COMPUTER);
						board.setVisible(true);
						cardsPanel.add(board, "board");
						cards.show(cardsPanel, "board");
						board.requestFocus();
					}
				});
				thread.start();

				if (client.isConnected()) {
					outputStream = new ObjectOutputStream(client.getOutputStream());

					inputStream = new ObjectInputStream(client.getInputStream());

					while (listening) {
						if (board.getPlayers()[0].getHearts() == 0 || board.getPlayers()[1].getHearts() == 0) {
							listening = false;
						}
						// ClientData clientData = (ClientData) inputStream.readObject();
						//
						// if (clientData != null)
						// System.out.println("Client pressed " + clientData.getKeyPressed());
						// System.out
						// .println("Ball is at (" + board.getBall().getX() + ", " +
						// board.getBall().getY() + ")");

						// int[] ball = { board.getBall().getX(), board.getBall().getY() };
						// int[] player1 = { board.getPlayers()[0].getX(), board.getPlayers()[0].getY()
						// };
						// int[] player2 = { board.getPlayers()[1].getX(), board.getPlayers()[1].getY()
						// };
						// int[] players_hearts = { board.getPlayers()[0].getHearts(),
						// board.getPlayers()[1].getHearts() };
						//
						// int[] powerups_top = new int[2];
						// int[] powerups_bot = new int[2];
						// String[] powerup_type = new String[2];
						//
						// if (board.getPowerups()[0] != null) {
						// powerups_top[0] = board.getPowerups()[0].getX();
						// powerups_bot[0] = board.getPowerups()[1].getX();
						// powerup_type[0] = board.getPowerups()[0].getName();
						// } else {
						// powerups_top[0] = -1;
						// powerups_bot[0] = -1;
						// powerup_type[0] = null;
						// }
						//
						// if (board.getPowerups()[1] != null) {
						// powerups_top[1] = board.getPowerups()[0].getY();
						// powerups_bot[1] = board.getPowerups()[1].getY();
						// powerup_type[1] = board.getPowerups()[1].getName();
						// } else {
						// powerups_top[1] = -1;
						// powerups_bot[1] = -1;
						// powerup_type[1] = null;
						// }

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

						ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
						outputStream.writeObject(new ServerData(board.ball.getX(), board.ball.getY(),
								board.players[0].getX(), board.players[0].getY(), board.players[1].getX(),
								board.players[1].getY(), board.players[0].isJumping(), board.players[0].isPunching(),
								board.players[1].isJumping(), board.players[1].isPunching(), p1Powerup, p1PowerupX,
								p1PowerupY, p2Powerup, p2PowerupX, p2PowerupY, pUpTopMsg, pUpBotMsg,
								board.players[0].getHearts(), board.players[1].getHearts(), board.players[0].isAlive(),
								board.players[1].isAlive(), board.players[0].isInvincible(),
								board.players[1].isInvincible(), board.countdown, board.round));
						outputStream.flush();
						outputStream = null;
					}
				} else {
					System.out.println("Client is Disconnected...");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		public void terminate() {
			terminate = true;
		}
	}
}
