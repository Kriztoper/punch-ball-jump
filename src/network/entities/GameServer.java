package network.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JPanel;
import javax.xml.stream.events.StartDocument;

import controllers.GameManager;
import punchballjump.Board;
import views.GameFrame;

public class GameServer implements PeerInterface{

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
				board = new Board(gameFrame);
				gameFrame.getCardsPanel().add(board, "board");
				gameFrame.getMenuPanel().setVisible(false);
				board.setVisible(true);
				board.requestFocus();
				
				
				if (client.isConnected()) {
					outputStream = new ObjectOutputStream(
							client.getOutputStream());
					
					inputStream = new ObjectInputStream(
							client.getInputStream());
					
					while (listening) {
						if (board.getPlayers()[0].getHearts() == 0 || board.getPlayers()[1].getHearts() == 0) {
							listening = false;
						}
						ClientData clientData = (ClientData) inputStream.readObject();
						
						if (clientData != null)
							System.out.println("Client pressed " + clientData.getKeyPressed());
						System.out.println("Ball is at (" + board.getBall().getX() + ", " + board.getBall().getY() + ")");
						
						int[] ball = {board.getBall().getX(),board.getBall().getY()};
						int[] player1 = {board.getPlayers()[0].getX(), board.getPlayers()[0].getY()};
						int[] player2 = {board.getPlayers()[1].getX(), board.getPlayers()[1].getY()};
						int[] players_hearts = {board.getPlayers()[0].getHearts(),board.getPlayers()[1].getHearts()};

						int[] powerups_top = new int[2];
						int[] powerups_bot = new int[2];
						String[] powerup_type = new String[2];
						
						if (board.getPowerups()[0] != null) {
							powerups_top[0] = board.getPowerups()[0].getX();
							powerups_bot[0] = board.getPowerups()[1].getX();
							powerup_type[0] = board.getPowerups()[0].getName();
						} else {
							powerups_top[0] = -1;
							powerups_bot[0] = -1;
							powerup_type[0] = null;
						}
						
						if (board.getPowerups()[1] != null) {
							powerups_top[1] = board.getPowerups()[0].getY();					
							powerups_bot[1] = board.getPowerups()[1].getY();
							powerup_type[1] = board.getPowerups()[1].getName();
						} else {
							powerups_top[1] = -1;					
							powerups_bot[1] = -1;
							powerup_type[1] = null;
						}
						
						outputStream.writeObject(new ServerData(ball, player1, player2, powerups_top, powerups_bot, 
								powerup_type, players_hearts, -1));
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
